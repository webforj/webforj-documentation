---
sidebar_position: 10
title: Events
description: >-
  Attach event listeners to webforJ components with lambdas, anonymous classes,
  or method references and reuse the event payload server-side.
slug: events
draft: false
_i18n_hash: e965d354159ccc38ad417700fc3686eb
---
<JavadocLink type="foundation" location="com/webforj/component/event/Event" top='true'/>

Componenten, of ze nu op maat gemaakt zijn of deel uitmaken van het framework, ondersteunen gebeurtenisverwerking. Je kunt gebeurtenislijsten toevoegen om verschillende soorten gebeurtenissen vast te leggen, zoals gebruikersinteracties, veranderingen in de status, of andere aangepaste gebeurtenissen. Deze gebeurtenislijsten kunnen worden gebruikt om specifieke acties of gedragingen te activeren in reactie op de gebeurtenissen.

In het onderstaande voorbeeld wordt een gebeurtenis toegevoegd met behulp van elk van de drie ondersteunde methoden: lambda-expressies, anonieme klassen en methode-referenties.
## Toevoegen van gebeurtenissen {#adding-events}

Het is mogelijk om een gebeurtenislijst toe te voegen met een van de volgende patronen waar:

- **`myComponent`** is het component waaraan je de gebeurtenislijst wilt koppelen.

- **`addEventListener`** wordt vervangen door de gebeurtenisspecifieke methode.

- **`EventListener`** wordt vervangen door het type gebeurtenis waarvoor geluisterd wordt.

```java
myComponent.addEventListener(e -> {
  //Uitgevoerd wanneer de gebeurtenis wordt geactiveerd
});

//OF

myComponent.addEventListener(new ComponentEventListener<EventListener>() {
  @Override
  public void onComponentEvent(ComponentEvent e){
    //Uitgevoerd wanneer de gebeurtenis wordt geactiveerd
  }
});

//OF

myComponent.addEventListener(this::eventMethod);
```

Aanvullende syntactische suiker methoden, of aliassen, zijn toegevoegd om alternatieve toevoegingen van gebeurtenissen mogelijk te maken door het voorvoegsel `on` te gebruiken, gevolgd door de gebeurtenis, zoals:

```java
myComponent.onEvent(e -> {
  //Uitgevoerd wanneer de gebeurtenis wordt geactiveerd
});
```

## Verwijderen van een gebeurtenis {#removing-an-event}

Wanneer je een gebeurtenislijst toevoegt, wordt er een `ListenerRegistration` object teruggegeven. Dit kan worden gebruikt, onder andere dingen, om de gebeurtenis later te verwijderen.

```java
//De gebeurtenis toevoegen
ListenerRegistration listenerRegistration = myComponent.addEventListener(e -> {
    //Uitgevoerd wanneer de gebeurtenis wordt geactiveerd
  });

//De gebeurtenis verwijderen
listenerRegistration.remove();
```

## Gebruik van gebeurtenispayload {#using-event-payload}

Het is belangrijk op te merken dat gebeurtenissen vaak gepaard gaan met een payload, die extra informatie bevat die verband houdt met de gebeurtenis. Je kunt deze payload efficiënt benutten binnen de gebeurtenishandler om relevante gegevens te verkrijgen zonder onnodige roundtrips tussen de client en de server. Door dit te doen, kun je de prestaties van je applicatie verbeteren.

De volgende codefragment vraagt het component op om informatie te verkrijgen die, voor onze demonstratiedoeleinden, al is opgenomen in de gebeurtenispayload, wat inefficiënt code vertegenwoordigt:

```java
myComponent.addEventListener(e -> {
  // Toegang tot gegevens van het component
  String componentText = e.getComponent().getText();

  //OF als het component toegankelijk is binnen de scope van de functie
  String componentText = myComponent.getText();

  // Gebruik de componentText voor andere acties.
});
```

In plaats daarvan, door de payload van de methode te benutten, die voor het voorbeeld de tekst van het component bevat, wordt een roundtrip vermeden:

```java
myComponent.addEventListener(e -> {
  // Toegang tot gegevens van de gebeurtenispayload
  String componentText = e.getText();

  // Gebruik de componentText voor andere acties.
});
```

Deze aanpak minimaliseert de behoefte om het component op te vragen voor informatie, aangezien de gegevens direct beschikbaar zijn in de gebeurtenispayload. Door deze efficiënte gebeurtenisverwerkingspraktijk te volgen, kun je de prestaties en responsiviteit van je componenten verbeteren. Voor meer informatie kun je verwijzen naar [Client/Server Interactie](../architecture/client-server).

### Voorbeeld {#sample}

Hieronder staat een demonstratie die de toevoeging van een <JavadocLink type="foundation" location="com/webforj/component/button/event/ButtonClickEvent"  code="true">ButtonClickEvent</JavadocLink> aan een [`Button`](#) toont. Deze [`Button`](#) gebruikt ook informatie die gepaard gaat met de payload van de gebeurtenis om informatie op het scherm weer te geven.

<ComponentDemo
path='/webforj/buttonevent'
files={['src/main/java/com/webforj/samples/views/button/ButtonEventView.java']}
height='100px'
/>
