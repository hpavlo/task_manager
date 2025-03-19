package org.example.taskmanager.controller;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.example.taskmanager.dto.TaskRequestDto;
import org.example.taskmanager.dto.TaskResponseDto;
import org.example.taskmanager.dto.mapper.RequestDtoMapper;
import org.example.taskmanager.dto.mapper.ResponseDtoMapper;
import org.example.taskmanager.model.Task;
import org.example.taskmanager.service.TaskService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {
    private final TaskService taskService;
    private final RequestDtoMapper<TaskRequestDto, Task> requestDtoMapper;
    private final ResponseDtoMapper<TaskResponseDto, Task> responseDtoMapper;

    public TaskController(TaskService taskService,
                          RequestDtoMapper<TaskRequestDto, Task> requestDtoMapper,
                          ResponseDtoMapper<TaskResponseDto, Task> responseDtoMapper) {
        this.taskService = taskService;
        this.requestDtoMapper = requestDtoMapper;
        this.responseDtoMapper = responseDtoMapper;
    }

    @GetMapping
    @ApiResponse(description = "Return all tasks")
    public List<TaskResponseDto> getTasks() {
        return taskService.getTasks()
                .stream()
                .map(responseDtoMapper::mapToDto)
                .toList();
    }

    @GetMapping("/{id}")
    @ApiResponse(description = "Return Task by id")
    public TaskResponseDto getTaskById(@PathVariable Long id) {
        return responseDtoMapper.mapToDto(taskService.getTask(id));
    }

    @PostMapping
    @ApiResponse(description = "Return created Task")
    public TaskResponseDto createTask(@RequestBody TaskRequestDto requestDto) {
        return responseDtoMapper.mapToDto(taskService.addTask(requestDtoMapper.mapToModel(requestDto)));
    }

    @PatchMapping
    @ApiResponse(description = "Return updated Task")
    public TaskResponseDto updateTask(@RequestBody TaskRequestDto requestDto) {
        return responseDtoMapper.mapToDto(taskService.updateTask(requestDtoMapper.mapToModel(requestDto)));
    }

    @DeleteMapping("/{id}")
    @ApiResponse(description = "Delete task by id, return no data")
    public void deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
    }
}
