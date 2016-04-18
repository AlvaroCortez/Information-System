package com.informsystem.view;

import com.informsystem.model.Order;
import com.informsystem.model.OrderLine;
import com.informsystem.service.OrderLineService;
import com.informsystem.service.OrderService;
import com.informsystem.view.form.OrderLineForm;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Den on 11.04.16.
 */
//@SpringComponent
//@UIScope
@SpringView(name = "order")
public class OrderView extends Panel /*VerticalLayout*/ implements View{

    @Autowired
    private OrderLineForm form;

    @Autowired
    private OrderLineService orderLineService;

    @Autowired
    private OrderService orderService;

    private Grid grid = new Grid();
    private TextField filterText = new TextField();
    public static final String TITLE_ID = "dashboard-title";

    @PostConstruct
    void init(){
        setSizeFull();//not know
        addStyleName(ValoTheme.PANEL_BORDERLESS);
        VerticalLayout layout = new VerticalLayout();
        layout.setSizeFull();//not know
        layout.setMargin(true);

        HorizontalLayout header = new HorizontalLayout();
        header.addStyleName("viewheader");
        header.setSpacing(true);

        Label titleLabel = new Label("Operations with Orders");
        titleLabel.setId(TITLE_ID);
        titleLabel.setSizeUndefined();
        titleLabel.addStyleName(ValoTheme.LABEL_H1);
        titleLabel.addStyleName(ValoTheme.LABEL_NO_MARGIN);
        header.addComponent(titleLabel);
        layout.addComponent(header);

        //form.setMyUI();
        form.setOrderLayout(this);

        filterText.setInputPrompt("filter by name...");
        filterText.addTextChangeListener(e -> {
            grid.setContainerDataSource(new BeanItemContainer<>(Order.class, orderService.findAll(e.getText())));
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

        Button addCustomerBtn = new Button("Create new order");
        addCustomerBtn.addClickListener(e -> {
            grid.select(null);
            form.setOrder(new Order(), new ArrayList<OrderLine>());
        });

        HorizontalLayout toolbar = new HorizontalLayout(filtering, addCustomerBtn);
        toolbar.setSpacing(true);

        grid.setColumns("orderId", "customer", "dateOfOrder", "numberOfProducts", "cost");

        HorizontalLayout main = new HorizontalLayout(grid, form);
        main.setSpacing(true);
        main.setSizeFull();
        grid.setSizeFull();
        main.setExpandRatio(grid, 1);

        layout.addComponents(toolbar, main);

        updateList();

        //layout.setMargin(true);
        //layout.setSpacing(true);
        setContent(layout);

        form.setVisible(false);

        grid.addSelectionListener(event -> {
            if (event.getSelected().isEmpty()) {
                form.setVisible(false);
            } else {
                Order order = (Order) event.getSelected().iterator().next();
                form.setOrder(order, order.getOrderLineList());
            }
        });
        //MainMenu mainMenu = new MainMenu();
        //addComponent(mainMenu);
    }

    public void updateList() {
        // fetch list of Customers from service and assign it to Grid
        List<Order> orders = orderService.findAll(filterText.getValue());
        //BeanItemContainer beanItemContainer = new BeanItemContainer(Order.class, orders);
        //beanItemContainer.getContainerProperty(1, "customer").setValue(orderService);
        grid.setContainerDataSource(new BeanItemContainer<>(Order.class, orders));
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {

    }
}
