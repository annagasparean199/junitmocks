package org.example.dbdatacreation.csvdatavalidation;

import org.example.entities.Product;

public class ProductDataValidator implements GenericDataValidator<Product> {

    public boolean isValid(Product product) {
        return isValidId(product.getId()) &&
                isValidColor(product.getColor()) &&
                isValidQuantity(product.getQuantity()) &&
                isValidName(product.getName()) &&
                isValidPrice(product.getPrice()) &&
                isValidInStock(product.getInStock());
    }

    private boolean isValidId(Long id) {
        return id != null && id > 0;
    }

    private boolean isValidColor(String color) {
        return color != null && !color.trim().isEmpty();
    }

    private boolean isValidQuantity(Integer quantity) {
        return quantity != null && quantity >= 0;
    }

    private boolean isValidName(String name) {
        return name != null && !name.trim().isEmpty();
    }

    private boolean isValidPrice(Double price) {
        return price != null && price >= 0;
    }

    private boolean isValidInStock(Boolean inStock) {
        return inStock != null;
    }
}