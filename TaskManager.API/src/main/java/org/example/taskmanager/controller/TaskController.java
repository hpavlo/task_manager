package org.example.taskmanager.controller;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.taskmanager.dto.TaskRequestDto;
import org.example.taskmanager.dto.TaskResponseDto;
import org.example.taskmanager.dto.mapper.RequestDtoMapper;
import org.example.taskmanager.dto.mapper.ResponseDtoMapper;
import org.example.taskmanager.model.Task;
import org.example.taskmanager.model.TaskStatus;
import org.example.taskmanager.service.TaskService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/tasks")
public class TaskController {
    private static final Logger logger = LogManager.getLogger(TaskController.class);
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
    public ResponseEntity<TaskResponseDto> getTaskById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(responseDtoMapper.mapToDto(taskService.getTask(id)));
        }
        catch (EntityNotFoundException e) {
            logger.error("Can't get task by id. Task with id {} not found", id);
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    @ApiResponse(description = "Creates and return new Task")
    public TaskResponseDto createTask(@Valid @RequestBody TaskRequestDto requestDto) {
        return responseDtoMapper.mapToDto(taskService.addTask(requestDtoMapper.mapToModel(requestDto)));
    }

    @PatchMapping("/{id}")
    @ApiResponse(description = "Updates Task info and return updated task")
    public ResponseEntity<TaskResponseDto> updateTask(@PathVariable Long id, @Valid @RequestBody TaskRequestDto requestDto) {
        try {
            Task task = taskService.getTask(id);
            task.setDescription(requestDto.getDescription());
            return ResponseEntity.ok(responseDtoMapper.mapToDto(taskService.updateTask(task)));
        }
        catch (EntityNotFoundException e) {
            logger.error("Can't update task. Task with id {} not found", id);
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    @ApiResponse(description = "Update task status")
    public ResponseEntity<TaskResponseDto> updateTaskStatus(@PathVariable Long id, @RequestParam("status") TaskStatus taskStatus) {
        try {
            return ResponseEntity.ok(responseDtoMapper.mapToDto(taskService.updateTaskStatus(id, taskStatus)));
        }
        catch (EntityNotFoundException e) {
            logger.error("Can't update task status to {}. Task with id {} not found", taskStatus, id);
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @ApiResponse(description = "Delete task by id")
    public void deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
    }

    // If we have validation exception, we will return bad request with exception description
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        logger.error(errors);
        return ResponseEntity.badRequest().body(errors);
    }
}
