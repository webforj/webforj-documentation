---
title: InfiniteScroll
sidebar_position: 60
_i18n_hash: 3384cb35d5087561cc9be2c11b95c7e1
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-infinite-scroll" />
<DocChip chip='since' label='25.00' />
<JavadocLink type="infinite-scroll" location="com/webforj/component/infinitescroll/InfiniteScroll" top='true'/>

De `InfiniteScroll` component in webforJ laadt automatisch meer inhoud wanneer gebruikers naar beneden scrollen, waardoor paginering niet nodig is. Dit creëert een soepele ervaring voor lijsten, feeds en datarijke weergaven door inhoud alleen te laden wanneer dat nodig is.

Wanneer gebruikers de onderkant van scrollbare inhoud bereiken, triggert `InfiniteScroll` een gebeurtenis voor het laden van meer gegevens. Terwijl nieuwe inhoud wordt geladen, toont het een [`Spinner`](../components/spinner) met aanpasbare tekst om aan te geven dat er meer artikelen onderweg zijn.

<AppLayoutViewer
path='/webforj/infinitescroll?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/infinitescroll/InfiniteScrollView.java'
cssURL='/css/infinitescroll/infinitescroll.css'
height = '400px'
mobile='true'
/>

## Statusbeheer {#state-management}

De `InfiniteScroll` component zendt gebeurtenissen en beheert interne status om te helpen regelen hoe en wanneer inhoud wordt geladen.

Om meer gegevens op te halen wanneer de gebruiker scrolt, gebruik de `onScroll()` of `addScrollListener()` methode om een listener te registreren. Binnen de listener laad je typisch aanvullende inhoud en roep je `update()` aan om de `InfiniteScroll` status te vernieuwen.

```java
infiniteScroll.onScroll(event -> {
    infiniteScroll.add(new Paragraph("Gelaad item"));
    infiniteScroll.update();
});
```

Zodra alle inhoud is geladen, markeer je de scroll als voltooid om verdere triggers te voorkomen. Na het instellen van voltooid, vergeet niet om `update()` aan te roepen om de nieuwe status toe te passen:

```java
infiniteScroll.setCompleted(true);
infiniteScroll.update();
```
Dit schakelt verder oneindig scrollen uit.

:::tip Reset de Laadstatus
Je kunt deze status resetten met `setCompleted(false)` als je later de gebruiker toestaat om meer inhoud te laden (bijvoorbeeld na een verversing).
:::


## Aanpassing van de laadindicator {#loading-indicator-customization}

Standaard toont `InfiniteScroll` een ingebouwde laadindicator - een kleine animatie [`Spinner`](../components/spinner) met de tekst "Gegevens aan het laden". Je kunt de weergegeven tekst wijzigen door een aangepaste boodschap door te geven aan de `InfiniteScroll` constructeur of door `setText()` te gebruiken.

```java
InfiniteScroll infiniteScroll = new InfiniteScroll("Meer records ophalen...");
infiniteScroll.setText("Meer artikelen laden...");
```

Evenzo kun je het weergegeven [`Pictogram`](../components/icon) tijdens het laden aanpassen met `setIcon()`.

<AppLayoutViewer
path='/webforj/infinitescrollloading?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/infinitescroll/InfiniteScrollLoadingView.java'
cssURL='/css/infinitescroll/infinitescroll.css'
height = '400px'
mobile='true'
/>

### Volledige aanpassing {#full-customization}

Als je zowel de [`Spinner`](../components/spinner) als de tekst volledig wilt vervangen door jouw eigen markup,
kun je inhoud rechtstreeks in de speciale inhoudsslot toevoegen met `addToContent()`.

Wanneer je het inhoudsslot vult, vervangt het volledig de standaard laadlay-out.

<AppLayoutViewer
path='/webforj/infinitescrollcustomloading?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/infinitescroll/InfiniteScrollCustomLoadingView.java'
cssURL='/css/infinitescroll/infinitescrollcustom.css'
height = '400px'
mobile='true'
/>

## Stijling {#styling}

<TableBuilder name="InfiniteScroll" />
