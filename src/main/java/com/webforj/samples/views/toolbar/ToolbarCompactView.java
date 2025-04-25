package com.webforj.samples.views.toolbar;

import com.webforj.Interval;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;
import com.webforj.component.html.elements.H1;
import com.webforj.component.html.elements.Img;
import com.webforj.component.Composite;
import com.webforj.component.Expanse;
import com.webforj.component.Theme;
import com.webforj.component.button.Button;
import com.webforj.component.button.event.ButtonClickEvent;
import com.webforj.component.progressbar.ProgressBar;
import com.webforj.component.layout.applayout.AppDrawerToggle;
import com.webforj.component.layout.applayout.AppLayout;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.component.layout.toolbar.Toolbar;
import com.webforj.component.optiondialog.MessageDialog;
import com.webforj.component.optiondialog.OptionDialog;

@Route
@FrameTitle("Toolbar Compact")
public class ToolbarCompactView extends Composite<AppLayout> {

  AppLayout appLayout = getBoundComponent();
  Button analyzeButton = new Button("Analyze data", this::analyze);
  ProgressBar bar = new ProgressBar();

  Toolbar mainToolbar = new Toolbar();
  Toolbar progressToolbar = new Toolbar();
  int delayStart = -2;
  Interval interval = new Interval(0.1f, this::progressLoad);

  public ToolbarCompactView() {

    mainToolbar.addToStart(new AppDrawerToggle());
    mainToolbar.addToTitle(new H1("webforJ App"));

    bar.setHeight("clamp(3px, calc(-0.01 * 100vw + 15px), 12px)");

    progressToolbar.addToContent(bar);
    progressToolbar.setCompact(true);

    analyzeButton.setDisableOnClick(true);
    analyzeButton.setExpanse(Expanse.XLARGE);

    FlexLayout flexLayout = FlexLayout.create(analyzeButton)
        .justify().center()
        .build();

    appLayout.setDrawerHeaderVisible(true)
        .addToDrawerTitle(new Img("https://docs.webforj.com/img/webforj_icon.svg"))
        .addToHeader(mainToolbar, progressToolbar)
        .addToContent(flexLayout)
        .setDrawerOpened(false);
  }

  private void analyze(ButtonClickEvent e) {
    delayStart = -2;
    bar.setTheme(Theme.PRIMARY);
    interval.restart();
  }

  private void progressLoad(Interval.ElapsedEvent e) {
    Integer progress = bar.getValue() + ++delayStart;
    bar.setValue(progress);

    if (progress >= bar.getMax()) {
      e.getInterval().stop();
      bar.setAnimated(false);
      bar.setTheme(Theme.WARNING);
      OptionDialog.showMessageDialog("No data to analyze!","Error", MessageDialog.MessageType.WARNING);
      analyzeButton.setEnabled(true);
      bar.setValue(0);
    }
  }

}