package com.informsystem.dao;

import com.informsystem.model.OrderLine;

import java.util.List;

/**
 * Created by Den on 21.03.16.
 */
public interface OrderLineDAO {
    public void saveOrderLine(OrderLine orderLine);

    public void updateOrderLine(OrderLine orderLine);

    public void deleteOrderLine(int orderLineID);

    public OrderLine getOrderLine(int orderLineID);

    public List<OrderLine> getAllOrderLines();
}
