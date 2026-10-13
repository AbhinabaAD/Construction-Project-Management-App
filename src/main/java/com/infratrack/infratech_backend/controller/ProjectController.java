
package com.infratrack.infratech_backend.controller;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.infratrack.infratech_backend.model.Project;
import com.infratrack.infratech_backend.repository.ProjectRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    private final ProjectRepository projectRepository;

    public ProjectController(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @GetMapping
    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Project> getProjectById(@PathVariable String id) {
        Optional<Project> project = projectRepository.findById(id);

        if (project.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(project.get());
    }

    @PostMapping
    public ResponseEntity<Project> createProject(
            @Valid @RequestBody Project project) {

        project.setId(null);
        project.setCreatedAt(Instant.now());

        Project saved = projectRepository.save(project);

        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Project> updateProject(
            @PathVariable String id,
            @Valid @RequestBody Project project) {

        Optional<Project> existingProject =
                projectRepository.findById(id);

        if (existingProject.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Project existing = existingProject.get();

        project.setId(existing.getId());
        project.setCreatedAt(existing.getCreatedAt());

        Project updated = projectRepository.save(project);

        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProject(@PathVariable String id) {
        if (!projectRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        projectRepository.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
