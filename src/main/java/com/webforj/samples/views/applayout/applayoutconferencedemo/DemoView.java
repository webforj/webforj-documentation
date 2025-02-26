package com.webforj.samples.views.applayout.applayoutconferencedemo;

import com.webforj.annotation.InlineStyleSheet;
import com.webforj.component.Composite;
import com.webforj.component.html.elements.Div;
import com.webforj.component.html.elements.H2;
import com.webforj.component.html.elements.Paragraph;
import com.webforj.router.annotation.Route;
import com.webforj.samples.views.applayout.AppLayoutConferenceDemoView;

@InlineStyleSheet("context://css/applayout/applayoutMobile.css")
@Route(value = "/demo", outlet = AppLayoutConferenceDemoView.class)
public class DemoView extends Composite<Div> {
    public DemoView() {
        for (int i = 0; i < 5; i++) {
            Div content = new Div().addClassName("card");
            content.add(
                    new H2("What is webforJ?"),
                    new Paragraph(
                            """
                                    webforJ is a powerful Java UI framework designed for building business-critical web applications entirely in Java. It provides a comprehensive API, design system, and a rich library of UI components, allowing developers to create modern, secure, and high-performance web applications without needing to rely on additional web development languages or frameworks.
                                    """));
            getBoundComponent().add(content);
        }
    }
}