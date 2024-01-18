package org.example.hibernate;

import org.example.entity.Credit;
import org.example.entity.Delivery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import static org.example.hibernate.HibernateUtility.getSessionFactory;


public class DeliveryDao {

    private SessionFactory sessionFactory;
    private Session session;
    private static DeliveryDao instance;

    public static synchronized DeliveryDao getDeliveryDaoInstance() {
        if (instance == null) {
            instance = new DeliveryDao();
        }
        return instance;
    }

    public void setUp() {
        sessionFactory = getSessionFactory();
        session = sessionFactory.openSession();
    }

    public Delivery findById(Long id) {
        Transaction transaction = null;
        Delivery delivery = null;
        try {
            setUp();
            transaction = session.beginTransaction();

            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<Delivery> query = criteriaBuilder.createQuery(Delivery.class);
            Root<Delivery> root = query.from(Delivery.class);
            query.select(root).where(criteriaBuilder.equal(root.get("id"), id));

            delivery = session.createQuery(query).uniqueResult();
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
        return delivery;
    }
}
