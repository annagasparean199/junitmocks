package utils;

import org.example.entity.*;
import org.example.hibernate.*;

import java.math.BigDecimal;
import java.util.*;

public class Utils {

    public static Long getRecord() {

        List<Object> list = getFirstRecord();
        Product product = Product.builder()
                .color("Red")
                .quantity(100)
                .name("Example Product")
                .price(50.0)
                .inStock(true)
                .build();
        ProductDao.getProductDaoInstance().save(product);

        Sales sales = Sales.builder()
                .product(product)
                .user((User) list.get(1))
                .purchaseDate(new Date())
                .quantity(5)
                .paidAmount(new BigDecimal("250.00"))
                .inCredit(true)
                .build();
        SalesDao.getSalesDaoInstance().save(sales);

        Delivery delivery = Delivery.builder()
                .deliveryDate(new Date())
                .quantity(3)
                .product(product)
                .build();
        DeliveryDao.getDeliveryDaoInstance().save(delivery);


        Credit credit = Credit.builder()
                .pricePerMonth(new BigDecimal("1000.00"))
                .months(12)
                .sales(SalesDao.getSalesDaoInstance().findById(2L, Sales.class))
                .paymentDate(new Date())
                .build();
        CreditDao.getCreditDaoInstance().save(credit);
        User user = (User) list.get(1);

        return user.getId();
    }


    public static List<Object> getFirstRecord() {

        List<Object> record = new ArrayList<>();

        Product product = Product.builder()
                .color("Red")
                .quantity(100)
                .name("Example Product")
                .price(50.0)
                .inStock(true)
                .build();
        ProductDao.getProductDaoInstance().save(product);

        User user = User.builder()
                .salary(50000.0)
                .uuid(UUID.randomUUID())
                .legalEntity(true)
                .company("Example Company")
                .name("John Doe")
                .build();
        UserDao.getUserDaoInstance().save(user);

        long timestamp = 1709459200000L; //1.12.2023
        Date date = new Date(timestamp);

        Sales sales = Sales.builder()
                .product(product)
                .user(user)
                .purchaseDate(date)
                .quantity(5)
                .paidAmount(new BigDecimal("250.00"))
                .inCredit(true)
                .build();
        SalesDao.getSalesDaoInstance().save(sales);

        Delivery delivery = Delivery.builder()
                .deliveryDate(new Date())
                .quantity(3)
                .product(product)
                .build();
        DeliveryDao.getDeliveryDaoInstance().save(delivery);


        Credit credit = Credit.builder()
                .pricePerMonth(new BigDecimal("1000.00"))
                .months(12)
                .sales(SalesDao.getSalesDaoInstance().findById(1L, Sales.class))
                .paymentDate(new Date())
                .build();
        CreditDao.getCreditDaoInstance().save(credit);


        Discount discount = Discount.builder()
                .user(user)
                .percentage(5.0)
                .build();

        DiscountDao.getDiscountDaoInstance().save(discount);

        record.add(product);
        record.add(user);
        record.add(sales);
        record.add(delivery);
        record.add(credit);
        record.add(discount);
        return record;
    }

    public static List<Object> getSecRecord() {

        List<Object> record = new LinkedList<>();

        Product product = Product.builder()
                .color("Blue")
                .quantity(75)
                .name("Another Product")
                .price(75.0)
                .inStock(true)
                .build();

        ProductDao.getProductDaoInstance().save(product);

        User user = User.builder()
                .salary(60000.0)
                .uuid(UUID.randomUUID())
                .legalEntity(false)
                .company("New Company")
                .name("Jane Smith")
                .build();
        UserDao.getUserDaoInstance().save(user);

        Sales sales = Sales.builder()
                .product(product)
                .user(user)
                .purchaseDate(new Date())
                .quantity(3)
                .paidAmount(new BigDecimal("150.00"))
                .inCredit(false)
                .build();

        SalesDao.getSalesDaoInstance().save(sales);

        Delivery delivery = Delivery.builder()
                .deliveryDate(new Date())
                .quantity(2)
                .product(product)
                .build();
        DeliveryDao.getDeliveryDaoInstance().save(delivery);

        Credit credit = Credit.builder()
                .pricePerMonth(new BigDecimal("800.00"))
                .months(10)
                .sales(sales)
                .paymentDate(new Date())
                .build();
        CreditDao.getCreditDaoInstance().save(credit);

        Discount discount = Discount.builder()
                .user(user)
                .percentage(7.5)
                .build();

        DiscountDao.getDiscountDaoInstance().save(discount);

        record.add(product);
        record.add(user);
        record.add(sales);
        record.add(delivery);
        record.add(credit);
        record.add(discount);

        return record;
    }
}
