---
title: Frontend watch
sidebar_position: 20
sidebar_class_name: new-content
description: >-
  Rebuild frontend sources while a webforJ app runs and refresh the browser,
  with stylesheet and image changes applied in place and script changes
  reloading the view.
_i18n_hash: efb22f8bcac71567979d21178e62ba7c
---
Das Frontend-Überwachungswerkzeug erstellt Ihre `src/main/frontend`-Quellen neu, während die App läuft und der Browser aktualisiert wird, sodass eine Frontend-Änderung ohne manuelles Bauen oder einen Server-Neustart angezeigt wird. Es ist die Entwicklungsseite des [Frontend-Bundlers](/docs/managing-resources/bundler/overview).

## Ausführen der Überwachung {#running-the-watch}

Führen Sie es neben Ihrem Server aus, da es das Ziel vor demjenigen ist, das die App startet. Ein Spring-Projekt legt dies als sein Standardziel fest, sodass `mvn` ohne Argumente beides startet:

```bash
mvn compile webforj:watch spring-boot:run
```

Gegen das [Maven Jetty-Plugin](/docs/configuration/deploy-reload/maven-jetty-plugin) starten Sie es auf die gleiche Weise:

```bash
mvn compile webforj:watch jetty:run
```

Für die Überwachung als Build-Schritt siehe [Build und Tests](/docs/managing-resources/bundler/build-and-tests#the-development-watch).

## Was passiert bei einer Änderung {#what-happens-on-a-change}

Wenn Sie eine Quelle speichern, wird die Überwachung neu gebaut und sendet die geänderten Dateien an den Browser. Was der Browser tut, hängt vom Ergebnis ab, nicht von der Quelle, die Sie bearbeitet haben:

- Ein **Stylesheet** wird vor Ort aktualisiert, ohne Reload, sodass Formulardaten und Scrollposition erhalten bleiben. Eine Bearbeitung von `.css`, `.scss`, `.sass` oder `.less` landet hier.
- Ein **Bild** wird vor Ort ausgetauscht, ohne Reload.
- **Alles andere** lädt die Ansicht neu. Eine Bearbeitung von `.ts`, `.tsx` oder `.js` landet hier, da neues Verhalten eine frische Seite benötigt.

Wenn ein Neubau mehrere Dateien berührt und alle vor Ort aktualisiert werden können, bleibt der Browser stehen. Wenn auch nur eine nicht aktualisiert werden kann, lädt er neu, sodass Sie eine Änderung nie halb sehen.

## Über einen Server-Neustart hinweg {#across-a-server-restart}

Eine Java-Änderung startet den Server neu, über [Spring DevTools](/docs/configuration/deploy-reload/spring-devtools), das [Maven Jetty-Plugin](/docs/configuration/deploy-reload/maven-jetty-plugin) oder [JRebel](/docs/configuration/deploy-reload/jrebel). Die Überwachung hält das Frontend auf dem neuesten Stand:

- **Ihre Stile bleiben während des Neustarts angewendet**, anstatt ungestylt zu flackern.
- **Die Seite lädt neu, wenn die App bereit ist**, nicht vorher, sodass Sie keinen Fehler durch zu frühes Neuladen erhalten. Ein kleines Symbol zeigt an, während der Server neu startet; ein manueller Reload zeigt nichts an.
- **Das Hinzufügen oder Entfernen eines `@BundleEntry` hat beim nächsten Neustart Effekt.**

## Konfiguration {#configuration}

Live-Reload ist eine webforJ-Einstellung, sodass sie auf jedem Server angewendet wird, den Sie ausführen. Eine Spring-App liest diese Schlüssel aus `application.properties`; ein Standalone-Server, wie das [Maven Jetty-Plugin](/docs/configuration/deploy-reload/maven-jetty-plugin), liest dieselben Schlüssel aus `webforj.conf`.

```ini title="application.properties"
# Aktualisieren Sie den Browser bei einer Änderung
webforj.devtools.livereload.enabled=true

# Patches für Stylesheets und Bilder vor Ort statt mit Neuladen (Standard: true)
webforj.devtools.livereload.static-resources-enabled=true

# Port und Pfad, mit dem der Browser verbunden ist (Standardwerte: 35730, /webforj-devtools-ws)
webforj.devtools.livereload.websocket-port=35730
webforj.devtools.livereload.websocket-path=/webforj-devtools-ws

# Herzschlagintervall in Millisekunden (Standard: 30000)
webforj.devtools.livereload.heartbeat-interval=30000
```
