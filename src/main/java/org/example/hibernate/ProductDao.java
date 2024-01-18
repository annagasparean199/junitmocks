package org.example.hibernate;

import org.example.entity.Credit;
import org.example.entity.Product;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import static org.example.hibernate.HibernateUtility.getSessionFactory;

public class ProductDao {

    private SessionFactory sessionFactory;
    private Session session;
    private static ProductDao instance;

    public static synchronized ProductDao getProductDaoInstance() {
        if (instance == null) {
            instance = new ProductDao();
        }
        return instance;
    }

    public void setUp() {
        sessionFactory = getSessionFactory();
        session = sessionFactory.openSession();
    }

    public Product findById(Long id) {
        Transaction transaction = null;
        Product product = null;
        try {
            setUp();
            transaction = session.beginTransaction();

            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<Product> query = criteriaBuilder.createQuery(Product.class);
            Root<Product> root = query.from(Product.class);
            query.select(root).where(criteriaBuilder.equal(root.get("id"), id));

            product = session.createQuery(query).uniqueResult();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return product;
    }
}
