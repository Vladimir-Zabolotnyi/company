package org.andersenTask.repository;

import lombok.extern.slf4j.Slf4j;

import org.andersenTask.connection.DataSource;
import org.andersenTask.entity.Employee;
import org.andersenTask.entity.Feedback;
import org.andersenTask.entity.Project;
import org.andersenTask.entity.Team;
import org.andersenTask.entity.exceptions.EntityDeleteException;
import org.andersenTask.entity.exceptions.EntityInsertException;
import org.andersenTask.entity.exceptions.EntityNotFoundException;
import org.andersenTask.entity.exceptions.EntityUpdateException;
import org.andersenTask.entity.utils.DeveloperLevel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class EmployeeRepository implements Repository<Employee> {

    @Override
    public Employee getById(Long id) {
        Employee employee = null;
        try (Connection connection = DataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    " select * from employee  e inner join" +
                            " feedback f on f.id = e.feedback_id inner join " +
                            "(project p inner join team t on p.team_id = t.id) " +
                            "on p.id = e.project_id where e.id = ?");
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {

                Project project = new Project();
                project.setId(resultSet.getLong(13));
                project.setTitle(resultSet.getString("title"));
                project.setClient(resultSet.getString("client"));
                project.setDuration(resultSet.getString("duration"));
                project.setProjectManager(resultSet.getString("project_manager"));
                project.setMethodology(resultSet.getString("methodology"));
                Team team = new TeamRepository().getById(resultSet.getLong("team_id"));
                team.setEmployeeList(null);
                project.setTeam(team);

                Feedback feedback = new Feedback();
                feedback.setId(resultSet.getLong(14));
                feedback.setDescription(resultSet.getString("description"));
                feedback.setDate(resultSet.getDate("date").toLocalDate());

                employee = new Employee();
                employee.setId(resultSet.getLong("id"));
                employee.setName(resultSet.getString("name"));
                employee.setSurname(resultSet.getString("surname"));
                employee.setFatherName(resultSet.getString("father_name"));
                employee.setEmail(resultSet.getString("email"));
                employee.setPhoneNumber(resultSet.getString("phone_number"));
                employee.setExperience(resultSet.getString("experience"));
                employee.setDateOfBirth(resultSet.getDate("date_of_birth").toLocalDate());
                employee.setDateOfRecruitment(resultSet.getDate("date_of_recruitment").toLocalDate());
                employee.setDeveloperLevel(DeveloperLevel.valueOf(resultSet.getString("developer_level")));
                employee.setLevelOfEnglish(resultSet.getString("level_of_english"));
                employee.setSkype(resultSet.getString("skype"));
                employee.setCurrentProject(project);
                employee.setFeedback(feedback);
                preparedStatement.close();
                resultSet.close();
            } else throw new EntityNotFoundException("employee not found with id= " + id);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        log.info("employee was got by id");
        return employee;

    }

    @Override
    public List<Employee> getAll() {
        List<Employee> listOfEmployee = null;
        PreparedStatement preparedStatement;
        try (Connection connection =  DataSource.getConnection()) {
            listOfEmployee = new ArrayList<>();
            preparedStatement = connection.prepareStatement(
                    " select * from employee  e inner join" +
                            " feedback f on f.id = e.feedback_id inner join " +
                            "(project p inner join team t on p.team_id = t.id) " +
                            "on p.id = e.project_id ");

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Project project = new Project();
                project.setId(resultSet.getLong(13));
                project.setTitle(resultSet.getString("title"));
                project.setClient(resultSet.getString("client"));
                project.setDuration(resultSet.getString("duration"));
                project.setProjectManager(resultSet.getString("project_manager"));
                project.setMethodology(resultSet.getString("methodology"));
                Team team = new TeamRepository().getById(resultSet.getLong("team_id"));
                team.setEmployeeList(null);
                project.setTeam(team);

                Feedback feedback = new Feedback();
                feedback.setId(resultSet.getLong(14));
                feedback.setDescription(resultSet.getString("description"));
                feedback.setDate(resultSet.getDate("date").toLocalDate());

                Employee employee = new Employee();
                employee.setId(resultSet.getLong("id"));
                employee.setName(resultSet.getString("name"));
                employee.setSurname(resultSet.getString("surname"));
                employee.setFatherName(resultSet.getString("father_name"));
                employee.setEmail(resultSet.getString("email"));
                employee.setPhoneNumber(resultSet.getString("phone_number"));
                employee.setExperience(resultSet.getString("experience"));
                employee.setDateOfBirth(resultSet.getDate("date_of_birth").toLocalDate());
                employee.setDateOfRecruitment(resultSet.getDate("date_of_recruitment").toLocalDate());
                employee.setDeveloperLevel(DeveloperLevel.valueOf(resultSet.getString("developer_level")));
                employee.setLevelOfEnglish(resultSet.getString("level_of_english"));
                employee.setSkype(resultSet.getString("skype"));
                employee.setCurrentProject(project);
                employee.setFeedback(feedback);

                listOfEmployee.add(employee);
            }
            preparedStatement.close();
            resultSet.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        if (listOfEmployee.isEmpty()) throw new EntityNotFoundException("no employees found");
        log.info("all employees were got");
        return listOfEmployee;
    }

    @Override
    public int deleteById(Long id) {
        int rs = 0;
        try (Connection connection =  DataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement("Delete from employee e where e.id = ?");
            preparedStatement.setLong(1, id);
            rs = preparedStatement.executeUpdate();
            if (rs == 0) throw new EntityDeleteException("employee not deleted with id=" + id);
            preparedStatement.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        log.info("employee was deleted");
        return rs;
    }


    @Override
    public int insert(Employee entity) {
        int rs = 0;
        try (Connection connection = DataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "insert into employee( name, surname, father_name, email, phone_number, experience, date_of_birth, date_of_recruitment, developer_level, level_of_english,skype,project_id, feedback_id, team_id) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
            preparedStatement.setString(1, entity.getName());
            preparedStatement.setString(2, entity.getSurname());
            preparedStatement.setString(3, entity.getFatherName());
            preparedStatement.setString(4, entity.getEmail());
            preparedStatement.setString(5, entity.getPhoneNumber());
            preparedStatement.setString(6, entity.getExperience());
            preparedStatement.setDate(7, java.sql.Date.valueOf(entity.getDateOfBirth()));
            preparedStatement.setDate(8, (java.sql.Date.valueOf(entity.getDateOfRecruitment())));
            preparedStatement.setString(9, entity.getDeveloperLevel().getName());
            preparedStatement.setString(10, entity.getLevelOfEnglish());
            preparedStatement.setString(11, entity.getSkype());
            preparedStatement.setLong(12, entity.getCurrentProject().getId());
            preparedStatement.setLong(13, entity.getFeedback().getId());
            preparedStatement.setLong(14, entity.getCurrentProject().getTeam().getId());
            rs = preparedStatement.executeUpdate();
            if (rs == 0) throw new EntityInsertException("employee not inserted");
            preparedStatement.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        log.info("employee was inserted");
        return rs;
    }

    @Override
    public int update(Employee entity) {
        int rs = 0;
        try (Connection connection = DataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "update employee set name = ?,surname = ?, father_name=?, email=?, phone_number=?, experience=?," +
                            " date_of_birth=?, date_of_recruitment=?, developer_level=?, level_of_english=?, skype=?," +
                            "project_id=?, feedback_id=?, team_id=? where id =?");
            preparedStatement.setString(1, entity.getName());
            preparedStatement.setString(2, entity.getSurname());
            preparedStatement.setString(3, entity.getFatherName());
            preparedStatement.setString(4, entity.getEmail());
            preparedStatement.setString(5, entity.getPhoneNumber());
            preparedStatement.setString(6, entity.getExperience());
            preparedStatement.setDate(7, java.sql.Date.valueOf(entity.getDateOfBirth()));
            preparedStatement.setDate(8, java.sql.Date.valueOf(entity.getDateOfRecruitment()));
            preparedStatement.setString(9, entity.getDeveloperLevel().getName());
            preparedStatement.setString(10, entity.getLevelOfEnglish());
            preparedStatement.setString(11, entity.getSkype());
            preparedStatement.setLong(12, entity.getCurrentProject().getId());
            preparedStatement.setLong(13, entity.getFeedback().getId());
            preparedStatement.setLong(14, entity.getCurrentProject().getTeam().getId());
            preparedStatement.setLong(15, entity.getId());
            rs = preparedStatement.executeUpdate();
            if (rs == 0) throw new EntityUpdateException("employee not updated");
            preparedStatement.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        log.info("employee was updated");
        return rs;
    }
}
