package utils;

import org.example.dbdatacreation.databaseinsertioncsv.CreditDbInserter;
import org.example.dbdatacreation.databaseinsertioncsv.DeliveryDbInserter;
import org.example.dbdatacreation.databaseinsertioncsv.GenericDbInserter;
import org.example.dbdatacreation.databaseinsertioncsv.ProductDbInserter;
import org.example.dbdatacreation.databaseinsertioncsv.SalesDbInserter;
import org.example.dbdatacreation.databaseinsertioncsv.UserDbInserter;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class TestDataFromCSV {

    private final ProductDbInserter productDbInserter = new ProductDbInserter();
    private final UserDbInserter userDbInserter = new UserDbInserter();
    private final DeliveryDbInserter deliveryDbInserter = new DeliveryDbInserter();
    private final CreditDbInserter creditDbInserter = new CreditDbInserter();
    private final SalesDbInserter salesDbInserter= new SalesDbInserter();

        public void processDaosInOrderAndSaveObjectsInDb() {
            List<GenericDbInserter> daos = Arrays.asList(productDbInserter, userDbInserter, deliveryDbInserter, creditDbInserter, salesDbInserter);
            daos.sort(Comparator.comparingInt(GenericDbInserter::getPriorityOfInsertionInDatabase));

            for (GenericDbInserter dao : daos) {
                dao.processCreatedDataAndInsertIntoDB();
            }
    }
}