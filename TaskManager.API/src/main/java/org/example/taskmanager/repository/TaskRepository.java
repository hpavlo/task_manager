package org.example.taskmanager.repository;

import org.example.taskmanager.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task, Long> {
    Optional<Task> findTaskByDescription(String description);
}
