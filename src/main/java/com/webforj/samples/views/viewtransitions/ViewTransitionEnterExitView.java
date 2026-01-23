package com.webforj.samples.views.viewtransitions;

import com.webforj.samples.views.viewtransitions.components.DemoHeader;
import com.webforj.samples.views.viewtransitions.components.NotificationCard;
import com.webforj.Page;
import com.webforj.annotation.StyleSheet;
import com.webforj.component.Composite;
import com.webforj.component.html.elements.Div;
import com.webforj.component.icons.FeatherIcon;
import com.webforj.component.icons.IconButton;
import com.webforj.component.layout.flexlayout.FlexAlignment;
import com.webforj.component.layout.flexlayout.FlexDirection;
import com.webforj.component.layout.flexlayout.FlexJustifyContent;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;

@Route
@FrameTitle("Custom Transitions")
@StyleSheet("ws://css/viewtransitions/enterexit.css")
public class ViewTransitionEnterExitView extends Composite<FlexLayout> {
  private final FlexLayout self = getBoundComponent();
  private final Div stage;
  private NotificationCard currentNotification;
  private boolean isVisible = false;

  public ViewTransitionEnterExitView() {
    self.setDirection(FlexDirection.COLUMN)
        .setAlignment(FlexAlignment.CENTER)
        .setJustifyContent(FlexJustifyContent.CENTER)
        .setSpacing("var(--dwc-space-l)")
        .setHeight("100vh");

    DemoHeader header = new DemoHeader(
        "Custom Transitions",
        "Custom CSS animations: 3D flip on enter, blur on exit.",
        "--dwc-color-success"
    );

    stage = new Div();
    stage.addClassName("notification-stage");

    IconButton triggerBtn = new IconButton(FeatherIcon.BELL.create());
    triggerBtn.addClassName("trigger-btn");
    triggerBtn.onClick(e -> toggle());

    self.add(header, stage, triggerBtn);
    whenAttached().thenAccept(c -> toggle());
  }

  private void toggle() {
    if (isVisible) {
      hide();
    } else {
      show();
    }
  }

  private void show() {
    if (isVisible) return;

    currentNotification = new NotificationCard(
        FeatherIcon.CHECK_CIRCLE,
        "Success!",
        "Your changes have been saved successfully."
    );
    currentNotification.onClose(e -> hide());

    Page.getCurrent().startViewTransition()
        .enter(currentNotification, "flip-in")
        .onUpdate(done -> {
          stage.add(currentNotification);
          isVisible = true;
          done.run();
        })
        .start();
  }

  private void hide() {
    if (!isVisible || currentNotification == null) return;

    NotificationCard toRemove = currentNotification;

    Page.getCurrent().startViewTransition()
        .exit(toRemove, "blur-out")
        .onUpdate(done -> {
          stage.remove(toRemove);
          currentNotification = null;
          isVisible = false;
          done.run();
        })
        .start();
  }
}
