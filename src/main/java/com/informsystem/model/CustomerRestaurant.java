package com.informsystem.model;

import javax.persistence.*;

/**
 * Created by Den on 15.02.16.
 */
public class CustomerRestaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "customer_restaurant_sequence")
    @SequenceGenerator(name = "customer_restaurant_sequence", sequenceName = "customer_restaurant_sequence")
    private Integer customerRestaurantId;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "CUSTOMER_ID")
    private Customer customer;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "RESTAURANT_ID")
    private Restaurant restaurant;

    public Integer getCustomerRestaurantId() {
        return customerRestaurantId;
    }

    public void setCustomerRestaurantId(Integer customerRestaurantId) {
        this.customerRestaurantId = customerRestaurantId;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }
}
