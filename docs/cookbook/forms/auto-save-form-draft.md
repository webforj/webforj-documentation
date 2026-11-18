---
title: "Auto-save a form draft in LocalStorage"
description: "Preserve unfinished TextField and TextArea values across reloads by saving them to LocalStorage."
tags: [forms, components]
components: [TextField, TextArea]
difficulty: beginner
---

Restore the saved values before attaching change listeners, then update the browser copy whenever either field changes. Remove the keys after the user clears or successfully submits the form.

```java
import com.webforj.component.Composite;
import com.webforj.component.button.Button;
import com.webforj.component.field.TextArea;
import com.webforj.component.field.TextField;
import com.webforj.component.layout.flexlayout.FlexDirection;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.router.annotation.Route;
import com.webforj.webstorage.LocalStorage;

@Route("message-draft")
public class MessageDraftView extends Composite<FlexLayout> {
  private static final String SUBJECT_KEY = "message-draft.subject";
  private static final String MESSAGE_KEY = "message-draft.message";

  private final FlexLayout self = getBoundComponent();
  private final LocalStorage storage = LocalStorage.getCurrent();
  private final TextField subject = new TextField("Subject");
  private final TextArea message = new TextArea("Message");

  public MessageDraftView() {
    self.setDirection(FlexDirection.COLUMN)
        .setSpacing("0.75rem")
        .setMaxWidth(480);
    message.setRows(8);

    restoreDraft();

    subject.addValueChangeListener(event -> storage.add(SUBJECT_KEY, event.getValue()));
    message.addValueChangeListener(event -> storage.add(MESSAGE_KEY, event.getValue()));

    Button clear = new Button("Clear draft", event -> clearDraft());
    self.add(subject, message, clear);
  }

  private void restoreDraft() {
    String savedSubject = storage.get(SUBJECT_KEY);
    String savedMessage = storage.get(MESSAGE_KEY);

    if (savedSubject != null) {
      subject.setValue(savedSubject);
    }
    if (savedMessage != null) {
      message.setValue(savedMessage);
    }
  }

  private void clearDraft() {
    subject.setValue("");
    message.setValue("");
    storage.remove(SUBJECT_KEY, MESSAGE_KEY);
  }
}
```

Local storage persists after the browser closes and is accessible to client-side scripts. Use it only for nonsensitive drafts, and remove the stored values after a successful save.
