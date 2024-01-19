package org.example.hibernate;

import org.example.entity.Credit;
import org.example.entity.Delivery;
import org.example.entity.Product;
import org.example.entity.Sales;
import org.example.interfaces.CreditCalculations;
import org.example.interfaces.DeliveryCalculations;
import org.example.interfaces.GenericDao;
import org.hibernate.Session;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;


public class DeliveryDao implements GenericDao<Delivery>, DeliveryCalculations {
    private static DeliveryDao instance;

    public static synchronized DeliveryDao getDeliveryDaoInstance() {
        if (instance == null) {
            instance = new DeliveryDao();
        }
        return instance;
    }

    @Override
    public Delivery findById(Long id, Class<Delivery> entityClass) {
        return GenericDao.super.findById(id, entityClass);
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

        List<Delivery> allDeliveries = DeliveryDao.getDeliveryDaoInstance().getAllEntities(Delivery.class);
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

        List<Sales> allSales = SalesDao.getSalesDaoInstance().getAllEntities(Sales.class);
        List<Credit> allCredits = CreditDao.getCreditDaoInstance().getAllEntities(Credit.class);
        double salesProfitAmount = 0;
        double creditsProfitAmount = 0;

        for (Sales sale : allSales) {
            Date saleDate = sale.getPurchaseDate();
            LocalDate localSaleDate = saleDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            if (localSaleDate.getMonthValue() == targetMonth) {
                salesProfitAmount+= sale.getPaidAmount().toBigInteger().doubleValue();
            }
        }

        for (Credit credit : allCredits) {
            Date creditDate = credit.getPaymentDate();
            LocalDate localSaleDate = creditDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            if (localSaleDate.getMonthValue() == targetMonth) {
                creditsProfitAmount+= credit.getPricePerMonth().doubleValue();
            }
        }
        return salesProfitAmount+creditsProfitAmount;
    }

    @Override
    public double getStoreSalesBalancePerMonth(int targetMonth){
        double profit = getStoreProfitAmountForMonth(targetMonth);
        double loss = getStoreLossAmountForMonth(targetMonth);
        return profit-loss;
    }
}
