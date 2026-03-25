package com.webforj.samples.views.spinner;

import com.webforj.annotation.StyleSheet;
import com.webforj.component.Composite;
import com.webforj.component.Theme;
import com.webforj.component.html.elements.Div;
import com.webforj.component.html.elements.H3;
import com.webforj.component.html.elements.Paragraph;
import com.webforj.component.icons.Icon;
import com.webforj.component.icons.TablerIcon;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.component.spinner.Spinner;
import com.webforj.component.spinner.SpinnerExpanse;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;

@Route
@FrameTitle("Spinner Basics")
@StyleSheet("ws://css/spinnerstyles/spinnerdemo.css")
public class SpinnerDemoView extends Composite<Div> {
  private final Div self = getBoundComponent();
  // UI Components
  private final Spinner spinner = new Spinner(Theme.PRIMARY, SpinnerExpanse.XXXSMALL);
  private final H3 title = new H3("Complete your job application:");
  private final Icon position = TablerIcon.create("checks");
  private final Icon location = TablerIcon.create("checks");
  private final Paragraph resume = new Paragraph("Uploading your resume");

  public SpinnerDemoView() {
    self.setStyle("margin-left", "20px");

    FlexLayout positionLayout = new FlexLayout(position, new Paragraph("Select the position you wish to apply for"));

    FlexLayout locationLayout = new FlexLayout(location, new Paragraph("Provide your current location details"));

    FlexLayout spinnerLayout = new FlexLayout(spinner, resume);

    FlexLayout items = FlexLayout.create(title, positionLayout, locationLayout, spinnerLayout)
        .vertical()
        .justify().center()
        .build();

    self.add(items);
  }
}
