package com.webforj.samples.views.button;

import com.webforj.component.Composite;
import com.webforj.component.button.Button;
import com.webforj.component.button.ButtonTheme;
import com.webforj.component.layout.columnslayout.ColumnsLayout;
import com.webforj.component.layout.columnslayout.ColumnsLayout.Breakpoint;
import com.webforj.component.layout.flexlayout.FlexAlignment;
import com.webforj.component.layout.flexlayout.FlexDirection;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;
import java.util.List;

@Route
@FrameTitle("Button Themes")
public class ButtonThemesView extends Composite<FlexLayout> {
  private final FlexLayout self = getBoundComponent();
  private final ColumnsLayout solidThemeLayout = new ColumnsLayout();
  private final ColumnsLayout outlinedThemeLayout = new ColumnsLayout();
  private final List<Breakpoint> breakpoints =
      List.of(new Breakpoint(0, 1), new Breakpoint(400, 2), new Breakpoint(600, 3));

  public ButtonThemesView() {

    self.setSize("100vw", "100vh")
        .setPadding("var(--dwc-space-xl)")
        .setDirection(FlexDirection.COLUMN)
        .setAlignment(FlexAlignment.CENTER)
        .setStyle("overflow-y", "scroll")
        .add(solidThemeLayout, outlinedThemeLayout);

    setLayout(solidThemeLayout);
    setLayout(outlinedThemeLayout);
    setButtonThemes();
  }

  public void setButtonThemes() {
    for (ButtonTheme theme : ButtonTheme.values()) {
      Button button = new Button(theme.name(), theme);
      if (theme.name().startsWith("OUTLINE")) {
        outlinedThemeLayout.add(button);
      } else {
        solidThemeLayout.add(button);
      }
    }
  }

  public void setLayout(ColumnsLayout layout) {
    layout.setBreakpoints(breakpoints).setWidth("100%").setMaxWidth(700);
  }
}
