---
title: TabbedPane
slug: tabbedpane
sidebar_position: 125
sidebar_class_name: new-content
_i18n_hash: 0b623c02434c6f0d140de0ade3a22c5d
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-tabbed-pane" />
<DocChip chip='since' label='23.06' />
<JavadocLink type="foundation" location="com/webforj/component/tabbedpane/TabbedPane" top='true'/>

Mehrere Inhaltsabschnitte können unter einem einzelnen `TabbedPane` organisiert werden, wobei jeder Abschnitt an einen klickbaren `Tab` gebunden ist. Nur ein Abschnitt ist zur gleichen Zeit sichtbar, und Tabs können Text, Symbole oder beides anzeigen, um den Benutzern bei der Navigation zu helfen.

<!-- INTRO_END -->

## Usages {#usages}

Die Klasse `TabbedPane` bietet Entwicklern ein leistungsstarkes Werkzeug zum Organisieren und Präsentieren mehrerer Tabs oder Abschnitte innerhalb einer Benutzeroberfläche. Hier sind einige typische Szenarien, in denen Sie ein `TabbedPane` in Ihrer Anwendung verwenden könnten:

1. **Dokumentenbetrachter**: Implementierung eines Dokumentenbetrachters, wobei jeder Tab ein anderes Dokument oder eine Datei darstellt. Benutzer können einfach zwischen offenen Dokumenten hin- und herschalten, um effizient Multitasking zu betreiben.

2. **Datenverwaltung:** Nutzen Sie ein `TabbedPane`, um Datenverwaltungsaufgaben zu organisieren, zum Beispiel:
    >- Verschiedene Datensätze, die in einer Anwendung angezeigt werden
    >- Verschiedene Benutzerprofile können innerhalb separater Tabs angezeigt werden
    >- Unterschiedliche Profile in einem Benutzermanagementsystem

3. **Modulauswahl**: Ein `TabbedPane` kann verschiedene Module oder Abschnitte darstellen. Jeder Tab kann die Funktionen eines bestimmten Moduls kapseln, sodass Benutzer sich jeweils auf einen Aspekt der Anwendung konzentrieren können.

4. **Aufgabenverwaltung**: Anwendungen zur Aufgabenverwaltung können ein `TabbedPane` verwenden, um verschiedene Projekte oder Aufgaben darzustellen. Jeder Tab könnte einem bestimmten Projekt entsprechen, sodass Benutzer Aufgaben separat verwalten und verfolgen können.

5. **Programmnavigation**: In einer Anwendung, die verschiedene Programme ausführen muss, könnte ein `TabbedPane`:
    >- Als Sidebar dienen, die es ermöglicht, verschiedene Anwendungen oder Programme innerhalb einer einzelnen Anwendung zu verwenden, wie im [`AppLayout`](./app-layout.md) -Template gezeigt
    >- Eine obere Leiste erstellen, die einen ähnlichen Zweck erfüllen oder Unteranwendungen innerhalb einer bereits ausgewählten Anwendung darstellen kann.
  
## Tabs {#tabs}

Tabs sind UI-Elemente, die zu Tabbed-Panes hinzugefügt werden können, um zwischen verschiedenen Inhaltsansichten zu organisieren und zu wechseln.

:::important
Tabs sind nicht dafür gedacht, als eigenständige Komponenten verwendet zu werden. Sie sind dazu gedacht, in Verbindung mit Tabbed-Panes verwendet zu werden. Diese Klasse ist kein `Component` und sollte nicht als solche verwendet werden.
:::

### Eigenschaften {#properties}

Tabs bestehen aus den folgenden Eigenschaften, die verwendet werden, wenn sie in einem `TabbedPane` hinzugefügt werden. Diese Eigenschaften haben Getter und Setter, um die Anpassung innerhalb eines `TabbedPane` zu erleichtern.

1. **Key(`Object`)**: Repräsentiert die eindeutige Kennung für den `Tab`.

2. **Text(`String`)**: Der Text, der als Titel für den `Tab` im `TabbedPane` angezeigt wird. Dies wird auch über die Methoden `getTitle()` und `setTitle(String title)` als Titel bezeichnet.

3. **Tooltip(`String`)**: Der Tooltip-Text, der mit dem `Tab` verknüpft ist und angezeigt wird, wenn der Cursor über den `Tab` schwebt.

4. **Enabled(`boolean`)**: Gibt an, ob der `Tab` derzeit aktiviert ist oder nicht. Kann mit der Methode `setEnabled(boolean enabled)` geändert werden.

5. **Closeable(`boolean`)**: Gibt an, ob der `Tab` geschlossen werden kann. Kann mit der Methode `setCloseable(boolean enabled)` geändert werden. Dies fügt dem `Tab` einen Schließen-Button hinzu, der vom Benutzer angeklickt werden kann und ein Ereignis zur Entfernung auslöst. Die Komponente `TabbedPane` bestimmt, wie mit der Entfernung umzugehen ist.

6. **Slot(`Component`)**: 
    Slots bieten vielseitige Optionen zur Verbesserung der Funktionalität eines `Tabs`. Sie können Symbole, Beschriftungen, Ladeanimationen, Klär-/Zurücksetzmöglichkeiten, Avatare/Profilbilder und andere nützliche Komponenten innerhalb eines `Tabs` einfügen, um die beabsichtigte Bedeutung für die Benutzer weiter zu verdeutlichen.
    Sie können ein Komponenten im `prefix`-Slot eines `Tabs` während der Konstruktion hinzufügen. Alternativ können Sie die Methoden `setPrefixComponent()` und `setSuffixComponent()` verwenden, um verschiedene Komponenten vor und nach der angezeigten Option innerhalb eines `Tabs` einzufügen.

        ```java
        TabbedPane pane = new TabbedPane();
        pane.addTab(new Tab("Documents", TablerIcon.create("files")));
        ```

## `Tab`-Manipulation {#tab-manipulation}

Es gibt verschiedene Methoden, die es Entwicklern ermöglichen, `Tab`-Elemente innerhalb des `TabbedPane` hinzuzufügen, einzufügen, zu entfernen und verschiedene Eigenschaften zu manipulieren.

### Hinzufügen eines `Tabs` {#adding-a-tab}

Die Methoden `addTab()` und `add()` existieren in verschiedenen überladenen Versionen, um Entwicklern Flexibilität beim Hinzufügen neuer Tabs zu geben. Das Hinzufügen eines `Tabs` platziert diesen nach allen zuvor vorhandenen Tabs.

1. **`addTab(String text)`** - Fügt einen `Tab` zum `TabbedPane` mit dem angegebenen `String` als Text des `Tabs` hinzu.
2. **`addTab(Tab tab)`** - Fügt den `Tab`, der als Parameter übergeben wird, zum `TabbedPane` hinzu.
3. **`addTab(String text, Component component)`** - Fügt einen `Tab` mit dem gegebenen `String` als Text des `Tabs` hinzu und dem bereitgestellten `Component`, der im Inhaltsbereich des `TabbedPane` angezeigt wird.
4. **`addTab(Tab tab, Component component)`** - Fügt den bereitgestellten `Tab` hinzu und zeigt die bereitgestellte `Komponente` im Inhaltsbereich des `TabbedPane` an.
5. **`add(Component... component)`** - Fügt ein oder mehrere `Component`-Instanzen zum `TabbedPane` hinzu, wobei für jede ein separater `Tab` erstellt wird, dessen Text auf den Namen des `Components` gesetzt wird.

:::info
Die Methode `add(Component... component)` bestimmt den Namen des übergebenen `Components`, indem sie `component.getName()` auf dem übergebenen Argument aufruft.
:::

### Einfügen eines `Tabs` {#inserting-a-tab}

Neben dem Hinzufügen eines `Tabs` am Ende der vorhandenen Tabs ist es auch möglich, einen neuen an einer bestimmten Position zu erstellen. Dazu gibt es mehrere überladene Versionen von `insertTab()`.

1. **`insertTab(int index, String text)`** - Fügt einen `Tab` an der gegebenen Indexposition im `TabbedPane` mit dem angegebenen `String` als Text des `Tabs` ein.
2. **`insertTab(int index, Tab tab)`** - Fügt den als Parameter übergebenen `Tab` an der angegebenen Indexposition im `TabbedPane` ein.
3. **`insertTab(int index, String text, Component component)`** - Fügt einen `Tab` mit dem angegebenen `String` als Text des `Tabs` ein und der bereitgestellte `Component`, der im Inhaltsbereich des `TabbedPane` angezeigt wird.
4. **`insertTab(int index, Tab tab, Component component)`** - Fügt den bereitgestellten `Tab` ein und zeigt den bereitgestellten `Component` im Inhaltsbereich des `TabbedPane` an.

### Entfernen eines `Tabs` {#removing-a-tab}

Um einen einzelnen `Tab` aus dem `TabbedPane` zu entfernen, verwenden Sie eine der folgenden Methoden:

1. **`removeTab(Tab tab)`** - Entfernt einen `Tab` aus dem `TabbedPane`, indem die Instanz des zu entfernenden Tabs übergeben wird.
2. **`removeTab(int index)`** - Entfernt einen `Tab` aus dem `TabbedPane`, indem der Index des zu entfernenden `Tabs` angegeben wird.

Zusätzlich zu diesen beiden Methoden zum Entfernen eines einzelnen `Tabs` kann die Methode **`removeAllTabs()`** verwendet werden, um das `TabbedPane` von allen Tabs zu leeren.

:::info
Die Methoden `remove()` und `removeAll()` entfernen keine Tabs innerhalb der Komponente.
:::

### Tab/Komponentenassoziation {#tabcomponent-association}

Um die `Komponente`, die für einen bestimmten `Tab` angezeigt werden soll, zu ändern, rufen Sie die Methode `setComponentFor()` auf und übergeben entweder die Instanz des `Tabs` oder den Index dieses Tabs innerhalb des `TabbedPane`.

:::info
Wenn diese Methode bei einem `Tab` verwendet wird, der bereits mit einer `Komponente` assoziiert ist, wird die zuvor assoziierte `Komponente` zerstört.
:::

## Konfiguration und Layout {#configuration-and-layout}

Die Klasse `TabbedPane` hat zwei wesentliche Teile: einen `Tab`, der an einem bestimmten Ort angezeigt wird, und eine Komponente, die angezeigt werden soll. Dies kann eine einzelne Komponente oder eine [`Composite`](../building-ui/composing-components) Komponente sein, die die Anzeige komplexerer Komponenten im Inhaltsbereich eines Tabs ermöglicht.

### Wischen {#swiping}

Das `TabbedPane` unterstützt die Navigation durch die verschiedenen Tabs durch Wischen. Dies ist ideal für eine mobile Anwendung, kann aber auch über eine integrierte Methode konfiguriert werden, um das Wischen mit der Maus zu unterstützen. Sowohl Wischen als auch Wischen mit der Maus sind standardmäßig deaktiviert, können jedoch mit den Methoden `setSwipable(boolean)` und `setSwipableWithMouse(boolean)` aktiviert werden.

### Tab-Platzierung {#tab-placement}

Die `Tabs` innerhalb eines `TabbedPane` können an verschiedenen Positionen innerhalb der Komponente basierend auf der Präferenz der Anwendungsentwickler platziert werden. Die bereitgestellten Optionen werden unter Verwendung des bereitgestellten Enums festgelegt, das die Werte `TOP`, `BOTTOM`, `LEFT`, `RIGHT` oder `HIDDEN` hat. Die Standardeinstellung ist `TOP`.

<ComponentDemo
path='/webforj/tabbedpaneplacement'
files={['src/main/java/com/webforj/samples/views/tabbedpane/TabbedPanePlacementView.java']}
height='400px'
/>

### Ausrichtung {#alignment}

Neben der Änderung der Platzierung der `Tab`-Elemente innerhalb des `TabbedPane` ist es auch möglich, zu konfigurieren, wie die Tabs innerhalb der Komponente ausgerichtet werden. Standardmäßig ist die Einstellung `AUTO` aktiv, wodurch die Platzierung der Tabs deren Ausrichtung bestimmt.

Die anderen Optionen sind `START`, `END`, `CENTER` und `STRETCH`. Die ersten drei beschreiben die Position relativ zur Komponente, wobei `STRETCH` die Tabs den verfügbaren Platz ausfüllen lässt.

<ComponentDemo
path='/webforj/tabbedpanealignment'
files={['src/main/java/com/webforj/samples/views/tabbedpane/TabbedPaneAlignmentView.java']}
height='250px'
/>

### Rahmen und Aktivitätsanzeige {#border-and-activity-indicator}

Das `TabbedPane` hat standardmäßig einen Rahmen, der für die darin befindlichen Tabs angezeigt wird und abhängig von der festgelegten `Placement` platziert ist. Dieser Rahmen hilft, den Bereich zu visualisieren, den die verschiedenen Tabs innerhalb des Paneels einnehmen.

Wenn ein `Tab` angeklickt wird, wird standardmäßig ein Aktivitätsindikator in der Nähe dieses `Tabs` angezeigt, um hervorzuheben, welcher der aktuell ausgewählte `Tab` ist.

Beide Optionen können von einem Entwickler angepasst werden, indem die booleschen Werte mit den entsprechenden Setter-Methoden geändert werden. Um zu ändern, ob der Rahmen angezeigt wird oder nicht, kann die Methode `setBorderless(boolean)` verwendet werden, wobei `true` den Rahmen ausblendet und `false`, der Standardwert, den Rahmen anzeigt.

:::info
Dieser Rahmen gilt nicht für das gesamte `TabbedPane`-Komponente und dient lediglich als Trennlinie zwischen den Tabs und dem Inhalt der Komponente.
:::

Um die Sichtbarkeit des aktiven Indikators festzulegen, kann die Methode `setHideActiveIndicator(boolean)` verwendet werden. Das Übergeben von `true` an diese Methode blendet den aktiven Indikator unter einem aktiven `Tab` aus, während `false`, der Standardwert, den Indikator angezeigt bleibt.

<ComponentDemo
path='/webforj/tabbedpaneborder'
files={['src/main/java/com/webforj/samples/views/tabbedpane/TabbedPaneBorderView.java']}
height='300px'
/>

### Aktivierungsmodi {#activation-modes}

Für mehr Kontrolle darüber, wie das `TabbedPane` beim Navigieren mit der Tastatur funktioniert, kann der `Aktivierungs`-Modus festgelegt werden, um zu spezifizieren, wie sich die Komponente verhalten soll.

- **`Auto`**: Wenn auf Auto gesetzt, wird das Navigieren der Tabs mit den Pfeiltasten die entsprechende Tab-Komponente sofort anzeigen.

- **`Manual`**: Wenn auf manuell gesetzt, erhält der Tab den Fokus, wird jedoch nicht angezeigt, bis der Benutzer die Leertaste oder Eingabetaste drückt.

<ComponentDemo
path='/webforj/tabbedpaneactivation'
files={['src/main/java/com/webforj/samples/views/tabbedpane/TabbedPaneActivationView.java']}
height='250px'
/>

### Entfernungsmöglichkeiten {#removal-options}

Einzelne `Tab`-Elemente können so konfiguriert werden, dass sie schließbar sind. Schließbare Tabs haben einen Schließen-Button, der zum Tab hinzugefügt wird, und ein Schließereignis auslöst, wenn er angeklickt wird. Das `TabbedPane` bestimmt, wie dieses Verhalten behandelt wird.

- **`Manual`**: Standardmäßig ist die Entfernung auf `MANUAL` eingestellt, was bedeutet, dass das Ereignis ausgelöst wird, es jedoch den Entwicklern überlassen bleibt, dieses Ereignis auf die gewünschte Weise zu behandeln.

- **`Auto`**: Alternativ kann `AUTO` verwendet werden, das das Ereignis auslöst und auch den `Tab` für den Entwickler aus der Komponente entfernt, wodurch die Notwendigkeit entfällt, dieses Verhalten manuell zu implementieren.

### Segmentsteuerung <DocChip chip='since' label='26.00' /> {#segment-control}

Das `TabbedPane` kann als Segmentsteuerung dargestellt werden, indem die `segment`-Eigenschaft mit `setSegment(true)` aktiviert wird. In diesem Modus werden Tabs mit einem rutschenden Pillenindikator angezeigt, der die aktive Auswahl hervorhebt und eine kompakte Alternative zur Standard-Tab-Interface bietet.

<ComponentDemo
path='/webforj/tabbedpanesegment'
files={['src/main/java/com/webforj/samples/views/tabbedpane/TabbedPaneSegmentView.java']}
height='250px'
/>

## Styling {#styling}

### Ausdehnung und Thema {#expanse-and-theme}

Das `TabbedPane` bietet integrierte `Expanse`- und `Theme`-Optionen, die anderen webforJ-Komponenten ähnlich sind. Diese können verwendet werden, um schnell ein Styling hinzuzufügen, das verschiedene Bedeutungen für den Endbenutzer vermittelt, ohne die Komponente mit CSS zu gestalten.

<ComponentDemo
path='/webforj/tabbedpaneexpansetheme'
files={['src/main/java/com/webforj/samples/views/tabbedpane/TabbedPaneExpanseThemeView.java']}
height='250px'
/>

<TableBuilder name={['Tab', 'TabbedPane']} />

## Best Practices {#best-practices}

Die folgenden Praktiken werden empfohlen, um das `TabbedPane` innerhalb von Anwendungen zu nutzen:

- **Logische Gruppierung**: Verwenden Sie Tabs, um verwandte Inhalte logisch zu gruppieren.
    >- Jeder Tab sollte eine DISTINCTE Kategorie oder Funktionalität innerhalb Ihrer Anwendung darstellen. 
    >- Gruppieren Sie ähnliche oder logische Tabs nahe beieinander.

- **Begrenzte Tabs**: Vermeiden Sie es, Benutzer mit zu vielen Tabs zu überwältigen. Ziehen Sie in Erwägung, eine hierarchische Struktur oder andere Navigationsmuster zu verwenden, wo dies für ein sauberes Interface anwendbar ist.

- **Klare Beschriftungen**: Beschriften Sie Ihre Tabs klar für intuitive Nutzung.
    >- Bieten Sie klare und prägnante Beschriftungen für jeden Tab an. 
    >- Beschriftungen sollten den Inhalt oder Zweck widerspiegeln, damit Benutzer es leicht verstehen können.
    >- Nutzen Sie Symbole und unterschiedliche Farben, wo anwendbar.

- **Tastaturnavigation**: Verwenden Sie die Tastaturnavigation von webforJ für das `TabbedPane`, um die Interaktion mit dem `TabbedPane` nahtloser und intuitiver für den Endbenutzer zu gestalten.

- **Standard-Tab**: Wenn der Standardtab nicht zu Beginn des `TabbedPane` platziert ist, ziehen Sie in Erwägung, diesen Tab als Standard für wesentliche oder häufig verwendete Informationen festzulegen.
