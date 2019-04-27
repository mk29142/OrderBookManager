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