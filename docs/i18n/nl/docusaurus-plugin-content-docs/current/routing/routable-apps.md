---
sidebar_position: 2
title: Routable Apps
description: >-
  Enable webforJ routing with the @Routify annotation to scan packages, manage
  frames, and control browser history.
_i18n_hash: bea0848523a00ddfff8d79265ea699ac
---
Routing in webforJ is een optioneel hulpmiddel. Ontwikkelaars kunnen kiezen tussen de webforJ routingoplossing of een traditioneel model met `Frame` manipulatie en zonder deep linking. Om routing in te schakelen, moet de **`@Routify`** annotatie worden toegepast op het niveau van een klasse die `App` implementeert. Dit geeft webforJ de autoriteit om de browsergeschiedenis te beheren, te reageren op navigatie-evenementen en de componenten van de app weer te geven op basis van de URL.

:::info
Voor meer informatie over het bouwen van UI's met behulp van frames, ingebouwde en aangepaste componenten, bezoek [Building UIs](../building-ui/overview).
:::

## Doel van de `@Routify` Annotatie {#purpose-of-the-routify-annotation}

**`@Routify`** stelt het framework in staat om routes automatisch te registreren, de zichtbaarheid van frames te beheren en routinggedragingen zoals debugging en frame-initialisatie te definiëren, waardoor dynamische, flexibele routing in de app mogelijk is.

## Gebruik van `@Routify` {#usage-of-routify}

De **`@Routify`** annotatie wordt toegepast op het klasseniveau van de hoofd-appklasse. Het specificeert de set packages die gescand moeten worden voor routes en beheert andere routinggerelateerde instellingen zoals frame-initialisatie en zichtbaarheid.

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
    // Applicatielogica komt hier
  }
}
```

:::tip Routify Standaard Configuraties
De **`@Routify`** annotatie komt met redelijke standaardconfiguraties. Het gaat ervan uit dat het huidige package waarin de app is gedefinieerd, samen met al zijn subpackages, moet worden gescand voor routes. Daarnaast gaat het ervan uit dat de app standaard slechts één frame beheert. Als je app deze structuur volgt, is er geen behoefte om aangepaste configuraties aan de annotatie te geven.
:::

## Sleutelelementen van `@Routify` {#key-elements-of-routify}

### 1. **`packages`** {#1-packages}

Het `packages` element definieert welke packages moeten worden gescand voor route-definities. Het stelt automatische ontdekking van routes mogelijk zonder handmatige registratie, waardoor het proces van het uitbreiden van het routing-systeem van de app gestroomlijnd wordt.

```java
@Routify(packages = {"com.myapp.views"})
```

Als er geen packages zijn opgegeven, wordt het standaard package van de app gebruikt.

### 2. **`defaultFrameName`** {#2-defaultframename}

Dit element specificeert de naam van het standaard frame dat de app initialiseert. Frames vertegenwoordigen top-level UI-containers, en deze instelling bepaalt hoe het eerste frame wordt genoemd en beheerd.

```java
@Routify(defaultFrameName = "MainFrame")
```

Standaard, als niet expliciet opgegeven, is de waarde ingesteld op `Routify.DEFAULT_FRAME_NAME`.

### 3. **`initializeFrame`** {#3-initializeframe}

De `initializeFrame` vlag bepaalt of het framework automatisch het eerste frame moet initialiseren wanneer de app wordt gestart. Dit op `true` instellen vereenvoudigt de initiële frame-instelling.

```java
@Routify(initializeFrame = true)
```

### 4. **`manageFramesVisibility`** {#4-manageframesvisibility}

Dit element beheert of het framework automatisch de zichtbaarheid van frames tijdens navigatie moet wisselen. Wanneer ingeschakeld, toont de overeenkomende route automatisch het bijbehorende frame terwijl andere worden verborgen, wat zorgt voor een schone en gefocuste UI. Deze instelling is alleen relevant wanneer je app meerdere frames beheert.

```java
@Routify(manageFramesVisibility = true)
```

### 5. **`debug`** {#5-debug}

De `debug` vlag schakelt de routing debugmodus in of uit. Wanneer ingeschakeld, worden routinginformatie en acties naar de console gelogd voor eenvoudigere debugging tijdens de ontwikkeling.

```java
@Routify(debug = true)
```

:::info Router Debug Modus en webforJ Debug Modus
Als de router debugmodus is ingesteld op `true`, maar de webforJ debugmodus is ingesteld op `false`, worden er geen debuginformatie in de console weergegeven.
:::
