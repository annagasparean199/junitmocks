package org.example.dbdatacreation.csvdatavalidation;

import org.example.entities.Credit;
import org.example.entities.Sales;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class CreditDataValidator implements GenericDataValidator<Credit> {

        private static final Set<Long> UNIQUE_SALES_IDS = new HashSet<>();

        public boolean isValid(Credit credit) {
            return isValidId(credit.getId()) &&
                    isValidPricePerMonth(credit.getPricePerMonth()) &&
                    isValidMonths(credit.getMonths()) &&
                    isUniqueSalesId(credit.getSales()) &&
                    isValidPaymentDate(credit.getPaymentDate());
        }

        private boolean isValidId(Long id) {
            return id != null && id > 0;
        }

        private boolean isValidPricePerMonth(BigDecimal pricePerMonth) {
            return pricePerMonth != null && pricePerMonth.compareTo(BigDecimal.ZERO) > 0;
        }

        private boolean isValidMonths(Integer months) {
            return months != null && months > 0 && months < 13;
        }

        private boolean isUniqueSalesId(Sales sales) {
            if (sales != null) {
                Long salesId = sales.getId();
                return salesId != null && UNIQUE_SALES_IDS.add(salesId);
            }
            return false;
        }

        private boolean isValidPaymentDate(Date paymentDate) {
            return paymentDate != null && !paymentDate.after(new Date());
        }
}