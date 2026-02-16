package com.webforj.samples.views.button;

import com.webforj.component.Composite;
import com.webforj.component.button.Button;
import com.webforj.component.button.ButtonTheme;
import com.webforj.component.html.elements.Div;
import com.webforj.component.layout.flexlayout.FlexDirection;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.component.layout.flexlayout.FlexWrap;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;

@Route
@FrameTitle("Button Themes")
public class ButtonThemesView extends Composite<FlexLayout> {
  private FlexLayout self = getBoundComponent();
  private Div scrollWrapper = new Div();

  public ButtonThemesView() {
    self.setDirection(FlexDirection.COLUMN)
            .setSpacing("var(--dwc-space-l)")
            .setMargin("var(--dwc-space-l)")
            .add(scrollWrapper);

    scrollWrapper.setStyle("overflow-x", "auto")
            .setStyle("white-space", "nowrap")
            .setStyle("margin", "var(--dwc-space-l)");

    FlexLayout solidRow = new FlexLayout()
            .setDirection(FlexDirection.ROW)
            .setWrap(FlexWrap.NOWRAP)
            .setSpacing("var(--dwc-space-s)");

    FlexLayout outlinedRow = new FlexLayout()
            .setDirection(FlexDirection.ROW)
            .setWrap(FlexWrap.NOWRAP)
            .setSpacing("var(--dwc-space-s)")
            .setStyle("margin-bottom", "var(--dwc-space-l)");

    for (ButtonTheme theme : ButtonTheme.values()) {
      if (theme.name().startsWith("OUTLINE")) {
        outlinedRow.add(new Button(theme.name(), theme)
                .setMinWidth("200px")
                .setMaxWidth("200px")
        );
      } else {
        solidRow.add(
                new Button(theme.name(), theme)
                        .setMinWidth("200px")
                        .setMaxWidth("200px")
        );
      }
    }

    FlexLayout scrollContent = FlexLayout.create(solidRow, outlinedRow)
            .vertical()
            .nowrap()
            .build()
            .setSpacing("var(--dwc-space-l)");

    scrollWrapper.add(scrollContent);
  }
}