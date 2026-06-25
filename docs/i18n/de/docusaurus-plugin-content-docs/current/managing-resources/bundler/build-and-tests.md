---
title: Build and tests
sidebar_position: 40
sidebar_class_name: new-content
description: >-
  What the bundler does across the build, the development watch, running
  frontend tests, tuning a compiler, and producing a minified production bundle.
_i18n_hash: 0fe6e8ed747a106be1fedf5a2506f803
---
import Tabs from '@theme/Tabs';
import TabItem from '@theme/TabItem';

Der Bundler läuft gemäß den Zielen des [webforJ-Build-Plugins](/docs/configuration/build-plugin). Fügen Sie das Plugin einmal hinzu, wie dort gezeigt, und ein normales `mvn package` oder `gradle build` erzeugt eine App mit ihrem Frontend, das mitcompiliert wird, während `mvn test` die Frontend-Tests zusammen mit den Java-Tests ausführt. Diese Seite behandelt, was der Bundler in diesen Schritten tut.

## Die Entwicklungsüberwachung {#the-development-watch}

Der `watch`-Schritt ist derjenige, den Sie während der Entwicklung manuell zusammen mit der App ausführen. Er kompiliert das Frontend einmal und baut bei jeder Änderung neu und aktualisiert den Browser.

```bash
mvn compile webforj:watch spring-boot:run
```

Ein webforJ-Projekt legt dies als sein Standardziel fest, sodass `mvn` ohne Argumente die Überwachung und die App zusammen startet. Das von ihm gesteuerte Neuladen, eine in-place angewandte Änderung des Stylesheets gegenüber einer Skriptänderung, die die Ansicht neu lädt, wird in [Frontend-Überwachung](/docs/configuration/deploy-reload/frontend-watch) behandelt.

## Frontend-Tests {#frontend-tests}

Der `test`-Schritt führt den Bun-Test-Runner über `src/main/frontend` während der Testphase aus, sodass `mvn test` die Frontend-Tests zusammen mit den Java-Tests ausführt. Wenn das Quellverzeichnis keine Testdateien enthält, wird der Schritt übersprungen, und ein fehlschlagender Frontend-Test schlägt den Build fehl, sodass ein defektes Frontend eine Veröffentlichung auf die gleiche Weise stoppt wie ein fehlerhafter Java-Test. Für das Schreiben dieser Tests siehe [Frontend-Test](/docs/testing/frontend-testing).

## Einen Compiler anpassen {#tuning-a-compiler}

Ein Compiler liest seine Einstellungen aus `src/main/frontend/bun.config.ts`, die nach der Erweiterungs-ID gekennzeichnet sind, sodass eine Einstellung den richtigen Compiler ohne Flag beim Build erreicht. Siehe [SCSS und Sass](/docs/managing-resources/bundler/extensions/scss) für ein Beispiel, das dem SCSS-Compiler einen Ladespeicher gibt.

## Das Produktions-Bundle {#the-production-bundle}

Der `bundle`-Schritt wird während `prepare-package` ausgeführt, sodass das Verpacken einer App ihr Frontend für die Produktion kompiliert. Ein Produktions-Build unterscheidet sich in zwei wesentlichen Punkten vom Entwicklungs-Build, wenn eine App bereitgestellt wird.

- **Hash-Dateinamen.** Jede Ausgabedatei enthält einen Hash ihres Inhalts in ihrem Namen. Ein Browser kann dann eine Datei lange zwischenspeichern, da eine Änderung des Inhalts einen neuen Namen produziert, und der neue Name zwingt zu einem frischen Abruf. Das Caching bleibt sicher, ohne dass ein manuelles Versions-Update erforderlich ist.
- **Minified output.** Leerzeichen und toter Code werden entfernt, sodass die Dateien, die ein Browser herunterlädt, so klein sind, wie es die Kompilation zulässt.

Ein Entwicklungs-Build überspringt beides. Er behält stabile Namen und lesbare Ausgaben bei, sodass die Überwachung eine Datei an Ort und Stelle ersetzen kann und Sie sehen können, was geladen wird, während Sie debuggen.

Da die Minifizierung Teil dieses Schrittes ist, benötigt ein Projekt, das den Bundler verwendet, nichts anderes, um minifiziertes CSS und JavaScript auszuliefern. Für eine App, die Assets über die [Asset-Annotationen](/docs/managing-resources/importing-assets) ohne den Bundler lädt, deckt das [Minifier-Plugin](/docs/configuration/minifier-plugin) diese Produktions-Minifizierung ab.

## Eager-Bundle {#eager-bundle}

Standardmäßig lädt jede Ansicht nur das Frontend, das sie benötigt, wenn eine Komponente dieser Klasse erstellt wird, sodass eine Ansicht nichts bezahlt, was sie nicht rendert.

Der Eager-Modus lädt das gesamte Frontend beim Start der App als ein einzelnes Bundle anstelle pro Ansicht. Aktivieren Sie es mit der `eager`-Option:

<Tabs>
<TabItem value="maven" label="Maven">

```xml title="pom.xml"
<plugin>
  <groupId>com.webforj</groupId>
  <artifactId>webforj-maven-plugin</artifactId>
  <extensions>true</extensions>
  <configuration>
    <eager>true</eager>
  </configuration>
</plugin>
```

</TabItem>
<TabItem value="gradle" label="Gradle">

```groovy title="build.gradle"
webforj {
  eager = true
}
```

</TabItem>
</Tabs>

Eager ist standardmäßig deaktiviert, und das Modell pro Ansicht eignet sich für die meisten Apps. Verwenden Sie es, wenn Sie das gesamte Frontend von Anfang an an Ort und Stelle haben möchten, anstatt es zu laden, während die Ansichten gerendert werden.
