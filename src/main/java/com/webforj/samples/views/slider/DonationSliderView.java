package com.webforj.samples.views.slider;

import static java.util.Map.entry;

import com.webforj.component.Composite;
import com.webforj.component.Theme;
import com.webforj.component.button.Button;
import com.webforj.component.button.ButtonTheme;
import com.webforj.component.icons.TablerIcon;
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

@Route
@FrameTitle("Donation Slider Demo")
public class DonationSliderView extends Composite<FlexLayout> {
  // Donation amount labels
  private static final Map<Integer, String> LABELS =
      Map.ofEntries(
          entry(0, "$0"),
          entry(10, "$10"),
          entry(20, "$20"),
          entry(30, "$30"),
          entry(40, "$40"),
          entry(50, "$50"),
          entry(60, "$60"),
          entry(70, "$70"),
          entry(80, "$80"));

  private final FlexLayout self = getBoundComponent();
  // Donation slider component
  private final Slider donationSlider = new Slider();

  // Current donation value
  private Integer currentDonationValue = 50;

  public DonationSliderView() {
    self.setDirection(FlexDirection.COLUMN)
        .setJustifyContent(FlexJustifyContent.CENTER)
        .setSpacing("var(--dwc-space-l)")
        .setMargin("var(--dwc-space-l)")
        .setAlignment(FlexAlignment.CENTER);

    donationSlider
        .setMax(80)
        .setMin(0)
        .setTicksVisible(true)
        .setMajorTickSpacing(10)
        .setMinorTickSpacing(5)
        .setLabelsVisible(true)
        .setSnapToTicks(true)
        .setTheme(Theme.GRAY)
        .setWidth("500px")
        .setLabels(LABELS)
        .addValueChangeListener(event -> currentDonationValue = event.getValue());

    Button confirmButton =
        new Button(
                "Confirm Donation", ButtonTheme.GRAY, e -> showToastMessage(currentDonationValue))
            .setPrefixComponent(TablerIcon.create("tip-jar-euro"));

    self.add(donationSlider, confirmButton);
  }

  /** Shows a toast message with the donation amount. */
  private void showToastMessage(Integer value) {
    new Toast()
        .setText("Thank you for your generous contribution of $" + value + "!")
        .setPlacement(Placement.BOTTOM)
        .setTheme(Theme.SUCCESS)
        .setDuration(1000)
        .open();
  }
}
