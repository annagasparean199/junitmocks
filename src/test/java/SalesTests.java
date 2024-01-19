import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.Year;

public class SalesTests {

    @Test
    public void testCalculateFullPriceFromPaidAmount(){
        Product product = new Product(color, quantity, name, price = 500, inStock);
        User user = new User(salary, UUID, legalentity, company, name);
        //company = "Maximum"
        Sales.createNewSaleRecord(secproduct.getId, user_id, purchase_date= "/01/", quantity, paid_amount = 485, inCredit = false); //id = 1 SERIAL
        Assert.assertEquals(Sales.calculateFullPrice(id=1), 500);
    }

    @Test
    public void testPercentageOfSalesPerYear(){
        Product product = new Product(color, quantity, name, price = 1000, inStock);
        Product secproduct = new Product(color, quantity, name, price = 500, inStock);
        Sales.createNewSaleRecord(product.getId, user_id, purchase_date= "/12/", quantity, paid_amount = 100, inCredit = true);
        Sales.createNewSaleRecord(secproduct.getId, user_id, purchase_date= "/01/", quantity, paid_amount = 500, inCredit = false);
        Credit.createNewCredit(pricePerMonth = 100, Months-9, sale_id = 1, payment_date = "/01/");
        Assert.assertEquals(Sales.calculatePercentageOfSales(Year.now()),50);
    }

//    @Test
//    public void testCompanyProfitPerMonth(){
//        Product product = new Product(color, quantity, name, price = 1000, inStock);
//       // Sales.createNewSaleRecord(product.getId, user_id, purchase_date= "/12/", quantity, paid_amount = 500, inCredit = true); //id1
//        Sales.createNewSaleRecord(secproduct.getId, user_id, purchase_date= "/01/", quantity, paid_amount = 1000, inCredit = false);
//        Credit.createNewCredit(pricePerMonth = 100, Months-5, sale_id = 1, payment_date = "/01/");
//        Assert.assertEquals(Sales.getCompanyProfitPerMonth("/01/"),1100);
//    }

    @Test
    public void testCalculatePriceWithDiscount(){
        Product product = new Product(color, quantity, name, price = 1000, inStock);
        User user = new User(salary, UUID, legalentity, company, name);
        //company = "Maximum"
        Assert.assertEquals(Sales.getPriceWithDiscount(user.getId, product.getPrice),970);
    }


    ////////////
    @Test
    public void calculationDefaultDiscount() {
        User user = new User(salary, UUID, legalentity, company, name);
        Integer userId = user.getId();
        Assert.assertEquals(0, Discount.getPercentageByUserId(userId));
    }

    @Test
    public void calculationDiscountUserWithThisCompany() {
        User user = new User(salary, UUID, legalentity, company, name);
        //company = "Maximum"
        Integer userId = user.getId();
        Assert.assertEquals(5, Discount.getPercentageByUserId(userId));
    }

    @Test
    public void calculationDiscountUserWithProduct() {
        User user = new User(salary, UUID, legalentity, company, name);
        Integer userId = user.getId();
        Product product = new Product(color, quantity, name, price = 11000, inStock);
        Sales.createPayment(product.getId, userId,date, quantity, paid_amount, inCredit = false);
        Assert.assertEquals(5, Discount.getPercentageByUserId(userId));
    }

    @Test
    public void calculationDiscountUserWithProductAndCompany() {
        User user = new User(salary, UUID, legalentity, company, name);
        //company = "Maximum"
        Product product = new Product(color, quantity, name, price = 11000, inStock);
        Sales.createPayment(product.getId, userId,date, quantity, paid_amount, inCredit = false);
        Integer userId = user.getId();
        Assert.assertEquals(10 , Discount.getPercentageByUserId(userId));
    }

    @Test
    public void calculationDiscountUserWithLegalEntity() {
        User user = new User(salary, UUID, legalentity, company, name);
        //legalentity = true
        Integer userId = user.getId();
        Assert.assertEquals(3 , Discount.getPercentageByUserId(userId));
    }

    @Test
    public void calculationDiscountUserWithProductLegalEntityAndCompany() {
        User user = new User(salary, UUID, legalentity, company, name);
        //company = "Maximum"
        //legalentity = true
        Product product = new Product(color, quantity, name, price = 11000, inStock);
        Sales.createPayment(product.getId, userId,date, quantity, paid_amount, inCredit = false);
        Integer userId = user.getId();
        Assert.assertEquals(13 , Discount.getPercentageByUserId(userId));
    }
}