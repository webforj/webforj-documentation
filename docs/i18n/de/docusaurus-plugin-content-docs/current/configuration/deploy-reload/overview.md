---
title: Redeployment and Live Reload
hide_table_of_contents: false
hide_giscus_comments: true
_i18n_hash: d98e2e989be9fbc147c128f8c46f905e
---
Effiziente Entwicklungsworkflows basieren auf Werkzeugen, die Codeänderungen erkennen und die App automatisch in Echtzeit aktualisieren. Continuous Deployment und Dynamic Reload arbeiten zusammen, um den Entwicklungsprozess zu vereinfachen, indem manuelle Schritte reduziert werden, sodass Sie Ihre Änderungen schnell sehen können, ohne den Server manuell neu starten zu müssen.

## Neuverteilung {#redeployment}

Neuverteilung in der Java-Entwicklung bezieht sich auf die automatische Erkennung und Bereitstellung von Codeänderungen, sodass Aktualisierungen in der App ohne einen manuellen Serverneustart angezeigt werden. Dieser Prozess umfasst typischerweise die Aktualisierung von Java-Klassen und Webressourcen in Echtzeit.

In einer webforJ-App bedeutet dies, dass die WAR-Datei bei jeder Änderung des Codes neu generiert wird.

Änderungen an Java-Klassen und Ressourcen im Klassenpfad werden normalerweise von der IDE überwacht. Wenn eine Java-Klasse geändert wird und die Datei gespeichert wird, entweder automatisch von der IDE oder manuell vom Entwickler, werden diese Werkzeuge aktiviert, um die aktualisierten Klassendateien im Zielverzeichnis zu kompilieren und anzuwenden.

Werkzeuge und Einstellungen, die das automatisierte oder optimierte Neuladen des Browsers ermöglichen, können hinzugefügt werden, um ein nahtloses Erlebnis zu bieten.

## Live-Neuladen {#live-reload}

Live-Neuladen stellt sicher, dass, sobald Änderungen bereitgestellt wurden, der Browser diese Updates in Echtzeit widerspiegelt, ohne dass ein manuelles Aktualisieren des Browsers erforderlich ist.

In einer webforJ-App kann das Live-Neuladen die Ansicht automatisch aktualisieren und Komponenten neu rendern, um den neuesten Zustand der App anzuzeigen, oder sogar Änderungen patchen, wenn sie nach Bedarf gebraucht werden.

## Themen {#topics}

<DocCardList className="topics-section" />
