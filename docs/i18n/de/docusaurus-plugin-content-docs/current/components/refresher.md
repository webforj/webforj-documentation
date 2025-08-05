---
title: Refresher
sidebar_position: 101
_i18n_hash: de00fad980f74bdd18544409408de0b8
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-refresher" />
<DocChip chip='since' label='25.00' />
<JavadocLink type="refresher" location="com/webforj/component/refresher/Refresher" top='true'/>

Die `Refresher`-Komponente in webforJ ermöglicht eine Pull-to-Refresh-Interaktion innerhalb von scrollbaren Containern – ideal für das dynamische Laden von Daten in mobilen oder benutzerfreundlichen Schnittstellen. Wenn die Benutzer nach unten wischen, überschreiten sie eine konfigurierbare Schwelle, und der Refresher durchläuft visuelle Zustände: `pull`, `release` und `refreshing`. Jeder Zustand zeigt ein anpassbares Symbol und lokalisierte Texte an, um das Feedback klar zu kommunizieren.

Sie können `Refresher` zusammen mit Komponenten wie [`InfiniteScroll`](../components/infinitescroll) verwenden, um Inhalte neu zu laden oder den Zustand über einfache gestenbasierte Eingaben zurückzusetzen. Die Komponente ist vollständig konfigurierbar in Bezug auf Interaktionsverhalten, Erscheinungsbild, Lokalisierung und Integration mit dem Rest Ihrer Benutzeroberfläche.

## Instanziierung und Internationalisierung {#instantiation-and-internationalization}

Fügen Sie einen `Refresher` hinzu, indem Sie ihn instanziieren und einen Refresh-Listener registrieren. Wenn die Aktualisierungsoperationen abgeschlossen sind, rufen Sie `finish()` auf, um die Komponente in ihren Leerlaufzustand zurückzusetzen.

:::info So aktivieren Sie den `Refresher`
Um den `Refresher` zu aktivieren, **klicken und ziehen Sie nach unten** von der Oberseite des scrollbaren Bereichs. Während diese Geste auf mobilen Geräten vertraut ist, ist sie auf Desktop-Geräten weniger üblich – stellen Sie sicher, dass Sie mit Ihrer Maus halten und ziehen.
:::

<AppLayoutViewer
path='/webforj/refresher?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/refresher/RefresherView.java'
cssURL='/css/refresher/refresher.css'
height = '400px'
mobile='true'
/>

Dieser Ansatz wird häufig verwendet, um paginierte Listen zu aktualisieren oder das Laden des unendlichen Scrollens neu zu starten.

### Internationalisierung {#internationalization}

Jeder Zustandslabel kann auch mithilfe des `RefresherI18n`-Objekts lokalisiert werden. Die drei Zustände sind:

- Pull: Text der ersten Geste (z.B. "Nach unten ziehen zum Aktualisieren")
- Release: Trigger-Schwelle erreicht (z.B. "Loslassen, um zu aktualisieren")
- Refresh: Ladezustand (z.B. "Wird aktualisiert")

Dies ermöglicht mehrsprachige Unterstützung und Branding-Anpassungen nach Bedarf.

<AppLayoutViewer 
path='/webforj/refresheri18n?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/refresher/RefresherI18nView.java'
cssURL='/css/refresher/refresher.css'
height = '400px'
mobile='true'
/>

## Symbolanpassung {#icon-customization}

Sie können die verwendeten [`Icons`](../components/icon) für die `pull`/`release` und `refreshing` Phasen entweder mit einer vordefinierten [`Icon`](../components/icon) oder einer [Icon-URL](../managing-resources/assets-protocols) ändern. Diese sind nützlich, wenn Sie Branding oder eine benutzerdefinierte Animation anwenden möchten.

<AppLayoutViewer 
path='/webforj/refreshericon?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/refresher/RefresherIconView.java'
cssURL='/css/refresher/refresher.css'
height = '400px'
mobile='true'
/>

## Pull-Verhalten Konfiguration {#pull-behavior-configuration}

### Schwelle {#threshold}

Legen Sie fest, wie weit der Benutzer nach unten ziehen muss (in Pixel), bevor das Aktualisieren ausgelöst wird:

```java
refresher.setThreshold(80); // Standard: 80px
```

### Maximale Schwelle {#threshold-maximum}

Um die maximal erlaubte Ziehdistanz festzulegen, verwenden Sie die Methode `setThresholdMax()`:

```java
refresher.setThresholdMax(160);
```

Diese Schwellen steuern die Gestensensibilität und den Widerstandskurve.

## Statusverwaltung {#state-management}

Die `Refresher`-Komponente verwaltet ihren eigenen internen Zustand und kommuniziert Zustandsänderungen über Ereignisse. Wenn ein Benutzer nach unten zieht, überschreitet er die definierte Schwelle, der `Refresher` gibt ein Aktualisierungsevent aus, auf das Sie reagieren können, indem Sie einen `onRefresh()`-Listener registrieren.

Innerhalb dieses Listeners wird erwartet, dass Sie die erforderliche Operation durchführen – beispielsweise das Abrufen neuer Daten oder das Zurücksetzen einer Liste – und dann explizit aufrufen:

```java
refresher.finish();
```
:::warning Fehlendes `finish()`
Wenn Sie vergessen, `finish()` aufzurufen, bleibt der Refresher im Ladezustand, bis Sie ihn manuell zurücksetzen.
:::

Sie können auch den `Refresher` jederzeit programmgesteuert deaktivieren, um zu verhindern, dass der Benutzer das Aktualisieren auslöst:

```java
refresher.setEnabled(false);
```

Dies ist nützlich, wenn Aktualisierungen vorübergehend untersagt werden sollten – beispielsweise während eines Ladebildschirms oder während ein anderer kritischer Prozess ausgeführt wird.

## Styling {#styling}

### Themen {#themes}

Die `Refresher`-Komponente unterstützt mehrere Themen, um unterschiedliche Zustände visuell zu unterscheiden oder um das Aussehen und Gefühl Ihrer App anzupassen. Themen können mittels der Methode `setTheme()` angewendet werden.

Das folgende Beispiel wechselt durch alle verfügbaren Themen, jedes Mal wenn Sie zum Aktualisieren ziehen, sodass Sie eine Live-Vorschau davon erhalten, wie der `Refresher` in verschiedenen Themen aussieht:

<AppLayoutViewer 
path='/webforj/refresherthemes?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/refresher/RefresherThemesView.java'
cssURL='/css/refresher/refresher.css'
height = '400px'
mobile='true'
/>

<TableBuilder name="Refresher" />
