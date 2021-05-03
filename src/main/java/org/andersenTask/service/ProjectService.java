package org.andersenTask.service;

import org.andersenTask.entity.Project;
import org.andersenTask.entity.Team;
import org.andersenTask.repository.ProjectRepository;
import org.andersenTask.repository.TeamRepository;

import java.util.List;

public class ProjectService {
    private ProjectRepository projectRepository;

    public ProjectService() {
        this.projectRepository = new ProjectRepository();
    }

    public void create(Project project) {
        projectRepository.insert(project);
    }

    public Project getById(Long id) {
        return projectRepository.getById(id);
    }

    public List<Project> getAll() {
        return projectRepository.getAll();
    }

    public void deleteById(Long id) {
        projectRepository.deleteById(id);
    }

    public void updateById(Project project) {
        projectRepository.update(project);
    }
}
