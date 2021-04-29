package org.andersenTask.repository;

import org.andersenTask.entity.Employee;
import org.andersenTask.entity.Feedback;
import org.andersenTask.entity.Project;
import org.andersenTask.entity.Team;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.*;


import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


class EmployeeRepositoryTest {
    Repository employeeRepository;
    Employee employee;

    @BeforeEach
    void setUp() {
        employeeRepository = new EmployeeRepository();

        Team team = new Team();
        team.setId(1L);
        team.setName("team1");

        Project project = new Project();
        project.setId(1L);
        project.setTitle("title1");
        project.setClient("client1");
        project.setDuration("duration1");
        project.setProjectManager("pm1");
        project.setMethodology("meth1");
        project.setTeam(team);

        Feedback feedback = new Feedback();
        feedback.setId(1L);
        feedback.setDescription("desc1");
        feedback.setDate(LocalDate.of(2021,02,02));

        employee = new Employee();
        employee.setId(1L);
        employee.setName("name1");
        employee.setSurname("surname1");
        employee.setFatherName("fatherName1");
        employee.setEmail("email1");
        employee.setPhoneNumber("phoneNumber1");
        employee.setExperience("exp1");
        employee.setDateOfBirth(LocalDate.of(1997, 3, 15));
        employee.setDateOfRecruitment(LocalDate.of(2021, 2, 19));
        employee.setDeveloperLevel("j1");
        employee.setLevelOfEnglish("c1");
        employee.setSkype("skype1");
        employee.setCurrentProject(project);
        employee.setFeedback(feedback);
    }

    @Test
    void insert() {

    }

    @Test
    void getById() {
        try {
            assertEquals(employee,employeeRepository.getById(1L));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Test
    void getAll() {
    }

    @Test
    void deleteById() {
    }


    @Test
    void update() {
    }
}