package com.webforj.samples.views.slider;

import com.webforj.component.Composite;
import com.webforj.component.layout.flexlayout.FlexAlignment;
import com.webforj.component.layout.flexlayout.FlexDirection;
import com.webforj.component.layout.flexlayout.FlexJustifyContent;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.component.optioninput.CheckBox;
import com.webforj.component.slider.Slider;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;
import static java.util.Map.entry;

import java.util.HashMap;
import java.util.Map;

@Route
@FrameTitle("Slider Tick and Non-Tick Demo")
public class SliderTickNonTickView extends Composite<FlexLayout> {

  Slider slider = new Slider(0, 100, 50);
  CheckBox showTickValuesCheckBox = new CheckBox("Show Tick Values");
  CheckBox showNonTickValuesCheckBox = new CheckBox("Show Non-Tick Values");

  private final Map<Integer, String> tickLabels = Map.ofEntries(
      entry(0, "0"),
      entry(20, "20"),
      entry(40, "40"),
      entry(60, "60"),
      entry(80, "80"),
      entry(100, "100")
  );

  private final Map<Integer, String> nonTickLabels = Map.ofEntries(
      entry(10, "10"),
      entry(30, "30"),
      entry(50, "50"),
      entry(70, "70"),
      entry(90, "90")
  );

  public SliderTickNonTickView() {
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
          .setMajorTickSpacing(50)
          .setMinorTickSpacing(20)
          .setLabelsVisible(true)
          .setTooltipVisibleOnSlideOnly(true)
          .setSnapToTicks(true) 
          .setWidth("400px");

    showTickValuesCheckBox.setChecked(true); 
    showNonTickValuesCheckBox.setChecked(false); 

    showTickValuesCheckBox.onValueChange(event -> updateLabels());
    showNonTickValuesCheckBox.onValueChange(event -> updateLabels());

    FlexLayout checkBoxLayout = new FlexLayout()
            .setDirection(FlexDirection.ROW)
            .setSpacing("var(--dwc-space-s)")
            .setJustifyContent(FlexJustifyContent.CENTER)
            .setAlignment(FlexAlignment.CENTER);

    checkBoxLayout.add(showTickValuesCheckBox, showNonTickValuesCheckBox);

    layout.add(slider, checkBoxLayout);
    updateLabels();
  }

  private void updateLabels() {
    Map<Integer, String> labels = new HashMap<>();

    boolean showTickValues = showTickValuesCheckBox.isChecked();
    boolean showNonTickValues = showNonTickValuesCheckBox.isChecked();

    if (showTickValues) {
      labels.putAll(tickLabels);
    }

    if (showNonTickValues) {
      labels.putAll(nonTickLabels);
    }

    slider.setLabels(labels);
  }
}