---
sidebar_position: 1
title: Component Basics
slug: basics
draft: false
_i18n_hash: d517f6169f7ac0798ed073bb27348eb5
---
<DocChip chip='since' label='23.05' />
<JavadocLink type="foundation" location="com/webforj/component/Component" top='true'/>

Componenten zijn fundamentele bouwstenen die aan een venster kunnen worden toegevoegd en gebruikersinterface-functionaliteit en aangepast gedrag bieden. In webforJ fungeert de `Component`-klasse als de basis voor alle componenten binnen de engine.

## Lifecycle management {#lifecycle-management}

Het begrijpen van de component lifecycle is essentieel voor het effectief creëren, beheren en gebruiken van componenten. De volgende twee lifecycle-staten hebben methoden om hun gedrag te manipuleren. Deze methoden mogen niet expliciet door de gebruiker worden aangeroepen.

### Creëren en vernietigen haken {#create-and-destroy-hooks}

Alle klassen die de `Component`-klasse uitbreiden zijn verantwoordelijk voor het implementeren van de functionaliteit die moet worden uitgevoerd wanneer de `Component` wordt gecreëerd, en wanneer deze wordt vernietigd. Dit gebeurt door de `onCreate()`- en `onDestroy()`-methoden te overschrijven.

#### `onCreate()` {#oncreate}

De `onCreate()`-methode wordt aangeroepen wanneer de component aan een venster wordt toegevoegd. Het creëren van componenten omvat het instellen van hun initiële status en functionaliteit. Dit is het moment waarop je definieert wat de component moet doen wanneer deze voor het eerst wordt gemaakt. Of het nu gaat om het initialiseren van variabelen, het instellen van event listeners of het uitvoeren van andere opstellingen, de `onCreate()`-methode is je toegangspunt voor het aanpassen van het componentgedrag.

Deze haak ontvangt een vensterinstantie waarmee componenten die binnen de component zijn opgenomen, kunnen worden toegevoegd.

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

Het vernietigen van componenten is een essentieel onderdeel van het beheren van middelen en het garanderen van een goede opruiming. Het vernietigen van een component is nodig wanneer deze niet langer nodig is of wanneer je middelen wilt vrijgeven die ermee zijn geassocieerd. Dit stelt een ontwikkelaar in staat om opruimtaken uit te voeren, zoals het stoppen van timers, het vrijgeven van geheugen of het loskoppelen van event listeners. Het stelt ook de `destroy()`-methode in staat om te worden aangeroepen op alle samenstellende componenten.

:::tip
De `onDestroy()`-methode is verantwoordelijk voor het aanroepen van de `destroy()`-methode op alle samenstellende componenten. Anders blijven deze componenten bestaan in de DOM, maar zijn ze niet bereikbaar via de API.
:::

### Asynchrone bijlage {#asynchronous-attachment}

De `whenAttached()`-methode maakt het mogelijk om functionaliteit uit te voeren nadat een component aan een venster is toegevoegd. Deze methode retourneert een <JavadocLink type="foundation" location="com/webforj/PendingResult" code='true'>PendingResult</JavadocLink>, die het mogelijk maakt om aanvullende opgegeven gedrag asynchroon uit te voeren zodra de component in de DOM is gehecht.

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
    berichtvenster weergeeft wanneer de knop aan het Frame is gehecht. */
    button.whenAttached().thenAccept(e -> {
      showMessageDialog("Ik ben gehecht!", "Asynchrone bijlage");
    });
  
    // de onCreate() methode wordt aangeroepen
    window.add(button); 
  }
}
```

### Observers {#observers}

Observers spelen een belangrijke rol bij het volgen van component lifecycle-evenementen. Observers kunnen worden toegevoegd en verwijderd met behulp van de methoden `addLifecycleObserver()` en `removeLifecycleObserver()`, en ontvangen notificaties over gebeurtenissen zoals de creatie en vernietiging van componenten.

Door observers toe te voegen, kun je actie ondernemen wanneer een component wordt gemaakt of vernietigd. Dit is vooral nuttig voor het implementeren van aangepaste logica of het afhandelen van specifieke scenario's op basis van componentevenementen.

```java
Button button = new Button();
button.addLifecycleObserver((button, lifecycleEvent) -> {
  if (lifecycleEvent == ComponentLifecycleObserver.LifecycleEvent.DESTROY) {
    // geïmplementeerde logica om uit te voeren wanneer de knop wordt vernietigd
  }
});
```

## Componenteigenschappen {#component-properties}

### Componentidentificatoren {#component-identifiers}

Component-ID's dienen als unieke identificatoren voor componenten, waarmee je effectief met hen kunt communiceren en hun status kunt beheren.

#### Server-side component-ID {#server-side-component-id}

Elke component die is gemaakt vanuit de `Component`-klasse krijgt automatisch een server-side identificator toegewezen. Server-side ID's zijn essentieel voor interne tracking en identificatie van componenten binnen het framework. Je kunt de server-side component-ID ophalen met de `getComponentId()`-methode.

Dit kan nuttig zijn in veel situaties waarin een unieke, server-side identificator nodig is, zoals het opvragen van een specifieke component binnen een container.

#### Client-side component-ID {#client-side-component-id}

Client-side ID's stellen de gebruiker in staat om de clientrepresentatie van de servercomponent die in Java is gemaakt, te verkrijgen. Alle aangeboden webforJ-componenten hebben een implementatie van deze ID. Als je toegang wilt krijgen tot en gebruik wilt maken van de client-side component, kun je `object.get()` uitvoeren met de client-ID om de gewenste clientcomponent te verkrijgen.

:::important
Deze ID is **niet** de ID-attribuut van het element in de DOM.
:::

In de onderstaande voorbeeld wordt er een `onClick`-evenement toegevoegd aan een knop, die vervolgens wordt geactiveerd door de methode op de clientcomponent aan te roepen nadat deze is verkregen via de `object.get()`-methode.

```java
@Override
public void run() throws WebforjException {
  Frame frame = new Frame();
  Button btn = new Button("Klik op mij");
  btn.onClick(e -> {
    showMessageDialog("De knop is aangeklikt", "Een gebeurtenis vond plaats");
  });

  btn.whenAttached().thenAccept(e -> {
    getPage().executeJs("objects.get('" + btn.getClientComponentId() + "').click()");
  });
  frame.add(btn);
}
```

### Gegevens van de gebruiker {#user-data}

De `Component`-klasse stelt je in staat om aanvullende informatie binnen de component op te nemen met behulp van de `setUserData()`-methode. Deze informatie is alleen toegankelijk aan de serverzijde van de component via de `getUserData()`-methode en wordt niet naar de client verzonden. 

Dit is erg handig wanneer er informatie is die moet worden opgenomen met een component, en wanneer die informatie toegankelijk moet zijn zonder dat er een trip naar de client hoeft te worden gemaakt om deze te verkrijgen.
