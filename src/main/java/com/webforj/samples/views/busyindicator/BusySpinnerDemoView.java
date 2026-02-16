package com.webforj.samples.views.busyindicator;

import com.webforj.App;
import com.webforj.BusyIndicator;
import com.webforj.Interval;
import com.webforj.component.Composite;
import com.webforj.component.Expanse;
import com.webforj.component.Theme;
import com.webforj.component.html.elements.Div;
import com.webforj.component.spinner.SpinnerExpanse;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;

/**
 * Demo to showcase the different customization options for the spinner in the BusyIndicator.
 */

@Route
@FrameTitle("Busy Spinners")
public class BusySpinnerDemoView extends Composite<Div> {
  private int state = 1;
  private BusyIndicator indicator;
  private Interval interval;
  
  public BusySpinnerDemoView() {
    indicator  = App.getBusyIndicator().setText("Displaying spinner with all themes...");

    interval = new Interval(3f, this::handleElapsedEvent);

    indicator.open();
    interval.start();
  }

  private void handleElapsedEvent(Interval.ElapsedEvent event) {
    switch (state) {
      case 1:
        updateIndicator(Theme.DEFAULT, SpinnerExpanse.SMALL,
                "Displaying default theme, small expanse");
        break;
      case 2:
        updateIndicator(Theme.DANGER, SpinnerExpanse.MEDIUM,
                "Displaying danger theme, medium expanse");
        break;
      case 3:
        updateIndicator(Theme.GRAY, SpinnerExpanse.LARGE,
                "Displaying gray theme, large expanse");
        break;
      case 4:
        updateIndicator(Theme.INFO, SpinnerExpanse.SMALL,
                "Displaying info theme, small expanse");
        break;
      case 5:
        updateIndicator(Theme.PRIMARY, SpinnerExpanse.MEDIUM,
                "Displaying primary theme, medium expanse");
        break;
      case 6:
        updateIndicator(Theme.SUCCESS, SpinnerExpanse.LARGE,
                "Displaying success theme, large expanse");
        break;
      case 7:
        updateIndicator(Theme.WARNING, SpinnerExpanse.SMALL,
                "Displaying warning theme, small expanse");
        break;
      case 8:
        indicator.getSpinner().setClockwise(false);
        indicator.setText("Now moving counterclockwise...");
        break;
      case 9:
        indicator.getSpinner().setSpeed(500);
        indicator.setText("Going faster...");
        break;
      default:
        event.getInterval().stop();
        indicator.getSpinner().setTheme(Theme.PRIMARY);
        indicator.setText("Demo complete!");
    }
    state++;
  }

  private void updateIndicator(Theme theme, SpinnerExpanse expanse, String text) {
    indicator
            .setText(text)
            .getSpinner()
            .setTheme(theme)
            .setExpanse(expanse);
  }
}
