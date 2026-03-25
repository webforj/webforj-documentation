---
sidebar_position: 11
title: Locale Management
sidebar_class_name: new-content
_i18n_hash: d3dcb4b1ded50923232cb33225364239
---
# Locatiebeheer <DocChip chip='since' label='25.10' />

webforJ biedt ingebouwde ondersteuning voor het beheren van de app-locatie. De locatie bepaalt welke taal en regionale opmaak in de app worden gebruikt. Componenten kunnen reageren op wijzigingen in de locatie via de `LocaleObserver`-interface, waardoor de gebruikersinterface onmiddellijk wordt bijgewerkt wanneer de gebruiker van taal wisselt.

## De standaardlocatie instellen {#setting-the-default-locale}

De app-locatie kan worden geconfigureerd met de `webforj.locale`-eigenschap. Dit stelt de locatie in die de app vanaf de start gebruikt, wat invloed heeft op alle locatiegevoelige opmaak en tekst. Wanneer `webforj.locale` niet is geconfigureerd, valt de app terug op de standaardlocatie van de server-JVM. Je kunt de huidige locatie op elk moment lezen met `App.getLocale()`.

Zie de [Configuratie](/docs/configuration/properties) sectie om te leren hoe je eigenschappen voor verschillende omgevingen instelt.

## De locatie wijzigen {#changing-the-locale}

Om de locatie tijdens runtime te wijzigen, roep je `App.setLocale()` aan. Dit werkt de locatie voor de hele app bij en meldt alle componenten die `LocaleObserver` implementeren, waardoor de gebruikersinterface kan worden bijgewerkt zonder een pagina opnieuw te laden.

```java
App.setLocale(Locale.GERMAN);
App.setLocale(Locale.forLanguageTag("fr"));
```

## Browser-locatie-detectie <DocChip chip='since' label='25.12' /> {#browser-locale-detection}

Wanneer autodetectie is ingeschakeld, leest webforJ de voorkeurs talen van de browser bij de start en stelt de app-locatie in op de beste overeenkomst van de geconfigureerde ondersteunde locaties. Als er geen overeenkomst wordt gevonden, wordt de eerste ondersteunde locatie als standaard gebruikt.

Schakel autodetectie in door `webforj.i18n.auto-detect` op `true` in te stellen en `webforj.i18n.supported-locales` te configureren met de locaties die je app ondersteunt. Zie de [Configuratie](/docs/configuration/properties) sectie om te leren hoe je eigenschappen voor verschillende omgevingen instelt.

:::info Vereist ondersteunde locaties
Autodetectie vereist dat `supported-locales` is geconfigureerd. Als de lijst leeg is, heeft autodetectie geen effect en gebruikt de app de standaardlocatie van `webforj.locale`.
:::

## De `LocaleObserver`-interface {#the-localeobserver-interface}

Componenten die hun inhoud moeten bijwerken wanneer de locatie verandert, moeten de `LocaleObserver`-interface implementeren. webforJ registreert automatisch waarnemers wanneer componenten worden gemaakt en verwijderd.

```java title="LocaleObserver.java"
@FunctionalInterface
public interface LocaleObserver {
  void onLocaleChange(LocaleEvent event);
}
```

Wanneer de locatie verandert, wordt `onLocaleChange` aangeroepen met de nieuwe locatie. Binnen deze methode moeten alle locatiegevoelige teksten of opmaak worden bijgewerkt:

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

:::tip Ingebouwde vertalingsondersteuning
Vanaf versie 25.12 biedt webforJ een ingebouwd [vertalingssysteem](/docs/advanced/i18n-localization) dat ondersteuning biedt voor resourcebundels, aangepaste resolvers, automatische browser-locatie-detectie en locatiebewuste databinding.
:::

### `LocaleEvent` {#localeevent}

De `LocaleEvent` die naar `onLocaleChange()` wordt gestuurd, biedt de nieuwe locatie en de component die het evenement heeft ontvangen:

| Methode | Retourneert | Beschrijving |
|--------|---------|-------------|
| `getLocale()` | `Locale` | De nieuwe locatie die is ingesteld |
| `getSource()` | `Object` | De component die het evenement heeft ontvangen |

## Handmatige locatie-updates {#manual-locale-updates}

Niet alles reageert automatisch op wijzigingen in de locatie. Sommige componenten, zoals [Masked Fields](/docs/components/fields/masked/overview), lezen `App.getLocale()` eenmaal tijdens de creatie om locatiegevoelige opmaak te configureren, maar implementeren geen `LocaleObserver`. Wanneer de locatie tijdens runtime verandert, moeten deze expliciet worden bijgewerkt binnen je `onLocaleChange()` handler:

```java
public class OrderForm extends Composite<FlexLayout> implements LocaleObserver {
  private MaskedDateField dateField = new MaskedDateField("Datum");
  private MaskedTimeField timeField = new MaskedTimeField("Tijd");

  @Override
  public void onLocaleChange(LocaleEvent event) {
    Locale newLocale = event.getLocale();
    dateField.setLocale(newLocale);
    timeField.setLocale(newLocale);
  }
}
```

:::tip Gegevensbinding
`BindingContext` ondersteunt locatiebewuste validatie en transformatieberichten. Zie [dynamische validatieberichten](/docs/data-binding/validation/validators#dynamic-validation-messages) en [locatiebewuste Jakarta-validatie](/docs/data-binding/validation/jakarta-validation#locale-aware-validation-messages).
:::
