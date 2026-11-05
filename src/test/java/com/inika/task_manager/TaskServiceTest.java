package com.inika.task_manager;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskService taskService;

    // TEST 1 — createTask returns task with correct title
    @Test
    void createTask_shouldReturnTaskWithCorrectTitle() {
        TaskRequestDTO dto = new TaskRequestDTO();
        dto.setTitle("Learn Spring Boot");
        dto.setDescription("Unit test");
        dto.setDueDate(LocalDate.now().plusDays(7));
        dto.setPriority(Priority.HIGH);

        // When repository.save is called, return the argument it received
        when(taskRepository.save(any(Task.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Task result = taskService.createTask(dto);

        assertNotNull(result);
        assertEquals("Learn Spring Boot", result.getTitle());
        assertEquals(Priority.HIGH, result.getPriority());
        assertFalse(result.isCompleted());
        assertNotNull(result.getCreatedAt());
    }

    // TEST 2 — getTaskById(999) throws TaskNotFoundException
    @Test
    void getTaskById_whenMissing_shouldThrowTaskNotFoundException() {
        when(taskRepository.findById(999L)).thenReturn(java.util.Optional.empty());

        assertThrows(TaskNotFoundException.class,
                () -> taskService.getTaskById(999L));
    }
}