---
sidebar_position: 1
title: Automatisierte Upgrades
description: >-
  Migrate webforJ projects between versions automatically with OpenRewrite
  recipes that rename APIs, update dependencies, and flag manual fixes.
_i18n_hash: 8c889d8dea5c90aad2ef224d152e611e
---
# Verwendung von OpenRewrite {#using-openrewrite}

OpenRewrite ist ein Open-Source-Projekt, das für automatisiertes Refactoring von Quellcode entwickelt wurde. Es ermöglicht Ihnen, effizient und automatisiert zwischen webforJ-Versionen für Projekte zu migrieren, die veraltete oder entfernte APIs verwenden.

## Wann OpenRewrite verwenden? {#when-to-use-openrewrite}

Sie können OpenRewrite nutzen, wenn Sie aktualisieren und sich von veralteten APIs entfernen möchten, nicht nur bei Hauptversionen. Dies hilft Ihnen, sich von neu veralterten Methoden zu entfernen, bevor sie in einer späteren Version entfernt werden.

## Einrichtung {#setup}

Um Ihr Projekt mit OpenRewrite zu aktualisieren, fügen Sie das OpenRewrite Maven-Plugin zu Ihrer `pom.xml` hinzu, mit [`webforj-rewrite`](https://github.com/webforj/webforj/tree/main/webforj-rewrite) als Abhängigkeit:

```xml
<plugin>
  <groupId>org.openrewrite.maven</groupId>
  <artifactId>rewrite-maven-plugin</artifactId>
  <version>6.36.0</version>
  <configuration>
    <activeRecipes>
      <recipe>RECIPE_NAME</recipe>
    </activeRecipes>
  </configuration>
  <dependencies>
    <dependency>
      <groupId>com.webforj</groupId>
      <artifactId>webforj-rewrite</artifactId>
      <version>TARGET_VERSION</version>
    </dependency>
  </dependencies>
</plugin>
```

Ersetzen Sie `TARGET_VERSION` durch die webforJ-Version, auf die Sie aktualisieren, und `RECIPE_NAME` durch eines der Rezepte aus dem Abschnitt [Verfügbare Rezepte](#available-recipes) dieses Artikels.

## Änderungen im Voraus anzeigen (optional) {#previewing-changes}

Wenn Sie die Änderungen, die OpenRewrite mit einem webforJ-Rezept vornehmen wird, im Voraus sehen möchten, erzeugt das Ausführen des folgenden Befehls einen Diff in `target/rewrite/rewrite.patch`, ohne Dateien zu ändern. Überprüfen Sie den Patch, um genau zu sehen, was das Rezept ändern wird.

```bash
mvn rewrite:dryRun
```

## OpenRewrite ausführen {#running-openrewrite}

Wenn Sie bereit sind, Änderungen mit OpenRewrite anzuwenden, führen Sie den folgenden Befehl aus:

```bash
mvn rewrite:run
```

Das Rezept übernimmt den Großteil der Aktualisierung automatisch, indem es Abhängigkeiten aktualisiert, Methoden umbenennt, Typen ändert und Rückgabewerte anpasst. In den wenigen Fällen, in denen kein 1:1-Ersatz vorhanden ist, fügt es `TODO`-Kommentare in Ihrem Code mit spezifischen Anweisungen hinzu. Suchen Sie in Ihrem Projekt nach diesen `TODO`s, um herauszufinden, was noch zu erledigen ist.

## Bereinigung {#clean-up}

Nachdem Sie OpenRewrite mit einem webforJ-Rezept ausgeführt und alle `TODO`-Kommentare behoben haben, entfernen Sie das Plugin aus Ihrer `pom.xml`.

## Verfügbare Rezepte {#available-recipes}

| Version | Standard-webforJ-Projekte | Spring Boot-Projekte |
| ------- | ------- | ------- |
| v26.03 | `com.webforj.rewrite.v26.UpgradeWebforj_26_03` | `com.webforj.rewrite.v26.UpgradeWebforjSpring_26_01` |
| v26.01 | `com.webforj.rewrite.v26.UpgradeWebforj_26_01` | `com.webforj.rewrite.v26.UpgradeWebforjSpring_26_01` |
| v26 | `com.webforj.rewrite.v26.UpgradeWebforj` | `com.webforj.rewrite.v26.UpgradeWebforjSpring` |
