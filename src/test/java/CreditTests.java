import lombok.extern.log4j.Log4j;

import org.example.DAO.DAOImpl.CreditDao;
import org.example.DAO.DAOImpl.ProductDao;
import org.example.DAO.DAOImpl.SalesDao;
import org.example.entity.*;

import static org.mockito.ArgumentMatchers.eq;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.*;

import static org.hamcrest.Matchers.any;
import static org.mockito.Mockito.when;
import static utils.Utils.credit;
import static utils.Utils.credit2;
import static utils.Utils.getRecord;
import static utils.Utils.product1;
import static utils.Utils.sales1;
import static utils.Utils.sales2;
import static utils.Utils.user;

@Log4j
public class CreditTests {

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Mock
    ProductDao productDao;
    @Mock
    private SalesDao salesDaoMock;

    @Spy
    @InjectMocks
    CreditDao creditDao;

    @Test
    public void getTotalPriceForOneCredit() {
        credit.setMonths(3);
        credit.setPricePerMonth(BigDecimal.valueOf(20.0));
        when(salesDaoMock.getAllEntities(Sales.class)).thenReturn(Arrays.asList(sales1, sales2));
        when(creditDao.findCreditBySalesId(eq(sales1.getId()))).thenReturn(credit);
        when(creditDao.findCreditBySalesId(eq(sales2.getId()))).thenReturn(credit);
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