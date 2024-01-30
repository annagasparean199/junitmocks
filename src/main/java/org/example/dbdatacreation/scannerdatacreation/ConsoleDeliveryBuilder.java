package org.example.dbdatacreation.scannerdatacreation;

import org.example.dao.impl.DeliveryDao;
import org.example.dao.impl.ProductDao;
import org.example.dbdatacreation.csvdatavalidation.DeliveryDataValidator;
import org.example.dbdatacreation.databaseinsertioncsv.GenericDbInserter;
import org.example.entities.Delivery;
import org.example.entities.Product;

import java.util.Date;

import static org.example.dbdatacreation.scannerdatacreation.utils.BuilderUtils.SCANNER;
import static org.example.dbdatacreation.scannerdatacreation.utils.BuilderUtils.parseDate;

public class ConsoleDeliveryBuilder implements GenericDbInserter {
    private static final ProductDao PRODUCT_DAO = new ProductDao();

    @Override
    public void processCreatedDataAndInsertIntoDB() {
        DeliveryDao deliveryDao  = new DeliveryDao();
        DeliveryDataValidator deliveryDataValidator = new DeliveryDataValidator();
        Delivery delivery = createDeliveryFromConsole();
        if(deliveryDataValidator.isValid(delivery)){
            deliveryDao.save(delivery);
        }
    }

    @Override
    public int getPriorityOfInsertionInDatabase() {
        return 4;
    }

    public static Delivery createDeliveryFromConsole() {
        Long deliveryId = getDeliveryIdFromConsole();
        SCANNER.nextLine();
        Product product = getProductFromConsole();
        Date deliveryDate = getDeliveryDateFromConsole();
        Integer quantity = getQuantityFromConsole();
        SCANNER.nextLine();

        return Delivery.builder()
                .id(deliveryId)
                .product(product)
                .deliveryDate(deliveryDate)
                .quantity(quantity)
                .build();
    }

    private static Long getDeliveryIdFromConsole() {
        System.out.print("Enter Delivery ID (Long): ");
        return SCANNER.nextLong();
    }

    private static Product getProductFromConsole() {
        System.out.print("Enter ProductId: ");
        Long productId = Long.parseLong(SCANNER.nextLine());
        return PRODUCT_DAO.findById(productId);
    }

    private static Date getDeliveryDateFromConsole() {
        System.out.print("Enter Delivery Date (yyyy-MM-dd): ");
        String dateString = SCANNER.nextLine();
        return parseDate(dateString);
    }

    private static Integer getQuantityFromConsole() {
        System.out.print("Enter Quantity: ");
        return SCANNER.nextInt();
    }
}