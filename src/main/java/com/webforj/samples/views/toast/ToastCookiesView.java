package com.webforj.samples.views.toast;

import com.webforj.component.Composite;
import com.webforj.component.Theme;
import com.webforj.component.button.Button;
import com.webforj.component.button.ButtonTheme;
import com.webforj.component.html.elements.Anchor;
import com.webforj.component.html.elements.Div;
import com.webforj.component.html.elements.Paragraph;
import com.webforj.component.icons.Icon;
import com.webforj.component.icons.TablerIcon;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.component.toast.Toast;
import com.webforj.component.toast.Toast.Placement;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;

@Route
@FrameTitle("Toast Cookies")
public class ToastCookiesView extends Composite<Div> {
  private final Div self = getBoundComponent();
  private Toast cookiesToast;

  public ToastCookiesView() {
    createAndOpenToast();
  }

  private void createAndOpenToast() {
    cookiesToast = new Toast()
      .setDuration(-1)
      .setTheme(Theme.DEFAULT)
      .setPlacement(Placement.CENTER);

    Icon cookieIcon = TablerIcon.create("cookie")
      .setStyle("width", "100px")
      .setStyle("height", "100px");

    // Use text block for cleaner multi-line text
    Paragraph toastText = new Paragraph("""
      We use cookies to improve your experience.
      By clicking 'Accept all cookies', you agree to our """);
    toastText.add(new Anchor("#", "Cookie Policy"));

    Button acceptButton = new Button("Accept all cookies")
      .setTheme(ButtonTheme.PRIMARY);
    acceptButton.addClickListener(event -> {
      cookiesToast.close();
      createAndOpenToast();
    });

    Button necessaryButton = new Button("Necessary cookies only")
      .setTheme(ButtonTheme.OUTLINED_PRIMARY);
    necessaryButton.addClickListener(event -> {
      cookiesToast.close();
      createAndOpenToast();
    });

    FlexLayout buttons = FlexLayout.create(acceptButton, necessaryButton)
      .horizontal()
      .build()
      .setSpacing("var(--dwc-space-l)");

    FlexLayout toastContent = FlexLayout.create(cookieIcon, toastText, buttons)
      .vertical()
      .align().center()
      .build()
      .setSpacing("var(--dwc-space-m)");

    cookiesToast.addToMessage(toastContent);
    cookiesToast.open();
  }
}
