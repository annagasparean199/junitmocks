package org.example.hibernate;

import org.example.entity.Delivery;
import org.example.entity.Discount;
import org.example.interfaces.GenericDao;
import org.hibernate.Session;

import java.util.List;

public class DiscountDao implements GenericDao<Discount> {
    private static DiscountDao instance;

    public static synchronized DiscountDao getDiscountDaoInstance() {
        if (instance == null) {
            instance = new DiscountDao();
        }
        return instance;
    }

    @Override
    public Discount findById(Long id, Class<Discount> entityClass) {
        return GenericDao.super.findById(id, entityClass);
    }

    @Override
    public List<Discount> getAllEntities(Class<Discount> entityClass) {
        return GenericDao.super.getAllEntities(entityClass);
    }

    @Override
    public void delete(Discount entity) {
        GenericDao.super.delete(entity);
    }

    @Override
    public Class<Discount> getEntityClass() {
        return Discount.class;
    }

    @Override
    public void deleteById(Long id) {
        GenericDao.super.deleteById(id);
    }

    @Override
    public void save(Discount entity) {
        GenericDao.super.save(entity);
    }

    @Override
    public void updateEntity(Discount entity) {
        GenericDao.super.updateEntity(entity);
    }
}
