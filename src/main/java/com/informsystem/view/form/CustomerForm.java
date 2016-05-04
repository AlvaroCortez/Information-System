package com.informsystem.view.form;

import com.informsystem.model.Customer;
import com.informsystem.service.CustomerService;
import com.informsystem.vaadin.MyUI;
import com.informsystem.view.CustomerView;
import com.vaadin.data.Validator;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.validator.IntegerRangeValidator;
import com.vaadin.event.ShortcutAction;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

@SpringComponent
@UIScope
//@SpringUI
//@SpringView(name = CustomerForm.VIEW_NAME)
//@DesignRoot
public class CustomerForm extends FormLayout implements View{//extends CustomerFormDesign {

    public static final String VIEW_NAME = "view";

    @Autowired
    CustomerService service;
    private Customer customer;
    private MyUI myUI;
    private CustomerView customerLayout;
    private TextField name = new TextField("First Name");
    private TextField surname = new TextField("Last Name");
    private TextField age = new TextField("Age");
    //private TextField sex = new TextField("Sex");
    private OptionGroup sex = new OptionGroup("Sex");
    private Button save = new Button("Save");
    private Button delete = new Button("Delete");

    @PostConstruct
    void init(){
        setSizeUndefined();
        HorizontalLayout buttons = new HorizontalLayout(save, delete);
        buttons.setSpacing(true);
        HorizontalLayout fullNameCustomer = new HorizontalLayout(name, surname);
        //sex.addValidator(new StringLengthValidator("The sex must contains one char symbol: M or F",1,1,true));
        sex.addItems('M', 'F');
        sex.setRequired(true);
        sex.setRequiredError("Choose sex of customer");
        sex.setImmediate(true);
        sex.addStyleName("horizontal");
        sex.setNullSelectionAllowed(false);
        //sex.setValue('M');
        //sex.select(0);
        age.setRequired(true);
        age.setRequiredError("Enter age please");
        age.setImmediate(true);
        age.addValidator(new IntegerRangeValidator("The age must between 0-120 (was {0})", 0, 120));
        age.setNullRepresentation("");
        name.setRequired(true);
        name.setRequiredError("Enter name please");
        name.setImmediate(true);
        name.setNullRepresentation("");
        surname.setNullRepresentation("");
        surname.setRequired(true);
        surname.setRequiredError("Enter surname please");
        surname.setImmediate(true);
        addComponents(fullNameCustomer, age, sex, buttons);
        save.setClickShortcut(ShortcutAction.KeyCode.ENTER);
        save.setStyleName(ValoTheme.BUTTON_PRIMARY);
        delete.setStyleName(ValoTheme.BUTTON_DANGER);
        save.addClickListener(e -> this.save());
        delete.addClickListener(e -> this.delete());
    }

    public void setCustomer(Customer customer) {
        //if(customer == null){
        //    customer = new Customer();
        //} else {
            this.customer = customer;
        //}
        BeanFieldGroup.bindFieldsUnbuffered(customer, this);

        // Show delete button for only customers already in the database
        delete.setVisible(customer.isPersisted());
        //delete.setVisible(true);
        setVisible(true);
        name.selectAll();
    }

    private void delete() {
        service.delete(customer);
        customerLayout.updateList();
        setVisible(false);
    }

    public CustomerView getCustomerLayout() {
        return customerLayout;
    }

    public void setCustomerLayout(CustomerView customerLayout) {
        this.customerLayout = customerLayout;
    }

    private void save() {
        try {
            age.validate();
            sex.validate();
            if (customer.getName() == null || customer.getSurname() == null) {
                Notification.show("You new to write full name of customer");
            } else if (sex.getValue().equals('\u0000')) {
                Notification.show("You need to choose sex of customer");
            } else {
                service.save(customer);
                customerLayout.updateList();
                setVisible(false);
            }
        } catch (Validator.InvalidValueException e){
            Notification.show(e.getMessage());
        }
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