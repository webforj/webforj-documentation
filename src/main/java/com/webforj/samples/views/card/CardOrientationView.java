package com.webforj.samples.views.card;

import com.webforj.bundle.annotation.BundleEntry;
import com.webforj.component.Composite;
import com.webforj.component.button.Button;
import com.webforj.component.button.ButtonTheme;
import com.webforj.component.card.Card;
import com.webforj.component.html.elements.H3;
import com.webforj.component.html.elements.Paragraph;
import com.webforj.component.html.elements.Span;
import com.webforj.component.icons.Icon;
import com.webforj.component.icons.TablerIcon;
import com.webforj.component.layout.flexlayout.FlexAlignment;
import com.webforj.component.layout.flexlayout.FlexDirection;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.component.optioninput.RadioButton;
import com.webforj.component.optioninput.RadioButtonGroup;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;

@Route
@FrameTitle("Course Catalog")
@BundleEntry("css/card/cardOrientation.css")
public class CardOrientationView extends Composite<FlexLayout> {
  private final FlexLayout self = getBoundComponent();
  private final Card course = buildCourse();

  public CardOrientationView() {
    self.setDirection(FlexDirection.COLUMN)
        .setAlignment(FlexAlignment.CENTER)
        .setSpacing("var(--dwc-space-l)")
        .addClassName("card-demo");

    RadioButton vertical = new RadioButton("Vertical", true);
    vertical.setStyle("align-self", "flex-start");
    RadioButton horizontal = new RadioButton("Horizontal");
    horizontal.setStyle("align-self", "flex-start");

    RadioButtonGroup orientation = new RadioButtonGroup("orientation", vertical, horizontal);

    orientation.onChange(
        event -> {
          if (horizontal.isChecked()) {
            course.setOrientation(Card.Orientation.HORIZONTAL).setMaxWidth("420px");
          } else {
            course.setOrientation(Card.Orientation.VERTICAL).setMaxWidth("280px");
          }
        });

    course.setOrientation(Card.Orientation.VERTICAL).setMaxWidth("280px");

    self.add(orientation, course);
  }

  private Card buildCourse() {
    Icon cover = TablerIcon.create("book-2");
    cover.addClassName("card-orientation__icon");

    Span meta = new Span("12 lessons").addClassName("card-orientation__meta");

    Card card =
        new Card(
            FlexLayout.create(
                    meta,
                    new Paragraph(
                        "Work through guided exercises and finish with a graded project."))
                .vertical()
                .build()
                .setSpacing("var(--dwc-space-xs)"));

    return card.addToFigure(cover)
        .addToTitle(new H3("Data Structures"))
        .addToFooter(new Button("Enroll", ButtonTheme.OUTLINED_PRIMARY));
  }
}
