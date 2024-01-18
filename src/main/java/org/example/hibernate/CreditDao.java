package org.example.hibernate;

import org.example.entity.Credit;
import org.example.interfaces.GenericDao;
import org.hibernate.Session;

import java.util.List;

public class CreditDao implements GenericDao<Credit> {

    private static CreditDao instance;

    public static synchronized CreditDao getCreditDaoInstance() {
        if (instance == null) {
            instance = new CreditDao();
        }
        return instance;
    }

    @Override
    public Credit findById(Long id, Class<Credit> entityClass) {
        return GenericDao.super.findById(id, entityClass);
    }

    @Override
    public List<Credit> getAllEntities(Class<Credit> entityClass) {
        return GenericDao.super.getAllEntities(entityClass);
    }

    @Override
    public void delete(Credit entity) {
        GenericDao.super.delete(entity);
    }

    @Override
    public Class<Credit> getEntityClass() {
        return Credit.class;
    }

    @Override
    public void deleteById(Long id) {
        GenericDao.super.deleteById(id);
    }

    @Override
    public void save(Credit entity) {
        GenericDao.super.save(entity);
    }

    @Override
    public Session getSession() {
        return GenericDao.super.getSession();
    }

    @Override
    public void updateEntity(Credit entity, Session session) {
        GenericDao.super.updateEntity(entity, session);
    }
}