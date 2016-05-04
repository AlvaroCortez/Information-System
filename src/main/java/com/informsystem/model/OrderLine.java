package com.informsystem.model;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Den on 21.03.16.
 */
@Entity
@Table(name = "ORDER_LINE")
public class OrderLine  implements Serializable, Cloneable{

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_line_sequence")
    @SequenceGenerator(name = "order_line_sequence", sequenceName = "order_line_sequence", allocationSize = 1)
    @Column(name = "ORDER_LINE_ID")
    private Integer orderLineId;

    @ManyToOne(/*cascade = CascadeType.ALL, */fetch = FetchType.LAZY)
    @JoinColumn(name = "ORDER_ID", foreignKey = @ForeignKey(name = "RefOLtoORD"))
    private Order order;

    @ManyToOne(/*cascade = CascadeType.ALL,*/ fetch = FetchType.LAZY)
    @JoinColumn(name = "DISH_ID", foreignKey = @ForeignKey(name = "RefOLtoDISH"))
    private Dish dish;

    @Column(name = "COUNT")
    private Integer count;

//    @Column(name = "COST")
//    private Integer cost;

    public Integer getOrderLineId() {
        return orderLineId;
    }

    public void setOrderLineId(Integer orderLineId) {
        this.orderLineId = orderLineId;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Dish getDish() {
        return dish;
    }

    public void setDish(Dish dish) {
        this.dish = dish;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

//    public Integer getCost() {
//        return cost;
//    }
//
//    public void setCost(Integer cost) {
//        this.cost = cost;
//    }

    public boolean isPersisted() {
        return orderLineId != null;
    }
//
//    @Override
//    public boolean equals(Object obj) {
//        if (this == obj) {
//            return true;
//        }
//        if (this.orderLineId == null) {
//            return false;
//        }
//
//        if (obj instanceof OrderLine && obj.getClass().equals(getClass())) {
//            return this.orderLineId.equals(((OrderLine) obj).orderLineId);
//        }
//
//        return false;
//    }
//
//    @Override
//    public int hashCode() {
//        int hash = 5;
//        hash = 43 * hash + (orderLineId == null ? 0 : orderLineId.hashCode());
//        return hash;
//    }
//
//    @Override
//    public OrderLine clone() throws CloneNotSupportedException {
//        return (OrderLine) super.clone();
//    }

    @Override
    public String toString() {
        return "OrderLine{" +
                "orderLineId=" + orderLineId +
                ", count=" + count +
//                ", cost=" + cost +
                '}';
    }
}
