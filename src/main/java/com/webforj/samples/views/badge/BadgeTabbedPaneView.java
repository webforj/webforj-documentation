package com.webforj.samples.views.badge;

import com.webforj.component.Composite;
import com.webforj.component.badge.Badge;
import com.webforj.component.badge.BadgeExpanse;
import com.webforj.component.badge.BadgeTheme;
import com.webforj.component.html.elements.H3;
import com.webforj.component.html.elements.Paragraph;
import com.webforj.component.layout.flexlayout.FlexDirection;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.component.tabbedpane.Tab;
import com.webforj.component.tabbedpane.TabbedPane;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;

@Route
@FrameTitle("Badge - Tabbed Pane")
public class BadgeTabbedPaneView extends Composite<FlexLayout> {
  private final FlexLayout self = getBoundComponent();

  public BadgeTabbedPaneView() {
    self.setDirection(FlexDirection.COLUMN)
        .setSpacing("var(--dwc-space-m)")
        .setPadding("var(--dwc-space-l)")
        .setMargin("0 auto")
        .setMaxWidth("700px");

    self.add(new H3("Badge in Tabbed Pane"));
    self.add(createFilledBadgePane());

    self.add(new H3("Outlined Badges in Tabs"));
    self.add(createOutlinedBadgePane());
  }

  private TabbedPane createFilledBadgePane() {
    TabbedPane pane = new TabbedPane();

    Tab inbox =
        new Tab("Inbox")
            .setSuffixComponent(createBadge("12", BadgeTheme.PRIMARY, BadgeExpanse.XSMALL));

    Tab notifications =
        new Tab("Notifications")
            .setSuffixComponent(createBadge("3", BadgeTheme.DANGER, BadgeExpanse.XSMALL));

    Tab drafts =
        new Tab("Drafts")
            .setSuffixComponent(createBadge("1", BadgeTheme.GRAY, BadgeExpanse.XSMALL));

    Tab sent = new Tab("Sent");

    pane.addTab(inbox, new Paragraph("You have 12 messages in your inbox."));
    pane.addTab(notifications, new Paragraph("You have 3 unread notifications."));
    pane.addTab(drafts, new Paragraph("You have 1 draft saved."));
    pane.addTab(sent, new Paragraph("Your sent messages."));

    return pane;
  }

  private TabbedPane createOutlinedBadgePane() {
    TabbedPane pane = new TabbedPane();

    Tab tasks =
        new Tab("Tasks")
            .setSuffixComponent(createBadge("5", BadgeTheme.OUTLINED_PRIMARY, BadgeExpanse.XSMALL));

    Tab issues =
        new Tab("Issues")
            .setSuffixComponent(createBadge("8", BadgeTheme.OUTLINED_DANGER, BadgeExpanse.XSMALL));

    Tab done =
        new Tab("Done")
            .setSuffixComponent(
                createBadge("24", BadgeTheme.OUTLINED_SUCCESS, BadgeExpanse.XSMALL));

    pane.addTab(tasks, new Paragraph("Your pending tasks."));
    pane.addTab(issues, new Paragraph("Open issues that need attention."));
    pane.addTab(done, new Paragraph("Completed items."));

    return pane;
  }

  private Badge createBadge(String text, BadgeTheme theme, BadgeExpanse expanse) {
    return new Badge(text).setTheme(theme).setExpanse(expanse);
  }
}
