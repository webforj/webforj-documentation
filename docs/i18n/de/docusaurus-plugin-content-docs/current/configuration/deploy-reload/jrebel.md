---
title: JRebel
_i18n_hash: 9e2b7ce994eb40e656cf61dc4967ec7e
---
JRebel ist ein Java-Entwicklungstool, das mit der JVM integriert ist, um Codeänderungen zu erkennen und modifizierte Klassen direkt im Speicher zu ersetzen, sodass Entwickler Codeänderungen sofort sehen können, ohne den Server neu zu starten.

Wenn eine Änderung an einer Klasse, Methode oder einem Feld vorgenommen wird, kompiliert JRebel den aktualisierten Bytecode dynamisch und injiziert ihn, wodurch die Notwendigkeit eines vollständigen Serverneustarts entfällt. Durch die direkte Anwendung von Änderungen auf die laufende App optimiert JRebel den Entwicklungsworkflow, spart Zeit und erhält den Zustand der App, einschließlich Benutzersitzungen.

## Installation {#installation}

Die offizielle JRebel-Website bietet [schnelle Startanweisungen](https://www.jrebel.com/products/jrebel/learn), um das Produkt in verschiedenen beliebten IDEs zum Laufen zu bringen. Befolgen Sie diese Anweisungen, um JRebel in Ihre Entwicklungsumgebung zu integrieren.

Nach Abschluss der Einrichtung öffnen Sie ein webforJ-Projekt und stellen sicher, dass die jetty `scan`-Eigenschaft in der `pom.xml`-Datei auf `0` gesetzt ist, um den automatischen Neustart des Servers zu deaktivieren. Sobald dies erledigt ist, verwenden Sie den folgenden Befehl:

```bash
mvn jetty:run
```

Wenn alles richtig gemacht wurde, gibt JRebel Protokollinformationen an das Terminal aus, und Änderungen, die an Ihrem Programm vorgenommen werden, sollten auf Abruf reflektiert werden.

:::info Änderungen sehen
Wenn eine Änderung an einer Ansicht oder einem Komponenten vorgenommen wird, die bereits angezeigt wird, zwingt JRebel nicht zu einem Neuladen der Seite, da der Server nicht neu gestartet wird.
:::
