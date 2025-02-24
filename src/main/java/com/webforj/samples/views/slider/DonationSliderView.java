package com.webforj.samples.views.slider;

import com.webforj.component.Composite;
import com.webforj.component.Theme;
import com.webforj.component.button.Button;
import com.webforj.component.button.ButtonTheme;
import com.webforj.component.layout.flexlayout.FlexAlignment;
import com.webforj.component.layout.flexlayout.FlexDirection;
import com.webforj.component.layout.flexlayout.FlexJustifyContent;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.component.slider.Slider;
import com.webforj.component.toast.Toast;
import com.webforj.component.toast.Toast.Placement;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;
import java.util.Map;
import static java.util.Map.entry;

@Route
@FrameTitle("Donation Slider Demo")
public class DonationSliderView extends Composite<FlexLayout> {

  Slider donationSlider = new Slider();
  Integer currentDonationValue = 50;

  public DonationSliderView() {
    FlexLayout layout = getBoundComponent();
    layout.setDirection(FlexDirection.ROW)
      .setJustifyContent(FlexJustifyContent.CENTER)
      .setSpacing("var(--dwc-space-l)")
      .setMargin("var(--dwc-space-l)")
      .setAlignment(FlexAlignment.CENTER);

    donationSlider.setMax(80)
    .setMin(0)
    .setTicksVisible(true)
    .setMajorTickSpacing(10)
    .setMinorTickSpacing(5)
    .setLabelsVisible(true)
    .setSnapToTicks(true)
    .setWidth("300px");

    Map<Integer, String> labels = Map.ofEntries(
        entry(0, "$0"),
        entry(10, "$10"),
        entry(20, "$20"),
        entry(30, "$30"),
        entry(40, "$40"),
        entry(50, "$50"),
        entry(60, "$60"),
        entry(70, "$70"),
        entry(80, "$80")
    );

    donationSlider.setLabels(labels);
    donationSlider.addValueChangeListener(event -> {
      currentDonationValue = event.getValue();
    });

    Button confirmButton = new Button("Confirm Donation").setTheme(ButtonTheme.PRIMARY);
    confirmButton.onClick(e -> showToastMessage(currentDonationValue));

    layout.add(donationSlider, confirmButton);
  }

  private void showToastMessage(Integer value) {
    Toast toast = new Toast();
    toast.setText("Thank you for your generous contribution of $" + value + "!")
         .setPlacement(Placement.BOTTOM)
         .setTheme(Theme.SUCCESS)
         .setDuration(1000);
    toast.open();
  }
}