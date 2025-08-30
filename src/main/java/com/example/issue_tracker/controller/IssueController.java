package com.example.issue_tracker.controller;

import com.example.issue_tracker.domain.IssueStatus;
import com.example.issue_tracker.dto.CreateIssueRequest;
import com.example.issue_tracker.dto.IssueDto;
import com.example.issue_tracker.dto.UpdateIssueRequest;
import com.example.issue_tracker.service.IssueService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Optional;


@RestController
@RequestMapping("/api/issues")
public class IssueController {

    private final IssueService service;

    public IssueController(IssueService service) {
        this.service = service;
    }


    @PostMapping
    public ResponseEntity<IssueDto> create(@Valid @RequestBody CreateIssueRequest req) {
        IssueDto dto = service.create(req);
        URI location = URI.create("/api/issues/" + dto.id());
        return ResponseEntity.created(location).body(dto);
    }

    @GetMapping
    public Page<IssueDto> search(
            @RequestParam(required = false) IssueStatus status,
            @RequestParam(required = false) Long assigneeId,
            @RequestParam(required = false) Long projectId,
            Pageable pageable
    ) {
        return service.search(
                Optional.ofNullable(status),
                Optional.ofNullable(assigneeId),
                Optional.ofNullable(projectId),
                pageable
        );
    }

    @GetMapping("/{id}")
    public IssueDto get(@PathVariable Long id) {
        return service.getById(id);
    }
    
    @PatchMapping("/{id}")
    public IssueDto update(@PathVariable Long id, @Valid @RequestBody UpdateIssueRequest req) {
        return service.update(id, req);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

}
