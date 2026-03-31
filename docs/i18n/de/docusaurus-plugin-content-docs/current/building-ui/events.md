---
sidebar_position: 10
title: Events
slug: events
draft: false
_i18n_hash: c5c07ac4ca0f8d88ea6ef86afd5bb408
---
<JavadocLink type="foundation" location="com/webforj/component/event/Event" top='true'/>

Komponenten, ob benutzerdefiniert oder Teil des Frameworks, unterstützen die Ereignisbehandlung. Sie können Ereignis-Listener hinzufügen, um verschiedene Arten von Ereignissen zu erfassen, wie zum Beispiel Benutzerinteraktionen, Änderungen im Zustand oder andere benutzerdefinierte Ereignisse. Diese Ereignis-Listener können verwendet werden, um spezifische Aktionen oder Verhaltensweisen als Reaktion auf die Ereignisse auszulösen.

Im folgenden Beispiel wird ein Ereignis mit jeder der drei unterstützten Methoden hinzugefügt: Lambda-Ausdrücke, anonyme Klassen und Methodenreferenzen.
## Hinzufügen von Ereignissen {#adding-events}

Das Hinzufügen eines Ereignis-Listeners ist möglich, indem eines der folgenden Muster verwendet wird, wobei:

- **`myComponent`** die Komponente ist, an die Sie den Ereignis-Listener anhängen möchten.

- **`addEventListener`** durch die ereignisspezifische Methode ersetzt wird.

- **`EventListener`** durch den Typ des Ereignisses ersetzt wird, auf das gehört wird.

```java
myComponent.addEventListener(e -> {
  // Ausgeführt, wenn das Ereignis ausgelöst wird
});

//ODER

myComponent.addEventListener(new ComponentEventListener<EventListener>() {
  @Override
  public void onComponentEvent(ComponentEvent e){
    // Ausgeführt, wenn das Ereignis ausgelöst wird
  }
});

//ODER

myComponent.addEventListener(this::eventMethod);
```

Zusätzliche syntaktische Zucker-Methoden oder Aliase wurden hinzugefügt, um eine alternative Hinzufügung von Ereignissen durch Verwendung des `on`-Präfixes gefolgt vom Ereignis zu ermöglichen, wie zum Beispiel:

```java
myComponent.onEvent(e -> {
  // Ausgeführt, wenn das Ereignis ausgelöst wird
});
```

## Entfernen eines Ereignisses {#removing-an-event}

Beim Hinzufügen eines Ereignis-Listeners wird ein `ListenerRegistration`-Objekt zurückgegeben. Dies kann unter anderem verwendet werden, um das Ereignis später zu entfernen.

```java
// Ereignis hinzufügen
ListenerRegistration listenerRegistration = myComponent.addEventListener(e -> {
    // Ausgeführt, wenn das Ereignis ausgelöst wird
  });

// Ereignis entfernen
listenerRegistration.remove();
```

## Verwendung der Ereignislast {#using-event-payload}

Es ist wichtig zu beachten, dass Ereignisse oft mit einer Last (Payload) kommen, die zusätzliche Informationen zum Ereignis enthält. Sie können diese Last effizient innerhalb des Ereignis-Handlers nutzen, um auf relevante Daten zuzugreifen, ohne unnötige Rundreisen zwischen Client und Server zu machen. Dadurch können Sie die Leistung Ihrer Anwendung verbessern.

Der folgende Code-Snippet fragt die Komponente ab, um Informationen zu erhalten, die für die Zwecke unserer Demonstration bereits in der Ereignislast enthalten sind und ineffizienten Code darstellen:

```java
myComponent.addEventListener(e -> {
  // Auf Daten von der Komponente zugreifen
  String componentText = e.getComponent().getText();

  //ODER, wenn die Komponente im Geltungsbereich der Funktion zugänglich ist
  String componentText = myComponent.getText();

  // Verwenden Sie componentText, um andere Aktionen auszuführen.
});
```

Stattdessen wird die Last der Methode genutzt, die der Beispiel wegen den Text der Komponente enthält, um eine Rundreise zu vermeiden:

```java
myComponent.addEventListener(e -> {
  // Auf Daten von der Ereignislast zugreifen
  String componentText = e.getText();
  
  // Verwenden Sie componentText, um andere Aktionen auszuführen.
});
```

Dieser Ansatz minimiert die Notwendigkeit, die Komponente nach Informationen zu fragen, da die Daten in der Ereignislast sofort verfügbar sind. Durch Befolgung dieser effizienten Ereignisbehandlungspraktik können Sie die Leistung und Reaktionsfähigkeit Ihrer Komponenten verbessern. Für weitere Informationen können Sie auf [Client/Server-Interaktion](../architecture/client-server) verweisen.

### Beispiel {#sample}

Im Folgenden finden Sie eine Demonstration, die das Hinzufügen eines <JavadocLink type="foundation" location="com/webforj/component/button/event/ButtonClickEvent"  code="true">ButtonClickEvent</JavadocLink> zu einem [`Button`](#) zeigt. Dieser [`Button`](#) verwendet auch Informationen, die mit der Ereignislast kommen, um Informationen auf dem Bildschirm anzuzeigen.

<ComponentDemo 
path='/webforj/buttonevent?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/button/ButtonEventView.java'
height='100px'
/>
