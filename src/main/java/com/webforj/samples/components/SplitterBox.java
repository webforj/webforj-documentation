package com.webforj.samples.components;

import com.webforj.bundle.annotation.BundleEntry;
import com.webforj.component.Composite;
import com.webforj.component.layout.flexlayout.FlexAlignment;
import com.webforj.component.layout.flexlayout.FlexJustifyContent;
import com.webforj.component.layout.flexlayout.FlexLayout;

@BundleEntry("css/splitter/splitter-box.css")
public class SplitterBox extends Composite<FlexLayout> {
  public enum Theme {
    INFO,
    SUCCESS,
    WARNING,
    PRIMARY
  }

  public SplitterBox(String label, Theme type) {
    super();
    FlexLayout layout = getBoundComponent();
    layout.setText(label);
    layout.setJustifyContent(FlexJustifyContent.CENTER);
    layout.setAlignment(FlexAlignment.CENTER);
    layout.addClassName("splitter-box", "splitter-box--" + type.name().toLowerCase());
  }
}
