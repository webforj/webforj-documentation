---
title: Erste Schritte
sidebar_position: 2
description: >-
  Add the devtools dependency, enable craftforJ in your configuration, and open
  craftforJ over a running webforJ app.
_i18n_hash: 6cb150ca0da866a142787024af9e2bd1
---
<!-- vale off -->
# Erste Schritte <DocChip chip='since' label='26.02' />
<!-- vale on -->

craftforJ wird mit webforJ ausgeliefert, sodass kein separater Download erforderlich ist. Auf dieser Seite erfahren Sie, was Ihre App benötigt, bevor craftforJ angezeigt wird, und wie Sie craftforJ öffnen.

:::tip In generierten Projekten bereits aktiviert
Projekte, die mit [startforJ](https://docs.webforj.com/startforj) oder aus einem webforJ-[Archetyp](/docs/building-ui/archetypes/overview) erstellt wurden, haben craftforJ bereits aktiviert. Wenn Sie mit einem solchen Projekt gestartet sind, führen Sie Ihre App aus und springen Sie direkt zu [craftforJ öffnen](#opening-craftforj).
:::

## Voraussetzungen {#requirements}

craftforJ wird nur dann an eine App angehängt, wenn alle folgenden Bedingungen erfüllt sind. Ist auch nur eine davon nicht erfüllt, wird nichts auf der Seite angezeigt.

### Abhängigkeit hinzufügen {#add-the-dependency}

Fügen Sie `webforj-devtools` zu Ihrem Projekt hinzu, falls es noch nicht enthalten ist:

```xml title="pom.xml"
<dependency>
  <groupId>com.webforj</groupId>
  <artifactId>webforj-devtools</artifactId>
  <version>${webforj.version}</version>
</dependency>
```

### Debug-Modus und craftforJ-Flag {#debug-mode-and-the-craftforj-flag}

Fügen Sie Ihrem Projekt die folgenden Eigenschaften hinzu. Wenn Sie eine standardmäßige webforJ-App haben, legen Sie die Eigenschaften in `webforj.conf` fest. Für ein webforJ-Projekt mit [Spring](/docs/integrations/spring/overview) legen Sie die Eigenschaften in `application.properties` fest.

```ini
webforj.debug = true
webforj.devtools.craftforj.enabled = true
```

craftforJ funktioniert nur, wenn beide Eigenschaften aktiviert sind. Daher gibt eine App, die mit aktiviertem Debug-Modus in Produktion geht, Ihren Quellbaum nicht preis.

### Lokaler Browser und Entwicklerlizenz {#a-local-browser-and-a-developer-license}

Öffnen Sie die App auf dem Rechner, auf dem sie ausgeführt wird, und vergewissern Sie sich, dass Sie über eine gültige Entwicklerlizenz verfügen. Um craftforJ von einem anderen Rechner aus zu erreichen, fügen Sie dessen Adresse zu [`hosts-allowed`](/docs/craftforj/configuration#access) hinzu.

Sobald diese Voraussetzungen erfüllt sind, starten Sie die App neu und laden Sie die Seite erneut.

## craftforJ öffnen {#opening-craftforj}

Wenn craftforJ aktiv ist, erscheint eine Schaltfläche über Ihrer App. Klicken Sie darauf, um craftforJ zu öffnen, oder drücken Sie an beliebiger Stelle in der App <kbd>Alt</kbd> + <kbd>Shift</kbd> + <kbd>D</kbd>. Mit derselben Tastenkombination schließen Sie craftforJ wieder. Sie können die Schaltfläche an eine beliebige Ecke ziehen.

<div class="videos-container">
  <video controls preload="metadata">
    <source src="https://cdn.webforj.com/webforj-documentation/video/craftforJ/getting-started.mp4#t=0.5" type="video/mp4" />
  </video>
</div>

Die Registerkarten umfassen den [Komponentenbaum](/docs/craftforj/inspector), [Routen](/docs/craftforj/routes), das [Theme](/docs/craftforj/theme) und den [Assistenten](/docs/craftforj/ai). Einstellungen und App-Informationen befinden sich daneben.


- **Der Auslöser** ist die Schaltfläche, mit der craftforJ geöffnet und geschlossen wird. Wenn craftforJ geschlossen ist, bleibt sie unauffällig.
- **Die Registerkartenleiste** verläuft entlang der Kante, die der App am nächsten ist, und wechselt zwischen den Ansichten von craftforJ.
- **Das Fenstermenü** enthält alle Einstellungen zur Position von craftforJ, die unter [Position von craftforJ](#where-craftforj-sits) beschrieben werden.

:::info Tastenkombinationen unter macOS
craftforJ zeigt jede Tastenkombination mit den Modifikatortasten der jeweiligen Plattform an. Daher wird <kbd>Alt</kbd> als <kbd>⌥</kbd> und <kbd>Ctrl</kbd> als <kbd>⌘</kbd> angezeigt. Drücken Sie in craftforJ <kbd>Shift</kbd> + <kbd>?</kbd>, um die aktuelle Liste anzuzeigen.
:::

## Position von craftforJ {#where-craftforj-sits}

Standardmäßig schwebt craftforJ über Ihrer App. Ziehen Sie es an eine beliebige Stelle auf der Seite, ändern Sie seine Größe an einer beliebigen Kante und minimieren Sie es wieder auf den Auslöser, wenn Sie die App für sich haben möchten. Wenn Sie es an eine Kante der Seite ziehen, wird es dort angedockt – über die gesamte Höhe oder Breite. Dabei bleibt die für jede Kante festgelegte Größe erhalten. Ziehen Sie es von der Kante weg, schwebt es wieder.

:::info Beim Andocken wird die App überlagert, nicht neu angeordnet
craftforJ wird über der Seite angezeigt. Die Größe Ihrer App ändert sich nicht, und nichts darin wird verschoben. Daher wird alles, was sich unter craftforJ befindet, verdeckt. Um den darunterliegenden Inhalt zu sehen, verschieben Sie craftforJ an eine andere Kante oder nehmen Sie es aus der Seite heraus.
:::

![craftforJ rechts neben einer App-Seite angedockt und überlagert diese Seite an der Kante](/img/craftforj/getting-started/docking.png#rounded-border)

Damit craftforJ die App gar nicht mehr überlagert, verschieben Sie es aus der Seite heraus in ein eigenes Browserfenster oder einen eigenen Tab. Das ist praktisch bei Verwendung eines zweiten Monitors. craftforJ untersucht Ihre App weiterhin über die Seite, von der aus es geöffnet wurde. Lassen Sie diese Seite daher geöffnet. Wenn Sie von ihr weg navigieren oder sie schließen, kann craftforJ nichts mehr untersuchen, bis Sie die App erneut öffnen.

<div class="videos-container">
  <video controls preload="metadata">
    <source src="https://cdn.webforj.com/webforj-documentation/video/craftforJ/undock-window.mp4#t=0.5" type="video/mp4" />
  </video>
</div>

Wählen Sie statt eines Fensters einen Tab, wenn Sie die geteilte Ansicht von Chrome verwenden. Damit werden Ihre App und craftforJ nebeneinander angezeigt; unterstützt werden dabei nur echte Tabs. Klicken Sie mit der rechten Maustaste auf den Tab Ihrer App, fügen Sie ihn einer neuen geteilten Ansicht hinzu und wählen Sie anschließend den craftforJ-Tab aus.

:::info Die geteilte Ansicht ist eine Chrome-Funktion
Die nebeneinander angeordnete Ansicht wird von Chrome bereitgestellt, nicht von craftforJ. Andere Browser bieten keine entsprechende Funktion. Dort wird craftforJ in einem normalen Tab geöffnet, zu dem Sie wechseln können. craftforJ selbst funktioniert in beiden Fällen gleich.
:::

<div class="videos-container">
  <video controls preload="metadata">
    <source src="https://cdn.webforj.com/webforj-documentation/video/craftforJ/split-view.mp4#t=0.5" type="video/mp4" />
  </video>
</div>

:::tip Verschieben, während der Assistent eine Antwort schreibt
Wenn Sie craftforJ in ein anderes Fenster verschieben, wird eine noch laufende Antwort beendet. craftforJ fragt vorher nach, und alles, was bis dahin geschrieben wurde, bleibt im Chat erhalten.
:::

## Erste Änderung vornehmen {#making-a-first-change}

1. Drücken Sie <kbd>Alt</kbd> + <kbd>Shift</kbd> + <kbd>C</kbd>, um die Komponentenauswahl zu starten.
2. Bewegen Sie den Mauszeiger über ein Element in Ihrer App und klicken Sie darauf.
3. Der Baum wählt diese Komponente aus, und in der Seitenleiste werden ihre Eigenschaften angezeigt.
4. Ändern Sie eine Eigenschaft. Die laufende App wird sofort aktualisiert.

Die Änderung betrifft nur die App, die gerade vor Ihnen geöffnet ist. Ihre Dateien bleiben unverändert, bis Sie die Änderung überprüfen und anwenden. Dies wird unter [Änderungen in den Quellcode schreiben](/docs/craftforj/source-changes) beschrieben.

![craftforJ neben einer laufenden App geöffnet, in der eine Komponente ausgewählt ist](/img/craftforj/getting-started/first-open.png#rounded-border)

Wenn überhaupt nichts angezeigt wird, gehen Sie die [Fehlerbehebung](/docs/craftforj/troubleshooting) durch.
