package utils;

import org.example.DAO.DAOImpl.*;
import org.example.entity.*;

import java.math.BigDecimal;
import java.util.*;

public class Utils {

    public static Product product1 = new Product(1L, "Red", 10, "Widget A", 20.0, true);
    public static Product product2 = new Product(2L, "Blue", 5, "Widget B", 30.0, false);
    public static User user = new User(1L, 50000.0, UUID.randomUUID(), true, "ABC Corporation", "John Doe");
    public static Sales sales1 = new Sales(1L, product1, user, new Date(), 5, new BigDecimal("100.0"), false);
    public static Sales sales2 = new Sales(2L, product2, user, new Date(), 3, new BigDecimal("90.0"), true);
    public static Credit credit = new Credit(1L, new BigDecimal("30.0"), 12, sales1, new Date());
    public static Credit credit2 = new Credit(2L, new BigDecimal("30.0"), 12, sales2, new Date());
    public static Delivery delivery1 = new Delivery(1L, new Date(), 5, product1);
    public static Delivery delivery2 = new Delivery(2L, new Date(), 3, product2);
}