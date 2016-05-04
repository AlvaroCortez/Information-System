package com.informsystem.service;

import com.informsystem.dao.impl.OrderLineDAOImpl;
import com.informsystem.model.OrderLine;
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
public class OrderLineService {

    @Autowired
    private OrderLineDAOImpl orderLineDAO;

    private static final Logger LOGGER = Logger.getLogger(DishService.class.getName());

    @Transactional(propagation = Propagation.REQUIRED)
    public synchronized List<OrderLine> findAll(String stringFilter) {
        List<OrderLine> orderLines = new ArrayList<>();
        for (OrderLine orderLine : orderLineDAO.getAllOrderLines()) {
//            try {
                //TODO need to change filter
                boolean passesFilter = (stringFilter == null || stringFilter.isEmpty())
                        || orderLine.toString().toLowerCase().contains(stringFilter.toLowerCase());
                if (passesFilter) {
                    Hibernate.initialize(orderLine.getDish());
                    orderLines.add(orderLine);
                }
//            } catch (CloneNotSupportedException ex) {
//            }
        }
        return orderLines;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public synchronized void save(OrderLine entry) {
        if (entry == null) {
            LOGGER.log(Level.SEVERE,
                    "Order is null.");
            return;
        }
        orderLineDAO.saveOrUpdateOrderLine(entry);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public synchronized void delete(OrderLine value) {
        orderLineDAO.deleteOrderLine(value.getOrderLineId());
    }
}
