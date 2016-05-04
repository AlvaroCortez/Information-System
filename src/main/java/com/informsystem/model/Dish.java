package com.informsystem.model;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Den on 21.03.16.
 */
@Entity
@Table
public class Dish implements Serializable, Cloneable{

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

    public boolean isPersisted() {
        return dishId != null;
    }

//    @Override
//    public boolean equals(Object obj) {
//        if (this == obj) {
//            return true;
//        }
//        if (this.dishId == null) {
//            return false;
//        }
//
//        if (obj instanceof Dish && obj.getClass().equals(getClass())) {
//            return this.dishId.equals(((Dish) obj).dishId);
//        }
//
//        return false;
//    }
//
//    @Override
//    public int hashCode() {
//        int hash = 5;
//        hash = 43 * hash + (dishId == null ? 0 : dishId.hashCode());
//        return hash;
//    }
//
//    @Override
//    public Dish clone() throws CloneNotSupportedException {
//        return (Dish) super.clone();
//    }

    @Override
    public String toString() {
        return name;
    }
}