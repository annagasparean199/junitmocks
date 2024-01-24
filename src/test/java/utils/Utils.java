package utils;


import org.example.DAO.DAOImpl.CreditDao;
import org.example.DAO.DAOImpl.DeliveryDao;
import org.example.DAO.DAOImpl.ProductDao;
import org.example.DAO.DAOImpl.SalesDao;
import org.example.DAO.DAOImpl.UserDao;
import org.example.entity.Credit;
import org.example.entity.Delivery;
import org.example.entity.Product;
import org.example.entity.Sales;
import org.example.entity.User;
import org.junit.jupiter.api.BeforeAll;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;


public class Utils {

    static ProductDao productDao = new ProductDao();
    static SalesDao salesDao = new SalesDao();
    static CreditDao creditDao = new CreditDao();
    static DeliveryDao deliveryDao = new DeliveryDao();
    static UserDao userDao = new UserDao();

    @BeforeAll
    public static void createUtils() {

        productDao.save(product1);
        productDao.save(product2);
        userDao.save(user);
        userDao.save(user2);
        salesDao.save(sales1);
        salesDao.save(sales2);
        deliveryDao.save(delivery1);
        deliveryDao.save(delivery2);
        creditDao.save(credit);
        creditDao.save(credit2);
    }

    public static Product product1 = new Product(1L, "Red", 10, "Widget A", 20.0, true, null, null);
    public static Product product2 = new Product(2L, "Blue", 5, "Widget B", 30.0, false, null, null);
    public static User user = new User(1L, 50000.0, UUID.randomUUID(), true, "ABC Corporation", "John Doe", null);
    public static User user2 = new User(2L, 50000.0, UUID.randomUUID(), false, "Apple", "John", null);
    public static Sales sales1 = new Sales(1L, product1, user, new Date(), 5, new BigDecimal("100.00"), false, null);
    public static Sales sales2 = new Sales(2L, product2, user, new Date(), 3, new BigDecimal("90.00"), true, null);
    public static Sales sale = new Sales(2L, product2, user, new Date(), 3, new BigDecimal("90.00"), true, null);
    public static Credit credit = new Credit(1L, new BigDecimal("30.00"), 12, sales1, new Date());
    public static Credit credit2 = new Credit(2L, new BigDecimal("30.00"), 12, sales2, new Date());
    public static Delivery delivery1 = new Delivery(1L, new Date(), 5, product1);
    public static Delivery delivery2 = new Delivery(2L, new Date(), 3, product2);
}