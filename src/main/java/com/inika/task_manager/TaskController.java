package com.inika.task_manager;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    // CREATE — POST /api/tasks → 201
    @PostMapping
    public ResponseEntity<Task> createTask(@Valid @RequestBody TaskRequestDTO dto) {
        Task created = taskService.createTask(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    // READ ALL with optional filters — GET /api/tasks?completed=&priority=
    @GetMapping
    public List<Task> getTasks(
            @RequestParam(required = false) Boolean completed,
            @RequestParam(required = false) Priority priority) {
        return taskService.getAllTasks(completed, priority);
    }

    // READ ONE — GET /api/tasks/{id}
    @GetMapping("/{id}")
    public Task getTaskById(@PathVariable Long id) {
        return taskService.getTaskById(id);
    }

    // UPDATE — PUT /api/tasks/{id}
    @PutMapping("/{id}")
    public Task updateTask(@PathVariable Long id, @Valid @RequestBody TaskRequestDTO dto) {
        return taskService.updateTask(id, dto);
    }

    // PATCH complete — PATCH /api/tasks/{id}/complete
    @PatchMapping("/{id}/complete")
    public Task markComplete(@PathVariable Long id) {
        return taskService.markComplete(id);
    }

    // DELETE — DELETE /api/tasks/{id} → 204
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }
}