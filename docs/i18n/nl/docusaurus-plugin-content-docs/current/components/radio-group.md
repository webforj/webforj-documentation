---
title: RadioButtonGroup
slug: radiobuttongroup
sidebar_position: 100
sidebar_class_name: updated-content
_i18n_hash: da7906128f0d003b9ed8c48c99c3aefc
---
<DocChip chip='since' label='23.01' />
<JavadocLink type="foundation" location="com/webforj/component/optioninput/RadioButtonGroup" top='true'/>

De `RadioButtonGroup` beheert een verzameling van [`RadioButton`](/docs/components/radiobutton) componenten. Slechts één `RadioButton` kan geselecteerd worden in een `RadioButtonGroup`. Wanneer een gebruiker een nieuwe radio button aanvinkt, wordt de eerder geselecteerde automatisch uitgeschakeld.

<ComponentDemo 
path='/webforj/radiobuttongroup?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/radiobutton/RadioButtonGroupView.java'
height="200px"
/>

:::important `RadioButtonGroup` renderen
De `RadioButtonGroup` component rendert geen HTML-element. Het biedt alleen logica om `RadioButton` componenten zich als een groep te laten gedragen in plaats van individueel.
:::

## Toevoegen en verwijderen van `RadioButton` componenten {#adding-and-removing-radiobuttons}

Je kunt `RadioButton` componenten opnemen in de `RadioButtonGroup` constructor om een groep te maken uit de opgegeven componenten. Om een `RadioButton` toe te voegen of te verwijderen uit een bestaande `RadioButtonGroup`, gebruik je de `add()` of `remove()` methoden.

:::tip Het verkrijgen van de Groep van een `RadioButton`
De `RadioButton` component heeft de `getButtonGroup()` methode, die de `RadioButtonGroup` retourneert waartoe het behoort, of `null` als het geen groep heeft.
:::

## Nesten <DocChip chip='since' label='25.11' /> {#nesting}

Net als andere componenten, kun je een `RadioButtonGroup` binnen een container nestelen, zodat je niet elke individuele `RadioButton` direct hoeft toe te voegen.

```java
RadioButton agree = new RadioButton("Akkoord");
RadioButton neutral = new RadioButton("Neutraal");
RadioButton disagree = new RadioButton("Niet akkoord");

RadioButtonGroup group = new RadioButtonGroup("keuzes", agree, neutral, disagree);

Fieldset fieldset = new Fieldset("Opties");
fieldset.add(group);
```

## Gebruik van `RadioButtonGroupChangeEvent` {#using-radiobuttongroupchangeevent}

Elke `RadioButton` kan zijn eigen gebeurtenisluisteraar hebben om te detecteren wanneer een gebruiker deze schakelt. Echter, een voordeel van het gebruiken van een `RadioButtonGroup` is dat je een enkele gebeurtenisluisteraar kunt gebruiken die reageert op alle radio buttons in de groep met de [`RadioButtonGroupChangeEvent`](https://javadoc.io/doc/com.webforj/webforj-foundation/latest/com/webforj/component/optioninput/event/RadioButtonGroupChangeEvent.html).

**Toevoegen van gebeurtenisluisteraars aan elke `RadioButton`**

```java 
agree.onValueChange(e -> changeEvent());
neutral.onValueChange(e -> changeEvent());
disagree.onValueChange(e -> changeEvent());
```

**Toevoegen van een enkele gebeurtenisluisteraar aan de `RadioButtonGroup`**

```java
RadioButtonGroup group = new RadioButtonGroup("keuzes", agree, neutral, disagree);
group.onChange(e -> changeEvent());
```

Het volgende voorbeeld van [Drawer Plaatsing](/docs/components/drawer#placement) gebruikt de `RadioButtonGroupChangeEvent` om automatisch de plaatsing van de `Drawer` component te wijzigen:

<ComponentDemo
path='/webforj/drawerplacement?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/drawer/DrawerPlacementView.java'
height='600px'
/>

## Naamgeving {#naming}

De `name` attribuut in een `RadioButtonGroup` groepeert gerelateerde RadioButtons samen, waardoor gebruikers een enkele keuze kunnen maken uit de beschikbare opties en exclusiviteit onder de RadioButtons afdwingen. De naam van een groep wordt echter niet weerspiegeld in de DOM en is een handige utility voor de Java-ontwikkelaar.
