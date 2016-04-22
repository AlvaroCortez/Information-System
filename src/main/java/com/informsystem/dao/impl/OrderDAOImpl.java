package com.informsystem.dao.impl;

import com.informsystem.dao.OrderDAO;
import com.informsystem.model.Order;
import com.vaadin.spring.annotation.SpringComponent;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by Den on 21.03.16.
 */
@SpringComponent
public class OrderDAOImpl implements OrderDAO {

    @Autowired
    private SessionFactory sessionFactory;

    public void saveOrder(Order order) {
        getCurrentSession().save(order);
    }

    public void saveOrUpdateOrder(Order order){
        getCurrentSession().saveOrUpdate(order);
    }

    public void updateOrder(Order order) {
//        String updateString = "update Order set customer = :customer, dateOfOrder = :date_of_order, " +
//                "numberOfProducts = :number_of_products where orderId = :orderId";
//        getCurrentSession().createQuery(updateString).setEntity("customer", order.getCustomer())
//                .setDate("date_of_order", order.getDateOfOrder())
//                .setInteger("number_of_products", order.getNumberOfProducts())
//                .setInteger("orderId", order.getOrderId())
//                .executeUpdate();
        Order orderToUpdate = getOrder(order.getOrderId());
        if(orderToUpdate != null){
            orderToUpdate.setCustomer(order.getCustomer());
            orderToUpdate.setDateOfOrder(order.getDateOfOrder());
            orderToUpdate.setNumberOfProducts(order.getNumberOfProducts());
            orderToUpdate.setCost(order.getCost());
            //getCurrentSession().flush();
            getCurrentSession().update(orderToUpdate);
        }
    }

    public void deleteOrder(int orderID) {
        Order order = getOrder(orderID);
        if(order != null){
            getCurrentSession().delete(order);
        }
    }

    public void deleteOrder(Order order){
        getCurrentSession().delete(order);
    }

    public Order getOrder(int orderID) {
        return (Order) getCurrentSession().get(Order.class, orderID);
    }

    //@Transactional
    @Override
    public List<Order> getAllOrders() {
        return getCurrentSession().createQuery("from Order").list();
    }

    private Session getCurrentSession(){
        return sessionFactory.getCurrentSession();
    }

    public SessionFactory getSessionFactory(){
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory){
        this.sessionFactory = sessionFactory;
    }
}
