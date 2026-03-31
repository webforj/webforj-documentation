---
title: RadioButtonGroup
slug: radiobuttongroup
sidebar_position: 100
sidebar_class_name: updated-content
_i18n_hash: 564d1d0c46ef2395fb2ad2785ba18d53
---
<DocChip chip='since' label='23.01' />
<JavadocLink type="foundation" location="com/webforj/component/optioninput/RadioButtonGroup" top='true'/>

De `RadioButtonGroup` beheert een verzameling van [`RadioButton`](/docs/components/radiobutton) componenten. Slechts één `RadioButton` kan geselecteerd zijn in een `RadioButtonGroup`. Wanneer een gebruiker een nieuwe radio-knop aanvinkt, wordt de eerder geselecteerde automatisch uitgevinkt.

<!-- INTRO_END -->

## Een `RadioButtonGroup` maken {#creating-a-radiobuttongroup}

:::important `RadioButtonGroup` rendering
De `RadioButtonGroup` component genereert geen HTML-element. Het biedt alleen de logica om `RadioButton` componenten te laten functioneren als een groep in plaats van individueel.
:::

Maak individuele `RadioButton` componenten en geef deze door aan de `RadioButtonGroup` constructor. Slechts één knop in de groep kan op een bepaald moment geselecteerd zijn.

<ComponentDemo 
path='/webforj/radiobuttongroup?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/radiobutton/RadioButtonGroupView.java'
height="200px"
/>


## `RadioButton` componenten toevoegen en verwijderen {#adding-and-removing-radiobuttons}

Je kunt `RadioButton` componenten opnemen in de `RadioButtonGroup` constructor om een groep te creëren uit de aangeboden componenten.
Om een `RadioButton` toe te voegen of te verwijderen uit een bestaande `RadioButtonGroup`, gebruik je de `add()` of `remove()` methoden.

:::tip Verkrijgen van de Groep van een `RadioButton`
De `RadioButton` component heeft de `getButtonGroup()` methode, die de `RadioButtonGroup` retourneert waartoe deze behoort, of `null` als het geen groep heeft.
:::

## Nestelen <DocChip chip='since' label='25.11' /> {#nesting}

Net als andere componenten, kun je een `RadioButtonGroup` binnen een container nestelen, zodat je niet elke individuele `RadioButton` direct hoeft toe te voegen.

```java
RadioButton agree = new RadioButton("Akkoord");
RadioButton neutral = new RadioButton("Neutraal");
RadioButton disagree = new RadioButton("Oneens");

RadioButtonGroup group = new RadioButtonGroup("keuzes", agree, neutral, disagree);

Fieldset fieldset = new Fieldset("Opties");
fieldset.add(group);
```

## Gebruik van `RadioButtonGroupChangeEvent` {#using-radiobuttongroupchangeevent}

Elke `RadioButton` kan zijn eigen gebeurtenisluisteraar hebben om te detecteren wanneer een gebruiker deze toggled. Een voordeel van het gebruik van een `RadioButtonGroup` is echter dat je een enkele gebeurtenisluisteraar kunt gebruiken die reageert op alle radio-knoppen in de groep met de [`RadioButtonGroupChangeEvent`](https://javadoc.io/doc/com.webforj/webforj-foundation/latest/com/webforj/component/optioninput/event/RadioButtonGroupChangeEvent.html).

**Gebeurtenisluisteraars toevoegen aan elke `RadioButton`**

```java 
agree.onValueChange(e -> changeEvent());
neutral.onValueChange(e -> changeEvent());
disagree.onValueChange(e -> changeEvent());
```

**Een enkele gebeurtenisluisteraar toevoegen aan de `RadioButtonGroup`**

```java
RadioButtonGroup group = new RadioButtonGroup("keuzes", agree, neutral, disagree);
group.onChange(e -> changeEvent());
```

Het volgende voorbeeld van [Drawer Plaatsing](/docs/components/drawer#placement) gebruikt de `RadioButtonGroupChangeEvent` om automatisch de plaatsing van de `Drawer` component te veranderen:

<ComponentDemo
path='/webforj/drawerplacement?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/drawer/DrawerPlacementView.java'
height='600px'
/>

## Naamgeving {#naming}

De `name` attribuut in een `RadioButtonGroup` groepeert gerelateerde RadioButtons samen, waardoor gebruikers een enkele keuze kunnen maken uit de geboden opties en exclusiviteit tussen de RadioButtons afdwingen. De naam van een groep wordt echter niet weerspiegeld in de DOM en is een handige utiliteit voor de Java-ontwikkelaar.
