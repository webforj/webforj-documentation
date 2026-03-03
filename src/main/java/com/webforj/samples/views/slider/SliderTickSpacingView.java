package com.webforj.samples.views.slider;

import com.webforj.component.Component;
import com.webforj.component.Composite;
import com.webforj.component.field.NumberField;
import com.webforj.component.layout.flexlayout.FlexDirection;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.component.optioninput.RadioButton;
import com.webforj.component.slider.Slider;
import com.webforj.data.event.ValueChangeEvent;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;

@Route
@FrameTitle("Slider Major and Minor Tick Spacing Demo")
public class SliderTickSpacingView extends Composite<FlexLayout> {
  private final FlexLayout self = getBoundComponent();
  // Slider component
  private final Slider slider = new Slider(0, 100, 50);
  // Input fields for tick spacing
  private final NumberField majorTickInput = new NumberField("Major Tick", 20d);
  private final NumberField minorTickInput = new NumberField("Minor Tick", 10d);
  // Toggle switches
  private final RadioButton snapToTicks = RadioButton.Switch("Snap to Ticks", false);
  private final RadioButton showTicks = RadioButton.Switch("Show Ticks", true);

  public SliderTickSpacingView() {
    // Configure layout
    self.setDirection(FlexDirection.COLUMN)
        .setMaxWidth("400px")
        .setSpacing("var(--dwc-space-m)")
        .setMargin("var(--dwc-space-m) auto");

    // Configure slider
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

    double range = slider.getMax() - slider.getMin();

    // Configure major tick input
    majorTickInput.setMin(1d)
        .setMax(range)
        .setInvalidMessage("Must be between 1 and " + range)
        .setPlaceholder("Enter major tick spacing (e.g., 10)")
        .onValueChange(this::updateTickSpacing);

    // Configure minor tick input
    minorTickInput.setMin(1d)
        .setMax(range)
        .setInvalidMessage("Must be between 1 and " + range)
        .setPlaceholder("Enter minor tick spacing (e.g., 2)")
        .onValueChange(this::updateTickSpacing);

    // Configure toggle switches
    snapToTicks.onToggle(ev -> slider.setSnapToTicks(ev.isToggled()));
    showTicks.onToggle(ev -> slider.setTicksVisible(ev.isToggled()));

    // Add components to layout
    self.add(
        slider,
        FlexLayout.create(
            majorTickInput,
            minorTickInput,
            FlexLayout.create(snapToTicks, showTicks).horizontal().build()
        ).vertical().build()
    );
  }

  /**
   * Updates tick spacing based on input field values.
   */
  private void updateTickSpacing(ValueChangeEvent<Double> ev) {
    Double eventVal = ev.getValue();
    Object source = ev.getSource();

    if (eventVal != null) {
      int spacingVal = eventVal.intValue();
      if (source == majorTickInput && !majorTickInput.isInvalid()) {
        slider.setMajorTickSpacing(spacingVal);
      } else if (source == minorTickInput && !minorTickInput.isInvalid()) {
        slider.setMinorTickSpacing(spacingVal);
      }
    }
  }
}
