package org.example.interfaces;

import org.example.entity.Credit;
import java.time.LocalDate;


public interface CreditCalculations {

    Credit findCreditBySalesId(Long salesId);

    Double getTotalPriceForOneCredit(Long productId, Long userId);

    Double getTotalPriceForPersonCreditsPerMonth(Long userId, int month);

    double getTotalAmountForPayedCredits(Long userId);

    int calculatePayedMonths(LocalDate paymentDate, Credit credit);

    double getTotalAmountForRemainCredits(Long userId);
}
