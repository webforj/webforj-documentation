package com.webforj.samples.views.javascript;

import com.webforj.Page;
import com.webforj.component.Composite;
import com.webforj.component.Theme;
import com.webforj.component.button.Button;
import com.webforj.component.button.ButtonTheme;
import com.webforj.component.field.TextField;
import com.webforj.component.html.elements.Div;
import com.webforj.component.html.elements.H2;
import com.webforj.component.layout.flexlayout.FlexAlignment;
import com.webforj.component.layout.flexlayout.FlexDirection;
import com.webforj.component.layout.flexlayout.FlexJustifyContent;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.component.toast.Toast;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;

@Route
@FrameTitle("Execute JavaScript")
public class ExecuteJavaScriptView extends Composite<FlexLayout> {
  private final FlexLayout self = getBoundComponent();

  private static final String INVITE_LINK = "https://webforj.com/start?invite=AB12CD";

  public ExecuteJavaScriptView() {
    self.setDirection(FlexDirection.COLUMN);
    self.setJustifyContent(FlexJustifyContent.CENTER);
    self.setAlignment(FlexAlignment.CENTER);
    self.setStyle("min-height", "100vh");

    Div card = new Div();
    card.setStyle("width", "100%");
    card.setStyle("max-width", "420px");
    card.setStyle("padding", "var(--dwc-space-l)");
    card.setStyle("background", "var(--dwc-surface-3)");
    card.setStyle(
        "border", "var(--dwc-border-width) var(--dwc-border-style) var(--dwc-border-color)");
    card.setStyle("border-radius", "var(--dwc-border-radius-l)");

    TextField link = new TextField();
    link.setValue(INVITE_LINK);
    link.setReadOnly(true);
    link.setStyle("flex", "1");

    Button copy = new Button("Copy link", e -> copyLink()).setTheme(ButtonTheme.PRIMARY);

    FlexLayout row = new FlexLayout(link, copy);
    row.setDirection(FlexDirection.ROW);
    row.setAlignment(FlexAlignment.CENTER);
    row.setSpacing("var(--dwc-space-s)");

    card.add(new H2("Invite a teammate"), row);
    self.add(card);
  }

  private void copyLink() {
    String script =
        "const text = '"
            + INVITE_LINK
            + "';"
            + "function fallback() {"
            + "  const ta = document.createElement('textarea');"
            + "  ta.value = text; ta.style.position = 'fixed'; ta.style.opacity = '0';"
            + "  document.body.appendChild(ta); ta.select();"
            + "  try { document.execCommand('copy'); } catch (e) {}"
            + "  document.body.removeChild(ta);"
            + "}"
            + "if (navigator.clipboard && navigator.clipboard.writeText) {"
            + "  navigator.clipboard.writeText(text).catch(fallback);"
            + "} else { fallback(); }";

    Page.getCurrent().executeJsVoidAsync(script);
    Toast.show("Link copied", 3000, Theme.SUCCESS);
  }
}
