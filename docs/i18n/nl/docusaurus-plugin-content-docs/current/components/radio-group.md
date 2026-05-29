---
title: RadioButtonGroup
slug: radiobuttongroup
sidebar_position: 100
_i18n_hash: 5716356b99e40dc53cfdf82a87fd9b3c
---
<DocChip chip='since' label='23.01' />
<JavadocLink type="foundation" location="com/webforj/component/optioninput/RadioButtonGroup" top='true'/>

De `RadioButtonGroup` beheert een verzameling van [`RadioButton`](/docs/components/radiobutton) componenten. Slechts één `RadioButton` kan geselecteerd worden in een `RadioButtonGroup`. Wanneer een gebruiker een nieuwe radio button aanvinkt, wordt de eerder geselecteerde automatisch uitgevinkt.

<!-- INTRO_END -->

## Het creëren van een `RadioButtonGroup` {#creating-a-radiobuttongroup}

:::important `RadioButtonGroup` rendering
De `RadioButtonGroup` component rendert geen HTML-element. Het biedt alleen logica om `RadioButton` componenten als een groep te laten functioneren in plaats van individueel.
:::

Maak individuele `RadioButton` componenten en geef deze door aan de constructor van de `RadioButtonGroup`. Slechts één knop in de groep kan tegelijkertijd geselecteerd zijn.

<ComponentDemo
path='/webforj/radiobuttongroup'
files={['src/main/java/com/webforj/samples/views/radiobutton/RadioButtonGroupView.java']}
height='200px'
/>


## Toevoegen en verwijderen van `RadioButton` componenten {#adding-and-removing-radiobuttons}

Je kunt `RadioButton` componenten opnemen in de constructor van de `RadioButtonGroup` om een groep te creëren uit de gegeven componenten.
Om een `RadioButton` toe te voegen of te verwijderen uit een bestaande `RadioButtonGroup`, gebruik je de `add()` of `remove()` methoden.

:::tip Het krijgen van de Groep van een `RadioButton`
De `RadioButton` component heeft de `getButtonGroup()` methode, die de `RadioButtonGroup` retourneert waar deze toe behoort, of `null` als deze geen groep heeft.
:::

## Nesting <DocChip chip='since' label='25.11' /> {#nesting}

Net als andere componenten, kun je een `RadioButtonGroup` binnen een container nesten, zodat je niet elke individuele `RadioButton` direct hoeft toe te voegen.

```java
RadioButton agree = new RadioButton("Akkoord");
RadioButton neutral = new RadioButton("Neutraal");
RadioButton disagree = new RadioButton("Oneens");

RadioButtonGroup group = new RadioButtonGroup("keuzes", agree, neutral, disagree);

Fieldset fieldset = new Fieldset("Opties");
fieldset.add(group);
```

## Gebruik van `RadioButtonGroupChangeEvent` {#using-radiobuttongroupchangeevent}

Elke `RadioButton` kan zijn eigen evenementluisteraar hebben om te detecteren wanneer een gebruiker deze in- of uitschakelt. Een voordeel van het gebruik van een `RadioButtonGroup` is dat je een enkele evenementluisteraar kunt gebruiken die reageert op alle radio buttons in de groep met de [`RadioButtonGroupChangeEvent`](https://javadoc.io/doc/com.webforj/webforj-foundation/latest/com/webforj/component/optioninput/event/RadioButtonGroupChangeEvent.html).

**Evenementluisteraars toevoegen aan elke `RadioButton`**

```java 
agree.onValueChange(e -> changeEvent());
neutral.onValueChange(e -> changeEvent());
disagree.onValueChange(e -> changeEvent());
```

**Een enkele evenementluisteraar toevoegen aan de `RadioButtonGroup`**

```java
RadioButtonGroup group = new RadioButtonGroup("keuzes", agree, neutral, disagree);
group.onChange(e -> changeEvent());
```

De volgende voorbeeld van [Drawer Placement](/docs/components/drawer#placement) gebruikt de `RadioButtonGroupChangeEvent` om automatisch de plaatsing van de `Drawer` component te veranderen:

<ComponentDemo
path='/webforj/drawerplacement'
files={['src/main/java/com/webforj/samples/views/drawer/DrawerPlacementView.java']}
height='600px'
/>

## Naamgeving {#naming}

De `name` attribuut in een `RadioButtonGroup` groepeert gerelateerde RadioButtons samen, waardoor gebruikers een enkele keuze kunnen maken uit de aangeboden opties en exclusiviteit afdwingen onder de RadioButtons. De naam van een groep wordt echter niet gereflecteerd in de DOM en is een handige hulpbron voor de Java-ontwikkelaar.
