package com.webforj.samples.views.progressbar;

import com.webforj.Interval;
import com.webforj.component.Composite;
import com.webforj.component.button.Button;
import com.webforj.component.icons.Icon;
import com.webforj.component.icons.TablerIcon;
import com.webforj.component.html.elements.Div;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.component.progressbar.ProgressBar;
import com.webforj.dispatcher.EventListener;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;

@Route
@FrameTitle("Progress Bar Basics")
public class ProgressBarBasicView extends Composite<Div> {

  Button start = new Button("Start"); 
	Button pause = new Button("Pause");
	Button reset = new Button("Reset");
  
	Icon startIcon = TablerIcon.create("player-play");
	Icon pauseIcon = TablerIcon.create("player-pause");
	Icon resetIcon = TablerIcon.create("refresh");

  ProgressBar bar = new ProgressBar("Progress {{x}}% - value: {{value}}");
  FlexLayout buttonContainer = FlexLayout.create(start, pause, reset).build();
  FlexLayout layout = FlexLayout.create(buttonContainer, bar).vertical().build()
      .setMaxWidth("320px")
      .setStyle("margin", "0 auto")
      .setStyle("padding", "20px");

  public ProgressBarBasicView() {

    start.setPrefixComponent(startIcon);
    pause.setPrefixComponent(pauseIcon);
    reset.setPrefixComponent(resetIcon);

    Interval interval = new Interval(0.1f, new EventListener<Interval.ElapsedEvent>() {
      @Override
      public void onEvent(Interval.ElapsedEvent event) {

        Integer progress = bar.getValue() + 1;
        bar.setValue(progress);
        
        if (progress >= bar.getMax()) {
          event.getInterval().stop();
          start.setEnabled(false);
          pause.setEnabled(false);
          bar.setAnimated(false);
        }
      }
    });

    bar.setStriped(true);
    bar.setAnimated(true);

    start.onClick(e -> {
      bar.setAnimated(true);
      start.setEnabled(false);
      pause.setEnabled(true);

      interval.start();
    });

    pause.setEnabled(false);
    pause.onClick(e -> {
      bar.setAnimated(false);
      start.setEnabled(true);
      pause.setEnabled(false);

      interval.stop();
    });

    reset.onClick(e -> {
      bar.setValue(0);
      bar.setAnimated(true);
      start.setEnabled(false);
      pause.setEnabled(true);

      interval.restart();
    });

    getBoundComponent().add(layout);
  }
}
