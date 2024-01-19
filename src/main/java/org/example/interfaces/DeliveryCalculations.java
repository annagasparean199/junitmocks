package org.example.interfaces;

public interface DeliveryCalculations {

    double getStoreLossAmountForMonth(int targetMonth);

    double getStoreProfitAmountForMonth(int targetMonth);

    double getStoreSalesBalancePerMonth(int targetMonth);
}
