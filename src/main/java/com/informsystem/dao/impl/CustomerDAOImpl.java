package com.informsystem.dao.impl;

import com.informsystem.dao.CustomerDAO;
import com.informsystem.model.Customer;
import com.vaadin.spring.annotation.SpringComponent;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by Den on 21.03.16.
 */
@SpringComponent
public class CustomerDAOImpl implements CustomerDAO {

    @Autowired
    private SessionFactory sessionFactory;

    public void saveCustomer(Customer customer) {
        getCurrentSession().save(customer);
    }

    public void saveOrUpdateCustomer(Customer customer){
        getCurrentSession().saveOrUpdate(customer);
    }

    public void updateCustomer(Customer customer) {
        Customer customerToUpdate = getCustomer(customer.getCustomerId());
        if(customerToUpdate != null) {
            customerToUpdate.setName(customer.getName());
            customerToUpdate.setSurname(customer.getSurname());
            customerToUpdate.setAge(customer.getAge());
            customerToUpdate.setOrders(customer.getOrders());
            customerToUpdate.setSex(customer.getSex());
            getCurrentSession().update(customerToUpdate);
        }
    }

    public void deleteCustomer(int customerID) {
        Customer customer = getCustomer(customerID);
        if(customer != null){
            getCurrentSession().delete(customer);
        }
    }

    public void deleteCustomer(Customer customer){
        getCurrentSession().delete(customer);
    }

    public Customer getCustomer(int customerID) {
        return (Customer) getCurrentSession().get(Customer.class, customerID);
    }

    @SuppressWarnings("unchecked")
    public List<Customer> getAllCustomers() {
        return getCurrentSession().createQuery("from Customer").list();
    }

    private Session getCurrentSession(){
        return sessionFactory.getCurrentSession();
    }
}
