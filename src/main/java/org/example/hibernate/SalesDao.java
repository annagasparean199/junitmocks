package org.example.hibernate;

import org.example.entity.Delivery;
import org.example.entity.Sales;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import static org.example.hibernate.HibernateUtility.getSessionFactory;

public class SalesDao {

    private SessionFactory sessionFactory;
    private Session session;
    private static SalesDao instance;

    public static synchronized SalesDao getSalesDaoInstance() {
        if (instance == null) {
            instance = new SalesDao();
        }
        return instance;
    }

    public void setUp() {
        sessionFactory = getSessionFactory();
        session = sessionFactory.openSession();
    }

    public Sales findById(Long id) {
        Transaction transaction = null;
        Sales sales = null;
        try {
            setUp();
            transaction = session.beginTransaction();

            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<Sales> query = criteriaBuilder.createQuery(Sales.class);
            Root<Sales> root = query.from(Sales.class);
            query.select(root).where(criteriaBuilder.equal(root.get("id"), id));

            sales = session.createQuery(query).uniqueResult();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return sales;
    }
}
