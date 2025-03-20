package org.example.taskmanager.service;

import org.example.taskmanager.model.Task;
import org.example.taskmanager.model.TaskStatus;
import java.util.List;

public interface TaskService {

    /**
     * Retrieves a list of all tasks from the database.
     *
     * @return A list of tasks stored in the database.
     * Returns an empty list if no tasks are found.
     */
    List<Task> getTasks();

    /**
     * Retrieves a task by its unique identifier.
     *
     * @param id The unique identifier of the task to retrieve.
     * @return The task found with the specified identifier.
     * @throws jakarta.persistence.EntityNotFoundException if the task is not found.
     */
    Task getTask(long id);

    /**
     * Adds a new task to the database.
     * If the task with the same description are already exist - returns that task
     *
     * @param task The task to add.
     * @return The added task with an automatically generated identifier.
     * @throws IllegalArgumentException if the task is null.
     */
    Task addTask(Task task);

    /**
     * Updates an existing task in the database.
     *
     * @param task The task to update.
     * @return The updated task.
     * @throws jakarta.persistence.EntityNotFoundException if the task is not found.
     * @throws IllegalArgumentException if the task is null.
     */
    Task updateTask(Task task);

    /**
     * Updates the status of a task by its identifier.
     *
     * @param id     The identifier of the task to update the status for.
     * @param status The new status of the task.
     * @return The updated task.
     * @throws jakarta.persistence.EntityNotFoundException if the task is not found.
     * @throws IllegalArgumentException if the status is null.
     */
    Task updateTaskStatus(Long id, TaskStatus status);

    /**
     * Deletes a task by its identifier.
     *
     * @param id The identifier of the task to delete.
     */
    void deleteTask(long id);
}
