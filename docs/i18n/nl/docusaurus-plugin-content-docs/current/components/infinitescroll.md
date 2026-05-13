---
title: InfiniteScroll
sidebar_position: 60
_i18n_hash: 8c7fc66f78d6508466b5fb9b5dfc3a68
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-infinite-scroll" />
<DocChip chip='since' label='25.00' />
<JavadocLink type="infinite-scroll" location="com/webforj/component/infinitescroll/InfiniteScroll" top='true'/>

De `InfiniteScroll` component in webforJ laadt automatisch meer inhoud als gebruikers naar beneden scrollen, waardoor paginering niet nodig is. Dit creëert een soepele ervaring voor lijsten, feeds en gegevensrijke weergaven door inhoud alleen te laden wanneer dat nodig is.

Wanneer gebruikers de onderkant van scrollbare inhoud bereiken, activeert `InfiniteScroll` een gebeurtenis om meer gegevens te laden. Terwijl nieuwe inhoud wordt geladen, toont het een [`Spinner`](../components/spinner) met aanpasbare tekst om aan te geven dat er meer items onderweg zijn.

<!-- INTRO_END -->

## Toestandbeheer {#state-management}

De `InfiniteScroll` component geeft evenementen af en beheert de interne toestand om te helpen beheren hoe en wanneer inhoud wordt geladen.

<ComponentDemo
path='/webforj/infinitescroll'
frame='mobile'
files={[
  'src/main/java/com/webforj/samples/views/infinitescroll/InfiniteScrollView.java',
  'src/main/resources/static/css/infinitescroll/infinitescroll.css',
]}
/>

Om meer gegevens op te halen wanneer de gebruiker scrollt, gebruik de `onScroll()` of `addScrollListener()` methode om een listener te registreren. Binnen de listener laad je doorgaans extra inhoud en roep je `update()` aan om de toestand van `InfiniteScroll` te verversen.

```java
infiniteScroll.onScroll(event -> {
  infiniteScroll.add(new Paragraph("Geladen item"));
  infiniteScroll.update();
});
```

Zodra alle inhoud is geladen, markeer je de scroll als voltooid om verdere triggers te voorkomen. Na het instellen van voltooid, vergeet niet om `update()` aan te roepen om de nieuwe toestand toe te passen:

```java
infiniteScroll.setCompleted(true);
infiniteScroll.update();
```
Dit schakelt verder oneindig scrollgedrag uit.

:::tip Reset het Laad Vlag
Je kunt deze vlag resetten met `setCompleted(false)` als je later de gebruiker toestaat om meer inhoud te laden (bijv. na een verversing).
:::

## Aanpassing van de laadindicator {#loading-indicator-customization}

Standaard toont `InfiniteScroll` een ingebouwde laadindicator - een kleine geanimeerde [`Spinner`](../components/spinner) samen met een “Gegevens laden” tekst. Je kunt de weergegeven tekst wijzigen door een aangepaste boodschap door te geven aan de `InfiniteScroll` constructor of door `setText()` te gebruiken.

```java
InfiniteScroll infiniteScroll = new InfiniteScroll("Meer records ophalen...");
infiniteScroll.setText("Meer items laden...");
```

Op dezelfde manier kun je het [`Icon`](../components/icon) dat tijdens het laden wordt weergegeven aanpassen met `setIcon()`.

<ComponentDemo
path='/webforj/infinitescrollloading'
frame='mobile'
files={[
  'src/main/java/com/webforj/samples/views/infinitescroll/InfiniteScrollLoadingView.java',
  'src/main/resources/static/css/infinitescroll/infinitescroll.css',
]}
/>

### Volledige aanpassing {#full-customization}

Als je zowel de [`Spinner`](../components/spinner) als de tekst volledig wilt vervangen door je eigen markup,
kun je inhoud rechtstreeks in de speciale inhoudsslot toevoegen met `addToContent()`.

Wanneer je het inhoudsslot vult, vervangt het volledig de standaard laadindeling.

<ComponentDemo
path='/webforj/infinitescrollcustomloading'
frame='mobile'
files={[
  'src/main/java/com/webforj/samples/views/infinitescroll/InfiniteScrollCustomLoadingView.java',
  'src/main/resources/static/css/infinitescroll/infinitescrollcustom.css',
]}
/>

## Styling {#styling}

<TableBuilder name="InfiniteScroll" />
