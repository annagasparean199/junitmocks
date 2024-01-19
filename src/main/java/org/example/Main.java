package org.example;

//import org.example.hibernate.DiscountDao;
import org.example.hibernate.HibernateUtility;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.time.Year;

public class Main {

    public static void main(String[] args) {

        SessionFactory sessionFactory =  HibernateUtility.getSessionFactory();
        sessionFactory.openSession();
    }
}