---
sidebar_position: 10
title: Events
slug: events
draft: false
_i18n_hash: 620635097d0620cc0cd4a639b0d29d97
---
<JavadocLink type="foundation" location="com/webforj/component/event/Event" top='true'/>

Componenten, of ze nu op maat gemaakt zijn of deel uitmaken van het framework, ondersteunen gebeurtenisafhandeling. Je kunt gebeurtenisluisteraars toevoegen om verschillende soorten gebeurtenissen vast te leggen, zoals gebruikersinteracties, wijzigingen in de status of andere aangepaste gebeurtenissen. Deze gebeurtenisluisteraars kunnen worden gebruikt om specifieke acties of gedragingen te triggeren als reactie op deze gebeurtenissen.

In het onderstaande voorbeeld wordt een gebeurtenis toegevoegd met behulp van elk van de drie ondersteunde methoden: lambda-expressies, anonieme klassen en methode-referenties.
## Toevoegen van gebeurtenissen {#adding-events}

Het is mogelijk om een gebeurtenisluisteraar toe te voegen met een van de volgende patronen, waarbij:

- **`myComponent`** de component is waaraan je de gebeurtenisluisteraar wilt koppelen.

- **`addEventListener`** wordt vervangen door de gebeurtenis-specifieke methode.

- **`EventListener`** wordt vervangen door het type gebeurtenis waarnaar geluisterd wordt.

```java
myComponent.addEventListener(e -> {
  //Uitgevoerd wanneer de gebeurtenis plaatsvindt
});

//OF

myComponent.addEventListener(new ComponentEventListener<EventListener>() {
  @Override
  public void onComponentEvent(ComponentEvent e){
    //Uitgevoerd wanneer de gebeurtenis plaatsvindt
  }
});

//OF

myComponent.addEventListener(this::eventMethod);
```

Extra syntactische suiker methoden, of aliassen, zijn toegevoegd om alternatieve toevoegingen van gebeurtenissen mogelijk te maken door de `on`-prefix te gebruiken, gevolgd door de gebeurtenis, zoals:

```java
myComponent.onEvent(e -> {
  //Uitgevoerd wanneer de gebeurtenis plaatsvindt
});
```

## Verwijderen van een gebeurtenis {#removing-an-event}

Wanneer je een gebeurtenisluisteraar toevoegt, wordt een `ListenerRegistration` object geretourneerd. Dit kan worden gebruikt, onder andere, om de gebeurtenis later te verwijderen.

```java
//De gebeurtenis toevoegen
ListenerRegistration listenerRegistration = myComponent.addEventListener(e -> {
        //Uitgevoerd wanneer de gebeurtenis plaatsvindt
    });

//De gebeurtenis verwijderen
listenerRegistration.remove();
```

## Gebruik van gebeurtenispayload {#using-event-payload}

Het is belangrijk op te merken dat gebeurtenissen vaak komen met een payload, die extra informatie bevat met betrekking tot de gebeurtenis. Je kunt deze payload efficiënt gebruiken binnen de gebeurtenis-handler om relevante gegevens te verkrijgen zonder onnodige ronden tussen de client en de server. Door dit te doen, kun je de prestatie van je toepassing verbeteren.

De volgende codefragment vraagt de component om informatie te verkrijgen die, voor onze demonstratie, al is opgenomen in de gebeurtenispayload, wat ondoelmatig code vertegenwoordigt:

```java
myComponent.addEventListener(e -> {
  // Toegang tot gegevens van de component
  String componentText = e.getComponent().getText();

  //OF als de component toegankelijk is binnen de scope van de functie
  String componentText = myComponent.getText();

  // Gebruik de componentText om andere acties uit te voeren.
});
```

In plaats daarvan, door gebruik te maken van de payload van de methode, die voor het voorbeeld de tekst van de component omvat, wordt een ronde reis vermeden:

```java
myComponent.addEventListener(e -> {
  // Toegang tot gegevens van de gebeurtenispayload
  String componentText = e.getText();
  
  // Gebruik de componentText om andere acties uit te voeren.
});
```

Deze aanpak minimaliseert de behoefte om de component te ondervragen voor informatie, aangezien de gegevens direct beschikbaar zijn in de gebeurtenispayload. Door deze efficiënte praktijken voor gebeurtenisafhandeling te volgen, kun je de prestaties en responsiviteit van je componenten verbeteren. Voor meer informatie kun je verwijzen naar [Client/Server Interactie](../architecture/client-server).

### Voorbeeld {#sample}

Hieronder staat een demonstratie die de toevoeging van een <JavadocLink type="foundation" location="com/webforj/component/button/event/ButtonClickEvent"  code="true">ButtonClickEvent</JavadocLink> aan een [`Button`](#) toont. Deze  [`Button`](#) gebruikt ook informatie die met de payload van de gebeurtenis komt om informatie op het scherm weer te geven.

<ComponentDemo 
path='/webforj/buttonevent?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/button/ButtonEventView.java'
height='100px'
/>
