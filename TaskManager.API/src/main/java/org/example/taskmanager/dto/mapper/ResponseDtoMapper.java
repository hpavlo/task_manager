package org.example.taskmanager.dto.mapper;

public interface ResponseDtoMapper<D, T> {
    D mapToDto(T t);
}
