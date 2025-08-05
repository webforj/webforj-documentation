---
title: Error Handling
sidebar_position: 25
_i18n_hash: a758848bf429e84f33f8b7ba8a4f7277
---
Fehlerbehandlung ist ein entscheidender Aspekt bei der Entwicklung robuster Webanwendungen. In webforJ ist die Fehlerbehandlung so konzipiert, dass sie flexibel und anpassbar ist, was es Entwicklern ermöglicht, Ausnahmen auf eine Weise zu behandeln, die den Bedürfnissen ihrer Anwendung am besten entspricht.

## Übersicht {#overview}

In webforJ dreht sich die Fehlerbehandlung um das `ErrorHandler`-Interface. Dieses Interface ermöglicht es Entwicklern, festzulegen, wie ihre Anwendung reagieren soll, wenn Ausnahmen während der Ausführung auftreten. Standardmäßig stellt webforJ einen `GlobalErrorHandler` zur Verfügung, der alle Ausnahmen auf allgemeine Weise behandelt. Entwickler können jedoch benutzerdefinierte Fehlerbehandler für spezifische Ausnahmen erstellen, um maßgeschneiderte Antworten zu bieten.

## Entdecken und Verwenden von Fehlerbehandlern {#discovering-and-using-error-handlers}

webforJ verwendet die Service Provider Interface (SPI) von Java, um Fehlerbehandler zu entdecken und zu laden.

### Entdeckungsprozess {#discovery-process}

1. **Service-Registrierung**: Fehlerbehandler werden über den Mechanismus `META-INF/services` registriert.
2. **Service-Laden**: Beim Start der Anwendung lädt webforJ alle Klassen, die in `META-INF/services/com.webforj.error.ErrorHandler` aufgeführt sind.
3. **Fehlerbehandlung**: Wenn eine Ausnahme auftritt, überprüft webforJ, ob ein Fehlerbehandler für diese spezielle Ausnahme vorhanden ist.

### Auswahl des Behandlers {#handler-selection}

- Wenn ein spezifischer Behandler für die Ausnahme existiert, wird er verwendet.
- Wenn kein spezifischer Behandler gefunden wird, aber ein benutzerdefinierter globaler Fehlerbehandler `WebforjGlobalErrorHandler` definiert ist, wird dieser verwendet.
- Wenn weder das eine noch das andere gefunden wird, wird der Standard-`GlobalErrorHandler` verwendet.

## Das `ErrorHandler`-Interface {#the-errorhandler-interface}

Das `ErrorHandler`-Interface ist dafür konzipiert, Fehler zu behandeln, die während der Ausführung einer webforJ-Anwendung auftreten. Anwendungen, die bestimmte Ausnahmen verwalten möchten, sollten dieses Interface implementieren.

### Methoden {#methods}

- **`onError(Throwable throwable, boolean debug)`**: Wird aufgerufen, wenn ein Fehler auftritt. Diese Methode sollte die Logik zur Behandlung der Ausnahme enthalten.
- **`showErrorPage(String title, String content)`**: Eine Standardmethode, die die Fehlerseite mit dem angegebenen Titel und Inhalt anzeigt.

### Namenskonvention {#naming-convention}

Die implementierende Klasse muss nach der Ausnahme benannt werden, die sie behandelt, mit dem Suffix `ErrorHandler`. Zum Beispiel sollte die Klasse zur Behandlung von `NullPointerException` `NullPointerExceptionErrorHandler` genannt werden.

### Registrierung {#registration}

Der benutzerdefinierte Fehlerbehandler muss in der Datei `META-INF/services/com.webforj.error.ErrorHandler` registriert werden, damit webforJ ihn entdecken und nutzen kann.

## Implementierung eines benutzerdefinierten Fehlerbehandlers {#implementing-a-custom-error-handler}

Die folgenden Schritte erläutern die Implementierung eines benutzerdefinierten Fehlerbehandlers für eine bestimmte Ausnahme:

### Schritt 1: Erstellen Sie die Fehlerbehandler-Klasse {#step-1-create-the-error-handler-class}

Erstellen Sie eine neue Klasse, die das `ErrorHandler`-Interface implementiert und nach der Ausnahme benannt ist, die sie behandelt.

```java
package com.example.error;

import com.webforj.error.ErrorHandler;

public class NullPointerExceptionErrorHandler implements ErrorHandler {

  @Override
  public void onError(Throwable throwable, boolean debug) {
    // Benutzerdefinierte Behandlungslogik für NullPointerException
    String title = "Null Pointer Exception";
    String content = "Ein null-Wert wurde an einer Stelle gefunden, an der ein Objekt erforderlich ist.";

    showErrorPage(title, content);
  }
}
```

:::info `showErrorPage()`-Methode
Die `showErrorPage`-Methode ist eine Dienstprogramm-Methode, die die webforJ-API nutzt, um den bereitgestellten HTML-Inhalt und die Seiteneinstellung an den Browser zu senden und eine Fehlerseite anzuzeigen. Wenn eine Ausnahme auftritt und die Anwendung nicht in der Lage ist, sich zu erholen, wird es unmöglich, webforJ-Komponenten zu nutzen, um eine benutzerdefinierte Fehlerseite zu erstellen. Die `Page`-API bleibt jedoch zugänglich, sodass der Entwickler eine Umleitung vornehmen oder eine Fehlerseite als letzten Versuch anzeigen kann.
:::

### Schritt 2: Registrieren Sie den Fehlerbehandler {#step-2-register-the-error-handler}

Erstellen Sie eine Datei mit dem Namen `com.webforj.error.ErrorHandler` im Verzeichnis `META-INF/services` Ihrer Anwendung. Fügen Sie den vollständig qualifizierten Namen Ihrer Fehlerbehandler-Klasse zu dieser Datei hinzu.

**Datei**: `META-INF/services/com.webforj.error.ErrorHandler`

```
com.example.error.NullPointerExceptionErrorHandler
```

Jetzt, wann immer eine `NullPointerException` geworfen wird, wählt webforJ Ihren registrierten Behandler aus und führt seine Logik zur Fehlerbehandlung aus.

## Verwendung von `AutoService` zur Vereinfachung der Registrierung {#using-autoservice-to-simplify-registration}

Es ist einfach für Entwickler, zu vergessen, die Dienstbeschreibungen zu aktualisieren oder korrekt anzugeben. Mit Googles `AutoService` können Sie die Generierung der Datei `META-INF/services/com.webforj.error.ErrorHandler` automatisieren. Alles, was Sie tun müssen, ist, den Fehlerbehandler mit der Annotation `AutoService` zu versehen. Weitere Informationen zu [AutoService finden Sie hier](https://github.com/google/auto/blob/main/service/README.md).

```java
@AutoService(ErrorHandler.class)
public class NullPointerExceptionErrorHandler implements ErrorHandler {

  @Override
  public void onError(Throwable throwable, boolean debug) {
    // Benutzerdefinierte Behandlungslogik für NullPointerException
    String title = "Null Pointer Exception";
    String content = "Ein null-Wert wurde an einer Stelle gefunden, an der ein Objekt erforderlich ist.";

    showErrorPage(title, content);
  }
}
```

## Die `GlobalErrorHandler`-Klasse {#the-globalerrorhandler-class}

Der `GlobalErrorHandler` ist der Standardfehlerbehandler, der von webforJ bereitgestellt wird. Er implementiert das `ErrorHandler`-Interface und bietet eine generische Fehlerbehandlung.

### Verhalten {#behavior}

- **Protokollierung**: Fehler werden sowohl in den Server- als auch in den Browserkonsolen protokolliert.
- **Anzeigen der Fehlerseite**: Je nach Debug-Modus wird die Fehlerseite mit dem Stack-Trace oder einer allgemeinen Fehlermeldung angezeigt.

### Definieren eines benutzerdefinierten globalen Fehlerbehandlers {#defining-a-custom-global-error-handler}

Um einen globalen Fehlerbehandler zu definieren, müssen Sie einen neuen Fehlerbehandler mit dem Namen `WebforjGlobalErrorHandler` erstellen und dann die [Schritte zur Registrierung von Fehlerbehandlern](#step-2-register-the-error-handler) wie zuvor erklärt befolgen. In diesem Fall sucht webforJ zuerst nach benutzerdefinierten Fehlerbehandlern, um Ausnahmen zu verwalten. Wenn keine gefunden werden, fällt webforJ auf den benutzerdefinierten globalen Fehlerbehandler zurück.

:::info
Wenn mehrere `WebforjGlobalErrorHandler` registriert sind, wählt webforJ den ersten aus.
:::
