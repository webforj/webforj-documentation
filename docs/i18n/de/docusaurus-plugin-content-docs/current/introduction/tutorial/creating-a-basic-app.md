---
title: Creating a Basic App
sidebar_position: 2
description: Step 1 - Add components to an app.
_i18n_hash: ac74bc5c04bce477a7407c9ff94323a4
---
In [Projektsetup](/docs/introduction/tutorial/project-setup) haben Sie ein webforJ-Projekt erstellt. Jetzt ist es an der Zeit, die Hauptklasse für das Projekt zu erstellen und eine interaktive Schnittstelle mit webforJ-Komponenten hinzuzufügen. In diesem Schritt erfahren Sie:

- Den Einstiegspunkt für Apps, die webforJ und Spring Boot verwenden
- webforJ- und HTML-Elementkomponenten
- Verwendung von CSS zur Gestaltung von Komponenten

Das Abschließen dieses Schrittes erstellt eine Version von [1-creating-a-basic-app](https://github.com/webforj/webforj-tutorial/tree/main/1-creating-a-basic-app).

<!-- Video hier einfügen -->

## Die App ausführen {#running-the-app}

Während Sie Ihre App entwickeln, können Sie [1-creating-a-basic-app](https://github.com/webforj/webforj-tutorial/tree/main/1-creating-a-basic-app) als Vergleich verwenden. Um die App in Aktion zu sehen:

1. Navigieren Sie zum übergeordneten Verzeichnis, das die Datei `pom.xml` enthält, dies ist `1-creating-a-basic-app`, wenn Sie der Version auf GitHub folgen.

2. Verwenden Sie den folgenden Maven-Befehl, um die Spring Boot-App lokal auszuführen:
    ```bash
    mvn
    ```

Das Ausführen der App öffnet automatisch einen neuen Browser unter `http://localhost:8080`.

## Der Einstiegspunkt {#entry-point}

Jede webforJ-App enthält eine einzelne Klasse, die <JavadocLink type="foundation" location="com/webforj/App" code='true'>App</JavadocLink> erweitert. Für dieses Tutorial und andere veröffentlichte webforJ-Projekte wird sie häufig `Application` genannt. Diese Klasse befindet sich in einem Paket, das nach der `groupId` benannt ist, die Sie im [Projektsetup](/docs/introduction/tutorial/project-setup) verwendet haben:

```
1-creating-a-basic-app 
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

Innerhalb der `Application`-Klasse verwendet die Methode `SpringApplication.run()` die Konfigurationen zum Starten der App. Die verschiedenen Annotationen sind für die Konfigurationen der App.

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

Die [`@SpringBootApplication`](https://docs.spring.io/spring-boot/api/java/org/springframework/boot/autoconfigure/SpringBootApplication.html) ist eine Kernannotation in Spring Boot. Sie setzen diese Annotation auf die Hauptklasse, um sie als Einstiegspunkt Ihrer App zu kennzeichnen.

`@StyleSheet`, `@AppTheme` und `@AppProfile` sind nur einige der vielen <JavadocLink type="foundation" location="com/webforj/annotation/package-summary">webforJ-Annotationen</JavadocLink>, die Ihnen zur Verfügung stehen, wenn Sie explizit Konfigurationen festlegen möchten.

- **`@StyleSheet`** bettet eine CSS-Datei in die Webseite ein. Weitere Informationen darüber, wie Sie mit einer bestimmten CSS-Datei interagieren können, finden Sie später in [Styling mit CSS](#styling-with-css).

- **`@AppTheme`** verwaltet das visuelle Thema der App. Wenn es auf `system` gesetzt ist, passt sich die App automatisch dem bevorzugten Thema des Benutzers an: `light`, `dark` oder `dark-pure`. Informationen zur Erstellung benutzerdefinierter Themen oder zum Überschreiben der Standardthemen finden Sie im Artikel [Themen](/docs/styling/themes).

- **`@AppProfile`** hilft, wie die App dem Benutzer als [installierbare App](/docs/configuration/installable-apps) präsentiert wird. Mindestens benötigt diese Annotation einen `name` für den vollständigen Namen der App und einen `shortName` für den Einsatz bei begrenztem Platz. Der `shortName` sollte nicht länger als 12 Zeichen sein.  

## Eine Benutzeroberfläche erstellen {#creating-a-ui}

Um Ihre Benutzeroberfläche zu erstellen, müssen Sie [HTML-Elementkomponenten](/docs/components/html-elements) und [webforJ-Komponenten](/docs/components/overview) hinzufügen. Für den Anfang haben Sie nur eine Single-Page-App, also fügen Sie Komponenten direkt in die `Application`-Klasse ein. 
Dazu überschreiben Sie die Methode `App.run()` und erstellen ein `Frame`, um Komponenten hinzuzufügen. 

```java
@Override
public void run() throws WebforjException {
  Frame mainFrame = new Frame();

  // UI-Komponenten erstellen und zum Frame hinzufügen

}
```

### Verwendung von HTML-Elementen {#using-html-elements}

Sie können Standard-HTML-Elemente mit [HTML-Elementkomponenten](/docs/components/html-elements) zu Ihrer App hinzufügen.
Erstellen Sie eine neue Instanz der Komponente und verwenden Sie die Methode `add()`, um sie zum `Frame` hinzuzufügen:

```java
// Container für die UI-Elemente erstellen
Frame mainFrame = new Frame();

// HTML-Komponente erstellen
Paragraph tutorial = new Paragraph("Tutorial-Anwendung!");

// Die Komponente zum Container hinzufügen
mainFrame.add(tutorial);
```

### Verwendung von webforJ-Komponenten {#webforj-components-and-html-elements}

Während HTML-Elemente nützlich für Struktur, Semantik und leichte UI-Bedürfnisse sind, bieten [webforJ-Komponenten](/docs/components/overview) komplexeres und dynamisches Verhalten.

Der folgende Code fügt eine [Schaltfläche](/docs/components/button)-Komponente hinzu, ändert ihr Aussehen mit der Methode `setTheme()` und fügt einen Ereignis-Listener hinzu, um eine [Nachrichtendialog](/docs/components/option-dialogs/message)-Komponente zu erstellen, wenn die Schaltfläche geklickt wird. Die meisten Methoden von webforJ-Komponenten, die eine Komponente ändern, geben die Komponente selbst zurück, sodass Sie mehrere Methoden für kompakteren Code aneinanderreihen können.

```java
// Container für die UI-Elemente erstellen
Frame mainFrame = new Frame();

// webforJ-Komponente erstellen
Button btn = new Button("Info");

// Die webforJ-Komponente ändern und einen Ereignis-Listener hinzufügen
btn.setTheme(ButtonTheme.PRIMARY)
  .addClickListener(e -> OptionDialog.showMessageDialog("Dies ist ein Tutorial!", "Info"));

// Die Komponente zum Container hinzufügen
mainFrame.add(btn);
```

## Styling mit CSS {#styling-with-css}

Die meisten webforJ-Komponenten verfügen über integrierte Methoden, um gängige Stiländerungen vorzunehmen, z. B. Größe und Thema.

```java
// Setzen Sie die Breite des Frames mit einem CSS-Schlüsselwort
mainFrame.setWidth("fit-content");

// Setzen Sie die maximale Breite der Schaltfläche in Pixel
btn.setMaxWidth(200);

// Setzen Sie das Schaltflächenthema auf PRIMARY
btn.setTheme(ButtonTheme.PRIMARY);
```

Neben diesen Methoden können Sie Ihre App mit CSS gestalten. Der Abschnitt **Styling** auf der Dokumentationsseite jeder Komponente enthält spezifische Details zu den relevanten CSS-Eigenschaften.

webforJ wird auch mit einem Satz von gestalteten CSS-Variablen geliefert, die DWC-Token genannt werden. Siehe die [Styling](/docs/styling/overview)-Dokumentation für detaillierte Informationen darüber, wie Sie webforJ-Komponenten gestalten und wie Sie die Token verwenden.

### Referenzierung einer CSS-Datei {#referencing-a-css-file} 

Es ist am besten, eine separate CSS-Datei zu haben, um alles organisiert und wartbar zu halten. Erstellen Sie eine Datei mit dem Namen `card.css` im Verzeichnis `src/main/resources/static/css` mit der folgenden CSS-Klassendefinition:

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

Referenzieren Sie dann die Datei in `Application.java`, indem Sie die Annotation `@StyleSheet` mit dem Namen der CSS-Datei verwenden. Für diesen Schritt lautet es `@StyleSheet("ws://css/card.css")`.

:::tip Webserverprotokoll
Dieses Tutorial verwendet das Webserverprotokoll, um auf die CSS-Datei zuzugreifen. Um mehr darüber zu erfahren, wie das funktioniert, siehe [Ressourcen verwalten](/docs/managing-resources/overview).
:::

### Hinzufügen von CSS-Klassen zu Komponenten {#adding-css-classes-to-components}

Sie können dynamisch Klassennamen zu Komponenten mit den Methoden `addClassName()` und `removeClassName()` hinzufügen oder entfernen. Für dieses Tutorial wird nur eine CSS-Klasse verwendet:

```java
mainFrame.addClassName("card");
```

## Abgeschlossene `Application` {#completed-application}

Ihre `Application`-Klasse sollte nun ähnlich wie folgt aussehen:

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
    Paragraph tutorial = new Paragraph("Tutorial-App!");
    Button btn = new Button("Info");

    btn.setTheme(ButtonTheme.PRIMARY)
        .setMaxWidth(200)
        .addClickListener(e -> OptionDialog.showMessageDialog("Dies ist ein Tutorial!", "Info"));

    mainFrame.setWidth("fit-content")
        .addClassName("card")
        .add(tutorial, btn);
  }

}
```

:::tip Mehrere Seiten
Für eine komplexere App können Sie die Benutzeroberfläche in mehrere Seiten unterteilen, um eine bessere Organisation zu gewährleisten. Dieses Konzept wird später in diesem Tutorial in [Routing und Komposits](/docs/introduction/tutorial/routing-and-composites) behandelt.
:::

## Nächster Schritt {#next-step}

Nachdem Sie eine funktionale App mit einer grundlegenden Benutzeroberfläche erstellt haben, ist der nächste Schritt, ein Datenmodell hinzuzufügen und die Ergebnisse in einer `Table`-Komponente in [Arbeiten mit Daten](/docs/introduction/tutorial/working-with-data) anzuzeigen.
