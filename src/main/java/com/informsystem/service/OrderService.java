package com.informsystem.service;

import com.informsystem.dao.OrderDAO;
import com.informsystem.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Den on 04.04.16.
 */
@Service("orderService")
public class OrderService {

    @Autowired
    private OrderDAO orderDAO;

    public List<Order> getAllOrders(){
        return orderDAO.getAllOrders();
    }
}
