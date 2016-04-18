package com.informsystem.service;

import com.informsystem.dao.impl.DishDAOImpl;
import com.informsystem.model.Dish;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.ViewScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Den on 04.04.16.
 */
@SpringComponent
@ViewScope
public class DishService {

    @Autowired
    private DishDAOImpl dishDAO;

    private static final Logger LOGGER = Logger.getLogger(DishService.class.getName());

    @Transactional(propagation = Propagation.REQUIRED)
    public synchronized List<Dish> findAll(String stringFilter) {
        List<Dish> dishes = new ArrayList<>();
        for (Dish dish : dishDAO.getAllDishes()) {
            try {
                boolean passesFilter = (stringFilter == null || stringFilter.isEmpty())
                        || dish.toString().toLowerCase().contains(stringFilter.toLowerCase());
                if (passesFilter) {
                    dishes.add(dish.clone());
                }
            } catch (CloneNotSupportedException ex) {
            }
        }
        return dishes;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public synchronized void save(Dish entry) {
        if (entry == null) {
            LOGGER.log(Level.SEVERE,
                    "Dish is null.");
            return;
        }
        dishDAO.saveOrUpdateDish(entry);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public synchronized void delete(Dish value) {
        dishDAO.deleteDish(value.getDishId());
    }
}
