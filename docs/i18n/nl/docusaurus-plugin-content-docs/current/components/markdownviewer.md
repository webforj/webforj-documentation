---
title: MarkdownViewer
sidebar_position: 74
_i18n_hash: e50beb488f343e35da80b6d4f9ceddf5
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-markdown-viewer" />
<DocChip chip='since' label='25.11' />
<JavadocLink type="markdown-viewer" location="com/webforj/component/markdown/MarkdownViewer" top='true'/>

De `MarkdownViewer` component rendert markdown tekst als HTML. Het ondersteunt standaard markdown-syntax, inclusief koppen, lijsten, codeblokken, links, afbeeldingen en het weergeven van emoji's. De component biedt ook progressieve rendering, die inhoud karakter voor karakter weergeeft voor een typemachine-effect.

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
De component implementeert `HasText`, zodat `setText()` en `getText()` als aliassen voor de inhoudsmethoden fungeren.
:::
<ComponentDemo
path='/webforj/markdownviewer'
files={['src/main/java/com/webforj/samples/views/markdownviewer/MarkdownViewerView.java']}
height='650px'
/>

## Inhoud toevoegen {#appending-content}

De `append()` methode voegt inhoud geleidelijk toe zonder wat er al is te vervangen:

```java
viewer.append("## Nieuwe Sectie\n\n");
viewer.append("Meer inhoud hier...");
```

Standaard verschijnt toegevoegde inhoud onmiddellijk. Wanneer [progressieve rendering](#progressive-rendering) is ingeschakeld, gaat toegevoegde inhoud naar een buffer en wordt karakter voor karakter weergegeven.

## Auto-scrollen {#auto-scroll}

Schakel auto-scrollen in om het viewport onderaan te houden terwijl de inhoud groeit. Dit werkt met elke methode om inhoud toe te voegen, of het nu `setContent()`, `append()` of progressieve rendering is. Als een gebruiker handmatig omhoog scrollt om eerdere inhoud te bekijken, pauzeert auto-scrollen en hervat het wanneer ze weer naar beneden scrollen.

```java
viewer.setAutoScroll(true);
```

## Progressieve rendering {#progressive-rendering}

Progressieve rendering toont inhoud karakter voor karakter in plaats van alles in één keer, wat een typemachine-effect creëert. AI-chatinterfaces gebruiken dit vaak om reacties geleidelijk te laten verschijnen:

```java
MarkdownViewer viewer = new MarkdownViewer();
viewer.setProgressiveRender(true);
```

Wanneer ingeschakeld, gaat inhoud die via `setContent()` of `append()` is toegevoegd naar een buffer en wordt deze geleidelijk weergegeven. Wanneer uitgeschakeld, verschijnt de inhoud onmiddellijk.

<ComponentDemo
path='/webforj/markdownviewerprogressive'
files={['src/main/java/com/webforj/samples/views/markdownviewer/MarkdownViewerProgressiveView.java']}
height='650px'
/>

### Render snelheid {#render-speed}

De `setRenderSpeed()` methode bepaalt hoeveel karakters per animatieframe worden weergegeven. Hogere waarden betekenen snellere rendering. Bij 60 fps komt de standaard snelheid van 4 overeen met ongeveer 240 karakters per seconde:

| Snelheid | Karakters per Seconde |
|----------|------------------------|
| 4 (standaard) | ~240 |
| 6 | ~360 |
| 10 | ~600 |

```java
viewer.setRenderSpeed(6);
```

:::tip Uw gegevenssnelheid afstemmen
Als uw server inhoud sneller verzendt dan de viewer rendert, groeit de buffer en loopt de weergegeven inhoud achter. Verhoog de `renderSpeed` om gelijke tred te houden, of roep `flush()` aan wanneer alle inhoud is ontvangen om de resterende inhoud onmiddellijk weer te geven.
:::

### Render status {#render-state}

Wanneer progressieve rendering is ingeschakeld, retourneert de `isRendering()` methode `true` terwijl de component actief gebufferde inhoud weergeeft. Chatinterfaces gebruiken dit vaak om een stopknop weer te geven of te verbergen:

```java
if (viewer.isRendering()) {
  stopButton.setVisible(true);
}
```

Deze methode retourneert altijd `false` wanneer progressieve rendering is uitgeschakeld.

### Besturing van rendering {#controlling-rendering}

Twee methoden beheren hoe de progressieve rendering stopt:

- **`stop()`** stopt de rendering en verwierpt alle gebufferde inhoud die nog niet is weergegeven. Roep dit aan wanneer de gebruiker annuleert.
- **`flush()`** stopt de rendering maar toont onmiddellijk alle resterende gebufferde inhoud. Roep dit aan wanneer alle inhoud is ontvangen en je deze wilt tonen zonder te wachten.

```java
// Gebruiker heeft op "Stop genereren" geklikt
viewer.stop();

// Alle inhoud ontvangen, toon nu alles
viewer.flush();
```

Deze methoden hebben geen effect wanneer progressieve rendering is uitgeschakeld.

### Wachten op voltooiing {#waiting-for-completion}

De `whenRenderComplete()` methode retourneert een `PendingResult` die voltooit wanneer progressieve rendering is gestopt met het weergeven van alle gebufferde inhoud:

```java
viewer.whenRenderComplete().thenAccept(v -> {
  inputField.setEnabled(true);
  inputField.focus();
});
```

Als progressieve rendering niet is ingeschakeld of er geen inhoud wordt weergegeven, voltooit de `PendingResult` onmiddellijk.

:::tip UI-coördinatie
Wanneer je progressieve rendering gebruikt, schakel de invoervelden niet opnieuw in uitsluitend op basis van wanneer je klaar bent met het aanroepen van `append()`. De renderer kan nog steeds gebufferde inhoud weergeven. Wacht op `whenRenderComplete()` zodat alle inhoud verschijnt voordat gebruikers weer kunnen interageren.
:::

De volgende demo simuleert een AI-chatinterface met `append()` met ingeschakelde progressieve rendering:

<ComponentDemo
path='/webforj/markdownviewerstreaming'
files={['src/main/java/com/webforj/samples/views/markdownviewer/MarkdownViewerStreamingView.java']}
height='700px'
/>

## Inhoud wissen {#clearing-content}

Verwijder alle inhoud met `clear()`:

```java
viewer.clear();
```

Als progressieve rendering actief is, stopt `clear()` ook met renderen en voltooit het eventuele lopende `whenRenderComplete()` resultaten.

## Syntaxhighlighting {#syntax-highlighting}

De `MarkdownViewer` ondersteunt syntaxhighlighting voor codeblokken wanneer [Prism.js](https://prismjs.com/) beschikbaar is. Voeg Prism.js toe aan je app met de `@JavaScript` en `@StyleSheet` annotaties:

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
