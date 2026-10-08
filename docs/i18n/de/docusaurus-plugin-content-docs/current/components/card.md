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

Die `Card`-Komponente bietet eine Oberfläche, um verwandte Inhalte und Aktionen in einem einzelnen Element zu gruppieren. Sie unterstützt bestimmte Bereiche für eine Abbildung, einen Header, einen Körper und einen Footer sowie Einstellungen für Ausrichtung, Elevation, Trennlinien und Dichte, die steuern, wie die Karte präsentiert wird.

<!-- INTRO_END -->

## Erstellung einer `Card` {#creating-a-card}

Erstellen Sie eine `Card`, indem Sie Inhalte an ihren Konstruktor übergeben, der diese Inhalte im Körper der Karte platziert. Der Körper kann auch nach der Erstellung mit `add()` oder `addToBody()` gefüllt werden, die das Gleiche tun.

```java
Card card = new Card(new Paragraph("Die Verkaufszahlen sind in allen Regionen gestiegen."));

//Entsprechend
Card card = new Card();
card.addToBody(new Paragraph("Die Verkaufszahlen sind in allen Regionen gestiegen."));
```

Eine leere `Card` zeigt nur ihren Rahmen und sonst nichts.

## Kartenregionen {#card-regions}

Jede Region, abgesehen vom Körper, wird über ihren eigenen Slot gefüllt, und eine Region, deren Slot keinen Inhalt hat, wird nicht angezeigt. Eine `Card` ohne Footer endet nach dem Körper, und eine `Card` mit nur einem Körper ist ein gerahmter Block von Inhalten.

- `addToFigure()` hält die Abbildung der Karte, wie ein Bild, ein Video oder ein Diagramm. Ihre Position hängt von der Ausrichtung der Karte ab.
- `addToIcon()` setzt die führende visuelle Darstellung in der Kopfzeile und akzeptiert jede Komponente, einschließlich eines `Icon` oder eines `Avatar`.
- `addToTitle()` setzt die Überschrift in der Kopfzeile.
- `addToCaption()` fügt eine sekundäre Zeile unter dem Titel hinzu, die nützlich für ein Datum, einen Autor oder einen Status ist.
- `addToHeaderActions()` füllt das Ende der Kopfzeile, normalerweise mit einem `Button` oder einem Menü.
- `addToFooter()` schließt die `Card`, normalerweise mit Aktionen oder Metadaten.

```java
Card card = new Card(new Paragraph("Die Verkaufszahlen sind in allen Regionen gestiegen."));
card.addToFigure(new Img("cover.png", "Titelseite des Berichts"))
    .addToIcon(TablerIcon.create("chart-bar"))
    .addToTitle(new H3("Monatsbericht"))
    .addToCaption(new Paragraph("Juli 2026"))
    .addToHeaderActions(new Button("Teilen"))
    .addToFooter(new Button("Mehr lesen"));
```

:::info Titel und zugänglicher Name
Eine `Card` kündigt sich als Region an, und der Titel wird zu ihrem zugänglichen Namen. Verwenden Sie ein Überschriftselement wie `H3`, damit Nutzer von Bildschirmlesegeräten die `Card` in der Überschriftenstruktur der Seite finden können.
:::

<ComponentDemo
path='/webforj/cardregions'
files={[
  'src/main/java/com/webforj/samples/views/card/CardRegionsView.java',
  'src/main/frontend/css/card/cardRegions.css',
]}
height='700px'
/>

## Ausrichtung {#orientation}

Die Ausrichtung steuert, wo die Abbildung im Vergleich zu den anderen Regionen sitzt, und wird mit `setOrientation()` festgelegt.

Karten sind standardmäßig vertikal, sodass sie die Abbildung über dem Header, Körper und Footer anordnen. Dies eignet sich für Karten, die in einem Grid angeordnet sind, wo jede eine schmale Spalte einnimmt. Wenn `Card.Orientation.HORIZONTAL` an `setOrientation()` übergeben wird, wird die Karte horizontal, indem die Abbildung neben diesen Regionen platziert wird.

```java
card.setOrientation(Card.Orientation.HORIZONTAL);
```

<ComponentDemo
path='/webforj/cardorientation'
files={['src/main/java/com/webforj/samples/views/card/CardOrientationView.java']}
height='500px'
/>

Da die Einstellung nur die Abbildung bewegt und nichts anderes, sieht eine `Card` ohne Abbildung in beiden Ausrichtungen gleich aus.

## Elevation und Rand {#elevation-and-border}

Zwei Einstellungen bestimmen, wie weit sich die `Card` von der Seite dahinter entfernt. `setShadow()` wendet einen Wert aus der Schattenskala an, die von `NONE` über `XSMALL`, `SMALL`, `MEDIUM`, `LARGE` und `XLARGE` bis zu `XXLARGE` reicht. `setBorderless()` steuert, ob die `Card` ihren Rand zeichnet. Die Standardeinstellungen sind `Shadow.XSMALL` mit gezeichnetem Rand.

Die Einstellungen sind unabhängig, sodass jeder Schatten ohne oder mit Rand kombiniert werden kann.

<ComponentDemo
path='/webforj/cardappearance'
files={[
  'src/main/java/com/webforj/samples/views/card/CardAppearanceView.java']}
height='300px'
/>

## Trennlinien und Ausdehnung {#dividers-and-expanse}

Während die Einstellungen für Elevation und Rand steuern, wie die `Card` gegen die Seite sitzt, steuern Trennlinien und Ausdehnung die lesbaren Regionen innerhalb der Karte selbst.

`setDivided(true)` zieht eine Trennlinie nach dem Header und vor dem Footer, was hilfreich ist, wenn die Regionen dichte Inhalte haben. Trennlinien sind standardmäßig deaktiviert. Eine Trennlinie für eine Region, die keinen Inhalt hat, wird nicht gezeichnet, sodass eine geteilte Karte ohne Footer eine Trennlinie unter dem Header zeigt. Trennlinien haben auf flachen Karten mehr Gewicht, wo kein Rahmen vorhanden ist, der diese Arbeit übernimmt.

`setExpanse()` steuert die Dichte, indem es den Abstand, die Lücken zwischen den Regionen und die Größe des Titels und der Caption steuert. `Card` verwendet das gemeinsame `Expanse`-Enum, das `NONE`, `XSMALL`, `SMALL`, `MEDIUM`, `LARGE` und `XLARGE` bietet, wobei `MEDIUM` der Standard ist. Kleinere Ausdehnungen eignen sich für Dashboard-Kacheln und Seitenleisten, wo mehrere Karten den Bildschirm teilen.

Das folgende Beispiel zeigt zwei `Card`-Komponenten mit Trennlinien. Eine `Card` verwendet `Expanse.LARGE`, während die andere `Expanse.SMALL` verwendet:

<ComponentDemo
path='/webforj/carddensity'
files={[
  'src/main/java/com/webforj/samples/views/card/CardDensityView.java',
  'src/main/frontend/css/card/cardDensity.css',
]}
height='400px'
/>

## Klickereignisse {#click-events}

Die `Card`-Komponente implementiert `HasElementClickListener`, sodass ein Zuhörer, der mit `onClick()` oder `addClickListener()` registriert ist, ein `ElementClickEvent` erhält. Dies macht die gesamte Oberfläche zu einem einzigen Ziel.

```java
card.onClick(event -> Router.getCurrent().navigate(new Location("/reports/july")));
```

:::warning Klicks innerhalb der `Card`
Klicks auf Komponenten innerhalb der `Card` erreichen ebenfalls die `Card`, sodass eine `Card` mit ihrem eigenen Zuhörer dieses auslöst, wenn der Benutzer auf einen `Button` in den Header-Aktionen oder im Footer klickt. Fügen Sie einen Zuhörer zur `Card` hinzu, wenn die `Card` eine eindeutige Aktion hat, und reservieren Sie die Schaltflächen darin für Aktionen, die die `Card` selbst nicht durchführt.
:::

## Styling {#styling}

<TableBuilder name="Card" />
