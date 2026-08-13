---
title: InfiniteScroll
sidebar_position: 60
description: >-
  Load more content as users scroll with the InfiniteScroll component, emitting
  scroll events and showing a customizable loading spinner.
_i18n_hash: f021168e8d6187e38da9107bd2f3ad65
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-infinite-scroll" />
<DocChip chip='since' label='25.00' />
<JavadocLink type="infinite-scroll" location="com/webforj/component/infinitescroll/InfiniteScroll" top='true'/>

De `InfiniteScroll` component in webforJ laadt automatisch meer inhoud terwijl gebruikers naar beneden scrollen, wat de noodzaak voor paginering elimineert. Dit creëert een soepele ervaring voor lijsten, feeds en datarijke weergaven door inhoud alleen te laden wanneer dat nodig is.

Wanneer gebruikers de onderkant van scrollbare inhoud bereiken, triggert `InfiniteScroll` een evenement voor het laden van meer gegevens. Terwijl nieuwe inhoud wordt geladen, toont het een [`Spinner`](../components/spinner) met aanpasbare tekst om aan te geven dat er meer items onderweg zijn.

<!-- INTRO_END -->

## Toestandbeheer {#state-management}

De `InfiniteScroll` component geeft evenementen af en behoudt een interne toestand om te helpen beheren hoe en wanneer inhoud wordt geladen.

<ComponentDemo
path='/webforj/infinitescroll'
frame='mobile'
files={[
  'src/main/java/com/webforj/samples/views/infinitescroll/InfiniteScrollView.java',
  'src/main/frontend/css/infinitescroll/infinitescroll.css',
]}
/>

Om meer gegevens op te halen wanneer de gebruiker scrollt, gebruik je de `onScroll()` of `addScrollListener()` methode om een listener te registreren. Binnen de listener laad je doorgaans extra inhoud en roep je `update()` aan om de toestand van `InfiniteScroll` te verversen.

```java
infiniteScroll.onScroll(event -> {
  infiniteScroll.add(new Paragraph("Loaded item"));
  infiniteScroll.update();
});
```

Zodra alle inhoud is geladen, markeer je de scroll als voltooid om verdere triggers te voorkomen. Vergeet niet `update()` aan te roepen na het instellen van volbracht:

```java
infiniteScroll.setCompleted(true);
infiniteScroll.update();
```
Dit schakelt het verdere gedrag van de oneindige scroll uit.

:::tip Reset het Laad Vlag
Je kunt deze vlag resetten met `setCompleted(false)` als je later de gebruiker toestaat om meer inhoud te laden (bijvoorbeeld na een vernieuwen).
:::


## Aanpassing van de laadindicator {#loading-indicator-customization}

Standaard toont `InfiniteScroll` een ingebouwde laadindicator - een kleine geanimeerde [`Spinner`](../components/spinner) samen met een "Gegevens laden" tekst. Je kunt de weergegeven tekst wijzigen door een aangepaste boodschap door te geven aan de `InfiniteScroll` constructor of door `setText()` te gebruiken.

```java
InfiniteScroll infiniteScroll = new InfiniteScroll("Fetching more records...");
infiniteScroll.setText("Loading more items...");
```

Evenzo kun je de [`Icon`](../components/icon) die tijdens het laden wordt weergegeven aanpassen door `setIcon()` te gebruiken.

<ComponentDemo
path='/webforj/infinitescrollloading'
frame='mobile'
files={[
  'src/main/java/com/webforj/samples/views/infinitescroll/InfiniteScrollLoadingView.java',
  'src/main/frontend/css/infinitescroll/infinitescroll.css',
]}
/>

### Volledige aanpassing {#full-customization}

Als je zowel de [`Spinner`](../components/spinner) als de tekst volledig wilt vervangen door je eigen markup,
kun je inhoud rechtstreeks in de speciale inhoudsslot toevoegen met `addToContent()`.

Wanneer je de inhoudsslot vul, vervangt deze volledig de standaard laadindeling.

<ComponentDemo
path='/webforj/infinitescrollcustomloading'
frame='mobile'
files={[
  'src/main/java/com/webforj/samples/views/infinitescroll/InfiniteScrollCustomLoadingView.java',
  'src/main/frontend/css/infinitescroll/infinitescrollcustom.css',
]}
/>

## Stijlen {#styling}

<TableBuilder name="InfiniteScroll" />
