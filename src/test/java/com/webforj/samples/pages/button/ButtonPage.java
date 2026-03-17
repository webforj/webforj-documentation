package com.webforj.samples.pages.button;

import com.microsoft.playwright.Page;
import com.webforj.component.button.Button;
import com.webforj.component.dialog.Dialog;
import com.webforj.component.field.TextField;
import com.webforj.component.html.elements.Div;
import com.webforj.samples.pages.AbstractPage;
import com.webforj.samples.utils.WebforjLocator;
import com.webforj.samples.views.button.ButtonView;

public class ButtonPage extends AbstractPage {

    public ButtonPage(Page page) {
      super(page, ButtonView.class);
    }

    public WebforjLocator getFirstName() {
        return getByClass(TextField.class).first().locator("input");
    }

    public WebforjLocator getLastName() {
        return getByClass(TextField.class).nth(1).locator("input");
    }

    public WebforjLocator getEmail() {
        return getByClass(TextField.class).last().locator("input");
    }

    public WebforjLocator getSubmitButton() {
        return getByClass(Button.class).getByText("Submit");
    }

    public WebforjLocator getClearButton() {
      return getByClass(Button.class).getByText("Clear");

    }

    public WebforjLocator getWelcomeDialog() {
        return getByClass(Dialog.class);
    }

    public WebforjLocator getOkButton() {
      return getByClass(Button.class).getByText("OK");
    }

    public WebforjLocator getDialogContent() {
      return getByText("Welcome to the app");
    }
}
