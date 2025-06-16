package com.webforj.samples.views.slider;

import com.webforj.component.Composite;
import com.webforj.component.field.NumberField;
import com.webforj.component.layout.flexlayout.FlexDirection;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.component.optioninput.RadioButton;
import com.webforj.component.slider.Slider;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;

@Route
@FrameTitle("Slider Major and Minor Tick Spacing Demo")
public class SliderTickSpacingView extends Composite<FlexLayout> {
  FlexLayout self = getBoundComponent();
  Slider slider = new Slider(0, 100, 50);
  NumberField majorTickInput = new NumberField("Major Tick", 20d);
  NumberField minorTickInput = new NumberField("Minor Tick", 10d);
  RadioButton snapToTicks = RadioButton.Switch("Snap to Ticks", false);
  RadioButton showTicks = RadioButton.Switch("Show Ticks", true);

  public SliderTickSpacingView() {
    self.setDirection(FlexDirection.COLUMN)
        .setMaxWidth("400px")
        .setSpacing("var(--dwc-space-m)")
        .setMargin("var(--dwc-space-m) auto");

    slider.setMin(0)
        .setMax(100)
        .setValue(0)
        .setFilled(true)
        .setTicksVisible(true)
        .setMajorTickSpacing(20)
        .setMinorTickSpacing(10)
        .setLabelsVisible(true)
        .setTooltipVisibleOnSlideOnly(true)
        .setStyle("padding", "var(--dwc-space-m) 0");

    int range = slider.getMax() - slider.getMin();

    majorTickInput
        .setMin(1d)
        .setMax((double) range)
        .setInvalidMessage("Must be between 1 and " + range)
        .onValueChange(ev -> updateTickSpacing());

    minorTickInput
        .setMin(1d)
        .setMax((double) range)
        .setInvalidMessage("Must be between 1 and " + range)
        .onValueChange(ev -> updateTickSpacing());

    snapToTicks.onToggle(ev -> slider.setSnapToTicks(ev.isToggled()));
    showTicks.onToggle(ev -> slider.setTicksVisible(ev.isToggled()));

    self.add(
        slider,
        FlexLayout.create(
            majorTickInput,
            minorTickInput,
            FlexLayout.create(snapToTicks, showTicks).horizontal().build()
        ).vertical().build()
    );
  }

  private void updateTickSpacing() {
    Double majorVal = majorTickInput.getValue();
    Double minorVal = minorTickInput.getValue();

    if (!majorTickInput.isInvalid() && majorVal != null) {
      slider.setMajorTickSpacing(majorVal.intValue());
    }

    if (!minorTickInput.isInvalid() && minorVal != null) {
      slider.setMinorTickSpacing(minorVal.intValue());
    }
  }
}
