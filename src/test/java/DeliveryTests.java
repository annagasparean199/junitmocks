import org.example.DAO.DAOImpl.*;
import org.example.entity.Delivery;
import org.example.entity.Product;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Date;

import static org.mockito.Mockito.when;
import static utils.Utils.getRecord;

public class DeliveryTests {

    @AfterEach
    public void clear() {
    }

//    @Mock
//    private ProductDao productDaoMock;
//
//    @Mock
//    private SalesDao salesDaoMock;
//
//    @Mock
//    private CreditDao creditDaoMock;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        rtt = new rtt(deliveryDao);
    }
    @Mock
    private DeliveryDao deliveryDao;

    @InjectMocks
    private rtt rtt;


    @Test
    public void testCalculateStoreLossForMonth() {
        //getRecord();
        Product product1 = new Product(1L, "Red", 10, "Widget A", 19.99, true);
        Product product2 = new Product(2L, "Blue", 5, "Widget B", 29.99, false);
        Delivery delivery1 = new Delivery(101L, new Date(), 5, product1);
        Delivery delivery2 = new Delivery(102L, new Date(), 3, product2);
        when(deliveryDao.getAllEntities(Delivery.class)).thenReturn(Arrays.asList(delivery1, delivery2));

        Assertions.assertEquals(300.0, rtt.getStoreLossAmountForMonth(1));
    }

//    @Test
//    public void testCalculateStoreProfitForMonth() {
//        getRecord();
//        Assertions.assertEquals(2250, DeliveryDao.getDeliveryDaoInstance().getStoreProfitAmountForMonth(1));
//    }
//
//    @Test
//    public void assertSalesBalance() {
//        getRecord();
//        double result = DeliveryDao.getDeliveryDaoInstance().getStoreSalesBalancePerMonth(1);
//        Assertions.assertEquals(1950, result);
//    }
}
//    @Test
//    public void testCalculateStoreLossForMonth(){
//        Product product = new Product(color, quantity, name, price = 10, inStock);
//        Product secproduct = new Product(color, quantity, name, price = 50, inStock);
//        Delivery.createNewDelivery(date = "/01/", product.getId, quantity = 1);
//        Delivery.createNewDelivery(date = "/01/", secproduct.getId, quantity = 1);
//        assertEquals(Delivery.getStoreLossAmountForMonth("Month"), 60);
//    }
//
//    @Test
//    public void testCalculateStoreProfitForMonth(){
//        Sales.createNewSaleRecord(prod_id, user_id, purchase_date= "/12/", quantity, paid_amount = 100, inCredit = true);
//        Sales.createNewSaleRecord(prod_id, user_id, purchase_date= "/01/", quantity, paid_amount = 1000, inCredit = false);
//        Credit.createNewCredit(date = "/01/", pricePerMonth = 100, sales_id = 1, months = 3);
//        assertEquals(Delivery.getStoreProfitPerMonth("Month"), 1000+Credit.getPricePerMonthById(1));
//    }
//
//    @Test
//    public void assertSalesBalance(){
//        Product product = new Product(color, quantity, name, price = 1000, inStock);
//        Product secproduct = new Product(color, quantity, name, price = 500, inStock);
//        Sales.createNewSaleRecord(product.getId, user_id, purchase_date= "/12/", quantity, paid_amount = 100, inCredit = true);
//        Sales.createNewSaleRecord(secproduct.getId, user_id, purchase_date= "/01/", quantity, paid_amount = 500, inCredit = false);
//        Credit.createNewCredit(pricePerMonth = 100, Months-9, sale_id = 1, payment_date = "/01/");
//        Delivery.createNewDelivery(date = "/01/", product.getId, quantity = 1);
//        Delivery.createNewDelivery(date = "/01/", secproduct.getId, quantity = 1);
//        int result = Delivery.getStoreProfitPerMonth("Month")-Delivery.getStoreLossAmountForMonth("Month");// ? or int result = getSalesBalancePerMonth("Month");
//        assertEquals(result, -900);
//    }
//        ProductDao.getProductDaoInstance().deleteById(1L);
//        ProductDao.getProductDaoInstance().deleteById(2L);
//        UserDao.getUserDaoInstance().deleteById(1L);
//        SalesDao.getSalesDaoInstance().deleteById(1L);
//        SalesDao.getSalesDaoInstance().deleteById(2L);
//        DeliveryDao.getDeliveryDaoInstance().deleteById(1L);
//        DeliveryDao.getDeliveryDaoInstance().deleteById(2L);
//        CreditDao.getCreditDaoInstance().deleteById(1L);
//        CreditDao.getCreditDaoInstance().deleteById(2L);
//        DiscountDao.getDiscountDaoInstance().deleteById(1L);
//}
