package com.webforj.samples.pages.alert;

import com.microsoft.playwright.Page;
import com.webforj.samples.pages.SupportedLanguage;

public class ClosableAlertPage{
    private static final String ROUTE = "closablealert";

    public static String getRoute(SupportedLanguage language) {
        return language.getPath(ROUTE);
    }
    
    public ClosableAlertPage(Page page){
    }
}
