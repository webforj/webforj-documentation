---
title: Frontend watch
sidebar_position: 20
sidebar_class_name: new-content
description: >-
  Rebuild the sources under src/main/frontend while a webforJ app runs, applying
  stylesheet and image output in place and reloading the view for script output.
_i18n_hash: 8307e05aa7a4c55b75fe8667be1f6b27
---
Die Frontend-Überwachung rebuildet die Quellcodes unter `src/main/frontend`, während die App läuft und sendet die Ausgabe an den Browser. Es ist die Entwicklungsseite des [Frontend-Bundlers](/docs/managing-resources/bundler/overview) und erfordert, dass `webforj.devtools.livereload.enabled` aktiv ist. Siehe die [Einstellungen](/docs/configuration/deploy-reload/overview#settings).

## Ausführen der Überwachung {#running-the-watch}

Führen Sie das `watch`-Ziel vor dem Ziel aus, das die App startet. Ein Archetypprojekt setzt dies als sein Standardziel, sodass `mvn` ohne Argumente beides ausführt:

```bash
mvn compile webforj:watch spring-boot:run
```

```bash
mvn compile webforj:watch jetty:run
```

Um die Überwachung als eigenständigen Build-Schritt auszuführen, siehe [Build und Tests](/docs/managing-resources/bundler/build-and-tests#the-development-watch).

## Wie die Ausgabe angewendet wird {#how-the-output-applies}

Die Browseraktion hängt von der produzierten Ausgabe ab, nicht von der bearbeiteten Datei:

| Ausgabe | Browseraktion |
|---|---|
| Stylesheet, aus einer `.css`, `.scss`, `.sass` oder `.less` Quelle | Vor Ort angewendet. Kein Neuladen, Formulardaten und Scrollposition bleiben. |
| Bild | Vor Ort ausgetauscht. Kein Neuladen. |
| Jede andere Ausgabe, wie kompilierte `.ts`, `.tsx` oder `.js` | Die Ansicht wird neu geladen. |

Wenn ein Rebuild mehrere Dateien produziert, wendet der Browser sie nur vor Ort an, wenn jede Datei qualifiziert. Andernfalls wird einmal neu geladen, sodass eine Änderung niemals teilweise angewendet wird.

## Während eines Serverneustarts {#during-a-server-restart}

Eine Java-Änderung ohne ein [Hotswap-Tool](/docs/configuration/deploy-reload/hotswap) startet den Server neu. Während des Neustarts:

- Angewandte Styles bleiben auf der Seite.
- Ein Indikator zeigt an, während der Server heruntergefahren ist. Er erscheint nur bei einem Neustart, nicht bei einem manuellen Neuladen.
- Die Seite wird neu geladen, wenn die App bereit ist, nicht vorher.

Eine `@BundleEntry`-Hinzufügung oder -Entfernung tritt in Kraft, wenn dieser Neustart abgeschlossen ist.
