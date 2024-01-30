import org.example.dao.impl.HibernateUtility;
import org.hibernate.SessionFactory;
import utils.ConsoleEntitiesCreator;

public class Main {

    public static void main(String[] args) {

        SessionFactory sessionFactory = HibernateUtility.getSessionFactory();
        sessionFactory.openSession();
        ConsoleEntitiesCreator consoleEntitiesCreator = new ConsoleEntitiesCreator();
        consoleEntitiesCreator.createEntitiesFromConsole();
    }
}