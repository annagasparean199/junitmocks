package org.example.dao;

import org.hibernate.Session;
import java.util.List;

public interface GenericDao<T> {

    Session setUp();

    T findById(Long id);

    List<T> getAllEntities();

    void delete(T entity);

    void deleteById(Long id);

    T save(T entity);

    void updateEntity(T entity);

}