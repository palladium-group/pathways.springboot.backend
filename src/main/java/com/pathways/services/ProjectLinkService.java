package com.pathways.services;

import com.pathways.models.Project;
import com.pathways.models.ProjectLink;
import com.pathways.payload.request.ProjectLinkRequest;
import com.pathways.payload.request.UpdateProjectLinkRequest;
import com.pathways.repository.ProjectLinkRepository;
import com.pathways.repository.ProjectRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProjectLinkService {
    private ProjectLinkRepository projectLinkRepository;
    private ProjectRepository projectRepository;

    public ProjectLinkService(ProjectLinkRepository projectLinkRepository, ProjectRepository projectRepository) {
        this.projectLinkRepository = projectLinkRepository;
        this.projectRepository = projectRepository;
    }

    public List<ProjectLink> getProjectLinksByProjectId(Integer projectId) throws Exception {
        Optional<Project> project = projectRepository.findById(projectId);
        if (project == null) {
            throw new Exception("Project Not Found");
        }
        return projectLinkRepository.findProjectLinkByProject(project.get());
    }

    public void createNewProjectLink(ProjectLinkRequest projectLinkRequest) throws Exception {
        Optional<Project> project = projectRepository.findById(projectLinkRequest.getProjectId());
        if (project == null) {
            throw new Exception("Project Not Found");
        }
        projectLinkRepository.save(new ProjectLink(projectLinkRequest.getName(), projectLinkRequest.getUrl(), projectLinkRequest.getColor(), project.get()));
    }

    public Optional<ProjectLink> getProjectLinkById(Integer id) {
        return projectLinkRepository.findById(id);
    }

    public void updateProjectLink(UpdateProjectLinkRequest updateProjectLinkRequest) {
        Optional<ProjectLink> optionalProjectLink = projectLinkRepository.findById(updateProjectLinkRequest.getProjectLinkId());
        if (optionalProjectLink.isPresent()) {
            // Get the ProjectLink entity from the Optional
            ProjectLink projectLink = optionalProjectLink.get();

            // Update the properties of the ProjectLink entity
            projectLink.setName(updateProjectLinkRequest.getName());
            projectLink.setUrl(updateProjectLinkRequest.getUrl());
            projectLink.setColor(updateProjectLinkRequest.getColor());

            // Save the updated ProjectLink entity back to the database
            projectLinkRepository.save(projectLink);
        }
    }
}
