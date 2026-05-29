---
title: App Basics
sidebar_position: 3
_i18n_hash: 23f93367391ac7cd42c28bf4cd3640ee
---
Sobald webforJ und seine Abhängigkeiten in Ihrem Projekt eingerichtet sind, sind Sie bereit, die App-Struktur zu erstellen. Dieser Artikel beschreibt die Schlüsselelemente einer grundlegenden webforJ-App, wobei insbesondere die Klassen `Application` und `HomeView` im Fokus stehen, die die grundlegenden Klassen im Starterprojekt `webforj-archetype-hello-world` sind.

## Haupt-App-Klasse: `Application.java` {#main-app-class-applicationjava}

Die Klasse `Application` dient als Einstiegspunkt für Ihre webforJ-App, indem sie wesentliche Konfigurationen und Routen einrichtet. Zunächst fällt die Deklaration der Klasse und die Annotationen ins Auge. 

Diese Klasse erweitert die Kernklasse `App` von webforJ, wodurch sie als webforJ-App erkennbar wird. Verschiedene Annotationen konfigurieren das Design, den Titel und die Routen der App.

```java
@Routify(packages = "com.samples.views")
@AppTitle("webforJ Hello World")
@StyleSheet("ws://app.css")
public class Application extends App {
}
```

- `@Routify`: Gibt an, dass webforJ das Paket `com.samples.views` nach Routenkomponenten scannen soll.
- `@AppTitle`: Definiert den Titel, der auf dem Browser-Tab der App angezeigt wird.
- `@StyleSheet`: Verknüpft eine externe CSS-Datei, `app.css`, die benutzerdefinierte Stile für die App ermöglicht.

Die Klasse `Application` enthält keine zusätzlichen Methoden, da die Konfigurationen durch Annotationen festgelegt werden und webforJ die Initialisierung der App übernimmt.

Mit `Application.java`, ist die App nun mit einem Titel und Routen konfiguriert, die auf das Ansichts-Paket zeigen. Als nächstes gibt eine Übersicht über die Klasse `HomeView` Einblick in das, was angezeigt wird, wenn die App ausgeführt wird.

### Entdecken einer `App` {#discovering-an-app}

Eine einzelne <JavadocLink type="foundation" location="com/webforj/App" code='true'>App</JavadocLink> Grenze wird in webforJ durchgesetzt, die alle Fehlerbehandlungsverantwortlichkeiten auf die Java-Seite verlagert und Entwicklern die volle Kontrolle über das Fehler-Management gibt.

Während des Bootstrap-Prozesses von webforJ werden alle Klassen, die von <JavadocLink type="foundation" location="com/webforj/App" code='true'>com.webforj.App</JavadocLink> abgeleitet sind, gescannt. Wenn mehrere Apps gefunden werden, sucht das System nach der <JavadocLink type="foundation" location="com/webforj/annotation/AppEntry" code='true'>com.webforj.annotation.AppEntry</JavadocLink> Annotation. Wenn eine der entdeckten Klassen mit <JavadocLink type="foundation" location="com/webforj/annotation/AppEntry" code='true'>@AppEntry</JavadocLink> annotiert ist, wird die erste gefundene als Einstiegspunkt angesehen.

- Wenn eine Klasse mit `@AppEntry` annotiert ist, wird diese Klasse als Einstiegspunkt ausgewählt.
- Wenn mehrere Klassen mit `@AppEntry` annotiert sind, wird eine Ausnahme ausgelöst, die alle entdeckten Klassen auflistet.
- Wenn keine Klasse annotiert ist und nur eine Unterklasse von `App` gefunden wird, wird diese Klasse als Einstiegspunkt ausgewählt.
- Wenn keine Klasse annotiert ist und mehrere Unterklassen von `App` gefunden werden, wird eine Ausnahme ausgelöst, in der jede Unterklasse aufgeführt wird.

:::tip Fehlerbehandlung
Für weitere Informationen zur Fehlerbehandlung in webforJ siehe [diesen Artikel](../advanced/error-handling).
:::

## Hauptansichtsklasse: `HomeView.java` {#main-view-class-homeviewjava}

Die Klasse `HomeView` definiert eine einfache Ansichtskomponente, die als Startseite für die App dient. Sie zeigt ein Eingabefeld und einen Button an, um den eingegebenen Namen des Benutzers zu begrüßen.

### Klassendeklaration und Annotationen {#class-declaration-and-annotations}

`HomeView` erweitert `Composite<FlexLayout>`, was es ihm ermöglicht, als wiederverwendbare Komponente aus einer [`FlexLayout`](../components/flex-layout) Komponente zu fungieren. Die [`@Route("/")`](../routing/overview) macht dies zur Hauptroute der App.

```java
@Route("/")
public class HelloWorldView extends Composite<FlexLayout> {

  private final FlexLayout self = getBoundComponent();
  private TextField hello = new TextField("Was ist Ihr Name?");
  private Button btn = new Button("Hallo sagen");

  public HelloWorldView(){
    self.setDirection(FlexDirection.COLUMN);
    self.setMaxWidth(300);
    self.setStyle("margin", "1em auto");

    btn.setTheme(ButtonTheme.PRIMARY)
        .addClickListener(e -> 
          Toast.show("Willkommen bei webforJ Starter " + hello.getValue() + "!", Theme.GRAY));

    self.add(hello, btn);
  }
}
```

### Komponenteninitialisierung {#component-initialization}

Innerhalb der Klasse werden mehrere UI-Elemente initialisiert und deklariert:

```java
private final FlexLayout self = getBoundComponent();
private TextField hello = new TextField("Was ist Ihr Name?");
private Button btn = new Button("Hallo sagen");
```

- `self`: Die Hauptlayoutkomponente, die verwendet wird, um ein [`FlexLayout`](../components/flex-layout) zu erstellen, konfiguriert als Container für die Elemente. Dieses Element verwendet die Methode `getBoundComponent()`, um das Haupt-`FlexLayout` zu speichern, das die Klasse enthält.
- `hello`: Ein [`TextField`](../components/fields/textfield) mit der Beschriftung `Was ist Ihr Name?`, damit Benutzer ihren Namen eingeben können.
- `btn`: Ein primär gestalteter [`Button`](../components/button) mit der Beschriftung `Hallo sagen`.

### Layout-Konfiguration {#layout-configuration}

Das Layout (self) wird mit einigen wichtigen Stil-Eigenschaften konfiguriert:

- `FlexDirection.COLUMN` stapelt die Elemente vertikal.
- `setMaxWidth(300)` begrenzt die Breite auf 300 Pixel für ein kompaktes Layout.
- `setStyle("margin", "1em auto")` zentriert das Layout mit einem Rand darum.

### Komponenten zum Layout hinzufügen {#adding-components-to-the-layout}
Schließlich werden das hello Eingabefeld und der btn Button in den [`FlexLayout`](../components/flex-layout) Container hinzugefügt, indem `self.add(hello, btn)` aufgerufen wird. Diese Anordnung definiert die Struktur der Ansicht und macht das Formular sowohl interaktiv als auch visuell zentriert.

## Styling der App {#styling-the-app}

Die Datei `styles.css` bietet benutzerdefiniertes Styling für Ihre webforJ-App. Diese CSS-Datei wird in der Application-Klasse mithilfe der [`@StyleSheet`](../managing-resources/importing-assets#importing-css-files) Annotation referenziert, die es der App ermöglicht, Stile auf Komponenten innerhalb der App anzuwenden.

Diese Datei befindet sich im Verzeichnis `resources/static` des Projekts und kann über die URL des Webservers `ws://app.css` referenziert werden.
