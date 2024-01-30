package org.example.dao.impl;

import org.example.dao.GenericDao;
import org.example.entities.Product;
import org.example.entities.Sales;
import org.example.entities.User;
import org.example.financemanager.SalesCalculations;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.time.LocalDate;
import java.time.Year;
import java.time.ZoneId;
import java.util.List;

import static org.example.dao.impl.HibernateUtility.getSessionFactory;

public class SalesDao implements GenericDao<Sales>, SalesCalculations {

    Session session;
    Transaction transaction;
    ProductDao productDao = new ProductDao();
    UserDao userDao = new UserDao();

    public Session setUp() {
        session = getSessionFactory().openSession();
        return session;
    }

    @Override
    public Sales findById(Long id) {
        Sales entity = null;
        try (Session session = setUp()) {
            transaction = session.beginTransaction();
            entity = session.get(Sales.class, id);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return entity;
    }

    @Override
    public List<Sales> getAllEntities() {
        List<Sales> entities = null;
        try (Session session = setUp()) {
            transaction = session.beginTransaction();
            entities = session.createQuery("FROM Sales").list();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return entities;
    }

    @Override
    public void delete(Sales entity) {
        try (Session session = setUp()) {
            transaction = session.beginTransaction();
            session.delete(entity);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public void deleteById(Long id) {
        try (Session session = setUp()) {
            transaction = session.beginTransaction();
            Sales entity = session.get(Sales.class, id);
            if (entity != null) {
                session.delete(entity);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public Sales save(Sales entity) {
        try (Session session = setUp()) {
            transaction = session.beginTransaction();
            Long generatedId = (Long) session.save(entity);
            transaction.commit();
            entity = session.get(Sales.class, generatedId);
            return entity;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public void updateEntity(Sales entity) {
        try (Session session = setUp()) {
            transaction = session.beginTransaction();
            session.update(entity);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public double getCalculatedPercentageForUser(Long userId) {
        User user = userDao.findById(userId);
        double sumOfUserPurchases = getTotalSumOfUserPurchaces(userId);
        return (sumOfUserPurchases >= 10000 ? 5 : 0)
                + (user.getLegalEntity() ? 3 : 0)
                + ("Maximum".equals(user.getCompany()) ? 5 : 0);
    }

    @Override
    public double getPriceWithDiscount(double discount, Long productId) {
        Product product = productDao.findById(productId);
        return product.getPrice() - (product.getPrice() / 100 * discount);
    }

    @Override
    public double getDiscountPercentageFromPaidAmount(Long salesId) {
        Sales sales = findById(salesId);
        Product product = sales.getProduct();
        return 100 * ((product.getPrice() - sales.getPaidAmount().doubleValue()) / ((product.getPrice())));
    }

    @Override
    public double getPercentageOfSalesPerYear(Year year) {
        double amountOfSales = getAllEntities().size();
        long salesAmountPerYear = getSalesAmountForProvidedYear(year);
        return (salesAmountPerYear / amountOfSales) * 100;
    }

    private long getSalesAmountForProvidedYear(Year year) {
        return getAllEntities()
                .stream()
                .filter(sale -> {
                    LocalDate purchaseDate = sale.getPurchaseDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                    return Year.from(purchaseDate).equals(year);
                })
                .count();
    }

    private double getTotalSumOfUserPurchaces(Long userId) {
        return getAllEntities()
                .stream()
                .filter(sale -> sale.getUser().getId().equals(userId))
                .mapToDouble(sale -> sale.getPaidAmount().doubleValue())
                .sum();
    }
}