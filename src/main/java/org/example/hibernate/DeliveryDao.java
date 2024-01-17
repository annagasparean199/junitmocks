package org.example.hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import static org.example.hibernate.HibernateUtility.getSessionFactory;


public class DeliveryDao {

    private SessionFactory sessionFactory;
    private Session session;
    private static DeliveryDao instance;

    public static synchronized DeliveryDao getAspNetUsersDaoInstance() {
        if (instance == null) {
            instance = new DeliveryDao();
        }
        return instance;
    }

    public void setUp() {
        sessionFactory = getSessionFactory();
        session = sessionFactory.openSession();
    }
}
