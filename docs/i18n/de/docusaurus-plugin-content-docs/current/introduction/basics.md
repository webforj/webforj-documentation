---
title: App Basics
sidebar_position: 3
_i18n_hash: eb456b4bb94bf617f33f8aa8425ad97f
---
Nachdem webforJ und seine Abhängigkeiten in Ihrem Projekt eingerichtet sind, sind Sie bereit, die Anwendungsstruktur zu erstellen. Dieser Artikel wird die Schlüsselelemente einer grundlegenden webforJ-App durchgehen, wobei der Schwerpunkt auf den Klassen `Application` und `HomeView` liegt, die die grundlegenden Klassen im Starterprojekt `webforj-archetype-hello-world` sind.

## Haupt-App-Klasse: `Application.java` {#main-app-class-applicationjava}

Die Klasse `Application` dient als Einstiegspunkt für Ihre webforJ-App und richtet wesentliche Konfigurationen und Routen ein. Zuerst beachten Sie die Deklaration und die Annotationen der Klasse.

Diese Klasse erweitert die Kernklasse `App` von webforJ, wodurch sie als webforJ-App erkannt wird. Verschiedene Annotationen konfigurieren das Thema, den Titel und die Routen der App.

```java
@Routify(packages = "com.samples.views")
@AppTitle("webforJ Hello World")
@StyleSheet("ws://app.css")
public class Application extends App {
}
```

- `@Routify`: Gibt an, dass webforJ das Paket `com.samples.views` nach Routenkomponenten durchsuchen soll.
- `@AppTitle`: Definiert den Titel, der auf dem Browser-Tab der App angezeigt wird.
- `@StyleSheet`: Verlinkt eine externe CSS-Datei, `app.css`, und ermöglicht benutzerdefinierte Stile für die App.

Die Klasse `Application` enthält keine zusätzlichen Methoden, da die Konfigurationen über Annotationen festgelegt werden und webforJ die Initialisierung der App übernimmt.

Mit der Einrichtung von `Application.java` ist die App nun mit einem Titel und Routen ausgestattet, die auf das Ansichtenpaket verweisen. Als Nächstes gibt eine Übersicht über die Klasse `HomeView` Einblicke, was angezeigt wird, wenn die App ausgeführt wird.

### Entdecken einer `App` {#discovering-an-app}

Eine einzige <JavadocLink type="foundation" location="com/webforj/App" code='true'>App</JavadocLink> -Grenze wird in webforJ durchgesetzt, die alle Fehlerbehandlungsaufgaben auf die Java-Seite verschiebt und Entwicklern die volle Kontrolle über das Fehlermanagement gibt.

Während des Bootstrapping-Prozesses von webforJ werden alle Klassen, die von <JavadocLink type="foundation" location="com/webforj/App" code='true'>com.webforj.App</JavadocLink> abgeleitet sind, durchsucht. Wenn mehrere Apps gefunden werden, sucht das System nach der Annotation <JavadocLink type="foundation" location="com/webforj/annotation/AppEntry" code='true'>com.webforj.annotation.AppEntry</JavadocLink>. Wenn eine der entdeckten Klassen mit <JavadocLink type="foundation" location="com/webforj/annotation/AppEntry" code='true' >@AppEntry</JavadocLink> annotiert ist, wird die erste gefundene als Einstiegspunkt betrachtet.

- Wenn eine Klasse mit `@AppEntry` annotiert ist, wird diese Klasse als Einstiegspunkt ausgewählt.
- Wenn mehrere Klassen mit `@AppEntry` annotiert sind, wird eine Ausnahme ausgelöst, die alle entdeckten Klassen auflistet.
- Wenn keine Klasse annotiert ist und nur eine Unterklasse von `App` gefunden wird, wird diese Klasse als Einstiegspunkt ausgewählt.
- Wenn keine Klasse annotiert ist und mehrere Unterklassen von `App` gefunden werden, wird eine Ausnahme ausgelöst, die jede Unterklasse im Detail beschreibt.

:::tip Fehlerbehandlung
Für weitere Informationen zur Fehlerbehandlung in webforJ siehe [diesen Artikel](../advanced/error-handling).
:::

## Hauptansichtsklasse: `HomeView.java` {#main-view-class-homeviewjava}

Die Klasse `HomeView` definiert eine einfache Ansichtskomponente, die als Startseite der App dient. Sie zeigt ein Feld und einen Button an, um den Namen des Benutzers zu begrüßen.

### Klassendeklaration und Annotationen {#class-declaration-and-annotations}

`HomeView` erweitert `Composite<FlexLayout>`, was es ihm ermöglicht, als wiederverwendbare Komponente zu fungieren, die aus einer [`FlexLayout`](../components/flex-layout)-Komponente besteht. Die [`@Route("/")`](../routing/overview) macht dies zur Stammroute der App.

```java
@Route("/")
public class HelloWorldView extends Composite<FlexLayout> {

  private FlexLayout self = getBoundComponent();
  private TextField hello = new TextField("Wie heißen Sie?");
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

### Komponenteninitialisierung {#component-initialization}

Innerhalb der Klasse werden mehrere UI-Elemente initialisiert und deklariert:

```java
private FlexLayout self = getBoundComponent();
private TextField hello = new TextField("Wie heißen Sie?");
private Button btn = new Button("Hallo sagen");
```

- `self`: Die Hauptlayoutkomponente, die [`FlexLayout`](../components/flex-layout) verwendet und als Container für die Elemente konfiguriert ist. Dieses Element verwendet die Methode `getBoundComponent()`, um das Haupt-`FlexLayout`, das die Klasse enthält, zu speichern.
- `hello`: Ein [`TextField`](../components/fields/textfield) mit der Bezeichnung `Wie heißen Sie?`, in das die Benutzer ihren Namen eingeben können.
- `btn`: Ein primär gestalteter [`Button`](../components/button) mit der Bezeichnung `Hallo sagen`.

### Layoutkonfiguration {#layout-configuration}

Das Layout `(self)` wird mit einigen wichtigen Stil-Eigenschaften konfiguriert:

- `FlexDirection.COLUMN` stapelt die Elemente vertikal.
- `setMaxWidth(300)` beschränkt die Breite auf 300 Pixel für ein kompaktes Layout.
- `setStyle("margin", "1em auto")` zentriert das Layout mit einem Rand darum.

### Hinzufügen von Komponenten zum Layout {#adding-components-to-the-layout}

Schließlich werden das Hallo-Textfeld und der btn-Button zum [`FlexLayout`](../components/flex-layout)-Container hinzugefügt, indem `self.add(hello, btn)` aufgerufen wird. Diese Anordnung definiert die Struktur der Ansicht und macht das Formular sowohl interaktiv als auch visuell zentriert.

## Styling der App {#styling-the-app}

Die Datei `styles.css` bietet benutzerdefiniertes Styling für Ihre webforJ-App. Diese CSS-Datei wird in der Klasse Application mit der [`@StyleSheet`](../managing-resources/importing-assets#importing-css-files) Annotation referenziert, die es der App ermöglicht, Stile auf Komponenten innerhalb der App anzuwenden.

Diese Datei befindet sich im Verzeichnis `resources/static` des Projekts und kann über die URL des Webbrowsers `ws://app.css` referenziert werden.
