package com.inika.task_manager;
import jakarta.validation.constraints.*;
import java.time.LocalDate;

public class TaskRequestDTO {

    @NotBlank(message = "must not be blank")
    @Size(min = 3, max = 100, message = "must be between 3 and 100 characters")
    private String title;

    @Size(max = 500, message = "must be at most 500 characters")
    private String description;

    @NotNull(message = "must not be null")
    @FutureOrPresent(message = "must be a future or present date")
    private LocalDate dueDate;

    @NotNull(message = "must not be null")
    private Priority priority;

    public TaskRequestDTO() {
    }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public LocalDate getDueDate() { return dueDate; }
    public void setDueDate(LocalDate dueDate) { this.dueDate = dueDate; }

    public Priority getPriority() { return priority; }
    public void setPriority(Priority priority) { this.priority = priority; }
}