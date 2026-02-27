package com.webforj.samples.views.loading;

import com.webforj.annotation.StyleSheet;
import com.webforj.component.Composite;
import com.webforj.component.Theme;
import com.webforj.component.button.Button;
import com.webforj.component.button.ButtonTheme;
import com.webforj.component.html.elements.Div;
import com.webforj.component.html.elements.Paragraph;
import com.webforj.component.icons.FeatherIcon;
import com.webforj.component.icons.Icon;
import com.webforj.component.layout.flexlayout.FlexDirection;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.component.loading.Loading;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;

/**
 * Demo to show Loading basics.
 */
@Route
@FrameTitle("Loading Basics")
@StyleSheet("ws://css/loadingstyles/loadingdemo.css")
public class LoadingDemoView extends Composite<FlexLayout> {
  private final FlexLayout self = getBoundComponent();
  // UI Components
  private final Div card1;
  private final Div card2;
  private final Icon guideIcon;
  private final Icon videoIcon;
  private final Button buyButton1;
  private final Button buyButton2;
  private final Loading loading;

  public LoadingDemoView() {
    // Configure layout with fluent API
    self.setDirection(FlexDirection.ROW)
            .setMargin("var(--dwc-space-l)");

    // Create first card with guide icon
    card1 = new Div()
            .addClassName("card");

    guideIcon = FeatherIcon.BOOK.create()
            .addClassName("icon");

    buyButton1 = new Button("Buy")
            .setTheme(ButtonTheme.PRIMARY);

    // Create second card with video icon
    card2 = new Div()
            .addClassName("card");

    videoIcon = FeatherIcon.YOUTUBE.create()
            .addClassName("icon");

    buyButton2 = new Button("Buy")
            .setTheme(ButtonTheme.PRIMARY);

    // Create loading indicator with primary theme
    loading = new Loading("Loading... Please wait.")
            .addClassName("loading-overlay");
    loading.getSpinner().setTheme(Theme.PRIMARY);

    // Add content to cards
    card1.add(new Paragraph("User Guide"), guideIcon, buyButton1);
    card2.add(new Paragraph("Video Lessons"), videoIcon, buyButton2, loading);

    // Open loading overlay and add cards to layout
    loading.open();
    self.add(card1, card2);
  }
}

