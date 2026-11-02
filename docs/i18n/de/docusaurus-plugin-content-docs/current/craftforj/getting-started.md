---
title: Einen Einstieg finden
sidebar_position: 2
description: >-
  Add the devtools dependency, enable craftforJ in your configuration, and open
  craftforJ over a running webforJ app.
_i18n_hash: fb5860ba7349ef5523c2dfb6bc425d9b
---
<DocChip chip='since' label='26.02' />

craftforJ wird zusammen mit webforJ ausgeliefert, sodass nichts separat heruntergeladen werden muss. Diese Seite behandelt die Anforderungen, die Ihre App erfüllen muss, bevor craftforJ verfügbar ist, und wie Sie es öffnen.

:::tip Bereits in generierten Projekten aktiviert
Projekte, die mit [startforJ](https://docs.webforj.com/startforj) oder aus einem webforJ [Archetyp](/docs/building-ui/archetypes/overview) erstellt wurden, haben craftforJ aktiviert. Wenn Sie von einem solchen Projekt gestartet sind, führen Sie Ihre App aus und springen Sie direkt zu [Öffnen von craftforJ](#opening-craftforj).
:::

## Anforderungen {#requirements}

craftforJ wird nur an eine App angehängt, wenn alle folgenden Bedingungen erfüllt sind. Wenn eine von ihnen nicht erfüllt ist, erscheint nichts auf der Seite.

### Abhängigkeit hinzufügen {#add-the-dependency}

Fügen Sie `webforj-devtools` zu Ihrem Projekt hinzu, falls es dort noch nicht vorhanden ist:

```xml title="pom.xml"
<dependency>
  <groupId>com.webforj</groupId>
  <artifactId>webforj-devtools</artifactId>
  <version>${webforj.version}</version>
</dependency>
```

### Debug-Modus und das craftforJ-Flag {#debug-mode-and-the-craftforj-flag}

Fügen Sie die folgenden Eigenschaften zu Ihrem Projekt hinzu. Wenn Sie eine Standard webforJ-App haben, setzen Sie die Eigenschaften in `webforj.conf`. Für ein webforJ-Projekt, das [Spring](/docs/integrations/spring/overview) verwendet, setzen Sie die Eigenschaften in `application.properties`.

```ini
webforj.debug = true
webforj.devtools.craftforj.enabled = true
```

craftforJ funktioniert nur, wenn beide Eigenschaften aktiviert sind; daher gibt eine App, die in die Produktion geht, ohne den Debug-Modus auszuschalten, Ihren Quellbaum nicht preis.

### Ein lokaler Browser und eine Entwicklerlizenz {#a-local-browser-and-a-developer-license}

Öffnen Sie die App von dem Computer, auf dem sie ausgeführt wird, und stellen Sie sicher, dass Sie eine gültige Entwicklerlizenz haben. Um von einem anderen Computer auf craftforJ zuzugreifen, fügen Sie dessen Adresse zu [`hosts-allowed`](/docs/craftforj/configuration#access) hinzu.

Sobald diese Anforderungen erfüllt sind, starten Sie die App neu und laden Sie die Seite neu.

## Öffnen von craftforJ {#opening-craftforj}

Wenn craftforJ aktiv ist, erscheint ein Auslöserbutton über Ihrer App. Klicken Sie darauf, um craftforJ zu öffnen, oder drücken Sie <kbd>Alt</kbd> + <kbd>Shift</kbd> + <kbd>D</kbd> von überall in der App. Die gleiche Tastenkombination schließt craftforJ wieder, und Sie können den Auslöser an die Ecke ziehen, die Ihnen am besten passt.

<div class="videos-container">
  <video controls preload="metadata">
    <source src="https://cdn.webforj.com/webforj-documentation/video/craftforJ/getting-started.mp4#t=0.5" type="video/mp4" />
  </video>
</div>

Seine Registerkarten decken den [Komponentenbaum](/docs/craftforj/inspector), [Routen](/docs/craftforj/routes), das [Thema](/docs/craftforj/theme) und den [Assistenten](/docs/craftforj/ai) ab. Einstellungen und Anwendungsinformationen befinden sich daneben.

- **Der Auslöser** ist die Schaltfläche, die craftforJ öffnet und schließt. Sie bleibt aus dem Weg, während craftforJ geschlossen ist.
- **Die Registerkartenleiste** verläuft entlang der Kante, die der App am nächsten ist, und wechselt zwischen dem, was craftforJ Ihnen zeigt.
- **Das Fenster-Menü** enthält alles über den Standort von craftforJ, das in [Wo sich craftforJ befindet](#where-craftforj-sits) behandelt wird.

:::info Tastenkombinationen auf macOS
craftforJ schreibt jede Tastenkombination unter Verwendung der Modifikatoren der Plattform, auf der Sie sich befinden, sodass <kbd>Alt</kbd> als <kbd>⌥</kbd> und <kbd>Ctrl</kbd> als <kbd>⌘</kbd> erscheint. Drücken Sie <kbd>Shift</kbd> + <kbd>?</kbd> in craftforJ, um die aktuelle Liste zu sehen.
:::

## Wo sich craftforJ befindet {#where-craftforj-sits}

craftforJ schwebt standardmäßig über Ihrer App. Ziehen Sie es irgendwo auf der Seite, ändern Sie die Größe von jeder Kante und minimieren Sie es zurück zu seinem Auslöser, wenn Sie die App für sich selbst haben möchten. Das Ziehen an eine Kante der Seite dockt es dort an, mit voller Höhe oder voller Breite, und jede Kante behält die Größe, die Sie ihm gegeben haben. Wenn Sie es von der Kante wegziehen, schwebt es wieder.

:::info Docking verdeckt die App, sie wird nicht umflossen
craftforJ wird über die Seite gezeichnet. Ihre App verändert nicht ihre Größe, und nichts darin bewegt sich aus dem Weg, sodass alles, was sich unter craftforJ befindet, verborgen ist, solange es dort ist. Um zu sehen, was sich darunter befindet, verschieben Sie craftforJ an eine andere Kante oder entfernen Sie es von der Seite.
:::

![craftforJ dockt rechts an einer App-Seite an und verdeckt diese Kante der App](/img/craftforj/getting-started/docking.png#rounded-border)

Um die App überhaupt nicht zu überdecken, bewegen Sie craftforJ aus der Seite in ein eigenes Browserfenster oder -tab, was sich für einen zweiten Monitor eignet. Es inspiziert Ihre App weiterhin durch die Seite, die es geöffnet hat, also lassen Sie diese Seite geöffnet. Navigieren Sie weg oder schließen Sie sie, und craftforJ hat nichts mehr zu inspizieren, bis Sie die App erneut öffnen.

<div class="videos-container">
  <video controls preload="metadata">
    <source src="https://cdn.webforj.com/webforj-documentation/video/craftforJ/undock-window.mp4#t=0.5" type="video/mp4" />
  </video>
</div>

Wählen Sie eine Registerkarte anstelle eines Fensters, wenn Sie den geteilten Blick von Chrome verwenden, der Ihre App und craftforJ nebeneinander anzeigt und nur echte Registerkarten akzeptiert. Klicken Sie mit der rechten Maustaste auf die Registerkarte Ihrer App, fügen Sie sie zu einer neuen Ansicht hinzu und wählen Sie dann die craftforJ-Registerkarte.

:::info Geteilter Blick ist eine Chrome-Funktion
Chrome bietet das nebeneinander angeordnete Layout, nicht craftforJ. Andere Browser haben keine Entsprechung, sodass craftforJ in anderen Browsern in einer gewöhnlichen Registerkarte geöffnet wird, zu der Sie wechseln. craftforJ selbst funktioniert in beiden Fällen gleich.
:::

<div class="videos-container">
  <video controls preload="metadata">
    <source src="https://cdn.webforj.com/webforj-documentation/video/craftforJ/split-view.mp4#t=0.5" type="video/mp4" />
  </video>
</div>

:::tip Bewegen, während der Assistent schreibt
Das Bewegen von craftforJ in ein anderes Fenster beendet eine Antwort, die noch gestreamt wird. craftforJ fragt zuerst, und alles, was bis dahin geschrieben wurde, bleibt im Chat.
:::

## Erste Änderungen vornehmen {#making-a-first-change}

1. Drücken Sie <kbd>Alt</kbd> + <kbd>Shift</kbd> + <kbd>C</kbd>, um einen Komponentenauswahlprozess zu starten.
2. Fahren Sie mit der Maus über etwas in Ihrer App und klicken Sie darauf.
3. Der Baum wählt diese Komponente aus, und die Seitenleiste wird mit ihren Eigenschaften gefüllt.
4. Ändern Sie eine Eigenschaft. Die laufende App aktualisiert sich sofort.

Die Änderung beeinflusst nur die App, die Sie vor sich haben. Ihre Dateien bleiben unangetastet, bis Sie die Änderung überprüfen und anwenden, was in [Änderungen am Quellcode vornehmen](/docs/craftforj/source-changes) behandelt wird.

![craftforJ geöffnet neben einer laufenden App mit ausgewählter Komponente](/img/craftforj/getting-started/first-open.png#rounded-border)

Wenn überhaupt nichts erscheint, arbeiten Sie die [Fehlerbehebung](/docs/craftforj/troubleshooting) durch.
