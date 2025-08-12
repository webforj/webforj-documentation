---
title: Terminate and Error Actions
sidebar_position: 40
_i18n_hash: 1a250a51020b32c8b3471ae75ea8f750
---
<!-- vale off -->
# Terminate and Error Actions <DocChip chip='since' label='23.06' />
<!-- vale on -->

Bij het ontwikkelen van applicaties met webforJ is het essentieel om te definiëren hoe je app zich gedraagt wanneer deze beëindigt of een fout tegenkomt. Het framework biedt mechanismen om deze gedragingen aan te passen via `terminate` en `error` acties.

## Overzicht {#overview}

De `App` klasse stelt je in staat om acties te definiëren die worden uitgevoerd wanneer de app normaal beëindigt of wanneer deze een fout tegenkomt. Deze acties zijn instanties van de `AppCloseAction` interface en kunnen worden ingesteld met:

- `setTerminateAction(AppCloseAction action)`: Stelt de actie in die moet worden uitgevoerd bij normale beëindiging.
- `setErrorAction(AppCloseAction action)`: Stelt de actie in die moet worden uitgevoerd wanneer er een fout optreedt.

Beschikbare implementaties van `AppCloseAction` omvatten:

- `DefaultAction`: Leegt de browser en toont een gelokaliseerd bericht om de gebruiker te vragen de app opnieuw te laden.
- `NoneAction`: Voert geen actie uit, waardoor elke eerder ingestelde actie effectief wordt gereset.
- `MessageAction`: Toont een aangepaste linkboodschap.
- `RedirectAction`: Stuur de gebruiker door naar een opgegeven URL.

:::info Het onderscheiden van beëindigingsacties en foutacties in webforJ
webforJ beschouwt beëindiging als gevolg van een opgegooide of niet-afgehandelde uitzondering niet als een foutactie, maar eerder als een beëindigingsactie omdat de app normaal stopt. Een foutactie treedt op wanneer de app een beëindigingssignaal ontvangt als gevolg van een externe fout, zoals wanneer de browser geen verbinding kan maken met de server die de app uitvoert.
:::

## Standaard gedrag {#default-behavior}

In webforJ versie `24.11` en eerder, gebruikt de app standaard `DefaultAction` voor zowel beëindigings- als foutgebeurtenissen. Dit betekent dat wanneer de app beëindigt of een fout tegenkomt, de browser een bericht toont om de gebruiker te vragen de app opnieuw te laden.

Vanaf versie `24.12` standaard webforJ naar `NoneAction` voor zowel beëindigings- als foutgebeurtenissen. Deze wijziging betekent dat er geen actie wordt ondernomen wanneer de app beëindigt of er een fout optreedt, waardoor webforJ de foutafhandeling kan delegeren aan een geschikte `ErrorHandler`, indien deze is geconfigureerd, of kan terugvallen op zijn generieke foutafhandelingsmechanismen. Door `NoneAction` te gebruiken, voorkomt de app dat de standaard foutafhandelingsflow wordt verstoord, zodat je aangepaste foutafhandelaars kunt definiëren of kunt vertrouwen op de ingebouwde foutbeheer van webforJ.

## Acties aanpassen {#customizing-actions}

Om het standaardgedrag te wijzigen, gebruik je de methoden `setTerminateAction()` en `setErrorAction()` in je `App` subclass.

### Instellen van een aangepaste berichtactie {#setting-a-custom-message-action}

Als je een aangepast bericht wilt tonen bij normale beëindiging:

```java
public class MyApp extends App {

  @Override
  public void run() throws WebforjException {
    // Stel een aangepaste berichtactie in
    setTerminateAction(new MessageAction(
        "Bedankt voor het gebruik van onze applicatie! Klik om opnieuw te laden"
    ));
  }
}
```

### Instellen van een doorverwijsactie {#setting-a-redirect-action}

Om de gebruiker naar een specifieke URL door te verwijzen bij normale beëindiging:

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

Je kunt je app programmatisch beëindigen door de methode `terminate()` aan te roepen:

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

Bij het aanroepen van `terminate()` wordt de actie die door `setTerminateAction()` is gedefinieerd uitgevoerd.

## Hooks voor beëindiging {#hooks-for-termination}

De `App` klasse biedt hookmethoden om acties uit te voeren vóór en na beëindiging:

- `onWillTerminate()`: Wordt aangeroepen vóór beëindiging.
- `onDidTerminate()`: Wordt aangeroepen ná beëindiging.

```java
public class MyApp extends App {

  @Override
  protected void onWillTerminate() {
    // Voer opschoon taken uit
  }

  @Override
  protected void onDidTerminate() {
    // Acties na beëindiging
  }
}
```

:::tip Externe levenscyclusluisteraars
Voor meer geavanceerd levenscyclusbeheer, overweeg het gebruik van `AppLifecycleListener` om beëindigingsevents van externe componenten af te handelen zonder de `App` klasse te wijzigen. Dit is bijzonder nuttig voor plugin-architecturen of wanneer meerdere componenten moeten reageren op app-beëindiging. Leer meer over [Levenscyclusluisteraars](lifecycle-listeners.md).
:::

### Aangepaste beëindigingspagina {#custom-termination-page}

In sommige gevallen wil je misschien een aangepaste beëindigingspagina weergeven wanneer je app eindigt, waarbij je gebruikers een gepersonaliseerd bericht of aanvullende bronnen biedt. Dit kan worden bereikt door de methode `onDidTerminate()` in je `App` subclass te overschrijven en aangepaste HTML in de pagina te injecteren.

Hier is een voorbeeld van hoe je een aangepaste beëindigingspagina kunt maken:

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
        <h1>Bedankt voor het gebruik van webforJ</h1>
        <p>Voor meer informatie, bezoek <a href="https://webforj.com">webforj.com</a></p>
    </div>
    """;

    Page.getCurrent().executeJsVoidAsync(
        String.format("document.body.innerHTML = `%s`", html)
    );
  }
}
```
