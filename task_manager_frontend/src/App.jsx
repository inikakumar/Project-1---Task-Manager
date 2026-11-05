import { useEffect, useState } from "react";
import axios from "axios";
import Dropdown from "./Dropdown";
import "./App.css";

const API = "http://localhost:8080/api/tasks";

const PRIORITY_COLORS = {
  HIGH: "#ef4444",
  MEDIUM: "#f59e0b",
  LOW: "#10b981",
};

const PRIORITY_OPTIONS = [
  { value: "LOW", label: "LOW" },
  { value: "MEDIUM", label: "MEDIUM" },
  { value: "HIGH", label: "HIGH" },
];

const STATUS_FILTER_OPTIONS = [
  { value: "", label: "All statuses" },
  { value: "false", label: "Active" },
  { value: "true", label: "Completed" },
];

const PRIORITY_FILTER_OPTIONS = [
  { value: "", label: "All priorities" },
  ...PRIORITY_OPTIONS,
];

function App() {
  const [tasks, setTasks] = useState([]);
  const [title, setTitle] = useState("");
  const [description, setDescription] = useState("");
  const [dueDate, setDueDate] = useState("");
  const [priority, setPriority] = useState("MEDIUM");
  const [error, setError] = useState(null);
  const [loading, setLoading] = useState(false);

  const [filterCompleted, setFilterCompleted] = useState("");
  const [filterPriority, setFilterPriority] = useState("");

  const fetchTasks = async () => {
    setLoading(true);
    try {
      const params = {};
      if (filterCompleted !== "") params.completed = filterCompleted;
      if (filterPriority !== "") params.priority = filterPriority;
      const res = await axios.get(API, { params });
      setTasks(res.data);
      setError(null);
    } catch {
      setError("Failed to load tasks");
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchTasks();
  }, [filterCompleted, filterPriority]);

  const createTask = async (e) => {
    e.preventDefault();
    try {
      await axios.post(API, { title, description, dueDate, priority });
      setTitle("");
      setDescription("");
      setDueDate("");
      setPriority("MEDIUM");
      setError(null);
      fetchTasks();
    } catch (err) {
      if (err.response?.status === 400) {
        const details = err.response.data.details;
        setError(
          Object.entries(details)
            .map(([k, v]) => `${k}: ${v}`)
            .join(" · ")
        );
      } else {
        setError("Failed to create task");
      }
    }
  };

  const markComplete = async (id) => {
    await axios.patch(`${API}/${id}/complete`);
    fetchTasks();
  };

  const deleteTask = async (id) => {
    await axios.delete(`${API}/${id}`);
    fetchTasks();
  };

  return (
    <div className="page">
      <header className="header">
        <h1>Task Manager</h1>
        <p className="subtitle">Stay on top of your work</p>
      </header>

      <section className="card">
        <h2>New Task</h2>
        <form onSubmit={createTask} className="form">
          <div className="row">
            <input
              placeholder="Title"
              value={title}
              onChange={(e) => setTitle(e.target.value)}
              required
            />
            <input
              placeholder="Description (optional)"
              value={description}
              onChange={(e) => setDescription(e.target.value)}
            />
          </div>
          <div className="row">
            <input
              type="date"
              className="date-input"
              value={dueDate}
              onChange={(e) => setDueDate(e.target.value)}
              required
            />
            <Dropdown
              value={priority}
              onChange={setPriority}
              options={PRIORITY_OPTIONS}
            />
            <button type="submit" className="btn primary">
              + Add Task
            </button>
          </div>
        </form>
        {error && <div className="error">{error}</div>}
      </section>

      <section className="card">
        <div className="toolbar">
          <h2>Tasks</h2>
          <div className="filters">
            <Dropdown
              className="filter"
              value={filterCompleted}
              onChange={setFilterCompleted}
              options={STATUS_FILTER_OPTIONS}
            />
            <Dropdown
              className="filter"
              value={filterPriority}
              onChange={setFilterPriority}
              options={PRIORITY_FILTER_OPTIONS}
            />
          </div>
        </div>

        {loading && <p className="muted">Loading…</p>}

        {!loading && tasks.length === 0 && (
          <p className="muted">No tasks match this filter.</p>
        )}

        <ul className="list">
          {tasks.map((t) => (
            <li key={t.id} className={t.completed ? "done" : ""}>
              <div className="task-main">
                <div className="task-title">
                  <span
                    className="badge"
                    style={{ background: PRIORITY_COLORS[t.priority] }}
                  >
                    {t.priority}
                  </span>
                  <span
                    className={`badge status ${
                      t.completed ? "completed" : "active"
                    }`}
                  >
                    {t.completed ? "Completed" : "Active"}
                  </span>
                  <strong>{t.title}</strong>
                </div>
                {t.description && <div className="desc">{t.description}</div>}
                <div className="meta">Due: {t.dueDate}</div>
              </div>
              <div className="actions">
                {!t.completed && (
                  <button
                    className="btn success"
                    onClick={() => markComplete(t.id)}
                  >
                    ✓ Complete
                  </button>
                )}
                <button
                  className="btn danger"
                  onClick={() => deleteTask(t.id)}
                >
                  Delete
                </button>
              </div>
            </li>
          ))}
        </ul>
      </section>
    </div>
  );
}

export default App;