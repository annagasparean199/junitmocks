package DAOtests;

import org.example.DAO.DAOImpl.CreditDao;
import org.example.DAO.DAOImpl.DeliveryDao;
import org.example.DAO.DAOImpl.SalesDao;


import org.example.entity.Credit;
import org.example.entity.Delivery;
import org.example.entity.Sales;
import org.hibernate.Session;
import org.hibernate.Transaction;

import org.hibernate.query.Query;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;


import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static utils.Utils.credit;
import static utils.Utils.delivery1;
import static utils.Utils.delivery2;
import static utils.Utils.product1;
import static utils.Utils.product2;
import static utils.Utils.sales1;
import static utils.Utils.sales2;


@RunWith(MockitoJUnitRunner.class)
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
    private Session session;
    @Mock
    private Transaction transaction;

    @InjectMocks
    @Spy
    private DeliveryDao deliveryDao;


    @Test
    public void testCalculateStoreLossForMonth() {
        delivery1.setDeliveryDate(new Date());
        delivery2.setDeliveryDate(new Date());
        delivery1.setQuantity(1);
        delivery2.setQuantity(2);
        product1.setPrice(100.0);
        product2.setPrice(50.0);
        when(deliveryDao.getAllEntities(Delivery.class)).thenReturn(Arrays.asList(delivery1, delivery2));
        double result = deliveryDao.getStoreLossAmountForMonth(1);

        Assertions.assertEquals(200.0, result);
    }


    @Test
    public void testCalculateStoreProfitForMonth() {
        credit.setPaymentDate(new Date());
        credit.setPricePerMonth(BigDecimal.valueOf(300));
        sales1.setPurchaseDate(new Date());
        sales2.setPurchaseDate(new Date());
        sales1.setPaidAmount(BigDecimal.valueOf(500));
        sales2.setPaidAmount(BigDecimal.valueOf(200));
        when(salesDaoMock.getAllEntities(Sales.class)).thenReturn(Arrays.asList(sales1, sales2));
        when(creditDaoMock.getAllEntities(Credit.class)).thenReturn(Collections.singletonList(credit));

        Assertions.assertEquals(1000, deliveryDao.getStoreProfitAmountForMonth(1));
    }

    @Test
    public void assertSalesBalance() {
        delivery1.setDeliveryDate(new Date());
        delivery2.setDeliveryDate(new Date());
        delivery1.setQuantity(1);
        delivery2.setQuantity(2);
        product1.setPrice(100.0);
        product2.setPrice(50.0);
        credit.setPaymentDate(new Date());
        credit.setPricePerMonth(BigDecimal.valueOf(300));
        sales1.setPurchaseDate(new Date());
        sales2.setPurchaseDate(new Date());
        sales1.setPaidAmount(BigDecimal.valueOf(500));
        sales2.setPaidAmount(BigDecimal.valueOf(200));
        when(deliveryDao.getAllEntities(Delivery.class)).thenReturn(Arrays.asList(delivery1, delivery2));
        when(salesDaoMock.getAllEntities(Sales.class)).thenReturn(Arrays.asList(sales1, sales2));
        when(creditDaoMock.getAllEntities(Credit.class)).thenReturn(Collections.singletonList(credit));

        double result = deliveryDao.getStoreSalesBalancePerMonth(1);
        Assertions.assertEquals(800, result);
    }

    @Test
    public void testFindByIdEntityFound() {
        doReturn(session).when(deliveryDao).setUp();
        doReturn(transaction).when(session).beginTransaction();
        doReturn(delivery1).when(session).get(eq(Delivery.class), eq(1L));

        Delivery result = deliveryDao.findById(1L, Delivery.class);
        assertNotNull(result);
        Assertions.assertEquals(delivery1, result);
    }

    @Test
    public void testFindAllEntities() {
        doReturn(session).when(deliveryDao).setUp();
        doReturn(transaction).when(session).beginTransaction();
        List<Delivery> deliveries = Arrays.asList(delivery1, delivery2);
        Query<Delivery> query = mock(Query.class);
        doReturn(query).when(session).createQuery(anyString(), eq(Delivery.class));
        doReturn(deliveries).when(query).list();

        List<Delivery> result = deliveryDao.getAllEntities(Delivery.class);

        assertNotNull(result);
        Assertions.assertEquals(deliveries, result);

        verify(transaction).commit();
        verify(session).close();
    }

    @Test
    public void testDeleteDeliveryEntity() {
        doReturn(session).when(deliveryDao).setUp();
        doReturn(transaction).when(session).beginTransaction();

        deliveryDao.delete(delivery1);

        verify(session).delete(delivery1);
        verify(transaction).commit();
        verify(session).close();
    }

    @Test
    public void testDeleteById() {
        doReturn(session).when(deliveryDao).setUp();
        doReturn(transaction).when(session).beginTransaction();
        doReturn(delivery1).when(session).get(eq(Delivery.class), eq(delivery1.getId()));

        deliveryDao.deleteById(delivery1.getId());

        verify(session).delete(delivery1);
        verify(transaction).commit();
        verify(session).close();
    }

    @Test
    public void testSaveDeliveryEntity() {
        doReturn(session).when(deliveryDao).setUp();
        doReturn(transaction).when(session).beginTransaction();

        deliveryDao.save(delivery1);

        verify(session).save(delivery1);
        verify(transaction).commit();
        verify(session).close();
    }

    @Test
    public void updateDeliveryEntity() {
        doReturn(session).when(deliveryDao).setUp();
        doReturn(transaction).when(session).beginTransaction();

        deliveryDao.updateEntity(delivery1);

        verify(session).update(delivery1);
        verify(transaction).commit();
        verify(session).close();
    }
}