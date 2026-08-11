---
title: Error Handling
sidebar_position: 5
description: >-
  Register custom ErrorHandler implementations through the Java Service Provider
  Interface to respond to specific exceptions in webforJ apps.
_i18n_hash: 55731ba6ae9454377d363fa461c817bc
---
Fehlerbehandlung ist ein entscheidender Aspekt bei der Entwicklung zuverlässiger Webanwendungen. In webforJ ist die Fehlerbehandlung so konzipiert, dass sie flexibel und anpassbar ist, sodass Entwickler Ausnahmen auf die Weise behandeln können, die am besten den Anforderungen ihrer Anwendung entspricht.

## Übersicht {#overview}

In webforJ dreht sich die Fehlerbehandlung um das `ErrorHandler`-Interface. Dieses Interface ermöglicht es Entwicklern, zu definieren, wie ihre Anwendung reagieren soll, wenn während der Ausführung Ausnahmen auftreten. Standardmäßig bietet webforJ einen `GlobalErrorHandler`, der alle Ausnahmen auf generische Weise behandelt. Entwickler können jedoch benutzerdefinierte Fehlerhandler für spezifische Ausnahmen erstellen, um maßgeschneiderte Antworten zu geben.

## Entdeckung und Verwendung von Fehlerhandlern {#discovering-and-using-error-handlers}

webforJ nutzt die Service Provider Interface (SPI) von Java, um Fehlerhandler zu entdecken und zu laden.

### Entdeckungsprozess {#discovery-process}

1. **Service-Registrierung**: Fehlerhandler werden über den `META-INF/services`-Mechanismus registriert.
2. **Service-Laden**: Beim Start der Anwendung lädt webforJ alle Klassen, die in `META-INF/services/com.webforj.error.ErrorHandler` aufgeführt sind.
3. **Fehlerbehandlung**: Wenn eine Ausnahme auftritt, überprüft webforJ, ob ein Fehlerhandler für diese spezifische Ausnahme existiert.

### Handler-Auswahl {#handler-selection}

- Wenn ein spezifischer Handler für die Ausnahme existiert, wird dieser verwendet.
- Wenn kein spezifischer Handler gefunden wird, aber ein benutzerdefinierter globaler Fehlerhandler `WebforjGlobalErrorHandler` definiert ist, wird dieser verwendet.
- Wenn keiner gefunden wird, wird der Standard-`GlobalErrorHandler` verwendet.

## Das `ErrorHandler`-Interface {#the-errorhandler-interface}

Das `ErrorHandler`-Interface ist dafür konzipiert, Fehler zu behandeln, die während der Ausführung einer webforJ-Anwendung auftreten. Anwendungen, die spezifische Ausnahmen verwalten möchten, sollten dieses Interface implementieren.

### Methoden {#methods}

- **`onError(Throwable throwable, boolean debug)`**: Wird aufgerufen, wenn ein Fehler auftritt. Diese Methode sollte die Logik zur Behandlung der Ausnahme enthalten.
- **`showErrorPage(String title, String content)`**: Eine Standardmethode, die die Fehlerseite mit dem angegebenen Titel und Inhalt anzeigt.

### Namenskonvention {#naming-convention}

Die implementierende Klasse muss nach der Ausnahme benannt werden, die sie behandelt, mit dem Suffix `ErrorHandler`. Zum Beispiel sollte die Klasse zur Behandlung von `NullPointerException` `NullPointerExceptionErrorHandler` heißen.

### Registrierung {#registration}

Der benutzerdefinierte Fehlerhandler muss in der Datei `META-INF/services/com.webforj.error.ErrorHandler` registriert werden, damit webforJ ihn entdecken und verwenden kann.

## Implementierung eines benutzerdefinierten Fehlerhandlers {#implementing-a-custom-error-handler}

Die folgenden Schritte beschreiben die Implementierung eines benutzerdefinierten Fehlermanagers für eine spezifische Ausnahme:

### Schritt 1: Erstellen der Fehlerhandler-Klasse {#step-1-create-the-error-handler-class}

Erstellen Sie eine neue Klasse, die das `ErrorHandler`-Interface implementiert und nach der Ausnahme benannt ist, die sie behandelt.

```java
package com.example.error;

import com.webforj.error.ErrorHandler;

public class NullPointerExceptionErrorHandler implements ErrorHandler {

  @Override
  public void onError(Throwable throwable, boolean debug) {
    // Benutzerdefinierte Behandlungslogik für NullPointerException
    String title = "Null Pointer Exception";
    String content = "Ein null-Wert wurde dort gefunden, wo ein Objekt erforderlich ist.";

    showErrorPage(title, content);
  }
}
```

:::info `showErrorPage()`-Methode
Die Methode `showErrorPage` ist eine Hilfsmethode, die die webforJ-API verwendet, um den bereitgestellten HTML-Inhalt und den Seitentitel an den Browser zu senden, wodurch eine Fehlerseite angezeigt wird. Wenn eine Ausnahme auftritt und die Anwendung sich nicht erholen kann, ist es unmöglich, webforJ-Komponenten zum Erstellen einer benutzerdefinierten Fehlerseite zu verwenden. Die `Page`-API bleibt jedoch zugänglich, sodass der Entwickler umleiten oder eine Fehlerseite als letzten Versuch anzeigen kann.
:::

### Schritt 2: Registrieren des Fehlerhandlers {#step-2-register-the-error-handler}

Erstellen Sie eine Datei mit dem Namen `com.webforj.error.ErrorHandler` im Verzeichnis `META-INF/services` Ihrer Anwendung. Fügen Sie den vollqualifizierten Namen Ihrer Fehlerhandler-Klasse zu dieser Datei hinzu.

**Datei**: `META-INF/services/com.webforj.error.ErrorHandler`

```
com.example.error.NullPointerExceptionErrorHandler
```

Jetzt, wann immer eine `NullPointerException` ausgelöst wird, wählt webforJ Ihren registrierten Handler aus und führt dessen Logik zur Fehlerbehandlung aus.

## Verwendung von `AutoService`, um die Registrierung zu vereinfachen {#using-autoservice-to-simplify-registration}

Es ist einfach für Entwickler, zu vergessen, die Dienstbeschreibungen zu aktualisieren oder korrekt anzugeben. Mit Googles `AutoService` können Sie die Generierung der Datei `META-INF/services/com.webforj.error.ErrorHandler` automatisieren. Alles, was Sie tun müssen, ist, den Fehlerhandler mit der `AutoService`-Annotation zu versehen. Weitere Informationen zu [AutoService finden Sie hier](https://github.com/google/auto/blob/main/service/README.md).

```java
@AutoService(ErrorHandler.class)
public class NullPointerExceptionErrorHandler implements ErrorHandler {

  @Override
  public void onError(Throwable throwable, boolean debug) {
    // Benutzerdefinierte Behandlungslogik für NullPointerException
    String title = "Null Pointer Exception";
    String content = "Ein null-Wert wurde dort gefunden, wo ein Objekt erforderlich ist.";

    showErrorPage(title, content);
  }
}
```

## Die `GlobalErrorHandler`-Klasse {#the-globalerrorhandler-class}

Der `GlobalErrorHandler` ist der standardmäßige Fehlerhandler, der von webforJ bereitgestellt wird. Er implementiert das `ErrorHandler`-Interface und bietet generische Fehlerbehandlung.

### Verhalten {#behavior}

- **Protokollierung**: Fehler werden sowohl in den Server- als auch in den Browserkonsolen protokolliert.
- **Anzeige der Fehlerseite**: Je nach Debug-Modus zeigt die Fehlerseite den Stack-Trace oder eine generische Fehlermeldung an.

### Definition eines benutzerdefinierten globalen Fehlerhandlers {#defining-a-custom-global-error-handler}

Um einen globalen Fehlerhandler zu definieren, müssen Sie einen neuen Fehlerhandler mit dem Namen `WebforjGlobalErrorHandler` erstellen. Dann folgen Sie [den Schritten zur Registrierung von Fehlerhandlern](#step-2-register-the-error-handler), wie zuvor erklärt. In diesem Fall sucht webforJ zunächst nach benutzerdefinierten Fehlerhandlern zur Verwaltung von Ausnahmen. Wenn keine gefunden werden, greift webforJ auf den benutzerdefinierten globalen Fehlerhandler zurück.

:::info
Wenn mehrere `WebforjGlobalErrorHandler` registriert sind, wählt webforJ den ersten aus.
:::
