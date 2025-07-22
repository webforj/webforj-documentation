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

@Route("toastcookies")
@FrameTitle("Toast Cookies")
public class ToastCookiesView extends Composite<Div> {

  private Toast cookiesToast;
  private Icon cookieIcon;
  private Paragraph toastText;
  private Button acceptButton;
  private Button necessaryButton;
  private FlexLayout buttons;

  public ToastCookiesView() {
    createAndOpenToast();
  }

  private void createAndOpenToast() {
    cookiesToast = new Toast().setDuration(-1).setTheme(Theme.DEFAULT)
        .setPlacement(Placement.CENTER);

    cookieIcon = TablerIcon.create("cookie");
    cookieIcon.setStyle("width", "100px");
    cookieIcon.setStyle("height", "100px");

    toastText = new Paragraph("We use cookies to improve your experience. "
        + "By clicking 'Accept all cookies', you agree to our ");
    toastText.add(new Anchor("#", "Cookie Policy"));

    acceptButton = new Button("Accept all cookies").setTheme(ButtonTheme.PRIMARY);
    acceptButton.addClickListener(event -> {
      cookiesToast.close();
      createAndOpenToast();
    });

    necessaryButton = new Button("Necessary cookies only").setTheme(ButtonTheme.OUTLINED_PRIMARY);
    necessaryButton.addClickListener(event -> {
      cookiesToast.close();
      createAndOpenToast();
    });

    buttons = FlexLayout.create(acceptButton, necessaryButton)
        .horizontal()
        .build();
    buttons.setSpacing("var(--dwc-space-l)");

    FlexLayout toastContent = FlexLayout.create(cookieIcon, toastText, buttons)
        .vertical()
        .align().center()
        .build();
    toastContent.setSpacing("var(--dwc-space-m)");

    cookiesToast.addToMessage(toastContent);
    cookiesToast.open();
  }
}
