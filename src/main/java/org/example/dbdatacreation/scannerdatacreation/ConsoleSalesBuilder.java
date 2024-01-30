package org.example.dbdatacreation.scannerdatacreation;

import org.example.dao.impl.ProductDao;
import org.example.dao.impl.SalesDao;
import org.example.dao.impl.UserDao;
import org.example.dbdatacreation.csvdatavalidation.SalesDataValidator;
import org.example.dbdatacreation.databaseinsertioncsv.GenericDbInserter;
import org.example.entities.Credit;
import org.example.entities.Product;
import org.example.entities.Sales;
import org.example.entities.User;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.example.dbdatacreation.scannerdatacreation.utils.BuilderUtils.SCANNER;
import static org.example.dbdatacreation.scannerdatacreation.utils.BuilderUtils.parseDate;

public class ConsoleSalesBuilder implements GenericDbInserter {

   private static final ProductDao PRODUCT_DAO = new ProductDao();
   private static final UserDao USER_DAO = new UserDao();
   private static final List<Credit> CREDITS = new ArrayList<>();

   @Override
   public void processCreatedDataAndInsertIntoDB() {
      SalesDao salesDao = new SalesDao();
      SalesDataValidator salesDataValidator = new SalesDataValidator();
      Sales sales = createSalesFromConsole();
      if(salesDataValidator.isValid(sales)){
         salesDao.save(sales);
      }
   }

   @Override
   public int getPriorityOfInsertionInDatabase() {
      return 3;
   }

   public static Sales createSalesFromConsole() {
      Long salesId = getSalesIdFromConsole();
      Date purchaseDate = getPurchaseDateFromConsole();
      Integer quantity = getQuantityFromConsole();
      Product product = getProductFromConsole();
      User user = getUserFromConsole();
      BigDecimal paidAmount = getPaidAmountFromConsole();
      Boolean inCredit = isInCreditFromConsole();

      return Sales.builder()
              .id(salesId)
              .product(product)
              .user(user)
              .purchaseDate(purchaseDate)
              .quantity(quantity)
              .paidAmount(paidAmount)
              .inCredit(inCredit)
              .credits(CREDITS)
              .build();
   }

   private static Long getSalesIdFromConsole() {
      System.out.print("Enter Sales ID (Long): ");
      Long salesId = SCANNER.nextLong();
      SCANNER.nextLine();
      return salesId;
   }

   private static Date getPurchaseDateFromConsole() {
      System.out.print("Enter Purchase Date (yyyy-MM-dd): ");
      String dateString = SCANNER.nextLine();
      return parseDate(dateString);
   }

   private static Integer getQuantityFromConsole() {
      System.out.print("Enter Quantity: ");
      Integer quantity = SCANNER.nextInt();
      SCANNER.nextLine();
      return quantity;
   }

   private static Product getProductFromConsole() {
      System.out.print("Enter ProductId: ");
      Long productId = Long.parseLong(SCANNER.nextLine());
      return PRODUCT_DAO.findById(productId);
   }

   private static User getUserFromConsole() {
      System.out.print("Enter UserId: ");
      Long userId = Long.parseLong(SCANNER.nextLine());
      return USER_DAO.findById(userId);
   }

   private static BigDecimal getPaidAmountFromConsole() {
      System.out.print("Enter Paid Amount: ");
      BigDecimal paidAmount = SCANNER.nextBigDecimal();
      SCANNER.nextLine();
      return paidAmount;
   }

   private static Boolean isInCreditFromConsole() {
      System.out.print("Is the Sale in Credit? (true/false): ");
      Boolean inCredit = SCANNER.nextBoolean();
      SCANNER.nextLine();
      return inCredit;
   }
}