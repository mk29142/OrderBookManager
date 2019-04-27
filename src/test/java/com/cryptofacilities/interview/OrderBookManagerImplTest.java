package com.cryptofacilities.interview;

import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.Assert.*;

public class OrderBookManagerImplTest {

    private OrderBookManagerImpl orderBookManager;

    @Before
    public void setUp() throws Exception {
        orderBookManager = new OrderBookManagerImpl();
    }

    @Test
    public void whenAddOrderCalled_GivenValidOrder_ShouldStoreOrderInOrderBook() {
        Order order = new Order("order1", "VOD.L", Side.buy, 200, 10);
        orderBookManager.addOrder(order);

        assertNotNull(orderBookManager.getOrdersAtLevel("VOD.L", Side.buy, 200));
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenAddOrderCalled_GivenInValidOrder_ShouldThrowException() {
        Order order = new Order("order1", "VOD.L", Side.buy, 200, 10);
        Order orderWithSameId = new Order("order1", "VOD.L", Side.buy, 200, 10);
        orderBookManager.addOrder(order);
        orderBookManager.addOrder(orderWithSameId);
    }

    @Test
    public void whenModifyOrderCalled_ShouldModifyValueInOrderBook() {
        Order order = new Order("order1", "VOD.L", Side.buy, 200, 10);
        orderBookManager.addOrder(order);

        long newQuantity = 15;
        orderBookManager.modifyOrder("order1", newQuantity);

        assertEquals(newQuantity, orderBookManager.getOrdersAtLevel("VOD.L", Side.buy, 200).get(0).getQuantity());
    }

    @Test(expected = NoSuchElementException.class)
    public void whenDeleteOrderCalled_GivenInvalidOrderID_ShouldThrowException() {
        orderBookManager.deleteOrder("order1");
    }

    @Test
    public void whenDeleteOrderCalled_ShouldDeleteFromOrderBooks() {
        Order order1 = new Order("order1", "VOD.L", Side.buy, 200, 10);
        Order order2 = new Order("order2", "VOD.L", Side.buy, 200, 10);
        orderBookManager.addOrder(order1);
        orderBookManager.addOrder(order2);

        orderBookManager.deleteOrder("order1");

        assertFalse(orderBookManager.getOrdersAtLevel("VOD.L", Side.buy, 200).contains(order1));
    }


    @Test
    public void whenGetBestPriceCalled_GivenBuyOrders_ShouldReturnHighestPrice() throws Exception {
        Order order1 = new Order("order1", "VOD.L", Side.buy, 200, 10);
        Order order2 = new Order("order2", "VOD.L", Side.buy, 300, 10);
        orderBookManager.addOrder(order1);
        orderBookManager.addOrder(order2);

        long bestBuyPrice = orderBookManager.getBestPrice("VOD.L", Side.buy);

        assertEquals(300, bestBuyPrice);
    }

    @Test
    public void whenGetBestPriceCalled_GivenSellOrders_ShouldReturnLowestPrice() throws Exception {
        Order order1 = new Order("order1", "VOD.L", Side.sell, 400, 10);
        Order order2 = new Order("order2", "VOD.L", Side.sell, 500, 10);
        orderBookManager.addOrder(order1);
        orderBookManager.addOrder(order2);

        long bestSellPrice = orderBookManager.getBestPrice("VOD.L", Side.sell);

        assertEquals(400, bestSellPrice);
    }

    @Test
    public void whenGetBestPriceCalled_GivenInstrumentThatDoesNotExist_ShouldReturnMinusOne() {
        long bestPrice = orderBookManager.getBestPrice("VOD.L", Side.sell);

        assertEquals(-1, bestPrice);
    }

    @Test
    public void whenGetOrderNumAtLevel_GivenInstrumentThatDoesNotExist_ShouldReturnMinusOne() {
        long orderNumber = orderBookManager.getOrderNumAtLevel("VOD.L", Side.sell, 200L);

        assertEquals(-1, orderNumber);
    }

    @Test
    public void whenGetOrderNumAtLevel_GivenWrongPrice_ShouldReturnMinusOne() {
        Order order1 = new Order("order1", "VOD.L", Side.sell, 200, 10);
        orderBookManager.addOrder(order1);

        long orderNumber = orderBookManager.getOrderNumAtLevel("VOD.L", Side.sell, 400L);

        assertEquals(-1, orderNumber);
    }

    @Test
    public void whenGetOrderNumAtLevel_GivenValidInstrument_ShouldReturnTotalOrderAtThatLevelAndSide() throws Exception {
        Order order1 = new Order("order1", "VOD.L", Side.sell, 200, 10);
        Order order2 = new Order("order2", "VOD.L", Side.sell, 200, 10);
        orderBookManager.addOrder(order1);
        orderBookManager.addOrder(order2);

        long orderNumber = orderBookManager.getOrderNumAtLevel("VOD.L", Side.sell, 200L);

        assertEquals(2, orderNumber);
    }

    @Test
    public void whenGetTotalQuantityAtLevel_GivenInstrumentThatDoesNotExist_ShouldReturnMinusOne() {
        long orderNumber = orderBookManager.getTotalQuantityAtLevel("VOD.L", Side.sell, 200L);

        assertEquals(-1, orderNumber);
    }

    @Test
    public void whenGetTotalQuantityAtLevel_ShouldReturnTotalQuantityOfInstrumentOrdersAtGivenSide() throws Exception {
        Order order1 = new Order("order1", "VOD.L", Side.sell, 200, 10);
        Order order2 = new Order("order2", "VOD.L", Side.sell, 200, 10);
        orderBookManager.addOrder(order1);
        orderBookManager.addOrder(order2);

        long totalQuantityAtLevel = orderBookManager.getTotalQuantityAtLevel("VOD.L", Side.sell, 200L);

        assertEquals(20, totalQuantityAtLevel);
    }

    @Test
    public void whenGetTotalQuantityAtLevel_GivenWrongPrice_ShouldReturnMinusOne() {
        Order order1 = new Order("order1", "VOD.L", Side.sell, 200, 10);
        orderBookManager.addOrder(order1);

        long totalQuantityAtLevel = orderBookManager.getTotalQuantityAtLevel("VOD.L", Side.sell, 400L);

        assertEquals(-1, totalQuantityAtLevel);
    }

    @Test
    public void whenGetTotalVolumeAtLevel_GivenInstrumentThatDoesNotExist_ShouldReturnMinusOne() {
        long totalVolumeAtLevel = orderBookManager.getTotalVolumeAtLevel("VOD.L", Side.sell, 200L);

        assertEquals(-1, totalVolumeAtLevel);
    }

    @Test
    public void whenGetTotalVolumeAtLevel_GivenWrongPrice_ShouldReturnMinusOne() {
        Order order1 = new Order("order1", "VOD.L", Side.sell, 200, 10);
        orderBookManager.addOrder(order1);

        long totalVolumeAtLevel = orderBookManager.getTotalVolumeAtLevel("VOD.L", Side.sell, 400L);

        assertEquals(-1, totalVolumeAtLevel);
    }

    @Test
    public void whenGetTotalVolumeAtLevel_ShouldReturnSumOfPriceAndQuantityForAllOrdersAtLevel() throws Exception {
        Order order1 = new Order("order1", "VOD.L", Side.sell, 200, 10);
        Order order2 = new Order("order2", "VOD.L", Side.sell, 200, 10);
        orderBookManager.addOrder(order1);
        orderBookManager.addOrder(order2);

        long totalVolumeAtLevel = orderBookManager.getTotalVolumeAtLevel("VOD.L", Side.sell, 200L);

        assertEquals(4000, totalVolumeAtLevel);
    }

    @Test
    public void whenGetOrdersAtLevelCalled_ShouldReturnList() throws Exception {
        Order order1 = new Order("order1", "VOD.L", Side.buy, 200, 10);
        Order order2 = new Order("order2", "VOD.L", Side.buy, 200, 10);
        orderBookManager.addOrder(order1);
        orderBookManager.addOrder(order2);

        List<Order> orders = orderBookManager.getOrdersAtLevel("VOD.L", Side.buy, 200);

        assertEquals(2, orders.size());
        assertTrue(orders.contains(order1));
        assertTrue(orders.contains(order2));
    }

    @Test
    public void whenGetOrdersAtLevelCalled_GivenInvalidInstrument_ShouldReturnEmptyList() throws Exception {
        List<Order> orders = orderBookManager.getOrdersAtLevel("VOD.L", Side.buy, 200);

        assertTrue(orders.isEmpty());
    }
}