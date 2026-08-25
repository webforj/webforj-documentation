package com.webforj.samples.views.card;

import com.webforj.component.Composite;
import com.webforj.component.Expanse;
import com.webforj.component.button.Button;
import com.webforj.component.button.ButtonTheme;
import com.webforj.component.card.Card;
import com.webforj.component.html.elements.H3;
import com.webforj.component.html.elements.Img;
import com.webforj.component.html.elements.Span;
import com.webforj.component.icons.IconButton;
import com.webforj.component.icons.TablerIcon;
import com.webforj.component.layout.flexlayout.FlexAlignment;
import com.webforj.component.layout.flexlayout.FlexDirection;
import com.webforj.component.layout.flexlayout.FlexJustifyContent;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.component.slider.Slider;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;

@Route
@FrameTitle("Now Playing")
public class CardOrientationView extends Composite<FlexLayout> {
  private static final String ALBUM_ART = "https://picsum.photos/seed/dwc-card-player/720/720";

  private final FlexLayout self = getBoundComponent();

  private final Card card = new Card();
  private final Slider scrubber = new Slider(38);

  public CardOrientationView() {
    self.setDirection(FlexDirection.COLUMN)
        .setAlignment(FlexAlignment.CENTER)
        .setHeight("100vh")
        .setJustifyContent(FlexJustifyContent.CENTER)
        .setPadding("var(--dwc-space-l)");

    card.setOrientation(Card.Orientation.HORIZONTAL);
    card.setShadow(Card.Shadow.MEDIUM);
    card.setWidth("100%");
    card.setMaxWidth("34rem");

    card.addToFigure(new Img(ALBUM_ART, "Album art for Ghost Harbour by Signal Path"));
    card.addToTitle(new H3("Ghost Harbour"));
    card.addToCaption(new Span("Signal Path"));

    scrubber.setFilled(true);
    scrubber.setTooltipVisible(false);
    scrubber.setAttribute("aria-label", "Seek");
    scrubber.setStyle("--dwc-slider-horizontal-height", "var(--dwc-space-3xl)");

    card.addToBody(scrubber, buildTimes());
    card.addToFooter(
        buildTransportButton("player-track-prev", "Previous Track"),
        buildPlayButton(),
        buildTransportButton("player-track-next", "Next track"));

    self.add(card);
  }

  private FlexLayout buildTimes() {
    FlexLayout times = FlexLayout.create(new Span("1:12"), new Span("-2:36")).horizontal().build();
    times.setJustifyContent(FlexJustifyContent.BETWEEN);
    times.setStyle("padding-inline", "var(--dwc-space-m)");
    times.setStyle("font-size", "var(--dwc-font-size-s)");
    times.setStyle("color", "var(--dwc-color-gray-text-light)");

    return times;
  }

  private Button buildPlayButton() {
    Button play = new Button(TablerIcon.create("player-play", TablerIcon.Variate.FILLED));
    play.setTheme(ButtonTheme.PRIMARY);
    play.setExpanse(Expanse.MEDIUM);
    play.setAttribute("aria-label", "Play");
    play.setStyle("--dwc-icon-size", "var(--dwc-font-size-l)");
    play.setMinWidth("3.5rem");

    return play;
  }

  private IconButton buildTransportButton(String iconName, String label) {
    IconButton button = new IconButton(TablerIcon.create(iconName, TablerIcon.Variate.FILLED));
    button.setLabel(label);

    return button;
  }
}
