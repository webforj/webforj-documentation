package com.webforj.samples.views.button;

import com.webforj.bundle.annotation.BundleEntry;
import com.webforj.component.Composite;
import com.webforj.component.button.Button;
import com.webforj.component.button.ButtonTheme;
import com.webforj.component.icons.Icon;
import com.webforj.component.icons.IconButton;
import com.webforj.component.icons.TablerIcon;
import com.webforj.component.layout.flexlayout.FlexAlignment;
import com.webforj.component.layout.flexlayout.FlexDirection;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.component.layout.flexlayout.FlexWrap;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;

@Route
@BundleEntry("css/button/buttonThemes.css")
@FrameTitle("Button Themes")
public class ButtonThemesView extends Composite<FlexLayout> {
  private final FlexLayout self = getBoundComponent();
  private final IconButton rightIcon = new IconButton(TablerIcon.create("caret-right"));
  private final IconButton leftIcon = new IconButton(TablerIcon.create("caret-left"));
  private final FlexLayout solidRow = new FlexLayout();
  private final FlexLayout outlinedRow = new FlexLayout();
  private final FlexLayout buttonWrapper = new FlexLayout(solidRow, outlinedRow);
  private int counter = 0;
  private int order;
  private int targetButton;

  public ButtonThemesView() {
    self.setSize("fit-content", "100vh")
        .setMargin("auto")
        .setAlignment(FlexAlignment.CENTER)
        .add(leftIcon, buttonWrapper, rightIcon);

    solidRow
        .setDirection(FlexDirection.ROW)
        .setWrap(FlexWrap.NOWRAP)
        .setSpacing("var(--dwc-space-s)");

    outlinedRow
        .setDirection(FlexDirection.ROW)
        .setWrap(FlexWrap.NOWRAP)
        .setSpacing("var(--dwc-space-s)");

    buttonWrapper
        .setDirection(FlexDirection.COLUMN)
        .setPadding("var(--dwc-space-s)")
        .setWrap(FlexWrap.NOWRAP)
        .addClassName("buttonWrapperWidth");

    for (ButtonTheme theme : ButtonTheme.values()) {
      if (theme.name().startsWith("OUTLINE")) {
        Button outlineButton =
            new Button(theme.name(), theme).setMaxWidth("200px").setMinWidth("200px");
        outlinedRow.add(outlineButton);
      } else {
        solidRow.add(new Button(theme.name(), theme).setMaxWidth("200px").setMinWidth("200px"));
      }
    }

    leftIcon.onClick(
        e -> {
          if (counter == 0) {
            counter = 6;
          } else {
            counter--;
          }
          targetButton = counter;
          setButtonOrder(targetButton, e.getComponent());
        });

    rightIcon.onClick(
        e -> {
          if (counter == 6) {
            counter = 0;
            targetButton = 6;
          } else {
            ++counter;
            targetButton = counter - 1;
          }
          setButtonOrder(targetButton, e.getComponent());
        });
  }

  public void setButtonOrder(int targetButton, Icon sourceIcon) {
    Button outlineButton = (Button) outlinedRow.getComponents().get(targetButton);
    Button solidButton = (Button) solidRow.getComponents().get(targetButton);

    if (sourceIcon.getName() == "caret-right") {
      order = outlinedRow.getItemOrder(outlineButton) + 1;
    } else {
      order = outlinedRow.getItemOrder(outlineButton) - 1;
    }
    outlinedRow.setItemOrder(order, outlineButton);
    solidRow.setItemOrder(order, solidButton);
  }
}
