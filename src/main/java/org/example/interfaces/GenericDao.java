package org.example.interfaces;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import static org.example.hibernate.HibernateUtility.getSessionFactory;

public interface GenericDao<T> {

    private Session setUp() {
        SessionFactory sessionFactory;
        sessionFactory = getSessionFactory();
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
}
