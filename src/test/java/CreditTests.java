import lombok.extern.log4j.Log4j;

import org.example.DAO.DAOImpl.CreditDao;
import org.example.entity.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Spy;

import java.util.*;

import static utils.Utils.getFirstRecord;
import static utils.Utils.getRecord;

@Log4j
public class CreditTests {

    @Spy
    CreditDao creditDao;

    @Test
    public void testTotalPriceForOneCredit() {
        Double result = creditDao.getTotalPriceForOneCredit(product.getId(), user.getId());
        log.info("Testing amount for one credit: \n" + user + "\n" + product);
        log.info("Actual " + result + " Expected 12000.0");
        Assertions.assertEquals(result, 12000.0);
    }

    @Test
    public void testTotalPriceForPersonCreditsPerMonth() {
        Assertions.assertEquals(CreditDao.getCreditDaoInstance().getTotalPriceForPersonCreditsPerMonth(getRecord(), 1), 2000);
    }
    @Test
    public void testTotalAmountForRemainCredits() {
        Assertions.assertEquals(13000, CreditDao.getCreditDaoInstance().getTotalAmountForRemainCredits(getRecord()));//month = 01.01
    }

    @Test
    public void testTotalAmountForPayedCredits() {
        Assertions.assertEquals(0, CreditDao.getCreditDaoInstance().getTotalAmountForPayedCredits(getRecord()));//month = 01.01
    }
}

//    //        Product product = new Product(color, quantity, name, price = 1000, inStock);
////        Sales.createNewSaleRecord(product.getId, user_id, purchase_date= "/12/", quantity, paid_amount = 500, inCredit = true); //id1
////        Credit.createNewCredit(pricePerMonth = 100, Months-5, sale_id = 1, payment_date = "/01/");

//    @Test
//    public void testTotalPriceForPersonCreditsPerMonth(){
//        Product product = new Product(color, quantity, name, price = 1000, inStock);
//        Product secproduct = new Product(color, quantity, name, price = 2000, inStock);
//        Sales.createNewSaleRecord(product.getId, user_id, purchase_date= "/12/", quantity, paid_amount = 500, inCredit = true); //id1
//        Sales.createNewSaleRecord(product.getId, user_id, purchase_date= "/12/", quantity, paid_amount = 500, inCredit = true); //id1
//        Credit.createNewCredit(pricePerMonth = 100, Months-5, sale_id = 1, payment_date = "12/01/");
//        Credit.createNewCredit(pricePerMonth = 500, Months-3, sale_id = 1, payment_date = "13/01/");
//        Assert.assertEquals(CreditDao.getCreditDaoInstance().getTotalPriceForPersonCreditsPerMonth( ,1).calculateTotalPriceForPersonCreditsPerMonth(user_id,month),600);//month = 01
//    }
//
//    @Test
//    public void testTotalAmountForRemainCredits(){
//        Product product = new Product(color, quantity, name, price = 1000, inStock);
//        Product secproduct = new Product(color, quantity, name, price = 2000, inStock);
//        Sales.createNewSaleRecord(product.getId, user_id, purchase_date= "/12/", quantity, paid_amount = 500, inCredit = true); //id1
//        Sales.createNewSaleRecord(product.getId, user_id, purchase_date= "/12/", quantity, paid_amount = 500, inCredit = true); //id1
//        Credit.createNewCredit(pricePerMonth = 100, Months-5, sale_id = 1, payment_date = "12/01/");
//        Credit.createNewCredit(pricePerMonth = 500, Months-3, sale_id = 1, payment_date = "13/01/");
//        Assert.assertEquals(Credit.calculateTotalAmountForRemainCredits(user_id,frommonth),2000);//month = 01.01
//        Assert.assertEquals(Credit.calculateTotalAmountForRemainCredits(user_id,frommonth),1400);//month = 01.02
//    }
