package org.example.dbdatacreation.csventityreaders;

import org.example.dao.impl.SalesDao;
import org.example.entities.Credit;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CreditCSVReader implements GenericCSVReader<Credit>{

    SalesDao salesDao = new SalesDao();
    private final String CSV_CREDITS_PATH = "src/test/resources/creditdata.csv";

    public List<Credit> readFromCSV(String filePath) {
        List<Credit> credits = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            br.readLine();

            while ((line = br.readLine()) != null) {
                String[] data = line.split("\t");
                Credit credit = createCreditFromCSV(data[0].split(","));
                credits.add(credit);
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

        return credits;
    }

    private Credit createCreditFromCSV(String[] data) throws ParseException {
        Credit.CreditBuilder creditBuilder = Credit.builder();

        creditBuilder.id(Long.parseLong(data[0].trim()))
                .pricePerMonth(new BigDecimal(data[1].trim()))
                .months(Integer.parseInt(data[2].trim()))
                .sales(salesDao.findById(Long.parseLong(data[3].trim())))
                .paymentDate(parseDate(data[4].trim()));

        return creditBuilder.build();
    }

    private Date parseDate(String dateString) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.parse(dateString);
    }
}