package com.inika.task_manager;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    // CREATE — takes DTO, builds entity, saves
    public Task createTask(TaskRequestDTO dto) {
        Task task = new Task();
        task.setTitle(dto.getTitle());
        task.setDescription(dto.getDescription());
        task.setDueDate(dto.getDueDate());
        task.setPriority(dto.getPriority());
        task.setCompleted(false);
        task.setCreatedAt(LocalDateTime.now());
        return taskRepository.save(task);
    }

   public List<Task> getAllTasks(Boolean completed, Priority priority) {
    List<Task> tasks = taskRepository.findAll();  // or filtered query

    return tasks.stream()
            .filter(t -> completed == null || t.isCompleted() == completed)
            .filter(t -> priority == null || t.getPriority() == priority)
            .sorted(Comparator
                    .comparingInt((Task t) -> switch (t.getPriority()) {
                        case HIGH -> 0;
                        case MEDIUM -> 1;
                        case LOW -> 2;
                    })
                    .thenComparing(Task::getDueDate))
            .toList();
}

    // READ ONE — throws TaskNotFoundException if missing
    public Task getTaskById(Long id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));
    }

    // UPDATE — find existing, copy fields from DTO
    public Task updateTask(Long id, TaskRequestDTO dto) {
        Task existing = getTaskById(id);
        existing.setTitle(dto.getTitle());
        existing.setDescription(dto.getDescription());
        existing.setDueDate(dto.getDueDate());
        existing.setPriority(dto.getPriority());
        return taskRepository.save(existing);
    }

    // PATCH complete — mark as completed
    public Task markComplete(Long id) {
        Task task = getTaskById(id);
        task.setCompleted(true);
        return taskRepository.save(task);
    }

    // DELETE — unchanged
    public void deleteTask(Long id) {
    getTaskById(id);   // throws TaskNotFoundException → 404 if missing
    taskRepository.deleteById(id);
}
}