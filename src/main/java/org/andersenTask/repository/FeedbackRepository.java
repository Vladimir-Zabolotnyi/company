package org.andersenTask.repository;

import lombok.extern.slf4j.Slf4j;
import org.andersenTask.connection.ConnectionPool;
import org.andersenTask.connection.ConnectionPoolImpl;
import org.andersenTask.connection.DataSource;
import org.andersenTask.entity.Feedback;
import org.andersenTask.entity.exceptions.EntityDeleteException;
import org.andersenTask.entity.exceptions.EntityInsertException;
import org.andersenTask.entity.exceptions.EntityNotFoundException;
import org.andersenTask.entity.exceptions.EntityUpdateException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class FeedbackRepository implements Repository<Feedback> {



    @Override
    public Feedback getById(Long id) {
        Feedback feedback = null;
        try (Connection connection = DataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from feedback  where id = ?");
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                feedback = new Feedback(resultSet.getLong("id"),
                        resultSet.getString("description"),
                        resultSet.getDate("date").toLocalDate());
            } else throw new EntityNotFoundException("feedback not found with id= " + id);
            resultSet.close();
            preparedStatement.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        log.info("employee was got by id");
        return feedback;
    }

    @Override
    public List<Feedback> getAll() {
        List<Feedback> listOfFeedbacks = null;
        try (Connection connection = DataSource.getConnection()) {
            listOfFeedbacks = new ArrayList<>();
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
            if (listOfFeedbacks.isEmpty()) throw new EntityNotFoundException("feedbacks not found");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        log.info("all feedbacks were got");
        return listOfFeedbacks;
    }

    @Override
    public int deleteById(Long id) {
        int rs = 0;
        try (Connection connection = DataSource.getConnection()) {
            PreparedStatement statementUpdate = connection.prepareStatement("update employee set feedback_id = null where feedback_id = ?");
            statementUpdate.setLong(1, id);
            statementUpdate.executeUpdate();
            statementUpdate.close();
            PreparedStatement preparedStatement = connection.prepareStatement("Delete  from feedback f where f.id = ?");
            preparedStatement.setLong(1, id);
            rs = preparedStatement.executeUpdate();
            preparedStatement.close();
            if (rs == 0) throw new EntityDeleteException("feedback not deleted with id= " + id);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        log.info("feedback was deleted");
        return rs;
    }


    @Override
    public int insert(Feedback entity) {
        int rs = 0;
        try (Connection connection = DataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement("insert into feedback (description, date) " +
                    "values (?,?);");
            preparedStatement.setString(1, entity.getDescription());
            preparedStatement.setDate(2, Date.valueOf(entity.getDate()));
            rs = preparedStatement.executeUpdate();
            preparedStatement.close();
            if (rs == 0) throw new EntityInsertException("feedback not insert");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        log.info("feedback was inserted");
        return rs;
    }

    @Override
    public int update(Feedback entity) {
        int rs = 0;
        try (Connection connection = DataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement("update  feedback set description = ? , date =? where id =?");
            preparedStatement.setString(1, entity.getDescription());
            preparedStatement.setDate(2, Date.valueOf(entity.getDate()));
            preparedStatement.setLong(3, entity.getId());
            rs = preparedStatement.executeUpdate();
            preparedStatement.close();
            if (rs == 0) throw new EntityUpdateException("feedback not update");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        log.info("feedback was updated");
        return rs;
    }
}
