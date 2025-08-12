---
title: Refresher
sidebar_position: 101
_i18n_hash: 77c3e72a5a59a55d61a7dba79efb7324
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-refresher" />
<DocChip chip='since' label='25.00' />
<JavadocLink type="refresher" location="com/webforj/component/refresher/Refresher" top='true'/>

Die `Refresher`-Komponente in webforJ ermöglicht eine Pull-to-Refresh-Interaktion innerhalb von Scrollbereichen – ideal für das dynamische Laden von Daten in mobilen oder berührungsgerechten Schnittstellen. Während Benutzer nach unten wischen und eine konfigurierbare Schwelle überschreiten, wechselt der Refresher durch visuelle Zustände: `pull`, `release` und `refreshing`. Jeder Zustand präsentiert ein anpassbares Symbol und lokalisierte Texte, um das Feedback klar zu kommunizieren.

Sie können `Refresher` in Kombination mit Komponenten wie [`InfiniteScroll`](../components/infinitescroll) verwenden, um Inhalte neu zu laden oder den Status über einfache gestenbasierte Eingaben zurückzusetzen. Die Komponente ist vollständig konfigurierbar in Bezug auf Interaktionsverhalten, Erscheinungsbild, Lokalisierung und Integration mit dem Rest Ihrer Benutzeroberfläche.

## Instanziierung und Internationalisierung {#instantiation-and-internationalization}

Fügen Sie einen `Refresher` hinzu, indem Sie ihn instanziieren und einen Refresh-Listener registrieren. Wenn die Refresh-Vorgänge abgeschlossen sind, rufen Sie `finish()` auf, um die Komponente in ihren Leerlaufzustand zurückzusetzen.

:::info Aktivierung des `Refresher`
Um den `Refresher` zu aktivieren, **klicken und ziehen Sie nach unten** von der oberen Kante des scrollbaren Bereichs. Während diese Geste auf Mobilgeräten vertraut ist, ist sie auf Desktop weniger verbreitet – stellen Sie sicher, dass Sie mit der Maus halten und ziehen.
:::

<AppLayoutViewer
path='/webforj/refresher?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/refresher/RefresherView.java'
cssURL='/css/refresher/refresher.css'
height = '400px'
mobile='true'
/>

Dieser Ansatz wird häufig verwendet, um paginierte Listen zu aktualisieren oder das Laden unendlicher Scrolls neu zu starten.

### Internationalisierung {#internationalization}

Jeder Zustandsbezeichner kann auch mithilfe des `RefresherI18n`-Objekts lokalisiert werden. Die drei Zustände sind:

- Pull: Text für die anfängliche Geste (z. B. "Nach unten ziehen zum Aktualisieren")
- Release: Auslöseschwelle erreicht (z. B. "Loslassen zum Aktualisieren")
- Refresh: Ladezustand (z. B. "Aktualisiere")

Dies ermöglicht mehrsprachige Unterstützung und Anpassungen des Marketings nach Bedarf.

<AppLayoutViewer 
path='/webforj/refresheri18n?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/refresher/RefresherI18nView.java'
cssURL='/css/refresher/refresher.css'
height = '400px'
mobile='true'
/>

## Symbolanpassung {#icon-customization}

Sie können die [`Icons`](../components/icon) ändern, die für die `pull`/`release`- und `refreshing`-Phasen verwendet werden, entweder durch ein vordefiniertes [`Icon`](../components/icon) oder eine [Icon-URL](../managing-resources/assets-protocols). Diese sind nützlich, wenn Sie Branding oder eine benutzerdefinierte Animation anwenden möchten.

<AppLayoutViewer 
path='/webforj/refreshericon?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/refresher/RefresherIconView.java'
cssURL='/css/refresher/refresher.css'
height = '400px'
mobile='true'
/>

## Pull-Verhalten Konfiguration {#pull-behavior-configuration}

### Schwelle {#threshold}

Legen Sie fest, wie weit der Benutzer (in Pixeln) nach unten ziehen muss, bevor der Refresh ausgelöst wird:

```java
refresher.setThreshold(80); // standard: 80px
```

### Maximale Schwelle {#threshold-maximum}

Um die maximale zulässige Ziehdistanz zu definieren, verwenden Sie die Methode `setThresholdMax()`:

```java
refresher.setThresholdMax(160);
```

Diese Schwellenwerte steuern die Sensitivität und Widerstandskurve der Geste.

## Zustandsverwaltung {#state-management}

Die `Refresher`-Komponente verwaltet ihren eigenen internen Zustand und kommuniziert Zustandsänderungen über Ereignisse. Wenn ein Benutzer über die definierte Schwelle nach unten zieht, gibt der `Refresher` ein Refresh-Ereignis aus, auf das Sie reagieren können, indem Sie einen `onRefresh()`-Listener registrieren.

Innerhalb dieses Listeners wird erwartet, dass Sie die erforderlichen Operationen ausführen – beispielsweise neue Daten abrufen oder eine Liste zurücksetzen – und dann explizit aufrufen:

```java
refresher.finish();
```
:::warning Fehlendes `finish()`
Wenn Sie vergessen, `finish()` aufzurufen, bleibt der Refresher im Ladezustand, bis auf unbestimmte Zeit.
:::

Sie können auch den `Refresher` jederzeit programmatisch deaktivieren, um zu verhindern, dass der Benutzer das Refresh-Verhalten auslöst:

```java
refresher.setEnabled(false);
```

Dies ist nützlich, wenn Aktualisierungen vorübergehend nicht erlaubt sein sollten – zum Beispiel während eines Ladebildschirms oder während ein anderer kritischer Prozess läuft.

## Styling {#styling}

### Themen {#themes}

Die `Refresher`-Komponente unterstützt mehrere Themen, um verschiedene Zustände visuell zu unterscheiden oder dem Erscheinungsbild Ihrer App zu entsprechen. Themen können mit der Methode `setTheme()` angewendet werden.

Das folgende Beispiel durchläuft beim Aktualisieren jedes Mal alle verfügbaren Themen und bietet Ihnen eine Live-Vorschau, wie der `Refresher` in verschiedenen Themen aussieht:

<AppLayoutViewer 
path='/webforj/refresherthemes?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/refresher/RefresherThemesView.java'
cssURL='/css/refresher/refresher.css'
height = '400px'
mobile='true'
/>

<TableBuilder name="Refresher" />
