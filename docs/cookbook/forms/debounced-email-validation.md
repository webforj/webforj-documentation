---
title: "Validate an email field as the user types, debounced"
description: "Combine the Debouncer with a text field's modify event so validation runs once typing pauses, not on every keystroke."
tags: [forms, validation]
components: []
difficulty: intermediate
---

Wire `onModify` to a `Debouncer` so the regex check only runs after the user pauses typing. `setInvalid` plus `setInvalidMessage` toggle the field's built-in error state.

```java
import com.webforj.Debouncer;
import com.webforj.component.Composite;
import com.webforj.component.field.TextField;
import com.webforj.component.layout.flexlayout.FlexDirection;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.router.annotation.Route;
import java.util.regex.Pattern;

@Route("cookbook-email-validation")
public class DebouncedEmailView extends Composite<FlexLayout> {
  private static final Pattern EMAIL_PATTERN = Pattern.compile("^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$");

  private final FlexLayout self = getBoundComponent();
  private final TextField email = new TextField();
  private final Debouncer debouncer = new Debouncer(0.5f);

  public DebouncedEmailView() {
    self.setDirection(FlexDirection.COLUMN)
        .setMaxWidth("400px")
        .setMargin("var(--dwc-space-xl) auto")
        .setPadding("var(--dwc-space-l)")
        .setSpacing("var(--dwc-space-l)")
        .add(email);

    email
        .setLabel("Email address")
        .setPlaceholder("you@example.com")
        .setHelperText("We'll validate after you pause typing.")
        .onModify(e -> debouncer.run(() -> {
          String value = e.getText();
          if (value.isEmpty()) {
            email.setInvalid(false).setInvalidMessage("");
            return;
          }

          if (EMAIL_PATTERN.matcher(value).matches()) {
            email.setInvalid(false).setInvalidMessage("");
          } else {
            email.setInvalid(true).setInvalidMessage("Enter a valid email address.");
          }
        }));
  }

  @Override
  protected void onDidDestroy() {
    debouncer.cancel();
  }
}
```

The `Debouncer` constructor takes seconds as a float (`0.5f` = 500 ms). Each `onModify` keystroke calls `debouncer.run(...)`, which cancels the previous pending action and restarts the timer — so the regex check fires only once typing settles. Always cancel the debouncer in `onDidDestroy` so a pending action can't fire against a destroyed view.
