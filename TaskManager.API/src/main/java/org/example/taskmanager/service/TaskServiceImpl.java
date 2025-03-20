package org.example.taskmanager.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.taskmanager.model.Task;
import org.example.taskmanager.model.TaskStatus;
import org.example.taskmanager.repository.TaskRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TaskServiceImpl implements TaskService {
    private static final Logger logger = LogManager.getLogger(TaskServiceImpl.class);
    private final TaskRepository taskRepository;

    public TaskServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public List<Task> getTasks() {
        return taskRepository.findAll();
    }

    @Override
    public Task getTask(long id) {
        return taskRepository.getReferenceById(id);
    }

    @Override
    public Task addTask(Task task) {
        Optional<Task> taskOptional = taskRepository.findTaskByDescription(task.getDescription());
        if (taskOptional.isPresent()) {
            logger.debug("Task with such description already exist: " + task.getDescription());
            return taskOptional.get();
        }

        task.setStatus(TaskStatus.TODO);
        task.setCreatedAt(LocalDateTime.now());
        return taskRepository.save(task);
    }

    @Override
    public Task updateTask(Task task) {
        return taskRepository.save(task);
    }

    @Override
    public Task updateTaskStatus(Long id, TaskStatus status) {
        Task task = taskRepository.getReferenceById(id);
        task.setStatus(status);
        if (status == TaskStatus.DONE) {
            task.setClosedAt(LocalDateTime.now());
        }
        return taskRepository.save(task);
    }

    @Override
    public void deleteTask(long id) {
        taskRepository.deleteById(id);
    }
}
