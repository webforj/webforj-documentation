package com.webforj.samples.components;

import com.webforj.annotation.StyleSheet;
import com.webforj.component.Composite;
import com.webforj.component.layout.flexlayout.FlexAlignment;
import com.webforj.component.layout.flexlayout.FlexJustifyContent;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;

// TODO refactor css.
@StyleSheet(/*css*/"""
    .splitter-box {
      width: 100%;
      height: 100%;
      font-weight: bold;
    }

    .splitter-box--primary {
      background: var(--dwc-color-primary-alt);
      color: var(--dwc-color-primary);
    }

    .splitter-box--info {
      background: var(--dwc-color-info-alt);
      color: var(--dwc-color-info);
    }

    .splitter-box--success {
      background: var(--dwc-color-success-alt);
      color: var(--dwc-color-success);
    }

    .splitter-box--warning {
      background: var(--dwc-color-warning-alt);
      color: var(--dwc-color-warning);
    }
    """)
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
