package com.pathways.controllers;

import com.pathways.models.Project;
import com.pathways.models.ProjectLink;
import com.pathways.payload.request.ProjectRequest;
import com.pathways.payload.request.UpdateProjectRequest;
import com.pathways.services.ProjectService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/project")
public class ProjectController {
    private ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping
    public List<Project> getAllProjects() {
        return projectService.getAllProjects();
    }

    @GetMapping("getProjectById/{projectId}")
    public ResponseEntity<?> getProjectById(@PathVariable Integer projectId) {
        try {
            Optional<Project> project = projectService.getProjectById(projectId);
            return ResponseEntity.ok(project);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred");
        }
    }

    @PostMapping
    public ResponseEntity<String> createNewProject(@Valid @RequestBody ProjectRequest projectRequest) {
        try {
            projectService.createNewProject(projectRequest);
            return ResponseEntity.ok("OK");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred");
        }
    }

    @PostMapping("/update")
    public ResponseEntity<?> updateProject(@Valid @RequestBody UpdateProjectRequest updateProjectRequest) {
        try {
            projectService.updateProject(updateProjectRequest);
            return ResponseEntity.ok("OK");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred");
        }
    }
}
