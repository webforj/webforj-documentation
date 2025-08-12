---
sidebar_position: 3
title: Navigation Blocking
_i18n_hash: a08d56654914719e12d1401d263c7956
---
Die Navigationseinschränkung fügt der gesamten zugrunde liegenden Router-API eine oder mehrere Ebenen der Kontrolle hinzu. Wenn blockierende Handler vorhanden sind, wird die Navigation wie folgt verhindert:

Wenn die Navigation durch etwas ausgelöst wird, das auf Router-Ebene gesteuert wird, können Sie eine Aufgabe ausführen oder eine UI-Aufforderung an den Benutzer anzeigen, um die Aktion zu bestätigen. Jede Komponente, die das `WillLeaveObserver` im [Routenbaum](../route-hierarchy/overview) implementiert, wird aufgerufen. Der Implementierer muss `accept` aufrufen, um die Navigation fortzusetzen, oder `reject`, um sie zu blockieren. Wenn mehrere Komponenten das `WillLeaveObserver` im Baum der Route implementieren, werden die Veto-Handler sequenziell in umgekehrter Reihenfolge ausgeführt.

:::info Praktisches Beispiel für die Veto-Behandlung
Um zu sehen, wie das Veto in der Praxis funktioniert, beziehen Sie sich auf die [Beispiele zur Verwendung von Lifecycle-Observer](observers#example-handling-unsaved-changes-with-willleaveobserver).
:::

Für Seitenereignisse, die nicht direkt kontrolliert werden können, greift der Router nicht ein und erzwingt kein bestimmtes Verhalten. Entwickler können jedoch weiterhin das [`beforeunload`](https://developer.mozilla.org/en-US/docs/Web/API/Window/beforeunload_event)-Ereignis abonnieren, um einen letzten Versuch zu unternehmen, den Benutzer auf ungespeicherte Daten hinzuweisen, falls erforderlich.

```java
PageEventOptions options = new PageEventOptions();
options.setCode(""" 
  event.preventDefault();
  return true;
  """);
Page.getCurrent().addEventListener("beforeunload", e -> {}, options);
```

## Zurück-Button im Browser {#browser-back-button}

Die Zurück-Taste funktioniert außerhalb der Kontrolle von Webanwendungen, was es schwierig macht, ihre Aktionen konsistent in allen Browsern abzufangen oder zu verhindern. Anstatt zu versuchen, die Zurück-Taste zu blockieren, ist es effektiver, Ihr UI/UX so zu gestalten, dass die Auswirkungen gemildert werden. Erwägen Sie Strategien wie das Speichern ungespeicherter Daten im [Session-Speicher](../../advanced/web-storage#session-storage), damit, wenn ein Benutzer die Seite verlässt und zurückkehrt, sein Fortschritt sicher wiederhergestellt wird. Dieser Ansatz gewährleistet den Datenschutz, ohne sich auf unzuverlässiges Verhalten des Browsers zu verlassen.
