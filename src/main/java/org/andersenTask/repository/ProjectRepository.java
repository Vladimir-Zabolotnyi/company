package org.andersenTask.repository;

import lombok.extern.slf4j.Slf4j;
import org.andersenTask.connection.ConnectionPool;
import org.andersenTask.connection.ConnectionPoolImpl;
import org.andersenTask.entity.Project;
import org.andersenTask.entity.exceptions.EntityDeleteException;
import org.andersenTask.entity.exceptions.EntityInsertException;
import org.andersenTask.entity.exceptions.EntityNotFoundException;
import org.andersenTask.entity.exceptions.EntityUpdateException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class ProjectRepository implements Repository<Project> {

    ConnectionPool connectionPool;

    {
        try {
            connectionPool = ConnectionPoolImpl.create("jdbc:postgresql://"
                    + "127.0.0.1:5432" + "/" + "company", "postgres", "1234");
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }

    @Override
    public Project getById(Long id) {
        Project project = null;
        try (Connection connection = connectionPool.getConnection()) {
            project = new Project();
            PreparedStatement preparedStatement = connection.prepareStatement("select * from project where id = ?");
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                project.setId(resultSet.getLong("id"));
                project.setTitle(resultSet.getString("title"));
                project.setClient(resultSet.getString("client"));
                project.setDuration(resultSet.getString("duration"));
                project.setProjectManager(resultSet.getString("project_manager"));
                project.setMethodology(resultSet.getString("methodology"));
                project.setTeam(new TeamRepository().getById(resultSet.getLong("team_id")));
            } else throw new EntityNotFoundException("project not found wit id= " + id);
            resultSet.close();
            preparedStatement.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        log.info("project was got with id=" + id);
        return project;
    }

    @Override
    public List<Project> getAll() {
        List<Project> listOfProject = null;
        try (Connection connection = connectionPool.getConnection()) {
            listOfProject = new ArrayList<>();
            PreparedStatement preparedStatement = connection.prepareStatement("select * from project ");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Project project = new Project();
                project.setId(resultSet.getLong("id"));
                project.setTitle(resultSet.getString("title"));
                project.setClient(resultSet.getString("client"));
                project.setDuration(resultSet.getString("duration"));
                project.setProjectManager(resultSet.getString("project_manager"));
                project.setMethodology(resultSet.getString("methodology"));
                project.setTeam(new TeamRepository().getById(resultSet.getLong("team_id")));
                listOfProject.add(project);
            }
            if (listOfProject.isEmpty()) throw new EntityNotFoundException("projects not found");
            resultSet.close();
            preparedStatement.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        log.info("projects were got");
        return listOfProject;
    }

    @Override
    public int deleteById(Long id) {
        int rs = 0;
        try (Connection connection = connectionPool.getConnection()) {
            PreparedStatement statementUpdate = connection.prepareStatement(
                    "update employee set project_id = null where project_id = ?");
            statementUpdate.setLong(1, id);
            statementUpdate.executeUpdate();
            statementUpdate.close();
            PreparedStatement preparedStatement = connection.prepareStatement("Delete from project p where p.id = ?");
            preparedStatement.setLong(1, id);
            rs = preparedStatement.executeUpdate();
            if (rs == 0) throw new EntityDeleteException("project not deleted with id= " + id);
            preparedStatement.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        log.info("project was deleted with id= " + id);
        return rs;
    }

    @Override
    public int insert(Project entity) {
        int rs = 0;
        try (Connection connection = connectionPool.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement("insert into project(title, client, duration, methodology, project_manager, team_id) " +
                    "VALUES (?,?,?,?,?,?)");
            preparedStatement.setString(1, entity.getTitle());
            preparedStatement.setString(2, entity.getClient());
            preparedStatement.setString(3, entity.getDuration());
            preparedStatement.setString(4, entity.getMethodology());
            preparedStatement.setString(5, entity.getProjectManager());
            preparedStatement.setLong(6, entity.getTeam().getId());
            rs = preparedStatement.executeUpdate();
            if (rs == 0) throw new EntityInsertException("project not inserted");
            preparedStatement.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        log.info("project was inserted");
        return rs;
    }

    @Override
    public int update(Project entity) {
        int rs = 0;
        try (Connection connection = connectionPool.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement("update  project set title = ?,client =?,duration=?,methodology=?,project_manager=?,team_id=? where id =?");
            preparedStatement.setString(1, entity.getTitle());
            preparedStatement.setString(2, entity.getClient());
            preparedStatement.setString(3, entity.getDuration());
            preparedStatement.setString(4, entity.getMethodology());
            preparedStatement.setString(5, entity.getProjectManager());
            preparedStatement.setLong(6, entity.getTeam().getId());
            preparedStatement.setLong(7, entity.getId());
            rs = preparedStatement.executeUpdate();
            preparedStatement.close();
            if (rs == 0) throw new EntityUpdateException("project not updated");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        log.info("project was updated");
        return rs;
    }
}
