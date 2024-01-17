//import org.junit.jupiter.api.Test;
//
//
//
//public class DiscountTests {
//
//    @Test
//    public void createUserWithDefaultDiscount() {
//        User user = new User(salary, UUID, legalentity, company, name);
//        Integer userId = user.getId();
//        Assert.assertEquals(0, Discount.getPercentageByUserId(userId));
//    }
//
//    @Test
//    public void createUserWithThisCompany() {
//        User user = new User(salary, UUID, legalentity, company, name);
//        //company = "Maximum"
//        Integer userId = user.getId();
//        Assert.assertEquals(5, Discount.getPercentageByUserId(userId));
//    }
//
//    @Test
//    public void createUserWithProduct() {
//        User user = new User(salary, UUID, legalentity, company, name);
//        Integer userId = user.getId();
//        Product product = new Product(color, quantity, name, price = 11000, inStock);
//        Sales.createPayment(product.getId, userId,date, quantity, paid_amount, inCredit = false);
//        Assert.assertEquals(5, Discount.getPercentageByUserId(userId));
//    }
//
//    @Test
//    public void createUserWithProductAndCompany() {
//        User user = new User(salary, UUID, legalentity, company, name);
//        //company = "Maximum"
//        Product product = new Product(color, quantity, name, price = 11000, inStock);
//        Sales.createPayment(product.getId, userId,date, quantity, paid_amount, inCredit = false);
//        Integer userId = user.getId();
//        Assert.assertEquals(10 , Discount.getPercentageByUserId(userId));
//    }
//
//    @Test
//    public void createUserWithLegalEntity() {
//        User user = new User(salary, UUID, legalentity, company, name);
//        //legalentity = true
//        Integer userId = user.getId();
//        Assert.assertEquals(3 , Discount.getPercentageByUserId(userId));
//    }
//
//    @Test
//    public void createUserWithProductLegalEntityAndCompany() {
//        User user = new User(salary, UUID, legalentity, company, name);
//        //company = "Maximum"
//        //legalentity = true
//        Product product = new Product(color, quantity, name, price = 11000, inStock);
//        Sales.createPayment(product.getId, userId,date, quantity, paid_amount, inCredit = false);
//        Integer userId = user.getId();
//        Assert.assertEquals(13 , Discount.getPercentageByUserId(userId));
//    }
//}