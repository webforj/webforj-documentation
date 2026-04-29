package com.webforj.samples.views.spinner;

import com.webforj.component.Composite;
import com.webforj.component.Theme;
import com.webforj.component.html.elements.H3;
import com.webforj.component.html.elements.Paragraph;
import com.webforj.component.icons.Icon;
import com.webforj.component.icons.TablerIcon;
import com.webforj.component.layout.columnslayout.ColumnsLayout;
import com.webforj.component.layout.columnslayout.ColumnsLayout.Alignment;
import com.webforj.component.layout.columnslayout.ColumnsLayout.Breakpoint;
import com.webforj.component.spinner.Spinner;
import com.webforj.component.spinner.SpinnerExpanse;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;
import java.util.List;

@Route
@FrameTitle("Spinner Basics")
public class SpinnerDemoView extends Composite<ColumnsLayout> {
  private final ColumnsLayout self = getBoundComponent();
  // UI Components
  private final Spinner spinner = new Spinner(Theme.PRIMARY, SpinnerExpanse.XXXSMALL);
  private final H3 title = new H3("Complete your job application:");
  private final Icon position = TablerIcon.create("checks");
  private final Paragraph positionText = new Paragraph("Select the position you wish to apply for");
  private final Icon location = TablerIcon.create("checks");
  private final Paragraph locationText = new Paragraph("Provide your current location details");
  private final Paragraph resume = new Paragraph("Uploading your resume");

  public SpinnerDemoView() {
    self.setStyle("margin-left", "20px");

    position.setTheme(Theme.SUCCESS);
    location.setTheme(Theme.SUCCESS);

    self.setBreakpoints(List.of(new Breakpoint(0, 10)))
        .setVerticalAlignment(Alignment.CENTER)
        .setWidth("fit-content")
        .add(title, position, positionText, location, locationText, spinner, resume);

    self.setSpan(title, 10).setSpan(positionText, 9).setSpan(locationText, 9).setSpan(resume, 9);
  }
}
