---
sidebar_position: 7
title: Events
description: >-
  Listen for component events, read the event payload, configure element events,
  and dispatch your own custom events with the EventDispatcher.
slug: events
sidebar_class_name: new-content
_i18n_hash: 5ceda90a316ff6a1528a686565011f88
---
Componenten, of ze nu op maat gemaakt zijn of deel uitmaken van het framework, ondersteunen evenementenhandelingen. Je kunt evenementenluisteraars toevoegen om verschillende soorten evenementen op te vangen, zoals gebruikersinteracties, veranderingen in de toestand, of evenementen die je zelf definieert. Deze luisteraars stellen je in staat om specifieke acties te activeren als reactie op wat er in je app gebeurt.

## Evenementen toevoegen {#adding-events}

Voeg een luisteraar toe met de evenementen-specifieke methode op de component. Elke component biedt een paar: een `addXxxListener` methode en, in de meeste gevallen, een kortere `on` alias die hetzelfde doet. Een `Button` biedt bijvoorbeeld zowel `addClickListener` als `onClick`.

Je kunt de luisteraar als een lambda doorgeven:

```java
Button button = new Button("Opslaan");
button.onClick(event -> {
  // Verwerk de klik
});
```

of als een methodeverwijzing:

```java
button.onClick(this::handleSave);
```

Niet elk evenement heeft een `on` alias. Waardeveranderingen, bijvoorbeeld, worden alleen toegevoegd met `addValueChangeListener`:

```java
TextField name = new TextField("Naam");
name.addValueChangeListener(event -> {
  String value = event.getValue();
  // Verwerk de nieuwe waarde
});
```

## Een evenement verwijderen {#removing-an-event}

Het toevoegen van een luisteraar retourneert een `ListenerRegistration`. Bewaar deze om de luisteraar later te verwijderen.

```java
ListenerRegistration<ButtonClickEvent> registration =
    button.onClick(event -> {
      // Verwerk de klik
    });

// Later, wanneer de luisteraar niet meer nodig is
registration.remove();
```

## Gebruik van evenementenpayload {#using-event-payload}

Evenementen dragen een payload met informatie over wat er gebeurd is. Het lezen van die payload in de handler geeft je de relevante gegevens zonder een extra ronde naar de client.

Bijvoorbeeld, een `ModifyEvent` van een `TextField` bevat de huidige tekst van het veld. Je kunt de component daarvoor raadplegen:

```java
TextField field = new TextField("Zoeken");
field.onModify(event -> {
  String text = field.getText();
  // Gebruik tekst
});
```

Dezelfde waarde is al op het evenement, dus het lezen ervan vanuit de payload voorkomt dat je terug moet naar de component:

```java
field.onModify(event -> {
  String text = event.getText();
  // Gebruik tekst
});
```

Lees vanuit de payload waar een evenement de gegevens biedt die je nodig hebt. Voor meer informatie over waarom dit belangrijk is, zie [Client/Server Interactie](../architecture/client-server).

## Configureren van elementevenementen {#configuring-element-events}

Wanneer je direct werkt met een <JavadocLink type="foundation" location="com/webforj/component/element/Element" code='true'>Element</JavadocLink>, worden de evenementen geconfigureerd met <JavadocLink type="foundation" location="com/webforj/component/element/event/ElementEventOptions" code='true'>ElementEventOptions</JavadocLink>. Dit controleert welke gegevens het evenement meedraagt, of het überhaupt wordt geactiveerd en hoe vaak, allemaal geëvalueerd op de client voordat het evenement de server bereikt.

### Evenementgegevens {#event-data}

Evenementgegevens hechten waarden van de client aan het evenement, zodat informatie op de server beschikbaar is zonder een extra verzoek. Je voegt het toe met `addData()`, waarbij je elke invoer een sleutel en een JavaScript-expressie geeft die de waarde produceert.

Twee variabelen zijn beschikbaar binnen deze expressies: `event`, het client-evenementobject, en `component`, het element waaraan de luisteraar is bevestigd.

```java
ElementEventOptions options = new ElementEventOptions()
    .addData("value", "component.value")
    .addData("key", "event.key");
```

Op de server wordt elke waarde uit het evenement gelezen op basis van zijn sleutel.

### JavaScript uitvoeren {#executing-javascript}

`setCode()` voert een snippet van JavaScript op de client uit voordat het evenement wordt geactiveerd. Dit is nuttig voor het voorbereiden van evenementgegevens of voor het reageren op de client zonder dat er een rondreis naar de server nodig is.

```java
ElementEventOptions options = new ElementEventOptions()
    .setCode("event.target.value = event.target.value.trim();");
```

### Filteren van evenementen {#filtering-events}

`setFilter()` stelt een JavaScript-expressie in die beslist of het evenement wordt geactiveerd. Als deze vals evalueert, bereikt het evenement de server nooit. Dit is nuttig wanneer je alleen om een evenement geeft onder bepaalde voorwaarden, zoals een invoer die aan een minimale lengte voldoet.

```java
ElementEventOptions options = new ElementEventOptions()
    .setFilter("event.target.value.length > 2");
```

### Debouncing en throttling {#debouncing-and-throttling}

Debouncing en throttling beperken hoe vaak een evenement de server bereikt, wat nuttig is voor snelle evenementen zoals typen of scrollen.

Debouncing wacht tot de activiteit tot rust is gekomen voordat het wordt geactiveerd. `setDebounce()` neemt een timeout in milliseconden en een optionele <JavadocLink type="foundation" location="com/webforj/component/element/event/DebouncePhase" code='true'>DebouncePhase</JavadocLink>: `LEADING` wordt geactiveerd aan het begin van de burst, `TRAILING` wordt geactiveerd nadat deze eindigt, en `BOTH` wordt aan elke rand geactiveerd. Wanneer je de fase weglaat, is deze standaard `TRAILING`.

```java
ElementEventOptions options = new ElementEventOptions()
    .setDebounce(300, DebouncePhase.TRAILING);
```

Throttling wordt geactiveerd met een constante maximumsnelheid terwijl de activiteit aanhoudt. `setThrottle()` neemt een timeout in milliseconden.

```java
ElementEventOptions options = new ElementEventOptions()
    .setThrottle(300);
```

Een evenement gebruikt het een of het ander. Het instellen van een debounce wist elke throttle op dezelfde opties, en het instellen van een throttle wist elke debounce.

### Annotaties {#annotations}

Element evenementopties kunnen ook worden ingesteld met annotaties, wat een beknoptere manier is om een luisteraar te configureren. De `@EventOptions` annotatie bevat de gegevensinvoeren, samen met filter-, debounce- en throttle-instellingen.

```java
@EventOptions(
    data = {@EventData(key = "value", exp = "component.value")},
    debounce = @DebounceSettings(value = 200))
```

Wanneer je ook een `ElementEventOptions` op de aanroep locatie doorgeeft, worden de gegevens daarvan gecombineerd met de gegevens van de annotatie, en zijn de code, filter, debounce en throttle van de `ElementEventOptions` bepalend voor die van de annotatie.

## Je eigen evenementen versturen {#dispatching-your-own-events}

De evenementen die tot nu toe zijn behandeld komen van de component waarop je luistert. Een component die je schrijft kan op dezelfde manier eigen evenementen publiceren, zodat de code die het gebruikt kan reageren zonder in de interne werking van de component te tasten.

:::tip Wanneer je een aangepast evenement moet verzenden
Stuur een aangepast evenement wanneer jouw component beslist dat er iets is gebeurd, zoals een formulier dat een succesvolle indiening rapporteert of een editor die een opgeslagen record rapporteert. Evenementen die afkomstig zijn van een interactie met een client op een `Element` worden geconfigureerd met [element evenementopties](#configuring-element-events) in plaats daarvan.
:::

Componenten hebben geen evenementdispatcher, dus een component die zijn eigen evenementen publiceert, houdt zijn eigen <JavadocLink type="foundation" location="com/webforj/dispatcher/EventDispatcher" code='true'>EventDispatcher</JavadocLink> en publiceert via deze.

### Het evenement definiëren {#defining-the-event}

Definieer het evenement als een klasse die `EventObject` uitbreidt. Geef de bron, het object dat het evenement publiceert, door aan de superklasse, en voeg toegangsmethoden toe voor de gegevens die luisteraars nodig hebben.

```java
public class OrderSubmittedEvent extends EventObject {
  private final String orderId;
  private final double total;

  public OrderSubmittedEvent(Object source, String orderId, double total) {
    super(source);
    this.orderId = orderId;
    this.total = total;
  }

  public String getOrderId() {
    return orderId;
  }

  public double getTotal() {
    return total;
  }
}
```

Het lezen van de gegevens uit het evenement volgt dezelfde redenering als [gebruik van evenementenpayload](#using-event-payload). Luisteraars krijgen wat ze nodig hebben van het evenement in plaats van de bron achteraf te raadplegen.

### Registreren en dispatchen {#registering-and-dispatching}

Maak een dispatcher, registreer luisteraars voor een evenementtype, en dispatch een instantie van dat type wanneer het evenement zich voordoet. Registreren retourneert een `ListenerRegistration`, die je bewaart om de luisteraar later te verwijderen.

```java
EventDispatcher dispatcher = new EventDispatcher();

ListenerRegistration<OrderSubmittedEvent> registration =
    dispatcher.addListener(OrderSubmittedEvent.class, event -> {
      String id = event.getOrderId();
      // Verwerk het evenement
    });

dispatcher.dispatchEvent(new OrderSubmittedEvent(this, "ORD-1001", 49.99));
```

Elke luisteraar die voor dat evenementtype is geregistreerd, wordt uitgevoerd wanneer het evenement wordt gedispatched.

Een component die een evenement publiceert, houdt de dispatcher intern en biedt een `onXxx` methode aan in plaats van de dispatcher zelf, zodat aanroepers zich op dezelfde manier kunnen abonneren als voor een ingebouwd evenement:

```java
public ListenerRegistration<OrderSubmittedEvent> onSubmit(
    EventListener<OrderSubmittedEvent> listener) {
  return dispatcher.addListener(OrderSubmittedEvent.class, listener);
}
```

### Luisteraars verwijderen {#removing-listeners}

Verwijder een luisteraar via zijn registratie, of door de luisteraar terug te geven aan de dispatcher:

```java
registration.remove();

//OF

dispatcher.removeListener(OrderSubmittedEvent.class, registration.getListener());
```

Om alle luisteraars die zijn geregistreerd voor een evenementtype in één keer te wissen:

```java
dispatcher.removeAllListeners(OrderSubmittedEvent.class);
```

### Geheugentekorten vermijden {#avoiding-memory-leaks}

Een dispatcher houdt zijn luisteraars vast, en elke luisteraar houdt wat het heeft vastgelegd. Een lambda of interne klasse legt impliciet `this` vast, samen met eventuele lokale variabelen die het gebruikt, waardoor de objecten achter een luisteraar bereikbaar blijven zolang de dispatcher deze vasthoudt.

Dit wordt een probleem wanneer een luisteraar langer leeft dan hetgeen het verwijst. Als een dialoog een luisteraar registreert die zijn eigen model leest en sluit zonder deze te verwijderen, houdt de dispatcher nog steeds de luisteraar vast, houdt de luisteraar nog steeds de dialoog vast, en geen van beiden kan worden opgeruimd. In een app die veel kortlevende weergaven creëert, kunnen zo vastgehouden luisteraars zich ophopen.

Verwijder een luisteraar wanneer:

- Het object dat registreerde klaar is, zoals een gesloten dialoog of een weergave waar vanaf genavigeerd is.
- De abonnement was gekoppeld aan een kortlevende taak of een eenmalige stroom.

Bewaar de geretourneerde `ListenerRegistration` op een plaats waar je deze kunt bereiken tijdens opruimen in plaats van een luisteraar te registreren die je later niet kunt verwijderen. In een component is `onDidDestroy()` het opruimpunt.
