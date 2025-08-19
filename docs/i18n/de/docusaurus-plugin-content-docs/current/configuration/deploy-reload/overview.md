---
title: Redeployment and Live Reload
hide_table_of_contents: false
hide_giscus_comments: true
_i18n_hash: ec300e413c9fab01c4723f90f0e4c532
---
Effiziente Entwicklungsabläufe basieren auf Tools, die Codeänderungen erkennen und die App automatisch in Echtzeit aktualisieren. Continuous Deployment und Dynamic Reload arbeiten zusammen, um den Entwicklungsprozess zu vereinfachen, indem manuelle Schritte reduziert werden, sodass Sie Ihre Änderungen schnell sehen können, ohne den Server manuell neu starten zu müssen.

## Neuverteilung {#redeployment}

Neuverteilung in der Java-Entwicklung bezieht sich auf das automatische Erkennen und Bereitstellen von Codeänderungen, sodass Aktualisierungen ohne einen manuellen Serverneustart in der App angezeigt werden. Dieser Prozess umfasst typischerweise die Aktualisierung von Java-Klassen und Web-Ressourcen im laufenden Betrieb.

In einer webforJ-App bedeutet dies, dass die WAR-Datei bei jeder Änderung des Codes neu generiert wird.

Änderungen an Java-Klassen und Ressourcen im Klassenpfad werden normalerweise von der IDE überwacht. Wenn eine Java-Klasse geändert und die Datei gespeichert wird, entweder automatisch durch die IDE oder manuell durch den Entwickler, treten diese Tools in Kraft, um die aktualisierten Klassendateien im Zielverzeichnis zu kompilieren und anzuwenden.

Tools und Einstellungen, die das Browsen von Reloads automatisieren oder optimieren, können für eine nahtlosere Erfahrung hinzugefügt werden.

## Live-Reload {#live-reload}

Live-Reload stellt sicher, dass einmal bereitgestellte Änderungen im Browser in Echtzeit angezeigt werden, ohne dass eine manuelle Aktualisierung des Browsers erforderlich ist.

In einer webforJ-App kann das Live-Reload automatisch die Ansicht aktualisieren, Komponenten neu rendern, um den neuesten Zustand der App anzuzeigen, oder sogar Änderungen nach Bedarf patchen.

## Themen {#topics}

<DocCardList className="topics-section" />
