---
sidebar_position: 10
title: Events
slug: events
draft: false
_i18n_hash: 6c1d6fc7f2d8e0027320e0323b107dca
---
<JavadocLink type="foundation" location="com/webforj/component/event/Event" top='true'/>

Componenten, of ze nu op maat gemaakt zijn of deel uitmaken van het framework, ondersteunen gebeurtenisafhandeling. Je kunt gebeurtenislisteners toevoegen om verschillende soorten gebeurtenissen vast te leggen, zoals gebruikersinteracties, statuswijzigingen of andere aangepaste gebeurtenissen. Deze gebeurtenislisteners kunnen worden gebruikt om specifieke acties of gedragingen in reactie op de gebeurtenissen te triggeren.

In het onderstaande voorbeeld wordt een gebeurtenis toegevoegd met elk van de drie ondersteunde methoden: lambda-expressies, anonieme klassen en methode-referenties.
## Gebeurtenissen toevoegen {#adding-events}

Het toevoegen van een gebeurtenislistener is mogelijk met een van de volgende patronen waarbij:

- **`myComponent`** de component is waaraan je de gebeurtenislistener wilt koppelen.

- **`addEventListener`** wordt vervangen door de gebeurtenisspecifieke methode.

- **`EventListener`** wordt vervangen door het type gebeurtenis waarvoor geluisterd wordt.

```java
myComponent.addEventListener(e -> {
  //G uitgevoerd wanneer de gebeurtenis wordt geactiveerd
});

//OF

myComponent.addEventListener(new ComponentEventListener<EventListener>() {
  @Override
  public void onComponentEvent(ComponentEvent e){
    //G uitgevoerd wanneer de gebeurtenis wordt geactiveerd
  }
});

//OF

myComponent.addEventListener(this::eventMethod);
```

Aanvullende syntactische suikermethoden, of aliassen, zijn toegevoegd om alternatieve toevoeging van gebeurtenissen mogelijk te maken door gebruik te maken van het `on` voorvoegsel gevolgd door de gebeurtenis, zoals:

```java
myComponent.onEvent(e -> {
  //G uitgevoerd wanneer de gebeurtenis wordt geactiveerd
});
```

## Een gebeurtenis verwijderen {#removing-an-event}

Wanneer je een gebeurtenislistener toevoegt, wordt er een `ListenerRegistration` object geretourneerd. Dit kan onder andere worden gebruikt om de gebeurtenis later te verwijderen.

```java
//De gebeurtenis toevoegen
ListenerRegistration listenerRegistration = myComponent.addEventListener(e -> {
    //G uitgevoerd wanneer de gebeurtenis wordt geactiveerd
  });

//De gebeurtenis verwijderen
listenerRegistration.remove();
```

## Gebruik van gebeurtenispayload {#using-event-payload}

Het is belangrijk op te merken dat gebeurtenissen vaak een payload met zich meebrengen, die aanvullende informatie bevat die gerelateerd is aan de gebeurtenis. Je kunt deze payload efficiënt benutten binnen de gebeurtenishandler om relevante gegevens te verkrijgen zonder onnodige ritten tussen de klant en de server. Hierdoor kun je de prestaties van je applicatie verbeteren.

De volgende code snippet vraagt de component op om informatie te verkrijgen die, voor onze demonstratiedoeleinden, al is opgenomen in de gebeurtenispayload, wat inefficiënte code vertegenwoordigt:

```java
myComponent.addEventListener(e -> {
  // Toegang tot gegevens van de component
  String componentText = e.getComponent().getText();

  //OF als de component toegankelijk is binnen de scope van de functie
  String componentText = myComponent.getText();

  // Gebruik de componentText om andere acties uit te voeren.
});
```

In plaats daarvan, door gebruik te maken van de payload van de methode, die voor de voorbeeld het tekst van de component omvat, wordt een ronde rit vermeden:

```java
myComponent.addEventListener(e -> {
  // Toegang tot gegevens van de gebeurtenispayload
  String componentText = e.getText();
  
  // Gebruik de componentText om andere acties uit te voeren.
});
```

Deze aanpak minimaliseert de noodzaak om de component op te vragen voor informatie, aangezien de gegevens direct beschikbaar zijn in de gebeurtenispayload. Door deze efficiënte gebeurtenisafhandelingspraktijk te volgen, kun je de prestaties en responsiviteit van je componenten verbeteren. Voor meer informatie kun je verwijzen naar [Client/Server Interaction](../architecture/client-server).

### Voorbeeld {#sample}

Hieronder staat een demonstratie die de toevoeging van een <JavadocLink type="foundation" location="com/webforj/component/button/event/ButtonClickEvent"  code="true">ButtonClickEvent</JavadocLink> aan een [`Button`](#) laat zien. Deze [`Button`](#) gebruikt ook informatie die met de payload van de gebeurtenis komt om informatie op het scherm weer te geven.

<ComponentDemo
path='/webforj/buttonevent'
files={['src/main/java/com/webforj/samples/views/button/ButtonEventView.java']}
height='100px'
/>
