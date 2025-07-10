package com.webforj.samples.views.advanced;

import com.webforj.dispatcher.EventDispatcher;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;
import com.webforj.component.button.Button;
import com.webforj.component.Composite;
import com.webforj.component.html.elements.Div;
import java.util.EventObject;

@Route
@FrameTitle("Event Dispatcher")
public class EventDispatcherCustomEventView extends Composite<Div> {
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

    public EventDispatcherCustomEventView() {
        Button button = new Button("Fire Custom Event");

        Div statusText = new Div("waiting for custom event");
        statusText.setStyle("border", "2px solid #333");
        statusText.setStyle("padding", "8px 24px");
        statusText.setStyle("margin-top", "24px");
        statusText.setStyle("border-radius", "6px");
        statusText.setStyle("font-size", "1.1em");
        statusText.setStyle("background", "#fafbfc");

        // Center the content using flexbox styles on the root Div
        Div root = this.getBoundComponent();
        root.setStyle("display", "flex");
        root.setStyle("flex-direction", "column");
        root.setStyle("justify-content", "center");
        root.setStyle("align-items", "center");
        root.setStyle("height", "100vh");

        root.add(button);
        root.add(statusText);

        // Register a listener for the custom event
        dispatcher.addListener(CustomMessageEvent.class, event -> {
            statusText.setText("received custom event ");
            button.setEnabled(false);
        });

        // Fire the custom event with a message when the button is clicked
        button.onClick(e -> dispatcher.dispatchEvent(new CustomMessageEvent(this, "Hello from custom event!")));
    }
}
