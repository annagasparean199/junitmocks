package org.example.DAO.DAOImpl;

import org.example.entity.Delivery;
import org.example.entity.Product;
import org.example.interfaces.DeliveryCalculations;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

public class rtt implements DeliveryCalculations {

    private final DeliveryDao deliveryDao;

    public rtt(DeliveryDao deliveryDao) {
        this.deliveryDao = deliveryDao;
    }

    @Override
    public double getStoreLossAmountForMonth(int targetMonth) {

        List<Delivery> allDeliveries = deliveryDao.getAllEntities(Delivery.class);
        double storeLossAmount = 0;

        for (Delivery delivery : allDeliveries) {
            Date deliveryDate = delivery.getDeliveryDate();
            LocalDate localDeliveryDate = deliveryDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            if (localDeliveryDate.getMonthValue() == targetMonth) {
                Product product = delivery.getProduct();
                int quantity = delivery.getQuantity();
                double price = product.getPrice();
                storeLossAmount += quantity * price;
            }
        }
        return storeLossAmount;
    }

    @Override
    public double getStoreProfitAmountForMonth(int targetMonth) {
        return 0;
    }

    @Override
    public double getStoreSalesBalancePerMonth(int targetMonth) {
        return 0;
    }
}
