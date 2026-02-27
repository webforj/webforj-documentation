package com.webforj.samples.views;

import com.webforj.component.Composite;
import com.webforj.component.button.Button;
import com.webforj.component.field.TextField;
import com.webforj.component.layout.flexlayout.FlexDirection;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;

@Route
@FrameTitle("Bound Component")
public class BoundComponentDemoView extends Composite<FlexLayout> {
  private final FlexLayout self = getBoundComponent();
  private final TextField text = new TextField("Todo Item");
  private FlexLayout todoDisplay;

  public BoundComponentDemoView() {
    self.setMargin("20px")
        .setWidth("50%")
        .setDirection(FlexDirection.COLUMN)
        .add(new DefaultComposite(), new OverrideComposite());
  }

  // Uses the default constructor for the FlexLayout class
  public static class DefaultComposite extends Composite<FlexLayout> {
    // self field enables fluent method chaining from the bound component
    private final FlexLayout self = getBoundComponent();
    private final TextField nameField = new TextField();
    private final Button submit = new Button("Submit");

    DefaultComposite() {
      self.setSpacing("3px")
          .add(nameField, submit);
    }
  }

  // Overrides initBoundComponent() to use a parameterized constructor
  public static class OverrideComposite extends Composite<FlexLayout> {
    private final TextField nameField = new TextField();
    private final Button submit = new Button("Submit");

    @Override
    protected FlexLayout initBoundComponent() {
      return new FlexLayout(nameField, submit);
    }
  }
}
