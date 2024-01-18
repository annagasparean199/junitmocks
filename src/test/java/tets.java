import org.example.entity.Credit;
import org.example.entity.Delivery;
import org.example.entity.Discount;
import org.example.entity.Product;
import org.example.hibernate.CreditDao;
import org.example.hibernate.DeliveryDao;
import org.example.hibernate.DiscountDao;
import org.example.hibernate.ProductDao;

import java.util.Date;
import java.util.List;

import static org.example.hibernate.CreditDao.getCreditDaoInstance;
import static org.example.hibernate.DeliveryDao.getDeliveryDaoInstance;
import static org.example.hibernate.DiscountDao.getDiscountDaoInstance;
import static org.example.hibernate.ProductDao.getProductDaoInstance;

public class tets {

    static CreditDao creditDao = getCreditDaoInstance();
    //    static DiscountDao discountDao = getDiscountDaoInstance();
//    static Discount discount = discountDao.findById(1L);
    //static DeliveryDao deliveryDao = getDeliveryDaoInstance();
    //static Delivery delivery = deliveryDao.findById(1L);
     static Credit credit = creditDao.findById(5L, Credit.class);
//    static ProductDao productDao = getProductDaoInstance();
//    static Product product = productDao.findById(1L);

    public static void main(String[] args) {
         System.out.println(credit);
        //System.out.println(delivery);
        //System.out.println(discount);
//        System.out.println(product);
        //List<Credit> creditList = creditDao.getAllCredits();
        // System.out.println(creditList);
    }
}
