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
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.GeneratedPropertyContainer;
import com.vaadin.data.util.PropertyValueGenerator;
import com.vaadin.event.ShortcutAction;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.Scrollable;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.*;
import com.vaadin.ui.renderers.ButtonRenderer;
import com.vaadin.ui.themes.ValoTheme;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Den on 17.04.16.
 */
@SpringComponent
@UIScope
public class OrderLineForm extends FormLayout implements View, Scrollable {

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
    private Dish selectedDish;
    private MyUI myUI;
    private OrderView orderLayout;
    //private TextField orderId = new TextField("Order ID");
    private ComboBox customer = new ComboBox("Customer of Order");
    private ComboBox orderBox = new ComboBox("Orders");
    private ComboBox dish = new ComboBox("Dish");
    private TextField count = new TextField("Count of Dish");
    private InlineDateField dateOfOrder = new InlineDateField("Date of Order");
    //private TextField numberOfProducts = new TextField("NumberOf Products");
    private Integer numberOfProductsInt = 0;
    private TextField cost = new TextField("Cost of Order");
    private Button save = new Button("Save");
    private Button delete = new Button("Delete");
    private Button plus = new Button("+");
    private Label orderLinesLabel = new Label("Order lines:");
    //private Button minus = new Button("-");
    private VerticalLayout container = new VerticalLayout();
    private Grid grid = new Grid();
    private GeneratedPropertyContainer gpc;
    private List<OrderLine> deleteOrderLines = new ArrayList<>();

    @PostConstruct
    void init(){
        setSizeUndefined();
        HorizontalLayout buttons = new HorizontalLayout(save, delete);
        buttons.setSpacing(true);
        HorizontalLayout fullNameDish = new HorizontalLayout(customer, dateOfOrder);
        //name.setNullRepresentation("");
        cost.setNullRepresentation("");
        container.addComponent(fullNameDish);
        container.addComponent(orderLinesLabel);
        container.addComponent(grid);
        HorizontalLayout fillOrderLine = new HorizontalLayout(dish, count);
        container.addComponent(fillOrderLine);
        container.addComponent(plus);
        container.addComponent(buttons);
        addComponents(container);

//        dish.setRequired(true);
//        dish.setRequiredError("Choose dish please");
//        dish.setImmediate(true);

        //addComponent(grid);
        save.setClickShortcut(ShortcutAction.KeyCode.ENTER);
        save.setStyleName(ValoTheme.BUTTON_PRIMARY);
        delete.setStyleName(ValoTheme.BUTTON_DANGER);
        plus.addStyleName(ValoTheme.BUTTON_BORDERLESS_COLORED);
        save.addClickListener(e -> this.save());
        delete.addClickListener(e -> this.delete());
        plus.addClickListener(e -> this.plus());
        //minus.addClickListener(e -> this.minus());
        //grid.setColumns("dish", "count");
        grid.addSelectionListener(event -> {
            if (event.getSelected().isEmpty()) {
                //form.setVisible(false);
            } else {
                OrderLine orderLine = (OrderLine) event.getSelected().iterator().next();
                //form.setOrder(order, order.getOrderLineList());
            }
        });
        //grid.setEditorEnabled(true);
    }

    public void setOrder(Order order, List<OrderLine> orderLines) {
        this.order = order;
        this.orderLines = orderLines;
        BeanFieldGroup.bindFieldsUnbuffered(order, this);
        dateOfOrder.setValue(order.getDateOfOrder());
        //customer.setValue(order.getCustomer().getSurname());

        List<Customer> customers = customerService.findAll(null);
        customer.setContainerDataSource(new BeanItemContainer<>(Customer.class, customers));
        customer.setItemCaptionMode(AbstractSelect.ItemCaptionMode.PROPERTY);
        customer.setItemCaptionPropertyId("surname");

        List<Dish> dishes = dishService.findAll(null);
        dish.setContainerDataSource(new BeanItemContainer<>(Dish.class, dishes));
        dish.setItemCaptionMode(AbstractSelect.ItemCaptionMode.PROPERTY);
        dish.setItemCaptionPropertyId("name");
        dish.addValueChangeListener(new Property.ValueChangeListener() {
            @Override
            public void valueChange(Property.ValueChangeEvent valueChangeEvent) {
                if(valueChangeEvent.getProperty().getValue() != null){
                    selectedDish = (Dish) valueChangeEvent.getProperty().getValue();
                }
            }
        });

        gpc = new GeneratedPropertyContainer(new BeanItemContainer(OrderLine.class, orderLines));
        gpc.addGeneratedProperty("delete", new PropertyValueGenerator<String>() {
            @Override
            public String getValue(Item item, Object o, Object o1) {
                return "Delete";
            }

            @Override
            public Class<String> getType() {
                return String.class;
            }
        });
        grid.setContainerDataSource(gpc);
        grid.getColumn("delete").setRenderer(new ButtonRenderer(e -> {
            grid.getContainerDataSource().removeItem(e.getItemId());
            //orderLines.remove(e.getItemId());
            deleteOrderLines.add((OrderLine) e.getItemId());
        }));
        grid.setColumns("dish", "count", "delete");
        //grid.setContainerDataSource(new BeanItemContainer(OrderLine.class, orderLines));

        // Show delete button for only customers already in the database
        delete.setVisible(order.isPersisted());
        //delete.setVisible(true);
        setVisible(true);
        cost.selectAll();
        count.setValue("");
        selectedDish = null;
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
        if (orderLines.size() == 0){
            Notification.show("Cannot create or update order without order lines");
        } else {
            //numberOfProductsInt = order.getNumberOfProducts();
            //if(numberOfProductsInt == null){
            //    numberOfProductsInt = 0;
            //}
            order.setOrderLineList(orderLines);
            for (OrderLine orderLine : orderLines) {
                //orderLineService.save(orderLine);
                numberOfProductsInt += orderLine.getCount();
            }
            for (OrderLine deleteOrderLine : deleteOrderLines) {
                numberOfProductsInt -= deleteOrderLine.getCount();
                //orderLineService.delete(deleteOrderLine);
            }
            order.setNumberOfProducts(numberOfProductsInt);
            orderService.save(order);
            for (OrderLine orderLine : orderLines) {
                orderLineService.save(orderLine);
                //numberOfProductsInt += orderLine.getCount();
            }
            for (OrderLine deleteOrderLine : deleteOrderLines) {
                //numberOfProductsInt -= deleteOrderLine.getCount();
                orderLineService.delete(deleteOrderLine);
            }
            orderLayout.updateList();
            setVisible(false);
        }
        numberOfProductsInt = 0;
        count.setValue("");
        selectedDish = null;
        deleteOrderLines.clear();
    }

    private void plus(){
        if(count.getValue() == null || count.getValue().isEmpty()){
            Notification.show("Enter count of dish");
        } else if(selectedDish == null){
            Notification.show("Choose dish please");
        } else {
            OrderLine orderLine = new OrderLine();
            orderLine.setDish(selectedDish);
            try {
                orderLine.setCount(Integer.parseInt(count.getValue()));
            } catch (NumberFormatException e) {

            }
            orderLine.setOrder(order);
            orderLines.add(orderLine);
            //grid.setContainerDataSource(new BeanItemContainer(OrderLine.class, orderLines));
            gpc = new GeneratedPropertyContainer(new BeanItemContainer(OrderLine.class, orderLines));
            gpc.addGeneratedProperty("delete", new PropertyValueGenerator<String>() {
                @Override
                public String getValue(Item item, Object o, Object o1) {
                    return "Delete";
                }

                @Override
                public Class<String> getType() {
                    return String.class;
                }
            });
            grid.setContainerDataSource(gpc);
            count.setValue("");
            dish.setValue(null);
            selectedDish = null;
        }
    }

    private void minus(){

    }

    public void setOrderLayout(OrderView orderLayout) {
        this.orderLayout = orderLayout;
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {

    }

    @Override
    public int getScrollLeft() {
        return 0;
    }

    @Override
    public void setScrollLeft(int i) {

    }

    @Override
    public int getScrollTop() {
        return 0;
    }

    @Override
    public void setScrollTop(int i) {

    }
}
