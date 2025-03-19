package org.example.taskmanager.model;

public enum TaskStatus {
    TODO("To do"),
    IN_PROGRESS("In progress"),
    DONE("Done"),
    PAUSED("Paused");

    TaskStatus(String value) {}
}
