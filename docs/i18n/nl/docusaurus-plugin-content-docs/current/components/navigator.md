---
title: Navigator
sidebar_position: 75
_i18n_hash: be5e20c3abb0d7b64b7a0eff03f7aded
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-navigator" />
<DocChip chip='since' label='24.00' />
<JavadocLink type="foundation" location="com/webforj/component/navigator/Navigator" top='true'/>

De `Navigator`-component voegt paginacoördinaten toe voor het navigeren door gegevenssets. Het kan eerste, laatste, volgende en vorige knoppen weergeven, samen met paginanummers of een sneljump-veld, en schakelt automatisch de bedieningselementen uit wanneer deze niet van toepassing zijn. Het bindt aan een `Paginator`-instantie om de onderliggende pagineringlogica te beheren.

<!-- INTRO_END -->

## Binding aan repositories {#binding-to-repositories}

Vaak geeft een `Navigator`-component informatie weer die is gevonden in een gebonden `Repository`. Deze binding stelt de `Navigator` in staat om automatisch gegevens te pagineren die door de repository worden beheerd en andere bindbare componenten, zoals tabellen, te verversen op basis van de genavigeerde gegevens.

Om dit te doen, hoeft u alleen het gewenste `Repository`-object door te geven aan de constructor van een toepasbare `Navigator`-object:

<ComponentDemo 
path='/webforj/navigatortable?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/navigator/NavigatorTableView.java'
height='475px'
/>

Dit voorbeeld creëert de `Navigator` en [`Table`](table/overview) met dezelfde `Repository`-instantie. Dit betekent dat wanneer u naar een nieuwe pagina navigeert met de `Navigator`, de [`Table`](table/overview) deze wijziging herkent en opnieuw weergeeft.

## Paginering {#pagination}

De `Navigator`-component is nauw verbonden met de `Paginator`-modelklasse, berekent paginering metadata zoals het totale aantal pagina's, start- / eindindices van items op de huidige pagina en een array van paginanummers voor navigatie. 

Hoewel het niet expliciet noodzakelijk is, stelt het gebruik van de `Paginator` de logica achter het navigeren in staat. Bij integratie met een `Paginator` reageert de navigator op wijzigingen binnen de `Paginator`. `Navigator`-objecten hebben toegang tot een ingebouwde `Paginator` via het gebruik van de `getPaginator()`-methode. Het kan ook een `Paginator`-instantie accepteren via de `setPaginator()`-methode, of door het gebruik van een van de toepasbare constructors.

Deze sectie bevat praktische codefragmenten om te illustreren hoe deze integratie in de praktijk werkt.

### Items {#items}

De term "items" betekent de individuele gepagineerde elementen of gegevensinvoeren. Dit kunnen registers, invoeren of enige discrete eenheden binnen een gegevensset zijn. U kunt het totale aantal items instellen met de `setTotalItems()`-methode. 

```java
navigator.getPaginator().setTotalItems(totalItems);
```

:::info
Een repository die is gekoppeld aan de `Paginator`-instantie heeft het totale aantal items dat rechtstreeks door de repository wordt beheerd en kan niet direct worden ingesteld.
:::

### Maximale pagina's {#maximum-pages}

De `setMax()`-methode stelt u in staat om het maximale aantal paginalinks dat moet worden weergegeven in de pagineringnavigatie te definiëren. Dit is vooral handig bij het omgaan met een groot aantal pagina's, omdat het het aantal paginalinks dat op elk moment zichtbaar is voor de gebruiker controleert.

```java
navigator.getPaginator().setMax(maxPages);
```

<ComponentDemo 
path='/webforj/navigatorpages?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/navigator/NavigatorPagesView.java'
height='125px'
/>

Dit programma toont een maximum van vijf pagina's op de `Navigator` tegelijkertijd door de `getPaginator()`-methode te gebruiken om de `Paginator` die aan het `Navigator`-object is gekoppeld op te halen en vervolgens de `setMax()`-methode te gebruiken om het gewenste aantal weergegeven maximale pagina's op te geven.

### Paginaformaat {#page-size}

De `setSize()`-methode stelt u in staat om het aantal items in te stellen dat op elke pagina van de paginering moet worden weergegeven. Wanneer u deze methode aanroept en een nieuwe pagina-grootte opgeeft, past het de paginering dienovereenkomstig aan. 

```java
navigator.getPaginator().setSize(pageSize);
```

## Personaliseren van knoppen, tekst en tooltips {#customizing-buttons-text-and-tooltips}

De `Navigator`-component biedt uitgebreide aanpassingsmogelijkheden voor knoppen, tekst en tooltips. Om de weergegeven tekst op de `Navigator`-component te wijzigen, gebruik de `setText()`-methode. Deze methode neemt tekst, evenals het gewenste `Part` van de `Navigator`. 

In het volgende voorbeeld geeft de `setText()`-methode een numerieke waarde aan de gebruiker weer. Het klikken op de knoppen activeert de `onChange`-methode van de `Navigator`, die komt met een `Direction`-waarde van de aangeklikte knop. 

<ComponentDemo 
path='/webforj/navigatorbasic?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/navigator/NavigatorBasicView.java'
height='100px'
/>

### Knoppen en componenttekst {#buttons-and-component-text}

De `setText()`-methode evalueert de tekstparameter als een JavaScript-expressie met de volgende parameters:

- `page` - het huidige paginanummer
- `current` - het momenteel geselecteerde paginanummer
- `x` - een alias voor de huidige pagina
- `startIndex` - Het startindex van de huidige pagina.
- `endIndex` - De eindindex van de huidige pagina.
- `totalItems` - Het totale aantal items.
- `startPage` - Het startpaginanummer.
- `endPage` - Het eindpaginanummer.
- `component` - De cliëntcomponent van de Navigator.

<!-- vale off -->
Bijvoorbeeld, om de tekst van de laatste pagina-knop in een `Navigator` met 10 pagina's in te stellen op "Ga naar pagina 10", gebruik de volgende codefragment: 
<!-- vale on -->

```java
navigator.setText("'Ga naar pagina ' + endPage", Navigator.Part.LAST_BUTTON);
```

### Tooltip-tekst {#tooltip-text}

U kunt tooltips voor verschillende delen van de `Navigator`-component aanpassen met behulp van de `setTooltipText()`-methode. Tooltips bieden nuttige hints aan gebruikers wanneer ze over navigatie-elementen zweven.

:::info
Tooltip-tekst evalueert niet naar JavaScript, in tegenstelling tot de tekst die door de `setText()`-methode wordt gebruikt.
:::

<!-- vale off -->
Bijvoorbeeld, om de tooltip-tekst van de laatste pagina-knop in een `Navigator` in te stellen op "Ga naar de laatste pagina", gebruik de volgende codefragment:
<!-- vale on -->

```java
navigator.setTooltipText("Ga naar de laatste pagina", Navigator.Part.LAST_BUTTON);
```

## Indelingen {#layouts}

Diverse indelingsopties bestaan voor de `Navigator`-component om flexibiliteit te bieden bij het weergeven van pagineringsbedieningselementen. Om toegang te krijgen tot deze indelingen, gebruikt u de waarden van de `Navigator.Layout`-enum. De opties zijn als volgt:

<ComponentDemo 
path='/webforj/navigatorlayout?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/navigator/NavigatorLayoutView.java'
height='200px'
/>

### 1. Geen indeling {#1-none-layout}

De `NONE`-indeling rendert geen tekst binnen de `Navigator`, en toont alleen de navigatieknoppen zonder een standaard tekstuele weergave. Om deze indeling te activeren, gebruikt u:

```java
navigator.setLayout(Navigator.Layout.NONE);
```

### 2. Genummerde indeling {#2-numbered-layout}

De genummerde indeling toont genummerde chips die overeenkomen met elke pagina binnen het weergavegebied van de `Navigator`. Het gebruik van deze indeling is ideaal voor scenario's waarin gebruikers directe navigatie naar specifieke pagina's wensen. Om deze indeling te activeren, gebruikt u:

```java
navigator.setLayout(Navigator.Layout.PAGES);
```

### 3. Voorvertoning indeling {#3-preview-layout}

De voorvertoning-indeling toont het huidige paginanummer en het totale aantal pagina's, en is geschikt voor compacte pagineringsinterfaces met beperkte ruimte.

:::info
Voorvertoning is de standaardindeling van de `Navigator`.
:::

Om deze indeling te activeren, gebruikt u:

```java
navigator.setLayout(Navigator.Layout.PREVIEW);
```

### 4. Snelle sprong indeling {#4-quick-jump-layout}

De snelle sprong-indeling biedt een [NumberField](./fields/number-field.md) voor gebruikers om een paginanummer in te voeren voor snelle navigatie. Dit is nuttig wanneer gebruikers snel naar een specifieke pagina moeten navigeren, vooral voor grote gegevenssets. Om deze indeling te activeren, gebruikt u:

```java
navigator.setLayout(Navigator.Layout.QUICK_JUMP);
```

## Stijlen {#styling}

<TableBuilder name="Navigator" />

## Beste praktijken {#best-practices}

Om een optimale gebruikerservaring te garanderen bij het gebruik van de `Navigator`-component, houdt u rekening met de volgende beste praktijken: 

- **Begrijp gegevensset**: Voordat u een `Navigator`-component in uw app integreert, moet u grondig de gegevensdoorbladervereisten van uw gebruikers begrijpen. Houd rekening met factoren zoals de grootte van de gegevensset, typische gebruikersinteracties en voorkeuren voor navigatiepatronen.

- **Kies een geschikte indeling**: Selecteer een indeling voor de `Navigator`-component die aansluit bij de gebruikerservaringdoelen en beschikbare schermruimte.

- **Pas tekst en tooltips aan**: Pas de tekst en tooltips van de `Navigator`-component aan om overeen te komen met de taal en terminologie die in uw app wordt gebruikt. Bied beschrijvende labels en nuttige hints om gebruikers te helpen effectief door de gegevensset te navigeren.
