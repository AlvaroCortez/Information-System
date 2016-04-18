package com.informsystem.view.form;

import com.informsystem.model.Dish;
import com.informsystem.service.DishService;
import com.informsystem.vaadin.MyUI;
import com.informsystem.view.DishView;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.event.ShortcutAction;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.themes.ValoTheme;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

/**
 * Created by Den on 14.04.16.
 */
@SpringComponent
@UIScope
public class DishForm extends FormLayout implements View{

    @Autowired
    private DishService service;
    private Dish dish;
    private MyUI myUI;
    private DishView dishLayout;
    private TextField name = new TextField("Name of Dish");
    private TextField cost = new TextField("Cost of Dish");
    private Button save = new Button("Save");
    private Button delete = new Button("Delete");

    @PostConstruct
    void init(){
        setSizeUndefined();
        HorizontalLayout buttons = new HorizontalLayout(save, delete);
        buttons.setSpacing(true);
        HorizontalLayout fullNameDish = new HorizontalLayout(name, cost);
        //sex.addValidator(new StringLengthValidator("The sex must contains one char symbol: M or F",1,1,true));
        name.setNullRepresentation("");
        cost.setNullRepresentation("");
        addComponents(fullNameDish, buttons);
        save.setClickShortcut(ShortcutAction.KeyCode.ENTER);
        save.setStyleName(ValoTheme.BUTTON_PRIMARY);
        delete.setStyleName(ValoTheme.BUTTON_DANGER);
        save.addClickListener(e -> this.save());
        delete.addClickListener(e -> this.delete());
    }

    public void setDish(Dish dish) {
        //if(dish == null){
        //    dish = new Customer();
        //} else {
        this.dish = dish;
        //}
        BeanFieldGroup.bindFieldsUnbuffered(dish, this);

        // Show delete button for only customers already in the database
        delete.setVisible(dish.isPersisted());
        //delete.setVisible(true);
        setVisible(true);
        name.selectAll();
    }

    private void delete() {
        service.delete(dish);
        dishLayout.updateList();
        setVisible(false);
    }

    public DishView getDishLayout() {
        return dishLayout;
    }

    public void setDishLayout(DishView dishLayout) {
        this.dishLayout = dishLayout;
    }

    private void save() {
        service.save(dish);
        dishLayout.updateList();
        setVisible(false);
    }

    public MyUI getMyUI() {
        return myUI;
    }

    public void setMyUI(MyUI myUI) {
        this.myUI = myUI;
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {

    }
}
