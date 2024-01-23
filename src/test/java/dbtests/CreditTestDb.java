package dbtests;


import lombok.extern.log4j.Log4j;
import org.example.DAO.DAOImpl.*;
import org.example.entity.Credit;
import org.example.entity.Sales;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static utils.Utils.*;

@Log4j
public class CreditTestDb {


    @BeforeAll
    public static void create() {
        createUtils();
    }

    public static Sales sale = new Sales(2L, product2, user, new Date(), 3, new BigDecimal("90.00"), true);

    @Test
    public void testFindByIdEntityFound() {
        CreditDao creditDao = new CreditDao();
        Long id = credit.getId();

        Credit result = creditDao.findById(id, Credit.class);
        log.info("Record from database : " + result);
        assertNotNull(result);
        Assertions.assertEquals(credit, result);
    }

    @Test
    public void testFindAllEntities() {
        CreditDao creditDao = new CreditDao();
        List<Credit> result = creditDao.getAllEntities(Credit.class);
        assertNotNull(result);
        Assertions.assertEquals(Arrays.asList(credit, credit2), result);
    }

    @Test
    public void testDeleteCreditEntity() {
        CreditDao creditDao = new CreditDao();
        log.info(creditDao.findById(credit.getId(), Credit.class));
        creditDao.delete(credit);
        Assertions.assertNull(creditDao.findById(credit.getId(), Credit.class));
        log.info("Object " + credit + " is deleted");
        creditDao.save(credit);
    }


    @Test
    public void testDeleteById() {
        CreditDao creditDao = new CreditDao();
        log.info(creditDao.findById(credit.getId(), Credit.class));
        creditDao.deleteById(credit.getId());
        Assertions.assertNull(creditDao.findById(credit.getId(), Credit.class));
        log.info("Object " + credit + " is deleted");
        creditDao.save(credit);
    }

    @Test
    public void testSaveCreditEntity() {
        CreditDao creditDao = new CreditDao();
        List<Credit> credits = creditDao.getAllEntities(Credit.class);
        for (Credit credit : credits) {
            creditDao.delete(credit);
        }
        SalesDao salesDao = new SalesDao();
        salesDao.delete(sale);
        salesDao.save(sale);
        Assertions.assertTrue(creditDao.getAllEntities(Credit.class).isEmpty());
        log.info("Record in not present in database");
        Credit credit = new Credit(3L, new BigDecimal("30.00"), 12, sale, new Date());
        Credit savedCredit = creditDao.save(credit);
        log.info(savedCredit + " is created");
        assertEquals(credit, savedCredit);
        assertNotNull(creditDao.findById(savedCredit.getId(), Credit.class));
        assertEquals(credit, creditDao.findById(savedCredit.getId(), Credit.class));
    }

    @Test
    public void updateDeliveryEntity() {
        CreditDao creditDao = new CreditDao();
        Credit data = new Credit(3L, new BigDecimal("30.0"), 12, sale, new Date());
        Credit credit = creditDao.save(data);
        Assertions.assertNotNull(creditDao.findById(credit.getId(), Credit.class));
        Assertions.assertEquals(creditDao.findById(credit.getId(), Credit.class).getPricePerMonth().doubleValue(), 30.0);
        log.info("Price per month for credit in database is : " + creditDao.findById(credit.getId(), Credit.class).getPricePerMonth().doubleValue());
        credit.setPricePerMonth(BigDecimal.valueOf(60.0));
        Assertions.assertEquals(creditDao.findById(credit.getId(), Credit.class).getPricePerMonth().doubleValue(), 30.0);
        log.info("Price per month for credit is changed locally to 60, but in database: "
                + creditDao.findById(credit.getId(), Credit.class).getPricePerMonth().doubleValue());
        creditDao.updateEntity(credit);
        Assertions.assertEquals(60.0, creditDao.findById(credit.getId(), Credit.class).getPricePerMonth().doubleValue());
        log.info("Price per month for credit in database is : " + creditDao.findById(credit.getId(), Credit.class).getPricePerMonth().doubleValue());
    }
}
