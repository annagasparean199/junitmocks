package org.example.dbdatacreation.csvdatavalidation;
import org.example.entities.Delivery;
import org.example.entities.Product;

import java.util.Date;

public class DeliveryDataValidator implements GenericDataValidator<Delivery> {

    private static final String REGEX_NUMBERS_AND_DOT = "\\d+(\\.\\d+)?";

    public boolean isValid(Delivery delivery) {
        return isValidId(delivery.getId()) &&
                isValidDeliveryDate(delivery.getDeliveryDate()) &&
                isValidQuantity(delivery.getQuantity()) &&
                isValidProduct(delivery.getProduct());
    }

    private boolean isValidId(Long id) {
        return id != null && id > 0;
    }

    private boolean isValidDeliveryDate(Date deliveryDate) {
        return deliveryDate != null && !deliveryDate.after(new Date());
    }

    private boolean isValidQuantity(Integer quantity) {
            if (quantity != null && quantity >= 0) {
                String quantityValue = String.valueOf(quantity);
                return quantityValue.matches(REGEX_NUMBERS_AND_DOT);
            }
            return false;
    }

    private boolean isValidProduct(Product product) {
        return product != null && product.getId() != null && product.getId() > 0;
    }
}