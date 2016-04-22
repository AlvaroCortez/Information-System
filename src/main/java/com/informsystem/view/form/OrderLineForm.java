package com.informsystem.view.form;

import com.informsystem.model.Customer;
import com.informsystem.model.Dish;
import com.informsystem.model.Order;
import com.informsystem.model.OrderLine;
import com.informsystem.service.CustomerService;
import com.informsystem.service.DishService;
import com.informsystem.service.OrderLineService;
import com.informsystem.service.OrderService;
import com.informsystem.vaadin.MyUI;
import com.informsystem.view.OrderView;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.ShortcutAction;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * Created by Den on 17.04.16.
 */
@SpringComponent
@UIScope
public class OrderLineForm extends FormLayout implements View {

    @Autowired
    private OrderLineService orderLineService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private DishService dishService;
    private OrderLine orderLine;
    private List<OrderLine> orderLines;
    private Order order;
    private MyUI myUI;
    private OrderView orderLayout;
    //private TextField orderId = new TextField("Order ID");
    private ComboBox customer = new ComboBox("Customer of Order");
    private ComboBox orderBox = new ComboBox("Orders");
    private ComboBox dish = new ComboBox("Dish");
    private TextField count = new TextField("Count of Dish");
    private TextField dataOfOrder = new TextField("Date of Order");
    private TextField numberOfProducts = new TextField("NumberOf Products");
    private TextField cost = new TextField("Cost of Order");
    private Button save = new Button("Save");
    private Button delete = new Button("Delete");
    private Button plus = new Button("+");
    private Label orderLinesLabel = new Label("Order lines:");
    //private Button minus = new Button("-");
    private FormLayout container = new FormLayout();
    private Grid grid = new Grid();

    @PostConstruct
    void init(){
        setSizeUndefined();
        HorizontalLayout buttons = new HorizontalLayout(save, delete);
        buttons.setSpacing(true);
        HorizontalLayout fullNameDish = new HorizontalLayout(customer, dataOfOrder, numberOfProducts);
        //name.setNullRepresentation("");
        cost.setNullRepresentation("");
        container.addComponent(fullNameDish);
        container.addComponent(orderLinesLabel);
        container.addComponent(grid);
        HorizontalLayout fillOrderLine = new HorizontalLayout(dish, count);
        container.addComponent(fillOrderLine);
        container.addComponent(plus);
        addComponents(container, buttons);
        //addComponent(grid);
        save.setClickShortcut(ShortcutAction.KeyCode.ENTER);
        save.setStyleName(ValoTheme.BUTTON_PRIMARY);
        delete.setStyleName(ValoTheme.BUTTON_DANGER);
        plus.addStyleName(ValoTheme.BUTTON_BORDERLESS_COLORED);
        save.addClickListener(e -> this.save());
        delete.addClickListener(e -> this.delete());
        plus.addClickListener(e -> this.plus());
        //minus.addClickListener(e -> this.minus());
        grid.setColumns("dish", "count");
        grid.addSelectionListener(event -> {
            if (event.getSelected().isEmpty()) {
                //form.setVisible(false);
            } else {
                OrderLine orderLine = (OrderLine) event.getSelected().iterator().next();
                //form.setOrder(order, order.getOrderLineList());
            }
        });
    }

    public void setOrder(Order order, List<OrderLine> orderLines) {
        this.order = order;
        this.orderLines = orderLines;
        BeanFieldGroup.bindFieldsUnbuffered(order, this);

        List<Customer> customers = customerService.findAll(null);
        customer.setContainerDataSource(new BeanItemContainer<>(Customer.class, customers));
        customer.setItemCaptionMode(AbstractSelect.ItemCaptionMode.PROPERTY);
        customer.setItemCaptionPropertyId("surname");

        List<Dish> dishes = dishService.findAll(null);
        dish.setContainerDataSource(new BeanItemContainer<>(Dish.class, dishes));
        dish.setItemCaptionMode(AbstractSelect.ItemCaptionMode.PROPERTY);
        dish.setItemCaptionPropertyId("name");

        grid.setContainerDataSource(new BeanItemContainer<>(OrderLine.class, orderLines));

        // Show delete button for only customers already in the database
        delete.setVisible(order.isPersisted());
        //delete.setVisible(true);
        setVisible(true);
        cost.selectAll();
    }

    private void delete() {
        orderService.delete(order);
        for (OrderLine orderLine : orderLines) {
            orderLineService.delete(orderLine);
        }
        orderLayout.updateList();
        setVisible(false);
    }

    private void save() {
//        if(order.isPersisted()) {
//            orderService.update(order);
//        } else {
//            orderService.save(order);
//        }
        orderService.save(order);
        for (OrderLine orderLine : orderLines) {
            orderLineService.save(orderLine);
        }
        orderLayout.updateList();
        setVisible(false);
    }

    private void plus(){

    }

    private void minus(){

    }

    public void setOrderLayout(OrderView orderLayout) {
        this.orderLayout = orderLayout;
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {

    }
}
