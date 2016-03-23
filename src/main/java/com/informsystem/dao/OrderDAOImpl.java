package com.informsystem.dao;

import com.informsystem.model.Order;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

/**
 * Created by Den on 21.03.16.
 */
public class OrderDAOImpl implements OrderDAO{

    private SessionFactory sessionFactory;

    public void saveOrder(Order order) {
        getCurrentSession().save(order);
    }

    public void updateOrder(Order order) {
        Order orderToUpdate = getOrder(order.getOrderId());
        if(orderToUpdate != null){
            orderToUpdate.setCustomer(order.getCustomer());
            orderToUpdate.setDateOfOrder(order.getDateOfOrder());
            orderToUpdate.setNumberOfProducts(order.getNumberOfProducts());
            orderToUpdate.setCost(order.getCost());
            getCurrentSession().update(orderToUpdate);
        }
    }

    public void deleteOrder(int orderID) {
        Order order = getOrder(orderID);
        if(order != null){
            getCurrentSession().delete(order);
        }
    }

    public Order getOrder(int orderID) {
        return (Order) getCurrentSession().get(Order.class, orderID);
    }

    @SuppressWarnings("unchecked")
    public List<Order> getAllOrders() {
        return getCurrentSession().createQuery("from Order").list();
    }

    private Session getCurrentSession(){
        return sessionFactory.getCurrentSession();
    }
}
