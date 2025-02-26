package com.webforj.samples.views.applayout.applayoutmultipleheaders;

import com.webforj.component.Composite;
import com.webforj.component.html.elements.Div;
import com.webforj.component.html.elements.Paragraph;
import com.webforj.router.annotation.Route;
import com.webforj.samples.views.applayout.AppLayoutMultipleHeadersView;

@Route(value = "/Products", outlet = AppLayoutMultipleHeadersView.class)
public class ProductsView extends Composite<Div>{
    Paragraph contentLabel = new Paragraph("Content for Products goes here");
    public ProductsView(){
        getBoundComponent().add(contentLabel);
    }
}
