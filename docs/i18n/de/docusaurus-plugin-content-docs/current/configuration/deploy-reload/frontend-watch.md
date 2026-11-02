---
title: Frontend watch
sidebar_position: 20
description: >-
  Rebuild the sources under src/main/frontend while a webforJ app runs, applying
  stylesheet and image output in place and reloading the view for script output.
_i18n_hash: 065e4f5ad8868a6758e682f38e2dd3b9
---
Der Frontend-Watch baut die Quellen unter `src/main/frontend` neu auf, während die App läuft, und sendet die Ausgabe an den Browser. Es ist die Entwicklungsseite des [Frontend-Bundlers](/docs/managing-resources/bundler/overview) und erfordert, dass `webforj.devtools.livereload.enabled` aktiviert ist, siehe die [Einstellungen](/docs/configuration/deploy-reload/overview#settings).

## Watch ausführen {#running-the-watch}

Führen Sie das Ziel `watch` vor dem Ziel aus, das die App startet. Ein Archetypprojekt setzt dies als sein Standardziel, sodass `mvn` ohne Argumente beide ausführt:

```bash
mvn compile webforj:watch spring-boot:run
```

```bash
mvn compile webforj:watch jetty:run
```

In BBjServices startet der Watch das [Entwicklungs-Run](/docs/configuration/deploy-reload/bbjservices) selbst:

```bash
mvn compile webforj:watch
```

Um den Watch als eigenständigen Build-Schritt auszuführen, siehe [Build und Tests](/docs/managing-resources/bundler/build-and-tests#the-development-watch).

## Wie die Ausgabe angewendet wird {#how-the-output-applies}

Die Browseraktion hängt von der produzierten Ausgabe ab, nicht von der bearbeiteten Datei:

| Ausgabe | Browseraktion |
|---|---|
| Stylesheet, aus einer `.css`, `.scss`, `.sass` oder `.less` Quelle | Wird vor Ort angewendet. Kein Reload, Formulardaten und Scrollposition bleiben. |
| Bild | Wird vor Ort ersetzt. Kein Reload. |
| Jede andere Ausgabe, wie kompilierte `.ts`, `.tsx` oder `.js` | Die Ansicht wird neu geladen. |

Wenn ein Build mehrere Dateien erzeugt, wendet der Browser sie nur dann vor Ort an, wenn jede Datei qualifiziert. Andernfalls wird einmal neu geladen, sodass eine Änderung nie teilweise angewendet wird.

## Während eines Serverneustarts {#during-a-server-restart}

Eine Java-Änderung ohne ein [Hotswap-Tool](/docs/configuration/deploy-reload/hotswap) startet den Server neu. Während des Neustarts:

- Angewendete Styles bleiben auf der Seite.
- Ein Indikator zeigt an, solange der Server offline ist. Er erscheint nur beim Neustart, nicht bei einem manuellen Reload.
- Die Seite wird neu geladen, wenn die App bereit ist, nicht vorher.

Eine Hinzufügung oder Entfernung von `@BundleEntry` tritt in Kraft, wenn dieser Neustart abgeschlossen ist.
