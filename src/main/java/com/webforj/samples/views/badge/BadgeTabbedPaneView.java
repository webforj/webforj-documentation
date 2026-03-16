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
    Badge inboxBadge = new Badge("12", BadgeTheme.PRIMARY);
    inboxBadge.setExpanse(BadgeExpanse.XSMALL);
    inbox.setSuffixComponent(inboxBadge);

    Tab notifications = new Tab("Notifications");
    Badge notifBadge = new Badge("3", BadgeTheme.DANGER);
    notifBadge.setExpanse(BadgeExpanse.XSMALL);
    notifications.setSuffixComponent(notifBadge);

    Tab drafts = new Tab("Drafts");
    Badge draftsBadge = new Badge("1", BadgeTheme.GRAY);
    draftsBadge.setExpanse(BadgeExpanse.XSMALL);
    drafts.setSuffixComponent(draftsBadge);

    Tab sent = new Tab("Sent");

    tabbedPane.addTab(inbox, new Paragraph("You have 12 messages in your inbox."));
    tabbedPane.addTab(notifications, new Paragraph("You have 3 unread notifications."));
    tabbedPane.addTab(drafts, new Paragraph("You have 1 draft saved."));
    tabbedPane.addTab(sent, new Paragraph("Your sent messages."));

    self.add(tabbedPane);

    self.add(new H3("Outlined Badges in Tabs"));

    TabbedPane outlinedPane = new TabbedPane();

    Tab tasks = new Tab("Tasks");
    Badge tasksBadge = new Badge("5", BadgeTheme.OUTLINED_PRIMARY);
    tasksBadge.setExpanse(BadgeExpanse.XSMALL);
    tasks.setSuffixComponent(tasksBadge);

    Tab issues = new Tab("Issues");
    Badge issuesBadge = new Badge("8", BadgeTheme.OUTLINED_DANGER);
    issuesBadge.setExpanse(BadgeExpanse.XSMALL);
    issues.setSuffixComponent(issuesBadge);

    Tab done = new Tab("Done");
    Badge doneBadge = new Badge("24", BadgeTheme.OUTLINED_SUCCESS);
    doneBadge.setExpanse(BadgeExpanse.XSMALL);
    done.setSuffixComponent(doneBadge);

    outlinedPane.addTab(tasks, new Paragraph("Your pending tasks."));
    outlinedPane.addTab(issues, new Paragraph("Open issues that need attention."));
    outlinedPane.addTab(done, new Paragraph("Completed items."));

    self.add(outlinedPane);
  }
}
