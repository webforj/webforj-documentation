package com.webforj.samples.views.tabbedpane;

import com.webforj.annotation.InlineStyleSheet;
import com.webforj.component.Composite;
import com.webforj.component.html.elements.Div;
import com.webforj.component.icons.Icon;
import com.webforj.component.icons.TablerIcon;
import com.webforj.component.list.ChoiceBox;
import com.webforj.component.tabbedpane.Tab;
import com.webforj.component.tabbedpane.TabbedPane;
import com.webforj.component.tabbedpane.TabbedPane.Alignment;
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
@FrameTitle("Tabbed Pane Alignment")
public class TabbedPaneAlignmentView extends Composite<Div> {

  TabbedPane pane = new TabbedPane();
  ChoiceBox alignments = new ChoiceBox("Alignment");

  public TabbedPaneAlignmentView() {
    getBoundComponent().addClassName("window");
    getBoundComponent().add(alignments, pane);

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
    
    for(Alignment alignment : Alignment.values()){
      alignments.add(alignment.toString());
    }
    alignments.setWidth("200px");
    alignments.selectIndex(0);

    alignments.onSelect( e -> pane.setAlignment(Alignment.valueOf(e.getSelectedItem().getText())));
  }
}
