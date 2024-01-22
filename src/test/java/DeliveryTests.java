import org.example.DAO.DAOImpl.*;
import org.example.DAO.GenericDao;
import org.example.entity.*;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import org.hibernate.query.Query;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;


import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import java.util.Arrays;
import java.util.Collections;


import static org.hamcrest.CoreMatchers.any;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static utils.Utils.*;

public class DeliveryTests {

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Mock
    private SalesDao salesDaoMock;
    @Mock
    private CreditDao creditDaoMock;
    @Mock
    Session session = mock(Session.class);
    @Mock
    Transaction transaction = mock(Transaction.class);
    @Spy
    GenericDao<DeliveryDao> genericDao;
    @Mock
    SessionFactory sessionFactoryMock = mock(SessionFactory.class);

    @Spy
    @InjectMocks
    private DeliveryDao deliveryDao;


    @Test
    public void testCalculateStoreLossForMonth() {
        when(deliveryDao.getAllEntities(Delivery.class)).thenReturn(Arrays.asList(delivery1, delivery2));
        double result = deliveryDao.getStoreLossAmountForMonth(1);

        Assertions.assertEquals(190.0, result);
    }


    @Test
    public void testCalculateStoreProfitForMonth() {
        when(salesDaoMock.getAllEntities(Sales.class)).thenReturn(Arrays.asList(sales1, sales2));
        when(creditDaoMock.getAllEntities(Credit.class)).thenReturn(Collections.singletonList(credit));

        Assertions.assertEquals(220, deliveryDao.getStoreProfitAmountForMonth(1));
    }

    @Test
    public void assertSalesBalance() {


        when(deliveryDao.getAllEntities(Delivery.class)).thenReturn(Arrays.asList(delivery1, delivery2));
        when(salesDaoMock.getAllEntities(Sales.class)).thenReturn(Arrays.asList(sales1, sales2));
        when(creditDaoMock.getAllEntities(Credit.class)).thenReturn(Collections.singletonList(credit));

        double result = deliveryDao.getStoreSalesBalancePerMonth(1);
        Assertions.assertEquals(30, result);
    }

    @Test
    public void testFindById() {
        when(genericDao.setUp()).thenReturn(session);
        when(session.beginTransaction()).thenReturn(transaction);

        Delivery delivery = deliveryDao.findById(1L, Delivery.class);
        Assertions.assertEquals(delivery1, delivery);
    }

    @Test
    public void testFindById_EntityFound() {
        when(deliveryDao.setUp()).thenReturn(session);
        when(session.beginTransaction()).thenReturn(transaction);
        when(session.get(eq(Delivery.class), eq(1L)))
                .thenReturn(delivery1);

        Delivery result = deliveryDao.findById(1L, Delivery.class);
        assertNotNull(result);
        Assertions.assertEquals(delivery1,result);
    }

}
//
//    @Override
//    public List<Delivery> getAllEntities(Class<Delivery> entityClass) {
//        return GenericDao.super.getAllEntities(entityClass);
//    }
//
//    @Override
//    public void delete(Delivery entity) {
//        GenericDao.super.delete(entity);
//    }
//
//    @Override
//    public Class<Delivery> getEntityClass() {
//        return Delivery.class;
//    }
//
//    @Override
//    public void deleteById(Long id) {
//        GenericDao.super.deleteById(id);
//    }
//
//    @Override
//    public void save(Delivery entity) {
//        GenericDao.super.save(entity);
//    }
//
//    @Override
//    public void updateEntity(Delivery entity) {
//        GenericDao.super.updateEntity(entity);
//    }


//    @Test
//    public void assertSalesBalance() {
//        getRecord();
//        double result = DeliveryDao.getDeliveryDaoInstance().getStoreSalesBalancePerMonth(1);
//        Assertions.assertEquals(1950, result);
//    }
//}
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
