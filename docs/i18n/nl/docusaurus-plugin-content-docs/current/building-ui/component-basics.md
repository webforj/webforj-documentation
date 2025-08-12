---
sidebar_position: 1
title: Component Basics
slug: basics
draft: false
_i18n_hash: e4d0cb9dd9f53dabda8bebe6664bf0d3
---
<DocChip chip='since' label='23.05' />
<JavadocLink type="foundation" location="com/webforj/component/Component" top='true'/>

Componenten zijn fundamentele bouwstenen die aan een venster kunnen worden toegevoegd en gebruikersinterfacefunctionaliteit en aangepaste gedrag bieden. In webforJ dient de `Component`-klasse als basis voor alle componenten binnen de engine.

## Lifecycle management {#lifecycle-management}

Het begrijpen van de levenscyclus van een component is essentieel voor het effectief creëren, beheren en gebruiken van componenten. De volgende twee levenscyclusstatussen hebben methoden om hun gedrag te manipuleren. Deze methoden mogen niet expliciet door de gebruiker worden aangeroepen.

### Create and destroy hooks {#create-and-destroy-hooks}

Alle klassen die de `Component`-klasse uitbreiden, zijn verantwoordelijk voor het implementeren van de functionaliteit die moet worden uitgevoerd wanneer de `Component` wordt aangemaakt en wanneer deze wordt vernietigd. Dit gebeurt door de `onCreate()`- en `onDestroy()`-methoden respectievelijk te overschrijven.

#### `onCreate()` {#oncreate}

De `onCreate()`-methode wordt aangeroepen wanneer de component aan een venster wordt toegevoegd. Het creëren van componenten houdt in dat hun initiële staat en functionaliteit worden ingesteld. Dit is waar je definieert wat de component moet doen wanneer deze voor het eerst wordt aangemaakt. Of het nu gaat om het initialiseren van variabelen, het instellen van gebeurtenislusjes of het uitvoeren van andere configuraties, de `onCreate()`-methode is jouw toegangspunt voor het aanpassen van het gedrag van de component.

Deze hook ontvangt een vensterinstantie waarmee componenten kunnen worden toegevoegd die binnen de component zijn.

```java
@Override
protected void onCreate(Window window) {
  TextField text = new TextField();
  Button btn = new Button();

  window.add(text, btn);
}
```

:::tip
De `onCreate()`-methode is waar de component en eventuele onderdelen aan het venster moeten worden toegevoegd.
:::

#### `onDestroy()` {#ondestroy}

Het vernietigen van componenten is een essentieel onderdeel van het beheren van middelen en het waarborgen van een goede opruiming. Het vernietigen van een component is noodzakelijk wanneer deze niet langer nodig is of wanneer je middelen wilt vrijgeven die ermee zijn geassocieerd. Het stelt een ontwikkelaar in staat om opruimtaken uit te voeren, zoals het stoppen van timers, het vrijgeven van geheugen of het loskoppelen van gebeurtenislusjes. Het stelt ook de `destroy()`-methode in staat om te worden aangeroepen op eventuele onderdeelcomponenten.

:::tip
De `onDestroy()`-methode is verantwoordelijk voor het aanroepen van de `destroy()`-methode op eventuele onderdeelcomponenten. Anders zullen deze componenten nog steeds bestaan in de DOM, maar zullen ze niet bereikbaar zijn via de API.
:::

### Asynchronous attachment {#asynchronous-attachment}

De `whenAttached()`-methode stelt functionaliteit in staat om te worden uitgevoerd nadat een component aan een venster is toegevoegd. Deze methode retourneert een <JavadocLink type="foundation" location="com/webforj/PendingResult" code='true'>PendingResult</JavadocLink>, waarmee aanvullende gespecificeerde gedrag asynchroon kan worden uitgevoerd zodra de component aan de DOM is gehecht.

:::tip
In tegenstelling tot de voorgaande drie methoden, is `whenAttached()` bedoeld om expliciet door de gebruiker te worden aangeroepen.
:::

```java
public class Demo extends App {
  @Override
  public void run() throws WebforjException {
    Frame window = new Frame();

    Button button = new Button(); 

    /* Expliciete aanroep van whenAttached() die een 
    berichtenvenster weergeeft wanneer de knop aan het Frame is gehecht.*/
    button.whenAttached().thenAccept( e -> {
      showMessageDialog("Ik ben gehecht!", "Asynchrone hechting");
    });
  
    // onCreate()-methode wordt aangeroepen
    window.add(button); 
  }
}
```

### Observers {#observers}

Observers spelen een essentiële rol bij het bijhouden van gebeurtenissen in de levenscyclus van componenten. Observers kunnen worden toegevoegd en verwijderd met de methoden `addLifecycleObserver()` en `removeLifecycleObserver()`, en ontvangen meldingen over gebeurtenissen zoals het creëren en vernietigen van componenten.

Door observers toe te voegen, kun je actie ondernemen wanneer een component wordt aangemaakt of vernietigd. Dit is bijzonder nuttig voor het implementeren van aangepaste logica of het afhandelen van specifieke scenario's op basis van componentgebeurtenissen.

```java
Button button = new Button();
button.addLifecycleObserver((button, lifecycleEvent) -> {
  if (lifecycleEvent == ComponentLifecycleObserver.LifecycleEvent.DESTROY) {
    // geïmplementeerde logica om uit te voeren wanneer de knop wordt vernietigd
  }
});
```

## Component properties {#component-properties}

### Component identifiers {#component-identifiers}

Component-ID's dienen als unieke identificatoren voor componenten, waardoor je effectief met hen kunt interageren en hun status kunt beheren.

#### Server-side component ID {#server-side-component-id}

Elke component die is gemaakt uit de `Component`-klasse krijgt automatisch een serverzijde identificator toegewezen. Serverzijde-ID's zijn essentieel voor interne tracking en identificatie van componenten binnen het framework. Je kunt de serverzijde component-ID ophalen met de methode `getComponentId()`.

Dit kan nuttig zijn in veel situaties waarin het hebben van een unieke, server-zijde identificator noodzakelijk is, zoals het opvragen van een specifieke component binnen een container.

#### Client-side component ID {#client-side-component-id}

Client-side ID's stellen de gebruiker in staat om de clientrepresentatie van de servercomponent die in Java is gemaakt, te verkrijgen. Alle aangeboden webforJ-componenten hebben een implementatie van deze ID. Als je toegang wilt verkrijgen tot en gebruik wilt maken van de client-side component, kun je `object.get()` uitvoeren met de client-ID om de gewenste clientcomponent te verkrijgen.

:::important
Deze ID is **niet** de ID-attribuut van het element in de DOM.
:::

In het onderstaande voorbeeld wordt een `onClick`-evenement toegevoegd aan een knop, die vervolgens wordt geactiveerd door de methode op de clientcomponent aan te roepen nadat deze is verkregen met de `object.get()`-methode.

```java
@Override
public void run() throws WebforjException {
  Frame frame = new Frame();
  Button btn = new Button("Klik op mij");
  btn.onClick(e -> {
    showMessageDialog("De knop is geklikt", "Een gebeurtenis is opgetreden");
  });

  btn.whenAttached().thenAccept(e -> {
    getPage().executeJs("objects.get('" + btn.getClientComponentId() + "').click()");
  });
  frame.add(btn);
}
```

### User data {#user-data}

De `Component`-klasse stelt je in staat om aanvullende informatie binnen de component op te nemen met de methode `setUserData()`. Deze informatie is alleen toegankelijk aan de serverzijde van de component via de methode `getUserData()`, en wordt niet naar de client verzonden.

Dit is bijzonder nuttig wanneer er informatie is die met een component moet worden opgenomen, en wanneer deze informatie toegankelijk moet zijn zonder naar de client te hoeven gaan om deze op te halen.
