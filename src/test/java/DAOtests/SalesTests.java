package DAOtests;

import org.example.DAO.DAOImpl.ProductDao;
import org.example.DAO.DAOImpl.SalesDao;
import org.example.DAO.DAOImpl.UserDao;
import org.example.entity.Product;
import org.example.entity.Sales;
import org.example.entity.User;
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
import java.time.Year;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.mock;
import static utils.Utils.*;

@RunWith(MockitoJUnitRunner.class)
public class SalesTests {

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    @Mock
    ProductDao productDao;
    @Mock
    UserDao userDao;
    @Mock
    Session session = mock(Session.class);
    @Mock
    Transaction transaction = mock(Transaction.class);
    @Spy
    @InjectMocks
    private SalesDao salesDao;

    @Test
    public void testFindByIdEntityFound() {
        doReturn(session).when(salesDao).setUp();
        doReturn(transaction).when(session).beginTransaction();
        doReturn(sales1).when(session).get(eq(Sales.class), eq(sales1.getId()));

        Sales result = salesDao.findById(sales1.getId(), Sales.class);
        assertNotNull(result);
        Assertions.assertEquals(sales1, result);
    }

    @Test
    public void testFindAllEntities() {
        doReturn(session).when(salesDao).setUp();
        doReturn(transaction).when(session).beginTransaction();
        List<Sales> sales = Arrays.asList(sales1, sales2);
        Query<Sales> query = mock(Query.class);
        doReturn(query).when(session).createQuery(anyString(), eq(Sales.class));
        doReturn(sales).when(query).list();

        List<Sales> result = salesDao.getAllEntities(Sales.class);

        assertNotNull(result);
        Assertions.assertEquals(sales, result);

        verify(transaction).commit();
        verify(session).close();
    }

    @Test
    public void testDeleteDeliveryEntity() {
        doReturn(session).when(salesDao).setUp();
        doReturn(transaction).when(session).beginTransaction();

        salesDao.delete(sales1);

        verify(session).delete(sales1);
        verify(transaction).commit();
        verify(session).close();
    }

    @Test
    public void testDeleteById() {
        doReturn(session).when(salesDao).setUp();
        doReturn(transaction).when(session).beginTransaction();
        doReturn(sales1).when(session).get(eq(Sales.class), eq(sales1.getId()));

        salesDao.deleteById(sales1.getId());

        verify(session).delete(sales1);
        verify(transaction).commit();
        verify(session).close();
    }

    @Test
    public void testSaveDeliveryEntity() {
        doReturn(session).when(salesDao).setUp();
        doReturn(transaction).when(session).beginTransaction();

        salesDao.save(sales1);

        verify(session).save(sales1);
        verify(transaction).commit();
        verify(session).close();
    }

    @Test
    public void updateDeliveryEntity(){
        doReturn(session).when(salesDao).setUp();
        doReturn(transaction).when(session).beginTransaction();

        salesDao.updateEntity(sales1);

        verify(session).update(sales1);
        verify(transaction).commit();
        verify(session).close();
    }

    @Test
    public void testDiscountPercentageFromPaidAmount() {
        sales1.setPaidAmount(BigDecimal.valueOf(80));
        product1.setPrice(100.0);
        doReturn(sales1).when(salesDao).findById(eq(sales1.getId()), eq(Sales.class));
        Assertions.assertEquals(20, salesDao.getDiscountPercentageFromPaidAmount(sales1.getId()));
    }

    @Test
    public void testPercentageOfSalesPerYear() {
        sales1.setPurchaseDate(new Date(1701459200000L));
        sales2.setPurchaseDate(new Date());
        doReturn(Arrays.asList(sales1, sales2)).when(salesDao).getAllEntities(eq(Sales.class));

        Assertions.assertEquals(50, salesDao.getPercentageOfSalesPerYear(Year.now()));
    }

    @Test
    public void testCalculatePriceWithDiscount() {
        product1.setPrice(1000.0);
        doReturn(product1).when(productDao).findById(eq(product1.getId()), eq(Product.class));
        Assertions.assertEquals(salesDao.getPriceWithDiscount(50, product1.getId()), 500);
    }

    @Test
    public void calculationDefaultDiscount() {
        user.setCompany("Apple");
        user.setLegalEntity(false);
        sales1.setPaidAmount(BigDecimal.valueOf(100));
        sales2.setPaidAmount(BigDecimal.valueOf(100));
        doReturn(user).when(userDao).findById(eq(user.getId()), eq(User.class));
        doReturn(Arrays.asList(sales1, sales2)).when(salesDao).getAllEntities(eq(Sales.class));
        Assertions.assertEquals(0, salesDao.getCalculatedPercentageForUser(user.getId()));
    }

    @Test
    public void calculationDiscountUserWithThisCompany() {
        user.setCompany("Maximum");
        user.setLegalEntity(false);
        sales1.setPaidAmount(BigDecimal.valueOf(100));
        sales2.setPaidAmount(BigDecimal.valueOf(100));
        doReturn(user).when(userDao).findById(eq(user.getId()), eq(User.class));
        doReturn(Arrays.asList(sales1, sales2)).when(salesDao).getAllEntities(eq(Sales.class));
        Assertions.assertEquals(5, salesDao.getCalculatedPercentageForUser(user.getId()));
    }

    @Test
    public void calculationDiscountUserWithProduct() {
        user.setCompany("Apple");
        user.setLegalEntity(false);
        sales1.setPaidAmount(BigDecimal.valueOf(10000));
        sales2.setPaidAmount(BigDecimal.valueOf(100));
        doReturn(user).when(userDao).findById(eq(user.getId()), eq(User.class));
        doReturn(Arrays.asList(sales1, sales2)).when(salesDao).getAllEntities(eq(Sales.class));
        Assertions.assertEquals(5, salesDao.getCalculatedPercentageForUser(user.getId()));
    }

    @Test
    public void calculationDiscountUserWithProductAndCompany() {
        user.setCompany("Maximum");
        user.setLegalEntity(false);
        sales1.setPaidAmount(BigDecimal.valueOf(10000));
        sales2.setPaidAmount(BigDecimal.valueOf(100));
        doReturn(user).when(userDao).findById(eq(user.getId()), eq(User.class));
        doReturn(Arrays.asList(sales1, sales2)).when(salesDao).getAllEntities(eq(Sales.class));
        Assertions.assertEquals(10, salesDao.getCalculatedPercentageForUser(user.getId()));
    }

    @Test
    public void calculationDiscountUserWithLegalEntity() {
        user.setCompany("Apple");
        user.setLegalEntity(true);
        sales1.setPaidAmount(BigDecimal.valueOf(100));
        sales2.setPaidAmount(BigDecimal.valueOf(100));
        doReturn(user).when(userDao).findById(eq(user.getId()), eq(User.class));
        doReturn(Arrays.asList(sales1, sales2)).when(salesDao).getAllEntities(eq(Sales.class));
        Assertions.assertEquals(3, salesDao.getCalculatedPercentageForUser(user.getId()));
    }

    @Test
    public void calculationDiscountUserWithProductLegalEntityAndCompany() {
        user.setCompany("Maximum");
        user.setLegalEntity(true);
        sales1.setPaidAmount(BigDecimal.valueOf(9900));
        sales2.setPaidAmount(BigDecimal.valueOf(100));
        doReturn(user).when(userDao).findById(eq(user.getId()), eq(User.class));
        doReturn(Arrays.asList(sales1, sales2)).when(salesDao).getAllEntities(eq(Sales.class));
        Assertions.assertEquals(13, salesDao.getCalculatedPercentageForUser(user.getId()));
    }
}