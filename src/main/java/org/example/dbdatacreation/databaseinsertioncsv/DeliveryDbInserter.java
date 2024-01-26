package org.example.dbdatacreation.databaseinsertioncsv;

import org.example.dbdatacreation.csvdatavalidation.DeliveryDataValidator;
import org.example.dbdatacreation.csventityreaders.DeliveryCSVReader;
import org.example.dao.impl.DeliveryDao;
import org.example.entities.Delivery;

import java.util.List;

public class DeliveryDbInserter implements GenericDbInserter{

    private static final String CSV_DELIVERY_PATH = "src/test/resources/deliverydata.csv";
    DeliveryCSVReader csvReader = new DeliveryCSVReader();
    DeliveryDataValidator dataValidator = new DeliveryDataValidator();
    DeliveryDao deliveryDao = new DeliveryDao();

    public int getPriorityOfInsertionInDatabase() {
        return 4;
    }

    public void processCreatedDataAndInsertIntoDB() {
        List<Delivery> entities = csvReader.readFromCSV(CSV_DELIVERY_PATH);

        for (Delivery entity : entities) {
            if (dataValidator.isValid(entity)) {
                deliveryDao.save(entity);
            }
        }
    }
}