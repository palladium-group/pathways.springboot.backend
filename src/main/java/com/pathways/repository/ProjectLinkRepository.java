package com.pathways.repository;

import com.pathways.models.Project;
import com.pathways.models.ProjectLink;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectLinkRepository extends JpaRepository<ProjectLink, Integer> {
    List<ProjectLink> findProjectLinkByProject(Project project);
}
