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

  Div card1;
  Div card2;
  Icon guideIcon;
  Icon videoIcon;
  Button buyButton1;
  Button buyButton2;
  Loading loading;

  public LoadingDemoView() {
    getBoundComponent().setDirection(FlexDirection.ROW)
        .setMargin("var(--dwc-space-l)");

    card1 = new Div()
        .addClassName("card");

    guideIcon = FeatherIcon.BOOK.create()
        .addClassName("icon");

    buyButton1 = new Button("Buy")
        .setTheme(ButtonTheme.PRIMARY);

    card2 = new Div()
        .addClassName("card");

    videoIcon = FeatherIcon.YOUTUBE.create()
        .addClassName("icon");

    buyButton2 = new Button("Buy")
        .setTheme(ButtonTheme.PRIMARY);

    loading = new Loading("Loading... Please wait.");
    loading.getSpinner().setTheme(Theme.PRIMARY);
    loading.addClassName("loading-overlay");

    card1.add(new Paragraph("User Guide"), guideIcon, buyButton1);
    card2.add(new Paragraph("Video Lessons"), videoIcon, buyButton2, loading);

    loading.open();

    getBoundComponent().add(card1, card2);
  }
}


