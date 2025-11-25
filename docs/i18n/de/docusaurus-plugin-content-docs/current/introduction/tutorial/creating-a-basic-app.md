---
title: Eine Grundlegende App Erstellen
sidebar_position: 2
_i18n_hash: c59ff0def84230ed79877cba3d5e5aa4
---
Dieser erste Schritt legt die Grundlage für die Kundenverwaltungs-App, indem er eine einfache, interaktive Benutzeroberfläche erstellt. Dies demonstriert, wie man eine grundlegende webforJ-App einrichtet, mit einem einzigen Button, der beim Klicken einen Dialog öffnet. Es ist eine unkomplizierte Implementierung, die wichtige Komponenten einführt und Ihnen ein Gefühl dafür gibt, wie webforJ funktioniert.

Dieser Schritt nutzt die Basis-App-Klasse von webforJ, um die Struktur und das Verhalten der App zu definieren. Im weiteren Verlauf wird in späteren Schritten auf eine fortgeschrittenere Einrichtung umgestiegen, die Routing verwendet, um mehrere Bildschirme zu verwalten, wie in [Scaling with Routing and Composites](./scaling-with-routing-and-composites) eingeführt.

Am Ende dieses Schrittes haben Sie eine funktionierende App, die die grundlegende Interaktion mit Komponenten und die Ereignisbehandlung in webforJ demonstriert. Um die App auszuführen:

- Gehen Sie zum Verzeichnis `1-creating-a-basic-app`
- Führen Sie den Befehl `mvn jetty:run` aus

<div class="videos-container">
  <video controls>
    <source src="https://cdn.webforj.com/webforj-documentation/video/tutorials/creating-a-basic-app.mp4" type="video/mp4"/>
  </video>
</div>

## Erstellung einer webforJ-App {#creating-a-webforj-app}

In webforJ stellt eine `App` das zentrale Element zur Definition und Verwaltung Ihres Projekts dar. Jede webforJ-App beginnt mit der Erstellung einer Klasse, die die grundlegende `App`-Klasse erweitert, die als Kernframework dient, um:

- Den Lebenszyklus der App zu verwalten, einschließlich Initialisierung und Beendigung.
- Routing und Navigation zu verwalten, falls aktiviert.
- Das Thema, die Locale und andere allgemeine Konfigurationen der App festzulegen.
- Wesentliche Hilfsfunktionen zur Interaktion mit der Umgebung und den Komponenten bereitzustellen.

### Erweiterung der `App`-Klasse {#extending-the-app-class}

Für diesen Schritt wird eine Klasse namens `DemoApplication.java` erstellt, die die `App`-Klasse erweitert.

```java title="DemoApplication.java"
public class DemoApplication extends App {
  @Override
  public void run() {
    // Die Kernaudio-Logik wird hier platziert
  }
}
```

:::tip Wichtige Konfigurationseigenschaften

In dieser Demo-App ist die Datei `webforj.conf` mit den folgenden zwei wesentlichen Eigenschaften konfiguriert:

- **`webforj.entry`**: Gibt den vollqualifizierten Namen der Klasse an, die `App` erweitert und als Haupteinstiegspunkt für Ihr Projekt fungiert. Für dieses Tutorial setzen Sie sie auf `com.webforj.demos.DemoApplication`, um Mehrdeutigkeiten während der Initialisierung zu vermeiden.
  ```hocon
  webforj.entry = com.webforj.demos.DemoApplication
  ```
- **`webforj.debug`**: Aktiviert den Debug-Modus für detaillierte Protokolle und Sichtbarkeit von Fehlern während der Entwicklung. Stellen Sie sicher, dass dies während der Arbeit an diesem Tutorial auf `true` gesetzt ist:
  ```hocon
  webforj.debug = true
  ```

Für weitere Informationen zu zusätzlichen Konfigurationsoptionen siehe den [Konfigurationsleitfaden](../../configuration/overview).
:::

### Überschreiben der `run()`-Methode {#overriding-the-run-method}

Nachdem Sie die korrekte Konfiguration für das Projekt sichergestellt haben, wird die `run()`-Methode in Ihrer `App`-Klasse überschrieben.

Die `run()`-Methode ist das Herz Ihrer App in webforJ. Sie definiert, was passiert, nachdem die App initialisiert wurde, und ist der Haupteinstiegspunkt für die Funktionen Ihrer App. Durch das Überschreiben der `run()`-Methode können Sie die Logik implementieren, die die Benutzeroberfläche und das Verhalten Ihrer App erstellt und verwaltet.

:::tip Verwendung von Routing
Bei der Implementierung von Routing innerhalb einer App ist das Überschreiben der `run()`-Methode nicht erforderlich, da das Framework automatisch die Initialisierung der Routen und die Erstellung des anfänglichen `Frame` behandelt. Die `run()`-Methode wird aufgerufen, nachdem die Basisroute aufgelöst wurde, sodass sichergestellt wird, dass das Navigationssystem der App vollständig initialisiert ist, bevor irgendwelche Logik ausgeführt wird. Dieses Tutorial wird später näher auf die Implementierung von Routing in [Schritt 3](scaling-with-routing-and-composites) eingehen. Weitere Informationen sind auch im [Routing-Artikel](../../routing/overview) verfügbar.
:::

```java title="DemoApplication.java"
public class DemoApplication extends App {
  @Override
  public void run() throws WebforjException {
    // App-Logik
  }
}
```

## Komponenten hinzufügen {#adding-components}

In webforJ sind Komponenten die Bausteine der Benutzeroberfläche Ihrer App. Diese Komponenten repräsentieren diskrete Teile der Benutzeroberfläche Ihrer App, wie Schaltflächen, Textfelder, Dialoge oder Tabellen.

Sie können sich eine Benutzeroberfläche als einen Baum von Komponenten vorstellen, wobei ein `Frame` als Wurzel fungiert. Jede Komponente, die zum `Frame` hinzugefügt wird, wird zu einem Zweig oder Blatt in diesem Baum und trägt zur Gesamtstruktur und zum Verhalten Ihrer App bei.

:::tip Komponenten-Katalog
Siehe [diese Seite](../../components/overview) für eine Liste der verschiedenen in webforJ verfügbaren Komponenten.
:::

### App `Frame` {#app-frame}

Die `Frame`-Klasse in webforJ repräsentiert ein nicht verschachtelbares, oberstes Fenster in Ihrer App. Ein `Frame` fungiert typischerweise als Hauptcontainer für UI-Komponenten und ist ein wesentlicher Baustein zum Erstellen der Benutzeroberfläche. Jede App beginnt mit mindestens einem `Frame`, in den Sie Komponenten wie Schaltflächen, Dialoge oder Formulare hinzufügen können.

Ein `Frame` wird in der `run()`-Methode in diesem Schritt erstellt – später werden hier Komponenten hinzugefügt.

```java title="DemoApplication.java"
public class DemoApplication extends App {
  @Override
  public void run() throws WebforjException {
    Frame mainFrame = new Frame();
  }
}
```

### Server- und clientseitige Komponenten {#server-and-client-side-components}

Jede serverseitige Komponente in webforJ hat eine entsprechende clientseitige Webkomponente. Serverseitige Komponenten behandeln Logik und Backend-Interaktionen, während clientseitige Komponenten wie `dwc-button` und `dwc-dialog` das Frontend-Rendering und Styling verwalten.

:::tip Zusammengesetzte Komponenten

Neben den von webforJ bereitgestellten Kernkomponenten können Sie benutzerdefinierte zusammengesetzte Komponenten entwerfen, indem Sie mehrere Elemente zu einer einzigen wiederverwendbaren Einheit gruppieren. Dieses Konzept wird in diesem Schritt des Tutorials behandelt. Weitere Informationen sind im [Artikel über zusammengesetzte Komponenten](../../building-ui/composite-components) verfügbar.
:::

Komponenten müssen einer Containerklasse hinzugefügt werden, die das <JavadocLink type="foundation" location="com/webforj/concern/HasComponents" code='true' >HasComponents</JavadocLink>-Interface implementiert. Der `Frame` ist eine solche Klasse - für diesen Schritt fügen Sie dem `Frame` ein `Paragraph` und einen `Button` hinzu, die in der Benutzeroberfläche im Browser gerendert werden:

```java title="DemoApplication.java"
public class DemoApplication extends App {
  Paragraph demo = new Paragraph("Demo-Anwendung!");
  Button btn = new Button("Info");

  @Override
  public void run() throws WebforjException {
    Frame mainFrame = new Frame();
    btn.setTheme(ButtonTheme.PRIMARY)
        .addClickListener(e -> showMessageDialog("Dies ist eine Demo!", "Info"));
    mainFrame.add(demo, btn);
  }
}
```

Wenn Sie dies ausführen, sollte Ihnen eine einfach gestaltete Schaltfläche ermöglichen, eine Nachricht anzuzeigen, die sagt: "Dies ist eine Demo!"

## Styling mit CSS {#styling-with-css}

Das Styling in webforJ bietet Ihnen vollständige Flexibilität, das Erscheinungsbild Ihrer App zu gestalten. Während das Framework ein kohärentes Design und Stil "out of the box" unterstützt, gibt es keinen spezifischen Stilansatz, den Sie einhalten müssen, sodass Sie benutzerdefinierte Stile anwenden können, die den Anforderungen Ihrer App entsprechen.

Mit webforJ können Sie dynamisch Klassennamen zu Komponenten für bedingtes oder interaktives Styling hinzufügen, CSS für ein konsistentes und skalierbares Designsystem verwenden und gesamte Inline- oder externe Stylesheets einfügen.

### Hinzufügen von CSS-Klassen zu Komponenten {#adding-css-classes-to-components}

Sie können dynamisch Klassennamen zu Komponenten hinzufügen oder entfernen, indem Sie die Methoden `addClassName()` und `removeClassName()` verwenden. Diese Methoden ermöglichen es Ihnen, die Stile der Komponente basierend auf der Logik Ihrer App zu steuern. Fügen Sie den Klassennamen `mainFrame` zum vorher erstellten `Frame` hinzu, indem Sie den folgenden Code in der `run()`-Methode einfügen:

```java
mainFrame.addClassName("mainFrame");
```

### Anfügen von CSS-Dateien {#attaching-css-files}

Um Ihre App zu gestalten, können Sie CSS-Dateien in Ihr Projekt einfügen, entweder durch Asset-Annotationen oder indem Sie die webforJ <JavadocLink type="foundation" location="com/webforj/Page" >Asset-API</JavadocLink> zur Laufzeit nutzen. [Siehe diesen Artikel](../../managing-resources/importing-assets) für weitere Informationen.

Beispielsweise wird die @StyleSheet-Annotation verwendet, um Stile aus dem Verzeichnis resources/static einzufügen. Sie generiert automatisch eine URL für die angegebene Datei und injiziert sie in das DOM, sodass sichergestellt wird, dass die Stile auf Ihre App angewendet werden. Beachten Sie, dass Dateien außerhalb des statischen Verzeichnisses nicht zugänglich sind.

```java title="DemoApplication.java"
@StyleSheet("ws://styles/library.css")
public class DemoApplication extends App {
  @Override
  public void run() {
    // App-Logik hier
  }
}
```
:::tip URLs für Webserver
Um sicherzustellen, dass statische Dateien zugänglich sind, sollten sie im resources/static-Ordner platziert werden. Um eine statische Datei einzufügen, können Sie deren URL mithilfe des Webserver-Protokolls konstruieren.
:::

### Beispiel CSS-Code {#sample-css-code}

Eine CSS-Datei wird in Ihrem Projekt unter `resources > static > css > demoApplication.css` verwendet, und der folgende CSS-Code wird verwendet, um grundlegende Stile auf die App anzuwenden.

```css
.mainFrame {
  display: inline-grid;
  gap: 20px;
  margin: 20px;
  padding: 20px;
  border: 1px dashed;
  border-radius: 10px;
}
```

Sobald dies erledigt ist, sollte die folgende Annotation zu Ihrer `App`-Klasse hinzugefügt werden:

```java title="DemoApplication.java"
@StyleSheet("ws://css/demoApplication.css")
@AppTitle("Demo Schritt 1")
public class DemoApplication extends App {
```

Die CSS-Stile werden auf den Haupt-`Frame` angewendet und sorgen für Struktur, indem sie die Komponenten mit einem [Grid-Layout](https://developer.mozilla.org/en-US/docs/Web/CSS/CSS_grid_layout) anordnen und Margin-, Padding- und Randstile hinzufügen, um die Benutzeroberfläche optisch zu organisieren.
