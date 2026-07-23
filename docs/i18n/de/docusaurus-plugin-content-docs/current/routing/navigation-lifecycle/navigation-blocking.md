---
sidebar_position: 3
title: Navigation Blocking
description: >-
  Intercept navigation with WillLeaveObserver veto handlers and the beforeunload
  event to guard unsaved changes.
_i18n_hash: 0deeb3e0583fdd425fe2a604ee1e9164
---
Die Navigationseinschränkung fügt der gesamten zugrunde liegenden Router-API eine oder mehrere Kontrollstufen hinzu. Wenn Einschränkungsbehandler vorhanden sind, wird die Navigation wie folgt verhindert:

Wenn die Navigation durch etwas ausgelöst wird, das auf Router-Ebene gesteuert wird, können Sie eine beliebige Aufgabe ausführen oder eine Benutzeroberfläche anzeigen, um den Benutzer zur Bestätigung der Aktion aufzufordern. Jede Komponente, die den `WillLeaveObserver` im [Routenbaum](../route-hierarchy/overview) implementiert, wird aufgerufen. Der Implementierer muss `accept` aufrufen, um die Navigation fortzusetzen, oder `reject`, um sie zu blockieren. Wenn mehrere Komponenten den `WillLeaveObserver` im Routenbaum implementieren, werden die Veto-Behandler der Reihe nach in umgekehrter Reihenfolge ausgeführt.

:::info Praktisches Beispiel für Veto-Handhabung
Um zu sehen, wie das Veto in der Praxis funktioniert, beachten Sie die [Beispiele für die Verwendung von Lebenszyklusbeobachtern](observers#example-handling-unsaved-changes-with-willleaveobserver).
:::

Für Seitenereignisse, die nicht direkt kontrolliert werden können, greift der Router nicht ein und erzwingt kein bestimmtes Verhalten. Entwickler können jedoch dennoch das [`beforeunload`](https://developer.mozilla.org/en-US/docs/Web/API/Window/beforeunload_event)-Ereignis abhören, um gegebenenfalls einen letzten Versuch zu unternehmen, den Benutzer über nicht gespeicherte Daten zu warnen.

```java
PageEventOptions options = new PageEventOptions();
options.setCode("""
  event.preventDefault();
  return true;
  """);
Page.getCurrent().addEventListener("beforeunload", e -> {}, options);
```

## Zurück-Button im Browser {#browser-back-button}

Der Zurück-Button funktioniert außerhalb der Kontrolle von Webanwendungen, was es schwierig macht, seine Aktion in allen Browsern konsistent abzufangen oder zu verhindern. Statt zu versuchen, den Zurück-Button zu blockieren, ist es effektiver, Ihre UI/UX so zu gestalten, dass die Auswirkungen gemildert werden. Erwägen Sie Strategien wie das Speichern nicht gespeicherter Daten im [Sitzungsspeicher](../../advanced/web-storage#session-storage), damit der Fortschritt eines Benutzers sicher wiederhergestellt wird, wenn er die Seite verlässt und zurückkehrt. Dieser Ansatz gewährleistet den Datenschutz, ohne sich auf unzuverlässiges Browserverhalten zu verlassen.
