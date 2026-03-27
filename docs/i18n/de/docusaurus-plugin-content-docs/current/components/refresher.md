---
title: Refresher
sidebar_position: 101
_i18n_hash: 763037d616f2274feb7a7ed24b9c91f0
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-refresher" />
<DocChip chip='since' label='25.00' />
<JavadocLink type="refresher" location="com/webforj/component/refresher/Refresher" top='true'/>

Pull-to-refresh ist ein gängiges Muster in mobilen und touch-freundlichen Oberflächen, und die `Refresher`-Komponente bringt es in scrollbare Container in webforJ. Wenn Benutzer nach unten wischen und dabei einen konfigurierbaren Schwellenwert überschreiten, wechselt die Komponente durch visuelle Zustände: `pull`, `release` und `refreshing`, jeweils mit einem anpassbaren Symbol und lokalisiertem Text. Sie ergänzt sich gut mit [`InfiniteScroll`](../components/infinitescroll) zum Neuladen oder Zurücksetzen von Inhalten durch gestenbasierte Eingaben.

<!-- INTRO_END -->

## Instanziierung und Internationalisierung {#instantiation-and-internationalization}

Fügen Sie einen `Refresher` hinzu, indem Sie ihn instanziieren und einen Refresh-Listener registrieren. Wenn die Refresh-Operationen abgeschlossen sind, rufen Sie `finish()` auf, um die Komponente in ihren Leerlauffzustand zurückzusetzen.

:::info So aktivieren Sie den `Refresher`
Um den `Refresher` zu aktivieren, **klicken und ziehen Sie nach unten** von der oberen Kante des scrollbaren Bereichs. Während diese Geste auf mobilen Geräten vertraut ist, ist sie auf dem Desktop weniger üblich—stellen Sie sicher, dass Sie mit der Maus halten und ziehen.
:::

<AppLayoutViewer
path='/webforj/refresher?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/refresher/RefresherView.java'
cssURL='/css/refresher/refresher.css'
height = '400px'
mobile='true'
/>

Dieser Ansatz wird häufig verwendet, um paginierte Listen zu aktualisieren oder das Laden von Inhalten im Endlos-Scroll-Modus neu zu starten.

### Internationalisierung {#internationalization}

Jede Zustandsbezeichnung kann auch mit dem `RefresherI18n`-Objekt lokalisiert werden. Die drei Zustände sind:

- Pull: Initiales Geste-Zeichen (z. B. „Nach unten ziehen, um zu aktualisieren“)
- Release: Auslöseschwellenwert erreicht (z. B. „Loslassen, um zu aktualisieren“)
- Refresh: Ladezustand (z. B. „Aktualisieren“)

Dies ermöglicht mehrsprachige Unterstützung und Anpassungen der Markenidentität nach Bedarf.

<AppLayoutViewer 
path='/webforj/refresheri18n?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/refresher/RefresherI18nView.java'
cssURL='/css/refresher/refresher.css'
height = '400px'
mobile='true'
/>

## Symbolanpassung {#icon-customization}

Sie können die verwendeten [`Icons`](../components/icon) für die Zustände `pull`/`release` und `refreshing` ändern, indem Sie entweder ein vordefiniertes [`Icon`](../components/icon) oder eine [Icon-URL](../managing-resources/assets-protocols) verwenden. Diese sind nützlich, wenn Sie Branding oder eine benutzerdefinierte Animation anwenden möchten.

<AppLayoutViewer 
path='/webforj/refreshericon?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/refresher/RefresherIconView.java'
cssURL='/css/refresher/refresher.css'
height = '400px'
mobile='true'
/>

## Konfiguration des Pull-Verhaltens {#pull-behavior-configuration}

### Schwellenwert {#threshold}

Legen Sie fest, wie weit der Benutzer nach unten ziehen muss (in Pixeln), bevor der Refresh ausgelöst wird:

```java
refresher.setThreshold(80); // Standard: 80px
```

### Maximale Schwellenwert {#threshold-maximum}

Um die maximal erlaubte Ziehdistanz zu definieren, verwenden Sie die Methode `setThresholdMax()`:

```java
refresher.setThresholdMax(160);
```

Diese Schwellenwerte steuern die Empfindlichkeit der Geste und die Widerstandskurve.

## Zustandsmanagement {#state-management}

Die `Refresher`-Komponente verwaltet ihren eigenen internen Zustand und kommuniziert Zustandsänderungen durch Ereignisse. Wenn ein Benutzer nach unten zieht, und dabei über den definierten Schwellenwert hinausgeht, gibt die `Refresher` ein Refresh-Ereignis aus, auf das Sie reagieren können, indem Sie einen `onRefresh()`-Listener registrieren.

Innerhalb dieses Listeners wird von Ihnen erwartet, dass Sie die erforderliche Operation durchführen—z. B. neue Daten abrufen oder eine Liste zurücksetzen—und dann ausdrücklich aufrufen:

```java
refresher.finish();
```
:::warning Fehlendes `finish()`
Wenn Sie vergessen, `finish()` aufzurufen, bleibt der Refresher im Lademodus hängen.
:::

Sie können den `Refresher` auch programmgesteuert jederzeit deaktivieren, um zu verhindern, dass der Benutzer das Refresh-Verhalten auslöst:

```java
refresher.setEnabled(false);
```

Dies ist nützlich, wenn Refreshes vorübergehend nicht erlaubt sein sollen—zum Beispiel während eines Ladebildschirms oder während ein anderer kritischer Prozess ausgeführt wird.

## Styling {#styling}

### Themen {#themes}

Die `Refresher`-Komponente unterstützt mehrere Themen, um verschiedene Zustände visuell zu unterscheiden oder um das Aussehen und Gefühl Ihrer App anzupassen. Themen können mit der Methode `setTheme()` angewendet werden.

Das folgende Beispiel durchläuft alle verfügbaren Themen, jedes Mal, wenn Sie zum Aktualisieren ziehen, und gibt Ihnen eine Live-Vorschau, wie die `Refresher`-Komponente in verschiedenen Themen aussieht:

<AppLayoutViewer 
path='/webforj/refresherthemes?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/refresher/RefresherThemesView.java'
cssURL='/css/refresher/refresher.css'
height = '400px'
mobile='true'
/>

<TableBuilder name="Refresher" />
