package daotests.financialtests;

import org.example.dao.impl.CreditDao;
import org.example.dao.impl.DeliveryDao;
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
import java.util.Collections;
import java.util.Date;

import static org.mockito.Mockito.when;
import static utils.Utils.microwaveDelivery;
import static utils.Utils.microwaveSales;
import static utils.Utils.microwaveSalesCredit;
import static utils.Utils.phoneDelivery;
import static utils.Utils.phoneSales;
import static utils.Utils.productMicrowave;
import static utils.Utils.productPhone;


@RunWith(MockitoJUnitRunner.class)
public class DeliveryFinancialTests {

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Mock
    private SalesDao salesDaoMock;
    @Mock
    private CreditDao creditDaoMock;
    @InjectMocks
    @Spy
    private DeliveryDao deliveryDao;

    @Test
    public void testCalculateStoreLossForMonth() {
        microwaveDelivery.setDeliveryDate(new Date());
        phoneDelivery.setDeliveryDate(new Date());
        microwaveDelivery.setQuantity(1);
        phoneDelivery.setQuantity(2);
        productMicrowave.setPrice(100.0);
        productPhone.setPrice(50.0);
        when(deliveryDao.getAllEntities()).thenReturn(Arrays.asList(microwaveDelivery, phoneDelivery));
        double result = deliveryDao.getStoreLossAmountForMonth(1);

        Assertions.assertEquals(200.0, result);
    }


    @Test
    public void testCalculateStoreProfitForMonth() {
        microwaveSalesCredit.setPaymentDate(new Date());
        microwaveSalesCredit.setPricePerMonth(BigDecimal.valueOf(300));
        microwaveSales.setPurchaseDate(new Date());
        phoneSales.setPurchaseDate(new Date());
        microwaveSales.setPaidAmount(BigDecimal.valueOf(500));
        phoneSales.setPaidAmount(BigDecimal.valueOf(200));
        when(salesDaoMock.getAllEntities()).thenReturn(Arrays.asList(microwaveSales, phoneSales));
        when(creditDaoMock.getAllEntities()).thenReturn(Collections.singletonList(microwaveSalesCredit));

        Assertions.assertEquals(1000, deliveryDao.getStoreProfitAmountForMonth(1));
    }

    @Test
    public void assertSalesBalance() {
        microwaveDelivery.setDeliveryDate(new Date());
        phoneDelivery.setDeliveryDate(new Date());
        microwaveDelivery.setQuantity(1);
        phoneDelivery.setQuantity(2);
        productMicrowave.setPrice(100.0);
        productPhone.setPrice(50.0);
        microwaveSalesCredit.setPaymentDate(new Date());
        microwaveSalesCredit.setPricePerMonth(BigDecimal.valueOf(300));
        microwaveSales.setPurchaseDate(new Date());
        phoneSales.setPurchaseDate(new Date());
        microwaveSales.setPaidAmount(BigDecimal.valueOf(500));
        phoneSales.setPaidAmount(BigDecimal.valueOf(200));
        when(deliveryDao.getAllEntities()).thenReturn(Arrays.asList(microwaveDelivery, phoneDelivery));
        when(salesDaoMock.getAllEntities()).thenReturn(Arrays.asList(microwaveSales, phoneSales));
        when(creditDaoMock.getAllEntities()).thenReturn(Collections.singletonList(microwaveSalesCredit));

        double result = deliveryDao.getStoreSalesBalancePerMonth(1);
        Assertions.assertEquals(800, result);
    }
}