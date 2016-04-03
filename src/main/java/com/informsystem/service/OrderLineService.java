package com.informsystem.service;

import com.informsystem.dao.OrderLineDAO;
import com.informsystem.model.OrderLine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Den on 04.04.16.
 */
@Service("orderLineService")
public class OrderLineService {

    @Autowired
    OrderLineDAO orderLineDAO;

    public List<OrderLine> getAllOrderLines(){
        return orderLineDAO.getAllOrderLines();
    }
}
