package DAOtests;

import lombok.extern.log4j.Log4j;

import org.example.DAO.DAOImpl.CreditDao;
import org.example.DAO.DAOImpl.SalesDao;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;

import org.example.entity.Credit;
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
import java.util.Date;
import java.util.List;


import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;
import static utils.Utils.*;

@Log4j
@RunWith(MockitoJUnitRunner.class)
public class CreditTests {

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    @Mock
    private SalesDao salesDaoMock;

    @Mock
    Session session = mock(Session.class);

    @Mock
    Transaction transaction = mock(Transaction.class);

    @Spy
    @InjectMocks
    CreditDao creditDao;

    @Test
    public void testFindByIdEntityFound() {
        doReturn(session).when(creditDao).setUp();
        doReturn(transaction).when(session).beginTransaction();
        doReturn(credit).when(session).get(eq(Credit.class), eq(credit.getId()));

        Credit result = creditDao.findById(credit.getId(), Credit.class);
        assertNotNull(result);
        Assertions.assertEquals(credit, result);
    }

    @Test
    public void testFindAllEntities() {
        doReturn(session).when(creditDao).setUp();
        doReturn(transaction).when(session).beginTransaction();
        List<Credit> credits = Arrays.asList(credit, credit2);
        Query<Credit> query = mock(Query.class);
        doReturn(query).when(session).createQuery(anyString(), eq(Credit.class));
        doReturn(credits).when(query).list();

        List<Credit> result = creditDao.getAllEntities(Credit.class);

        assertNotNull(result);
        Assertions.assertEquals(credits, result);

        verify(transaction).commit();
        verify(session).close();
    }

    @Test
    public void testDeleteDeliveryEntity() {
        doReturn(session).when(creditDao).setUp();
        doReturn(transaction).when(session).beginTransaction();

        creditDao.delete(credit);

        verify(session).delete(credit);
        verify(transaction).commit();
        verify(session).close();
    }

    @Test
    public void testDeleteById() {
        doReturn(session).when(creditDao).setUp();
        doReturn(transaction).when(session).beginTransaction();
        doReturn(credit).when(session).get(eq(Credit.class), eq(credit.getId()));

        creditDao.deleteById(credit.getId());

        verify(session).delete(credit);
        verify(transaction).commit();
        verify(session).close();
    }

    @Test
    public void testSaveDeliveryEntity() {
        doReturn(session).when(creditDao).setUp();
        doReturn(transaction).when(session).beginTransaction();

        creditDao.save(credit);

        verify(session).save(credit);
        verify(transaction).commit();
        verify(session).close();
    }

    @Test
    public void updateDeliveryEntity(){
        doReturn(session).when(creditDao).setUp();
        doReturn(transaction).when(session).beginTransaction();

        creditDao.updateEntity(credit);

        verify(session).update(credit);
        verify(transaction).commit();
        verify(session).close();
    }

    @Test
    public void getTotalPriceForOneCredit() {
        credit.setMonths(3);
        credit.setPaymentDate(new Date());
        credit.setPricePerMonth(BigDecimal.valueOf(20.0));
        when(salesDaoMock.getAllEntities(Sales.class)).thenReturn(Arrays.asList(sales1, sales2));
        when(creditDao.findCreditBySalesId(eq(sales1.getId()))).thenReturn(credit);
        Double result = creditDao.getTotalPriceForOneCredit(product1.getId(), user.getId());
        log.info("Testing amount for one credit: \n" + user + "\n" + product1);
        log.info("Actual " + result + " Expected " + credit.getPricePerMonth().doubleValue()
                * credit.getMonths());
        Assertions.assertEquals(60.0, result);
    }


    @Test
    public void testTotalPriceForPersonCreditsPerMonth() {
        credit.setPricePerMonth(BigDecimal.valueOf(40.0));
        credit2.setPricePerMonth(BigDecimal.valueOf(50.0));
        when(salesDaoMock.getAllEntities(Sales.class)).thenReturn(Arrays.asList(sales1, sales2));
        when(creditDao.getAllEntities(Credit.class)).thenReturn(Arrays.asList(credit,credit2));
        when(creditDao.findCreditBySalesId(eq(sales1.getId()))).thenReturn(credit);
        when(creditDao.findCreditBySalesId(eq(sales2.getId()))).thenReturn(credit2);

        Assertions.assertEquals(90.0,
                creditDao.getTotalPriceForPersonCreditsPerMonth(user.getId(), 1));
    }

    @Test
    public void testTotalAmountForRemainCredits() {
        credit.setMonths(3);
        credit2.setMonths(2);
        credit.setPricePerMonth(BigDecimal.valueOf(40.0));
        credit2.setPricePerMonth(BigDecimal.valueOf(50.0));
        credit.setPaymentDate(new Date());
        credit2.setPaymentDate(new Date());
        when(salesDaoMock.getAllEntities(Sales.class)).thenReturn(Arrays.asList(sales1, sales2));
        when(creditDao.findCreditBySalesId(eq(sales1.getId()))).thenReturn(credit);
        when(creditDao.findCreditBySalesId(eq(sales2.getId()))).thenReturn(credit2);
        Assertions.assertEquals(220.0, creditDao.getTotalAmountForRemainCredits(user.getId()));
    }

    @Test
    public void testTotalAmountForPayedCredits() {
        credit.setMonths(3);
        credit2.setMonths(2);
        credit.setPricePerMonth(BigDecimal.valueOf(30.0));
        credit2.setPricePerMonth(BigDecimal.valueOf(50.0));
        credit.setPaymentDate(new Date(1698799000000L)); //1.11.2023
        credit2.setPaymentDate(new Date(1701459200000L)); //1.12.2023
        when(salesDaoMock.getAllEntities(Sales.class)).thenReturn(Arrays.asList(sales1, sales2));
        when(creditDao.getAllEntities(Credit.class)).thenReturn(Arrays.asList(credit,credit2));
        when(creditDao.findCreditBySalesId(eq(sales1.getId()))).thenReturn(credit);
        when(creditDao.findCreditBySalesId(eq(sales2.getId()))).thenReturn(credit2);
        Assertions.assertEquals(110, creditDao.getTotalAmountForPayedCredits(user.getId()));
    }
}