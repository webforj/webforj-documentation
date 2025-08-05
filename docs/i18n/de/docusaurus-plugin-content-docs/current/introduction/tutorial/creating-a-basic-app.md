---
title: Creating a Basic App
sidebar_position: 2
_i18n_hash: 9bd91b6d733198a2c16c9377029e8162
---
Dieser erste Schritt legt die Grundlagen für die Kundenverwaltungs-App, indem eine einfache, interaktive Schnittstelle erstellt wird. Dies demonstriert, wie man eine grundlegende webforJ-App einrichtet, mit einem einzigen Button, der beim Klicken einen Dialog öffnet. Es handelt sich um eine unkomplizierte Implementierung, die wichtige Komponenten einführt und Ihnen ein Gefühl dafür gibt, wie webforJ funktioniert.

In diesem Schritt wird die Basis-App-Klasse, die von webforJ bereitgestellt wird, genutzt, um die Struktur und das Verhalten der App zu definieren. Das Durcharbeiten späterer Schritte wird auf ein fortgeschritteneres Setup überleiten, bei dem Routing verwendet wird, um mehrere Bildschirme zu verwalten, eingeführt in [Scaling with Routing and Composites](./scaling-with-routing-and-composites).

Am Ende dieses Schrittes haben Sie eine funktionierende App, die grundlegende Interaktionen mit Komponenten und Ereignisbehandlung in webforJ demonstriert. Um die App auszuführen:

- Gehen Sie zum Verzeichnis `1-creating-a-basic-app`
- Führen Sie den Befehl `mvn jetty:run` aus

<div class="videos-container">
  <video controls>
    <source src="https://cdn.webforj.com/webforj-documentation/video/tutorials/creating-a-basic-app.mp4" type="video/mp4"/>
  </video>
</div>

## Erstellung einer webforJ-App {#creating-a-webforj-app}

In webforJ stellt eine `App` das zentrale Drehkreuz zur Definition und Verwaltung Ihres Projekts dar. Jede webforJ-App beginnt mit der Erstellung einer Klasse, die die grundlegende `App`-Klasse erweitert, die als Kernframework dient, um:

- Den App-Lebenszyklus, einschließlich Initialisierung und Beendigung, zu verwalten.
- Routing und Navigation, falls aktiviert, zu handhaben.
- Das Thema, die Sprache und andere allgemeine Konfigurationen der App zu definieren.
- Wesentliche Dienstprogramme für die Interaktion mit der Umgebung und den Komponenten bereitzustellen.

### Erweiterung der `App`-Klasse {#extending-the-app-class}

Für diesen Schritt wird eine Klasse namens `DemoApplication.java` erstellt, die die `App`-Klasse erweitert.

```java title="DemoApplication.java"
public class DemoApplication extends App {
  @Override
  public void run() {
    // Kern-Logik der App wird hier platziert
  }
}
```

:::tip Wichtige Konfigurationseigenschaften

In dieser Demoversion der App ist die Datei `webforj.conf` mit den folgenden zwei wesentlichen Eigenschaften konfiguriert:

- **`webforj.entry`**: Gibt den vollqualifizierten Namen der Klasse an, die `App` erweitert und als Haupteinstiegspunkt für Ihr Projekt dient. Für dieses Tutorial setzen Sie es auf `com.webforj.demos.DemoApplication`, um Mehrdeutigkeiten während der Initialisierung zu vermeiden.
  ```hocon
  webforj.entry = com.webforj.demos.DemoApplication
  ```
- **`webforj.debug`**: Aktiviert den Debug-Modus für detaillierte Protokolle und Fehlersichtbarkeit während der Entwicklung. Stellen Sie sicher, dass dies während der Arbeit an diesem Tutorial auf `true` gesetzt ist:
  ```hocon
  webforj.debug = true
  ```

Für weitere Informationen zu zusätzlichen Konfigurationsoptionen siehe den [Konfigurationsleitfaden](../../configuration/overview).
:::

### Überschreiben der `run()`-Methode {#overriding-the-run-method}

Nachdem Sie die richtige Konfiguration für das Projekt sichergestellt haben, wird die `run()`-Methode in Ihrer `App`-Klasse überschrieben.

Die `run()`-Methode ist das Herzstück Ihrer App in webforJ. Sie definiert, was passiert, nachdem die App initialisiert wurde, und ist der Haupteinstiegspunkt für die Funktionen Ihrer App. Durch das Überschreiben der `run()`-Methode können Sie die Logik implementieren, die die Benutzeroberfläche und das Verhalten Ihrer App erstellt und verwaltet.

:::tip Verwendung von Routing
Beim Implementieren von Routing innerhalb einer App ist das Überschreiben der `run()`-Methode nicht erforderlich, da das Framework automatisch die Initialisierung von Routen und die Erstellung des initialen `Frame` behandelt. Die `run()`-Methode wird aufgerufen, nachdem die Basisroute aufgelöst ist, und stellt sicher, dass das Navigationssystem der App vollständig initialisiert ist, bevor irgendeine Logik ausgeführt wird. Dieses Tutorial wird in [Schritt 3](scaling-with-routing-and-composites) detaillierter auf die Implementierung von Routing eingehen. Weitere Informationen sind auch im [Routing-Artikel](../../routing/overview) verfügbar.
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

In webforJ sind Komponenten die Bausteine der Benutzeroberfläche Ihrer App. Diese Komponenten repräsentieren diskrete Teile der UI Ihrer App, wie Buttons, Textfelder, Dialoge oder Tabellen.

Sie können sich eine UI als Baum von Komponenten vorstellen, wobei ein `Frame` als Wurzel dient. Jede in den `Frame` hinzugefügte Komponente wird zu einem Ast oder Blatt in diesem Baum, der zur gesamten Struktur und zum Verhalten Ihrer App beiträgt.

:::tip Komponenten-Katalog
Siehe [diese Seite](../../components/overview) für eine Liste der verschiedenen Komponenten, die in webforJ verfügbar sind.
:::

### App `Frame` {#app-frame}

Die `Frame`-Klasse in webforJ stellt ein nicht-nistbares, oberstes Fenster in Ihrer App dar. Ein `Frame` fungiert in der Regel als Hauptcontainer für UI-Komponenten und ist somit ein wesentlicher Baustein zum Konstruieren der Benutzeroberfläche. Jede App beginnt mit mindestens einem `Frame`, in den Sie Komponenten wie Buttons, Dialoge oder Formulare hinzufügen können.

In diesem Schritt wird ein `Frame` innerhalb der `run()`-Methode erstellt - später werden hier Komponenten hinzugefügt.

```java title="DemoApplication.java"
public class DemoApplication extends App {
  @Override
  public void run() throws WebforjException {
    Frame mainFrame = new Frame();
  }
}
```

### Server- und clientseitige Komponenten {#server-and-client-side-components}

Jede serverseitige Komponente in webforJ hat eine entsprechende clientseitige Webkomponente. Serverseitige Komponenten verwalten Logik und Backend-Interaktionen, während clientseitige Komponenten wie `dwc-button` und `dwc-dialog` das Rendering und das Styling auf der Frontend-Seite übernehmen.

:::tip Kompositionale Komponenten

Neben den von webforJ bereitgestellten Kernkomponenten können Sie benutzerdefinierte kompositionale Komponenten entwerfen, indem Sie mehrere Elemente zu einer einzelnen wiederverwendbaren Einheit gruppieren. Dieses Konzept wird in diesem Schritt des Tutorials behandelt. Weitere Informationen sind im [Composite-Artikel](../../building-ui/composite-components) verfügbar.
:::

Komponenten müssen einer Containerklasse hinzugefügt werden, die das <JavadocLink type="foundation" location="com/webforj/concern/HasComponents" code='true'>HasComponents</JavadocLink>-Interface implementiert. Der `Frame` ist eine solche Klasse - für diesen Schritt fügen Sie einen `Paragraph` und einen `Button` zum `Frame` hinzu, die in der Benutzeroberfläche im Browser gerendert werden:

```java title="DemoApplication.java"
public class DemoApplication extends App {
  Paragraph demo = new Paragraph("Demo-Anwendung!");
  Button btn = new Button("Info");

  @Override
  public void run() throws WebforjException {
    Frame mainFrame = new Frame();
    btn.setTheme(ButtonTheme.PRIMARY)
        .addClickListener(e -> showMessageDialog("Das ist eine Demo!", "Info"));
    mainFrame.add(demo, btn);
  }
}
```

Das Ausführen dieser App sollte Ihnen einen einfach gestalteten Button geben, der eine Nachricht anzeigt mit "Das ist eine Demo!".

## Styling mit CSS {#styling-with-css}

Styling in webforJ bietet Ihnen vollständige Flexibilität, um das Erscheinungsbild Ihrer App zu gestalten. Während das Framework ein kohärentes Design und Stil out of the box unterstützt, zwingt es nicht zu einem bestimmten Styling-Ansatz, sodass Sie benutzerdefinierte Stile anwenden können, die mit den Anforderungen Ihrer App übereinstimmen.

Mit webforJ können Sie dynamisch Klassennamen zu Komponenten für bedingtes oder interaktives Styling hinzufügen, CSS für ein konsistentes und skalierbares Designsystem verwenden und ganze Inline- oder externe Stylesheets einfügen.

### Hinzufügen von CSS-Klassen zu Komponenten {#adding-css-classes-to-components}

Sie können dynamisch Klassennamen zu Komponenten hinzufügen oder entfernen, indem Sie die Methoden `addClassName()` und `removeClassName()` verwenden. Diese Methoden ermöglichen es Ihnen, die Stile der Komponente basierend auf der Logik Ihrer App zu steuern. Fügen Sie den Klassennamen `mainFrame` zum im vorherigen Schritt erstellten `Frame` hinzu, indem Sie den folgenden Code in der `run()`-Methode einfügen:

```java
mainFrame.addClassName("mainFrame");
```

### Anhängen von CSS-Dateien {#attaching-css-files}

Um Ihre App zu stylen, können Sie CSS-Dateien in Ihrem Projekt einfügen, entweder durch Verwendung von Asset-Anmerkungen oder indem Sie die webforJ <JavadocLink type="foundation" location="com/webforj/Page">Asset-API</JavadocLink> zur Laufzeit nutzen. [Siehe diesen Artikel](../../managing-resources/importing-assets) für weitere Informationen.

Das @StyleSheet-Annotation wird verwendet, um Stile aus dem Verzeichnis resources/static einzufügen. Es generiert automatisch eine URL für die angegebene Datei und injiziert sie in das DOM, um sicherzustellen, dass die Stile auf Ihre App angewendet werden. Beachten Sie, dass Dateien außerhalb des statischen Verzeichnisses nicht zugänglich sind.

```java title="DemoApplication.java"
@StyleSheet("ws://styles/library.css")
public class DemoApplication extends App {
  @Override
  public void run() {
    // App-Logik hier
  }
}
```
:::tip Webserver-URLs
Um sicherzustellen, dass statische Dateien zugänglich sind, sollten sie im resources/static-Ordner abgelegt werden. Um eine statische Datei einzufügen, können Sie ihre URL unter Verwendung des Webserverprotokolls konstruieren.
:::

### Beispiel-CSS-Code {#sample-css-code}

Eine CSS-Datei wird in Ihrem Projekt unter `resources > static > css > demoApplication.css` verwendet, und der folgende CSS-Code wird verwendet, um grundlegendes Styling für die App anzuwenden.

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

Sobald dies erledigt ist, sollte die folgende Anmerkung zu Ihrer `App`-Klasse hinzugefügt werden:

```java title="DemoApplication.java"
@StyleSheet("ws://css/demoApplication.css")
@AppTitle("Demo Schritt 1")
public class DemoApplication extends App {
```

Die CSS-Stile werden auf den Haupt-`Frame` angewendet und strukturieren die Anordnung der Komponenten mit einem [Grid-Layout](https://developer.mozilla.org/en-US/docs/Web/CSS/CSS_grid_layout) und fügen Rand-, Polster- und Randstile hinzu, um die Benutzeroberfläche visuell zu organisieren.
