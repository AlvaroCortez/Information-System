package com.informsystem.model;

import org.hibernate.annotations.Check;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Den on 15.02.16.
 */
@Entity
@Table(name = "RESTAURANT")
public class Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "restaurant_sequence")
    @SequenceGenerator(name = "restaurant_sequence", sequenceName = "restaurant_sequence")
    @Column(name = "RESTAURANT_ID")
    private Integer restaurantId;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "CUSTOMER_RESTAURANT",
    joinColumns = @JoinColumn(name = "RESTAURANT_ID"),
    inverseJoinColumns = @JoinColumn(name = "CUSTOMER_ID"))
    private List<Customer> customers = new ArrayList<Customer>();

    @Column(name = "NAME")
    private String name;

    @Column(name = "ADDRESS")
    private String address;

    @Column(name = "RATING")
    @Check(constraints = "RATING BETWEEN 1 AND 10")
    private Integer rating;

    @Column(name = "KITCHEN_TYPE")
    private String kitchenType;

    public Integer getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(Integer restaurantId) {
        this.restaurantId = restaurantId;
    }

    public List<Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(List<Customer> customers) {
        this.customers = customers;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getKitchenType() {
        return kitchenType;
    }

    public void setKitchenType(String kitchenType) {
        this.kitchenType = kitchenType;
    }

    @Override
    public String toString() {
        return "Restaurant{" +
                "restaurantId=" + restaurantId +
                //", customers=" + customers +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", rating=" + rating +
                ", kitchenType='" + kitchenType + '\'' +
                '}';
    }
}
