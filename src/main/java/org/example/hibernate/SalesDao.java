package org.example.hibernate;

import org.example.entity.Sales;
import org.example.interfaces.GenericDao;
import org.hibernate.Session;

import java.util.List;

public class SalesDao implements GenericDao<Sales> {
    private static SalesDao instance;

    public static synchronized SalesDao getSalesDaoInstance() {
        if (instance == null) {
            instance = new SalesDao();
        }
        return instance;
    }

    @Override
    public Sales findById(Long id, Class<Sales> entityClass) {
        return GenericDao.super.findById(id, entityClass);
    }

    @Override
    public List<Sales> getAllEntities(Class<Sales> entityClass) {
        return GenericDao.super.getAllEntities(entityClass);
    }

    @Override
    public void delete(Sales entity) {
        GenericDao.super.delete(entity);
    }

    @Override
    public Class<Sales> getEntityClass() {
        return Sales.class;
    }

    @Override
    public void deleteById(Long id) {
        GenericDao.super.deleteById(id);
    }

    @Override
    public void save(Sales entity) {
        GenericDao.super.save(entity);
    }

    @Override
    public void updateEntity(Sales entity) {
        GenericDao.super.updateEntity(entity);
    }
}