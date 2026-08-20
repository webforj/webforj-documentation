---
title: Card
sidebar_position: 17
sidebar_class_name: new-content
description: Group related content and actions with the Card component, including slotted regions. orientation, elevation, dividers, and click handling.
---

<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-card" />
<DocChip chip='since' label='26.02' />
<JavadocLink type="card" location="com/webforj/component/card/Card" top='true'/>

The `Card` component provides a surface for grouping related content and actions into a single item. It supports slotted regions for a figure, a header, a body, and a footer, along with orientation, elevation, divider, and density settings that control how the card is presented.

<!-- INTRO_END -->

## Creating a `Card` {#creating-a-card}

Create a `Card` by passing content to its constructor, which places that content in the card's body. The body can also be filled after creation with `add()` or `addToBody()`. which do the same thing.

```java
Card card = new Card(new Paragraph("Sales climbed across every region."));

//Equivalent 
Card card = new Card();
card.addToBody(new Paragraph("Sales climbed across every region."));
```

An empty `Card` renders its frame and nothing else.

## Card regions {#card-regions}

Each region, apart from the body, is filled through its own slot, and a region whose slot holds no content isn't rendered. A `Card` without a footer closes after the body, and a `Card` with a body alone is a framed block of content.

- `addToFigure()` holds the Card's illustration, such as an image, a video, or a chart. Its position depends on the Card's orientation.
- `addToIcon()` sets the leading visual in the header row and accepts any component, including an `Icon` or an `Avatar`.
- `addToTitle()` sets the heading in the header row.
- `addToCaption()` adds a secondary line under the title, useful for a date, an author, or a status.
- `addToHeaderActions()` fills the end of the header row, usually with a `Button` or a menu.
- `addToFooter()` closes the `Card`, usually with actions, or metadata.

```java
Card card = new Card(new Paragraph("Sales climbed across every region."));
card.addToFigure(new Img("cover.png", "Report cover"))
    .addToIcon(TablerIcon.create("chart-bar"))
    .addToTitle(new H3("Monthly report"))
    .addToCaption(new Paragraph("July 2026"))
    .addToHeaderActions(new Button("Share"))
    .addToFooter(new Button("Read more"));
````

:::info Title and accessible name
A `Card` announces itself as a region, and the title becomes its accessible name. Use a heading element such as `H3` there so screen reader users can find the `Card` through the page's heading structure.
:::

<ComponentDemo
path='/webforj/cardregions'
files={[
  'src/main/java/com/webforj/samples/views/card/CardRegionsView.java',
  'src/main/frontend/css/card/cardRegions.css',
]}
height='700px'
/>

## Orientation {#orientation}

Orientation controls where the figure sits relative to the other regions, and is set with `setOrientation()`. 

Cards are vertical by default, so they stack the figure above the header, body, and footer. This suits cards arranged in a grid, where each one occupies a narrow column. Passing `Card.Orientation.HORIZONTAL` to `setOrientation()` makes the card horizontal instead, placing the figure beside those regions.

```java
card.setOrientation(Card.Orientation.HORIZONTAL);
```

<ComponentDemo
path='/webforj/cardorientation'
files={['src/main/java/com/webforj/samples/views/card/CardOrientationView.java']}
height='500px'
/>

Because the setting moves the figure and nothing else, a `Card` without a figure looks the same in either orientation.

## Elevation and border {#elevation-and-border}

Two settings determine how far the `Card` separates from the page behind it. `setShadow()` applies a value from the shadow scale, which runs from `NONE` through `XSMALL`, `SMALL`, `MEDIUM`, `LARGE`, and `XLARGE` to `XXLARGE`. `setBorderless()` controls whether the `Card` draws its border. The defaults are `Shadow.XSMALL` with the border drawn.

The settings are independent, so any shadow can be paired with or without the border. Three pairings are common enough to have names:

- An **outlined** `Card` keeps the border and sets a `Shadow.NONE`, marking a bounded area without lifting off the page.
- An **elevated** `Card` drops the border with `setBorderless(true)` and raises the shadow, so it stands above the surface behind it.
- A **flat** `Card` drops both, grouping its content without any separation of its own.

<ComponentDemo
path='/webforj/cardappearance'
files={[
  'src/main/java/com/webforj/samples/views/card/CardAppearanceView.java',
  'src/main/frontend/css/card/cardAppearance.css',
]}
height='250px'
/>

## Dividers and expanse {#dividers-and-expanse}

While elevation and border settings control how the `Card` sits against the page, dividers and expanse control the readable regions within the card itself.

`setDivided(true)` draws a divider after the header and before the footer, which helps when the regions hold dense content. Dividers are off by default. A divider for a region that holds no content isn't drawn, so a divided card without a footer shows one divider, under the header. Dividers carry more weight on flat cards, where no frame is present to do that work.

`setExpanse()` controls density, driving the padding, the gaps between regions, and the size of the title and the caption. `Card` uses the shared `Expanse` enum, which offers `NONE`, `XSMALL`, `SMALL`, `MEDIUM`, `LARGE`, and `XLARGE`, with `MEDIUM` as the default. Smaller expanses suit dashboard tiles and sidebars, where several cards share the screen.

The following example shows two `Card` components with dividers. One `Card` is using `Expanse.LARGE`, while the other is using `Expanse.SMALL`:

<ComponentDemo
path='/webforj/carddensity'
files={[
  'src/main/java/com/webforj/samples/views/card/CardDensityView.java',
  'src/main/frontend/css/card/cardDensity.css',
]}
height='400px'
/>

## Click events {#click-events}

The `Card` component implements `HasElementClickListener`, so a listener registered with `onClick()` or `addClickListener()` receives an `ElementClickEvent`. This makes the whole surface a single target.

```java
card.onClick(event -> Router.getCurrent().navigate(new Location("/reports/july")));
```

:::warning Clicks from inside the `Card`
Clicks on components inside the `Card` also reach the `Card`, so a `Card` with its own listener fires it when the user presses a `Button` in the header actions or the footer. Add a listener to the `Card` when the `Card` has one clear action, and reserve the buttons inside it for actions the `Card` itself doesn't perform.
:::

## Styling {#styling}

<TableBuilder name="Card" />