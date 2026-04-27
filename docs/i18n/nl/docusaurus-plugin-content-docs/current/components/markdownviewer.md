---
title: MarkdownViewer
sidebar_position: 74
_i18n_hash: dcbc11ba7581a82ae6857abfe11a62c1
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-markdown-viewer" />
<DocChip chip='since' label='25.11' />
<JavadocLink type="markdown-viewer" location="com/webforj/component/markdown/MarkdownViewer" top='true'/>

De `MarkdownViewer` component rendert markdown tekst als HTML. Het ondersteunt standaard markdown-syntaxis, waaronder koppen, lijsten, codeblokken, links, afbeeldingen en emoji-weergave. De component biedt ook progressieve rendering, die inhoud karakter voor karakter weergeeft voor een typemachine-effect.

## Inhoud instellen {#setting-content}

Maak een `MarkdownViewer` met of zonder initiële inhoud en werk het daarna bij met `setContent()`:

```java
MarkdownViewer viewer = new MarkdownViewer("# Hallo Wereld");

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
De component implementeert `HasText`, dus `setText()` en `getText()` werken als aliassen voor de inhoudsmethoden.
:::
<ComponentDemo 
path='/webforj/markdownviewer?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/markdownviewer/MarkdownViewerView.java'
height='650px'
/>

## Inhoud toevoegen {#appending-content}

De `append()` methode voegt inhoud incrementeel toe zonder wat daar al is te vervangen:

```java
viewer.append("## Nieuwe Sectie\n\n");
viewer.append("Meer inhoud hier...");
```

Standaard verschijnt toegevoegde inhoud onmiddellijk. Wanneer [progressieve rendering](#progressive-rendering) is ingeschakeld, wordt toegevoegde inhoud in een buffer geplaatst en wordt deze karakter voor karakter weergegeven.

## Autoscrollen {#auto-scroll}

Schakel autoscrollen in om het viewport onderaan te houden terwijl de inhoud groeit. Dit werkt met elke methode voor het toevoegen van inhoud, of het nu `setContent()`, `append()` of progressieve rendering is. Als een gebruiker handmatig omhoog scrollt om eerdere inhoud te bekijken, pauzeert autoscrollen en hervat het wanneer ze terugscrollen naar de onderkant.

```java
viewer.setAutoScroll(true);
```

## Progressieve rendering {#progressive-rendering}

Progressieve rendering toont inhoud karakter voor karakter in plaats van alles in één keer, wat een typemachine-effect creëert. AI-chatinterfaces gebruiken dit vaak om reacties geleidelijk te laten verschijnen:

```java
MarkdownViewer viewer = new MarkdownViewer();
viewer.setProgressiveRender(true);
```

Wanneer ingeschakeld, wordt inhoud die via `setContent()` of `append()` is toegevoegd in een buffer geplaatst en incrementieel weergegeven. Wanneer uitgeschakeld, verschijnt de inhoud onmiddellijk.

<ComponentDemo 
path='/webforj/markdownviewerprogressive?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/markdownviewer/MarkdownViewerProgressiveView.java'
height='650px'
/>

### Render snelheid {#render-speed}

De `setRenderSpeed()` methode controleert hoeveel karakters per animatiefase worden gerenderd. Hogere waarden betekenen snellere rendering. Bij 60 fps vertaalt de standaard snelheid van 4 zich naar ongeveer 240 karakters per seconde:

| Snelheid | Karakters/Sec |
|----------|----------------|
| 4 (standaard) | ~240 |
| 6 | ~360 |
| 10 | ~600 |

```java
viewer.setRenderSpeed(6);
```

:::tip Matching your data rate
Als uw server inhoud sneller verzendt dan de kijker rendert, groeit de buffer en loopt de weergegeven inhoud achter. Verhoog `renderSpeed` om gelijke tred te houden, of roep `flush()` aan wanneer alle inhoud is ontvangen om de resterende inhoud onmiddellijk weer te geven.
:::

### Render toestand {#render-state}

Wanneer progressieve rendering is ingeschakeld, retourneert de `isRendering()` methode `true` terwijl de component actief bufferinhoud weergeeft. Chatinterfaces gebruiken dit vaak om een stopknop te tonen of te verbergen:

```java
if (viewer.isRendering()) {
  stopButton.setVisible(true);
}
```

Deze methode retourneert altijd `false` wanneer progressieve rendering is uitgeschakeld.

### Rendering controleren {#controlling-rendering}

Twee methoden controleren hoe progressieve rendering stopt:

- **`stop()`** onderbreekt rendering en verworpen alle bufferinhoud die nog niet is weergegeven. Roep dit aan wanneer de gebruiker annuleert.
- **`flush()`** onderbreekt rendering, maar toont onmiddellijk alle resterende bufferinhoud. Roep dit aan wanneer alle inhoud is ontvangen en u het zonder wachten wilt tonen.

```java
// Gebruiker klikte op "Stop met genereren"
viewer.stop();

// Alle inhoud ontvangen, laat nu alles zien
viewer.flush();
```

Deze methoden hebben geen effect wanneer progressieve rendering is uitgeschakeld.

### Wachten op voltooiing {#waiting-for-completion}

De `whenRenderComplete()` methode retourneert een `PendingResult` dat voltooid is wanneer progressieve rendering alle bufferinhoud heeft weergegeven:

```java
viewer.whenRenderComplete().thenAccept(v -> {
  inputField.setEnabled(true);
  inputField.focus();
});
```

Als progressieve rendering niet is ingeschakeld of er geen inhoud wordt gerenderd, voltooit de `PendingResult` onmiddellijk.

:::tip UI-coördinatie
Bij gebruik van progressieve rendering, stel invoervelden niet opnieuw in op basis van wanneer u `append()` hebt beëindigd. De renderer kan nog steeds bufferinhoud weergeven. Wacht op `whenRenderComplete()` zodat alle inhoud verschijnt voordat gebruikers opnieuw kunnen interageren.
:::

De volgende demo simuleert een AI-chatinterface met gebruik van `append()` met ingeschakelde progressieve rendering:

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

Als progressieve rendering actief is, stopt `clear()` ook de rendering en voltooit alle hangende `whenRenderComplete()` resultaten.

## Syntax highlighting {#syntax-highlighting}

De `MarkdownViewer` ondersteunt syntax highlighting voor codeblokken wanneer [Prism.js](https://prismjs.com/) beschikbaar is. Voeg Prism.js toe aan uw app met de `@JavaScript` en `@StyleSheet` annotaties:

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

De autoloader-plugin laadt taaldefinities indien nodig, zodat codeblokken met taal hints zoals ` ```java ` of ` ```python ` automatisch worden gemarkeerd.

<TableBuilder name="MarkdownViewer" />
