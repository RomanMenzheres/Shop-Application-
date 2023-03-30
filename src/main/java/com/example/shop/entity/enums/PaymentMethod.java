package com.example.shop.entity.enums;

import java.util.List;

public enum PaymentMethod {
    CASH,
    CARD;

    public static List<PaymentMethod> getAll(){
        return List.of(PaymentMethod.CARD, PaymentMethod.CASH);
    }
}
