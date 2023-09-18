package com.pathways.services;

import com.pathways.models.Project;
import com.pathways.payload.request.ProjectRequest;
import com.pathways.payload.request.UpdateProjectRequest;
import com.pathways.repository.ProjectRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProjectService {
    private ProjectRepository projectRepository;

    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    public void createNewProject(ProjectRequest projectRequest) {
        projectRepository.save(new Project(projectRequest.getName(), projectRequest.getIcon(), projectRequest.getColor()));
    }

    public Optional<Project> getProjectById(Integer id) {
        return projectRepository.findById(id);
    }

    public void updateProject(UpdateProjectRequest updateProjectRequest) {
        Optional<Project> optionalProject = projectRepository.findById(updateProjectRequest.getProjectId());
        if (optionalProject.isPresent()) {
            Project project = optionalProject.get();

            project.setName(updateProjectRequest.getName());
            project.setColor(updateProjectRequest.getColor());
            project.setIcon(updateProjectRequest.getIcon());

            projectRepository.save(project);
        }
    }
}
