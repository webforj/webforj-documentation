---
title: Navigator
sidebar_position: 75
_i18n_hash: 7c09a72456eb84a8227da3ff263b6c69
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-navigator" />
<DocChip chip='since' label='24.00' />
<JavadocLink type="foundation" location="com/webforj/component/navigator/Navigator" top='true'/>

De `Navigator` component is een aanpasbare pagineringcomponent die is ontworpen om door datasets te navigeren en meerdere indelingen ondersteunt. U kunt deze configureren om verschillende navigatiecontroles weer te geven, zoals eerste, laatste, volgende en vorige knoppen, samen met paginanummers of een snel springveld, afhankelijk van de indelingsinstelling.

Het ondersteunt automatische uitschakeling van navigatieknoppen op basis van de huidige pagina en het totale aantal items, en biedt aanpassingsmogelijkheden voor tekst en tooltips voor verschillende delen van de navigator. Daarnaast kunt u het koppelen aan een `Paginator` instantie om de pagineringlogica van de dataset te beheren en wijzigingen in de navigatiecontroles weer te geven.

## Koppelen aan repositories {#binding-to-repositories}

Vaak geeft een `Navigator` component informatie weer die is gevonden in een gebonden `Repository`. Deze binding stelt de `Navigator` in staat om gegevens die door de repository worden beheerd automatisch te pagineren en andere te koppelen componenten, zoals tabellen, bij te werken op basis van de genavigeerde gegevens.

Om dit te doen, geeft u eenvoudig het gewenste `Repository` object door aan de constructor van een toepasselijke `Navigator` object:

<ComponentDemo 
path='/webforj/navigatortable?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/navigator/NavigatorTableView.java'
height='475px'
/>

Dit voorbeeld maakt de `Navigator` en [`Table`](table/overview) met dezelfde `Repository` instantie. Dit betekent dat wanneer u naar een nieuwe pagina navigeert met de `Navigator`, de [`Table`](table/overview) deze wijziging herkent en opnieuw rendert.

## Paginering {#pagination}

De `Navigator` component is nauw verbonden met de `Paginator` modelklasse en berekent pagineringmetadata zoals het totale aantal pagina's, start/eindindices van items op de huidige pagina, en een array met paginanummers voor navigatie.

Hoewel niet per se noodzakelijk, maakt het gebruik van de `Paginator` de logica achter navigatie mogelijk. Bij integratie met een `Paginator` reageert de navigator op wijzigingen binnen de `Paginator`. `Navigator` objecten hebben toegang tot een ingebouwde `Paginator` via de `getPaginator()` methode. Het kan ook een `Paginator` instantie accepteren via de `setPaginator()` methode, of door gebruik te maken van een van de toepasselijke constructeurs.

Dit gedeelte bevat praktische codefragmenten om te illustreren hoe deze integratie in de praktijk werkt.

### Items {#items}

De term "items" verwijst naar de individuele gepagineerde elementen of gegevensinvoeren. Dit kunnen records, invoeren of andere discrete eenheden binnen een dataset zijn. U kunt het totale aantal items instellen met de `setTotalItems()` methode.

```java
navigator.getPaginator().setTotalItems(totalItems);
```

:::info
Een repository die is gekoppeld aan de `Paginator` instantie heeft het totale aantal items dat rechtstreeks door de repository wordt beheerd en kan niet rechtstreeks worden ingesteld.
:::

### Maximale pagina's {#maximum-pages}

De `setMax()` methode stelt u in staat om het maximale aantal paginalinks dat in de pagineringsnavigatie moet worden weergegeven, te definiëren. Dit is bijzonder nuttig wanneer u met een groot aantal pagina's werkt, aangezien het het aantal paginalinks beperkt dat op elk moment zichtbaar is voor de gebruiker.

```java
navigator.getPaginator().setMax(maxPages);
```

<ComponentDemo 
path='/webforj/navigatorpages?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/navigator/NavigatorPagesView.java'
height='125px'
/>

Dit programma toont maximaal vijf pagina's op de `Navigator` tegelijk door de `getPaginator()` methode te gebruiken om de `Paginator` op te halen die is gekoppeld aan het `Navigator` object, en vervolgens de `setMax()` methode te gebruiken om een gewenst aantal maximale pagina's weer te geven.

### Pagina grootte {#page-size}

De `setSize()` methode stelt u in staat om het aantal items dat op elke pagina van de paginering moet worden weergegeven, te specificeren. Wanneer u deze methode aanroept en een nieuwe paginagrootte opgeeft, past het de paginering dienovereenkomstig aan.

```java
navigator.getPaginator().setSize(pageSize);
```

## Aanpassen van knoppen, tekst en tooltips {#customizing-buttons-text-and-tooltips}

De `Navigator` component biedt uitgebreide aanpassingsmogelijkheden voor knoppen, tekst en tooltips. Om de weergegeven tekst op de `Navigator` component te wijzigen, gebruikt u de `setText()` methode. Deze methode neemt tekst aan, evenals het gewenste `Part` van de `Navigator`.

In het volgende voorbeeld toont de `setText()` methode een numerieke waarde aan de gebruiker. Klikken op de knoppen activeert de `onChange` methode van de `Navigator`, die wordt geleverd met een `Direction` waarde van de aangeklikte knop.

<ComponentDemo 
path='/webforj/navigatorbasic?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/navigator/NavigatorBasicView.java'
height='100px'
/>

### Knoppen en componenttekst {#buttons-and-component-text}

De `setText()` methode evalueert de tekstparameter als een JavaScript-expressie met behulp van de volgende parameters:

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
Bijvoorbeeld, om de tekst van de laatste pagina-knop in een `Navigator` met 10 pagina's in te stellen op "Ga naar pagina 10", gebruikt u het volgende codefragment:
<!-- vale on -->

```java
navigator.setText("'Ga naar pagina ' + endPage", Navigator.Part.LAST_BUTTON);
```

### Tooltip tekst {#tooltip-text}

U kunt tooltips voor verschillende delen van de `Navigator` component aanpassen met behulp van de `setTooltipText()` methode. Tooltips geven nuttige aanwijzingen aan gebruikers wanneer ze over de navigatie-elementen bewegen.

:::info
Tooltip-tekst evalueert niet naar Javascript, in tegenstelling tot de tekst die wordt gebruikt door de `setText()` methode.
:::

<!-- vale off -->
Bijvoorbeeld, om de tooltip tekst van de laatste pagina-knop in een `Navigator` in te stellen op "Ga naar de laatste pagina", gebruikt u het volgende codefragment:
<!-- vale on -->

```java
navigator.setTooltipText("Ga naar de laatste pagina", Navigator.Part.LAST_BUTTON);
```

## Indelingen {#layouts}

Er zijn verschillende indelingsopties voor de `Navigator` component om flexibiliteit te bieden bij het weergeven van pagineringscontroles. Om toegang te krijgen tot deze indelingen, gebruikt u de waarden van de `Navigator.Layout` enum. De opties zijn als volgt:

<ComponentDemo 
path='/webforj/navigatorlayout?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/navigator/NavigatorLayoutView.java'
height='200px'
/>

### 1. Geen indeling {#1-none-layout}

De `NONE` indeling rendert geen tekst binnen de `Navigator` en toont alleen de navigatieknoppen zonder een standaard tekstweergave. Om deze indeling te activeren, gebruikt u:

```java
navigator.setLayout(Navigator.Layout.NONE);
```

### 2. Nummerindeling {#2-numbered-layout}

De genummerde indeling toont genummerde chips die overeenkomen met elke pagina binnen het weergavegebied van de `Navigator`. Het gebruik van deze indeling is ideaal voor scenario's waarin gebruikers voorkeur geven aan directe navigatie naar specifieke pagina's. Om deze indeling te activeren, gebruikt u:

```java
navigator.setLayout(Navigator.Layout.PAGES);
```

### 3. Voorvertoning indeling {#3-preview-layout}

De voorvertoning indeling toont het huidige paginanummer en het totale aantal pagina's, en is geschikt voor compacte pagineringsinterfaces met beperkte ruimte.

:::info
Voorvertoning is de standaardindeling van de `Navigator`.
:::

Om deze indeling te activeren, gebruikt u:

```java
navigator.setLayout(Navigator.Layout.PREVIEW);
```

### 4. Snelle sprong indeling {#4-quick-jump-layout}

De snelle sprong indeling biedt een [NumberField](./fields/number-field.md) voor gebruikers om een paginanummer in te voeren voor snelle navigatie. Dit is nuttig wanneer gebruikers snel naar een specifieke pagina moeten navigeren, vooral voor grote datasets. Om deze indeling te activeren, gebruikt u:

```java
navigator.setLayout(Navigator.Layout.QUICK_JUMP);
```

## Stijlen {#styling}

<TableBuilder name="Navigator" />

## Beste praktijken {#best-practices}

Om een optimale gebruikerservaring te waarborgen bij het gebruik van de `Navigator` component, overweeg de volgende beste praktijken:

- **Begrijp de dataset**: Voordat u een `Navigator` component in uw app integreert, begrijpt u grondig de vereisten voor het doorbladeren van gegevens van uw gebruikers. Houd rekening met factoren zoals de grootte van de dataset, typische gebruikersinteracties en voorkeuren voor navigatiepatronen.

- **Kies een geschikte indeling**: Selecteer een indeling voor de `Navigator` component die aansluit bij de gebruikerservaringdoelen en de beschikbare schermruimte.

- **Pas tekst en tooltips aan**: Pas de tekst en tooltips van de `Navigator` component aan om overeen te komen met de taal en terminologie die in uw app wordt gebruikt. Bied beschrijvende labels en nuttige aanwijzingen om gebruikers te helpen de dataset effectief te navigeren.
