package com.cryptofacilities.interview;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class OrderBookManagerImplTest {

    private OrderBookManagerImpl orderBookManager;


    @Before
    public void setUp() throws Exception {
        orderBookManager = new OrderBookManagerImpl();
    }

    @Test
    public void whenAddOrderCalled_GivenValidOrder_ShouldStoreByIdsAndCreateOrderBook() {
        Order order = new Order("order1", "VOD.L", Side.buy, 200, 10);
        orderBookManager.addOrder(order);

        assertNotNull(orderBookManager.orders.get("order1"));
        assertNotNull(orderBookManager.orderBooks.get("VOD.L"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenAddOrderCalled_GivenInValidOrder_ShouldThrowException() {
        Order order = new Order("order1", "VOD.L", Side.buy, 200, 10);
        Order orderWithSameId = new Order("order1", "VOD.L", Side.buy, 200, 10);
        orderBookManager.addOrder(order);
        orderBookManager.addOrder(orderWithSameId);
    }

    // with modify use methods for get total amount etc
    // with delete, can use getOrdersAtLevel

//    @Test
//    public void whenAddOrderCalled_GivenValidOrder_OrderShouldBeStoredByIdAndPrice() throws Exception {
//        Order order = new Order("order1", "VOD.L", Side.buy, 200, 10);
//        orderBookManager.addOrder(order);
//
//        assertNotNull(orderBookManager.getAllOrders().get("order1"));
//        assertNotNull(orderBookManager.getAllBuyLevels().get(200L).get(0));
//    }
//
//    @Test(expected = IllegalArgumentException.class)
//    public void whenAddOrderCalled_GivenOrderIdAlreadyExists_ShouldThrowException() throws Exception {
//        Order order = new Order("order1", "VOD.L", Side.buy, 200, 10);
//        Order orderWithSameId = new Order("order1", "VOD.L", Side.buy, 200, 10);
//        orderBookManager.addOrder(order);
//        orderBookManager.addOrder(orderWithSameId);
//    }
//
//    @Test
//    public void whenModifyOrderCalled_GivenDecreaseInQuantity_PositionOfOrderShouldNotChange() throws Exception {
//        Order order1 = new Order("order1", "instrument1", Side.buy, 200, 10);
//        Order order2 = new Order("order2", "instrument2", Side.buy, 200, 20);
//        Order order3 = new Order("order3", "instrument3", Side.buy, 200, 30);
//        orderBookManager.addOrder(order1);
//        orderBookManager.addOrder(order2);
//        orderBookManager.addOrder(order3);
//
//        int expectedIndex = 1;
//        orderBookManager.modifyOrder("order2", 15);
//
//        assertEquals(expectedIndex, orderBookManager.getAllBuyLevels().get(200L).indexOf(order2));
//    }
//
//    @Test
//    public void whenModifyOrderCalled_GivenIncreaseInQuantity_OrderShouldBeMovedToEndOfQueue() throws Exception {
//        Order order1 = new Order("order1", "instrument1", Side.buy, 200, 10);
//        Order order2 = new Order("order2", "instrument2", Side.buy, 200, 20);
//        Order order3 = new Order("order3", "instrument3", Side.buy, 200, 30);
//        orderBookManager.addOrder(order1);
//        orderBookManager.addOrder(order2);
//        orderBookManager.addOrder(order3);
//
//        int expectedIndex = 2;
//        orderBookManager.modifyOrder("order2", 25);
//
//        assertEquals(expectedIndex, orderBookManager.getAllBuyLevels().get(200L).indexOf(order2));
//    }
//
//    @Test
//    public void whenDeleteOrder_ShouldRemoveOrderFromAllOrders() throws Exception {
//        Order order = new Order("order1", "instrument1", Side.buy, 200, 10);
//        orderBookManager.addOrder(order);
//
//        orderBookManager.deleteOrder("order1");
//
//        assertTrue(orderBookManager.getAllOrders().isEmpty());
//    }
//
//    @Test
//    public void whenDeleteOrder_GivenLevelContainsTwoOrders_ShouldRemoveOrderFromList() throws Exception {
//        Order order1 = new Order("order1", "instrument1", Side.buy, 200, 10);
//        Order order2 = new Order("order2", "instrument1", Side.buy, 200, 10);
//        orderBookManager.addOrder(order1);
//        orderBookManager.addOrder(order2);
//
//        orderBookManager.deleteOrder("order1");
//
//        assertFalse(orderBookManager.getAllBuyLevels().get(200L).contains(order1));
//    }
//
//    @Test
//    public void whenDeleteOrder_GivenLevelContainsOnlyOneOrders_ShouldDeleteLevel() throws Exception {
//        Order order1 = new Order("order1", "instrument1", Side.buy, 200, 10);
//        orderBookManager.addOrder(order1);
//
//        orderBookManager.deleteOrder("order1");
//
//        assertFalse(orderBookManager.getAllBuyLevels().containsKey(200L));
//    }

    @Test
    public void getBestPrice() throws Exception {

    }

    @Test
    public void getOrderNumAtLevel() throws Exception {

    }

    @Test
    public void getTotalQuantityAtLevel() throws Exception {

    }

    @Test
    public void getTotalVolumeAtLevel() throws Exception {

    }

    @Test
    public void getOrdersAtLevel() throws Exception {

    }

}