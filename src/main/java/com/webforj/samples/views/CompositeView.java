package com.webforj.samples.views;

import com.webforj.annotation.InlineStyleSheet;
import com.webforj.component.Composite;
import com.webforj.component.Expanse;
import com.webforj.component.button.Button;
import com.webforj.component.button.ButtonTheme;
import com.webforj.component.event.KeypressEvent;
import com.webforj.component.field.TextField;
import com.webforj.component.html.elements.Div;
import com.webforj.component.html.elements.H1;
import com.webforj.component.layout.flexlayout.FlexAlignment;
import com.webforj.component.layout.flexlayout.FlexDirection;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.component.optioninput.RadioButton;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;

@Route
@InlineStyleSheet("context://css/composite.css")
@FrameTitle("Constructor Setup Demo")
public class CompositeView extends Composite<Div> {

  private TextField taskInput;
  private FlexLayout taskContainer;
  private H1 title = new H1("To-do List");

  public CompositeView() {
    initializeComponents();
    setupLayout();
    setupEventHandlers();
    addSampleTasks();
  }

  private void initializeComponents() {
    taskInput = new TextField()
        .setPlaceholder("Enter a new task and press Enter...")
        .setExpanse(Expanse.XLARGE);
        
    taskContainer = FlexLayout.create()
        .vertical()
        .build()
        .setSpacing("var(--dwc-space-s)")
        .addClassName("todo--display");
  }

  private void setupLayout() {
    getBoundComponent()
        .addClassName("frame")
        .add(title, taskInput, taskContainer);
  }

  private void setupEventHandlers() {
    taskInput.onKeypress(e -> {
      if (e.getKeyCode().equals(KeypressEvent.Key.ENTER) && !taskInput.getText().trim().isEmpty()) {
        taskContainer.add(new SimpleTaskItem(taskInput.getText().trim()));
        taskInput.setText("");
      }
    });
  }

  private void addSampleTasks() {
    taskContainer.add(new SimpleTaskItem("Review documentation"));
    taskContainer.add(new SimpleTaskItem("Write unit tests"));
    taskContainer.add(new SimpleTaskItem("Deploy application"));
  }

  public static class SimpleTaskItem extends Composite<FlexLayout> {
    
    private RadioButton toggleButton;
    private Div taskText;
    private Button deleteButton;

    public SimpleTaskItem(String text) {
      initializeComponents(text);
      setupLayout();
      setupEventHandlers();
    }

    private void initializeComponents(String text) {
      toggleButton = RadioButton.Switch();
      taskText = new Div(text).addClassName("todo-text");
      deleteButton = new Button("Delete", ButtonTheme.DANGER);
    }

    private void setupLayout() {
      getBoundComponent()
          .setDirection(FlexDirection.ROW)
          .setAlignment(FlexAlignment.CENTER)
          .setSpacing("var(--dwc-space-s)")
          .addClassName("item__todo--display")
          .add(toggleButton, taskText);
      
      taskText.setStyle("flex-grow", "1");
      getBoundComponent().add(deleteButton);
    }

    private void setupEventHandlers() {
      toggleButton.onToggle(e -> {
        if (e.isToggled()) {
          taskText.setStyle("text-decoration", "line-through");
        } else {
          taskText.setStyle("text-decoration", "none");
        }
      });

      deleteButton.onClick(e -> {
        getBoundComponent().setVisible(false);
      });
    }
  }
}