package com.webforj.samples.unit.views.flexlayout.container;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.webforj.automation.act.UserInteraction;
import com.webforj.automation.find.ComponentLocator;
import com.webforj.component.field.MaskedNumberFieldSpinner;
import com.webforj.component.layout.flexlayout.FlexAlignment;
import com.webforj.component.layout.flexlayout.FlexContentAlignment;
import com.webforj.component.layout.flexlayout.FlexDirection;
import com.webforj.component.layout.flexlayout.FlexJustifyContent;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.component.layout.flexlayout.FlexWrap;
import com.webforj.component.list.ChoiceBox;
import com.webforj.samples.components.Box;
import com.webforj.samples.components.CodeDisplay;
import com.webforj.samples.views.flexlayout.container.FlexContainerBuilderView;
import com.webforj.testbed.WebforjTestbed;
import com.webforj.testbed.annotation.WebforjTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

@WebforjTest
class FlexContainerBuilderViewTest {
  @Test
  void shouldShowAsManyBoxesAsTheSpinnerAsksFor(WebforjTestbed context) {
    context.add(new FlexContainerBuilderView());

    var spinner = ComponentLocator.of(MaskedNumberFieldSpinner.class).locate();
    var boxes = ComponentLocator.of(FlexLayout.class).byClassName("button__container").locate();

    UserInteraction.type(spinner, "4");
    assertEquals(4, ComponentLocator.of(Box.class).within(boxes).count());

    UserInteraction.type(spinner, "2");
    assertEquals(2, ComponentLocator.of(Box.class).within(boxes).count());
  }

  @Test
  void shouldApplyThePickedOptionsToTheBoxesAndWriteTheirBuilderCode(WebforjTestbed context) {
    context.add(new FlexContainerBuilderView());

    var boxes = ComponentLocator.of(FlexLayout.class).byClassName("button__container").locate();

    var directions = ComponentLocator.of(ChoiceBox.class).byLabel("Direction Options").locate();
    UserInteraction.select(directions, directions.find("Column").orElseThrow());

    var justifications =
        ComponentLocator.of(ChoiceBox.class).byLabel("Justification Options").locate();
    UserInteraction.select(justifications, justifications.find("Center").orElseThrow());

    var alignments = ComponentLocator.of(ChoiceBox.class).byLabel("Alignment Options").locate();
    UserInteraction.select(alignments, alignments.find("End").orElseThrow());

    var contentAlignments =
        ComponentLocator.of(ChoiceBox.class).byLabel("Content-Alignment Options").locate();
    UserInteraction.select(contentAlignments, contentAlignments.find("Between").orElseThrow());

    assertEquals(FlexDirection.COLUMN, boxes.getDirection());
    assertEquals(FlexJustifyContent.CENTER, boxes.getJustifyContent());
    assertEquals(FlexAlignment.END, boxes.getAlignment());
    assertEquals(FlexContentAlignment.BETWEEN, boxes.getAlignContent());
    assertEquals(
        """
        FlexLayout boxLayout = FlexLayout.create()\s
        .vertical()
        .justify().center()
        .align().end()
        .contentAlign().between()
        """,
        ComponentLocator.of(CodeDisplay.class).locate().getText());
  }

  @ParameterizedTest
  @CsvSource({"Wrap, WRAP, .wrap().wrap()", "Reverse, WRAP_REVERSE, .wrap().reverse()"})
  void shouldWrapTheBoxesAndWriteTheWrapCodeForThePickedOption(
      String option, FlexWrap wrap, String code, WebforjTestbed context) {
    context.add(new FlexContainerBuilderView());

    var boxes = ComponentLocator.of(FlexLayout.class).byClassName("button__container").locate();

    var wraps = ComponentLocator.of(ChoiceBox.class).byLabel("Wrap Options").locate();
    UserInteraction.select(wraps, wraps.find(option).orElseThrow());

    assertEquals(wrap, boxes.getWrap());
    assertEquals(
        """
        FlexLayout boxLayout = FlexLayout.create()\s
        .horizontal()\s
        %s
        """
            .formatted(code),
        ComponentLocator.of(CodeDisplay.class).locate().getText());
  }

  @Test
  void shouldDropTheOptionCodeWhenTheDefaultsArePickedAgain(WebforjTestbed context) {
    context.add(new FlexContainerBuilderView());

    var boxes = ComponentLocator.of(FlexLayout.class).byClassName("button__container").locate();
    var directions = ComponentLocator.of(ChoiceBox.class).byLabel("Direction Options").locate();
    var justifications =
        ComponentLocator.of(ChoiceBox.class).byLabel("Justification Options").locate();
    var alignments = ComponentLocator.of(ChoiceBox.class).byLabel("Alignment Options").locate();
    var contentAlignments =
        ComponentLocator.of(ChoiceBox.class).byLabel("Content-Alignment Options").locate();
    var wraps = ComponentLocator.of(ChoiceBox.class).byLabel("Wrap Options").locate();

    UserInteraction.select(directions, directions.find("Column").orElseThrow());
    UserInteraction.select(justifications, justifications.find("Center").orElseThrow());
    UserInteraction.select(alignments, alignments.find("End").orElseThrow());
    UserInteraction.select(contentAlignments, contentAlignments.find("Between").orElseThrow());
    UserInteraction.select(wraps, wraps.find("Wrap").orElseThrow());

    UserInteraction.select(directions, directions.find("Row").orElseThrow());
    UserInteraction.select(justifications, justifications.find("Start").orElseThrow());
    UserInteraction.select(alignments, alignments.find("Stretch").orElseThrow());
    UserInteraction.select(contentAlignments, contentAlignments.find("Normal").orElseThrow());
    UserInteraction.select(wraps, wraps.find("Nowrap").orElseThrow());

    assertEquals(FlexDirection.ROW, boxes.getDirection());
    assertEquals(FlexJustifyContent.START, boxes.getJustifyContent());
    assertEquals(FlexAlignment.STRETCH, boxes.getAlignment());
    assertEquals(FlexContentAlignment.NORMAL, boxes.getAlignContent());
    assertEquals(FlexWrap.NOWRAP, boxes.getWrap());
    assertEquals(
        """
        FlexLayout boxLayout = FlexLayout.create()\s
        .horizontal()
        """,
        ComponentLocator.of(CodeDisplay.class).locate().getText());
  }
}
