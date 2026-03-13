---
sidebar_position: 11
title: Locale Management
sidebar_class_name: new-content
_i18n_hash: cfcad51aaedd77b781334fc048b0a4f1
---
# Localebeheer <DocChip chip='since' label='25.10' />

webforJ biedt ingebouwde ondersteuning voor het beheren van de app-locale. De locale bepaalt welke taal en regionale opmaak door de app wordt gebruikt. Componenten kunnen reageren op wijzigingen in de locale via de `LocaleObserver` interface, waardoor de gebruikersinterface onmiddellijk kan worden bijgewerkt wanneer de gebruiker van taal wisselt.

## De standaardlocale instellen {#setting-the-default-locale}

De app-locale kan worden geconfigureerd met de `webforj.locale` property. Dit stelt de locale in die de app vanaf de opstart gebruikt, wat invloed heeft op alle locale-gevoelige opmaak en tekst. Wanneer `webforj.locale` niet is geconfigureerd, valt de app terug op de standaard locale van de JVM van de server. Je kunt de huidige locale op elk moment lezen met `App.getLocale()`.

Zie de [Configuratie](/docs/configuration/properties) sectie voor informatie over het instellen van eigenschappen voor verschillende omgevingen.

## De locale wijzigen {#changing-the-locale}

Om de locale tijdens runtime te wijzigen, roep je `App.setLocale()` aan. Dit werkt de locale voor de gehele app bij en meldt alle componenten die `LocaleObserver` implementeren, waardoor de gebruikersinterface kan worden bijgewerkt zonder een pagina-herlaad.

```java
App.setLocale(Locale.GERMAN);
App.setLocale(Locale.forLanguageTag("fr"));
```

## Browserlocale-detectie <DocChip chip='since' label='25.12' /> {#browser-locale-detection}

Wanneer autodetectie is ingeschakeld, leest webforJ de voorkeurstalen van de browser bij de opstart en stelt de app-locale in op de beste overeenkomst van de geconfigureerde ondersteunde locales. Als er geen overeenkomst wordt gevonden, wordt de eerste ondersteunde locale als standaard gebruikt.

Schakel autodetectie in door `webforj.i18n.auto-detect` in te stellen op `true` en `webforj.i18n.supported-locales` te configureren met de locales die je app ondersteunt. Zie de [Configuratie](/docs/configuration/properties) sectie voor informatie over het instellen van eigenschappen voor verschillende omgevingen.

:::info Vereist ondersteunde locales
Autodetectie vereist dat `supported-locales` is geconfigureerd. Als de lijst leeg is, heeft autodetectie geen effect en gebruikt de app de standaardlocale van `webforj.locale`.
:::

## De `LocaleObserver` interface {#the-localeobserver-interface}

Componenten die hun inhoud moeten bijwerken wanneer de locale verandert, dienen de `LocaleObserver` interface te implementeren. webforJ registreert en deregistreert automatisch waarnemers wanneer componenten worden gemaakt en vernietigd.

```java title="LocaleObserver.java"
@FunctionalInterface
public interface LocaleObserver {
    void onLocaleChange(LocaleEvent event);
}
```

Wanneer de locale verandert, wordt `onLocaleChange` aangeroepen met de nieuwe locale. Binnen deze methode werk je alle locale-gevoelige tekst of opmaak bij:

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

:::tip Ingebouwde vertaalondersteuning
Vanaf versie 25.12 biedt webforJ een ingebouwd [vertaalsysteem](/docs/advanced/i18n-localization) dat resourcebundels, aangepaste resolvers, automatische browserlocale-detectie en locale-bewuste databinding ondersteunt.
:::

### `LocaleEvent` {#localeevent}

De `LocaleEvent` die aan `onLocaleChange()` wordt doorgegeven, biedt de nieuwe locale en de component die de gebeurtenis heeft ontvangen:

| Methode | Retourneert | Beschrijving |
|--------|---------|-------------|
| `getLocale()` | `Locale` | De nieuwe locale die is ingesteld |
| `getSource()` | `Object` | De component die de gebeurtenis heeft ontvangen |

## Handmatige locale-updates {#manual-locale-updates}

Niet alles reageert automatisch op wijzigingen in de locale. Sommige componenten, zoals [Masked Fields](/docs/components/fields/masked/overview), lezen `App.getLocale()` één keer tijdens de creatie om locale-gevoelige opmaak te configureren, maar implementeren geen `LocaleObserver`. Wanneer de locale tijdens runtime verandert, moeten deze expliciet worden bijgewerkt binnen je `onLocaleChange()` handler:

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
`BindingContext` ondersteunt locale-bewuste validatie en transformatieberichten. Zie [dynamische validatiemeldingen](/docs/data-binding/validation/validators#dynamic-validation-messages) en [locale-bewuste Jakarta Validatie](/docs/data-binding/validation/jakarta-validation#locale-aware-validation-messages).
:::
