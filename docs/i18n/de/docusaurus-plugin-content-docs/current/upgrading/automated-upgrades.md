---
sidebar_position: 2
title: Automated Upgrades
sidebar_class_name: new-content
_i18n_hash: 1681300490f592540c6d96fbdbfd8ee4
---
# Verwendung von OpenRewrite {#using-openrewrite}

OpenRewrite ist ein Open-Source-Projekt, das für automatisierte Quellcode-Refactoring entwickelt wurde. Es ermöglicht Ihnen, effizient und automatisch zwischen webforJ-Versionen zu migrieren, für Projekte, die veraltete oder entfernte APIs verwenden.

## Wann OpenRewrite verwenden? {#when-to-use-openrewrite}

Sie können OpenRewrite verwenden, wenn Sie ein Upgrade durchführen, um sich von veralteten APIs zu entfernen, und zwar nicht nur bei Hauptversionen. Dies hilft Ihnen, sich von neu veralteten Methoden zu entfernen, bevor sie in einer späteren Version entfernt werden.

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

## Änderungen vorab anzeigen (optional) {#previewing-changes}

Wenn Sie es vorziehen würden, die Änderungen, die OpenRewrite mit einem webforJ-Rezept vornehmen wird, vorzuschauen, generiert das Ausführen des folgenden Befehls einen Diff in `target/rewrite/rewrite.patch`, ohne Dateien zu ändern. Überprüfen Sie den Patch, um genau zu sehen, was das Rezept ändern wird.

```bash
mvn rewrite:dryRun
```

## OpenRewrite ausführen {#running-openrewrite}

Wenn Sie bereit sind, Änderungen mit OpenRewrite anzuwenden, führen Sie den folgenden Befehl aus:

```bash
mvn rewrite:run
```

Das Rezept verarbeitet die meisten Upgrades automatisch, indem es Abhängigkeiten aktualisiert, Methoden umbenennt, Typen ändert und Rückgabetypen anpasst. Für die wenigen Fälle, in denen kein 1:1-Ersatz vorhanden ist, fügt es `TODO`-Kommentare in Ihrem Code mit spezifischen Anweisungen hinzu. Durchsuchen Sie Ihr Projekt nach diesen `TODO`s, um zu finden, was übrig geblieben ist.

## Aufräumen {#clean-up}

Nachdem Sie OpenRewrite mit einem webforJ-Rezept ausgeführt und alle `TODO`-Kommentare überprüft haben, entfernen Sie das Plugin aus Ihrer `pom.xml`.

## Verfügbare Rezepte {#available-recipes}

| Version | Standard webforJ-Projekte | Spring Boot-Projekte |
| ------- | ------- | ------- |
| v26 | `com.webforj.rewrite.v26.UpgradeWebforj` | `com.webforj.rewrite.v26.UpgradeWebforjSpring` |
