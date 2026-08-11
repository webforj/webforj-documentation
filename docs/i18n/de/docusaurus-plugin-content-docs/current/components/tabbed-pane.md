---
title: TabbedPane
slug: tabbedpane
sidebar_position: 125
description: >-
  Organize content into switchable Tab sections with the TabbedPane component,
  supporting icons, keys, and customizable tab properties.
_i18n_hash: 563f9251b928e2e9426d69d4b5192880
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-tabbed-pane" />
<DocChip chip='since' label='23.06' />
<JavadocLink type="foundation" location="com/webforj/component/tabbedpane/TabbedPane" top='true'/>

In mehreren Abschnitten kann Inhalt unter einem einzelnen `TabbedPane` organisiert werden, wobei jeder Abschnitt mit einem anklickbaren `Tab` verknüpft ist. Nur ein Abschnitt ist gleichzeitig sichtbar, und Tabs können Text, Symbole oder beides anzeigen, um den Nutzern die Navigation zu erleichtern.

<!-- INTRO_END -->

## Usos {#usages}

Die `TabbedPane`-Klasse ist ein mächtiges Werkzeug für Entwickler, um mehrere Tabs oder Abschnitte innerhalb einer Benutzeroberfläche zu organisieren und darzustellen. Hier sind einige typische Szenarien, in denen Sie ein `TabbedPane` in Ihrer Anwendung verwenden könnten:

1. **Dokumentenbetrachter**: Implementierung eines Dokumentenbetrachters, bei dem jeder Tab ein anderes Dokument oder eine andere Datei darstellt. Benutzer können problemlos zwischen geöffneten Dokumenten wechseln, um effizient multitasking zu betreiben.

2. **Datenverwaltung:**: Verwendung eines `TabbedPane`, um Datenverwaltungsaufgaben zu organisieren, beispielsweise:
    >- Verschiedene Datensätze, die in einer Anwendung angezeigt werden
    >- Verschiedene Benutzerprofile können in separaten Tabs angezeigt werden
    >- Verschiedene Profile in einem Benutzermanagementsystem

3. **Modulwahl**: Ein `TabbedPane` kann verschiedene Module oder Abschnitte repräsentieren. Jeder Tab kann die Funktionen eines bestimmten Moduls kapseln und es den Nutzern ermöglichen, sich jeweils auf einen Aspekt der Anwendung zu konzentrieren.

4. **Aufgabenmanagement**: Aufgabenverwaltungsanwendungen können ein `TabbedPane` verwenden, um verschiedene Projekte oder Aufgaben darzustellen. Jeder Tab könnte einem bestimmten Projekt entsprechen, was es den Nutzern ermöglicht, Aufgaben separat zu verwalten und zu verfolgen.

5. **Programmnavigation**: Innerhalb einer Anwendung, die verschiedene Programme ausführen muss, könnte ein `TabbedPane`:
    >- Als Seitenleiste dienen, die es ermöglicht, verschiedene Anwendungen oder Programme innerhalb einer einzelnen Anwendung auszuführen, wie es im [`AppLayout`](./app-layout.md) Template gezeigt wird
    >- Eine obere Leiste erstellen, die einen ähnlichen Zweck erfüllt oder Subanwendungen innerhalb einer bereits ausgewählten Anwendung darstellt.

## Tabs {#tabs}

Tabs sind UI-Elemente, die Tabbed-Panes hinzugefügt werden können, um verschiedene Inhaltsansichten zu organisieren und zwischen ihnen zu wechseln.

:::important
Tabs sind nicht als eigenständige Komponenten gedacht. Sie sind dafür gedacht, in Verbindung mit Tabbed-Panes verwendet zu werden. Diese Klasse ist kein `Component` und sollte nicht als solche verwendet werden.
:::

### Eigenschaften {#properties}

Tabs bestehen aus den folgenden Eigenschaften, die beim Hinzufügen in ein `TabbedPane` verwendet werden. Diese Eigenschaften haben Getter und Setter, um die Anpassung innerhalb eines `TabbedPane` zu erleichtern.

1. **Schlüssel(`Object`)**: Stellt den eindeutigen Identifikator für den `Tab` dar.

2. **Text(`String`)**: Der Text, der als Titel für den `Tab` im `TabbedPane` angezeigt wird. Dies wird auch als Titel über die Methoden `getTitle()` und `setTitle(String title)` bezeichnet.

3. **Tooltip(`String`)**: Der Tooltip-Text, der mit dem `Tab` assoziiert ist und angezeigt wird, wenn der Cursor über den `Tab` schwebt.

4. **Aktiviert(`boolean`)**: Stellt dar, ob der `Tab` derzeit aktiviert ist oder nicht. Kann mit der Methode `setEnabled(boolean enabled)` geändert werden.

5. **Schließbar(`boolean`)**: Stellt dar, ob der `Tab` geschlossen werden kann. Kann mit der Methode `setCloseable(boolean enabled)` geändert werden. Dadurch wird eine Schaltfläche zum Schließen auf dem `Tab` hinzugefügt, die vom Benutzer angeklickt werden kann und ein Entfernungsevent auslöst. Die `TabbedPane`-Komponente bestimmt, wie mit der Entfernung umgegangen wird.

6. **Slot(`Component`)**:
    Slots bieten flexible Optionen zur Verbesserung der Leistungsfähigkeit eines `Tabs`. Sie können Symbole, Beschriftungen, Ladeanimationen, Löschen/Zurücksetzen-Funktionen, Avatare/Profilbilder und andere nützliche Komponenten innerhalb eines `Tabs` verschachteln, um die beabsichtigte Bedeutung für die Nutzer weiter zu verdeutlichen.
    Sie können eine Komponente im `prefix`-Slot eines `Tabs` während der Konstruktion hinzufügen. Alternativ können Sie die Methoden `setPrefixComponent()` und `setSuffixComponent()` verwenden, um verschiedene Komponenten vor und nach der angezeigten Option innerhalb eines `Tabs` einzufügen.

        ```java
        TabbedPane pane = new TabbedPane();
        pane.addTab(new Tab("Documents", TablerIcon.create("files")));
        ```

## `Tab`-Manipulation {#tab-manipulation}

Es gibt verschiedene Methoden, die Entwicklern ermöglichen, `Tab`-Elemente innerhalb des `TabbedPane` hinzuzufügen, einzufügen, zu entfernen und verschiedene Eigenschaften zu manipulieren.

### Hinzufügen eines `Tabs` {#adding-a-tab}

Die Methoden `addTab()` und `add()` existieren in verschiedenen überladenen Kapazitäten, um Entwicklern Flexibilität beim Hinzufügen neuer Tabs zum `TabbedPane` zu ermöglichen. Das Hinzufügen eines `Tabs` platziert ihn nach allen zuvor bestehenden Tabs.

1. **`addTab(String text)`** - Fügt dem `TabbedPane` einen `Tab` mit dem angegebenen `String` als Text des `Tabs` hinzu.
2. **`addTab(Tab tab)`** - Fügt den als Parameter übergebenen `Tab` zum `TabbedPane` hinzu.
3. **`addTab(String text, Component component)`** - Fügt einen `Tab` mit dem gegebenen `String` als Text des `Tabs` hinzu, und zeigt die bereitgestellte `Komponente` im Inhaltsbereich des `TabbedPane` an.
4. **`addTab(Tab tab, Component component)`** - Fügt den bereitgestellten `Tab` hinzu und zeigt die bereitgestellte `Komponente` im Inhaltsbereich des `TabbedPane` an.
5. **`add(Component... component)`** - Fügt eine oder mehrere `Component`-Instanzen dem `TabbedPane` hinzu und erstellt für jede einen eigenständigen `Tab`, wobei der Text auf den Namen der `Komponente` gesetzt wird.

:::info
Die Methode `add(Component... component)` bestimmt den Namen der übergebenen `Komponente`, indem sie `component.getName()` für das übergebene Argument aufruft.
:::

### Einfügen eines `Tabs` {#inserting-a-tab}

Zusätzlich zum Hinzufügen eines `Tabs` am Ende der bestehenden Tabs ist es auch möglich, einen neuen an einer bestimmten Position zu erstellen. Dazu gibt es mehrere überladene Versionen der Methode `insertTab()`.

1. **`insertTab(int index, String text)`** - Fügt einen `Tab` an die angegebene Indexposition im `TabbedPane` mit dem angegebenen `String` als Text des `Tabs` ein.
2. **`insertTab(int index, Tab tab)`** - Fügt den als Parameter übergebenen `Tab` an der angegebenen Indexposition zum `TabbedPane` ein.
3. **`insertTab(int index, String text, Component component)`** - Fügt einen `Tab` mit dem angegebenen `String` als Text des `Tabs` ein, und zeigt die bereitgestellte `Komponente` im Inhaltsbereich des `TabbedPane` an.
4. **`insertTab(int index, Tab tab, Component component)`** - Fügt den bereitgestellten `Tab` hinzu und zeigt die bereitgestellte `Komponente` im Inhaltsbereich des `TabbedPane` an.

### Entfernen eines `Tabs` {#removing-a-tab}

Um einen einzelnen `Tab` aus dem `TabbedPane` zu entfernen, verwenden Sie eine der folgenden Methoden:

1. **`removeTab(Tab tab)`** - Entfernt einen `Tab` aus dem `TabbedPane`, indem die zu entfernende Tab-Instanz übergeben wird.
2. **`removeTab(int index)`** - Entfernt einen `Tab` aus dem `TabbedPane`, indem der Index des zu entfernenden `Tabs` angegeben wird.

Neben den beiden oben genannten Methoden zur Entfernung eines einzelnen `Tabs`, verwenden Sie die Methode **`removeAllTabs()`**, um das `TabbedPane` von allen Tabs zu befreien.

:::info
Die Methoden `remove()` und `removeAll()` entfernen keine Tabs innerhalb der Komponente.
:::

### Tab/Kompontenten-Assoziation {#tabcomponent-association}

Um die `Komponente` zu ändern, die für einen bestimmten `Tab` angezeigt werden soll, rufen Sie die Methode `setComponentFor()` auf und übergeben entweder die Instanz des `Tabs` oder den Index dieses `Tabs` im `TabbedPane`.

:::info
Wenn diese Methode bei einem `Tab` verwendet wird, der bereits mit einer `Komponente` assoziiert ist, wird die zuvor assoziierte `Komponente` zerstört.
:::

## Konfiguration und Layout {#configuration-and-layout}

Die `TabbedPane`-Klasse hat zwei Bestandteile: einen `Tab`, der an einem bestimmten Ort angezeigt wird, und eine Komponente, die angezeigt werden soll. Dies kann eine einzelne Komponente oder eine [`Composite`](/docs/building-ui/composing-components) Komponente sein, die die Anzeige komplexerer Komponenten innerhalb des Inhaltsbereichs eines Tabs ermöglicht.

### Wischen {#swiping}

Das `TabbedPane` unterstützt die Navigation durch die verschiedenen Tabs über Wischen. Dies ist ideal für eine mobile Anwendung, kann aber auch über eine integrierte Methode konfiguriert werden, um das Wischen mit der Maus zu unterstützen. Sowohl Wischen als auch Wischen mit der Maus sind standardmäßig deaktiviert, können aber mit den Methoden `setSwipable(boolean)` und `setSwipableWithMouse(boolean)` aktiviert werden.

### Tab-Platzierung {#tab-placement}

Die `Tabs` innerhalb eines `TabbedPane` können je nach Vorliebe der Anwendungsentwickler an verschiedenen Positionen innerhalb der Komponente platziert werden. Bereitgestellte Optionen werden unter Verwendung des bereitgestellten Enumerationswerts gesetzt, der die Werte `TOP`, `BOTTOM`, `LEFT`, `RIGHT` oder `HIDDEN` hat. Die Standardeinstellung ist `TOP`.

<ComponentDemo
path='/webforj/tabbedpaneplacement'
files={['src/main/java/com/webforj/samples/views/tabbedpane/TabbedPanePlacementView.java']}
height='400px'
/>

### Ausrichtung {#alignment}

Zusätzlich zur Änderung der Platzierung der `Tab`-Elemente im `TabbedPane` ist es auch möglich, zu konfigurieren, wie die Tabs innerhalb der Komponente ausgerichtet werden. Standardmäßig ist die Einstellung `AUTO` aktiv, die es ermöglicht, dass die Platzierung der Tabs ihre Ausrichtung bestimmt.

Die anderen Optionen sind `START`, `END`, `CENTER` und `STRETCH`. Die ersten drei beschreiben die Position relativ zur Komponente, wobei `STRETCH` die Tabs den verfügbaren Raum ausfüllen lässt.

<ComponentDemo
path='/webforj/tabbedpanealignment'
files={['src/main/java/com/webforj/samples/views/tabbedpane/TabbedPaneAlignmentView.java']}
height='250px'
/>

### Rahmen- und Aktivitätsanzeige {#border-and-activity-indicator}

Das `TabbedPane` zeigt standardmäßig einen Rahmen für die Tabs innerhalb davon an, der je nach festgelegter `Platzierung` platziert ist. Dieser Rahmen hilft, den Platz zu visualisieren, den die verschiedenen Tabs innerhalb des Panes einnehmen.

Wenn ein `Tab` angeklickt wird, wird standardmäßig ein Aktivitätsindikator in der Nähe dieses `Tabs` angezeigt, um zu helfen, den aktuell ausgewählten `Tab` zu kennzeichnen.

Beide Optionen können von einem Entwickler angepasst werden, indem die booleschen Werte mithilfe der entsprechenden Setter-Methoden geändert werden. Um festzulegen, ob der Rahmen angezeigt werden soll oder nicht, kann die Methode `setBorderless(boolean)` verwendet werden, wobei `true` den Rahmen verbirgt und `false`, dem Standardwert, den Rahmen anzeigt.

:::info
Dieser Rahmen gilt nicht für die Gesamtheit der `TabbedPane`-Komponente und dient nur als Trennlinie zwischen den Tabs und dem Inhalt der Komponente.
:::

Um die Sichtbarkeit des aktiven Indikators festzulegen, kann die Methode `setHideActiveIndicator(boolean)` verwendet werden. Das Übergeben von `true` an diese Methode verbirgt den aktiven Indikator unter einem aktiven `Tab`, während `false`, der Standardwert, den Indikator sichtbar hält.

<ComponentDemo
path='/webforj/tabbedpaneborder'
files={['src/main/java/com/webforj/samples/views/tabbedpane/TabbedPaneBorderView.java']}
height='300px'
/>

### Aktivierungsmodi {#activation-modes}

Für mehr Kontrolle darüber, wie das `TabbedPane` beim Navigieren mit der Tastatur funktioniert, kann der `Aktivierungs`-Modus festgelegt werden, um anzugeben, wie sich die Komponente verhalten soll.

- **`Automatisch`**: Wenn auf automatisch eingestellt, werden beim Navigieren mit den Pfeiltasten die entsprechenden Tab-Komponenten sofort angezeigt.

- **`Manuell`**: Wenn auf manuell eingestellt, erhält der Tab den Fokus, wird aber erst angezeigt, wenn der Benutzer Leertaste oder Eingabetaste drückt.

<ComponentDemo
path='/webforj/tabbedpaneactivation'
files={['src/main/java/com/webforj/samples/views/tabbedpane/TabbedPaneActivationView.java']}
height='250px'
/>

### Entfernungsoptionen {#removal-options}

Einzelne `Tab`-Elemente können als schließbar festgelegt werden. Schließbare Tabs haben eine Schaltfläche zum Schließen, die zum Tab hinzugefügt wird und ein Schließereignis auslöst, wenn sie angeklickt wird. Das `TabbedPane` bestimmt, wie dieses Verhalten behandelt wird.

- **`Manuell`**: Standardmäßig ist die Entfernung auf `MANUAL` festgelegt, was bedeutet, dass das Ereignis ausgelöst wird, es jedoch dem Entwickler überlassen bleibt, dieses Ereignis auf beliebige Weise zu behandeln.

- **`Automatisch`**: Alternativ kann `AUTO` verwendet werden, was das Ereignis auslöst und auch den `Tab` automatisch von der Komponente entfernt, sodass der Entwickler dieses Verhalten nicht manuell implementieren muss.

### Segmentsteuerung <DocChip chip='since' label='26.00' /> {#segment-control}

Das `TabbedPane` kann als Segmentsteuerung gerendert werden, indem die `segments`-Eigenschaft mit `setSegment(true)` aktiviert wird. In diesem Modus werden Tabs mit einem gleitenden Pillenindikator angezeigt, der die aktive Auswahl hervorhebt und eine kompakte Alternative zur standardmäßigen Tab-basierten Benutzeroberfläche bietet.

<ComponentDemo
path='/webforj/tabbedpanesegment'
files={['src/main/java/com/webforj/samples/views/tabbedpane/TabbedPaneSegmentView.java']}
height='250px'
/>

## Styling {#styling}

### Ausdehnung und Thema {#expanse-and-theme}

Das `TabbedPane` verfügt über integrierte `Expanse`- und `Theme`-Optionen, ähnlich wie andere webforJ-Komponenten. Diese können verwendet werden, um schnell Styling hinzuzufügen, das den Endbenutzern verschiedene Bedeutungen vermittelt, ohne die Komponente mit CSS zu stylen.

<ComponentDemo
path='/webforj/tabbedpaneexpansetheme'
files={['src/main/java/com/webforj/samples/views/tabbedpane/TabbedPaneExpanseThemeView.java']}
height='250px'
/>

<TableBuilder name={['Tab', 'TabbedPane']} />

## Best Practices {#best-practices}

Die folgenden Praktiken werden empfohlen, um das `TabbedPane` innerhalb von Anwendungen zu verwenden:

- **Logische Gruppierung**: Verwenden Sie Tabs, um verwandte Inhalte logisch zu gruppieren
    >- Jeder Tab sollte eine bestimmte Kategorie oder Funktionalität innerhalb Ihrer Anwendung repräsentieren
    >- Gruppieren Sie ähnliche oder logische Tabs nahe beieinander

- **Begrenzte Tabs**: Vermeiden Sie es, Benutzer mit zu vielen Tabs zu überfordern. Erwägen Sie die Verwendung einer hierarchischen Struktur oder anderer Navigationsmuster, wenn dies für eine saubere Oberfläche anwendbar ist.

- **Klare Beschriftungen**: Beschriften Sie Ihre Tabs klar, um eine intuitive Verwendung zu gewährleisten
    >- Geben Sie klare und prägnante Beschriftungen für jeden Tab an
    >- Beschriftungen sollten den Inhalt oder Zweck widerspiegeln, damit die Benutzer es leicht verstehen können
    >- Nutzen Sie Symbole und unterschiedliche Farben, wo es angebracht ist

- **Tastaturnavigation**: Verwenden Sie die Tastaturnavigationsunterstützung von webforJ's `TabbedPane`, um die Interaktion mit dem `TabbedPane` für den Endbenutzer nahtloser und intuitiver zu gestalten.

- **Standard-Tab**: Wenn der Standard-Tab nicht am Anfang des `TabbedPane` platziert ist, ziehen Sie in Betracht, diesen Tab als Standard für wichtige oder häufig verwendete Informationen festzulegen.
