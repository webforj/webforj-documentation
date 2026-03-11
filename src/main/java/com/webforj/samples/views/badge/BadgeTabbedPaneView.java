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
    self.setDirection(FlexDirection.COLUMN);
    self.setMaxWidth("700px");
    self.setStyle("margin", "0 auto");
    self.setPadding("var(--dwc-space-l)");
    self.setSpacing("var(--dwc-space-m)");

    self.add(new H3("Badge in Tabbed Pane"));

    TabbedPane tabbedPane = new TabbedPane();

    Tab inbox = new Tab("Inbox");
    inbox.setSuffixComponent(new Badge("12", BadgeTheme.PRIMARY)
        .setExpanse(BadgeExpanse.XSMALL));

    Tab notifications = new Tab("Notifications");
    notifications.setSuffixComponent(new Badge("3", BadgeTheme.DANGER)
        .setExpanse(BadgeExpanse.XSMALL));

    Tab drafts = new Tab("Drafts");
    drafts.setSuffixComponent(new Badge("1", BadgeTheme.GRAY)
        .setExpanse(BadgeExpanse.XSMALL));

    Tab sent = new Tab("Sent");

    tabbedPane.addTab(inbox, new Paragraph("You have 12 messages in your inbox."));
    tabbedPane.addTab(notifications, new Paragraph("You have 3 unread notifications."));
    tabbedPane.addTab(drafts, new Paragraph("You have 1 draft saved."));
    tabbedPane.addTab(sent, new Paragraph("Your sent messages."));

    self.add(tabbedPane);

    self.add(new H3("Outlined Badges in Tabs"));

    TabbedPane outlinedPane = new TabbedPane();

    Tab tasks = new Tab("Tasks");
    tasks.setSuffixComponent(new Badge("5", BadgeTheme.OUTLINED_PRIMARY)
        .setExpanse(BadgeExpanse.XSMALL));

    Tab issues = new Tab("Issues");
    issues.setSuffixComponent(new Badge("8", BadgeTheme.OUTLINED_DANGER)
        .setExpanse(BadgeExpanse.XSMALL));

    Tab done = new Tab("Done");
    done.setSuffixComponent(new Badge("24", BadgeTheme.OUTLINED_SUCCESS)
        .setExpanse(BadgeExpanse.XSMALL));

    outlinedPane.addTab(tasks, new Paragraph("Your pending tasks."));
    outlinedPane.addTab(issues, new Paragraph("Open issues that need attention."));
    outlinedPane.addTab(done, new Paragraph("Completed items."));

    self.add(outlinedPane);
  }
}
