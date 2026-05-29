---
title: Navigator
sidebar_position: 75
_i18n_hash: db351d8f9fdf344a571d374e8d373f22
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-navigator" />
<DocChip chip='since' label='24.00' />
<JavadocLink type="foundation" location="com/webforj/component/navigator/Navigator" top='true'/>

De `Navigator` component voegt paginatie-controls toe voor het navigeren door datasets. Het kan eerste, laatste, volgende en vorige knoppen weergeven, samen met paginanummers of een snel-jump veld, en schakelt automatisch de controls uit wanneer ze niet van toepassing zijn. Het bindt aan een `Paginator` instantie om de onderliggende paginatie-logica te beheren.

<!-- INTRO_END -->

## Binding aan repositories {#binding-to-repositories}

Vaak toont een `Navigator` component informatie die te vinden is in een gebonden `Repository`. Deze binding stelt de `Navigator` in staat om automatisch data gepagineerd door de repository te tonen en andere bindbare componenten, zoals tabellen, te vernieuwen, op basis van de genavigeerde data.

Om dit te doen, geef eenvoudig het gewenste `Repository`-object door aan de constructor van een toepasselijke `Navigator`-object:

<ComponentDemo
path='/webforj/navigatortable'
files={['src/main/java/com/webforj/samples/views/navigator/NavigatorTableView.java']}
height='475px'
/>

Dit voorbeeld creëert de `Navigator` en [`Table`](table/overview) met dezelfde `Repository`-instantie. Dit betekent dat wanneer je naar een nieuwe pagina navigeert met de `Navigator`, de [`Table`](table/overview) deze verandering herkent en opnieuw weergeeft.

## Paginatie {#pagination}

De `Navigator` component is nauw verbonden met de `Paginator` modelklasse, berekent paginatie metadata zoals het totale aantal pagina's, start/eind indices van items op de huidige pagina, en een array van paginanummers voor navigatie.

Hoewel niet expliciet noodzakelijk, maakt het gebruik van de `Paginator` de logica achter navigatie mogelijk. Wanneer je integreert met een `Paginator`, reageert de navigator op elke verandering binnen de `Paginator`. `Navigator` objecten hebben toegang tot een ingebouwde `Paginator` via het gebruik van de `getPaginator()` methode. Het kan ook een `Paginator` instantie accepteren via de `setPaginator()` methode, of door gebruik te maken van een van de toepasselijke constructors.

Deze sectie bevat praktische codefragmenten om te illustreren hoe deze integratie in de praktijk werkt.

### Items {#items}

De term "items" duidt op de individuele gepagineerde elementen of data-invoeren. Dit kunnen records, invoeren, of andere discrete eenheden binnen een dataset zijn. Je kunt het totale aantal items instellen met de `setTotalItems()` methode.

```java
navigator.getPaginator().setTotalItems(totalItems);
```

:::info
Een repository die geassocieerd is met de `Paginator` instantie heeft het totale aantal items dat direct door de repository wordt beheerd en kan niet direct worden ingesteld.
:::

### Maximale pagina's {#maximum-pages}

De `setMax()` methode stelt je in staat om het maximale aantal paginalinks te definiëren dat in de paginatie-navigatie moet worden weergegeven. Dit is bijzonder nuttig bij een groot aantal pagina's, omdat het het aantal paginalinks regelt dat zichtbaar is voor de gebruiker op elk moment.

```java
navigator.getPaginator().setMax(maxPages);
```

<ComponentDemo
path='/webforj/navigatorpages'
files={['src/main/java/com/webforj/samples/views/navigator/NavigatorPagesView.java']}
height='125px'
/>

Dit programma toont maximaal vijf pagina's op de `Navigator` tegelijk door de `getPaginator()` methode te gebruiken om de `Paginator` op te halen die aan het `Navigator` object is gekoppeld, en vervolgens de `setMax()` methode te gebruiken om een gewenste aantal maximale pagina's weer te geven.

### Paginaformaat {#page-size}

De `setSize()` methode stelt je in staat om het aantal items in te stellen dat op elke pagina van de paginatie moet worden weergegeven. Wanneer je deze methode aanroept en een nieuwe paginaformaat opgeeft, past het de paginatie dienovereenkomstig aan.

```java
navigator.getPaginator().setSize(pageSize);
```

## Het aanpassen van knoppen, tekst en tooltips {#customizing-buttons-text-and-tooltips}

De `Navigator` component biedt uitgebreide aanpassingsopties voor knoppen, tekst en tooltips. Om de weergegeven tekst op de `Navigator` component te wijzigen, gebruik je de `setText()` methode. Deze methode neemt tekst, evenals het gewenste `Part` van de `Navigator`.

In het volgende voorbeeld toont de `setText()` methode een numerieke waarde aan de gebruiker. Het klikken op de knoppen activeert de `onChange` methode van de `Navigator`, die wordt meegeleverd met een `Direction` waarde van de aangeklikte knop.

<ComponentDemo
path='/webforj/navigatorbasic'
files={['src/main/java/com/webforj/samples/views/navigator/NavigatorBasicView.java']}
height='100px'
/>

### Knoppen en componenttekst {#buttons-and-component-text}

De `setText()` methode evalueert de tekstparameter als een JavaScript-uitdrukking met behulp van de volgende parameters:

- `page` - het huidige paginanummer
- `current` - het momenteel geselecteerde paginanummer
- `x` - een alias voor de huidige pagina
- `startIndex` - De startindex van de huidige pagina.
- `endIndex` - De eindindex van de huidige pagina.
- `totalItems` - Het totale aantal items.
- `startPage` - Het startpaginanummer.
- `endPage` - Het eindpaginanummer.
- `component` - De Navigator client component.

<!-- vale off -->
Bijvoorbeeld, om de tekst van de laatste pagina-knop in een `Navigator` met 10 pagina's in te stellen op "Ga naar pagina 10", gebruik je de volgende codefragment:
<!-- vale on -->

```java
navigator.setText("'Ga naar pagina ' + endPage", Navigator.Part.LAST_BUTTON);
```

### Tooltip tekst {#tooltip-text}

Je kunt tooltips voor verschillende onderdelen van de `Navigator` component aanpassen met behulp van de `setTooltipText()` methode. Tooltips bieden nuttige hints aan gebruikers wanneer ze over navigatie-elementen zweven.

:::info
Tooltip tekst evalueert niet naar Javascript, in tegenstelling tot de tekst die door de `setText()` methode wordt gebruikt.
:::

<!-- vale off -->
Bijvoorbeeld, om de tooltiptekst van de laatste pagina-knop in een `Navigator` in te stellen op "Ga naar de laatste pagina", gebruik je de volgende codefragment:
<!-- vale on -->

```java
navigator.setTooltipText("Ga naar de laatste pagina", Navigator.Part.LAST_BUTTON);
```

## Indelingen {#layouts}

Verschillende lay-out opties bestaan voor de `Navigator` component om flexibiliteit te bieden in het weergeven van paginatie-controls. Om deze lay-outs te benaderen, gebruik je de waarden van de `Navigator.Layout` enum. De opties zijn als volgt:

<ComponentDemo
path='/webforj/navigatorlayout'
files={['src/main/java/com/webforj/samples/views/navigator/NavigatorLayoutView.java']}
height='200px'
/>

### 1. Geen lay-out {#1-none-layout}

De `NONE` lay-out render geen tekst binnen de `Navigator`, en toont alleen de navigatieknoppen zonder een standaard tekstuele weergave. Om deze lay-out te activeren, gebruik:

```java
navigator.setLayout(Navigator.Layout.NONE);
```

### 2. Genummerde lay-out {#2-numbered-layout}

De genummerde lay-out toont genummerde chips die overeenkomen met elke pagina binnen het weergavegebied van de `Navigator`. Het gebruik van deze lay-out is ideaal voor scenario's waarin gebruikers directe navigatie naar specifieke pagina's verkiezen. Om deze lay-out te activeren, gebruik:

```java
navigator.setLayout(Navigator.Layout.PAGES);
```

### 3. Voorbeeld lay-out {#3-preview-layout}

De voorbeeld lay-out toont het huidige paginanummer en het totale aantal pagina's, en is geschikt voor compacte paginatie-interfaces met beperkte ruimte.

:::info
Voorbeeld is de standaard `Navigator` lay-out.
:::

Om deze lay-out te activeren, gebruik:

```java
navigator.setLayout(Navigator.Layout.PREVIEW);
```

### 4. Snelle jump lay-out {#4-quick-jump-layout}

De snelle jump lay-out biedt een [NumberField](./fields/number-field.md) voor gebruikers om een paginanummer in te voeren voor snelle navigatie. Dit is handig wanneer gebruikers snel naar een specifieke pagina willen navigeren, vooral voor grote datasets. Om deze lay-out te activeren, gebruik:

```java
navigator.setLayout(Navigator.Layout.QUICK_JUMP);
```

## Styling {#styling}

<TableBuilder name="Navigator" />

## Best practices {#best-practices}

Om een optimale gebruikerservaring te waarborgen bij het gebruik van de `Navigator` component, overweeg je de volgende best practices:

- **Begrijp dataset**: Voordat je een `Navigator` component in je app integreert, begrijp je grondig de vereisten voor dataverkenning van je gebruikers. Overweeg factoren zoals de grootte van de dataset, typische gebruikersinteracties en gewenste navigatiepatronen.

- **Kies een geschikte lay-out**: Kies een lay-out voor de `Navigator` component die aansluit bij de gebruikerservaringdoelen en beschikbare schermruimte.

- **Pas tekst en tooltips aan**: Pas de tekst en tooltips van de `Navigator` component aan om overeen te komen met de taal en terminologie die in je app wordt gebruikt. Geef beschrijvende labels en nuttige hints om gebruikers goed te helpen bij het navigeren door de dataset.
