---
sidebar_position: 11
title: Locale Management
sidebar_class_name: new-content
_i18n_hash: 066d555fcf006756c6ae0e542d409d77
---
# Locatiebeheer <DocChip chip='since' label='25.10' />

webforJ biedt ingebouwde ondersteuning voor het beheren van de app-locatie. De locatie bepaalt welke taal en regionale opmaak in de hele app worden gebruikt. Componenten kunnen reageren op wijzigingen in de locatie via de interface `LocaleObserver`, waardoor de UI onmiddellijk wordt bijgewerkt wanneer de gebruiker van taal wisselt.

## Standaardlocatie instellen {#setting-the-default-locale}

De app-locatie kan worden geconfigureerd met behulp van de eigenschap `webforj.locale`. Dit stelt de locatie in die de app vanaf de opstart gebruikt, wat invloed heeft op alle locatiegevoelige opmaak en tekst. Wanneer `webforj.locale` niet is geconfigureerd, valt de app terug op de standaardlocatie van de server-JVM. Je kunt de huidige locatie op elk moment lezen met `App.getLocale()`.

Zie de [Configuratie](/docs/configuration/properties) sectie om te leren hoe je eigenschappen voor verschillende omgevingen instelt.

## De locatie wijzigen {#changing-the-locale}

Om de locatie tijdens runtime te wijzigen, roep je `App.setLocale()` aan. Dit werkt de locatie bij voor de hele app en stelt alle componenten die `LocaleObserver` implementeren op de hoogte, waardoor de UI zonder een pagina-herlaad wordt bijgewerkt.

```java
App.setLocale(Locale.GERMAN);
App.setLocale(Locale.forLanguageTag("fr"));
```

## Browserlocatie-detectie <DocChip chip='since' label='25.12' /> {#browser-locale-detection}

Wanneer auto-detectie is ingeschakeld, leest webforJ de voorkeurs-talen van de browser bij de opstart en stelt de app-locatie in op de beste match uit de geconfigureerde ondersteunde locaties. Als er geen match wordt gevonden, wordt de eerste ondersteunde locatie als standaard gebruikt.

Schakel auto-detectie in door `webforj.i18n.auto-detect` in te stellen op `true` en `webforj.i18n.supported-locales` te configureren met de locaties die jouw app ondersteunt. Zie de [Configuratie](/docs/configuration/properties) sectie om te leren hoe je eigenschappen voor verschillende omgevingen instelt.

:::info Vereist ondersteunde locaties
Auto-detectie vereist dat `supported-locales` is geconfigureerd. Als de lijst leeg is, heeft auto-detectie geen effect en gebruikt de app de standaardlocatie van `webforj.locale`.
:::

## De `LocaleObserver` interface {#the-localeobserver-interface}

Componenten die hun inhoud moeten bijwerken wanneer de locatie verandert, moeten de interface `LocaleObserver` implementeren. webforJ registreert en deregistreert automatisch waarnemers wanneer componenten worden gemaakt en vernietigd.

```java title="LocaleObserver.java"
@FunctionalInterface
public interface LocaleObserver {
    void onLocaleChange(LocaleEvent event);
}
```

Wanneer de locatie verandert, wordt `onLocaleChange` aangeroepen met de nieuwe locatie. Binnen deze methode werk je elke locatiegevoelige tekst of opmaak bij:

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

:::tip Ingebouwde vertaalondersteuning
Vanaf versie 25.12 biedt webforJ een ingebouwd [vertalingssysteem](/docs/advanced/i18n-localization) dat resource-bundels, aangepaste resolvers, automatische browserlocatie-detectie en locatiebewuste databinding ondersteunt.
:::

### `LocaleEvent` {#localeevent}

Het `LocaleEvent` dat naar `onLocaleChange()` wordt verzonden, biedt de nieuwe locatie en de component die het evenement heeft ontvangen:

| Methode | Retourneert | Beschrijving |
|--------|---------|-------------|
| `getLocale()` | `Locale` | De nieuwe locatie die is ingesteld |
| `getSource()` | `Object` | De component die het evenement heeft ontvangen |

## Handmatige locatie-updates {#manual-locale-updates}

Niet alles reageert automatisch op locatieveranderingen. Sommige componenten, zoals [Gemaskerde Velden](/docs/components/fields/masked/overview), lezen `App.getLocale()` eenmaal tijdens de creatie om locatiegevoelige opmaak te configureren, maar implementeren `LocaleObserver` niet. Wanneer de locatie tijdens runtime verandert, moeten deze expliciet worden bijgewerkt binnen je `onLocaleChange()` handler:

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

:::tip Databinding
`BindingContext` ondersteunt locatiebewuste validatie en transformatie berichten. Zie [dynamische validatieberichten](/docs/data-binding/validation/validators#dynamic-validation-messages) en [locatiebewuste Jakarta Validatie](/docs/data-binding/validation/jakarta-validation#locale-aware-validation-messages).
:::
