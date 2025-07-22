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
  
  public IconPrefixSuffixView() {
    getBoundComponent().setDirection(FlexDirection.ROW).setMargin("var(--dwc-space-l)")
    .setSpacing("var(--dwc-space-l)");
    
    Icon arrow = TablerIcon.create("arrow-narrow-right");
    Button nextButton = new Button("Next").setSuffixComponent(arrow)
    .setTheme(ButtonTheme.PRIMARY);
    
    Icon filter = TablerIcon.create("filter");
    Button filterButton = new Button("Filter").setPrefixComponent(filter)
    .setTheme(ButtonTheme.DEFAULT);
    
    getBoundComponent().add(nextButton, filterButton);
  }
}