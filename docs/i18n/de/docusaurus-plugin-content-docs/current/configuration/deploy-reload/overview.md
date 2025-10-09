---
title: Redeployment and Live Reload
hide_table_of_contents: false
hide_giscus_comments: true
sidebar_class_name: has-new-content
_i18n_hash: 9b0d2672241250200ed14343e57d3926
---
Effiziente Entwicklungsworkflows stützen sich auf Werkzeuge, die Codeänderungen erkennen und die App in Echtzeit automatisch aktualisieren. Continuous Deployment und Dynamic Reload arbeiten zusammen, um den Entwicklungsprozess zu vereinfachen, indem manuelle Schritte reduziert werden, sodass Sie Ihre Änderungen schnell sehen können, ohne den Server manuell neu starten zu müssen.

## Redeployment {#redeployment}

Redeployment in der Java-Entwicklung bezieht sich auf das automatische Erkennen und Bereitstellen von Codeänderungen, sodass Updates in der App ohne einen manuellen Serverneustart widergespiegelt werden. Dieser Prozess umfasst typischerweise die Aktualisierung von Java-Klassen und Webressourcen im laufenden Betrieb.

In einer webforJ-App bedeutet dies, dass die WAR-Datei jedes Mal neu generiert wird, wenn Änderungen am Code vorgenommen werden.

Änderungen an Java-Klassen und Ressourcen im Klassenpfad werden typischerweise von der IDE überwacht. Wenn eine Java-Klasse geändert wird und die Datei gespeichert wird, entweder automatisch von der IDE oder manuell vom Entwickler, treten diese Werkzeuge in Kraft, um die aktualisierten Klassendateien im Zielverzeichnis zu kompilieren und anzuwenden.

Für die beste Erfahrung verwenden Sie automatisches Redeployment in Kombination mit Werkzeugen oder Einstellungen, die das Neuladen des Browsers automatisieren.

## Live reload {#live-reload}

Sobald Änderungen bereitgestellt werden, lädt live reload die App automatisch neu, sodass der Browser die Updates sofort widerspiegelt, ohne dass ein manuelles Aktualisieren des Browsers erforderlich ist.

In einer webforJ-App kann live reload die Ansicht automatisch aktualisieren, Komponenten neu rendern, um den neuesten Zustand der App anzuzeigen, oder sogar Änderungen nach Bedarf auf Abruf patchen.

## Themen {#topics}

<DocCardList className="topics-section" />
