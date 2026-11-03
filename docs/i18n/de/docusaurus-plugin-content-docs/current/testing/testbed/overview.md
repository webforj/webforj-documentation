---
sidebar_position: 0
title: TestBed
hide_table_of_contents: true
hide_giscus_comments: true
description: >-
  The TestBed is webforJ's browserless testing, which runs views and components
  in plain JUnit tests without a browser or a server.
sidebar_class_name: new-content
_i18n_hash: a97354d7621ab9d89fc8b88c7592847f
---
<Head>
  <style>{`
  .container {
    max-width: 65em !important;
  }
  `}</style>
</Head>

<!-- vale Google.Headings = NO -->
# TestBed <DocChip chip='since' label='26.03' />
<!-- vale Google.Headings = YES -->

Zum Testen einer webforJ-Ansicht startet man üblicherweise die App, öffnet einen Browser und steuert ihn mit Selenium oder Playwright. So lässt sich prüfen, was nur im Browser sichtbar ist. Dafür muss jedoch jeder Test den Browser, den Server und die Wartezeit dazwischen in Kauf nehmen.

Das TestBed ermöglicht browserloses Testen mit webforJ. Eine Ansicht läuft in einem einfachen JUnit-Test. Es wird weder ein Browser geöffnet noch ein Server gestartet. Der Test fügt die Ansicht hinzu, führt darauf Aktionen wie ein Benutzer aus und überprüft das Ergebnis in Java. Komponenten werden eingebunden, lösen ihre Ereignisse aus und navigieren genau wie in einer laufenden App. Jede Interaktion ist abgeschlossen, bevor die nächste Zeile des Tests ausgeführt wird. So gibt es keine Wartezeiten und keine instabilen Tests.

## So funktioniert ein Test {#how-a-test-works}

Ein TestBed-Test folgt den Schritten eines Benutzers. Er findet eine Komponente auf der Seite, interagiert mit ihr und prüft anschließend, was die Seite anzeigt. Das folgende Formular abonniert eine E-Mail-Adresse. Die Schaltfläche **Subscribe** bleibt deaktiviert, bis das Feld eine Adresse enthält:

```java title="NewsletterForm.java" showLineNumbers
import com.webforj.component.Composite;
import com.webforj.component.button.Button;
import com.webforj.component.field.TextField;
import com.webforj.component.html.elements.Div;
import com.webforj.component.html.elements.Paragraph;

public class NewsletterForm extends Composite<Div> {

  public NewsletterForm() {
    TextField email = new TextField("Email");
    Paragraph status = new Paragraph();
    Button subscribe =
        new Button(
            "Subscribe", e -> status.setText("Subscribed " + email.getValue()));
    subscribe.setEnabled(false);
    email.onModify(e -> subscribe.setEnabled(!email.getValue().isBlank()));

    getBoundComponent().add(email, subscribe, status);
  }
}
```

Der Test findet das Feld und die Schaltfläche, gibt eine Adresse ein, klickt darauf und überprüft die Bestätigung:

```java title="NewsletterFormTest.java" showLineNumbers
@Test
void shouldSubscribeTheEmailTheUserTyped(WebforjTestbed context) {
  context.add(new NewsletterForm());

  var email = ComponentLocator.of(TextField.class).byLabel("Email").locate();
  var subscribe = ComponentLocator.of(Button.class)
      .byText("Subscribe")
      .locate();

  UserInteraction.type(email, "ada@example.com");
  UserInteraction.click(subscribe);

  assertTrue(
      ComponentLocator.of(Paragraph.class)
          .byText("Subscribed ada@example.com")
          .isPresent());
}
```

Jeder Schritt hat seinen eigenen Teil des TestBed:

- **Finden.** `ComponentLocator` beschreibt eine Komponente so, wie ein Benutzer sie auswählt: anhand ihres Typs und dessen, was sie anzeigt, zum Beispiel ein Feld anhand seiner Beschriftung. Siehe [Komponenten finden](./finding-components).
- **Interagieren.** Mit `UserInteraction` werden die Aktionen eines Benutzers ausgeführt – und nur solche, die ein Benutzer auch ausführen könnte. Wird auf eine deaktivierte Schaltfläche geklickt, schlägt der Test fehl, statt den Listener auszuführen. Siehe [Benutzerinteraktion](./user-interaction).
- **Überprüfen.** `verifyThat` prüft, was ein Benutzer an einer Komponente sehen würde. Ein Snapshot vergleicht die gesamte Ansicht mit einer gespeicherten Beschreibung davon. Siehe [Assertions](./assertions) und [Snapshots](./snapshots).

Der Test greift niemals direkt auf die Felder der Ansicht zu. Er arbeitet mit dem, was die Seite anzeigt. Dadurch bleibt er erfolgreich, wenn die Ansicht anders aufgebaut ist, und schlägt fehl, wenn der Benutzer etwas anderes sehen würde.

Das TestBed kann über einzelne Ansichten hinaus Routen aufrufen, geschützte Ansichten testen, Dialoge beantworten und Timer ausführen, wie unter [Apps testen](./testing-apps) beschrieben. Außerdem kann es innerhalb einer Spring-Boot-App mit deren Beans und Sicherheitsmechanismen ausgeführt werden.

Browser-Tests sind weiterhin wichtig, wenn es um Dinge geht, die nur ein Browser zeigen kann, etwa Styling, Layout und Verhalten auf der Clientseite. Das TestBed deckt den Rest ab: das Verhalten deiner Ansichten bei der Interaktion mit einem Benutzer.

Unter [Erste Schritte](./getting-started) erfährst du, wie du die Abhängigkeit hinzufügst und deinen ersten Test schreibst.

## Themen {#topics}

<DocCardList className="topics-section" />
