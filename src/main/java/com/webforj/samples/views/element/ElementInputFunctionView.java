package com.webforj.samples.views.element;

import com.webforj.bundle.annotation.BundleEntry;
import com.webforj.component.Composite;
import com.webforj.component.Theme;
import com.webforj.component.button.Button;
import com.webforj.component.button.ButtonTheme;
import com.webforj.component.element.Element;
import com.webforj.component.html.elements.Div;
import com.webforj.component.html.elements.H2;
import com.webforj.component.layout.flexlayout.FlexAlignment;
import com.webforj.component.layout.flexlayout.FlexDirection;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.component.toast.Toast;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;

@Route
@BundleEntry("css/element/elementInput.css")
@FrameTitle("Input Function")
public class ElementInputFunctionView extends Composite<Div> {
  private final Div self = getBoundComponent();
  private final Element search = new Element("input");

  public ElementInputFunctionView() {
    self.setStyle("display", "flex");
    self.setStyle("flex-direction", "column");
    self.setStyle("justify-content", "center");
    self.setStyle("align-items", "center");
    self.setStyle("min-height", "100vh");

    Div card = new Div();
    card.setStyle("width", "100%");
    card.setStyle("max-width", "420px");
    card.setStyle("padding", "var(--dwc-space-l)");
    card.setStyle("background", "var(--dwc-surface-3)");
    card.setStyle("border", "var(--dwc-border-width) var(--dwc-border-style) var(--dwc-border-color)");
    card.setStyle("border-radius", "var(--dwc-border-radius-l)");

    search.setAttribute("type", "search");
    search.setAttribute("placeholder", "Search products");
    search.setAttribute("style", "flex: 1; min-width: 0; box-sizing: border-box; padding: var(--dwc-space-xs) var(--dwc-space-s); font-size: 1rem;");

    Button focus = new Button("Focus search", e -> focusSearch()).setTheme(ButtonTheme.PRIMARY);

    FlexLayout row = new FlexLayout(search, focus);
    row.setDirection(FlexDirection.ROW);
    row.setAlignment(FlexAlignment.CENTER);
    row.setSpacing("var(--dwc-space-s)");

    card.add(new H2("Search"), row);
    self.add(card);
  }
  
  private void focusSearch() {
    search.callJsFunctionAsync("focus")
        .thenAccept(result -> Toast.show("Search focused", 2000, Theme.GRAY));
  }
}