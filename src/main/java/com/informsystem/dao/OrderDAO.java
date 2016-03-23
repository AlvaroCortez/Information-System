package com.informsystem.dao;

import com.informsystem.model.Order;

import java.util.List;

/**
 * Created by Den on 21.03.16.
 */
public interface OrderDAO {

    public void saveOrder(Order order);

    public void updateOrder(Order order);

    public void deleteOrder(int orderID);

    public Order getOrder(int orderID);

    public List<Order> getAllOrders();
}
