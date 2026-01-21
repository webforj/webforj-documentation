package com.webforj.samples.views.viewtransitions;

import com.webforj.samples.views.viewtransitions.components.DemoHeader;
import com.webforj.samples.views.viewtransitions.components.ShuffleCard;
import com.webforj.Page;
import com.webforj.annotation.StyleSheet;
import com.webforj.component.Composite;
import com.webforj.component.icons.FeatherIcon;
import com.webforj.component.icons.IconButton;
import com.webforj.component.layout.flexlayout.FlexAlignment;
import com.webforj.component.layout.flexlayout.FlexDirection;
import com.webforj.component.layout.flexlayout.FlexJustifyContent;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Route
@FrameTitle("List Shuffle")
@StyleSheet("ws://css/viewtransitions/shuffle.css")
public class ViewTransitionShuffleView extends Composite<FlexLayout> {
  private final FlexLayout self = getBoundComponent();
  private final FlexLayout listContainer;
  private final List<CardData> items;

  record CardData(String id, String title, String subtitle, String color, FeatherIcon icon) {
  }

  public ViewTransitionShuffleView() {
    self.setDirection(FlexDirection.COLUMN)
        .setAlignment(FlexAlignment.CENTER)
        .setJustifyContent(FlexJustifyContent.CENTER)
        .setSpacing("var(--dwc-space-l)")
        .setHeight("100vh");

    items = new ArrayList<>(List.of(
        new CardData("1", "Messages", "3 unread", "blue", FeatherIcon.MAIL),
        new CardData("2", "Tasks", "5 pending", "green", FeatherIcon.CHECK_SQUARE),
        new CardData("3", "Calendar", "2 events today", "purple", FeatherIcon.CALENDAR),
        new CardData("4", "Settings", "Last updated", "orange", FeatherIcon.SETTINGS)));

    IconButton shuffleBtn = new IconButton(FeatherIcon.SHUFFLE.create());
    shuffleBtn.addClassName("shuffle-btn");
    shuffleBtn.onClick(e -> shuffle());

    DemoHeader header = new DemoHeader(
        "List Reorder",
        "Click shuffle to reorder items. Each item animates to its new position.",
        "--dwc-color-primary"
    );
    header.addAction(shuffleBtn);

    listContainer = FlexLayout.create().vertical().build();
    listContainer.setSpacing("var(--dwc-space-m)")
        .addClassName("shuffle-list");

    renderList();
    self.add(header, listContainer);
  }

  private void renderList() {
    listContainer.removeAll();
    for (int i = 0; i < items.size(); i++) {
      CardData item = items.get(i);
      ShuffleCard card = new ShuffleCard(
          item.id(), item.title(), item.subtitle(), item.color(), item.icon(), i + 1
      );
      listContainer.add(card);
    }
  }

  private void shuffle() {
    Collections.shuffle(items);

    Page.getCurrent().startViewTransition()
        .onUpdate(done -> {
          renderList();
          done.run();
        })
        .start();
  }
}
