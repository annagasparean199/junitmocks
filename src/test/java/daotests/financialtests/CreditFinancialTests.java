package daotests.financialtests;

import lombok.extern.log4j.Log4j;
import org.example.dao.impl.CreditDao;
import org.example.dao.impl.SalesDao;
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

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static utils.Utils.defaultUser;
import static utils.Utils.microwaveSales;
import static utils.Utils.microwaveSalesCredit;
import static utils.Utils.phoneSales;
import static utils.Utils.phoneSalesCredit;
import static utils.Utils.productMicrowave;

@Log4j
@RunWith(MockitoJUnitRunner.class)
public class CreditFinancialTests {

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Mock
    private SalesDao salesDaoMock;

    @Spy
    @InjectMocks
    CreditDao creditDao;

    @Test
    public void getTotalPriceForOneCredit() {
        microwaveSalesCredit.setMonths(3);
        microwaveSalesCredit.setPaymentDate(new Date());
        microwaveSalesCredit.setPricePerMonth(BigDecimal.valueOf(20.0));
        when(salesDaoMock.getAllEntities()).thenReturn(Arrays.asList(microwaveSales, phoneSales));
        when(creditDao.findCreditBySalesId(eq(microwaveSales.getId()))).thenReturn(microwaveSalesCredit);
        Double result = creditDao.getTotalPriceForOneCredit(productMicrowave.getId(), defaultUser.getId());
        log.info("Testing amount for one credit: \n" + defaultUser + "\n" + productMicrowave);
        log.info("Actual " + result + " Expected " + microwaveSalesCredit.getPricePerMonth().doubleValue() * microwaveSalesCredit.getMonths());
        Assertions.assertEquals(60.0, result);
    }


    @Test
    public void testTotalPriceForPersonCreditsPerMonth() {
        microwaveSalesCredit.setPricePerMonth(BigDecimal.valueOf(40.0));
        phoneSalesCredit.setPricePerMonth(BigDecimal.valueOf(50.0));
        when(salesDaoMock.getAllEntities()).thenReturn(Arrays.asList(microwaveSales, phoneSales));
        when(creditDao.getAllEntities()).thenReturn(Arrays.asList(microwaveSalesCredit, phoneSalesCredit));
        when(creditDao.findCreditBySalesId(eq(microwaveSales.getId()))).thenReturn(microwaveSalesCredit);
        when(creditDao.findCreditBySalesId(eq(phoneSales.getId()))).thenReturn(phoneSalesCredit);

        Assertions.assertEquals(90.0, creditDao.getTotalPriceForPersonCreditsPerMonth(defaultUser.getId(), 1));
    }

    @Test
    public void testTotalAmountForRemainCredits() {
        microwaveSalesCredit.setMonths(3);
        phoneSalesCredit.setMonths(2);
        microwaveSalesCredit.setPricePerMonth(BigDecimal.valueOf(40.0));
        phoneSalesCredit.setPricePerMonth(BigDecimal.valueOf(50.0));
        microwaveSalesCredit.setPaymentDate(new Date());
        phoneSalesCredit.setPaymentDate(new Date());
        when(salesDaoMock.getAllEntities()).thenReturn(Arrays.asList(microwaveSales, phoneSales));
        when(creditDao.findCreditBySalesId(eq(microwaveSales.getId()))).thenReturn(microwaveSalesCredit);
        when(creditDao.findCreditBySalesId(eq(phoneSales.getId()))).thenReturn(phoneSalesCredit);
        Assertions.assertEquals(220.0, creditDao.getTotalAmountForRemainCredits(defaultUser.getId()));
    }

    @Test
    public void testTotalAmountForPayedCredits() {
        microwaveSalesCredit.setMonths(3);
        phoneSalesCredit.setMonths(2);
        microwaveSalesCredit.setPricePerMonth(BigDecimal.valueOf(30.0));
        phoneSalesCredit.setPricePerMonth(BigDecimal.valueOf(50.0));
        microwaveSalesCredit.setPaymentDate(new Date(1698799000000L)); //1.11.2023
        phoneSalesCredit.setPaymentDate(new Date(1701459200000L)); //1.12.2023
        when(salesDaoMock.getAllEntities()).thenReturn(Arrays.asList(microwaveSales, phoneSales));
        when(creditDao.getAllEntities()).thenReturn(Arrays.asList(microwaveSalesCredit, phoneSalesCredit));
        when(creditDao.findCreditBySalesId(eq(microwaveSales.getId()))).thenReturn(microwaveSalesCredit);
        when(creditDao.findCreditBySalesId(eq(phoneSales.getId()))).thenReturn(phoneSalesCredit);
        Assertions.assertEquals(110, creditDao.getTotalAmountForPayedCredits(defaultUser.getId()));
    }
}