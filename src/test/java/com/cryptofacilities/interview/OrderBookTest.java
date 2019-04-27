package com.cryptofacilities.interview;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class OrderBookTest {

    private OrderBook orderBook;

    @Before
    public void setUp() throws Exception {
        orderBook = new OrderBook();
    }

    @Test
    public void whenAddCalled_GivenValidOrder_OrderShouldBeAddedToCorrectLevel() throws Exception {
        Order order1 = new Order("order1", "VOD.L", Side.buy, 200, 10);
        Order order2 = new Order("order2", "VOD.L", Side.sell, 200, 10);
        orderBook.add(order1);
        orderBook.add(order2);

        assertTrue(orderBook.getOrdersAtLevel(Side.buy, 200L).contains(order1));
        assertTrue(orderBook.getOrdersAtLevel(Side.sell, 200L).contains(order2));
    }

    @Test
    public void whenModifyCalled_GivenChangeInQuantity_ShouldModifyQuantityOfOrder() throws Exception {
        Order order1 = new Order("order1", "instrument1", Side.buy, 200, 10);
        Order order2 = new Order("order2", "instrument1", Side.buy, 200, 20);
        Order order3 = new Order("order3", "instrument1", Side.buy, 200, 30);
        orderBook.add(order1);
        orderBook.add(order2);
        orderBook.add(order3);

        orderBook.modify(order2, 15);

        assertEquals(15, orderBook.getOrdersAtLevel(Side.buy, 200L).get(1).getQuantity());
    }


    @Test
    public void whenModifyCalled_GivenDecreaseInQuantity_PositionOfOrderShouldNotChange() throws Exception {
        Order order1 = new Order("order1", "instrument1", Side.buy, 200, 10);
        Order order2 = new Order("order2", "instrument1", Side.buy, 200, 20);
        Order order3 = new Order("order3", "instrument1", Side.buy, 200, 30);
        orderBook.add(order1);
        orderBook.add(order2);
        orderBook.add(order3);

        int expectedIndex = 1;
        orderBook.modify(order2, 15);

        assertEquals(expectedIndex, orderBook.getOrdersAtLevel(Side.buy, 200L).indexOf(order2));
    }

    @Test
    public void whenModifyCalled_GivenIncreaseInQuantity_OrderShouldBeMovedToEndOfList() throws Exception {
        Order order1 = new Order("order1", "instrument1", Side.buy, 200, 10);
        Order order2 = new Order("order2", "instrument1", Side.buy, 200, 20);
        Order order3 = new Order("order3", "instrument1", Side.buy, 200, 30);
        orderBook.add(order1);
        orderBook.add(order2);
        orderBook.add(order3);

        int expectedIndex = 2;
        orderBook.modify(order2, 25);

        assertEquals(expectedIndex, orderBook.getOrdersAtLevel(Side.buy, 200L).indexOf(order2));
    }


    @Test
    public void whenDeleteCalled_GivenLevelContainsTwoOrders_ShouldRemoveOrderFromList() throws Exception {
        Order order1 = new Order("order1", "instrument1", Side.buy, 200, 10);
        Order order2 = new Order("order2", "instrument1", Side.buy, 200, 10);
        orderBook.add(order1);
        orderBook.add(order2);

        orderBook.delete(order1);

        assertFalse(orderBook.getOrdersAtLevel(Side.buy, 200L).contains(order1));
    }

//    @Test
//    public void whenDeleteCalled_GivenLevelContainsOnlyOneOrders_ShouldDeleteLevel() throws Exception {
//        Order order1 = new Order("order1", "instrument1", Side.buy, 200, 10);
//        orderBook.add(order1);
//
//        orderBook.delete(order1);
//
//        assertTrue(orderBook.getOrdersAtLevel(Side.buy, 200L).isEmpty());
//    }

    @Test
    public void whenGetBestPriceCalled_GivenBuySide_ShouldReturnHighestPrice() {
        Order order1 = new Order("order1", "instrument1", Side.buy, 200, 10);
        Order order2 = new Order("order2", "instrument1", Side.buy, 300, 10);
        Order order3 = new Order("order3", "instrument1", Side.buy, 400, 10);
        orderBook.add(order1);
        orderBook.add(order2);
        orderBook.add(order3);

        long bestBuyPrice = orderBook.getBestPrice(Side.buy);

        assertEquals(400L, bestBuyPrice);
    }

    @Test
    public void whenGetBestPriceCalled_GivenSellSide_ShouldReturnLowestPrice() {
        Order order1 = new Order("order1", "instrument1", Side.sell, 200, 10);
        Order order2 = new Order("order2", "instrument1", Side.sell, 300, 10);
        Order order3 = new Order("order3", "instrument1", Side.sell, 400, 10);
        orderBook.add(order1);
        orderBook.add(order2);
        orderBook.add(order3);

        long bestBuyPrice = orderBook.getBestPrice(Side.sell);

        assertEquals(200L, bestBuyPrice);
    }

    @Test
    public void whenGetOrderNumAtLevelCalled_GiveSideAndPrice_ShouldReturnSizeOfLevelAtPrice() {
        Order sell1 = new Order("order1", "instrument1", Side.sell, 200, 10);
        Order sell2 = new Order("order2", "instrument1", Side.sell, 200, 10);
        Order buy1 = new Order("order3", "instrument1", Side.buy, 200, 10);
        orderBook.add(sell1);
        orderBook.add(sell2);
        orderBook.add(buy1);

        long expectedSellOrders = 2;
        long totalSellOrders = orderBook.getOrderNumAtLevel(Side.sell, 200L);

        long expectedBuyOrders = 1;
        long totalBuyOrders = orderBook.getOrderNumAtLevel(Side.buy, 200L);

        assertEquals(expectedSellOrders, totalSellOrders);
        assertEquals(expectedBuyOrders, totalBuyOrders);
    }

    @Test
    public void whenGetOrderNumAtLevelCalled_GivenInvalidPrice_ShouldReturnMinusOne() {
        long expectedOrders = -1;
        long totalOrders = orderBook.getOrderNumAtLevel(Side.sell, 200L);

        assertEquals(expectedOrders, totalOrders);
    }

    @Test
    public void whenGetTotalQuantityAtLevelCalled_GivenValidPrice_ShouldReturnSumOfQuantities() {
        Order buy1 = new Order("order1", "instrument1", Side.buy, 200, 10);
        Order buy2 = new Order("order2", "instrument1", Side.buy, 200, 20);
        Order sell1 = new Order("order3", "instrument1", Side.sell, 200, 30);
        Order sell2 = new Order("order4", "instrument1", Side.sell, 200, 40);
        orderBook.add(buy1);
        orderBook.add(buy2);
        orderBook.add(sell1);
        orderBook.add(sell2);

        long expectedTotalBuyQuantity = 30;
        long totalBuyQuantity = orderBook.getTotalQuantityAtLevel(Side.buy, 200L);

        long expectedTotalSellQuantity = 70;
        long totalSellQuantity = orderBook.getTotalQuantityAtLevel(Side.sell, 200L);

        assertEquals(expectedTotalBuyQuantity, totalBuyQuantity);
        assertEquals(expectedTotalSellQuantity, totalSellQuantity);
    }

    @Test
    public void whenGetTotalQuantityAtLevelCalled_GivenInvalidPrice_ShouldReturnMinusOne() {
        long expectedTotalQuantity = -1;
        long totalQuantity = orderBook.getTotalQuantityAtLevel(Side.sell, 200L);

        assertEquals(expectedTotalQuantity, totalQuantity);
    }

    @Test
    public void whenGetTotalVolumeAtLevelCalled_GivenValidPrice_ShouldReturnSumOfQuantitiesTimesPrices() {
        Order buy1 = new Order("order1", "instrument1", Side.buy, 200, 10);
        Order buy2 = new Order("order2", "instrument1", Side.buy, 200, 20);
        Order sell1 = new Order("order3", "instrument1", Side.sell, 200, 30);
        Order sell2 = new Order("order4", "instrument1", Side.sell, 200, 40);
        orderBook.add(buy1);
        orderBook.add(buy2);
        orderBook.add(sell1);
        orderBook.add(sell2);

        long expectedTotalBuyVolume = 200 * (10 + 20);
        long totalBuyVolume = orderBook.getTotalVolumeAtLevel(Side.buy, 200L);

        long expectedTotalSellVolume = 200 * (30 + 40);
        long totalSellVolume = orderBook.getTotalVolumeAtLevel(Side.sell, 200L);

        assertEquals(expectedTotalBuyVolume, totalBuyVolume);
        assertEquals(expectedTotalSellVolume, totalSellVolume);
    }

    @Test
    public void whenGetTotalVolumeAtLevelCalled_GivenInvalidPrice_ShouldReturnMinusOne() {
        long expectedTotalVolume = -1;
        long totalVolume = orderBook.getTotalQuantityAtLevel(Side.sell, 200L);

        assertEquals(expectedTotalVolume, totalVolume);
    }

    @Test
    public void whenGetOrdersAtLevelCalled_GivenValidPrice_ShouldReturnNonEmptyList() {
        Order buy1 = new Order("order1", "instrument1", Side.buy, 200, 10);
        Order buy2 = new Order("order2", "instrument1", Side.buy, 200, 20);
        Order sell1 = new Order("order3", "instrument1", Side.sell, 200, 30);
        Order sell2 = new Order("order4", "instrument1", Side.sell, 200, 40);
        orderBook.add(buy1);
        orderBook.add(buy2);
        orderBook.add(sell1);
        orderBook.add(sell2);

        List<Order> buyOrders = orderBook.getOrdersAtLevel(Side.buy, 200L);
        List<Order> sellOrders = orderBook.getOrdersAtLevel(Side.sell, 200L);

        assertFalse(buyOrders.isEmpty());
        assertFalse(sellOrders.isEmpty());
    }

    @Test
    public void whenGetOrdersAtLevelCalled_GivenInvalidPrice_ShouldReturnEmptyList() {
        List<Order> orders = orderBook.getOrdersAtLevel(Side.buy, 200L);

        assertTrue(orders.isEmpty());
    }
}