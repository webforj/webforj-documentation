---
title: Creating a Basic App
sidebar_position: 2
description: Step 1 - Add components to an app.
_i18n_hash: d7385c22706cf76508b7e1971186f88d
---
In [Projektsetup](/docs/introduction/tutorial/project-setup) haben Sie ein webforJ-Projekt erstellt. Jetzt ist es an der Zeit, die Hauptklasse für das Projekt zu erstellen und eine interaktive Benutzeroberfläche mit webforJ-Komponenten hinzuzufügen. In diesem Schritt lernen Sie:

- Den Einstiegspunkt für Apps, die webforJ und Spring Boot verwenden
- webforJ- und HTML-Elemente-Komponenten
- Verwendung von CSS zur Gestaltung von Komponenten

Das Abschließen dieses Schrittes erstellt eine Version von [1-eine-grundlegende-app-erstellen](https://github.com/webforj/webforj-tutorial/tree/main/1-creating-a-basic-app).

<!-- Video hier einfügen -->

## App ausführen {#running-the-app}

Während Sie Ihre App entwickeln, können Sie [1-eine-grundlegende-app-erstellen](https://github.com/webforj/webforj-tutorial/tree/main/1-creating-a-basic-app) als Vergleich verwenden. Um die App in Aktion zu sehen:

1. Navigieren Sie zum obersten Verzeichnis, das die `pom.xml`-Datei enthält; dies ist `1-eine-grundlegende-app`, wenn Sie der Version auf GitHub folgen.

2. Verwenden Sie den folgenden Maven-Befehl, um die Spring Boot-App lokal auszuführen:
    ```bash
    mvn
    ```

Beim Ausführen der App wird automatisch ein neuer Browser unter `http://localhost:8080` geöffnet.

## Der Einstiegspunkt {#entry-point}

Jede webforJ-App enthält eine einzelne Klasse, die <JavadocLink type="foundation" location="com/webforj/App" code='true'>App</JavadocLink> erweitert. Für dieses Tutorial und andere veröffentlichte webforJ-Projekte wird sie üblicherweise `Application` genannt. Diese Klasse befindet sich in einem Paket, das nach der `groupId` benannt ist, die Sie in [Projektsetup](/docs/introduction/tutorial/project-setup) verwendet haben:

```
1-eine-grundlegende-app
│   .editorconfig
│   .gitignore
│   pom.xml
│   README.md
│
├───.vscode
├───src/main/java
// highlight-next-line
│   └──com/webforj/tutorial
// highlight-next-line
│       └──Application.java
└───target
```

Innerhalb der `Application`-Klasse verwendet die Methode `SpringApplication.run()` die Konfigurationen, um die App zu starten. Die verschiedenen Annotationen sind für die Konfigurationen der App.

```java title="Application.java"
@SpringBootApplication
@BundleEntry("css/card.css")
@AppTheme("system")
@AppProfile(name = "Kundenanwendung", shortName = "CustomerApp")
public class Application extends App {

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }
}
```

### Annotationen {#annotations}

Die [`@SpringBootApplication`](https://docs.spring.io/spring-boot/api/java/org/springframework/boot/autoconfigure/SpringBootApplication.html) ist eine Kernannotation in Spring Boot. Sie platzieren diese Annotation auf der Hauptklasse, um sie als Einstiegspunkt Ihrer App zu kennzeichnen.

`@BundleEntry`, `@AppTheme` und `@AppProfile` sind nur einige der <JavadocLink type="foundation" location="com/webforj/annotation/package-summary">webforJ-Annotationen</JavadocLink>, die verfügbar sind, wenn Sie Konfigurationen explizit festlegen möchten.

- **`@BundleEntry`** fügt eine Datei aus `src/main/frontend` zum Frontend-Bündel der App hinzu. In diesem Schritt lädt es die CSS-Datei, die Sie später in [Gestaltung mit CSS](#styling-with-css) erstellen werden.

- **`@AppTheme`** verwaltet das visuelle Thema der App. Wenn es auf `system` gesetzt ist, nimmt die App automatisch das bevorzugte Thema des Benutzers an: `light`, `dark` oder `dark-pure`. Weitere Informationen zur Erstellung benutzerdefinierter Themen oder zum Überschreiben der Standardthemen finden Sie im Artikel [Themen](/docs/styling/themes).

- **`@AppProfile`** hilft, zu konfigurieren, wie die App dem Benutzer als [installierbare App](/docs/configuration/installable-apps) präsentiert wird. Mindestens benötigt diese Annotation einen `name` für den vollständigen Namen der App und einen `shortName`, der verwendet wird, wenn der Platz begrenzt ist. Der `shortName` sollte 12 Zeichen nicht überschreiten.

## Benutzeroberfläche erstellen {#creating-a-ui}

Um Ihre UI zu erstellen, müssen Sie [HTML-Element-Komponenten](/docs/components/html-elements) und [webforJ-Komponenten](/docs/components/overview) hinzufügen. Für den Moment haben Sie nur eine Single-Page-App, sodass Sie Komponenten direkt zur `Application`-Klasse hinzufügen werden.
Dazu überschreiben Sie die Methode `App.run()` und erstellen ein `Frame`, um die Komponenten hinzuzufügen.

```java
@Override
public void run() throws WebforjException {
  Frame mainFrame = new Frame();

  // UI-Komponenten erstellen und zum Frame hinzufügen

}
```

### Verwendung von HTML-Elementen {#using-html-elements}

Sie können standardmäßige HTML-Elemente zu Ihrer App mit [HTML-Element-Komponenten](/docs/components/html-elements) hinzufügen.
Erstellen Sie eine neue Instanz der Komponente und verwenden Sie die Methode `add()`, um sie zum `Frame` hinzuzufügen:

```java
// Container für die UI-Elemente erstellen
Frame mainFrame = new Frame();

// HTML-Komponente erstellen
Paragraph tutorial = new Paragraph("Tutorial-Anwendung!");

// Komponente zum Container hinzufügen
mainFrame.add(tutorial);
```

### Verwendung von webforJ-Komponenten {#webforj-components-and-html-elements}

Während HTML-Elemente für Struktur, Semantik und leichte UI-Anforderungen nützlich sind, bieten [webforJ-Komponenten](/docs/components/overview) komplexeres und dynamisches Verhalten.

Der folgende Code fügt eine [Button](/docs/components/button)-Komponente hinzu, ändert ihr Aussehen mit der Methode `setTheme()` und fügt einen Ereignislistener hinzu, um eine [Message Dialog](/docs/components/option-dialogs/message)-Komponente zu erstellen, wenn der Button angeklickt wird. Die meisten Methoden von webforJ-Komponenten, die eine Komponente modifizieren, geben die Komponente selbst zurück, sodass Sie mehrere Methoden für kompakteren Code verketten können.

```java
// Container für die UI-Elemente erstellen
Frame mainFrame = new Frame();

// webforJ-Komponente erstellen
Button btn = new Button("Info");

// webforJ-Komponente verändern und einen Ereignislistener hinzufügen
btn.setTheme(ButtonTheme.PRIMARY)
  .addClickListener(e -> OptionDialog.showMessageDialog("Dies ist ein Tutorial!", "Info"));

// Komponente zum Container hinzufügen
mainFrame.add(btn);
```

## Gestaltung mit CSS {#styling-with-css}

Die meisten webforJ-Komponenten haben integrierte Methoden, um häufige Stiländerungen vorzunehmen, z. B. Größenanpassungen und Themenänderungen.

```java
// Breite des Frames mit einem CSS-Schlüsselwort festlegen
mainFrame.setWidth("fit-content");

// Maximale Breite des Buttons in Pixeln festlegen
btn.setMaxWidth(200);

// Button-Thema auf PRIMARY festlegen
btn.setTheme(ButtonTheme.PRIMARY);
```

Zusätzlich zu diesen Methoden können Sie Ihre App mit CSS gestalten. Der Abschnitt **Gestaltung** auf der Dokumentationsseite jeder Komponente enthält spezifische Details zu den relevanten CSS-Eigenschaften.

webforJ bringt auch eine Reihe von entworfenen CSS-Variablen namens DWC-Tokens mit. Siehe die Dokumentation [Gestaltung](/docs/styling/overview) für detaillierte Informationen, wie Sie webforJ-Komponenten gestalten und wie Sie die Tokens verwenden können.

### Hinzufügen von CSS zum Frontend-Bündel {#referencing-a-css-file}

Es ist am besten, eine separate CSS-Datei zu haben, um alles organisiert und wartbar zu halten. Erstellen Sie eine Datei namens `card.css` im Verzeichnis `src/main/frontend/css`, mit der folgenden CSS-Klassendefinition:

```css title="card.css"
.card {
  display: grid;
  gap: var(--dwc-space-l);
  padding: var(--dwc-space-l);
  margin: var(--dwc-space-l) auto;
  border: thin solid var(--dwc-color-default);
  border-radius: 16px;
  background-color: var(--dwc-surface-3);
  box-shadow: var(--dwc-shadow-xs);
}
```

Fügen Sie dann die Datei vom `Application.java` zum Frontend-Bündel hinzu, indem Sie `@BundleEntry("css/card.css")` verwenden. Der Pfad ist relativ zu `src/main/frontend`.

:::tip Frontend-Bundler
Die Maven-Konfiguration des Tutorial-Projekts führt den webforJ-Frontend-Watcher aus, wenn Sie die App mit `mvn` starten, sodass Änderungen unter `src/main/frontend` während der Entwicklung neu gebaut werden. Um mehr zu erfahren, siehe [Frontend-Bundler](/docs/managing-resources/bundler/overview).
:::

### Hinzufügen von CSS-Klassen zu Komponenten {#adding-css-classes-to-components}

Sie können dynamisch Klassennamen zu Komponenten hinzufügen oder entfernen, indem Sie die Methoden `addClassName()` und `removeClassName()` verwenden. Für dieses Tutorial wird nur eine CSS-Klasse verwendet:

```java
mainFrame.addClassName("card");
```

## Abgeschlossene `Application` {#completed-application}

Ihre `Application`-Klasse sollte jetzt ähnlich wie folgt aussehen:

```java title="Application.java"
@SpringBootApplication
@BundleEntry("css/card.css")
@AppTheme("system")
@AppProfile(name = "Kundenanwendung", shortName = "CustomerApp")
public class Application extends App {

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }

  @Override
  public void run() throws WebforjException {
    Frame mainFrame = new Frame();
    Paragraph tutorial = new Paragraph("Tutorial-App!");
    Button btn = new Button("Info");

    btn
      .setTheme(ButtonTheme.PRIMARY)
      .setMaxWidth(200)
      .addClickListener(e ->
        OptionDialog.showMessageDialog("Dies ist ein Tutorial!", "Info")
      );

    mainFrame.setWidth("fit-content").addClassName("card").add(tutorial, btn);
  }
}
```

:::tip Mehrere Seiten
Für eine komplexere App können Sie die UI in mehrere Seiten aufteilen, um eine bessere Organisation zu erreichen. Dieses Konzept wird später in diesem Tutorial in [Routing und Kompositen](/docs/introduction/tutorial/routing-and-composites) behandelt.
:::

## Nächster Schritt {#next-step}

Nachdem Sie eine funktionale App mit einer grundlegenden Benutzeroberfläche erstellt haben, besteht der nächste Schritt darin, ein Datenmodell hinzuzufügen und die Ergebnisse in einer `Table`-Komponente in [Mit Daten arbeiten](/docs/introduction/tutorial/working-with-data) anzuzeigen.
