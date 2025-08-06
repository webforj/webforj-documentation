---
title: Error Handling
sidebar_position: 25
_i18n_hash: a758848bf429e84f33f8b7ba8a4f7277
---
Foutafhandeling is een cruciaal aspect van het ontwikkelen van robuuste webapplicaties. In webforJ is foutafhandeling ontworpen om flexibel en aanpasbaar te zijn, zodat ontwikkelaars uitzonderingen kunnen afhandelen op een manier die het beste past bij de behoeften van hun app.

## Overzicht {#overview}

In webforJ draait foutafhandeling om de `ErrorHandler` interface. Deze interface stelt ontwikkelaars in staat om te definiëren hoe hun app moet reageren wanneer uitzonderingen optreden tijdens de uitvoering. Standaard biedt webforJ een `GlobalErrorHandler` die alle uitzonderingen op een generieke manier afhandelt. Ontwikkelaars kunnen echter aangepaste foutafhandelaars maken voor specifieke uitzonderingen om meer op maat gemaakte reacties te geven.

## Ontdekken en gebruiken van foutafhandelaars {#discovering-and-using-error-handlers}

webforJ gebruikt Java's Service Provider Interface (SPI) om foutafhandelaars te ontdekken en te laden.

### Ontdekkingsproces {#discovery-process}

1. **Service Registratie**: Foutafhandelaars worden geregistreerd via het `META-INF/services` mechanisme.
2. **Service Laden**: Bij het opstarten van de app laadt webforJ alle klassen die zijn vermeld in `META-INF/services/com.webforj.error.ErrorHandler`.
3. **Foutafhandeling**: Wanneer een uitzondering optreedt, controleert webforJ of er een foutafhandelaar bestaat voor die specifieke uitzondering.

### Afhandeling selectie {#handler-selection}

- Als er een specifieke afhandelaar voor de uitzondering bestaat, wordt deze gebruikt.
- Als er geen specifieke afhandelaar wordt gevonden, maar een aangepaste globale foutafhandelaar `WebforjGlobalErrorHandler` is gedefinieerd, wordt deze gebruikt.
- Als geen van beide wordt gevonden, wordt de standaard `GlobalErrorHandler` gebruikt.

## De `ErrorHandler` Interface {#the-errorhandler-interface}

De `ErrorHandler` interface is ontworpen om fouten af te handelen die optreden tijdens de uitvoering van een webforJ-app. Toepassingen die specifieke uitzonderingen willen beheren, moeten deze interface implementeren.

### Methoden {#methods}

- **`onError(Throwable throwable, boolean debug)`**: Wordt aangeroepen wanneer er een fout optreedt. Deze methode moet de logica bevatten voor het afhandelen van de uitzondering.
- **`showErrorPage(String title, String content)`**: Een standaardmethode die de foutpagina toont met de gegeven titel en inhoud.

### Benaming conventie {#naming-convention}

De implementerende klasse moet worden genoemd naar de uitzondering die deze afhandelt, met de suffix `ErrorHandler`. Bijvoorbeeld, om `NullPointerException` af te handelen, moet de klasse `NullPointerExceptionErrorHandler` worden genoemd.

### Registratie {#registration}

De aangepaste foutafhandelaar moet worden geregistreerd in het bestand `META-INF/services/com.webforj.error.ErrorHandler`, zodat webforJ deze kan ontdekken en gebruiken.

## Een aangepaste foutafhandelaar implementeren {#implementing-a-custom-error-handler}

De volgende stappen beschrijven de implementatie van een aangepaste foutafhandelaar voor een specifieke uitzondering:

### Stap 1: Maak de foutafhandelaar klasse aan {#step-1-create-the-error-handler-class}

Maak een nieuwe klasse die de `ErrorHandler` interface implementeert en is genoemd naar de uitzondering die deze afhandelt.

```java
package com.example.error;

import com.webforj.error.ErrorHandler;

public class NullPointerExceptionErrorHandler implements ErrorHandler {

  @Override
  public void onError(Throwable throwable, boolean debug) {
    // Aangepaste afhandelingslogica voor NullPointerException
    String title = "Null Pointer Exception";
    String content = "Een null-waarde werd aangetroffen waar een object nodig is.";

    showErrorPage(title, content);
  }
}
```

:::info `showErrorPage()` methode
De `showErrorPage` methode is een hulpfunctie die de webforJ API gebruikt om de verstrekte HTML-inhoud en pagina titel naar de browser te sturen, waarbij een foutpagina wordt weergegeven. Wanneer een uitzondering optreedt en de app niet kan herstellen, wordt het onmogelijk om webforJ-componenten te gebruiken om een aangepaste foutpagina te bouwen. De `Page` API blijft echter toegankelijk, zodat de ontwikkelaar kan omleiden of een foutpagina als laatste poging kan weergeven.
:::

### Stap 2: Registreer de foutafhandelaar {#step-2-register-the-error-handler}

Maak een bestand met de naam `com.webforj.error.ErrorHandler` in de `META-INF/services` directory van uw app. Voeg de volledig gekwalificeerde naam van uw foutafhandelaar klasse aan dit bestand toe.

**Bestand**: `META-INF/services/com.webforj.error.ErrorHandler`

```
com.example.error.NullPointerExceptionErrorHandler
```

Nu, wanneer er een `NullPointerException` wordt gegooid, selecteert webforJ uw geregistreerde afhandelaar en voert zijn logica uit om de fout af te handelen.

## Gebruik `AutoService` om registratie te vereenvoudigen {#using-autoservice-to-simplify-registration}

Het is eenvoudig voor ontwikkelaars om te vergeten servicebeschrijvingen bij te werken of correct op te geven. Door Google's `AutoService` te gebruiken, kunt u de generatie van het `META-INF/services/com.webforj.error.ErrorHandler` bestand automatiseren. Het enige wat u hoeft te doen is de foutafhandelaar te annoteren met de `AutoService` annotatie. U kunt meer leren over [AutoService hier](https://github.com/google/auto/blob/main/service/README.md).

```java
@AutoService(ErrorHandler.class)
public class NullPointerExceptionErrorHandler implements ErrorHandler {

  @Override
  public void onError(Throwable throwable, boolean debug) {
    // Aangepaste afhandelingslogica voor NullPointerException
    String title = "Null Pointer Exception";
    String content = "Een null-waarde werd aangetroffen waar een object nodig is.";

    showErrorPage(title, content);
  }
}
```

## De `GlobalErrorHandler` klasse {#the-globalerrorhandler-class}

De `GlobalErrorHandler` is de standaard foutafhandelaar die door webforJ wordt geleverd. Het implementeert de `ErrorHandler` interface en biedt generieke foutafhandeling.

### Gedrag {#behavior}

- **Logging**: Fouten worden gelogd naar zowel de server- als de browserconsoles.
- **Foutpagina Weergeven**: Afhankelijk van de debugmodus toont de foutpagina de stacktrace of een generiek foutbericht.

### Een aangepaste globale foutafhandelaar definiëren {#defining-a-custom-global-error-handler}

Om een globale foutafhandelaar te definiëren, moet u een nieuwe foutafhandelaar maken met de naam `WebforjGlobalErrorHandler`. Volg vervolgens [de stappen om foutafhandelaars te registreren](#step-2-register-the-error-handler) zoals eerder uitgelegd. In dit geval zoekt webforJ eerst naar eventuele aangepaste foutafhandelaars om uitzonderingen te beheren. Als er geen worden gevonden, valt webforJ terug op de aangepaste globale foutafhandelaar.

:::info
Als er meerdere `WebforjGlobalErrorHandler` geregistreerd zijn, selecteert webforJ de eerste.
:::
