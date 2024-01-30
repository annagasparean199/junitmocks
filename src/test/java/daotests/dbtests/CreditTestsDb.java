package daotests.dbtests;

import lombok.extern.log4j.Log4j;
import org.example.entities.Credit;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import utils.TestDataFromCSV;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static utils.Utils.creditDao;
import static utils.Utils.secondaryPhoneSale;

@Log4j
public class CreditTestsDb {


    @BeforeAll
    public static void create() {
        TestDataFromCSV testDataFromCSV = new TestDataFromCSV();
        testDataFromCSV.processDaosInOrderAndSaveObjectsInDb();
    }

    @Test
    public void testFindByIdEntityFound() {
        Long id = 1L;
        Credit result = creditDao.findById(id);
        log.info("Record from database : " + result);
        assertNotNull(result);
    }

    @Test
    public void testFindAllEntities() {
        List<Credit> result = creditDao.getAllEntities();
        log.info("Record from database : " + result);
        assertNotNull(result);
    }

    @Test
    public void testDeleteCreditEntity() {
        log.info(creditDao.findById(1L));
        creditDao.delete(creditDao.findById(1L));
        Assertions.assertNull(creditDao.findById(1L));
        log.info("Object is deleted");
       // creditDao.save(microwaveSalesCredit);
    }

    @Test
    public void testDeleteById() {
        log.info(creditDao.findById(2L));
        creditDao.deleteById(2L);
        Assertions.assertNull(creditDao.findById(2L));
        log.info("Object is deleted");
      //  creditDao.save(microwaveSalesCredit);
    }

    @Test
    public void testSaveCreditEntity() {
        List<Credit> credits = creditDao.getAllEntities();
        for (Credit credit : credits) {
            creditDao.delete(credit);
        }
        //SalesDao salesDao = new SalesDao();
        //salesDao.delete(secondaryPhoneSale);
        //salesDao.save(secondaryPhoneSale);
        Assertions.assertTrue(creditDao.getAllEntities().isEmpty());
        log.info("Records are not present in database");
        Credit credit = new Credit(3L, new BigDecimal("30.00"), 12, secondaryPhoneSale, new Date());
        Credit savedCredit = creditDao.save(credit);
        log.info(savedCredit + " is created");
        assertEquals(credit, savedCredit);
        assertNotNull(creditDao.findById(savedCredit.getId()));
        assertEquals(credit, creditDao.findById(savedCredit.getId()));
    }

    @Test
    public void updateDeliveryEntity() {
        Credit data = new Credit(3L, new BigDecimal("30.0"), 12, secondaryPhoneSale, new Date());
        Credit credit = creditDao.save(data);
        Assertions.assertNotNull(creditDao.findById(credit.getId()));
        Assertions.assertEquals(creditDao.findById(credit.getId()).getPricePerMonth().doubleValue(), 30.0);
        log.info("Price per month for credit in database is : " + creditDao.findById(credit.getId()).getPricePerMonth().doubleValue());
        credit.setPricePerMonth(BigDecimal.valueOf(60.0));
        Assertions.assertEquals(creditDao.findById(credit.getId()).getPricePerMonth().doubleValue(), 30.0);
        log.info("Price per month for credit is changed locally to 60, but in database: " + creditDao.findById(credit.getId()).getPricePerMonth().doubleValue());
        creditDao.updateEntity(credit);
        Assertions.assertEquals(60.0, creditDao.findById(credit.getId()).getPricePerMonth().doubleValue());
        log.info("Price per month for credit in database is : " + creditDao.findById(credit.getId()).getPricePerMonth().doubleValue());
    }
}