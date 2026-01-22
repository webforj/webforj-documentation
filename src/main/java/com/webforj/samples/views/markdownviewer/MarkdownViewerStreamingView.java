package com.webforj.samples.views.markdownviewer;

import com.webforj.component.Composite;
import com.webforj.component.button.Button;
import com.webforj.component.button.ButtonTheme;
import com.webforj.component.html.elements.Div;
import com.webforj.component.layout.flexlayout.FlexDirection;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.component.markdown.MarkdownViewer;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;

import java.util.Timer;
import java.util.TimerTask;

@Route
@FrameTitle("MarkdownViewer Streaming")
public class MarkdownViewerStreamingView extends Composite<FlexLayout> {
  private final MarkdownViewer viewer = new MarkdownViewer();
  private final Button startButton = new Button("Start Stream");
  private final Button stopButton = new Button("Stop");
  private Timer streamTimer;
  private int charIndex;

  private static final String SAMPLE_RESPONSE = """
      # Streaming Response

      This content is being **progressively rendered** to simulate an AI chat response.

      ## How it works

      1. Content arrives in chunks from the server
      2. `append()` adds each chunk to the buffer
      3. Progressive rendering displays it character-by-character

      ```java
      viewer.setProgressiveRender(true);
      viewer.append(chunk);
      ```

      The typewriter effect creates a natural reading experience! 🚀
      """;

  public MarkdownViewerStreamingView() {
    FlexLayout self = getBoundComponent();
    self.setDirection(FlexDirection.COLUMN)
        .setSpacing("var(--dwc-space-m)")
        .setStyle("padding", "var(--dwc-space-l)")
        .setStyle("maxWidth", "600px");

    viewer.setProgressiveRender(true)
        .setRenderSpeed(6);

    Div scrollContainer = new Div();
    scrollContainer.setStyle("height", "300px")
        .setStyle("overflowY", "auto")
        .setStyle("border", "1px solid var(--dwc-color-default)")
        .setStyle("borderRadius", "var(--dwc-border-radius-m)")
        .setStyle("padding", "var(--dwc-space-m)");
    scrollContainer.add(viewer);

    startButton.setTheme(ButtonTheme.PRIMARY);
    stopButton.setTheme(ButtonTheme.DANGER);
    stopButton.setVisible(false);

    startButton.onClick(e -> startStream());
    stopButton.onClick(e -> stopStream());

    FlexLayout buttons = FlexLayout.create(startButton, stopButton).horizontal().build();
    buttons.setSpacing("var(--dwc-space-s)");

    self.add(scrollContainer, buttons);
  }

  private void startStream() {
    viewer.clear();
    charIndex = 0;
    startButton.setVisible(false);
    stopButton.setVisible(true);

    streamTimer = new Timer();
    streamTimer.scheduleAtFixedRate(new TimerTask() {
      @Override
      public void run() {
        if (charIndex < SAMPLE_RESPONSE.length()) {
          int end = Math.min(charIndex + 3, SAMPLE_RESPONSE.length());
          viewer.append(SAMPLE_RESPONSE.substring(charIndex, end));
          charIndex = end;
        } else {
          streamComplete();
        }
      }
    }, 0, 50);
  }

  private void stopStream() {
    if (streamTimer != null) {
      streamTimer.cancel();
      streamTimer = null;
    }
    viewer.stop();
    resetButtons();
  }

  private void streamComplete() {
    if (streamTimer != null) {
      streamTimer.cancel();
      streamTimer = null;
    }
    viewer.whenRenderComplete().thenAccept(v -> resetButtons());
  }

  private void resetButtons() {
    startButton.setVisible(true);
    stopButton.setVisible(false);
  }
}