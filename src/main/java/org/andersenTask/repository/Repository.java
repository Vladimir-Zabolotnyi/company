package org.andersenTask.repository;

import java.sql.SQLException;
import java.util.List;

public interface Repository<T> {
    T getById(Long id) throws SQLException;

    List<T> getAll() throws SQLException;

    int deleteById(Long id) throws SQLException;


    int insert(T entity) throws SQLException;

    int update(T entity) throws SQLException;

}
