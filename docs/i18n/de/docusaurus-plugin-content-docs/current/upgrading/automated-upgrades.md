---
sidebar_position: 1
title: Automated Upgrades
description: >-
  Migrate webforJ projects between versions automatically with OpenRewrite
  recipes that rename APIs, update dependencies, and flag manual fixes.
sidebar_class_name: new-content
_i18n_hash: 7f4b51fb3daf9ba91f0e755758d8ec52
---
# OpenRewrite verwenden {#using-openrewrite}

OpenRewrite ist ein Open-Source-Projekt, das für automatisiertes Refactoring von Quellcode konzipiert ist und es Ihnen ermöglicht, effizient und automatisch zwischen webforJ-Versionen für Projekte zu migrieren, die veraltete oder entfernte APIs verwenden.

## Wann OpenRewrite verwenden? {#when-to-use-openwrite}

Sie können OpenRewrite verwenden, wenn Sie ein Upgrade durchführen und von veralteten APIs weg möchten, nicht nur bei größeren Versionen. Dies hilft Ihnen, von neu veralteten Methoden wegzukommen, bevor sie in einer späteren Version entfernt werden.

## Einrichtung {#setup}

Um Ihr Projekt mit OpenRewrite zu aktualisieren, fügen Sie das OpenRewrite Maven-Plugin zu Ihrem `pom.xml` hinzu, mit [`webforj-rewrite`](https://github.com/webforj/webforj/tree/main/webforj-rewrite) als Abhängigkeit:

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

## Änderungen anzeigen (optional) {#previewing-changes}

Wenn Sie die Änderungen, die OpenRewrite mit einem webforJ-Rezept vornehmen wird, lieber vorab ansehen möchten, erzeugt das Ausführen des folgenden Befehls ein Diff in `target/rewrite/rewrite.patch`, ohne Dateien zu ändern. Überprüfen Sie den Patch, um genau zu sehen, was das Rezept ändern wird.

```bash
mvn rewrite:dryRun
```

## OpenRewrite ausführen {#running-openrewrite}

Wenn Sie bereit sind, Änderungen mit OpenRewrite anzuwenden, führen Sie den folgenden Befehl aus:

```bash
mvn rewrite:run
```

Das Rezept übernimmt den Großteil des Upgrades automatisch, indem es Abhängigkeiten aktualisiert, Methoden umbenennt, Typen ändert und Rückgabetypen anpasst. In den wenigen Fällen, in denen es keinen 1:1-Ersatz gibt, fügt es Ihrem Code `TODO`-Kommentare mit spezifischen Anweisungen hinzu. Durchsuchen Sie Ihr Projekt nach diesen `TODO`s, um zu sehen, was noch zu erledigen ist.

## Bereinigen {#clean-up}

Nachdem Sie OpenRewrite mit einem webforJ-Rezept ausgeführt und alle `TODO`-Kommentare bearbeitet haben, entfernen Sie das Plugin aus Ihrem `pom.xml`.

## Verfügbare Rezepte {#available-recipes}

| Version | Standard webforJ Projekte | Spring Boot Projekte |
| ------- | ------- | ------- |
| v26.01 | `com.webforj.rewrite.v26.UpgradeWebforj_26_01` | `com.webforj.rewrite.v26.UpgradeWebforjSpring_26_01` |
| v26 | `com.webforj.rewrite.v26.UpgradeWebforj` | `com.webforj.rewrite.v26.UpgradeWebforjSpring` |
