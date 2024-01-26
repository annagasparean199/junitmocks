package utils;

import org.example.dao.GenericDao;
import org.example.dao.impl.CreditDao;
import org.example.dao.impl.DeliveryDao;
import org.example.dao.impl.ProductDao;
import org.example.dao.impl.SalesDao;
import org.example.dao.impl.UserDao;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class TestDataFromCSV {

    private final ProductDao productDao = new ProductDao();
    private final UserDao userDao = new UserDao();
    private final DeliveryDao deliveryDao = new DeliveryDao();
    private final CreditDao creditDao = new CreditDao();
    private final SalesDao salesDao = new SalesDao();

        public void processDaosInOrder() {
            List<GenericDao> daos = Arrays.asList(productDao, userDao, salesDao, deliveryDao, creditDao);
            daos.sort(Comparator.comparingInt(GenericDao::getPriorityOfInsertionInDatabase));

            for (GenericDao dao : daos) {

            }
    }

    public void createDBDataForTests(){

    }
}
