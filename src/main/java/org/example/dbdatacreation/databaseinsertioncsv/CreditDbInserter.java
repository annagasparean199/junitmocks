package org.example.dbdatacreation.databaseinsertioncsv;

import org.example.dao.impl.CreditDao;
import org.example.dbdatacreation.csvdatavalidation.CreditDataValidator;
import org.example.dbdatacreation.csventityreaders.CreditCSVReader;
import org.example.entities.Credit;

import java.util.List;

public class CreditDbInserter implements GenericDbInserter{

    private static final String CSV_CREDITS_PATH = "src/test/resources/creditdata.csv";
    CreditCSVReader csvReader = new CreditCSVReader();
    CreditDataValidator dataValidator = new CreditDataValidator();
    CreditDao creditDao = new CreditDao();

    public void processCreatedDataAndInsertIntoDB() {

        List<Credit> entities = csvReader.readFromCSV(CSV_CREDITS_PATH);

        for (Credit entity : entities) {
            if (dataValidator.isValid(entity)) {
                creditDao.save(entity);
            }
        }
    }
}