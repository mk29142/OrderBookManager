package com.cryptofacilities.interview;

import java.util.*;

public class OrderBook {

    HashMap<Long, List<Order>> buyLevels;
    HashMap<Long, List<Order>> sellLevels;

    private PriorityQueue<Long> buyPrices;
    private PriorityQueue<Long> sellPrices;

    public OrderBook() {
        buyLevels = new HashMap<>();
        sellLevels = new HashMap<>();
        buyPrices = new PriorityQueue<>(Comparator.reverseOrder());
        sellPrices = new PriorityQueue<>();
    }

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

    public long getBestPrice(Side side) {
        if(side == Side.buy) {
            return buyPrices.peek();
        } else {
            return sellPrices.peek();
        }
    }

    public long getOrderNumAtLevel(Side side, long price) {
        if(side == Side.buy) {
            return getOrderNumber(price, buyLevels);
        } else {
            return getOrderNumber(price, sellLevels);
        }
    }

    private long getOrderNumber(long price, Map<Long, List<Order>> levels) {
        if(!levels.containsKey(price)) {
            return -1;
        }
        return levels.get(price).size();
    }

    public long getTotalQuantityAtLevel(Side side, long price) {
        if(side == Side.buy) {
            return getTotalQuantity(price, buyLevels);
        } else {
            return getTotalQuantity(price, sellLevels);
        }
    }

    private long getTotalQuantity(long price, Map<Long, List<Order>> levels) {
        if(!levels.containsKey(price)) {
            return -1;
        }
        List<Order> ordersAtLevel = levels.get(price);
        return ordersAtLevel.stream()
                .map(Order::getQuantity)
                .reduce(0L, (subtotal, orderPrice) -> subtotal + orderPrice);
    }

    public long getTotalVolumeAtLevel(Side side, long price) {
        if(side == Side.buy) {
            return getTotalVolume(price, buyLevels);
        } else {
            return getTotalVolume(price, sellLevels);
        }
    }

    private long getTotalVolume(long price, Map<Long, List<Order>> levels) {
        if(!levels.containsKey(price)) {
            return -1;
        }
        List<Order> ordersAtLevel = levels.get(price);
        return ordersAtLevel.stream()
                .map(order -> order.getPrice() * order.getQuantity())
                .reduce(0L, (subtotal, orderVolume) -> subtotal + orderVolume);
    }

    public List<Order> getOrdersAtLevel(Side side, long price) {
        if(side == Side.buy) {
            return getOrders(price, buyLevels);
        } else {
            return getOrders(price, sellLevels);
        }
    }

    private List<Order> getOrders(long price, Map<Long, List<Order>> levels) {
        if(!levels.containsKey(price)) {
            return new ArrayList<>();
        }
        return levels.get(price);
    }
}
