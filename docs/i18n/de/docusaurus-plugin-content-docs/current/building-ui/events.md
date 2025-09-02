---
sidebar_position: 10
title: Events
slug: events
draft: false
_i18n_hash: 620635097d0620cc0cd4a639b0d29d97
---
<JavadocLink type="foundation" location="com/webforj/component/event/Event" top='true'/>

Komponenten, ob benutzerdefiniert oder Teil des Frameworks, unterstützen die Ereignisbehandlung. Sie können Ereignislistener hinzufügen, um verschiedene Arten von Ereignissen zu erfassen, wie z. B. Benutzerinteraktionen, Zustandsänderungen oder andere benutzerdefinierte Ereignisse. Diese Ereignislistener können verwendet werden, um spezifische Aktionen oder Verhaltensweisen als Reaktion auf die Ereignisse auszulösen.

Im folgenden Beispiel wird ein Ereignis hinzugefügt, indem jede der drei unterstützten Methoden verwendet wird: Lambda-Ausdrücke, anonyme Klassen und Methodenreferenzen.
## Hinzufügen von Ereignissen {#adding-events}

Das Hinzufügen eines Ereignislisteners ist möglich, indem eines der folgenden Muster verwendet wird, wobei:

- **`myComponent`** die Komponente ist, an die Sie den Ereignislistener anhängen möchten.

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

Zusätzliche syntaktische Zucker-Methoden oder Aliase wurden hinzugefügt, um die alternative Hinzufügung von Ereignissen durch Verwendung des `on`-Präfixes gefolgt vom Ereignis zu ermöglichen, wie z. B.:

```java
myComponent.onEvent(e -> {
  // Wird ausgeführt, wenn das Ereignis ausgelöst wird
});
```

## Entfernen eines Ereignisses {#removing-an-event}

Beim Hinzufügen eines Ereignislisteners wird ein `ListenerRegistration`-Objekt zurückgegeben. Dies kann unter anderem verwendet werden, um das Ereignis später zu entfernen.

```java
// Ereignis hinzufügen
ListenerRegistration listenerRegistration = myComponent.addEventListener(e -> {
        // Wird ausgeführt, wenn das Ereignis ausgelöst wird
    });

// Ereignis entfernen
listenerRegistration.remove();
```

## Verwenden von Ereignis-Payload {#using-event-payload}

Es ist wichtig zu beachten, dass Ereignisse häufig mit einer Payload kommen, die zusätzliche Informationen zum Ereignis enthält. Sie können diese Payload effizient innerhalb des Ereignishandlers nutzen, um auf relevante Daten zuzugreifen, ohne unnötige Rundreisen zwischen Client und Server zu machen. Dadurch verbessern Sie die Leistung Ihrer Anwendung.

Der folgende Codeausschnitt fragt die Komponente ab, um Informationen zu erhalten, die für unsere Demonstration bereits in der Ereignis-Payload enthalten sind, was ineffizienten Code darstellt:

```java
myComponent.addEventListener(e -> {
  // Daten von der Komponente abrufen
  String componentText = e.getComponent().getText();

  //ODER wenn die Komponente im Gültigkeitsbereich der Funktion zugänglich ist
  String componentText = myComponent.getText();

  // Verwenden Sie den componentText, um andere Aktionen auszuführen.
});
```

Stattdessen wird mit der Verwendung der Payload der Methode, die im Beispiel den Text der Komponente enthält, eine Rundreise vermieden:

```java
myComponent.addEventListener(e -> {
  // Daten aus der Ereignis-Payload abrufen
  String componentText = e.getText();
  
  // Verwenden Sie den componentText, um andere Aktionen auszuführen.
});
```

Dieser Ansatz minimiert die Notwendigkeit, die Komponente nach Informationen abzufragen, da die Daten in der Ereignis-Payload sofort verfügbar sind. Durch die Befolgung dieser effizienten Ereignisbehandlungspraktiken können Sie die Leistung und Reaktionsfähigkeit Ihrer Komponenten verbessern. Für weitere Informationen können Sie auf [Client/Server-Interaktion](../architecture/client-server) verweisen.

### Beispiel {#sample}

Nachfolgend finden Sie eine Demonstration, die die Hinzufügung eines <JavadocLink type="foundation" location="com/webforj/component/button/event/ButtonClickEvent"  code="true">ButtonClickEvent</JavadocLink> zu einem [`Button`](#) zeigt. Dieser [`Button`](#) verwendet ebenfalls Informationen, die mit der Payload des Ereignisses kommen, um Informationen auf dem Bildschirm anzuzeigen.

<ComponentDemo 
path='/webforj/buttonevent?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/button/ButtonEventView.java'
height='100px'
/>
