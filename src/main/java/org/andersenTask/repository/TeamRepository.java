package org.andersenTask.repository;

import org.andersenTask.connection.ConnectionPool;
import org.andersenTask.connection.ConnectionPoolImpl;
import org.andersenTask.entity.Employee;
import org.andersenTask.entity.Team;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TeamRepository implements Repository<Team> {
    ConnectionPool connectionPool;

    {
        try {
            connectionPool = ConnectionPoolImpl.create("jdbc:postgresql://" + "127.0.0.1:5432" + "/" + "company", "postgres", "1234");
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }

    @Override
    public Team getById(Long id) throws SQLException {
        Connection connection = connectionPool.getConnection();
        Team team = new Team();
        List<Employee> listOfEmployee = new ArrayList<>();
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * from employee where team_id = ?");
        preparedStatement.setLong(1,id);
        ResultSet resultSet = preparedStatement.executeQuery();
        while(resultSet.next()){
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
           employee.setDeveloperLevel(resultSet.getString("developer_level"));
           employee.setLevelOfEnglish(resultSet.getString("level_of_english"));
           employee.setSkype(resultSet.getString("skype"));
        listOfEmployee.add(employee);
        }
        resultSet.close();
        preparedStatement.close();
        PreparedStatement preparedStatement1 = connection.prepareStatement("Select * from team where id = ?");
        preparedStatement1.setLong(1,id);
        ResultSet resultSet1 = preparedStatement1.executeQuery();
        resultSet1.next();
        team.setId(id);
        team.setName(resultSet1.getString("name"));
        team.setEmployeeList(listOfEmployee);
        resultSet1.close();
        preparedStatement1.close();
        connection.close();
        return team;
    }

    @Override
    public List<Team> getAll() throws SQLException {
        Connection connection = connectionPool.getConnection();
        List<Team> listOfTeam = new ArrayList<>();
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * from team ");
        ResultSet resultSet = preparedStatement.executeQuery();
        while(resultSet.next()){
            Team team = new Team();
            List<Employee> listOfEmployee = new ArrayList<>();
            PreparedStatement preparedStatement1 = connection.prepareStatement("SELECT * from employee where team_id = ?");
            preparedStatement1.setLong(1,resultSet.getLong("id"));
            ResultSet resultSet1 = preparedStatement.executeQuery();
            while(resultSet1.next()){
                Employee employee = new Employee();
                employee.setId(resultSet1.getLong("id"));
                employee.setName(resultSet1.getString("name"));
                employee.setSurname(resultSet1.getString(3));
                employee.setFatherName(resultSet1.getString("father_name"));
                employee.setEmail(resultSet1.getString("email"));
                employee.setPhoneNumber(resultSet1.getString("phone_number"));
                employee.setExperience(resultSet1.getString("experience"));
                employee.setDateOfBirth(resultSet1.getDate("date_of_birth").toLocalDate());
                employee.setDateOfRecruitment(resultSet1.getDate("date_of_recruitment").toLocalDate());
                employee.setDeveloperLevel(resultSet1.getString("developer_level"));
                employee.setLevelOfEnglish(resultSet1.getString("level_of_english"));
                employee.setSkype(resultSet1.getString("skype"));
                listOfEmployee.add(employee);
            }
            team.setId(resultSet.getLong("id"));
            team.setName(resultSet.getString("name"));
            team.setEmployeeList(listOfEmployee);
            listOfTeam.add(team);
            resultSet1.close();
            preparedStatement1.close();
        }
        resultSet.close();
        preparedStatement.close();
        connection.close();
        return listOfTeam;
    }

    @Override
    public int deleteById(Long id) throws SQLException {
        Connection connection = connectionPool.getConnection();
        PreparedStatement statementUpdate = connection.prepareStatement(
                "update employee set team_id = null where team_id = ?");
        statementUpdate.setLong(1,id);
        statementUpdate.executeUpdate();
        statementUpdate.close();
        PreparedStatement statementUpdate2 = connection.prepareStatement(
                "update project set team_id = null where team_id = ?");
        statementUpdate2.setLong(1,id);
        statementUpdate2.executeUpdate();
        statementUpdate2.close();
        PreparedStatement preparedStatement = connection.prepareStatement("Delete  from team t where t.id = ?");
        preparedStatement.setLong(1, id);
        int rs = preparedStatement.executeUpdate();
        preparedStatement.close();
        connection.close();
        return rs;
    }

    @Override
    public int insert(Team entity) throws SQLException {
        Connection connection = connectionPool.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("insert into team (name) " +
                "values (?);");
        preparedStatement.setString(1,entity.getName());
        int rs = preparedStatement.executeUpdate();
        preparedStatement.close();
        connection.close();
        return  rs;
    }

    @Override
    public int update(Team entity) throws SQLException {
        Connection connection = connectionPool.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("update  team set name = ? where id =?");
        preparedStatement.setString(1,entity.getName());
        preparedStatement.setLong(2,entity.getId());
        int rs = preparedStatement.executeUpdate();
        preparedStatement.close();
        connection.close();
        return rs;
    }
}
