---
title: TabbedPane
slug: tabbedpane
sidebar_position: 125
_i18n_hash: d1522c6bd692420a02df494fa0509a23
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-tabbed-pane" />
<DocChip chip='since' label='23.06' />
<JavadocLink type="foundation" location="com/webforj/component/tabbedpane/TabbedPane" top='true'/>

Mehrere Inhaltsabschnitte können unter einem einzelnen `TabbedPane` organisiert werden, wobei jeder Abschnitt an einen klickbaren `Tab` gebunden ist. Nur ein Abschnitt ist gleichzeitig sichtbar, und Tabs können Text, Icons oder beides anzeigen, um den Benutzern die Navigation zwischen ihnen zu erleichtern.

<!-- INTRO_END -->

## Usos {#usages}

Die `TabbedPane`-Klasse bietet Entwicklern ein leistungsstarkes Werkzeug zum Organisieren und Präsentieren mehrerer Tabs oder Abschnitte innerhalb einer Benutzeroberfläche. Hier sind einige typische Szenarien, in denen Sie ein `TabbedPane` in Ihrer Anwendung verwenden könnten:

1. **Dokumentenbetrachter**: Implementierung eines Dokumentenbetrachters, bei dem jeder Tab ein anderes Dokument oder eine Datei darstellt. Benutzer können einfach zwischen geöffneten Dokumenten wechseln, um effizient Multitasking zu betreiben.

2. **Datenmanagement:**: Verwenden Sie ein `TabbedPane`, um Datenmanagementaufgaben zu organisieren, z. B.:
    >- Verschiedene Datensätze, die in einer Anwendung angezeigt werden sollen
    >- Verschiedene Benutzerprofile können in separaten Tabs angezeigt werden
    >- Verschiedene Profile in einem Benutzerverwaltungssystem

3. **Modulwahl**: Ein `TabbedPane` kann verschiedene Module oder Abschnitte repräsentieren. Jeder Tab kann die Funktionalitäten eines bestimmten Moduls kapseln und es den Benutzern ermöglichen, sich jeweils auf einen Aspekt der Anwendung zu konzentrieren.

4. **Aufgabenmanagement**: Anwendungen zur Aufgabenverwaltung können ein `TabbedPane` verwenden, um verschiedene Projekte oder Aufgaben darzustellen. Jeder Tab könnte einem bestimmten Projekt entsprechen, sodass Benutzer Aufgaben separat verwalten und verfolgen können.

5. **Programmnavigation**: Innerhalb einer Anwendung, die verschiedene Programme ausführen muss, könnte ein `TabbedPane`:
    >- Als Seitenleiste dienen, die es ermöglicht, verschiedene Anwendungen oder Programme innerhalb einer einzigen Anwendung auszuführen, wie im [`AppLayout`](./app-layout.md)-Template dargestellt
    >- Eine obere Leiste erstellen, die einen ähnlichen Zweck erfüllen oder Unteranwendungen innerhalb einer bereits ausgewählten Anwendung darstellen kann.
  
## Tabs {#tabs}

Tabs sind UI-Elemente, die zu Tabbed-Panes hinzugefügt werden können, um zwischen verschiedenen Inhaltsansichten zu organisieren und zu wechseln.

:::important
Tabs sind nicht als eigenständige Komponenten gedacht. Sie sollen in Verbindung mit Tabbed-Panes verwendet werden. Diese Klasse ist keine `Component` und sollte nicht als solche verwendet werden.
:::

### Eigenschaften {#properties}

Tabs bestehen aus den folgenden Eigenschaften, die verwendet werden, wenn sie in einem `TabbedPane` hinzugefügt werden. Diese Eigenschaften haben Getter und Setter, um die Anpassung innerhalb eines `TabbedPane` zu erleichtern.

1. **Key(`Object`)**: Stellt den eindeutigen Bezeichner für den `Tab` dar.

2. **Text(`String`)**: Der Text, der als Titel für den `Tab` innerhalb des `TabbedPane` angezeigt wird. Dies wird auch als Titel über die Methoden `getTitle()` und `setTitle(String title)` bezeichnet.

3. **Tooltip(`String`)**: Der Tooltip-Text, der mit dem `Tab` verknüpft ist und angezeigt wird, wenn der Cursor über den `Tab` schwebt.

4. **Enabled(`boolean`)**: Gibt an, ob der `Tab` derzeit aktiviert ist oder nicht. Kann mit der Methode `setEnabled(boolean enabled)` geändert werden.

5. **Closeable(`boolean`)**: Gibt an, ob der `Tab` geschlossen werden kann. Kann mit der Methode `setCloseable(boolean enabled)` geändert werden. Dies fügt dem `Tab` eine Schaltfläche zum Schließen hinzu, die vom Benutzer angeklickt werden kann und ein Entfernungsevent auslöst. Die `TabbedPane`-Komponente legt fest, wie die Entfernung behandelt wird.

6. **Slot(`Component`)**: 
    Slots bieten flexible Optionen zur Verbesserung der Funktionalität eines `Tab`. Sie können Icons, Beschriftungen, Lade-Spinners, Löschen/Zurücksetzen-Funktionen, Avatare/Profilbilder und andere nützliche Komponenten innerhalb eines `Tab` hinzufügen, um die beabsichtigte Bedeutung für die Benutzer weiter zu verdeutlichen.
    Sie können eine Komponente während der Konstruktion in den `prefix`-Slot eines `Tab` hinzufügen. Alternativ können Sie die Methoden `setPrefixComponent()` und `setSuffixComponent()` verwenden, um verschiedene Komponenten vor und nach der angezeigten Option innerhalb eines `Tab` einzufügen.

        ```java
        TabbedPane pane = new TabbedPane();
        pane.addTab(new Tab("Dokumente", TablerIcon.create("files")));
        ```

## `Tab`-Manipulation {#tab-manipulation}

Es gibt verschiedene Methoden, die Entwicklern erlauben, `Tab`-Elemente innerhalb des `TabbedPane` hinzuzufügen, einzufügen, zu entfernen und verschiedene Eigenschaften zu manipulieren.

### Einen `Tab` hinzufügen {#adding-a-tab}

Die Methoden `addTab()` und `add()` existieren in verschiedenen überladenen Varianten, um Entwicklern Flexibilität beim Hinzufügen neuer Tabs zum `TabbedPane` zu ermöglichen. Das Hinzufügen eines `Tab` erfolgt nach allen zuvor vorhandenen Tabs.

1. **`addTab(String text)`** - Fügt einen `Tab` zum `TabbedPane` mit dem angegebenen `String` als Text des `Tab` hinzu.
2. **`addTab(Tab tab)`** - Fügt den `Tab` hinzu, der als Parameter an das `TabbedPane` übergeben wurde.
3. **`addTab(String text, Component component)`** - Fügt einen `Tab` mit dem angegebenen `String` als Text des `Tab`, und dem bereitgestellten `Component` angezeigt im Inhaltsbereich des `TabbedPane` hinzu.
4. **`addTab(Tab tab, Component component)`** - Fügt den bereitgestellten `Tab` hinzu und zeigt den bereitgestellten `Component` im Inhaltsbereich des `TabbedPane` an.
5. **`add(Component... component)`** - Fügt ein oder mehrere `Component`-Instanzen zum `TabbedPane` hinzu, indem für jede ein separater `Tab` erstellt wird, wobei der Text auf den Namen des `Component` gesetzt wird.

:::info
Die `add(Component... component)`-Methode bestimmt den Namen des übergebenen `Component`, indem die Methode `component.getName()` auf das übergebene Argument aufgerufen wird.
:::

### Einen `Tab` einfügen {#inserting-a-tab}

Zusätzlich zum Hinzufügen eines `Tabs` am Ende der vorhandenen Tabs ist es auch möglich, einen neuen an einer bestimmten Position zu erstellen. Dazu gibt es mehrere überladene Versionen von `insertTab()`.

1. **`insertTab(int index, String text)`** - Fügt einen `Tab` an die angegebene Stelle im `TabbedPane` mit dem angegebenen `String` als Text des `Tab` ein.
2. **`insertTab(int index, Tab tab)`** - Fügt den als Parameter bereitgestellten `Tab` an der angegebenen Stelle in das `TabbedPane` ein.
3. **`insertTab(int index, String text, Component component)`** - Fügt einen `Tab` mit dem angegebenen `String` als Text des `Tab` hinzu und den bereitgestellten `Component`, der im Inhaltsbereich des `TabbedPane` angezeigt wird.
4. **`insertTab(int index, Tab tab, Component component)`** - Fügt den bereitgestellten `Tab` hinzu und zeigt den bereitgestellten `Component` im Inhaltsbereich des `TabbedPane` an.

### Einen `Tab` entfernen {#removing-a-tab}

Um einen einzelnen `Tab` aus dem `TabbedPane` zu entfernen, verwenden Sie eine der folgenden Methoden:

1. **`removeTab(Tab tab)`** - Entfernt einen `Tab` aus dem `TabbedPane`, indem die zu entfernende Tab-Instanz übergeben wird.
2. **`removeTab(int index)`** - Entfernt einen `Tab` aus dem `TabbedPane`, indem der Index des zu entfernenden `Tab` angegeben wird.

Neben den beiden oben genannten Methoden zur Entfernung eines einzelnen `Tabs` verwenden Sie die **`removeAllTabs()`**-Methode, um das `TabbedPane` von allen Tabs zu leeren.

:::info
Die Methoden `remove()` und `removeAll()` entfernen keine Tabs innerhalb des Komponenten.
:::

### Tab/Komponenten-Zuordnung {#tabcomponent-association}

Um die im `Tab` angezeigte `Component` zu ändern, rufen Sie die Methode `setComponentFor()` auf und übergeben entweder die Instanz des `Tab` oder den Index dieses Tabs innerhalb des `TabbedPane`.

:::info
Wenn diese Methode für einen `Tab` verwendet wird, der bereits mit einer `Component` verknüpft ist, wird die zuvor verknüpfte `Component` zerstört.
:::

## Konfiguration und Layout {#configuration-and-layout}

Die `TabbedPane`-Klasse besteht aus zwei Bestandteilen: einem `Tab`, der an einem bestimmten Ort angezeigt wird, und einer anzuzeigenden Komponente. Dies kann eine einzelne Komponente oder eine [`Composite`](../building-ui/composite-components)-Komponente sein, die die Anzeige komplexerer Komponenten im Inhaltsbereich eines Tabs ermöglicht.

### Wischen {#swiping}

Das `TabbedPane` unterstützt die Navigation durch die verschiedenen Tabs durch Wischen. Dies ist ideal für eine mobile Anwendung, kann aber auch über eine integrierte Methode konfiguriert werden, um das Wischen mit der Maus zu unterstützen. Sowohl das Wischen als auch das Wischen mit der Maus sind standardmäßig deaktiviert, können jedoch mit den Methoden `setSwipable(boolean)` und `setSwipableWithMouse(boolean)` aktiviert werden.

### Tab-Platzierung {#tab-placement}

Die `Tabs` innerhalb eines `TabbedPane` können an verschiedenen Positionen innerhalb des Komponenten basierend auf den Vorlieben der Anwendungsentwickler platziert werden. Die bereitgestellten Optionen werden mithilfe des bereitgestellten Enums festgelegt, das die Werte `TOP`, `BOTTOM`, `LEFT`, `RIGHT` oder `HIDDEN` hat. Die Standardeinstellung ist `TOP`.

<ComponentDemo 
path='/webforj/tabbedpaneplacement?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/tabbedpane/TabbedPanePlacementView.java'
height="400px"
/>

### Ausrichtung {#alignment}

Zusätzlich zur Änderung der Platzierung der `Tab`-Elemente innerhalb des `TabbedPane` ist es auch möglich, zu konfigurieren, wie die Tabs innerhalb des Komponenten ausgerichtet werden. Standardmäßig ist die Einstellung `AUTO` aktiv, wodurch die Platzierung der Tabs ihre Ausrichtung diktiert.

Die anderen Optionen sind `START`, `END`, `CENTER` und `STRETCH`. Die ersten drei beschreiben die Position relativ zum Komponenten, wobei `STRETCH` die Tabs den verfügbaren Platz ausfüllen lässt.

<ComponentDemo 
path='/webforj/tabbedpanealignment?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/tabbedpane/TabbedPaneAlignmentView.java'
height="250px"
/>

### Rahmen und Aktivitätsanzeige {#border-and-activity-indicator}

Das `TabbedPane` hat standardmäßig einen Rahmen, der für die darin enthaltenen Tabs angezeigt wird, und zwar abhängig von der festgelegten `Platzierung`. Dieser Rahmen hilft dabei, den Raum zu visualisieren, den die verschiedenen Tabs innerhalb des Fensters einnehmen.

Wenn ein `Tab` angeklickt wird, wird standardmäßig ein Aktivitätsindikator in der Nähe dieses `Tab` angezeigt, um hervorzuheben, welcher `Tab` derzeit ausgewählt ist.

Beide Optionen können von einem Entwickler angepasst werden, indem die booleschen Werte mit den entsprechenden Setter-Methoden geändert werden. Um zu ändern, ob der Rahmen angezeigt wird oder nicht, kann die Methode `setBorderless(boolean)` verwendet werden, wobei `true` den Rahmen verbirgt und `false`, der Standardwert, den Rahmen anzeigt.

:::info
Dieser Rahmen gilt nicht für das gesamte `TabbedPane`-Komponenten und dient lediglich als Trennung zwischen den Tabs und dem Inhalt des Komponenten.
:::

Um die Sichtbarkeit des aktiven Indikators festzulegen, kann die Methode `setHideActiveIndicator(boolean)` verwendet werden. Das Übergeben von `true` an diese Methode blendet den aktiven Indikator beneath an einem aktiven `Tab` aus, während `false`, der Standardwert, den Indikator sichtbar lässt.

<ComponentDemo 
path='/webforj/tabbedpaneborder?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/tabbedpane/TabbedPaneBorderView.java'
height="300px"
/>

### Aktivierungsmodi {#activation-modes}

Für eine feinere Kontrolle darüber, wie das `TabbedPane` bei der Navigation über die Tastatur funktioniert, kann der `Aktivierungs`-Modus eingestellt werden, um zu spezifizieren, wie sich die Komponente verhalten soll.

- **`Auto`**: Bei der Einstellung auf Auto zeigt das Navigieren zwischen den Tabs mit den Pfeiltasten sofort die entsprechende Tab-Komponente an.

- **`Manuell`**: Bei der Einstellung auf manuell erhält der Tab den Fokus, wird jedoch erst angezeigt, wenn der Benutzer auf die Leertaste oder die Eingabetaste drückt.

<ComponentDemo 
path='/webforj/tabbedpaneactivation?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/tabbedpane/TabbedPaneActivationView.java'
height="250px"
/>

### Entfernungsoptionen {#removal-options}

E einzelne `Tab`-Elemente können so eingestellt werden, dass sie schließbar sind. Schließbare Tabs haben eine Schaltfläche zum Schließen, die dem Tab hinzugefügt wird, die ein Schließereignis auslöst, wenn sie angeklickt wird. Das `TabbedPane` legt fest, wie dieses Verhalten behandelt wird.

- **`Manuell`**: Standardmäßig ist die Entfernung auf `MANUAL` eingestellt, was bedeutet, dass das Ereignis ausgelöst wird, es liegt jedoch am Entwickler, dieses Ereignis auf die von ihm gewählte Weise zu behandeln.

- **`Automatisch`**: Alternativ kann `AUTO` verwendet werden, wodurch das Ereignis ausgelöst wird und auch der `Tab` für den Entwickler aus der Komponente entfernt wird, wodurch der Entwickler nicht manuell diesen Vorgang implementieren muss. 

## Styling {#styling}

### Ausdehnung und Thema {#expanse-and-theme}

Das `TabbedPane` verfügt über integrierte `Expanse`- und `Theme`-Optionen, die ähnlich wie andere webforJ-Komponenten verwendet werden können. Diese können verwendet werden, um schnell Stile hinzuzufügen, die verschiedene Bedeutungen für den Endbenutzer vermitteln, ohne dass CSS zur Stilgestaltung der Komponente verwendet werden muss.

<ComponentDemo 
path='/webforj/tabbedpaneexpansetheme?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/tabbedpane/TabbedPaneExpanseThemeView.java'
height="250px"
/>

<TableBuilder name="TabbedPane" />

## Best Practices {#best-practices}

Die folgenden Praktiken werden empfohlen, um das `TabbedPane` innerhalb von Anwendungen zu nutzen:

- **Logische Gruppierung**: Verwenden Sie Tabs, um verwandte Inhalte logisch zu gruppieren
    >- Jeder Tab sollte eine eindeutige Kategorie oder Funktionalität innerhalb Ihrer Anwendung darstellen
    >- Gruppieren Sie ähnliche oder logische Tabs in der Nähe zueinander

- **Begrenzte Tabs**: Vermeiden Sie es, die Benutzer mit zu vielen Tabs zu überwältigen. Ziehen Sie in Betracht, eine hierarchische Struktur oder andere Navigationsmuster zu verwenden, wenn dies für eine saubere Benutzeroberfläche geeignet ist.

- **Klare Beschriftungen**: Kennzeichnen Sie Ihre Tabs klar für eine intuitive Nutzung
    >- Geben Sie klare und prägnante Beschriftungen für jeden Tab an
    >- Beschriftungen sollten den Inhalt oder Zweck widerspiegeln und es den Benutzern erleichtern, sie zu verstehen
    >- Verwenden Sie Icons und distintive Farben, wo dies anwendbar ist

- **Tastaturnavigation**: Nutzen Sie die Tastaturnavigation des `TabbedPane` von webforJ, um die Interaktion mit dem `TabbedPane` für den Endbenutzer nahtloser und intuitiver zu gestalten

- **Standard-Tab**: Wenn der Standard-Tab nicht am Anfang des `TabbedPane` platziert ist, erwägen Sie, diesen Tab als Standard für wesentliche oder häufig verwendete Informationen festzulegen.
