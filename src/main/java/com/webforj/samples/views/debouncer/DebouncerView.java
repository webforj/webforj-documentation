package com.webforj.samples.views.debouncer;

import com.webforj.Debouncer;
import com.webforj.component.Composite;
import com.webforj.component.field.TextField;
import com.webforj.component.html.elements.Paragraph;
import com.webforj.component.layout.flexlayout.FlexDirection;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;

@Route
@FrameTitle("Debouncer Demo")
public class DebouncerView extends Composite<FlexLayout> {

  private final FlexLayout self = getBoundComponent();
  private final TextField input = new TextField();
  private final Paragraph output = new Paragraph();
  private final Paragraph counter = new Paragraph();
  private final Debouncer debounce = new Debouncer(0.5f);
  private int count = 0;

  public DebouncerView() {
    self.setDirection(FlexDirection.COLUMN)
        .setStyle("padding", "20px")
        .setStyle("gap", "10px")
        .setStyle("maxWidth", "400px");

    input.setLabel("Type something (debounced 500ms)");
    input.setPlaceholder("Start typing...");
    input.onModify(e -> {
      count++;
      counter.setText("Key events: " + count);

      debounce.run(() -> {
        output.setText("Debounced: " + e.getText());
        count = 0;
        counter.setText("Key events: 0");
      });
    });

    output.setText("Debounced: (waiting...)");
    counter.setText("Key events: 0");

    self.add(input, counter, output);
  }

  @Override
  protected void onDidDestroy() {
    debounce.cancel();
  }
}
