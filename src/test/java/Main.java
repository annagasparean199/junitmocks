
import org.example.DAO.DAOImpl.CreditDao;
import org.example.DAO.DAOImpl.HibernateUtility;
import org.example.DAO.DAOImpl.ProductDao;
import org.example.entity.Credit;
import org.example.entity.Product;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Assertions;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static utils.Utils.*;

public class Main {
    public static void main(String[] args) {
//        long timestamp = 1698799000000L;
//        Date sec = new Date(1672531200000L);
//        Date date = new Date(timestamp);
//        System.out.println(date.getYear());
//        ProductDao productDao = new ProductDao();
        SessionFactory sessionFactory =  HibernateUtility.getSessionFactory();
        sessionFactory.openSession();
        //  productDao.save(product1);
//        createUtils();
//        CreditDao creditDao = new CreditDao();
//        Long id = credit.getId();
//
//        Credit result = creditDao.findById(id, Credit.class);
//        assertNotNull(result);
//        Assertions.assertEquals(credit, result);
    }

}
