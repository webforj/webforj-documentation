package com.webforj.samples.views.tabbedpane;

import com.webforj.component.Composite;
import com.webforj.component.icons.Icon;
import com.webforj.component.icons.TablerIcon;
import com.webforj.component.layout.flexlayout.FlexDirection;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.component.optioninput.RadioButton;
import com.webforj.component.tabbedpane.Tab;
import com.webforj.component.tabbedpane.TabbedPane;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;

@Route
@FrameTitle("Tabbed Pane Border")
public class TabbedPaneBorderView extends Composite<FlexLayout> {
  private final FlexLayout self = getBoundComponent();
  // UI Components
  private final TabbedPane pane = new TabbedPane();
  private final RadioButton border = RadioButton.Switch("Hide Border");
  private final RadioButton active = RadioButton.Switch("Hide Active Indicator");

  public TabbedPaneBorderView() {
    self.setDirection(FlexDirection.COLUMN)
        .setSpacing("50px")
        .setMargin("20px")
        .add(border, active, pane);

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

    border.onToggle(e -> pane.setBorderless(!pane.isBorderless()));

    active.onToggle(e -> pane.setHideActiveIndicator(!pane.isHideActiveIndicator()));
  }
}
