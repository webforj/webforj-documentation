---
title: MarkdownViewer
sidebar_position: 74
_i18n_hash: dcbc11ba7581a82ae6857abfe11a62c1
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-markdown-viewer" />
<DocChip chip='since' label='25.11' />
<JavadocLink type="markdown-viewer" location="com/webforj/component/markdown/MarkdownViewer" top='true'/>

Die `MarkdownViewer`-Komponente rendert Markdown-Text als HTML. Sie unterstützt die standardmäßige Markdown-Syntax, einschließlich Überschriften, Listen, Codeblöcken, Links, Bildern und Emojis. Die Komponente bietet auch eine progressive Darstellung, die den Inhalt zeichenweise für einen Schreibmaschineneffekt anzeigt.

## Konfiguration des Inhalts {#setting-content}

Erstellen Sie einen `MarkdownViewer` mit oder ohne Anfangsinhalt und aktualisieren Sie ihn dann mit `setContent()`:

```java
MarkdownViewer viewer = new MarkdownViewer("# Hallo Welt");

// Inhalt vollständig ersetzen
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
path='/webforj/markdownviewer?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/markdownviewer/MarkdownViewerView.java'
height='650px'
/>

## Anhängen von Inhalten {#appending-content}

Die `append()`-Methode fügt Inhalte schrittweise hinzu, ohne das bereits Vorhandene zu ersetzen:

```java
viewer.append("## Neuer Abschnitt\n\n");
viewer.append("Weitere Inhalte hier...");
```

Standardmäßig erscheint hinzugefügter Inhalt sofort. Wenn [progressive Darstellung](#progressive-rendering) aktiviert ist, wird der hinzugefügte Inhalt in einen Puffer überführt und zeichenweise angezeigt.

## Automatisches Scrollen {#auto-scroll}

Aktivieren Sie das automatische Scrollen, um die Ansicht am unteren Ende zu halten, während der Inhalt wächst. Dies funktioniert mit jeder Methode zum Hinzufügen von Inhalten, egal ob `setContent()`, `append()` oder progressive Darstellung. Wenn ein Benutzer manuell nach oben scrollt, um frühere Inhalte zu überprüfen, pausiert das automatische Scrollen und wird wieder aktiviert, wenn er zurück nach unten scrollt.

```java
viewer.setAutoScroll(true);
```

## Progressive Darstellung {#progressive-rendering}

Die progressive Darstellung zeigt Inhalte zeichenweise an, anstatt alles auf einmal anzuzeigen, wodurch ein Schreibmaschineneffekt entsteht. KI-Chatoberflächen verwenden dies häufig, um Antworten schrittweise erscheinen zu lassen:

```java
MarkdownViewer viewer = new MarkdownViewer();
viewer.setProgressiveRender(true);
```

Wenn aktiviert, werden über `setContent()` oder `append()` hinzugefügte Inhalte in einen Puffer gestellt und schrittweise angezeigt. Wenn deaktiviert, erscheint der Inhalt sofort.

<ComponentDemo 
path='/webforj/markdownviewerprogressive?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/markdownviewer/MarkdownViewerProgressiveView.java'
height='650px'
/>

### Rendegeschwindigkeit {#render-speed}

Die Methode `setRenderSpeed()` steuert, wie viele Zeichen pro Animationsrahmen gerendert werden. Höhere Werte bedeuten schnellere Rendierung. Bei 60 fps entspricht die Standardgeschwindigkeit von 4 ungefähr 240 Zeichen pro Sekunde:

| Geschwindigkeit | Zeichen/Sekunde |
|------------------|------------------|
| 4 (Standard)     | ~240             |
| 6                | ~360             |
| 10               | ~600             |

```java
viewer.setRenderSpeed(6);
```

:::tip Anpassung Ihrer Datenrate
Wenn Ihr Server Inhalte schneller sendet, als der Viewer sie rendert, wächst der Puffer und die angezeigten Inhalte bleiben zurück. Erhöhen Sie `renderSpeed`, um Schritt zu halten, oder rufen Sie `flush()` auf, wenn alle Inhalte empfangen wurden, um die verbleibenden Inhalte sofort anzuzeigen.
:::

### Rendestatus {#render-state}

Wenn die progressive Darstellung aktiviert ist, gibt die Methode `isRendering()` `true` zurück, während die Komponente aktiv gepufferten Inhalt anzeigt. Chatoberflächen verwenden dies häufig, um einen Stopp-Button anzuzeigen oder auszublenden:

```java
if (viewer.isRendering()) {
  stopButton.setVisible(true);
}
```

Diese Methode gibt immer `false` zurück, wenn die progressive Darstellung deaktiviert ist.

### Steuerung der Darstellung {#controlling-rendering}

Zwei Methoden steuern, wie die progressive Darstellung stoppt:

- **`stop()`** stoppt das Rendern und verwirft alle gepufferten Inhalte, die noch nicht angezeigt wurden. Rufen Sie dies auf, wenn der Benutzer abbricht.
- **`flush()`** stoppt das Rendern, zeigt aber sofort alle verbleibenden gepufferten Inhalte an. Rufen Sie dies auf, wenn alle Inhalte empfangen wurden und Sie sie ohne zu warten anzeigen möchten.

```java
// Benutzer hat auf "Stopp Generierung" geklickt
viewer.stop();

// Alle Inhalte empfangen, jetzt alles anzeigen
viewer.flush();
```

Diese Methoden haben keine Auswirkung, wenn die progressive Darstellung deaktiviert ist.

### Warten auf den Abschluss {#waiting-for-completion}

Die Methode `whenRenderComplete()` gibt ein `PendingResult` zurück, das abgeschlossen wird, wenn die progressive Darstellung alle gepufferten Inhalte angezeigt hat:

```java
viewer.whenRenderComplete().thenAccept(v -> {
  inputField.setEnabled(true);
  inputField.focus();
});
```

Wenn die progressive Darstellung nicht aktiviert ist oder kein Inhalt gerendert wird, wird das `PendingResult` sofort abgeschlossen.

:::tip UI-Koordination
Wenn Sie die progressive Darstellung verwenden, aktivieren Sie die Eingabefelder nicht nur basierend darauf, wann Sie `append()` aufgerufen haben. Der Renderer zeigt möglicherweise noch gepufferten Inhalt an. Warten Sie auf `whenRenderComplete()`, damit alle Inhalte erscheinen, bevor Benutzer wieder interagieren können.
:::

Die folgende Demo simuliert eine KI-Chatoberfläche, die `append()` mit aktivierter progressiver Darstellung verwendet:

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

Wenn die progressive Darstellung aktiv ist, stoppt `clear()` auch das Rendern und schließt alle ausstehenden `whenRenderComplete()`-Ergebnisse ab.

## Syntaxhervorhebung {#syntax-highlighting}

Der `MarkdownViewer` unterstützt die Syntaxhervorhebung für Codeblöcke, wenn [Prism.js](https://prismjs.com/) verfügbar ist. Fügen Sie Prism.js zu Ihrer Anwendung mit den Anmerkungen `@JavaScript` und `@StyleSheet` hinzu:

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

Das Autoloader-Plugin lädt die Sprachdefinitionen nach Bedarf, sodass Codeblöcke mit Sprachhinweisen wie ` ```java ` oder ` ```python ` automatisch hervorgehoben werden.

<TableBuilder name="MarkdownViewer" />
