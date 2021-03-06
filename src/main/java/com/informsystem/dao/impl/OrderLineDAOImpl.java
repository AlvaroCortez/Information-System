package com.informsystem.dao.impl;

import com.informsystem.dao.OrderLineDAO;
import com.informsystem.model.OrderLine;
import com.vaadin.spring.annotation.SpringComponent;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by Den on 21.03.16.
 */
@SpringComponent
public class OrderLineDAOImpl implements OrderLineDAO {

    @Autowired
    private SessionFactory sessionFactory;

    public void saveOrderLine(OrderLine orderLine) {
        getCurrentSession().save(orderLine);
    }

    public void saveOrUpdateOrderLine(OrderLine orderLine){
        if(getCurrentSession().contains(orderLine)){
            getCurrentSession().merge(orderLine);
        } else {
            getCurrentSession().saveOrUpdate(orderLine);
        }
    }

    public void updateOrderLine(OrderLine orderLine) {
        OrderLine orderLineToUpdate = getOrderLine(orderLine.getOrderLineId());
        orderLineToUpdate.setOrder(orderLine.getOrder());
        orderLineToUpdate.setDish(orderLine.getDish());
        orderLineToUpdate.setCount(orderLine.getCount());
        //orderLineToUpdate.setCost(orderLine.getCost());
        getCurrentSession().update(orderLineToUpdate);
    }

    public void deleteOrderLine(int orderLineID) {
        OrderLine orderLine = getOrderLine(orderLineID);
        if(orderLine != null){
            getCurrentSession().delete(orderLine);
        }
    }

    public void deleteOrderLine(OrderLine orderLine){
        getCurrentSession().delete(orderLine);
    }

    public OrderLine getOrderLine(int orderLineID) {
        return (OrderLine) getCurrentSession().get(OrderLine.class, orderLineID);
    }

    @SuppressWarnings("unchecked")
    public List<OrderLine> getAllOrderLines() {
        return getCurrentSession().createQuery("from OrderLine").list();
    }

    private Session getCurrentSession(){
        return sessionFactory.getCurrentSession();
    }
}
