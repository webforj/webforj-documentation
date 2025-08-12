---
title: Navigator
sidebar_position: 75
_i18n_hash: 920c1d604673e69a32f58161e3fd4e14
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-navigator" />
<DocChip chip='since' label='24.00' />
<JavadocLink type="foundation" location="com/webforj/component/navigator/Navigator" top='true'/>

Die `Navigator`-Komponente ist eine anpassbare Seitenzahl-Komponente, die dazu dient, durch Datensätze zu navigieren und mehrere Layouts unterstützt. Sie können sie so konfigurieren, dass verschiedene Navigationselemente wie Erst-, Letzt-, Weiter- und Zurück-Buttons sowie Seitenzahlen oder ein Schneller Feldsprung je nach Layouteinstellung angezeigt werden.

Sie unterstützt das automatische Deaktivieren von Navigationsschaltflächen basierend auf der aktuellen Seite und der Gesamtanzahl der Elemente und bietet Anpassungsoptionen für Text und Tooltips für verschiedene Teile des Navigators. Darüber hinaus können Sie sie an eine `Paginator`-Instanz binden, um die Paginierungslogik des Datensatzes zu verwalten und Änderungen in den Navigationselementen widerzuspiegeln.

## Binding an Repositories {#binding-to-repositories}

Oft zeigt eine `Navigator`-Komponente Informationen an, die in einem gebundenen `Repository` gefunden werden. Diese Bindung ermöglicht es dem `Navigator`, Daten, die vom Repository verwaltet werden, automatisch zu paginieren und andere bindbare Komponenten, wie Tabellen, basierend auf den navigierten Daten zu aktualisieren.

Um dies zu tun, übergeben Sie einfach das gewünschte `Repository`-Objekt an den Konstruktor eines anwendbaren `Navigator`-Objekts:

<ComponentDemo 
path='/webforj/navigatortable?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/navigator/NavigatorTableView.java'
height='475px'
/>

Dieses Beispiel erstellt den `Navigator` und die [`Table`](table/overview) mit derselben `Repository`-Instanz. Das bedeutet, dass beim Navigieren zu einer neuen Seite mit dem `Navigator` die [`Table`](table/overview) diese Änderung erkennt und neu rendert.

## Paginierung {#pagination}

Die `Navigator`-Komponente ist eng mit der `Paginator`-Modellklasse verknüpft, die Metadaten zur Paginierung berechnet, wie die Gesamtzahl der Seiten, Start-/Endindizes der Elemente auf der aktuellen Seite und ein Array von Seitenzahlen zur Navigation.

Obwohl es nicht unbedingt notwendig ist, ermöglicht die Nutzung des `Paginator` die Logik hinter der Navigation. Beim Integrationsprozess mit einem `Paginator` reagiert der Navigator auf Änderungen innerhalb des `Paginators`. `Navigator`-Objekte haben über die Methode `getPaginator()` Zugriff auf einen integrierten `Paginator`. Es kann auch eine `Paginator`-Instanz über die Methode `setPaginator()` oder die Anwendung eines der passenden Konstruktoren akzeptieren.

Dieser Abschnitt enthält praktische Codebeispiele, um zu veranschaulichen, wie diese Integration in der Praxis funktioniert.

### Elemente {#items}

Der Begriff "Elemente" bezeichnet die einzelnen paginierten Elemente oder Datensätze. Diese könnten Aufzeichnungen, Einträge oder beliebige diskrete Einheiten innerhalb eines Datensatzes sein. Sie können die Gesamtzahl der Elemente mit der Methode `setTotalItems()` festlegen.

```java
navigator.getPaginator().setTotalItems(totalItems);
```

:::info
Ein Repository, das mit der `Paginator`-Instanz assoziiert ist, verfügt über die Gesamtzahl der Elemente, die direkt vom Repository verwaltet werden und kann nicht direkt festgelegt werden.
:::

### Maximale Seiten {#maximum-pages}

Die Methode `setMax()` ermöglicht es Ihnen, die maximale Anzahl der anzuzeigenden Seitenlinks in der Paginierungsnavigation zu definieren. Dies ist besonders nützlich, wenn Sie es mit einer großen Anzahl von Seiten zu tun haben, da sie die Anzahl der Seitenlinks steuert, die dem Benutzer zu einem bestimmten Zeitpunkt angezeigt werden.

```java
navigator.getPaginator().setMax(maxPages);
```

<ComponentDemo 
path='/webforj/navigatorpages?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/navigator/NavigatorPagesView.java'
height='125px'
/>

Dieses Programm zeigt maximal fünf Seiten im `Navigator` gleichzeitig an, indem die Methode `getPaginator()` verwendet wird, um den mit dem `Navigator`-Objekt verbundenen `Paginator` abzurufen, und dann die Methode `setMax()` verwendet wird, um eine gewünschte Anzahl angezeigter maximaler Seiten anzugeben.

### Seitengröße {#page-size}

Die Methode `setSize()` ermöglicht es Ihnen, die Anzahl der Elemente festzulegen, die auf jeder Seite der Paginierung angezeigt werden sollen. Wenn Sie diese Methode aufrufen und eine neue Seitengröße angeben, wird die Paginierung entsprechend angepasst.

```java
navigator.getPaginator().setSize(pageSize);
```

## Anpassung von Schaltflächen, Text und Tooltips {#customizing-buttons-text-and-tooltips}

Die `Navigator`-Komponente bietet umfangreiche Anpassungsoptionen für Schaltflächen, Text und Tooltips. Um den angezeigten Text in der `Navigator`-Komponente zu ändern, verwenden Sie die Methode `setText()`. Diese Methode nimmt Text sowie den gewünschten `Part` des `Navigators`.

Im folgenden Beispiel zeigt die Methode `setText()` einen numerischen Wert für den Benutzer an. Das Klicken der Schaltflächen löst die Methode `onChange` des `Navigators` aus, die mit einem `Direction`-Wert der angeklickten Schaltfläche kommt.

<ComponentDemo 
path='/webforj/navigatorbasic?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/navigator/NavigatorBasicView.java'
height='100px'
/>

### Schaltflächen- und Komponententext {#buttons-and-component-text}

Die Methode `setText()` bewertet den Textparameter als JavaScript-Ausdruck unter Verwendung der folgenden Parameter:

- `page` - die aktuelle Seitenzahl
- `current` - die aktuell ausgewählte Seitenzahl
- `x` - ein Alias für die aktuelle Seite
- `startIndex` - der Startindex der aktuellen Seite.
- `endIndex` - der Endindex der aktuellen Seite.
- `totalItems` - die Gesamtzahl der Elemente.
- `startPage` - die Startseitenzahl.
- `endPage` - die Endseitenzahl.
- `component` - die Navigator-Client-Komponente.

<!-- vale off -->
Um beispielsweise den Text der letzten Seite-Schaltfläche in einem `Navigator` mit 10 Seiten auf "Gehe zu Seite 10" zu setzen, verwenden Sie den folgenden Codeausschnitt: 
<!-- vale on -->

```java
navigator.setText("'Gehe zu Seite ' + endPage", Navigator.Part.LAST_BUTTON);
```

### Tooltip-Text {#tooltip-text}

Sie können Tooltips für verschiedene Teile der `Navigator`-Komponente mithilfe der Methode `setTooltipText()` anpassen. Tooltips bieten hilfreiche Hinweise für Benutzer, wenn sie über Navigationselemente fahren.

:::info
Tooltip-Text wird nicht in JavaScript ausgewertet, im Gegensatz zu dem Text, der von der Methode `setText()` verwendet wird.
:::

<!-- vale off -->
Um beispielsweise den Tooltip-Text der letzten Seite-Schaltfläche in einem `Navigator` auf "Gehe zur letzten Seite" zu setzen, verwenden Sie den folgenden Codeausschnitt:
<!-- vale on -->

```java
navigator.setTooltipText("Gehe zur letzten Seite", Navigator.Part.LAST_BUTTON);
```

## Layouts {#layouts}

Für die `Navigator`-Komponente stehen verschiedene Layoutoptionen zur Verfügung, um Flexibilität bei der Anzeige von Paginierungssteuerelementen zu bieten. Um auf diese Layouts zuzugreifen, verwenden Sie die Werte des Enums `Navigator.Layout`. Die Optionen sind wie folgt:

<ComponentDemo 
path='/webforj/navigatorlayout?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/navigator/NavigatorLayoutView.java'
height='200px'
/>

### 1. Kein Layout {#1-none-layout}

Das `NONE`-Layout rendert keinen Text innerhalb des `Navigators` und zeigt nur die Navigationsschaltflächen ohne standardmäßige textuelle Anzeige an. Um dieses Layout zu aktivieren, verwenden Sie:

```java
navigator.setLayout(Navigator.Layout.NONE);
```

### 2. Nummeriertes Layout {#2-numbered-layout}

Das nummerierte Layout zeigt nummerierte Chips an, die jeder Seite im Anzeigebereich des `Navigators` entsprechen. Dieses Layout eignet sich ideal für Szenarien, in denen Benutzer die direkte Navigation zu bestimmten Seiten bevorzugen. Um dieses Layout zu aktivieren, verwenden Sie:

```java
navigator.setLayout(Navigator.Layout.PAGES);
```

### 3. Vorschau-Layout {#3-preview-layout}

Das Vorschau-Layout zeigt die aktuelle Seitenzahl und die Gesamtzahl der Seiten an und ist geeignet für kompakte Paginierungsinterfaces mit begrenztem Platz.

:::info
Die Vorschau ist das standardmäßige `Navigator`-Layout.
:::

Um dieses Layout zu aktivieren, verwenden Sie:

```java
navigator.setLayout(Navigator.Layout.PREVIEW);
```

### 4. Schneller Sprung-Layout {#4-quick-jump-layout}

Das schnelle Sprung-Layout bietet ein [NumberField](./fields/number-field.md), in das Benutzer eine Seitenzahl für eine schnelle Navigation eingeben können. Dies ist nützlich, wenn Benutzer schnell zu einer bestimmten Seite navigieren müssen, insbesondere bei großen Datensätzen. Um dieses Layout zu aktivieren, verwenden Sie:

```java
navigator.setLayout(Navigator.Layout.QUICK_JUMP);
```

## Styling {#styling}

<TableBuilder name="Navigator" />

## Beste Praktiken {#best-practices}

Um ein optimales Benutzererlebnis bei der Verwendung der `Navigator`-Komponente zu gewährleisten, sollten Sie die folgenden Best Practices berücksichtigen:

- **Dataset verstehen**: Bevor Sie eine `Navigator`-Komponente in Ihre App integrieren, sollten Sie die Datenbrowse-Anforderungen Ihrer Benutzer gründlich verstehen. Berücksichtigen Sie Faktoren wie die Größe des Datensatzes, typische Benutzerinteraktionen und bevorzugte Navigationsmuster.

- **Angemessenes Layout wählen**: Wählen Sie ein Layout für die `Navigator`-Komponente, das mit den Benutzererfahrungszielen und dem verfügbaren Bildschirmplatz übereinstimmt.

- **Text und Tooltips anpassen**: Passen Sie den Text und die Tooltips der `Navigator`-Komponente an, um die Sprache und Terminologie Ihrer App zu entsprechen. Stellen Sie beschreibende Labels und hilfreiche Hinweise zur Verfügung, um Benutzer effektiv bei der Navigation durch den Datensatz zu unterstützen.
