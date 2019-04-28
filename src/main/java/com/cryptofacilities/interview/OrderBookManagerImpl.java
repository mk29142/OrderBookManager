package com.cryptofacilities.interview;

import java.util.*;

public class OrderBookManagerImpl implements OrderBookManager {

    private HashMap<String, Order> allOrders = new HashMap<>();
    private HashMap<String, OrderBook> orderBooks = new HashMap<>();

    public void addOrder(Order order) {
        if(allOrders.containsKey(order.getOrderId())) {
            throw new IllegalArgumentException(String.format("Order with key %s already exists", order.getOrderId()));
        }

        String instrument = order.getInstrument();
        allOrders.put(order.getOrderId(), order);

        if(!orderBooks.containsKey(instrument)) {
            orderBooks.put(instrument, new OrderBook());
        }

        orderBooks.get(instrument).add(order);
    }

    public void modifyOrder(String orderId, long newQuantity) {
        Order order = allOrders.get(orderId);
        orderBooks.get(order.getInstrument()).modify(order, newQuantity);
        order.setQuantity(newQuantity);
    }

    public void deleteOrder(String orderId) {
        if(!allOrders.containsKey(orderId)) {
            throw new NoSuchElementException(String.format("Order with orderID %s does not exist", orderId));
        }
        Order order = allOrders.remove(orderId);
        orderBooks.get(order.getInstrument()).delete(order);
    }

    public long getBestPrice(String instrument, Side side) {
        if(!orderBooks.containsKey(instrument)) {
            return -1;
        }
        return orderBooks.get(instrument).getBestPrice(side);
    }

    public long getOrderNumAtLevel(String instrument, Side side, long price) {
        if(!orderBooks.containsKey(instrument)) {
            return -1;
        }
        return orderBooks.get(instrument).getOrderNumAtLevel(side, price);
    }

    public long getTotalQuantityAtLevel(String instrument, Side side, long price) {
        if(!orderBooks.containsKey(instrument)) {
            return -1;
        }
        return orderBooks.get(instrument).getTotalQuantityAtLevel(side, price);
    }

    public long getTotalVolumeAtLevel(String instrument, Side side, long price) {
        if(!orderBooks.containsKey(instrument)) {
            return -1;
        }
        return orderBooks.get(instrument).getTotalVolumeAtLevel(side, price);
    }

    public List<Order> getOrdersAtLevel(String instrument, Side side, long price) {
        if(!orderBooks.containsKey(instrument)) {
            return new ArrayList<>();
        }
        return orderBooks.get(instrument).getOrdersAtLevel(side, price);
    }
}
