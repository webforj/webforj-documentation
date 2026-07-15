---
sidebar_position: 10
title: Events
description: >-
  Attach event listeners to webforJ components with lambdas, anonymous classes,
  or method references and reuse the event payload server-side.
slug: events
draft: false
_i18n_hash: e965d354159ccc38ad417700fc3686eb
---
<JavadocLink type="foundation" location="com/webforj/component/event/Event" top='true'/>

Komponenten, egal ob benutzerdefiniert oder Teil des Frameworks, unterstützen die Ereignisbehandlung. Sie können Ereignislistener hinzufügen, um verschiedene Arten von Ereignissen zu erfassen, wie z.B. Benutzerinteraktionen, Änderungen des Zustands oder andere benutzerdefinierte Ereignisse. Diese Ereignislistener können verwendet werden, um spezifische Aktionen oder Verhaltensweisen als Reaktion auf die Ereignisse auszulösen.

Im folgenden Beispiel wird ein Ereignis mit jedem der drei unterstützten Methoden hinzugefügt: Lambda-Ausdrücke, anonyme Klassen und Methodenreferenzen.
## Hinzufügen von Ereignissen {#adding-events}

Das Hinzufügen eines Ereignislisteners ist möglich, indem eines der folgenden Muster verwendet wird, wobei:

- **`myComponent`** die Komponente ist, an die Sie den Ereignislistener anfügen möchten.

- **`addEventListener`** durch die ereignisspezifische Methode ersetzt wird.

- **`EventListener`** durch den Typ des Ereignisses ersetzt wird, auf das gehört wird.

```java
myComponent.addEventListener(e -> {
  // Wird ausgeführt, wenn das Ereignis ausgelöst wird
});

//ODER

myComponent.addEventListener(new ComponentEventListener<EventListener>() {
  @Override
  public void onComponentEvent(ComponentEvent e){
    // Wird ausgeführt, wenn das Ereignis ausgelöst wird
  }
});

//ODER

myComponent.addEventListener(this::eventMethod);
```

Zusätzliche syntaktische Zucker-Methoden oder Aliase wurden hinzugefügt, um eine alternative Hinzufügung von Ereignissen durch Verwendung des `on`-Präfixes gefolgt vom Ereignis zu ermöglichen, wie zum Beispiel:

```java
myComponent.onEvent(e -> {
  // Wird ausgeführt, wenn das Ereignis ausgelöst wird
});
```

## Entfernen eines Ereignisses {#removing-an-event}

Beim Hinzufügen eines Ereignislisteners wird ein `ListenerRegistration`-Objekt zurückgegeben. Dieses kann unter anderem verwendet werden, um das Ereignis später zu entfernen.

```java
//Ereignis hinzufügen
ListenerRegistration listenerRegistration = myComponent.addEventListener(e -> {
    // Wird ausgeführt, wenn das Ereignis ausgelöst wird
  });

//Ereignis entfernen
listenerRegistration.remove();
```

## Verwendung der Ereignislast {#using-event-payload}

Es ist wichtig zu beachten, dass Ereignisse oft eine Last haben, die zusätzliche Informationen zum Ereignis enthält. Sie können diese Last innerhalb des Ereignishandlers effizient nutzen, um auf relevante Daten zuzugreifen, ohne unnötige Rückfragen zwischen dem Client und dem Server zu stellen. Dadurch können Sie die Leistung Ihrer Anwendung verbessern.

Der folgende Code-Schnipsel fragt die Komponente ab, um Informationen zu erhalten, die zu Demonstrationszwecken bereits in der Ereignislast enthalten sind und ineffizienten Code darstellen:

```java
myComponent.addEventListener(e -> {
  // Greifen Sie auf Daten von der Komponente zu
  String componentText = e.getComponent().getText();

  //ODER wenn die Komponente innerhalb des Geltungsbereichs der Funktion zugänglich ist
  String componentText = myComponent.getText();

  // Verwenden Sie componentText, um andere Aktionen auszuführen.
});
```

Stattdessen wird durch die Nutzung der Last der Methode, die zum Zweck des Beispiels den Text der Komponente beinhaltet, eine Rückfrage vermieden:

```java
myComponent.addEventListener(e -> {
  // Greifen Sie auf Daten aus der Ereignislast zu
  String componentText = e.getText();

  // Verwenden Sie componentText, um andere Aktionen auszuführen.
});
```

Dieser Ansatz minimiert die Notwendigkeit, die Komponente nach Informationen zu fragen, da die Daten in der Ereignislast sofort verfügbar sind. Durch die Befolgung dieser effizienten Ereignisbehandlungspraktiken können Sie die Leistung und Reaktionsfähigkeit Ihrer Komponenten verbessern. Für weitere Informationen können Sie auf [Client/Server-Interaktion](../architecture/client-server) verweisen.

### Beispiel {#sample}

Im Folgenden finden Sie eine Demonstration, die das Hinzufügen eines <JavadocLink type="foundation" location="com/webforj/component/button/event/ButtonClickEvent" code="true">ButtonClickEvent</JavadocLink> zu einem [`Button`](#) zeigt. Dieser [`Button`](#) verwendet ebenfalls Informationen, die mit der Last des Ereignisses geliefert werden, um Informationen auf dem Bildschirm anzuzeigen.

<ComponentDemo
path='/webforj/buttonevent'
files={['src/main/java/com/webforj/samples/views/button/ButtonEventView.java']}
height='100px'
/>
