package org.example.taskmanager.service;

import org.example.taskmanager.model.Task;
import org.example.taskmanager.model.TaskStatus;
import java.util.List;

public interface TaskService {
    List<Task> getTasks();
    Task getTask(long id);
    Task addTask(Task task);
    Task updateTask(Task task);
    Task updateTaskStatus(Long id, TaskStatus status);
    void deleteTask(long id);
}
