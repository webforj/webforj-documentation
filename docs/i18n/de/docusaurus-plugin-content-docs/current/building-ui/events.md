---
sidebar_position: 10
title: Events
slug: events
draft: false
_i18n_hash: 6c1d6fc7f2d8e0027320e0323b107dca
---
<JavadocLink type="foundation" location="com/webforj/component/event/Event" top='true'/>

Komponenten, unabhängig davon, ob sie benutzerdefiniert sind oder Teil des Frameworks, unterstützen die Ereignisverarbeitung. Sie können Ereignis-Listener hinzufügen, um verschiedene Arten von Ereignissen zu erfassen, wie z.B. Benutzerinteraktionen, Änderungen im Zustand oder andere benutzerdefinierte Ereignisse. Diese Ereignis-Listener können verwendet werden, um spezifische Aktionen oder Verhaltensweisen als Reaktion auf die Ereignisse auszulösen.

Im folgenden Beispiel wird ein Ereignis hinzugefügt, indem jede der drei unterstützten Methoden verwendet wird: Lambda-Ausdrücke, anonyme Klassen und Methodenreferenzen.
## Ereignisse hinzufügen {#adding-events}

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

Weitere syntaktische Zucker-Methoden oder Aliase wurden hinzugefügt, um eine alternative Hinzufügung von Ereignissen zu ermöglichen, indem das Präfix `on` gefolgt vom Ereignis verwendet wird, wie z.B.:

```java
myComponent.onEvent(e -> {
  // Ausgeführt, wenn das Ereignis ausgelöst wird
});
```

## Ein Ereignis entfernen {#removing-an-event}

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

Es ist wichtig zu beachten, dass Ereignisse häufig mit einer Last kommen, die zusätzliche Informationen zu dem Ereignis enthält. Sie können diese Last effizient innerhalb des Ereignis-Handlers nutzen, um auf relevante Daten zuzugreifen, ohne unnötige Hin- und Rückreisen zwischen dem Client und dem Server zu machen. Durch diese Vorgehensweise können Sie die Leistung Ihrer Anwendung verbessern.

Der folgende Code-Snippet fragt die Komponente ab, um Informationen zu erhalten, die für unsere Demonstration bereits in der Ereignislast enthalten sind, was ineffizienten Code darstellt:

```java
myComponent.addEventListener(e -> {
  // Auf Daten der Komponente zugreifen
  String componentText = e.getComponent().getText();

  //ODER wenn die Komponente innerhalb des Geltungsbereichs der Funktion zugänglich ist
  String componentText = myComponent.getText();

  // Verwenden Sie den componentText, um andere Aktionen auszuführen.
});
```

Stattdessen wird durch die Nutzung der Last der Methode, die im Beispiel den Text der Komponente enthält, eine Hin- und Rückreise vermieden:

```java
myComponent.addEventListener(e -> {
  // Auf Daten aus der Ereignislast zugreifen
  String componentText = e.getText();
  
  // Verwenden Sie den componentText, um andere Aktionen auszuführen.
});
```

Dieser Ansatz minimiert die Notwendigkeit, die Komponente nach Informationen zu befragen, da die Daten bereits in der Ereignislast verfügbar sind. Indem Sie diese effiziente Vorgehensweise bei der Ereignisverarbeitung befolgen, können Sie die Leistung und Reaktionsschnelligkeit Ihrer Komponenten verbessern. Weitere Informationen finden Sie unter [Client/Server-Interaktion](../architecture/client-server).

### Beispiel {#sample}

Im Folgenden finden Sie eine Demonstration, die das Hinzufügen eines <JavadocLink type="foundation" location="com/webforj/component/button/event/ButtonClickEvent"  code="true">ButtonClickEvent</JavadocLink> zu einem [`Button`](#) zeigt. Dieser [`Button`](#) verwendet ebenfalls Informationen, die mit der Ereignislast geliefert werden, um Informationen auf dem Bildschirm anzuzeigen.

<ComponentDemo
path='/webforj/buttonevent'
files={['src/main/java/com/webforj/samples/views/button/ButtonEventView.java']}
height='100px'
/>
