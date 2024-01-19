package org.example.interfaces;

import java.time.Year;


public interface SalesCalculations {

    double getCalculatedPercentageForUser(Long userId);
    double getPriceWithDiscount(double discount, Long productId);
    double getFullPriceFromPaidAmount(Long salesId);
    double getPercentageOfSalesPerYear(Year year);
}
