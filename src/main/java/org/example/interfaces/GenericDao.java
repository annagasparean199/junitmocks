package org.example.interfaces;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import java.util.List;

import static org.example.hibernate.HibernateUtility.getSessionFactory;


public interface GenericDao<T> {

    private Session setUp() {
        SessionFactory sessionFactory = getSessionFactory();
        return sessionFactory.openSession();
    }

    default Session getSession(){
        SessionFactory sessionFactory = getSessionFactory();
        return sessionFactory.openSession();
    }

    default T findById(Long id, Class<T> entityClass) {
        Transaction transaction = null;
        T entity = null;
        try {
            Session session = setUp();
            transaction = session.beginTransaction();

            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<T> query = criteriaBuilder.createQuery(entityClass);
            Root<T> root = query.from(entityClass);
            query.select(root).where(criteriaBuilder.equal(root.get("id"), id));

            entity = session.createQuery(query).uniqueResult();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return entity;
    }

    default List<T> getAllEntities(Class<T> entityClass) {
        Transaction transaction = null;
        List<T> entities = null;
        try(Session session = setUp()) {
            transaction = session.beginTransaction();

            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<T> query = criteriaBuilder.createQuery(entityClass);
            Root<T> root = query.from(entityClass);
            query.select(root);

            entities = session.createQuery(query).list();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return entities;
    }

    default void delete(T entity) {
        Transaction transaction = null;
        try {
            Session session = setUp();
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

    Class<T> getEntityClass();

    default void deleteById(Long id) {
        Transaction transaction = null;
        try {
            Session session = setUp();
            transaction = session.beginTransaction();
            T entity = session.get(getEntityClass(), id);
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

    default void save(T entity) {
        Transaction transaction = null;
        try {
            Session session = setUp();
            transaction = session.beginTransaction();
            session.save(entity);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

//    default void updateEntity(T entity) {
//        Transaction transaction = null;
//        try {
//            Session session = setUp();
//            transaction = session.beginTransaction();
//            session.update(entity);
//            transaction.commit();
//        } catch (Exception e) {
//            if (transaction != null) {
//                transaction.rollback();
//            }
//            e.printStackTrace();
//        }
//    }
default void updateEntity(T entity, Session session) {
    Transaction transaction = null;
    try {
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
