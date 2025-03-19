package org.example.taskmanager.dto.mapper;

public interface RequestDtoMapper<D, T> {
    T mapToModel(D dto);
}
