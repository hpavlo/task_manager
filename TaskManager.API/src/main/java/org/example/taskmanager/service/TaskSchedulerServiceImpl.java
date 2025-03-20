package org.example.taskmanager.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.taskmanager.model.TaskStatus;
import org.example.taskmanager.repository.TaskRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
public class TaskSchedulerServiceImpl implements TaskSchedulerService {
    private static final Logger logger = LogManager.getLogger(TaskSchedulerServiceImpl.class);
    private final TaskRepository taskRepository;

    public TaskSchedulerServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    @Scheduled(cron = "0 0 0 * * *")
    public void pauseTasksAtMidnight() {
        taskRepository.findAll().stream()
                .filter(task -> task.getStatus() == TaskStatus.IN_PROGRESS)
                .forEach(task -> {
                    task.setStatus(TaskStatus.PAUSED);
                    taskRepository.save(task);
                });
        logger.debug("All tasks with status IN_PROGRESS paused at " + LocalDateTime.now());
    }
}
