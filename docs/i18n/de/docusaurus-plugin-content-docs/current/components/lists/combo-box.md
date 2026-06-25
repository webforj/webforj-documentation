---
sidebar_position: 10
title: ComboBox
slug: combobox
description: >-
  Combine a dropdown list with a text input in the ComboBox to let users select
  preset items or type custom values with placeholder support.
_i18n_hash: 4ef8ce7040bed877e314790f155f728a
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-combobox" />
<DocChip chip='since' label='23.05' />
<JavadocLink type="foundation" location="com/webforj/component/list/ComboBox" top='true'/>

Die `ComboBox`-Komponente kombiniert eine Dropdown-Liste mit einem Texteingabefeld, sodass Benutzer entweder aus vordefinierten Optionen auswählen oder einen benutzerdefinierten Wert eingeben können. Wenn benutzerdefinierte Einträge zusammen mit einem Satz empfohlenen Optionen zulässig sein müssen, füllt dies die Lücke, die `ChoiceBox` nicht abdeckt.

<!-- INTRO_END -->

## Usos {#usages}

<ParentLink parent="Liste" />

Die ComboBox-Komponente ist ein vielseitiges Eingabeelement, das die Funktionen sowohl einer Dropdown-Liste als auch eines Texteingabefelds kombiniert. Sie ermöglicht es Benutzern, Elemente aus einer vordefinierten Liste auszuwählen oder nach Bedarf benutzerdefinierte Werte einzugeben. In diesem Abschnitt werden gängige Anwendungen der ComboBox-Komponente in verschiedenen Szenarien untersucht:

1. **Produktsuche und -eingabe**: Verwenden Sie eine ComboBox, um eine Produktsuch- und Eingabefunktion zu implementieren. Benutzer können entweder ein Produkt aus einer vordefinierten Liste auswählen oder einen benutzerdefinierten Produktnamen eingeben. Dies ist hilfreich für Anwendungen wie E-Commerce-Websites, in denen Produkte zahlreich und vielfältig sind.

2. **Tag-Auswahl und -eingabe**: In Anwendungen, die Inhalte taggen, kann eine ComboBox eine ausgezeichnete Wahl sein. Benutzer können aus einer Liste bestehender Tags auswählen oder benutzerdefinierte Tags hinzufügen, indem sie sie eingeben. Dies ist nützlich für die Organisation und Kategorisierung von Inhalten. Beispiele für solche Tags sind:
    >- Projektbeschriftungen: In einem Projektmanagement-Tool können Benutzer Beschriftungen oder Tags (z. B. "Dringend", "In Bearbeitung", "Abgeschlossen") auswählen, um Aufgaben oder Projekte zu kategorisieren, und sie können nach Bedarf benutzerdefinierte Beschriftungen erstellen.
    >- Rezeptzutaten: In einer Koch- oder Rezept-App können Benutzer Zutaten aus einer Liste auswählen (z. B. "Tomaten", "Zwiebeln", "Hähnchen") oder ihre eigenen Zutaten für benutzerdefinierte Rezepte hinzufügen.
    >- Standort-Tags: In einer Karten- oder Geotagging-Anwendung können Benutzer vordefinierte Standort-Tags auswählen (z. B. "Strand", "Stadt", "Park") oder benutzerdefinierte Tags erstellen, um bestimmte Orte auf einer Karte zu markieren.

3. **Dateneingabe mit vorgeschlagenen Werten**: In Dateneingabeformularen kann eine ComboBox verwendet werden, um die Eingabe zu beschleunigen, indem eine Liste vorgeschlagener Werte basierend auf der Benutzereingabe bereitgestellt wird. Dies hilft Benutzern, Daten genau und effizient einzugeben.

    :::tip
    Die `ComboBox` sollte verwendet werden, wenn Benutzern das Eingeben benutzerdefinierter Werte erlaubt ist. Wenn nur vordefinierte Werte gewünscht sind, verwenden Sie stattdessen eine [`ChoiceBox`](./choice-box.md).
    :::

## Benutzerdefinierter Wert {#custom-value}

Die Änderung der benutzerdefinierten Wert-Eigenschaft ermöglicht die Kontrolle darüber, ob ein Benutzer den Wert im Eingabefeld der `ComboBox`-Komponente ändern kann oder nicht. Wenn `true`, was der Standard ist, kann ein Benutzer den Wert ändern. Wenn auf `false` gesetzt, kann der Benutzer den Wert nicht ändern. Dies kann mit der <JavadocLink type="foundation" location="com/webforj/component/list/ComboBox" code='true' suffix='#setAllowCustomValue(boolean)'>setAllowCustomValue()</JavadocLink>-Methode festgelegt werden.

<ComponentDemo
path='/webforj/comboboxcustomvalue'
files={['src/main/java/com/webforj/samples/views/lists/combobox/ComboBoxCustomValueView.java']}
height='200px'
/>

## Platzhalter {#placeholder}

Ein Platzhalter kann für eine `ComboBox` festgelegt werden, der im Texteingabefeld der Komponente angezeigt wird, wenn es leer ist, um Benutzer zur gewünschten Eingabe im Feld aufzufordern. Dies kann mit der <JavadocLink type="foundation" location="com/webforj/component/list/ComboBox" code='true' suffix='#setPlaceholder(java.lang.String)'>setPlaceholder()</JavadocLink>-Methode erreicht werden.

<ComponentDemo
path='/webforj/comboboxplaceholder'
files={['src/main/java/com/webforj/samples/views/lists/combobox/ComboBoxPlaceholderView.java']}
height='200px'
/>

## Dropdown-Typ {#dropdown-type}

Die Verwendung der <JavadocLink type="foundation" location="com/webforj/component/list/DwcSelectDropdown" code='true' suffix='#setDropdownType(java.lang.String)'>setDropdownType()</JavadocLink>-Methode weist dem `type`-Attribut einer `ComboBox` einen Wert zu und einen entsprechenden Wert für das `data-dropdown-for`-Attribut im Dropdown der `ComboBox`. Dies ist hilfreich für das Styling, da das Dropdown aus seiner aktuellen Position im DOM entfernt und an das Ende des Seitenkörpers verschoben wird, wenn es geöffnet wird.

Diese Abtrennung schafft eine Situation, in der es schwierig wird, das Dropdown direkt mit CSS oder Shadow-Part-Selektoren aus der übergeordneten Komponente anzusprechen, es sei denn, Sie verwenden das Dropdown-Typ-Attribut.

Im folgenden Beispiel wird der Dropdown-Typ festgelegt und im CSS-Datei verwendet, um eine Option zu vergrößern, wenn Sie darüber hovern.

<ComponentDemo
path='/webforj/comboboxdropdowntype'
files={[
  'src/main/java/com/webforj/samples/views/lists/combobox/ComboBoxDropdownTypeView.java',
  'src/main/resources/static/css/lists/combobox/comboBoxDropDownType.css',
]}
height='250px'
/>

## Maximale Zeilenanzahl {#max-row-count}

Standardmäßig wird die Anzahl der in der Dropdown-Liste einer `ComboBox` angezeigten Zeilen erhöht, um den Inhalt anzupassen. Verwenden Sie jedoch die <JavadocLink type="foundation" location="com/webforj/component/list/DwcSelectDropdown" code='true' suffix='#setMaxRowCount(int)'>setMaxRowCount()</JavadocLink>-Methode, um zu steuern, wie viele Elemente angezeigt werden.

:::caution
Die Verwendung einer Zahl, die kleiner oder gleich 0 ist, führt dazu, dass diese Eigenschaft zurückgesetzt wird.
:::

<ComponentDemo
path='/webforj/comboboxmaxrow'
files={['src/main/java/com/webforj/samples/views/lists/combobox/ComboBoxMaxRowView.java']}
height='450px'
/>

## Öffnen und Schließen {#opening-and-closing}

Die Sichtbarkeit der Optionen für eine `ComboBox` kann programmgesteuert mit den Methoden `open()` und `close()` gesteuert werden. Diese Methoden ermöglichen es Ihnen, die Liste der auszuwählenden Optionen anzuzeigen oder bei Bedarf auszublenden, was eine größere Flexibilität bei der Verwaltung des Verhaltens einer `ComboBox` bietet.

Darüber hinaus hat webforJ Ereignis-Listener für das Schließen und Öffnen der `ComboBox`, die Ihnen mehr Kontrolle geben, um spezifische Aktionen auszulösen.

```Java
//Fokussieren oder öffnen Sie die nächste Komponente in einem Formular
ComboBox universität = new ComboBox("Universität");
ComboBox hauptfach = new ComboBox("Hauptfach");
Button einreichen = new Button("Einreichen");

//... Fügen Sie Listen von Universitäten und Hauptfächern hinzu

universität.onClose( e ->{
  hauptfach.open();
});

hauptfach.onClose( e ->{
  einreichen.focus();
});
```

## Öffnungsdimensionen {#opening-dimensions}

Die `ComboBox`-Komponente verfügt über Methoden, die eine Manipulation der Dropdown-Dimensionen ermöglichen. Die **maximale Höhe** und **minimale Breite** des Dropdowns können mit den <JavadocLink type="foundation" location="com/webforj/component/list/DwcSelectDropdown" code='true' suffix='#setOpenHeight(int)'>setOpenHeight()</JavadocLink>- und <JavadocLink type="foundation" location="com/webforj/component/list/DwcSelectDropdown" code='true' suffix='#setOpenWidth(int)'>setOpenWidth()</JavadocLink>-Methoden festgelegt werden.

:::tip
Das Übergeben eines `String`-Wertes an eine dieser Methoden ermöglicht die Anwendung von [beliebigen gültigen CSS-Einheiten](https://developer.mozilla.org/en-US/docs/Learn/CSS/Building_blocks/Values_and_units), wie Pixel, Ansichtsweite oder andere gültige Regeln. Das Übergeben einer `int`-Zahl legt den übergebenen Wert in Pixeln fest.
:::

## Hervorhebung {#highlighting}

Beim Arbeiten mit einer `ComboBox` können Sie anpassen, wann der Text hervorgehoben wird, basierend darauf, wie die Komponente den Fokus erhält. Diese Funktion kann Eingabefehler beim Ausfüllen von Formularen reduzieren und das allgemeine Navigationserlebnis verbessern. Ändern Sie das Hervorhebungsverhalten mit der Methode `setHighlightOnFocus()` mit einer der integrierten `HasHighlightOnFocus.Behavior`-Enums:

- `ALL`
Der Inhalt der Komponente wird immer automatisch hervorgehoben, wenn die Komponente den Fokus erhält.
- `FOCUS`
Der Inhalt der Komponente wird automatisch hervorgehoben, wenn die Komponente programmgesteuert den Fokus erhält.
- `FOCUS_OR_KEY`
Der Inhalt der Komponente wird automatisch hervorgehoben, wenn die Komponente programmgesteuert den Fokus erhält oder durch Tabben hinein gelangt.
- `FOCUS_OR_MOUSE`
Der Inhalt der Komponente wird automatisch hervorgehoben, wenn die Komponente programmgesteuert den Fokus erhält oder durch einen Klick mit der Maus hinein gelangt.
- `KEY`
Der Inhalt der Komponente wird automatisch hervorgehoben, wenn die Komponente den Fokus erhält, indem man hinein tabbt.
- `KEY_MOUSE`
Der Inhalt der Komponente wird automatisch hervorgehoben, wenn die Komponente den Fokus erhält, indem man hinein tabbt oder mit der Maus hinein klickt.
- `MOUSE`
Der Inhalt der Komponente wird automatisch hervorgehoben, wenn die Komponente den Fokus erhält, indem man mit der Maus hinein klickt.
- `NONE`
Der Inhalt der Komponente wird niemals automatisch hervorgehoben, wenn die Komponente den Fokus erhält.

:::note
Wenn der Inhalt beim Verlassen des Fokus hervorgehoben war, wird er beim Wiedererlangen des Fokus erneut hervorgehoben, unabhängig vom festgelegten Verhalten.
:::

## Präfix und Suffix {#prefix-and-suffix}

Slots bieten flexible Optionen zur Verbesserung der Fähigkeit einer `ComboBox`. Sie können Icons, Beschriftungen, Ladeanimationen, Löschen/Zurücksetzen, Avatar/profilbilder und andere nützliche Komponenten innerhalb einer `ComboBox` einfügen, um die beabsichtigte Bedeutung für die Benutzer weiter zu verdeutlichen. Die `ComboBox` hat zwei Slots: die `prefix`- und `suffix`-Slots. Verwenden Sie die Methoden `setPrefixComponent()` und `setSuffixComponent()`, um verschiedene Komponenten vor und nach den Optionen innerhalb einer `ComboBox` einzufügen.

```java
ComboBox comboBox = new ComboBox());
  comboBox.setPrefixComponent(TablerIcon.create("box"));
  comboBox.setSuffixComponent(TablerIcon.create("box"));
```

## Styling {#styling}

<TableBuilder name="ComboBox" />

## Beste Praktiken {#best-practices}

Um ein optimales Benutzererlebnis bei der Verwendung der `ComboBox`-Komponente zu gewährleisten, sollten Sie die folgenden besten Praktiken beachten:

1. **Vorab häufig verwendete Werte laden**: Wenn es häufig verwendete Elemente gibt, laden Sie diese in die Liste der `ComboBox`. Dies beschleunigt die Auswahl für häufig gewählte Elemente und fördert die Konsistenz.

2. **Benutzerfreundliche Labels**: Stellen Sie sicher, dass die angezeigten Beschriftungen für jede Option benutzerfreundlich und selbsterklärend sind. Die Benutzer sollten leicht verstehen können, welchen Zweck jede Wahl hat.

3. **Validierung**: Implementieren Sie eine Eingabevalidierung, um benutzerdefinierte Eingaben zu bearbeiten. Überprüfen Sie die Daten auf Genauigkeit und Konsistenz. Möglicherweise möchten Sie Korrekturen oder Bestätigungen für mehrdeutige Eingaben vorschlagen.

4. **Standardauswahl**: Legen Sie eine Standardeinstellung fest, insbesondere wenn es eine gängige oder empfohlene Wahl gibt. Dies verbessert das Benutzererlebnis, indem es die Notwendigkeit zusätzlicher Klicks verringert.

5. **ComboBox vs. andere Listenkomponenten**: Eine `ComboBox` ist die beste Wahl, wenn Sie eine Eingabe vom Benutzer benötigen und ihm vordefinierte Auswahlmöglichkeiten sowie die Möglichkeit bieten möchten, seine Eingabe anzupassen. Eine andere Listenkomponente kann besser geeignet sein, wenn folgende Verhaltensweisen erforderlich sind:
    - Mehrfachauswahl und Anzeige aller Elemente auf einmal: [ListBox](./list-box.md)
    - Verhindern benutzerdefinierter Eingaben: [ChoiceBox](./choice-box.md)
