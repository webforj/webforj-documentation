package com.webforj.samples.views.flexlayout.container;

import java.util.ArrayList;
import java.util.HashMap;

import com.webforj.component.Composite;
import com.webforj.component.event.ModifyEvent;
import com.webforj.component.field.MaskedNumberFieldSpinner;
import com.webforj.component.html.elements.Div;
import com.webforj.annotation.StyleSheet;
import com.webforj.component.list.ChoiceBox;
import com.webforj.component.list.event.ListSelectEvent;
import com.webforj.component.layout.flexlayout.FlexAlignment;
import com.webforj.component.layout.flexlayout.FlexContentAlignment;
import com.webforj.component.layout.flexlayout.FlexDirection;
import com.webforj.component.layout.flexlayout.FlexJustifyContent;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.component.layout.flexlayout.FlexWrap;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;
import com.webforj.samples.components.Box;
import com.webforj.samples.components.CodeDisplay;

@Route
@StyleSheet("ws://css/flexlayout/container/flexContainerBuilder.css")
@FrameTitle("Container Builder")
public class FlexContainerBuilderView extends Composite<Div> {
  private static final int HUE = 36; // 360 / 10
  private Div self = getBoundComponent();
  private MaskedNumberFieldSpinner spinner = new MaskedNumberFieldSpinner();

  private FlexLayout boxLayout;
  private ArrayList<Box> boxes;
  private int numBoxes;

  private String javaCode;
  private CodeDisplay codeWindow = new CodeDisplay();
  private HashMap<String, String> codeSnippetBuilder;

  public static final String REGEX = "^(.+?)-";

  public FlexContainerBuilderView() {
    self.addClassName("app__frame");

    FlexLayout mainLayout = FlexLayout.create()
            .horizontal()
            .build();

    boxLayout = FlexLayout.create()
            .horizontal()
            .build()
            .addClassName("button__container");

    FlexLayout flexContainerOptions = FlexLayout.create()
            .vertical()
            .build()
            .addClassName("flex__options");

    spinner.setLabel("Number of Boxes")
            .setMin(1d)
            .setText("1")
            .onModify(this::spinnerChange);

    boxes = new ArrayList<>();
    numBoxes = 0;
    addBox(1);

    flexContainerOptions.add(spinner);

    mainLayout.add(flexContainerOptions, boxLayout);
    self.add(mainLayout);

    ChoiceBox directions = new ChoiceBox()
            .setLabel("Direction Options");
    directions.onSelect(this::selectDirection);
    for (FlexDirection justify : FlexDirection.values()) {
      String label = justify.getValue();
      String key = justify.toString().toLowerCase();
      String text = label.substring(0, 1).toUpperCase()
              + label.substring(1);
      directions.add("." + key + "()", text);
    }
    directions.selectIndex(0);

    ChoiceBox justifications = new ChoiceBox()
            .setLabel("Justification Options");
    justifications.onSelect(this::selectJustification);
    for (FlexJustifyContent justify : FlexJustifyContent.values()) {
      String label = justify.getValue().replaceAll(REGEX, "");
      String key = justify.toString().toLowerCase().replaceAll(REGEX, "");
      String text = label.substring(0, 1).toUpperCase() + label.substring(1);
      justifications.add(".justify()." + key + "()", text);
    }
    justifications.selectIndex(0);

    ChoiceBox alignments = new ChoiceBox()
            .setLabel("Alignment Options");
    alignments.onSelect(this::selectAlignment);
    for (FlexAlignment justify : FlexAlignment.values()) {
      String label = justify.getValue().replaceAll(REGEX, "");
      String key = justify.toString().toLowerCase().replaceAll(REGEX, "");
      String text = label.substring(0, 1).toUpperCase() + label.substring(1);
      alignments.add(".align()." + key + "()", text);
    }
    alignments.selectIndex(0);

    ChoiceBox contentAlignments = new ChoiceBox()
            .setLabel("Content-Alignment Options");
    contentAlignments.onSelect(this::selectAlignContent);
    for (FlexContentAlignment justify : FlexContentAlignment.values()) {
      String label = justify.getValue().replaceAll(REGEX, "");
      String key = justify.toString().toLowerCase().replaceAll(REGEX, "");
      String text = label.substring(0, 1).toUpperCase() + label.substring(1);
      contentAlignments.add(".contentAlign()." + key + "()", text);
    }
    contentAlignments.selectIndex(0);

    ChoiceBox wraps = new ChoiceBox()
            .setLabel("Wrap Options");
    wraps.onSelect(this::selectWrap);
    for (FlexWrap justify : FlexWrap.values()) {
      String label = justify.getValue().replaceAll(REGEX, "");
      String key = justify.toString().toLowerCase().replaceAll(REGEX, "");
      String text = label.substring(0, 1).toUpperCase() + label.substring(1);
      wraps.add(".wrap()." + key + "()", text);
    }
    wraps.selectIndex(0);

    flexContainerOptions.add(directions, justifications, alignments, contentAlignments, wraps);

    self.add(codeWindow);
    codeWindow.setLanguage("java")
            .addClassName("code__block");

    createStrings();
    updateCode();
  }

  private void createStrings() {
    codeSnippetBuilder = new HashMap<>();
    codeSnippetBuilder.put("FlexDirection", ".horizontal() \n");
    codeSnippetBuilder.put("FlexJustifyContent", "");
    codeSnippetBuilder.put("FlexAlignment", "");
    codeSnippetBuilder.put("FlexContentAlignment", "");
    codeSnippetBuilder.put("FlexWrap", "");
  }

  private void updateCode() {
    javaCode = "FlexLayout boxLayout = FlexLayout.create() \n" +
            codeSnippetBuilder.get("FlexDirection") +
            codeSnippetBuilder.get("FlexJustifyContent") +
            codeSnippetBuilder.get("FlexAlignment") +
            codeSnippetBuilder.get("FlexContentAlignment") +
            codeSnippetBuilder.get("FlexWrap");
    codeWindow.setText(javaCode);
  }

  private void spinnerChange(ModifyEvent ev) {
    if (!spinner.isInvalid() && Integer.parseInt(ev.getText()) > 0) {
      if (Integer.parseInt(ev.getText()) > numBoxes) {
        addBox(Integer.parseInt(ev.getText()));
      } else if (Integer.parseInt(ev.getText()) < numBoxes) {
        removeBox(Integer.parseInt(ev.getText()));
      }
    }
  }

  private void addBox(int newNum) {
    while (newNum > numBoxes) {
      numBoxes++;
      String hue = String.valueOf(HUE * numBoxes); Box newBox = new Box(numBoxes);
      newBox.setStyle("background", "hsla(" + hue + ", 50%, 75%, 0.25)");
      newBox.setStyle("border", "2px solid " + "hsl(" + hue + ", 50%, 35%)");
      newBox.setStyle("color", "hsl(" + hue + ", 50%, 25%)");
      boxes.add(newBox);
      boxLayout.add(newBox);
    }
  }

  private void removeBox(int newNum) {
    while (newNum < numBoxes) {
      boxes.get(numBoxes - 1).destroyBox();
      boxes.remove(numBoxes - 1);
      numBoxes--;
    }
  }

  private void selectDirection(ListSelectEvent<?> ev) {
    FlexDirection direction = FlexDirection.fromValue(ev.getSelectedItem().getText());
    boxLayout.setDirection(direction);

    switch (direction) {
      case ROW_REVERSE -> codeSnippetBuilder.put("FlexDirection", ".horizontalReverse()\n");
      case COLUMN -> codeSnippetBuilder.put("FlexDirection", ".vertical()\n");
      case COLUMN_REVERSE -> codeSnippetBuilder.put("FlexDirection", ".verticalReverse()\n");
      case ROW -> codeSnippetBuilder.put("FlexDirection", ".horizontal()\n");
    }
    updateCode();
  }

  private void selectJustification(ListSelectEvent<?> ev) {
    boxLayout.setJustifyContent(FlexJustifyContent.fromValue(ev.getSelectedItem().getText()));
    if (ev.getSelectedItem().getKey().toString().equals(".justify().start()")) {
      codeSnippetBuilder.put("FlexJustifyContent", "");
    } else {
      codeSnippetBuilder.put("FlexJustifyContent", ev.getSelectedItem().getKey().toString() + "\n");
    }
    updateCode();
  }

  private void selectAlignment(ListSelectEvent<?> ev) {
    boxLayout.setAlignment(FlexAlignment.fromValue(ev.getSelectedItem().getText()));
    if (ev.getSelectedItem().getKey().toString().equals(".align().stretch()")) {
      codeSnippetBuilder.put("FlexAlignment", "");
    } else {
      codeSnippetBuilder.put("FlexAlignment", ev.getSelectedItem().getKey().toString() + "\n");
    }
    updateCode();
  }

  private void selectAlignContent(ListSelectEvent<?> ev) {
    boxLayout.setAlignContent(FlexContentAlignment.fromValue(ev.getSelectedItem().getText()));
    if (ev.getSelectedItem().getKey().toString().equals(".contentAlign().normal()")) {
      codeSnippetBuilder.put("FlexContentAlignment", "");
    } else {
      codeSnippetBuilder.put("FlexContentAlignment", ev.getSelectedItem().getKey().toString() + "\n");
    }
    updateCode();
  }

  private void selectWrap(ListSelectEvent<?> ev) {
    if (ev.getSelectedItem().getKey().toString().equals(".wrap().nowrap()")) {
      boxLayout.setWrap(FlexWrap.fromValue(ev.getSelectedItem().getText()));
      codeSnippetBuilder.put("FlexWrap", "");
    } else if (ev.getSelectedItem().getKey().toString().equals(".wrap().reverse()")) {
      boxLayout.setWrap(FlexWrap.WRAP_REVERSE);
      codeSnippetBuilder.put("FlexWrap", ev.getSelectedItem().getKey().toString() + "\n");
    } else {
      boxLayout.setWrap(FlexWrap.fromValue(ev.getSelectedItem().getText()));
      codeSnippetBuilder.put("FlexWrap", ev.getSelectedItem().getKey().toString() + "\n");
    }
    updateCode();
  }
}
