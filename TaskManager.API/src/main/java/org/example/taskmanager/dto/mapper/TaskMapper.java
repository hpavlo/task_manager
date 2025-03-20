package org.example.taskmanager.dto.mapper;

import org.example.taskmanager.dto.TaskRequestDto;
import org.example.taskmanager.dto.TaskResponseDto;
import org.example.taskmanager.model.Task;
import org.springframework.stereotype.Component;

@Component
public class TaskMapper implements RequestDtoMapper<TaskRequestDto, Task>, ResponseDtoMapper<TaskResponseDto, Task> {
    @Override
    public Task mapToModel(TaskRequestDto dto) {
        Task task = new Task();
        task.setDescription(dto.getDescription());
        return task;
    }

    @Override
    public TaskResponseDto mapToDto(Task task) {
        TaskResponseDto dto = new TaskResponseDto();
        dto.setId(task.getId());
        dto.setDescription(task.getDescription());
        dto.setStatus(task.getStatus());
        dto.setCreatedAt(task.getCreatedAt());
        dto.setClosedAt(task.getClosedAt());
        return dto;
    }
}
