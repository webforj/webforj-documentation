package com.webforj.samples.views.applayout;

import com.webforj.component.Composite;
import com.webforj.component.html.elements.Div;
import com.webforj.component.html.elements.H1;
import com.webforj.component.html.elements.Paragraph;
import com.webforj.router.Router;
import com.webforj.router.event.NavigateEvent;
import com.webforj.router.history.ParametersBag;

public abstract class AbstractContentView extends Composite<Div> {
  private final Div self = getBoundComponent();
  protected String name;
  protected Paragraph contentLabel = new Paragraph("Loading content...");

  public AbstractContentView() {
    self.add(new H1("Application Title"), contentLabel);
    Router.getCurrent().onNavigate(this::onNavigate);
  }

  private void onNavigate(NavigateEvent ev) {
    ParametersBag parameters = ev.getContext().getRouteParameters();
    parameters.get("name").ifPresent(name -> {
      this.name = name;
      contentLabel.setText(String.format("Content for %s goes here", name));
    });
  }
}
