package com.example.issue_tracker.repository;

import com.example.issue_tracker.domain.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Long> {
    boolean existsByKey(String key);
}
