package com.informsystem.service;

import com.informsystem.dao.impl.CustomerDAOImpl;
import com.informsystem.model.Customer;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.ViewScope;
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
public class CustomerService {

    @Autowired
    private CustomerDAOImpl customerDAO;

    private static final Logger LOGGER = Logger.getLogger(CustomerService.class.getName());

    @Transactional(propagation = Propagation.REQUIRED)
    public synchronized List<Customer> findAll(String stringFilter) {
        List<Customer> customers = new ArrayList<>();
        for (Customer contact : customerDAO.getAllCustomers()) {
            try {
                boolean passesFilter = (stringFilter == null || stringFilter.isEmpty())
                        || contact.toString().toLowerCase().contains(stringFilter.toLowerCase());
                if (passesFilter) {
                    customers.add(contact.clone());
                }
            } catch (CloneNotSupportedException ex) {
            }
        }
//        Collections.sort(arrayList, new Comparator<Customer>() {
//
//            @Override
//            public int compare(Customer o1, Customer o2) {
//                return (int) (o2.getCustomerId() - o1.getCustomerId());
//            }
//        });
        return customers;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public synchronized void save(Customer entry) {
        if (entry == null) {
            LOGGER.log(Level.SEVERE,
                    "Customer is null. Are you sure you have connected your form to the application as described in tutorial chapter 7?");
            return;
        }
        customerDAO.saveOrUpdateCustomer(entry);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public synchronized void delete(Customer value) {
        customerDAO.deleteCustomer(value.getCustomerId());
    }
}
