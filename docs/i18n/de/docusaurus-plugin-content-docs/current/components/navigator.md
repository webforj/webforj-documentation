---
title: Navigator
sidebar_position: 75
description: >-
  Add pagination controls with the Navigator component, binding to a Paginator
  or Repository to drive page size, navigation, and labels.
_i18n_hash: 1223e167b76000411cd73c4bbbbda3d5
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-navigator" />
<DocChip chip='since' label='24.00' />
<JavadocLink type="foundation" location="com/webforj/component/navigator/Navigator" top='true'/>

Die `Navigator`-Komponente fügt Steuerungselemente für die Seitennavigation von Datenmengen hinzu. Sie kann Schaltflächen für das Erste, Letzte, Nächste und Vorherige sowie Seitenzahlen oder ein Schnell-Jump-Feld anzeigen und deaktiviert automatisch Steuerungselemente, wenn diese nicht anwendbar sind. Sie bindet sich an eine `Paginator`-Instanz zur Verwaltung der zugrunde liegenden Paginierungslogik.

<!-- INTRO_END -->

## Bindung an Repositories {#binding-to-repositories}

Häufig zeigt eine `Navigator`-Komponente Informationen an, die in einem gebundenen `Repository` gefunden werden. Diese Bindung ermöglicht es dem `Navigator`, Daten, die vom Repository verwaltet werden, automatisch zu paginieren und andere bindbare Komponenten, wie Tabellen, basierend auf den navigierten Daten zu aktualisieren.

Um dies zu tun, übergeben Sie einfach das gewünschte `Repository`-Objekt an den Konstruktor eines anwendbaren `Navigator`-Objekts:

<ComponentDemo
path='/webforj/navigatortable'
files={['src/main/java/com/webforj/samples/views/navigator/NavigatorTableView.java']}
height='475px'
/>

Dieses Beispiel erstellt den `Navigator` und [`Table`](table/overview) mit derselben `Repository`-Instanz. Das bedeutet, dass, wenn zum Navigieren eine neue Seite mit dem `Navigator` aufgerufen wird, die [`Table`](table/overview) diese Änderung erkennt und neu rendert.

## Paginierung {#pagination}

Die `Navigator`-Komponente ist eng mit der `Paginator`-Modellklasse verbunden, die Paginierungsmetadaten wie die Gesamtzahl der Seiten, Start-/Endindizes der Elemente auf der aktuellen Seite und ein Array von Seitenzahlen für die Navigation berechnet.

Obwohl es nicht unbedingt notwendig ist, ermöglicht die Verwendung des `Paginator` die Logik hinter der Navigation. Bei der Integration mit einem `Paginator` reagiert der Navigator auf alle Änderungen innerhalb des `Paginator`. `Navigator`-Objekte haben Zugriff auf einen integrierten `Paginator` durch Verwendung der Methode `getPaginator()`. Er kann auch eine `Paginator`-Instanz über die Methode `setPaginator()` akzeptieren oder durch Nutzung eines der anwendbaren Konstruktoren.

Dieser Abschnitt enthält praktische Codebeispiele, um zu veranschaulichen, wie diese Integration in der Praxis funktioniert.

### Elemente {#items}

Der Begriff "Elemente" bezeichnet die einzelnen paginierten Elemente oder Datensätze. Dies könnten Datensätze, Einträge oder beliebige diskrete Einheiten innerhalb eines Datensatzes sein. Sie können die Gesamtzahl der Elemente mit der Methode `setTotalItems()` festlegen.

```java
navigator.getPaginator().setTotalItems(totalItems);
```

:::info
Ein mit der `Paginator`-Instanz verbundenes Repository verwaltet die direkt von dem Repository verwaltete Gesamtzahl der Elemente und kann nicht direkt festgelegt werden.
:::

### Maximale Seiten {#maximum-pages}

Die Methode `setMax()` ermöglicht es Ihnen, die maximale Anzahl der Seitenlinks festzulegen, die in der Paginierungsnavigation angezeigt werden. Dies ist besonders nützlich, wenn es eine große Anzahl von Seiten gibt, da es die Anzahl der Seitenlinks steuert, die dem Benutzer zu jedem Zeitpunkt sichtbar sind.

```java
navigator.getPaginator().setMax(maxPages);
```

<ComponentDemo
path='/webforj/navigatorpages'
files={['src/main/java/com/webforj/samples/views/navigator/NavigatorPagesView.java']}
height='125px'
/>

Dieses Programm zeigt maximal fünf Seiten im `Navigator` gleichzeitig an, indem die Methode `getPaginator()` verwendet wird, um den mit dem `Navigator`-Objekt verbundenen `Paginator` abzurufen, und dann die Methode `setMax()` verwendet wird, um eine gewünschte Anzahl von maximal angezeigten Seiten anzugeben.

### Seitengröße {#page-size}

Die Methode `setSize()` erlaubt es Ihnen, die Anzahl der Elemente festzulegen, die auf jeder Seite der Paginierung angezeigt werden sollen. Wenn Sie diese Methode aufrufen und eine neue Seitengröße angeben, passt sie die Paginierung entsprechend an.

```java
navigator.getPaginator().setSize(pageSize);
```

## Anpassen von Schaltflächen, Texten und Tooltips {#customizing-buttons-text-and-tooltips}

Die `Navigator`-Komponente bietet umfangreiche Anpassungsoptionen für Schaltflächen, Texte und Tooltips. Um den angezeigten Text auf der `Navigator`-Komponente zu ändern, verwenden Sie die Methode `setText()`. Diese Methode nimmt Text sowie den gewünschten `Part` des `Navigator` entgegen.

Im folgenden Beispiel zeigt die Methode `setText()` einen numerischen Wert für den Benutzer an. Das Klicken auf die Schaltflächen ruft die Methode `onChange` des `Navigator` auf, die mit einem Wert für `Direction` die angeklickte Schaltfläche hat.

<ComponentDemo
path='/webforj/navigatorbasic'
files={['src/main/java/com/webforj/samples/views/navigator/NavigatorBasicView.java']}
height='100px'
/>

### Schaltflächen- und Komponenten-Text {#buttons-and-component-text}

Die Methode `setText()` bewertet den Textparameter als einen JavaScript-Ausdruck unter Verwendung der folgenden Parameter:

- `page` - die aktuelle Seitenzahl
- `current` - die aktuell ausgewählte Seitenzahl
- `x` - ein Alias für die aktuelle Seite
- `startIndex` - Der Startindex der aktuellen Seite.
- `endIndex` - Der Endindex der aktuellen Seite.
- `totalItems` - Die Gesamtzahl der Elemente.
- `startPage` - Die Startseitenzahl.
- `endPage` - Die Endseitenzahl.
- `component` - Die Navigator-Clientkomponente.

<!-- vale off -->
Um beispielsweise den Text der Schaltfläche für die letzte Seite in einem `Navigator` mit 10 Seiten auf "Gehe zu Seite 10" festzulegen, verwenden Sie den folgenden Codeausschnitt:
<!-- vale on -->

```java
navigator.setText("'Gehe zu Seite ' + endPage", Navigator.Part.LAST_BUTTON);
```

### Tooltip-Text {#tooltip-text}

Sie können Tooltips für verschiedene Teile der `Navigator`-Komponente mit der Methode `setTooltipText()` anpassen. Tooltips bieten den Benutzern nützliche Hinweise, wenn sie über Navigationselemente fahren.

:::info
Tooltip-Text wird nicht als JavaScript ausgewertet, im Gegensatz zu dem Text, der von der Methode `setText()` verwendet wird.
:::

<!-- vale off -->
Um beispielsweise den Tooltip-Text der Schaltfläche für die letzte Seite in einem `Navigator` auf "Gehe zur letzten Seite" festzulegen, verwenden Sie den folgenden Codeausschnitt:
<!-- vale on -->

```java
navigator.setTooltipText("Gehe zur letzten Seite", Navigator.Part.LAST_BUTTON);
```

## Layouts {#layouts}

Es gibt verschiedene Layout-Optionen für die `Navigator`-Komponente, um Flexibilität bei der Anzeige von Paginierungssteuerungen zu bieten. Um auf diese Layouts zuzugreifen, verwenden Sie die Werte des Enums `Navigator.Layout`. Die Optionen sind wie folgt:

<ComponentDemo
path='/webforj/navigatorlayout'
files={['src/main/java/com/webforj/samples/views/navigator/NavigatorLayoutView.java']}
height='200px'
/>

### 1. Kein Layout {#1-none-layout}

Das Layout `NONE` rendert keinen Text innerhalb des `Navigator`, sondern zeigt nur die Navigationsschaltflächen ohne eine standardmäßige Textanzeige an. Um dieses Layout zu aktivieren, verwenden Sie:

```java
navigator.setLayout(Navigator.Layout.NONE);
```

### 2. Nummeriertes Layout {#2-numbered-layout}

Das nummerierte Layout zeigt nummerierte Chips an, die jeder Seite innerhalb des Anzeigebereichs des `Navigator` entsprechen. Die Verwendung dieses Layouts ist ideal für Szenarien, in denen Benutzer eine direkte Navigation zu bestimmten Seiten bevorzugen. Um dieses Layout zu aktivieren, verwenden Sie:

```java
navigator.setLayout(Navigator.Layout.PAGES);
```

### 3. Vorschau-Layout {#3-preview-layout}

Das Vorschau-Layout zeigt die aktuelle Seitenzahl und die Gesamtzahl der Seiten an und eignet sich für kompakte Paginierungsoberflächen mit begrenztem Platz.

:::info
Vorschau ist das standardmäßige Layout des `Navigator`.
:::

Um dieses Layout zu aktivieren, verwenden Sie:

```java
navigator.setLayout(Navigator.Layout.PREVIEW);
```

### 4. Schnell-Jump-Layout {#4-quick-jump-layout}

Das Schnell-Jump-Layout bietet ein [NumberField](./fields/number-field.md) für Benutzer, um eine Seitenzahl für eine schnelle Navigation einzugeben. Dies ist nützlich, wenn Benutzer schnell zu einer bestimmten Seite navigieren müssen, insbesondere bei großen Datensätzen. Um dieses Layout zu aktivieren, verwenden Sie:

```java
navigator.setLayout(Navigator.Layout.QUICK_JUMP);
```

## Styling {#styling}

<TableBuilder name="Navigator" />

## Beste Praktiken {#best-practices}

Um eine optimale Benutzererfahrung bei der Verwendung der `Navigator`-Komponente zu gewährleisten, beachten Sie die folgenden besten Praktiken:

- **Dataset verstehen**: Verstehen Sie gründlich die Anforderungen an das Datenbrowsing Ihrer Benutzer, bevor Sie eine `Navigator`-Komponente in Ihre App integrieren. Berücksichtigen Sie Faktoren wie die Größe des Datensatzes, typische Benutzerinteraktionen und bevorzugte Navigationsmuster.

- **Angemessenes Layout wählen**: Wählen Sie ein Layout für die `Navigator`-Komponente, das mit den Zielen der Benutzererfahrung und dem verfügbaren Platz auf dem Bildschirm übereinstimmt.

- **Text und Tooltips anpassen**: Passen Sie den Text und die Tooltips der `Navigator`-Komponente an die Sprache und Terminologie an, die in Ihrer App verwendet werden. Stellen Sie beschreibende Labels und hilfreiche Hinweise zur Verfügung, um Benutzern zu helfen, den Datensatz effektiv zu navigieren.
