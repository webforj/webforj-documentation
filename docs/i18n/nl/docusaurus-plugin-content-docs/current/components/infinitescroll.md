---
title: InfiniteScroll
sidebar_position: 60
_i18n_hash: afeb43fb31ce58db2860ceddd8e8527c
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-infinite-scroll" />
<DocChip chip='since' label='25.00' />
<JavadocLink type="infinite-scroll" location="com/webforj/component/infinitescroll/InfiniteScroll" top='true'/>

De `InfiniteScroll` component in webforJ laadt automatisch meer content terwijl gebruikers naar beneden scrollen, waardoor paginering niet nodig is. Dit creëert een soepele ervaring voor lijsten, feeds en data-intensieve weergaven door content alleen te laden wanneer dat nodig is.

Wanneer gebruikers de onderkant van de scrollbare inhoud bereiken, activeert `InfiniteScroll` een evenement voor het laden van meer gegevens. Terwijl nieuwe content wordt geladen, wordt er een [`Spinner`](../components/spinner) weergegeven met aanpasbare tekst om aan te geven dat er meer items onderweg zijn.

<AppLayoutViewer
path='/webforj/infinitescroll?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/infinitescroll/InfiniteScrollView.java'
cssURL='/css/infinitescroll/infinitescroll.css'
height = '400px'
mobile='true'
/>

## Toestandbeheer {#state-management}

De `InfiniteScroll` component stoot gebeurtenissen uit en behoudt een interne staat om te helpen beheren hoe en wanneer inhoud wordt geladen.

Om meer gegevens op te halen wanneer de gebruiker scrollt, gebruik je de `onScroll()` of `addScrollListener()` methode om een luisteraar te registreren. Binnen de luisteraar laad je doorgaans extra content en roep je `update()` aan om de staat van `InfiniteScroll` te vernieuwen.

```java
infiniteScroll.onScroll(event -> {
    infiniteScroll.add(new Paragraph("Item geladen"));
    infiniteScroll.update();
});
```

Zodra alle inhoud is geladen, markeer je de scroll als voltooid om verdere triggers te voorkomen. Vergeet na het instellen van voltooid niet om `update()` aan te roepen om de nieuwe staat toe te passen:

```java
infiniteScroll.setCompleted(true);
infiniteScroll.update();
```
Dit schakelt verder oneindig scrollgedrag uit.

:::tip Reset de Laadvlag
Je kunt deze vlag resetten met `setCompleted(false)` als je later de gebruiker toestaat om meer content te laden (bijv. na een verversing).
:::

## Aanpassing van de laadindicator {#loading-indicator-customization}

Standaard toont `InfiniteScroll` een ingebouwde laadindicator - een kleine geanimeerde [`Spinner`](../components/spinner) samen met een “Gegevens laden” tekst. Je kunt de weergegeven tekst wijzigen door een aangepaste boodschap door te geven aan de `InfiniteScroll` constructor of door gebruik te maken van `setText()`.

```java
InfiniteScroll infiniteScroll = new InfiniteScroll("Meer records ophalen...");
infiniteScroll.setText("Meer items laden...");
```

Evenzo kun je het [`Pictogram`](../components/icon) dat wordt weergegeven tijdens het laden aanpassen met `setIcon()`.

<AppLayoutViewer
path='/webforj/infinitescrollloading?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/infinitescroll/InfiniteScrollLoadingView.java'
cssURL='/css/infinitescroll/infinitescroll.css'
height = '400px'
mobile='true'
/>

### Volledige aanpassing {#full-customization}

Als je zowel de [`Spinner`](../components/spinner) als de tekst volledig wilt vervangen door je eigen markup,
kun je inhoud rechtstreeks in de speciale inhoudsslot toevoegen met `addToContent()`.

Wanneer je het inhoudsslot vult, vervangt het de standaard laadindeling volledig.

<AppLayoutViewer
path='/webforj/infinitescrollcustomloading?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/infinitescroll/InfiniteScrollCustomLoadingView.java'
cssURL='/css/infinitescroll/infinitescrollcustom.css'
height = '400px'
mobile='true'
/>

## Stijling {#styling}

<TableBuilder name="InfiniteScroll" />
