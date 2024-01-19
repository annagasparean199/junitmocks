package org.example.interfaces;

import org.example.entity.Credit;
import org.example.entity.Delivery;
import org.example.entity.Product;
import org.example.entity.Sales;
import org.example.hibernate.CreditDao;
import org.example.hibernate.DeliveryDao;
import org.example.hibernate.SalesDao;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

public interface DeliveryCalculations {

    double getStoreLossAmountForMonth(int targetMonth);

    double getStoreProfitAmountForMonth(int targetMonth);

    double getStoreSalesBalancePerMonth(int targetMonth);
}
