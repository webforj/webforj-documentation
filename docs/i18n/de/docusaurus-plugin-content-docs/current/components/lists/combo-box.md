---
sidebar_position: 10
title: ComboBox
slug: combobox
_i18n_hash: ec3f88523477bf08e92fe9153b014b91
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-combobox" />
<DocChip chip='since' label='23.05' />
<JavadocLink type="foundation" location="com/webforj/component/list/ComboBox" top='true'/>

<ParentLink parent="Liste" />

Das `ComboBox`-Komponente ist ein Benutzeroberflächenelement, das dafür entwickelt wurde, den Benutzern eine Liste von Optionen oder Auswahlmöglichkeiten sowie ein Feld für die Eingabe eigener benutzerdefinierter Werte bereitzustellen. Benutzer können eine einzelne Option aus dieser Liste auswählen, typischerweise durch Klicken auf das `ComboBox`, was die Anzeige einer Dropdown-Liste mit verfügbaren Auswahlmöglichkeiten auslöst, oder sie können einen benutzerdefinierten Wert eingeben. Benutzer können auch mit den Pfeiltasten mit dem `ComboBox` interagieren. Wenn ein Benutzer eine Auswahl trifft, wird die gewählte Option im `ComboBox` angezeigt.

## Anwendungen {#usages}

Die ComboBox-Komponente ist ein vielseitiges Eingabeelement, das die Funktionen einer Dropdown-Liste und eines Texteingabefelds vereint. Sie ermöglicht es Benutzern, Elemente aus einer vordefinierten Liste auszuwählen oder bei Bedarf benutzerdefinierte Werte einzugeben. In diesem Abschnitt werden häufige Anwendungen der ComboBox-Komponente in verschiedenen Szenarien untersucht:

1. **Produkt Suche und Eingabe**: Verwenden Sie ein ComboBox, um eine Produkt-Such- und Eingabefunktion zu implementieren. Benutzer können entweder ein Produkt aus einer vordefinierten Liste auswählen oder einen benutzerdefinierten Produktnamen eingeben. Dies ist hilfreich für Anwendungen wie E-Commerce-Websites, auf denen die Produkte zahlreich und vielfältig sind.

2. **Tag-Auswahl und Eingabe**: In Anwendungen, die das Tagging von Inhalten beinhalten, kann ein ComboBox eine hervorragende Wahl sein. Benutzer können aus einer Liste vorhandener Tags auswählen oder benutzerdefinierte Tags durch Eingabe hinzufügen. Dies ist nützlich, um Inhalte zu organisieren und zu kategorisieren. Beispiele für solche Tags sind:
    >- Projektlabels: In einem Projektmanagement-Tool können Benutzer Labels oder Tags (z.B. "Dringend", "In Bearbeitung", "Abgeschlossen") auswählen, um Aufgaben oder Projekte zu kategorisieren, und sie können nach Bedarf benutzerdefinierte Labels erstellen.
    >- Rezept-Zutaten: In einer Koch- oder Rezept-App können Benutzer Zutaten aus einer Liste auswählen (z.B. "Tomaten", "Zwiebeln", "Hähnchen") oder ihre eigenen Zutaten für benutzerdefinierte Rezepte hinzufügen.
    >- Standort-Tags: In einer Karten- oder Geotagging-Anwendung können Benutzer vordefinierte Standort-Tags (z.B. "Strand", "Stadt", "Park") wählen oder benutzerdefinierte Tags erstellen, um spezielle Orte auf einer Karte zu kennzeichnen.

3. **Dateneingabe mit vorgeschlagenen Werten**: In Dateneingabeformularen kann ein ComboBox verwendet werden, um die Eingabe zu beschleunigen, indem eine Liste vorgeschlagener Werte basierend auf der Benutzereingabe bereitgestellt wird. Dies hilft den Benutzern, Daten genau und effizient einzugeben.

    :::tip
    Das `ComboBox` sollte verwendet werden, wenn Benutzern das Eingeben benutzerdefinierter Werte erlaubt ist. Wenn nur vordefinierte Werte gewünscht sind, verwenden Sie stattdessen ein [`ChoiceBox`](./choice-box.md).
    :::

## Benutzerdefinierter Wert {#custom-value}

Das Ändern der benutzerdefinierten Wert-Eigenschaft ermöglicht die Kontrolle darüber, ob ein Benutzer in der Lage ist, den Wert im Eingabefeld der `ComboBox`-Komponente zu ändern oder nicht. Wenn `true`, was der Standard ist, kann ein Benutzer den Wert ändern. Wenn auf `false` gesetzt, kann der Benutzer den Wert nicht ändern. Dies kann mit der <JavadocLink type="foundation" location="com/webforj/component/list/ComboBox" code='true' suffix='#setAllowCustomValue(boolean)'>setAllowCustomValue()</JavadocLink>-Methode eingestellt werden.

<ComponentDemo 
path='/webforj/comboboxcustomvalue?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/lists/combobox/ComboBoxCustomValueView.java'
height = '200px'
/>

## Platzhalter {#placeholder}

Ein Platzhalter kann für ein `ComboBox` festgelegt werden, der im Texteingabefeld der Komponente angezeigt wird, wenn es leer ist, um die Benutzer zur gewünschten Eingabe im Feld aufzufordern. Dies kann mit der <JavadocLink type="foundation" location="com/webforj/component/list/ComboBox" code='true' suffix='#setPlaceholder(java.lang.String)'>setPlaceholder()</JavadocLink>-Methode durchgeführt werden.

<ComponentDemo 
path='/webforj/comboboxplaceholder?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/lists/combobox/ComboBoxPlaceholderView.java'
height = '200px'
/>

## Dropdown-Typ {#dropdown-type}

Durch Verwendung der <JavadocLink type="foundation" location="com/webforj/component/list/DwcSelectDropdown" code='true' suffix='#setDropdownType(java.lang.String)'>setDropdownType()</JavadocLink>-Methode kann ein Wert dem `type`-Attribut eines `ComboBox` zugewiesen werden, sowie ein entsprechender Wert für das `data-dropdown-for`-Attribut im Dropdown des `ComboBox`. Dies ist hilfreich für das Styling, da das Dropdown aus seiner aktuellen Position im DOM entfernt und an das Ende des Seitenkörpers verlagert wird, wenn es geöffnet wird.

Diese Abtrennung schafft eine Situation, in der es herausfordernd wird, das Dropdown direkt mit CSS oder Shadow-Part-Selektoren vom übergeordneten Element aus zu adressieren, es sei denn, Sie nutzen das Dropdown-Typ-Attribut.

Im folgenden Demo wird der Dropdown-Typ festgelegt und in der CSS-Datei verwendet, um das Dropdown auszuwählen und die Hintergrundfarbe zu ändern.

<ComponentDemo 
path='/webforj/comboboxdropdowntype?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/lists/combobox/ComboBoxDropdownTypeView.java'
cssURL='/css/lists/combobox/comboBoxDropDownType.css'
height='250px'
/>

## Maximale Zeilenanzahl {#max-row-count}

Standardmäßig wird die Anzahl der in der Dropdown-Liste eines `ComboBox` angezeigten Zeilen erhöht, um den Inhalt zu passen. Die Verwendung der <JavadocLink type="foundation" location="com/webforj/component/list/DwcSelectDropdown" code='true' suffix='#setMaxRowCount(int)'>setMaxRowCount()</JavadocLink>-Methode ermöglicht jedoch die Kontrolle darüber, wie viele Elemente angezeigt werden.

:::caution
Die Verwendung einer Zahl, die kleiner oder gleich 0 ist, führt dazu, dass diese Eigenschaft zurückgesetzt wird.
:::

<ComponentDemo 
path='/webforj/comboboxmaxrow?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/lists/combobox/ComboBoxMaxRowView.java'
height='450px'
/>

## Öffnen und Schließen {#opening-and-closing}

Die Sichtbarkeit der Optionen für ein `ComboBox` kann programmgesteuert mit den Methoden `open()` und `close()` gesteuert werden. Diese Methoden ermöglichen es, die Liste der Auswahlmöglichkeiten anzuzeigen oder nach Bedarf auszublenden und bieten somit mehr Flexibilität in der Handhabung des Verhaltens eines `ComboBox`.

Darüber hinaus bietet webforJ Ereignislistener für das Schließen und Öffnen des `ComboBox`, mit denen Sie spezifische Aktionen auslösen können.

```Java
// Fokus oder öffne das nächste Element in einem Formular
ComboBox universität = new ComboBox("Universität");
ComboBox hauptfach = new ComboBox("Hauptfach");
Button absenden = new Button("Abschicken");

// ... Fügen Sie Listen von Universitäten und Hauptfächern hinzu

universität.onClose( e ->{
  hauptfach.open();
});

hauptfach.onClose( e ->{
  absenden.focus();
});
```

## Öffnungsdimensionen {#opening-dimensions}

Die `ComboBox`-Komponente hat Methoden, die es ermöglichen, die Dimensionen des Dropdowns zu manipulieren. Die **maximale Höhe** und **minimale Breite** des Dropdowns können mit den <JavadocLink type="foundation" location="com/webforj/component/list/DwcSelectDropdown" code='true' suffix='#setOpenHeight(int)'>setOpenHeight()</JavadocLink>- und <JavadocLink type="foundation" location="com/webforj/component/list/DwcSelectDropdown" code='true' suffix='#setOpenWidth(int)'>setOpenWidth()</JavadocLink>-Methoden festgelegt werden.

:::tip
Das Übergeben eines `String`-Wertes an eine dieser Methoden ermöglicht die Anwendung von [beliebigen gültigen CSS-Einheiten](https://developer.mozilla.org/en-US/docs/Learn/CSS/Building_blocks/Values_and_units), wie Pixel, Ansichtsdimensionen oder anderen gültigen Regeln. Das Übergeben eines `int` setzt den übergebenen Wert in Pixeln.
:::

## Hervorhebung {#highlighting}

Beim Arbeiten mit einem `ComboBox` können Sie anpassen, wann der Text hervorgehoben wird, basierend darauf, wie die Komponente den Fokus erhält. Diese Funktion kann Eingabefehler reduzieren, wenn Benutzer Formulare ausfüllen, und kann das gesamte Navigationserlebnis verbessern. Ändern Sie das Hervorhebung Verhalten mit der Methode `setHighlightOnFocus()` mit einer der integrierten `HasHighlightOnFocus.Behavior`-Enums:

- `ALL`
Inhalte der Komponente werden immer automatisch hervorgehoben, wenn die Komponente den Fokus erhält.
- `FOCUS`
Inhalte der Komponente werden automatisch hervorgehoben, wenn die Komponente unter Programmsteuerung den Fokus erhält.
- `FOCUS_OR_KEY`
Inhalte der Komponente werden automatisch hervorgehoben, wenn die Komponente unter Programmsteuerung den Fokus erhält oder durch Tabben in sie.
- `FOCUS_OR_MOUSE`
Inhalte der Komponente werden automatisch hervorgehoben, wenn die Komponente unter Programmsteuerung den Fokus erhält oder durch Klicken mit der Maus.
- `KEY`
Inhalte der Komponente werden automatisch hervorgehoben, wenn die Komponente den Fokus erhält, indem sie hinein getabbt wird.
- `KEY_MOUSE`
Inhalte der Komponente werden automatisch hervorgehoben, wenn die Komponente den Fokus erhält, indem hinein getabbt oder durch Klicken mit der Maus.
- `MOUSE`
Inhalte der Komponente werden automatisch hervorgehoben, wenn die Komponente den Fokus erhält, indem sie durch Klicken mit der Maus.
- `NONE`
Inhalte der Komponente werden nie automatisch hervorgehoben, wenn die Komponente den Fokus erhält.

:::note
Wenn der Inhalt beim Verlust des Fokus hervorgehoben wurde, wird er beim Wiedererlangen des Fokus wieder hervorgehoben, unabhängig vom festgelegten Verhalten.
:::

## Präfix und Suffix {#prefix-and-suffix}

Slots bieten flexible Optionen zur Verbesserung der Funktionalität eines `ComboBox`. Sie können Symbole, Labels, Ladekreisel, Löschen/Zurücksetzen-Fähigkeiten, Avatar-/Profilbilder und andere nützliche Komponenten innerhalb eines `ComboBox` haben, um den beabsichtigten Sinn für die Benutzer weiter zu verdeutlichen. Das `ComboBox` hat zwei Slots: die `prefix`- und `suffix`-Slots. Verwenden Sie die Methoden `setPrefixComponent()` und `setSuffixComponent()` um verschiedene Komponenten vor und nach den Optionen innerhalb eines `ComboBox` einzufügen.

```java
ComboBox comboBox = new ComboBox());
  comboBox.setPrefixComponent(TablerIcon.create("box"));
  comboBox.setSuffixComponent(TablerIcon.create("box"));
```

## Styling {#styling}

<TableBuilder name="ComboBox" />

## Best Practices {#best-practices}

Um ein optimales Benutzererlebnis bei der Verwendung der `ComboBox`-Komponente zu gewährleisten, berücksichtigen Sie die folgenden Best Practices:

1. **Häufige Werte vorab laden**: Wenn es häufige oder regelmäßig verwendete Elemente gibt, laden Sie diese im `ComboBox`-Liste vor. Dies beschleunigt die Auswahl für häufig gewählte Elemente und fördert die Konsistenz.

2. **Benutzerfreundliche Labels**: Stellen Sie sicher, dass die angezeigten Labels für jede Option benutzerfreundlich und selbsterklärend sind. Achten Sie darauf, dass Benutzer den Zweck jeder Wahl leicht verstehen können.

3. **Validierung**: Implementieren Sie eine Eingabvalidierung zur Handhabung von benutzerdefinierten Eingaben. Überprüfen Sie die Daten auf Genauigkeit und Konsistenz. Sie möchten möglicherweise Korrekturen oder Bestätigungen für mehrdeutige Eingaben vorschlagen.

4. **Standardeinstellung**: Setzen Sie eine Standardeinstellung, insbesondere wenn es eine gängige oder empfohlene Wahl gibt. Dies verbessert das Benutzererlebnis, indem die Notwendigkeit zusätzlicher Klicks verringert wird.

5. **ComboBox vs. andere Listenkomponenten**: Eine `ComboBox` ist die beste Wahl, wenn Sie eine einzelne Eingabe vom Benutzer benötigen und Ihnen vordefinierte Auswahlmöglichkeiten sowie die Möglichkeit zur Anpassung der Eingabe bieten möchten. Eine andere Listenkomponente könnte besser geeignet sein, wenn Sie folgende Verhaltensweisen benötigen:
    - Mehrfachauswahl und Anzeige aller Elemente auf einmal: [ListBox](./list-box.md)
    - Verhindern von benutzerdefinierten Eingaben: [ChoiceBox](./choice-box.md)
