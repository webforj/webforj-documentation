---
title: Fehlerbehebung
sidebar_position: 11
description: >-
  Fix the common cases where craftforJ doesn't appear, a feature is unavailable,
  or the assistant doesn't answer.
_i18n_hash: fcc5f7188c92523c0fb500bfc7b0ce58
---
### Nichts erscheint auf der Seite {#nothing-appears-on-the-page}

craftforJ wird nur dann aktiviert, wenn alle Anforderungen in [Erste Schritte](/docs/craftforj/getting-started#requirements) erfüllt sind, und es zeigt überhaupt nichts an, wenn eine fehlt. Überprüfen Sie diese der Reihe nach: die Abhängigkeit `webforj-devtools` im Classpath, den Debug-Modus, die craftforJ-Eigenschaft, einen Browser auf dem Gerät, das die App ausführt, und eine gültige Entwicklerlizenz. Eine Konfigurationsdatei an der falschen Stelle oder ein Profil, das eine der Eigenschaften überschreibt, führt genau zum gleichen Ergebnis wie die Eigenschaft, die deaktiviert ist.

### Eine Funktion ist nicht verfügbar {#a-feature-is-unavailable}

craftforJ zeigt eine deaktivierte Funktion an, anstatt sie auszublenden, sodass ein Steuerungselement, das vorhanden, aber als nicht unterstützt markiert ist, absichtlich deaktiviert wurde. Entweder wurde es in der App-Konfiguration mit einem [Funktions-Flag](/docs/craftforj/configuration#feature-flags) deaktiviert, oder die Version von `webforj-devtools` in Ihrem Classpath ist älter.

Auch das Schreiben in die Quelle benötigt ein Projektverzeichnis, das craftforJ finden kann. Überprüfen Sie das, das es in [App-Info](/docs/craftforj/app-info) erkannt hat, und setzen Sie [`project-root`](/docs/craftforj/configuration#project-root), wenn es falsch ist.

### Die Java-Validierung ist schwächer als erwartet {#java-validation-is-weaker-than-expected}

Die [Kompilierungsvalidierung](/docs/craftforj/ai#it-writes-java) des Assistenten benötigt ein JDK. Überprüfen Sie die Java-Version in [App-Info](/docs/craftforj/app-info) und führen Sie die App auf einem JDK und nicht auf einem JRE aus.

### craftforJ sieht nach einem Update veraltet aus {#craftforj-looks-out-of-date-after-an-update}

Ihr Browser hat die vorherige Version im Cache gespeichert. Laden Sie die Seite hart neu oder öffnen Sie die App in einem privaten Fenster. Wenn das Problem weiterhin besteht, überprüfen Sie, welche Version von `webforj-devtools` tatsächlich im Classpath in [App-Info](/docs/craftforj/app-info) ist, da eine alte JAR in Ihrem lokalen Maven-Repository im Browser gleich aussieht.

### Der Assistent antwortet nicht {#the-assistant-doesnt-answer}

Der Assistent benötigt einen konfigurierten Anbieter und ein Modell, das Werkzeuge aufrufen kann. Ein Modell ohne Unterstützung für Werkzeuge kann eine Konversation führen, aber nichts inspizieren oder ändern. Ein lokales Modell, das ständig den Überblick über die Konversation verliert, läuft normalerweise mit einem zu kleinen Kontextfenster.

Wenn ein lokales Modell konfiguriert und erreichbar ist, aber jede Anfrage abgelehnt wird, lehnt der Modellserver die Herkunft der Seite ab. Für Ollama, erlauben Sie die Herkunft und starten Sie es neu:

```bash
launchctl setenv OLLAMA_ORIGINS "*"
pkill ollama && ollama serve
```

Unter Linux setzen Sie `OLLAMA_ORIGINS` in der Umgebung, aus der Ollama gestartet wird, und starten Sie es neu.

### craftforJ sagt, dass die App neu gestartet wird {#craftforj-says-the-app-is-restarting}

Ihre App verschwindet regelmäßig in der Entwicklung, jedes Mal, wenn sie neu aufgebaut wird. craftforJ berichtet, was passiert, anstatt einzufrieren, sodass es anzeigt, wenn die App neu gestartet wird oder die Seite neu lädt, und seine Steuerungselemente bleiben inaktiv, bis die App zurückkehrt. Es verbindet sich automatisch mit Ihrer Auswahl und Ihrer ausstehenden Arbeit, daher gibt es nichts zu tun, außer zu warten. Wenn es meldet, dass es die App überhaupt nicht erreichen kann, bestätigen Sie, dass die App weiterhin läuft und laden Sie die Seite neu.

### Die App startet ständig neu {#the-app-keeps-restarting}

Das Anwenden einer Änderung an der Quelle startet die App neu, wie in [Nachdem Sie anwenden](/docs/craftforj/source-changes#after-you-apply) beschrieben. Neustarts, die ohne eine angewendete Änderung stattfinden, stammen von dem Dateiwächter Ihres Builds und nicht von craftforJ.

### Protokolle sammeln {#collecting-logs}

Bevor Sie ein Problem melden, aktivieren Sie das ausführliche Protokollieren in den craftforJ-Einstellungen, löschen Sie das Protokoll, reproduzieren Sie das Problem, und laden Sie dann das Protokoll herunter. Fügen Sie es zusammen mit den Inhalten von [App-Info](/docs/craftforj/app-info) bei.
