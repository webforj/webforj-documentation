package com.webforj.samples.views.terminal;

import java.time.LocalTime;
import java.util.Random;

import com.webforj.Interval;
import com.webforj.component.Composite;
import com.webforj.component.button.Button;
import com.webforj.component.button.ButtonTheme;
import com.webforj.component.icons.TablerIcon;
import com.webforj.component.layout.flexlayout.FlexAlignment;
import com.webforj.component.layout.flexlayout.FlexDirection;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.component.terminal.Terminal;
import com.webforj.router.annotation.Route;
import com.webforj.router.annotation.FrameTitle;

@Route
@FrameTitle("Server Logs Stream")
public class ServerLogsView extends Composite<FlexLayout> {
  private FlexLayout self = getBoundComponent();
  private final Terminal terminal = new Terminal();
  private final Button startButton = new Button("Start Log Stream", ButtonTheme.PRIMARY);
  private final Random random = new Random();
  private final String[] logLevels = { "INFO", "WARN", "DEBUG", "ERROR" };
  private Interval interval;
  private boolean isStreaming = false;

  // Watermark variables
  private static final int HIGH_WATERMARK = 500;
  private static final int LOW_WATERMARK = 200;
  private int bufferedLines = 0;

  public ServerLogsView() {
    self.setDirection(FlexDirection.COLUMN)
        .setAlignment(FlexAlignment.END)
        .setStyle("background-color", "#1e1e1e")
        .setHeight("100vh")
        .setSpacing("0px");

    setupInterval();
    printHelp();

    terminal.setAutoFit(true)
        .setStyle("margin", "0px var(--dwc-space-m)")
        .setSize("95%", "90vh");

    startButton
        .setMaxWidth("180px")
        .setStyle("margin", "1rem")
        .setPrefixComponent(TablerIcon.create("player-play"))
        .onClick(e -> {
          if (isStreaming) {
            stopStreaming();
          } else {
            startStreaming();
          }
        });

    self.add(startButton, terminal);
  }

  @Override
  protected void onDidDestroy() {
    if (interval != null) {
      interval.stop();
    }
  }

  private void startStreaming() {
    isStreaming = true;
    startButton
        .setText("Stop Log Stream")
        .setPrefixComponent(TablerIcon.create("player-pause"));
    terminal.writeln("\u001B[42;30m  Log streaming started...  \u001B[0m");
    interval.start();
  }

  private void stopStreaming() {
    isStreaming = false;
    startButton
        .setText("Start Log Stream")
        .setPrefixComponent(TablerIcon.create("player-play"));
    interval.stop();
    terminal.writeln("\u001B[41;37m  Log streaming stopped.  \u001B[0m");
  }

  private void setupInterval() {
    interval = new Interval(0.01f, e -> {
      if (!isStreaming) {
        return;
      }

      bufferedLines++;

      if (bufferedLines > HIGH_WATERMARK) {
        interval.stop();
      }

      terminal.writeln(generateLog(), c -> {
        bufferedLines--;

        if (bufferedLines < LOW_WATERMARK && isStreaming && !interval.isRunning()) {
          interval.start();
        }
      });
    });
  }

  private String generateLog() {
    String level = logLevels[random.nextInt(logLevels.length)];
    String timestamp = LocalTime.now().toString();
    String message = "Simulated log message...";

    switch (level) {
      case "INFO":
        return "[" + timestamp + "] [\u001B[32mINFO\u001B[0m] " + message;
      case "WARN":
        return "[" + timestamp + "] [\u001B[33mWARN\u001B[0m] " + message;
      case "DEBUG":
        return "[" + timestamp + "] [\u001B[34mDEBUG\u001B[0m] " + message;
      case "ERROR":
        return "[" + timestamp + "] [\u001B[31mERROR\u001B[0m] " + message;
      default:
        return "[" + timestamp + "] [" + level + "] " + message;
    }
  }

  private void printHelp() {
    terminal.writeln("\u001B[1;36mServer Log Stream Demo\u001B[0m");
    terminal.writeln("");
    terminal.writeln("\u001B[1;37mHow to use:\u001B[0m");
    terminal.writeln("- Click the \u001B[32mStart\u001B[0m button to begin streaming logs.");
    terminal.writeln(
        "- Log levels are color-coded: \u001B[32mINFO\u001B[0m, \u001B[33mWARN\u001B[0m, \u001B[34mDEBUG\u001B[0m, \u001B[31mERROR\u001B[0m.");
    terminal.writeln("- The terminal automatically slows down if needed.");
    terminal.writeln("");
    terminal.writeln("\u001B[1;33mTip:\u001B[0m Watch how buffering is managed with high-speed logs.");
  }
}
