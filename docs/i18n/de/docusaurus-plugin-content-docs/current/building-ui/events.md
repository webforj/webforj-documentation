---
sidebar_position: 10
title: Events
slug: events
draft: false
_i18n_hash: 3896ad417b6309ffbfbc46b2f893589b
---
<JavadocLink type="foundation" location="com/webforj/component/event/Event" top='true'/>

Komponenten, ob benutzerdefiniert oder Teil des Frameworks, unterstützen das Event-Handling. Sie können Ereignis-Listener hinzufügen, um verschiedene Arten von Ereignissen zu erfassen, wie Benutzerinteraktionen, Zustandsänderungen oder andere benutzerdefinierte Ereignisse. Diese Ereignis-Listener können verwendet werden, um bestimmte Aktionen oder Verhaltensweisen als Reaktion auf die Ereignisse auszulösen.

Im folgenden Beispiel wird ein Ereignis mit jeder der drei unterstützten Methoden hinzugefügt: Lambda-Ausdrücke, anonyme Klassen und Methodenreferenzen.
## Hinzufügen von Ereignissen {#adding-events}

Das Hinzufügen eines Ereignis-Listeners ist möglich, indem man eines der folgenden Muster verwendet, wobei:

- **`myComponent`** die Komponente ist, an die Sie den Ereignis-Listener anhängen möchten.

- **`addEventListener`** durch die spezifische Methode des Ereignisses ersetzt wird.

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

Zusätzliche syntaktische Zuckermethoden oder Aliase wurden hinzugefügt, um eine alternative Hinzufügung von Ereignissen durch Verwendung des `on`-Präfixes gefolgt vom Ereignis zu ermöglichen, wie zum Beispiel:

```java
myComponent.onEvent(e -> {
  // Wird ausgeführt, wenn das Ereignis ausgelöst wird
});
```

## Entfernen eines Ereignisses {#removing-an-event}

Beim Hinzufügen eines Ereignis-Listeners wird ein `ListenerRegistration`-Objekt zurückgegeben. Dies kann unter anderem verwendet werden, um das Ereignis später zu entfernen.

```java
// Hinzufügen des Ereignisses
ListenerRegistration listenerRegistration = myComponent.addEventListener(e -> {
        // Wird ausgeführt, wenn das Ereignis ausgelöst wird
    });

// Entfernen des Ereignisses
listenerRegistration.remove();
```

## Verwendung von Ereignis-Payload {#using-event-payload}

Es ist wichtig zu beachten, dass Ereignisse oft mit einer Payload kommen, die zusätzliche Informationen zu dem Ereignis enthält. Sie können diese Payload effizient innerhalb des Ereignis-Handlers nutzen, um auf relevante Daten zuzugreifen, ohne unnötige Rückfragen zwischen dem Client und dem Server zu machen. Dadurch können Sie die Leistung Ihrer Anwendung verbessern.

Der folgende Code-Schnipsel fragt die Komponente ab, um Informationen zu erhalten, die für unsere Demonstrationszwecke bereits in der Ereignis-Payload enthalten sind und ineffizienten Code darstellt:

```java
myComponent.addEventListener(e -> {
  // Zugriff auf Daten der Komponente
  String componentText = e.getComponent().getText();

  //ODER wenn die Komponente innerhalb des Umfangs der Funktion zugänglich ist
  String componentText = myComponent.getText();

  // Verwenden Sie componentText, um andere Aktionen auszuführen.
});
```

Stattdessen kann durch die Nutzung der Payload der Methode, die für das Beispiel den Text der Komponente enthält, eine Rückfrage vermieden werden:

```java
myComponent.addEventListener(e -> {
  // Zugriff auf Daten aus der Ereignis-Payload
  String componentText = e.getText();
  
  // Verwenden Sie componentText, um andere Aktionen auszuführen.
});
```

Dieser Ansatz minimiert die Notwendigkeit, die Komponente nach Informationen zu fragen, da die Daten in der Ereignis-Payload bereits verfügbar sind. Durch die Befolgung dieser effizienten Praktiken im Event-Handling können Sie die Leistung und Reaktionsfähigkeit Ihrer Komponenten verbessern. Weitere Informationen finden Sie unter [Client/Server-Interaktion](../architecture/client-server).

### Beispiel {#sample}

Nachfolgend finden Sie eine Demonstration, die das Hinzufügen eines <JavadocLink type="foundation" location="com/webforj/component/button/event/ButtonClickEvent"  code="true">ButtonClickEvent</JavadocLink> zu einem [`Button`](#) zeigt. Dieser [`Button`](#) verwendet ebenfalls Informationen, die mit der Ereignis-Payload geliefert werden, um Informationen auf dem Bildschirm anzuzeigen.

<ComponentDemo 
path='/webforj/buttonevent?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/button/ButtonEventView.java'
height='100px'
/>

<!-- <EventTable base events={['drawerOpen', 'drawerClose']} /> -->
