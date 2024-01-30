package org.example.dbdatacreation.scannerdatacreation;

import org.example.dao.impl.ProductDao;
import org.example.dbdatacreation.csvdatavalidation.ProductDataValidator;
import org.example.dbdatacreation.databaseinsertioncsv.GenericDbInserter;
import org.example.entities.Delivery;
import org.example.entities.Product;
import org.example.entities.Sales;

import java.util.ArrayList;
import java.util.List;

import static org.example.dbdatacreation.scannerdatacreation.utils.BuilderUtils.SCANNER;

public class ConsoleProductBuilder implements GenericDbInserter {

    private static final List<Delivery> DELIVERIES = new ArrayList<>();
    private static final List<Sales> SALES = new ArrayList<>();

    @Override
    public void processCreatedDataAndInsertIntoDB() {
        ProductDao productDao = new ProductDao();
        ProductDataValidator productDataValidator = new ProductDataValidator();
        Product product = createProductFromConsole();
        if(productDataValidator.isValid(product)) {
            productDao.save(product);
        }
    }

    @Override
    public int getPriorityOfInsertionInDatabase() {
        return 1;
    }

    public static Product createProductFromConsole() {
        Long productId = getProductIDFromConsole();
        String color = getProductColorFromConsole();
        Integer quantity = getProductQuantityFromConsole();
        String name = getProductNameFromConsole();
        Double price = getProductPriceFromConsole();
        Boolean inStock = isProductInStockFromConsole();

        return Product.builder()
                .id(productId)
                .color(color)
                .quantity(quantity)
                .name(name)
                .price(price)
                .inStock(inStock)
                .deliveries(DELIVERIES)
                .sales(SALES)
                .build();
    }

    private static Long getProductIDFromConsole() {
        System.out.print("Enter Product ID (Long): ");
        Long productId = SCANNER.nextLong();
        SCANNER.nextLine();
        return productId;
    }

    private static String getProductColorFromConsole() {
        System.out.print("Enter Product Color: ");
        return SCANNER.nextLine();
    }

    private static Integer getProductQuantityFromConsole() {
        System.out.print("Enter Product Quantity: ");
        Integer quantity = SCANNER.nextInt();
        SCANNER.nextLine();
        return quantity;
    }

    private static String getProductNameFromConsole() {
        System.out.print("Enter Product Name: ");
        return SCANNER.nextLine();
    }

    private static Double getProductPriceFromConsole() {
        System.out.print("Enter Product Price: ");
        Double price = SCANNER.nextDouble();
        SCANNER.nextLine();
        return price;
    }

    private static Boolean isProductInStockFromConsole() {
        System.out.print("Is the Product in stock? (true/false): ");
        Boolean inStock = SCANNER.nextBoolean();
        SCANNER.nextLine();
        return inStock;
    }
}