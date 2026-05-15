package com.example.todoback.todo.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import java.time.Instant;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Entity
@Table(name = "todos")
public class Todo {

    @Setter(AccessLevel.NONE)   // ne génère pas de setter pour cet élément.
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "todo_sequence")
    @SequenceGenerator(name = "todo_sequence", sequenceName = "todo_sequence", allocationSize = 1)
    private Long id;

    @Column(nullable = false, length = 160)
    private String title;

    @Column(length = 2000)
    private String description;

    @Column(nullable = false)
    private boolean completed;

    @Setter(AccessLevel.NONE)
    @Column(nullable = false)
    private Instant createdAt;

    @Column(nullable = false)
    private Instant updatedAt;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 16)
    private TodoPriority priority;

    @Column(length = 80)
    private String category;

    private Instant dueDate;
}
