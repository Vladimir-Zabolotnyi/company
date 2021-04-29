package org.andersenTask.repository;

import org.andersenTask.connection.ConnectionPool;
import org.andersenTask.connection.ConnectionPoolImpl;
import org.andersenTask.entity.Project;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
    public Project getById(Long id) throws SQLException {
        Connection connection = connectionPool.getConnection();
        Project project = new Project();
        PreparedStatement preparedStatement = connection.prepareStatement("select * from project where id = ?");
        preparedStatement.setLong(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        project.setId(resultSet.getLong("id"));
        project.setTitle(resultSet.getString("title"));
        project.setClient(resultSet.getString("client"));
        project.setDuration(resultSet.getString("duration"));
        project.setProjectManager(resultSet.getString("project_manager"));
        project.setMethodology(resultSet.getString("methodology"));
        project.setTeam(new TeamRepository().getById(resultSet.getLong("team_id")));
        resultSet.close();
        preparedStatement.close();
        connection.close();
        return project;
    }

    @Override
    public List<Project> getAll() throws SQLException {
        Connection connection = connectionPool.getConnection();
        List<Project> listOfProject = new ArrayList<>();
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
        resultSet.close();
        preparedStatement.close();
        connection.close();
        return listOfProject;
    }

    @Override
    public int deleteById(Long id) throws SQLException {
        Connection connection = connectionPool.getConnection();
        PreparedStatement statementUpdate = connection.prepareStatement(
                "update employee set project_id = null where project_id = ?");
        statementUpdate.setLong(1, id);
        statementUpdate.executeUpdate();
        statementUpdate.close();
        PreparedStatement preparedStatement = connection.prepareStatement("Delete from project p where p.id = ?");
        preparedStatement.setLong(1, id);
        int rs = preparedStatement.executeUpdate();
        preparedStatement.close();
        connection.close();
        return rs;
    }

    @Override
    public int insert(Project entity) throws SQLException {
        Connection connection = connectionPool.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("insert into project(id, title, client, duration, methodology, project_manager, team_id) " +
                "VALUES (?,?,?,?,?,?)");
        preparedStatement.setString(1, entity.getTitle());
        preparedStatement.setString(2, entity.getClient());
        preparedStatement.setString(3, entity.getDuration());
        preparedStatement.setString(4, entity.getMethodology());
        preparedStatement.setString(5, entity.getProjectManager());
        preparedStatement.setLong(6, entity.getTeam().getId());
        int rs = preparedStatement.executeUpdate();
        preparedStatement.close();
        connection.close();
        return rs;
    }

    @Override
    public int update(Project entity) throws SQLException {
        Connection connection = connectionPool.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("update  team set title = ?,clien =?,duration=?,methodology=?,project_manager=?,team_id=? where id =?");
        preparedStatement.setString(1,entity.getTitle());
        preparedStatement.setString(2,entity.getClient());
        preparedStatement.setString(3,entity.getDuration());
        preparedStatement.setString(4,entity.getMethodology());
        preparedStatement.setString(5,entity.getProjectManager());
        preparedStatement.setLong(6,entity.getTeam().getId());
        preparedStatement.setLong(7,entity.getId());
        int rs = preparedStatement.executeUpdate();
        preparedStatement.close();
        connection.close();
        return rs;
    }
}
