---
title: App Basics
sidebar_position: 3
_i18n_hash: e4eae914f0cbd5c9e5eacb6e681570a9
---
Sobald webforJ und seine Abhängigkeiten in Ihrem Projekt eingerichtet sind, sind Sie bereit, die Anwendungsstruktur zu erstellen. Dieser Artikel beschreibt die wichtigsten Elemente einer grundlegenden webforJ-Anwendung, insbesondere die Klassen `Application` und `HomeView`, die die grundlegenden Klassen im Starterprojekt `webforj-archetype-hello-world` sind.

## Hauptanwendungsklasse: `Application.java` {#main-app-class-applicationjava}

Die Klasse `Application` dient als Einstiegspunkt für Ihre webforJ-Anwendung und richtet wesentliche Konfigurationen und Routen ein. Zunächst beachten Sie die Deklaration und Annotationen der Klasse.

Diese Klasse erweitert die Kernklasse `App` von webforJ, was sie als webforJ-Anwendung erkennbar macht. Verschiedene Annotationen konfigurieren das Thema, den Titel und die Routen der Anwendung.

```java
@Routify(packages = "com.samples.views")
@AppTitle("webforJ Hello World")
@StyleSheet("ws://app.css")
public class Application extends App {
}
```

- `@Routify`: Gibt an, dass webforJ das Paket `com.samples.views` nach Routenkomponenten durchsuchen soll.
- `@AppTitle`: Definiert den Titel, der im Browser-Tab der Anwendung angezeigt wird.
- `@StyleSheet`: Verknüpft eine externe CSS-Datei, `app.css`, um benutzerdefiniertes Styling für die Anwendung zu ermöglichen.

Die Klasse `Application` enthält keine zusätzlichen Methoden, da die Konfigurationen über Annotationen festgelegt sind und webforJ die Initialisierung der Anwendung übernimmt.

Mit eingerichtetem `Application.java` ist die Anwendung nun mit einem Titel und Routen konfiguriert, die auf das Ansichtenpaket zeigen. Als Nächstes gibt eine Übersicht über die Klasse `HomeView` Einblick, was angezeigt wird, wenn die Anwendung ausgeführt wird.

### Entdeckung einer `App` {#discovering-an-app}

Eine einzelne <JavadocLink type="foundation" location="com/webforj/App" code='true'>App</JavadocLink> Grenze wird in webforJ durchgesetzt, die alle Fehlermanagementaufgaben auf die Java-Seite verschiebt und Entwicklern die volle Kontrolle über das Fehlermanagement gibt.

Während des Bootstrapping-Prozesses von webforJ werden alle Klassen, die von <JavadocLink type="foundation" location="com/webforj/App" code='true'>com.webforj.App</JavadocLink> erben, durchsucht. Wenn mehrere Anwendungen gefunden werden, sucht das System nach der <JavadocLink type="foundation" location="com/webforj/annotation/AppEntry" code='true'>com.webforj.annotation.AppEntry</JavadocLink> Annotation. Wenn eine der entdeckten Klassen mit <JavadocLink type="foundation" location="com/webforj/annotation/AppEntry" code='true'>@AppEntry</JavadocLink> annotiert ist, wird die erste gefundene als Einstiegspunkt betrachtet.

- Wenn eine Klasse mit `@AppEntry` annotiert ist, wird diese Klasse als Einstiegspunkt ausgewählt.
- Wenn mehrere Klassen mit `@AppEntry` annotiert sind, wird eine Ausnahme ausgelöst, die alle entdeckten Klassen auflistet.
- Wenn keine Klasse annotiert ist und nur eine Unterklasse von `App` gefunden wird, wird diese Klasse als Einstiegspunkt ausgewählt.
- Wenn keine Klasse annotiert ist und mehrere Unterklassen von `App` gefunden werden, wird eine Ausnahme ausgelöst, die jede Unterklasse detailliert angibt.

:::tip Fehlerbehandlung
Für weitere Informationen zur Fehlerbehandlung in webforJ siehe [diesen Artikel](../advanced/error-handling).
:::

## Hauptansichtsklasse: `HomeView.java` {#main-view-class-homeviewjava}

Die Klasse `HomeView` definiert eine einfache Ansichtskomponente, die als Startseite für die Anwendung dient. Sie zeigt ein Feld und einen Button an, um den eingegebenen Namen des Benutzers zu begrüßen.

### Klassendeklaration und Annotationen {#class-declaration-and-annotations}

`HomeView` erweitert `Composite<FlexLayout>`, was es ihr ermöglicht, als wiederverwendbare Komponente zu agieren, die aus einer [`FlexLayout`](../components/flex-layout) Komponente besteht. Die [`@Route("/")`](../routing/overview) macht dies zur Stammroute der Anwendung.

```java
@Route("/")
public class HelloWorldView extends Composite<FlexLayout> {

  private final FlexLayout self = getBoundComponent();
  private TextField hello = new TextField("Wie heißt du?");
  private Button btn = new Button("Hallo sagen");

  public HelloWorldView(){
    self.setDirection(FlexDirection.COLUMN);
    self.setMaxWidth(300);
    self.setStyle("margin", "1em auto");

    btn.setTheme(ButtonTheme.PRIMARY)
        .addClickListener(e -> 
          Toast.show("Willkommen im webforJ Starter " + hello.getValue() + "!", Theme.GRAY));

    self.add(hello, btn);
  }
}
```

### Komponenteninitalisierung {#component-initialization}

Innerhalb der Klasse werden mehrere UI-Elemente initialisiert und deklariert:

```java
private final FlexLayout self = getBoundComponent();
private TextField hello = new TextField("Wie heißt du?");
private Button btn = new Button("Hallo sagen");
```

- `self`: Die Hauptlayoutkomponente, die [`FlexLayout`](../components/flex-layout) verwendet, und als Container für die Elemente konfiguriert ist. Dieses Element verwendet die Methode `getBoundComponent()`, um das Haupt-`FlexLayout` zu speichern, das die Klasse enthält.
- `hello`: Ein [`TextField`](../components/fields/textfield) mit der Bezeichnung `Wie heißt du?`, in das die Benutzer ihren Namen eingeben können.
- `btn`: Ein primär gestalteter [`Button`](../components/button) mit der Bezeichnung `Hallo sagen`.

### Layoutkonfiguration {#layout-configuration}

Das Layout `(self)` wird mit einigen wichtigen Stil-Eigenschaften konfiguriert:

- `FlexDirection.COLUMN` stapelt die Elemente vertikal.
- `setMaxWidth(300)` beschränkt die Breite auf 300 Pixel für ein kompaktes Layout.
- `setStyle("margin", "1em auto")` zentriert das Layout mit einem Rand darum herum.

### Hinzufügen von Komponenten zum Layout {#adding-components-to-the-layout}
Schließlich werden das Nameingabefeld und der Button `btn` zum [`FlexLayout`](../components/flex-layout) Container hinzugefügt, indem `self.add(hello, btn)` aufgerufen wird. Diese Anordnung definiert die Struktur der Ansicht und macht das Formular sowohl interaktiv als auch visuell zentriert.

## Styling der Anwendung {#styling-the-app}

Die Datei `styles.css` bietet benutzerdefiniertes Styling für Ihre webforJ-Anwendung. Diese CSS-Datei wird in der Klassenanwendung mit der [`@StyleSheet`](../managing-resources/importing-assets#importing-css-files) Annotation referenziert, die es der Anwendung ermöglicht, Stile auf Komponenten innerhalb der Anwendung anzuwenden.

Diese Datei befindet sich im Verzeichnis `resources/static` des Projekts und kann über die URL des Webservers `ws://app.css` referenziert werden.
