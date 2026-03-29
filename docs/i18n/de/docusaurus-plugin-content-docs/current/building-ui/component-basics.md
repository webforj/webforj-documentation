---
sidebar_position: 3
title: Component Basics
slug: basics
draft: false
_i18n_hash: 0a9127dc9219a32aeb1eef280b386d77
---
<JavadocLink type="foundation" location="com/webforj/component/Component" top='true'/>

Komponenten sind grundlegende Bausteine, die zu einem Fenster hinzugefügt werden können und Benutzeroberflächenfunktionalität sowie benutzerdefiniertes Verhalten bereitstellen. In webforJ dient die `Component`-Klasse als Grundlage für alle Komponenten innerhalb der Engine.

## Lifecycle-Management {#lifecycle-management}

Das Verständnis des Komponenten-Lifecycle ist entscheidend für die effektive Erstellung, Verwaltung und Nutzung von Komponenten. Die folgenden beiden Lifecycle-Zustände verfügen über Methoden, um ihr Verhalten zu manipulieren. Diese Methoden sollten nicht ausdrücklich vom Benutzer aufgerufen werden.

### Erstellungs- und Zerstörungs-Hooks {#create-and-destroy-hooks}

Alle Klassen, die die `Component`-Klasse erweitern, sind dafür verantwortlich, die Funktionalität zu implementieren, die beim Erstellen und Zerstören der `Component` ausgeführt werden soll. Dies geschieht durch das Überschreiben der Methoden `onCreate()` und `onDestroy()`.

#### `onCreate()` {#oncreate}

Die `onCreate()`-Methode wird aufgerufen, wenn die Komponente zu einem Fenster hinzugefügt wird. Das Erstellen von Komponenten umfasst das Einrichten ihres Anfangszustands und ihrer Funktionalität. Hier definieren Sie, was die Komponente beim ersten Erstellen tun soll. Ob es darum geht, Variablen zu initialisieren, Ereignis-Listener aufzurichten oder andere Vorbereitungen zu treffen, die `onCreate()`-Methode ist Ihr Eingangspunkt zur Anpassung des Verhaltens der Komponente. 

Dieser Hook erhält eine Fensterinstanz, die das Hinzufügen von Komponenten innerhalb der Komponente ermöglicht.

```java
@Override
protected void onCreate(Window window) {
  TextField text = new TextField();
  Button btn = new Button();

  window.add(text, btn);
}
```

:::tip
Die `onCreate()`-Methode ist der Ort, an dem die Komponente und alle Bestandteile zum Fenster hinzugefügt werden sollten.
:::

#### `onDestroy()` {#ondestroy}

Das Zerstören von Komponenten ist ein wesentlicher Teil der Ressourcenverwaltung und sorgt für eine ordnungsgemäße Bereinigung. Eine Komponente zu zerstören, ist notwendig, wenn sie nicht mehr benötigt wird oder wenn Sie die mit ihr verbundenen Ressourcen freigeben möchten. Sie ermöglicht es einem Entwickler, Bereinigungsaufgaben durchzuführen, wie das Stoppen von Timern, das Freigeben von Speicher oder das Trennen von Ereignis-Listenern. Sie ermöglicht auch, dass die `destroy()`-Methode auf allen Bestandteilen aufgerufen wird.

:::tip
Die `onDestroy()`-Methode ist dafür verantwortlich, die `destroy()`-Methode auf allen Bestandteilen aufzurufen. Andernfalls existieren diese Komponenten weiterhin im DOM, sind jedoch nicht über die API erreichbar.
:::

### Asynchrone Anbringung {#asynchronous-attachment}

Die `whenAttached()`-Methode ermöglicht das Ausführen von Funktionalität, nachdem eine Komponente zu einem Fenster hinzugefügt wurde. Diese Methode gibt ein <JavadocLink type="foundation" location="com/webforj/PendingResult" code='true'>PendingResult</JavadocLink> zurück, das es ermöglicht, dass zusätzliche spezifizierte Verhalten asynchron ausgeführt wird, sobald die Komponente im DOM angebracht ist. 

:::tip
Im Gegensatz zu den vorherigen drei Methoden soll `whenAttached()` ausdrücklich vom Benutzer aufgerufen werden.
:::

```java
public class Demo extends App {
  @Override
  public void run() throws WebforjException {
    Frame window = new Frame();

    Button button = new Button(); 

    /* Expliziter Aufruf von whenAttached(), der ein 
    Nachrichtenfeld anzeigt, wenn der Button an das Frame angebracht wird.*/
    button.whenAttached().thenAccept( e -> {
      showMessageDialog("Ich bin angebracht!", "Asynchrone Anbringung");
    });
  
    // die onCreate()-Methode wird aufgerufen
    window.add(button); 
  }
}
```

### Beobachter {#observers}

Beobachter spielen eine wichtige Rolle bei der Verfolgung von Ereignissen im Komponenten-Lifecycle. Beobachter können mit den Methoden `addLifecycleObserver()` und `removeLifecycleObserver()` hinzugefügt und entfernt werden und erhalten Benachrichtigungen über Ereignisse wie Erstellung und Zerstörung von Komponenten.

Durch das Hinzufügen von Beobachtern können Sie Maßnahmen ergreifen, wenn eine Komponente erstellt oder zerstört wird. Dies ist besonders nützlich für die Implementierung benutzerdefinierter Logik oder die Handhabung spezifischer Szenarien basierend auf Ereignissen der Komponenten.

```java
Button button = new Button();
button.addLifecycleObserver((button, lifecycleEvent) -> {
  if (lifecycleEvent == ComponentLifecycleObserver.LifecycleEvent.DESTROY) {
    // implementierte Logik, die ausgeführt wird, wenn der Button zerstört wird
  }
});
```

## Komponenten-Eigenschaften {#component-properties}

### Komponenten-Identifikatoren {#component-identifiers}

Komponenten-IDs dienen als eindeutige Identifikatoren für Komponenten, die es Ihnen ermöglichen, mit ihnen zu interagieren und deren Zustand effektiv zu verwalten.

#### Serverseitige Komponenten-ID {#server-side-component-id}

Jede Komponente, die aus der `Component`-Klasse erstellt wird, erhält automatisch eine serverseitige Kennung. Serverseitige IDs sind wichtig für die interne Nachverfolgung und Identifikation von Komponenten innerhalb des Frameworks. Sie können die serverseitige Komponenten-ID mit der Methode `getComponentId()` abrufen.

Dies kann in vielen Situationen hilfreich sein, in denen eine eindeutige, serverseitige Kennung erforderlich ist, z. B. bei der Abfrage einer bestimmten Komponente innerhalb eines Containers.

#### Client-seitige Komponenten-ID {#client-side-component-id}

Client-seitige IDs ermöglichen es dem Benutzer, die Client-Darstellung der auf dem Server in Java erstellten Komponente zu erhalten. Alle bereitgestellten webforJ-Komponenten haben eine Implementierung dieser ID. Wenn Sie Zugriff auf die client-seitige Komponente erhalten und sie verwenden möchten, können Sie `object.get()` mit der client-seitigen ID ausführen, um die gewünschte client-seitige Komponente zu erhalten.

:::important
Diese ID ist **nicht** das ID-Attribut des Elements im DOM.
:::

Im folgenden Beispiel wird ein `onClick`-Ereignis zu einem Button hinzugefügt, das dann ausgelöst wird, indem die Methode auf der client-seitigen Komponente aufgerufen wird, nachdem sie mit der `object.get()`-Methode erhalten wurde.

```java
@Override
public void run() throws WebforjException {
  Frame frame = new Frame();
  Button btn = new Button("Klick mich");
  btn.onClick(e -> {
    showMessageDialog("Der Button wurde geklickt", "Ein Ereignis trat auf");
  });

  btn.whenAttached().thenAccept(e -> {
    getPage().executeJs("objects.get('" + btn.getClientComponentId() + "').click()");
  });
  frame.add(btn);
}
```

### Benutzerdaten {#user-data}

Die `Component`-Klasse ermöglicht es Ihnen, zusätzliche Informationen innerhalb der Komponente mit der Methode `setUserData()` einzuschließen. Diese Informationen sind nur auf der Serverseite der Komponente über die Methode `getUserData()` zugänglich und werden nicht an den Client gesendet.

Dies ist besonders nützlich, wenn Informationen zusammen mit einer Komponente enthalten werden sollen und wenn diese Informationen ohne einen Gang zum Client abgerufen werden sollen.
