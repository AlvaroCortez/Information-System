package com.informsystem.dao;

import com.informsystem.model.Dish;

import java.util.List;

/**
 * Created by Den on 21.03.16.
 */
public interface DishDAO {

    public void saveDish(Dish dish);

    public void updateDish(Dish dish);

    public void deleteDish(int dishID);

    public Dish getDish(int dishID);

    public List<Dish> getAllDishes();
}
