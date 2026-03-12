---
sidebar_position: 11
title: Locale Management
sidebar_class_name: new-content
_i18n_hash: cfcad51aaedd77b781334fc048b0a4f1
---
# Lokalisierungsmanagement <DocChip chip='since' label='25.10' />

webforJ bietet integrierte Unterstützung für die Verwaltung der App-Lokalisierung. Die Lokalisierung bestimmt, welche Sprache und regionalen Formatierungen in der gesamten App verwendet werden. Komponenten können auf Änderungen der Lokalisierung über das Schnittstelle `LocaleObserver` reagieren, wodurch die Benutzeroberfläche sofort aktualisiert wird, wenn der Benutzer die Sprache wechselt.

## Standardlokalisierung festlegen {#setting-the-default-locale}

Die App-Lokalisierung kann über die Eigenschaft `webforj.locale` konfiguriert werden. Dies legt die Lokalisierung fest, die die App bei Start verwendet, und beeinflusst alle lokalisierungsabhängigen Formatierungen und Texte. Wenn `webforj.locale` nicht konfiguriert ist, fällt die App auf die Standardlokalisierung der JVM des Servers zurück. Sie können die aktuelle Lokalisierung jederzeit mit `App.getLocale()` abfragen.

Siehe die [Konfiguration](/docs/configuration/properties) Sektion, um zu erfahren, wie Sie Eigenschaften für verschiedene Umgebungen festlegen.

## Lokalisierung ändern {#changing-the-locale}

Um die Lokalisierung zur Laufzeit zu ändern, rufen Sie `App.setLocale()` auf. Dies aktualisiert die Lokalisierung für die gesamte App und informiert alle Komponenten, die `LocaleObserver` implementieren, wodurch die Benutzeroberfläche ohne einen Seitenneuladen aktualisiert wird.

```java
App.setLocale(Locale.GERMAN);
App.setLocale(Locale.forLanguageTag("fr"));
```

## Browser-Lokalisierungserkennung <DocChip chip='since' label='25.12' /> {#browser-locale-detection}

Wenn die automatische Erkennung aktiviert ist, liest webforJ beim Start die bevorzugten Sprachen des Browsers und setzt die App-Lokalisierung auf die beste Übereinstimmung aus den konfigurierten unterstützten Lokalisierungen. Wenn keine Übereinstimmung gefunden wird, wird die erste unterstützte Lokalisierung als Standard verwendet.

Aktivieren Sie die automatische Erkennung, indem Sie `webforj.i18n.auto-detect` auf `true` setzen und `webforj.i18n.supported-locales` mit den Lokalisierungen, die Ihre App unterstützt, konfigurieren. Siehe die [Konfiguration](/docs/configuration/properties) Sektion, um zu erfahren, wie Sie Eigenschaften für verschiedene Umgebungen festlegen.

:::info Erfordert unterstützte Lokalisierungen
Die automatische Erkennung erfordert, dass `supported-locales` konfiguriert ist. Wenn die Liste leer ist, hat die automatische Erkennung keine Wirkung und die App verwendet die Standardlokalisierung aus `webforj.locale`.
:::

## Die `LocaleObserver` Schnittstelle {#the-localeobserver-interface}

Komponenten, die ihren Inhalt bei Änderungen der Lokalisierung aktualisieren müssen, sollten die Schnittstelle `LocaleObserver` implementieren. webforJ registriert und deregistriert automatisch Beobachter, während Komponenten erstellt und gelöscht werden.

```java title="LocaleObserver.java"
@FunctionalInterface
public interface LocaleObserver {
    void onLocaleChange(LocaleEvent event);
}
```

Wenn die Lokalisierung geändert wird, wird `onLocaleChange` mit der neuen Lokalisierung aufgerufen. Aktualisieren Sie in dieser Methode alle lokalisierungsabhängigen Texte oder Formatierungen:

```java title="MainLayout.java"
@Route
public class MainLayout extends Composite<AppLayout>
    implements HasTranslation, LocaleObserver {

  private final AppLayout self = getBoundComponent();
  private AppNavItem inboxItem;
  private AppNavItem outboxItem;

  public MainLayout() {
    inboxItem = new AppNavItem(t("menu.inbox"), InboxView.class, TablerIcon.create("inbox"));
    outboxItem = new AppNavItem(t("menu.outbox"), OutboxView.class, TablerIcon.create("send-2"));

    AppNav appNav = new AppNav();
    appNav.addItem(inboxItem);
    appNav.addItem(outboxItem);

    self.addToDrawer(appNav);
  }

  @Override
  public void onLocaleChange(LocaleEvent event) {
    inboxItem.setText(t("menu.inbox"));
    outboxItem.setText(t("menu.outbox"));
  }
}
```

:::tip Eingebaute Übersetzungsunterstützung
Ab Version 25.12 bietet webforJ ein integriertes [Übersetzungssystem](/docs/advanced/i18n-localization), das Ressourcenbündel, benutzerdefinierte Resolver, automatische Browser-Lokalisierungserkennung und lokalisierungsbewusste Datenbindung unterstützt.
:::

### `LocaleEvent` {#localeevent}

Das `LocaleEvent`, das an `onLocaleChange()` übergeben wird, bietet die neue Lokalisierung und die Komponente, die das Ereignis erhalten hat:

| Methode | Gibt zurück | Beschreibung |
|---------|-------------|--------------|
| `getLocale()` | `Locale` | Die neue Lokalisierung, die gesetzt wurde |
| `getSource()` | `Object` | Die Komponente, die das Ereignis empfangen hat |

## Manuelle Lokalisierungsupdates {#manual-locale-updates}

Nicht alles reagiert automatisch auf Änderungen der Lokalisierung. Einige Komponenten, wie [Maskierte Felder](/docs/components/fields/masked/overview), lesen `App.getLocale()` einmal während der Erstellung, um lokalisierungsabhängige Formatierungen zu konfigurieren, implementieren jedoch nicht `LocaleObserver`. Wenn sich die Lokalisierung zur Laufzeit ändert, müssen diese explizit in Ihrem `onLocaleChange()`-Handler aktualisiert werden:

```java
public class OrderForm extends Composite<FlexLayout> implements LocaleObserver {
  private MaskedDateField dateField = new MaskedDateField("Datum");
  private MaskedTimeField timeField = new MaskedTimeField("Uhrzeit");

  @Override
  public void onLocaleChange(LocaleEvent event) {
    Locale newLocale = event.getLocale();
    dateField.setLocale(newLocale);
    timeField.setLocale(newLocale);
  }
}
```

:::tip Datenbindung
`BindingContext` unterstützt lokalisierungsabhängige Validierungs- und Transformationsnachrichten. Siehe [dynamische Validierungsnachrichten](/docs/data-binding/validation/validators#dynamic-validation-messages) und [lokalisierungsbewusste Jakarta-Validierung](/docs/data-binding/validation/jakarta-validation#locale-aware-validation-messages).
:::
