package com.informsystem.view;

import com.informsystem.model.Dish;
import com.informsystem.service.DishService;
import com.informsystem.view.form.DishForm;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * Created by Den on 13.04.16.
 */
//@SpringComponent
//@UIScope
@SpringView(name = "dish")
public class DishView extends /*Panel*/VerticalLayout implements View {

    @Autowired
    private DishForm form;

    @Autowired
    private DishService service;

    private Grid grid = new Grid();
    private TextField filterText = new TextField();

    //private VerticalLayout root;
    private Label titleLabel;
    public static final String TITLE_ID = "dashboard-title";

//    public DishView(){
//        addStyleName(ValoTheme.PANEL_BORDERLESS);
//        setSizeFull();
//        //root = new VerticalLayout();
//        setSizeFull();
//        setMargin(true);
//        //setContent(root);
//    }

    @PostConstruct
    public void init(){
        addStyleName(ValoTheme.PANEL_BORDERLESS);
        setMargin(true);
        setSpacing(true);

        HorizontalLayout header = new HorizontalLayout();
        header.addStyleName("viewheader");
        header.setSpacing(true);

        titleLabel = new Label("Operations with Dishes");
        titleLabel.setId(TITLE_ID);
        titleLabel.setSizeUndefined();
        titleLabel.addStyleName(ValoTheme.LABEL_H1);
        titleLabel.addStyleName(ValoTheme.LABEL_NO_MARGIN);
        header.addComponent(titleLabel);
        addComponent(header);

        form.setDishLayout(this);

        filterText.setInputPrompt("filter by name...");
        filterText.addTextChangeListener(e -> {
            grid.setContainerDataSource(new BeanItemContainer<>(Dish.class, service.findAll(e.getText())));
        });

        Button clearFilterTextBtn = new Button(FontAwesome.TIMES);
        clearFilterTextBtn.setDescription("Clear the current filter");
        clearFilterTextBtn.addClickListener(e -> {
            filterText.clear();
            updateList();
        });

        CssLayout filtering = new CssLayout();
        filtering.addComponents(filterText, clearFilterTextBtn);
        filtering.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);

        Button addCustomerBtn = new Button("Add new dish");
        addCustomerBtn.addClickListener(e -> {
            grid.select(null);
            form.setDish(new Dish());
        });

        HorizontalLayout toolbar = new HorizontalLayout(filtering, addCustomerBtn);
        toolbar.setSpacing(true);

        grid.setColumns("name", "cost");

        HorizontalLayout main = new HorizontalLayout(grid, form);
        main.setSpacing(true);
        main.setSizeFull();
        grid.setSizeFull();
        main.setExpandRatio(grid, 1);

        addComponents(toolbar, main);
        setComponentAlignment(toolbar, Alignment.TOP_LEFT);
        setComponentAlignment(main, Alignment.TOP_LEFT);

        updateList();

        form.setVisible(false);

        grid.addSelectionListener(event -> {
            if (event.getSelected().isEmpty()) {
                form.setVisible(false);
            } else {
                Dish dish = (Dish) event.getSelected().iterator().next();
                form.setDish(dish);
            }
        });
    }

    public void updateList() {
        // fetch list of Customers from service and assign it to Grid
        List<Dish> dishes = service.findAll(filterText.getValue());
        grid.setContainerDataSource(new BeanItemContainer<>(Dish.class, dishes));
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {

    }
}
