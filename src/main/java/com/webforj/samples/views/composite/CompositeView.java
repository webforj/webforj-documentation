package com.webforj.samples.views.composite;

import com.webforj.annotation.StyleSheet;
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
@FrameTitle("To-Do List")
@StyleSheet("ws://composite/composite.css")
public class CompositeView extends Composite<Div> {
  private Div self = getBoundComponent();
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
   self.addClassName("frame")
        .add(title, taskInput, taskContainer);
  }

  private void setupEventHandlers() {
    taskInput.onKeypress(e -> {
      String task = taskInput.getText().trim();
      if (e.getKeyCode() == KeypressEvent.Key.ENTER && !task.isEmpty()) {
        taskContainer.add(new SimpleTaskItem(task));
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
    private FlexLayout self;
    private RadioButton toggleButton;
    private Div taskText;
    private Button deleteButton;

    public SimpleTaskItem(String text) {
      initializeComponents(text);
      setupLayout();
      setupEventHandlers();
    }

    private void initializeComponents(String text) {
      self = getBoundComponent();
      toggleButton = RadioButton.Switch();
      taskText = new Div(text)
              .setStyle("flex-grow", "1")
              .addClassName("todo-text");
      deleteButton = new Button("Delete", ButtonTheme.DANGER);
    }

    private void setupLayout() {
      self.setDirection(FlexDirection.ROW)
          .setAlignment(FlexAlignment.CENTER)
          .setSpacing("var(--dwc-space-s)")
          .addClassName("item__todo--display")
          .add(toggleButton, taskText, deleteButton);
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
        self.setVisible(false);
      });
    }
  }
}