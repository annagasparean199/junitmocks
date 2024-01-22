package org.example.DAO.DAOImpl;

import org.example.DAO.GenericDao;
import org.example.entity.Product;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

import static org.example.DAO.DAOImpl.HibernateUtility.getSessionFactory;

public class ProductDao implements GenericDao<Product> {

    @Override
    public Product findById(Long id, Class<Product> entityClass) {
        return GenericDao.super.findById(id, entityClass);
    }

    @Override
    public List<Product> getAllEntities(Class<Product> entityClass) {
        return GenericDao.super.getAllEntities(entityClass);
    }

    @Override
    public void delete(Product entity) {
        GenericDao.super.delete(entity);
    }

    @Override
    public Class<Product> getEntityClass() {
        return Product.class;
    }

    @Override
    public void deleteById(Long id) {
        GenericDao.super.deleteById(id);
    }

    @Override
    public void save(Product entity) {
        GenericDao.super.save(entity);
    }

    @Override
    public void updateEntity(Product entity) {
        GenericDao.super.updateEntity(entity);
    }
}