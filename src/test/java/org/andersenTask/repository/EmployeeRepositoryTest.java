package org.andersenTask.repository;

import org.andersenTask.entity.Employee;
import org.andersenTask.entity.Feedback;
import org.andersenTask.entity.Project;
import org.andersenTask.entity.Team;
import org.andersenTask.entity.utils.DeveloperLevel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


class EmployeeRepositoryTest {
    Repository<Employee> employeeRepository;
    Employee employee;
    List<Employee> employeeList;

    @BeforeEach
    void setUp() {
        employeeRepository = new EmployeeRepository();
        employeeList = new ArrayList<>();
        Team team = new Team();
        team.setId(1L);
        team.setName("team2");


        Project project = new Project();
        project.setId(1L);
        project.setTitle("title1");
        project.setClient("client1");
        project.setDuration("year1");
        project.setProjectManager("project_manager1");
        project.setMethodology("methodology1");
        project.setTeam(team);

        Feedback feedback = new Feedback();
        feedback.setId(1L);
        feedback.setDescription("desc1");
        feedback.setDate(LocalDate.of(2021, 02, 02));

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
        employee.setDeveloperLevel(DeveloperLevel.j1);
        employee.setLevelOfEnglish("c1");
        employee.setSkype("skype1");
        employee.setCurrentProject(project);
        employee.setFeedback(feedback);
        employeeList.add(employee);

    }

    @Test
    @Order(1)
    void insert() {
        assertEquals(1, employeeRepository.insert(employee));
    }

    @Test
    @Order(2)
    void getById() {
        assertEquals(employee, employeeRepository.getById(1L));
    }

    @Test
    @Order(3)
    void getAll() {
        assertEquals(employeeList, employeeRepository.getAll());
    }

    @Test
    @Order(5)
    void deleteById() {
        assertEquals(1, employeeRepository.deleteById(1L));
    }


    @Test
    @Order(4)
    void update() {
        employee.setSkype("sss");
        assertEquals(1, employeeRepository.update(employee));
    }
}