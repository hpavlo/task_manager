package org.example.taskmanager.dto;

import lombok.Getter;
import lombok.Setter;
import org.example.taskmanager.model.TaskStatus;
import java.time.LocalDateTime;

@Getter
@Setter
public class TaskResponseDto {
    private Long id;
    private String description;
    private TaskStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime closedAt;
}
