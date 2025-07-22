package com.webforj.samples.views.tabbedpane;

import com.webforj.annotation.InlineStyleSheet;
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

@InlineStyleSheet(/*css */"""
  .window {
    display: flex;
    flex-direction: column;
    gap: 50px;
    margin: 20px;
  }
""")
@Route
@FrameTitle("Tabbed Pane Placement")
public class TabbedPanePlacementView extends Composite<Div>{

  TabbedPane pane = new TabbedPane();
  ChoiceBox placements = new ChoiceBox("Placement");

  public TabbedPanePlacementView() {
    getBoundComponent().addClassName("window");
    getBoundComponent().add(placements, pane);

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
    
    for(Placement placement : Placement.values()){
      placements.add(placement.toString());
    }
    placements.setWidth("200px");
    placements.selectIndex(0);

    placements.onSelect( e -> pane.setPlacement(Placement.valueOf(e.getSelectedItem().getText())));
  }
}
