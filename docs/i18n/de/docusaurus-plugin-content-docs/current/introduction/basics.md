---
title: App Grundlagen
sidebar_position: 3
_i18n_hash: ad73702df52f27ebff7e226bb75e3a6a
---
Sobald webforJ und seine Abhängigkeiten in Ihrem Projekt eingerichtet sind, sind Sie bereit, die Anwendungsstruktur zu erstellen. Dieser Artikel behandelt die wichtigsten Elemente einer grundlegenden webforJ-App, insbesondere die Klassen `Application` und `HomeView`, die die grundlegenden Klassen im Starterprojekt `webforj-archetype-hello-world` sind.

## Hauptanwendungsklasse: `Application.java` {#main-app-class-applicationjava}

Die Klasse `Application` dient als Einstiegspunkt für Ihre webforJ-App und richtet wesentliche Konfigurationen und Routen ein. Zu Beginn beachten Sie die Deklaration der Klasse und die Annotationen.

Diese Klasse erweitert die Kernklasse `App` von webforJ, wodurch sie als webforJ-App erkennbar wird. Verschiedene Annotationen konfigurieren das Thema, den Titel und die Routen der App.

```java
@Routify(packages = "com.samples.views")
@AppTitle("webforJ Hello World")
@StyleSheet("ws://app.css")
public class Application extends App {
}
```

- `@Routify`: Gibt an, dass webforJ das Paket `com.samples.views` nach Routenkomponenten durchsuchen soll.
- `@AppTitle`: Definiert den Titel, der in der Registerkarte des Browsers der App angezeigt wird.
- `@StyleSheet`: Verknüpft eine externe CSS-Datei, `app.css`, die benutzerdefinierte Stile für die App ermöglicht.

Die Klasse `Application` enthält keine zusätzlichen Methoden, da die Konfigurationen über Annotationen festgelegt werden und webforJ die Initialisierung der App übernimmt.

Mit `Application.java` eingerichtet, ist die App nun mit einem Titel und Routen versehen, die auf das Ansichts-Paket zeigen. Als Nächstes gibt eine Übersicht über die Klasse `HomeView` Einblick in das, was angezeigt wird, wenn die App ausgeführt wird.

### Entdecken einer `App` {#discovering-an-app}

Ein einzelnes <JavadocLink type="foundation" location="com/webforj/App" code='true'>App</JavadocLink> Limit wird in webforJ durchgesetzt, was alle Fehlerbehandlungsaufgaben auf die Java-Seite verlagert und den Entwicklern die volle Kontrolle über das Fehler-Management gibt.

Während des Bootstrapping-Prozesses von webforJ werden alle Klassen, die die Klasse <JavadocLink type="foundation" location="com/webforj/App" code='true'>com.webforj.App</JavadocLink> erweitern, durchsucht. Wenn mehrere Apps gefunden werden, sucht das System nach der Annotation <JavadocLink type="foundation" location="com/webforj/annotation/AppEntry" code='true'>com.webforj.annotation.AppEntry</JavadocLink>. Wenn eine der entdeckten Klassen mit <JavadocLink type="foundation" location="com/webforj/annotation/AppEntry" code='true' >@AppEntry</JavadocLink> annotiert ist, wird die zuerst gefundene als Einstiegspunkt angesehen.

- Wenn eine Klasse mit `@AppEntry` annotiert ist, wird diese Klasse als Einstiegspunkt ausgewählt.
- Wenn mehrere Klassen mit `@AppEntry` annotiert sind, wird eine Ausnahme ausgelöst, die alle entdeckten Klassen auflistet.
- Wenn keine Klasse annotiert ist und nur eine Unterklasse von `App` gefunden wird, wird diese Klasse als Einstiegspunkt ausgewählt.
- Wenn keine Klasse annotiert ist und mehrere Unterklassen von `App` gefunden werden, wird eine Ausnahme ausgelöst, die jede Unterklasse detailliert beschreibt.

:::tip Fehlerbehandlung
Für weitere Informationen darüber, wie Fehler in webforJ behandelt werden, siehe [diesen Artikel](../advanced/error-handling).
:::

## Hauptansichtsklasse: `HomeView.java` {#main-view-class-homeviewjava}

Die Klasse `HomeView` definiert eine einfache Ansichtskomponente, die als Homepage der App dient. Sie zeigt ein Feld und einen Button an, um den eingegebenen Namen des Benutzers zu begrüßen.

### Klassendeklaration und Annotationen {#class-declaration-and-annotations}

`HomeView` erweitert `Composite<FlexLayout>`, wodurch sie als wiederverwendbare Komponente fungieren kann, die aus einer [`FlexLayout`](../components/flex-layout) Komponente besteht. Die [`@Route("/")`](../routing/overview) macht dies zur Stammroute der App.

```java
@Route("/")
public class HelloWorldView extends Composite<FlexLayout> {

  private FlexLayout self = getBoundComponent();
  private TextField hello = new TextField("Wie heißt du?");
  private Button btn = new Button("Sag Hallo");

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
private FlexLayout self = getBoundComponent();
private TextField hello = new TextField("Wie heißt du?");
private Button btn = new Button("Sag Hallo");
```

- `self`: Die Hauptlayoutkomponente, die [`FlexLayout`](../components/flex-layout) verwendet und als Container für die Elemente konfiguriert ist. Dieses Element verwendet die Methode `getBoundComponent()`, um das Haupt-`FlexLayout`, das die Klasse enthält, zu speichern.
- `hello`: Ein [`TextField`](../components/fields/textfield) mit dem Label `Wie heißt du?`, in das Benutzer ihren Namen eingeben können.
- `btn`: Ein primär gestalteter [`Button`](../components/button) mit dem Label `Sag Hallo`.

### Layoutkonfiguration {#layout-configuration}

Das Layout `(self)` wird mit einigen wichtigen Stil-Eigenschaften konfiguriert:

- `FlexDirection.COLUMN` stapelt die Elemente vertikal.
- `setMaxWidth(300)` beschränkt die Breite auf 300 Pixel für ein kompaktes Layout.
- `setStyle("margin", "1em auto")` zentriert das Layout mit einem Rand darum.

### Komponenten zum Layout hinzufügen {#adding-components-to-the-layout}
Schließlich werden das Textfeld hello und der Button btn zum [`FlexLayout`](../components/flex-layout) Container hinzugefügt, indem `self.add(hello, btn)` aufgerufen wird. Diese Anordnung definiert die Struktur der Ansicht und macht das Formular sowohl interaktiv als auch visuell zentriert.

## Styling der App {#styling-the-app}

Die Datei `styles.css` bietet benutzerdefiniertes Styling für Ihre webforJ-App. Diese CSS-Datei wird in der Klasse Application unter Verwendung der [`@StyleSheet`](../managing-resources/importing-assets#importing-css-files) Annotation referenziert, die es der App ermöglicht, Stile auf die Komponenten innerhalb der App anzuwenden.

Diese Datei befindet sich im Verzeichnis `resources/static` des Projekts und kann unter Verwendung der URL des Webservers `ws://app.css` referenziert werden.
