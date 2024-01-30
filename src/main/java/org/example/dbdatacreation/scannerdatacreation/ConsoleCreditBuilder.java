package org.example.dbdatacreation.scannerdatacreation;

import org.example.dao.impl.CreditDao;
import org.example.dao.impl.SalesDao;
import org.example.dbdatacreation.csvdatavalidation.CreditDataValidator;
import org.example.dbdatacreation.databaseinsertioncsv.GenericDbInserter;
import org.example.entities.Credit;
import org.example.entities.Sales;

import java.math.BigDecimal;
import java.util.Date;

import static org.example.dbdatacreation.scannerdatacreation.utils.BuilderUtils.SCANNER;
import static org.example.dbdatacreation.scannerdatacreation.utils.BuilderUtils.parseDate;


public class ConsoleCreditBuilder implements GenericDbInserter{

    private static final SalesDao SALES_DAO = new SalesDao();

    @Override
    public void processCreatedDataAndInsertIntoDB() {
        CreditDao creditDao = new CreditDao();
        Credit credit = createCreditFromConsole();
        CreditDataValidator creditDataValidator = new CreditDataValidator();
        if(creditDataValidator.isValid(credit)) {
            creditDao.save(credit);
        }
    }

    @Override
    public int getPriorityOfInsertionInDatabase() {
        return 5;
    }

    public static Credit createCreditFromConsole() {
        Long creditId = getCreditIdFromConsole();
        SCANNER.nextLine();
        BigDecimal pricePerMonth = getPricePerMonthFromConsole();
        SCANNER.nextLine();
        Sales sales = getSalesFromConsole();
        Integer months = getMonthsFromConsole();
        SCANNER.nextLine();
        Date paymentDate = getPaymentDateFromConsole();

        return Credit.builder()
                .id(creditId)
                .pricePerMonth(pricePerMonth)
                .months(months)
                .sales(sales)
                .paymentDate(paymentDate)
                .build();
    }

    private static Long getCreditIdFromConsole() {
        System.out.print("Enter Credit ID (Long): ");
        return SCANNER.nextLong();
    }

    private static BigDecimal getPricePerMonthFromConsole() {
        System.out.print("Enter Price per Month: ");
        return SCANNER.nextBigDecimal();
    }

    private static Sales getSalesFromConsole() {
        System.out.print("Enter SalesId: ");
        Long salesId = Long.parseLong(SCANNER.nextLine());
        return SALES_DAO.findById(salesId);
    }

    private static Integer getMonthsFromConsole() {
        System.out.print("Enter Number of Months: ");
        return SCANNER.nextInt();
    }

    private static Date getPaymentDateFromConsole() {
        System.out.print("Enter Payment Date (yyyy-MM-dd): ");
        String dateString = SCANNER.nextLine();
        return parseDate(dateString);
    }
}