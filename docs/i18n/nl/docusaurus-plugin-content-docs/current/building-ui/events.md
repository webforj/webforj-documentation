---
sidebar_position: 10
title: Events
slug: events
draft: false
_i18n_hash: 3896ad417b6309ffbfbc46b2f893589b
---
<JavadocLink type="foundation" location="com/webforj/component/event/Event" top='true'/>

Componenten, of ze nu aangepast zijn of onderdeel van het framework, ondersteunen gebeurtenisverwerking. Je kunt gebeurtenisluisteraars toevoegen om verschillende soorten gebeurtenissen te vangen, zoals gebruikersinteracties, veranderingen in staat, of andere aangepaste gebeurtenissen. Deze gebeurtenisluisteraars kunnen worden gebruikt om specifieke acties of gedrag uit te voeren als reactie op de gebeurtenissen.

In het onderstaande voorbeeld wordt een gebeurtenis toegevoegd met behulp van elk van de drie ondersteunde methoden: lambda-expressies, anonieme klassen en methodereferenties.
## Evenementen toevoegen {#adding-events}

Een gebeurtenisluisteraar toevoegen is mogelijk met een van de volgende patronen, waarbij:

- **`myComponent`** de component is waaraan je de gebeurtenisluisteraar wilt koppelen.

- **`addEventListener`** wordt vervangen door de gebeurtenis-specifieke methode.

- **`EventListener`** wordt vervangen door het type gebeurtenis dat wordt beluisterd.

```java
myComponent.addEventListener(e -> {
  //Wordt uitgevoerd wanneer de gebeurtenis wordt geactiveerd
});

//OF

myComponent.addEventListener(new ComponentEventListener<EventListener>() {
  @Override
  public void onComponentEvent(ComponentEvent e){
    //Wordt uitgevoerd wanneer de gebeurtenis wordt geactiveerd
  }
});

//OF

myComponent.addEventListener(this::eventMethod);
```

Extra syntactische suiker methoden, of aliassen, zijn toegevoegd om alternatieve toevoeging van gebeurtenissen mogelijk te maken door gebruik te maken van het `on`-voorvoegsel, gevolgd door de gebeurtenis, zoals:

```java
myComponent.onEvent(e -> {
  //Wordt uitgevoerd wanneer de gebeurtenis wordt geactiveerd
});
```

## Een gebeurtenis verwijderen {#removing-an-event}

Bij het toevoegen van een gebeurtenisluisteraar wordt een `ListenerRegistration` object geretourneerd. Dit kan onder andere worden gebruikt om de gebeurtenis later te verwijderen.

```java
//De gebeurtenis toevoegen
ListenerRegistration listenerRegistration = myComponent.addEventListener(e -> {
        //Wordt uitgevoerd wanneer de gebeurtenis wordt geactiveerd
    });

//De gebeurtenis verwijderen
listenerRegistration.remove();
```

## Het gebruik van gebeurtenispayload {#using-event-payload}

Het is belangrijk op te merken dat gebeurtenissen vaak met een payload komen, die aanvullende informatie bevat die gerelateerd is aan de gebeurtenis. Je kunt deze payload efficiënt gebruiken binnen de gebeurtenis-handler om relevante gegevens te krijgen zonder onnodige ronden tussen de client en server. Door dit te doen, kun je de prestaties van je applicatie verbeteren.

De volgende code snippet vraagt de component op om informatie te krijgen die, ter illustratie van onze demonstratie, al is opgenomen in de gebeurtenispayload, wat inefficient code vertegenwoordigt:

```java
myComponent.addEventListener(e -> {
  // Toegang tot gegevens van de component
  String componentText = e.getComponent().getText();

  //OF als de component binnen de scope van de functie toegankelijk is
  String componentText = myComponent.getText();

  // Gebruik de componentText om andere acties uit te voeren.
});
```

In plaats daarvan, door gebruik te maken van de payload van de methode, die voor de illustratie de tekst van de component omvat, wordt een ronde reis vermeden:

```java
myComponent.addEventListener(e -> {
  // Toegang tot gegevens uit de gebeurtenispayload
  String componentText = e.getText();
  
  // Gebruik de componentText om andere acties uit te voeren.
});
```

Deze aanpak minimaliseert de noodzaak om de component op te vragen voor informatie, aangezien de gegevens direct beschikbaar zijn in de gebeurtenispayload. Door deze efficiënte gebeurtenisverwerkingspraktijk te volgen, kun je de prestaties en responsiviteit van je componenten verbeteren. Voor meer informatie kun je verwijzen naar [Client/Server Interaction](../architecture/client-server).

### Voorbeeld {#sample}

Hieronder vind je een demonstratie die de toevoeging toont van een <JavadocLink type="foundation" location="com/webforj/component/button/event/ButtonClickEvent"  code="true">ButtonClickEvent</JavadocLink> aan een [`Button`](#). Deze  [`Button`](#) maakt ook gebruik van informatie die afkomstig is van de payload van de gebeurtenis om informatie op het scherm weer te geven.

<ComponentDemo 
path='/webforj/buttonevent?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/button/ButtonEventView.java'
height='100px'
/>

<!-- <EventTable base events={['drawerOpen', 'drawerClose']} /> -->
