---
title: Error Handling
sidebar_position: 5
_i18n_hash: 7957d907ae8a5bd9e7b3f7c2fdba2623
---
Foutafhandeling is een cruciaal aspect van de ontwikkeling van betrouwbare webapps. In webforJ is foutafhandeling ontworpen om flexibel en aanpasbaar te zijn, waardoor ontwikkelaars uitzonderingen kunnen afhandelen op een manier die het beste aansluit bij de behoeften van hun app.

## Overzicht {#overview}

In webforJ draait de foutafhandeling om de `ErrorHandler` interface. Deze interface stelt ontwikkelaars in staat om te definiëren hoe hun app moet reageren wanneer er uitzonderingen optreden tijdens de uitvoering. Standaard biedt webforJ een `GlobalErrorHandler` die alle uitzonderingen op een generieke manier afhandelt. Ontwikkelaars kunnen echter aangepaste foutafhandelaars maken voor specifieke uitzonderingen om meer gepersonaliseerde reacties te bieden.

## Ontdekken en gebruiken van foutafhandelaars {#discovering-and-using-error-handlers}

webforJ gebruikt de Service Provider Interface (SPI) van Java om foutafhandelaars te ontdekken en te laden.

### Ontdekproces {#discovery-process}

1. **Service Registratie**: Foutafhandelaars worden geregistreerd via het `META-INF/services` mechanisme.
2. **Service Laden**: Bij het opstarten van de app laadt webforJ alle klassen die zijn vermeld in `META-INF/services/com.webforj.error.ErrorHandler`.
3. **Foutafhandeling**: Wanneer er een uitzondering optreedt, controleert webforJ of er een foutafhandelaar bestaat voor die specifieke uitzondering.

### Handlerselectie {#handler-selection}

- Als er een specifieke handler voor de uitzondering bestaat, wordt deze gebruikt.
- Als er geen specifieke handler is gevonden, maar er een aangepaste globale foutafhandelaar `WebforjGlobalErrorHandler` is gedefinieerd, wordt deze gebruikt.
- Als er geen van beide wordt gevonden, wordt de standaard `GlobalErrorHandler` gebruikt.

## De `ErrorHandler` Interface {#the-errorhandler-interface}

De `ErrorHandler` interface is ontworpen om fouten te behandelen die optreden tijdens de uitvoering van een webforJ app. Toepassingen die specifieke uitzonderingen willen beheren, moeten deze interface implementeren.

### Methoden {#methods}

- **`onError(Throwable throwable, boolean debug)`**: Wordt aangeroepen wanneer er een fout optreedt. Deze methode moet de logica bevatten voor het afhandelen van de uitzondering.
- **`showErrorPage(String title, String content)`**: Een standaardmethode die de foutpagina weergeeft met de gegeven titel en inhoud.

### Naamgevingsconventie {#naming-convention}

De implementerende klasse moet worden genoemd naar de uitzondering die het afhandelt, met de suffix `ErrorHandler`. Om bijvoorbeeld `NullPointerException` af te handelen, moet de klasse `NullPointerExceptionErrorHandler` worden genoemd.

### Registratie {#registration}

De aangepaste foutafhandelaar moet worden geregistreerd in het `META-INF/services/com.webforj.error.ErrorHandler` bestand, zodat webforJ deze kan ontdekken en gebruiken.

## Implementeren van een aangepaste foutafhandelaar {#implementing-a-custom-error-handler}

De volgende stappen beschrijven de implementatie van een aangepaste foutafhandelaar voor een specifieke uitzondering:

### Stap 1: Maak de foutafhandelaar klasse {#step-1-create-the-error-handler-class}

Maak een nieuwe klasse die de `ErrorHandler` interface implementeert en is genoemd naar de uitzondering die het afhandelt.

```java
package com.example.error;

import com.webforj.error.ErrorHandler;

public class NullPointerExceptionErrorHandler implements ErrorHandler {

  @Override
  public void onError(Throwable throwable, boolean debug) {
    // Aangepaste afhandelingslogica voor NullPointerException
    String title = "Null Pointer Exception";
    String content = "Een null-waarde werd aangetroffen waar een object vereist is.";

    showErrorPage(title, content);
  }
}
```

:::info `showErrorPage()` methode
De `showErrorPage` methode is een hulpfunctie die de webforJ API gebruikt om de gegeven HTML-inhoud en paginatitel naar de browser te verzenden, waarmee een foutpagina wordt weergegeven. Wanneer er een uitzondering optreedt en de app niet kan herstellen, wordt het onmogelijk om webforJ-componenten te gebruiken om een aangepaste foutpagina te bouwen. De `Page` API blijft echter toegankelijk, zodat de ontwikkelaar een omleiding of het weergeven van een foutpagina als laatste poging kan uitvoeren.
:::

### Stap 2: Registreer de foutafhandelaar {#step-2-register-the-error-handler}

Maak een bestand met de naam `com.webforj.error.ErrorHandler` in de `META-INF/services` map van uw app. Voeg de volledig gekwalificeerde naam van uw foutafhandelaar klasse aan dit bestand toe.

**Bestand**: `META-INF/services/com.webforj.error.ErrorHandler`

```
com.example.error.NullPointerExceptionErrorHandler
```

Nu, wanneer er een `NullPointerException` wordt gegooid, selecteert webforJ uw geregistreerde handler en voert de logica uit om de fout af te handelen.

## Gebruik van `AutoService` om registratie te vereenvoudigen {#using-autoservice-to-simplify-registration}

Het is gemakkelijk voor ontwikkelaars om te vergeten servicebeschrijvingen bij te werken of correct op te geven. Door gebruik te maken van Google's `AutoService`, kunt u de generatie van het `META-INF/services/com.webforj.error.ErrorHandler` bestand automatiseren. Het enige dat u hoeft te doen, is de foutafhandelaar te annoteren met de `AutoService` annotatie. U kunt meer leren over [AutoService hier](https://github.com/google/auto/blob/main/service/README.md).

```java
@AutoService(ErrorHandler.class)
public class NullPointerExceptionErrorHandler implements ErrorHandler {

  @Override
  public void onError(Throwable throwable, boolean debug) {
    // Aangepaste afhandelingslogica voor NullPointerException
    String title = "Null Pointer Exception";
    String content = "Een null-waarde werd aangetroffen waar een object vereist is.";

    showErrorPage(title, content);
  }
}
```

## De `GlobalErrorHandler` klasse {#the-globalerrorhandler-class}

De `GlobalErrorHandler` is de standaard foutafhandelaar die door webforJ wordt geleverd. Het implementeert de `ErrorHandler` interface en biedt generieke foutafhandeling.

### Gedrag {#behavior}

- **Logging**: Fouten worden gelogd naar zowel de server- als browserconsoles.
- **Foutpaginaweergave**: Afhankelijk van de debugmodus toont de foutpagina de stacktrace of een generiek foutbericht.

### Het definiëren van een aangepaste globale foutafhandelaar {#defining-a-custom-global-error-handler}

Om een globale foutafhandelaar te definiëren, moet u een nieuwe foutafhandelaar maken met de naam `WebforjGlobalErrorHandler`. volg vervolgens [de stappen om foutafhandelaars te registreren](#step-2-register-the-error-handler) zoals eerder uitgelegd. In dit geval kijkt webforJ eerst naar eventuele aangepaste foutafhandelaars om uitzonderingen te beheren. Als er geen zijn gevonden, valt webforJ terug op de aangepaste globale foutafhandelaar.

:::info
Als er meerdere `WebforjGlobalErrorHandler` zijn geregistreerd, selecteert webforJ de eerste.
:::
