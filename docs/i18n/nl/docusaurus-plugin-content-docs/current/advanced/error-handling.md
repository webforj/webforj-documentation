---
title: Foutafhandeling
sidebar_position: 25
_i18n_hash: 15106dd9fa7ccf0d4f722ca675f0d362
---
Foutafhandeling is een cruciaal aspect van het ontwikkelen van robuuste webapplicaties. In webforJ is de foutafhandeling ontworpen om flexibel en aanpasbaar te zijn, waardoor ontwikkelaars uitzonderingen kunnen afhandelen op een manier die het beste aansluit bij de behoeften van hun applicatie.

## Overzicht {#overview}

In webforJ draait de foutafhandeling om de `ErrorHandler` interface. Deze interface stelt ontwikkelaars in staat te definiëren hoe hun applicatie moet reageren wanneer er tijdens de uitvoering uitzonderingen optreden. Standaard biedt webforJ een `GlobalErrorHandler` die alle uitzonderingen op een generieke manier afhandelt. Ontwikkelaars kunnen echter aangepaste foutafhandelaars maken voor specifieke uitzonderingen om meer op maat gemaakte reacties te bieden.

## Ontdekken en gebruiken van foutafhandelaars {#discovering-and-using-error-handlers}

webforJ gebruikt Java's Service Provider Interface (SPI) om foutafhandelaars te ontdekken en te laden.

### Ontdekkingsproces {#discovery-process}

1. **Service Registratie**: Foutafhandelaars worden geregistreerd via het `META-INF/services` mechanisme.
2. **Service Laden**: Bij het opstarten van de applicatie laadt webforJ alle klassen die zijn vermeld in `META-INF/services/com.webforj.error.ErrorHandler`.
3. **Foutafhandeling**: Wanneer er een uitzondering optreedt, controleert webforJ of er een foutafhandelaar bestaat voor die specifieke uitzondering.

### Selectie van de afhandelaar {#handler-selection}

- Als er een specifieke afhandelaar voor de uitzondering bestaat, wordt deze gebruikt.
- Als er geen specifieke afhandelaar wordt gevonden, maar er is een aangepaste globale foutafhandelaar `WebforjGlobalErrorHandler` gedefinieerd, wordt deze gebruikt.
- Als geen van beide wordt gevonden, wordt de standaard `GlobalErrorHandler` gebruikt.

## De `ErrorHandler` Interface {#the-errorhandler-interface}

De `ErrorHandler` interface is ontworpen om fouten af te handelen die optreden tijdens de uitvoering van een webforJ-applicatie. Toepassingen die specifieke uitzonderingen willen beheren, moeten deze interface implementeren.

### Methoden {#methods}

- **`onError(Throwable throwable, boolean debug)`**: Wordt aangeroepen wanneer er een fout optreedt. Deze methode moet de logica bevatten voor het afhandelen van de uitzondering.
- **`showErrorPage(String title, String content)`**: Een standaardmethode die de foutpagina weergeeft met de gegeven titel en inhoud.

### Naamgevingsconventie {#naming-convention}

De implementerende klasse moet worden genoemd naar de uitzondering die deze afhandelt, met de suffix `ErrorHandler`. Bijvoorbeeld, om `NullPointerException` af te handelen, moet de klasse `NullPointerExceptionErrorHandler` worden genoemd.

### Registratie {#registration}

De aangepaste foutafhandelaar moet worden geregistreerd in het `META-INF/services/com.webforj.error.ErrorHandler` bestand zodat webforJ deze kan ontdekken en gebruiken.

## Implementeren van een aangepaste foutafhandelaar {#implementing-a-custom-error-handler}

De volgende stappen beschrijven de implementatie van een aangepaste foutafhandelaar voor een specifieke uitzondering:

### Stap 1: Maak de foutafhandelaar klasse aan {#step-1-create-the-error-handler-class}

Maak een nieuwe klasse die de `ErrorHandler` interface implementeert en genoemd is naar de uitzondering die deze afhandelt.

```java
package com.example.error;

import com.webforj.error.ErrorHandler;

public class NullPointerExceptionErrorHandler implements ErrorHandler {

  @Override
  public void onError(Throwable throwable, boolean debug) {
    // Aangepaste afhandelingslogica voor NullPointerException
    String title = "Null Pointer Exception";
    String content = "Er is een null-waarde aangetroffen waar een object vereist is.";

    showErrorPage(title, content);
  }
}
```

:::info `showErrorPage()` methode
De `showErrorPage` methode is een hulpfunctie die de webforJ API gebruikt om de verstrekte HTML-inhoud en paginatitel naar de browser te sturen, en een foutpagina weer te geven. Wanneer er een uitzondering optreedt en de applicatie niet kan herstellen, wordt het onmogelijk om webforJ componenten te gebruiken om een aangepaste foutpagina te bouwen. De `Page` API blijft echter toegankelijk, waardoor de ontwikkelaar kan omleiden of een foutpagina kan weergeven als laatste poging.
:::

### Stap 2: Registreer de foutafhandelaar {#step-2-register-the-error-handler}

Maak een bestand genaamd `com.webforj.error.ErrorHandler` in de `META-INF/services` directory van je applicatie. Voeg de volledig gekwalificeerde naam van je foutafhandelaar klasse toe aan dit bestand.

**Bestand**: `META-INF/services/com.webforj.error.ErrorHandler`

```
com.example.error.NullPointerExceptionErrorHandler
```

Nu, wanneer er een `NullPointerException` wordt opgegooid, selecteert webforJ jouw geregistreerde afhandelaar en voert deze logica uit om de fout af te handelen.

## Gebruik van `AutoService` om registratie te vereenvoudigen {#using-autoservice-to-simplify-registration}

Het is gemakkelijk voor ontwikkelaars om te vergeten om servicetypes bij te werken of correct op te geven. Door Google's `AutoService` te gebruiken, kun je het genereren van het `META-INF/services/com.webforj.error.ErrorHandler` bestand automatiseren. Het enige wat je hoeft te doen is de foutafhandelaar te annoteren met de `AutoService` annotatie. Je kunt hier meer leren over [AutoService](https://github.com/google/auto/blob/main/service/README.md).

```java
@AutoService(ErrorHandler.class)
public class NullPointerExceptionErrorHandler implements ErrorHandler {

  @Override
  public void onError(Throwable throwable, boolean debug) {
    // Aangepaste afhandelingslogica voor NullPointerException
    String title = "Null Pointer Exception";
    String content = "Er is een null-waarde aangetroffen waar een object vereist is.";

    showErrorPage(title, content);
  }
}
```

## De `GlobalErrorHandler` klasse {#the-globalerrorhandler-class}

De `GlobalErrorHandler` is de standaard foutafhandelaar die door webforJ wordt geleverd. Het implementeert de `ErrorHandler` interface en biedt generieke foutafhandeling.

### Gedrag {#behavior}

- **Logging**: Fouten worden gelogd naar zowel de server- als de browserconsole.
- **Weergeven van de foutpagina**: Afhankelijk van de debugmodus wordt de foutpagina de stacktrace of een generiek foutbericht weergegeven.

### Definiëren van een aangepaste globale foutafhandelaar {#defining-a-custom-global-error-handler}

Om een globale foutafhandelaar te definiëren, moet je een nieuwe foutafhandelaar maken genaamd `WebforjGlobalErrorHandler`. Volg daarna [de stappen om foutafhandelaars te registreren](#step-2-register-the-error-handler) zoals eerder uitgelegd. In dit geval zoekt webforJ eerst naar eventuele aangepaste foutafhandelaars om uitzonderingen te beheren. Als er geen worden gevonden, valt webforJ terug op de aangepaste globale foutafhandelaar.

:::info
Als er meerdere `WebforjGlobalErrorHandler` zijn geregistreerd, selecteert webforJ de eerste.
:::
