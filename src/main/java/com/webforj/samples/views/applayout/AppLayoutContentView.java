package com.webforj.samples.views.applayout;

import com.webforj.router.annotation.Route;

@Route(value = "/content/:name", outlet = AppLayoutView.class)
public class AppLayoutContentView extends AbstractContentView{
    
}
