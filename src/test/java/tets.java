import org.example.DAO.DAOImpl.CreditDao;
import org.example.entity.*;
import org.junit.jupiter.api.Test;

import static org.example.DAO.DAOImpl.CreditDao.getCreditDaoInstance;
//import static org.example.hibernate.DiscountDao.getDiscountDaoInstance;


public class tets {

    // static CreditDao creditDao = getCreditDaoInstance();
//    static SalesDao salesDao = SalesDao.getSalesDaoInstance();
    //    static DiscountDao discountDao = getDiscountDaoInstance();
//    static Discount discount = discountDao.findById(1L);
    //static DeliveryDao deliveryDao = getDeliveryDaoInstance();
    //static Delivery delivery = deliveryDao.findById(1L);
//    static Credit credit = creditDao.findById(10L, Credit.class);
//    static Credit credit = Credit.builder()
//            .id(1L)
//            .pricePerMonth(new BigDecimal("1000.00"))
//            .months(12)
//            .sales(salesDao.findById(3L))
//            .paymentDate(new Date())
//            .build();

    //    static ProductDao productDao = getProductDaoInstance();
//    static Product product = productDao.findById(1L);
    @Test
    public void terst() {
        CreditDao creditDao = getCreditDaoInstance();
        Credit credit = creditDao.findById(1L, Credit.class);
        credit.setMonths(3);
        creditDao.updateEntity(credit);
    }

    public static void main(String[] args) {
        // System.out.println(credit);
        //System.out.println(delivery);
        //System.out.println(discount);
//        System.out.println(product);
        // List<Credit> creditList = creditDao.getAllEntities(Credit.class);
        // System.out.println(creditList);
//        creditDao.deleteById(8L);
        // creditDao.save(credit);

    }
}
