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

Die `MarkdownViewer`-Komponente rendert Markdown-Text als HTML. Sie unterstützt den Standard-Markdown-Syntax, einschließlich Überschriften, Listen, Codeblöcken, Links, Bildern und Emoji-Rendering. Die Komponente bietet auch ein progressives Rendering, das den Inhalt Zeichen für Zeichen für einen Schreibmaschinen-Effekt anzeigt.

## Konfiguration des Inhalts {#setting-content}

Erstellen Sie einen `MarkdownViewer` mit oder ohne anfänglichen Inhalt und aktualisieren Sie ihn dann mit `setContent()`:

```java
MarkdownViewer viewer = new MarkdownViewer("# Hallo Welt");

// Ersetzen Sie den gesamten Inhalt
viewer.setContent("""
    ## Neuer Inhalt

    - Element 1
    - Element 2
    """);

// Aktuellen Inhalt abrufen
String content = viewer.getContent();
```
:::tip
Die Komponente implementiert `HasText`, sodass `setText()` und `getText()` als Aliase für die Inhaltsmethoden funktionieren.
:::

<ComponentDemo
path='/webforj/markdownviewer'
files={['src/main/java/com/webforj/samples/views/markdownviewer/MarkdownViewerView.java']}
height='650px'
/>

## Anfügen von Inhalten {#appending-content}

Die Methode `append()` fügt Inhalte schrittweise hinzu, ohne das bereits Vorhandene zu ersetzen:

```java
viewer.append("## Neuer Abschnitt\n\n");
viewer.append("Hier sind weitere Inhalte...");
```

Standardmäßig erscheint der angefügte Inhalt sofort. Wenn das [progressive Rendering](#progressive-rendering) aktiviert ist, geht der angefügte Inhalt in einen Puffer und wird Zeichen für Zeichen anstatt sofort angezeigt.

## Automatisches Scrollen {#auto-scroll}

Aktivieren Sie das automatische Scrollen, um den Ansichtsbereich am unteren Rand zu halten, während der Inhalt wächst. Dies funktioniert mit jeder Methode zum Hinzufügen von Inhalten, egal ob `setContent()`, `append()` oder progressives Rendering. Wenn ein Benutzer manuell nach oben scrollt, um frühere Inhalte zu überprüfen, pausiert das automatische Scrollen und wird wieder aktiviert, wenn er zurück nach unten scrollt.

```java
viewer.setAutoScroll(true);
```

## Progressives Rendering {#progressive-rendering}

Das progressive Rendering zeigt Inhalte Zeichen für Zeichen anstatt alles auf einmal und erzeugt einen Schreibmaschinen-Effekt. KI-Chat-Schnittstellen verwenden dies häufig, um Antworten allmählich anzuzeigen:

```java
MarkdownViewer viewer = new MarkdownViewer();
viewer.setProgressiveRender(true);
```

Wenn aktiviert, geht der über `setContent()` oder `append()` hinzugefügte Inhalt in einen Puffer und wird schrittweise angezeigt. Wenn deaktiviert, erscheint der Inhalt sofort.

<ComponentDemo
path='/webforj/markdownviewerprogressive'
files={['src/main/java/com/webforj/samples/views/markdownviewer/MarkdownViewerProgressiveView.java']}
height='650px'
/>

### Rendergeschwindigkeit {#render-speed}

Die Methode `setRenderSpeed()` steuert, wie viele Zeichen pro Animationsrahmen gerendert werden. Höhere Werte bedeuten schnelleres Rendering. Bei 60 fps entspricht die Standardgeschwindigkeit von 4 etwa 240 Zeichen pro Sekunde:

| Geschwindigkeit | Zeichen/Sekunde |
|-----------------|------------------|
| 4 (Standard)    | ~240             |
| 6               | ~360             |
| 10              | ~600             |

```java
viewer.setRenderSpeed(6);
```

:::tip Abstimmung Ihrer Datenrate
Wenn Ihr Server Inhalte schneller sendet, als der Viewer rendert, wächst der Puffer und der angezeigte Inhalt hinkt hinterher. Erhöhen Sie die `renderSpeed`, um Schritt zu halten, oder rufen Sie `flush()` auf, wenn alle Inhalte empfangen wurden, um die verbleibenden Inhalte sofort anzuzeigen.
:::

### Renderstatus {#render-state}

Wenn das progressive Rendering aktiviert ist, gibt die Methode `isRendering()` `true` zurück, während die Komponente aktiv Inhalte im Puffer anzeigt. Chat-Schnittstellen verwenden dies oft, um einen Stop-Button anzuzeigen oder auszublenden:

```java
if (viewer.isRendering()) {
  stopButton.setVisible(true);
}
```

Diese Methode gibt immer `false` zurück, wenn das progressive Rendering deaktiviert ist.

### Steuerung des Renderings {#controlling-rendering}

Zwei Methoden steuern, wie das progressive Rendering stoppt:

- **`stop()`** stoppt das Rendering und verwirft alle gepufferten Inhalte, die noch nicht angezeigt wurden. Rufen Sie dies auf, wenn der Benutzer abbricht.
- **`flush()`** stoppt das Rendering, zeigt jedoch sofort alle verbleibenden gepufferten Inhalte an. Rufen Sie dies auf, wenn alle Inhalte empfangen wurden und Sie alles ohne Warten anzeigen möchten.

```java
// Benutzer hat auf "Generierung stoppen" geklickt
viewer.stop();

// Alle Inhalte wurden empfangen, jetzt alles anzeigen
viewer.flush();
```

Diese Methoden haben keine Auswirkung, wenn das progressive Rendering deaktiviert ist.

### Warten auf den Abschluss {#waiting-for-completion}

Die Methode `whenRenderComplete()` gibt ein `PendingResult` zurück, das abgeschlossen ist, wenn das progressive Rendering alle gepufferten Inhalte vollständig angezeigt hat:

```java
viewer.whenRenderComplete().thenAccept(v -> {
  inputField.setEnabled(true);
  inputField.focus();
});
```

Wenn das progressive Rendering nicht aktiviert ist oder keine Inhalte gerendert werden, wird das `PendingResult` sofort abgeschlossen.

:::tip UI-Koordination
Beim Einsatz von progressivem Rendering sollten Sie Eingabefelder nicht nur basierend darauf wieder aktivieren, wenn Sie `append()` fertig aufgerufen haben. Der Renderer könnte weiterhin gepufferte Inhalte anzeigen. Warten Sie auf `whenRenderComplete()`, damit alle Inhalte erscheinen, bevor die Benutzer wieder interagieren können.
:::

Die folgende Demo simuliert eine KI-Chat-Schnittstelle, die mit `append()` und aktiviertem progressivem Rendering arbeitet:

<ComponentDemo
path='/webforj/markdownviewerstreaming'
files={['src/main/java/com/webforj/samples/views/markdownviewer/MarkdownViewerStreamingView.java']}
height='700px'
/>

## Löschen des Inhalts {#clearing-content}

Entfernen Sie alle Inhalte mit `clear()`:

```java
viewer.clear();
```

Wenn das progressive Rendering aktiv ist, stoppt `clear()` auch das Rendering und schließt alle ausstehenden `whenRenderComplete()`-Ergebnisse.

## Syntaxhervorhebung {#syntax-highlighting}

Der `MarkdownViewer` unterstützt die Syntaxhervorhebung für Codeblöcke, wenn [Prism.js](https://prismjs.com/) verfügbar ist. Bringen Sie Prism in Ihre App mit dem [Frontend-Bundler](/docs/managing-resources/bundler/overview): Deklarieren Sie das Paket in Ihrer `App`-Klasse und erstellen Sie einen Eintrag, der Prism, das Autoloader-Plugin und ein Design importiert.

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

Das Autoloader-Plugin lädt Sprachdefinitionen je nach Bedarf, sodass Codeblöcke mit Sprachhinweisen wie ` ```java ` oder ` ```python ` automatisch hervorgehoben werden.

<TableBuilder name="MarkdownViewer" />
