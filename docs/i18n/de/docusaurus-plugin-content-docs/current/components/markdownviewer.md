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

Die `MarkdownViewer`-Komponente rendert Markdown-Text als HTML. Sie unterstützt die Standard-Markdown-Syntax, einschließlich Überschriften, Listen, Codeblöcke, Links, Bilder und Emoji-Darstellung. Die Komponente bietet auch eine schrittweise Rendering-Funktion, die den Inhalt Zeichen für Zeichen für einen Schreibmaschineneffekt anzeigt.

## Inhalt festlegen {#setting-content}

Erstellen Sie einen `MarkdownViewer` mit oder ohne Anfangsinhalte und aktualisieren Sie ihn dann mit `setContent()`:

```java
MarkdownViewer viewer = new MarkdownViewer("# Hallo Welt");

// Inhalt komplett ersetzen
viewer.setContent("""
    ## Neuer Inhalt

    - Punkt 1
    - Punkt 2
    """);

// Aktuellen Inhalt abrufen
String content = viewer.getContent();
```
:::tip
Die Komponente implementiert `HasText`, sodass `setText()` und `getText()` als Alias für die Inhaltsmethoden arbeiten.
:::
<ComponentDemo 
path='/webforj/markdownviewer?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/markdownviewer/MarkdownViewerView.java'
height='600px'
/>

## Inhalt anhängen {#appending-content}

Die Methode `append()` fügt Inhalte schrittweise hinzu, ohne das bereits Vorhandene zu ersetzen:

```java
viewer.append("## Neuer Abschnitt\n\n");
viewer.append("Mehr Inhalt hier...");
```

Standardmäßig erscheint der angefügte Inhalt sofort. Wenn das [schrittweise Rendering](#progressive-rendering) aktiviert ist, geht der angefügte Inhalt in einen Puffer und wird Zeichen für Zeichen angezeigt.

## Automatisches Scrollen {#auto-scroll}

Aktivieren Sie das automatische Scrollen, um den Ansichtsbereich am unteren Ende zu halten, während der Inhalt wächst. Dies funktioniert mit jeder Methode zum Hinzufügen von Inhalten, sei es `setContent()`, `append()` oder schrittweises Rendering. Wenn ein Benutzer manuell nach oben scrollt, um frühere Inhalte zu überprüfen, pausiert das automatische Scrollen und wird wieder aktiviert, wenn er zurück nach unten scrollt.

```java
viewer.setAutoScroll(true);
```

## Schrittweises Rendering {#progressive-rendering}

Das schrittweise Rendering zeigt Inhalte Zeichen für Zeichen an, anstatt alles auf einmal darzustellen, wodurch ein Schreibmaschineneffekt erzeugt wird. KI-Chat-Schnittstellen nutzen dies häufig, um Antworten allmählich anzuzeigen:

```java
MarkdownViewer viewer = new MarkdownViewer();
viewer.setProgressiveRender(true);
```

Wenn aktiviert, gehen die über `setContent()` oder `append()` hinzugefügten Inhalte in einen Puffer und werden schrittweise angezeigt. Wenn deaktiviert, erscheint der Inhalt sofort.

<ComponentDemo 
path='/webforj/markdownviewerprogressive?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/markdownviewer/MarkdownViewerProgressiveView.java'
height='650px'
/>

### Rendergeschwindigkeit {#render-speed}

Die Methode `setRenderSpeed()` steuert, wie viele Zeichen pro Animationsrahmen gerendert werden. Höhere Werte bedeuten schnellere Rendierung. Bei 60 fps entsprechen die Standardgeschwindigkeit 4 etwa 240 Zeichen pro Sekunde:

| Geschwindigkeit | Zeichen/Sekunde |
|------------------|-----------------|
| 4 (Standard)     | ~240            |
| 6                | ~360            |
| 10               | ~600            |

```java
viewer.setRenderSpeed(6);
```

:::tip Datenrate anpassen
Wenn Ihr Server Inhalte schneller sendet, als der Viewer rendert, wächst der Puffer und die angezeigten Inhalte hinken hinterher. Erhöhen Sie `renderSpeed`, um Schritt zu halten, oder rufen Sie `flush()` auf, wenn alle Inhalte empfangen wurden, um die verbleibenden Inhalte sofort anzuzeigen.
:::

### Renderstatus {#render-state}

Wenn das schrittweise Rendering aktiviert ist, gibt die Methode `isRendering()` `true` zurück, während die Komponente aktiv gepufferte Inhalte anzeigt. Chat-Schnittstellen verwenden dies häufig, um eine Stopp-Schaltfläche anzuzeigen oder auszublenden:

```java
if (viewer.isRendering()) {
  stopButton.setVisible(true);
}
```

Diese Methode gibt immer `false` zurück, wenn das schrittweise Rendering deaktiviert ist.

### Rendering steuern {#controlling-rendering}

Zwei Methoden steuern, wie das schrittweise Rendering stoppt:

- **`stop()`** stoppt die Rendierung und verwirft alle gepufferten Inhalte, die noch nicht angezeigt wurden. Rufen Sie dies auf, wenn der Benutzer abbricht.
- **`flush()`** stoppt die Rendierung, zeigt jedoch sofort alle verbleibenden gepufferten Inhalte an. Rufen Sie dies auf, wenn alle Inhalte empfangen wurden und Sie sie ohne Warten anzeigen möchten.

```java
// Benutzer hat "Stop generating" geklickt
viewer.stop();

// Alle Inhalte empfangen, jetzt alles anzeigen
viewer.flush();
```

Diese Methoden haben keine Auswirkungen, wenn das schrittweise Rendering deaktiviert ist.

### Warten auf Abschluss {#waiting-for-completion}

Die Methode `whenRenderComplete()` gibt ein `PendingResult` zurück, das abgeschlossen wird, wenn das schrittweise Rendering alle gepufferten Inhalte fertig angezeigt hat:

```java
viewer.whenRenderComplete().thenAccept(v -> {
  inputField.setEnabled(true);
  inputField.focus();
});
```

Wenn das schrittweise Rendering nicht aktiviert ist oder kein Inhalt gerendert wird, wird das `PendingResult` sofort abgeschlossen.

:::tip UI-Koordination
Wenn Sie schrittweises Rendering verwenden, aktivieren Sie Eingabefelder nicht nur basierend darauf, wann Sie `append()` abgeschlossen haben. Der Renderer könnte weiterhin gepufferte Inhalte anzeigen. Warten Sie auf `whenRenderComplete()`, damit alle Inhalte erscheinen, bevor die Benutzer wieder interagieren können.
:::

Die folgende Demo simuliert eine KI-Chat-Schnittstelle unter Verwendung von `append()` mit aktiviertem schrittweisen Rendering:

<ComponentDemo 
path='/webforj/markdownviewerstreaming?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/markdownviewer/MarkdownViewerStreamingView.java'
height='700px'
/>

## Inhalt löschen {#clearing-content}

Entfernen Sie alle Inhalte mit `clear()`:

```java
viewer.clear();
```

Wenn das schrittweise Rendering aktiv ist, stoppt `clear()` auch die Rendierung und vervollständigt alle ausstehenden `whenRenderComplete()`-Ergebnisse.

## Syntaxhervorhebung {#syntax-highlighting}

Der `MarkdownViewer` unterstützt Syntaxhervorhebung für Codeblöcke, wenn [Prism.js](https://prismjs.com/) verfügbar ist. Fügen Sie Prism.js zu Ihrer Anwendung mit den Annotationen `@JavaScript` und `@StyleSheet` hinzu:

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

Das Autolade-Plugin lädt Sprachdefinitionen nach Bedarf, sodass Codeblöcke mit Sprachhinweisen wie ` ```java ` oder ` ```python ` automatisch hervorgehoben werden.

<TableBuilder name="MarkdownViewer" />
