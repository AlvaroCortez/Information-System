package com.informsystem.vaadin;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Widgetset;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.EnableVaadin;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.navigator.SpringViewProvider;
import com.vaadin.spring.server.SpringVaadinServlet;
import com.vaadin.ui.Button;
import com.vaadin.ui.UI;
import com.vaadin.ui.themes.ValoTheme;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.ContextLoaderListener;

import javax.servlet.annotation.WebListener;
import javax.servlet.annotation.WebServlet;

/**
 * Created by Den on 09.04.16.
 */
//@Theme("valo")
//@Widgetset("au.com.blogspot.ojitha.MyAppWidgetset")
@Theme("mytheme")
@Widgetset("com.informsystem.MyAppWidgetset")
@SpringUI
public class MyUI extends UI {

    //MainMenu mainMenu = new MainMenu();
    //ComponentContainer container = new CssLayout();

//    CustomerForm form = new CustomerForm(this);
    private Navigator navigator;

    @Autowired
    private SpringViewProvider viewProvider;
//    @Autowired
//    private CustomerLayout customerLayout;

    @Override
    protected void init(VaadinRequest vaadinRequest) {

        //Responsive.makeResponsive(this);
        addStyleName(ValoTheme.UI_WITH_MENU);
        //navigator = new DashboardNavigator(this);
        setContent(new MainMenu(viewProvider));
        //getNavigator().navigateTo(getNavigator().getState());


//        final CssLayout navigationBar = new CssLayout();
//        navigationBar.addStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
//        navigationBar.addComponent(createNavigationButton("View Scoped View",
//                CustomerForm.VIEW_NAME));
//        layout.addComponent(navigationBar);
//        final Panel viewContainer = new Panel();
//        viewContainer.setSizeFull();
//        layout.addComponent(viewContainer);
//        layout.setExpandRatio(viewContainer, 1.0f);
//
//        Navigator navigator = new Navigator(this, viewContainer);
//        navigator.addProvider(viewProvider);
    }

    private Button createNavigationButton(String caption, final String viewName) {
        Button button = new Button(caption);
        button.addStyleName(ValoTheme.BUTTON_SMALL);
        // If you didn't choose Java 8 when creating the project, convert this
        // to an anonymous listener class
        button.addClickListener(event -> getUI().getNavigator().navigateTo(
                viewName));
        return button;
    }

    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    //@VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
    public static class MyUIServlet extends SpringVaadinServlet {
    }

    @WebListener
    public static class MyContextLoaderListener extends ContextLoaderListener {}

    @Configuration
    @EnableVaadin
    public static class MyConfiguration {}

}
