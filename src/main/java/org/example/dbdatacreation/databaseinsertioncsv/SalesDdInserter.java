package org.example.dbdatacreation.databaseinsertioncsv;

import org.example.dbdatacreation.csvdatavalidation.SalesDataValidator;
import org.example.dbdatacreation.csventityreaders.SalesCSVReader;
import org.example.dao.impl.SalesDao;
import org.example.entities.Sales;

import java.util.List;

public class SalesDdInserter implements GenericDbInserter{

    private static final String CSV_SALES_PATH = "src/test/resources/salesdata.csv";
    SalesCSVReader csvReader = new SalesCSVReader();
    SalesDataValidator dataValidator = new SalesDataValidator();
    SalesDao salesDao = new SalesDao();

    public void processCreatedDataAndInsertIntoDB() {

        List<Sales> entities = csvReader.readFromCSV(CSV_SALES_PATH);

        for (Sales entity : entities) {
            if (dataValidator.isValid(entity)) {
                salesDao.save(entity);
            }
        }
    }
}
