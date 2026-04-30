package com.webforj.samples.views.appnav;

import com.webforj.component.Composite;
import com.webforj.component.html.elements.Div;
import com.webforj.component.html.elements.H1;
import com.webforj.component.html.elements.Paragraph;
import com.webforj.router.Router;
import com.webforj.router.annotation.Route;
import com.webforj.router.event.NavigateEvent;
import com.webforj.router.history.ParametersBag;

@Route(value = ":id", outlet = AppNavView.class)
public class AppNavPageView extends Composite<Div> {
  private final Div self = getBoundComponent();
  private final Paragraph text = new Paragraph();

  public AppNavPageView() {
    H1 title = new H1("Application Title");
    self.add(title, text);
    Router.getCurrent().onNavigate(this::onNavigate);
  }

  private void onNavigate(NavigateEvent ev) {
    ParametersBag parameters = ev.getContext().getRouteParameters();
    parameters
        .get("id")
        .ifPresent(
            id -> {
              text.setHtml("Content for <strong>%s</strong> goes here".formatted(id));
            });
  }

  @Override
  protected void onDestroy() {
    Router.getCurrent().removeAllListeners();
    super.onDestroy();
  }
}
