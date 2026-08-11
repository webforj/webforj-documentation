---
title: JRebel
description: >-
  Use JRebel with webforJ to hot-swap modified classes into a running Jetty
  server and skip full restarts during development.
_i18n_hash: 639c97ac6892efd7261824c13b7162da
---
JRebel ist ein Java-Entwicklungstool, das mit der JVM integriert ist, um Codeänderungen zu erkennen und modifizierte Klassen direkt im Speicher zu ersetzen, sodass Entwickler Änderungen sofort sehen können, ohne den Server neu zu starten.

Wenn eine Änderung an einer Klasse, Methode oder einem Feld vorgenommen wird, kompiliert JRebel den aktualisierten Bytecode in Echtzeit und injiziert ihn, wodurch die Notwendigkeit eines vollständigen Serverneustarts entfällt. Durch die direkte Anwendung von Änderungen auf die laufende Anwendung optimiert JRebel den Entwicklungsworkflow, spart Zeit und bewahrt den Anwendungszustand, einschließlich Benutzersitzungen.

:::tip Frontend-Änderungen
Änderungen im `src/main/frontend` werden vom [Frontend-Watch](/docs/configuration/deploy-reload/frontend-watch) behandelt, der sie neu baut und den Browser zusammen mit dem Server aktualisiert.
:::

## Installation {#installation}

Die offizielle JRebel-Website bietet [schnelle Startanleitungen](https://www.jrebel.com/products/jrebel/learn), um das Produkt in verschiedenen beliebten IDEs in Gang zu bringen. Befolgen Sie diese Anleitungen, um JRebel in Ihre Entwicklungsumgebung zu integrieren.

Nach Abschluss der Einrichtung öffnen Sie ein webforJ-Projekt und stellen Sie sicher, dass die Jetty-Eigenschaft `scan` in der `pom.xml` Datei auf `0` eingestellt ist, um den automatischen Neustart des Servers zu deaktivieren. Sobald dies erledigt ist, verwenden Sie den folgenden Befehl:

```bash
mvn jetty:run
```

Wenn alles richtig gemacht wurde, gibt JRebel Protokollinformationen im Terminal aus, und Änderungen, die Sie an Ihrem Programm vornehmen, sollten nach Bedarf angezeigt werden.

:::info Ihre Änderungen sehen
Wenn eine Änderung an einer Ansicht oder Komponente vorgenommen wird, die bereits angezeigt wird, wird JRebel keinen Seitenneuladen erzwingen, da der Server nicht neu gestartet wird.
:::
