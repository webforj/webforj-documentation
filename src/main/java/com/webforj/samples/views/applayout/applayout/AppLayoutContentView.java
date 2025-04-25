package com.webforj.samples.views.applayout.applayout;

import com.webforj.router.annotation.Route;
import com.webforj.samples.views.applayout.AbstractContentView;

@Route(value = "/content/:name", outlet = AppLayoutView.class)
public class AppLayoutContentView extends AbstractContentView{
    
}
