---
title: JRebel
_i18n_hash: e0a60884cfab5835f788e6f225047d2c
---
JRebel ist ein Java-Entwicklungstool, das mit der JVM integriert ist, um Änderungen im Code zu erkennen und modifizierte Klassen direkt im Speicher zu ersetzen, sodass Entwickler Änderungen sofort sehen können, ohne den Server neu zu starten.

Wenn eine Änderung an einer Klasse, Methode oder einem Feld vorgenommen wird, kompiliert JRebel den aktualisierten Bytecode und injiziert ihn dynamisch, wodurch die Notwendigkeit eines vollständigen Serverneustarts entfällt. Durch die direkte Anwendung von Änderungen auf die laufende App optimiert JRebel den Entwicklungsworkflow, spart Zeit und bewahrt den Status der App, einschließlich Benutzersitzungen.

## Installation {#installation}

Die offizielle JRebel-Website bietet [Schnellstartanweisungen](https://www.jrebel.com/products/jrebel/learn), um das Produkt in verschiedenen beliebten IDEs zum Laufen zu bringen. Befolgen Sie diese Anweisungen, um JRebel in Ihre Entwicklungsumgebung zu integrieren.

Nachdem die Einrichtung abgeschlossen ist, öffnen Sie ein webforJ-Projekt und stellen Sie sicher, dass die Jetty-`scan`-Eigenschaft in der `pom.xml`-Datei auf `0` gesetzt ist, um den automatischen Neustart des Servers zu deaktivieren. Sobald dies erledigt ist, verwenden Sie den folgenden Befehl:

```bash
mvn jetty:run
```

Wenn alles richtig gemacht wurde, wird JRebel Protokollinformationen an das Terminal ausgeben, und Änderungen, die an Ihrem Programm vorgenommen wurden, sollten auf Abruf sichtbar sein.

:::info Ihre Änderungen sehen
Wenn eine Änderung an einer Ansicht oder Komponente vorgenommen wird, die bereits angezeigt wird, zwingt JRebel nicht zu einem Neuladen der Seite, da der Server nicht neu gestartet wird.
:::
