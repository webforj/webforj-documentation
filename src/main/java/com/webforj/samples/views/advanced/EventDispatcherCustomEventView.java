package com.webforj.samples.views.advanced;

import com.webforj.dispatcher.EventDispatcher;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;
import com.webforj.component.button.Button;
import com.webforj.component.Composite;
import com.webforj.component.html.elements.Div;
import com.webforj.component.layout.flexlayout.FlexJustifyContent;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.component.layout.flexlayout.FlexLayoutBuilder;

import java.util.EventObject;

@Route
@FrameTitle("Event Dispatcher")
public class EventDispatcherCustomEventView extends Composite<FlexLayout> {
    private final EventDispatcher dispatcher = new EventDispatcher();

    /**
     * A custom event that carries a message string.
     */
    public static class CustomMessageEvent extends EventObject {
        private final String message;

        public CustomMessageEvent(Object source, String message) {
            super(source);
            this.message = message;
        }

        public String getMessage() {
            return message;
        }
    }

    Button button = new Button("Fire custom event");
    Div statusText = new Div("Waiting for custom event");
    FlexLayoutBuilder layoutBuilder = new FlexLayoutBuilder(button, statusText);

    public EventDispatcherCustomEventView() {

        button.setWidth("fit-content");

        statusText.setWidth("fit-content");
        statusText.setStyle("border", "2px solid #333");
        statusText.setStyle("padding", "8px 24px");
        statusText.setStyle("border-radius", "var(--dwc-border-radius-s)");
        statusText.setStyle("font-size", "var(--dwc-font-size-l)");
        statusText.setStyle("background", "var(--dwc-surface-3)");

        FlexLayout layout = layoutBuilder.vertical()
                .justify().center()
                .align().center()
                .build()
                .setHeight("100vh")
                .setSpacing("var(--dwc-space-xl)");

        FlexLayout root = this.getBoundComponent();
        root.setJustifyContent(FlexJustifyContent.CENTER)
                .add(layout);

        // Register a listener for the custom event
        dispatcher.addListener(CustomMessageEvent.class, e -> {
            statusText.setText("Received custom event");
            button.setEnabled(false);
        });

        // Fire the custom event with a message when the button is clicked
        button.onClick(e -> {
            dispatcher.dispatchEvent(new CustomMessageEvent(this, "Hello from custom event!"));
        });
    }
}
