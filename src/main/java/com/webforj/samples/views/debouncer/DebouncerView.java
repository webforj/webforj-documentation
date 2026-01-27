package com.webforj.samples.views.debouncer;

import com.webforj.Debouncer;
import com.webforj.component.Composite;
import com.webforj.component.field.TextArea;
import com.webforj.component.field.TextField;
import com.webforj.component.layout.flexlayout.FlexDirection;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;

@Route
@FrameTitle("Debouncer Demo")
public class DebouncerView extends Composite<FlexLayout> {

  private final FlexLayout self = getBoundComponent();
  private final TextField input = new TextField();
  private final TextArea output = new TextArea();
  private final Debouncer debouncer = new Debouncer(0.5f);
  private int count = 0;

  public DebouncerView() {
    self.setDirection(FlexDirection.COLUMN)
        .setMaxWidth("400px")
        .setMargin("var(--dwc-space-xl) auto")
        .setPadding("var(--dwc-space-l)")
        .setSpacing("var(--dwc-space-l)");

    input.setLabel("Type something");
    input.setPlaceholder("Start typing...");
    input.setHelperText("Key events: 0");
    input.onModify(e -> {
      count++;
      input.setHelperText("Key events: " + count);

      debouncer.run(() -> {
        output.setValue(e.getText());
        input.setHelperText("Key events: 0");
        count = 0;
      });
    });

    output.setLabel("Debounced output");
    output.setReadOnly(true);

    self.add(input, output);
  }

  @Override
  protected void onDidDestroy() {
    debouncer.cancel();
  }
}
