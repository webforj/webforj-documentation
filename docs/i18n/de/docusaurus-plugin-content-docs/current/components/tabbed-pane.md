---
title: TabbedPane
slug: tabbedpane
sidebar_position: 125
_i18n_hash: 4f2421ca62abb88a3edd750e7887d2db
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-tabbed-pane" />
<DocChip chip='since' label='23.06' />
<JavadocLink type="foundation" location="com/webforj/component/tabbedpane/TabbedPane" top='true'/>

Die `TabbedPane`-Klasse bietet eine kompakte und organisierte Möglichkeit zur Anzeige von Inhalten, die in mehrere Abschnitte unterteilt sind, die jeweils mit einem `Tab` assoziiert sind. Benutzer können zwischen diesen Abschnitten wechseln, indem sie auf die entsprechenden Registerkarten klicken, die häufig mit Text und/oder Symbolen beschriftet sind. Diese Klasse vereinfacht die Erstellung von facettenreichen Schnittstellen, bei denen unterschiedliche Inhalte oder Formulare zugänglich sein müssen, jedoch nicht gleichzeitig sichtbar sind.

## Usages {#usages}

Die `TabbedPane`-Klasse bietet Entwicklern ein leistungsstarkes Werkzeug zum Organisieren und Präsentieren von mehreren Registerkarten oder Abschnitten innerhalb einer Benutzeroberfläche. Hier sind einige typische Szenarien, in denen Sie eine `TabbedPane` in Ihrer Anwendung verwenden könnten:

1. **Dokumentenansicht**: Implementierung einer Dokumentenansicht, bei der jede Registerkarte ein anderes Dokument oder eine andere Datei darstellt. Benutzer können leicht zwischen offenen Dokumenten wechseln, um effizientes Multitasking zu ermöglichen.

2. **Datenverwaltung:**: Nutzen Sie eine `TabbedPane`, um Aufgaben zur Datenverwaltung zu organisieren, beispielsweise:
    >- Verschiedene Datensätze, die in einer Anwendung angezeigt werden sollen
    >- Verschiedene Benutzerprofile, die innerhalb separater Registerkarten angezeigt werden können
    >- Verschiedene Profile in einem Benutzermanagement-System

3. **Modulwahl**: Eine `TabbedPane` kann verschiedene Module oder Abschnitte darstellen. Jede Registerkarte kann die Funktionen eines bestimmten Moduls kapseln und es den Benutzern ermöglichen, sich jeweils auf einen Aspekt der Anwendung zu konzentrieren.

4. **Aufgabenverwaltung**: Anwendungen zur Aufgabenverwaltung können eine `TabbedPane` verwenden, um verschiedene Projekte oder Aufgaben darzustellen. Jede Registerkarte könnte einem bestimmten Projekt entsprechen, sodass die Benutzer Aufgaben separat verwalten und verfolgen können.

5. **Programmnavigation**: In einer Anwendung, die verschiedene Programme ausführen muss, könnte eine `TabbedPane`:
    >- Als Sidebar dienen, die es ermöglicht, verschiedene Anwendungen oder Programme innerhalb einer einzigen Anwendung auszuführen, wie im [`AppLayout`](./app-layout.md) Template gezeigt
    >- Eine obere Leiste erstellen, die einen ähnlichen Zweck erfüllen oder Unteranwendungen innerhalb einer bereits ausgewählten Anwendung darstellen kann.

## Tabs {#tabs}

Registerkarten sind UI-Elemente, die zu Registerkarten-Paneelen hinzugefügt werden können, um verschiedene Inhaltsansichten zu organisieren und zwischen ihnen zu wechseln.

:::important
Registerkarten sind nicht als eigenständige Komponenten gedacht. Sie sollen in Verbindung mit Registerkarten-Paneelen verwendet werden. Diese Klasse ist keine `Component` und sollte nicht als solche verwendet werden.
:::

### Eigenschaften {#properties}

Registerkarten bestehen aus den folgenden Eigenschaften, die beim Hinzufügen zu einer `TabbedPane` verwendet werden. Diese Eigenschaften haben Getter und Setter, um die Anpassung innerhalb einer `TabbedPane` zu erleichtern.

1. **Schlüssel(`Object`)**: Stellt den eindeutigen Identifikator für die `Tab` dar.

2. **Text(`String`)**: Der Text, der als Titel für die `Tab` innerhalb der `TabbedPane` angezeigt wird. Dies wird auch als Titel über die Methoden `getTitle()` und `setTitle(String title)` bezeichnet.

3. **Tooltip(`String`)**: Der Tooltip-Text, der mit der `Tab` verbunden ist und angezeigt wird, wenn der Cursor über der `Tab` schwebt.

4. **Aktiviert(`boolean`)**: Stellt dar, ob die `Tab` derzeit aktiviert ist oder nicht. Kann mit der Methode `setEnabled(boolean enabled)` geändert werden.

5. **Schließbar(`boolean`)**: Stellt dar, ob die `Tab` geschlossen werden kann. Kann mit der Methode `setCloseable(boolean enabled)` geändert werden. Dies fügt eine Schaltfläche zum Schließen auf der `Tab` hinzu, die vom Benutzer angeklickt werden kann, und löst ein Entfernevent aus. Die `TabbedPane`-Komponente bestimmt, wie mit dem Entfernen umgegangen wird.

6. **Slot(`Component`)**: 
    Slots bieten flexible Möglichkeiten zur Verbesserung der Leistungsfähigkeit einer `Tab`. Sie können Symbole, Labels, Lade-Spiner, Löschen/Zurücksetzen-Funktionen, Avatar/Profilbilder und andere nützliche Komponenten innerhalb einer `Tab` haben, um den beabsichtigten Sinn für Benutzer weiter zu verdeutlichen.
    Sie können eine Komponente im `prefix`-Slot einer `Tab` während der Konstruktion hinzufügen. Alternativ können Sie die Methoden `setPrefixComponent()` und `setSuffixComponent()` verwenden, um verschiedene Komponenten vor und nach der angezeigten Option innerhalb einer `Tab` einzufügen.

        ```java
        TabbedPane pane = new TabbedPane();
        pane.addTab(new Tab("Documents", TablerIcon.create("files")));
        ```

## `Tab`-Manipulation {#tab-manipulation}

Es gibt verschiedene Methoden, die Entwicklern ermöglichen, `Tab`-Elemente innerhalb der `TabbedPane` hinzuzufügen, einzufügen, zu entfernen und verschiedene Eigenschaften zu manipulieren.

### Hinzufügen einer `Tab` {#adding-a-tab}

Die Methoden `addTab()` und `add()` existieren in verschiedenen überladenen Variationen, um Entwicklern Flexibilität beim Hinzufügen neuer Registerkarten zur `TabbedPane` zu bieten. Das Hinzufügen einer `Tab` platziert sie nach allen zuvor vorhandenen Registerkarten.

1. **`addTab(String text)`** - Fügt einer `TabbedPane` eine `Tab` mit dem angegebenen `String` als Text der `Tab` hinzu.
2. **`addTab(Tab tab)`** - Fügt die als Parameter angegebene `Tab` zur `TabbedPane` hinzu.
3. **`addTab(String text, Component component)`** - Fügt eine `Tab` mit dem angegebenen `String` als Text der `Tab` hinzu, und das angegebene `Component` wird im Inhaltsabschnitt der `TabbedPane` angezeigt.
4. **`addTab(Tab tab, Component component)`** - Fügt die bereitgestellte `Tab` hinzu und zeigt das bereitgestellte `Component` im Inhaltsabschnitt der `TabbedPane` an.
5. **`add(Component... component)`** - Fügt der `TabbedPane` eine oder mehrere `Component`-Instanzen hinzu, wobei für jede eine separate `Tab` erstellt wird, deren Text auf den Namen der `Component` gesetzt wird.

:::info
Die Methode `add(Component... component)` bestimmt den Namen der übergebenen `Component`, indem `component.getName()` auf das übergebene Argument aufgerufen wird.
:::

### Einfügen einer `Tab` {#inserting-a-tab}

Zusätzlich zum Hinzufügen einer `Tab` am Ende der vorhandenen Registerkarten ist es auch möglich, eine neue an einer bestimmten Position zu erstellen. Dazu werden mehrere überladene Versionen von `insertTab()` verwendet.

1. **`insertTab(int index, String text)`** - Fügt eine `Tab` in die `TabbedPane` an der angegebenen Position mit dem angegebenen `String` als Text der `Tab` ein.
2. **`insertTab(int index, Tab tab)`** - Fügt die als Parameter angegebene `Tab` zur `TabbedPane` an der angegebenen Position ein.
3. **`insertTab(int index, String text, Component component)`** - Fügt eine `Tab` mit dem angegebenen `String` als Text der `Tab` ein, und das angegebene `Component` wird im Inhaltsabschnitt der `TabbedPane` angezeigt.
4. **`insertTab(int index, Tab tab, Component component)`** - Fügt die bereitgestellte `Tab` ein und zeigt das bereitgestellte `Component` im Inhaltsabschnitt der `TabbedPane` an.

### Entfernen einer `Tab` {#removing-a-tab}

Um eine einzelne `Tab` aus der `TabbedPane` zu entfernen, verwenden Sie eine der folgenden Methoden:

1. **`removeTab(Tab tab)`** - Entfernt eine `Tab` aus der `TabbedPane`, indem die zu entfernende Tab-Instanz übergeben wird.
2. **`removeTab(int index)`** - Entfernt eine `Tab` aus der `TabbedPane`, indem der Index der zu entfernenden `Tab` spezifiziert wird.

Neben den beiden oben genannten Methoden zum Entfernen einer einzelnen `Tab` verwenden Sie die Methode **`removeAllTabs()`**, um alle Registerkarten aus der `TabbedPane` zu löschen.

:::info
Die Methoden `remove()` und `removeAll()` entfernen keine Registerkarten innerhalb der Komponente.
:::

### Tab/Component-Zuordnung {#tabcomponent-association}

Um die `Component` zu ändern, die für eine gegebene `Tab` angezeigt werden soll, rufen Sie die Methode `setComponentFor()` auf und übergeben entweder die Instanz der `Tab` oder den Index dieser Tab innerhalb der `TabbedPane`.

:::info
Wenn diese Methode auf eine `Tab` angewendet wird, die bereits mit einer `Component` assoziiert ist, wird die zuvor assoziierte `Component` zerstört.
:::

## Konfiguration und Layout {#configuration-and-layout}

Die `TabbedPane`-Klasse hat zwei Bestandteile: eine `Tab`, die an einem bestimmten Ort angezeigt wird, und eine Komponente, die angezeigt werden soll. Dies kann eine einzelne Komponente oder eine [`Composite`](../building-ui/composite-components)-Komponente sein, die es ermöglicht, komplexere Komponenten im Inhaltsabschnitt einer Tab anzuzeigen.

### Wischen {#swiping}

Die `TabbedPane` unterstützt die Navigation durch die verschiedenen Registerkarten per Wischen. Dies ist ideal für eine mobile Anwendung, kann aber auch über eine integrierte Methode konfiguriert werden, um das Wischen mit der Maus zu unterstützen. Sowohl das Wischen als auch das Wischen mit der Maus sind standardmäßig deaktiviert, können jedoch mit den Methoden `setSwipable(boolean)` und `setSwipableWithMouse(boolean)` aktiviert werden.

### Platzierung der Tabs {#tab-placement}

Die `Tabs` innerhalb einer `TabbedPane` können je nach Vorliebe der Anwendungsentwickler an verschiedenen Stellen innerhalb der Komponente platziert werden. Die bereitgestellten Optionen werden mit dem bereitgestellten Enum gesetzt, das die Werte `TOP`, `BOTTOM`, `LEFT`, `RIGHT` oder `HIDDEN` hat. Die Standardeinstellung ist `TOP`.

<ComponentDemo 
path='/webforj/tabbedpaneplacement?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/tabbedpane/TabbedPanePlacementView.java'
height="400px"
/>

### Ausrichtung {#alignment}

Zusätzlich zur Änderung der Platzierung der `Tab`-Elemente innerhalb der `TabbedPane` ist es auch möglich, wie die Registerkarten innerhalb der Komponente ausgerichtet werden. Standardmäßig hat die Einstellung `AUTO` Vorrang, die es ermöglicht, dass die Platzierung der Registerkarten ihre Ausrichtung bestimmt.

Die anderen Optionen sind `START`, `END`, `CENTER` und `STRETCH`. Die ersten drei beschreiben die Position relativ zur Komponente, wobei `STRETCH` die Registerkarten den verfügbaren Platz ausfüllen lässt.

<ComponentDemo 
path='/webforj/tabbedpanealignment?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/tabbedpane/TabbedPaneAlignmentView.java'
height="250px"
/>

### Rand und Aktivitätsanzeige {#border-and-activity-indicator}

Die `TabbedPane` hat standardmäßig einen Rand, der für die Registerkarten innerhalb davon angezeigt wird, abhängig davon, welches `Placement` gesetzt wurde. Dieser Rand hilft, den Raum zu visualisieren, den die verschiedenen Registerkarten innerhalb des Bereichs einnehmen.

Wenn eine `Tab` angeklickt wird, wird standardmäßig eine Aktivitätsanzeige in der Nähe dieser `Tab` angezeigt, um hervorzuheben, welche die derzeit ausgewählte `Tab` ist.

Beide Optionen können von einem Entwickler angepasst werden, indem die booleschen Werte mit den entsprechenden Settermethoden geändert werden. Um zu ändern, ob der Rand angezeigt wird oder nicht, kann die Methode `setBorderless(boolean)` verwendet werden, wobei `true` den Rand ausblendet und `false`, der Standardwert, den Rand anzeigt.

:::info
Dieser Rand gilt nicht für die Gesamtheit der `TabbedPane`-Komponente und dient lediglich als Trenner zwischen den Registerkarten und dem Inhalt der Komponente.
:::

Um die Sichtbarkeit der aktiven Anzeige einzustellen, kann die Methode `setHideActiveIndicator(boolean)` verwendet werden. Das Übergeben von `true` an diese Methode blendet die aktive Anzeige unter einer aktiven `Tab` aus, während `false`, der Standardwert, die Anzeige sichtbar hält.

<ComponentDemo 
path='/webforj/tabbedpaneborder?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/tabbedpane/TabbedPaneBorderView.java'
height="300px"
/>

### Aktivierungsmodi {#activation-modes}

Für eine genauere Steuerung darüber, wie sich die `TabbedPane` verhält, wenn sie mit der Tastatur navigiert wird, kann der `Aktivierungs`-Modus festgelegt werden, um anzugeben, wie sich die Komponente verhalten soll.

- **`Auto`**: Wenn auf Auto gestellt, wird beim Navigieren zu Registerkarten mit den Pfeiltasten sofort die entsprechende Tab-Komponente angezeigt.

- **`Manual`**: Wenn auf manuell gestellt, erhält die Registerkarte den Fokus, wird jedoch erst angezeigt, wenn der Benutzer die Leertaste oder Eingabetaste drückt.

<ComponentDemo 
path='/webforj/tabbedpaneactivation?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/tabbedpane/TabbedPaneActivationView.java'
height="250px"
/>

### Entfernungsoptionen {#removal-options}

Individuelle `Tab`-Elemente können so eingestellt werden, dass sie schließbar sind. Schließbare Registerkarten haben eine Schaltfläche zum Schließen, die der Registerkarte hinzugefügt wird, die ein Schließevent auslöst, wenn sie angeklickt wird. Die `TabbedPane` bestimmt, wie dieses Verhalten gehandhabt wird.

- **`Manual`**: Standardmäßig ist die Entfernung auf `MANUAL` eingestellt, was bedeutet, dass das Ereignis ausgelöst wird, es aber dem Entwickler überlassen bleibt, dieses Ereignis in beliebiger Weise zu behandeln.

- **`Auto`**: Alternativ kann `AUTO` verwendet werden, was das Ereignis auslöst und auch die `Tab` von der Komponente entfernt, sodass der Entwickler nicht gezwungen ist, dieses Verhalten manuell zu implementieren.

## Stil {#styling}

### Ausdehnung und Thema {#expanse-and-theme}

Die `TabbedPane` wird mit integrierten `Expanse`- und `Thema`-Optionen geliefert, die denen anderer webforJ-Komponenten ähnlich sind. Diese können verwendet werden, um schnell Stile hinzuzufügen, die dem Endbenutzer verschiedene Bedeutungen vermitteln, ohne die Komponente mit CSS zu gestalten.

<ComponentDemo 
path='/webforj/tabbedpaneexpansetheme?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/tabbedpane/TabbedPaneExpanseThemeView.java'
height="250px"
/>

<TableBuilder name="TabbedPane" />

## Best Practices {#best-practices}

Die folgenden Praktiken werden empfohlen, um die `TabbedPane` innerhalb von Anwendungen zu nutzen:

- **Logische Gruppierung**: Verwenden Sie Registerkarten, um verwandte Inhalte logisch zu gruppieren
    >- Jede Registerkarte sollte eine deutliche Kategorie oder Funktionalität innerhalb Ihrer Anwendung repräsentieren
    >- Gruppieren Sie ähnliche oder logische Registerkarten in der Nähe zueinander

- **Begrenzte Tabs**: Vermeiden Sie es, Benutzer mit zu vielen Registerkarten zu überfordern. Ziehen Sie in Betracht, eine hierarchische Struktur oder andere Navigationsmuster zu verwenden, wo dies anwendbar ist, um eine saubere Benutzeroberfläche zu ermöglichen.

- **Klare Beschriftungen**: Beschriften Sie Ihre Registerkarten klar für eine intuitive Verwendung
    >- Geben Sie klare und prägnante Beschriftungen für jede Registerkarte an
    >- Beschriftungen sollten den Inhalt oder den Zweck widerspiegeln und es den Benutzern erleichtern, zu verstehen
    >- Nutzen Sie Symbole und unterschiedliche Farben, wo anwendbar

- **Tastaturnavigation**: Nutzen Sie die Unterstützung der Tastaturnavigation des `TabbedPane` von webforJ, um die Interaktion mit der `TabbedPane` für den Endbenutzer nahtlos und intuitiv zu gestalten.

- **Standard-Tab**: Wenn der Standard-Tab nicht am Anfang der `TabbedPane` platziert ist, ziehen Sie in Betracht, diesen Tab als Standard für wesentliche oder häufig verwendete Informationen festzulegen.
