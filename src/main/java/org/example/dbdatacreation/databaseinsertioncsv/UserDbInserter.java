package org.example.dbdatacreation.databaseinsertioncsv;

import org.example.dao.impl.UserDao;
import org.example.dbdatacreation.csvdatavalidation.UserDataValidator;
import org.example.dbdatacreation.csventityreaders.UserCSVReader;
import org.example.entities.User;

import java.util.List;

public class UserDbInserter implements GenericDbInserter{

    private static final String CSV_USER_PATH = "src/test/resources/userdata.csv";
    UserCSVReader csvReader = new UserCSVReader();
    UserDataValidator dataValidator = new UserDataValidator();
    UserDao userDao = new UserDao();

    public int getPriorityOfInsertionInDatabase(){
        return 2;
    }

    public void processCreatedDataAndInsertIntoDB() {

        List<User> entities = csvReader.readFromCSV(CSV_USER_PATH);

        for (User entity : entities) {
            if (dataValidator.isValid(entity)) {
                userDao.save(entity);
            }
        }
    }
}