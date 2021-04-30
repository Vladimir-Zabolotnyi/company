package org.andersenTask.repository;

import java.util.List;

public interface Repository<T> {
    T getById(Long id);

    List<T> getAll();

    int deleteById(Long id);


    int insert(T entity);

    int update(T entity);

}
