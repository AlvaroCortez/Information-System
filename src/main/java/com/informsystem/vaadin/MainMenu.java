package com.informsystem.vaadin;

import com.vaadin.spring.navigator.SpringViewProvider;
import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;

/**
 * Created by Den on 11.04.16.
 */
public class MainMenu extends HorizontalLayout {

    public MainMenu(SpringViewProvider viewProvider){
        setSizeFull();
        addStyleName("mainview");
        addComponent(new DashboardMenu());
        ComponentContainer content = new CssLayout();
        content.addStyleName("view-content");
        content.setSizeFull();
        addComponent(content);
        setExpandRatio(content, 1.0f);

        new DashboardNavigator(content, viewProvider);
    }
}
