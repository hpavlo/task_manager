package org.example.taskmanager.service;

import org.example.taskmanager.model.Task;
import org.example.taskmanager.model.TaskStatus;
import org.example.taskmanager.repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TaskServiceImplTest {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskServiceImpl taskService;

    private Task task1;
    private Task task2;

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
    }

    @Test
    void getTasks_shouldReturnAllTasks() {
        when(taskRepository.findAll()).thenReturn(Arrays.asList(task1, task2));
        List<Task> tasks = taskService.getTasks();
        assertEquals(2, tasks.size());
        assertEquals(task1, tasks.get(0));
        assertEquals(task2, tasks.get(1));
        verify(taskRepository, times(1)).findAll();
    }

    @Test
    void getTask_shouldReturnTaskById() {
        when(taskRepository.getReferenceById(1L)).thenReturn(task1);
        Task task = taskService.getTask(1L);
        assertEquals(task1, task);
        verify(taskRepository, times(1)).getReferenceById(1L);
    }

    @Test
    void addTask_shouldAddAndReturnTask_whenTaskDoesNotExist() {
        when(taskRepository.findTaskByDescription(task1.getDescription())).thenReturn(Optional.empty());
        when(taskRepository.save(any(Task.class))).thenReturn(task1);

        Task task = taskService.addTask(task1);

        assertEquals(task1, task);
        assertEquals(TaskStatus.TODO, task.getStatus());
        verify(taskRepository, times(1)).findTaskByDescription(task1.getDescription());
        verify(taskRepository, times(1)).save(any(Task.class));
    }

    @Test
    void addTask_shouldReturnExistingTask_whenTaskExists() {
        when(taskRepository.findTaskByDescription(task1.getDescription())).thenReturn(Optional.of(task1));

        Task task = taskService.addTask(task1);

        assertEquals(task1, task);
        verify(taskRepository, times(1)).findTaskByDescription(task1.getDescription());
        verify(taskRepository, never()).save(any(Task.class));
    }

    @Test
    void updateTask_shouldUpdateAndReturnTask() {
        when(taskRepository.save(task1)).thenReturn(task1);
        Task task = taskService.updateTask(task1);
        assertEquals(task1, task);
        verify(taskRepository, times(1)).save(task1);
    }

    @Test
    void updateTaskStatus_shouldUpdateStatusAndReturnTask() {
        when(taskRepository.getReferenceById(1L)).thenReturn(task1);
        when(taskRepository.save(task1)).thenReturn(task1);
        Task task = taskService.updateTaskStatus(1L, TaskStatus.DONE);
        assertEquals(TaskStatus.DONE, task.getStatus());
        verify(taskRepository, times(1)).getReferenceById(1L);
        verify(taskRepository, times(1)).save(task1);
    }

    @Test
    void deleteTask_shouldDeleteTaskById() {
        taskService.deleteTask(1L);
        verify(taskRepository, times(1)).deleteById(1L);
    }
}