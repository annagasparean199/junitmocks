package org.example.dbdatacreation.csvdatavalidation;

import org.example.entities.Product;
import org.example.entities.Sales;
import org.example.entities.User;

import java.math.BigDecimal;
import java.util.Date;

public class SalesDataValidator implements GenericDataValidator<Sales> {

    public boolean isValid(Sales sales) {
        return isValidId(sales.getId()) &&
                isValidProduct(sales.getProduct()) &&
                isValidUser(sales.getUser()) &&
                isValidQuantity(sales.getQuantity()) &&
                isValidPaidAmount(sales.getPaidAmount()) &&
                isValidPurchaseDate(sales.getPurchaseDate());
    }

    private boolean isValidId(Long id) {
        return id != null && id > 0;
    }

    private boolean isValidProduct(Product product) {
        return product != null && product.getId() != null && product.getId() > 0;
    }

    private boolean isValidUser(User user) {
        return user != null && user.getId() != null && user.getId() > 0;
    }

    private boolean isValidQuantity(Integer quantity) {
        return quantity != null && quantity > 0;
    }

    private boolean isValidPaidAmount(BigDecimal paidAmount) {
        return paidAmount != null && paidAmount.compareTo(BigDecimal.ZERO) > 0;
    }

    private boolean isValidPurchaseDate(Date purchaseDate) {
        return purchaseDate != null && !purchaseDate.after(new Date());
    }
}