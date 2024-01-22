package org.example.DAO.DAOImpl;

import org.example.DAO.GenericDao;
import org.example.entity.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

import static org.example.DAO.DAOImpl.HibernateUtility.getSessionFactory;

public class UserDao implements GenericDao<User> {

    private static UserDao instance;
    SessionFactory sessionFactory = getSessionFactory();

    public static synchronized UserDao getUserDaoInstance() {
        if (instance == null) {
            instance = new UserDao();
        }
        return instance;
    }

    @Override
    public User findById(Long id, Class<User> entityClass) {
        return GenericDao.super.findById(id, entityClass);
    }

    @Override
    public List<User> getAllEntities(Class<User> entityClass) {
        return GenericDao.super.getAllEntities(entityClass);
    }

    @Override
    public void delete(User entity) {
        GenericDao.super.delete(entity);
    }

    @Override
    public Class<User> getEntityClass() {
        return User.class;
    }

    @Override
    public void deleteById(Long id) {
        GenericDao.super.deleteById(id);
    }

    @Override
    public void save(User entity) {
        GenericDao.super.save(entity);
    }

    @Override
    public void updateEntity(User entity) {
        GenericDao.super.updateEntity(entity);
    }
}