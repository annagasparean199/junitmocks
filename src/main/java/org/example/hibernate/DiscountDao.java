package org.example.hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import static org.example.hibernate.HibernateUtility.getSessionFactory;

public class DiscountDao {

    private SessionFactory sessionFactory;
    private Session session;
    private static DiscountDao instance;

    public static synchronized DiscountDao getAspNetUsersDaoInstance() {
        if (instance == null) {
            instance = new DiscountDao();
        }
        return instance;
    }

    public void setUp() {
        sessionFactory = getSessionFactory();
        session = sessionFactory.openSession();
    }
}
