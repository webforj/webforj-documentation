---
title: Refresher
sidebar_position: 101
description: >-
  Enable pull-to-refresh on scrollable areas with the Refresher component, with
  pull, release, and refreshing states and i18n labels.
_i18n_hash: 9bb531347032e46ccbb9e7fa28c403f8
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-refresher" />
<DocChip chip='since' label='25.00' />
<JavadocLink type="refresher" location="com/webforj/component/refresher/Refresher" top='true'/>

Pull-to-refresh ist ein gängiges Muster in mobilen und berührungsfreundlichen Schnittstellen, und die `Refresher`-Komponente bringt es zu scrollbaren Containern in webforJ. Wenn Benutzer nach unten wischen, überschreiten sie einen konfigurierbaren Schwellenwert und wechseln durch visuelle Zustände: `pull`, `release` und `refreshing`, jeweils mit einem anpassbaren Symbol und lokalisiertem Text. Es passt gut zu [`InfiniteScroll`](../components/infinitescroll) zum Neuladen oder Zurücksetzen von Inhalten durch gestenbasierte Eingabe.

<!-- INTRO_END -->

## Instanziierung und Internationalisierung {#instantiation-and-internationalization}

Fügen Sie einen `Refresher` hinzu, indem Sie ihn instanziieren und einen Aktualisierungslistener registrieren. Wenn die Aktualisierung abgeschlossen ist, rufen Sie `finish()` auf, um die Komponente in ihren Ruhemodus zurückzusetzen.

:::info So aktivieren Sie den `Refresher`
Um den `Refresher` zu aktivieren, **klicken und ziehen Sie nach unten** von der Oberkante des scrollbaren Bereichs. Während diese Geste auf mobilen Geräten vertraut ist, ist sie auf dem Desktop weniger gebräuchlich - stellen Sie sicher, dass Sie mit der Maus halten und ziehen.
:::

<ComponentDemo
path='/webforj/refresher'
frame='mobile'
files={[
  'src/main/java/com/webforj/samples/views/refresher/RefresherView.java',
  'src/main/frontend/css/refresher/refresher.css',
]}
/>

Dieser Ansatz wird häufig verwendet, um paginierte Listen zu aktualisieren oder das Laden von unendlichem Scrollen neu zu starten.

### Internationalisierung {#internationalization}

Jedes Zustandsetikett kann auch mithilfe des `RefresherI18n`-Objekts lokalisiert werden. Die drei Zustände sind:

- Pull: Text der anfänglichen Geste (z. B. "Nach unten ziehen, um zu aktualisieren")
- Release: Auslöschschwelle erreicht (z. B. "Loslassen, um zu aktualisieren")
- Refresh: Ladezustand (z. B. "Wird aktualisiert")

Dies ermöglicht mehrsprachige Unterstützung und Anpassungen der Markenbildung nach Bedarf.

<ComponentDemo
path='/webforj/refresheri18n'
frame='mobile'
files={[
  'src/main/java/com/webforj/samples/views/refresher/RefresherI18nView.java',
  'src/main/frontend/css/refresher/refresher.css',
]}
/>

## Symbolanpassung {#icon-customization}

Sie können die verwendeten [`Icons`](../components/icon) für die `pull`/`release` und `refreshing` Phasen ändern, indem Sie entweder ein vordefiniertes [`Icon`](../components/icon) oder eine [Icon-URL](../managing-resources/assets-protocols) verwenden. Diese sind nützlich, wenn Sie Branding oder eine benutzerdefinierte Animation anwenden möchten.

<ComponentDemo
path='/webforj/refreshericon'
frame='mobile'
files={[
  'src/main/java/com/webforj/samples/views/refresher/RefresherIconView.java',
  'src/main/frontend/css/refresher/refresher.css',
]}
/>

## Pull-Verhalten-Konfiguration {#pull-behavior-configuration}

### Schwellenwert {#threshold}

Legen Sie fest, wie weit der Benutzer nach unten ziehen muss (in Pixeln), bevor die Aktualisierung ausgelöst wird:

```java
refresher.setThreshold(80); // Standard: 80px
```

### Maximale Schwelle {#threshold-maximum}

Um die maximale Ziehdistanz zu definieren, verwenden Sie die Methode `setThresholdMax()`:

```java
refresher.setThresholdMax(160);
```

Diese Schwellenwerte steuern die Gestensensibilität und die Widerstandskurve.

## Zustandsverwaltung {#state-management}

Die `Refresher`-Komponente verwaltet ihren eigenen internen Zustand und kommuniziert Zustandsänderungen über Ereignisse. Wenn ein Benutzer nach unten zieht, überschreitet er den definierten Schwellenwert, und der `Refresher` gibt ein Aktualisierungsereignis aus, auf das Sie reagieren können, indem Sie einen `onRefresh()`-Listener registrieren.

Innerhalb diesesListeners wird erwartet, dass Sie die erforderliche Operation ausführen - z. B. neue Daten abrufen oder eine Liste zurücksetzen - und dann explizit aufrufen:

```java
refresher.finish();
```
:::warning Fehlendes `finish()`
Wenn Sie vergessen, `finish()` aufzurufen, bleibt der Refresher im Ladezustand, bis Sie ihn zurücksetzen.
:::

Sie können den `Refresher` auch programmgesteuert deaktivieren, um zu verhindern, dass der Benutzer das Rückführungsverhalten auslöst:

```java
refresher.setEnabled(false);
```

Dies ist nützlich, wenn Aktualisierungen vorübergehend untersagt werden sollen - zum Beispiel während eines Ladebildschirms oder während ein anderer kritischer Prozess ausgeführt wird.

## Styling {#styling}

### Themen {#themes}

Die `Refresher`-Komponente unterstützt mehrere Themen, um verschiedene Zustände visuell zu unterscheiden oder an das Aussehen und Gefühl Ihrer App anzupassen. Themen können mit der Methode `setTheme()` angewendet werden.

Das folgende Beispiel wechselt durch alle verfügbaren Themen, jedes Mal, wenn Sie ziehen, um zu aktualisieren, und gibt Ihnen eine Live-Vorschau, wie die `Refresher`-Komponente über verschiedene Themen aussieht:

<ComponentDemo
path='/webforj/refresherthemes'
frame='mobile'
files={[
  'src/main/java/com/webforj/samples/views/refresher/RefresherThemesView.java',
  'src/main/frontend/css/refresher/refresher.css',
]}
/>

<TableBuilder name="Refresher" />
