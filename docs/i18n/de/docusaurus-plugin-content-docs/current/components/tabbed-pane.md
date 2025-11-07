---
title: TabbedPane
slug: tabbedpane
sidebar_position: 125
_i18n_hash: ebf6bff550fd69aeb6ab8e4dfefd2323
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-tabbed-pane" />
<DocChip chip='since' label='23.06' />
<JavadocLink type="foundation" location="com/webforj/component/tabbedpane/TabbedPane" top='true'/>

Die `TabbedPane`-Klasse bietet eine kompakte und organisierte Möglichkeit, Inhalte anzuzeigen, die in mehrere Sektionen unterteilt sind, wobei jede mit einem `Tab` verknüpft ist. Benutzer können zwischen diesen Sektionen wechseln, indem sie auf die entsprechenden Registerkarten klicken, die oft mit Text und/oder Symbolen beschriftet sind. Diese Klasse vereinfacht die Erstellung vielschichtiger Benutzeroberflächen, bei denen unterschiedliche Inhalte oder Formulare zugänglich sein müssen, jedoch nicht gleichzeitig sichtbar sind.

## Verwendungen {#usages}

Die `TabbedPane`-Klasse bietet Entwicklern ein leistungsfähiges Werkzeug zur Organisation und Präsentation mehrerer Registerkarten oder Sektionen innerhalb einer Benutzeroberfläche. Hier sind einige typische Szenarien, in denen Sie eine `TabbedPane` in Ihrer Anwendung nutzen könnten:

1. **Dokumentenbetrachter**: Implementierung eines Dokumentenbetrachters, bei dem jede Registerkarte ein anderes Dokument oder eine andere Datei darstellt. Benutzer können problemlos zwischen geöffneten Dokumenten wechseln, um effizient multitasking zu betreiben.

2. **Datenmanagement**: Nutzen Sie eine `TabbedPane`, um Datenmanagementaufgaben zu organisieren, zum Beispiel:
    >- Unterschiedliche Datensätze, die in einer Anwendung angezeigt werden
    >- Verschiedene Benutzerprofile können in separaten Registerkarten angezeigt werden
    >- Unterschiedliche Profile in einem Benutzermanagementsystem

3. **Modulwahl**: Eine `TabbedPane` kann verschiedene Module oder Sektionen darstellen. Jede Registerkarte kann die Funktionen eines bestimmten Moduls kapseln, sodass Benutzer sich jeweils auf einen Aspekt der Anwendung konzentrieren können.

4. **Aufgabenverwaltung**: Aufgabenverwaltungsanwendungen können eine `TabbedPane` nutzen, um verschiedene Projekte oder Aufgaben darzustellen. Jede Registerkarte könnte einem bestimmten Projekt entsprechen, sodass Benutzer Aufgaben separat verwalten und verfolgen können.

5. **Programmnavigation**: In einer Anwendung, die verschiedene Programme ausführen muss, könnte eine `TabbedPane`:
    >- Als Seitenleisten dienen, die es ermöglichen, verschiedene Anwendungen oder Programme innerhalb einer einzigen Anwendung auszuführen, wie im [`AppLayout`](./app-layout.md)-Template gezeigt
    >- Eine obere Leiste erstellen, die einen ähnlichen Zweck erfüllen oder Unteranwendungen innerhalb einer bereits ausgewählten Anwendung darstellen kann. 

## Registerkarten {#tabs}

Registerkarten sind UI-Elemente, die zu Registerkarten-Panels hinzugefügt werden können, um zwischen verschiedenen Inhaltsansichten zu organisieren und zu wechseln.

:::important
Registerkarten sind nicht als eigenständige Komponenten gedacht. Sie sollen in Verbindung mit Registerkarten-Panels verwendet werden. Diese Klasse ist kein `Component` und sollte nicht als solche verwendet werden.
:::

### Eigenschaften {#properties}

Registerkarten bestehen aus den folgenden Eigenschaften, die verwendet werden, wenn sie in einer `TabbedPane` hinzugefügt werden. Diese Eigenschaften verfügen über Getter und Setter, um die Anpassung innerhalb einer `TabbedPane` zu erleichtern.

1. **Key(`Object`)**: Stellt den eindeutigen Bezeichner für die `Tab` dar.

2. **Text(`String`)**: Der Text, der als Titel für die `Tab` innerhalb der `TabbedPane` angezeigt wird. Dies wird auch als Titel über die Methoden `getTitle()` und `setTitle(String title)` bezeichnet.

3. **Tooltip(`String`)**: Der Tooltip-Text, der mit der `Tab` verknüpft ist und angezeigt wird, wenn der Cursor über die `Tab` fährt.

4. **Enabled(`boolean`)**: Gibt an, ob die `Tab` derzeit aktiviert ist oder nicht. Kann mit der Methode `setEnabled(boolean enabled)` geändert werden.

5. **Closeable(`boolean`)**: Gibt an, ob die `Tab` geschlossen werden kann. Kann mit der Methode `setCloseable(boolean enabled)` geändert werden. Dies fügt einen Schließen-Button auf der `Tab` hinzu, auf den der Benutzer klicken kann, und löst ein Entfernevent aus. Die `TabbedPane`-Komponente diktiert, wie das Entfernen behandelt wird.

6. **Slot(`Component`)**: 
    Slots bieten flexible Optionen zur Verbesserung der Funktionalität einer `Tab`. Sie können Symbole, Beschriftungen, Ladekreise, Löschen/Rücksetzen-Fähigkeiten, Avatare/Profilbilder und andere nützliche Komponenten innerhalb einer `Tab` einfügen, um die beabsichtigte Bedeutung für Benutzer weiter zu verdeutlichen.
    Sie können eine Komponente während der Erstellung in den `prefix` Slot einer `Tab` hinzufügen. Alternativ können Sie die Methoden `setPrefixComponent()` und `setSuffixComponent()` verwenden, um verschiedene Komponenten vor und nach der angezeigten Option innerhalb einer `Tab` einzufügen.

        ```java
        TabbedPane pane = new TabbedPane();
        pane.addTab(new Tab("Documents", TablerIcon.create("files")));
        ```

## `Tab`-Manipulation {#tab-manipulation}

Es gibt verschiedene Methoden, mit denen Entwickler `Tab`-Elemente innerhalb der `TabbedPane` hinzufügen, einfügen, entfernen und verschiedene Eigenschaften manipulieren können.

### Hinzufügen einer `Tab` {#adding-a-tab}

Die Methoden `addTab()` und `add()` existieren in verschiedenen überladenen Varianten, um Entwicklern Flexibilität beim Hinzufügen neuer Registerkarten zur `TabbedPane` zu ermöglichen. Das Hinzufügen einer `Tab` platziert sie nach allen zuvor vorhandenen Tabs.

1. **`addTab(String text)`** - Fügt der `TabbedPane` eine `Tab` mit dem angegebenen `String` als Text der `Tab` hinzu.
2. **`addTab(Tab tab)`** - Fügt die als Parameter bereitgestellte `Tab` zur `TabbedPane` hinzu.
3. **`addTab(String text, Component component)`** - Fügt eine `Tab` mit dem angegebenen `String` als Text der `Tab` hinzu, und die bereitgestellte `Component` wird im Inhaltsbereich der `TabbedPane` angezeigt.
4. **`addTab(Tab tab, Component component)`** - Fügt die bereitgestellte `Tab` hinzu und zeigt die bereitgestellte `Component` im Inhaltsbereich der `TabbedPane` an.
5. **`add(Component... component)`** - Fügt ein oder mehrere `Component`-Instanzen zur `TabbedPane` hinzu, wobei für jede einen diskrete `Tab` erstellt wird, deren Text auf den Namen der `Component` gesetzt wird.

:::info
Die `add(Component... component)` bestimmt den Namen der übergebenen `Component`, indem `component.getName()` auf das übergebene Argument aufgerufen wird.
:::

### Einfügen einer `Tab` {#inserting-a-tab}

Neben dem Hinzufügen einer `Tab` am Ende der vorhandenen Registerkarten ist es auch möglich, eine neue an einer bestimmten Position zu erstellen. Dazu gibt es mehrere überladene Versionen von `insertTab()`:

1. **`insertTab(int index, String text)`** - Fügt eine `Tab` in die `TabbedPane` an der angegebenen Stelle mit dem angegebenen `String` als Text der `Tab` ein.
2. **`insertTab(int index, Tab tab)`** - Fügt die als Parameter angegebene `Tab` an der angegebenen Stelle in die `TabbedPane` ein.
3. **`insertTab(int index, String text, Component component)`** - Fügt eine `Tab` mit dem angegebenen `String` als Text der `Tab` ein und die angegebene `Component` wird im Inhaltsbereich der `TabbedPane` angezeigt.
4. **`insertTab(int index, Tab tab, Component component)`** - Fügt die bereitgestellte `Tab` hinzu und zeigt die bereitgestellte `Component` im Inhaltsbereich der `TabbedPane` an.

### Entfernen einer `Tab` {#removing-a-tab}

Um eine einzelne `Tab` aus der `TabbedPane` zu entfernen, verwenden Sie eine der folgenden Methoden:

1. **`removeTab(Tab tab)`** - Entfernt eine `Tab` aus der `TabbedPane`, indem die zu entfernende Tab-Instanz übergeben wird.
2. **`removeTab(int index)`** - Entfernt eine `Tab` aus der `TabbedPane`, indem der Index der zu entfernenden `Tab` angegeben wird.

Neben den beiden obigen Methoden zum Entfernen einer einzelnen `Tab` kann die Methode **`removeAllTabs()`** verwendet werden, um die `TabbedPane` von allen Registerkarten zu leeren.

:::info
Die Methoden `remove()` und `removeAll()` entfernen keine Tabs innerhalb der Komponente.
:::

### Tab/Component-Zuordnung {#tabcomponent-association}

Um die `Component`, die für eine gegebene `Tab` angezeigt wird, zu ändern, rufen Sie die Methode `setComponentFor()` auf und übergeben entweder die Instanz der `Tab` oder den Index dieser Tab innerhalb der `TabbedPane`.

:::info
Wenn diese Methode für eine `Tab` verwendet wird, die bereits mit einer `Component` verknüpft ist, wird die zuvor verknüpfte `Component` zerstört.
:::

## Konfiguration und Layout {#configuration-and-layout}

Die `TabbedPane`-Klasse hat zwei Bestandteile: eine `Tab`, die an einem bestimmten Ort angezeigt wird, und eine Komponente, die angezeigt wird. Dies kann eine einzelne Komponente oder eine [`Composite`](../building-ui/composite-components)-Komponente sein, die es ermöglicht, komplexere Komponenten innerhalb des Inhaltsbereichs einer Registerkarte anzuzeigen.

### Wischen {#swiping}

Die `TabbedPane` unterstützt die Navigation durch die verschiedenen Registerkarten über Wischen. Dies ist ideal für eine mobile Anwendung, kann jedoch auch über eine integrierte Methode konfiguriert werden, um Mausklicks zu unterstützen. Sowohl das Wischen als auch das Mausklicken sind standardmäßig deaktiviert, können jedoch mittels der Methoden `setSwipable(boolean)` und `setSwipableWithMouse(boolean)` aktiviert werden.

### Positionierung der Registerkarten {#tab-placement}

Die `Tabs` innerhalb einer `TabbedPane` können je nach Vorliebe des Anwendungsentwicklers an verschiedenen Positionen innerhalb der Komponente platziert werden. Die bereitgestellten Optionen werden mit Hilfe der bereitgestellten Enum festgelegt, der die Werte `TOP`, `BOTTOM`, `LEFT`, `RIGHT` oder `HIDDEN` hat. Die Standardeinstellung ist `TOP`.

<ComponentDemo 
path='/webforj/tabbedpaneplacement?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/tabbedpane/TabbedPanePlacementView.java'
height="400px"
/>

### Ausrichtung {#alignment}

Zusätzlich zur Änderung der Platzierung der `Tab`-Elemente innerhalb der `TabbedPane` ist es auch möglich, zu konfigurieren, wie die Registerkarten innerhalb der Komponente ausgerichtet werden. Standardmäßig ist die Einstellung `AUTO` aktiv, die es ermöglicht, dass die Platzierung der Registerkarten deren Ausrichtung diktiert.

Die anderen Optionen sind `START`, `END`, `CENTER` und `STRETCH`. Die ersten drei beschreiben die Position relativ zur Komponente, wobei `STRETCH` die Registerkarten den verfügbaren Platz füllen lässt.

<ComponentDemo 
path='/webforj/tabbedpanealignment?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/tabbedpane/TabbedPaneAlignmentView.java'
height="250px"
/>

### Rand und Aktivitätsindikator {#border-and-activity-indicator}

Die `TabbedPane` zeigt standardmäßig einen Rand für die Registerkarten innerhalb von ihr an, der je nach festgelegter `Placement` platziert wird. Dieser Rand hilft, den Raum zu visualisieren, den die verschiedenen Registerkarten innerhalb des Panels einnehmen.

Wenn auf eine `Tab` geklickt wird, wird standardmäßig ein Aktivitätsindikator in der Nähe dieser `Tab` angezeigt, um zu helfen, darzustellen, welche die aktuell ausgewählte `Tab` ist.

Beide Optionen können von einem Entwickler angepasst werden, indem die booleschen Werte mithilfe der entsprechenden Setter-Methoden geändert werden. Um festzulegen, ob der Rand angezeigt wird oder nicht, kann die Methode `setBorderless(boolean)` verwendet werden, wobei `true` den Rand ausblendet und `false`, der Standardwert, den Rand anzeigt.

:::info
Dieser Rand gilt nicht für die gesamte `TabbedPane`-Komponente und dient lediglich als Trennlinie zwischen den Registerkarten und dem Inhalt der Komponente.
:::

Um die Sichtbarkeit des aktiven Indikators festzulegen, kann die Methode `setHideActiveIndicator(boolean)` verwendet werden. Wenn `true` an diese Methode übergeben wird, wird der aktive Indikator unter einer aktiven `Tab` ausgeblendet, während `false`, der Standardwert, den Indikator angezeigt lässt.

<ComponentDemo 
path='/webforj/tabbedpaneborder?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/tabbedpane/TabbedPaneBorderView.java'
height="300px"
/>

### Aktivierungsmodi {#activation-modes}

Für eine präzisere Kontrolle darüber, wie die `TabbedPane` navigiert wird, kann der `Activation`-Modus festgelegt werden, um anzugeben, wie die Komponente sich verhalten soll.

- **`Auto`**: Wenn auf Auto gesetzt, werden die entsprechenden Tab-Komponenten beim Navigieren mit den Pfeiltasten sofort angezeigt.

- **`Manual`**: Wenn auf manuell gesetzt, erhält die Tab den Fokus, wird jedoch nicht angezeigt, bis der Benutzer die Leertaste oder Eingabetaste drückt.

<ComponentDemo 
path='/webforj/tabbedpaneactivation?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/tabbedpane/TabbedPaneActivationView.java'
height="250px"
/>

### Entfernungsoptionen {#removal-options}

Einzelne `Tab`-Elemente können so festgelegt werden, dass sie schließbar sind. Schließbare Registerkarten haben einen Schließen-Button, der zur Registerkarte hinzugefügt wird und ein Schließevent auslöst, wenn er angeklickt wird. Die `TabbedPane` diktiert, wie dieses Verhalten gehandhabt wird.

- **`Manual`**: Standardmäßig ist das Entfernen auf `MANUAL` eingestellt, was bedeutet, dass das Ereignis ausgelöst wird, es aber in der Verantwortung des Entwicklers liegt, dieses Ereignis auf die von ihm gewählte Weise zu behandeln.

- **`Auto`**: Alternativ kann `AUTO` verwendet werden, was das Ereignis auslöst und auch die `Tab` von der Komponente entfernt, wodurch es für den Entwickler nicht mehr erforderlich ist, dieses Verhalten manuell zu implementieren.

## Styling {#styling}

### Expanse und Thema {#expanse-and-theme}

Die `TabbedPane` verfügt über integrierte Optionen für `Expanse` und `Theme`, die anderen webforJ-Komponenten ähnlich sind. Diese können verwendet werden, um schnell Stile hinzuzufügen, die verschiedene Bedeutungen für den Endbenutzer vermitteln, ohne die Komponente mit CSS zu gestalten.

<ComponentDemo 
path='/webforj/tabbedpaneexpansetheme?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/tabbedpane/TabbedPaneExpanseThemeView.java'
height="250px"
/>

<TableBuilder name="TabbedPane" />

## Beste Praktiken {#best-practices}

Die folgenden Praktiken werden empfohlen für die Nutzung der `TabbedPane` innerhalb von Anwendungen:

- **Logische Gruppierung**: Verwenden Sie Registerkarten, um verwandte Inhalte logisch zu gruppieren
    >- Jede Registerkarte sollte eine deutliche Kategorie oder Funktionalität innerhalb Ihrer Anwendung darstellen
    >- Gruppieren Sie ähnliche oder logische Registerkarten nahe beieinander

- **Begrenzte Registerkarten**: Vermeiden Sie es, Benutzer mit zu vielen Registerkarten zu überfordern. Überlegen Sie, eine hierarchische Struktur oder andere Navigationsmuster zu verwenden, wo zutreffend, für eine saubere Benutzeroberfläche

- **Klare Beschriftungen**: Beschriften Sie Ihre Registerkarten klar für eine intuitive Verwendung
    >- Bieten Sie klare und prägnante Beschriftungen für jede Registerkarte an
    >- Beschriftungen sollten den Inhalt oder die Zweckmäßigkeit widerspiegeln und es Benutzern erleichtern, zu verstehen
    >- Nutzen Sie Symbole und unterschiedliche Farben, wo zutreffend

- **Tastaturnavigation**: Verwenden Sie die Tastaturnavigation von webforJ's `TabbedPane`, um die Interaktion mit der `TabbedPane` für den Endbenutzer nahtloser und intuitiver zu gestalten

- **Standardregisterkarte**: Wenn die Standardregisterkarte nicht zu Beginn der `TabbedPane` platziert ist, ziehen Sie in Betracht, diese Registerkarte als Standard für wesentliche oder häufig verwendete Informationen festzulegen.
