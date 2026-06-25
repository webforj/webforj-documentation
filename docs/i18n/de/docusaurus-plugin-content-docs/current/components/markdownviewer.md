---
title: MarkdownViewer
sidebar_position: 74
_i18n_hash: e50beb488f343e35da80b6d4f9ceddf5
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-markdown-viewer" />
<DocChip chip='since' label='25.11' />
<JavadocLink type="markdown-viewer" location="com/webforj/component/markdown/MarkdownViewer" top='true'/>

Die `MarkdownViewer`-Komponente rendert Markdown-Text als HTML. Sie unterstützt die standardmäßige Markdown-Syntax, einschließlich Überschriften, Listen, Codeblöcke, Links, Bilder und Emoji-Rendering. Die Komponente bietet auch ein Fortschritts-Rendering, das den Inhalt zeichenweise für einen Schreibmaschinen-Effekt anzeigt.

## Konfiguration des Inhalts {#setting-content}

Erstellen Sie einen `MarkdownViewer` mit oder ohne anfänglichen Inhalt und aktualisieren Sie ihn dann mit `setContent()`:

```java
MarkdownViewer viewer = new MarkdownViewer("# Hallo Welt");

// Ersetzen Sie den Inhalt vollständig
viewer.setContent("""
    ## Neuer Inhalt

    - Element 1
    - Element 2
    """);

// Aktuellen Inhalt abrufen
String content = viewer.getContent();
```
:::tip
Die Komponente implementiert `HasText`, sodass `setText()` und `getText()` als Aliase für die Inhaltsmethoden fungieren.
:::
<ComponentDemo
path='/webforj/markdownviewer'
files={['src/main/java/com/webforj/samples/views/markdownviewer/MarkdownViewerView.java']}
height='650px'
/>

## Hinzufügen von Inhalten {#appending-content}

Die Methode `append()` fügt Inhalte schrittweise hinzu, ohne das bereits Vorhandene zu ersetzen:

```java
viewer.append("## Neuer Abschnitt\n\n");
viewer.append("Hier ist mehr Inhalt...");
```

Standardmäßig erscheint der hinzugefügte Inhalt sofort. Wenn das [Fortschritts-Rendering](#progressive-rendering) aktiviert ist, wird der hinzugefügte Inhalt in einen Puffer eingefügt und zeichenweise angezeigt.

## Automatisches Scrollen {#auto-scroll}

Aktivieren Sie das automatische Scrollen, um den Ansichtsbereich am unteren Ende zu halten, während der Inhalt wächst. Dies funktioniert mit jeder Methode zum Hinzufügen von Inhalten, sei es `setContent()`, `append()` oder Fortschritts-Rendering. Wenn ein Benutzer manuell nach oben scrollt, um frühere Inhalte anzusehen, pausiert das automatische Scrollen und setzt sich fort, wenn er zurück nach unten scrollt.

```java
viewer.setAutoScroll(true);
```

## Fortschritts-Rendering {#progressive-rendering}

Das Fortschritts-Rendering zeigt den Inhalt zeichenweise an, anstatt alles auf einmal darzustellen, was einen Schreibmaschinen-Effekt erzeugt. KI-Chatoberflächen verwenden dies häufig, um Antworten allmählich erscheinen zu lassen:

```java
MarkdownViewer viewer = new MarkdownViewer();
viewer.setProgressiveRender(true);
```

Wenn aktiviert, wird der über `setContent()` oder `append()` hinzugefügte Inhalt in einem Puffer gespeichert und schrittweise angezeigt. Wenn deaktiviert, erscheint der Inhalt sofort.

<ComponentDemo
path='/webforj/markdownviewerprogressive'
files={['src/main/java/com/webforj/samples/views/markdownviewer/MarkdownViewerProgressiveView.java']}
height='650px'
/>

### Rendervelocity {#render-speed}

Die Methode `setRenderSpeed()` steuert, wie viele Zeichen pro Animationsframe gerendert werden. Höhere Werte bedeuten schnellere Rendervelocity. Bei 60 fps entspricht die Standardgeschwindigkeit von 4 ungefähr 240 Zeichen pro Sekunde:

| Geschwindigkeit | Zeichen/Sekunde |
|-----------------|------------------|
| 4 (Standard)    | ~240             |
| 6               | ~360             |
| 10              | ~600             |

```java
viewer.setRenderSpeed(6);
```

:::tip Anpassen Ihrer Datenrate
Wenn Ihr Server Inhalte schneller sendet, als der Viewer rendert, wächst der Puffer und der angezeigte Inhalt bleibt zurück. Erhöhen Sie die `renderSpeed`, um Schritt zu halten, oder rufen Sie `flush()` auf, wenn alle Inhalte empfangen wurden, um verbleibende Inhalte sofort anzuzeigen.
:::

### Renderrstatus {#render-state}

Wenn das Fortschritts-Rendering aktiviert ist, gibt die Methode `isRendering()` `true` zurück, während die Komponente aktiv den gepufferten Inhalt anzeigt. Chatoberflächen verwenden dies häufig, um einen Stop-Button anzuzeigen oder auszublenden:

```java
if (viewer.isRendering()) {
  stopButton.setVisible(true);
}
```

Diese Methode gibt immer `false` zurück, wenn das Fortschritts-Rendering deaktiviert ist.

### Steuerung der Darstellung {#controlling-rendering}

Zwei Methoden steuern, wie das Fortschritts-Rendering stoppt:

- **`stop()`** stoppt das Rendern und verwirft alle gepufferten Inhalte, die noch nicht angezeigt wurden. Rufen Sie dies auf, wenn der Benutzer abbricht.
- **`flush()`** stoppt das Rendern, zeigt jedoch sofort alle verbleibenden gepufferten Inhalte an. Rufen Sie dies auf, wenn alle Inhalte empfangen wurden und Sie sie ohne Warten anzeigen möchten.

```java
// Benutzer hat auf "Stop Generieren" geklickt
viewer.stop();

// Alle Inhalte empfangen, jetzt alles anzeigen
viewer.flush();
```

Diese Methoden haben keine Auswirkungen, wenn das Fortschritts-Rendering deaktiviert ist.

### Warten auf Vollständigkeit {#waiting-for-completion}

Die Methode `whenRenderComplete()` gibt ein `PendingResult` zurück, das abgeschlossen ist, wenn das Fortschritts-Rendering alle gepufferten Inhalte angezeigt hat:

```java
viewer.whenRenderComplete().thenAccept(v -> {
  inputField.setEnabled(true);
  inputField.focus();
});
```

Wenn das Fortschritts-Rendering nicht aktiviert ist oder kein Inhalt gerendert wird, wird das `PendingResult` sofort abgeschlossen.

:::tip UI-Koordination
Bei der Verwendung des Fortschritts-Renderings aktivieren Sie Eingabefelder nicht nur basierend darauf, wann Sie `append()` abgeschlossen haben. Der Renderer zeigt möglicherweise immer noch gepufferten Inhalt an. Warten Sie auf `whenRenderComplete()`, damit der gesamte Inhalt erscheint, bevor die Benutzer wieder interagieren können.
:::

Die folgende Demo simuliert eine KI-Chat-Oberfläche, die `append()` mit aktiviertem Fortschritts-Rendering verwendet:

<ComponentDemo
path='/webforj/markdownviewerstreaming'
files={['src/main/java/com/webforj/samples/views/markdownviewer/MarkdownViewerStreamingView.java']}
height='700px'
/>

## Inhalt löschen {#clearing-content}

Entfernen Sie gesamten Inhalt mit `clear()`:

```java
viewer.clear();
```

Wenn das Fortschritts-Rendering aktiv ist, stoppt `clear()` auch das Rendern und schließt alle ausstehenden `whenRenderComplete()`-Ergebnisse ab.

## Syntax-Highlighting {#syntax-highlighting}

Der `MarkdownViewer` unterstützt Syntax-Highlighting für Codeblöcke, wenn [Prism.js](https://prismjs.com/) verfügbar ist. Fügen Sie Prism.js zu Ihrer Anwendung mit den `@JavaScript`- und `@StyleSheet`-Annotationen hinzu:

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

Das Autoloader-Plugin lädt bei Bedarf Sprachdefinitionen, sodass Codeblöcke mit Sprachhinweisen wie ` ```java ` oder ` ```python ` automatisch hervorgehoben werden.

<TableBuilder name="MarkdownViewer" />
