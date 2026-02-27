package com.webforj.samples.views.loading;

import com.webforj.Interval;
import com.webforj.annotation.StyleSheet;
import com.webforj.component.Composite;
import com.webforj.component.Theme;
import com.webforj.component.html.elements.Div;
import com.webforj.component.loading.Loading;
import com.webforj.component.spinner.SpinnerExpanse;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;

/**
 * Demo to showcase the different customization options for the LoadingSpinner.
 */
@Route
@FrameTitle("Loading Spinners")
@StyleSheet("ws://css/loadingstyles/loadingspinnerdemo.css")
public class LoadingSpinnerDemoView extends Composite<Div> {
  private final Div self = getBoundComponent();
  // UI Components
  private final Div parentDiv;
  private final Loading loading;
  private final Interval interval;

  // State counter for demo progression
  private int state = 1;

  public LoadingSpinnerDemoView() {
    // Create parent container
    parentDiv = new Div()
        .addClassName("card");

    // Create loading indicator without backdrop
    loading = new Loading("Displaying spinner with all themes...")
        .setBackdropVisible(false);

    // Create interval that cycles through spinner themes and options
    interval = new Interval(3f, event -> {
      // Use switch expression for cleaner state handling
      String text = switch (state) {
        case 1 -> {
          loading.getSpinner().setTheme(Theme.DEFAULT).setExpanse(SpinnerExpanse.SMALL);
          yield "Displaying default theme, small expanse";
        }
        case 2 -> {
          loading.getSpinner().setTheme(Theme.DANGER).setExpanse(SpinnerExpanse.MEDIUM);
          yield "Displaying danger theme, medium expanse";
        }
        case 3 -> {
          loading.getSpinner().setTheme(Theme.GRAY).setExpanse(SpinnerExpanse.LARGE);
          yield "Displaying gray theme, large expanse";
        }
        case 4 -> {
          loading.getSpinner().setTheme(Theme.INFO).setExpanse(SpinnerExpanse.SMALL);
          yield "Displaying info theme, small expanse";
        }
        case 5 -> {
          loading.getSpinner().setTheme(Theme.PRIMARY).setExpanse(SpinnerExpanse.MEDIUM);
          yield "Displaying primary theme, medium expanse";
        }
        case 6 -> {
          loading.getSpinner().setTheme(Theme.SUCCESS).setExpanse(SpinnerExpanse.LARGE);
          yield "Displaying success theme, large expanse";
        }
        case 7 -> {
          loading.getSpinner().setTheme(Theme.WARNING).setExpanse(SpinnerExpanse.SMALL);
          yield "Displaying warning theme, small expanse";
        }
        case 8 -> {
          loading.getSpinner().setClockwise(false);
          yield "Now moving counterclockwise...";
        }
        case 9 -> {
          loading.getSpinner().setSpeed(500);
          yield "Going faster...";
        }
        default -> {
          event.getInterval().stop();
          loading.getSpinner().setTheme(Theme.PRIMARY);
          yield "Demo complete!";
        }
      };

      loading.setText(text);
      state++;
    });

    // Add components and start the demo
    self.add(parentDiv);
    parentDiv.add(loading);
    loading.open();
    interval.start();
  }
}

