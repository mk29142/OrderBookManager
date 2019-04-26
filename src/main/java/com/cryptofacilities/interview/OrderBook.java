package com.cryptofacilities.interview;

import java.util.*;

public class OrderBook {

    private Map<Long, List<Order>> buyLevels = new HashMap<>();
    private Map<Long, List<Order>> sellLevels = new HashMap<>();

    // to determine levels
    private PriorityQueue<Long> buyPrices = new PriorityQueue<>();
    private PriorityQueue<Long> sellPrices = new PriorityQueue<>(Comparator.reverseOrder());

    public void add(Order order) {
        long price = order.getPrice();

        if (order.getSide() == Side.buy) {
            buyPrices.add(price);
            addToLevels(price, order, buyLevels);
        } else {
            sellPrices.add(price);
            addToLevels(price, order, sellLevels);
        }
    }

    private void addToLevels(long price, Order order, Map<Long, List<Order>> levels) {
        if(levels.containsKey(price)) {
            levels.get(price).add(order);
        } else {
            ArrayList<Order> orders = new ArrayList<>();
            orders.add(order);
            levels.put(price, orders);
        }
    }

    public void modify(Order order, long newQuantity) {
        long price = order.getPrice();
        if (order.getSide() == Side.buy) {
            modifyOrderInLevel(newQuantity, price, order, buyLevels);
        } else {
            modifyOrderInLevel(newQuantity, price, order, sellLevels);
        }
    }

    private void modifyOrderInLevel(long newQuantity, long price, Order order, Map<Long, List<Order>> levels) {
        List<Order> levelOrder = levels.get(price);
        if(order.getQuantity() < newQuantity) {
            levelOrder.remove(order);
            levelOrder.add(order);
        }
        order.setQuantity(newQuantity);
    }

    public void delete(Order order) {
        long price = order.getPrice();
        if(order.getSide() == Side.buy) {
            deleteOrderFromLevel(price, order, buyLevels);
        } else {
            deleteOrderFromLevel(price, order, sellLevels);
        }
    }

    private void deleteOrderFromLevel(long price, Order order, Map<Long, List<Order>> levels) {
        levels.get(price).remove(order);
        if(levels.get(price).isEmpty()) {
            levels.remove(price);
        }
    }
}
