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

  TextField text = new TextField("Todo Item");
  FlexLayout todoDisplay;

  public BoundComponentDemoView() {
    getBoundComponent().setMargin("20px").setWidth("50%")
        .setDirection(FlexDirection.COLUMN)
        .add(new DefaultComposite(), new OverrideComposite());

  }

  // Uses the default constructor for the FlexLayout class
  public static class DefaultComposite extends Composite<FlexLayout> {

    TextField nameField;
    Button submit;

    DefaultComposite() {
      nameField = new TextField();
      submit = new Button("Submit");
      getBoundComponent()
          .setSpacing("3px")
          .add(nameField, submit);
    }
  }

  // Overrides initBoundComponent() to use a parameterized constructor
  public static class OverrideComposite extends Composite<FlexLayout> {

    TextField nameField;
    Button submit;

    @Override
    protected FlexLayout initBoundComponent() {
      nameField = new TextField();
      submit = new Button("Submit");
      return new FlexLayout(nameField, submit);
    }
  }
}