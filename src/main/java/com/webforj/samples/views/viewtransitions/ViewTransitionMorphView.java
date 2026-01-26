package com.webforj.samples.views.viewtransitions;

import com.webforj.samples.views.viewtransitions.components.BlogCard;
import com.webforj.samples.views.viewtransitions.components.BlogDetail;
import com.webforj.samples.views.viewtransitions.components.DemoHeader;
import com.webforj.Page;
import com.webforj.ViewTransition;
import com.webforj.annotation.StyleSheet;
import com.webforj.component.Composite;
import com.webforj.component.html.elements.Div;
import com.webforj.component.layout.flexlayout.FlexAlignment;
import com.webforj.component.layout.flexlayout.FlexDirection;
import com.webforj.component.layout.flexlayout.FlexJustifyContent;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;

@Route
@FrameTitle("Shared Element Morph")
@StyleSheet("ws://css/viewtransitions/morph.css")
public class ViewTransitionMorphView extends Composite<FlexLayout> {
  private static final String TRANSITION_NAME = "blog-image";
  private static final String TITLE = "The Art of Writing";
  private static final String EXCERPT = "Discover techniques that transform words into compelling stories.";
  private static final String FULL_TEXT = "Writing is about connecting with readers and sharing ideas that resonate. Every sentence matters, every word carries weight. Great writers understand that clarity comes from revision, and the best stories emerge when we strip away the unnecessary. Whether crafting fiction or technical documentation, the goal remains the same: communicate with precision and purpose.";

  private FlexLayout self = getBoundComponent();
  private Div stage;
  private boolean isExpanded = false;

  public ViewTransitionMorphView() {
    self.setDirection(FlexDirection.COLUMN)
        .setAlignment(FlexAlignment.CENTER)
        .setJustifyContent(FlexJustifyContent.CENTER)
        .setSpacing("var(--dwc-space-l)")
        .setHeight("100vh");

    DemoHeader header = new DemoHeader(
        "Shared Element Morph",
        "Click the card to expand. The image and title morph between states.",
        "--dwc-color-info"
    );

    stage = new Div();
    stage.addClassName("morph-stage");

    BlogCard card = new BlogCard(TITLE, EXCERPT, TRANSITION_NAME);
    card.onClick(e -> expand());
    stage.add(card);

    self.add(header, stage);
  }

  private void expand() {
    if (isExpanded) return;

    BlogDetail detail = new BlogDetail(TITLE, FULL_TEXT, TRANSITION_NAME);
    detail.onClose(e -> collapse());

    Page.getCurrent().startViewTransition()
        .enter(detail, ViewTransition.ZOOM)
        .onUpdate(done -> {
          stage.removeAll();
          stage.add(detail);
          isExpanded = true;
          done.run();
        })
        .start();
  }

  private void collapse() {
    if (!isExpanded) return;

    BlogCard card = new BlogCard(TITLE, EXCERPT, TRANSITION_NAME);
    card.onClick(e -> expand());

    Page.getCurrent().startViewTransition()
        .enter(card, ViewTransition.ZOOM)
        .onUpdate(done -> {
          stage.removeAll();
          stage.add(card);
          isExpanded = false;
          done.run();
        })
        .start();
  }
}
