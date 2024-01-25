package daotests.financialtests;

import org.example.dao.impl.ProductDao;
import org.example.dao.impl.SalesDao;
import org.example.dao.impl.UserDao;
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

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static utils.Utils.productMicrowave;
import static utils.Utils.microwaveSales;
import static utils.Utils.phoneSales;
import static utils.Utils.defaultUser;

@RunWith(MockitoJUnitRunner.class)
public class SalesFinancialTests {

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Mock
    ProductDao productDao;
    @Mock
    UserDao userDao;
    @Spy
    @InjectMocks
    private SalesDao salesDao;

    @Test
    public void testDiscountPercentageFromPaidAmount() {
        microwaveSales.setPaidAmount(BigDecimal.valueOf(80));
        productMicrowave.setPrice(100.0);
        doReturn(microwaveSales).when(salesDao).findById(eq(microwaveSales.getId()));
        Assertions.assertEquals(20, salesDao.getDiscountPercentageFromPaidAmount(microwaveSales.getId()));
    }

    @Test
    public void testPercentageOfSalesPerYear() {
        microwaveSales.setPurchaseDate(new Date(1701459200000L));
        phoneSales.setPurchaseDate(new Date());
        doReturn(Arrays.asList(microwaveSales, phoneSales)).when(salesDao).getAllEntities();
        Assertions.assertEquals(50, salesDao.getPercentageOfSalesPerYear(Year.now()));
    }

    @Test
    public void testCalculatePriceWithDiscount() {
        productMicrowave.setPrice(1000.0);
        doReturn(productMicrowave).when(productDao).findById(eq(productMicrowave.getId()));
        Assertions.assertEquals(salesDao.getPriceWithDiscount(50, productMicrowave.getId()), 500);
    }

    @Test
    public void calculationDefaultDiscount() {
        defaultUser.setCompany("Apple");
        defaultUser.setLegalEntity(false);
        microwaveSales.setPaidAmount(BigDecimal.valueOf(100));
        phoneSales.setPaidAmount(BigDecimal.valueOf(100));
        doReturn(defaultUser).when(userDao).findById(eq(defaultUser.getId()));
        doReturn(Arrays.asList(microwaveSales, phoneSales)).when(salesDao).getAllEntities();
        Assertions.assertEquals(0, salesDao.getCalculatedPercentageForUser(defaultUser.getId()));
    }

    @Test
    public void calculationDiscountUserWithThisCompany() {
        defaultUser.setCompany("Maximum");
        defaultUser.setLegalEntity(false);
        microwaveSales.setPaidAmount(BigDecimal.valueOf(100));
        phoneSales.setPaidAmount(BigDecimal.valueOf(100));
        doReturn(defaultUser).when(userDao).findById(eq(defaultUser.getId()));
        doReturn(Arrays.asList(microwaveSales, phoneSales)).when(salesDao).getAllEntities();
        Assertions.assertEquals(5, salesDao.getCalculatedPercentageForUser(defaultUser.getId()));
    }

    @Test
    public void calculationDiscountUserWithProduct() {
        defaultUser.setCompany("Apple");
        defaultUser.setLegalEntity(false);
        microwaveSales.setPaidAmount(BigDecimal.valueOf(10000));
        phoneSales.setPaidAmount(BigDecimal.valueOf(100));
        doReturn(defaultUser).when(userDao).findById(eq(defaultUser.getId()));
        doReturn(Arrays.asList(microwaveSales, phoneSales)).when(salesDao).getAllEntities();
        Assertions.assertEquals(5, salesDao.getCalculatedPercentageForUser(defaultUser.getId()));
    }

    @Test
    public void calculationDiscountUserWithProductAndCompany() {
        defaultUser.setCompany("Maximum");
        defaultUser.setLegalEntity(false);
        microwaveSales.setPaidAmount(BigDecimal.valueOf(10000));
        phoneSales.setPaidAmount(BigDecimal.valueOf(100));
        doReturn(defaultUser).when(userDao).findById(eq(defaultUser.getId()));
        doReturn(Arrays.asList(microwaveSales, phoneSales)).when(salesDao).getAllEntities();
        Assertions.assertEquals(10, salesDao.getCalculatedPercentageForUser(defaultUser.getId()));
    }

    @Test
    public void calculationDiscountUserWithLegalEntity() {
        defaultUser.setCompany("Apple");
        defaultUser.setLegalEntity(true);
        microwaveSales.setPaidAmount(BigDecimal.valueOf(100));
        phoneSales.setPaidAmount(BigDecimal.valueOf(100));
        doReturn(defaultUser).when(userDao).findById(eq(defaultUser.getId()));
        doReturn(Arrays.asList(microwaveSales, phoneSales)).when(salesDao).getAllEntities();
        Assertions.assertEquals(3, salesDao.getCalculatedPercentageForUser(defaultUser.getId()));
    }

    @Test
    public void calculationDiscountUserWithProductLegalEntityAndCompany() {
        defaultUser.setCompany("Maximum");
        defaultUser.setLegalEntity(true);
        microwaveSales.setPaidAmount(BigDecimal.valueOf(9900));
        phoneSales.setPaidAmount(BigDecimal.valueOf(100));
        doReturn(defaultUser).when(userDao).findById(eq(defaultUser.getId()));
        doReturn(Arrays.asList(microwaveSales, phoneSales)).when(salesDao).getAllEntities();
        Assertions.assertEquals(13, salesDao.getCalculatedPercentageForUser(defaultUser.getId()));
    }
}