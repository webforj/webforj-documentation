package com.webforj.samples.views.slider;

import com.webforj.component.Composite;
import com.webforj.component.button.Button;
import com.webforj.component.button.ButtonTheme;
import com.webforj.component.field.TextField;
import com.webforj.component.layout.flexlayout.FlexAlignment;
import com.webforj.component.layout.flexlayout.FlexDirection;
import com.webforj.component.layout.flexlayout.FlexJustifyContent;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.component.slider.Slider;
import com.webforj.component.toast.Toast;
import com.webforj.component.toast.Toast.Placement;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;

@Route
@FrameTitle("Slider Major and Minor Tick Spacing Demo")
public class SliderTickSpacingView extends Composite<FlexLayout> {

  Slider slider = new Slider(0, 100, 50);
  TextField majorTickInput = new TextField("Major Tick Spacing");
  TextField minorTickInput = new TextField("Minor Tick Spacing");
  Button applyTickSpacingButton = new Button("Apply Tick Spacing").setTheme(ButtonTheme.PRIMARY);
  Button toggleSnapToTicksButton = new Button("Enable Snap to Ticks").setTheme(ButtonTheme.PRIMARY);
  Button toggleTicksVisibilityButton = new Button("Hide Ticks").setTheme(ButtonTheme.PRIMARY);

  private boolean isSnapToTicksEnabled = false;
  private boolean areTicksVisible = true;

  public SliderTickSpacingView() {
    FlexLayout layout = getBoundComponent();
    layout.setDirection(FlexDirection.COLUMN)
          .setJustifyContent(FlexJustifyContent.CENTER)
          .setSpacing("var(--dwc-space-m)")
          .setMargin("var(--dwc-space-m)")
          .setAlignment(FlexAlignment.CENTER);

    slider.setMin(0)
          .setMax(100)
          .setValue(0)
          .setTicksVisible(true)
          .setMajorTickSpacing(20)
          .setMinorTickSpacing(10)
          .setLabelsVisible(true)
          .setTooltipVisibleOnSlideOnly(true)
          .setWidth("400px");

    majorTickInput.setPlaceholder("Enter major tick spacing (e.g., 10)")
                  .setWidth("300px");
    minorTickInput.setPlaceholder("Enter minor tick spacing (e.g., 2)")
                  .setWidth("300px");

    applyTickSpacingButton.onClick(event -> applyTickSpacing());
    toggleSnapToTicksButton.onClick(event -> toggleSnapToTicks());
    toggleTicksVisibilityButton.onClick(event -> toggleTicksVisibility());

    FlexLayout buttonLayout = new FlexLayout()
        .setDirection(FlexDirection.ROW)
        .setSpacing("var(--dwc-space-s)")
        .setMargin("var(--dwc-space-s)")
        .setJustifyContent(FlexJustifyContent.CENTER)
        .setAlignment(FlexAlignment.CENTER);

    buttonLayout.add(applyTickSpacingButton, toggleSnapToTicksButton, toggleTicksVisibilityButton);

    layout.add(slider, majorTickInput, minorTickInput, buttonLayout);
  }

  private void applyTickSpacing() {
    try {
      int majorSpacing = Integer.parseInt(majorTickInput.getValue());
      int minorSpacing = Integer.parseInt(minorTickInput.getValue());

      if (majorSpacing <= 0 || minorSpacing <= 0) {
        Toast.show("Tick spacing values must be greater than 0.")
               .setPlacement(Placement.CENTER);
        return;
      }

      slider.setMajorTickSpacing(majorSpacing);
      slider.setMinorTickSpacing(minorSpacing);
    } catch (NumberFormatException | NullPointerException e) {
      Toast.show("Invalid input for tick spacing. Please enter valid numbers.")
           .setPlacement(Placement.CENTER);
    }
  }

  private void toggleSnapToTicks() {
    isSnapToTicksEnabled = !isSnapToTicksEnabled;
    slider.setSnapToTicks(isSnapToTicksEnabled);
    toggleSnapToTicksButton.setText(isSnapToTicksEnabled 
        ? "Disable Snap to Ticks" : "Enable Snap to Ticks");
  }

  private void toggleTicksVisibility() {
    areTicksVisible = !areTicksVisible;
    slider.setTicksVisible(areTicksVisible);
    toggleTicksVisibilityButton.setText(areTicksVisible 
        ? "Hide Ticks" : "Show Ticks");
  }
}