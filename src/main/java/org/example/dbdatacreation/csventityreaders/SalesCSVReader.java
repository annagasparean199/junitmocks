package org.example.dbdatacreation.csventityreaders;

import org.example.dao.impl.ProductDao;
import org.example.dao.impl.UserDao;
import org.example.entities.Sales;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SalesCSVReader implements GenericCSVReader<Sales>{

    ProductDao productDao = new ProductDao();
    UserDao userDao = new UserDao();

    private static final String PATTERN_DATE_FORMAT = "MM/dd/yyyy";

    public List<Sales> readFromCSV(String filePath) {
        List<Sales> salesList = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            br.readLine();

            while ((line = br.readLine()) != null) {
                String[] data = line.split("\t");
                Sales sales = createSalesFromCSV(data[0].split(","));
                salesList.add(sales);
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return salesList;
    }

    private Sales createSalesFromCSV(String[] data) throws ParseException {
        Sales.SalesBuilder salesBuilder = Sales.builder();

        salesBuilder.id(Long.parseLong(data[0].trim()))
                .product(productDao.findById(Long.parseLong(data[1].trim())))
                .user(userDao.findById(Long.parseLong(data[2].trim())))
                .purchaseDate(parseDate(data[3].trim()))
                .quantity(Integer.parseInt(data[4].trim()))
                .paidAmount(new BigDecimal(data[5].trim()))
                .inCredit(Boolean.parseBoolean(data[6].trim()));

        return salesBuilder.build();
    }

    private Date parseDate(String dateString) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat(PATTERN_DATE_FORMAT);
        return dateFormat.parse(dateString);
    }
}