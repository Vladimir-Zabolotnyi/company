package org.andersenTask.service;

import org.andersenTask.entity.Team;
import org.andersenTask.repository.TeamRepository;

import java.util.List;

public class TeamService {
    private TeamRepository teamRepository;

    public TeamService() {
        this.teamRepository = new TeamRepository();
    }

    public void create(Team team) {
        teamRepository.insert(team);
    }

    public Team getById(Long id) {
        return teamRepository.getById(id);
    }

    public List<Team> getAll() {
        return teamRepository.getAll();
    }

    public void deleteById(Long id) {
        teamRepository.deleteById(id);
    }

    public void updateById(Team team) {
        teamRepository.update(team);
    }
}
