package org.example.taskmanager.service;

public interface TaskSchedulerService {
    /**
     * Pauses all tasks that are in the 'IN_PROGRESS' status at midnight.
     * This method is scheduled to run daily at 00:00:00.
     * It retrieves all tasks from the database, filters out the ones that are not
     * 'IN_PROGRESS', and sets the status of the remaining tasks to 'PAUSED'.
     * It then saves the updated tasks back to the database.
     *
     * This method should be used for automated task status management, ensuring
     * that tasks are paused at the end of each day.
     *
     * Note: This method does not return any value. It performs an action on the tasks.
     */
    void pauseTasksAtMidnight();
}
