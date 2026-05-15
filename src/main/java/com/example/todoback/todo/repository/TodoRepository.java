package com.example.todoback.todo.repository;

import com.example.todoback.todo.domain.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoRepository extends JpaRepository<Todo, Long> {

    long countByCompletedTrue();

    long deleteByCompletedTrue();
}
