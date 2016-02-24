package com.informsystem.model;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Den on 15.02.16.
 */
@Entity
@Table(name = "ORDERS")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_sequence")
    @SequenceGenerator(name = "order_sequence", sequenceName = "order_sequence")
    @Column(name = "ORDER_ID")
    private Integer orderId;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "CUSTOMER_ID", foreignKey = @ForeignKey(name = "RefORDtoCUS"))
    private Customer customer;

    @Column(name = "DATE_OF_ORDER")
    @Temporal(TemporalType.DATE)
    private Date dateOfOrder;

    @Column(name = "NUMBER_OF_PRODUCTS")
    private Integer numberOfProducts;

    @Column(name = "ORDER_AMOUNT")
    private Integer orderAmount;

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Date getDateOfOrder() {
        return dateOfOrder;
    }

    public void setDateOfOrder(Date dateOfOrder) {
        this.dateOfOrder = dateOfOrder;
    }

    public Integer getNumberOfProducts() {
        return numberOfProducts;
    }

    public void setNumberOfProducts(Integer numberOfProducts) {
        this.numberOfProducts = numberOfProducts;
    }

    public Integer getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(Integer orderAmount) {
        this.orderAmount = orderAmount;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                //", customer=" + customer.toString() +
                ", dateOfOrder=" + dateOfOrder +
                ", numberOfProducts=" + numberOfProducts +
                ", orderAmount=" + orderAmount +
                '}';
    }
}
