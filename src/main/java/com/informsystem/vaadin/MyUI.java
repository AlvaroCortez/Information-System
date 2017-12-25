package com.informsystem.vaadin;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Widgetset;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.EnableVaadin;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.navigator.SpringViewProvider;
import com.vaadin.spring.server.SpringVaadinServlet;
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

    @Autowired
    private SpringViewProvider viewProvider;

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        addStyleName(ValoTheme.UI_WITH_MENU);
        setContent(new MainMenu(viewProvider));
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
