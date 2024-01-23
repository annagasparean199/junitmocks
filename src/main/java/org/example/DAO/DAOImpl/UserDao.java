package org.example.DAO.DAOImpl;

import org.example.DAO.GenericDao;
import org.example.entity.Product;
import org.example.entity.User;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

import static org.example.DAO.DAOImpl.HibernateUtility.getSessionFactory;

public class UserDao implements GenericDao<User> {

    Session session;
    Transaction transaction;

    public Session setUp() {
        session = getSessionFactory().openSession();
        return session;
    }

    @Override
    public User findById(Long id, Class<User> entityClass) {
        User entity = null;
        try (Session session = setUp()) {
            transaction = session.beginTransaction();
            entity = session.get(entityClass, id);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return entity;
    }

    @Override
    public List<User> getAllEntities(Class<User> entityClass) {
        List<User> entities = null;
        try (Session session = setUp()) {
            transaction = session.beginTransaction();
            entities = session.createQuery("FROM " + entityClass.getName(), entityClass)
                    .list();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return entities;
    }

    @Override
    public void delete(User entity) {
        try (Session session = setUp()) {
            transaction = session.beginTransaction();
            session.delete(entity);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public void deleteById(Long id) {
        try (Session session = setUp()) {
            transaction = session.beginTransaction();
            User entity = session.get(User.class, id);
            if (entity != null) {
                session.delete(entity);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public User save(User entity) {
        try (Session session = setUp()) {
            transaction = session.beginTransaction();
            Long generatedId = (Long) session.save(entity);
            transaction.commit();
            entity = session.get(User.class, generatedId);
            return entity;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void updateEntity(User entity) {
        try (Session session = setUp()) {
            transaction = session.beginTransaction();
            session.update(entity);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }
}
