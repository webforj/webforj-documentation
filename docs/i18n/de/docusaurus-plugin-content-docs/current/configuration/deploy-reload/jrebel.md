---
title: JRebel
description: >-
  Use JRebel with webforJ to hot-swap modified classes into a running Jetty
  server and skip full restarts during development.
_i18n_hash: dfb60fdaf7a9ffd31ee6fb920e0e8289
---
JRebel ist ein Java-Entwicklungstool, das sich in die JVM integriert, um Codeänderungen zu erkennen und modifizierte Klassen direkt im Speicher zu ersetzen, sodass Entwickler Codeänderungen sofort sehen können, ohne den Server neu zu starten.

Wenn eine Änderung an einer Klasse, Methode oder einem Feld vorgenommen wird, kompiliert JRebel den aktualisierten Bytecode im laufenden Betrieb und injiziert ihn, wodurch die Notwendigkeit eines vollständigen Serverneustarts entfällt. Durch die direkte Anwendung von Änderungen auf die laufende Anwendung optimiert JRebel den Entwicklungsworkflow, spart Zeit und bewahrt den Anwendungszustand, einschließlich der Benutzersitzungen.

:::tip Frontend-Änderungen
Änderungen unter `src/main/frontend` werden von der [Frontend-Überwachung](/docs/configuration/deploy-reload/frontend-watch) verarbeitet, die sie neu erstellt und den Browser zusammen mit dem Server aktualisiert.
:::

## Installation {#installation}

Die offizielle JRebel-Website bietet [schnelle Startanleitungen](https://www.jrebel.com/products/jrebel/learn), um das Produkt in verschiedenen beliebten IDEs zum Laufen zu bringen. Befolgen Sie diese Anweisungen, um JRebel in Ihre Entwicklungsumgebung zu integrieren.

Nachdem die Einrichtung abgeschlossen ist, öffnen Sie ein webforJ-Projekt und stellen Sie sicher, dass die Jetty `scan`-Eigenschaft in der `pom.xml`-Datei auf `0` gesetzt ist, um den automatischen Neustart des Servers zu deaktivieren. Sobald dies erledigt ist, verwenden Sie den folgenden Befehl:

```bash
mvn jetty:run
```

Wenn alles korrekt durchgeführt wurde, gibt JRebel Protokollinformationen im Terminal aus, und Änderungen, die an Ihrem Programm vorgenommen werden, sollten auf Abruf sichtbar sein.

:::info Ihre Änderungen sehen
Wenn eine Änderung an einer Ansicht oder Komponente vorgenommen wird, die bereits angezeigt wird, zwingt JRebel nicht zu einem Neuladen der Seite, da der Server nicht neu gestartet wird.
:::
