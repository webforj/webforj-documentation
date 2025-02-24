package com.webforj.samples.views.slider;

import com.webforj.component.text.Label;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;
import com.webforj.component.Composite;
import com.webforj.component.Theme;
import com.webforj.component.layout.flexlayout.FlexAlignment;
import com.webforj.component.layout.flexlayout.FlexDirection;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.component.slider.Slider;

@Route
@FrameTitle("Slider Themes")
public class SliderThemesView extends Composite<FlexLayout> {
  
  FlexLayout layout = getBoundComponent();
  
    public SliderThemesView() {
    getBoundComponent().setDirection(FlexDirection.COLUMN)
      .setAlignment(FlexAlignment.CENTER)
      .setMargin("var(--dwc-space-l)");

    Slider sl1 = new Slider().setMax(100)
        .setMin(0)
        .setValue(50)
        .setTheme(Theme.DEFAULT)
        .setWidth("500px");

    Slider sl2 = new Slider().setMax(100)
        .setMin(0)
        .setValue(50)
        .setTheme(Theme.DANGER)
        .setWidth("500px");

    Slider sl3 = new Slider().setMax(100)
        .setMin(0)
        .setValue(50)
        .setTheme(Theme.GRAY)
        .setWidth("500px");

    Slider sl4 = new Slider().setMax(100)
        .setMin(0)
        .setValue(50)
        .setTheme(Theme.INFO)
        .setWidth("500px");

    Slider sl5 = new Slider().setMax(100)
        .setMin(0)
        .setValue(50)
        .setTheme(Theme.SUCCESS)
        .setWidth("500px");

    Slider sl6 = new Slider().setMax(100)
        .setMin(0)
        .setValue(50)
        .setTheme(Theme.WARNING)
        .setWidth("500px");

    getBoundComponent().add(
        new Label("DEFAULT"), sl1,
        new Label("DANGER"), sl2,
        new Label("GRAY"), sl3,
        new Label("INFO"), sl4,
        new Label("SUCCESS"), sl5,
        new Label("WARNING"), sl6);
  }
}
