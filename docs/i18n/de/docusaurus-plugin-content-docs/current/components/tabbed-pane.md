---
title: TabbedPane
slug: tabbedpane
sidebar_position: 125
description: >-
  Organize content into switchable Tab sections with the TabbedPane component,
  supporting icons and customizable tab properties.
_i18n_hash: 544ab11783e8369075f1c02aba2d8dc8
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-tabbed-pane" />
<DocChip chip='since' label='23.06' />
<JavadocLink type="foundation" location="com/webforj/component/tabbedpane/TabbedPane" top='true'/>

Mehrere Inhaltsabschnitte können unter einem einzelnen `TabbedPane` organisiert werden, wobei jeder Abschnitt mit einem klickbaren `Tab` verbunden ist. Nur ein Abschnitt ist gleichzeitig sichtbar, und Tabs können Text, Symbole oder beides anzeigen, um Benutzern die Navigation zu erleichtern.

<!-- INTRO_END -->

## Anwendungen {#usages}

Die Klasse `TabbedPane` bietet Entwicklern ein leistungsstarkes Werkzeug zur Organisation und Präsentation mehrerer Tabs oder Abschnitte innerhalb einer Benutzeroberfläche. Hier sind einige typische Szenarien, in denen Sie ein `TabbedPane` in Ihrer App verwenden könnten:

1. **Dokumentenbetrachter**: Implementierung eines Dokumentenbetrachters, bei dem jeder Tab ein anderes Dokument oder eine andere Datei darstellt. Benutzer können einfach zwischen geöffneten Dokumenten wechseln, um effizient multitasking zu ermöglichen.

2. **Datenverwaltung**: Verwenden Sie ein `TabbedPane`, um Aufgaben zur Datenverwaltung zu organisieren, zum Beispiel:
    - Unterschiedliche Datensätze, die in einer App angezeigt werden sollen
    - Verschiedene Benutzerprofile können in separaten Tabs angezeigt werden
    - Unterschiedliche Profile in einem Benutzermanagementsystem

3. **Modulauswahl**: Ein `TabbedPane` kann verschiedene Module oder Abschnitte darstellen. Jeder Tab kann die Funktionalitäten eines bestimmten Moduls kapseln, sodass Benutzer sich jeweils auf einen Aspekt der App konzentrieren können.

4. **Aufgabenmanagement**: Aufgabenverwaltungsapps können ein `TabbedPane` verwenden, um verschiedene Projekte oder Aufgaben darzustellen. Jeder Tab könnte einem bestimmten Projekt entsprechen, was es den Benutzern ermöglicht, Aufgaben separat zu verwalten und zu verfolgen.

5. **Programmnavigation**: Innerhalb einer App, die verschiedene Programme ausführen muss, könnte ein `TabbedPane`:
    - Als Seitenleiste dienen, die es ermöglicht, verschiedene Apps oder Programme innerhalb einer einzigen App auszuführen, wie im [`AppLayout`](./app-layout.md)-Template gezeigt
    - Eine obere Leiste erstellen, die einen ähnlichen Zweck erfüllen oder Unteranwendungen innerhalb einer bereits ausgewählten App darstellen kann

## Tabs {#tabs}

Tabs sind UI-Elemente, die zu Tabbed-Panes hinzugefügt werden können, um verschiedene Inhaltsansichten zu organisieren und zwischen ihnen zu wechseln.

:::important
Tabs sind nicht dazu gedacht, als eigenständige Komponenten verwendet zu werden. Sie sollen in Verbindung mit Tabbed-Panes verwendet werden. Diese Klasse ist keine `Component` und sollte nicht so verwendet werden.
:::

### Eigenschaften {#properties}

Tabs bestehen aus den folgenden Eigenschaften, die beim Hinzufügen in einem `TabbedPane` verwendet werden. Diese Eigenschaften verfügen über Getter und Setter, um die Anpassung innerhalb eines `TabbedPane` zu erleichtern.

1. **Text(`String`)**: Der Text, der als Titel für den `Tab` innerhalb des `TabbedPane` angezeigt wird. Dies wird auch über die Methoden `getTitle()` und `setTitle(String title)` als Titel bezeichnet.

2. **Tooltip(`String`)**: Der Tooltip-Text, der mit dem `Tab` verbunden ist, der angezeigt wird, wenn der Cursor über den `Tab` schwebt.

3. **Enabled(`boolean`)**: Gibt an, ob der `Tab` aktiviert ist oder nicht. Kann mit der Methode `setEnabled(boolean enabled)` geändert werden.

4. **Closable(`boolean`)**: Gibt an, ob der `Tab` geschlossen werden kann. Kann mit der Methode `setClosable(boolean closable)` geändert werden. Dies fügt einen Schließknopf auf dem `Tab` hinzu, der angeklickt werden kann und ein Entfernevent auslöst. Die `TabbedPane`-Komponente diktiert, wie mit der Entfernung umzugehen ist.

5. **Slot(`Component`)**:
    Slots bieten flexible Optionen zur Verbesserung der Funktionalität eines `Tabs`. Sie können Symbole, Beschriftungen, Ladesymbole, Rücksetzfunktionen, Avatar-/Profilbilder und andere nützliche Komponenten innerhalb eines `Tabs` hinzufügen, um den Benutzern weitere Klarheit über die beabsichtigte Bedeutung zu geben. Sie können eine Komponente zum `prefix`-Slot eines `Tabs` während der Konstruktion hinzufügen. Alternativ können Sie die Methoden `setPrefixComponent()` und `setSuffixComponent()` verwenden, um verschiedene Komponenten vor und nach der angezeigten Option innerhalb eines `Tabs` einzufügen.

        ```java
        TabbedPane pane = new TabbedPane();
        pane.addTab(new Tab("Dokumente", TablerIcon.create("files")));
        ```

## `Tab`-Manipulation {#tab-manipulation}

Es gibt verschiedene Methoden, die Entwicklern ermöglichen, verschiedene Eigenschaften von `Tab`-Elementen innerhalb des `TabbedPane` hinzuzufügen, einzufügen, zu entfernen und zu manipulieren.

### Hinzufügen eines `Tabs` {#adding-a-tab}

Die Methoden `addTab()` und `add()` existieren in unterschiedlichen überladenen Variationen, um Entwicklern Flexibilität beim Hinzufügen neuer Tabs zum `TabbedPane` zu ermöglichen. Das Hinzufügen eines `Tabs` erfolgt nach bereits vorhandenen Tabs.

1. **`addTab(String text)`**: Fügt dem `TabbedPane` einen `Tab` mit dem angegebenen `String` als Text des `Tabs` hinzu.
2. **`addTab(Tab tab)`**: Fügt den bereitgestellten `Tab` als Parameter zum `TabbedPane` hinzu.
3. **`addTab(String text, Component component)`**: Fügt einen `Tab` mit dem gegebenen `String` als Text des `Tabs` hinzu und zeigt die bereitgestellte `Component` im Inhaltsbereich des `TabbedPane`.
4. **`addTab(Tab tab, Component component)`**: Fügt den bereitgestellten `Tab` hinzu und zeigt die bereitgestellte `Component` im Inhaltsbereich des `TabbedPane`.
5. **`add(Component... component)`**: Fügt ein oder mehrere `Component`-Instanzen zum `TabbedPane` hinzu und erstellt einen separaten `Tab` für jede, wobei der Text auf den Namen der `Component` gesetzt wird.

:::info
Die Methode `add(Component... component)` bestimmt den Namen der übergebenen `Component` durch den Aufruf von `component.getName()` auf dem übergebenen Argument.
:::

### Einfügen eines `Tabs` {#inserting-a-tab}

Zusätzlich zum Hinzufügen eines `Tabs` am Ende der vorhandenen Tabs ist es auch möglich, einen neuen an einer bestimmten Stelle zu erstellen. Dazu gibt es mehrere überladene Versionen der Methode `insertTab()`.

1. **`insertTab(int index, String text)`**: Fügt einen `Tab` an der angegebenen Indexstelle im `TabbedPane` mit dem angegebenen `String` als Text des `Tabs` ein.
2. **`insertTab(int index, Tab tab)`**: Fügt den bereitgestellten `Tab` als Parameter zum `TabbedPane` an der angegebenen Indexstelle ein.
3. **`insertTab(int index, String text, Component component)`**: Fügt einen `Tab` mit dem gegebenen `String` als Text des `Tabs` hinzu und zeigt die bereitgestellte `Component` im Inhaltsbereich des `TabbedPane`.
4. **`insertTab(int index, Tab tab, Component component)`**: Fügt den bereitgestellten `Tab` ein und zeigt die bereitgestellte `Component` im Inhaltsbereich des `TabbedPane`.

### Entfernen eines `Tabs` {#removing-a-tab}

Um einen einzelnen `Tab` aus dem `TabbedPane` zu entfernen, verwenden Sie eine der folgenden Methoden:

1. **`removeTab(Tab tab)`**: Entfernt einen `Tab` aus dem `TabbedPane`, indem die Tab-Instanz übergeben wird, die entfernt werden soll.
2. **`removeTab(int index)`**: Entfernt einen `Tab` aus dem `TabbedPane`, indem der Index des zu entfernenden `Tabs` angegeben wird.

Zusätzlich zu den beiden oben genannten Methoden zum Entfernen eines einzelnen `Tabs` können Sie die Methode **`removeAllTabs()`** verwenden, um das `TabbedPane` von allen Tabs zu bereinigen.

:::info
Die Methoden `remove()` und `removeAll()` entfernen keine Tabs innerhalb der Komponente.
:::

### Tab-/Komponenten-Assoziation {#tabcomponent-association}

Um die `Component` zu ändern, die für einen bestimmten `Tab` angezeigt werden soll, rufen Sie die Methode `setComponentFor()` auf und übergeben entweder die Instanz des `Tabs` oder den Index dieses Tabs innerhalb des `TabbedPane`.

:::info
Wenn diese Methode bei einem `Tab` verwendet wird, der bereits mit einer `Component` verbunden ist, wird die zuvor verbundene `Component` zerstört.
:::

## Konfiguration und Layout {#configuration-and-layout}

Die Klasse `TabbedPane` hat zwei wesentliche Teile: einen `Tab`, der an einem bestimmten Ort angezeigt wird, und eine Komponente, die angezeigt werden soll. Dies kann eine einzelne Komponente oder eine [`Composite`](/docs/building-ui/composing-components)-Komponente sein, die die Anzeige komplexerer Komponenten im Inhaltsbereich eines Tabs ermöglicht.

### Wischen {#swiping}

Das `TabbedPane` unterstützt das Navigieren durch die verschiedenen Tabs per Wischen. Dies ist ideal für eine mobile App, kann aber auch über eine integrierte Methode konfiguriert werden, um das Wischen mit der Maus zu unterstützen. Sowohl das Wischen als auch das Wischen mit der Maus sind standardmäßig deaktiviert, können jedoch mit den Methoden `setSwipeable(boolean)` und `setSwipeWithMouse(boolean)` aktiviert werden.

### Tab-Platzierung {#tab-placement}

Die `Tabs` innerhalb eines `TabbedPane` können in verschiedenen Positionen innerhalb der Komponente je nach Vorliebe der App-Entwickler platziert werden. Bereitgestellte Optionen werden mit dem bereitgestellten Enum gesetzt, das die Werte `TOP`, `BOTTOM`, `LEFT`, `RIGHT` oder `HIDDEN` hat. Die Standardeinstellung ist `TOP`.

<ComponentDemo
path='/webforj/tabbedpaneplacement'
files={['src/main/java/com/webforj/samples/views/tabbedpane/TabbedPanePlacementView.java']}
height='400px'
/>

### Ausrichtung {#alignment}

Zusätzlich zur Änderung der Platzierung der `Tab`-Elemente innerhalb des `TabbedPane` ist es auch möglich, zu konfigurieren, wie die Tabs innerhalb der Komponente ausgerichtet werden. Standardmäßig ist die Einstellung `AUTO` aktiv, die es ermöglicht, dass die Platzierung der Tabs ihre Ausrichtung diktiert.

Die anderen Optionen sind `START`, `END`, `CENTER` und `STRETCH`. Die ersten drei beschreiben die Position relativ zur Komponente, wobei `STRETCH` die Tabs den verfügbaren Platz ausfüllen lässt.

<ComponentDemo
path='/webforj/tabbedpanealignment'
files={['src/main/java/com/webforj/samples/views/tabbedpane/TabbedPaneAlignmentView.java']}
height='250px'
/>

### Rahmen und Aktivitätsanzeige {#border-and-activity-indicator}

Das `TabbedPane` zeigt standardmäßig einen Rahmen für die darin enthaltenen Tabs an, der abhängig von der festgelegten `Placement` platziert wird. Dieser Rahmen hilft, den Platz zu visualisieren, den die verschiedenen Tabs innerhalb des Paneels einnehmen.

Wenn ein `Tab` angeklickt wird, wird standardmäßig eine Aktivitätsanzeige in der Nähe dieses `Tabs` angezeigt, um hervorzuheben, welcher `Tab` derzeit ausgewählt ist.

Beide Optionen können angepasst werden, indem die booleschen Werte über die entsprechenden Setter-Methoden geändert werden. Um zu ändern, ob der Rahmen angezeigt wird oder nicht, kann die Methode `setBorderless(boolean)` verwendet werden, wobei `true` den Rahmen versteckt und `false`, der Standardwert, den Rahmen anzeigt.

:::info
Dieser Rahmen gilt nicht für die gesamte `TabbedPane`-Komponente und dient lediglich als Trennlinie zwischen den Tabs und dem Inhalt der Komponente.
:::

Um die Sichtbarkeit des aktiven Indikators einzustellen, kann die Methode `setHideActiveIndicator(boolean)` verwendet werden. Das Übergeben von `true` an diese Methode versteckt den aktiven Indikator unter einem aktiven `Tab`, während `false`, der Standard, den Indikator sichtbar lässt.

<ComponentDemo
path='/webforj/tabbedpaneborder'
files={['src/main/java/com/webforj/samples/views/tabbedpane/TabbedPaneBorderView.java']}
height='300px'
/>

### Aktivierungsmodi {#activation-modes}

Um eine feinere Kontrolle darüber zu erhalten, wie das `TabbedPane` mit der Tastaturnavigation umgeht, kann der `Aktivierungsmodus` festgelegt werden, um zu spezifizieren, wie sich die Komponente verhalten soll.

- **`Auto`**: Wenn auf Auto gesetzt, zeigt die Navigation der Tabs mit den Pfeiltasten sofort die entsprechende Tab-Komponente an.

- **`Manual`**: Wenn auf manuell gesetzt, erhält der Tab den Fokus, zeigt jedoch nicht an, bis der Benutzer die Leertaste oder Enter drückt.

<ComponentDemo
path='/webforj/tabbedpaneactivation'
files={['src/main/java/com/webforj/samples/views/tabbedpane/TabbedPaneActivationView.java']}
height='250px'
/>

### Entfernen-Optionen {#removal-options}

Einzelne `Tab`-Elemente können so eingestellt werden, dass sie schließbar sind. Schließbare Tabs haben einen Schließknopf, der beim Klicken ein Schließereignis auslöst. Das `TabbedPane` gibt vor, wie dieses Verhalten behandelt wird.

- **`Manual`**: Standardmäßig ist das Entfernen auf `MANUAL` eingestellt, was bedeutet, dass das Ereignis ausgelöst wird, aber es liegt am Entwickler, dieses Ereignis auf beliebige Weise zu behandeln.

- **`Auto`**: Alternativ kann `AUTO` verwendet werden, was das Ereignis auslöst und auch den `Tab` aus der Komponente entfernt, sodass der Entwickler dieses Verhalten nicht manuell implementieren muss.

### Segmentsteuerung <DocChip chip='since' label='26.00' /> {#segment-control}

Das `TabbedPane` kann als Segmentsteuerung gerendert werden, indem die `segment`-Eigenschaft mit `setSegment(true)` aktiviert wird. In diesem Modus werden Tabs mit einem gleitenden Pillenindikator angezeigt, der die aktive Auswahl hervorhebt und eine kompakte Alternative zur standardmäßigen Tab-Oberfläche bietet.

<ComponentDemo
path='/webforj/tabbedpanesegment'
files={['src/main/java/com/webforj/samples/views/tabbedpane/TabbedPaneSegmentView.java']}
height='250px'
/>

## Styling {#styling}

### Ausdehnung und Thema {#expanse-and-theme}

Das `TabbedPane` bietet integrierte `Expanse`- und `Theme`-Optionen, die anderen webforJ-Komponenten ähnlich sind. Diese können verwendet werden, um schnell Stile hinzuzufügen, die dem Endbenutzer verschiedene Bedeutungen vermitteln, ohne das Element mit CSS zu stylen.

<ComponentDemo
path='/webforj/tabbedpaneexpansetheme'
files={['src/main/java/com/webforj/samples/views/tabbedpane/TabbedPaneExpanseThemeView.java']}
height='250px'
/>

<TableBuilder name={['Tab', 'TabbedPane']} />

## Beste Praktiken {#best-practices}

Die folgenden Praktiken werden empfohlen, um das `TabbedPane` innerhalb von Anwendungen zu verwenden:

- **Logische Gruppierung**: Verwenden Sie Tabs, um verwandte Inhalte logisch zu gruppieren:
    - Jeder Tab sollte eine distinct Kategorie oder Funktion darstellen.
    - Gruppieren Sie ähnliche oder logische Tabs in der Nähe voneinander.

- **Begrenzte Tabs**: Vermeiden Sie es, Benutzer mit zu vielen Tabs zu überwältigen. Ziehen Sie es in Betracht, eine hierarchische Struktur oder andere Navigationsmuster anzuwenden, wo dies angebracht ist, um eine saubere Benutzeroberfläche zu gestalten.

- **Klare Beschriftungen**: Beschriften Sie Ihre Tabs klar für eine intuitive Nutzung:
    - Bieten Sie klare und prägnante Beschriftungen für jeden Tab an.
    - Die Beschriftungen sollten den Inhalt oder Zweck widerspiegeln, um es den Benutzern zu erleichtern, es zu verstehen.
    - Verwenden Sie Symbole und unterschiedliche Farben, wo es angebracht ist.

- **Tastaturnavigation**: Verwenden Sie die Unterstützung für die Tastaturnavigation des webforJ `TabbedPane`, um die Interaktion mit dem `TabbedPane` für den Endbenutzer nahtloser und intuitiver zu gestalten.

- **Standard-Tab**: Wenn der Standard-Tab nicht am Anfang des `TabbedPane` platziert ist, sollten Sie in Betracht ziehen, diesen Tab als Standard für wichtige oder häufig verwendete Informationen festzulegen.
