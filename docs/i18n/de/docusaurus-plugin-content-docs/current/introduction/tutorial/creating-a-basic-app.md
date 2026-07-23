---
title: Creating a Basic App
sidebar_position: 2
description: Step 1 - Add components to an app.
_i18n_hash: 7c98bf3851e1db10d5e0dd68045ea22d
---
In [Projekt Setup](/docs/introduction/tutorial/project-setup) haben Sie ein webforJ-Projekt erstellt. Jetzt ist es an der Zeit, die Hauptklasse für das Projekt zu erstellen und eine interaktive Benutzeroberfläche mit webforJ-Komponenten hinzuzufügen. In diesem Schritt lernen Sie:

- Den Einstiegspunkt für Apps, die webforJ und Spring Boot verwenden
- webforJ- und HTML-Element-Komponenten
- CSS zur Gestaltung von Komponenten verwenden

Wenn Sie diesen Schritt abschließen, erstellen Sie eine Version von [1-eine-einfache-App-erstellen](https://github.com/webforj/webforj-tutorial/tree/main/1-creating-a-basic-app).

<!-- Video hier einfügen -->

## App ausführen {#running-the-app}

Während Sie Ihre App entwickeln, können Sie [1-eine-einfache-App-erstellen](https://github.com/webforj/webforj-tutorial/tree/main/1-creating-a-basic-app) als Vergleich verwenden. So sehen Sie die App in Aktion:

1. Navigieren Sie zum obersten Verzeichnis, das die Datei `pom.xml` enthält, dies ist `1-eine-einfache-App`, wenn Sie der Version auf GitHub folgen.

2. Verwenden Sie den folgenden Maven-Befehl, um die Spring Boot-App lokal auszuführen:
    ```bash
    mvn
    ```

Die Ausführung der App öffnet automatisch einen neuen Browser unter `http://localhost:8080`.

## Der Einstiegspunkt {#entry-point}

Jede webforJ-App enthält eine einzelne Klasse, die <JavadocLink type="foundation" location="com/webforj/App" code='true'>App</JavadocLink> erweitert. Für dieses Tutorial und andere veröffentlichte webforJ-Projekte wird sie häufig `Application` genannt. Diese Klasse ist in einem Paket enthalten, das nach der `groupId` benannt ist, die Sie in [Projekt Setup](/docs/introduction/tutorial/project-setup) verwendet haben:

```
1-eine-einfache-App
│   .editorconfig
│   .gitignore
│   pom.xml
│   README.md
│
├───.vscode
├───src/main/java
// markiere die nächste Zeile
│   └──com/webforj/tutorial
// markiere die nächste Zeile
│       └──Application.java
└───target
```

Innerhalb der `Application`-Klasse verwendet die Methode `SpringApplication.run()` die Konfigurationen, um die App zu starten. Die verschiedenen Annotationen dienen den Konfigurationen der App.

```java title="Application.java"
@SpringBootApplication
@StyleSheet("ws://css/card.css")
@AppTheme("system")
@AppProfile(name = "Kundenanwendung", shortName = "CustomerApp")
public class Application extends App {

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }
}
```

### Annotationen {#annotations}

Die [`@SpringBootApplication`](https://docs.spring.io/spring-boot/api/java/org/springframework/boot/autoconfigure/SpringBootApplication.html) ist eine Kern-Annotation in Spring Boot. Sie fügen diese Annotation der Hauptklasse hinzu, um sie als Einstiegspunkt für Ihre App zu kennzeichnen.

`@StyleSheet`, `@AppTheme` und `@AppProfile` sind nur einige der vielen <JavadocLink type="foundation" location="com/webforj/annotation/package-summary">webforJ-Annotationen</JavadocLink>, die verfügbar sind, wenn Sie explizit Konfigurationen festlegen möchten.

- **`@StyleSheet`** bettet eine CSS-Datei in die Webseite ein. Weitere Einzelheiten zur Interaktion mit einer bestimmten CSS-Datei finden Sie später im Abschnitt [Stylen mit CSS](#styling-with-css).

- **`@AppTheme`** verwaltet das visuelle Thema der App. Wenn es auf `system` gesetzt ist, übernimmt die App automatisch das bevorzugte Thema des Benutzers: `light`, `dark` oder `dark-pure`. Informationen zur Erstellung benutzerdefinierter Themen oder zum Überschreiben der Standardthemen finden Sie im Artikel [Themen](/docs/styling/themes).

- **`@AppProfile`** hilft dabei zu konfigurieren, wie die App sich dem Benutzer als [installierbare App](/docs/configuration/installable-apps) präsentiert. Mindestens benötigt diese Annotation einen `name` für den vollständigen Namen der App und einen `shortName`, der verwendet wird, wenn der Platz begrenzt ist. Der `shortName` darf 12 Zeichen nicht überschreiten.

## Eine Benutzeroberfläche erstellen {#creating-a-ui}

Um Ihre UI zu erstellen, müssen Sie [HTML-Element-Komponenten](/docs/components/html-elements) und [webforJ-Komponenten](/docs/components/overview) hinzufügen. Für den Moment haben Sie nur eine Ein-Seiten-App, also fügen Sie die Komponenten direkt zur `Application`-Klasse hinzu. Dazu überschreiben Sie die Methode `App.run()` und erstellen ein `Frame`, um Komponenten hinzuzufügen.

```java
@Override
public void run() throws WebforjException {
  Frame mainFrame = new Frame();

  // UI-Komponenten erstellen und zum Frame hinzufügen

}
```

### Verwendung von HTML-Elementen {#using-html-elements}

Sie können standardmäßige HTML-Elemente zu Ihrer App mit [HTML-Element-Komponenten](/docs/components/html-elements) hinzufügen. Erstellen Sie eine neue Instanz der Komponente und verwenden Sie die Methode `add()`, um sie zum `Frame` hinzuzufügen:

```java
// Container für die UI-Elemente erstellen
Frame mainFrame = new Frame();

// HTML-Komponente erstellen
Paragraph tutorial = new Paragraph("Tutorial-Anwendung!");

// Die Komponente zum Container hinzufügen
mainFrame.add(tutorial);
```

### Verwendung von webforJ-Komponenten {#webforj-components-and-html-elements}

Während HTML-Elemente nützlich für Struktur, Semantik und leichte UI-Bedürfnisse sind, bieten [webforJ-Komponenten](/docs/components/overview) komplexeres und dynamischeres Verhalten.

Der folgende Code fügt eine [Button](/docs/components/button)-Komponente hinzu, ändert ihr Aussehen mit der Methode `setTheme()` und fügt einen Ereignis-Listener hinzu, um eine [Nachrichten-Dialog](/docs/components/option-dialogs/message)-Komponente zu erstellen, wenn der Button angeklickt wird. Die meisten Methoden von webforJ-Komponenten, die eine Komponente ändern, geben die Komponente selbst zurück, sodass Sie mehrere Methoden für kompakteren Code verketten können.

```java
// Container für die UI-Elemente erstellen
Frame mainFrame = new Frame();

// webforJ-Komponente erstellen
Button btn = new Button("Info");

// Die webforJ-Komponente ändern und einen Ereignis-Listener hinzufügen
btn.setTheme(ButtonTheme.PRIMARY)
  .addClickListener(e -> OptionDialog.showMessageDialog("Das ist ein Tutorial!", "Info"));

// Die Komponente zum Container hinzufügen
mainFrame.add(btn);
```

## Styling mit CSS {#styling-with-css}

Die meisten webforJ-Komponenten verfügen über integrierte Methoden, um häufige Stiländerungen vorzunehmen, wie z.B. Größenanpassungen und Themen.

```java
//Setzen Sie die Breite des Frames mit einem CSS-Schlüsselwort
mainFrame.setWidth("fit-content");

//Setzen Sie die maximale Breite des Buttons mit Pixeln
btn.setMaxWidth(200);

//Setzen Sie das Button-Thema auf PRIMARY
btn.setTheme(ButtonTheme.PRIMARY);
```

Neben diesen Methoden können Sie Ihre App auch mit CSS gestalten. Der Abschnitt **Styling** der Dokumentationsseite jeder Komponente enthält spezifische Informationen zu den entsprechenden CSS-Eigenschaften.

webforJ kommt auch mit einer Reihe gestalteter CSS-Variablen, die DWC-Tokens genannt werden. Weitere Informationen dazu, wie Sie webforJ-Komponenten gestalten und die Tokens verwenden, finden Sie in der [Styling](/docs/styling/overview)-Dokumentation.

### Referenzieren einer CSS-Datei {#referencing-a-css-file}

Es ist am besten, eine separate CSS-Datei zu haben, um alles organisiert und wartbar zu halten. Erstellen Sie eine Datei namens `card.css` im Verzeichnis `src/main/resources/static/css` mit der folgenden CSS-Klassendefinition:

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

Referenzieren Sie dann die Datei in `Application.java`, indem Sie die Annotation `@StyleSheet` mit dem Namen der CSS-Datei verwenden. Für diesen Schritt ist es `@StyleSheet("ws://css/card.css")`.

:::tip Webserver-Protokoll
Dieses Tutorial verwendet das Webserver-Protokoll, um die CSS-Datei zu referenzieren. Um mehr darüber zu erfahren, wie das funktioniert, siehe [Verwalten von Ressourcen](/docs/managing-resources/overview).
:::

### Hinzufügen von CSS-Klassen zu Komponenten {#adding-css-classes-to-components}

Sie können Klassennamen dynamisch zu Komponenten hinzufügen oder entfernen, indem Sie die Methoden `addClassName()` und `removeClassName()` verwenden. Für dieses Tutorial wird nur eine CSS-Klasse verwendet:

```java
mainFrame.addClassName("card");
```

## Fertige `Application` {#completed-application}

Ihre `Application`-Klasse sollte nun ähnlich aussehen wie das Folgende:

```java title="Application.java"
@SpringBootApplication
@StyleSheet("ws://css/card.css")
@AppTheme("system")
@AppProfile(name = "Kundenanwendung", shortName = "CustomerApp")
public class Application extends App {

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }

  @Override
  public void run() throws WebforjException {
    Frame mainFrame = new Frame();
    Paragraph tutorial = new Paragraph("Tutorial App!");
    Button btn = new Button("Info");

    btn.setTheme(ButtonTheme.PRIMARY)
        .setMaxWidth(200)
        .addClickListener(e -> OptionDialog.showMessageDialog("Das ist ein Tutorial!", "Info"));

    mainFrame.setWidth("fit-content")
        .addClassName("card")
        .add(tutorial, btn);
  }

}
```

:::tip Mehrere Seiten
Für eine komplexere App können Sie die UI in mehrere Seiten unterteilen, um die Organisation zu verbessern. Dieses Konzept wird später in diesem Tutorial im Abschnitt [Routing und Composites](/docs/introduction/tutorial/routing-and-composites) behandelt.
:::

## Nächster Schritt {#next-step}

Nachdem Sie eine funktionale App mit einer einfachen Benutzeroberfläche erstellt haben, besteht der nächste Schritt darin, ein Datenmodell hinzuzufügen und die Ergebnisse in einer `Table`-Komponente in [Arbeiten mit Daten](/docs/introduction/tutorial/working-with-data) anzuzeigen.
