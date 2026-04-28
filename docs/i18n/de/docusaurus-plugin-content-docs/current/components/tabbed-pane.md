---
title: TabbedPane
slug: tabbedpane
sidebar_position: 125
sidebar_class_name: new-content
_i18n_hash: 790dce3f2bce2da54e03b7407c11204b
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-tabbed-pane" />
<DocChip chip='since' label='23.06' />
<JavadocLink type="foundation" location="com/webforj/component/tabbedpane/TabbedPane" top='true'/>

Mehrere Inhaltsabschnitte können unter einem einzigen `TabbedPane` organisiert werden, wobei jeder Abschnitt mit einem anklickbaren `Tab` verknüpft ist. Immer nur ein Abschnitt ist gleichzeitig sichtbar, und Tabs können Text, Symbole oder beides anzeigen, um den Benutzern die Navigation zu erleichtern.

<!-- INTRO_END -->

## Usages {#usages}

Die Klasse `TabbedPane` bietet Entwicklern ein leistungsfähiges Werkzeug zum Organisieren und Präsentieren mehrerer Tabs oder Abschnitte innerhalb einer Benutzeroberfläche. Hier sind einige typische Szenarien, in denen Sie ein `TabbedPane` in Ihrer Anwendung verwenden könnten:

1. **Dokumentenbetrachter**: Implementierung eines Dokumentenbetrachters, bei dem jeder Tab ein anderes Dokument oder eine Datei darstellt. Benutzer können einfach zwischen geöffneten Dokumenten wechseln, um effizient multitaskingfähig zu sein.

2. **Datenverwaltung:**: Nutzen Sie ein `TabbedPane`, um Datenverwaltungsaufgaben zu organisieren, beispielsweise:
    >- Verschiedene Datensätze, die in einer Anwendung angezeigt werden
    >- Verschiedene Benutzerprofile können in separaten Tabs angezeigt werden
    >- Unterschiedliche Profile in einem Benutzermanagementsystem

3. **Modulwahl**: Ein `TabbedPane` kann verschiedene Module oder Abschnitte darstellen. Jeder Tab kann die Funktionen eines bestimmten Moduls kapseln, sodass Benutzer sich jeweils auf einen Aspekt der Anwendung konzentrieren können.

4. **Aufgabenmanagement**: Aufgabenverwaltungsanwendungen können ein `TabbedPane` verwenden, um verschiedene Projekte oder Aufgaben darzustellen. Jeder Tab könnte einem bestimmten Projekt entsprechen, sodass Benutzer Aufgaben getrennt verwalten und verfolgen können.

5. **Anwendungsnavigation**: Innerhalb einer Anwendung, die verschiedene Programme ausführen muss, könnte ein `TabbedPane`:
    >- Als Seitenleiste dienen, die es erlaubt, verschiedene Anwendungen oder Programme innerhalb einer einzigen Anwendung auszuführen, wie im [`AppLayout`](./app-layout.md) -Template dargestellt
    >- Eine obere Leiste erstellen, die einen ähnlichen Zweck erfüllen oder Unteranwendungen innerhalb einer bereits ausgewählten Anwendung darstellen kann.
  
## Tabs {#tabs}

Tabs sind UI-Elemente, die zu Tabbed-Panes hinzugefügt werden können, um verschiedene Inhaltsansichten zu organisieren und zwischen ihnen zu wechseln.

:::important
Tabs sind nicht als unabhängige Komponenten gedacht. Sie sollen in Verbindung mit Tabbed-Panes verwendet werden. Diese Klasse ist kein `Component` und sollte nicht als solche verwendet werden.
:::

### Eigenschaften {#properties}

Tabs bestehen aus den folgenden Eigenschaften, die verwendet werden, wenn sie in einem `TabbedPane` hinzugefügt werden. Diese Eigenschaften verfügen über Getter und Setter, um eine Anpassung innerhalb eines `TabbedPane` zu erleichtern.

1. **Key(`Object`)**: Stellt den eindeutigen Bezeichner für den `Tab` dar.

2. **Text(`String`)**: Der Text, der als Titel für den `Tab` innerhalb des `TabbedPane` angezeigt wird. Dies wird auch über die Methoden `getTitle()` und `setTitle(String title)` bezeichnet.

3. **Tooltip(`String`)**: Der Tooltip-Text, der mit dem `Tab` verknüpft ist und angezeigt wird, wenn der Cursor über den `Tab` schwebt.

4. **Enabled(`boolean`)**: Stellt dar, ob der `Tab` derzeit aktiviert ist oder nicht. Kann mit der Methode `setEnabled(boolean enabled)` geändert werden.

5. **Closeable(`boolean`)**: Stellt dar, ob der `Tab` geschlossen werden kann. Kann mit der Methode `setCloseable(boolean enabled)` geändert werden. Dadurch wird eine Schaltfläche zum Schließen auf dem `Tab` hinzugefügt, die vom Benutzer angeklickt werden kann und ein Entfernevent auslöst. Die `TabbedPane`-Komponente bestimmt, wie das Entfernen behandelt wird.

6. **Slot(`Component`)**: 
    Slots bieten flexible Optionen zur Verbesserung der Funktionalität eines `Tabs`. Sie können Symbole, Beschriftungen, Lade-Spinners, Optionen zum Löschen/Zurücksetzen, Avatar-/Profilbilder und andere nützliche Komponenten innerhalb eines `Tabs` hinzufügen, um die beabsichtigte Bedeutung für die Benutzer weiter zu verdeutlichen.
    Sie können eine Komponente während der Konstruktion zum `prefix`-Slot eines `Tabs` hinzufügen. Alternativ können Sie die Methoden `setPrefixComponent()` und `setSuffixComponent()` verwenden, um verschiedene Komponenten vor und nach der angezeigten Option innerhalb eines `Tabs` einzufügen.

        ```java
        TabbedPane pane = new TabbedPane();
        pane.addTab(new Tab("Documents", TablerIcon.create("files")));
        ```

## `Tab`-Manipulation {#tab-manipulation}

Es gibt verschiedene Methoden, mit denen Entwickler Tabs hinzufügen, einfügen, entfernen und verschiedene Eigenschaften von `Tab`-Elementen innerhalb des `TabbedPane` manipulieren können.

### Hinzufügen eines `Tabs` {#adding-a-tab}

Die Methoden `addTab()` und `add()` existieren in verschiedenen überladenen Varianten, um den Entwicklern Flexibilität beim Hinzufügen neuer Tabs zum `TabbedPane` zu ermöglichen. Das Hinzufügen eines `Tabs` erfolgt nach bereits bestehenden Tabs.

1. **`addTab(String text)`** - Fügt dem `TabbedPane` einen `Tab` mit dem angegebenen `String` als Text des `Tabs` hinzu.
2. **`addTab(Tab tab)`** - Fügt den als Parameter bereitgestellten `Tab` zum `TabbedPane` hinzu.
3. **`addTab(String text, Component component)`** - Fügt einen `Tab` mit dem angegebenen `String` als Text des `Tabs` hinzu, und die bereitgestellte `Komponente` wird im Inhaltsbereich des `TabbedPane` angezeigt.
4. **`addTab(Tab tab, Component component)`** - Fügt den bereitgestellten `Tab` hinzu und zeigt die bereitgestellte `Komponente` im Inhaltsbereich des `TabbedPane` an.
5. **`add(Component... component)`** - Fügt ein oder mehrere `Component`-Instanzen zum `TabbedPane` hinzu und erstellt für jede eine separate `Tab`, wobei der Text auf den Namen der `Komponente` gesetzt wird.

:::info
Die Methode `add(Component... component)` bestimmt den Namen der übergebenen `Komponente` durch den Aufruf von `component.getName()` auf dem übergebenen Argument.
:::

### Einfügen eines `Tabs` {#inserting-a-tab}

Zusätzlich zum Hinzufügen eines `Tabs` am Ende der bestehenden Tabs ist es auch möglich, einen neuen an einer bestimmten Position zu erstellen. Dazu gibt es mehrere überladene Versionen von `insertTab()`. 

1. **`insertTab(int index, String text)`** - Fügt einen `Tab` in das `TabbedPane` an der angegebenen Position mit dem angegebenen `String` als Text des `Tabs` ein.
2. **`insertTab(int index, Tab tab)`** - Fügt den als Parameter bereitgestellten `Tab` an der angegebenen Position in das `TabbedPane` ein.
3. **`insertTab(int index, String text, Component component)`** - Fügt einen `Tab` mit dem angegebenen `String` als Text des `Tabs` ein, und die bereitgestellte `Komponente` wird im Inhaltsbereich des `TabbedPane` angezeigt.
4. **`insertTab(int index, Tab tab, Component component)`** - Fügt den bereitgestellten `Tab` ein und zeigt die bereitgestellte `Komponente` im Inhaltsbereich des `TabbedPane` an.

### Entfernen eines `Tabs` {#removing-a-tab}

Um einen einzelnen `Tab` aus dem `TabbedPane` zu entfernen, verwenden Sie eine der folgenden Methoden:

1. **`removeTab(Tab tab)`** - Entfernt einen `Tab` aus dem `TabbedPane`, indem die zu entfernende Tab-Instanz übergeben wird.
2. **`removeTab(int index)`** - Entfernt einen `Tab` aus dem `TabbedPane`, indem der Index des zu entfernenden `Tabs` angegeben wird.

Neben den beiden oben genannten Methoden zum Entfernen eines einzelnen `Tabs` verwenden Sie die Methode **`removeAllTabs()`**, um das `TabbedPane` von allen Tabs zu bereinigen.

:::info
Die Methoden `remove()` und `removeAll()` entfernen keine Tabs innerhalb der Komponente.
:::

### Tab-/Komponenten-Zuordnung {#tabcomponent-association}

Um die `Komponente` zu ändern, die für einen bestimmten `Tab` angezeigt wird, rufen Sie die Methode `setComponentFor()` auf und übergeben entweder die Instanz des `Tabs` oder den Index dieses Tabs im `TabbedPane`.

:::info
Wenn diese Methode für einen `Tab` verwendet wird, der bereits mit einer `Komponente` verknüpft ist, wird die zuvor verknüpfte `Komponente` zerstört.
:::

## Konfiguration und Layout {#configuration-and-layout}

Die Klasse `TabbedPane` hat zwei Bestandteile: einen `Tab`, der an einem bestimmten Ort angezeigt wird, und eine Komponente, die angezeigt werden soll. Dies kann eine einzelne Komponente oder eine [`Composite`](../building-ui/composite-components) -Komponente sein, die die Anzeige komplexerer Komponenten im Inhaltsbereich eines Tabs ermöglicht.

### Wischen {#swiping}

Das `TabbedPane` unterstützt die Navigation durch die verschiedenen Tabs über Wischen. Dies ist ideal für eine mobile Anwendung, kann jedoch auch über eine integrierte Methode konfiguriert werden, um Mauswischen zu unterstützen. Sowohl Wischen als auch Mauswischen sind standardmäßig deaktiviert, können jedoch mithilfe der Methoden `setSwipable(boolean)` und `setSwipableWithMouse(boolean)` aktiviert werden.

### Tab-Platzierung {#tab-placement}

Die `Tabs` innerhalb eines `TabbedPane` können je nach Präferenz der Anwendungsentwickler an verschiedenen Positionen innerhalb der Komponente platziert werden. Die möglichen Optionen werden mit dem bereitgestellten Enum eingestellt, das die Werte `TOP`, `BOTTOM`, `LEFT`, `RIGHT` oder `HIDDEN` hat. Die Standardeinstellung ist `TOP`.

<ComponentDemo 
path='/webforj/tabbedpaneplacement?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/tabbedpane/TabbedPanePlacementView.java'
height="400px"
/>

### Ausrichtung {#alignment}

Neben der Änderung der Platzierung der `Tab`-Elemente innerhalb des `TabbedPane` ist es auch möglich, zu konfigurieren, wie die Tabs innerhalb der Komponente ausgerichtet werden. Standardmäßig ist die Einstellung `AUTO` aktiv, wodurch die Platzierung der Tabs ihre Ausrichtung bestimmt.

Die anderen Optionen sind `START`, `END`, `CENTER` und `STRETCH`. Die ersten drei beschreiben die Position relativ zur Komponente, wobei `STRETCH` die Tabs den verfügbaren Platz ausfüllen lässt.

<ComponentDemo 
path='/webforj/tabbedpanealignment?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/tabbedpane/TabbedPaneAlignmentView.java'
height="250px"
/>

### Rahmen und Aktivitätsindikator {#border-and-activity-indicator}

Das `TabbedPane` wird standardmäßig mit einem Rahmen für die darin enthaltenen Tabs angezeigt, der je nach den festgelegten `Placement`-Einstellungen platziert wird. Dieser Rahmen hilft dabei, den Raum zu visualisieren, den die verschiedenen Tabs innerhalb des Panes einnehmen. 

Wenn ein `Tab` angeklickt wird, wird standardmäßig ein Aktivitätsindikator in der Nähe dieses `Tabs` angezeigt, um hervorzuheben, welcher der derzeit ausgewählte `Tab` ist.

Beide Optionen können von einem Entwickler angepasst werden, indem die booleschen Werte mit den entsprechenden Setter-Methoden geändert werden. Um zu ändern, ob der Rahmen angezeigt wird, kann die Methode `setBorderless(boolean)` verwendet werden, wobei `true` den Rahmen ausblendet und `false`, der Standardwert, den Rahmen anzeigt.

:::info
Dieser Rahmen gilt nicht für die Gesamtheit der `TabbedPane`-Komponente und dient lediglich als Trenner zwischen den Tabs und dem Inhalt der Komponente.
:::

Um die Sichtbarkeit des aktiven Indikators festzulegen, kann die Methode `setHideActiveIndicator(boolean)` verwendet werden. Wenn `true` an diese Methode übergeben wird, wird der aktive Indikator unter einem aktiven `Tab` ausgeblendet, während `false`, der Standardwert, den Indikator angezeigt lässt.

<ComponentDemo 
path='/webforj/tabbedpaneborder?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/tabbedpane/TabbedPaneBorderView.java'
height="300px"
/>

### Aktivierungsmodi {#activation-modes}

Für eine präzisere Steuerung, wie das `TabbedPane` reagieren soll, wenn es mit der Tastatur navigiert wird, kann der `Aktivierungsmodus` festgelegt werden, um zu spezifizieren, wie die Komponente sich verhalten soll.

- **`Auto`**: Bei Einstellung auf Auto zeigt die Navigation zwischen den Tabs mit den Pfeiltasten sofort die entsprechende Tab-Komponente an.

- **`Manual`**: Bei Einstellung auf Manuel erhält der Tab den Fokus, wird jedoch erst angezeigt, wenn der Benutzer die Leertaste oder die Eingabetaste drückt.

<ComponentDemo 
path='/webforj/tabbedpaneactivation?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/tabbedpane/TabbedPaneActivationView.java'
height="250px"
/>

### Entfernungsoptionen {#removal-options}

Einzelne `Tab`-Elemente können closable gesetzt werden. Schließbare Tabs haben eine Schaltfläche zum Schließen, die dem Tab hinzugefügt wird und ein Schließevent auslöst, wenn sie angeklickt wird. Das `TabbedPane` bestimmt, wie dieses Verhalten gehandhabt wird.

- **`Manual`**: Standardmäßig ist das Entfernen auf `MANUAL` eingestellt, was bedeutet, dass das Ereignis ausgelöst wird, aber es liegt am Entwickler, dieses Ereignis auf die von ihm gewählte Weise zu behandeln.

- **`Auto`**: Alternativ kann `AUTO` verwendet werden, welches das Ereignis auslöst und auch den `Tab` automatisch aus der Komponente entfernt, wodurch es nicht mehr erforderlich ist, dass der Entwickler dieses Verhalten manuell implementiert. 

### Segmentsteuerung <DocChip chip='since' label='26.00' /> {#segment-control}

Das `TabbedPane` kann als Segmentsteuerung gerendert werden, indem die `segment`-Eigenschaft mit `setSegment(true)` aktiviert wird. In diesem Modus werden die Tabs mit einem seitlich gleitenden Pillenindikator angezeigt, der die aktive Auswahl hervorhebt und eine kompakte Alternative zur standardmäßigen Tab-Oberfläche bietet. 

<ComponentDemo 
path='/webforj/tabbedpanesegment?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/tabbedpane/TabbedPaneSegmentView.java'
height="250px"
/>

## Styling {#styling}

### Größe und Thema {#expanse-and-theme}

Das `TabbedPane` bietet integrierte `Größe`- und `Thema`-Optionen, die ähnlich wie bei anderen webforJ-Komponenten verwendet werden können. Diese können verwendet werden, um schnell Stile hinzuzufügen, die verschiedene Bedeutungen für den Endbenutzer vermitteln, ohne die Komponente mit CSS zu gestalten.

<ComponentDemo 
path='/webforj/tabbedpaneexpansetheme?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/tabbedpane/TabbedPaneExpanseThemeView.java'
height="250px"
/>

<TableBuilder name={['Tab', 'TabbedPane']} />

## Best Practices {#best-practices}

Die folgenden Praktiken werden empfohlen, um das `TabbedPane` innerhalb von Anwendungen zu nutzen:

- **Logische Gruppierung**: Nutzen Sie Tabs, um verwandte Inhalte logisch zu gruppieren
    >- Jeder Tab sollte eine distinct Kategorie oder Funktionalität in Ihrer Anwendung darstellen
    >- Gruppieren Sie ähnliche oder logische Tabs nahe beieinander

- **Limitierte Tabs**: Vermeiden Sie, Benutzer mit zu vielen Tabs zu überfordern. Erwägen Sie, wo möglich, eine hierarchische Struktur oder andere Navigationsmuster für eine saubere Benutzeroberfläche zu verwenden

- **Klare Beschriftungen**: Beschriften Sie Ihre Tabs klar zur intuitiven Nutzung
    >- Stellen Sie klare und prägnante Beschriftungen für jeden Tab bereit
    >- Beschriftungen sollten den Inhalt oder Zweck widergeben, um es den Benutzern zu erleichtern, zu verstehen
    >- Nutzen Sie Symbole und unterschiedliche Farben, wo anwendbar

- **Tastaturnavigation** Nutzen Sie die Tastaturnavigatefunktionalität von webforJ's `TabbedPane`, um die Interaktion mit dem `TabbedPane` nahtloser und intuitiver für den Endbenutzer zu gestalten

- **Standard-Tab**: Wenn der Standard-Tab nicht am Anfang des `TabbedPane` platziert ist, sollten Sie diesen Tab als standardmäßig festlegen für essentielle oder häufig verwendete Informationen.
