---
title: MarkdownViewer
sidebar_position: 74
description: >-
  Render markdown as HTML with the MarkdownViewer component, supporting append,
  auto-scroll, and progressive typewriter rendering.
_i18n_hash: fbd31d2317bf5de95c282a1319f35cf6
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-markdown-viewer" />
<DocChip chip='since' label='25.11' />
<JavadocLink type="markdown-viewer" location="com/webforj/component/markdown/MarkdownViewer" top='true'/>

De `MarkdownViewer` component rendert markdown tekst als HTML. Het ondersteunt standaard markdown-syntaxis, inclusief koppen, lijsten, codeblokken, links, afbeeldingen en emoji-renderring. De component biedt ook progressieve renderring, wat inhoud karakter voor karakter weergeeft voor een typemachine-effect.

## Inhoud instellen {#setting-content}

Maak een `MarkdownViewer` met of zonder initiële inhoud en werk deze vervolgens bij met `setContent()`:

```java
MarkdownViewer viewer = new MarkdownViewer("# Hallo Wereld");

// Vervang inhoud volledig
viewer.setContent("""
    ## Nieuwe Inhoud

    - Item 1
    - Item 2
    """);

// Verkrijg actuele inhoud
String content = viewer.getContent();
```
:::tip
De component implementeert `HasText`, dus `setText()` en `getText()` werken als aliassen voor de inhoudsmethoden.
:::
<ComponentDemo
path='/webforj/markdownviewer'
files={['src/main/java/com/webforj/samples/views/markdownviewer/MarkdownViewerView.java']}
height='650px'
/>

## Inhoud toevoegen {#appending-content}

De `append()` methode voegt inhoud incrementeel toe zonder wat er al is te vervangen:

```java
viewer.append("## Nieuwe Sectie\n\n");
viewer.append("Meer inhoud hier...");
```

Standaard verschijnt toegevoegde inhoud onmiddellijk. Wanneer [progressieve renderring](#progressive-rendering) is ingeschakeld, gaat toegevoegde inhoud in een buffer en wordt deze karakter voor karakter weergegeven.

## Auto-scrollen {#auto-scroll}

Schakel auto-scrollen in om de viewport onderaan te houden naarmate de inhoud groeit. Dit werkt met elke methode van het toevoegen van inhoud, of het nu `setContent()`, `append()` of progressieve renderring is. Als een gebruiker handmatig omhoog scrolt om eerdere inhoud te bekijken, pauzeert auto-scrollen en gaat weer verder wanneer ze weer naar beneden scrollen.

```java
viewer.setAutoScroll(true);
```

## Progressieve renderring {#progressive-rendering}

Progressieve renderring toont inhoud karakter voor karakter in plaats van alles tegelijk, wat een typemachine-effect creëert. AI-chatinterfaces gebruiken dit vaak om antwoorden geleidelijk weer te geven:

```java
MarkdownViewer viewer = new MarkdownViewer();
viewer.setProgressiveRender(true);
```

Wanneer ingeschakeld, gaat inhoud die via `setContent()` of `append()` wordt toegevoegd in een buffer en wordt deze incrementeel weergegeven. Wanneer uitgeschakeld, verschijnt inhoud onmiddellijk.

<ComponentDemo
path='/webforj/markdownviewerprogressive'
files={['src/main/java/com/webforj/samples/views/markdownviewer/MarkdownViewerProgressiveView.java']}
height='650px'
/>

### Render snelheid {#render-speed}

De `setRenderSpeed()` methode bepaalt hoeveel karakters per animatieframe worden weergegeven. Hogere waarden betekenen snellere weergave. Bij 60fps komt de standaard snelheid van 4 overeen met ongeveer 240 karakters per seconde:

| Snelheid | Karakters/Seconde |
|----------|--------------------|
| 4 (standaard) | ~240 |
| 6 | ~360 |
| 10 | ~600 |

```java
viewer.setRenderSpeed(6);
```

:::tip Afstemmen op je datasnelheid
Als je server inhoud sneller verstuurt dan de viewer rendert, groeit de buffer en ligt weergegeven inhoud achter. Verhoog `renderSpeed` om gelijke tred te houden, of roep `flush()` aan wanneer alle inhoud is ontvangen om resterende inhoud onmiddellijk weer te geven.
:::

### Render status {#render-state}

Wanneer progressieve renderring is ingeschakeld, retourneert de `isRendering()` methode `true` terwijl de component actief gebufferde inhoud weergeeft. Chatinterfaces gebruiken dit vaak om een stopknop weer te geven of te verbergen:

```java
if (viewer.isRendering()) {
  stopButton.setVisible(true);
}
```

Deze methode retourneert altijd `false` wanneer progressieve renderring is uitgeschakeld.

### Rendering regelen {#controlling-rendering}

Twee methoden regelen hoe progressieve renderring stopt:

- **`stop()`** stopt de weergave en verwerpt alle gebufferde inhoud die nog niet is weergegeven. Roep dit aan wanneer de gebruiker annuleert.
- **`flush()`** stopt de weergave maar toont onmiddellijk alle resterende gebufferde inhoud. Roep dit aan wanneer alle inhoud is ontvangen en je deze zonder wachten wilt weergeven.

```java
// Gebruiker klikte op "Stop met genereren"
viewer.stop();

// Alle inhoud ontvangen, toon alles nu
viewer.flush();
```

Deze methoden hebben geen effect wanneer progressieve renderring is uitgeschakeld.

### Wachten op voltooiing {#waiting-for-completion}

De `whenRenderComplete()` methode retourneert een `PendingResult` dat voltooit wanneer progressieve renderring alle gebufferde inhoud heeft weergegeven:

```java
viewer.whenRenderComplete().thenAccept(v -> {
  inputField.setEnabled(true);
  inputField.focus();
});
```

Als progressieve renderring niet is ingeschakeld of er geen inhoud wordt weergegeven, voltooit het `PendingResult` onmiddellijk.

:::tip UI coördinatie
Bij het gebruik van progressieve renderring, heractiveer invoervelden niet alleen op basis van wanneer je klaar bent met het aanroepen van `append()`. De renderer kan nog steeds gebufferde inhoud weergeven. Wacht op `whenRenderComplete()` zodat alle inhoud verschijnt voordat gebruikers weer kunnen interageren.
:::

De volgende demo simuleert een AI-chatinterface met gebruik van `append()` met progressieve renderring ingeschakeld:

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

Als progressieve renderring actief is, stopt `clear()` ook de weergave en voltooit eventuele lopende `whenRenderComplete()` resultaten.

## Syntax highlighting {#syntax-highlighting}

De `MarkdownViewer` ondersteunt syntax highlighting voor codeblokken wanneer [Prism.js](https://prismjs.com/) beschikbaar is. Breng Prism in je app met de [frontend bundler](/docs/managing-resources/bundler/overview): declareer het pakket in je `App` klasse en schrijf een invoer die Prism, de autoloader-plugin en een thema importeert.

```java title="Application.java"
@BundlePackage(value = "prismjs", version = "^1.29.0")
@BundleEntry("prism/entry.ts")
public class Application extends App {
  // ...
}
```

```ts title="src/main/frontend/prism/entry.ts"
import "prismjs";
import "prismjs/plugins/autoloader/prism-autoloader";
import "prismjs/themes/prism-tomorrow.min.css";
```

De autoloader-plugin laadt taald definities zoals nodig, zodat codeblokken met taalhints zoals ` ```java ` of ` ```python ` automatisch worden gemarkeerd.

<TableBuilder name="MarkdownViewer" />
