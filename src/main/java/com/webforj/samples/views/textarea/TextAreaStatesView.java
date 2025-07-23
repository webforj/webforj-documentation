package com.webforj.samples.views.textarea;

import com.webforj.component.Composite;
import com.webforj.component.field.TextArea;
import com.webforj.component.layout.flexlayout.FlexAlignment;
import com.webforj.component.layout.flexlayout.FlexJustifyContent;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;

@Route
@FrameTitle("TextArea States")
public class TextAreaStatesView extends Composite<FlexLayout> {

  FlexLayout self = getBoundComponent();
  TextArea readonlyArea = new TextArea("Read-Only", "Value");
  TextArea disabledArea = new TextArea("Disabled", "Value");

  public TextAreaStatesView() {
    self.setJustifyContent(FlexJustifyContent.CENTER)
        .setAlignment(FlexAlignment.CENTER)
        .setSpacing("var(--dwc-space-m)")
        .setStyle("padding", "var(--dwc-space-m)")
        .setMargin("50px auto")
        .setMaxWidth("600px");

    readonlyArea.setReadOnly(true);
    disabledArea.setEnabled(false);

    self.add(readonlyArea, disabledArea);
  }
}
