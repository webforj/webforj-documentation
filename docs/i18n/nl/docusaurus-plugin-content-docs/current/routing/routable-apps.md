---
sidebar_position: 2
title: Routable Apps
_i18n_hash: 6d09e8327e3391cedd4e8059d9390d09
---
Routing in webforJ is een optioneel hulpmiddel. Ontwikkelaars kunnen kiezen tussen de webforJ routingoplossing of een traditioneel model met `Frame` manipulatie en zonder deep linking. Om routing in te schakelen, moet de **`@Routify`** annotatie worden toegepast op het niveau van een klasse die `App` implementeert. Hierdoor krijgt webforJ de bevoegdheid om de browsergeschiedenis te beheren, te reageren op navigatie-evenementen en de componenten van de app weer te geven op basis van de URL.

:::info
Om meer te leren over het bouwen van UI's met behulp van frames, ingebouwde en aangepaste componenten, bezoek de sectie [Building UIs](../building-ui/basics).
:::

## Doel van de `@Routify` Annotatie {#purpose-of-the-routify-annotation}

**`@Routify`** stelt het framework in staat om automatisch routes te registreren, de zichtbaarheid van frames te beheren en routinggedragingen zoals foutopsporing en frame-initialisatie te definiëren, waardoor dynamische, flexibele routing in de app mogelijk wordt.

## Gebruik van `@Routify` {#usage-of-routify}

De **`@Routify`** annotatie wordt toegepast op het klasseniveau van de hoofdapplicatieklasse. Het specificeert de set van pakketten die gescand moeten worden voor routes en beheert andere routinggerelateerde instellingen zoals frame-initialisatie en zichtbaarheid.

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

:::tip Standaardconfiguraties van Routify
De **`@Routify`** annotatie komt met redelijke standaardconfiguraties. Het gaat ervan uit dat het huidige pakket waarin de app is gedefinieerd, samen met al zijn subpakketten, moet worden gescand voor routes. Daarnaast gaat het ervan uit dat de app standaard maar één frame beheert. Als je app deze structuur volgt, is er geen behoefte om aangepaste configuraties aan de annotatie te geven.
:::

## Belangrijkste elementen van `@Routify` {#key-elements-of-routify}

### 1. **`packages`** {#1-packages}

Het `packages` element definieert welke pakketten moeten worden gescand voor routedefinities. Het stelt automatische ontdekking van routes mogelijk zonder handmatige registratie, waardoor het proces van het uitbreiden van het routing-systeem van de app wordt vereenvoudigd.

```java
@Routify(packages = {"com.myapp.views"})
```

Als er geen pakketten zijn opgegeven, wordt het standaardpakket van de app gebruikt.

### 2. **`defaultFrameName`** {#2-defaultframename}

Dit element specificeert de naam van het standaardframe dat de app initialiseert. Frames vertegenwoordigen top-level UI-containers, en deze instelling controleert hoe het eerste frame wordt genoemd en beheerd.

```java
@Routify(defaultFrameName = "MainFrame")
```

Standaard, als dit niet expliciet is opgegeven, wordt de waarde ingesteld op `Routify.DEFAULT_FRAME_NAME`.

### 3. **`initializeFrame`** {#3-initializeframe}

De `initializeFrame` vlag bepaalt of het framework automatisch het eerste frame moet initialiseren wanneer de app start. Dit op `true` instellen vereenvoudigt de initiële frame-instelling.

```java
@Routify(initializeFrame = true)
```

### 4. **`manageFramesVisibility`** {#4-manageframesvisibility}

Dit element controleert of het framework automatisch de zichtbaarheid van frames tijdens navigatie moet wisselen. Wanneer dit is ingeschakeld, toont de bijbehorende route automatisch het overeenkomstige frame terwijl andere worden verborgen, wat zorgt voor een schone en gefocuste UI. Deze instelling is alleen relevant wanneer je app meerdere frames beheert.

```java
@Routify(manageFramesVisibility = true)
```

### 5. **`debug`** {#5-debug}

De `debug` vlag schakelt de routingdebug-modus in of uit. Wanneer ingeschakeld, worden routinginformatie en -acties gelogd naar de console voor gemakkelijker foutopsporing tijdens ontwikkeling.

```java
@Routify(debug = true)
```

:::info Router Debugmodus en webforJ Debugmodus  
Als de routerdebugmodus is ingesteld op `true`, maar de webforJ-debugmodus is ingesteld op `false`, worden er geen foutopsporingsinformatie in de console weergegeven.  
:::
