package org.example.financemanager;

import org.example.entities.Credit;

public interface CreditCalculations {

    Credit findCreditBySalesId(Long salesId);

    Double getTotalPriceForOneCredit(Long productId, Long userId);

    Double getTotalPriceForPersonCreditsPerMonth(Long userId, int month);

    double getTotalAmountForPayedCredits(Long userId);

    double getTotalAmountForRemainCredits(Long userId);
}