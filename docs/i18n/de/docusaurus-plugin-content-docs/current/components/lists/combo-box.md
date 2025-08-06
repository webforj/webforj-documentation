---
sidebar_position: 10
title: ComboBox
slug: combobox
_i18n_hash: d0112ef19b8ef7b0b2621af5c500a6c9
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-combobox" />
<DocChip chip='since' label='23.05' />
<JavadocLink type="foundation" location="com/webforj/component/list/ComboBox" top='true'/>

<ParentLink parent="Liste" />

Die `ComboBox`-Komponente ist ein Benutzeroberflächenelement, das dafür entwickelt wurde, den Nutzern eine Liste von Optionen oder Auswahlmöglichkeiten sowie ein Feld zur Eingabe eigener benutzerdefinierter Werte anzubieten. Benutzer können eine einzelne Option aus dieser Liste auswählen, typischerweise durch Klicken auf die `ComboBox`, was die Anzeige einer Dropdown-Liste mit verfügbaren Auswahlmöglichkeiten auslöst, oder sie können einen benutzerdefinierten Wert eingeben. Benutzer können auch mit den Pfeiltasten mit der `ComboBox` interagieren. Wenn ein Benutzer eine Auswahl trifft, wird die gewählte Option in der `ComboBox` angezeigt.

## Anwendungen {#usages}

Die ComboBox-Komponente ist ein vielseitiges Eingabeelement, das die Funktionen einer Dropdown-Liste und eines Texteingabefelds kombiniert. Sie ermöglicht es Benutzern, Elemente aus einer vordefinierten Liste auszuwählen oder nach Bedarf benutzerdefinierte Werte einzugeben. In diesem Abschnitt werden gängige Anwendungen der ComboBox-Komponente in verschiedenen Szenarien untersucht:

1. **Produktsuche und Eingabe**: Verwenden Sie eine ComboBox, um eine Produktsuche und Eingabefunktion zu implementieren. Benutzer können entweder ein Produkt aus einer vordefinierten Liste auswählen oder einen benutzerdefinierten Produktnamen eingeben. Dies ist hilfreich für Anwendungen wie E-Commerce-Websites, auf denen die Produkte zahlreich und vielfältig sind.

2. **Tag-Auswahl und Eingabe**: In Anwendungen, die sich mit dem Tagging von Inhalten befassen, kann eine ComboBox eine ausgezeichnete Wahl sein. Benutzer können aus einer Liste von vorhandenen Tags auswählen oder benutzerdefinierte Tags hinzufügen, indem sie diese eingeben. Dies ist nützlich, um Inhalte zu organisieren und zu kategorisieren. Beispiele für solche Tags sind:
    >- Projektlabels: In einem Projektmanagement-Tool können Benutzer Labels oder Tags (z. B. "Dringend", "In Bearbeitung", "Abgeschlossen") auswählen, um Aufgaben oder Projekte zu kategorisieren, und sie können bei Bedarf benutzerdefinierte Labels erstellen.
    >- Rezeptzutaten: In einer Koch- oder Rezept-App können Benutzer Zutaten aus einer Liste auswählen (z. B. "Tomaten", "Zwiebeln", "Hühnchen") oder ihre eigenen Zutaten für benutzerdefinierte Rezepte hinzufügen.
    >- Standort-Tags: In einer Karten- oder Geotagging-Anwendung können Benutzer vordefinierte Standort-Tags (z. B. "Strand", "Stadt", "Park") auswählen oder benutzerdefinierte Tags erstellen, um bestimmte Orte auf einer Karte zu kennzeichnen.

3. **Dateneingabe mit Vorschlägen**: In Dateneingabeformularen kann eine ComboBox verwendet werden, um die Eingabe zu beschleunigen, indem eine Liste von vorgeschlagenen Werten basierend auf der Benutzereingabe bereitgestellt wird. Dies hilft Benutzern, Daten genau und effizient einzugeben.

    :::tip
    Die `ComboBox` sollte verwendet werden, wenn Benutzern die Eingabe benutzerdefinierter Werte erlaubt ist. Wenn nur vordefinierte Werte gewünscht sind, verwenden Sie stattdessen eine [`ChoiceBox`](./choice-box.md).
    :::

## Benutzerdefinierter Wert {#custom-value}

Die Änderung der Eigenschaft für den benutzerdefinierten Wert ermöglicht die Kontrolle darüber, ob ein Benutzer in der Lage ist, den Wert im Eingabefeld der `ComboBox`-Komponente zu ändern. Wenn `true`, was der Standard ist, kann ein Benutzer den Wert ändern. Wenn auf `false` gesetzt, kann der Benutzer den Wert nicht ändern. Dies kann mit der <JavadocLink type="foundation" location="com/webforj/component/list/ComboBox" code='true' suffix='#setAllowCustomValue(boolean)'>setAllowCustomValue()</JavadocLink>-Methode eingestellt werden.

<ComponentDemo 
path='/webforj/comboboxcustomvalue?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/lists/combobox/ComboBoxCustomValueView.java'
height = '200px'
/>

## Platzhalter {#placeholder}

Ein Platzhalter kann für eine `ComboBox` festgelegt werden, der im Textfeld der Komponente angezeigt wird, wenn es leer ist, um die Benutzer zur gewünschten Eingabe im Feld anzuregen. Dies kann mit der <JavadocLink type="foundation" location="com/webforj/component/list/ComboBox" code='true' suffix='#setPlaceholder(java.lang.String)'>setPlaceholder()</JavadocLink>-Methode getan werden.

<ComponentDemo 
path='/webforj/comboboxplaceholder?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/lists/combobox/ComboBoxPlaceholderView.java'
height = '200px'
/>

## Dropdown-Typ {#dropdown-type}

Die Verwendung der <JavadocLink type="foundation" location="com/webforj/component/list/DwcSelectDropdown" code='true' suffix='#setDropdownType(java.lang.String)'>setDropdownType()</JavadocLink>-Methode weist dem `type`-Attribut einer `ComboBox` einen Wert zu und einen entsprechenden Wert für das `data-dropdown-for`-Attribut im Dropdown der `ComboBox`. Dies ist hilfreich für das Styling, da das Dropdown bei der Öffnung aus seiner aktuellen Position im DOM entfernt und ans Ende des Seitenkörpers verschoben wird.

Diese Abtrennung schafft eine Situation, in der das direkte Anvisieren des Dropdowns mit CSS oder Shadow-Part-Selektoren aus der übergeordneten Komponente schwierig wird, es sei denn, Sie verwenden das Dropdown-Typ-Attribut.

Im folgenden Demo wird der Dropdown-Typ festgelegt und im CSS-File verwendet, um das Dropdown auszuwählen und die Hintergrundfarbe zu ändern.

<ComponentDemo 
path='/webforj/comboboxdropdowntype?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/lists/combobox/ComboBoxDropdownTypeView.java'
cssURL='/css/lists/combobox/comboBoxDropDownType.css'
height='250px'
/>

## Maximaler Zeilenanzahl {#max-row-count}

Standardmäßig wird die Anzahl der in der Dropdown-Liste einer `ComboBox` angezeigten Zeilen erhöht, um den Inhalt zu passen. Mit der <JavadocLink type="foundation" location="com/webforj/component/list/DwcSelectDropdown" code='true' suffix='#setMaxRowCount(int)'>setMaxRowCount()</JavadocLink>-Methode kann jedoch gesteuert werden, wie viele Elemente angezeigt werden.

:::caution
Wenn eine Zahl verwendet wird, die kleiner oder gleich 0 ist, wird diese Eigenschaft zurückgesetzt.
:::

<ComponentDemo 
path='/webforj/comboboxmaxrow?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/lists/combobox/ComboBoxMaxRowView.java'
height='450px'
/>

## Öffnen und Schließen {#opening-and-closing}

Die Sichtbarkeit der Optionen für eine `ComboBox` kann programmgesteuert mit den Methoden `open()` und `close()` gesteuert werden. Diese Methoden ermöglichen es Ihnen, die Liste der Auswahlmöglichkeiten anzuzeigen oder auszublenden, wodurch eine größere Flexibilität bei der Verwaltung des Verhaltens einer `ComboBox` gegeben ist.

Darüber hinaus bietet webforJ Ereignislistener für den Fall, wenn die `ComboBox` geschlossen bzw. geöffnet wird, was Ihnen mehr Kontrolle gibt, um spezifische Aktionen auszulösen.

```Java
//Fokussieren oder Öffnen des nächsten Elements in einem Formular
ComboBox university = new ComboBox("Universität");
ComboBox major = new ComboBox("Studienrichtung");
Button submit = new Button("Absenden");

//... Listen von Universitäten und Studienrichtungen hinzufügen

university.onClose( e ->{
  major.open();
});

major.onClose( e ->{
  submit.focus();
});
```

## Öffnungsdimensionen {#opening-dimensions}

Die `ComboBox`-Komponente verfügt über Methoden, die die Manipulation der Dropdown-Dimensionen ermöglichen. Die **maximale Höhe** und **minimale Breite** des Dropdowns können mit der <JavadocLink type="foundation" location="com/webforj/component/list/DwcSelectDropdown" code='true' suffix='#setOpenHeight(int)'>setOpenHeight()</JavadocLink>- und der <JavadocLink type="foundation" location="com/webforj/component/list/DwcSelectDropdown" code='true' suffix='#setOpenWidth(int)'>setOpenWidth()</JavadocLink>-Methode festgelegt werden.

:::tip
Wenn ein `String`-Wert an eine dieser Methoden übergeben wird, wird [jede gültige CSS-Einheit](https://developer.mozilla.org/en-US/docs/Learn/CSS/Building_blocks/Values_and_units) angewendet, wie z. B. Pixel, Ansichtsmaße oder andere gültige Regeln. Wenn ein `int` übergeben wird, wird der übergebene Wert in Pixeln gesetzt.
:::

## Hervorheben {#highlighting}

Wenn Sie mit einer `ComboBox` arbeiten, können Sie anpassen, wann der Text hervorgehoben wird, basierend darauf, wie die Komponente den Fokus erhält. Diese Funktion kann Eingabefehler beim Ausfüllen von Formularen reduzieren und die gesamte Navigationserfahrung verbessern. Ändern Sie das Hervorhebungsverhalten mit der `setHighlightOnFocus()`-Methode mit einer der integrierten `HasHighlightOnFocus.Behavior`-Enums:

- `ALL`
Inhalte der Komponente werden immer automatisch hervorgehoben, wenn die Komponente den Fokus erhält.
- `FOCUS`
Inhalte der Komponente werden automatisch hervorgehoben, wenn die Komponente unter Programmsteuerung den Fokus erhält.
- `FOCUS_OR_KEY`
Inhalte der Komponente werden automatisch hervorgehoben, wenn die Komponente unter Programmsteuerung den Fokus erhält oder wenn in sie hinein getabbt wird.
- `FOCUS_OR_MOUSE`
Inhalte der Komponente werden automatisch hervorgehoben, wenn die Komponente unter Programmsteuerung den Fokus erhält oder wenn mit der Maus auf sie geklickt wird.
- `KEY`
Inhalte der Komponente werden automatisch hervorgehoben, wenn die Komponente durch Tabben den Fokus erhält.
- `KEY_MOUSE`
Inhalte der Komponente werden automatisch hervorgehoben, wenn die Komponente durch Tabben den Fokus erhält oder wenn mit der Maus darauf geklickt wird.
- `MOUSE`
Inhalte der Komponente werden automatisch hervorgehoben, wenn die Komponente durch Klicken mit der Maus den Fokus erhält.
- `NONE`
Inhalte der Komponente werden niemals automatisch hervorgehoben, wenn die Komponente den Fokus erhält.

:::note
Wenn der Inhalt beim Verlassen des Fokus hervorgehoben war, wird er beim Wiedererlangen des Fokus wieder hervorgehoben, unabhängig vom festgelegten Verhalten.
:::

## Präfix und Suffix {#prefix-and-suffix}

Slots bieten flexible Optionen zur Verbesserung der Funktionalität einer `ComboBox`. Sie können Symbole, Beschriftungen, Ladeanzeigen, Möglichkeiten zum Löschen/Zurücksetzen, Avatar-Profilbilder und andere nützliche Komponenten in einer `ComboBox` verschachteln, um die beabsichtigte Bedeutung für die Benutzer weiter zu verdeutlichen. Die `ComboBox` hat zwei Slots: den `prefix`- und den `suffix`-Slot. Verwenden Sie die Methoden `setPrefixComponent()` und `setSuffixComponent()`, um verschiedene Komponenten vor und nach den Optionen innerhalb einer `ComboBox` einzufügen.

```java
ComboBox comboBox = new ComboBox());
  comboBox.setPrefixComponent(TablerIcon.create("box"));
  comboBox.setSuffixComponent(TablerIcon.create("box"));
```

## Styling {#styling}

<TableBuilder name="ComboBox" />

## Best Practices {#best-practices}

Um ein optimales Benutzererlebnis bei der Verwendung der `ComboBox`-Komponente sicherzustellen, beachten Sie die folgenden Best Practices:

1. **Vorab häufig genutzte Werte laden**: Wenn es häufig verwendete Elemente gibt, laden Sie diese im `ComboBox`-Liste vor. Dies beschleunigt die Auswahl für häufig gewählte Elemente und fördert die Konsistenz.

2. **Benutzerfreundliche Labels**: Stellen Sie sicher, dass die angezeigten Labels für jede Option benutzerfreundlich und selbsterklärend sind. Stellen Sie sicher, dass die Benutzer den Zweck jeder Wahl leicht verstehen können.

3. **Validierung**: Implementieren Sie eine Eingabvalidierung, um benutzerdefinierte Eingaben zu bearbeiten. Überprüfen Sie die Daten auf Genauigkeit und Konsistenz. Möglicherweise möchten Sie Korrekturen oder Bestätigungen für mehrdeutige Eingaben vorschlagen.

4. **Standardauswahl**: Legen Sie eine Standardauswahl fest, insbesondere wenn es eine häufige oder empfohlene Wahl gibt. Dies verbessert das Nutzungserlebnis, indem die Notwendigkeit zusätzlicher Klicks reduziert wird.

5. **ComboBox vs. andere Listenkomponenten**: Eine `ComboBox` ist die beste Wahl, wenn Sie eine einzelne Eingabe vom Benutzer benötigen und ihm vordefinierte Auswahlmöglichkeiten sowie die Möglichkeit zur Anpassung bieten möchten. Eine andere Listenkomponente könnte besser geeignet sein, wenn Sie die folgenden Verhaltensweisen benötigen:
    - Mehrfachauswahl und gleichzeitige Anzeige aller Elemente: [ListBox](./list-box.md)
    - Verhindern von benutzerdefinierter Eingabe: [ChoiceBox](./choice-box.md)
