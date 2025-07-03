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
import com.webforj.samples.config.RouteConfig;

@Route(RouteConfig.APP_NAV)
public class AppNavView extends Composite<AppLayout> {
 private AppLayout self = getBoundComponent();

  public AppNavView() {
    setHeader();
    setDrawer();
  }

  private void setHeader() {
    self.setHeaderOffscreen(false);
    self.setDrawerHeaderVisible(true);

    Toolbar toolbar = new Toolbar();
    toolbar.setTheme(Theme.PRIMARY);
    toolbar.addToStart(new AppDrawerToggle());
    toolbar.addToTitle(new H1("Application"));

    self.addToHeader(toolbar);
  }

  private void setDrawer() {

    AppNav appNav = new AppNav();
    appNav.setAutoOpen(true);

    AppNavItem inbox = new AppNavItem("Inbox");
    inbox.setPrefixComponent(TablerIcon.create("inbox"));
    inbox.setSuffixComponent(new Strong("54"));
    inbox.addItem(new AppNavItem("Primary", AppNavPageView.class, ParametersBag.of("id=Primary"), TablerIcon.create("mailbox")));
    inbox.addItem(new AppNavItem("Promotions", AppNavPageView.class, ParametersBag.of("id=Promotions"), TablerIcon.create("tag")));
    inbox.addItem(new AppNavItem("Social", AppNavPageView.class, ParametersBag.of("id=Social"), TablerIcon.create("users")));
    inbox.addItem(new AppNavItem("Updates", AppNavPageView.class, ParametersBag.of("id=Updates"), TablerIcon.create("bell")));
    inbox.addItem(new AppNavItem("Forums", AppNavPageView.class, ParametersBag.of("id=Forums"), TablerIcon.create("message-circle")));

    appNav.addItem(inbox);
    appNav.addItem(new AppNavItem("Sent", AppNavPageView.class, ParametersBag.of("id=Sent"), TablerIcon.create("send")));
    appNav.addItem(new AppNavItem("Archived", AppNavPageView.class, ParametersBag.of("id=Archived"), TablerIcon.create("archive")));
    appNav.addItem(new AppNavItem("Trash", AppNavPageView.class, ParametersBag.of("id=Trash"), TablerIcon.create("trash")));
    appNav.addItem(new AppNavItem("Spam", AppNavPageView.class, ParametersBag.of("id=Spam"), TablerIcon.create("alert-hexagon")));

    AppNavItem about = new AppNavItem("About");
    about.setPrefixComponent(TablerIcon.create("info-circle"));

    about.addItem(new AppNavItem("webforJ", "https://webforj.com/", TablerIcon.create("external-link")));
    about.addItem(new AppNavItem("GitHub", "https://github.com/webforj/webforj", TablerIcon.create("brand-github")));
    about.addItem(new AppNavItem("Documentation", "https://documentation.webforj.com/", TablerIcon.create("book")));

    appNav.addItem(about);
    self.addToDrawer(appNav);
  }
}
