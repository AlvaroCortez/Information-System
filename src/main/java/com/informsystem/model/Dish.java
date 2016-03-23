package com.informsystem.model;

import javax.persistence.*;

/**
 * Created by Den on 21.03.16.
 */
@Entity
@Table
public class Dish {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "dish_sequence")
    @SequenceGenerator(name = "dish_sequence", sequenceName = "dish_sequence")
    @Column(name = "DISH_ID")
    private Integer dishId;

    @Column(name = "NAME")
    private String name;

    @Column(name = "COST")
    private Integer cost;

    public Integer getDishId() {
        return dishId;
    }

    public void setDishId(Integer dishId) {
        this.dishId = dishId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCost() {
        return cost;
    }

    public void setCost(Integer cost) {
        this.cost = cost;
    }

    @Override
    public String toString() {
        return "Dish{" +
                "dishId=" + dishId +
                ", name='" + name + '\'' +
                ", cost=" + cost +
                '}';
    }
}
