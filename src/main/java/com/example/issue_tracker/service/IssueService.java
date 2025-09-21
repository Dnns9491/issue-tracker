package com.example.issue_tracker.service;

import com.example.issue_tracker.domain.AppUser;
import com.example.issue_tracker.domain.Issue;
import com.example.issue_tracker.domain.IssueStatus;
import com.example.issue_tracker.dto.CreateIssueRequest;
import com.example.issue_tracker.dto.IssueDto;
import com.example.issue_tracker.dto.UserDto;
import com.example.issue_tracker.dto.ProjectDto;
import com.example.issue_tracker.repository.IssueRepository;
import com.example.issue_tracker.repository.ProjectRepository;
import com.example.issue_tracker.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.issue_tracker.dto.UpdateIssueRequest;   // <- for your DTO
import org.springframework.data.domain.Pageable;            // <- for paging
import jakarta.persistence.EntityNotFoundException;         // <- if missing


import java.util.Optional;

@Service
public class IssueService {
    private final IssueRepository issues;
    private final ProjectRepository projects;
    private final UserRepository users;

    public IssueService(IssueRepository issues, ProjectRepository projects, UserRepository users) {
        this.issues = issues;
        this.projects = projects;
        this.users = users;
    }

    @Transactional
    public IssueDto create(CreateIssueRequest req) {
        var project = projects.findById(req.projectId()).orElseThrow(() -> notFound("Project", req.projectId()));
        AppUser assignee = req.assigneeId() == null ? null : users.findById(req.assigneeId()).orElseThrow(() -> notFound("User", req.assigneeId()));

        var issue = new Issue(project, assignee, req.title(), req.description());
        issue = issues.save(issue);
        return toDto(issue);
    }

    @Transactional
    public IssueDto update(Long id, UpdateIssueRequest req) {
        var issue = issues.findById(id).orElseThrow(() -> notFound("Issue", id));
        if (req.title() != null) issue.setTitle(req.title());
        if (req.description() != null) issue.setDescription(req.description());
        if (req.status() != null) issue.setStatus(req.status());
        if (req.assigneeId() != null) {
            var u = users.findById(req.assigneeId()).orElseThrow(() -> notFound("User", req.assigneeId()));
            issue.setAssignee(u);
        }
        return toDto(issue);
    }

    @Transactional
    public void delete(Long id) {
        if (!issues.existsById(id)) throw notFound("Issue", id);
        issues.deleteById(id);
    }

    @Transactional(readOnly = true)
    public Page<IssueDto> search(Optional<IssueStatus> status, Optional<Long> assigneeId, Optional<Long> projectId, Pageable pageable) {
        Specification<Issue> spec = (root, query, criteriaBuilder) -> null;
        if (status.isPresent()) {
            spec = spec.and((r, q, cb) -> cb.equal(r.get("status"), status.get()));
        }
        if (assigneeId.isPresent()) {
            spec = spec.and((r, q, cb) -> cb.equal(r.get("assignee").get("id"), assigneeId.get()));
        }
        if (projectId.isPresent()) {
            spec = spec.and((r, q, cb) -> cb.equal(r.get("project").get("id"), projectId.get()));
        }
        return issues.findAll(spec, pageable).map(this::toDto);
    }


    private IssueDto toDto(Issue i) {
        UserDto assigneeDto = (i.getAssignee() == null) ? null : new UserDto(i.getAssignee().getId(), i.getAssignee().getEmail(), i.getAssignee().getName());
        ProjectDto projectDto = new ProjectDto(i.getProject().getId(), i.getProject().getKey(), i.getProject().getName(), i.getProject().getCreatedAt());

        return new IssueDto(i.getId(), i.getTitle(), i.getDescription(), i.getStatus(), assigneeDto, projectDto, i.getCreatedAt(), i.getUpdatedAt());
    }

    private EntityNotFoundException notFound(String type, Object id) {
        return new EntityNotFoundException(type + " " + id + " not found");
    }

    @Transactional(readOnly = true)
    public IssueDto getById(Long id) {
        try {
            var issue = issues.findById(id).orElseThrow(() -> notFound("Issue", id));
            return toDto(issue);
        } catch (Exception e) {
            System.err.println("ERROR getting issue " + id + ": " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    //Adding a method to assign an issue to a user
    @Transactional
    public IssueDto assignTo(Long issueId, Long userId) {
        var issue = issues.findById(issueId).orElseThrow(() -> notFound("Issue", issueId));
        var user = users.findById(userId).orElseThrow(() -> notFound("User", userId));
        issue.setAssignee(user);
        return toDto(issue);
    }

    //Adding a method to unassign an issue from a user
    @Transactional
    public IssueDto unassign(Long issueId, Long userId) {
        var issue = issues.findById(issueId).orElseThrow(() -> notFound("Issue", issueId));
        issue.setAssignee(null);
        return toDto(issue);
    }

    //Adding a method to get unassigned issues
    @Transactional(readOnly = true)
    public Page<IssueDto> getUnassigned(Pageable pageable) {
        //Build a query: "WHERE assignee_id IS NULL"
        Specification<Issue> spec = (root, query, cb) -> cb.isNull(root.get("assignee"));
        //Execute the query with pagination
        return issues.findAll(spec, pageable).map(this::toDto);
    }

    //Adding a method to get issues assigned to a user
    @Transactional(readOnly = true)
    public Page<IssueDto> getAssignedTo(Long userId, Pageable pageable) {
        Specification<Issue> spec = (root, query, cb) -> cb.equal(root.get("assignee").get("id"), userId);
        return issues.findAll(spec, pageable).map(this::toDto);
    }

    //Adding a method for quick status update - mark issue open
    @Transactional
    public IssueDto markOpen(Long issueId) {
        var issue = issues.findById(issueId)
                .orElseThrow(() -> notFound("Issues", issueId));
        //Change status to IN_PROGRESS
        issue.setStatus(IssueStatus.OPEN);
        return toDto(issue);
    }

    //Adding a method for quick status update - mark issue in progress
    @Transactional
    public IssueDto markInProgress(Long issueId) {
        var issue = issues.findById(issueId)
                .orElseThrow(() -> notFound("Issues", issueId));
        //Change status to IN_PROGRESS
        issue.setStatus(IssueStatus.IN_PROGRESS);
        return toDto(issue);
    }

    //Adding a method for quick status update - mark issue as done
    @Transactional
    public IssueDto markDone(Long issueId) {
        var issue = issues.findById(issueId)
                .orElseThrow(() -> notFound("Issues", issueId));
        //Change status to IN_PROGRESS
        issue.setStatus(IssueStatus.DONE);
        return toDto(issue);
    }

}
