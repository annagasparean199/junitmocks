package org.example.hibernate;

import org.example.entity.Delivery;
import org.example.entity.Discount;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import static org.example.hibernate.HibernateUtility.getSessionFactory;

public class DiscountDao {

    private SessionFactory sessionFactory;
    private Session session;
    private static DiscountDao instance;

    public static synchronized DiscountDao getDiscountDaoInstance() {
        if (instance == null) {
            instance = new DiscountDao();
        }
        return instance;
    }

    public void setUp() {
        sessionFactory = getSessionFactory();
        session = sessionFactory.openSession();
    }

    public Discount findById(Long id) {
        Transaction transaction = null;
        Discount discount = null;
        try {
            setUp();
            transaction = session.beginTransaction();

            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<Discount> query = criteriaBuilder.createQuery(Discount.class);
            Root<Discount> root = query.from(Discount.class);
            query.select(root).where(criteriaBuilder.equal(root.get("id"), id));

            discount = session.createQuery(query).uniqueResult();
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
        return discount;
    }
}
