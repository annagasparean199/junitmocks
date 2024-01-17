package org.example.hibernate;//package org.example.hibernate;

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
            configuration.addAnnotatedClass(Discount.class);
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
//import org.hibernate.Session;
//import org.hibernate.SessionFactory;
//import org.hibernate.cfg.Configuration;
//
//public class HibernateUtility {
//
//    private static final SessionFactory sessionFactory = buildSessionFactory();
//
//    private static SessionFactory buildSessionFactory() {
//        try {
//
//            return new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
//        } catch (Throwable ex) {
//            System.err.println("Initial SessionFactory creation failed." + ex);
//            throw new ExceptionInInitializerError(ex);
//        }
//    }
//
//    public static SessionFactory getSessionFactory() {
//        return sessionFactory;
//    }
//}

