package org.example.DAO.DAOImpl;

import org.example.DAO.GenericDao;
import org.example.entity.Delivery;
import org.example.entity.Product;
import org.example.entity.Sales;
import org.example.entity.User;
import org.example.interfaces.SalesCalculations;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.time.LocalDate;
import java.time.Year;
import java.time.ZoneId;
import java.util.List;

import static org.example.DAO.DAOImpl.HibernateUtility.getSessionFactory;


public class SalesDao implements GenericDao<Sales>, SalesCalculations {

    Session session;
    Transaction transaction;
    ProductDao productDao = new ProductDao();
    UserDao userDao = new UserDao();

    public Session setUp(){
        session = getSessionFactory().openSession();
        return session;
    }

    @Override
    public Sales findById(Long id, Class<Sales> entityClass) {
        Sales entity = null;
        try(Session session = setUp()) {
            transaction = session.beginTransaction();
            entity = session.get(entityClass, id);
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
    public List<Sales> getAllEntities(Class<Sales> entityClass) {
        List<Sales> entities = null;
        try (Session session = setUp()) {
            transaction = session.beginTransaction();
            entities = session.createQuery("FROM " + entityClass.getName(), entityClass)
                    .list();
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
        try (Session session = setUp()){
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
        try(Session session = setUp()) {
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
        try(Session session = setUp()) {
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
        try(Session session = setUp()) {
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
        User user = userDao.findById(userId, User.class);
        double percentage = 0;

        if ("Maximum".equals(user.getCompany())) {
            percentage += 5;
        }

        double sumOfPurchases =
                getAllEntities(Sales.class)
                .stream()
                .filter(sale -> sale.getUser().getId().equals(userId))
                .mapToDouble(sale -> sale.getPaidAmount().doubleValue())
                .sum();

        if (sumOfPurchases >= 10000) {
            percentage += 5;
        }

        if (user.getLegalEntity()) {
            percentage += 3;
        }

        return percentage;
    }

    @Override
    public double getPriceWithDiscount(double discount, Long productId) {
        Product product = productDao.findById(productId,Product.class);
        return product.getPrice() - (product.getPrice()/100*discount);
    }

    @Override
    public double getDiscountPercentageFromPaidAmount(Long salesId){
        Sales sales = findById(salesId, Sales.class);
        Product product = sales.getProduct();
        return 100*((product.getPrice()-sales.getPaidAmount().doubleValue())/((product.getPrice())));
    }

    @Override
    public double getPercentageOfSalesPerYear(Year year) {
        List<Sales> sales = getAllEntities(Sales.class);

        long salesForTheYear = sales.stream()
                .filter(sale -> {
                    LocalDate purchaseDate = sale.getPurchaseDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                    return Year.from(purchaseDate).equals(year);
                })
                .count();

        if (salesForTheYear != 0) {
            return (salesForTheYear / (double) sales.size()) * 100;
        } else {
            return 0;
        }
    }
}


//    public double getPercentageOfSalesPerYear(Year year){
//        List<Sales> sales = getAllEntities(Sales.class);
//        double salesRecords = sales.size();
//        double salesForTheYear = 0;
//        for (Sales sale : sales){
//            Year thisYear = Year.of(sale.getPurchaseDate().getYear());
//            if(thisYear.equals(year)){
//                salesForTheYear++;
//            }
//        }
//        return salesForTheYear / salesRecords * 100;
//    }