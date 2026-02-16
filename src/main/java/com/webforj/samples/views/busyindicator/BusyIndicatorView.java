package com.webforj.samples.views.busyindicator;

import com.webforj.App;
import com.webforj.BusyIndicator;
import com.webforj.Interval;
import com.webforj.component.Composite;
import com.webforj.component.Theme;
import com.webforj.component.html.elements.Div;
import com.webforj.component.spinner.SpinnerExpanse;
import com.webforj.dispatcher.EventListener;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;

@Route
@FrameTitle("Busy Indicator")
public class BusyIndicatorView extends Composite<Div> {
  private int retry = 1;
  private BusyIndicator indicator = App.getBusyIndicator();

  public BusyIndicatorView() {

    // Advance configurations
    indicator.setText("Initiating spoon pending sequence... Hold tight! This will take 3 seconds.");

    Interval interval = new Interval(3f, this::handleElapsedEvent);

    indicator.open();
    interval.start();
  }

  private void handleElapsedEvent(Interval.ElapsedEvent event) {
    switch (retry) {
      case 1:
        indicator.getSpinner().setTheme(Theme.WARNING).setSpeed(200)
                .setExpanse(SpinnerExpanse.LARGE);
        indicator.setText(
                "Attempt 1: Still trying to bend the spoon... It's trickier than it looks!, Trying harder...");
        break;
      case 2:
        indicator.getSpinner().setTheme(Theme.DANGER);
        indicator.setText(
                "Attempt 2: Spoon bending failed... Maybe it’s magic-proof. Let's stop here.");
        break;
      default:
        event.getInterval().stop();
        indicator.getSpinner().setTheme(Theme.SUCCESS);
        indicator.setText(
                "Demo complete! Remember, it's not about bending spoons, it's about having fun!");
    }

    retry++;
  }
}
