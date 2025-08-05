---
sidebar_position: 1
title: Component Basics
slug: basics
draft: false
_i18n_hash: d517f6169f7ac0798ed073bb27348eb5
---
<DocChip chip='since' label='23.05' />
<JavadocLink type="foundation" location="com/webforj/component/Component" top='true'/>

Komponenten sind grundlegende Bausteine, die zu einem Fenster hinzugefügt werden können und Benutzeroberflächenfunktionen sowie benutzerdefiniertes Verhalten bereitstellen. In webforJ dient die `Component`-Klasse als Grundlage für alle Komponenten innerhalb der Engine.

## Lifecycle-Management {#lifecycle-management}

Das Verständnis des Lebenszyklus von Komponenten ist entscheidend, um Komponenten effektiv zu erstellen, zu verwalten und zu nutzen. Die folgenden beiden Lebenszykluszustände verfügen über Methoden, um ihr Verhalten zu manipulieren. Diese Methoden sollten nicht ausdrücklich vom Benutzer aufgerufen werden.

### Erstellungs- und Zerstörungsmethoden {#create-and-destroy-hooks}

Alle Klassen, die die `Component`-Klasse erweitern, sind dafür verantwortlich, die Funktionalität zu implementieren, die ausgeführt werden soll, wenn die `Component` erstellt und wenn sie zerstört wird. Dies geschieht durch Überschreiben der Methoden `onCreate()` und `onDestroy()`.

#### `onCreate()` {#oncreate}

Die Methode `onCreate()` wird aufgerufen, wenn die Komponente zu einem Fenster hinzugefügt wird. Komponenten zu erstellen bedeutet, ihren anfänglichen Zustand und ihre Funktionalität einzurichten. Hier definierst du, was die Komponente tun soll, wenn sie zum ersten Mal erstellt wird. Egal, ob es darum geht, Variablen zu initialisieren, Ereignis-Listener einzurichten oder eine andere Einrichtung vorzunehmen, die Methode `onCreate()` ist dein Einstiegspunkt zur Anpassung des Verhaltens der Komponente.

Dieser Hook erhält eine Fensterinstanz, die die Hinzufügung der innerhalb der Komponente enthaltenen Komponenten ermöglicht.

```java
@Override
protected void onCreate(Window window) {
  TextField text = new TextField();
  Button btn = new Button();

  window.add(text, btn);
}
```

:::tip
Die Methode `onCreate()` ist der Ort, an dem die Komponente und alle Komponenten hinzugefügt werden sollten.
:::

#### `onDestroy()` {#ondestroy}

Die Zerstörung von Komponenten ist ein wesentlicher Bestandteil des Ressourcenmanagements und der ordnungsgemäßen Bereinigung. Es ist notwendig, eine Komponente zu zerstören, wenn sie nicht mehr benötigt wird oder wenn du Ressourcen freigeben möchtest, die mit ihr verbunden sind. Dies ermöglicht es einem Entwickler, Bereinigungsaufgaben durchzuführen, z. B. Timer zu stoppen, Speicher freizugeben oder Ereignis-Listener zu trennen. Es ermöglicht auch, die Methode `destroy()` auf allen dazugehörigen Komponenten aufzurufen.

:::tip
Die Methode `onDestroy()` ist dafür verantwortlich, die Methode `destroy()` auf allen zugehörigen Komponenten aufzurufen. Andernfalls bestehen diese Komponenten weiterhin im DOM, sind jedoch über die API nicht mehr erreichbar.
:::

### Asynchrone Anbindung {#asynchronous-attachment}

Die Methode `whenAttached()` ermöglicht es, Funktionen auszuführen, nachdem eine Komponente zu einem Fenster hinzugefügt wurde. Diese Methode gibt eine <JavadocLink type="foundation" location="com/webforj/PendingResult" code='true'>PendingResult</JavadocLink> zurück, die es ermöglicht, zusätzlich festgelegtes Verhalten asynchron auszuführen, sobald die Komponente im DOM angehängt ist.

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
    Nachrichtenfenster anzeigt, wenn der Button dem Frame angehängt ist.*/
    button.whenAttached().thenAccept( e -> {
      showMessageDialog("Ich bin angehängt!", "Asynchrone Anbindung");
    });
  
    // Methode onCreate() wird aufgerufen
    window.add(button); 
  }
}
```

### Beobachter {#observers}

Beobachter spielen eine wichtige Rolle beim Verfolgen von Ereignissen im Lebenszyklus von Komponenten. Beobachter können mit den Methoden `addLifecycleObserver()` und `removeLifecycleObserver()` hinzugefügt und entfernt werden und erhalten Benachrichtigungen über Ereignisse wie die Erstellung und Zerstörung von Komponenten.

Durch das Hinzufügen von Beobachtern kannst du Maßnahmen ergreifen, wenn eine Komponente erstellt oder zerstört wird. Dies ist besonders nützlich, um benutzerdefinierte Logik zu implementieren oder spezifische Szenarien basierend auf Komponentenereignissen zu behandeln.

```java
Button button = new Button();
button.addLifecycleObserver((button, lifecycleEvent) -> {
  if (lifecycleEvent == ComponentLifecycleObserver.LifecycleEvent.DESTROY) {
    // Implementierte Logik, die ausgeführt werden soll, wenn der Button zerstört wird
  }
});
```

## Komponentenattribute {#component-properties}

### Komponentenidentifikatoren {#component-identifiers}

Komponenten-IDs dienen als eindeutige Identifikatoren für Komponenten, die es ermöglichen, mit ihnen zu interagieren und ihren Zustand effektiv zu verwalten.

#### Serverseitige Komponenten-ID {#server-side-component-id}

Jede Komponente, die aus der `Component`-Klasse erstellt wird, erhält automatisch eine serverseitige Identifikationsnummer. Serverseitige IDs sind für die interne Nachverfolgung und Identifizierung von Komponenten innerhalb des Frameworks unerlässlich. Du kannst die serverseitige Komponenten-ID mit der Methode `getComponentId()` abrufen.

Dies kann in vielen Situationen hilfreich sein, in denen eine eindeutige, serverseitige Identifikation erforderlich ist, z. B. bei der Abfrage einer bestimmten Komponente innerhalb eines Containers.

#### Client-seitige Komponenten-ID {#client-side-component-id}

Client-seitige IDs ermöglichen es dem Benutzer, die Client-Darstellung der serverseitig in Java erstellten Komponente zu erhalten. Alle bereitgestellten webforJ-Komponenten haben eine Implementierung dieser ID. Wenn du Zugriff auf die client-seitige Komponente erhalten und sie verwenden möchtest, kannst du `object.get()` mit der Client-ID ausführen, um die gewünschte Client-Komponente zu erhalten.

:::important
Diese ID ist **nicht** das ID-Attribut des Elements im DOM.
:::

Im untenstehenden Beispiel wird ein `onClick`-Ereignis zu einem Button hinzugefügt, der dann durch den Aufruf der Methode auf der client-seitigen Komponente ausgelöst wird, nachdem sie mit der Methode `object.get()` abgerufen wurde.

```java
@Override
public void run() throws WebforjException {
  Frame frame = new Frame();
  Button btn = new Button("Klicke mich");
  btn.onClick(e -> {
    showMessageDialog("Der Button wurde geklickt", "Ein Ereignis ist aufgetreten");
  });

  btn.whenAttached().thenAccept(e -> {
    getPage().executeJs("objects.get('" + btn.getClientComponentId() + "').click()");
  });
  frame.add(btn);
}
```

### Benutzerdaten {#user-data}

Die `Component`-Klasse ermöglicht es dir, zusätzliche Informationen innerhalb der Komponente mithilfe der Methode `setUserData()` einzuschließen. Diese Informationen sind nur auf der Serverseite der Komponente über die Methode `getUserData()` zugänglich und werden nicht an den Client gesendet.

Dies ist besonders nützlich, wenn Informationen mit einer Komponente verbunden sein sollen und diese Informationen ohne zusätzliche Abfrage an den Client zugänglich sein sollen.
