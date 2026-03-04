---
title: InfiniteScroll
sidebar_position: 60
_i18n_hash: b6795a86cf03a60d9ef9e7d89749c9ab
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-infinite-scroll" />
<DocChip chip='since' label='25.00' />
<JavadocLink type="infinite-scroll" location="com/webforj/component/infinitescroll/InfiniteScroll" top='true'/>

De `InfiniteScroll` component in webforJ laadt automatisch meer inhoud terwijl gebruikers naar beneden scrollen, waardoor paginering niet nodig is. Dit creëert een soepele ervaring voor lijsten, feeds en datarijke weergaven door inhoud alleen te laden wanneer dat nodig is.

Wanneer gebruikers de onderkant van scrollbare inhoud bereiken, activeert `InfiniteScroll` een gebeurtenis om meer gegevens te laden. Terwijl nieuwe inhoud wordt geladen, wordt er een [`Spinner`](../components/spinner) weergegeven met een aanpasbare tekst om aan te geven dat er meer items aankomen.

<!-- INTRO_END -->

## Toestandbeheer {#state-management}

De `InfiniteScroll` component geeft gebeurtenissen uit en behoudt interne toestand om te helpen beheren hoe en wanneer inhoud wordt geladen.

<AppLayoutViewer
path='/webforj/infinitescroll?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/infinitescroll/InfiniteScrollView.java'
cssURL='/css/infinitescroll/infinitescroll.css'
height = '400px'
mobile='true'
/>

Om meer gegevens op te halen wanneer de gebruiker scrollt, gebruik de `onScroll()` of `addScrollListener()` methode om een luisteraar te registreren. Binnen de luisteraar laad je meestal aanvullende inhoud en roep je `update()` aan om de toestand van `InfiniteScroll` te vernieuwen.

```java
infiniteScroll.onScroll(event -> {
    infiniteScroll.add(new Paragraph("Item geladen"));
    infiniteScroll.update();
});
```

Zodra alle inhoud is geladen, markeer je de scroll als voltooid om verdere triggers te voorkomen. Vergeet niet `update()` aan te roepen na het instellen van voltooid om de nieuwe toestand toe te passen:

```java
infiniteScroll.setCompleted(true);
infiniteScroll.update();
```
Dit schakelt verder eindeloos scrollen uit.

:::tip Reset het Laad-vlag
Je kunt deze vlag resetten met `setCompleted(false)` als je later de gebruiker toestaat om meer inhoud te laden (bijv. na een vernieuwing).
:::


## Aanpassing van de laadindicator {#loading-indicator-customization}

Standaard toont `InfiniteScroll` een ingebouwde laadindicator - een kleine geanimeerde [`Spinner`](../components/spinner) samen met een "Gegevens aan het laden" tekst. Je kunt de weergegeven tekst wijzigen door een aangepaste boodschap door te geven aan de constructor van `InfiniteScroll` of door `setText()` te gebruiken.

```java
InfiniteScroll infiniteScroll = new InfiniteScroll("Meer records ophalen...");
infiniteScroll.setText("Meer items laden...");
```

Evenzo kun je de tijdens het laden weergegeven [`Icon`](../components/icon) aanpassen door `setIcon()` te gebruiken.

<AppLayoutViewer
path='/webforj/infinitescrollloading?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/infinitescroll/InfiniteScrollLoadingView.java'
cssURL='/css/infinitescroll/infinitescroll.css'
height = '400px'
mobile='true'
/>

### Volledige aanpassing {#full-customization}

Als je zowel de [`Spinner`](../components/spinner) als de tekst volledig wilt vervangen door je eigen markup,
kun je inhoud direct in de speciale inhoudslot toevoegen met `addToContent()`.

Wanneer je de inhoudslot populateert, vervangt dit de standaard laadlay-out volledig.

<AppLayoutViewer
path='/webforj/infinitescrollcustomloading?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/infinitescroll/InfiniteScrollCustomLoadingView.java'
cssURL='/css/infinitescroll/infinitescrollcustom.css'
height = '400px'
mobile='true'
/>

## Stylen {#styling}

<TableBuilder name="InfiniteScroll" />
