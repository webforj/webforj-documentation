---
sidebar_position: 2
title: Routable Apps
_i18n_hash: edec1086b0723febd831816f8d1fa76a
---
Routing in webforJ is een optioneel hulpmiddel. Ontwikkelaars kunnen kiezen tussen de webforJ-routingoplossing of een traditioneel model met `Frame`-manipulatie en zonder deep linking. Om routing in te schakelen, moet de **`@Routify`** annotatie worden toegepast op het niveau van een klasse die `App` implementeert. Dit geeft webforJ de bevoegdheid om de geschiedenis van de browser te beheren, te reageren op navigatie-evenementen en de componenten van de app weer te geven op basis van de URL.

:::info
Voor meer informatie over het bouwen van gebruikersinterfaces met frames, ingebouwde en aangepaste componenten, bezoek [Building UIs](../building-ui/overview).
:::

## Doel van de `@Routify` Annotatie {#purpose-of-the-routify-annotation}

**`@Routify`** stelt het framework in staat om automatisch routes te registreren, de zichtbaarheid van frames te beheren en routinggedragingen zoals foutopsporing en frame-initialisatie te definiëren, wat zorgt voor dynamische, flexibele routing in de app.

## Gebruik van `@Routify` {#usage-of-routify}

De **`@Routify`** annotatie wordt toegepast op het klassenniveau van de hoofdapp-klasse. Het specificeert de set van pakketten die gescand moeten worden op routes en beheert andere routinggerelateerde instellingen zoals frame-initialisatie en zichtbaarheidbeheer.

Hier is een basisvoorbeeld:

```java
@Routify(
  packages = {"com.myapp.views"},
  defaultFrameName = "MainFrame",
  initializeFrame = true,
  manageFramesVisibility = false,
  debug = true
)
public class MyApp extends App {

  @Override
  public void run() {
    // Applicatielogica gaat hier
  }
}
```

:::tip Routify Standaardconfiguraties
De **`@Routify`** annotatie wordt geleverd met redelijke standaardconfiguraties. Het gaat ervan uit dat het huidige pakket waarin de app is gedefinieerd, samen met al zijn subpakketten, moet worden gescand op routes. Daarnaast gaat het ervan uit dat de app standaard slechts één frame beheert. Als uw app deze structuur volgt, is er geen behoefte om aangepaste configuraties aan de annotatie te geven.
:::

## Sleutelelementen van `@Routify` {#key-elements-of-routify}

### 1. **`packages`** {#1-packages}

Het `packages`-element definieert welke pakketten moeten worden gescand op route-definities. Het maakt automatische ontdekking van routes mogelijk zonder handmatige registratie, waardoor het proces van het uitbreiden van het apps-routing systeem wordt vereenvoudigd.

```java
@Routify(packages = {"com.myapp.views"})
```

Als er geen pakketten zijn gespecificeerd, wordt het standaardpakket van de app gebruikt.

### 2. **`defaultFrameName`** {#2-defaultframename}

Dit element specificeert de naam van het standaardframe dat de app initialiseert. Frames vertegenwoordigen de top-level UI-containers, en deze instelling beheert hoe het eerste frame wordt genoemd en beheerd.

```java
@Routify(defaultFrameName = "MainFrame")
```

Standaard, als niet expliciet opgegeven, wordt de waarde ingesteld op `Routify.DEFAULT_FRAME_NAME`.

### 3. **`initializeFrame`** {#3-initializeframe}

De `initializeFrame`-vlag bepaalt of het framework het eerste frame automatisch moet initialiseren wanneer de app start. Het instellen van deze waarde op `true` vereenvoudigt de initiële frame-instelling.

```java
@Routify(initializeFrame = true)
```

### 4. **`manageFramesVisibility`** {#4-manageframesvisibility}

Dit element beheert of het framework automatisch de zichtbaarheid van frames moet wisselen tijdens navigatie. Wanneer ingeschakeld, toont de bijbehorende route automatisch het corresponderende frame terwijl andere worden verborgen, wat zorgt voor een schone en gefocuste UI. Deze instelling is alleen relevant wanneer uw app meerdere frames beheert.

```java
@Routify(manageFramesVisibility = true)
```

### 5. **`debug`** {#5-debug}

De `debug`-vlag schakelt de routingsfoutopsporingsmodus in of uit. Wanneer ingeschakeld, worden routingegevens en -acties naar de console gelogd voor gemakkelijker debuggen tijdens de ontwikkeling.

```java
@Routify(debug = true)
```

:::info Router Debug Mode en webforJ Debug Mode  
Als de routerfoutopsporingsmodus is ingesteld op `true`, maar de webforJ-foutopsporingsmodus is ingesteld op `false`, wordt er geen foutopsporingsinformatie weergegeven in de console.  
:::
