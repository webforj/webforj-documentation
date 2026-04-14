package com.webforj.samples.views.tabbedpane;

import com.webforj.annotation.StyleSheet;
import com.webforj.component.Composite;
import com.webforj.component.html.elements.Div;
import com.webforj.component.icons.Icon;
import com.webforj.component.icons.TablerIcon;
import com.webforj.component.list.ChoiceBox;
import com.webforj.component.tabbedpane.Tab;
import com.webforj.component.tabbedpane.TabbedPane;
import com.webforj.component.tabbedpane.TabbedPane.Placement;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;

@StyleSheet("ws://css/tabbedpane/window.css")
@Route
@FrameTitle("Tabbed Pane Placement")
public class TabbedPanePlacementView extends Composite<Div> {
  private final Div self = getBoundComponent();
  // UI Components
  private final TabbedPane pane = new TabbedPane();
  private final ChoiceBox placements = new ChoiceBox("Placement");

  public TabbedPanePlacementView() {
    self.addClassName("window")
            .add(placements, pane);

    Icon dashboardIcon = TablerIcon.create("dashboard");
    Icon ordersIcon = TablerIcon.create("shopping-cart");
    Icon customersIcon = TablerIcon.create("users");
    Icon productsIcon = TablerIcon.create("box");
    Icon documentsIcon = TablerIcon.create("files");

    pane.addTab(new Tab("Dashboard", dashboardIcon));
    pane.addTab(new Tab("Orders", ordersIcon));
    pane.addTab(new Tab("Customers", customersIcon));
    pane.addTab(new Tab("Products", productsIcon));
    pane.addTab(new Tab("Documents", documentsIcon));

    for (Placement placement : Placement.values()) {
      placements.add(placement.toString());
    }
    placements.setWidth("200px")
            .selectIndex(0)
            .onSelect(e -> pane.setPlacement(Placement.valueOf(e.getSelectedItem().getText())));
  }
}
