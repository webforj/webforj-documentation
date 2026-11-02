---
title: TabbedPane
slug: tabbedpane
sidebar_position: 125
description: >-
  Organize content into switchable Tab sections with the TabbedPane component,
  supporting icons and customizable tab properties.
_i18n_hash: 2d2e78638206283a7423cea736e3bf2c
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-tabbed-pane" />
<DocChip chip='since' label='23.06' />
<JavadocLink type="foundation" location="com/webforj/component/tabbedpane/TabbedPane" top='true'/>

Mehrere Inhaltsbereiche können unter einem einzelnen `TabbedPane` organisiert werden, wobei jeder Bereich mit einem anklickbaren `Tab` verbunden ist. Immer nur ein Bereich ist gleichzeitig sichtbar, und Tabs können Text, Symbole oder beides anzeigen, um den Benutzern die Navigation zu erleichtern.

<!-- INTRO_END -->

## Verwendungen {#usages}

Die Klasse `TabbedPane` bietet Entwicklern ein leistungsstarkes Werkzeug zur Organisation und Präsentation mehrerer Tabs oder Abschnitte innerhalb einer Benutzeroberfläche. Hier sind einige typische Szenarien, in denen Sie ein `TabbedPane` in Ihrer App verwenden könnten:

1. **Dokumentenanzeige**: Implementierung eines Dokumentenbetrachters, bei dem jeder Tab ein anderes Dokument oder eine Datei darstellt. Benutzer können leicht zwischen geöffneten Dokumenten wechseln, um effizient Multitasking zu betreiben.

2. **Datenverwaltung**: Verwenden Sie ein `TabbedPane`, um Datenverwaltungsaufgaben zu organisieren, zum Beispiel:
    - Verschiedene Datensätze, die in einer App angezeigt werden
    - Verschiedene Benutzerprofile können innerhalb separater Tabs dargestellt werden
    - Verschiedene Profile in einem Benutzermanagementsystem

3. **Modulauswahl**: Ein `TabbedPane` kann verschiedene Module oder Abschnitte darstellen. Jeder Tab kann die Funktionen eines bestimmten Moduls kapseln, wodurch Benutzer sich jeweils auf einen Aspekt der App konzentrieren können.

4. **Aufgabenverwaltung**: Aufgabenverwaltungs-Apps können ein `TabbedPane` verwenden, um verschiedene Projekte oder Aufgaben darzustellen. Jeder Tab könnte einem bestimmten Projekt entsprechen, sodass Benutzer Aufgaben separat verwalten und verfolgen können.

5. **Programmnavigation**: Innerhalb einer App, die verschiedene Programme ausführen muss, könnte ein `TabbedPane`:
    - Als Seitenleiste dienen, die es ermöglicht, verschiedene Apps oder Programme innerhalb einer einzigen App auszuführen, ähnlich wie im [`AppLayout`](./app-layout.md) Template gezeigt
    - Eine obere Leiste erstellen, die einen ähnlichen Zweck erfüllen oder Unteranwendungen innerhalb einer bereits ausgewählten App darstellen kann

## Tabs <DocChip chip='name' label="dwc-tab" /> {#tabs}

Tabs sind UI-Elemente, die zu Tab-Bereichen hinzugefügt werden können, um zwischen verschiedenen Inhaltsansichten zu organisieren und zu wechseln.

:::important
Tabs sind nicht als eigenständige Komponenten gedacht. Sie sollten in Verbindung mit Tab-Bereichen verwendet werden. Diese Klasse ist kein `Component` und sollte nicht als solche verwendet werden.
:::

### Eigenschaften {#properties}

Tabs bestehen aus den folgenden Eigenschaften, die verwendet werden, wenn sie in einem `TabbedPane` hinzugefügt werden. Diese Eigenschaften verfügen über Getter und Setter, um die Anpassung innerhalb eines `TabbedPane` zu erleichtern.

1. **Text(`String`)**: Der Text, der als Titel für den `Tab` innerhalb des `TabbedPane` angezeigt wird. Dies wird auch als Titel über die Methoden `getTitle()` und `setTitle(String title)` bezeichnet.

2. **Tooltip(`String`)**: Der mit dem `Tab` verbundene Tooltip-Text, der angezeigt wird, wenn der Cursor über den `Tab` schwebt.

3. **Enabled(`boolean`)**: Repräsentiert, ob der `Tab` aktiviert ist oder nicht. Kann mit der Methode `setEnabled(boolean enabled)` modifiziert werden.

4. **Closable(`boolean`)**: Repräsentiert, ob der `Tab` geschlossen werden kann. Kann mit der Methode `setClosable(boolean closable)` modifiziert werden. Dies fügt einen Schließen-Button zum `Tab` hinzu, der angeklickt werden kann und ein Entfernevent auslöst. Die `TabbedPane`-Komponente bestimmt, wie das Entfernen gehandhabt wird.

5. **Slot(`Component`)**:
    Slots bieten flexible Optionen zur Verbesserung der Funktionalität eines `Tabs`. Sie können Symbole, Beschriftungen, Ladeanimationen, Löschen/Zurücksetzen-Funktionen, Avatar-/Profilbilder und andere nützliche Komponenten innerhalb eines `Tabs` einfügen, um den Benutzern weitergehende Bedeutung zu vermitteln.
    Sie können einen Bestandteil zum `prefix`-Slot eines `Tabs` während des Aufbaus hinzufügen. Alternativ können Sie die Methoden `setPrefixComponent()` und `setSuffixComponent()` verwenden, um verschiedene Komponenten vor und nach der angezeigten Option innerhalb eines `Tabs` einzufügen.

        ```java
        TabbedPane pane = new TabbedPane();
        pane.addTab(new Tab("Dokumente", TablerIcon.create("files")));
        ```

## `Tab`-Manipulation {#tab-manipulation}

Es gibt verschiedene Methoden, die Entwicklern ermöglichen, `Tab`-Elemente innerhalb des `TabbedPane` hinzuzufügen, einzufügen, zu entfernen und verschiedene Eigenschaften zu manipulieren.

### Hinzufügen eines `Tabs` {#adding-a-tab}

Die Methoden `addTab()` und `add()` existieren in verschiedenen überladenen Varianten, um Entwicklern Flexibilität beim Hinzufügen neuer Tabs zum `TabbedPane` zu bieten. Das Hinzufügen eines `Tabs` erfolgt nach allen zuvor bestehenden Tabs.

1. **`addTab(String text)`**: Fügt einen `Tab` zum `TabbedPane` mit dem angegebenen `String` als Text des `Tabs` hinzu.
2. **`addTab(Tab tab)`**: Fügt den `Tab`, der als Parameter bereitgestellt wird, zum `TabbedPane` hinzu.
3. **`addTab(String text, Component component)`**: Fügt einen `Tab` mit dem gegebenen `String` als Text des `Tabs` hinzu, und die bereitgestellte `Component` wird im Inhaltsbereich des `TabbedPane` angezeigt.
4. **`addTab(Tab tab, Component component)`**: Fügt den bereitgestellten `Tab` hinzu und zeigt die bereitgestellte `Component` im Inhaltsbereich des `TabbedPane` an.
5. **`add(Component... component)`**: Fügt eine oder mehrere `Component`-Instanzen zum `TabbedPane` hinzu, wobei für jede eine separate `Tab` erstellt wird, wobei der Text auf den Namen der `Component` gesetzt wird.

:::info
Die Methode `add(Component... component)` bestimmt den Namen der übergebenen `Component`, indem `component.getName()` auf das übergebene Argument aufgerufen wird.
:::

### Einfügen eines `Tabs` {#inserting-a-tab}

Neben dem Hinzufügen eines `Tabs` am Ende der bestehenden Tabs ist es auch möglich, einen neuen an einer bestimmten Position zu erstellen. Dazu gibt es mehrere überladene Versionen der `insertTab()`.

1. **`insertTab(int index, String text)`**: Fügt einen `Tab` an der angegebenen Indexposition im `TabbedPane` mit dem angegebenen `String` als Text des `Tabs` ein.
2. **`insertTab(int index, Tab tab)`**: Fügt den als Parameter übergebenen `Tab` an der angegebenen Indexposition im `TabbedPane` ein.
3. **`insertTab(int index, String text, Component component)`**: Fügt einen `Tab` mit dem gegebenen `String` als Text des `Tabs` ein, und die bereitgestellte `Component` wird im Inhaltsbereich des `TabbedPane` angezeigt.
4. **`insertTab(int index, Tab tab, Component component)`**: Fügt den bereitgestellten `Tab` ein und zeigt die bereitgestellte `Component` im Inhaltsbereich des `TabbedPane` an.

### Entfernen eines `Tabs` {#removing-a-tab}

Um einen einzelnen `Tab` aus dem `TabbedPane` zu entfernen, verwenden Sie eine der folgenden Methoden:

1. **`removeTab(Tab tab)`**: Entfernt einen `Tab` aus dem `TabbedPane`, indem die zu entfernende `Tab`-Instanz übergeben wird.
2. **`removeTab(int index)`**: Entfernt einen `Tab` aus dem `TabbedPane`, indem der Index des zu entfernenden `Tabs` angegeben wird.

Neben den beiden oben genannten Methoden zum Entfernen eines einzelnen `Tabs` kann die Methode **`removeAllTabs()`** verwendet werden, um das `TabbedPane` von allen Tabs zu leeren.

:::info
Die Methoden `remove()` und `removeAll()` entfernen keine Tabs innerhalb der Komponente.
:::

### Tab/Komponenten-Zuordnung {#tabcomponent-association}

Um die `Component`, die für einen bestimmten `Tab` angezeigt werden soll, zu ändern, rufen Sie die Methode `setComponentFor()` auf und übergeben entweder die Instanz des `Tabs` oder den Index dieses Tabs innerhalb des `TabbedPane`.

:::info
Wenn diese Methode bei einem `Tab` verwendet wird, der bereits mit einer `Component` assoziiert ist, wird die zuvor assoziierte `Component` zerstört.
:::

## Konfiguration und Layout {#configuration-and-layout}

Die Klasse `TabbedPane` hat zwei wesentliche Teile: einen `Tab`, der an einem bestimmten Ort angezeigt wird, und eine anzuzeigende Komponente. Dies kann eine einzelne Komponente oder eine [`Composite`](/docs/building-ui/composing-components) Komponente sein, die die Anzeige komplexerer Komponenten im Inhaltsbereich eines Tabs ermöglicht.

### Wischen {#swiping}

Das `TabbedPane` unterstützt die Navigation über verschiedene Tabs durch Wischen. Dies ist ideal für eine mobile App, kann jedoch auch über eine integrierte Methode konfiguriert werden, um das Wischen mit der Maus zu unterstützen. Sowohl Wischen als auch Wischen mit der Maus sind standardmäßig deaktiviert, können jedoch mit den Methoden `setSwipeable(boolean)` und `setSwipeWithMouse(boolean)` respectively aktiviert werden.

### Tab-Platzierung {#tab-placement}

Die `Tabs` innerhalb eines `TabbedPane` können in verschiedenen Positionen innerhalb der Komponente basierend auf den Vorlieben der Anwendungsentwickler platziert werden. Die verfügbaren Optionen werden mit dem bereitgestellten Enum eingestellt, das die Werte `TOP`, `BOTTOM`, `LEFT`, `RIGHT` oder `HIDDEN` enthält. Die Standardeinstellung ist `TOP`.

<ComponentDemo
path='/webforj/tabbedpaneplacement'
files={['src/main/java/com/webforj/samples/views/tabbedpane/TabbedPanePlacementView.java']}
height='400px'
/>

### Ausrichtung {#alignment}

Neben der Änderung der Platzierung der `Tab`-Elemente innerhalb des `TabbedPane` ist es auch möglich, zu konfigurieren, wie die Tabs innerhalb der Komponente ausgerichtet werden. Standardmäßig ist die Einstellung `AUTO` aktiv, was es der Platzierung der Tabs ermöglicht, ihre Ausrichtung zu diktieren.

Die anderen Optionen sind `START`, `END`, `CENTER` und `STRETCH`. Die ersten drei beschreiben die Position relativ zur Komponente, wobei `STRETCH` die Tabs den verfügbaren Raum ausfüllen lässt.

<ComponentDemo
path='/webforj/tabbedpanealignment'
files={['src/main/java/com/webforj/samples/views/tabbedpane/TabbedPaneAlignmentView.java']}
height='250px'
/>

### Rahmen und Aktivitätsindikator {#border-and-activity-indicator}

Das `TabbedPane` hat standardmäßig einen Rahmen, der für die darin enthaltenen Tabs angezeigt wird, und dieser wird abhängig von der festgelegten `Platzierung` platziert. Dieser Rahmen hilft, den Raum zu visualisieren, den die verschiedenen Tabs innerhalb der Pane einnehmen.

Wenn ein `Tab` angeklickt wird, wird standardmäßig ein Aktivitätsindikator in der Nähe dieses `Tabs` angezeigt, um zu verdeutlichen, welcher der aktuell ausgewählte `Tab` ist.

Beide Optionen können angepasst werden, indem die booleschen Werte mithilfe der entsprechenden Setter-Methoden geändert werden. Um festzulegen, ob der Rahmen angezeigt wird oder nicht, kann die Methode `setBorderless(boolean)` verwendet werden, wobei `true` den Rahmen versteckt und `false`, der Standardwert, den Rahmen anzeigt.

:::info
Dieser Rahmen gilt nicht für die Gesamtheit der `TabbedPane`-Komponente und dient lediglich als Trennlinie zwischen den Tabs und dem Inhalt der Komponente.
:::

Um die Sichtbarkeit des aktiven Indikators festzulegen, kann die Methode `setHideActiveIndicator(boolean)` verwendet werden. Durch das Übergeben von `true` an diese Methode wird der aktive Indikator unter einem aktiven `Tab` verborgen, während `false`, der Standardwert, den Indikator sichtbar hält.

<ComponentDemo
path='/webforj/tabbedpaneborder'
files={['src/main/java/com/webforj/samples/views/tabbedpane/TabbedPaneBorderView.java']}
height='300px'
/>

### Aktivierungsmodi {#activation-modes}

Für eine genauere Steuerung darüber, wie das `TabbedPane` mit der Tastaturnavigation umgeht, kann der `Aktivierungs`-Modus festgelegt werden, um anzugeben, wie sich die Komponente verhalten soll.

- **`Auto`**: Wenn auf Auto gesetzt, wird beim Navigieren durch die Tabs mit den Pfeiltasten sofort die entsprechende Tab-Komponente angezeigt.

- **`Manuell`**: Wenn auf manuell gesetzt, erhält der Tab den Fokus, wird jedoch nicht angezeigt, bis der Benutzer die Leertaste oder Eingabetaste drückt.

<ComponentDemo
path='/webforj/tabbedpaneactivation'
files={['src/main/java/com/webforj/samples/views/tabbedpane/TabbedPaneActivationView.java']}
height='250px'
/>

### Entfernen-Optionen {#removal-options}

Einzelne `Tab`-Elemente können so eingestellt werden, dass sie schließbar sind. Schließbare Tabs erhalten einen Schließen-Button, der zur Tab hinzugefügt wird, was ein Schließen-Ereignis auslöst, wenn er angeklickt wird. Das `TabbedPane` bestimmt, wie dieses Verhalten gehandhabt wird.

- **`Manuell`**: Standardmäßig ist das Entfernen auf `MANUAL` gesetzt, was bedeutet, dass das Ereignis ausgelöst wird, aber es liegt am Entwickler, dieses Ereignis auf die von ihm gewählte Weise zu verwalten.

- **`Auto`**: Alternativ kann `AUTO` verwendet werden, das das Ereignis auslöst und den `Tab` auch für den Entwickler aus der Komponente entfernt, wodurch die Notwendigkeit entfällt, dieses Verhalten manuell zu implementieren.

### Segmentsteuerung <DocChip chip='since' label='26.00' /> {#segment-control}

Das `TabbedPane` kann als Segmentsteuerelement dargestellt werden, indem die `segment`-Eigenschaft mit `setSegment(true)` aktiviert wird. In diesem Modus werden die Tabs mit einem gleitenden Pillenindikator angezeigt, der die aktive Auswahl hervorhebt und eine kompakte Alternative zur standardmäßigen Tab-Oberfläche bietet.

<ComponentDemo
path='/webforj/tabbedpanesegment'
files={['src/main/java/com/webforj/samples/views/tabbedpane/TabbedPaneSegmentView.java']}
height='250px'
/>

## Styling {#styling}

### Expanse und Thema {#expanse-and-theme}

Das `TabbedPane` verfügt über integrierte `Expanse`- und `Theme`-Optionen, die den anderen webforJ-Komponenten ähnlich sind. Diese können verwendet werden, um schnell Styling hinzuzufügen, das den Endbenutzern verschiedene Bedeutungen vermittelt, ohne die Komponente mit CSS zu stylen.

<ComponentDemo
path='/webforj/tabbedpaneexpansetheme'
files={['src/main/java/com/webforj/samples/views/tabbedpane/TabbedPaneExpanseThemeView.java']}
height='250px'
/>

<TableBuilder name={['Tab', 'TabbedPane']} />

## Beste Praktiken {#best-practices}

Die folgenden Praktiken werden für die Verwendung des `TabbedPane` innerhalb von Anwendungen empfohlen:

- **Logische Gruppierung**: Verwenden Sie Tabs, um verwandte Inhalte logisch zu gruppieren:
    - Jeder Tab sollte eine separate Kategorie oder Funktionalität innerhalb Ihrer App darstellen.
    - Gruppieren Sie ähnliche oder logische Tabs nahe beieinander.

- **Begrenzte Tabs**: Vermeiden Sie es, Benutzer mit zu vielen Tabs zu überfordern. Ziehen Sie in Betracht, eine hierarchische Struktur oder andere Navigationsmuster zu verwenden, wo dies anwendbar ist, für eine saubere Benutzeroberfläche.

- **Klare Beschriftungen**: Beschriften Sie Ihre Tabs klar für eine intuitive Verwendung:
    - Geben Sie klare und prägnante Beschriftungen für jeden Tab an.
    - Beschriftungen sollten den Inhalt oder Zweck widerspiegeln und es Benutzern erleichtern, sie zu verstehen.
    - Verwenden Sie Symbole und unterschiedliche Farben, wo dies anwendbar ist.

- **Tastaturnavigation**: Verwenden Sie die Tastaturnavigation des `TabbedPane` von webforJ, um die Interaktion mit dem `TabbedPane` für den Endbenutzer nahtloser und intuitiver zu gestalten.

- **Standard-Tab**: Wenn der Standard-Tab nicht am Anfang des `TabbedPane` platziert wird, ziehen Sie in Betracht, diesen Tab als Standard für wesentliche oder häufig verwendete Informationen festzulegen.
