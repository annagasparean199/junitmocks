package org.example.dao.impl;

import org.example.dao.GenericDao;
import org.example.entities.Product;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

import static org.example.dao.impl.HibernateUtility.getSessionFactory;

public class ProductDao implements GenericDao<Product> {

    Session session;
    Transaction transaction;

    public Session setUp() {
        session = getSessionFactory().openSession();
        return session;
    }

    @Override
    public Product findById(Long id) {
        Product entity = null;
        try (Session session = setUp()) {
            transaction = session.beginTransaction();
            entity = session.get(Product.class, id);
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
    public List<Product> getAllEntities() {
        List<Product> entities = null;
        try (Session session = setUp()) {
            transaction = session.beginTransaction();
            entities = session.createQuery("FROM Product").list();
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
    public void delete(Product entity) {
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
            Product entity = session.get(Product.class, id);
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
    public Product save(Product entity) {
        try (Session session = setUp()) {
            transaction = session.beginTransaction();
            Long generatedId = (Long) session.save(entity);
            transaction.commit();
            entity = session.get(Product.class, generatedId);
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
    public void updateEntity(Product entity) {
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