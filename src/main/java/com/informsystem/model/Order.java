package com.informsystem.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Den on 15.02.16.
 */
@Entity
@Table(name = "ORDERS")
public class Order implements Serializable, Cloneable{

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_sequence")
    @SequenceGenerator(name = "order_sequence", sequenceName = "order_sequence")
    @Column(name = "ORDER_ID")
    private Integer orderId;

    @ManyToOne(/*cascade = CascadeType.ALL,*/ fetch = FetchType.LAZY)
    @JoinColumn(name = "CUSTOMER_ID", foreignKey = @ForeignKey(name = "RefORDtoCUS"))
    private Customer customer;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderLine> orderLineList = new ArrayList<OrderLine>();

    @Column(name = "DATE_OF_ORDER")
    @Temporal(TemporalType.DATE)
    private Date dateOfOrder;

    @Column(name = "NUMBER_OF_PRODUCTS")
    private Integer numberOfProducts;

    //@Column(name = "COST")
    @Transient
    private Integer cost;

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

    public Integer getCost() {
        return cost;
    }

    public void setCost(Integer cost) {
        this.cost = cost;
    }

    public List<OrderLine> getOrderLineList() {
        return orderLineList;
    }

    public void setOrderLineList(List<OrderLine> orderLineList) {
        this.orderLineList = orderLineList;
    }

    public boolean isPersisted() {
        return orderId != null;
    }

//    @Override
//    public boolean equals(Object obj) {
//        if (this == obj) {
//            return true;
//        }
//        if (this.orderId == null) {
//            return false;
//        }
//
//        if (obj instanceof Order && obj.getClass().equals(getClass())) {
//            return this.orderId.equals(((Order) obj).orderId);
//        }
//
//        return false;
//    }

//    @Override
//    public int hashCode() {
//        int hash = 5;
//        hash = 43 * hash + (orderId == null ? 0 : orderId.hashCode());
//        return hash;
//    }
//
//    @Override
//    public Order clone() throws CloneNotSupportedException {
//        return (Order) super.clone();
//    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                //", customer=" + customer.toString() +
                ", dateOfOrder=" + dateOfOrder +
                ", numberOfProducts=" + numberOfProducts +
                ", cost=" + cost +
                '}';
    }
}
