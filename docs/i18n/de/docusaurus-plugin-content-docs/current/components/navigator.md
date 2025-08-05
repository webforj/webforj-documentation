---
title: Navigator
sidebar_position: 75
_i18n_hash: 7c09a72456eb84a8227da3ff263b6c69
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-navigator" />
<DocChip chip='since' label='24.00' />
<JavadocLink type="foundation" location="com/webforj/component/navigator/Navigator" top='true'/>

Die `Navigator`-Komponente ist eine anpassbare Paginierungskomponente, die entwickelt wurde, um durch Datensätze zu navigieren und mehrere Layouts zu unterstützen. Sie können sie so konfigurieren, dass verschiedene Navigationssteuerungen angezeigt werden, wie z.B. Schaltflächen für erste, letzte, nächste und vorherige Seiten sowie Seitennummern oder ein Schnellzugriffsfeld, abhängig von den Layout-Einstellungen.

Sie unterstützt das automatische Deaktivieren von Navigationsschaltflächen basierend auf der aktuellen Seite und der Gesamtanzahl der Elemente und bietet Anpassungsoptionen für Text und Tooltips für verschiedene Teile des Navigators. Zudem können Sie diese an eine `Paginator`-Instanz binden, um die Paginierungslogik des Datensatzes zu verwalten und Änderungen in den Navigationssteuerungen widerzuspiegeln.

## Binden an Repositories {#binding-to-repositories}

Oft zeigt eine `Navigator`-Komponente Informationen an, die in einem gebundenen `Repository` gefunden werden. Diese Bindung ermöglicht es dem `Navigator`, Daten, die vom Repository verwaltet werden, automatisch zu paginieren und andere bindbare Komponenten, wie z.B. Tabellen, basierend auf den navigierten Daten zu aktualisieren.

Um dies zu tun, geben Sie einfach das gewünschte `Repository`-Objekt an den Konstruktor des entsprechenden `Navigator`-Objekts weiter:

<ComponentDemo 
path='/webforj/navigatortable?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/navigator/NavigatorTableView.java'
height='475px'
/>

Dieses Beispiel erstellt den `Navigator` und die [`Table`](table/overview) mit derselben `Repository`-Instanz. Das bedeutet, dass der [`Table`](table/overview) diese Änderung erkennt und neu rendert, wenn zu einer neuen Seite mit dem `Navigator` navigiert wird.

## Paginierung {#pagination}

Die `Navigator`-Komponente ist eng mit der `Paginator`-Modellklasse verknüpft, die Metadaten zur Paginierung berechnet, wie die Gesamtzahl der Seiten, Start-/Endindizes der Elemente auf der aktuellen Seite und ein Array von Seitennummern zur Navigation.

Obwohl nicht unbedingt erforderlich, ermöglicht die Nutzung des `Paginator` die Logik hinter der Navigation. Wenn Sie mit einem `Paginator` integrieren, reagiert der Navigator auf alle Änderungen innerhalb des `Paginator`. `Navigator`-Objekte haben über die Methode `getPaginator()` Zugriff auf einen eingebauten `Paginator`. Es kann auch eine `Paginator`-Instanz über die Methode `setPaginator()` akzeptieren oder die Nutzung eines der anwendbaren Konstruktoren.

Dieser Abschnitt enthält praktische Codebeispiele, um zu veranschaulichen, wie diese Integration in der Praxis funktioniert.

### Elemente {#items}

Der Begriff "Elemente" bezeichnet die einzelnen paginierten Elemente oder Daten. Dies können Datensätze, Einträge oder beliebige diskrete Einheiten innerhalb eines Datensatzes sein. Sie können die Gesamtzahl der Elemente mit der Methode `setTotalItems()` festlegen.

```java
navigator.getPaginator().setTotalItems(totalItems);
```

:::info
Ein mit der `Paginator`-Instanz verknüpftes Repository hat die Gesamtzahl der von dem Repository direkt verwalteten Elemente und kann nicht direkt festgelegt werden.
:::

### Maximale Seiten {#maximum-pages}

Die Methode `setMax()` ermöglicht es Ihnen, die maximale Anzahl der Seitenlinks festzulegen, die in der Paginierungsnavigation angezeigt werden sollen. Dies ist besonders nützlich, wenn Sie es mit einer großen Anzahl von Seiten zu tun haben, da Sie die Anzahl der Seitenlinks steuern können, die dem Benutzer zu einem bestimmten Zeitpunkt angezeigt werden.

```java
navigator.getPaginator().setMax(maxPages);
```

<ComponentDemo 
path='/webforj/navigatorpages?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/navigator/NavigatorPagesView.java'
height='125px'
/>

Dieses Programm zeigt maximal fünf Seiten auf dem `Navigator` gleichzeitig an, indem die Methode `getPaginator()` verwendet wird, um den mit dem `Navigator`-Objekt verbundenen `Paginator` abzurufen, und dann die Methode `setMax()` verwendet wird, um eine gewünschte Anzahl maximal angezeigter Seiten anzugeben.

### Seitenanzahl {#page-size}

Die Methode `setSize()` ermöglicht es Ihnen, die Anzahl der Elemente festzulegen, die auf jeder Seite der Paginierung angezeigt werden sollen. Wenn Sie diese Methode aufrufen und eine neue Seitenanzahl angeben, passt sie die Paginierung entsprechend an.

```java
navigator.getPaginator().setSize(pageSize);
```

## Anpassung von Schaltflächen, Text und Tooltips {#customizing-buttons-text-and-tooltips}

Die `Navigator`-Komponente bietet umfangreiche Anpassungsoptionen für Schaltflächen, Text und Tooltips. Um den angezeigten Text auf der `Navigator`-Komponente zu ändern, verwenden Sie die Methode `setText()`. Diese Methode erwartet Text sowie den gewünschten `Part` des `Navigators`.

Im folgenden Beispiel zeigt die Methode `setText()` einen numerischen Wert an den Benutzer. Das Klicken der Schaltflächen löst die Methode `onChange` des `Navigators` aus, die mit einem `Direction`-Wert der angeklickten Schaltfläche kommt.

<ComponentDemo 
path='/webforj/navigatorbasic?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/navigator/NavigatorBasicView.java'
height='100px'
/>

### Schaltflächen- und Komponententext {#buttons-and-component-text}

Die Methode `setText()` bewertet den Textparameter als JavaScript-Ausdruck unter Verwendung der folgenden Parameter:

- `page` - die aktuelle Seitennummer
- `current` - die aktuell ausgewählte Seitennummer
- `x` - ein Alias für die aktuelle Seite
- `startIndex` - Der Startindex der aktuellen Seite.
- `endIndex` - Der Endindex der aktuellen Seite.
- `totalItems` - Die Gesamtzahl der Elemente.
- `startPage` - Die Startseitennummer.
- `endPage` - Die Endseitennummer.
- `component` - Die Navigator-Clientkomponente.

<!-- vale off -->
Um beispielsweise den Text der Schaltfläche für die letzte Seite in einem `Navigator` mit 10 Seiten auf "Gehe zu Seite 10" zu setzen, verwenden Sie den folgenden Code-Schnipsel: 
<!-- vale on -->

```java
navigator.setText("'Gehe zu Seite ' + endPage", Navigator.Part.LAST_BUTTON);
```

### Tooltip-Text {#tooltip-text}

Sie können Tooltips für verschiedene Teile der `Navigator`-Komponente mit der Methode `setTooltipText()` anpassen. Tooltips bieten hilfreiche Hinweise für Benutzer, wenn sie über Navigationselemente fahren.

:::info
Tooltip-Text wird nicht als JavaScript ausgewertet, im Gegensatz zu dem Text, der von der Methode `setText()` verwendet wird.
:::

<!-- vale off -->
Um beispielsweise den Tooltip-Text der Schaltfläche für die letzte Seite in einem `Navigator` auf "Gehe zur letzten Seite" zu setzen, verwenden Sie den folgenden Code-Schnipsel:
<!-- vale on -->

```java
navigator.setTooltipText("Gehe zur letzten Seite", Navigator.Part.LAST_BUTTON);
```

## Layouts {#layouts}

Für die `Navigator`-Komponente gibt es verschiedene Layoutoptionen, um Flexibilität bei der Anzeige von Paginierungssteuerungen zu bieten. Um auf diese Layouts zuzugreifen, verwenden Sie die Werte des `Navigator.Layout`-Enums. Die Optionen sind wie folgt:

<ComponentDemo 
path='/webforj/navigatorlayout?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/navigator/NavigatorLayoutView.java'
height='200px'
/>

### 1. Kein Layout {#1-none-layout}

Das `NONE`-Layout zeigt keinen Text innerhalb des `Navigator` an und zeigt nur die Navigationsschaltflächen ohne eine Standardtextanzeige an. Um dieses Layout zu aktivieren, verwenden Sie:

```java
navigator.setLayout(Navigator.Layout.NONE);
```

### 2. Nummeriertes Layout {#2-numbered-layout}

Das nummerierte Layout zeigt nummerierte Chips an, die jeder Seite im Anzeigebereich des `Navigators` entsprechen. Die Verwendung dieses Layouts ist ideal für Szenarien, in denen Benutzer eine direkte Navigation zu bestimmten Seiten bevorzugen. Um dieses Layout zu aktivieren, verwenden Sie:

```java
navigator.setLayout(Navigator.Layout.PAGES);
```

### 3. Vorschau-Layout {#3-preview-layout}

Das Vorschau-Layout zeigt die aktuelle Seitenzahl und die Gesamtzahl der Seiten an und eignet sich für kompakte Paginierungsoberflächen mit begrenztem Platz.

:::info
Vorschau ist das Standardlayout des `Navigators`.
:::

Um dieses Layout zu aktivieren, verwenden Sie:

```java
navigator.setLayout(Navigator.Layout.PREVIEW);
```

### 4. Schnellzugriffs-Layout {#4-quick-jump-layout}

Das Schnellzugriffs-Layout bietet ein [NumberField](./fields/number-field.md) für Benutzer, um eine Seitennummer für eine schnelle Navigation einzugeben. Dies ist nützlich, wenn Benutzer schnell zu einer bestimmten Seite navigieren müssen, insbesondere bei großen Datensätzen. Um dieses Layout zu aktivieren, verwenden Sie:

```java
navigator.setLayout(Navigator.Layout.QUICK_JUMP);
```

## Styling {#styling}

<TableBuilder name="Navigator" />

## Best Practices {#best-practices}

Um ein optimales Benutzererlebnis bei der Verwendung der `Navigator`-Komponente sicherzustellen, sollten Sie die folgenden Best Practices beachten:

- **Dataset verstehen**: Bevor Sie die `Navigator`-Komponente in Ihre App integrieren, sollten Sie die Anforderungen an die Datenbrowser der Benutzer gründlich verstehen. Berücksichtigen Sie Faktoren wie die Größe des Datensatzes, typische Benutzerinteraktionen und bevorzugte Navigationsmuster.

- **Geeignetes Layout wählen**: Wählen Sie ein Layout für die `Navigator`-Komponente aus, das mit den Zielen der Benutzererfahrung und dem verfügbaren Bildschirmplatz übereinstimmt.

- **Text und Tooltips anpassen**: Passen Sie den Text und die Tooltips der `Navigator`-Komponente an, um die Sprache und Terminologie zu berücksichtigen, die in Ihrer App verwendet wird. Bieten Sie beschreibende Labels und hilfreiche Hinweise an, um den Benutzern zu helfen, effizient durch den Datensatz zu navigieren.
