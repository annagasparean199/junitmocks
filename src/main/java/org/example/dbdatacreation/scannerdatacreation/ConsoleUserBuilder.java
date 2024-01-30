package org.example.dbdatacreation.scannerdatacreation;

import org.example.dao.impl.UserDao;
import org.example.dbdatacreation.csvdatavalidation.UserDataValidator;
import org.example.dbdatacreation.databaseinsertioncsv.GenericDbInserter;
import org.example.entities.Sales;
import org.example.entities.User;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.example.dbdatacreation.scannerdatacreation.utils.BuilderUtils.SCANNER;

public class ConsoleUserBuilder implements GenericDbInserter {

    private static final List<Sales> SALES = new ArrayList<>();

     @Override
     public void processCreatedDataAndInsertIntoDB() {
        UserDao userDao = new UserDao();
        UserDataValidator userDataValidator = new UserDataValidator();
        User user =  createUserFromConsole();
        if(userDataValidator.isValid(user)) {
           userDao.save(user);
        }
     }

     @Override
     public int getPriorityOfInsertionInDatabase() {
        return 2;
     }

    public static User createUserFromConsole() {
        Long userId = getUserIdFromConsole();
        Double salary = getSalaryFromConsole();
        UUID uuid = UUID.randomUUID();
        Boolean legalEntity = isLegalEntityFromConsole();
        String company = getCompanyFromConsole();
        String name = getUserNameFromConsole();

        return User.builder()
                .id(userId)
                .salary(salary)
                .uuid(uuid)
                .legalEntity(legalEntity)
                .company(company)
                .name(name)
                .sales(SALES)
                .build();
    }

    private static Long getUserIdFromConsole() {
        System.out.print("Enter User ID (Long): ");
        Long userId = SCANNER.nextLong();
        SCANNER.nextLine();
        return userId;
    }

    private static Double getSalaryFromConsole() {
        System.out.print("Enter Salary: ");
        Double salary = SCANNER.nextDouble();
        SCANNER.nextLine();
        return salary;
    }

    private static Boolean isLegalEntityFromConsole() {
        System.out.print("Is the User a Legal Entity? (true/false): ");
        Boolean legalEntity = SCANNER.nextBoolean();
        SCANNER.nextLine();
        return legalEntity;
    }

    private static String getCompanyFromConsole() {
        System.out.print("Enter Company: ");
        SCANNER.nextLine();
        return SCANNER.nextLine();
    }

    private static String getUserNameFromConsole() {
        System.out.print("Enter User Name: ");
        return SCANNER.nextLine();
    }
}