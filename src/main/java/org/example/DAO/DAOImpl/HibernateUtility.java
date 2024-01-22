package org.example.DAO.DAOImpl;

import org.example.entity.*;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtility {
    private static final SessionFactory sessionFactory;

    static {
        try {
            Configuration configuration = new Configuration().configure("hibernate.cfg.xml");

            configuration.addAnnotatedClass(Sales.class);
            configuration.addAnnotatedClass(Delivery.class);
            configuration.addAnnotatedClass(User.class);
            configuration.addAnnotatedClass(Product.class);
            configuration.addAnnotatedClass(Credit.class);

            sessionFactory = configuration.buildSessionFactory();
        } catch (Exception ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}