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

  boolean isSnapToTicksEnabled = false;
  boolean areTicksVisible = true;

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

    majorTickInput
        .setHelperText("Enter major tick spacing (e.g., 10)")
        .setHorizontalAlignment(NumberField.Alignment.LEFT)
        .setMin(0d)
        .onValueChange(ev -> updateTickSpacing());
    minorTickInput
        .setHelperText("Enter minor tick spacing (e.g., 2)")
        .setHorizontalAlignment(NumberField.Alignment.LEFT)
        .setMin(0d)
        .onValueChange(ev -> updateTickSpacing());

    snapToTicks.onToggle(ev -> {
      isSnapToTicksEnabled = ev.isToggled();
      slider.setSnapToTicks(isSnapToTicksEnabled);
    });

    showTicks.onToggle(ev -> {
      areTicksVisible = ev.isToggled();
      slider.setTicksVisible(areTicksVisible);
    });

    self.add(
        slider,
        FlexLayout.create(
            majorTickInput,
            minorTickInput,
            FlexLayout.create(snapToTicks, showTicks).horizontal().build()).vertical().build());
  }

  private void updateTickSpacing() {
    Double majorVal = majorTickInput.getValue();
    Double minorVal = minorTickInput.getValue();

    int range = slider.getMax() - slider.getMin();

    if (majorVal == null || minorVal == null) {
      return;
    }

    int majorSpacing = majorVal.intValue();
    int minorSpacing = minorVal.intValue();

    if (majorSpacing <= 0 || majorSpacing > range) {
      majorTickInput.setHelperText("Must be between 1 and " + range);
      return;
    }

    if (minorSpacing <= 0 || minorSpacing > range) {
      minorTickInput.setHelperText("Must be between 1 and " + range);
      return;
    }

    majorTickInput.setHelperText("Enter major tick spacing (e.g., 10)");
    minorTickInput.setHelperText("Enter minor tick spacing (e.g., 2)");

    slider.setMajorTickSpacing(majorSpacing);
    slider.setMinorTickSpacing(minorSpacing);
  }
}
