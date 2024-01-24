package org.example;

import org.example.DAO.DAOImpl.HibernateUtility;
import org.hibernate.SessionFactory;

public class Main {

    public static void main(String[] args) {
        SessionFactory sessionFactory =  HibernateUtility.getSessionFactory();
        sessionFactory.openSession();
    }
}