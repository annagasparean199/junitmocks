//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.Test;
//
//public class CreditTests {
//
//    @AfterEach
//    public void clear(){
//
//    }
//
//    @Test
//    public void testTotalPriceForOneCredit(){
//        Product product = new Product(color, quantity, name, price = 1000, inStock);
//        Sales.createNewSaleRecord(product.getId, user_id, purchase_date= "/12/", quantity, paid_amount = 500, inCredit = true); //id1
//        Credit.createNewCredit(pricePerMonth = 100, Months-5, sale_id = 1, payment_date = "/01/");
//        Assert.assertEquals(Credit.calculateTotalPriceForOneCredit(credit_id, user_id),500);
//    }
//
//    @Test
//    public void testTotalPriceForPersonCreditsPerMonth(){
//        Product product = new Product(color, quantity, name, price = 1000, inStock);
//        Product secproduct = new Product(color, quantity, name, price = 2000, inStock);
//        Sales.createNewSaleRecord(product.getId, user_id, purchase_date= "/12/", quantity, paid_amount = 500, inCredit = true); //id1
//        Sales.createNewSaleRecord(product.getId, user_id, purchase_date= "/12/", quantity, paid_amount = 500, inCredit = true); //id1
//        Credit.createNewCredit(pricePerMonth = 100, Months-5, sale_id = 1, payment_date = "12/01/");
//        Credit.createNewCredit(pricePerMonth = 500, Months-3, sale_id = 1, payment_date = "13/01/");
//        Assert.assertEquals(Credit.calculateTotalPriceForPersonCreditsPerMonth(user_id,month),600);//month = 01
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
//}
