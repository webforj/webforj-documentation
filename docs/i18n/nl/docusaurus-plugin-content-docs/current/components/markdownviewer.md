---
title: MarkdownViewer
sidebar_position: 74
sidebar_class_name: new-content
_i18n_hash: 4583c753ac5c37b5f1c44106347f5732
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-markdown-viewer" />
<DocChip chip='since' label='25.11' />
<JavadocLink type="markdown-viewer" location="com/webforj/component/markdown/MarkdownViewer" top='true'/>

De `MarkdownViewer` component rendert markdown tekst als HTML. Het ondersteunt standaard markdown-syntaxis, inclusief koppen, lijsten, codeblokken, links, afbeeldingen en emoji-weergave. De component biedt ook progressieve weergave, die inhoud karakter voor karakter weergeeft voor een typemachine-effect.

## Inhoud instellen {#setting-content}

Maak een `MarkdownViewer` met of zonder initiële inhoud en werk deze vervolgens bij met `setContent()`:

```java
MarkdownViewer viewer = new MarkdownViewer("# Hello World");

// Vervang de inhoud volledig
viewer.setContent("""
    ## Nieuwe Inhoud

    - Item 1
    - Item 2
    """);

// Verkrijg de huidige inhoud
String content = viewer.getContent();
```
:::tip
De component implementeert `HasText`, zodat `setText()` en `getText()` werken als aliassen voor de inhoudsmethoden.
:::
<ComponentDemo 
path='/webforj/markdownviewer?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/markdownviewer/MarkdownViewerView.java'
height='600px'
/>

## Inhoud toevoegen {#appending-content}

De `append()` methode voegt inhoud incrementeel toe zonder het al aanwezige te vervangen:

```java
viewer.append("## Nieuwe Sectie\n\n");
viewer.append("Meer inhoud hier...");
```

Standaard verschijnt toegevoegde inhoud onmiddellijk. Wanneer [progressieve weergave](#progressive-rendering) is ingeschakeld, gaat toegevoegde inhoud naar een buffer en wordt deze karakter voor karakter weergegeven.

## Automatische scrollen {#auto-scroll}

Schakel automatische scrolling in om het viewport onderaan te houden terwijl de inhoud groeit. Dit werkt met elk methode van het toevoegen van inhoud, of het nu `setContent()`, `append()` of progressieve weergave is. Als een gebruiker handmatig omhoog scrollt om eerdere inhoud te bekijken, pauzeert de automatische scrollfunctie en hervat deze wanneer ze weer naar beneden scrollen.

```java
viewer.setAutoScroll(true);
```

## Progressieve weergave {#progressive-rendering}

Progressieve weergave toont inhoud karakter voor karakter in plaats van in één keer, waardoor een typemachine-effect ontstaat. AI-chatinterfaces gebruiken dit vaak om reacties geleidelijk weer te geven:

```java
MarkdownViewer viewer = new MarkdownViewer();
viewer.setProgressiveRender(true);
```

Wanneer ingeschakeld, gaat de inhoud die via `setContent()` of `append()` wordt toegevoegd naar een buffer en wordt deze incrementeel weergegeven. Wanneer uitgeschakeld, verschijnt de inhoud onmiddellijk.

<ComponentDemo 
path='/webforj/markdownviewerprogressive?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/markdownviewer/MarkdownViewerProgressiveView.java'
height='650px'
/>

### Render snelheid {#render-speed}

De `setRenderSpeed()` methode bepaalt hoeveel karakters per animatiekader worden gerenderd. Hogere waarden betekenen snellere rendering. Bij 60fps vertaalt de standaard snelheid van 4 zich naar ongeveer 240 karakters per seconde:

| Snelheid | Karakters/Secunde |
|----------|---------------------|
| 4 (standaard) | ~240 |
| 6 | ~360 |
| 10 | ~600 |

```java
viewer.setRenderSpeed(6);
```

:::tip Bij het afstemmen op uw gegevenssnelheid
Als uw server inhoud sneller verzendt dan de viewer rendert, groeit de buffer en loopt de weergegeven inhoud achter. Verhoog `renderSpeed` om bij te blijven, of roep `flush()` op wanneer alle inhoud is ontvangen om de resterende inhoud onmiddellijk weer te geven.
:::

### Render status {#render-state}

Wanneer progressieve weergave is ingeschakeld, retourneert de `isRendering()` methode `true` terwijl de component actief weergegeven inhoud in de buffer weergeeft. Chatinterfaces gebruiken dit vaak om een stopknop weer te geven of te verbergen:

```java
if (viewer.isRendering()) {
  stopButton.setVisible(true);
}
```

Deze methode retourneert altijd `false` wanneer progressieve weergave is uitgeschakeld.

### Het renderen beheersen {#controlling-rendering}

Twee methoden beheersen hoe progressieve weergave stopt:

- **`stop()`** stopt de weergave en verwerpt alle gebufferde inhoud die nog niet is weergegeven. Roep dit aan wanneer de gebruiker annuleert.
- **`flush()`** stopt de weergave maar toont onmiddellijk alle resterende gebufferde inhoud. Roep dit aan wanneer alle inhoud is ontvangen en u deze wilt tonen zonder te wachten.

```java
// De gebruiker heeft op "Stop met genereren" geklikt
viewer.stop();

// Alle inhoud ontvangen, toon alles nu
viewer.flush();
```

Deze methoden hebben geen effect wanneer progressieve weergave is uitgeschakeld.

### Wachten op voltooiing {#waiting-for-completion}

De `whenRenderComplete()` methode retourneert een `PendingResult` dat wordt voltooid wanneer progressieve weergave is voltooid en alle gebufferde inhoud is weergegeven:

```java
viewer.whenRenderComplete().thenAccept(v -> {
  inputField.setEnabled(true);
  inputField.focus();
});
```

Als progressieve weergave niet is ingeschakeld of er geen inhoud wordt weergegeven, voltooit de `PendingResult` onmiddellijk.

:::tip UI-coördinatie
Wanneer u progressieve weergave gebruikt, schakelt u invoervelden niet opnieuw in uitsluitend op basis van wanneer u klaar bent met het aanroepen van `append()`. De renderer kan nog steeds gebufferde inhoud weergeven. Wacht op `whenRenderComplete()` zodat alle inhoud verschijnt voordat gebruikers opnieuw kunnen interageren.
:::

De volgende demo simuleert een AI-chatinterface met `append()` met progressieve weergave ingeschakeld:

<ComponentDemo 
path='/webforj/markdownviewerstreaming?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/markdownviewer/MarkdownViewerStreamingView.java'
height='700px'
/>

## Inhoud wissen {#clearing-content}

Verwijder alle inhoud met `clear()`:

```java
viewer.clear();
```

Als progressieve weergave actief is, stopt `clear()` ook met renderen en voltooit het eventuele hangende `whenRenderComplete()` resultaten.

## Syntax highlighting {#syntax-highlighting}

De `MarkdownViewer` ondersteunt syntax highlighting voor codeblokken wanneer [Prism.js](https://prismjs.com/) beschikbaar is. Voeg Prism.js toe aan uw app met de annotaties `@JavaScript` en `@StyleSheet`:

```java
@StyleSheet("https://cdn.jsdelivr.net/npm/prismjs@1/themes/prism-tomorrow.min.css")
@JavaScript(
  value = "https://cdn.jsdelivr.net/combine/npm/prismjs@1/prism.min.js,npm/prismjs@1/plugins/autoloader/prism-autoloader.min.js",
  top = true
)
public class Application extends App {
  // ...
}
```

De autoloader-plugin laadt taaldiefinities zoals nodig, zodat codeblokken met taalsuggesties zoals ` ```java ` of ` ```python ` automatisch worden gemarkeerd.

<TableBuilder name="MarkdownViewer" />
