package org.example.interfaces;

import java.time.Year;


public interface SalesCalculations {

    double getCalculatedPercentageForUser(Long userId);
    double getPriceWithDiscount(double discount, Long productId);
    double getDiscountPercentageFromPaidAmount(Long salesId);
    double getPercentageOfSalesPerYear(Year year);
}
