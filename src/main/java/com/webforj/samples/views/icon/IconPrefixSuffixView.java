package com.webforj.samples.views.icon;

import com.webforj.component.Composite;
import com.webforj.component.button.Button;
import com.webforj.component.button.ButtonTheme;
import com.webforj.component.icons.Icon;
import com.webforj.component.icons.TablerIcon;
import com.webforj.component.layout.flexlayout.FlexDirection;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;

@Route
@FrameTitle("Icon Action Buttons")
public class IconPrefixSuffixView extends Composite<FlexLayout> {
  private final FlexLayout self = getBoundComponent();

  public IconPrefixSuffixView() {
    // Configure layout with fluent API
    self.setDirection(FlexDirection.ROW)
        .setMargin("var(--dwc-space-l)")
        .setSpacing("var(--dwc-space-l)");

    // Create next button with suffix icon (arrow pointing right)
    Icon arrow = TablerIcon.create("arrow-narrow-right");
    Button nextButton = new Button("Next")
        .setSuffixComponent(arrow)
        .setTheme(ButtonTheme.PRIMARY);

    // Create filter button with prefix icon
    Icon filter = TablerIcon.create("filter");
    Button filterButton = new Button("Filter")
        .setPrefixComponent(filter)
        .setTheme(ButtonTheme.DEFAULT);

    self.add(nextButton, filterButton);
  }
}