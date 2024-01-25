package org.example.dao.impl;

import org.example.dao.GenericDao;
import org.example.entities.Credit;
import org.example.entities.Delivery;
import org.example.entities.Sales;
import org.example.financemanager.DeliveryCalculations;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import static org.example.dao.impl.HibernateUtility.getSessionFactory;

public class DeliveryDao implements GenericDao<Delivery>, DeliveryCalculations {

    SalesDao salesDao = new SalesDao();
    CreditDao creditDao = new CreditDao();
    Session session;
    Transaction transaction;

    public Session setUp() {
        session = getSessionFactory().openSession();
        return session;
    }

    @Override
    public Delivery findById(Long id) {
        Delivery entity = null;
        try (Session session = setUp()) {
            transaction = session.beginTransaction();
            entity = session.get(Delivery.class, id);
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
    public List<Delivery> getAllEntities() {
        List<Delivery> entities = null;
        try (Session session = setUp()) {
            transaction = session.beginTransaction();
            entities = session.createQuery("FROM " + Delivery.class.getName(), Delivery.class).list();
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
    public void delete(Delivery entity) {
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
            Delivery entity = session.get(Delivery.class, id);
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
    public Delivery save(Delivery entity) {
        try (Session session = setUp()) {
            transaction = session.beginTransaction();
            Long generatedId = (Long) session.save(entity);
            transaction.commit();
            entity = session.get(Delivery.class, generatedId);
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
    public void updateEntity(Delivery entity) {
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
    public double getStoreLossAmountForMonth(int targetMonth) {
        List<Delivery> allDeliveries = getAllEntities();
        double storeLossAmount = 0;
        for (Delivery delivery : allDeliveries) {
            storeLossAmount += getStoreLossForDelivery(delivery, targetMonth);
        }
        return storeLossAmount;
    }

    @Override
    public double getStoreProfitAmountForMonth(int targetMonth) {
        double salesProfit = getTotalSalesPayedAmountForTargetMonth(targetMonth);
        double creditProfit = getTotalCreditPricePerMonthForTargetMonth(targetMonth);
        return salesProfit + creditProfit;
    }

    @Override
    public double getStoreSalesBalancePerMonth(int targetMonth) {
        double profit = getStoreProfitAmountForMonth(targetMonth);
        double loss = getStoreLossAmountForMonth(targetMonth);
        return profit - loss;
    }

    private double getStoreLossForDelivery(Delivery delivery, int targetMonth) {
        if (getLocalDateFromDate(delivery.getDeliveryDate()).getMonthValue() == targetMonth) {
            int quantity = delivery.getQuantity();
            double price = delivery.getProduct().getPrice();
            return quantity * price;
        }
        return 0;
    }

    private double getTotalCreditPricePerMonthForTargetMonth(int targetMonth) {
        double profitAmount = 0;
        for (Credit credit : creditDao.getAllEntities()) {
            if (getLocalDateFromDate(credit.getPaymentDate()).getMonthValue() == targetMonth) {
                profitAmount += credit.getPricePerMonth().doubleValue();
            }
        }
        return profitAmount;
    }

    private double getTotalSalesPayedAmountForTargetMonth(int targetMonth) {
        double profitAmount = 0;
        for (Sales sale : salesDao.getAllEntities()) {
            if (getLocalDateFromDate(sale.getPurchaseDate()).getMonthValue() == targetMonth) {
                profitAmount += sale.getPaidAmount().doubleValue();
            }
        }
        return profitAmount;
    }

    private static LocalDate getLocalDateFromDate(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }
}