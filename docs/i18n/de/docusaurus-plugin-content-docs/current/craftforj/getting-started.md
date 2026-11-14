---
title: Erste Schritte
sidebar_position: 2
description: >-
  Add the devtools dependency, enable craftforJ in your configuration, and open
  craftforJ over a running webforJ app.
_i18n_hash: 5d30f5dd80df008a05be5d4617c40369
---
<!-- vale off -->
# Erste Schritte <DocChip chip='since' label='26.02' />
<!-- vale on -->

craftforJ wird mit webforJ ausgeliefert, sodass kein separater Download erforderlich ist. Auf dieser Seite erfahren Sie, was Ihre App benötigt, damit craftforJ angezeigt wird, und wie Sie es öffnen.

:::tip In generierten Projekten bereits aktiviert
Projekte, die mit [startforJ](https://docs.webforj.com/startforj) oder einem webforJ-[Archetyp](/docs/building-ui/archetypes/overview) erstellt wurden, enthalten craftforJ bereits. Wenn Sie mit einem solchen Projekt begonnen haben, starten Sie Ihre App und springen Sie direkt zu [craftforJ öffnen](#opening-craftforj).
:::

## Voraussetzungen {#requirements}

craftforJ wird nur dann an eine App angehängt, wenn alle folgenden Bedingungen erfüllt sind. Fehlt eine davon, wird auf der Seite nichts angezeigt.

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

Fügen Sie die folgenden Eigenschaften zu Ihrem Projekt hinzu. Wenn Sie eine Standard-webforJ-App verwenden, legen Sie die Eigenschaften in `webforj.conf` fest. Bei einem webforJ-Projekt mit [Spring](/docs/integrations/spring/overview) legen Sie die Eigenschaften in `application.properties` fest.

```ini
webforj.debug = true
webforj.devtools.craftforj.enabled = true
```

craftforJ funktioniert nur, wenn beide Eigenschaften aktiviert sind. Daher wird bei einer App, die mit aktiviertem Debug-Modus in die Produktion geht, Ihr Quellverzeichnis nicht offengelegt.

### Lokaler Browser und Entwicklerlizenz {#a-local-browser-and-a-developer-license}

Öffnen Sie die App auf dem Rechner, auf dem sie ausgeführt wird, und stellen Sie sicher, dass Sie über eine gültige Entwicklerlizenz verfügen. Um von einem anderen Rechner aus auf craftforJ zuzugreifen, fügen Sie dessen Adresse zu [`hosts-allowed`](/docs/craftforj/configuration#access) hinzu.

Sobald diese Voraussetzungen erfüllt sind, starten Sie die App neu und laden Sie die Seite erneut.

## craftforJ öffnen {#opening-craftforj}

Wenn craftforJ aktiv ist, wird eine Schaltfläche über Ihrer App angezeigt. Klicken Sie darauf, um craftforJ zu öffnen, oder drücken Sie an einer beliebigen Stelle in der App <kbd>Alt</kbd> + <kbd>Shift</kbd> + <kbd>D</kbd>. Mit derselben Tastenkombination schließen Sie craftforJ wieder. Außerdem können Sie den Auslöser in eine beliebige Ecke ziehen.

<VideoPlayer video="craftforj/getting-started" width={1920} height={984} title="Getting started" />

Die Registerkarten umfassen den [Komponentenbaum](/docs/craftforj/inspector), [Routen](/docs/craftforj/routes), das [Theme](/docs/craftforj/theme) und den [Assistenten](/docs/craftforj/ai). Einstellungen und App-Informationen befinden sich daneben.


- **Der Auslöser** ist die Schaltfläche, mit der craftforJ geöffnet und geschlossen wird. Wenn craftforJ geschlossen ist, bleibt er unauffällig.
- **Die Registerkartenleiste** verläuft entlang der Kante zur App hin und wechselt zwischen den Ansichten von craftforJ.
- **Das Fenstermenü** enthält alle Optionen zur Positionierung von craftforJ, beschrieben unter [Position von craftforJ](#where-craftforj-sits).

:::info Tastenkombinationen unter macOS
craftforJ zeigt jede Tastenkombination mit den Modifikatortasten der verwendeten Plattform an. Daher wird <kbd>Alt</kbd> als <kbd>⌥</kbd> und <kbd>Ctrl</kbd> als <kbd>⌘</kbd> dargestellt. Drücken Sie in craftforJ <kbd>Shift</kbd> + <kbd>?</kbd>, um die aktuelle Liste anzuzeigen.
:::

## Position von craftforJ {#where-craftforj-sits}

Standardmäßig schwebt craftforJ über Ihrer App. Ziehen Sie es an eine beliebige Stelle auf der Seite, ändern Sie die Größe an jeder Kante und minimieren Sie es wieder auf den Auslöser, wenn Sie die App für sich allein haben möchten. Wenn Sie craftforJ an eine Seitenkante ziehen, wird es dort angedockt – über die gesamte Höhe oder Breite. An jeder Kante bleibt die von Ihnen festgelegte Größe erhalten. Ziehen Sie es von der Kante weg, schwebt es wieder.

:::info Angedockt überdeckt craftforJ die App, statt ihren Inhalt neu anzuordnen
craftforJ wird über der Seite angezeigt. Ihre App wird nicht verkleinert, und nichts darin wird verschoben. Daher ist der Bereich unter craftforJ verdeckt, solange es dort angezeigt wird. Um den verdeckten Inhalt zu sehen, verschieben Sie craftforJ an eine andere Kante oder nehmen Sie es von der Seite herunter.
:::

![craftforJ rechts neben einer App-Seite angedockt und überdeckt diesen Rand der App](/img/craftforj/getting-started/docking.png#rounded-border)

Damit craftforJ die App nicht mehr überdeckt, verschieben Sie es von der Seite in ein eigenes Browserfenster oder einen eigenen Tab. Das eignet sich besonders für einen zweiten Monitor. craftforJ überprüft Ihre App weiterhin über die Seite, von der aus es geöffnet wurde. Lassen Sie diese Seite daher geöffnet. Navigieren Sie von ihr weg oder schließen Sie sie, kann craftforJ nichts mehr überprüfen, bis Sie die App erneut öffnen.

<VideoPlayer video="craftforj/undock-window" width={1920} height={1048} title="Undock window" />

Wählen Sie statt eines Fensters einen Tab, wenn Sie die geteilte Ansicht von Chrome verwenden. Damit werden Ihre App und craftforJ nebeneinander angezeigt; unterstützt werden dabei nur echte Tabs. Klicken Sie mit der rechten Maustaste auf den Tab Ihrer App, fügen Sie ihn einer neuen geteilten Ansicht hinzu und wählen Sie dann den craftforJ-Tab aus.

:::info Geteilte Ansicht ist eine Chrome-Funktion
Die nebeneinander angeordnete Ansicht wird von Chrome bereitgestellt, nicht von craftforJ. Andere Browser bieten keine entsprechende Funktion. Dort wird craftforJ in einem normalen Tab geöffnet, zu dem Sie wechseln können. craftforJ selbst funktioniert in beiden Fällen gleich.
:::

<VideoPlayer video="craftforj/split-view" width={1920} height={1080} title="Split view" />

:::tip Verschieben, während der Assistent eine Antwort schreibt
Wenn Sie craftforJ in ein anderes Fenster verschieben, wird eine noch gestreamte Antwort beendet. craftforJ fragt vorher nach, und alles, was bis dahin geschrieben wurde, bleibt im Chat erhalten.
:::

## Erste Änderung vornehmen {#making-a-first-change}

1. Drücken Sie <kbd>Alt</kbd> + <kbd>Shift</kbd> + <kbd>C</kbd>, um die Komponentenauswahl zu starten.
2. Bewegen Sie den Mauszeiger über ein Element in Ihrer App und klicken Sie darauf.
3. Im Baum wird diese Komponente ausgewählt, und die Seitenleiste zeigt ihre Eigenschaften an.
4. Ändern Sie eine Eigenschaft. Die laufende App wird sofort aktualisiert.

Die Änderung wirkt sich nur auf die App aus, die Sie gerade verwenden. Ihre Dateien bleiben unverändert, bis Sie die Änderung überprüfen und anwenden. Dies wird unter [Änderungen in die Quelldateien schreiben](/docs/craftforj/source-changes) beschrieben.

![craftforJ neben einer laufenden App geöffnet, mit ausgewählter Komponente](/img/craftforj/getting-started/first-open.png#rounded-border)

Wenn überhaupt nichts angezeigt wird, gehen Sie die [Fehlerbehebung](/docs/craftforj/troubleshooting) durch.
