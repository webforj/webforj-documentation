package com.webforj.samples.verify;

import com.webforj.Debouncer;
import com.webforj.component.Composite;
import com.webforj.component.element.Element;
import com.webforj.component.element.event.ElementEventOptions;
import com.webforj.component.field.TextArea;
import com.webforj.component.field.TextField;
import com.webforj.component.html.elements.Div;
import com.webforj.component.html.elements.Paragraph;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.router.annotation.Route;

/**
 * Verification file for debouncing documentation code snippets.
 */
@Route
public class VerifyDebouncingSnippets extends Composite<Div> {

  public VerifyDebouncingSnippets() {
    verifyBasicUsage();
    verifyCancelPending();
    verifyFlushExecution();
    verifyCheckPending();
    verifySearchAsYouType();
    verifyFormValidation();
    verifyAutoSave();
    verifyElementEventOptions();
    verifyCleanup();
  }

  private void verifyBasicUsage() {
    TextField textField = new TextField();
    Debouncer debounce = new Debouncer(0.3f);

    textField.onModify(e -> {
      debounce.run(() -> search(textField.getText()));
    });
  }

  private void verifyCancelPending() {
    Debouncer debounce = new Debouncer(1f);

    debounce.run(() -> saveDocument());

    // User navigates away before save executes
    debounce.cancel();
  }

  private void verifyFlushExecution() {
    TextField textField = new TextField();
    Debouncer debounce = new Debouncer(0.5f);
    com.webforj.component.button.Button submitButton = new com.webforj.component.button.Button();

    textField.onModify(e -> {
      debounce.run(() -> validateInput(textField.getText()));
    });

    // Force validation before form submission
    submitButton.onClick(e -> {
      debounce.flush();
      if (isValid()) {
        submitForm();
      }
    });
  }

  private void verifyCheckPending() {
    Paragraph statusLabel =
        new Paragraph();
    Debouncer debounce = new Debouncer(0.3f);

    if (debounce.isPending()) {
      statusLabel.setText("Processing...");
    }
  }

  private void verifySearchAsYouType() {
    // SearchView class from docs
    class SearchView extends Composite<FlexLayout> {
      private final Debouncer searchDebounce = new Debouncer(0.3f);
      private final TextField searchField = new TextField();
      private final Div results = new Div();

      public SearchView() {
        searchField.setPlaceholder("Search...");
        searchField.onModify(e -> {
          searchDebounce.run(() -> performSearch(searchField.getText()));
        });

        getBoundComponent().add(searchField, results);
      }

      private void performSearch(String query) {
        // Execute search and update results
        results.setText("Results for: " + query);
      }
    }

    new SearchView();
  }

  private void verifyFormValidation() {
    TextField emailField = new TextField();
    Debouncer validationDebounce = new Debouncer(0.5f);

    emailField.onModify(e -> {
      validationDebounce.run(() -> {
        String email = emailField.getText();
        if (!isValidEmail(email)) {
          emailField.setInvalid(true);
          emailField.setInvalidMessage("Please enter a valid email address");
        } else {
          emailField.setInvalid(false);
        }
      });
    });
  }

  private void verifyAutoSave() {
    TextArea editor = new TextArea();
    Debouncer saveDebounce = new Debouncer(2f);

    editor.onModify(e -> {
      saveDebounce.run(() -> {
        saveDocument(editor.getText());
        showNotification("Document saved");
      });
    });
  }

  private void verifyElementEventOptions() {
    Element element = new Element("div");

    // Using ElementEventOptions for client-side debouncing
    ElementEventOptions options = new ElementEventOptions();
    options.setDebounce(300);

    element.addEventListener("input", e -> {
      // This handler is debounced on the client
    }, options);
  }

  private void verifyCleanup() {
    class SearchPanel extends Composite<Div> {
      private final Debouncer debounce = new Debouncer(0.3f);

      @Override
      protected void onDidDestroy() {
        debounce.cancel();
      }
    }

    new SearchPanel();
  }

  // Helper methods for compilation
  private void search(String text) {}
  private void saveDocument() {}
  private void saveDocument(String text) {}
  private void validateInput(String text) {}
  private boolean isValid() { return true; }
  private void submitForm() {}
  private boolean isValidEmail(String email) { return true; }
  private void showNotification(String message) {}
}
