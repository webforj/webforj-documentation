---
sidebar_position: 10
title: Events
slug: events
draft: false
_i18n_hash: 35a5057106e5fe7f270cbadaff74b924
---
<JavadocLink type="foundation" location="com/webforj/component/event/Event" top='true'/>

Componenten, of ze nu aangepast zijn of deel uitmaken van het framework, ondersteunen evenementafhandeling. Je kunt event listeners toevoegen om verschillende soorten evenementen vast te leggen, zoals gebruikersinteracties, wijzigingen in de status of andere aangepaste evenementen. Deze event listeners kunnen worden gebruikt om specifieke acties of gedragingen te activeren als reactie op de evenementen.

In het onderstaande voorbeeld wordt een evenement toegevoegd met behulp van elk van de drie ondersteunde methoden: lambda-expressies, anonieme klassen en methode-referenties.  
## Evenementen toevoegen {#adding-events}

Een event listener toevoegen is mogelijk met een van de volgende patronen, waarbij:

- **`myComponent`** de component is waaraan je de event listener wilt koppelen.

- **`addEventListener`** wordt vervangen door de gebeurtenisspecifieke methode.

- **`EventListener`** wordt vervangen door het type evenement waarvoor geluisterd wordt.

```java
myComponent.addEventListener(e -> {
  //Uitgevoerd wanneer het evenement plaatsvindt
});

//OF

myComponent.addEventListener(new ComponentEventListener<EventListener>() {
  @Override
  public void onComponentEvent(ComponentEvent e){
    //Uitgevoerd wanneer het evenement plaatsvindt
  }
});

//OF

myComponent.addEventListener(this::eventMethod);
```

Extra syntactische suiker-methoden, of aliassen, zijn toegevoegd om alternatieve toevoegingen van evenementen mogelijk te maken door gebruik te maken van het `on`-prefix gevolgd door het evenement, zoals:

```java
myComponent.onEvent(e -> {
  //Uitgevoerd wanneer het evenement plaatsvindt
});
```

## Een evenement verwijderen {#removing-an-event}

Bij het toevoegen van een event listener wordt een `ListenerRegistration`-object geretourneerd. Dit kan onder andere worden gebruikt om het evenement later te verwijderen.

```java
//Het evenement toevoegen
ListenerRegistration listenerRegistration = myComponent.addEventListener(e -> {
        //Uitgevoerd wanneer het evenement plaatsvindt
    });

//Het evenement verwijderen
listenerRegistration.remove();
```

## Gebruik van gebeurtenisspecifieke gegevens {#using-event-payload}

Het is belangrijk op te merken dat evenementen vaak gepaard gaan met een payload, die aanvullende informatie bevat die gerelateerd is aan het evenement. Je kunt deze payload efficiënt benutten binnen de event handler om toegang te krijgen tot relevante gegevens zonder onnodige rondreizen tussen de client en de server. Door dit te doen, kun je de prestaties van je applicatie verbeteren.

De volgende codefragment vraagt de component op om informatie te verkrijgen die, voor onze demonstratie, al is opgenomen in de event payload, wat inefficiënte code vertegenwoordigt:

```java
myComponent.addEventListener(e -> {
  //Toegang tot gegevens van de component
  String componentText = e.getComponent().getText();

  //OF als de component toegankelijk is binnen de scope van de functie
  String componentText = myComponent.getText();

  //Gebruik de componentText om andere acties uit te voeren.
});
```

In plaats daarvan, door de payload van de methode te benutten, die in dit voorbeeld de tekst van de component bevat, wordt een rondreis vermeden:

```java
myComponent.addEventListener(e -> {
  //Toegang tot gegevens van de event payload
  String componentText = e.getText();
  
  //Gebruik de componentText om andere acties uit te voeren.
});
```

Deze benadering minimaliseert de noodzaak om de component op te vragen voor informatie, aangezien de gegevens onmiddellijk beschikbaar zijn in de event payload. Door deze efficiënte praktijk voor evenementafhandeling te volgen, kun je de prestaties en responsiviteit van je componenten verbeteren. Voor meer informatie kun je verwijzen naar [Client/Server Interactie](../architecture/client-server).

### Voorbeeld {#sample}

Hieronder staat een demonstratie die de toevoeging van een <JavadocLink type="foundation" location="com/webforj/component/button/event/ButtonClickEvent"  code="true">ButtonClickEvent</JavadocLink> aan een [`Button`](#) laat zien. Deze  [`Button`](#) gebruikt ook informatie die met de payload van het evenement komt om informatie op het scherm weer te geven.

<ComponentDemo 
path='/webforj/buttonevent?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/button/ButtonEventView.java'
height='100px'
/>

<!-- <EventTable base events={['drawerOpen', 'drawerClose']} /> -->
