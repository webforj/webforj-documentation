---
title: Redeployment and Live Reload
hide_table_of_contents: false
hide_giscus_comments: true
description: >-
  Combine automatic redeployment with live browser reload so code changes appear
  in a running webforJ app without manual restarts.
_i18n_hash: dc1833dbce97bdbf387e98fab07967ca
---
Effiziente Entwicklungsabläufe basieren auf Tools, die Codeänderungen erkennen und die App automatisch in Echtzeit aktualisieren. Continuous Deployment und Dynamic Reload arbeiten zusammen, um den Entwicklungsprozess zu vereinfachen, indem manuelle Schritte reduziert werden, sodass Sie Ihre Änderungen schnell sehen können, ohne den Server manuell neu starten zu müssen.

## Redeployment {#redeployment}

Redeployment in der Java-Entwicklung bezieht sich auf die automatische Erkennung und Bereitstellung von Codeänderungen, sodass Aktualisierungen in der App ohne einen manuellen Serverneustart reflektiert werden. Dieser Prozess umfasst typischerweise die Aktualisierung von Java-Klassen und Webressourcen on the fly.

In einer webforJ-App bedeutet dies, dass die WAR-Datei immer dann neu generiert wird, wenn Änderungen am Code vorgenommen werden.

Änderungen an Java-Klassen und Ressourcen im Classpath werden typischerweise von der IDE überwacht. Wenn eine Java-Klasse geändert und die Datei gespeichert wird, entweder von der IDE automatisch oder manuell vom Entwickler, werden diese Tools aktiviert, um die aktualisierten Klassendateien im Zielverzeichnis zu kompilieren und die Änderungen anzuwenden.

Für das beste Erlebnis verwenden Sie automatisches Redeployment in Kombination mit Tools oder Einstellungen, die das Neuladen des Browsers automatisieren.

## Live reload {#live-reload}

Sobald Änderungen bereitgestellt werden, lädt live reload die App automatisch neu, sodass der Browser die Aktualisierungen sofort widerspiegelt, ohne dass ein manueller Neuladevorgang erforderlich ist.

In einer webforJ-App kann live reload die Ansicht automatisch aktualisieren und die Komponenten neu rendern, um den neuesten Zustand der App zu zeigen oder sogar Änderungen bei Bedarf on demand zu patchen.

Für Frontend-Quellen rebuildet der [frontend watch](/docs/configuration/deploy-reload/frontend-watch) bei jeder Änderung und patcht ein Stylesheet oder Bild an Ort und Stelle, wobei die Ansicht nur aktualisiert wird, wenn ein Skript geändert wird.

## Themen {#topics}

<DocCardList className="topics-section" />
