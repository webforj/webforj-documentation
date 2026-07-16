package com.webforj.samples.views.element;

import com.webforj.bundle.annotation.BundleEntry;
import com.webforj.component.Composite;
import com.webforj.component.Theme;
import com.webforj.component.button.Button;
import com.webforj.component.button.ButtonTheme;
import com.webforj.component.element.Element;
import com.webforj.component.html.elements.Div;
import com.webforj.component.html.elements.H2;
import com.webforj.component.icons.Icon;
import com.webforj.component.icons.TablerIcon;
import com.webforj.component.layout.flexlayout.FlexAlignment;
import com.webforj.component.layout.flexlayout.FlexDirection;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.component.layout.flexlayout.FlexWrap;
import com.webforj.component.toast.Toast;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;

@Route
@BundleEntry("css/element/elementsearch.css")
@FrameTitle("Input Function")
public class ElementSearchView extends Composite<Div> {
  private final Div self = getBoundComponent();
  private final Element search = new Element("input");

  public ElementSearchView() {
    self.addClassName("element-demo-frame");

    Div card = new Div();
    card.addClassName("element-demo-card");

    Icon searchIcon = TablerIcon.create("search");
    searchIcon.addClassName("search-input-icon");

    search.addClassName("search-input-field");
    search.setAttribute("type", "search");
    search.setAttribute("placeholder", "Search products");

    Div field = new Div();
    field.addClassName("search-input");
    field.add(searchIcon, search);

    Button focus = new Button("Focus search", e -> focusSearch()).setTheme(ButtonTheme.PRIMARY);

    FlexLayout row = new FlexLayout(field, focus);
    row.setDirection(FlexDirection.ROW);
    row.setWrap(FlexWrap.WRAP);
    row.setAlignment(FlexAlignment.CENTER);
    row.setSpacing("var(--dwc-space-s)");

    card.add(new H2("Search"), row);
    self.add(card);
  }

  private void focusSearch() {
    search
        .callJsFunctionAsync("focus")
        .thenAccept(result -> Toast.show("Search focused", 2000, Theme.GRAY));
  }
}
