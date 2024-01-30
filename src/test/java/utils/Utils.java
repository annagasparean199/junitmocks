package utils;

import org.example.dao.impl.CreditDao;
import org.example.dao.impl.DeliveryDao;
import org.example.dao.impl.ProductDao;
import org.example.dao.impl.SalesDao;
import org.example.dao.impl.UserDao;
import org.example.entities.Credit;
import org.example.entities.Delivery;
import org.example.entities.Product;
import org.example.entities.Sales;
import org.example.entities.User;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

public class Utils {

    static ProductDao productDao = new ProductDao();
    static SalesDao salesDao = new SalesDao();
    public static CreditDao creditDao = new CreditDao();
    static DeliveryDao deliveryDao = new DeliveryDao();
    static UserDao userDao = new UserDao();

   // @BeforeAll
    public static void createUtils() {

        productDao.save(productMicrowave);
        productDao.save(productPhone);
        userDao.save(defaultUser);
        userDao.save(secondaryUser);
        salesDao.save(microwaveSales);
        salesDao.save(phoneSales);
        deliveryDao.save(microwaveDelivery);
        deliveryDao.save(phoneDelivery);
        creditDao.save(microwaveSalesCredit);
        creditDao.save(phoneSalesCredit);
    }

    public static Product productMicrowave = new Product(1L, "Red", 10, "Microwave", 20.0, true, null, null);
    public static Product productPhone = new Product(2L, "Blue", 5, "Phone", 30.0, false, null, null);
    public static User defaultUser = new User(1L, 50000.0, UUID.randomUUID(), true, "ABC Corporation", "John Doe", null);
    public static User secondaryUser = new User(2L, 50000.0, UUID.randomUUID(), false, "Apple", "John", null);
    public static Sales microwaveSales = new Sales(1L, productMicrowave, defaultUser, new Date(), 5, new BigDecimal("100.00"), false, null);
    public static Sales phoneSales = new Sales(2L, productPhone, defaultUser, new Date(), 3, new BigDecimal("90.00"), true, null);
    public static Sales secondaryPhoneSale = new Sales(2L, productPhone, defaultUser, new Date(), 3, new BigDecimal("90.00"), true, null);
    public static Credit microwaveSalesCredit = new Credit(1L, new BigDecimal("30.00"), 12, microwaveSales, new Date());
    public static Credit phoneSalesCredit = new Credit(2L, new BigDecimal("30.00"), 12, phoneSales, new Date());
    public static Delivery microwaveDelivery = new Delivery(1L, new Date(), 5, productMicrowave);
    public static Delivery phoneDelivery = new Delivery(2L, new Date(), 3, productPhone);
}