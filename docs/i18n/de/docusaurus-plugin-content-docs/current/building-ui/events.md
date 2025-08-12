---
sidebar_position: 10
title: Events
slug: events
draft: false
_i18n_hash: 35a5057106e5fe7f270cbadaff74b924
---
<JavadocLink type="foundation" location="com/webforj/component/event/Event" top='true'/>

Komponenten, ob benutzerdefiniert oder Teil des Frameworks, unterstützen die Ereignisbehandlung. Sie können Ereignislistener hinzufügen, um verschiedene Arten von Ereignissen zu erfassen, wie z. B. Benutzerinteraktionen, Statusänderungen oder andere benutzerdefinierte Ereignisse. Diese Ereignislistener können verwendet werden, um bestimmte Aktionen oder Verhaltensweisen als Reaktion auf die Ereignisse auszulösen.

Im folgenden Beispiel wird ein Ereignis mit jeweils einer der drei unterstützten Methoden hinzugefügt: Lambda-Ausdrücke, anonyme Klassen und Methodenreferenzen.
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

Zusätzliche syntaktische Zucker-Methoden oder Aliase wurden hinzugefügt, um die alternative Hinzufügung von Ereignissen mit dem `on`-Präfix gefolgt vom Ereignis zu ermöglichen, wie z. B.:

```java
myComponent.onEvent(e -> {
  // Wird ausgeführt, wenn das Ereignis ausgelöst wird
});
```

## Entfernen eines Ereignisses {#removing-an-event}

Beim Hinzufügen eines Ereignislisteners wird ein `ListenerRegistration`-Objekt zurückgegeben. Dieses kann unter anderem verwendet werden, um das Ereignis später zu entfernen.

```java
// Hinzufügen des Ereignisses
ListenerRegistration listenerRegistration = myComponent.addEventListener(e -> {
        // Wird ausgeführt, wenn das Ereignis ausgelöst wird
    });

// Entfernen des Ereignisses
listenerRegistration.remove();
```

## Verwendung der Ereignislademethode {#using-event-payload}

Es ist wichtig zu beachten, dass Ereignisse oft mit einem Payload verbunden sind, der zusätzliche Informationen zu dem Ereignis enthält. Sie können diese Daten innerhalb des Ereignishandlers effizient nutzen, um relevante Informationen zuzugreifen, ohne unnötige Datenübertragungen zwischen Client und Server zu machen. Dadurch können Sie die Leistung Ihrer Anwendung verbessern.

Der folgende Code-Ausschnitt fragt die Komponente ab, um Informationen zu erhalten, die für unsere Demonstration bereits im Ereignispayload enthalten sind und ineffizienten Code darstellen:

```java
myComponent.addEventListener(e -> {
  // Auf Daten aus der Komponente zugreifen
  String componentText = e.getComponent().getText();

  //ODER wenn die Komponente im Gültigkeitsbereich der Funktion zugänglich ist
  String componentText = myComponent.getText();

  // Verwenden Sie den componentText, um weitere Aktionen durchzuführen.
});
```

Stattdessen wird durch die Nutzung des Payloads der Methode, die in diesem Beispiel den Text der Komponente beinhaltet, eine Datenübertragung vermieden:

```java
myComponent.addEventListener(e -> {
  // Auf Daten aus dem Ereignispayload zugreifen
  String componentText = e.getText();
  
  // Verwenden Sie den componentText, um weitere Aktionen durchzuführen.
});
```

Dieser Ansatz minimiert die Notwendigkeit, die Komponente nach Informationen zu fragen, da die Daten im Ereignispayload sofort verfügbar sind. Durch die Befolgung dieser effizienten Praktiken in der Ereignisbehandlung können Sie die Leistung und Reaktionsfähigkeit Ihrer Komponenten verbessern. Weitere Informationen finden Sie unter [Client/Server-Interaktion](../architecture/client-server).

### Beispiel {#sample}

Im Folgenden finden Sie eine Demonstration, die das Hinzufügen eines <JavadocLink type="foundation" location="com/webforj/component/button/event/ButtonClickEvent"  code="true">ButtonClickEvent</JavadocLink> zu einem [`Button`](#) zeigt. Dieser [`Button`](#) verwendet auch Informationen, die mit dem Payload des Ereignisses kommen, um Informationen auf dem Bildschirm anzuzeigen.

<ComponentDemo 
path='/webforj/buttonevent?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/button/ButtonEventView.java'
height='100px'
/>

<!-- <EventTable base events={['drawerOpen', 'drawerClose']} /> -->
