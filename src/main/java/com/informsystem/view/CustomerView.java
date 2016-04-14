package com.informsystem.view;

import com.informsystem.model.Customer;
import com.informsystem.service.CustomerService;
import com.informsystem.view.form.CustomerForm;
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
 * Created by Den on 11.04.16.
 */
//@SpringComponent
//@UIScope
@SpringView(name = "customer")
public class CustomerView extends Panel /*VerticalLayout*/ implements View{

    @Autowired
    private CustomerForm form;

    @Autowired
    private CustomerService service;

    private Grid grid = new Grid();
    private TextField filterText = new TextField();
    private Label titleLabel;
    public static final String TITLE_ID = "dashboard-title";

//    public CustomerLayout(){
//
//    }

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

        titleLabel = new Label("Operations with Customers");
        titleLabel.setId(TITLE_ID);
        titleLabel.setSizeUndefined();
        titleLabel.addStyleName(ValoTheme.LABEL_H1);
        titleLabel.addStyleName(ValoTheme.LABEL_NO_MARGIN);
        header.addComponent(titleLabel);
        layout.addComponent(header);

        //form.setMyUI();
        form.setCustomerLayout(this);

        filterText.setInputPrompt("filter by name...");
        filterText.addTextChangeListener(e -> {
            grid.setContainerDataSource(new BeanItemContainer<>(Customer.class, service.findAll(e.getText())));
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

        Button addCustomerBtn = new Button("Add new customer");
        addCustomerBtn.addClickListener(e -> {
            grid.select(null);
            form.setCustomer(new Customer());
        });

        HorizontalLayout toolbar = new HorizontalLayout(filtering, addCustomerBtn);
        toolbar.setSpacing(true);

        grid.setColumns("name", "surname", "age", "sex");

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
                Customer customer = (Customer) event.getSelected().iterator().next();
                form.setCustomer(customer);
            }
        });
        //MainMenu mainMenu = new MainMenu();
        //addComponent(mainMenu);
    }

    public void updateList() {
        // fetch list of Customers from service and assign it to Grid
        List<Customer> customers = service.findAll(filterText.getValue());
        grid.setContainerDataSource(new BeanItemContainer<>(Customer.class, customers));
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {

    }
}
