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

  public ToastCookiesView() {
    openToast();
  }

  private void openToast() {
    Toast cookiesToast = new Toast();
    cookiesToast
        .setDuration(-1)
        .setTheme(Theme.DEFAULT)
        .setPlacement(Placement.BOTTOM)
        .setStyle("--dwc-toast-minWidth", "400px");

    Icon cookieIcon =
        TablerIcon.create("cookie").setStyle("width", "100px").setStyle("height", "100px");

    Paragraph toastText =
        new Paragraph(
            """
      We use cookies to improve your experience.
      By clicking 'Accept all cookies', you agree to our\s""");
    toastText.add(new Anchor("#", "Cookie Policy"));

    Button acceptButton = new Button("Accept all cookies").setTheme(ButtonTheme.PRIMARY);
    acceptButton.addClickListener(
        event -> {
          cookiesToast.close();
          openToast();
        });

    Button necessaryButton =
        new Button("Necessary cookies only").setTheme(ButtonTheme.OUTLINED_PRIMARY);
    necessaryButton.addClickListener(
        event -> {
          cookiesToast.close();
          openToast();
        });

    FlexLayout buttons =
        FlexLayout.create(acceptButton, necessaryButton)
            .horizontal()
            .build()
            .setSpacing("var(--dwc-space-l)");

    FlexLayout toastContent =
        FlexLayout.create(cookieIcon, toastText, buttons)
            .vertical()
            .align()
            .center()
            .build()
            .setSpacing("var(--dwc-space-m)");

    cookiesToast.addToMessage(toastContent);
    cookiesToast.open();
  }
}
