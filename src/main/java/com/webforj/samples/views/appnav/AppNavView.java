package com.webforj.samples.views.appnav;

import com.webforj.component.Composite;
import com.webforj.component.Theme;
import com.webforj.component.html.elements.H1;
import com.webforj.component.html.elements.Strong;
import com.webforj.component.icons.TablerIcon;
import com.webforj.component.layout.applayout.AppDrawerToggle;
import com.webforj.component.layout.applayout.AppLayout;
import com.webforj.component.layout.appnav.AppNav;
import com.webforj.component.layout.appnav.AppNavItem;
import com.webforj.component.layout.toolbar.Toolbar;
import com.webforj.router.annotation.Route;
import com.webforj.router.history.ParametersBag;

@Route
public class AppNavView extends Composite<AppLayout> {
  private AppLayout self = getBoundComponent();
  private Toolbar toolbar = new Toolbar();
  private AppNav appNav = new AppNav();

  public AppNavView() {
    setHeader();
    setDrawer();
  }

  private void setHeader() {
    self.setHeaderOffscreen(false)
            .setDrawerHeaderVisible(true)
            .addToHeader(toolbar);

    toolbar.setTheme(Theme.PRIMARY)
            .addToStart(new AppDrawerToggle())
            .addToTitle(new H1("Application"));
  }

  private void setDrawer() {
    self.addToDrawer(appNav);

    AppNavItem inbox = new AppNavItem("Inbox")
            .setPrefixComponent(TablerIcon.create("inbox"))
            .setSuffixComponent(new Strong("54"))
            .addItem(createItem("Primary", "mailbox"))
            .addItem(createItem("Promotions", "tag"))
            .addItem(createItem("Social", "users"))
            .addItem(createItem("Updates", "bell"))
            .addItem(createItem("Forums", "message-circle"));

    AppNavItem about = new AppNavItem("About")
            .setPrefixComponent(TablerIcon.create("info-circle"))
            .addItem(new AppNavItem("webforJ", "https://webforj.com/", TablerIcon.create("external-link")))
            .addItem(new AppNavItem("GitHub", "https://github.com/webforj/webforj", TablerIcon.create("brand-github")))
            .addItem(new AppNavItem("Documentation", "https://documentation.webforj.com/", TablerIcon.create("book")));

    appNav.setAutoOpen(true)
            .addItem(inbox)
            .addItem(createItem("Sent", "send"))
            .addItem(createItem("Archived", "archive"))
            .addItem(createItem("Trash", "trash"))
            .addItem(createItem("Spam", "alert-hexagon"))
            .addItem(about);
  }

  private AppNavItem createItem(String text, String icon) {
    return new AppNavItem(text, AppNavPageView.class, ParametersBag.of("id=" + text), TablerIcon.create(icon));
  }
}
