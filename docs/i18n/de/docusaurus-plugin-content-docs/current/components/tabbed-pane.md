---
title: TabbedPane
slug: tabbedpane
sidebar_position: 125
_i18n_hash: 2e67673ef0ac49904be50764ef47ecb0
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-tabbed-pane" />
<DocChip chip='since' label='23.06' />
<JavadocLink type="foundation" location="com/webforj/component/tabbedpane/TabbedPane" top='true'/>

Die Klasse `TabbedPane` bietet eine kompakte und organisierte Möglichkeit, Inhalte anzuzeigen, die in mehrere Abschnitte unterteilt sind, die jeweils mit einem `Tab` verbunden sind. Benutzer können zwischen diesen Abschnitten wechseln, indem sie auf die jeweiligen Taps klicken, die häufig mit Text und/oder Icons beschriftet sind. Diese Klasse vereinfacht die Erstellung von facettenreichen Benutzeroberflächen, in denen unterschiedliche Inhalte oder Formulare zugänglich, aber nicht gleichzeitig sichtbar sind.

## Usages {#usages}

Die Klasse `TabbedPane` ist ein leistungsstarkes Werkzeug für Entwickler zur Organisation und Präsentation mehrerer Taps oder Abschnitte innerhalb einer Benutzeroberfläche. Hier sind einige typische Szenarien, in denen Sie ein `TabbedPane` in Ihrer Anwendung nutzen könnten:

1. **Dokumentenansicht**: Implementierung eines Dokumentenbetrachters, bei dem jede Registerkarte ein anderes Dokument oder eine andere Datei darstellt. Benutzer können problemlos zwischen offenen Dokumenten wechseln, um effizient multitasking-fähig zu sein.

2. **Datenverwaltung**: Nutzen Sie ein `TabbedPane`, um Aufgaben zur Datenverwaltung zu organisieren, zum Beispiel:
    >- Verschiedene Datensätze, die in einer Anwendung angezeigt werden
    >- Verschiedene Benutzerprofile, die in separaten Taps angezeigt werden können
    >- Unterschiedliche Profile in einem Benutzermanagementsystem

3. **Modulwahl**: Ein `TabbedPane` kann verschiedene Module oder Abschnitte darstellen. Jede Registerkarte kann die Funktionen eines bestimmten Moduls kapseln, sodass Benutzer sich jeweils auf einen Aspekt der Anwendung konzentrieren können.

4. **Aufgabenverwaltung**: Anwendungen zur Aufgabenverwaltung können ein `TabbedPane` verwenden, um verschiedene Projekte oder Aufgaben darzustellen. Jede Registerkarte könnte einem bestimmten Projekt entsprechen und es Benutzern ermöglichen, Aufgaben separat zu verwalten und zu verfolgen.

5. **Programmnavigation**: In einer Anwendung, die verschiedene Programme ausführen muss, könnte ein `TabbedPane`:
    >- Als Sidebar fungieren, die es ermöglicht, verschiedene Anwendungen oder Programme innerhalb einer einzigen Anwendung auszuführen, ähnlich wie es im [`AppLayout`](./app-layout.md) -Template gezeigt wird
    >- Eine obere Leiste erstellen, die einen ähnlichen Zweck erfüllt oder Unteranwendungen innerhalb einer bereits ausgewählten Anwendung darstellt.

## Tabs {#tabs}

Tabs sind UI-Elemente, die zu Tab-Panes hinzugefügt werden können, um zwischen verschiedenen Inhaltsansichten zu organisieren und zu wechseln.

:::important
Tabs sind nicht als eigenständige Komponenten gedacht. Sie sollen in Verbindung mit Tab-Panes verwendet werden. Diese Klasse ist kein `Component` und sollte nicht als solche verwendet werden.
:::

### Eigenschaften {#properties}

Tabs bestehen aus den folgenden Eigenschaften, die verwendet werden, wenn sie in einem `TabbedPane` hinzugefügt werden. Diese Eigenschaften haben Getter und Setter, um die Anpassung innerhalb eines `TabbedPane` zu erleichtern.

1. **Key(`Object`)**: Stellt den eindeutigen Bezeichner für den `Tab` dar.

2. **Text(`String`)**: Der Text, der als Titel für den `Tab` innerhalb des `TabbedPane` angezeigt wird. Dieser wird auch als Titel über die Methoden `getTitle()` und `setTitle(String title)` bezeichnet.

3. **Tooltip(`String`)**: Der Tooltip-Text, der mit dem `Tab` verbunden ist und angezeigt wird, wenn der Cursor über den `Tab` fährt.

4. **Enabled(`boolean`)**: Stellt dar, ob der `Tab` derzeit aktiviert ist oder nicht. Kann mit der Methode `setEnabled(boolean enabled)` modifiziert werden.

5. **Closeable(`boolean`)**: Stellt dar, ob der `Tab` geschlossen werden kann. Kann mit der Methode `setCloseable(boolean enabled)` modifiziert werden. Dies fügt dem `Tab` einen Schließen-Button hinzu, der vom Benutzer angeklickt werden kann und ein Entfernungsevent auslöst. Die `TabbedPane`-Komponente bestimmt, wie die Entfernung behandelt wird.

6. **Slot(`Component`)**: 
    Slots bieten flexible Optionen zur Verbesserung der Funktionalität eines `Tabs`. Sie können Icons, Labels, Ladeanimationen, Löschen/Zurücksetzen-Funktionalitäten, Avatar/Profilbilder und andere nützliche Komponenten in einem `Tab` verschachteln, um den Benutzern die beabsichtigte Bedeutung weiter zu verdeutlichen.
    Sie können ein Komponenten im `prefix`-Slot eines `Tabs` während des Aufbaus hinzufügen. Alternativ können Sie die Methoden `setPrefixComponent()` und `setSuffixComponent()` verwenden, um verschiedene Komponenten vor und nach der angezeigten Option innerhalb eines `Tabs` einzufügen.

        ```java
        TabbedPane pane = new TabbedPane();
        pane.addTab(new Tab("Documents", TablerIcon.create("files")));
        ```

## `Tab`-Manipulation {#tab-manipulation}

Es gibt verschiedene Methoden, die es Entwicklern ermöglichen, verschiedene Eigenschaften von `Tab`-Elementen innerhalb des `TabbedPane` hinzuzufügen, einzufügen, zu entfernen und zu manipulieren.

### Hinzufügen eines `Tabs` {#adding-a-tab}

Die Methoden `addTab()` und `add()` existieren in verschiedenen überladenen Varianten, um Entwicklern Flexibilität beim Hinzufügen neuer Taps zum `TabbedPane` zu ermöglichen. Das Hinzufügen eines `Tabs` platziert ihn hinter allen zuvor vorhandenen Taps.

1. **`addTab(String text)`** - Fügt einen `Tab` zum `TabbedPane` mit dem angegebenen `String` als Text des `Tabs` hinzu.
2. **`addTab(Tab tab)`** - Fügt den als Parameter bereitgestellten `Tab` dem `TabbedPane` hinzu.
3. **`addTab(String text, Component component)`** - Fügt einen `Tab` mit dem angegebenen `String` als Text des `Tabs` hinzu, und die bereitgestellte `Component` wird im Inhaltsbereich des `TabbedPane` angezeigt.
4. **`addTab(Tab tab, Component component)`** - Fügt den bereitgestellten `Tab` hinzu und zeigt die bereitgestellte `Component` im Inhaltsbereich des `TabbedPane` an.
5. **`add(Component... component)`** - Fügt eine oder mehrere `Component`-Instanzen zum `TabbedPane` hinzu und erstellt für jede eine diskrete `Tab`, wobei der Text auf den Namen der `Component` gesetzt wird.

:::info
Die Methode `add(Component... component)` bestimmt den Namen der übergebenen `Component`, indem sie `component.getName()` auf dem übergebenen Argument aufruft.
:::

### Einfügen eines `Tabs` {#inserting-a-tab}

Neben dem Hinzufügen eines `Tabs` am Ende der vorhandenen Taps ist es auch möglich, einen neuen an einer bestimmten Position zu erstellen. Dazu gibt es mehrere überladene Versionen der Methode `insertTab()`.

1. **`insertTab(int index, String text)`** - Fügt einen `Tab` in das `TabbedPane` an der angegebenen Indexposition mit dem angegebenen `String` als Text des `Tabs` ein.
2. **`insertTab(int index, Tab tab)`** - Fügt den als Parameter bereitgestellten `Tab` an der angegebenen Indexposition in das `TabbedPane` ein.
3. **`insertTab(int index, String text, Component component)`** - Fügt einen `Tab` mit dem angegebenen `String` als Text des `Tabs` ein, und die bereitgestellte `Component` wird im Inhaltsbereich des `TabbedPane` angezeigt.
4. **`insertTab(int index, Tab tab, Component component)`** - Fügt den bereitgestellten `Tab` ein und zeigt die bereitgestellte `Component` im Inhaltsbereich des `TabbedPane` an.

### Entfernen eines `Tabs` {#removing-a-tab}

Um einen einzelnen `Tab` aus dem `TabbedPane` zu entfernen, verwenden Sie eine der folgenden Methoden:

1. **`removeTab(Tab tab)`** - Entfernt einen `Tab` aus dem `TabbedPane`, indem die Tab-Instanz übergeben wird, die entfernt werden soll.
2. **`removeTab(int index)`** - Entfernt einen `Tab` aus dem `TabbedPane`, indem der Index des zu entfernenden `Tabs` angegeben wird.

Neben den beiden oben genannten Methoden zum Entfernen eines einzelnen `Tabs` kann die Methode **`removeAllTabs()`** verwendet werden, um das `TabbedPane` von allen Taps zu leeren.

:::info
Die Methoden `remove()` und `removeAll()` entfernen keine Taps innerhalb der Komponente.
:::

### Tab-/Komponenten-Zuordnung {#tabcomponent-association}

Um die `Component`, die für einen bestimmten `Tab` angezeigt werden soll, zu ändern, rufen Sie die Methode `setComponentFor()` auf und übergeben entweder die Instanz des `Tab` oder den Index dieses Taps innerhalb des `TabbedPane`.

:::info
Wird diese Methode für einen `Tab` verwendet, der bereits mit einer `Component` assoziiert ist, wird die zuvor assoziierte `Component` zerstört.
:::

## Konfiguration und Layout {#configuration-and-layout}

Die Klasse `TabbedPane` besteht aus zwei Bestandteilen: einem `Tab`, der an einer bestimmten Stelle angezeigt wird, und einer Komponente, die angezeigt wird. Dies kann eine einzelne Komponente oder eine [`Composite`](../building-ui/composite-components)-Komponente sein, die die Anzeige komplexerer Komponenten im Inhaltsbereich eines Taps ermöglicht.

### Wischen {#swiping}

Das `TabbedPane` unterstützt das Navigieren durch die verschiedenen Taps durch Wischen. Dies ist ideal für eine mobile Anwendung, kann jedoch auch über eine integrierte Methode zur Unterstützung von Wischbewegungen mit der Maus konfiguriert werden. Sowohl Wischen als auch Wischen mit der Maus sind standardmäßig deaktiviert, können jedoch mit den Methoden `setSwipable(boolean)` und `setSwipableWithMouse(boolean)` aktiviert werden.

### Tab-Platzierung {#tab-placement}

Die `Tabs` innerhalb eines `TabbedPane` können an verschiedenen Positionen innerhalb der Komponente basierend auf den Vorlieben der Anwendungsentwickler platziert werden. Die bereitgestellten Optionen werden mithilfe des bereitgestellten Enums gesetzt, das die Werte `TOP`, `BOTTOM`, `LEFT`, `RIGHT` oder `HIDDEN` hat. Die Standardeinstellung ist `TOP`.

<ComponentDemo 
path='/webforj/tabbedpaneplacement?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/tabbedpane/TabbedPanePlacementView.java'
height="400px"
/>

### Ausrichtung {#alignment}

Neben der Änderung der Platzierung der `Tab`-Elemente innerhalb des `TabbedPane` ist es auch möglich, zu konfigurieren, wie die Taps innerhalb der Komponente ausgerichtet werden. Standardmäßig ist die Einstellung `AUTO` aktiv, was es ermöglicht, dass die Platzierung der Taps deren Ausrichtung bestimmt.

Die anderen Optionen sind `START`, `END`, `CENTER` und `STRETCH`. Die ersten drei beschreiben die Position relativ zur Komponente, wobei `STRETCH` bewirkt, dass die Taps den verfügbaren Platz ausfüllen.

<ComponentDemo 
path='/webforj/tabbedpanealignment?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/tabbedpane/TabbedPaneAlignmentView.java'
height="250px"
/>

### Rahmen und Aktivitätsanzeige {#border-and-activity-indicator}

Das `TabbedPane` zeigt standardmäßig einen Rahmen für die darin enthaltenen Taps an, der je nach festgelegter `Placement` platziert ist. Dieser Rahmen hilft, den Raum zu visualisieren, den die verschiedenen Taps innerhalb des Panes einnehmen.

Wenn auf einen `Tab` geklickt wird, wird standardmäßig ein Aktivitätsindikator in der Nähe dieses `Tabs` angezeigt, um hervorzuheben, welcher `Tab` derzeit ausgewählt ist.

Beide Optionen können von einem Entwickler angepasst werden, indem sie die boolean-Werte mit den entsprechenden Setter-Methoden ändern. Um zu ändern, ob der Rahmen angezeigt wird oder nicht, kann die Methode `setBorderless(boolean)` verwendet werden, wobei `true` den Rahmen ausblendet und `false`, der Standardwert, den Rahmen anzeigt.

:::info
Dieser Rahmen gilt nicht für die Gesamtheit der `TabbedPane`-Komponente und dient lediglich als Trennlinie zwischen den Taps und dem Inhalt der Komponente.
:::

Um die Sichtbarkeit des aktiven Indikators zu steuern, kann die Methode `setHideActiveIndicator(boolean)` verwendet werden. Das Übergeben von `true` an diese Methode blendet den aktiven Indikator unter einem aktiven `Tab` aus, während `false`, der Standardwert, den Indikator sichtbar hält.

<ComponentDemo 
path='/webforj/tabbedpaneborder?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/tabbedpane/TabbedPaneBorderView.java'
height="300px"
/>

### Aktivierungsmodi {#activation-modes}

Für eine genauere Steuerung darüber, wie sich das `TabbedPane` bei der Navigation mit der Tastatur verhält, kann der `Activation`-Modus so eingestellt werden, dass das Verhalten der Komponente spezifiziert wird.

- **`Auto`**: Bei aktivierter Automatik wird beim Navigieren der Taps mit den Pfeiltasten sofort die entsprechende Tab-Komponente angezeigt.

- **`Manual`**: Bei manueller Einstellung erhält der Tab den Fokus, wird jedoch nicht angezeigt, es sei denn, der Benutzer drückt die Leertaste oder Eingabetaste.

<ComponentDemo 
path='/webforj/tabbedpaneactivation?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/tabbedpane/TabbedPaneActivationView.java'
height="250px"
/>

### Entfernen von Optionen {#removal-options}

Einzelne `Tab`-Elemente können als schließbar festgelegt werden. Schließbare Taps erhalten einen Schließen-Button, der ein Schließen-Event auslöst, wenn er angeklickt wird. Das `TabbedPane` bestimmt, wie dieses Verhalten gehandhabt wird.

- **`Manual`**: Standardmäßig ist das Entfernen auf `MANUAL` festgelegt, was bedeutet, dass das Event ausgelöst wird, aber es liegt am Entwickler, dieses Ereignis auf beliebige Weise zu handhaben.

- **`Auto`**: Alternativ kann `AUTO` verwendet werden, was das Ereignis auslöst und auch den `Tab` von der Komponente entfernt, wodurch die Notwendigkeit entfällt, dieses Verhalten manuell umzusetzen. 

## Stil {#styling}

### Ausdehnung und Design {#expanse-and-theme}

Das `TabbedPane` bietet integrierte Optionen für `Expanse` und `Theme`, ähnlich wie andere webforJ-Komponenten. Diese können verwendet werden, um schnell Styling hinzuzufügen, das verschiedene Bedeutungen für den Endbenutzer vermittelt, ohne die Komponente mit CSS zu gestalten.

<ComponentDemo 
path='/webforj/tabbedpaneexpansetheme?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/tabbedpane/TabbedPaneExpanseThemeView.java'
height="250px"
/>

<TableBuilder name="TabbedPane" />

## Beste Praktiken {#best-practices}

Die folgenden Praktiken werden empfohlen, um das `TabbedPane` innerhalb von Anwendungen zu nutzen:

- **Logische Gruppierung**: Verwenden Sie Taps, um verwandte Inhalte logisch zu gruppieren.
    >- Jede Registerkarte sollte eine bestimmte Kategorie oder Funktionalität innerhalb Ihrer Anwendung darstellen.
    >- Gruppieren Sie ähnliche oder logische Taps nahe beieinander.

- **Begrenzte Taps**: Vermeiden Sie es, Benutzer mit zu vielen Taps zu überfordern. Überlegen Sie sich gegebenenfalls eine hierarchische Struktur oder andere Navigationstechniken für eine saubere Benutzeroberfläche.

- **Klare Bezeichnungen**: Beschriften Sie Ihre Taps klar für eine intuitive Nutzung.
    >- Geben Sie klare und prägnante Bezeichnungen für jede Tap an.
    >- Bezeichnungen sollten den Inhalt oder Zweck widerspiegeln, sodass Benutzer leicht verstehen, was gemeint ist.
    >- Verwenden Sie Icons und deutliche Farben, wo dies sinnvoll ist.

- **Tastaturnavigation**: Nutzen Sie die Unterstützung der Tastaturnavigation des webforJ `TabbedPane`, um die Interaktion mit dem `TabbedPane` nahtloser und intuitiver für den Endbenutzer zu gestalten.

- **Standard-Tab**: Wenn der Standard-Tab nicht an erster Stelle im `TabbedPane` platziert ist, ziehen Sie in Betracht, diesen Tab als Standard für wesentliche oder häufig genutzte Informationen festzulegen.
