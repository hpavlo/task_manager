package org.example.taskmanager.service;

import org.example.taskmanager.model.TaskStatus;
import org.example.taskmanager.repository.TaskRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
public class TaskSchedulerServiceImpl implements TaskSchedulerService {
    private final TaskRepository taskRepository;

    public TaskSchedulerServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    @Scheduled(cron = "0 0 0 * * *")
    public void pauseTasksAtMidnight() {
        taskRepository.findAll().stream()
                .filter(task -> task.getStatus() == TaskStatus.PAUSED)
                .forEach(task -> {
                    task.setStatus(TaskStatus.PAUSED);
                    taskRepository.save(task);
                });
        System.out.println("Tasks paused at " + LocalDateTime.now());
    }
}
