package com.webforj.samples.views.tabbedpane;

import com.webforj.component.Composite;
import com.webforj.component.Theme;
import com.webforj.component.icons.Icon;
import com.webforj.component.icons.TablerIcon;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.component.tabbedpane.Tab;
import com.webforj.component.tabbedpane.TabbedPane;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;

@Route
@FrameTitle("Tabbed Pane Segment")
public class TabbedPaneSegmentView extends Composite<FlexLayout> {
  private final FlexLayout self = getBoundComponent();
  private final TabbedPane pane = new TabbedPane();

  public TabbedPaneSegmentView() {
    pane.setMaxWidth("max-content");
    pane.setTheme(Theme.PRIMARY);

    Icon dashboardIcon = TablerIcon.create("dashboard");
    Icon ordersIcon = TablerIcon.create("shopping-cart");
    Icon customersIcon = TablerIcon.create("users");

    pane.addTab(new Tab("Dashboard", dashboardIcon));
    pane.addTab(new Tab("Orders", ordersIcon));
    pane.addTab(new Tab("Customers", customersIcon));

    pane.setSegment(true);

    FlexLayout container =
        FlexLayout.create(pane)
            .vertical()
            .justify()
            .center()
            .align()
            .center()
            .build()
            .setStyle("width", "100%")
            .setStyle("min-height", "100vh");

    self.add(container);
  }
}
