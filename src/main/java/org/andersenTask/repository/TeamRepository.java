package org.andersenTask.repository;

import lombok.extern.slf4j.Slf4j;
import org.andersenTask.connection.ConnectionPool;
import org.andersenTask.connection.ConnectionPoolImpl;
import org.andersenTask.connection.DataSource;
import org.andersenTask.entity.Employee;
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
public class TeamRepository implements Repository<Team> {


    @Override
    public Team getById(Long id) {
        Team team = null;
        try (Connection connection = DataSource.getConnection()) {
            team = new Team();
            List<Employee> listOfEmployee = new ArrayList<>();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * from employee where team_id = ?");
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
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
                listOfEmployee.add(employee);
            }
            resultSet.close();
            preparedStatement.close();
            PreparedStatement preparedStatement1 = connection.prepareStatement("Select * from team where id = ?");
            preparedStatement1.setLong(1, id);
            ResultSet resultSet1 = preparedStatement1.executeQuery();
            if (resultSet1.next()) {
                team.setId(id);
                team.setName(resultSet1.getString("name"));
                team.setEmployeeList(listOfEmployee);
            } else throw new EntityNotFoundException("team not found with id= " + id);
            resultSet1.close();
            preparedStatement1.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        log.info("team was found with id= " + id);
        return team;
    }

    @Override
    public List<Team> getAll() {
        List<Team> listOfTeam = null;
        try (Connection connection = DataSource.getConnection()) {
            listOfTeam = new ArrayList<>();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * from team ");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Team team = new Team();
                List<Employee> listOfEmployee = new ArrayList<>();
                PreparedStatement preparedStatement1 = connection.prepareStatement("SELECT * from employee where team_id = ?");
                preparedStatement1.setLong(1, resultSet.getLong("id"));
                ResultSet resultSet1 = preparedStatement.executeQuery();
                while (resultSet1.next()) {
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
                    employee.setDeveloperLevel(DeveloperLevel.valueOf(resultSet1.getString("developer_level")));
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
            if (listOfTeam.isEmpty()) throw new EntityNotFoundException("project not found");
            resultSet.close();
            preparedStatement.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        log.info("teams were found");
        return listOfTeam;
    }

    @Override
    public int deleteById(Long id) {
        int rs = 0;
        try (Connection connection = DataSource.getConnection()) {
            PreparedStatement statementUpdate = connection.prepareStatement(
                    "update employee set team_id = null where team_id = ?");
            statementUpdate.setLong(1, id);
            statementUpdate.executeUpdate();
            statementUpdate.close();
            PreparedStatement statementUpdate2 = connection.prepareStatement(
                    "update project set team_id = null where team_id = ?");
            statementUpdate2.setLong(1, id);
            statementUpdate2.executeUpdate();
            statementUpdate2.close();
            PreparedStatement preparedStatement = connection.prepareStatement("Delete  from team t where t.id = ?");
            preparedStatement.setLong(1, id);
            rs = preparedStatement.executeUpdate();
            if (rs == 0) throw new EntityDeleteException("team not deleted with id= " + id);
            preparedStatement.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        log.info("team was deleted with id= " + id);
        return rs;
    }

    @Override
    public int insert(Team entity) {
        int rs = 0;
        try (Connection connection = DataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement("insert into team (name) " +
                    "values (?);");
            preparedStatement.setString(1, entity.getName());
            rs = preparedStatement.executeUpdate();
            if (rs == 0) throw new EntityInsertException("team not insert");
            preparedStatement.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        log.info("team was inserted");
        return rs;
    }

    @Override
    public int update(Team entity) {
        int rs = 0;
        try (Connection connection = DataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement("update  team set name = ? where id =?");
            preparedStatement.setString(1, entity.getName());
            preparedStatement.setLong(2, entity.getId());
            rs = preparedStatement.executeUpdate();
            if (rs == 0) throw new EntityUpdateException("team not update");
            preparedStatement.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        log.info("team was updated");
        return rs;
    }
}
