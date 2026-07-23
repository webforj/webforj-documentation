---
title: RadioButtonGroup
slug: radiobuttongroup
sidebar_position: 100
description: >-
  Coordinate mutually exclusive RadioButton selections with RadioButtonGroup,
  including nested containers and dynamic membership.
_i18n_hash: a616c60faaf0d58f9d9a1e778318880a
---
<DocChip chip='since' label='23.01' />
<JavadocLink type="foundation" location="com/webforj/component/optioninput/RadioButtonGroup" top='true'/>

De `RadioButtonGroup` beheert een verzameling van [`RadioButton`](/docs/components/radiobutton) componenten. Slechts één `RadioButton` kan geselecteerd zijn in een `RadioButtonGroup`. Wanneer een gebruiker een nieuwe radio button aanvinkt, wordt de eerder geselecteerde automatisch uitgeschakeld.

<!-- INTRO_END -->

## Het aanmaken van een `RadioButtonGroup` {#creating-a-radiobuttongroup}

:::important Weergave van `RadioButtonGroup`
De `RadioButtonGroup` component rendert geen HTML-element. Het biedt alleen logica om `RadioButton` componenten als een groep te laten functioneren in plaats van individueel.
:::

Maak individuele `RadioButton` componenten en geef ze door aan de `RadioButtonGroup` constructor. Slechts één knop in de groep kan op een tijd geselecteerd zijn.

<ComponentDemo
path='/webforj/radiobuttongroup'
files={['src/main/java/com/webforj/samples/views/radiobutton/RadioButtonGroupView.java']}
height='200px'
/>

## Het toevoegen en verwijderen van `RadioButton` componenten {#adding-and-removing-radiobuttons}

Je kunt `RadioButton` componenten opnemen in de `RadioButtonGroup` constructor om een groep te creëren uit de aangeboden componenten.
Om een `RadioButton` toe te voegen of te verwijderen uit een bestaande `RadioButtonGroup`, gebruik de `add()` of `remove()` methoden.

:::tip Het krijgen van de Groep van een `RadioButton`
De `RadioButton` component heeft de `getButtonGroup()` methode, die de `RadioButtonGroup` retourneert waartoe deze behoort, of `null` als het geen groep heeft.
:::

## Nesting <DocChip chip='since' label='25.11' /> {#nesting}

Net als andere componenten kun je een `RadioButtonGroup` nestelen binnen een container, zodat je niet elke individuele `RadioButton` direct hoeft toe te voegen.

```java
RadioButton agree = new RadioButton("Akkoord");
RadioButton neutral = new RadioButton("Neutraal");
RadioButton disagree = new RadioButton("Niet eens");

RadioButtonGroup group = new RadioButtonGroup("keuzes", agree, neutral, disagree);

Fieldset fieldset = new Fieldset("Opties");
fieldset.add(group);
```

## Gebruik van `RadioButtonGroupChangeEvent` {#using-radiobuttongroupchangeevent}

Elke `RadioButton` kan zijn eigen eventlistener hebben om te detecteren wanneer een gebruiker deze in- of uitschakelt. Een voordeel van het gebruik van een `RadioButtonGroup` is echter dat je een enkele eventlistener kunt gebruiken die reageert op alle radio buttons in de groep met de [`RadioButtonGroupChangeEvent`](https://javadoc.io/doc/com.webforj/webforj-foundation/latest/com/webforj/component/optioninput/event/RadioButtonGroupChangeEvent.html).

**Eventlisteners toevoegen aan elke `RadioButton`**

```java
agree.onValueChange(e -> changeEvent());
neutral.onValueChange(e -> changeEvent());
disagree.onValueChange(e -> changeEvent());
```

**Een enkele eventlistener toevoegen aan de `RadioButtonGroup`**

```java
RadioButtonGroup group = new RadioButtonGroup("keuzes", agree, neutral, disagree);
group.onChange(e -> changeEvent());
```

Het volgende voorbeeld van [Drawer Plaatsing](/docs/components/drawer#placement) gebruikt de `RadioButtonGroupChangeEvent` om automatisch de plaatsing van de `Drawer` component te wijzigen:

<ComponentDemo
path='/webforj/drawerplacement'
files={['src/main/java/com/webforj/samples/views/drawer/DrawerPlacementView.java']}
height='600px'
/>

## Naamgeving {#naming}

De `name` eigenschap in een `RadioButtonGroup` groepeert gerelateerde RadioButtons samen, zodat gebruikers een enkele keuze kunnen maken uit de aangeboden opties en exclusiviteit onder de RadioButtons afdwingt. De naam van een groep wordt echter niet weerspiegeld in de DOM en is een handige utility voor de Java-ontwikkelaar.
