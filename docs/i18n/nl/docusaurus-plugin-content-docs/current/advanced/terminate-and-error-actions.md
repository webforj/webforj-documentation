---
title: Terminate and Error Actions
sidebar_position: 40
_i18n_hash: d0f7532dd9019f6cd611255055c76754
---
<!-- vale off -->
# Beëindigen en Fout Acties <DocChip chip='since' label='23.06' />
<!-- vale on -->

Bij het ontwikkelen van applicaties met webforJ is het essentieel om te definiëren hoe uw app zich gedraagt wanneer deze wordt beëindigd of een fout tegenkomt. Het framework biedt mechanismen om deze gedragingen aan te passen via `terminate` en `error` acties.

## Overzicht {#overview}

De `App` klasse stelt u in staat om acties te definiëren die worden uitgevoerd wanneer de app normaal wordt beëindigd of wanneer deze een fout tegenkomt. Deze acties zijn instanties van de `AppCloseAction` interface en kunnen worden ingesteld met:

- `setTerminateAction(AppCloseAction action)`: Stelt de actie in die moet worden uitgevoerd bij normale beëindiging.
- `setErrorAction(AppCloseAction action)`: Stelt de actie in die moet worden uitgevoerd wanneer er een fout optreedt.

Beschikbare implementaties van `AppCloseAction` zijn onder andere:

- `DefaultAction`: Wis de browser en toont een gelokaliseerd bericht dat de gebruiker vraagt de app te herladen.
- `NoneAction`: Voert geen actie uit, waardoor eerder ingestelde acties effectief worden gereset.
- `MessageAction`: Toont een aangepaste linkboodschap.
- `RedirectAction`: Stuur de gebruiker door naar een opgegeven URL.

:::info Onderscheid maken tussen Beëindigingsacties en Foutacties in webforJ
webforJ beschouwt beëindiging door een gegooide of niet-afgehandelde uitzondering niet als een foutactie, maar eerder als een beëindigingsactie omdat de app normaal afsluit. Een foutactie treedt op wanneer de app een beëindigingssignaal ontvangt als gevolg van een externe fout, zoals wanneer de browser geen verbinding kan maken met de server die de app uitvoert.
:::

## Standaardgedrag {#default-behavior}

In webforJ versie `24.11` en eerder, gebruikt de app standaard `DefaultAction` voor zowel beëindigings- als foutgebeurtenissen. Dit betekent dat wanneer de app beëindigt of een fout tegenkomt, de browser een bericht toont dat de gebruiker vraagt de app te herladen.

Vanaf versie `24.12` gebruikt webforJ standaard `NoneAction` voor zowel beëindigings- als foutgebeurtenissen. Deze wijziging betekent dat er geen actie wordt ondernomen wanneer de app beëindigt of er een fout optreedt, waardoor webforJ foutafhandeling kan delegeren aan een geschikte `ErrorHandler` indien deze is geconfigureerd, of kan terugvallen op zijn generieke foutafhandelingsmechanismen. Door `NoneAction` te gebruiken, voorkomt de app verstoringen van de standaard foutafhandelingsflow, zodat u aangepaste foutafhandelaars kunt definiëren of kunt vertrouwen op de ingebouwde foutbeheer van webforJ.

## Acties aanpassen {#customizing-actions}

Om het standaardgedrag te wijzigen, gebruikt u de methoden `setTerminateAction()` en `setErrorAction()` in uw `App` subklasse.

### Een aangepaste boodschapactie instellen {#setting-a-custom-message-action}

Als u een aangepaste boodschap wilt weergeven bij normale beëindiging:

```java
public class MyApp extends App {

  @Override
  public void run() throws WebforjException {
    // Stel een aangepaste boodschapactie in
    setTerminateAction(new MessageAction(
        "Bedankt voor het gebruiken van onze applicatie! Klik om te herladen"
    ));
  }
}
```

### Een doorverwijsactie instellen {#setting-a-redirect-action}

Om de gebruiker bij normale beëindiging naar een specifieke URL door te verwijzen:

```java
public class MyApp extends App {

  @Override
  public void run() throws WebforjException {
    // Stel een doorverwijsactie in bij een fout
    setTerminateAction(new RedirectAction(
        "https://example.com/error"
    ));
  }
}
```

## De app beëindigen {#terminating-the-app}

U kunt uw app programatisch beëindigen door de methode `terminate()` aan te roepen:

```java
public class MyApp extends App {

  @Override
  public void run() throws WebforjException {
    // Beëindig de applicatie onder bepaalde voorwaarden
    if (someCondition) {
      terminate();
    }
  }
}
```

Bij het aanroepen van `terminate()` wordt de actie die is gedefinieerd door `setTerminateAction()` uitgevoerd.

## Haakjes voor beëindiging {#hooks-for-termination}

De `App` klasse biedt haakmethoden om acties uit te voeren voor en na beëindiging:

- `onWillTerminate()`: Wordt aangeroepen voor beëindiging.
- `onDidTerminate()`: Wordt aangeroepen na beëindiging.

```java
public class MyApp extends App {

  @Override
  protected void onWillTerminate() {
    // Voer opruimtaken uit
  }

  @Override
  protected void onDidTerminate() {
    // Acties na beëindiging
  }
}
```

:::tip Externe lifecycle listeners
Voor meer geavanceerd levenscyclusbeheer, overweeg het gebruik van `AppLifecycleListener` om beëindigingsgebeurtenissen van externe componenten te beheren zonder de `App` klasse te modificeren. Dit is bijzonder nuttig voor plugin-architecturen of wanneer meerdere componenten moeten reageren op app-beëindiging. Leer meer over [Lifecycle Listeners](lifecycle-listeners.md).
:::

### Aangepaste beëindigingspagina {#custom-termination-page}

In sommige gevallen wilt u misschien een aangepaste beëindigingspagina weergeven wanneer uw app eindigt, waarmee gebruikers een gepersonaliseerd bericht of aanvullende bronnen wordt aangeboden. Dit kan worden bereikt door de methode `onDidTerminate()` in uw `App` subklasse te override en aangepaste HTML in de pagina in te voegen.

Hier is een voorbeeld van hoe u een aangepaste beëindigingspagina kunt maken:

```java
public class MyApp extends App {

  @Override
  public void run() throws WebforjException {
    setTerminateAction(new NoneAction());
    terminate();
  }

  @Override
  protected void onDidTerminate() {
    String html = """
    <div style="display: flex; justify-content: center; align-items: center; height: 100vh; flex-direction: column;">
        <h1>Bedankt voor het gebruiken van webforJ</h1>
        <p>Voor meer informatie, bezoek <a href="https://webforj.com">webforj.com</a></p>
    </div>
    """;

    Page.getCurrent().executeJsVoidAsync(
        String.format("document.body.innerHTML = `%s`", html)
    );
  }
}
```
