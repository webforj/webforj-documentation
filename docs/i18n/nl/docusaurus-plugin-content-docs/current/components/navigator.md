---
title: Navigator
sidebar_position: 75
description: >-
  Add pagination controls with the Navigator component, binding to a Paginator
  or Repository to drive page size, navigation, and labels.
_i18n_hash: 1223e167b76000411cd73c4bbbbda3d5
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-navigator" />
<DocChip chip='since' label='24.00' />
<JavadocLink type="foundation" location="com/webforj/component/navigator/Navigator" top='true'/>

De `Navigator` component voegt paginabesturingselementen toe voor het navigeren door datasets. Het kan de eerste, laatste, volgende en vorige knoppen weergeven, samen met paginanummers of een snel-jump veld, en schakelt automatisch de knoppen uit wanneer ze niet van toepassing zijn. Het bindt aan een `Paginator` instantie om de onderliggende pagineringslogica te beheren.

<!-- INTRO_END -->

## Binding aan repositories {#binding-to-repositories}

Vaak toont een `Navigator` component informatie die te vinden is in een gebonden `Repository`. Deze binding stelt de `Navigator` in staat om automatisch data te pagineren die door de repository wordt beheerd en vernieuwt andere bindbare componenten, zoals tabellen, op basis van de genavigeerde data.

Om dit te doen, geef eenvoudig het gewenste `Repository` object door aan de constructor van een toepasselijke `Navigator` object:

<ComponentDemo
path='/webforj/navigatortable'
files={['src/main/java/com/webforj/samples/views/navigator/NavigatorTableView.java']}
height='475px'
/>

Dit voorbeeld maakt de `Navigator` en [`Table`](table/overview) met dezelfde `Repository` instantie. Dit betekent dat wanneer je naar een nieuwe pagina navigeert met de `Navigator`, de [`Table`](table/overview) deze wijziging opmerkt en opnieuw weergeeft.

## Paginering {#pagination}

De `Navigator` component is nauw verbonden met de `Paginator` modelklasse, berekent paginering metadata zoals totaal aantal pagina's, start/eind indexen van items op de huidige pagina, en een array van paginanummers voor navigatie.

Hoewel het niet strikt noodzakelijk is, maakt het gebruik van de `Paginator` de logica achter navigatie mogelijk. Bij integratie met een `Paginator`, reageert de navigator op wijzigingen binnen de `Paginator`. `Navigator` objecten hebben toegang tot een ingebouwde `Paginator` via het `getPaginator()` method. Het kan ook een `Paginator` instantie accepteren via de `setPaginator()` method, of het gebruik van een van de toepasselijke constructors.

Deze sectie bevat praktische codefragmenten om te illustreren hoe deze integratie in de praktijk werkt.

### Items {#items}

De term "items" verwijst naar de individuele gepagineerde elementen of data-invoeringen. Dit kunnen records, invoeringen of andere discrete eenheden binnen een dataset zijn. Je kunt het totale aantal items instellen met behulp van de `setTotalItems()` methode.

```java
navigator.getPaginator().setTotalItems(totalItems);
```

:::info
Een repository die is gekoppeld aan de `Paginator` instantie heeft het totale aantal items dat rechtstreeks door de repository wordt beheerd en kan niet rechtstreeks worden ingesteld.
:::

### Maximale pagina's {#maximum-pages}

De `setMax()` methode stelt je in staat om het maximale aantal paginelinks te definiëren dat weergegeven moet worden in de navigatie van de paginering. Dit is bijzonder nuttig wanneer je met een groot aantal pagina's werkt, omdat het het aantal paginelinks dat zichtbaar is voor de gebruiker op elk gegeven moment controleert.

```java
navigator.getPaginator().setMax(maxPages);
```

<ComponentDemo
path='/webforj/navigatorpages'
files={['src/main/java/com/webforj/samples/views/navigator/NavigatorPagesView.java']}
height='125px'
/>

Dit programma toont maximaal vijf pagina's op de `Navigator` tegelijk door de `getPaginator()` methode te gebruiken om de `Paginator` op te halen die aan het `Navigator` object is gekoppeld, en vervolgens de `setMax()` methode te gebruiken om een gewenst aantal maximaal weergegeven pagina's te specificeren.

### Pagina formaat {#page-size}

De `setSize()` methode stelt je in staat om het aantal items in te stellen dat op elke pagina van de paginering moet worden weergegeven. Wanneer je deze methode aanroept en een nieuwe paginagrootte opgeeft, past het de paginering dienovereenkomstig aan.

```java
navigator.getPaginator().setSize(pageSize);
```

## Aanpassen van knoppen, tekst en tooltips {#customizing-buttons-text-and-tooltips}

De `Navigator` component biedt uitgebreide aanpassingsmogelijkheden voor knoppen, tekst en tooltips. Om de weergegeven tekst op de `Navigator` component te wijzigen, gebruik je de `setText()` methode. Deze methode neemt tekst, evenals het gewenste `Part` van de `Navigator`.

In het volgende voorbeeld toont de `setText()` methode een numerieke waarde aan de gebruiker. Het klikken op de knoppen activeert de `onChange` methode van de `Navigator`, die een `Direction` waarde meebrengt van de geklikte knop.

<ComponentDemo
path='/webforj/navigatorbasic'
files={['src/main/java/com/webforj/samples/views/navigator/NavigatorBasicView.java']}
height='100px'
/>

### Knoppen en componenttekst {#buttons-and-component-text}

De `setText()` methode evalueert de tekstparameter als een JavaScript-uitdrukking met de volgende parameters:

- `page` - het huidige paginanummer
- `current` - het momenteel geselecteerde paginanummer
- `x` - een alias voor de huidige pagina
- `startIndex` - de startindex van de huidige pagina.
- `endIndex` - de eindindex van de huidige pagina.
- `totalItems` - het totale aantal items.
- `startPage` - het startpaginanummer.
- `endPage` - het eindpaginanummer.
- `component` - de Navigator clientcomponent.

<!-- vale off -->
Bijvoorbeeld, om de tekst van de laatste pagina knop in een `Navigator` met 10 pagina's in te stellen op "Ga naar pagina 10", gebruik je het volgende codefragment:
<!-- vale on -->

```java
navigator.setText("'Ga naar pagina ' + endPage", Navigator.Part.LAST_BUTTON);
```

### Tooltiptekst {#tooltip-text}

Je kunt tooltips voor verschillende delen van de `Navigator` component aanpassen met behulp van de `setTooltipText()` methode. Tooltips bieden nuttige hints aan gebruikers wanneer ze met de muis over navigatie-elementen bewegen.

:::info
Tooltiptekst wordt niet geëvalueerd naar Javascript, in tegenstelling tot de tekst die door de `setText()` methode wordt gebruikt.
:::

<!-- vale off -->
Bijvoorbeeld, om de tooltiptekst van de laatste pagina knop in een `Navigator` in te stellen op "Ga naar de laatste pagina", gebruik je het volgende codefragment:
<!-- vale on -->

```java
navigator.setTooltipText("Ga naar de laatste pagina", Navigator.Part.LAST_BUTTON);
```

## Indelingen {#layouts}

Er zijn verschillende opmaakopties beschikbaar voor de `Navigator` component om flexibiliteit te bieden in het weergeven van pagineringselementen. Om toegang te krijgen tot deze indelingen, gebruik je de waarden van de `Navigator.Layout` enum. De opties zijn als volgt:

<ComponentDemo
path='/webforj/navigatorlayout'
files={['src/main/java/com/webforj/samples/views/navigator/NavigatorLayoutView.java']}
height='200px'
/>

### 1. Geen indeling {#1-none-layout}

De `NONE` indeling render geen tekst binnen de `Navigator`, alleen de navigatieknoppen zonder een standaard tekstweergave. Om deze indeling te activeren, gebruik:

```java
navigator.setLayout(Navigator.Layout.NONE);
```

### 2. Genummerde indeling {#2-numbered-layout}

De genummerde indeling toont genummerde chips die overeenkomen met elke pagina binnen het weergavegebied van de `Navigator`. Het gebruik van deze indeling is ideaal voor scenario's waarin gebruikers de voorkeur geven aan directe navigatie naar specifieke pagina's. Om deze indeling te activeren, gebruik:

```java
navigator.setLayout(Navigator.Layout.PAGES);
```

### 3. Voorbeeld indeling {#3-preview-layout}

De voorbeeld indeling toont het huidige paginanummer en het totaal aantal pagina's, en is geschikt voor compacte paginering interfaces met beperkte ruimte.

:::info
Voorbeeld is de standaard `Navigator` indeling.
:::

Om deze indeling te activeren, gebruik:

```java
navigator.setLayout(Navigator.Layout.PREVIEW);
```

### 4. Snel-jump indeling {#4-quick-jump-layout}

De snel-jump indeling biedt een [NumberField](./fields/number-field.md) voor gebruikers om een paginanummer in te voeren voor snelle navigatie. Dit is nuttig wanneer gebruikers snel naar een specifieke pagina moeten navigeren, vooral voor grote datasets. Om deze indeling te activeren, gebruik:

```java
navigator.setLayout(Navigator.Layout.QUICK_JUMP);
```

## Stijling {#styling}

<TableBuilder name="Navigator" />

## Beste praktijken {#best-practices}

Om een optimale gebruikerservaring te waarborgen bij het gebruik van de `Navigator` component, overweeg de volgende beste praktijken:

- **Begrijp dataset**: Voordat je een `Navigator` component in je app integreert, begrijp de data-browsing vereisten van je gebruikers grondig. Houd rekening met factoren zoals de grootte van de dataset, typische gebruikersinteracties en voorkeuren voor navigatiepatronen.

- **Kies geschikte indeling**: Kies een indeling voor de `Navigator` component die in lijn is met de gebruikerservaring doelen en beschikbare schermruimte.

- **Pas tekst en tooltips aan**: Pas de tekst en tooltips van de `Navigator` component aan om overeen te komen met de taal en terminologie die in je app wordt gebruikt. Bied beschrijvende labels en nuttige hints om gebruikers effectief te helpen bij het navigeren door de dataset.
