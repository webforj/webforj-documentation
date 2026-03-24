---
title: Navigator
sidebar_position: 75
_i18n_hash: be5e20c3abb0d7b64b7a0eff03f7aded
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-navigator" />
<DocChip chip='since' label='24.00' />
<JavadocLink type="foundation" location="com/webforj/component/navigator/Navigator" top='true'/>

Die `Navigator`-Komponente fügt Steuerungselemente zur Pagination hinzu, um durch Datensätze zu navigieren. Sie kann Schaltflächen für „Erste“, „Letzte“, „Nächste“ und „Vorherige“ sowie Seitenzahlen oder ein Feld zum schnellen Springen anzeigen und deaktiviert automatisch Steuerungselemente, wenn diese nicht anwendbar sind. Sie bindet an eine `Paginator`-Instanz, um die zugrunde liegende Paging-Logik zu verwalten.

<!-- INTRO_END -->

## Bindung an Repositories {#binding-to-repositories}

Häufig zeigt eine `Navigator`-Komponente Informationen an, die in einem gebundenen `Repository` gefunden werden. Diese Bindung ermöglicht es dem `Navigator`, Daten, die vom Repository verwaltet werden, automatisch zu paginieren und andere bindbare Komponenten, wie Tabellen, basierend auf den navigierten Daten zu aktualisieren.

Um dies zu tun, übergeben Sie einfach das gewünschte `Repository`-Objekt an den Konstruktor eines anwendbaren `Navigator`-Objekts:

<ComponentDemo 
path='/webforj/navigatortable?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/navigator/NavigatorTableView.java'
height='475px'
/>

Dieses Beispiel erstellt den `Navigator` und [`Table`](table/overview) mit der gleichen `Repository`-Instanz. Das bedeutet, dass beim Navigieren zu einer neuen Seite mit dem `Navigator` die [`Table`](table/overview) diese Änderung erkennt und neu gerendert wird.

## Pagination {#pagination}

Die `Navigator`-Komponente ist eng mit der `Paginator`-Modellklasse verbunden, die Metadaten zur Pagination wie die Gesamtanzahl der Seiten, Start-/End-Indizes von Elementen auf der aktuellen Seite und ein Array von Seitennummern zur Navigation berechnet.

Obwohl es nicht unbedingt notwendig ist, ermöglicht die Nutzung des `Paginator` die Logik hinter der Navigation. Bei der Integration mit einem `Paginator` reagiert der Navigator auf alle Änderungen innerhalb des `Paginator`. `Navigator`-Objekte haben über die Methode `getPaginator()` Zugriff auf einen integrierten `Paginator`. Es kann auch eine `Paginator`-Instanz über die Methode `setPaginator()` akzeptiert werden oder durch die Nutzung eines der anwendbaren Konstruktoren.

Dieser Abschnitt enthält praktische Codebeispiele, um zu veranschaulichen, wie diese Integration in der Praxis funktioniert.

### Elemente {#items}

Der Begriff „Elemente“ bezeichnet die einzelnen paginierten Elemente oder Dateneinträge. Dies könnten Datensätze, Einträge oder beliebige diskrete Einheiten innerhalb eines Datensatzes sein. Sie können die Gesamtanzahl der Elemente mit der Methode `setTotalItems()` festlegen.

```java
navigator.getPaginator().setTotalItems(totalItems);
```

:::info
Ein mit der `Paginator`-Instanz verbundenes Repository hat die Gesamtanzahl der Elemente, die direkt vom Repository verwaltet werden, und kann nicht direkt festgelegt werden.
:::

### Maximale Seiten {#maximum-pages}

Die Methode `setMax()` ermöglicht es Ihnen, die maximale Anzahl von Seitenlinks festzulegen, die in der Paginationsnavigation angezeigt werden sollen. Dies ist besonders nützlich bei einer großen Anzahl von Seiten, da es die Anzahl der Seitenlinks steuert, die dem Benutzer zu einem bestimmten Zeitpunkt angezeigt werden.

```java
navigator.getPaginator().setMax(maxPages);
```

<ComponentDemo 
path='/webforj/navigatorpages?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/navigator/NavigatorPagesView.java'
height='125px'
/>

Dieses Programm zeigt maximal fünf Seiten im `Navigator` gleichzeitig an, indem die Methode `getPaginator()` verwendet wird, um den mit dem `Navigator`-Objekt verbundenen `Paginator` abzurufen, und die Methode `setMax()` verwendet wird, um eine gewünschte Anzahl von maximal angezeigten Seiten festzulegen.

### Seitengröße {#page-size}

Die Methode `setSize()` ermöglicht es Ihnen, die Anzahl der Elemente festzulegen, die auf jeder Seite der Pagination angezeigt werden sollen. Wenn Sie diese Methode aufrufen und eine neue Seitengröße angeben, passt sie die Pagination entsprechend an.

```java
navigator.getPaginator().setSize(pageSize);
```

## Anpassen von Schaltflächen, Text und Tooltips {#customizing-buttons-text-and-tooltips}

Die `Navigator`-Komponente bietet umfassende Anpassungsoptionen für Schaltflächen, Text und Tooltips. Um den angezeigten Text auf der `Navigator`-Komponente zu ändern, verwenden Sie die Methode `setText()`. Diese Methode nimmt Text sowie das gewünschte `Part` des `Navigator` entgegen.

Im folgenden Beispiel zeigt die Methode `setText()` einen numerischen Wert für den Benutzer an. Das Klicken auf die Schaltflächen löst die Methode `onChange` des `Navigator` aus, die mit einem `Direction`-Wert der geklickten Schaltfläche kommt.

<ComponentDemo 
path='/webforj/navigatorbasic?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/navigator/NavigatorBasicView.java'
height='100px'
/>

### Schaltflächen- und Komponententext {#buttons-and-component-text}

Die Methode `setText()` wertet den Textparameter als JavaScript-Ausdruck unter Verwendung folgender Parameter aus:

- `page` - die aktuelle Seitenzahl
- `current` - die aktuell ausgewählte Seitenzahl
- `x` - ein Alias für die aktuelle Seite
- `startIndex` - Der Start-Index der aktuellen Seite.
- `endIndex` - Der End-Index der aktuellen Seite.
- `totalItems` - Die Gesamtanzahl der Elemente.
- `startPage` - Die Startseitenzahl.
- `endPage` - Die Endseitenzahl.
- `component` - Die Navigator-Clientkomponente.

<!-- vale off -->
Zum Beispiel, um den Text der letzten Seite Schaltfläche in einem `Navigator` mit 10 Seiten auf „Gehe zu Seite 10“ zu setzen, verwenden Sie den folgenden Code-Snippet: 
<!-- vale on -->

```java
navigator.setText("'Gehe zu Seite ' + endPage", Navigator.Part.LAST_BUTTON);
```

### Tooltip-Text {#tooltip-text}

Sie können Tooltips für verschiedene Teile der `Navigator`-Komponente mithilfe der Methode `setTooltipText()` anpassen. Tooltips bieten nützliche Hinweise für Benutzer, wenn sie über Navigationselemente fahren.

:::info
Tooltip-Text wird nicht in JavaScript ausgewertet, anders als der Text, der von der Methode `setText()` verwendet wird.
:::

<!-- vale off -->
Zum Beispiel, um den Tooltip-Text der letzten Seite Schaltfläche in einem `Navigator` auf „Gehe zur letzten Seite“ zu setzen, verwenden Sie den folgenden Code-Snippet:
<!-- vale on -->

```java
navigator.setTooltipText("Gehe zur letzten Seite", Navigator.Part.LAST_BUTTON);
```

## Layouts {#layouts}

Es gibt verschiedene Layoutoptionen für die `Navigator`-Komponente, um Flexibilität bei der Anzeige von Paginationselementen zu bieten. Um auf diese Layouts zuzugreifen, verwenden Sie die Werte der `Navigator.Layout`-Enum. Die Optionen sind wie folgt:

<ComponentDemo 
path='/webforj/navigatorlayout?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/navigator/NavigatorLayoutView.java'
height='200px'
/>

### 1. Kein Layout {#1-none-layout}

Das `NONE`-Layout rendert keinen Text innerhalb des `Navigator`, sondern zeigt nur die Navigationsschaltflächen ohne eine standardmäßige textliche Anzeige an. Um dieses Layout zu aktivieren, verwenden Sie:

```java
navigator.setLayout(Navigator.Layout.NONE);
```

### 2. Nummeriertes Layout {#2-numbered-layout}

Das nummerierte Layout zeigt nummerierte Chips an, die jeder Seite im Anzeigebereich des `Navigator` entsprechen. Die Verwendung dieses Layouts ist ideal für Szenarien, in denen Benutzer eine direkte Navigation zu bestimmten Seiten bevorzugen. Um dieses Layout zu aktivieren, verwenden Sie:

```java
navigator.setLayout(Navigator.Layout.PAGES);
```

### 3. Vorschau-Layout {#3-preview-layout}

Das Vorschau-Layout zeigt die aktuelle Seitenzahl und die Gesamtanzahl der Seiten an und eignet sich für kompakte Pagination-Schnittstellen mit begrenztem Platz.

:::info
Vorschau ist das standardmäßige `Navigator`-Layout.
:::

Um dieses Layout zu aktivieren, verwenden Sie:

```java
navigator.setLayout(Navigator.Layout.PREVIEW);
```

### 4. Schnelljump-Layout {#4-quick-jump-layout}

Das Schnelljump-Layout bietet ein [NumberField](./fields/number-field.md) für Benutzer, um eine Seitenzahl für eine schnelle Navigation einzugeben. Dies ist nützlich, wenn Benutzer schnell zu einer bestimmten Seite navigieren müssen, insbesondere bei großen Datensätzen. Um dieses Layout zu aktivieren, verwenden Sie:

```java
navigator.setLayout(Navigator.Layout.QUICK_JUMP);
```

## Styling {#styling}

<TableBuilder name="Navigator" />

## Best Practices {#best-practices}

Um ein optimales Benutzererlebnis bei der Verwendung der `Navigator`-Komponente zu gewährleisten, berücksichtigen Sie die folgenden Best Practices: 

- **Datensatz verstehen**: Bevor Sie eine `Navigator`-Komponente in Ihre Anwendung integrieren, sollten Sie die Anforderungen an das Data Browsing Ihrer Benutzer gründlich verstehen. Berücksichtigen Sie Faktoren wie die Größe des Datensatzes, typische Benutzerinteraktionen und bevorzugte Navigationsmuster.

- **Geeignetes Layout wählen**: Wählen Sie ein Layout für die `Navigator`-Komponente, das mit den Zielen der Benutzererfahrung und dem verfügbaren Platz auf dem Bildschirm übereinstimmt.

- **Text und Tooltips anpassen**: Passen Sie den Text und die Tooltips der `Navigator`-Komponente an, um der Sprache und Terminologie zu entsprechen, die in Ihrer Anwendung verwendet werden. Stellen Sie beschreibende Bezeichnungen und hilfreiche Hinweise bereit, um den Benutzern zu helfen, den Datensatz effektiv zu navigieren.
