package com.informsystem.service;

import com.informsystem.dao.DishDAO;
import com.informsystem.model.Dish;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Den on 04.04.16.
 */
@Service("dishService")
public class DishService {

    @Autowired
    DishDAO dishDAO;

    public List<Dish> getAllDishes(){
        return dishDAO.getAllDishes();
    }
}
