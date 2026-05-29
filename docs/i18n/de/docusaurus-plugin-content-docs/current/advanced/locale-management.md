---
sidebar_position: 11
title: Locale Management
sidebar_class_name: new-content
_i18n_hash: d3dcb4b1ded50923232cb33225364239
---
# Lokalisierungsmanagement <DocChip chip='since' label='25.10' />

webforJ bietet integrierte Unterstützung zur Verwaltung der App-Lokalisierung. Die Lokalisierung bestimmt, welche Sprache und regionalen Formatierungen in der gesamten App verwendet werden. Komponenten können auf Änderungen der Sprache reagieren, indem sie das `LocaleObserver`-Interface implementieren, wodurch die Benutzeroberfläche sofort aktualisiert wird, wenn der Benutzer die Sprache wechselt.

## Standardlokalisierung festlegen {#setting-the-default-locale}

Die App-Lokalisierung kann über die Eigenschaft `webforj.locale` konfiguriert werden. Diese legt die Lokalisierung fest, die die App beim Start verwendet und beeinflusst alle lokalisierungssensiblen Formatierungen und Texte. Wenn `webforj.locale` nicht konfiguriert ist, fällt die App auf die Standardlokalisierung der JVM des Servers zurück. Sie können die aktuelle Lokalisierung jederzeit mit `App.getLocale()` abfragen.

Siehe den Abschnitt [Konfiguration](/docs/configuration/properties), um zu erfahren, wie Sie Eigenschaften für verschiedene Umgebungen festlegen können.

## Lokalisierung zur Laufzeit ändern {#changing-the-locale}

Um die Lokalisierung zur Laufzeit zu ändern, rufen Sie `App.setLocale()` auf. Dies aktualisiert die Lokalisierung für die gesamte App und benachrichtigt alle Komponenten, die das `LocaleObserver` implementieren, wodurch die Benutzeroberfläche ohne ein Neuladen der Seite aktualisiert wird.

```java
App.setLocale(Locale.GERMAN);
App.setLocale(Locale.forLanguageTag("fr"));
```

## Browserlokalisierungserkennung <DocChip chip='since' label='25.12' /> {#browser-locale-detection}

Wenn die automatische Erkennung aktiviert ist, liest webforJ die bevorzugten Sprachen des Browsers beim Start und legt die App-Lokalisierung auf das am besten passende von den konfigurierten unterstützten Lokalisierungen fest. Wenn kein passendes gefunden wird, wird die erste unterstützte Lokalisierung als Standard verwendet.

Aktivieren Sie die automatische Erkennung, indem Sie `webforj.i18n.auto-detect` auf `true` setzen und `webforj.i18n.supported-locales` mit den Lokalisierungen konfigurieren, die Ihre App unterstützt. Siehe den Abschnitt [Konfiguration](/docs/configuration/properties), um zu erfahren, wie Sie Eigenschaften für verschiedene Umgebungen festlegen können.

:::info Erfordert unterstützte Lokalisierungen
Die automatische Erkennung erfordert, dass `supported-locales` konfiguriert ist. Wenn die Liste leer ist, hat die automatische Erkennung keine Wirkung und die App verwendet die Standardlokalisierung aus `webforj.locale`.
:::

## Das `LocaleObserver`-Interface {#the-localeobserver-interface}

Komponenten, die ihren Inhalt aktualisieren müssen, wenn sich die Lokalisierung ändert, sollten das `LocaleObserver`-Interface implementieren. webforJ registriert und deregistriert Beobachter automatisch, während Komponenten erstellt und zerstört werden.

```java title="LocaleObserver.java"
@FunctionalInterface
public interface LocaleObserver {
  void onLocaleChange(LocaleEvent event);
}
```

Wenn sich die Lokalisierung ändert, wird `onLocaleChange` mit der neuen Lokalisierung aufgerufen. In dieser Methode aktualisieren Sie alle lokalisierungssensiblen Texte oder Formatierungen:

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

:::tip Integrierte Übersetzungsunterstützung
Ab Version 25.12 bietet webforJ ein integriertes [Übersetzungssystem](/docs/advanced/i18n-localization), das Ressourcen-Bundles, benutzerdefinierte Resolver, automatische Browserlokalisierungserkennung und lokalisierungsbewusstes Datenbinding unterstützt.
:::

### `LocaleEvent` {#localeevent}

Das an `onLocaleChange()` übergebene `LocaleEvent` enthält die neue Lokalisierung und die Komponente, die das Ereignis empfangen hat:

| Methode | Gibt zurück | Beschreibung |
|--------|---------|-------------|
| `getLocale()` | `Locale` | Die neue Lokalisierung, die festgelegt wurde |
| `getSource()` | `Object` | Die Komponente, die das Ereignis empfangen hat |

## Manuelle Lokalisierungsaktualisierungen {#manual-locale-updates}

Nicht alles reagiert automatisch auf Lokalisierungsänderungen. Einige Komponenten, wie [Maskierte Felder](/docs/components/fields/masked/overview), lesen `App.getLocale()` einmal während der Erstellung, um lokalisierungssensible Formatierungen zu konfigurieren, implementieren jedoch nicht `LocaleObserver`. Wenn sich die Lokalisierung zur Laufzeit ändert, müssen diese innerhalb Ihres `onLocaleChange()`-Handlers explizit aktualisiert werden:

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
`BindingContext` unterstützt lokalisierungsbewusste Validierungs- und Transformationsnachrichten. Siehe [dynamische Validierungsnachrichten](/docs/data-binding/validation/validators#dynamic-validation-messages) und [lokalisierungsbewusste Jakarta Validierung](/docs/data-binding/validation/jakarta-validation#locale-aware-validation-messages).
:::
