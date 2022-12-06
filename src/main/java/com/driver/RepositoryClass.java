package com.driver;

import jdk.nashorn.internal.objects.annotations.Getter;
import org.springframework.stereotype.Repository;

import java.util.HashMap;

@Repository

public class RepositoryClass {
    HashMap<String , Order> order =   new HashMap<>();

    HashMap<String, DeliveryPartner> deliveryPartner =  new HashMap<>();

    HashMap<Order, DeliveryPartner> orderDeliveryPartnerHashMap = new HashMap<>();;

    @Override
    public String toString() {
        return "RepositoryClass{" +
                "order=" + order +
                ", deliveryPartner=" + deliveryPartner +
                ", orderDeliveryPartnerHashMap=" + orderDeliveryPartnerHashMap +
                '}';
    }
}
