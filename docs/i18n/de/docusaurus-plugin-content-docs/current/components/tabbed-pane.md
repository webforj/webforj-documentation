---
title: TabbedPane
slug: tabbedpane
sidebar_position: 125
sidebar_class_name: new-content
description: >-
  Organize content into switchable Tab sections with the TabbedPane component,
  supporting icons, keys, and customizable tab properties.
_i18n_hash: 9a348db865b5ea1688eb09c4f6f75a57
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-tabbed-pane" />
<DocChip chip='since' label='23.06' />
<JavadocLink type="foundation" location="com/webforj/component/tabbedpane/TabbedPane" top='true'/>

In mehreren Abschnitten kann der Inhalt unter einem einzelnen `TabbedPane` organisiert werden, wobei jeder Abschnitt an einen klickbaren `Tab` gebunden ist. Nur ein Abschnitt ist gleichzeitig sichtbar, und die Tabs können Text, Symbole oder beides anzeigen, um den Nutzern die Navigation zu erleichtern.

<!-- INTRO_END -->

## Usos {#usages}

Die Klasse `TabbedPane` bietet Entwicklern ein leistungsstarkes Werkzeug zur Organisation und Präsentation mehrerer Tabs oder Abschnitte innerhalb einer Benutzeroberfläche. Hier sind einige typische Szenarien, in denen Sie ein `TabbedPane` in Ihrer Anwendung nutzen könnten:

1. **Dokumentenbetrachter**: Implementierung eines Dokumentenbetrachters, bei dem jeder Tab ein anderes Dokument oder eine Datei darstellt. Benutzer können einfach zwischen geöffneten Dokumenten wechseln, um effizient zu multitasken.

2. **Datenmanagement:**: Nutzen Sie ein `TabbedPane`, um Aufgaben im Datenmanagement zu organisieren, beispielsweise:
    >- Verschiedene Datensätze, die in einer Anwendung angezeigt werden
    >- Verschiedene Benutzerprofile können innerhalb separater Tabs angezeigt werden
    >- Verschiedene Profile in einem Benutzerverwaltungssystem

3. **Modulwahl**: Ein `TabbedPane` kann verschiedene Module oder Abschnitte darstellen. Jeder Tab kann die Funktionalitäten eines bestimmten Moduls kapseln, wodurch die Nutzer sich auf einen Aspekt der Anwendung konzentrieren können.

4. **Aufgabenmanagement**: Aufgabeverwaltungsanwendungen können ein `TabbedPane` verwenden, um verschiedene Projekte oder Aufgaben darzustellen. Jeder Tab könnte einem bestimmten Projekt entsprechen, wodurch die Benutzer Aufgaben separat verwalten und verfolgen können.

5. **Programmnavigation**: Innerhalb einer Anwendung, die verschiedene Programme ausführen muss, könnte ein `TabbedPane`:
    >- Als Seitenleiste dienen, die es ermöglicht, verschiedene Anwendungen oder Programme innerhalb einer einzigen Anwendung auszuführen, wie im [`AppLayout`](./app-layout.md) Template gezeigt
    >- Eine obere Leiste erstellen, die einen ähnlichen Zweck erfüllt oder Unteranwendungen innerhalb einer bereits ausgewählten Anwendung darstellt.

## Tabs {#tabs}

Tabs sind UI-Elemente, die zu tabbed panes hinzugefügt werden können, um zwischen verschiedenen Inhaltsansichten zu organisieren und zu wechseln.

:::important
Tabs sind nicht dafür gedacht, als eigenständige Komponenten verwendet zu werden. Sie sollen zusammen mit tabbed panes verwendet werden. Diese Klasse ist keine `Component` und sollte nicht als solche verwendet werden.
:::

### Eigenschaften {#properties}

Tabs bestehen aus den folgenden Eigenschaften, die beim Hinzufügen in ein `TabbedPane` verwendet werden. Diese Eigenschaften haben Getter und Setter, um die Anpassung innerhalb eines `TabbedPane` zu erleichtern.

1. **Key(`Object`)**: Stellt den eindeutigen Bezeichner für den `Tab` dar.

2. **Text(`String`)**: Der Text, der als Titel für den `Tab` innerhalb des `TabbedPane` angezeigt wird. Dieser wird auch als Titel über die Methoden `getTitle()` und `setTitle(String title)` bezeichnet.

3. **Tooltip(`String`)**: Der Tooltip-Text, der mit dem `Tab` verbunden ist und angezeigt wird, wenn der Cursor über den `Tab` fährt.

4. **Enabled(`boolean`)**: Gibt an, ob der `Tab` derzeit aktiviert ist oder nicht. Kann mit der Methode `setEnabled(boolean enabled)` geändert werden.

5. **Closeable(`boolean`)**: Gibt an, ob der `Tab` geschlossen werden kann. Kann mit der Methode `setCloseable(boolean enabled)` geändert werden. Dies fügt dem `Tab` eine Schaltfläche zum Schließen hinzu, die vom Benutzer angeklickt werden kann und ein Entfernungsevent auslöst. Die `TabbedPane`-Komponente bestimmt, wie mit der Entfernung umgegangen wird.

6. **Slot(`Component`)**: 
    Slots bieten flexible Optionen zur Verbesserung der Funktionalität eines `Tabs`. Sie können Symbole, Beschriftungen, Ladeanimationen, Rücksetzen-Funktionen, Avatar-/Profilbilder und andere nützliche Komponenten innerhalb eines `Tabs` haben, um die beabsichtigte Bedeutung für die Nutzer weiter zu verdeutlichen. 
    Sie können während der Konstruktion eine Komponente im `prefix` Slot eines `Tabs` hinzufügen. Alternativ können Sie die Methoden `setPrefixComponent()` und `setSuffixComponent()` verwenden, um verschiedene Komponenten vor und nach der angezeigten Option innerhalb eines `Tabs` einzufügen.

        ```java
        TabbedPane pane = new TabbedPane();
        pane.addTab(new Tab("Dokumente", TablerIcon.create("files")));
        ```

## `Tab`-Manipulation {#tab-manipulation}

Verschiedene Methoden ermöglichen es Entwicklern, Tabs hinzuzufügen, einzufügen, zu entfernen und verschiedene Eigenschaften von `Tab`-Elementen innerhalb des `TabbedPane` zu manipulieren.

### Einen `Tab` hinzufügen {#adding-a-tab}

Die Methoden `addTab()` und `add()` existieren in verschiedenen überladenen Kapazitäten, um Entwicklern Flexibilität beim Hinzufügen neuer Tabs zum `TabbedPane` zu ermöglichen. Das Hinzufügen eines `Tabs` platziert ihn nach allen zuvor vorhandenen Tabs.

1. **`addTab(String text)`** - Fügt einen `Tab` zum `TabbedPane` mit dem angegebenen `String` als Text des `Tabs` hinzu.
2. **`addTab(Tab tab)`** - Fügt den als Parameter angegebenen `Tab` zum `TabbedPane` hinzu.
3. **`addTab(String text, Component component)`** - Fügt einen `Tab` mit dem angegebenen `String` als Text des `Tabs` hinzu und zeigt die angegebene `Component` im Inhaltsbereich des `TabbedPane` an.
4. **`addTab(Tab tab, Component component)`** - Fügt den angegebenen `Tab` hinzu und zeigt die angegebene `Component` im Inhaltsbereich des `TabbedPane` an.
5. **`add(Component... component)`** - Fügt eine oder mehrere `Component`-Instanzen zum `TabbedPane` hinzu und erstellt für jede einen eigenen `Tab`, wobei der Text auf den Namen der `Component` gesetzt wird.

:::info
Die Methode `add(Component... component)` bestimmt den Namen der übergebenen `Component`, indem sie `component.getName()` auf dem übergebenen Argument aufruft.
:::

### Einen `Tab` einfügen {#inserting-a-tab}

Es ist auch möglich, einen neuen Tab an einer bestimmten Position zu erstellen, anstatt einen `Tab` am Ende der vorhandenen Tabs hinzuzufügen. Dazu stehen mehrere überladene Versionen von `insertTab()` zur Verfügung.

1. **`insertTab(int index, String text)`** - Fügt einen `Tab` in das `TabbedPane` an dem angegebenen Index mit dem angegebenen `String` als Text des `Tabs` ein.
2. **`insertTab(int index, Tab tab)`** - Fügt den als Parameter angegebenen `Tab` am angegebenen Index zum `TabbedPane` ein.
3. **`insertTab(int index, String text, Component component)`** - Fügt einen `Tab` mit dem angegebenen `String` als Text des `Tabs` ein und zeigt die angegebene `Component` im Inhaltsbereich des `TabbedPane` an.
4. **`insertTab(int index, Tab tab, Component component)`** - Fügt den angegebenen `Tab` ein und zeigt die angegebene `Component` im Inhaltsbereich des `TabbedPane` an.

### Einen `Tab` entfernen {#removing-a-tab}

Um einen einzelnen `Tab` aus dem `TabbedPane` zu entfernen, verwenden Sie eine der folgenden Methoden:

1. **`removeTab(Tab tab)`** - Entfernt einen `Tab` aus dem `TabbedPane`, indem die zu entfernende Tab-Instanz übergeben wird.
2. **`removeTab(int index)`** - Entfernt einen `Tab` aus dem `TabbedPane`, indem der Index des zu entfernenden `Tabs` angegeben wird.

Zusätzlich zu den beiden oben genannten Methoden für die Entfernung eines einzelnen `Tabs` verwenden Sie die Methode **`removeAllTabs()`**, um das `TabbedPane` von allen Tabs zu leeren.

:::info
Die Methoden `remove()` und `removeAll()` entfernen keine Tabs innerhalb der Komponente.
:::

### Tab/Komponenten-Zuordnung {#tabcomponent-association}

Um die anzuzeigende `Component` für einen bestimmten `Tab` zu ändern, rufen Sie die Methode `setComponentFor()` auf und übergeben entweder die Instanz des `Tabs` oder den Index dieses Tabs innerhalb des `TabbedPane`.

:::info
Wenn diese Methode bei einem `Tab` verwendet wird, der bereits mit einer `Component` assoziiert ist, wird die zuvor assoziierte `Component` zerstört.
:::

## Konfiguration und Layout {#configuration-and-layout}

Die Klasse `TabbedPane` hat zwei Bestandteilteile: einen `Tab`, der an einem bestimmten Ort angezeigt wird, und eine anzuzeigende Komponente. Dies kann eine einzelne Komponente oder eine [`Composite`](/docs/building-ui/composing-components)-Komponente sein, die die Anzeige komplexerer Komponenten im Inhaltsbereich eines Tabs ermöglicht.

### Wischen {#swiping}

Das `TabbedPane` unterstützt die Navigation durch die verschiedenen Tabs durch Wischen. Dies ist ideal für eine mobile Anwendung, kann aber auch über eine integrierte Methode konfiguriert werden, um das Wischen mit der Maus zu unterstützen. Sowohl Wischen als auch Mauswischen sind standardmäßig deaktiviert, können jedoch mit den Methoden `setSwipable(boolean)` und `setSwipableWithMouse(boolean)` aktiviert werden.

### Tab-Platzierung {#tab-placement}

Die `Tabs` innerhalb eines `TabbedPane` können je nach Vorliebe der Anwendungsentwickler in verschiedenen Positionen innerhalb der Komponente platziert werden. Die bereitgestellten Optionen werden mithilfe des bereitgestellten Enums festgelegt, das die Werte `TOP`, `BOTTOM`, `LEFT`, `RIGHT` oder `HIDDEN` hat. Die Standardeinstellung ist `TOP`.

<ComponentDemo
path='/webforj/tabbedpaneplacement'
files={['src/main/java/com/webforj/samples/views/tabbedpane/TabbedPanePlacementView.java']}
height='400px'
/>

### Ausrichtung {#alignment}

Zusätzlich zur Änderung der Position der `Tab`-Elemente innerhalb des `TabbedPane` ist es auch möglich, zu konfigurieren, wie die Tabs innerhalb der Komponente ausgerichtet werden. Standardmäßig ist die Einstellung `AUTO` aktiv, die es der Platzierung der Tabs ermöglicht, ihre Ausrichtung zu bestimmen.

Die anderen Optionen sind `START`, `END`, `CENTER` und `STRETCH`. Die ersten drei beschreiben die Position relativ zur Komponente, während `STRETCH` dafür sorgt, dass die Tabs den verfügbaren Platz ausfüllen.

<ComponentDemo
path='/webforj/tabbedpanealignment'
files={['src/main/java/com/webforj/samples/views/tabbedpane/TabbedPaneAlignmentView.java']}
height='250px'
/>

### Rahmen und Aktivitätsanzeige {#border-and-activity-indicator}

Das `TabbedPane` wird standardmäßig mit einem Rahmen angezeigt, der für die Tabs innerhalb davon platziert ist, abhängig davon, welche `Placement`-Einstellung vorgenommen wurde. Dieser Rahmen hilft, den Platz zu visualisieren, den die verschiedenen Tabs innerhalb des Paneels einnehmen.

Wenn ein `Tab` angeklickt wird, wird standardmäßig ein Aktivitätsindikator in der Nähe dieses `Tabs` angezeigt, um hervorzuheben, welcher `Tab` derzeit ausgewählt ist.

Beide Optionen können von einem Entwickler angepasst werden, indem die booleschen Werte mit den entsprechenden Setter-Methoden geändert werden. Um zu ändern, ob der Rahmen angezeigt wird oder nicht, kann die Methode `setBorderless(boolean)` verwendet werden, wobei `true` den Rahmen ausblendet und `false`, den Standardwert, den Rahmen anzeigt.

:::info
Dieser Rahmen gilt nicht für die gesamte `TabbedPane`-Komponente und dient lediglich als Separator zwischen den Tabs und dem Inhalt der Komponente.
:::

Um die Sichtbarkeit des aktiven Indikators festzulegen, kann die Methode `setHideActiveIndicator(boolean)` verwendet werden. Das Übergeben von `true` an diese Methode blendet den aktiven Indikator unter einem aktiven `Tab` aus, während `false`, der Standardwert, den Indikator anzeigt.

<ComponentDemo
path='/webforj/tabbedpaneborder'
files={['src/main/java/com/webforj/samples/views/tabbedpane/TabbedPaneBorderView.java']}
height='300px'
/>

### Aktivierungsmodi {#activation-modes}

Für eine feinere Steuerung darüber, wie das `TabbedPane` beim Navigieren durch die Tastatur reagiert, kann der Bewertungsmodus festgelegt werden, um zu bestimmen, wie sich die Komponente verhalten soll.

- **`Auto`**: Wenn auf Auto gesetzt, wird beim Navigieren mit den Pfeiltasten die entsprechende Tab-Komponente sofort angezeigt.

- **`Manual`**: Wenn auf manuell gesetzt, erhält der Tab den Fokus, zeigt jedoch erst, wenn der Benutzer die Leertaste oder die Eingabetaste drückt.

<ComponentDemo
path='/webforj/tabbedpaneactivation'
files={['src/main/java/com/webforj/samples/views/tabbedpane/TabbedPaneActivationView.java']}
height='250px'
/>

### Entfernen-Optionen {#removal-options}

Einzelne `Tab`-Elemente können so festgelegt werden, dass sie schließbar sind. Schließbare Tabs haben eine Schaltfläche zum Schließen, die dem Tab hinzugefügt wird, die ein Schließereignis auslöst, wenn sie angeklickt wird. Das `TabbedPane` bestimmt, wie dieses Verhalten gehandhabt wird.

- **`Manual`**: Standardmäßig ist das Entfernen auf `MANUAL` gesetzt, was bedeutet, dass das Ereignis ausgelöst wird, aber es dem Entwickler überlassen bleibt, dieses Ereignis auf jede beliebige Weise zu behandeln.

- **`Auto`**: Alternativ kann `AUTO` verwendet werden, was das Ereignis auslöst und auch den `Tab` von der Komponente entfernt, sodass der Entwickler dieses Verhalten nicht manuell implementieren muss.

### Segmentsteuerung <DocChip chip='since' label='26.00' /> {#segment-control}

Das `TabbedPane` kann als Segmentsteuerung gerendert werden, indem die `segment`-Eigenschaft mit `setSegment(true)` aktiviert wird. In diesem Modus werden die Tabs mit einem gleitenden Pillenindikator angezeigt, der die aktive Auswahl hervorhebt und eine kompakte Alternative zur standardmäßigen Tab-Oberfläche bietet.

<ComponentDemo
path='/webforj/tabbedpanesegment'
files={['src/main/java/com/webforj/samples/views/tabbedpane/TabbedPaneSegmentView.java']}
height='250px'
/>

## Styling {#styling}

### Expansions- und Themen {#expanse-and-theme}

Das `TabbedPane` verfügt über integrierte `Expanse`- und `Theme`-Optionen, die anderen webforJ-Komponenten ähnlich sind. Diese können verwendet werden, um schnell Styling hinzuzufügen, das verschiedene Bedeutungen für den Endbenutzer vermittelt, ohne die Komponente mit CSS gestalten zu müssen.

<ComponentDemo
path='/webforj/tabbedpaneexpansetheme'
files={['src/main/java/com/webforj/samples/views/tabbedpane/TabbedPaneExpanseThemeView.java']}
height='250px'
/>

<TableBuilder name={['Tab', 'TabbedPane']} />

## Best Practices {#best-practices}

Die folgenden Praktiken werden empfohlen, um das `TabbedPane` innerhalb von Anwendungen zu nutzen:

- **Logische Gruppierung**: Verwenden Sie Tabs, um verwandte Inhalte logisch zu gruppieren
    >- Jeder Tab sollte eine eindeutige Kategorie oder Funktionalität innerhalb Ihrer Anwendung darstellen
    >- Gruppieren Sie ähnliche oder logische Tabs nahe beieinander

- **Begrenzte Tabs**: Vermeiden Sie es, die Benutzer mit zu vielen Tabs zu überfordern. Ziehen Sie in Betracht, eine hierarchische Struktur oder andere Navigationsmuster zu verwenden, wo immer dies möglich ist, um eine saubere Benutzeroberfläche zu schaffen

- **Klare Beschriftungen**: Beschriften Sie Ihre Tabs klar für eine intuitive Nutzung
    >- Geben Sie klare und prägnante Beschriftungen für jeden Tab an
    >- Beschriftungen sollten den Inhalt oder Zweck widerspiegeln, was es den Benutzern erleichtert, sie zu verstehen
    >- Nutzen Sie Icons und unterschiedliche Farben, wo dies zutrifft

- **Tastaturnavigation**: Verwenden Sie die Unterstützung der Tastaturnavigation von webforJ für das `TabbedPane`, um die Interaktion mit dem `TabbedPane` nahtlos und intuitiv für den Endbenutzer zu gestalten

- **Standard-Tab**: Wenn der Standard-Tab nicht am Anfang des `TabbedPane` platziert ist, ziehen Sie in Betracht, diesen Tab als Standard für wesentliche oder häufig verwendete Informationen festzulegen.
