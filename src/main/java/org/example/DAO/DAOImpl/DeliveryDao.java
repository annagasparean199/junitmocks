package org.example.DAO.DAOImpl;

import org.example.DAO.GenericDao;
import org.example.entity.Credit;
import org.example.entity.Delivery;
import org.example.entity.Product;
import org.example.entity.Sales;
import org.example.interfaces.DeliveryCalculations;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import static org.example.DAO.DAOImpl.HibernateUtility.getSessionFactory;

public class DeliveryDao implements GenericDao<Delivery>, DeliveryCalculations {

    SalesDao salesDao = new SalesDao();
    CreditDao creditDao = new CreditDao();
    SessionFactory sessionFactory;
    Transaction transaction;

    public Session setUp(){
        sessionFactory = getSessionFactory();
        return sessionFactory.openSession();
    }


    @Override
    public Delivery findById(Long id, Class<Delivery> entityClass) {
        Delivery entity = null;
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
    public List<Delivery> getAllEntities(Class<Delivery> entityClass) {
        return GenericDao.super.getAllEntities(entityClass);
    }

    @Override
    public void delete(Delivery entity) {
        GenericDao.super.delete(entity);
    }

    @Override
    public Class<Delivery> getEntityClass() {
        return Delivery.class;
    }

    @Override
    public void deleteById(Long id) {
        GenericDao.super.deleteById(id);
    }

    @Override
    public void save(Delivery entity) {
        GenericDao.super.save(entity);
    }

    @Override
    public void updateEntity(Delivery entity) {
        GenericDao.super.updateEntity(entity);
    }


    @Override
    public double getStoreLossAmountForMonth(int targetMonth) {

        List<Delivery> allDeliveries = getAllEntities(Delivery.class);
        double storeLossAmount = 0;

        for (Delivery delivery : allDeliveries) {
            Date deliveryDate = delivery.getDeliveryDate();
            LocalDate localDeliveryDate = deliveryDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            if (localDeliveryDate.getMonthValue() == targetMonth) {
                Product product = delivery.getProduct();
                int quantity = delivery.getQuantity();
                double price = product.getPrice();
                storeLossAmount += quantity * price;
            }
        }
        return storeLossAmount;
    }


    @Override
    public double getStoreProfitAmountForMonth(int targetMonth) {

        List<Sales> allSales = salesDao.getAllEntities(Sales.class);
        List<Credit> allCredits = creditDao.getAllEntities(Credit.class);
        double salesProfitAmount = 0;
        double creditsProfitAmount = 0;

        for (Sales sale : allSales) {
            Date saleDate = sale.getPurchaseDate();
            LocalDate localSaleDate = saleDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            if (localSaleDate.getMonthValue() == targetMonth) {
                salesProfitAmount += sale.getPaidAmount().toBigInteger().doubleValue();
            }
        }

        for (Credit credit : allCredits) {
            Date creditDate = credit.getPaymentDate();
            LocalDate localSaleDate = creditDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            if (localSaleDate.getMonthValue() == targetMonth) {
                creditsProfitAmount += credit.getPricePerMonth().doubleValue();
            }
        }
        return salesProfitAmount + creditsProfitAmount;
    }

    @Override
    public double getStoreSalesBalancePerMonth(int targetMonth) {
        double profit = getStoreProfitAmountForMonth(targetMonth);
        double loss = getStoreLossAmountForMonth(targetMonth);
        return profit - loss;
    }
}
