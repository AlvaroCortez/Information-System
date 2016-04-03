package com.informsystem.service;

import com.informsystem.dao.CustomerDAO;
import com.informsystem.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Den on 04.04.16.
 */
@Service("customerService")
public class CustomerService {

    @Autowired
    CustomerDAO customerDAO;

    public List<Customer> getAllCustomers(){
        return customerDAO.getAllCustomers();
    }
}
