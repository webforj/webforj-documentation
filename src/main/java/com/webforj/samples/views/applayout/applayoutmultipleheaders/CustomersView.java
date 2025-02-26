package com.webforj.samples.views.applayout.applayoutmultipleheaders;

import com.webforj.component.Composite;
import com.webforj.component.html.elements.Div;
import com.webforj.component.html.elements.Paragraph;
import com.webforj.router.annotation.Route;
import com.webforj.samples.views.applayout.AppLayoutMultipleHeadersView;

@Route(value = "/Customers", outlet = AppLayoutMultipleHeadersView.class)
public class CustomersView extends Composite<Div>{
    Paragraph contentLabel = new Paragraph("Content for Customers goes here");
    public CustomersView(){
        getBoundComponent().add(contentLabel);
    }
}
