---
sidebar_position: 2
title: Routable Apps
_i18n_hash: 889bb5d90fac8315d6b7b1cf766fadea
---
Routing in webforJ is een optionele tool. Ontwikkelaars kunnen kiezen tussen de webforJ routeringsoplossing of een traditioneel model met `Frame` manipulatie en zonder deep linking. Om routing in te schakelen, moet de **`@Routify`** annotatie worden toegepast op het niveau van een klasse die `App` implementeert. Dit geeft webforJ de autoriteit om de browsergeschiedenis te beheren, te reageren op navigatie-evenementen en de componenten van de app weer te geven op basis van de URL.

:::info
Om meer te leren over het bouwen van gebruikersinterfaces met frames, ingebouwde en aangepaste componenten, bezoek de [Building UIs](../building-ui/basics) sectie.
:::

## Doel van de `@Routify` Annotatie {#purpose-of-the-routify-annotation}

**`@Routify`** stelt het framework in staat om automatisch routes te registreren, de zichtbaarheid van frames te beheren en routeringsgedragingen te definiëren zoals debugging en frame-initialisatie, wat dynamische, flexibele routing in de app mogelijk maakt.

## Gebruik van `@Routify` {#usage-of-routify}

De **`@Routify`** annotatie wordt toegepast op het klassenniveau van de hoofdsappklasse. Het specificeert de set van pakketten die gescand moeten worden voor routes en behandelt andere routering gerelateerde instellingen zoals frame-initialisatie en zichtbaarheidbeheer.

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
    // Toepassingslogica gaat hier
  }
}
```

:::tip Routify Standaardconfiguraties
De **`@Routify`** annotatie wordt geleverd met redelijke standaardconfiguraties. Het gaat ervan uit dat het huidige pakket waarin de app is gedefinieerd, samen met al zijn subpakketten, gescand moet worden voor routes. Bovendien gaat het ervan uit dat de app standaard slechts één frame beheert. Als je app deze structuur volgt, is het niet nodig om aangepaste configuraties aan de annotatie te geven.
:::

## Sleutelelementen van `@Routify` {#key-elements-of-routify}

### 1. **`packages`** {#1-packages}

Het `packages` element definieert welke pakketten gescand moeten worden voor route-definities. Het maakt automatische ontdekking van routes mogelijk zonder handmatige registratie, waardoor het proces van het uitbreiden van het app-routeringsysteem wordt gestroomlijnd.

```java
@Routify(packages = {"com.myapp.views"})
```

Als er geen pakketten zijn gespecificeerd, wordt het standaardpakket van de app gebruikt.

### 2. **`defaultFrameName`** {#2-defaultframename}

Dit element specificeert de naam van het standaardframe dat de app initialiseerd. Frames vertegenwoordigen top-level UI-containers, en deze instelling beheert hoe het eerste frame wordt genoemd en beheerd.

```java
@Routify(defaultFrameName = "MainFrame")
```

Standaard is de waarde, als deze niet expliciet is opgegeven, ingesteld op `Routify.DEFAULT_FRAME_NAME`.

### 3. **`initializeFrame`** {#3-initializeframe}

De `initializeFrame` vlag bepaalt of het framework automatisch het eerste frame moet initialiseren wanneer de app start. Dit op `true` instellen vereenvoudigt de initiële frame-instelling.

```java
@Routify(initializeFrame = true)
```

### 4. **`manageFramesVisibility`** {#4-manageframesvisibility}

Dit element beheert of het framework automatisch de zichtbaarheid van frames tijdens navigatie moet schakelen. Wanneer ingeschakeld, toont de bijbehorende frame automatisch de gematchte route terwijl andere worden verborgen, wat zorgt voor een schone en gefocuste UI. Deze instelling is alleen relevant wanneer je app meerdere frames beheert.

```java
@Routify(manageFramesVisibility = true)
```

### 5. **`debug`** {#5-debug}

De `debug` vlag schakelt de routering debugmodus in of uit. Wanneer ingeschakeld, worden routeringsinformatie en acties in de console gelogd voor eenvoudiger debugging tijdens de ontwikkeling. 

```java
@Routify(debug = true)
```

:::info Router Debugmodus en webforJ Debugmodus  
Als de router debugmodus is ingesteld op `true`, maar de webforJ debugmodus is ingesteld op `false`, wordt er geen debuginformatie in de console weergegeven.  
:::
