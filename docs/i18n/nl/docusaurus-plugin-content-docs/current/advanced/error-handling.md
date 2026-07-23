---
title: Error Handling
sidebar_position: 5
description: >-
  Register custom ErrorHandler implementations through the Java Service Provider
  Interface to respond to specific exceptions in webforJ apps.
_i18n_hash: 55731ba6ae9454377d363fa461c817bc
---
Foutafhandeling is een cruciaal aspect van het ontwikkelen van betrouwbare web-apps. In webforJ is foutafhandeling ontworpen om flexibel en aanpasbaar te zijn, zodat ontwikkelaars uitzonderingen kunnen verwerken op een manier die het beste past bij de behoeften van hun app.

## Overzicht {#overview}

In webforJ draait foutafhandeling om de `ErrorHandler` interface. Deze interface stelt ontwikkelaars in staat om te definiëren hoe hun app moet reageren wanneer zich uitzonderingen voordoen tijdens de uitvoering. Standaard biedt webforJ een `GlobalErrorHandler` die alle uitzonderingen op een generieke manier afhandelt. Ontwikkelaars kunnen echter aangepaste foutbehandelaars maken voor specifieke uitzonderingen om meer op maat gemaakte reacties te bieden.

## Ontdekken en gebruiken van foutbehandelaars {#discovering-and-using-error-handlers}

webforJ maakt gebruik van de Service Provider Interface (SPI) van Java om foutbehandelaars te ontdekken en te laden.

### Ontdekkingsproces {#discovery-process}

1. **Service Registratie**: Foutbehandelaars worden geregistreerd via het `META-INF/services` mechanisme.
2. **Service Laden**: Bij het opstarten van de app laadt webforJ alle klassen die zijn vermeld in `META-INF/services/com.webforj.error.ErrorHandler`.
3. **Foutafhandeling**: Wanneer zich een uitzondering voordoet, controleert webforJ of er een foutbehandelaar bestaat voor die specifieke uitzondering.

### Beheerder selectie {#handler-selection}

- Als er een specifieke beheerder voor de uitzondering bestaat, wordt deze gebruikt.
- Als er geen specifieke beheerder wordt gevonden, maar een aangepaste globale foutbehandelaar `WebforjGlobalErrorHandler` is gedefinieerd, wordt deze gebruikt.
- Als geen van beiden wordt gevonden, wordt de standaard `GlobalErrorHandler` gebruikt.

## De `ErrorHandler` Interface {#the-errorhandler-interface}

De `ErrorHandler` interface is ontworpen om fouten te verwerken die zich voordoen tijdens de uitvoering van een webforJ-app. Toepassingen die specifieke uitzonderingen willen beheren, moeten deze interface implementeren.

### Methoden {#methods}

- **`onError(Throwable throwable, boolean debug)`**: Wordt aangeroepen wanneer er een fout optreedt. Deze methode moet de logica bevatten voor het afhandelen van de uitzondering.
- **`showErrorPage(String title, String content)`**: Een standaardmethode die de foutpagina weergeeft met de gegeven titel en inhoud.

### Benaming conventie {#naming-convention}

De implementerende klasse moet naar de uitzondering zijn vernoemd die hij behandelt, met de suffix `ErrorHandler`. Bijvoorbeeld, om `NullPointerException` te behandelen, moet de klasse `NullPointerExceptionErrorHandler` worden genoemd.

### Registratie {#registration}

De aangepaste foutbehandelaar moet worden geregistreerd in het `META-INF/services/com.webforj.error.ErrorHandler` bestand, zodat webforJ deze kan ontdekken en gebruiken.

## Implementeren van een aangepaste foutbehandelaar {#implementing-a-custom-error-handler}

De volgende stappen beschrijven de implementatie van een aangepaste foutbehandelaar voor een specifieke uitzondering:

### Stap 1: Maak de foutbehandelaar klasse aan {#step-1-create-the-error-handler-class}

Maak een nieuwe klasse die de `ErrorHandler` interface implementeert en is vernoemd naar de uitzondering die hij behandelt.

```java
package com.example.error;

import com.webforj.error.ErrorHandler;

public class NullPointerExceptionErrorHandler implements ErrorHandler {

  @Override
  public void onError(Throwable throwable, boolean debug) {
    // Aangepaste afhandelingslogica voor NullPointerException
    String title = "Null Pointer Exception";
    String content = "Er is een null waarde aangetroffen waar een object vereist is.";

    showErrorPage(title, content);
  }
}
```

:::info `showErrorPage()` methode
De `showErrorPage` methode is een hulpmethode die de webforJ API gebruikt om de verstrekte HTML-inhoud en paginatitel naar de browser te verzenden, waarmee een foutpagina wordt weergegeven. Wanneer er een uitzondering optreedt en de app niet kan herstellen, wordt het onmogelijk om webforJ-componenten te gebruiken om een aangepaste foutpagina te bouwen. De `Page` API blijft echter toegankelijk, waardoor de ontwikkelaar de pagina kan omleiden of een foutpagina kan weergeven als laatste poging.
:::

### Stap 2: Registreer de foutbehandelaar {#step-2-register-the-error-handler}

Maak een bestand met de naam `com.webforj.error.ErrorHandler` in de `META-INF/services` directory van uw app. Voeg de volledig gekwalificeerde naam van uw foutbehandelaarklasse toe aan dit bestand.

**Bestand**: `META-INF/services/com.webforj.error.ErrorHandler`

```
com.example.error.NullPointerExceptionErrorHandler
```

Nu, wanneer er een `NullPointerException` wordt opgegooid, selecteert webforJ uw geregistreerde beheerder en voert deze logica uit om de fout te verwerken.

## Gebruik van `AutoService` om registratie te vereenvoudigen {#using-autoservice-to-simplify-registration}

Het is gemakkelijk voor ontwikkelaars om te vergeten om servicebeschrijvingen bij te werken of correct op te geven. Door gebruik te maken van Google's `AutoService`, kunt u de generatie van het `META-INF/services/com.webforj.error.ErrorHandler` bestand automatiseren. Alles wat u hoeft te doen, is de foutbehandelaar van een `AutoService` annotatie te voorzien. U kunt meer leren over [AutoService hier](https://github.com/google/auto/blob/main/service/README.md).

```java
@AutoService(ErrorHandler.class)
public class NullPointerExceptionErrorHandler implements ErrorHandler {

  @Override
  public void onError(Throwable throwable, boolean debug) {
    // Aangepaste afhandelingslogica voor NullPointerException
    String title = "Null Pointer Exception";
    String content = "Er is een null waarde aangetroffen waar een object vereist is.";

    showErrorPage(title, content);
  }
}
```

## De `GlobalErrorHandler` klasse {#the-globalerrorhandler-class}

De `GlobalErrorHandler` is de standaard foutbehandelaar die door webforJ wordt geleverd. Deze implementeert de `ErrorHandler` interface en biedt generieke foutafhandeling.

### Gedrag {#behavior}

- **Logging**: Fouten worden gelogd naar zowel de server- als browserconsole.
- **Foutpagina Weergeven**: Afhankelijk van de debugmodus toont de foutpagina de stacktrace of een generieke foutmelding.

### Definiëren van een aangepaste globale foutbehandelaar {#defining-a-custom-global-error-handler}

Om een globale foutbehandelaar te definiëren, moet u een nieuwe foutbehandelaar maken met de naam `WebforjGlobalErrorHandler`. Volg vervolgens [de stappen om foutbehandelaars te registreren](#step-2-register-the-error-handler) zoals eerder uitgelegd. In dit geval zal webforJ eerst zoeken naar aangepaste foutbehandelaars om uitzonderingen te beheren. Als er geen worden gevonden, valt webforJ terug op de aangepaste globale foutbehandelaar.

:::info
Als er meerdere `WebforjGlobalErrorHandler` zijn geregistreerd, selecteert webforJ de eerste.
:::
