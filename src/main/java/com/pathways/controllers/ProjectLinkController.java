package com.pathways.controllers;

import com.pathways.models.ProjectLink;
import com.pathways.payload.request.ProjectLinkRequest;
import com.pathways.payload.request.UpdateProjectLinkRequest;
import com.pathways.services.ProjectLinkService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/project-link")
public class ProjectLinkController {
    private ProjectLinkService projectLinkService;

    public ProjectLinkController(ProjectLinkService projectLinkService) {
        this.projectLinkService = projectLinkService;
    }

    @GetMapping("/getProjectLinks/{projectId}")
    public ResponseEntity<?> getProjectLinksByProjectId(@PathVariable Integer projectId) {
        try {
            List<ProjectLink> projectLinks = projectLinkService.getProjectLinksByProjectId(projectId);
            return ResponseEntity.ok(projectLinks);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred");
        }
    }

    @GetMapping("/getProjectLinkById/{id}")
    public ResponseEntity<?> getProjectLinkById(@PathVariable Integer id) {
        try {
            Optional<ProjectLink> projectLinks = projectLinkService.getProjectLinkById(id);
            return ResponseEntity.ok(projectLinks);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred");
        }
    }

    @PostMapping
    public ResponseEntity<?> createNewProjectLink(@Valid @RequestBody ProjectLinkRequest projectLinkRequest) {
        try {
            projectLinkService.createNewProjectLink(projectLinkRequest);
            return ResponseEntity.ok("OK");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred");
        }
    }

    @PostMapping("/update")
    public ResponseEntity<?> updateProjectLink(@Valid @RequestBody UpdateProjectLinkRequest updateProjectLinkRequest) {
        try {
            projectLinkService.updateProjectLink(updateProjectLinkRequest);
            return ResponseEntity.ok("OK");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred");
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteProjectLink(@PathVariable Integer id) {
        try {
            projectLinkService.deleteProjectLinkById(id);
            return ResponseEntity.ok("OK");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred");
        }
    }
}
