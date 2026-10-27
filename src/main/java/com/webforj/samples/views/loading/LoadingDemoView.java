package com.webforj.samples.views.loading;

import com.webforj.bundle.annotation.BundleEntry;
import com.webforj.component.Composite;
import com.webforj.component.Theme;
import com.webforj.component.button.Button;
import com.webforj.component.button.ButtonTheme;
import com.webforj.component.card.Card;
import com.webforj.component.html.elements.Paragraph;
import com.webforj.component.icons.FeatherIcon;
import com.webforj.component.icons.Icon;
import com.webforj.component.layout.flexlayout.FlexAlignment;
import com.webforj.component.layout.flexlayout.FlexJustifyContent;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.component.loading.Loading;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;

/** Demo to show Loading basics. */
@Route
@FrameTitle("Loading Basics")
@BundleEntry("css/loadingstyles/loadingdemo.css")
public class LoadingDemoView extends Composite<FlexLayout> {
  private final FlexLayout self = getBoundComponent();
  // UI Components
  private final Card guideCard;
  private final Card videoCard;
  private final Icon guideIcon;
  private final Icon videoIcon;
  private final Loading loading;

  public LoadingDemoView() {
    self.setHeight("100vh")
        .setAlignment(FlexAlignment.CENTER)
        .setSpacing("var(--dwc-space-xl)")
        .setJustifyContent(FlexJustifyContent.CENTER);

    guideIcon = FeatherIcon.BOOK.create();
    guideCard = createCard("User Guide", guideIcon);

    videoIcon = FeatherIcon.YOUTUBE.create();
    videoCard = createCard("Video Lessons", videoIcon);

    loading = new Loading("Loading... Please wait.").addClassName("loading-overlay");
    loading.getSpinner().setTheme(Theme.PRIMARY);

    videoCard.add(loading);

    loading.open();
    self.add(guideCard, videoCard);
  }

  private Card createCard(String title, Icon icon) {
    Card card = new Card();
    Button buyButton = new Button("Buy").setTheme(ButtonTheme.PRIMARY);
    Icon cardIcon = icon.setSize(100, 100);

    card.setSize(300, 220);
    card.addToTitle(new Paragraph(title));
    card.addClassName("card");
    card.addToTitle(cardIcon);
    card.addToFooter(buyButton);

    return card;
  }
}
