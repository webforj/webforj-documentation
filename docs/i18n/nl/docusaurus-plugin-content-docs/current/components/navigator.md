---
title: Navigator
sidebar_position: 75
_i18n_hash: 920c1d604673e69a32f58161e3fd4e14
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-navigator" />
<DocChip chip='since' label='24.00' />
<JavadocLink type="foundation" location="com/webforj/component/navigator/Navigator" top='true'/>

De `Navigator` component is een aanpasbare pagineringcomponent die is ontworpen om door gegevenssets te navigeren en verschillende lay-outs ondersteunt. Je kunt het configureren om diverse navigatiecontroles weer te geven, zoals eerste, laatste, volgende en vorige knoppen, samen met paginanummers of een snel jump-veld, afhankelijk van de lay-outinstelling.

Het ondersteunt automatische uitschakeling van navigatieknoppen op basis van de huidige pagina en het totale aantal items, en biedt aanpassingsmogelijkheden voor tekst en tooltips voor verschillende delen van de navigator. Bovendien kun je het koppelen aan een `Paginator`-instantie om de pagineringslogica van de gegevensset te beheren en wijzigingen in de navigatiecontroles weer te geven.

## Binding aan repositories {#binding-to-repositories}

Vaak toont een `Navigator` component informatie die is gevonden in een gebonden `Repository`. Deze binding stelt de `Navigator` in staat om automatisch data te pagineren die door de repository wordt beheerd en om andere verbindbare componenten, zoals tabellen, te verversen op basis van de genavigeerde gegevens.

Om dit te doen, geef je gewoon het gewenste `Repository` object door aan de constructor van een toepasselijk `Navigator` object:

<ComponentDemo 
path='/webforj/navigatortable?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/navigator/NavigatorTableView.java'
height='475px'
/>

Dit voorbeeld creëert de `Navigator` en de [`Table`](table/overview) met dezelfde `Repository` instantie. Dit betekent dat wanneer je naar een nieuwe pagina navigeert met de `Navigator`, de [`Table`](table/overview) deze wijziging erkent en opnieuw rendert.

## Paginering {#pagination}

De `Navigator` component is nauw verbonden met de `Paginator` modelklasse, die pagineringsmetadata berekent, zoals het totale aantal pagina's, start-/eindindexen van items op de huidige pagina, en een array van paginanummers voor navigatie.

Hoewel niet expliciet noodzakelijk, maakt het gebruik van de `Paginator` de logica achter navigatie mogelijk. Bij integratie met een `Paginator` reageert de navigator op wijzigingen binnen de `Paginator`. `Navigator` objecten hebben toegang tot een ingebouwde `Paginator` via de `getPaginator()` methode. Het kan ook een `Paginator` instantie accepteren via de `setPaginator()` methode, of gebruik maken van een van de toepasselijke constructeurs.

Deze sectie bevat praktische codefragmenten om te illustreren hoe deze integratie in de praktijk werkt.

### Items {#items}

De term "items" verwijst naar de individuele gepagineerde elementen of gegevensinvoer. Dit kunnen records, vermeldingen of andere discrete eenheden binnen een dataset zijn. Je kunt het totale aantal items instellen met de `setTotalItems()` methode.

```java
navigator.getPaginator().setTotalItems(totalItems);
```

:::info
Een repository die is gekoppeld aan de `Paginator` instantie heeft het totale aantal items dat rechtstreeks door de repository wordt beheerd en kan niet direct worden ingesteld.
:::

### Maximale pagina's {#maximum-pages}

De `setMax()` methode stelt je in staat om het maximale aantal paginalinks dat in de pagineringsnavigatie moet worden weergegeven, te definiëren. Dit is bijzonder nuttig bij een groot aantal pagina's, omdat het het aantal paginalinks zichtbaar voor de gebruiker op elk moment beheert.

```java
navigator.getPaginator().setMax(maxPages);
```

<ComponentDemo 
path='/webforj/navigatorpages?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/navigator/NavigatorPagesView.java'
height='125px'
/>

Dit programma toont maximaal vijf pagina's op de `Navigator` tegelijkertijd door de `getPaginator()` methode te gebruiken om de `Paginator` op te halen die aan het `Navigator` object is gekoppeld, en vervolgens de `setMax()` methode te gebruiken om een gewenst aantal maximaal weergegeven pagina's op te geven.

### Paginaformaat {#page-size}

De `setSize()` methode stelt je in staat om het aantal items dat op elke pagina van de paginering moet worden weergegeven, op te geven. Wanneer je deze methode aanroept en een nieuwe paginaformaat opgeeft, past het de paginering dienovereenkomstig aan.

```java
navigator.getPaginator().setSize(pageSize);
```

## Aangepaste knoppen, tekst en tooltips {#customizing-buttons-text-and-tooltips}

De `Navigator` component biedt uitgebreide aanpassingsmogelijkheden voor knoppen, tekst en tooltips. Om de weergegeven tekst op de `Navigator` component te wijzigen, gebruik je de `setText()` methode. Deze methode neemt tekst, evenals het gewenste `Part` van de `Navigator`.

In het volgende voorbeeld toont de `setText()` methode een numerieke waarde aan de gebruiker. Het klikken op de knoppen activeert de `onChange` methode van de `Navigator`, die wordt geleverd met een `Direction` waarde van de aangeklikte knop.

<ComponentDemo 
path='/webforj/navigatorbasic?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/navigator/NavigatorBasicView.java'
height='100px'
/>

### Knoppen en componenttekst {#buttons-and-component-text}

De `setText()` methode evalueert de tekstparameter als een JavaScript-expressie met de volgende parameters:

- `page` - het huidige paginanummer
- `current` - het momenteel geselecteerde paginanummer
- `x` - een alias voor de huidige pagina
- `startIndex` - De startindex van de huidige pagina.
- `endIndex` - De eindindex van de huidige pagina.
- `totalItems` - Het totale aantal items.
- `startPage` - Het startpaginanummer.
- `endPage` - Het eindpaginanummer.
- `component` - De Navigator clientcomponent.

<!-- vale off -->
Bijvoorbeeld, om de tekst van de laatste pagina knop in een `Navigator` met 10 pagina's in te stellen op "Ga naar pagina 10", gebruik je het volgende codefragment: 
<!-- vale on -->

```java
navigator.setText("'Ga naar pagina ' + endPage", Navigator.Part.LAST_BUTTON);
```

### Tooltiptekst {#tooltip-text}

Je kunt tooltips voor verschillende delen van de `Navigator` component aanpassen met de `setTooltipText()` methode. Tooltips bieden handige hints aan gebruikers wanneer ze met muis over navigatie-elementen bewegen.

:::info
Tooltiptekst evalueert niet naar Javascript, in tegenstelling tot de tekst die door de `setText()` methode wordt gebruikt.
:::

<!-- vale off -->
Bijvoorbeeld, om de tooltiptekst van de laatste pagina knop in een `Navigator` in te stellen op "Ga naar de laatste pagina", gebruik je het volgende codefragment:
<!-- vale on -->

```java
navigator.setTooltipText("Ga naar de laatste pagina", Navigator.Part.LAST_BUTTON);
```

## Lay-outs {#layouts}

Er zijn verschillende lay-outopties beschikbaar voor de `Navigator` component om flexibiliteit te bieden bij het weergeven van pagineringscontroles. Om toegang te krijgen tot deze lay-outs, gebruik je de waarden van de `Navigator.Layout` enum. De opties zijn als volgt:

<ComponentDemo 
path='/webforj/navigatorlayout?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/navigator/NavigatorLayoutView.java'
height='200px'
/>

### 1. Geen lay-out {#1-none-layout}

De `NONE` lay-out rendert geen tekst binnen de `Navigator`, en toont alleen de navigatieknoppen zonder een standaard tekstweergave. Om deze lay-out te activeren, gebruik:

```java
navigator.setLayout(Navigator.Layout.NONE);
```

### 2. Genummerde lay-out {#2-numbered-layout}

De genummerde lay-out toont genummerde chips die overeenkomen met elke pagina binnen het weergavegebied van de `Navigator`. Het gebruik van deze lay-out is ideaal voor scenario's waarin gebruikers directe navigatie naar specifieke pagina's verkiezen. Om deze lay-out te activeren, gebruik:

```java
navigator.setLayout(Navigator.Layout.PAGES);
```

### 3. Voorbeeldlay-out {#3-preview-layout}

De voorbeeldlay-out toont het huidige paginanummer en het totale aantal pagina's, en is geschikt voor compacte pagineringsinterfaces met beperkte ruimte.

:::info
Voorbeeld is de standaard `Navigator` lay-out.
:::

Om deze lay-out te activeren, gebruik:

```java
navigator.setLayout(Navigator.Layout.PREVIEW);
```

### 4. Snelle jump lay-out {#4-quick-jump-layout}

De snelle jump lay-out biedt een [NumberField](./fields/number-field.md) voor gebruikers om een paginanummer in te voeren voor snelle navigatie. Dit is nuttig wanneer gebruikers snel naar een specifieke pagina moeten navigeren, vooral voor grote datasets. Om deze lay-out te activeren, gebruik:

```java
navigator.setLayout(Navigator.Layout.QUICK_JUMP);
```

## Stylen {#styling}

<TableBuilder name="Navigator" />

## Best practices {#best-practices}

Om een optimale gebruikerservaring te waarborgen bij het gebruik van de `Navigator` component, overweeg de volgende best practices:

- **Begrijp de dataset**: Voordat je een `Navigator` component in je app integreert, moet je de vereisten voor het doorbladeren van gegevens van je gebruikers grondig begrijpen. Overweeg factoren zoals de grootte van de dataset, typische gebruikersinteracties en voorkeuren voor navigatiepatronen.

- **Kies een geschikte lay-out**: Selecteer een lay-out voor de `Navigator` component die aansluit bij de doelstellingen voor de gebruikerservaring en de beschikbare schermruimte.

- **Pas tekst en tooltips aan**: Pas de tekst en tooltips van de `Navigator` component aan om overeen te komen met de taal en terminologie die in je app wordt gebruikt. Bied beschrijvende labels en handige hints om gebruikers te helpen bij het effectief navigeren door de dataset.
