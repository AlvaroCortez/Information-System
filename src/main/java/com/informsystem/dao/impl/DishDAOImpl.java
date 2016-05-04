package com.informsystem.dao.impl;

import com.informsystem.dao.DishDAO;
import com.informsystem.model.Dish;
import com.vaadin.spring.annotation.SpringComponent;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by Den on 21.03.16.
 */
@SpringComponent
public class DishDAOImpl implements DishDAO {

    @Autowired
    private SessionFactory sessionFactory;

    public void saveDish(Dish dish) {
        getCurrentSession().save(dish);
    }

    public void saveOrUpdateDish(Dish dish){
        if(getCurrentSession().contains(dish)){
            getCurrentSession().merge(dish);
        } else {
            getCurrentSession().saveOrUpdate(dish);
        }
    }

    public void updateDish(Dish dish) {
        Dish dishToUpdate = getDish(dish.getDishId());
        if(dishToUpdate != null){
            dishToUpdate.setName(dish.getName());
            dishToUpdate.setCost(dish.getCost());
            getCurrentSession().update(dishToUpdate);
        }
    }

    public void deleteDish(int dishID) {
        Dish dish = getDish(dishID);
        if(dish != null){
            getCurrentSession().delete(dish);
        }
    }

    public void deleteDish(Dish dish){
        getCurrentSession().delete(dish);
    }

    public Dish getDish(int dishID) {
        return (Dish) getCurrentSession().get(Dish.class, dishID);
    }

    @SuppressWarnings("unchecked")
    public List<Dish> getAllDishes() {
        return getCurrentSession().createQuery("from Dish").list();
    }

    private Session getCurrentSession(){
        return sessionFactory.getCurrentSession();
    }
}
