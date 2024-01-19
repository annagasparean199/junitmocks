package org.example.DAO.DAOImpl;

import org.example.DAO.GenericDao;
import org.example.entity.Credit;
import org.example.entity.Sales;
import org.example.interfaces.CreditCalculations;

import java.text.SimpleDateFormat;
import java.time.Period;

import java.time.LocalDate;
import java.util.List;

public class CreditDao implements GenericDao<Credit>, CreditCalculations {

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

    @Override
    public List<Credit> getAllEntities(Class<Credit> entityClass) {
        return GenericDao.super.getAllEntities(entityClass);
    }

    @Override
    public void delete(Credit entity) {
        GenericDao.super.delete(entity);
    }

    @Override
    public Class<Credit> getEntityClass() {
        return Credit.class;
    }

    @Override
    public void deleteById(Long id) {
        GenericDao.super.deleteById(id);
    }

    @Override
    public void save(Credit entity) {
        GenericDao.super.save(entity);
    }


    @Override
    public void updateEntity(Credit entity) {
        GenericDao.super.updateEntity(entity);
    }

    public Credit findCreditBySalesId(Long salesId) {
        CreditDao creditDao = CreditDao.getCreditDaoInstance();
        List<Credit> creditList = creditDao.getAllEntities(Credit.class);

        for (Credit credit : creditList) {
            if (credit.getSales() != null && credit.getSales().getId().equals(salesId)) {
                return credit;
            }
        }
        return null;
    }

    @Override
    public Double getTotalPriceForOneCredit(Long productId, Long userId) {

        SalesDao salesDao = SalesDao.getSalesDaoInstance();
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

        SalesDao salesDao = SalesDao.getSalesDaoInstance();
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
        SalesDao salesDao = SalesDao.getSalesDaoInstance();
        List<Sales> salesList = salesDao.getAllEntities(Sales.class);
        double totalAmountForPayedCredits = 0d;

        for (Sales sales : salesList) {
            if (sales.getUser().getId().equals(userId)) {
                Long salesId = Long.parseLong(sales.getId().toString());
                Credit credit = findCreditBySalesId(salesId);
            if (credit != null) {
                LocalDate paymentDate = LocalDate.parse(new SimpleDateFormat("yyyy-MM-dd").format(credit.getPaymentDate()));

                if (paymentDate.isAfter(LocalDate.now())) {
                    int payedMonths = calculatePayedMonths(paymentDate);
                    totalAmountForPayedCredits += credit.getPricePerMonth().doubleValue() * payedMonths;
                }
            }
        }
    }

        return totalAmountForPayedCredits;
}

    @Override
    public int calculatePayedMonths(LocalDate paymentDate) {
        int currentMonth = LocalDate.now().getMonthValue();
        int paymentMonth = paymentDate.getMonthValue();
        int remainingMonths = paymentMonth - currentMonth;

        return Math.max(remainingMonths, 0);
    }

    @Override
    public double getTotalAmountForRemainCredits(Long userId) {
        SalesDao salesDao = SalesDao.getSalesDaoInstance();
        List<Sales> salesList = salesDao.getAllEntities(Sales.class);
        double totalAmountForRemainCredits = 0d;

        for (Sales sales : salesList) {
            if (sales.getUser().getId().equals(userId)) {
                Long salesId = Long.parseLong(sales.getId().toString());
                Credit credit = findCreditBySalesId(salesId);
                if (credit != null) {
                    LocalDate paymentDate = LocalDate.parse(new SimpleDateFormat("yyyy-MM-dd").format(credit.getPaymentDate()));

                    int remainMonths = credit.getMonths()-calculatePayedMonths(paymentDate);
                        totalAmountForRemainCredits += credit.getPricePerMonth().doubleValue() * remainMonths;
                    }
                }
            }

        return totalAmountForRemainCredits;
    }
}