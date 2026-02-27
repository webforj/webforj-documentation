---
sidebar_position: 11
title: Locale Management
sidebar_class_name: new-content
_i18n_hash: 066d555fcf006756c6ae0e542d409d77
---
# Lokalisierungsmanagement <DocChip chip='since' label='25.10' />

webforJ bietet integrierte Unterstützung für die Verwaltung der App-Lokalisierung. Die Lokalisierung bestimmt, welche Sprache und regionale Formatierung in der gesamten App verwendet wird. Komponenten können auf Änderungen der Lokalisierung durch das `LocaleObserver`-Interface reagieren, sodass die Benutzeroberfläche sofort aktualisiert wird, wenn der Benutzer die Sprache wechselt.

## Standardlokalisierung festlegen {#setting-the-default-locale}

Die App-Lokalisierung kann über die Eigenschaft `webforj.locale` konfiguriert werden. Dies legt die Lokalisierung fest, die die App beim Start verwendet, und wirkt sich auf alle lokalisierungssensiblen Formate und Texte aus. Wenn `webforj.locale` nicht konfiguriert ist, fällt die App auf die Standardlokalisierung der JVM des Servers zurück. Sie können die aktuelle Lokalisierung jederzeit mit `App.getLocale()` abfragen.

Siehe den Abschnitt [Konfiguration](/docs/configuration/properties), um zu erfahren, wie Sie Eigenschaften für verschiedene Umgebungen festlegen.

## Lokalisierung ändern {#changing-the-locale}

Um die Lokalisierung zur Laufzeit zu ändern, rufen Sie `App.setLocale()` auf. Dies aktualisiert die Lokalisierung für die gesamte App und benachrichtigt alle Komponenten, die `LocaleObserver` implementieren, sodass die Benutzeroberfläche ohne Neuladen der Seite aktualisiert werden kann.

```java
App.setLocale(Locale.GERMAN);
App.setLocale(Locale.forLanguageTag("fr"));
```

## Browser-Lokalisierungserkennung <DocChip chip='since' label='25.12' /> {#browser-locale-detection}

Wenn die automatische Erkennung aktiviert ist, liest webforJ beim Start die bevorzugten Sprachen des Browsers und setzt die App-Lokalisierung auf das am besten geeignete aus den konfigurierten unterstützten Lokalisierungen. Wenn keine Übereinstimmung gefunden wird, wird die erste unterstützte Lokalisierung als Standard verwendet.

Aktivieren Sie die automatische Erkennung, indem Sie `webforj.i18n.auto-detect` auf `true` setzen und `webforj.i18n.supported-locales` mit den Lokalisierungen konfigurieren, die Ihre App unterstützt. Siehe den Abschnitt [Konfiguration](/docs/configuration/properties), um zu erfahren, wie Sie Eigenschaften für verschiedene Umgebungen festlegen.

:::info Erfordert unterstützte Lokalisierungen
Die automatische Erkennung erfordert, dass `supported-locales` konfiguriert ist. Wenn die Liste leer ist, hat die automatische Erkennung keine Wirkung, und die App verwendet die Standardlokalisierung aus `webforj.locale`.
:::

## Das `LocaleObserver`-Interface {#the-localeobserver-interface}

Komponenten, die ihren Inhalt bei Änderungen der Lokalisierung aktualisieren müssen, sollten das `LocaleObserver`-Interface implementieren. webforJ registriert und deregistriert automatisch Beobachter, wenn Komponenten erstellt und zerstört werden.

```java title="LocaleObserver.java"
@FunctionalInterface
public interface LocaleObserver {
    void onLocaleChange(LocaleEvent event);
}
```

Wenn sich die Lokalisierung ändert, wird `onLocaleChange` mit der neuen Lokalisierung aufgerufen. Aktualisieren Sie innerhalb dieser Methode alle lokalisierungssensiblen Texte oder Formate:

```java title="MainLayout.java"
@Route
public class MainLayout extends Composite<AppLayout>
    implements HasTranslation, LocaleObserver {

  private AppLayout self = getBoundComponent();
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
Ab Version 25.12 bietet webforJ ein integriertes [Übersetzungssystem](/docs/advanced/i18n-localization), das Ressourcenbündel, benutzerdefinierte Resolver, automatische Browser-Lokalisierungserkennung und lokalisierungssensibles Datenbindung unterstützt.
:::

### `LocaleEvent` {#localeevent}

Das `LocaleEvent`, das an `onLocaleChange()` übergeben wird, liefert die neue Lokalisierung und die Komponente, die das Ereignis erhalten hat:

| Methode | Rückgabetyp | Beschreibung |
|---------|-------------|--------------|
| `getLocale()` | `Locale` | Die neue Lokalisierung, die gesetzt wurde |
| `getSource()` | `Object` | Die Komponente, die das Ereignis erhalten hat |

## Manuelle Lokalisierungupdates {#manual-locale-updates}

Nicht alles reagiert automatisch auf Änderungen der Lokalisierung. Einige Komponenten, wie z. B. [Maskierte Felder](/docs/components/fields/masked/overview), lesen `App.getLocale()` einmal während der Erstellung, um lokalisierungssensible Formate zu konfigurieren, implementieren jedoch nicht `LocaleObserver`. Wenn sich die Lokalisierung zur Laufzeit ändert, müssen diese explizit innerhalb Ihres `onLocaleChange()`-Handlers aktualisiert werden:

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
`BindingContext` unterstützt lokalisierungssensible Validierungs- und Transformatierungsnachrichten. Siehe [dynamische Validierungsnachrichten](/docs/data-binding/validation/validators#dynamic-validation-messages) und [lokalisierungssensible Jakarta-Validierung](/docs/data-binding/validation/jakarta-validation#locale-aware-validation-messages).
:::
