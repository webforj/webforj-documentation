package com.webforj.samples.views;

import com.webforj.annotation.InlineStyleSheet;
import com.webforj.component.Composite;
import com.webforj.component.Expanse;
import com.webforj.component.event.KeypressEvent;
import com.webforj.component.field.TextField;
import com.webforj.component.html.elements.Div;
import com.webforj.component.html.elements.H1;
import com.webforj.component.layout.flexlayout.FlexAlignment;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.component.optioninput.RadioButton;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;

@Route
@InlineStyleSheet("context://css/composite.css")
@FrameTitle("Composite Component")
public class CompositeView extends Composite<Div> {

  TextField text = new TextField();
  FlexLayout todoDisplay;
  H1 title = new H1("Todos");

  public CompositeView() {
    getBoundComponent().addClassName("frame");

    text.setExpanse(Expanse.XLARGE);
    todoDisplay = FlexLayout.create(text)
        .vertical()
        .build()
        .setSpacing("5px")
        .addClassName("todo--display");
    getBoundComponent().add(title, todoDisplay);

    text.setPlaceholder("Add Todo item. Press Enter to save.")
        .onKeypress(e -> {
          if (e.getKeyCode().equals(KeypressEvent.Key.ENTER) && !text.getText().isBlank()) {
            todoDisplay.add(new TodoItem(text.getText()));
            text.setText("");
          }
        });

    todoDisplay.add(new TodoItem("Groceries"),
        new TodoItem("Water Plants"),
        new TodoItem("Exercise"));
  }

  public class TodoItem extends Composite<FlexLayout> {

    RadioButton radioButton = RadioButton.Switch();
    Div text = new Div();

    TodoItem(String todoText) {
      this.text.setText(todoText);
      getBoundComponent().setSpacing("3px")
          .setAlignment(FlexAlignment.CENTER)
          .addClassName("item__todo--display")
          .add(radioButton, text);

      radioButton.onToggle(e -> {
        if (e.isToggled()) {
          text.setStyle("text-decoration", "line-through");
        } else {
          text.setStyle("text-decoration", "unset");
        }
      });
    }
  }
}