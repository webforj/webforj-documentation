---
title: App Basics
description: >-
  Walk through the Application and HomeView classes of the hello-world archetype
  to see how routing, annotations, and views shape a webforJ app.
sidebar_position: 3
_i18n_hash: 2ebddfe300802013e4376681bc2ccf04
---
Sobald webforJ und seine Abhängigkeiten in Ihrem Projekt eingerichtet sind, sind Sie bereit, die App-Struktur zu erstellen. Dieser Artikel beschreibt die Schlüsselaspekte einer grundlegenden webforJ-App und konzentriert sich insbesondere auf die Klassen `Application` und `HomeView`, die die grundlegenden Klassen im Starterprojekt `webforj-archetype-hello-world` sind.

## Haupt-App-Klasse: `Application.java` {#main-app-class-applicationjava}

Die Klasse `Application` dient als Einstiegspunkt für Ihre webforJ-App und richtet die erforderlichen Konfigurationen und Routen ein. Zunächst beachten Sie die Deklaration und Anmerkungen der Klasse.

Diese Klasse erweitert die Kernklasse `App` von webforJ, wodurch sie als webforJ-App erkennbar ist. Verschiedene Anmerkungen konfigurieren das Theme, den Titel und das Routing der App.

```java
@Routify(packages = "com.samples.views")
@AppTitle("webforJ Hello World")
@StyleSheet("ws://app.css")
public class Application extends App {
}
```

- `@Routify`: Gibt an, dass webforJ das Paket `com.samples.views` nach Routenkomponenten durchsuchen soll.
- `@AppTitle`: Definiert den Titel, der auf dem Browser-Tab der App angezeigt wird.
- `@StyleSheet`: Verknüpft eine externe CSS-Datei, `app.css`, die individuelles Styling für die App ermöglicht.

Die Klasse `Application` enthält keine zusätzlichen Methoden, da die Konfigurationen über Anmerkungen festgelegt werden und webforJ die Initialisierung der App übernimmt.

Mit der Einrichtung von `Application.java` ist die App nun mit einem Titel und Routen, die auf das Ansichts-Paket zeigen, konfiguriert. Als Nächstes gibt eine Übersicht über die Klasse `HomeView` Einblick, was angezeigt wird, wenn die App ausgeführt wird.

### Entdecken einer `App` {#discovering-an-app}

Es gilt eine Beschränkung auf eine einzige <JavadocLink type="foundation" location="com/webforj/App" code='true'>App</JavadocLink> in webforJ, wodurch alle Fehlerbehandlungsverantwortlichkeiten auf die Java-Seite verschoben werden und Entwickler die vollständige Kontrolle über das Fehler-Management haben.

Während des Bootstrapping-Prozesses von webforJ werden alle Klassen gescannt, die <JavadocLink type="foundation" location="com/webforj/App" code='true'>com.webforj.App</JavadocLink> erweitern. Wenn mehrere Apps gefunden werden, sucht das System nach der <JavadocLink type="foundation" location="com/webforj/annotation/AppEntry" code='true'>com.webforj.annotation.AppEntry</JavadocLink>-Anmerkung. Wenn eine der gefundenen Klassen mit <JavadocLink type="foundation" location="com/webforj/annotation/AppEntry" code='true'>@AppEntry</JavadocLink> annotiert ist, wird die zuerst gefundene als Einstiegspunkt betrachtet.

- Wenn eine Klasse mit `@AppEntry` annotiert ist, wird diese Klasse als Einstiegspunkt ausgewählt.
- Wenn mehrere Klassen mit `@AppEntry` annotiert sind, wird eine Ausnahme ausgelöst, die alle gefundenen Klassen auflistet.
- Wenn keine Klasse annotiert ist und nur eine Unterklasse von `App` gefunden wird, wird diese Klasse als Einstiegspunkt ausgewählt.
- Wenn keine Klasse annotiert ist und mehrere Unterklassen von `App` gefunden werden, wird eine Ausnahme ausgelöst, die jede Unterklasse detailliert beschreibt.

:::tip Fehlerbehandlung
Für weitere Informationen zur Fehlerbehandlung in webforJ, siehe [diesen Artikel](../advanced/error-handling).
:::

## Hauptansichtsklasse: `HomeView.java` {#main-view-class-homeviewjava}

Die Klasse `HomeView` definiert eine einfache Ansichtskomponente, die als Startseite für die App dient. Sie zeigt ein Textfeld und einen Button an, um den eingegebenen Namen des Benutzers zu begrüßen.

### Klassendeklaration und Anmerkungen {#class-declaration-and-annotations}

`HomeView` erweitert `Composite<FlexLayout>`, was es ihm ermöglicht, als wiederverwendbare Komponente, die aus einer [`FlexLayout`](../components/flex-layout)-Komponente besteht, zu agieren. Die [`@Route("/")`](../routing/overview) macht dies zur Root-Route der App.

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

- `self`: Die Hauptlayout-Komponente, die die [`FlexLayout`](../components/flex-layout) verwendet, konfiguriert als Container für die Elemente. Dieses Element verwendet die Methode `getBoundComponent()`, um das Haupt-FlexLayout, das die Klasse enthält, zu speichern.
- `hello`: Ein [`TextField`](../components/fields/textfield), das mit `Was ist Ihr Name?` beschriftet ist, damit Benutzer ihren Namen eingeben können.
- `btn`: Ein primär gestalteter [`Button`](../components/button) mit der Beschriftung `Hallo sagen`.

### Layoutkonfiguration {#layout-configuration}

Das Layout (self) ist mit einigen wichtigen Stil-Eigenschaften konfiguriert:

- `FlexDirection.COLUMN` stapelt die Elemente vertikal.
- `setMaxWidth(300)` beschränkt die Breite auf 300 Pixel für ein kompaktes Layout.
- `setStyle("margin", "1em auto")` zentriert das Layout mit einem Rand darum.

### Hinzufügen von Komponenten zum Layout {#adding-components-to-the-layout}
Schließlich werden das Hallo-Textfeld und der btn-Button zum [`FlexLayout`](../components/flex-layout)-Container hinzugefügt, indem `self.add(hello, btn)` aufgerufen wird. Diese Anordnung definiert die Struktur der Ansicht und macht das Formular sowohl interaktiv als auch optisch zentriert.

## Styling der App {#styling-the-app}

Die Datei `styles.css` bietet individuelles Styling für Ihre webforJ-App. Diese CSS-Datei wird in der Klasse Application über die [`@StyleSheet`](../managing-resources/importing-assets#importing-css-files)-Anmerkung referenziert, was es der App ermöglicht, Stile auf Komponenten innerhalb der App anzuwenden.

Diese Datei befindet sich im Verzeichnis `resources/static` des Projekts und kann über die URL des Webservers `ws://app.css` referenziert werden.
