package com.informsystem.dao;

import com.informsystem.model.Dish;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

/**
 * Created by Den on 21.03.16.
 */
public class DishDAOImpl implements DishDAO{

    private SessionFactory sessionFactory;

    public void saveDish(Dish dish) {
        getCurrentSession().save(dish);
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
