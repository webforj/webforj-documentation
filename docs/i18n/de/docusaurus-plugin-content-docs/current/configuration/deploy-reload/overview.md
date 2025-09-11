---
title: Redeployment and Live Reload
hide_table_of_contents: false
hide_giscus_comments: true
_i18n_hash: 2e8bf7fded04e11ec6bab6d8a7c1c2b5
---
Effiziente Entwicklungs-Workflows verlassen sich auf Tools, die Codeänderungen erkennen und die App in Echtzeit automatisch aktualisieren. Continuous Deployment und Dynamic Reload arbeiten zusammen, um den Entwicklungsprozess zu vereinfachen, indem manuelle Schritte reduziert werden. So können Sie Ihre Änderungen schnell sehen, ohne den Server manuell neu starten zu müssen.

## Neuimplementierung {#redeployment}

Neuimplementierung in der Java-Entwicklung bezieht sich auf das automatische Erkennen und Bereitstellen von Codeänderungen, sodass Aktualisierungen in der App angezeigt werden, ohne dass ein manueller Serverneustart erforderlich ist. Dieser Prozess umfasst typischerweise das Aktualisieren von Java-Klassen und Webressourcen im laufenden Betrieb.

In einer webforJ-App bedeutet dies, dass die WAR-Datei jedes Mal neu generiert wird, wenn Änderungen am Code vorgenommen werden.

Änderungen an Java-Klassen und Ressourcen im Klassenpfad werden typischerweise von der IDE überwacht. Wenn eine Java-Klasse geändert und die Datei gespeichert wird, entweder automatisch durch die IDE oder manuell durch den Entwickler, werden diese Tools aktiv, um die aktualisierten Klassendateien im Zielverzeichnis zu kompilieren und anzuwenden.

Tools und Einstellungen, die das automatische oder optimierte Browser-Neuladen ermöglichen, können für ein nahtloseres Erlebnis hinzugefügt werden.

## Live-Neuladen {#live-reload}

Live-Neuladen stellt sicher, dass, sobald Änderungen bereitgestellt werden, der Browser diese Aktualisierungen in Echtzeit anzeigt, ohne dass eine manuelle Aktualisierung des Browsers erforderlich ist.

In einer webforJ-App kann das Live-Neuladen die Ansicht automatisch aktualisieren und Komponenten neu rendern, um den neuesten Status der App anzuzeigen oder sogar Änderungen bei Bedarf sofort anzuwenden.

## Themen {#topics}

<DocCardList className="topics-section" />
