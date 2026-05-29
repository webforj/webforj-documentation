---
title: Navigator
sidebar_position: 75
_i18n_hash: db351d8f9fdf344a571d374e8d373f22
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-navigator" />
<DocChip chip='since' label='24.00' />
<JavadocLink type="foundation" location="com/webforj/component/navigator/Navigator" top='true'/>

Die `Navigator`-Komponente fügt Steuerungselemente für die Seitenpagination hinzu, um durch Datensätze zu navigieren. Sie kann Schaltflächen für "Erste", "Letzte", "Nächste" und "Vorherige" sowie Seitenzahlen oder ein Schnellzugriffsfeld anzeigen und deaktiviert automatisch die Steuerungselemente, wenn diese nicht anwendbar sind. Sie bindet sich an eine `Paginator`-Instanz, um die zugrunde liegende Paginierungslogik zu verwalten.

<!-- INTRO_END -->

## Binding an Repositories {#binding-to-repositories}

Oft zeigt eine `Navigator`-Komponente Informationen, die in einem gebundenen `Repository` gefunden werden. Diese Bindung ermöglicht es dem `Navigator`, Daten, die vom Repository verwaltet werden, automatisch zu paginieren und andere bindbare Komponenten, wie Tabellen, basierend auf den navigierten Daten zu aktualisieren.

Um dies zu tun, geben Sie einfach das gewünschte `Repository`-Objekt an den Konstruktor eines geeigneten `Navigator`-Objekts weiter:

<ComponentDemo
path='/webforj/navigatortable'
files={['src/main/java/com/webforj/samples/views/navigator/NavigatorTableView.java']}
height='475px'
/>

Dieses Beispiel erstellt den `Navigator` und [`Table`](table/overview) mit der gleichen `Repository`-Instanz. Das bedeutet, dass der [`Table`](table/overview) diese Änderung erkennt und neu rendert, wenn zu einer neuen Seite mit dem `Navigator` navigiert wird.

## Paginierung {#pagination}

Die `Navigator`-Komponente ist eng mit der Modellklasse `Paginator` verbunden, berechnet Paginierungsmetadaten wie die Gesamtzahl der Seiten, Start-/Endindizes der Elemente auf der aktuellen Seite und ein Array von Seitenzahlen zur Navigation.

Obwohl dies nicht unbedingt erforderlich ist, ermöglicht die Verwendung des `Paginator` die Logik hinter der Navigation. Bei der Integration mit einem `Paginator` reagiert der Navigator auf alle Änderungen innerhalb des `Paginators`. `Navigator`-Objekte haben über die Methode `getPaginator()` Zugriff auf einen integrierten `Paginator`. Es kann auch eine `Paginator`-Instanz über die Methode `setPaginator()` oder die Nutzung eines der anwendbaren Konstruktoren akzeptieren.

In diesem Abschnitt finden Sie praktische Codebeispiele, um zu veranschaulichen, wie diese Integration in der Praxis funktioniert.

### Elemente {#items}

Der Begriff "Elemente" bezeichnet die einzelnen paginierten Elemente oder Datenpunkte. Dies könnten Datensätze, Einträge oder andere diskrete Einheiten innerhalb eines Datensatzes sein. Sie können die Gesamtanzahl der Elemente mit der Methode `setTotalItems()` festlegen.

```java
navigator.getPaginator().setTotalItems(totalItems);
```

:::info
Ein mit der `Paginator`-Instanz verbundenes Repository hat die Gesamtzahl der Elemente, die direkt vom Repository verwaltet werden, und kann nicht direkt festgelegt werden.
:::

### Maximale Seiten {#maximum-pages}

Die Methode `setMax()` ermöglicht es Ihnen, die maximale Anzahl von Seitenlinks festzulegen, die in der Paginierungsnavigation angezeigt werden. Dies ist besonders nützlich, wenn es eine große Anzahl von Seiten gibt, da sie die Anzahl der Seitenlinks steuert, die dem Benutzer zu jedem Zeitpunkt sichtbar sind.

```java
navigator.getPaginator().setMax(maxPages);
```

<ComponentDemo
path='/webforj/navigatorpages'
files={['src/main/java/com/webforj/samples/views/navigator/NavigatorPagesView.java']}
height='125px'
/>

Dieses Programm zeigt maximal fünf Seiten im `Navigator` zu einem Zeitpunkt an, indem die Methode `getPaginator()` verwendet wird, um den mit dem `Navigator`-Objekt verbundenen `Paginator` abzurufen, und dann die Methode `setMax()` verwendet wird, um eine gewünschte Anzahl von maximal angezeigten Seiten festzulegen.

### Seitengröße {#page-size}

Die Methode `setSize()` ermöglicht es Ihnen, die Anzahl der Elemente festzulegen, die auf jeder Seite der Paginierung angezeigt werden sollen. Wenn Sie diese Methode aufrufen und eine neue Seitengröße angeben, passt sich die Paginierung entsprechend an.

```java
navigator.getPaginator().setSize(pageSize);
```

## Anpassung von Schaltflächen, Text und Tooltips {#customizing-buttons-text-and-tooltips}

Die `Navigator`-Komponente bietet umfangreiche Anpassungsmöglichkeiten für Schaltflächen, Text und Tooltips. Um den angezeigten Text auf der `Navigator`-Komponente zu ändern, verwenden Sie die Methode `setText()`. Diese Methode akzeptiert Text sowie den gewünschten `Part` des `Navigators`.

Im folgenden Beispiel zeigt die Methode `setText()`, einen numerischen Wert für den Benutzer an. Durch Klicken auf die Schaltflächen wird die Methode `onChange` des `Navigators` ausgelöst, die mit einem `Direction`-Wert der geklickten Schaltfläche ausgestattet ist.

<ComponentDemo
path='/webforj/navigatorbasic'
files={['src/main/java/com/webforj/samples/views/navigator/NavigatorBasicView.java']}
height='100px'
/>

### Schaltflächen- und Komponententext {#buttons-and-component-text}

Die Methode `setText()` wertet den Textparameter als JavaScript-Ausdruck unter Verwendung folgender Parameter aus:

- `page` - die aktuelle Seitenzahl
- `current` - die derzeit ausgewählte Seitenzahl
- `x` - ein Alias für die aktuelle Seite
- `startIndex` - der Startindex der aktuellen Seite.
- `endIndex` - der Endindex der aktuellen Seite.
- `totalItems` - die Gesamtanzahl der Elemente.
- `startPage` - die Seitenzahl des Startseite.
- `endPage` - die Seitenzahl der letzten Seite.
- `component` - die Navigator-Clientkomponente.

<!-- vale off -->
Um beispielsweise den Text der letzten Seite-Schaltfläche in einem `Navigator` mit 10 Seiten auf "Gehe zu Seite 10" zu setzen, verwenden Sie den folgenden Code-Snippet:
<!-- vale on -->

```java
navigator.setText("'Gehe zu Seite ' + endPage", Navigator.Part.LAST_BUTTON);
```

### Tooltip-Text {#tooltip-text}

Sie können Tooltips für verschiedene Teile der `Navigator`-Komponente mit der Methode `setTooltipText()` anpassen. Tooltips bieten hilfreiche Hinweise für Benutzer, wenn sie über die Navigationselemente fahren.

:::info
Tooltip-Text wird nicht als JavaScript ausgewertet, im Gegensatz zum mit der Methode `setText()` verwendeten Text.
:::

<!-- vale off -->
Um beispielsweise den Tooltip-Text der letzten Seite-Schaltfläche in einem `Navigator` auf "Gehe zur letzten Seite" zu setzen, verwenden Sie den folgenden Code-Snippet:
<!-- vale on -->

```java
navigator.setTooltipText("Gehe zur letzten Seite", Navigator.Part.LAST_BUTTON);
```

## Layouts {#layouts}

Es gibt verschiedene Layoutoptionen für die `Navigator`-Komponente, um Flexibilität bei der Anzeige von Paginierungssteuerungselementen zu bieten. Um auf diese Layouts zuzugreifen, verwenden Sie die Werte des `Navigator.Layout`-Enums. Die Optionen sind wie folgt:

<ComponentDemo
path='/webforj/navigatorlayout'
files={['src/main/java/com/webforj/samples/views/navigator/NavigatorLayoutView.java']}
height='200px'
/>

### 1. Kein Layout {#1-none-layout}

Das `NONE`-Layout rendert keinen Text innerhalb des `Navigators` und zeigt nur die Navigationsschaltflächen ohne standardmäßige Textanzeige an. Um dieses Layout zu aktivieren, verwenden Sie:

```java
navigator.setLayout(Navigator.Layout.NONE);
```

### 2. Nummeriertes Layout {#2-numbered-layout}

Das nummerierte Layout zeigt nummerierte Chips, die jeder Seite innerhalb des Anzeigebereichs des `Navigators` entsprechen. Die Verwendung dieses Layouts ist ideal für Szenarien, in denen Benutzer eine direkte Navigation zu bestimmten Seiten bevorzugen. Um dieses Layout zu aktivieren, verwenden Sie:

```java
navigator.setLayout(Navigator.Layout.PAGES);
```

### 3. Vorschau-Layout {#3-preview-layout}

Das Vorschau-Layout zeigt die aktuelle Seitenzahl und die Gesamtanzahl der Seiten an und eignet sich für kompakte Paginierungsoberflächen mit begrenztem Platz.

:::info
Vorschau ist das standardmäßige `Navigator`-Layout.
:::

Um dieses Layout zu aktivieren, verwenden Sie:

```java
navigator.setLayout(Navigator.Layout.PREVIEW);
```

### 4. Schnellzugriffs-Layout {#4-quick-jump-layout}

Das Schnellzugriffs-Layout bietet ein [NumberField](./fields/number-field.md) für Benutzer, um eine Seitenzahl für eine schnelle Navigation einzugeben. Dies ist nützlich, wenn Benutzer schnell zu einer bestimmten Seite navigieren müssen, insbesondere bei großen Datensätzen. Um dieses Layout zu aktivieren, verwenden Sie:

```java
navigator.setLayout(Navigator.Layout.QUICK_JUMP);
```

## Styling {#styling}

<TableBuilder name="Navigator" />

## Beste Praktiken {#best-practices}

Um eine optimale Benutzererfahrung bei der Verwendung der `Navigator`-Komponente sicherzustellen, beachten Sie die folgenden besten Praktiken:

- **Datensatz verstehen**: Bevor Sie eine `Navigator`-Komponente in Ihre Anwendung integrieren, sollten Sie die Anforderungen der Benutzer an die Datenbrowser gründlich verstehen. Berücksichtigen Sie Faktoren wie die Größe des Datensatzes, typische Benutzerinteraktionen und bevorzugte Navigationsmuster.

- **Geeignetes Layout auswählen**: Wählen Sie ein Layout für die `Navigator`-Komponente aus, das mit den Zielen der Benutzererfahrung und dem verfügbaren Bildschirmplatz übereinstimmt.

- **Text und Tooltips anpassen**: Passen Sie den Text und die Tooltips der `Navigator`-Komponente an die in Ihrer Anwendung verwendete Sprache und Terminologie an. Bieten Sie beschreibende Labels und hilfreiche Hinweise an, um Benutzern beim Navigieren im Datensatz effektiv zu helfen.
