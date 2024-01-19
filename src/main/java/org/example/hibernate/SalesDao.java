package org.example.hibernate;

import org.example.entity.Product;
import org.example.entity.Sales;
import org.example.entity.User;
import org.example.interfaces.GenericDao;
import org.junit.jupiter.api.Test;

import java.time.Year;
import java.util.List;

public class SalesDao implements GenericDao<Sales> {
    private static SalesDao instance;

    public static synchronized SalesDao getSalesDaoInstance() {
        if (instance == null) {
            instance = new SalesDao();
        }
        return instance;
    }

    @Override
    public Sales findById(Long id, Class<Sales> entityClass) {
        return GenericDao.super.findById(id, entityClass);
    }

    @Override
    public List<Sales> getAllEntities(Class<Sales> entityClass) {
        return GenericDao.super.getAllEntities(entityClass);
    }

    @Override
    public void delete(Sales entity) {
        GenericDao.super.delete(entity);
    }

    @Override
    public Class<Sales> getEntityClass() {
        return Sales.class;
    }

    @Override
    public void deleteById(Long id) {
        GenericDao.super.deleteById(id);
    }

    @Override
    public void save(Sales entity) {
        GenericDao.super.save(entity);
    }

    @Override
    public void updateEntity(Sales entity) {
        GenericDao.super.updateEntity(entity);
    }

    public double getCalculatedPercentageForUser(Long userId) {
        User user = UserDao.getUserDaoInstance().findById(userId, User.class);
        double percentage = 0;

        if ("Maximum".equals(user.getCompany())) {
            percentage += 5;
        }

        double sumOfPurchases = SalesDao.getSalesDaoInstance()
                .getAllEntities(Sales.class)
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

    public double getPriceWithDiscount(double discount, Long productId) {
        Product product = ProductDao.getProductDaoInstance().findById(productId,Product.class);
        return product.getPrice() - (product.getPrice()/100*discount);
    }

    public double getFullPriceFromPaidAmount(Long salesId){
        Sales sales = SalesDao.getSalesDaoInstance().findById(salesId, Sales.class);
        Product product = sales.getProduct();
        return product.getPrice() - (sales.getPaidAmount().doubleValue()/ product.getPrice() * 100);
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

    public double getPercentageOfSalesPerYear(Year year) {
        List<Sales> sales = getAllEntities(Sales.class);

        long salesForTheYear = sales.stream()
                .filter(sale -> Year.of(sale.getPurchaseDate().getYear()).equals(year))
                .count();

        return (salesForTheYear / (double) sales.size()) * 100;
    }
}