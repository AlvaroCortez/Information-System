package com.informsystem.view.form;

import com.informsystem.model.Customer;
import com.informsystem.service.CustomerService;
import com.informsystem.vaadin.MyUI;
import com.informsystem.view.CustomerView;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
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

//    CustomerService service = CustomerService.getInstance();
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

//    public CustomerForm(MyUI myUI) {
//        this.myUI = myUI;
//        //status.addItems(CustomerStatus.values());
//        setSizeUndefined();
//        HorizontalLayout buttons = new HorizontalLayout(save, delete);
//        buttons.setSpacing(true);
//        addComponents(name, surname, age, buttons);
//        save.setClickShortcut(KeyCode.ENTER);
//        save.setStyleName(ValoTheme.BUTTON_PRIMARY);
//        save.addClickListener(e -> this.save());
//        delete.addClickListener(e -> this.delete());
//    }

    @PostConstruct
    void init(){
        setSizeUndefined();
        HorizontalLayout buttons = new HorizontalLayout(save, delete);
        buttons.setSpacing(true);
        HorizontalLayout fullNameCustomer = new HorizontalLayout(name, surname);
        //sex.addValidator(new StringLengthValidator("The sex must contains one char symbol: M or F",1,1,true));
        sex.addItems('M', 'F');
        sex.addStyleName("horizontal");
        sex.setNullSelectionAllowed(false);
        sex.select(0);
        age.setNullRepresentation("0");
        name.setNullRepresentation("");
        surname.setNullRepresentation("");
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
        service.save(customer);
        customerLayout.updateList();
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