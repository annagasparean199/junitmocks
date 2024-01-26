package org.example.dbdatacreation.databaseinsertioncsv;

import org.example.dbdatacreation.csvdatavalidation.ProductDataValidator;
import org.example.dbdatacreation.csventityreaders.ProductCSVReader;
import org.example.dao.impl.ProductDao;
import org.example.entities.Product;

import java.util.List;

public class ProductDbInserter implements GenericDbInserter{

    private static final String CSV_PRODUCT_PATH = "src/test/resources/productdata.csv";
    ProductCSVReader csvReader = new ProductCSVReader();
    ProductDataValidator dataValidator = new ProductDataValidator();
    ProductDao productDao = new ProductDao();

    public void processCreatedDataAndInsertIntoDB() {

        List<Product> entities = csvReader.readFromCSV(CSV_PRODUCT_PATH);

        for (Product entity : entities) {
            if (dataValidator.isValid(entity)) {
                productDao.save(entity);
            }
        }
    }
}