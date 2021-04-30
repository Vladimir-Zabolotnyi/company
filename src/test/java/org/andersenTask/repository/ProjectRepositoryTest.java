package org.andersenTask.repository;

import org.andersenTask.entity.Employee;
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

class ProjectRepositoryTest {
    Repository<Project> projectRepository;
    Project project;
    List<Project> projectList;

    @BeforeEach
    void setUp() {
        projectRepository = new ProjectRepository();
        projectList = new ArrayList<>();
        project = new Project();
        project.setId(1l);
        project.setTitle("title1");
        project.setClient("client1");
        project.setDuration("year1");
        project.setProjectManager("project_manager1");
        project.setMethodology("methodology1");


        List<Employee> employeeList = new ArrayList<>();

        Employee employee = new Employee();
        employee.setId(1L);
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

        Team team = new Team();
        team.setId(1L);
        team.setName("team2");
        team.setEmployeeList(employeeList);
        project.setTeam(team);
        projectList.add(project);
    }

    @Test
    @Order(1)
    void insert() {
        assertEquals(1, projectRepository.insert(project));
    }

    @Test
    @Order(2)
    void getById() {
        assertEquals(project, projectRepository.getById(1L));
    }

    @Test
    @Order(3)
    void getAll() {
        assertEquals(projectList, projectRepository.getAll());
    }

    @Test
    @Order(5)
    void deleteById() {
        assertEquals(1, projectRepository.deleteById(1L));
    }


    @Test
    @Order(4)
    void update() {
        project.setId(2L);
        project.setTitle("1");
        assertEquals(1, projectRepository.update(project));

    }
}