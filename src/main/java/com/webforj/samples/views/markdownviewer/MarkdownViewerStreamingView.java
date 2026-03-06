package com.webforj.samples.views.markdownviewer;

import com.webforj.annotation.StyleSheet;
import com.webforj.component.Composite;
import com.webforj.component.button.Button;
import com.webforj.component.button.ButtonTheme;
import com.webforj.component.html.elements.Div;
import com.webforj.component.html.elements.Span;
import com.webforj.component.icons.TablerIcon;
import com.webforj.component.layout.flexlayout.FlexDirection;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.component.markdown.MarkdownViewer;
import com.webforj.component.field.TextField;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;
import com.webforj.Interval;

import com.webforj.component.event.KeypressEvent;

import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

@Route
@FrameTitle("Streaming Demo")
@StyleSheet("ws://css/markdownviewer/markdownviewerstreaming.css")
public class MarkdownViewerStreamingView extends Composite<FlexLayout> {

  private static final List<String> RESPONSES = List.of(
      """
      ## Quick Pasta Recipe 🍝

      Here's a simple **aglio e olio** you can make in 15 minutes:

      1. Boil spaghetti until al dente
      2. Sauté sliced garlic in olive oil
      3. Add red pepper flakes
      4. Toss with pasta and parsley

      > Pro tip: Save some pasta water to make the sauce silky!
      """,
      """
      ### Random Facts About Space

      The universe is **wild**. Consider this:

      - A day on Venus is longer than its year
      - There's a planet made of diamonds (55 Cancri e)
      - Space is completely silent
      - Neutron stars can spin 600 times per second

      | Planet | Fun Fact |
      |--------|----------|
      | Jupiter | Has 95 known moons |
      | Saturn | Could float in water |
      | Mars | Home to the tallest volcano |
      """,
      """
      Here's a quick **productivity tip**:

      The **Pomodoro Technique** works like this:

      ```
      1. Work for 25 minutes
      2. Take a 5-minute break
      3. Repeat 4 times
      4. Take a longer 15-30 min break
      ```

      It's simple but effective for maintaining focus throughout the day!

      > "Focus is more about saying no than saying yes." - Steve Jobs
      """
  );

  private final FlexLayout messagesArea;
  private final MarkdownViewer viewer = new MarkdownViewer();
  private final TextField input = new TextField();
  private final Button sendButton = new Button(TablerIcon.create("send"));
  private final Button stopButton = new Button(TablerIcon.create("square-filled"));

  private Div thinkingIndicator;
  private Interval streamInterval;
  private Interval delayInterval;
  private final Random random = new Random();

  public MarkdownViewerStreamingView() {
    FlexLayout self = getBoundComponent();
    self.setDirection(FlexDirection.COLUMN)
        .addClassName("chat")
        .setStyle("overflow", "hidden");

    messagesArea = createMessagesArea();

    self.add(createHeader(), messagesArea, createInputArea());
    self.setItemGrow(1, messagesArea);
  }

  private Div createHeader() {
    Div header = new Div();
    header.addClassName("chat__header");
    header.add(TablerIcon.create("message-chatbot"), new Span("AI Chat Demo"));
    return header;
  }

  private FlexLayout createMessagesArea() {
    FlexLayout area = FlexLayout.create(viewer).vertical().build();
    area.addClassName("chat__messages");
    area.setStyle("overflowY", "auto");

    viewer.setProgressiveRender(true)
        .setAutoScroll(true)
        .setRenderSpeed(6);

    return area;
  }

  private Div createInputArea() {
    Div inputArea = new Div();
    inputArea.addClassName("chat__input-area");

    input.setPlaceholder("Type a message...");
    input.setWidth("100%");
    input.onKeypress(e -> {
      if (e.getKeyCode().equals(KeypressEvent.Key.ENTER)) {
        sendMessage();
      }
    });

    sendButton.setTheme(ButtonTheme.PRIMARY);
    sendButton.onClick(e -> sendMessage());

    stopButton.setTheme(ButtonTheme.DANGER);
    stopButton.setVisible(false);
    stopButton.onClick(e -> stopStreaming());

    FlexLayout buttonWrapper = FlexLayout.create(sendButton, stopButton).build();
    input.setSuffixComponent(buttonWrapper);
    inputArea.add(input);

    return inputArea;
  }

  private void sendMessage() {
    String message = input.getText();
    if (message == null || message.trim().isEmpty()) {
      return;
    }

    if (!viewer.getContent().isEmpty()) {
      viewer.append("\n\n---\n\n");
    }
    viewer.append("""
        <p style="text-align:right;color:var(--dwc-color-primary);font-weight:500">%s</p>

        """.formatted(message.trim()));

    input.setText("");
    showThinking();

    delayInterval = new Interval(0.6f, e -> {
      delayInterval.stop();
      hideThinking();
      startStreaming();
    });
    delayInterval.start();
  }

  private void startStreaming() {
    sendButton.setVisible(false);
    stopButton.setVisible(true);

    String response = RESPONSES.get(random.nextInt(RESPONSES.size()));
    AtomicInteger index = new AtomicInteger(0);

    streamInterval = new Interval(0.04f, e -> {
      int current = index.get();
      if (current < response.length()) {
        int end = Math.min(current + 4 + random.nextInt(4), response.length());
        viewer.append(response.substring(current, end));
        index.set(end);
      } else {
        streamInterval.stop();
        viewer.whenRenderComplete().thenAccept(v -> {
          sendButton.setVisible(true);
          stopButton.setVisible(false);
          input.focus();
        });
      }
    });
    streamInterval.start();
  }

  private void stopStreaming() {
    if (delayInterval != null) {
      delayInterval.stop();
      delayInterval = null;
    }
    if (streamInterval != null) {
      streamInterval.stop();
      streamInterval = null;
    }
    hideThinking();
    viewer.stop();
    sendButton.setVisible(true);
    stopButton.setVisible(false);
    input.focus();
  }

  private void showThinking() {
    thinkingIndicator = new Div();
    thinkingIndicator.addClassName("chat__thinking");
    thinkingIndicator.add(TablerIcon.create("loader-2"), new Span("Thinking..."));
    messagesArea.add(thinkingIndicator);
  }

  private void hideThinking() {
    if (thinkingIndicator != null) {
      messagesArea.remove(thinkingIndicator);
      thinkingIndicator = null;
    }
  }

  @Override
  protected void onDestroy() {
    if (delayInterval != null) {
      delayInterval.stop();
    }
    if (streamInterval != null) {
      streamInterval.stop();
    }
  }
}