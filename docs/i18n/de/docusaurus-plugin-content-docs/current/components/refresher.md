---
title: Refresher
sidebar_position: 101
_i18n_hash: 99793e9f95d4c5a052014f677aa8a6cb
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-refresher" />
<DocChip chip='since' label='25.00' />
<JavadocLink type="refresher" location="com/webforj/component/refresher/Refresher" top='true'/>

Pull-to-refresh ist ein gängiges Muster in mobilen und berührungsfreundlichen Schnittstellen, und die `Refresher`-Komponente bringt es in scrollbare Container in webforJ. Während Benutzer nach unten wischen und dabei einen konfigurierbaren Schwellenwert überschreiten, wechselt sie durch visuelle Zustände: `pull`, `release` und `refreshing`, jeweils mit einem anpassbaren Symbol und lokalisiertem Text. Sie harmoniert gut mit [`InfiniteScroll`](../components/infinitescroll), um Inhalte durch gestenbasierte Eingaben neu zu laden oder zurückzusetzen.

<!-- INTRO_END -->

## Instanziierung und Internationalisierung {#instantiation-and-internationalization}

Fügen Sie einen `Refresher` hinzu, indem Sie ihn instanziieren und einen Refresh-Listener registrieren. Wenn die Refresh-Operationen abgeschlossen sind, rufen Sie `finish()` auf, um die Komponente in ihren Leerlaufzustand zurückzusetzen.

:::info So aktivieren Sie den `Refresher`
Um den `Refresher` zu aktivieren, **klicken und ziehen Sie nach unten** vom oberen Rand des scrollbaren Bereichs. Während diese Geste auf Mobilgeräten vertraut ist, ist sie auf Desktops weniger häufig – stellen Sie sicher, dass Sie mit der Maus halten und ziehen.
:::

<ComponentDemo
path='/webforj/refresher'
frame='mobile'
files={[
  'src/main/java/com/webforj/samples/views/refresher/RefresherView.java',
  'src/main/resources/static/css/refresher/refresher.css',
]}
/>

Dieser Ansatz wird häufig verwendet, um paginierte Listen zu aktualisieren oder das Laden des unendlichen Scrollens neu zu starten.

### Internationalisierung {#internationalization}

Jedes Zustandslabel kann auch mithilfe des `RefresherI18n`-Objekts lokalisiert werden. Die drei Zustände sind:

- Pull: Text für die Anfangsgeste (z. B. "Nach unten ziehen, um zu aktualisieren")
- Release: Auslöseschwelle erreicht (z. B. "Loslassen, um zu aktualisieren")
- Refresh: Ladezustand (z. B. "Wird aktualisiert")

Dies ermöglicht mehrsprachige Unterstützung und Anpassungen an der Markenidentität nach Bedarf.

<ComponentDemo
path='/webforj/refresheri18n'
frame='mobile'
files={[
  'src/main/java/com/webforj/samples/views/refresher/RefresherI18nView.java',
  'src/main/resources/static/css/refresher/refresher.css',
]}
/>

## Symbolanpassung {#icon-customization}

Sie können die [`Icons`](../components/icon) für die `pull`/`release` und `refreshing` Phasen entweder durch ein vordefiniertes [`Icon`](../components/icon) oder eine [Icon-URL](../managing-resources/assets-protocols) ändern. Diese sind nützlich, wenn Sie Branding oder eine benutzerdefinierte Animation anwenden möchten.

<ComponentDemo
path='/webforj/refreshericon'
frame='mobile'
files={[
  'src/main/java/com/webforj/samples/views/refresher/RefresherIconView.java',
  'src/main/resources/static/css/refresher/refresher.css',
]}
/>

## Pull-Verhalten Konfiguration {#pull-behavior-configuration}

### Schwellenwert {#threshold}

Legen Sie fest, wie weit der Benutzer nach unten ziehen muss (in Pixeln), bevor die Aktualisierung ausgelöst wird:

```java
refresher.setThreshold(80); // Standard: 80px
```

### Maximale Schwelle {#threshold-maximum}

Um die maximale Zugdistanz festzulegen, verwenden Sie die Methode `setThresholdMax()`:

```java
refresher.setThresholdMax(160);
```

Diese Schwellenwerte steuern die Empfindlichkeit der Geste und die Widerstandskurve.

## Zustandsmanagement {#state-management}

Die `Refresher`-Komponente verwaltet ihren eigenen internen Zustand und kommuniziert Zustandsänderungen über Ereignisse. Wenn ein Benutzer nach unten zieht und den definierten Schwellenwert überschreitet, gibt der `Refresher` ein Refresh-Ereignis aus, auf das Sie reagieren können, indem Sie einen `onRefresh()`-Listener registrieren.

Innerhalb dieses Listeners wird von Ihnen erwartet, dass Sie die erforderliche Operation durchführen – wie das Abrufen neuer Daten oder das Zurücksetzen einer Liste – und dann ausdrücklich aufrufen:

```java
refresher.finish();
```
:::warning Fehlendes `finish()`
Wenn Sie vergessen, `finish()` aufzurufen, bleibt der Refresher dauerhaft im Ladezustand.
:::

Sie können den `Refresher` auch programmgesteuert jederzeit deaktivieren, um zu verhindern, dass der Benutzer die Aktualisierungsfunktion auslöst:

```java
refresher.setEnabled(false);
```

Dies ist nützlich, wenn Aktualisierungen vorübergehend nicht zulässig sein sollten – zum Beispiel während eines Ladebildschirms oder wenn ein anderer kritischer Prozess ausgeführt wird.

## Stil {#styling}

### Themen {#themes}

Die `Refresher`-Komponente unterstützt mehrere Themen, um verschiedene Zustände visuell zu unterscheiden oder um das Erscheinungsbild Ihrer App zu entsprechen. Themen können mit der Methode `setTheme()` angewendet werden.

Das folgende Beispiel durchläuft alle verfügbaren Themen jedes Mal, wenn Sie zum Aktualisieren ziehen, und gibt Ihnen eine Live-Vorschau, wie der `Refresher` in verschiedenen Themen aussieht:

<ComponentDemo
path='/webforj/refresherthemes'
frame='mobile'
files={[
  'src/main/java/com/webforj/samples/views/refresher/RefresherThemesView.java',
  'src/main/resources/static/css/refresher/refresher.css',
]}
/>

<TableBuilder name="Refresher" />
