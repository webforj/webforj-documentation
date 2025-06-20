package com.webforj.samples.views;

import com.webforj.annotation.InlineStyleSheet;
import com.webforj.component.Composite;
import com.webforj.component.Expanse;
import com.webforj.component.event.KeypressEvent;
import com.webforj.component.field.TextField;
import com.webforj.component.html.elements.Div;
import com.webforj.component.html.elements.H1;
import com.webforj.component.html.elements.Paragraph;
import com.webforj.component.layout.flexlayout.FlexAlignment;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.component.optioninput.RadioButton;
import com.webforj.component.progressbar.ProgressBar;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;

@Route
@InlineStyleSheet("context://css/composite.css")
@FrameTitle("Composite Demo")
public class CompositeView extends Composite<Div> {

  TextField text = new TextField();
  FlexLayout todoDisplay;
  H1 title = new H1("To-do List");
  Paragraph progressLabel = new Paragraph();
  ProgressBar progressBar = new ProgressBar();
  int total = 3;
  int completed = 0;

  public CompositeView() {
    getBoundComponent().addClassName("frame");

    text.setExpanse(Expanse.XLARGE);
    text.setPlaceholder("Add To-do item. Press Enter to save.");

    progressLabel.addClassName("todo-progress-label");
    updateProgress();

    progressBar.setValue(0).addClassName("todo-progress-bar");

    todoDisplay = FlexLayout.create()
      .vertical()
      .build()
      .addClassName("todo--display");

    getBoundComponent().add(title, text, todoDisplay, progressLabel, progressBar);

    text.onKeypress(e -> {
      if (e.getKeyCode().equals(KeypressEvent.Key.ENTER) && !text.getText().isEmpty()) {
        total++;
        todoDisplay.add(new TodoItem(text.getText()));
        updateProgress();
        text.setText("");
      }
    });

    todoDisplay.add(new TodoItem("Groceries"),
        new TodoItem("Water Plants"),
        new TodoItem("Exercise"));
  }

  private void updateProgress() {
    progressLabel.setText(completed + " of " + total + " completed");
    int percent = (int)((double) completed / total * 100);
    progressBar.setValue(percent);
  }

  public class TodoItem extends Composite<FlexLayout> {

    RadioButton radioButton = RadioButton.Switch();
    Div text = new Div();

    TodoItem(String todoText) {
      this.text.setText(todoText);
      this.text.addClassName("todo-text");

      getBoundComponent()
        .setSpacing("var(--dwc-space-s)")
        .setAlignment(FlexAlignment.CENTER)
        .addClassName("item__todo--display")
        .add(radioButton, text);

      radioButton.onToggle(e -> {
        if (e.isToggled()) {
          completed++;
          text.setStyle("text-decoration", "line-through");
        } else {
          completed--;
          text.setStyle("text-decoration", "unset");
        }
        updateProgress();
      });
    }
  }
}