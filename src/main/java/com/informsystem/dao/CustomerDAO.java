package com.informsystem.dao;

import com.informsystem.model.Customer;

import java.util.List;

/**
 * Created by Den on 21.03.16.
 */
public interface CustomerDAO {

    public void saveCustomer(Customer customer);

    public void updateCustomer(Customer customer);

    public void deleteCustomer(int customerID);

    public Customer getCustomer(int customerID);

    public List<Customer> getAllCustomers();
}
