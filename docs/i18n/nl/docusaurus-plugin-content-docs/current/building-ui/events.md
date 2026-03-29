---
sidebar_position: 10
title: Events
slug: events
draft: false
_i18n_hash: c5c07ac4ca0f8d88ea6ef86afd5bb408
---
<JavadocLink type="foundation" location="com/webforj/component/event/Event" top='true'/>

Componenten, of ze nu op maat gemaakt zijn of deel uitmaken van het framework, ondersteunen evenementafhandeling. Je kunt event listeners toevoegen om verschillende soorten evenementen te registreren, zoals gebruikersinteracties, statuswijzigingen of andere aangepaste evenementen. Deze event listeners kunnen worden gebruikt om specifieke acties of gedragingen te activeren als reactie op de evenementen.

In het onderstaande voorbeeld wordt een evenement toegevoegd met behulp van elk van de drie ondersteunde methoden: lambda-expressies, anonieme klassen en methode-referenties.
## Evenementen toevoegen {#adding-events}

Het is mogelijk om een event listener toe te voegen met een van de volgende patronen waarbij:

- **`myComponent`** de component is waaraan je de event listener wilt koppelen.

- **`addEventListener`** wordt vervangen door de evenement-specifieke methode.

- **`EventListener`** wordt vervangen door het type evenement waarvoor wordt geluisterd.

```java
myComponent.addEventListener(e -> {
  //Uitgevoerd wanneer het evenement wordt geactiveerd
});

//OF

myComponent.addEventListener(new ComponentEventListener<EventListener>() {
  @Override
  public void onComponentEvent(ComponentEvent e){
    //Uitgevoerd wanneer het evenement wordt geactiveerd
  }
});

//OF

myComponent.addEventListener(this::eventMethod);
```

Extra syntactische suiker methoden, of aliassen, zijn toegevoegd om alternatieve toevoegingen van evenementen mogelijk te maken door gebruik te maken van het `on`-voorvoegsel gevolgd door het evenement, zoals:

```java
myComponent.onEvent(e -> {
  //Uitgevoerd wanneer het evenement wordt geactiveerd
});
```

## Een evenement verwijderen {#removing-an-event}

Bij het toevoegen van een event listener zal een `ListenerRegistration` object worden geretourneerd. Dit kan worden gebruikt, onder andere dingen, om het evenement later te verwijderen.

```java
//Het evenement toevoegen
ListenerRegistration listenerRegistration = myComponent.addEventListener(e -> {
    //Uitgevoerd wanneer het evenement wordt geactiveerd
  });

//Het evenement verwijderen
listenerRegistration.remove();
```

## Het gebruik van evenementpayload {#using-event-payload}

Het is belangrijk op te merken dat evenementen vaak gepaard gaan met een payload, die aanvullende informatie bevat met betrekking tot het evenement. Je kunt deze payload efficiënt gebruiken binnen de event handler om relevante gegevens te verkrijgen zonder onnodige rondreizen tussen de client en server. Door dit te doen, kun je de prestaties van je applicatie verbeteren.

De volgende code snippet vraagt de component op om informatie te krijgen die, voor onze demonstratiedoeleinden, al is opgenomen in de evenementpayload, wat representeert inefficiënte code:

```java
myComponent.addEventListener(e -> {
  // Toegang tot gegevens van de component
  String componentText = e.getComponent().getText();

  //OF als de component binnen de scope van de functie toegankelijk is
  String componentText = myComponent.getText();

  // Gebruik de componentText om andere acties uit te voeren.
});
```

In plaats daarvan, door gebruik te maken van de payload van de methode, die voor het voorbeeld de tekst van de component bevat, wordt een rondreis vermeden:

```java
myComponent.addEventListener(e -> {
  // Toegang tot gegevens van de evenementpayload
  String componentText = e.getText();
  
  // Gebruik de componentText om andere acties uit te voeren.
});
```

Deze aanpak minimaliseert de noodzaak om de component op informatie te ondervragen, aangezien de gegevens direct beschikbaar zijn in de evenementpayload. Door deze efficiënte praktijk van evenementafhandeling te volgen, kun je de prestaties en responsiviteit van je componenten verbeteren. Voor meer informatie kun je verwijzen naar [Client/Server Interactie](../architecture/client-server).

### Voorbeeld {#sample}

Hieronder wordt een demonstratie getoond die de toevoeging van een <JavadocLink type="foundation" location="com/webforj/component/button/event/ButtonClickEvent"  code="true">ButtonClickEvent</JavadocLink> aan een [`Button`](#) laat zien. Deze [`Button`](#) maakt ook gebruik van informatie die met de payload van het evenement wordt meegeleverd om informatie op het scherm weer te geven.

<ComponentDemo 
path='/webforj/buttonevent?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/button/ButtonEventView.java'
height='100px'
/>
