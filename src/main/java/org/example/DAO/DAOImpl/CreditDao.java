package org.example.DAO.DAOImpl;

import org.example.DAO.GenericDao;
import org.example.entity.Credit;
import org.example.entity.Delivery;
import org.example.entity.Sales;
import org.example.interfaces.CreditCalculations;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.text.SimpleDateFormat;
import java.time.Period;

import java.time.LocalDate;
import java.util.List;

import static org.example.DAO.DAOImpl.HibernateUtility.getSessionFactory;

public class CreditDao implements GenericDao<Credit>, CreditCalculations {

    SalesDao salesDao = new SalesDao();

    Session session;
    Transaction transaction;

    public Session setUp(){
        session = getSessionFactory().openSession();
        return session;
    }

    @Override
    public Credit findById(Long id, Class<Credit> entityClass) {
        Credit entity = null;
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
    public List<Credit> getAllEntities(Class<Credit> entityClass) {
        List<Credit> entities = null;
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
    public void delete(Credit entity) {
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
    public Class<Credit> getEntityClass() {
        return null;
    }

    @Override
    public void deleteById(Long id) {
        try(Session session = setUp()) {
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
    public void save(Credit entity) {
        try(Session session = setUp()) {
            transaction = session.beginTransaction();
            session.save(entity);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public void updateEntity(Credit entity) {
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

    public Credit findCreditBySalesId(Long salesId) {
        List<Credit> creditList = getAllEntities(Credit.class);

        for (Credit credit : creditList) {
            if (credit.getSales() != null && credit.getSales().getId().equals(salesId)) {
                return credit;
            }
        }
        return null;
    }

    @Override
    public Double getTotalPriceForOneCredit(Long productId, Long userId) {

        List<Sales> salesList = salesDao.getAllEntities(Sales.class);
        Long salesId = null;

        for (Sales sales : salesList) {
            if (sales.getProduct().getId().equals(productId) && sales.getUser().getId().equals(userId)) {
                salesId = Long.parseLong(sales.getId().toString());
            }
        }
        Credit credit = findCreditBySalesId(salesId);
        LocalDate purchaseDate = LocalDate.parse(new SimpleDateFormat("yyyy-MM-dd").format(credit.getPaymentDate()));
        Period period = Period.between(purchaseDate, LocalDate.now());
        int monthsDifference = credit.getMonths() - (period.getYears() * 12 + period.getMonths());
        return monthsDifference * credit.getPricePerMonth().doubleValue();
    }

    @Override
    public Double getTotalPriceForPersonCreditsPerMonth(Long userId, int month) {

        List<Sales> salesList = salesDao.getAllEntities(Sales.class);
        double result = 0d;

        for (Sales sales : salesList) {
            if (sales.getUser().getId().equals(userId)) {
                Long salesId = Long.parseLong(sales.getId().toString());
                Credit credit = findCreditBySalesId(salesId);
                if (credit != null) {
                    LocalDate purchaseDate = LocalDate.parse(new SimpleDateFormat("yyyy-MM-dd").format(credit.getPaymentDate()));
                    int numericalMonth = purchaseDate.getMonth().getValue();
                    if (numericalMonth == month) {
                        result += credit.getPricePerMonth().doubleValue();
                    }
                }
            }
        }
        return result;
    }

    @Override
    public double getTotalAmountForPayedCredits(Long userId) {
        List<Sales> salesList = salesDao.getAllEntities(Sales.class);
        double totalAmountForPayedCredits = 0d;

        for (Sales sales : salesList) {
            if (sales.getUser().getId().equals(userId)) {
                Long salesId = Long.parseLong(sales.getId().toString());
                Credit credit = findCreditBySalesId(salesId);
                if (credit != null) {
                    LocalDate paymentDate = LocalDate.parse(new SimpleDateFormat("yyyy-MM-dd")
                            .format(credit.getPaymentDate()));

                    if (paymentDate.isBefore(LocalDate.now())) {
               int realPayedMonths = calculatePayedMonths(paymentDate, credit);
                        totalAmountForPayedCredits += credit.getPricePerMonth().doubleValue() * realPayedMonths;
                    }
                }
            }
        }

        return totalAmountForPayedCredits;
    }

    @Override
    public int calculatePayedMonths(LocalDate paymentDate, Credit credit) {
        Period period = Period.between(paymentDate, LocalDate.now());
        int monthDifference = credit.getMonths() - (period.getYears() * 12 + period.getMonths());
        int payedMonths =  credit.getMonths() - monthDifference;
        return Math.max(payedMonths, 0);
    }

    @Override
    public double getTotalAmountForRemainCredits(Long userId) {
        List<Sales> salesList = salesDao.getAllEntities(Sales.class);
        double totalAmountForRemainCredits = 0d;

        for (Sales sales : salesList) {
            if (sales.getUser().getId().equals(userId)) {
                Long salesId = Long.parseLong(sales.getId().toString());
                Credit credit = findCreditBySalesId(salesId);
                if (credit != null) {
                    LocalDate paymentDate = LocalDate.parse(new SimpleDateFormat("yyyy-MM-dd").format(credit.getPaymentDate()));

                    int remainMonths = credit.getMonths() - calculatePayedMonths(paymentDate, credit);
                    totalAmountForRemainCredits += credit.getPricePerMonth().doubleValue() * remainMonths;
                }
            }
        }

        return totalAmountForRemainCredits;
    }
}