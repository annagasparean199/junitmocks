package org.example.dbdatacreation.csventityreaders;

import org.example.dao.impl.ProductDao;
import org.example.entities.Delivery;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DeliveryCSVReader  implements GenericCSVReader<Delivery>{

    ProductDao productDao = new ProductDao();
    private static final String PATTERN_DATE_FORMAT = "M/d/yyyy";

    public List<Delivery> readFromCSV(String filePath) {
        List<Delivery> deliveries = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            br.readLine();

            while ((line = br.readLine()) != null) {
                String[] data = line.split("\t");
                Delivery delivery = createDeliveryFromCSV(data[0].split(","));
                deliveries.add(delivery);
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

        return deliveries;
    }

    private Delivery createDeliveryFromCSV(String[] data) throws ParseException {
        Delivery.DeliveryBuilder deliveryBuilder = Delivery.builder();

        deliveryBuilder.id(Long.parseLong(data[0].trim()))
                .deliveryDate(parseDate(data[1].trim()))
                .quantity(Integer.parseInt(data[2].trim()))
                .product(productDao.findById(Long.parseLong(data[3].trim())));

        return deliveryBuilder.build();
    }

    private Date parseDate(String dateString) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat(PATTERN_DATE_FORMAT);
        return dateFormat.parse(dateString);
    }
}