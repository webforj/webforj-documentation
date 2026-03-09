---
title: Error Handling
sidebar_position: 5
_i18n_hash: 7957d907ae8a5bd9e7b3f7c2fdba2623
---
Fehlerbehandlung ist ein entscheidender Aspekt bei der Entwicklung zuverlässiger Webanwendungen. In webforJ ist die Fehlerbehandlung so gestaltet, dass sie flexibel und anpassbar ist, sodass Entwickler Ausnahmen auf die Weise behandeln können, die am besten zu den Bedürfnissen ihrer Anwendung passt.

## Übersicht {#overview}

In webforJ dreht sich die Fehlerbehandlung um das `ErrorHandler`-Interface. Dieses Interface ermöglicht es Entwicklern, zu definieren, wie ihre Anwendung auf Ausnahmen reagieren soll, die während der Ausführung auftreten. Standardmäßig bietet webforJ einen `GlobalErrorHandler`, der alle Ausnahmen auf generische Weise behandelt. Entwickler können jedoch benutzerdefinierte Fehlerhandler für spezifische Ausnahmen erstellen, um maßgeschneiderte Antworten zu liefern.

## Entdeckung und Verwendung von Fehlerhandlern {#discovering-and-using-error-handlers}

webforJ verwendet das Service Provider Interface (SPI) von Java, um Fehlerhandler zu entdecken und zu laden.

### Entdeckungsprozess {#discovery-process}

1. **Dienstregistrierung**: Fehlerhandler werden über den Mechanismus `META-INF/services` registriert.
2. **Dienstladung**: Beim Start der Anwendung lädt webforJ alle Klassen, die in `META-INF/services/com.webforj.error.ErrorHandler` aufgeführt sind.
3. **Fehlerbehandlung**: Wenn eine Ausnahme auftritt, prüft webforJ, ob ein Fehlerhandler für diese spezifische Ausnahme existiert.

### Auswahl des Handlers {#handler-selection}

- Wenn ein spezifischer Handler für die Ausnahme existiert, wird dieser verwendet.
- Wenn kein spezifischer Handler gefunden wird, aber ein benutzerdefinierter globaler Fehlerhandler `WebforjGlobalErrorHandler` definiert ist, wird dieser verwendet.
- Wenn keiner gefunden wird, wird der Standard-`GlobalErrorHandler` verwendet.

## Das `ErrorHandler`-Interface {#the-errorhandler-interface}

Das `ErrorHandler`-Interface ist dafür ausgelegt, Fehler zu verarbeiten, die während der Ausführung einer webforJ-Anwendung auftreten. Anwendungen, die spezifische Ausnahmen verwalten möchten, sollten dieses Interface implementieren.

### Methoden {#methods}

- **`onError(Throwable throwable, boolean debug)`**: Wird aufgerufen, wenn ein Fehler auftritt. Diese Methode sollte die Logik zur Verarbeitung der Ausnahme enthalten.
- **`showErrorPage(String title, String content)`**: Eine Standardmethode, die die Fehlerseite mit dem angegebenen Titel und Inhalt anzeigt.

### Namenskonvention {#naming-convention}

Die implementierende Klasse muss nach der Ausnahme benannt werden, die sie verarbeitet, mit dem Suffix `ErrorHandler`. Um beispielsweise die `NullPointerException` zu behandeln, sollte die Klasse `NullPointerExceptionErrorHandler` heißen.

### Registrierung {#registration}

Der benutzerdefinierte Fehlerhandler muss in der Datei `META-INF/services/com.webforj.error.ErrorHandler` registriert werden, damit webforJ ihn entdecken und verwenden kann.

## Implementierung eines benutzerdefinierten Fehlerhandlers {#implementing-a-custom-error-handler}

Die folgenden Schritte beschreiben die Implementierung eines benutzerdefinierten Fehlerhandlers für eine spezifische Ausnahme:

### Schritt 1: Erstellen Sie die Fehlerhandler-Klasse {#step-1-create-the-error-handler-class}

Erstellen Sie eine neue Klasse, die das `ErrorHandler`-Interface implementiert und nach der Ausnahme benannt ist, die sie verarbeitet.

```java
package com.example.error;

import com.webforj.error.ErrorHandler;

public class NullPointerExceptionErrorHandler implements ErrorHandler {

  @Override
  public void onError(Throwable throwable, boolean debug) {
    // Benutzerdefinierte Logik zur Behandlung der NullPointerException
    String title = "Nullpointer-Ausnahme";
    String content = "Ein Nullwert wurde an einer Stelle gefunden, wo ein Objekt erforderlich ist.";

    showErrorPage(title, content);
  }
}
```

:::info `showErrorPage()`-Methode
Die `showErrorPage`-Methode ist eine Hilfsmethode, die die webforJ-API verwendet, um den bereitgestellten HTML-Inhalt und den Seitentitel an den Browser zu senden und eine Fehlerseite anzuzeigen. Wenn eine Ausnahme auftritt und die Anwendung nicht wiederhergestellt werden kann, ist es unmöglich, webforJ-Komponenten zu verwenden, um eine benutzerdefinierte Fehlerseite zu erstellen. Die `Page`-API bleibt jedoch zugänglich, sodass der Entwickler versuchen kann, eine Fehlerseite anzuzeigen oder weiterzuleiten.
:::

### Schritt 2: Registrieren Sie den Fehlerhandler {#step-2-register-the-error-handler}

Erstellen Sie eine Datei namens `com.webforj.error.ErrorHandler` im Verzeichnis `META-INF/services` Ihrer Anwendung. Fügen Sie den vollqualifizierten Namen Ihrer Fehlerhandler-Klasse in diese Datei ein.

**Datei**: `META-INF/services/com.webforj.error.ErrorHandler`

```
com.example.error.NullPointerExceptionErrorHandler
```

Jetzt wird, wann immer eine `NullPointerException` ausgelöst wird, von webforJ Ihr registrierter Handler ausgewählt und seine Logik ausgeführt, um den Fehler zu behandeln.

## Verwendung von `AutoService` zur Vereinfachung der Registrierung {#using-autoservice-to-simplify-registration}

Es ist leicht für Entwickler, zu vergessen, Dienstbeschreibungen zu aktualisieren oder korrekt festzulegen. Durch die Verwendung von Googles `AutoService` können Sie die Generierung der Datei `META-INF/services/com.webforj.error.ErrorHandler` automatisieren. Alles, was Sie tun müssen, ist, den Fehlerhandler mit der `AutoService`-Annotation zu versehen. Weitere Informationen zu [AutoService finden Sie hier](https://github.com/google/auto/blob/main/service/README.md).

```java
@AutoService(ErrorHandler.class)
public class NullPointerExceptionErrorHandler implements ErrorHandler {

  @Override
  public void onError(Throwable throwable, boolean debug) {
    // Benutzerdefinierte Logik zur Behandlung der NullPointerException
    String title = "Nullpointer-Ausnahme";
    String content = "Ein Nullwert wurde an einer Stelle gefunden, wo ein Objekt erforderlich ist.";

    showErrorPage(title, content);
  }
}
```

## Die `GlobalErrorHandler`-Klasse {#the-globalerrorhandler-class}

Der `GlobalErrorHandler` ist der standardmäßige Fehlerhandler, der von webforJ bereitgestellt wird. Er implementiert das `ErrorHandler`-Interface und bietet generische Fehlerbehandlung.

### Verhalten {#behavior}

- **Protokollierung**: Fehler werden sowohl in den Server- als auch in den Browserkonsolen protokolliert.
- **Anzeige der Fehlerseite**: Abhängig vom Debug-Modus wird auf der Fehlerseite der Stack-Trace oder eine generische Fehlermeldung angezeigt.

### Definition eines benutzerdefinierten globalen Fehlerhandlers {#defining-a-custom-global-error-handler}

Um einen globalen Fehlerhandler zu definieren, müssen Sie einen neuen Fehlerhandler mit dem Namen `WebforjGlobalErrorHandler` erstellen, und dann die [Schritte zur Registrierung von Fehlerhandlern](#step-2-register-the-error-handler) folgen, wie zuvor erklärt. In diesem Fall sucht webforJ zuerst nach benutzerdefinierten Fehlerhandlern, um Ausnahmen zu verwalten. Wenn keine gefunden werden, fällt webforJ auf den benutzerdefinierten globalen Fehlerhandler zurück.

:::info
Wenn mehrere `WebforjGlobalErrorHandler` registriert sind, wählt webforJ den ersten aus.
:::
