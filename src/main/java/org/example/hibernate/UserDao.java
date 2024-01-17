package org.example.hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import static org.example.hibernate.HibernateUtility.getSessionFactory;

public class UserDao {

    private SessionFactory sessionFactory;
    private Session session;
    private static UserDao instance;

    public static synchronized UserDao getAspNetUsersDaoInstance() {
        if (instance == null) {
            instance = new UserDao();
        }
        return instance;
    }

    public void setUp() {
        sessionFactory = getSessionFactory();
        session = sessionFactory.openSession();
    }
}
