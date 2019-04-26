package com.cryptofacilities.interview;

import java.util.*;

/**
 * Created by CF-8 on 6/27/2017.
 */
public class OrderBookManagerImpl implements OrderBookManager {

    // store based on orderID
    Map<String, Order> orders = new HashMap<>();

    // store based on instrument id
    HashMap<String, OrderBook> orderBooks = new HashMap<>();

    public void addOrder(Order order) {
        if(orders.containsKey(order.getOrderId())) {
            throw new IllegalArgumentException(String.format("Order with key %s already exists", order.getOrderId()));
        }

        String instrument = order.getInstrument();
        orders.put(order.getOrderId(), order);

        if(!orderBooks.containsKey(instrument)) {
            orderBooks.put(instrument, new OrderBook());
        }

        orderBooks.get(instrument).add(order);
    }

    public void modifyOrder(String orderId, long newQuantity) {
        Order order = orders.get(orderId);
        orderBooks.get(order.getInstrument()).modify(order, newQuantity);
        order.setQuantity(newQuantity);
    }

    public void deleteOrder(String orderId) {
        Order order = orders.remove(orderId);
        orderBooks.get(order.getInstrument()).delete(order);
    }

    public long getBestPrice(String instrument, Side side) {
        return 0;
    }

    public long getOrderNumAtLevel(String instrument, Side side, long price) {
        return 0;
    }

    public long getTotalQuantityAtLevel(String instrument, Side side, long price) {
        return 0;
    }

    public long getTotalVolumeAtLevel(String instrument, Side side, long price) {
        return 0;
    }

    public List<Order> getOrdersAtLevel(String instrument, Side side, long price) {
        return null;
    }
}
