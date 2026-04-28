package com.webforj.samples.components;

import com.webforj.annotation.StyleSheet;
import com.webforj.component.Composite;
import com.webforj.component.layout.flexlayout.FlexAlignment;
import com.webforj.component.layout.flexlayout.FlexJustifyContent;
import com.webforj.component.layout.flexlayout.FlexLayout;

@StyleSheet("ws://css/splitter/splitter-box.css")
public class SplitterBox extends Composite<FlexLayout> {
  public enum Theme {
    INFO, SUCCESS, WARNING, PRIMARY
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
