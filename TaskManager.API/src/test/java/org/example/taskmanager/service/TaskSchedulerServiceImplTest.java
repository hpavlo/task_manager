package org.example.taskmanager.service;

import org.example.taskmanager.model.Task;
import org.example.taskmanager.model.TaskStatus;
import org.example.taskmanager.repository.TaskRepository;
import org.example.taskmanager.service.TaskSchedulerServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TaskSchedulerServiceImplTest {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskSchedulerServiceImpl taskSchedulerService;

    private Task task1;
    private Task task2;
    private Task task3;

    @BeforeEach
    void setUp() {
        task1 = new Task();
        task1.setId(1L);
        task1.setDescription("Task 1");
        task1.setStatus(TaskStatus.TODO);
        task1.setCreatedAt(LocalDateTime.now());

        task2 = new Task();
        task2.setId(2L);
        task2.setDescription("Task 2");
        task2.setStatus(TaskStatus.IN_PROGRESS);
        task2.setCreatedAt(LocalDateTime.now());

        task3 = new Task();
        task3.setId(3L);
        task3.setDescription("Task 3");
        task3.setStatus(TaskStatus.DONE);
        task3.setCreatedAt(LocalDateTime.now());
    }

    @Test
    void pauseTasksAtMidnight_shouldPauseTasks() {
        List<Task> tasks = Arrays.asList(task1, task2, task3);
        when(taskRepository.findAll()).thenReturn(tasks);

        taskSchedulerService.pauseTasksAtMidnight();

        verify(taskRepository, times(1)).findAll();
        verify(taskRepository, times(1)).save(any(Task.class));
        verify(taskRepository, never()).save(task1);
        verify(taskRepository, never()).save(task3);

        verify(taskRepository).save(argThat(task -> task.getId() == 2L && task.getStatus() == TaskStatus.PAUSED));
    }
}