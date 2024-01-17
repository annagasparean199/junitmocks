package org.example.hibernate;

import org.example.entity.Credit;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

import static org.example.hibernate.HibernateUtility.getSessionFactory;

public class CreditDao {

    private SessionFactory sessionFactory;
    private Session session;
    private static CreditDao instance;

    public static synchronized CreditDao getCreditDaoInstance() {
        if (instance == null) {
            instance = new CreditDao();
        }
        return instance;
    }

    private void setUp() {
        sessionFactory = getSessionFactory();
        session = sessionFactory.openSession();
    }

    public Credit findById(Integer id) {
        Transaction transaction = null;
        Credit credit = null;
        try {
            setUp();
            transaction = session.beginTransaction();

            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<Credit> query = criteriaBuilder.createQuery(Credit.class);
            Root<Credit> root = query.from(Credit.class);
            query.select(root).where(criteriaBuilder.equal(root.get("id"), id));

            credit = session.createQuery(query).uniqueResult();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace(); // Log or handle the exception appropriately
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return credit;
    }

    public List<Credit> getAllCredits() {
        Transaction transaction = null;
        List<Credit> credits = null;
        try {
            setUp();
            transaction = session.beginTransaction();
            credits = session.createQuery("from Credit").list();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace(); // Log or handle the exception appropriately
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return credits;
    }
}
