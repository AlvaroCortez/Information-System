package com.informsystem.vaadin;

import com.informsystem.view.CustomerView;
import com.informsystem.view.DishView;
import com.informsystem.view.OrderView;
import com.informsystem.view.StartView;
import com.vaadin.navigator.View;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Resource;

/**
 * Created by Den on 11.04.16.
 */
public enum DashboardViewType {
    CUSTOMER("customer", CustomerView.class, FontAwesome.HOME, true),
    DISH("dish", DishView.class, FontAwesome.FUTBOL_O, true),
    ORDER("order",OrderView.class, FontAwesome.REORDER, true),
    START("start", StartView.class, FontAwesome.STAR, true)
    ;

    private final String viewName;
    private final Class<? extends View> viewClass;
    private final Resource icon;
    private final boolean stateful;

    DashboardViewType(final String viewName,
                      final Class<? extends View> viewClass, final Resource icon,
                      final boolean stateful) {
        this.viewName = viewName;
        this.viewClass = viewClass;
        this.icon = icon;
        this.stateful = stateful;
    }

    public boolean isStateful() {
        return stateful;
    }

    public String getViewName() {
        return viewName;
    }

    public Class<? extends View> getViewClass() {
        return viewClass;
    }

    public Resource getIcon() {
        return icon;
    }

    public static DashboardViewType getByViewName(final String viewName) {
        DashboardViewType result = null;
        for (DashboardViewType viewType : values()) {
            if (viewType.getViewName().equals(viewName)) {
                result = viewType;
                break;
            }
        }
        return result;
    }

}
