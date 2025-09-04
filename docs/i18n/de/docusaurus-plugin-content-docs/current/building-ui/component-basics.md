---
sidebar_position: 1
title: Component Basics
slug: basics
draft: false
_i18n_hash: e4d0cb9dd9f53dabda8bebe6664bf0d3
---
<DocChip chip='since' label='23.05' />
<JavadocLink type="foundation" location="com/webforj/component/Component" top='true'/>

Komponenten sind grundlegende Bausteine, die zu einem Fenster hinzugefügt werden können und Funktionen für die Benutzeroberfläche sowie benutzerdefiniertes Verhalten bereitstellen. In webforJ dient die Klasse `Component` als Grundlage für alle Komponenten innerhalb der Engine.

## Lebenszyklusverwaltung {#lifecycle-management}

Das Verständnis des Komponentenlebenszyklus ist entscheidend für die effektive Erstellung, Verwaltung und Nutzung von Komponenten. Die folgenden beiden Lebenszykluszustände verfügen über Methoden, um ihr Verhalten zu manipulieren. Diese Methoden sollten nicht explizit vom Benutzer aufgerufen werden.

### Erstellungs- und Zerstörungs-Hooks {#create-and-destroy-hooks}

Alle Klassen, die die Klasse `Component` erweitern, sind dafür verantwortlich, die Funktionalität zu implementieren, die beim Erstellen der `Component` und beim Zerstören ausgeführt wird. Dies geschieht, indem die Methoden `onCreate()` und `onDestroy()` überschrieben werden.

#### `onCreate()` {#oncreate}

Die Methode `onCreate()` wird aufgerufen, wenn die Komponente zu einem Fenster hinzugefügt wird. Die Erstellung von Komponenten umfasst die Einrichtung ihres Anfangszustands und ihrer Funktionalität. Hier definierst du, was die Komponente tun soll, wenn sie erstmals erstellt wird. Ob es darum geht, Variablen zu initialisieren, Ereignislistener einzurichten oder andere Vorbereitungen zu treffen, die Methode `onCreate()` ist dein Einstiegspunkt zur Anpassung des Komponentenverhaltens.

Dieser Hook erhält eine Fensterinstanz, die die Hinzufügung von innerhalb der Komponente enthaltenen Komponenten ermöglicht.

```java
@Override
protected void onCreate(Window window) {
  TextField text = new TextField();
  Button btn = new Button();

  window.add(text, btn);
}
```

:::tip
Die Methode `onCreate()` ist der Ort, an dem die Komponente und alle Bestandteile zum Fenster hinzugefügt werden sollten.
:::

#### `onDestroy()` {#ondestroy}

Das Zerstören von Komponenten ist ein wesentlicher Bestandteil des Ressourcenmanagements und gewährleistet eine ordnungsgemäße Bereinigung. Das Zerstören einer Komponente ist notwendig, wenn sie nicht mehr benötigt wird oder wenn du Ressourcen, die damit verbunden sind, freigeben möchtest. Es ermöglicht einem Entwickler, Bereinigungsaufgaben durchzuführen, wie das Stoppen von Timern, das Freigeben von Speicher oder das Abtrennen von Ereignislistenern. Es ermöglicht auch, die Methode `destroy()` für alle Bestandteil-Komponenten aufzurufen.

:::tip
Die Methode `onDestroy()` ist dafür verantwortlich, die Methode `destroy()` für alle Bestandteil-Komponenten aufzurufen. Andernfalls existieren diese Komponenten weiterhin im DOM, sind jedoch nicht über die API erreichbar.
:::

### Asynchrone Anbringung {#asynchronous-attachment}

Die Methode `whenAttached()` ermöglicht es, Funktionen auszuführen, nachdem eine Komponente zu einem Fenster hinzugefügt wurde. Diese Methode gibt ein <JavadocLink type="foundation" location="com/webforj/PendingResult" code='true'>PendingResult</JavadocLink> zurück, das es ermöglicht, spezifizierte zusätzliche Verhalten asynchron auszuführen, sobald die Komponente im DOM angehängt ist.

:::tip
Anders als die vorherigen drei Methoden soll `whenAttached()` explizit vom Benutzer aufgerufen werden.
:::

```java
public class Demo extends App {
  @Override
  public void run() throws WebforjException {
    Frame window = new Frame();

    Button button = new Button(); 

    /* Expliziter Aufruf von whenAttached(), der ein 
    Nachrichtenfeld anzeigt, wenn der Button an das Frame angehängt wird.*/
    button.whenAttached().thenAccept( e -> {
      showMessageDialog("Ich bin angehängt!", "Asynchrone Anbringung");
    });
  
    // Methode onCreate() wird aufgerufen
    window.add(button); 
  }
}
```

### Beobachter {#observers}

Beobachter spielen eine entscheidende Rolle bei der Verfolgung von Ereignissen im Lebenszyklus von Komponenten. Beobachter können mit den Methoden `addLifecycleObserver()` und `removeLifecycleObserver()` hinzugefügt und entfernt werden und erhalten Benachrichtigungen über Ereignisse wie die Erstellung und Zerstörung von Komponenten.

Durch das Hinzufügen von Beobachtern kannst du Maßnahmen ergreifen, wenn eine Komponente erstellt oder zerstört wird. Dies ist besonders nützlich, um benutzerdefinierte Logik zu implementieren oder spezifische Szenarien basierend auf Komponentenereignissen zu behandeln.

```java
Button button = new Button();
button.addLifecycleObserver((button, lifecycleEvent) -> {
  if (lifecycleEvent == ComponentLifecycleObserver.LifecycleEvent.DESTROY) {
    // implementierte Logik ausführen, wenn der Button zerstört wird
  }
});
```

## Komponenten Eigenschaften {#component-properties}

### Komponentenidentifikatoren {#component-identifiers}

Komponenten-IDs dienen als eindeutige Identifikatoren für Komponenten, die es dir ermöglichen, mit ihnen zu interagieren und ihren Zustand effektiv zu verwalten.

#### Serverseitige Komponenten-ID {#server-side-component-id}

Jede Komponente, die aus der Klasse `Component` erstellt wird, erhält automatisch eine serverseitige Identifikationsnummer. Serverseitige IDs sind wichtig für das interne Tracking und die Identifikation von Komponenten innerhalb des Frameworks. Du kannst die serverseitige Komponenten-ID mit der Methode `getComponentId()` abrufen.

Dies kann in vielen Situationen hilfreich sein, in denen eine eindeutige, serverseitige Identifikationsnummer erforderlich ist, z. B. beim Abfragen einer bestimmten Komponente innerhalb eines Containers.

#### Clientseitige Komponenten-ID {#client-side-component-id}

Clientseitige IDs ermöglichen es dem Benutzer, die Clientdarstellung der serverseitig in Java erstellten Komponente zu erhalten. Alle bereitgestellten webforJ-Komponenten verfügen über eine Implementierung dieser ID. Wenn du Zugriff auf die clientseitige Komponente erhalten und sie verwenden möchtest, kannst du `object.get()` mit der Client-ID ausführen, um die gewünschte Client-Komponente zu erhalten.

:::important
Diese ID ist **nicht** das ID-Attribut des Elements im DOM.
:::

Im folgenden Beispiel wird ein `onClick`-Ereignis zu einem Button hinzugefügt, das dann ausgelöst wird, indem die Methode auf der Client-Komponente aufgerufen wird, nachdem sie mit der `object.get()`-Methode erhalten wurde.

```java
@Override
public void run() throws WebforjException {
  Frame frame = new Frame();
  Button btn = new Button("Klick mich");
  btn.onClick(e -> {
    showMessageDialog("Der Button wurde angeklickt", "Ein Ereignis trat ein");
  });

  btn.whenAttached().thenAccept(e -> {
    getPage().executeJs("objects.get('" + btn.getClientComponentId() + "').click()");
  });
  frame.add(btn);
}
```

### Benutzerdaten {#user-data}

Die Klasse `Component` ermöglicht es dir, zusätzliche Informationen innerhalb der Komponente mithilfe der Methode `setUserData()` einzuschließen. Diese Informationen sind nur auf der Serverseite der Komponente über die Methode `getUserData()` zugänglich und werden nicht an den Client gesendet.

Dies ist besonders nützlich, wenn Informationen, die mit einer Komponente enthalten sein sollen, vorhanden sind und wenn diese Informationen ohne einen Zugriff auf den Client abgerufen werden sollen.
