import org.example.entity.Credit;
import org.example.hibernate.CreditDao;
import org.hibernate.event.spi.SaveOrUpdateEvent;

import java.util.List;

import static org.example.hibernate.CreditDao.getCreditDaoInstance;

public class tets {

    static CreditDao creditDao = getCreditDaoInstance();
    static Credit credit = creditDao.findById(5);

    public static void main(String[] args) {
        System.out.println(credit);
        //List<Credit> creditList = creditDao.getAllUsers();
       // System.out.println(creditList.get(1));
    }
}
