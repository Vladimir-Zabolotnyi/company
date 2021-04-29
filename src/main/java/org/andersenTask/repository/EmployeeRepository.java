package org.andersenTask.repository;

import org.andersenTask.connection.ConnectionPool;
import org.andersenTask.connection.ConnectionPoolImpl;
import org.andersenTask.entity.Employee;
import org.andersenTask.entity.Feedback;
import org.andersenTask.entity.Project;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeRepository implements Repository<Employee> {

ConnectionPool connectionPool;

    {
        try {
            connectionPool = ConnectionPoolImpl.create("jdbc:postgresql://" + "127.0.0.1:5432" + "/" + "company","postgres","1234");
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }

    @Override
    public Employee getById(Long id) throws SQLException {
        Connection connection = connectionPool.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(
                " select * from employee  e inner join" +
                        " feedback f on f.id = e.feedback_id inner join " +
                        "(project p inner join team t on p.team_id = t.id) " +
                        "on p.id = e.project_id where e.id = ?");
        preparedStatement.setLong(1,id);
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();

        Project project =new Project();
        project.setId(resultSet.getLong("id"));
        project.setTitle(resultSet.getString("title"));
        project.setClient(resultSet.getString("client"));
        project.setDuration(resultSet.getString("duration"));
        project.setProjectManager(resultSet.getString("project_manager"));
        project.setMethodology(resultSet.getString("methodology"));
        project.setTeam(new TeamRepository().getById(resultSet.getLong("team_id")));

        Feedback feedback = new Feedback();
        feedback.setId(resultSet.getLong("f.id"));
        feedback.setDescription(resultSet.getString("description"));
        feedback.setDate(resultSet.getDate("date").toLocalDate());

        Employee employee = new Employee();
        employee.setId(resultSet.getLong("e.id"));
        employee.setName(resultSet.getString("e.name"));
        employee.setSurname(resultSet.getString("surname"));
        employee.setFatherName(resultSet.getString("father_name"));
        employee.setEmail(resultSet.getString("email"));
        employee.setPhoneNumber(resultSet.getString("phone_number"));
        employee.setExperience(resultSet.getString("experience"));
        employee.setDateOfBirth(resultSet.getDate("date_of_birth").toLocalDate());
        employee.setDateOfRecruitment(resultSet.getDate("date_of_recruitment").toLocalDate());
        employee.setDeveloperLevel(resultSet.getString("developer_level"));
        employee.setLevelOfEnglish(resultSet.getString("level_of_english"));
        employee.setSkype(resultSet.getString("skype"));
        employee.setCurrentProject(project);
        employee.setFeedback(feedback);
        preparedStatement.close();
        resultSet.close();
        connection.close();
        return employee;

    }

    @Override
    public List<Employee> getAll()throws SQLException {
        Connection connection = connectionPool.getConnection();
        List<Employee> listOfEmployee = new ArrayList<>();
        PreparedStatement preparedStatement = connection.prepareStatement(
                " select * from employee  e inner join" +
                        " feedback f on f.id = e.feedback_id inner join " +
                        "(project p inner join team t on p.team_id = t.id) " +
                        "on p.id = e.project_id ");
        ResultSet resultSet = preparedStatement.executeQuery();
      while(  resultSet.next()){
          Project project =new Project();
          project.setId(resultSet.getLong("id"));
          project.setTitle(resultSet.getString("title"));
          project.setClient(resultSet.getString("client"));
          project.setDuration(resultSet.getString("duration"));
          project.setProjectManager(resultSet.getString("project_manager"));
          project.setMethodology(resultSet.getString("methodology"));
          project.setTeam(new TeamRepository().getById(resultSet.getLong("team_id")));

          Feedback feedback = new Feedback();
          feedback.setId(resultSet.getLong("f.id"));
          feedback.setDescription(resultSet.getString("description"));
          feedback.setDate(resultSet.getDate("date").toLocalDate());

          Employee employee = new Employee();
          employee.setId(resultSet.getLong("e.id"));
          employee.setName(resultSet.getString("e.name"));
          employee.setSurname(resultSet.getString("surname"));
          employee.setFatherName(resultSet.getString("father_name"));
          employee.setEmail(resultSet.getString("email"));
          employee.setPhoneNumber(resultSet.getString("phone_number"));
          employee.setExperience(resultSet.getString("name"));
          employee.setDateOfBirth(resultSet.getDate("date_of_birth").toLocalDate());
          employee.setDateOfRecruitment(resultSet.getDate("date_of_recruitment").toLocalDate());
          employee.setDeveloperLevel(resultSet.getString("developer_level"));
          employee.setLevelOfEnglish(resultSet.getString("level_of_english"));
          employee.setSkype(resultSet.getString("skype"));
          employee.setCurrentProject(project);
          employee.setFeedback(feedback);
          listOfEmployee.add(employee);
      }
      return listOfEmployee;
    }

    @Override
    public int deleteById(Long id)throws SQLException {
        Connection connection = connectionPool.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("Delete from employee e where e.id = ?");
        preparedStatement.setLong(1, id);
        int rs = preparedStatement.executeUpdate();
        preparedStatement.close();
        connection.close();
        return rs;
    }


    @Override
    public int insert(Employee entity) throws SQLException{
        Connection connection = connectionPool.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(
        "insert into employee( name, surname, father_name, email, phone_number, experience, date_of_birth, date_of_recruitment, developer_level, level_of_english,project_id, feedback_id, team_id) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?)");
        preparedStatement.setString(1,entity.getName());
        preparedStatement.setString(2,entity.getSurname());
        preparedStatement.setString(3,entity.getFatherName());
        preparedStatement.setString(4,entity.getEmail());
        preparedStatement.setString(5,entity.getPhoneNumber());
        preparedStatement.setString(6,entity.getExperience());
        preparedStatement.setDate(7, java.sql.Date.valueOf(entity.getDateOfBirth()));
        preparedStatement.setDate(8,(java.sql.Date.valueOf(entity.getDateOfRecruitment())));
        preparedStatement.setString(9,entity.getDeveloperLevel());
        preparedStatement.setString(10,entity.getLevelOfEnglish());
        preparedStatement.setLong(11,entity.getCurrentProject().getId());
        preparedStatement.setLong(12,entity.getFeedback().getId());
        preparedStatement.setLong(13,entity.getCurrentProject().getTeam().getId());
        int rs = preparedStatement.executeUpdate();
        preparedStatement.close();
        connection.close();
        return rs;
    }

    @Override
    public int update(Employee entity)throws SQLException {
        Connection connection = connectionPool.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(
                "update employee set name = ?,surname = ?, father_name=?, email=?, phone_number=?, experience=?," +
                        " date_of_birth=?, date_of_recruitment=?, developer_level=?, level_of_english=?, " +
                        "project_id=?, feedback_id=?, team_id=? where id =?");
        preparedStatement.setString(1,entity.getName());
        preparedStatement.setString(2,entity.getSurname());
        preparedStatement.setString(3,entity.getFatherName());
        preparedStatement.setString(4,entity.getEmail());
        preparedStatement.setString(5,entity.getPhoneNumber());
        preparedStatement.setString(6,entity.getExperience());
        preparedStatement.setDate(7, java.sql.Date.valueOf(entity.getDateOfBirth()));
        preparedStatement.setDate(8,java.sql.Date.valueOf(entity.getDateOfRecruitment()));
        preparedStatement.setString(9,entity.getDeveloperLevel());
        preparedStatement.setString(10,entity.getLevelOfEnglish());
        preparedStatement.setLong(10,entity.getCurrentProject().getId());
        preparedStatement.setLong(11,entity.getFeedback().getId());
        preparedStatement.setLong(12,entity.getCurrentProject().getTeam().getId());
        preparedStatement.setLong(13,entity.getId());
        int rs = preparedStatement.executeUpdate();
        preparedStatement.close();
        connection.close();
        return rs;
    }
}
