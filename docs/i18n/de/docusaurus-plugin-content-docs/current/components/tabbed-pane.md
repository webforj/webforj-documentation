---
title: TabbedPane
slug: tabbedpane
sidebar_position: 125
_i18n_hash: f903eeae452aae41b3eb04c170b9e98e
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-tabbed-pane" />
<DocChip chip='since' label='23.06' />
<JavadocLink type="foundation" location="com/webforj/component/tabbedpane/TabbedPane" top='true'/>

Mehrere Inhaltsabschnitte können unter einem einzelnen `TabbedPane` organisiert werden, wobei jeder Abschnitt mit einem klickbaren `Tab` verbunden ist. Immer nur ein Abschnitt ist gleichzeitig sichtbar, und Tabs können Text, Icons oder beides anzeigen, um den Nutzern die Navigation zu erleichtern.

<!-- INTRO_END -->

## Usos {#usages}

Die Klasse `TabbedPane` bietet Entwicklern ein leistungsstarkes Werkzeug zur Organisation und Präsentation mehrerer Tabs oder Abschnitte innerhalb einer Benutzeroberfläche. Hier sind einige typische Szenarien, in denen Sie ein `TabbedPane` in Ihrer Anwendung nutzen könnten:

1. **Dokumentenbetrachter**: Implementierung eines Dokumentenbetrachters, bei dem jeder Tab ein anderes Dokument oder eine andere Datei darstellt. Benutzer können einfach zwischen geöffneten Dokumenten wechseln, um effizient Multitasking zu betreiben.

2. **Datenverwaltung:**: Nutzen Sie ein `TabbedPane`, um Aufgaben der Datenverwaltung zu organisieren, beispielsweise:
    >- Unterschiedliche Datensätze, die in einer Anwendung angezeigt werden
    >- Verschiedene Benutzerprofile können in separaten Tabs angezeigt werden
    >- Unterschiedliche Profile in einem Benutzermanagementsystem

3. **Modulauswahl**: Ein `TabbedPane` kann verschiedene Module oder Abschnitte darstellen. Jeder Tab kann die Funktionen eines bestimmten Moduls kapseln, sodass die Benutzer sich auf einen Aspekt der Anwendung zur gleichen Zeit konzentrieren können.

4. **Aufgabenverwaltung**: Aufgabenverwaltungsanwendungen können ein `TabbedPane` nutzen, um verschiedene Projekte oder Aufgaben darzustellen. Jeder Tab könnte einem bestimmten Projekt entsprechen, sodass die Benutzer Aufgaben separat verwalten und verfolgen können.

5. **Programmnavigation**: Innerhalb einer Anwendung, die verschiedene Programme ausführen muss, könnte ein `TabbedPane`:
    >- Als Seitenleiste dienen, die es ermöglicht, verschiedene Anwendungen oder Programme innerhalb einer einzigen Anwendung auszuführen, wie im [`AppLayout`](./app-layout.md) Template gezeigt
    >- Eine obere Leiste erstellen, die einen ähnlichen Zweck erfüllt oder Unteranwendungen innerhalb einer bereits ausgewählten Anwendung darstellt.
  
## Tabs {#tabs}

Tabs sind UI-Elemente, die zu Tabbed-Panes hinzugefügt werden können, um verschiedene Inhaltsansichten zu organisieren und zwischen ihnen zu wechseln.

:::important
Tabs sind nicht als eigenständige Komponenten gedacht. Sie sind dazu gedacht, in Verbindung mit Tabbed-Panes verwendet zu werden. Diese Klasse ist keine `Komponente` und sollte nicht als solche verwendet werden.
:::

### Eigenschaften {#properties}

Tabs bestehen aus den folgenden Eigenschaften, die beim Hinzufügen zu einem `TabbedPane` verwendet werden. Diese Eigenschaften haben Getter und Setter, um die Anpassung innerhalb eines `TabbedPane` zu erleichtern.

1. **Key(`Object`)**: Repräsentiert den eindeutigen Bezeichner für den `Tab`.

2. **Text(`String`)**: Der Text, der als Titel für den `Tab` innerhalb des `TabbedPane` angezeigt wird. Dies wird auch über die Methoden `getTitle()` und `setTitle(String title)` als Titel bezeichnet.

3. **Tooltip(`String`)**: Der Tooltip-Text, der mit dem `Tab` verbunden ist und angezeigt wird, wenn der Mauszeiger über den `Tab` fährt.

4. **Enabled(`boolean`)**: Repräsentiert, ob der `Tab` derzeit aktiviert oder deaktiviert ist. Kann mit der Methode `setEnabled(boolean enabled)` geändert werden.

5. **Closeable(`boolean`)**: Repräsentiert, ob der `Tab` geschlossen werden kann. Kann mit der Methode `setCloseable(boolean enabled)` geändert werden. Dies fügt einen Schließen-Button auf dem `Tab` hinzu, der vom Benutzer angeklickt werden kann und ein Entfernungsevent auslöst. Die `TabbedPane`-Komponente bestimmt, wie die Entfernung behandelt wird.

6. **Slot(`Component`)**: 
    Slots bieten flexible Optionen zur Verbesserung der Fähigkeiten eines `Tabs`. Sie können Icons, Labels, Lade-Spinners, Möglichkeiten zum Löschen/Zurücksetzen, Avatar/Profilbilder und andere nützliche Komponenten im Inneren eines `Tabs` haben, um die beabsichtigte Bedeutung für Benutzer weiter zu verdeutlichen. 
    Sie können während der Konstruktion eine Komponente zum `prefix` Slot eines `Tabs` hinzufügen. Alternativ können Sie die Methoden `setPrefixComponent()` und `setSuffixComponent()` verwenden, um verschiedene Komponenten vor und nach der angezeigten Option innerhalb eines `Tabs` einzufügen.

        ```java
        TabbedPane pane = new TabbedPane();
        pane.addTab(new Tab("Documents", TablerIcon.create("files")));
        ```

## `Tab`-Manipulation {#tab-manipulation}

Es gibt verschiedene Methoden, die Entwicklern erlauben, Tabs hinzuzufügen, einzufügen, zu entfernen und verschiedene Eigenschaften von `Tab`-Elementen innerhalb des `TabbedPane` zu manipulieren.

### Hinzufügen eines `Tabs` {#adding-a-tab}

Die Methoden `addTab()` und `add()` existieren in verschiedenen überladenen Versionen, um Entwicklern Flexibilität beim Hinzufügen neuer Tabs zum `TabbedPane` zu ermöglichen. Das Hinzufügen eines `Tabs` platziert ihn nach allen bereits vorhandenen Tabs.

1. **`addTab(String text)`** - Fügt einen `Tab` zum `TabbedPane` mit dem angegebenen `String` als Text des `Tabs` hinzu.
2. **`addTab(Tab tab)`** - Fügt den als Parameter bereitgestellten `Tab` zum `TabbedPane` hinzu.
3. **`addTab(String text, Component component)`** - Fügt einen `Tab` mit dem gegebenen `String` als Text des `Tabs` hinzu, und die bereitgestellte `Komponente` wird im Inhaltsbereich des `TabbedPane` angezeigt.
4. **`addTab(Tab tab, Component component)`** - Fügt den bereitgestellten `Tab` hinzu und zeigt die bereitgestellte `Komponente` im Inhaltsbereich des `TabbedPane` an.
5. **`add(Component... component)`** - Fügt ein oder mehrere `Component`-Instanzen dem `TabbedPane` hinzu, wobei für jede ein diskreter `Tab` erstellt wird, dessen Text auf den Namen der `Komponente` gesetzt wird.

:::info
Die Methode `add(Component... component)` bestimmt den Namen der übergebenen `Komponente`, indem sie `component.getName()` auf das übergebene Argument aufruft.
:::

### Einfügen eines `Tabs` {#inserting-a-tab}

Neben dem Hinzufügen eines `Tabs` am Ende der vorhandenen Tabs ist es auch möglich, einen neuen an einer bestimmten Position zu erstellen. Dafür gibt es mehrere überladene Versionen der Methode `insertTab()`.

1. **`insertTab(int index, String text)`** - Fügt einen `Tab` in den `TabbedPane` an dem angegebenen Index mit dem angegebenen `String` als Text des `Tabs` hinzu.
2. **`insertTab(int index, Tab tab)`** - Fügt den als Parameter bereitgestellten `Tab` zum `TabbedPane` an dem angegebenen Index hinzu.
3. **`insertTab(int index, String text, Component component)`** - Fügt einen `Tab` mit dem gegebenen `String` als Text des `Tabs` hinzu, und die bereitgestellte `Komponente` wird im Inhaltsbereich des `TabbedPane` angezeigt.
4. **`insertTab(int index, Tab tab, Component component)`** - Fügt den bereitgestellten `Tab` hinzu und zeigt die bereitgestellte `Komponente` im Inhaltsbereich des `TabbedPane` an.

### Entfernen eines `Tabs` {#removing-a-tab}

Um einen einzelnen `Tab` aus dem `TabbedPane` zu entfernen, verwenden Sie eine der folgenden Methoden:

1. **`removeTab(Tab tab)`** - Entfernt einen `Tab` aus dem `TabbedPane`, indem die zu entfernde Tab-Instanz übergeben wird.
2. **`removeTab(int index)`** - Entfernt einen `Tab` aus dem `TabbedPane`, indem der Index des zu entfernenden `Tabs` angegeben wird.

Neben den beiden oben genannten Methoden zum Entfernen eines einzelnen `Tabs` können Sie die Methode **`removeAllTabs()`** verwenden, um das `TabbedPane` von allen Tabs zu leeren.

:::info
Die Methoden `remove()` und `removeAll()` entfernen keine Tabs innerhalb der Komponente.
:::

### Tab-/Komponenten-Assoziation {#tabcomponent-association}

Um die `Komponente` zu ändern, die für einen bestimmten `Tab` angezeigt werden soll, rufen Sie die Methode `setComponentFor()` auf und übergeben entweder die Instanz des `Tabs` oder den Index dieses Tabs innerhalb des `TabbedPane`.

:::info
Wenn diese Methode bei einem `Tab` verwendet wird, der bereits mit einer `Komponente` assoziiert ist, wird die zuvor assoziierte `Komponente` zerstört.
:::

## Konfiguration und Layout {#configuration-and-layout}

Die Klasse `TabbedPane` hat zwei Teile: einen `Tab`, der an einem bestimmten Ort angezeigt wird, und eine Komponente, die angezeigt werden soll. Dies kann eine einzige Komponente oder eine [`Composite`](../building-ui/composite-components) Komponente sein, die die Anzeige komplexerer Komponenten im Inhaltsbereich eines Tabs ermöglicht.

### Wischen {#swiping}

Das `TabbedPane` unterstützt die Navigation durch die verschiedenen Tabs per Wischbewegung. Dies ist ideal für eine mobile Anwendung, kann jedoch auch über eine eingebaute Methode konfiguriert werden, um das Wischen mit der Maus zu unterstützen. Sowohl Wischen als auch Wischen mit der Maus sind standardmäßig deaktiviert, können jedoch mit den Methoden `setSwipable(boolean)` und `setSwipableWithMouse(boolean)` aktiviert werden.

### Tab-Platzierung {#tab-placement}

Die `Tabs` innerhalb eines `TabbedPane` können je nach Vorlieben der Anwendungsentwickler an verschiedenen Positionen innerhalb der Komponente platziert werden. Die angebotenen Optionen werden mithilfe des bereitgestellten Enums gesetzt, das die Werte `TOP`, `BOTTOM`, `LEFT`, `RIGHT` oder `HIDDEN` hat. Die Standardeinstellung ist `TOP`.

<ComponentDemo 
path='/webforj/tabbedpaneplacement?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/tabbedpane/TabbedPanePlacementView.java'
height="400px"
/>

### Ausrichtung {#alignment}

Neben der Änderung der Platzierung der `Tab`-Elemente innerhalb des `TabbedPane` ist es auch möglich, zu konfigurieren, wie die Tabs innerhalb der Komponente ausgerichtet werden. Standardmäßig ist die Einstellung `AUTO` aktiv, die es zulässt, dass die Platzierung der Tabs ihre Ausrichtung diktiert.

Die anderen Optionen sind `START`, `END`, `CENTER` und `STRETCH`. Die ersten drei beschreiben die Position relativ zur Komponente, während `STRETCH` die Tabs den verfügbaren Platz ausfüllen lässt.

<ComponentDemo 
path='/webforj/tabbedpanealignment?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/tabbedpane/TabbedPaneAlignmentView.java'
height="250px"
/>

### Rahmen und Aktivitätsindikator {#border-and-activity-indicator}

Das `TabbedPane` wird standardmäßig mit einem Rahmen für die Tabs innerhalb davon angezeigt, der abhängig von der gesetzten `Placement` platziert ist. Dieser Rahmen hilft, den Raum, den die verschiedenen Tabs innerhalb des Paneels einnehmen, zu visualisieren.

Wenn ein `Tab` angeklickt wird, wird standardmäßig ein Aktivitätsindikator in der Nähe dieses `Tabs` angezeigt, um hervorzuheben, welcher `Tab` derzeit ausgewählt ist.

Beide Optionen können von einem Entwickler angepasst werden, indem die booleschen Werte mit den entsprechenden Setter-Methoden geändert werden. Um zu ändern, ob der Rahmen angezeigt wird, kann die Methode `setBorderless(boolean)` verwendet werden, wobei `true` den Rahmen ausblendet und `false`, der Standardwert, den Rahmen anzeigt.

:::info
Dieser Rahmen gilt nicht für die Gesamtheit des `TabbedPane`-Elements, sondern dient lediglich als Trennlinie zwischen den Tabs und dem Inhalt der Komponente.
:::

Um die Sichtbarkeit des aktiven Indikators festzulegen, kann die Methode `setHideActiveIndicator(boolean)` verwendet werden. Das Übergeben von `true` an diese Methode blendet den aktiven Indikator unter einem aktiven `Tab` aus, während `false`, der Standardwert, den Indikator sichtbar lässt.

<ComponentDemo 
path='/webforj/tabbedpaneborder?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/tabbedpane/TabbedPaneBorderView.java'
height="300px"
/>

### Aktivierungsmodi {#activation-modes}

Für eine detailliertere Kontrolle über das Verhalten des `TabbedPane` bei der Navigation über die Tastatur kann der Aktivierungsmodus so eingestellt werden, dass angegeben wird, wie sich die Komponente verhalten soll.

- **`Auto`**: Wenn auf Auto gesetzt, zeigt das Navigieren von Tabs mit den Pfeiltasten sofort die jeweilige Tab-Komponente an.

- **`Manual`**: Wenn auf manuell gesetzt, erhält der Tab den Fokus, wird jedoch nicht angezeigt, bis der Benutzer die Leertaste oder die Eingabetaste drückt.

<ComponentDemo 
path='/webforj/tabbedpaneactivation?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/tabbedpane/TabbedPaneActivationView.java'
height="250px"
/>

### Entfernen-Optionen {#removal-options}

Einzelne `Tab`-Elemente können so eingestellt werden, dass sie schließbar sind. Schließbare Tabs erhalten einen Schließen-Button, der beim Klicken ein Schließerevent auslöst. Das `TabbedPane` bestimmt, wie dieses Verhalten gehandhabt wird.

- **`Manual`**: Standardmäßig ist die Entfernung auf `MANUAL` gesetzt, was bedeutet, dass das Ereignis ausgelöst wird, es jedoch dem Entwickler überlassen bleibt, dieses Ereignis in welcher Weise auch immer zu behandeln.

- **`Auto`**: Alternativ kann `AUTO` verwendet werden, was das Ereignis auslöst und den `Tab` auch automatisch aus der Komponente entfernt, sodass der Entwickler dieses Verhalten nicht manuell implementieren muss.

## Styling {#styling}

### Ausdehnung und Thema {#expanse-and-theme}

Das `TabbedPane` verfügt über integrierte `Expanse`- und `Theme`-Optionen, die anderen webforJ-Komponenten ähneln. Diese können verwendet werden, um schnell Stile hinzuzufügen, die verschiedene Bedeutungen für den Endbenutzer vermitteln, ohne dass das Element mit CSS gestaltet werden muss.

<ComponentDemo 
path='/webforj/tabbedpaneexpansetheme?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/tabbedpane/TabbedPaneExpanseThemeView.java'
height="250px"
/>

<TableBuilder name={['Tab', 'TabbedPane']} />

## Best Practices {#best-practices}

Die folgenden Praktiken werden empfohlen, um das `TabbedPane` innerhalb von Anwendungen zu nutzen:

- **Logische Gruppierung**: Verwenden Sie Tabs, um verwandte Inhalte logisch zu gruppieren
    >- Jeder Tab sollte eine ausgeprägte Kategorie oder Funktionalität innerhalb Ihrer Anwendung darstellen
    >- Gruppieren Sie ähnliche oder logische Tabs nahe beieinander

- **Begrenzte Tabs**: Vermeiden Sie es, Benutzer mit zu vielen Tabs zu überwältigen. Ziehen Sie in Betracht, eine hierarchische Struktur oder andere Navigationsmuster anzuwenden, wo dies für eine saubere Benutzeroberfläche anwendbar ist

- **Klare Beschriftungen**: Kennzeichnen Sie Ihre Tabs deutlich für eine intuitive Nutzung
    >- Geben Sie klare und prägnante Beschriftungen für jeden Tab an
    >- Beschriftungen sollten den Inhalt oder Zweck widerspiegeln, um es den Benutzern zu erleichtern, diesen zu verstehen
    >- Nutzen Sie Icons und ausgeprägte Farben, wo anwendbar

- **Tastaturnavigation**: Nutzen Sie die Unterstützung der Tastaturnavigation des `TabbedPane` von webforJ, um die Interaktion mit dem `TabbedPane` nahtloser und intuitiver für den Endbenutzer zu gestalten

- **Standard-Tab**: Wenn der Standard-Tab nicht am Anfang des `TabbedPane` platziert ist, ziehen Sie in Betracht, diesen Tab als Standard für wichtige oder häufig verwendete Informationen festzulegen.
