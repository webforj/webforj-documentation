package com.webforj.samples.views.markdownviewer;

import com.webforj.component.Composite;
import com.webforj.component.button.Button;
import com.webforj.component.button.ButtonTheme;
import com.webforj.component.layout.flexlayout.FlexDirection;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.component.list.ChoiceBox;
import com.webforj.component.markdown.MarkdownViewer;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;

@Route
@FrameTitle("Progressive Rendering Demo")
public class MarkdownViewerProgressiveView extends Composite<FlexLayout> {

  private final MarkdownViewer viewer = new MarkdownViewer();
  private final Button startButton = new Button("Start", ButtonTheme.PRIMARY);
  private final Button stopButton = new Button("Stop", ButtonTheme.DANGER);
  private final ChoiceBox speedChoice = new ChoiceBox();

  private static final String SAMPLE_CONTENT = """
      # The Octopus: Nature's Escape Artist

      Octopuses are **incredibly intelligent** creatures with some remarkable abilities.

      ## Fun Facts

      - They have **three hearts** and blue blood
      - Each arm contains its own "mini-brain"
      - They can change color in just 200 milliseconds
      - Some species can edit their own RNA

      ## Escape Artists

      Octopuses are famous for escaping aquariums:

      ```
      1. Squeeze through tiny gaps
      2. Unscrew jar lids from inside
      3. Short out lights by splashing water
      4. Make a run for the ocean
      ```

      > "The octopus is the closest we will come to meeting an intelligent alien." - Peter Godfrey-Smith
      """;

  public MarkdownViewerProgressiveView() {
    FlexLayout self = getBoundComponent();
    self.setDirection(FlexDirection.COLUMN)
        .setSpacing("var(--dwc-space-m)")
        .setPadding("var(--dwc-space-l)");

    speedChoice.setLabel("Render Speed");
    speedChoice.add("2", "Slow (2)");
    speedChoice.add("4", "Default (4)");
    speedChoice.add("6", "Fast (6)");
    speedChoice.add("10", "Very Fast (10)");
    speedChoice.selectIndex(1);
    speedChoice.onSelect(e -> {
      int speed = Integer.parseInt(speedChoice.getSelected().getKey().toString());
      viewer.setRenderSpeed(speed);
    });

    viewer.setProgressiveRender(true)
        .setRenderSpeed(4)
        .setMinHeight("350px")
        .setStyle("border", "1px solid var(--dwc-color-default)")
        .setStyle("border-radius", "var(--dwc-border-radius-m)")
        .setStyle("padding", "var(--dwc-space-m)");

    FlexLayout buttons = new FlexLayout();
    buttons.setSpacing("var(--dwc-space-s)");
    buttons.add(startButton, stopButton);

    stopButton.setEnabled(false);

    startButton.onClick(e -> {
      viewer.clear();
      viewer.setContent(SAMPLE_CONTENT);
      startButton.setEnabled(false);
      stopButton.setEnabled(true);

      viewer.whenRenderComplete().thenAccept(v -> {
        startButton.setEnabled(true);
        stopButton.setEnabled(false);
      });
    });

    stopButton.onClick(e -> {
      viewer.stop();
      startButton.setEnabled(true);
      stopButton.setEnabled(false);
    });

    self.add(speedChoice, viewer, buttons);
  }
}