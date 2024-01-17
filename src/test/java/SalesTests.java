//import org.junit.jupiter.api.Test;
//
//public class SalesTests {
//
//    @Test
//    public void testCalculateFullPriceFromPaidAmount(){
//        Product product = new Product(color, quantity, name, price = 500, inStock);
//        User user = new User(salary, UUID, legalentity, company, name);
//        //company = "Maximum"
//        Sales.createNewSaleRecord(secproduct.getId, user_id, purchase_date= "/01/", quantity, paid_amount = 485, inCredit = false); //id = 1 SERIAL
//        Assert.assertEquals(Sales.calculateFullPrice(id=1), 500);
//    }
//
//    @Test
//    public void testPercentageOfSales(){
//        Product product = new Product(color, quantity, name, price = 1000, inStock);
//        Product secproduct = new Product(color, quantity, name, price = 500, inStock);
//        Sales.createNewSaleRecord(product.getId, user_id, purchase_date= "/12/", quantity, paid_amount = 100, inCredit = true);
//        Sales.createNewSaleRecord(secproduct.getId, user_id, purchase_date= "/01/", quantity, paid_amount = 500, inCredit = false);
//        Credit.createNewCredit(pricePerMonth = 100, Months-9, sale_id = 1, payment_date = "/01/");
//        Assert.assertEquals(Sales.calculatePercentageOfSales(),50);
//    }
//
//    @Test
//    public void testCompanyProfitPerMonth(){
//        Product product = new Product(color, quantity, name, price = 1000, inStock);
//       // Sales.createNewSaleRecord(product.getId, user_id, purchase_date= "/12/", quantity, paid_amount = 500, inCredit = true); //id1
//        Sales.createNewSaleRecord(secproduct.getId, user_id, purchase_date= "/01/", quantity, paid_amount = 1000, inCredit = false);
//        Credit.createNewCredit(pricePerMonth = 100, Months-5, sale_id = 1, payment_date = "/01/");
//        Assert.assertEquals(Sales.getCompanyProfitPerMonth("/01/"),1100);
//    }
//
//    @Test
//    public void testCalculatePriceWithDiscount(){
//        Product product = new Product(color, quantity, name, price = 1000, inStock);
//        User user = new User(salary, UUID, legalentity, company, name);
//        //company = "Maximum"
//        Assert.assertEquals(Sales.getPriceWithDiscount(user.getId, product.getPrice),970);
//    }
//}