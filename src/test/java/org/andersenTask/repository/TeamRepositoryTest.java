package org.andersenTask.repository;

import org.andersenTask.entity.Employee;
import org.andersenTask.entity.Feedback;
import org.andersenTask.entity.Project;
import org.andersenTask.entity.Team;
import org.andersenTask.entity.utils.DeveloperLevel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TeamRepositoryTest {
    Repository<Team> teamRepository;
    Team team;
    List<Team> teamList;
    Team team2;

    @BeforeEach
    void setUp() {
        teamList = new ArrayList<>();

        teamRepository = new TeamRepository();
        team = new Team();
        team.setId(1L);
        team.setName("team2");

        Project project = new Project();
        project.setId(1l);
        project.setTitle("title1");
        project.setClient("client1");
        project.setDuration("duration1");
        project.setProjectManager("project_manager1");
        project.setMethodology("methodology1");


        Feedback feedback = new Feedback();
        feedback.setId(2L);
        feedback.setDescription("desc3");
        feedback.setDate(LocalDate.of(2021, 03, 15));

        List<Employee> employeeList = new ArrayList<>();

        Employee employee = new Employee();
        employee.setId(1l);
        employee.setName("name1");
        employee.setSurname("surname1");
        employee.setFatherName("father_name1");
        employee.setEmail("email1");
        employee.setPhoneNumber("phone_number1");
        employee.setExperience("experience1");
        employee.setDateOfBirth(LocalDate.of(1997, 03, 05));
        employee.setDateOfRecruitment(LocalDate.of(2021, 05, 18));
        employee.setDeveloperLevel(DeveloperLevel.j1);
        employee.setLevelOfEnglish("c1");
        employee.setSkype("skype1");
        employeeList.add(employee);

        team.setEmployeeList(employeeList);
        project.setTeam(team);
        teamList.add(team);

        team2 = new Team();
        team2.setId(1L);
        team2.setName("team2");
    }

    @Test()
    @Order(2)
    void getById() {
        assertEquals(team, teamRepository.getById(1L));
    }

    @Test
    @Order(3)
    void getAll() {
        assertEquals(teamList, teamRepository.getAll());
    }

    @Test
    @Order(5)
    void deleteById() {
        assertEquals(1, teamRepository.deleteById(1L));
    }

    @Test
    @Order(1)
    void insert() {
        assertEquals(1, teamRepository.insert(team));
    }

    @Test
    @Order(4)
    void update() {
        assertEquals(1, teamRepository.update(team2));
    }
}