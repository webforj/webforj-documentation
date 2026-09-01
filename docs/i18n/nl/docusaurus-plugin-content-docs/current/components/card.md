---
title: Card
sidebar_position: 17
sidebar_class_name: new-content
description: >-
  Group related content and actions with the Card component, including slotted
  regions. orientation, elevation, dividers, and click handling.
_i18n_hash: 08b0239bc5bbeb0b14f3b03dda7b8b17
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-card" />
<DocChip chip='since' label='26.02' />
<JavadocLink type="card" location="com/webforj/component/card/Card" top='true'/>

De `Card`-component biedt een oppervlak voor het groeperen van gerelateerde inhoud en acties in één item. Het ondersteunt geconfigureerde regio's voor een figuur, een kop, een lichaam en een voettekst, samen met instellingen voor oriëntatie, schaduw, scheidingslijnen en dichtheid die bepalen hoe de kaart wordt weergegeven.

<!-- INTRO_END -->

## Het maken van een `Card` {#creating-a-card}

Maak een `Card` door inhoud door te geven aan de constructor, waarmee die inhoud in het lichaam van de kaart wordt geplaatst. Het lichaam kan ook na de creatie worden gevuld met `add()` of `addToBody()`, die hetzelfde doen.

```java
Card card = new Card(new Paragraph("Verkopen stegen in elke regio."));

//Equivalent
Card card = new Card();
card.addToBody(new Paragraph("Verkopen stegen in elke regio."));
```

Een lege `Card` toont alleen zijn omtrek en verder niets.

## Regio's van de Card {#card-regions}

Elke regio, afgezien van het lichaam, wordt gevuld via zijn eigen slot, en een regio waarvan het slot geen inhoud bevat, wordt niet weergegeven. Een `Card` zonder voettekst sluit na het lichaam, en een `Card` met alleen een lichaam is een omlijnd blok inhoud.

- `addToFigure()` bevat de illustratie van de Card, zoals een afbeelding, een video of een diagram. De positie hangt af van de oriëntatie van de Card.
- `addToIcon()` stelt de voorste visuele weergave in de kopregel in en accepteert elk component, inclusief een `Icon` of een `Avatar`.
- `addToTitle()` stelt de kop in de kopregel in.
- `addToCaption()` voegt een secundaire regel toe onder de titel, nuttig voor een datum, een auteur of een status.
- `addToHeaderActions()` vult het einde van de kopregel, meestal met een `Button` of een menu.
- `addToFooter()` sluit de `Card`, meestal met acties of metadata.

```java
Card card = new Card(new Paragraph("Verkopen stegen in elke regio."));
card.addToFigure(new Img("cover.png", "Omslag rapport"))
    .addToIcon(TablerIcon.create("chart-bar"))
    .addToTitle(new H3("Maandrapport"))
    .addToCaption(new Paragraph("Juli 2026"))
    .addToHeaderActions(new Button("Deel"))
    .addToFooter(new Button("Lees meer"));
````

:::info Titel en toegankelijke naam
Een `Card` kondigt zichzelf aan als een regio, en de titel wordt de toegankelijke naam. Gebruik een kop-element zoals `H3` zodat gebruikers van schermlezers de `Card` kunnen vinden via de kopstructuur van de pagina.
:::

<ComponentDemo
path='/webforj/cardregions'
files={[
  'src/main/java/com/webforj/samples/views/card/CardRegionsView.java',
  'src/main/frontend/css/card/cardRegions.css',
]}
height='700px'
/>

## Oriëntatie {#orientation}

Oriëntatie bepaalt waar de figuur zich verhoudt tot de andere regio's en wordt ingesteld met `setOrientation()`.

Kaarten zijn standaard verticaal, zodat ze de figuur boven de kop, het lichaam en de voettekst stapelen. Dit is geschikt voor kaarten die in een raster zijn gerangschikt, waar elke kaart een smalle kolom beslaat. Het doorgeven van `Card.Orientation.HORIZONTAL` aan `setOrientation()` maakt de kaart horizontaal en plaatst de figuur naast die regio's.

```java
card.setOrientation(Card.Orientation.HORIZONTAL);
```

<ComponentDemo
path='/webforj/cardorientation'
files={['src/main/java/com/webforj/samples/views/card/CardOrientationView.java']}
height='500px'
/>

Omdat de instelling de figuur verplaatst en niets anders, ziet een `Card` zonder figuur er in beide oriëntaties hetzelfde uit.

## Schaduw en rand {#elevation-and-border}

Twee instellingen bepalen hoe ver de `Card` zich scheidt van de pagina erachter. `setShadow()` past een waarde toe van de schaduw-schaal, die loopt van `NONE` via `XSMALL`, `SMALL`, `MEDIUM`, `LARGE`, en `XLARGE` tot `XXLARGE`. `setBorderless()` bepaalt of de `Card` zijn rand tekent. De standaardinstellingen zijn `Shadow.XSMALL` met de rand getekend.

De instellingen zijn onafhankelijk, zodat elke schaduw kan worden gecombineerd met of zonder de rand.

<ComponentDemo
path='/webforj/cardappearance'
files={[
  'src/main/java/com/webforj/samples/views/card/CardAppearanceView.java']}
height='300px'
/>

## Scheidingslijnen en ruimte {#dividers-and-expanse}

Terwijl schaduw- en randinstellingen bepalen hoe de `Card` zich ten opzichte van de pagina bevindt, regelen scheidingslijnen en ruimte de leesbare regio's binnen de kaart zelf.

`setDivided(true)` tekent een scheidingslijn na de kop en vóór de voettekst, wat helpt wanneer de regio's dichte inhoud bevatten. Scheidingslijnen zijn standaard uitgeschakeld. Een scheidingslijn voor een regio die geen inhoud bevat, wordt niet getekend, dus een verdeelde kaart zonder voettekst toont één scheidingslijn, onder de kop. Scheidingslijnen hebben meer gewicht op vlakke kaarten, waar er geen omlijning is om dat werk te doen.

`setExpanse()` regelt de dichtheid, die padding, de gaten tussen regio's en de grootte van de titel en het onderschrift aanstuurt. `Card` gebruikt de gedeelde `Expanse`-enum, die `NONE`, `XSMALL`, `SMALL`, `MEDIUM`, `LARGE`, en `XLARGE` biedt, met `MEDIUM` als standaard. Kleinere ruimtes zijn geschikt voor dashboardtegels en zijbalken, waar verschillende kaarten het scherm delen.

Het volgende voorbeeld toont twee `Card`-componenten met scheidingslijnen. Eén `Card` gebruikt `Expanse.LARGE`, terwijl de andere `Expanse.SMALL` gebruikt:

<ComponentDemo
path='/webforj/carddensity'
files={[
  'src/main/java/com/webforj/samples/views/card/CardDensityView.java',
  'src/main/frontend/css/card/cardDensity.css',
]}
height='400px'
/>

## Klikgebeurtenissen {#click-events}

De `Card`-component implementeert `HasElementClickListener`, zodat een listener die is geregistreerd met `onClick()` of `addClickListener()` een `ElementClickEvent` ontvangt. Dit maakt het hele oppervlak tot één doel.

```java
card.onClick(event -> Router.getCurrent().navigate(new Location("/reports/july")));
```

:::warning Klikken vanuit de `Card`
Klikken op componenten binnen de `Card` bereiken ook de `Card`, zodat een `Card` met zijn eigen listener deze activeert wanneer de gebruiker op een `Button` in de kopacties of de voettekst drukt. Voeg een listener toe aan de `Card` wanneer de `Card` één duidelijke actie heeft, en reserveer de knoppen erin voor acties die de `Card` zelf niet uitvoert.
:::

## Stijlen {#styling}

<TableBuilder name="Card" />
