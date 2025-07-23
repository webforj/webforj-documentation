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
  
  FlexLayout layout = getBoundComponent();
  Div scrollWrapper = new Div();

  public ButtonThemesView() {
    layout.setDirection(FlexDirection.COLUMN)
          .setSpacing("var(--dwc-space-l)")
          .setMargin("var(--dwc-space-l)");

    scrollWrapper.setStyle("overflow-x", "auto");
    scrollWrapper.setStyle("white-space", "nowrap");
    scrollWrapper.setStyle("margin","var(--dwc-space-l)");
    
    FlexLayout scrollContent = new FlexLayout();
    scrollContent.setDirection(FlexDirection.COLUMN)
                 .setSpacing("var(--dwc-space-l)")
                 .setWrap(FlexWrap.NOWRAP);

    FlexLayout solidRow = new FlexLayout();
    solidRow.setDirection(FlexDirection.ROW)
            .setWrap(FlexWrap.NOWRAP)
            .setSpacing("var(--dwc-space-s)");

    FlexLayout outlinedRow = new FlexLayout();
    outlinedRow.setDirection(FlexDirection.ROW)
               .setWrap(FlexWrap.NOWRAP)
               .setSpacing("var(--dwc-space-s)")
               .setStyle("margin-bottom","var(--dwc-space-l)");

    for (ButtonTheme theme : ButtonTheme.values()) {
      if (!theme.name().contains("OUTLINE")) {
        Button solid = new Button(theme.name(), theme)
            .setMinWidth("200px")
            .setMaxWidth("200px");
        solidRow.add(solid);

        Button outlined = new Button("OUTLINED_" + theme.name(),
            ButtonTheme.valueOf("OUTLINED_" + theme.name()))
            .setMinWidth("200px")
            .setMaxWidth("200px");
        outlinedRow.add(outlined);
      }
    }

    scrollContent.add(solidRow, outlinedRow);
    scrollWrapper.add(scrollContent);

    layout.add(scrollWrapper);
  }
}