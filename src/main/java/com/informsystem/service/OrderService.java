package com.informsystem.service;

import com.informsystem.dao.impl.OrderDAOImpl;
import com.informsystem.model.Order;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.ViewScope;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Den on 04.04.16.
 */
@SpringComponent
@ViewScope
public class OrderService {

    @Autowired
    private OrderDAOImpl orderDAO;

    private static final Logger LOGGER = Logger.getLogger(DishService.class.getName());

    @Transactional(propagation = Propagation.REQUIRED)
    public synchronized List<Order> findAll(String stringFilter) {
        List<Order> orders = new ArrayList<>();
        for (Order order : orderDAO.getAllOrders()) {
            try {
                //TODO need to change filter
                boolean passesFilter = (stringFilter == null || stringFilter.isEmpty())
                        || order.toString().toLowerCase().contains(stringFilter.toLowerCase());
                if (passesFilter) {
                    Hibernate.initialize(order.getCustomer());
                    orders.add(order.clone());
                }
            } catch (CloneNotSupportedException ex) {
            }
        }
        return orders;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public synchronized void save(Order entry) {
        if (entry == null) {
            LOGGER.log(Level.SEVERE,
                    "Order is null.");
            return;
        }
        orderDAO.saveOrUpdateOrder(entry);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public synchronized void update(Order entry) {
        if (entry == null) {
            LOGGER.log(Level.SEVERE,
                    "Order is null.");
            return;
        }
        orderDAO.updateOrder(entry);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public synchronized void delete(Order value) {
        orderDAO.deleteOrder(value.getOrderId());
    }

}
