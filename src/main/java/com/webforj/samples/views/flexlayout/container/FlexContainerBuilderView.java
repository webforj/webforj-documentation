package com.webforj.samples.views.flexlayout.container;

import com.webforj.annotation.StyleSheet;
import com.webforj.component.Composite;
import com.webforj.component.event.ModifyEvent;
import com.webforj.component.field.MaskedNumberFieldSpinner;
import com.webforj.component.html.elements.Div;
import com.webforj.component.layout.flexlayout.FlexAlignment;
import com.webforj.component.layout.flexlayout.FlexContentAlignment;
import com.webforj.component.layout.flexlayout.FlexDirection;
import com.webforj.component.layout.flexlayout.FlexJustifyContent;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.component.layout.flexlayout.FlexWrap;
import com.webforj.component.list.ChoiceBox;
import com.webforj.component.list.event.ListSelectEvent;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;
import com.webforj.samples.components.Box;
import com.webforj.samples.components.CodeDisplay;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Route
@StyleSheet("ws://css/flexlayout/container/flexContainerBuilder.css")
@FrameTitle("Container Builder")
public class FlexContainerBuilderView extends Composite<Div> {
  private static final int HUE = 36; // 360 / 10
  private final Div self = getBoundComponent();

  private final MaskedNumberFieldSpinner spinner = new MaskedNumberFieldSpinner();
  private final FlexLayout boxLayout;
  private final List<Box> boxes;
  private final CodeDisplay codeWindow = new CodeDisplay();

  private int numBoxes;
  private String javaCode;
  private Map<String, String> codeSnippetBuilder;

  public static final String REGEX = "^(.+?)-";

  public FlexContainerBuilderView() {
    self.addClassName("app__frame");

    FlexLayout mainLayout = createMainLayout();
    this.boxLayout = createBoxLayout();
    this.boxes = new ArrayList<>();
    numBoxes = 0;

    FlexLayout flexContainerOptions = createFlexContainerOptions();
    spinnerChangeHandler();
    addBox(1);

    flexContainerOptions.add(spinner);
    mainLayout.add(flexContainerOptions, boxLayout);
    self.add(mainLayout);

    createAndAddChoiceBoxes(flexContainerOptions);

    self.add(codeWindow);
    codeWindow.setLanguage("java").addClassName("code__block");

    initializeCodeSnippets();
    updateCode();
  }

  private FlexLayout createMainLayout() {
    return FlexLayout.create().horizontal().build();
  }

  private FlexLayout createBoxLayout() {
    return FlexLayout.create().horizontal().build().addClassName("button__container");
  }

  private FlexLayout createFlexContainerOptions() {
    return FlexLayout.create().vertical().build().addClassName("flex__options");
  }

  private void spinnerChangeHandler() {
    spinner.setLabel("Number of Boxes").setMin(1d).setText("1").onModify(this::onSpinnerChange);
  }

  private void createAndAddChoiceBoxes(FlexLayout flexContainerOptions) {
    ChoiceBox directions = createDirectionsChoiceBox();
    ChoiceBox justifications = createJustificationsChoiceBox();
    ChoiceBox alignments = createAlignmentsChoiceBox();
    ChoiceBox contentAlignments = createContentAlignmentsChoiceBox();
    ChoiceBox wraps = createWrapsChoiceBox();

    flexContainerOptions.add(directions, justifications, alignments, contentAlignments, wraps);
  }

  private ChoiceBox createDirectionsChoiceBox() {
    ChoiceBox directions = new ChoiceBox().setLabel("Direction Options");
    directions.onSelect(this::onDirectionSelect);
    for (FlexDirection justify : FlexDirection.values()) {
      String label = justify.getValue();
      String key = justify.toString().toLowerCase();
      String text = label.substring(0, 1).toUpperCase() + label.substring(1);
      directions.add("." + key + "()", text);
    }
    directions.selectIndex(0);
    return directions;
  }

  private ChoiceBox createJustificationsChoiceBox() {
    ChoiceBox justifications = new ChoiceBox().setLabel("Justification Options");
    justifications.onSelect(this::onJustificationSelect);
    for (FlexJustifyContent justify : FlexJustifyContent.values()) {
      String label = justify.getValue().replaceAll(REGEX, "");
      String key = justify.toString().toLowerCase().replaceAll(REGEX, "");
      String text = label.substring(0, 1).toUpperCase() + label.substring(1);
      justifications.add(".justify()." + key + "()", text);
    }
    justifications.selectIndex(0);
    return justifications;
  }

  private ChoiceBox createAlignmentsChoiceBox() {
    ChoiceBox alignments = new ChoiceBox().setLabel("Alignment Options");
    alignments.onSelect(this::onAlignmentSelect);
    for (FlexAlignment justify : FlexAlignment.values()) {
      String label = justify.getValue().replaceAll(REGEX, "");
      String key = justify.toString().toLowerCase().replaceAll(REGEX, "");
      String text = label.substring(0, 1).toUpperCase() + label.substring(1);
      alignments.add(".align()." + key + "()", text);
    }
    alignments.selectIndex(0);
    return alignments;
  }

  private ChoiceBox createContentAlignmentsChoiceBox() {
    ChoiceBox contentAlignments = new ChoiceBox().setLabel("Content-Alignment Options");
    contentAlignments.onSelect(this::onAlignContentSelect);
    for (FlexContentAlignment justify : FlexContentAlignment.values()) {
      String label = justify.getValue().replaceAll(REGEX, "");
      String key = justify.toString().toLowerCase().replaceAll(REGEX, "");
      String text = label.substring(0, 1).toUpperCase() + label.substring(1);
      contentAlignments.add(".contentAlign()." + key + "()", text);
    }
    contentAlignments.selectIndex(0);
    return contentAlignments;
  }

  private ChoiceBox createWrapsChoiceBox() {
    ChoiceBox wraps = new ChoiceBox().setLabel("Wrap Options");
    wraps.onSelect(this::onWrapSelect);
    for (FlexWrap justify : FlexWrap.values()) {
      String label = justify.getValue().replaceAll(REGEX, "");
      String key = justify.toString().toLowerCase().replaceAll(REGEX, "");
      String text = label.substring(0, 1).toUpperCase() + label.substring(1);
      wraps.add(".wrap()." + key + "()", text);
    }
    wraps.selectIndex(0);
    return wraps;
  }

  private void initializeCodeSnippets() {
    codeSnippetBuilder =
        Map.of(
            "FlexDirection", ".horizontal() \n",
            "FlexJustifyContent", "",
            "FlexAlignment", "",
            "FlexContentAlignment", "",
            "FlexWrap", "");
  }

  private void updateCode() {
    javaCode =
        "FlexLayout boxLayout = FlexLayout.create() \n"
            + codeSnippetBuilder.get("FlexDirection")
            + codeSnippetBuilder.get("FlexJustifyContent")
            + codeSnippetBuilder.get("FlexAlignment")
            + codeSnippetBuilder.get("FlexContentAlignment")
            + codeSnippetBuilder.get("FlexWrap");
    codeWindow.setText(javaCode);
  }

  private void onSpinnerChange(ModifyEvent ev) {
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
      String hue = String.valueOf(HUE * numBoxes);
      Box newBox = new Box(numBoxes);
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

  private void onDirectionSelect(ListSelectEvent<?> ev) {
    FlexDirection direction = FlexDirection.fromValue(ev.getSelectedItem().getText());
    boxLayout.setDirection(direction);

    codeSnippetBuilder =
        switch (direction) {
          case ROW_REVERSE ->
              Map.ofEntries(
                  Map.entry("FlexDirection", ".horizontalReverse()\n"),
                  Map.entry("FlexJustifyContent", codeSnippetBuilder.get("FlexJustifyContent")),
                  Map.entry("FlexAlignment", codeSnippetBuilder.get("FlexAlignment")),
                  Map.entry("FlexContentAlignment", codeSnippetBuilder.get("FlexContentAlignment")),
                  Map.entry("FlexWrap", codeSnippetBuilder.get("FlexWrap")));
          case COLUMN ->
              Map.ofEntries(
                  Map.entry("FlexDirection", ".vertical()\n"),
                  Map.entry("FlexJustifyContent", codeSnippetBuilder.get("FlexJustifyContent")),
                  Map.entry("FlexAlignment", codeSnippetBuilder.get("FlexAlignment")),
                  Map.entry("FlexContentAlignment", codeSnippetBuilder.get("FlexContentAlignment")),
                  Map.entry("FlexWrap", codeSnippetBuilder.get("FlexWrap")));
          case COLUMN_REVERSE ->
              Map.ofEntries(
                  Map.entry("FlexDirection", ".verticalReverse()\n"),
                  Map.entry("FlexJustifyContent", codeSnippetBuilder.get("FlexJustifyContent")),
                  Map.entry("FlexAlignment", codeSnippetBuilder.get("FlexAlignment")),
                  Map.entry("FlexContentAlignment", codeSnippetBuilder.get("FlexContentAlignment")),
                  Map.entry("FlexWrap", codeSnippetBuilder.get("FlexWrap")));
          case ROW ->
              Map.ofEntries(
                  Map.entry("FlexDirection", ".horizontal()\n"),
                  Map.entry("FlexJustifyContent", codeSnippetBuilder.get("FlexJustifyContent")),
                  Map.entry("FlexAlignment", codeSnippetBuilder.get("FlexAlignment")),
                  Map.entry("FlexContentAlignment", codeSnippetBuilder.get("FlexContentAlignment")),
                  Map.entry("FlexWrap", codeSnippetBuilder.get("FlexWrap")));
        };
    updateCode();
  }

  private void onJustificationSelect(ListSelectEvent<?> ev) {
    boxLayout.setJustifyContent(FlexJustifyContent.fromValue(ev.getSelectedItem().getText()));
    if (ev.getSelectedItem().getKey().toString().equals(".justify().start()")) {
      codeSnippetBuilder =
          Map.ofEntries(
              Map.entry("FlexDirection", codeSnippetBuilder.get("FlexDirection")),
              Map.entry("FlexJustifyContent", ""),
              Map.entry("FlexAlignment", codeSnippetBuilder.get("FlexAlignment")),
              Map.entry("FlexContentAlignment", codeSnippetBuilder.get("FlexContentAlignment")),
              Map.entry("FlexWrap", codeSnippetBuilder.get("FlexWrap")));
    } else {
      codeSnippetBuilder =
          Map.ofEntries(
              Map.entry("FlexDirection", codeSnippetBuilder.get("FlexDirection")),
              Map.entry("FlexJustifyContent", ev.getSelectedItem().getKey().toString() + "\n"),
              Map.entry("FlexAlignment", codeSnippetBuilder.get("FlexAlignment")),
              Map.entry("FlexContentAlignment", codeSnippetBuilder.get("FlexContentAlignment")),
              Map.entry("FlexWrap", codeSnippetBuilder.get("FlexWrap")));
    }
    updateCode();
  }

  private void onAlignmentSelect(ListSelectEvent<?> ev) {
    boxLayout.setAlignment(FlexAlignment.fromValue(ev.getSelectedItem().getText()));
    if (ev.getSelectedItem().getKey().toString().equals(".align().stretch()")) {
      codeSnippetBuilder =
          Map.ofEntries(
              Map.entry("FlexDirection", codeSnippetBuilder.get("FlexDirection")),
              Map.entry("FlexJustifyContent", codeSnippetBuilder.get("FlexJustifyContent")),
              Map.entry("FlexAlignment", ""),
              Map.entry("FlexContentAlignment", codeSnippetBuilder.get("FlexContentAlignment")),
              Map.entry("FlexWrap", codeSnippetBuilder.get("FlexWrap")));
    } else {
      codeSnippetBuilder =
          Map.ofEntries(
              Map.entry("FlexDirection", codeSnippetBuilder.get("FlexDirection")),
              Map.entry("FlexJustifyContent", codeSnippetBuilder.get("FlexJustifyContent")),
              Map.entry("FlexAlignment", ev.getSelectedItem().getKey().toString() + "\n"),
              Map.entry("FlexContentAlignment", codeSnippetBuilder.get("FlexContentAlignment")),
              Map.entry("FlexWrap", codeSnippetBuilder.get("FlexWrap")));
    }
    updateCode();
  }

  private void onAlignContentSelect(ListSelectEvent<?> ev) {
    boxLayout.setAlignContent(FlexContentAlignment.fromValue(ev.getSelectedItem().getText()));
    if (ev.getSelectedItem().getKey().toString().equals(".contentAlign().normal()")) {
      codeSnippetBuilder =
          Map.ofEntries(
              Map.entry("FlexDirection", codeSnippetBuilder.get("FlexDirection")),
              Map.entry("FlexJustifyContent", codeSnippetBuilder.get("FlexJustifyContent")),
              Map.entry("FlexAlignment", codeSnippetBuilder.get("FlexAlignment")),
              Map.entry("FlexContentAlignment", ""),
              Map.entry("FlexWrap", codeSnippetBuilder.get("FlexWrap")));
    } else {
      codeSnippetBuilder =
          Map.ofEntries(
              Map.entry("FlexDirection", codeSnippetBuilder.get("FlexDirection")),
              Map.entry("FlexJustifyContent", codeSnippetBuilder.get("FlexJustifyContent")),
              Map.entry("FlexAlignment", codeSnippetBuilder.get("FlexAlignment")),
              Map.entry("FlexContentAlignment", ev.getSelectedItem().getKey().toString() + "\n"),
              Map.entry("FlexWrap", codeSnippetBuilder.get("FlexWrap")));
    }
    updateCode();
  }

  private void onWrapSelect(ListSelectEvent<?> ev) {
    String key = ev.getSelectedItem().getKey().toString();
    if (key.equals(".wrap().nowrap()")) {
      boxLayout.setWrap(FlexWrap.fromValue(ev.getSelectedItem().getText()));
      codeSnippetBuilder =
          Map.ofEntries(
              Map.entry("FlexDirection", codeSnippetBuilder.get("FlexDirection")),
              Map.entry("FlexJustifyContent", codeSnippetBuilder.get("FlexJustifyContent")),
              Map.entry("FlexAlignment", codeSnippetBuilder.get("FlexAlignment")),
              Map.entry("FlexContentAlignment", codeSnippetBuilder.get("FlexContentAlignment")),
              Map.entry("FlexWrap", ""));
    } else if (key.equals(".wrap().reverse()")) {
      boxLayout.setWrap(FlexWrap.WRAP_REVERSE);
      codeSnippetBuilder =
          Map.ofEntries(
              Map.entry("FlexDirection", codeSnippetBuilder.get("FlexDirection")),
              Map.entry("FlexJustifyContent", codeSnippetBuilder.get("FlexJustifyContent")),
              Map.entry("FlexAlignment", codeSnippetBuilder.get("FlexAlignment")),
              Map.entry("FlexContentAlignment", codeSnippetBuilder.get("FlexContentAlignment")),
              Map.entry("FlexWrap", key + "\n"));
    } else {
      boxLayout.setWrap(FlexWrap.fromValue(ev.getSelectedItem().getText()));
      codeSnippetBuilder =
          Map.ofEntries(
              Map.entry("FlexDirection", codeSnippetBuilder.get("FlexDirection")),
              Map.entry("FlexJustifyContent", codeSnippetBuilder.get("FlexJustifyContent")),
              Map.entry("FlexAlignment", codeSnippetBuilder.get("FlexAlignment")),
              Map.entry("FlexContentAlignment", codeSnippetBuilder.get("FlexContentAlignment")),
              Map.entry("FlexWrap", key + "\n"));
    }
    updateCode();
  }
}
