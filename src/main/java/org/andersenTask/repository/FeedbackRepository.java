package org.andersenTask.repository;

import org.andersenTask.connection.ConnectionPool;
import org.andersenTask.connection.ConnectionPoolImpl;
import org.andersenTask.entity.Feedback;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FeedbackRepository implements Repository<Feedback> {

    ConnectionPool connectionPool;

    {
        try {
            connectionPool = ConnectionPoolImpl.create("jdbc:postgresql://" + "127.0.0.1:5432" + "/" + "company", "postgres", "1234");
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }

    @Override
    public Feedback getById(Long id) throws SQLException {
        Connection connection = connectionPool.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("select * from feedback  where id = ?");
        preparedStatement.setLong(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        Feedback feedback = new Feedback(resultSet.getLong("id"),
                resultSet.getString("description"),
                resultSet.getDate("date").toLocalDate());
        resultSet.close();
        preparedStatement.close();
        connection.close();
        return feedback;
    }

    @Override
    public List<Feedback> getAll() throws SQLException {
        Connection connection = connectionPool.getConnection();
        List<Feedback> listOfFeedbacks = new ArrayList<>();
        PreparedStatement preparedStatement = connection.prepareStatement("select * from feedback f ");
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            Feedback feedback = new Feedback(resultSet.getLong("id"),
                    resultSet.getString("description"),
                    resultSet.getDate("date").toLocalDate());
            listOfFeedbacks.add(feedback);
        }
        resultSet.close();
        preparedStatement.close();
        connection.close();
        return listOfFeedbacks;
    }

    @Override
    public int deleteById(Long id) throws SQLException {
        Connection connection = connectionPool.getConnection();
        PreparedStatement statementUpdate = connection.prepareStatement("update employee set feedback_id = null where feedback_id = ?");
        statementUpdate.setLong(1, id);
        statementUpdate.executeUpdate();
        statementUpdate.close();
        PreparedStatement preparedStatement = connection.prepareStatement("Delete  from feedback f where f.id = ?");
        preparedStatement.setLong(1, id);
        int rs = preparedStatement.executeUpdate();
        preparedStatement.close();
        connection.close();
        return rs;
    }


    @Override
    public int insert(Feedback entity) throws SQLException {
        Connection connection = connectionPool.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("insert into feedback (description, date) " +
                "values (?,?);");
        preparedStatement.setString(1, entity.getDescription());
        preparedStatement.setDate(2, Date.valueOf(entity.getDate()));
        int rs = preparedStatement.executeUpdate();
        preparedStatement.close();
        connection.close();
        return rs;
    }

    @Override
    public int update(Feedback entity) throws SQLException {
        Connection connection = connectionPool.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("update  feedback set description = ? , date =? where id =?");
        preparedStatement.setString(1, entity.getDescription());
        preparedStatement.setDate(2, Date.valueOf(entity.getDate()));
        preparedStatement.setLong(3, entity.getId());
        int rs = preparedStatement.executeUpdate();
        preparedStatement.close();
        connection.close();
        return rs;
    }
}
