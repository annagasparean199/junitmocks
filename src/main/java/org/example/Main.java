package org.example;

import org.example.dao.impl.HibernateUtility;
import org.hibernate.SessionFactory;

public class Main {

    public static void main(String[] args) {

        SessionFactory sessionFactory = HibernateUtility.getSessionFactory();
        sessionFactory.openSession();
    }
}