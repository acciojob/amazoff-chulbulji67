package com.driver;

import com.sun.org.apache.xpath.internal.operations.Or;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


@Service
public class ServiceClass {

    @Autowired
    RepositoryClass repositoryClass;

    public String addOrder(Order order){
       HashMap<String, Order> orderHashMap = repositoryClass.order;
       orderHashMap.put(order.getId(), order);
        return "success";
    }

    public void addPartner( String partnerId){

        repositoryClass.deliveryPartner.put(partnerId, new DeliveryPartner(partnerId));
    }

    public void addOrderPartnerPair(String orderId, String partnerId){
        Order order = getOrderById(orderId);
        DeliveryPartner deliveryPartner = getPartnerById(partnerId);
        System.out.println(order+" , "+deliveryPartner);
        if(order !=null && deliveryPartner!=null)
        deliveryPartner.setNumberOfOrders(deliveryPartner.getNumberOfOrders()+1) ;
        repositoryClass.orderDeliveryPartnerHashMap.put(order, deliveryPartner);
    }


    public Order getOrderById(String orderId){
        HashMap<String, Order> orderHashMap = repositoryClass.order;
        System.out.println("Are you running");
        if(orderHashMap.containsKey(orderId)){
            System.out.println("Yes Key is Present");
            return orderHashMap.get(orderId);}
        return null;

    }

   public DeliveryPartner getPartnerById(String partnerId){
        if(repositoryClass.deliveryPartner.containsKey(partnerId)){
            System.out.println("Yeah Partner id is present");
            return repositoryClass.deliveryPartner.get(partnerId);
        }
        return null;
    }

    public int getOrderCountByPartnerId(String partnerId){
        if(repositoryClass.deliveryPartner.containsKey(partnerId))
        return repositoryClass.deliveryPartner.get(partnerId).getNumberOfOrders();
        return 0;
    }

    public List<String > getOrdersByPartnerId(String partnerId){
        List<String> ordersByPartnerId = new ArrayList<>();
        for (Order order : repositoryClass.orderDeliveryPartnerHashMap.keySet()){
            if(repositoryClass.orderDeliveryPartnerHashMap.get(order).getId().equals(partnerId)){
                ordersByPartnerId.add(order.getId());
            }
        }
        return ordersByPartnerId;
    }

    public List<String> getAllOrders(){
        List<String> allOrders = new ArrayList<>();

        for(Order order : repositoryClass.order.values()){
            allOrders.add(order.getId());
        }
        return allOrders;
    }

    public int getCountOfUnassignedOrders(){
        int totalOrder = repositoryClass.order.size();
        int assignOrders = repositoryClass.orderDeliveryPartnerHashMap.size();
        return totalOrder-assignOrders;
    }

    public int getOrdersLeftAfterGivenTimeByPartnerId(String time, String partnerId){
        int total = 0;

        int hh = Integer.parseInt(time.substring(0, 2));
        System.out.println("Hours is : "+hh);
        int mm = Integer.parseInt(time.substring(3));
        System.out.println("Minutes is "+mm);
        int givenTime = 60*hh+mm;

        for(Order order : repositoryClass.orderDeliveryPartnerHashMap.keySet()){
            if(order.getDeliveryTime() > givenTime && repositoryClass.orderDeliveryPartnerHashMap.get(order).getId().equals(partnerId)){
                total++;
            }
        }
        return total;
    }

    public String getLastDeliveryTimeByPartnerId(String partnerId){
        int lastDelevery = 0;
        for(Order order:repositoryClass.orderDeliveryPartnerHashMap.keySet()){
            if(repositoryClass.orderDeliveryPartnerHashMap.get(order).getId().equals(partnerId)){
                if(order.getDeliveryTime() > lastDelevery){
                    lastDelevery = order.getDeliveryTime();
                }
            }
        }
        int hh = lastDelevery/60;
        int mm = lastDelevery%60;
        return Integer.toString(hh)+":"+Integer.toString(mm);
    }

    public String deletePartnerById(String partnerId){
        if(repositoryClass.deliveryPartner.containsKey(partnerId)){
            repositoryClass.deliveryPartner.remove(partnerId);
        }
        for(Order order: repositoryClass.orderDeliveryPartnerHashMap.keySet()){
            if(repositoryClass.orderDeliveryPartnerHashMap.get(order).getId().equals(partnerId)){
                repositoryClass.orderDeliveryPartnerHashMap.remove(order);
            }
        }
        return "Success";

    }


    public String deleteOrderById(String orderId){
        repositoryClass.order.remove(orderId);
        repositoryClass.orderDeliveryPartnerHashMap.remove(getOrderById(orderId));
        return "success";
    }

}
