//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import static org.junit.jupiter.api.AssertEquals.assertEquals;
//
//public class DeliveryTests {
//
//    @AfterEach
//    public void clear(){
//        DeliveryService.deleteAllRecords();
//        SalesService.deleteAllRecords();
//        CreditService.deleteAllCredits();
//        ProductService.deleteAllProducts();
//    }
//
//    @Test
//    public void testCalculateStoreLossForMonth(){
//        Product product = new Product(color, quantity, name, price = 10, inStock);
//        Product secproduct = new Product(color, quantity, name, price = 50, inStock);
//        Delivery.createNewDelivery(date = "/01/", product.getId, quantity = 1);
//        Delivery.createNewDelivery(date = "/01/", secproduct.getId, quantity = 1);
//        assertEquals(Delivery.getStoreLossAmountForMonth("Month"), 60);
//    }
//
//    @Test
//    public void testCalculateStoreProfitForMonth(){
//        Sales.createNewSaleRecord(prod_id, user_id, purchase_date= "/12/", quantity, paid_amount = 100, inCredit = true);
//        Sales.createNewSaleRecord(prod_id, user_id, purchase_date= "/01/", quantity, paid_amount = 1000, inCredit = false);
//        Credit.createNewCredit(date = "/01/", pricePerMonth = 100, sales_id = 1, months = 3);
//        assertEquals(Delivery.getStoreProfitPerMonth("Month"), 1000+Credit.getPricePerMonthById(1));
//    }
//
//    @Test
//    public void assertSalesBalance(){
//        Product product = new Product(color, quantity, name, price = 1000, inStock);
//        Product secproduct = new Product(color, quantity, name, price = 500, inStock);
//        Sales.createNewSaleRecord(product.getId, user_id, purchase_date= "/12/", quantity, paid_amount = 100, inCredit = true);
//        Sales.createNewSaleRecord(secproduct.getId, user_id, purchase_date= "/01/", quantity, paid_amount = 500, inCredit = false);
//        Credit.createNewCredit(pricePerMonth = 100, Months-9, sale_id = 1, payment_date = "/01/");
//        Delivery.createNewDelivery(date = "/01/", product.getId, quantity = 1);
//        Delivery.createNewDelivery(date = "/01/", secproduct.getId, quantity = 1);
//        int result = Delivery.getStoreProfitPerMonth("Month")-Delivery.getStoreLossAmountForMonth("Month");// ? or int result = getSalesBalancePerMonth("Month");
//        assertEquals(result, -900);
//    }
//}
