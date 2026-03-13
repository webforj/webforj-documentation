---
sidebar_position: 3
title: Component Basics
slug: basics
draft: false
_i18n_hash: 0a9127dc9219a32aeb1eef280b386d77
---
<JavadocLink type="foundation" location="com/webforj/component/Component" top='true'/>

Componenten zijn fundamentele bouwstenen die aan een venster kunnen worden toegevoegd en gebruikersinterfacefunctionaliteit en aangepaste gedragingen bieden. In webforJ dient de `Component`-klasse als de basis voor alle componenten binnen de engine.

## Lifecycle management {#lifecycle-management}

Het begrijpen van de levenscyclus van componenten is essentieel voor het effectief creëren, beheren en gebruiken van componenten. De volgende twee levenscyclusstaten hebben methoden om hun gedrag te manipuleren. Deze methoden mogen niet expliciet door de gebruiker worden aangeroepen.

### Create and destroy hooks {#create-and-destroy-hooks}

Alle klassen die de `Component`-klasse uitbreiden zijn verantwoordelijk voor het implementeren van de functionaliteit die moet worden uitgevoerd wanneer de `Component` wordt gemaakt en wanneer deze wordt vernietigd. Dit wordt gedaan door respectievelijk de `onCreate()`- en `onDestroy()`-methoden te overschrijven.

#### `onCreate()` {#oncreate}

De `onCreate()`-methode wordt aangeroepen wanneer de component aan een venster wordt toegevoegd. Het creëren van componenten houdt in dat hun initiële toestand en functionaliteit worden ingesteld. Dit is waar je definieert wat de component moet doen wanneer deze voor het eerst wordt gemaakt. Of het nu gaat om het initialiseren van variabelen, het instellen van gebeurtenislisters of het uitvoeren van enige andere opstelling, de `onCreate()`-methode is je toegangspunt voor het aanpassen van het gedrag van de component.

Deze hook ontvangt een vensterinstantie waarmee componenten die binnen de component zijn contained, kunnen worden toegevoegd.

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

Het vernietigen van componenten is een essentieel onderdeel van het beheren van middelen en het waarborgen van een goede opruiming. Het vernietigen van een component is noodzakelijk wanneer deze niet langer nodig is of wanneer je bronnen wilt vrijgeven die eraan zijn gekoppeld. Het stelt een ontwikkelaar in staat om opruimtaken uit te voeren, zoals het stoppen van timers, het vrijgeven van geheugen of het loskoppelen van gebeurtenislisters. Het stelt ook de `destroy()`-methode in staat om op eventuele constituent-componenten te worden aangeroepen.

:::tip
De `onDestroy()`-methode is verantwoordelijk voor het aanroepen van de `destroy()`-methode op eventuele constituent-componenten. Anders blijven deze componenten bestaan in de DOM, maar zijn ze niet bereikbaar via de API.
:::

### Asynchronous attachment {#asynchronous-attachment}

De `whenAttached()`-methode stelt in staat om functionaliteit uit te voeren nadat een component aan een venster is toegevoegd. Deze methode retourneert een <JavadocLink type="foundation" location="com/webforj/PendingResult" code='true'>PendingResult</JavadocLink>, waarmee extra gespecificeerde gedragingen asynchroon kunnen worden uitgevoerd zodra de component aan de DOM is toegevoegd.

:::tip
In tegenstelling tot de vorige drie methoden, is `whenAttached()` bedoeld om expliciet door de gebruiker te worden aangeroepen.
:::

```java
public class Demo extends App {
  @Override
  public void run() throws WebforjException {
    Frame window = new Frame();

    Button button = new Button(); 

    /* Expliciete aanroep van whenAttached() die een 
    berichtvenster weergeeft wanneer de knop aan het Frame is toegevoegd.*/
    button.whenAttached().thenAccept( e -> {
      showMessageDialog("Ik ben toegevoegd!", "Asynchrone toevoeging");
    });
  
    // onCreate() method wordt aangeroepen
    window.add(button); 
  }
}
```

### Observers {#observers}

Observers spelen een vitale rol bij het bijhouden van levenscyclusevenementen van componenten. Observers kunnen worden toegevoegd en verwijderd met de `addLifecycleObserver()`- en `removeLifecycleObserver()`-methoden, en ontvangen meldingen over gebeurtenissen zoals de creatie en vernietiging van componenten.

Door observers toe te voegen, kun je actie ondernemen wanneer een component wordt gemaakt of vernietigd. Dit is bijzonder nuttig voor het implementeren van aangepaste logica of het afhandelen van specifieke scenario's op basis van componentgebeurtenissen.

```java
Button button = new Button();
button.addLifecycleObserver((button, lifecycleEvent) -> {
  if (lifecycleEvent == ComponentLifecycleObserver.LifecycleEvent.DESTROY) {
    //geïmplementeerde logica om uit te voeren wanneer de knop wordt vernietigd
  }
});
```

## Component properties {#component-properties}

### Component identifiers {#component-identifiers}

Component-ID's dienen als unieke identificatoren voor componenten, waardoor je met hen kunt omgaan en hun toestand effectief kunt beheren.

#### Server-side component ID {#server-side-component-id}

Elke component die is gemaakt van de `Component`-klasse krijgt automatisch een server-side identificator toegewezen. Server-side ID's zijn essentieel voor interne tracking en identificatie van componenten binnen het framework. Je kunt de server-side component ID verkrijgen met de `getComponentId()`-methode.

Dit kan handig zijn in veel situaties waarin het hebben van een unieke, server-size identificator noodzakelijk is, zoals het opvragen van een specifieke component binnen een container.

#### Client-side component ID {#client-side-component-id}

Client-side ID's stellen de gebruiker in staat de clientrepresentatie van de servercomponent die in Java is gemaakt, te verkrijgen. Alle geboden webforJ-componenten hebben een implementatie van deze ID. Als je toegang wilt krijgen tot en gebruik wilt maken van de client-side component, kun je `object.get()` uitvoeren met de client-ID om de gewenste clientcomponent te verkrijgen.

:::important
Deze ID is **niet** de ID-attribuut van het element in de DOM.
:::

In het onderstaande voorbeeld wordt een `onClick`-evenement toegevoegd aan een knop, die wordt geactiveerd door de methode aan te roepen op de clientcomponent nadat deze is verkregen met de `object.get()`-methode.

```java
@Override
public void run() throws WebforjException {
  Frame frame = new Frame();
  Button btn = new Button("Klik op mij");
  btn.onClick(e -> {
    showMessageDialog("De knop is aangeklikt", "Een gebeurtenis heeft plaatsgevonden");
  });

  btn.whenAttached().thenAccept(e -> {
    getPage().executeJs("objects.get('" + btn.getClientComponentId() + "').click()");
  });
  frame.add(btn);
}
```

### User data {#user-data}

De `Component`-klasse stelt je in staat om aanvullende informatie binnen de component op te nemen met behulp van de `setUserData()`-methode. Deze informatie is alleen toegankelijk aan de serverzijde van de component via de `getUserData()`-methode en wordt niet naar de client verzonden.

Dit is erg nuttig wanneer er informatie is die bij een component moet worden opgenomen, en wanneer die informatie toegankelijk moet zijn zonder dat je naar de client hoeft te gaan om het op te halen.
