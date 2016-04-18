package com.informsystem.service;

import com.informsystem.dao.OrderLineDAO;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.ViewScope;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by Den on 04.04.16.
 */
@SpringComponent
@ViewScope
public class OrderLineService {

    @Autowired
    private OrderLineDAO orderLineDAO;

}
