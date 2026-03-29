---
title: InfiniteScroll
sidebar_position: 60
_i18n_hash: b41c992436f501c03ae93b1dfc2c254b
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-infinite-scroll" />
<DocChip chip='since' label='25.00' />
<JavadocLink type="infinite-scroll" location="com/webforj/component/infinitescroll/InfiniteScroll" top='true'/>

De `InfiniteScroll` component in webforJ laadt automatisch meer inhoud wanneer gebruikers naar beneden scrollen, waardoor paginering niet nodig is. Dit creëert een soepele ervaring voor lijsten, feeds en datarijke weergaven door inhoud alleen te laden wanneer dat nodig is.

Wanneer gebruikers de onderkant van de scrollbare inhoud bereiken, activeert `InfiniteScroll` een gebeurtenis voor het laden van meer gegevens. Terwijl nieuwe inhoud wordt geladen, toont het een [`Spinner`](../components/spinner) met aanpasbare tekst om aan te geven dat er meer items onderweg zijn.

<!-- INTRO_END -->

## Toestandsbeheer {#state-management}

De `InfiniteScroll` component genereert evenementen en houdt interne status bij om te helpen bij het beheren van hoe en wanneer inhoud wordt geladen.

<AppLayoutViewer
path='/webforj/infinitescroll?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/infinitescroll/InfiniteScrollView.java'
cssURL='/css/infinitescroll/infinitescroll.css'
height = '400px'
mobile='true'
/>

Om meer gegevens op te halen wanneer de gebruiker scrollt, gebruik de `onScroll()` of `addScrollListener()` methode om een listener te registreren. Binnen de listener laad je doorgaans extra inhoud en roep je `update()` aan om de `InfiniteScroll` status te vernieuwen.

```java
infiniteScroll.onScroll(event -> {
  infiniteScroll.add(new Paragraph("Loaded item"));
  infiniteScroll.update();
});
```

Zodra alle inhoud is geladen, markeer de scroll als voltooid om verdere triggers te voorkomen. Na het instellen van voltooid, vergeet niet om `update()` aan te roepen om de nieuwe status toe te passen:

```java
infiniteScroll.setCompleted(true);
infiniteScroll.update();
```
Dit schakelt verder oneindig scrollen uit.

:::tip Reset de Laad Vlag
Je kunt deze vlag resetten met `setCompleted(false)` als je later de gebruiker toestaat om meer inhoud te laden (bijvoorbeeld na een vernieuwen).
:::


## Aanpassing laadindicator {#loading-indicator-customization}

Standaard toont `InfiniteScroll` een ingebouwde laadindicator - een kleine geanimeerde [`Spinner`](../components/spinner) met de tekst “Gegevens laden”. Je kunt de weergegeven tekst veranderen door een aangepaste boodschap door te geven aan de `InfiniteScroll` constructor of door `setText()` te gebruiken.

```java
InfiniteScroll infiniteScroll = new InfiniteScroll("Bezig met het ophalen van meer records...");
infiniteScroll.setText("Bezig met het laden van meer items...");
```

Op een vergelijkbare manier kun je het [`Icon`](../components/icon) dat tijdens het laden wordt weergegeven aanpassen met `setIcon()`.

<AppLayoutViewer
path='/webforj/infinitescrollloading?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/infinitescroll/InfiniteScrollLoadingView.java'
cssURL='/css/infinitescroll/infinitescroll.css'
height = '400px'
mobile='true'
/>

### Volledige aanpassing {#full-customization}

Als je zowel de [`Spinner`](../components/spinner) als de tekst volledig wilt vervangen door je eigen markup, kun je inhoud rechtstreeks in de speciale inhoudslot toevoegen met `addToContent()`.

Wanneer je de inhoudslot vuldt, vervangt het de standaard laadlay-out volledig.

<AppLayoutViewer
path='/webforj/infinitescrollcustomloading?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/infinitescroll/InfiniteScrollCustomLoadingView.java'
cssURL='/css/infinitescroll/infinitescrollcustom.css'
height = '400px'
mobile='true'
/>

## Stijl {#styling}

<TableBuilder name="InfiniteScroll" />
