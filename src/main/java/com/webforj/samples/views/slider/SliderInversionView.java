package com.webforj.samples.views.slider;

import com.webforj.component.Composite;
import com.webforj.component.button.Button;
import com.webforj.component.button.ButtonTheme;
import com.webforj.component.layout.flexlayout.FlexAlignment;
import com.webforj.component.layout.flexlayout.FlexDirection;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.component.slider.Slider;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;
import java.util.HashMap;
import java.util.Map;

@Route
@FrameTitle("Slider Inversion")
public class SliderInversionView extends Composite<FlexLayout> {

  FlexLayout layout = getBoundComponent();

  public SliderInversionView() {
    layout.setDirection(FlexDirection.COLUMN)
          .setAlignment(FlexAlignment.CENTER)
          .setMargin("var(--dwc-space-l)");

    Map<Integer, String> mapMatch = new HashMap<>();
    mapMatch.put(0, "0");
    mapMatch.put(50, "50");
    mapMatch.put(100, "100");

    Slider slider = new Slider()
        .setMax(100)
        .setMin(0)
        .setTicksVisible(true)
        .setMinorTickSpacing(10)
        .setLabelsVisible(true)
        .setLabels(mapMatch)
        .setWidth("400px");

    Button toggleInversionButton = new Button("Invert Slider").setTheme(ButtonTheme.PRIMARY);
    toggleInversionButton.onClick(event -> {
      boolean isInverted = "true".equals(slider.getAttribute("inverted"));
      if (isInverted) {
        slider.removeAttribute("inverted");
        toggleInversionButton.setText("Invert Slider");
      } else {
        slider.setAttribute("inverted", "true");
        toggleInversionButton.setText("Uninvert Slider");
      }
    });

    layout.add(slider, toggleInversionButton);
  }
}