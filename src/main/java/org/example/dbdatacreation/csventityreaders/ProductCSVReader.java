package org.example.dbdatacreation.csventityreaders;

import org.example.entities.Product;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ProductCSVReader  implements GenericCSVReader<Product>{

    public List<Product> readFromCSV(String filePath) {
        List<Product> products = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            br.readLine();

            while ((line = br.readLine()) != null) {
                String[] data = line.split("\t");
                Product product = createProductFromCSV(data[0].split(","));
                products.add(product);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return products;
    }

    private Product createProductFromCSV(String[] data) {
        Product.ProductBuilder productBuilder = Product.builder();

        productBuilder.id(Long.parseLong(data[0].trim()))
                .color(data[1].trim())
                .quantity(Integer.parseInt(data[2].trim()))
                .name(data[3].trim())
                .price(Double.parseDouble(data[4].trim()))
                .inStock(Boolean.parseBoolean(data[5].trim()));

        return productBuilder.build();
    }
}