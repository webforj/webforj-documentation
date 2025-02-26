package com.webforj.samples.views.applayout.applayoutmultipleheaders;

import com.webforj.component.Composite;
import com.webforj.component.html.elements.Div;
import com.webforj.component.html.elements.Paragraph;
import com.webforj.router.annotation.Route;
import com.webforj.samples.views.applayout.AppLayoutMultipleHeadersView;

@Route(value = "/Orders", outlet = AppLayoutMultipleHeadersView.class)
public class OrdersView extends Composite<Div>{
    Paragraph contentLabel = new Paragraph("Content for Orders goes here");
    public OrdersView(){
        getBoundComponent().add(contentLabel);
    }
}
