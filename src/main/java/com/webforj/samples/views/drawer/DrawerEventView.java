package com.webforj.samples.views.drawer;

import com.webforj.component.Composite;
import com.webforj.component.button.Button;
import com.webforj.component.button.ButtonTheme;
import com.webforj.component.drawer.Drawer;
import com.webforj.component.field.TextField;
import com.webforj.component.layout.flexlayout.FlexDirection;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.component.optioninput.CheckBox;
import com.webforj.component.toast.Toast;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Route
@FrameTitle("Task Manager Drawer")
public class DrawerEventView extends Composite<FlexLayout> {
  private FlexLayout self = getBoundComponent();
  private Drawer drawer = new Drawer();
  private List<CheckBox> taskList = new ArrayList<>();
  private FlexLayout tasks = new FlexLayout();
  private Button addTaskButton = new Button("Add Task", ButtonTheme.PRIMARY);
  private int taskAmount = 0;

  public DrawerEventView() {
    drawer.setLabel("Task Manager")
            .open();

    drawer.addOpenListener(e -> Toast.show("Drawer Opened", 3000));
    drawer.addCloseListener(e -> Toast.show("Drawer Closed", 3000));

    tasks.setDirection(FlexDirection.COLUMN)
            .setSpacing("var(--dwc-space-s)")
            .setStyle("overflow-y", "auto")
            .setMaxHeight("60vh");

    addTask("Finish project documentation");
    addTask("Call John about the meeting");
    addTask("Prepare slides for tomorrow");

    TextField newTaskField = new TextField("New Task", "")
            .setMaxLength(50);

    addTaskButton.onClick(e -> {
      String taskText = newTaskField.getValue();
      if (!taskText.isBlank() && !newTaskField.isInvalid()) {
        addTask(taskText);
        newTaskField.setValue("");
      }
    });

    Button clearTasksButton = new Button("Clear Completed", ButtonTheme.DANGER);
    clearTasksButton.onClick(e -> clearCompletedTasks());

    FlexLayout footerContainer = new FlexLayout(newTaskField, addTaskButton, clearTasksButton)
            .setDirection(FlexDirection.COLUMN)
            .setSpacing("var(--dwc-space-s)");

    drawer.add(tasks);
    drawer.addToFooter(footerContainer);

    Button openDrawerButton = new Button("Open Task Manager");
    openDrawerButton.onClick(e -> drawer.open());

    self.setMargin("var(--dwc-space-l)")
            .add(openDrawerButton, drawer);
  }

  private void addTask(String taskText) {
    CheckBox task = new CheckBox(taskText);
    taskList.add(task);
    tasks.add(task);
    taskAmount = taskAmount + 1;
    checkTaskLimit();
  }

  private void clearCompletedTasks() {
    Iterator<CheckBox> iterator = taskList.iterator();
    while (iterator.hasNext()) {
      CheckBox task = iterator.next();
      if (task.isChecked()) {
        iterator.remove();
        tasks.remove(task);
        taskAmount = taskAmount - 1;
      }
    }
    checkTaskLimit();
  }

  private void checkTaskLimit() {
    addTaskButton.setEnabled(taskAmount < 50);
  }
}