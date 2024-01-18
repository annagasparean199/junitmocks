package org.example.hibernate;

import org.example.entity.Credit;
import org.example.interfaces.GenericDao;

public class CreditDao implements GenericDao<Credit> {

    private static CreditDao instance;

    public static synchronized CreditDao getCreditDaoInstance() {
        if (instance == null) {
            instance = new CreditDao();
        }
        return instance;
    }

    @Override
    public Credit findById(Long id, Class<Credit> entityClass) {
        return GenericDao.super.findById(id, entityClass);
    }

//    public List<Credit> getAllCredits() {
//        Transaction transaction = null;
//        List<Credit> credits = null;
//        try {
//            setUp();
//            transaction = session.beginTransaction();
//            credits = session.createQuery("from Credit").list();
//            transaction.commit();
//        } catch (Exception e) {
//            if (transaction != null) {
//                transaction.rollback();
//            }
//            e.printStackTrace();
//        } finally {
//            if (session != null) {
//                session.close();
//            }
//        }
//        return credits;
//    }

//    @Override
//    public Credit findById(Long id) {
//        return ;
//    }
}
