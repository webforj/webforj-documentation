---
sidebar_position: 11
title: Localization
sidebar_class_name: new-content
_i18n_hash: 91f5af285113e5e76d50a201a2fbf88f
---
# Lokalisierung <DocChip chip='since' label='25.10' />

Komponenten, die das `LocaleObserver`-Interface implementieren, erhalten automatische Benachrichtigungen, wenn sich die Locale ändert. Dies ermöglicht es UI-Elementen, ihren Text, ihre Formatierung und andere locale-spezifische Inhalte ohne manuelle Koordination zu aktualisieren.

## Das `LocaleObserver`-Interface {#the-localeobserver-interface}

```java title="LocaleObserver.java"
@FunctionalInterface
public interface LocaleObserver extends Serializable {
    void onLocaleChange(LocaleEvent event);
}
```

Wenn eine Komponente dieses Interface implementiert, registriert webforJ automatisch:
- Registriert die Komponente bei der Erstellung, um Ereignisse zur Änderung der Locale zu empfangen
- Stornieren die Registrierung der Komponente beim Zerstören
- Ruft `onLocaleChange()` auf, wann immer sich die Locale ändert

Diese Registrierung erfolgt über den Lebenszyklus der Komponente.

## Übersetzungen Handhaben {#handling-translations}

Wenn `onLocaleChange()` aufgerufen wird, erhalten Komponenten die neue Locale. Wie sie Übersetzungen laden und anwenden, liegt beim Entwickler. Häufige Ansätze sind:

- Java `ResourceBundle` mit Eigenschaftsdateien
- Datenbankabfragen für Übersetzungen
- Benutzerdefinierte Übersetzungsanbieter
- Hardcodierte Maps für einfache Fälle

Dieses Beispiel verwendet `ResourceBundle`, das Übersetzungen in Eigenschaftsdateien speichert:

```
messages.properties        # Fallback/Standard
messages_en.properties     # Englisch
messages_de.properties     # Deutsch
```

Eigenschaftsdateien enthalten Schlüssel-Wert-Paare:

```properties title="messages_en.properties"
app.title=Mailbox
menu.inbox=Inbox
```

```properties title="messages_de.properties"
app.title=Postfach
menu.inbox=Posteingang
```
## Ändern der Locale {#changing-the-locale}

Verwenden Sie `App.setLocale()`, um die Locale der App zu ändern. Dies löst Benachrichtigungen an alle registrierten Beobachter aus:

```java
App.setLocale(Locale.GERMAN);
App.setLocale(Locale.forLanguageTag("fr"));
```

Eine typische Implementierung könnte ein Dropdown oder eine Auswahlkomponente verwenden:

```java
ChoiceBox languageSelector = new ChoiceBox();
languageSelector.add("en", "Englisch");
languageSelector.add("de", "Deutsch");
languageSelector.add("fr", "Französisch");

languageSelector.onSelect(e -> {
  String lang = (String) e.getSelectedItem().getKey();
  Locale newLocale = Locale.forLanguageTag(lang);

  App.setLocale(newLocale);
});
```

Wenn der Benutzer eine Sprache auswählt, wird `App.setLocale()` ausgelöst, und alle Komponenten, die `LocaleObserver` implementieren, erhalten die Aktualisierung.

## Implementieren von Beobachtern {#implementing-observers}

Wenn eine Komponente `LocaleObserver` implementiert, muss sie zwei Szenarien handhaben: die erste Anzeige mit der aktuellen Locale und die Aktualisierung, wenn sich die Locale ändert. Das folgende Beispiel demonstriert dieses Muster mit einer Komponente, die lokalisierten Text und Links anzeigt.

Die Komponente speichert Referenzen zu Elementen, die Übersetzungen benötigen. Bei der Konstruktion lädt sie die Übersetzungen der aktuellen Locale. Wenn sich die Locale ändert, wird `onLocaleChange()` ausgelöst, was die Komponente ermöglicht, Übersetzungen neu zu laden und ihren angezeigten Text zu aktualisieren.

```java title="TranslationService.java"
import com.webforj.App;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

@Service
public class TranslationService {
  private final MessageSource messageSource;

  public TranslationService(MessageSource messageSource) {
    this.messageSource = messageSource;
  }

  public String get(String key) {
    return messageSource.getMessage(key, null, App.getLocale());
  }
}
```

```java title="Explore.java"
public class Explore extends Composite<FlexLayout> implements LocaleObserver {
  private final TranslationService i18n;
  private FlexLayout self = getBoundComponent();
  private H3 titleElement;
  private Anchor anchor;
  private String titleKey;

  public Explore(TranslationService i18n, String titleKey) {
    this.i18n = i18n;
    this.titleKey = titleKey;

    self.addClassName("explore-component");
    self.setStyle("margin", "1em auto");
    self.setDirection(FlexDirection.COLUMN);
    self.setAlignment(FlexAlignment.CENTER);
    self.setMaxWidth(300);
    self.setSpacing(".3em");

    Img img = new Img(String.format("ws://explore/%s.svg", titleKey), "mailbox");
    img.setMaxWidth(250);

    String translatedTitle = i18n.get("menu." + titleKey.toLowerCase());
    titleElement = new H3(translatedTitle);

    anchor = new Anchor("https://docs.webforj.com/docs/components/overview", i18n.get("explore.link"));
    anchor.setTarget("_blank");

    self.add(img, titleElement, anchor);
  }

  @Override
  public void onLocaleChange(LocaleEvent event) {
    titleElement.setText(i18n.get("menu." + titleKey.toLowerCase()));
    anchor.setText(i18n.get("explore.link"));
  }
}
```

Die Komponente speichert Referenzen zu Elementen, die übersetzten Inhalt anzeigen (`titleElement` und `anchor`). Übersetzungen werden im Konstruktor unter Verwendung der aktuellen Locale geladen. Wenn sich die Locale ändert, aktualisiert `onLocaleChange()` nur den Text, der übersetzt werden muss.

## Lebenszyklusverwaltung {#lifecycle-management}

Das Framework behandelt die Registrierung von Beobachtern automatisch über die Lebenszyklus-Hooks der Komponente:

- **Bei Erstellung**: Komponenten, die `LocaleObserver` implementieren, werden im `LocaleObserverRegistry` registriert
- **Bei Zerstörung**: Komponenten werden abgemeldet, um Speicherlecks zu verhindern

Jede App-Instanz verwaltet ihr eigenes Beobachter-Register. Diese automatische Verwaltung bedeutet:

- Keine manuellen Registrierungs-/Abmeldungsaufrufe
- Keine Speicherlecks durch zerstörte Komponenten
- Thread-sichere gleichzeitige Benachrichtigungen

:::info Pro-App-Registry
Jede App-Instanz verwaltet ihr eigenes Beobachter-Register. Beobachter, die in einer App registriert sind, erhalten keine Benachrichtigungen von anderen Apps, die im selben JVM laufen.
:::

## `LocaleEvent` {#localeevent}

Das `LocaleEvent`, das an `onLocaleChange()` übergeben wird, bietet:

| Methode | Gibt zurück | Beschreibung |
|---------|-------------|--------------|
| `getLocale()` | `Locale` | Die neue Locale, die eingestellt wurde |
| `getSource()` | `Object` | Die Komponente, die das Ereignis erhalten hat |

```java
@Override
public void onLocaleChange(LocaleEvent event) {
  Locale newLocale = event.getLocale();
  Object source = event.getSource();

  // Komponente mit neuer Locale aktualisieren
  ResourceBundle bundle = ResourceBundle.getBundle("messages", newLocale);
  updateUI(bundle);
}
```
