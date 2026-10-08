package com.webforj.samples.views.composite;

import com.webforj.bundle.annotation.BundleEntry;
import com.webforj.component.Composite;
import com.webforj.component.Expanse;
import com.webforj.component.button.Button;
import com.webforj.component.button.ButtonTheme;
import com.webforj.component.card.Card;
import com.webforj.component.event.KeypressEvent;
import com.webforj.component.field.TextField;
import com.webforj.component.html.elements.Div;
import com.webforj.component.html.elements.H1;
import com.webforj.component.layout.flexlayout.FlexAlignment;
import com.webforj.component.layout.flexlayout.FlexDirection;
import com.webforj.component.layout.flexlayout.FlexJustifyContent;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.component.layout.toolbar.Toolbar;
import com.webforj.component.optioninput.RadioButton;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;

@Route
@FrameTitle("To-Do List")
@BundleEntry("composite/composite.css")
public class CompositeView extends Composite<FlexLayout> {
  private final FlexLayout self = getBoundComponent();
  private final TextField taskInput = new TextField();
  private final Card taskContainer = new Card();
  private final H1 title = new H1("To-do List");
  private final Card frame = new Card();

  public CompositeView() {
    initializeComponents();
    setupLayout();
    setupEventHandlers();
    addSampleTasks();
  }

  private void initializeComponents() {
    taskInput.setPlaceholder("Enter a new task and press Enter...").setExpanse(Expanse.XLARGE);
    taskInput.setWidth("100%");
    taskContainer.addClassName("todo--display");
  }

  private void setupLayout() {
    frame
        .setWidth(600)
        .setMaxWidth("100%")
        .addToTitle(title)
        .addToBody(taskInput, taskContainer)
        .setExpanse(Expanse.XLARGE)
        .addClassName("frame");

    self.setDirection(FlexDirection.COLUMN)
        .setAlignment(FlexAlignment.CENTER)
        .setJustifyContent(FlexJustifyContent.CENTER)
        .addClassName("todo-stage")
        .add(frame);
  }

  private void setupEventHandlers() {
    taskInput.onKeypress(
        e -> {
          String task = taskInput.getText().trim();
          if (e.getKeyCode() == KeypressEvent.Key.ENTER && !task.isEmpty()) {
            taskContainer.add(new SimpleTaskItem(task));
            taskInput.setText("");
          }
        });
  }

  private void addSampleTasks() {
    taskContainer.add(new SimpleTaskItem("Review the documentation"));
    taskContainer.add(new SimpleTaskItem("Write unit tests"));
    taskContainer.add(new SimpleTaskItem("Deploy application"));
  }

  public static class SimpleTaskItem extends Composite<Toolbar> {

    private final Toolbar self = getBoundComponent();
    private final RadioButton toggleButton = RadioButton.Switch();
    private final Div taskText = new Div();
    private final Button deleteButton = new Button("Delete", ButtonTheme.DANGER);

    public SimpleTaskItem(String text) {
      initializeComponents(text);
      setupLayout();
      setupEventHandlers();
    }

    private void initializeComponents(String text) {
      taskText.setText(text).addClassName("todo-text");
    }

    private void setupLayout() {
      self.addToStart(toggleButton)
          .addToTitle(taskText)
          .addToEnd(deleteButton)
          .addClassName("item__todo--display")
          .setCompact(true);
    }

    private void setupEventHandlers() {
      toggleButton.onToggle(
          e -> {
            if (e.isToggled()) {
              taskText.setStyle("text-decoration", "line-through");
            } else {
              taskText.setStyle("text-decoration", "none");
            }
          });

      deleteButton.onClick(
          e -> {
            self.destroy();
          });
    }
  }
}
