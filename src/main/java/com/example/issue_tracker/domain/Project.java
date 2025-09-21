package com.example.issue_tracker.domain;

import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(name = "projects")

public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "`key`", nullable = false, unique = true, length = 16)
    private String key;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt = Instant.now();

    protected Project() {
    }

    public Project(String key, String name) {
        this.key = key;
        this.name = name;
    }

    @PrePersist
    void prePersist() {
        if (createdAt == null)
            createdAt = Instant.now();
    }

    public Long getId() {
        return id;
    }

    public String getKey() {
        return key;
    }

    public String getName() {
        return name;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

}
