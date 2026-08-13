---
sidebar_position: 4
title: Managing Secrets
description: >-
  Keep database passwords, API keys, and other secrets out of your webforJ
  source tree and configuration files by resolving them at runtime.
_i18n_hash: 3c20f1e66f7fb00f96c26f0b00d46f07
---
Die Regel hinter jedem Geheimnis, einem Datenbankpasswort, einem API-Schlüssel, einem Signaturschlüssel ist, dass sein echter Wert niemals in deinem Code, deiner `webforj.conf` oder deinem Repository lebt. Löse es stattdessen zur Laufzeit auf, damit der gleiche Build in jeder Umgebung läuft und ein geleaktes Repository nichts preisgibt.

## Geheimnisse aus der Umgebung lesen {#read-secrets-from-the-environment}

Der portabelste Ansatz ist, jedes Geheimnis als Umgebungsvariable auf der Maschine oder dem Container zu speichern, der die App ausführt, und es beim Start zu lesen, anstatt es irgendwo zu committen.

```bash
# setze, wo die App läuft, niemals in einer verfolgten Datei
export DB_PASSWORD=…
```

Halte diese Werte aus der `webforj.conf` und jeder anderen Datei, die du commitest, heraus, und stelle sicher, dass dein Deployment sie festlegt, bevor die App startet.

## Auf Spring Boot {#on-spring-boot}

Wenn deine App auf Spring Boot läuft, greife auf seine externalisierte Konfiguration zurück, anstatt deine eigene zu erfinden. Du kannst eine Umgebungsvariable aus der `application.properties` mit einem `${...}` Platzhalter referenzieren und eine Geheimnisdatei, die außerhalb des Projekts (und außerhalb der Versionskontrolle) liegt, mit `spring.config.import` einbinden.

```properties title="application.properties"
spring.datasource.password=${DB_PASSWORD}
spring.config.import=optional:file:./secrets.properties
```

Dies sind Funktionen von Spring Boot, nicht von webforJ; siehe die [Dokumentation zur externalisierten Konfiguration](https://docs.spring.io/spring-boot/reference/features/external-config.html) von Spring Boot für die vollständige Palette der Optionen.

:::tip Ein durchgesickertes Geheimnis ist ein verbranntes Geheimnis
Füge Dateien wie `secrets.properties` zu `.gitignore` hinzu, prüfe deine Historie auf Werte, die hineingerutscht sind, und rotiere alles, was jemals exponiert war. Geheimnisse aus neuen Commits herauszuhalten, macht die bereits gepushten nicht rückgängig.
:::
