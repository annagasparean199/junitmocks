package org.example.dao.impl;

import org.example.dao.GenericDao;
import org.example.entities.Credit;
import org.example.entities.Sales;
import org.example.financemanager.CreditCalculations;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

import static org.example.dao.impl.HibernateUtility.getSessionFactory;

public class CreditDao implements GenericDao<Credit>, CreditCalculations {

    SalesDao salesDao = new SalesDao();
    Session session;
    Transaction transaction;

    public Session setUp() {
        session = getSessionFactory().openSession();
        return session;
    }

    @Override
    public Credit findById(Long id) {
        Credit entity = null;

        try (Session session = setUp()) {
            transaction = session.beginTransaction();
            entity = session.get(Credit.class, id);
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
    public List<Credit> getAllEntities() {
        List<Credit> entities = null;

        try (Session session = setUp()) {
            transaction = session.beginTransaction();
            entities = session.createQuery("FROM Credit").list();
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
    public void delete(Credit entity) {
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
            Credit entity = session.get(Credit.class, id);
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
    public Credit save(Credit entity) {
        try (Session session = setUp()) {
            transaction = session.beginTransaction();
            Long generatedId = (Long) session.save(entity);
            transaction.commit();
            entity = session.get(Credit.class, generatedId);
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
    public void updateEntity(Credit entity) {
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
    public double getTotalAmountForRemainCredits(Long userId) {
        List<Sales> salesList = salesDao.getAllEntities();
        double totalAmountForRemainCredits = 0d;

        for (Sales sale : salesList) {
            totalAmountForRemainCredits += getPayedPriceForCreditIfUserEquals(userId, sale);
        }

        return totalAmountForRemainCredits;
    }

    @Override
    public Double getTotalPriceForOneCredit(Long productId, Long userId) {
        List<Sales> salesList = salesDao.getAllEntities();
        Long salesId = findSaleByProductAndUser(salesList, productId, userId);
        Credit credit = findCreditBySalesId(salesId);
        int monthsDifference = getMonthsBetweenPaymentAndActualDates(credit);
        return monthsDifference * credit.getPricePerMonth().doubleValue();
    }

    @Override
    public Double getTotalPriceForPersonCreditsPerMonth(Long userId, int month) {
        List<Credit> userCredits = getListOfUserCredits(userId);
        return getTotalPriceForUserCreditsPerProvidedMonth(userCredits, month);
    }

    @Override
    public double getTotalAmountForPayedCredits(Long userId) {
        List<Sales> salesList = salesDao.getAllEntities();
        double totalAmountForPayedCredits = 0d;

        for (Sales sale : salesList) {
            totalAmountForPayedCredits += getPayedPriceForCreditIfUserEquals(userId, sale);
        }
        return totalAmountForPayedCredits;
    }

    public Credit findCreditBySalesId(Long salesId) {
        List<Credit> creditList = getAllEntities();

        for (Credit credit : creditList) {
            if (credit.getSales() != null && credit.getSales().getId().equals(salesId)) {
                return credit;
            }
        }
        return null;
    }

    private double getPayedPriceForCreditIfUserEquals(Long userId, Sales sale) {
        if (sale.getUser().getId().equals(userId)) {
            Credit credit = findCreditBySalesId(sale.getId());
            if (credit != null) {
                return credit.getPricePerMonth().doubleValue() * getMonthsBetweenPaymentAndActualDates(credit);
            }
        }
        return 0;
    }

    private int getMonthsBetweenPaymentAndActualDates(Credit credit) {
        LocalDate paymentDate = LocalDate.parse(new SimpleDateFormat("yyyy-MM-dd").format(credit.getPaymentDate()));
        Period period = Period.between(paymentDate, LocalDate.now());

        int monthDifference = (paymentDate.isBefore(LocalDate.now())) ?
                (credit.getMonths() - (period.getYears() * 12 + period.getMonths()))
                : period.getYears() * 12 + period.getMonths();

        return Math.max(credit.getMonths() - monthDifference, 0);
    }

    private List<Credit> getListOfUserCredits(Long userId) {
        List<Sales> salesList = salesDao.getAllEntities();
        List<Credit> userCredits = new ArrayList<>();
        for (Sales sales : salesList) {
            if (sales.getUser().getId().equals(userId)) {
                userCredits.add(findCreditBySalesId(sales.getId()));
            }
        }
        return userCredits;
    }

    private Long findSaleByProductAndUser(List<Sales> sales, Long productId, Long userId) {
        for (Sales sale : sales) {
            if (sale.getProduct().getId().equals(productId) && sale.getUser().getId().equals(userId)) {
                return sale.getId();
            }
        }
        return null;
    }

    private double getTotalPriceForUserCreditsPerProvidedMonth(List<Credit> userCredits, int month) {
        double amountForUserCreditsPerMonth = 0d;
        for (Credit credit : userCredits) {
            if (credit != null) {
                LocalDate purchaseDate = LocalDate.parse(new SimpleDateFormat("yyyy-MM-dd").format(credit.getPaymentDate()));
                int numericalMonth = purchaseDate.getMonth().getValue();
                if (numericalMonth == month) {
                    amountForUserCreditsPerMonth += credit.getPricePerMonth().doubleValue();
                }
            }
        }
        return amountForUserCreditsPerMonth;
    }
}