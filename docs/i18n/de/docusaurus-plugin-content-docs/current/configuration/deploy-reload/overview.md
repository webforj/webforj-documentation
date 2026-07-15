---
title: Redeployment and Live Reload
hide_table_of_contents: false
hide_giscus_comments: true
description: >-
  Combine automatic redeployment with live browser reload so code changes appear
  in a running webforJ app without manual restarts.
_i18n_hash: 1b9e4b7fe64a9bcb0aa2aa16b0866ec9
---
Effiziente Entwicklungsarbeitsabläufe beruhen auf Werkzeugen, die Codeänderungen erkennen und die App automatisch in Echtzeit aktualisieren. Continuous Deployment und Dynamic Reload arbeiten zusammen, um den Entwicklungsprozess zu vereinfachen, indem manuelle Schritte reduziert werden, sodass Sie Ihre Änderungen schnell sehen können, ohne den Server manuell neu starten zu müssen.

## Neuinstallation {#redeployment}

Neuinstallation in der Java-Entwicklung bezieht sich auf das automatische Erkennen und Bereitstellen von Codeänderungen, sodass Updates in der App ohne einen manuellen Serverneustart angezeigt werden. Dieser Prozess umfasst typischerweise die Aktualisierung von Java-Klassen und Webressourcen im laufenden Betrieb.

In einer webforJ-App bedeutet dies, dass die WAR-Datei regeneriert wird, wann immer Änderungen am Code vorgenommen werden.

Änderungen an Java-Klassen und Ressourcen im Klassenpfad werden typischerweise von der IDE überwacht. Wenn eine Java-Klasse geändert und die Datei gespeichert wird, entweder automatisch durch die IDE oder manuell durch den Entwickler, treten diese Werkzeuge in Kraft, um die aktualisierten Klassendateien im Zielverzeichnis zu kompilieren und die Änderungen anzuwenden.

Für das beste Erlebnis verwenden Sie die automatische Neuinstallation in Kombination mit Werkzeugen oder Einstellungen, die das Neuladen des Browsers automatisieren.

## Live-Neuladung {#live-reload}

Sobald Änderungen bereitgestellt sind, lädt die Live-Neuladung die App automatisch neu, sodass der Browser die Updates sofort widerspiegelt, ohne dass ein manuelles Aktualisieren des Browsers erforderlich ist.

In einer webforJ-App kann die Live-Neuladung die Ansicht automatisch aktualisieren und Komponenten neu rendern, um den neuesten Status der App anzuzeigen, oder sogar Änderungen nach Bedarf sofort anwenden.

Für Frontend-Quellen erstellt das [frontend watch](/docs/configuration/deploy-reload/frontend-watch) bei jeder Änderung neu und patcht ein Stylesheet oder Bild vor Ort, wobei die Ansicht nur aktualisiert wird, wenn sich ein Skript ändert.

## Themen {#topics}

<DocCardList className="topics-section" />
