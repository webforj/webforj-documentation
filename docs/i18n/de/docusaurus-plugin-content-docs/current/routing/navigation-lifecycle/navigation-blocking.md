---
sidebar_position: 3
title: Navigation Blocking
_i18n_hash: c0d79c6ce266eb4b9f9fd28915dcc380
---
Die Navigationseinschränkung fügt eine oder mehrere Schichten der Kontrolle zur gesamten zugrunde liegenden Router-API hinzu. Wenn blockierende Handler vorhanden sind, wird die Navigation wie folgt verhindert:

Wenn die Navigation durch etwas ausgelöst wird, das auf Router-Ebene gesteuert wird, können Sie eine Aufgabe ausführen oder eine UI-Aufforderung anzeigen, um die Aktion zu bestätigen. Jede Komponente, die den `WillLeaveObserver` im [Routenbaum](../route-hierarchy/overview) implementiert, wird aufgerufen. Der Implementierer muss `accept` aufrufen, um die Navigation fortzusetzen, oder `reject`, um sie zu blockieren. Wenn mehrere Komponenten den `WillLeaveObserver` im Routenbaum implementieren, werden die Veto-Handler nacheinander in umgekehrter Reihenfolge ausgeführt.

:::info Praktisches Beispiel für die Veto-Behandlung
Um zu sehen, wie das Veto in der Praxis funktioniert, verweisen Sie auf die [Beispiele zur Verwendung von Lebenszyklusbeobachtern](observers#example-handling-unsaved-changes-with-willleaveobserver).
:::

Für Seitenereignisse, die nicht direkt kontrolliert werden können, greift der Router nicht ein oder erzwingt ein bestimmtes Verhalten. Entwickler können jedoch weiterhin das [`beforeunload`](https://developer.mozilla.org/en-US/docs/Web/API/Window/beforeunload_event) Ereignis abhören, um einen letzten Versuch zu unternehmen, den Benutzer über nicht gespeicherte Daten zu warnen, wenn dies erforderlich ist.

```java
PageEventOptions options = new PageEventOptions();
options.setCode(""" 
  event.preventDefault();
  return true;
  """);
Page.getCurrent().addEventListener("beforeunload", e -> {}, options);
```

## Zurück-Button im Browser {#browser-back-button}

Der Zurück-Button funktioniert außerhalb der Kontrolle von Webanwendungen, wodurch es schwierig ist, dessen Aktion in allen Browsern konsistent abzufangen oder zu verhindern. Anstatt zu versuchen, den Zurück-Button zu blockieren, ist es effektiver, Ihre UI/UX so zu gestalten, dass die Auswirkungen gemildert werden. Ziehen Sie Strategien in Betracht, wie das Speichern nicht gespeicherter Daten im [Session Storage](../../advanced/web-storage#session-storage), sodass der Fortschritt eines Benutzers sicher wiederhergestellt wird, wenn er weg navigiert und zurückkehrt. Dieser Ansatz stellt den Datenschutz sicher, ohne sich auf unzuverlässiges Browserverhalten zu verlassen.
