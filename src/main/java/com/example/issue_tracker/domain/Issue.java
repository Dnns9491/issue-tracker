package com.example.issue_tracker.domain;

import jakarta.persistence.*;

import java.time.Instant;

@Entity @Table(name = "issues")
public class Issue {

    @Id @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne (optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id")
    private Project project;

    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn(name = "assignee_id")
    private AppUser assignee;

    @Column(name = "title", nullable = false, length = 200)
    private String title;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private IssueStatus status = IssueStatus.OPEN;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt = Instant.now();

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt = Instant.now();

    protected Issue() {}
        public Issue(Project p, AppUser a, String title, String desc) {
            this.project = p;
            this.assignee = a;
            this.title = title;
            this.description = desc;
        }

        @PreUpdate void touch() {
            updatedAt = Instant.now();
        }

        public Long getId() {
            return id;
        }

        public Project getProject() {
            return project;
        }

        public AppUser getAssignee() {
            return assignee;
        }

        public String getTitle() {
            return title;
        }

        public String getDescription() {
            return description;
        }

        public IssueStatus getStatus() {
            return status;
        }

        public Instant getCreatedAt() {
            return createdAt;
        }

        public Instant getUpdatedAt() {
            return updatedAt;
        }

        public void setTitle(String t){
            this.title = t;
        }

        public void setDescription(String d){
            this.description = d;
        }

        public void setStatus(IssueStatus s){
            this.status = s;
        }

        public void setAssignee(AppUser u){
            this.assignee = u;
        }
    }
