package org.example.taskmanager.service;

import org.example.taskmanager.model.Task;
import java.util.List;

public interface TaskService {
    List<Task> getTasks();
    Task getTask(long id);
    Task addTask(Task task);
    Task updateTask(Task task);
    void deleteTask(long id);
}
