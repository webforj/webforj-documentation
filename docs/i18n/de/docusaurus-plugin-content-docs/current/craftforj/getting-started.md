---
title: Getting Started
sidebar_position: 2
description: >-
  Add the devtools dependency, enable craftforJ in your configuration, and open
  craftforJ over a running webforJ app.
_i18n_hash: 81825a3ba8656a8aee4820dee71da732
---
<DocChip chip='since' label='26.02' />

craftforJ wird zusammen mit webforJ geliefert, sodass es nichts separat herunterzuladen gibt. Diese Seite behandelt, was Ihre App benötigt, bevor craftforJ angezeigt wird, und wie Sie es öffnen können.

:::tip Bereits in generierten Projekten aktiviert
Projekte, die mit [startforJ](https://docs.webforj.com/startforj) oder aus einem webforJ [Archetyp](/docs/building-ui/archetypes/overview) erstellt wurden, haben craftforJ aktiviert. Wenn Sie von einem solchen Projekt gestartet haben, führen Sie Ihre App aus und überspringen Sie den Abschnitt [Öffnen von craftforJ](#opening-craftforj).
:::

## Anforderungen {#requirements}

craftforJ wird nur dann an eine App angehängt, wenn alle folgenden Bedingungen erfüllt sind. Wenn eine davon nicht erfüllt ist, wird nichts auf der Seite angezeigt.

### Abhängigkeit hinzufügen {#add-the-dependency}

Fügen Sie `webforj-devtools` zu Ihrem Projekt hinzu, wenn es noch nicht vorhanden ist:

```xml title="pom.xml"
<dependency>
  <groupId>com.webforj</groupId>
  <artifactId>webforj-devtools</artifactId>
  <version>${webforj.version}</version>
</dependency>
```

### Debug-Modus und das craftforJ-Symbol {#debug-mode-and-the-craftforj-flag}

Fügen Sie die folgenden Eigenschaften zu Ihrem Projekt hinzu. Wenn Sie eine Standard-webforJ-App haben, setzen Sie die Eigenschaften in `webforj.conf`. Für ein webforJ-Projekt, das [Spring](/docs/integrations/spring/overview) verwendet, setzen Sie die Eigenschaften in `application.properties`.

```ini
webforj.debug = true
webforj.devtools.craftforj.enabled = true
```

craftforJ funktioniert nur, wenn beide Eigenschaften aktiviert sind; daher exponiert eine App, die mit aktiviertem Debug-Modus in die Produktion geht, nicht Ihren Quellbaum.

### Ein lokaler Browser und eine Entwicklerlizenz {#a-local-browser-and-a-developer-license}

Öffnen Sie die App von dem Rechner, auf dem sie läuft, und stellen Sie sicher, dass Sie eine gültige Entwicklerlizenz haben. Um von einem anderen Rechner auf craftforJ zuzugreifen, fügen Sie dessen Adresse zu [`hosts-allowed`](/docs/craftforj/configuration#access) hinzu.

Sobald dies eingerichtet ist, starten Sie die App neu und laden Sie die Seite erneut.

## Öffnen von craftforJ {#opening-craftforj}

Wenn craftforJ aktiv ist, erscheint ein Trigger-Button über Ihrer App. Klicken Sie darauf, um craftforJ zu öffnen, oder drücken Sie <kbd>Alt</kbd> + <kbd>Shift</kbd> + <kbd>D</kbd> von überall in der App. Derselbe Shortcut schließt craftforJ und Sie können den Trigger an die Ecke ziehen, die Ihnen am besten gefällt.

<div class="videos-container">
  <video controls preload="metadata">
    <source src="https://cdn.webforj.com/webforj-documentation/video/craftforJ/getting-started.mp4" type="video/mp4" />
  </video>
</div>

Seine Tabs decken den [Komponentenbaum](/docs/craftforj/inspector), [Routen](/docs/craftforj/routes), das [Theme](/docs/craftforj/theme) und den [Assistenten](/docs/craftforj/ai) ab. Einstellungen und App-Informationen befinden sich neben ihnen.

- **Der Trigger** ist der Button, der craftforJ öffnet und schließt. Er bleibt aus dem Weg, während craftforJ geschlossen ist.
- **Die Tab-Leiste** verläuft entlang der Kante, die der App am nächsten ist, und wechselt zwischen dem, was craftforJ Ihnen zeigt.
- **Das Fenster-Menü** enthält alles darüber, wo craftforJ sitzt, behandelt in [Wo craftforJ sitzt](#where-craftforj-sits).

:::info Tastenkombinationen auf macOS
craftforJ schreibt jede Tastenkombination unter Verwendung der Modifikatoren der Plattform, auf der Sie sich befinden, sodass <kbd>Alt</kbd> als <kbd>⌥</kbd> und <kbd>Ctrl</kbd> als <kbd>⌘</kbd> erscheint. Drücken Sie <kbd>Shift</kbd> + <kbd>?</kbd> in craftforJ, um die aktuelle Liste zu sehen.
:::

## Wo craftforJ sitzt {#where-craftforj-sits}

craftforJ schwebt standardmäßig über Ihrer App. Ziehen Sie es irgendwo auf die Seite, ändern Sie die Größe von jeder Kante und minimieren Sie es zurück zu seinem Trigger, wenn Sie die App für sich haben möchten. Wenn Sie es an eine Kante der Seite ziehen, docken Sie es dort an, mit voller Höhe oder voller Breite, und jede Kante behält die Größe, die Sie ihm gegeben haben. Wenn Sie es von der Kante wegziehen, schwebt es wieder.

:::info Docking überdeckt die App, es sorgt nicht für ein Neu-Layout
craftforJ wird über der Seite gezeichnet. Ihre App wird nicht neu dimensioniert, und nichts darin bewegt sich aus dem Weg, sodass alles, was sich unter craftforJ befindet, verborgen ist, während es dort ist. Um zu sehen, was darunter liegt, bewegen Sie craftforJ an eine andere Kante oder nehmen Sie es aus der Seite.
:::

![craftforJ angedockt an der rechten Seite einer App-Seite, die diese Kante der App überdeckt](/img/craftforj/getting-started/docking.png#rounded-border)

Um die App überhaupt nicht zu überdecken, ziehen Sie craftforJ aus der Seite in ein Browserfenster oder einen Tab für sich, was sich für einen zweiten Monitor eignet. Es inspiziert weiterhin Ihre App durch die Seite, die es geöffnet hat, also lassen Sie diese Seite offen. Navigieren Sie weg oder schließen Sie sie, hat craftforJ nichts mehr zu inspizieren, bis Sie die App erneut öffnen.

<div class="videos-container">
  <video controls preload="metadata">
    <source src="https://cdn.webforj.com/webforj-documentation/video/craftforJ/undock-window.mp4" type="video/mp4" />
  </video>
</div>

Wählen Sie einen Tab anstelle eines Fensters, wenn Sie die Split-View von Chrome verwenden, die Ihre App und craftforJ nebeneinander anzeigt und nur echte Tabs akzeptiert. Klicken Sie mit der rechten Maustaste auf den Tab Ihrer App, fügen Sie ihn zu einem neuen Split-View hinzu und wählen Sie dann den Tab von craftforJ.

:::info Split-View ist eine Chrome-Funktion
Chrome bietet die Anordnung nebeneinander an, nicht craftforJ. Andere Browser haben kein entsprechendes Pendant, sodass craftforJ in anderen Browsern in einem normalen Tab geöffnet wird, zu dem Sie wechseln. craftforJ selbst funktioniert in beiden Fällen gleich.
:::

<div class="videos-container">
  <video controls preload="metadata">
    <source src="https://cdn.webforj.com/webforj-documentation/video/craftforJ/split-view.mp4" type="video/mp4" />
  </video>
</div>

:::tip Bewegen während der Assistent schreibt
Das Bewegen von craftforJ in ein anderes Fenster beendet eine Antwort, die noch gestreamt wird. craftforJ fragt zuerst, und alles, was bis zu diesem Punkt geschrieben wurde, bleibt im Chat.
:::

## Erste Änderung vornehmen {#making-a-first-change}

1. Drücken Sie <kbd>Alt</kbd> + <kbd>Shift</kbd> + <kbd>C</kbd>, um mit der Auswahl einer Komponente zu beginnen.
2. Fahren Sie mit der Maus über etwas in Ihrer App und klicken Sie darauf.
3. Der Baum wählt diese Komponente aus, und die Seitenleiste füllt sich mit ihren Eigenschaften.
4. Ändern Sie eine Eigenschaft. Die laufende App wird sofort aktualisiert.

Die Änderung wirkt sich nur auf die App vor Ihnen aus. Ihre Dateien bleiben unberührt, bis Sie die Änderung überprüfen und anwenden, was in [Änderungen in den Quellcode schreiben](/docs/craftforj/source-changes) behandelt wird.

![craftforJ geöffnet neben einer laufenden App mit einer ausgewählten Komponente](/img/craftforj/getting-started/first-open.png#rounded-border)

Wenn überhaupt nichts angezeigt wird, arbeiten Sie durch die [Fehlerbehebung](/docs/craftforj/troubleshooting).
