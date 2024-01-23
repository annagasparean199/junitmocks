package org.example.DAO;

import org.hibernate.Session;

import java.util.List;

public interface GenericDao<T> {

    Session setUp();

    T findById(Long id, Class<T> entityClass);
    List<T> getAllEntities(Class<T> entityClass);

    void delete(T entity);

    void deleteById(Long id);

    T save(T entity);

    void updateEntity(T entity);
}