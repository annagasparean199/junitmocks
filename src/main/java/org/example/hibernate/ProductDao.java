package org.example.hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import static org.example.hibernate.HibernateUtility.getSessionFactory;

public class ProductDao {

    private SessionFactory sessionFactory;
    private Session session;
    private static ProductDao instance;

    public static synchronized ProductDao getAspNetUsersDaoInstance() {
        if (instance == null) {
            instance = new ProductDao();
        }
        return instance;
    }

    public void setUp() {
        sessionFactory = getSessionFactory();
        session = sessionFactory.openSession();
    }
}
