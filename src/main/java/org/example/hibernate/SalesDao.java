package org.example.hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import static org.example.hibernate.HibernateUtility.getSessionFactory;

public class SalesDao {

    private SessionFactory sessionFactory;
    private Session session;
    private static SalesDao instance;

    public static synchronized SalesDao getAspNetUsersDaoInstance() {
        if (instance == null) {
            instance = new SalesDao();
        }
        return instance;
    }

    public void setUp() {
        sessionFactory = getSessionFactory();
        session = sessionFactory.openSession();
    }
}
