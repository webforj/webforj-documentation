---
sidebar_position: 10
title: ComboBox
slug: combobox
_i18n_hash: b1ed30653bdca5af11b2f138a491baef
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-combobox" />
<DocChip chip='since' label='23.05' />
<JavadocLink type="foundation" location="com/webforj/component/list/ComboBox" top='true'/>

Die `ComboBox`-Komponente kombiniert eine Dropdown-Liste mit einem Texteingabefeld, sodass Nutzer entweder aus vordefinierten Optionen auswählen oder einen benutzerdefinierten Wert eingeben können. Wenn benutzerdefinierte Einträge neben einer Reihe von vorgeschlagenen Optionen erlaubt sein müssen, füllt sie die Lücke, die `ChoiceBox` nicht abdeckt.

<!-- INTRO_END -->

## Verwendungen {#usages}

<ParentLink parent="Liste" />

Die ComboBox-Komponente ist ein vielseitiges Eingabefeld, das die Funktionen sowohl einer Dropdown-Liste als auch eines Texteingabefelds kombiniert. Sie ermöglicht es Nutzern, Elemente aus einer vordefinierten Liste auszuwählen oder bei Bedarf benutzerdefinierte Werte einzugeben. In diesem Abschnitt werden häufige Verwendungen der ComboBox-Komponente in verschiedenen Szenarien behandelt:

1. **Produktsuche und Eingabe**: Verwenden Sie eine ComboBox, um eine Produktsuche und -eingabefunktion zu implementieren. Nutzer können entweder ein Produkt aus einer vordefinierten Liste auswählen oder einen benutzerdefinierten Produktnamen eingeben. Dies ist hilfreich für Anwendungen wie E-Commerce-Seiten, auf denen die Produkte zahlreich und vielfältig sind.

2. **Tag-Auswahl und -Eingabe**: In Anwendungen, die das Tagging von Inhalten betreffen, kann eine ComboBox eine ausgezeichnete Wahl sein. Nutzer können aus einer Liste von vorhandenen Tags auswählen oder benutzerdefinierte Tags eingeben. Dies ist nützlich zur Organisation und Kategorisierung von Inhalten. Beispiele solcher Tags sind:
    >- Projektlabels: In einem Projektmanagement-Tool können Nutzer Labels oder Tags (z. B. "Dringend", "In Bearbeitung", "Abgeschlossen") zum Kategorisieren von Aufgaben oder Projekten auswählen, und sie können bei Bedarf benutzerdefinierte Labels erstellen.
    >- Rezeptzutaten: In einer Koch- oder Rezept-App können Nutzer Zutaten aus einer Liste auswählen (z. B. "Tomaten", "Zwiebeln", "Hühnchen") oder ihre eigenen Zutaten für benutzerdefinierte Rezepte hinzufügen.
    >- Standort-Tags: In einer Kartierungs- oder Geotagging-Anwendung können Nutzer vordefinierte Standort-Tags (z. B. "Strand", "Stadt", "Park") auswählen oder benutzerdefinierte Tags erstellen, um bestimmte Orte auf einer Karte zu markieren.

3. **Dateneingabe mit vorgeschlagenen Werten**: In Dateneingabeformularen kann eine ComboBox verwendet werden, um die Eingabe zu beschleunigen, indem eine Liste von vorgeschlagenen Werten basierend auf der Benutzereingabe bereitgestellt wird. Dies hilft Nutzer, Daten genau und effizient einzugeben.

    :::tip
    Die `ComboBox` sollte verwendet werden, wenn Nutzer erlaubte benutzerdefinierte Werte eingeben können. Wenn nur voreingestellte Werte gewünscht sind, verwenden Sie stattdessen eine [`ChoiceBox`](./choice-box.md).
    :::

## Benutzerdefinierter Wert {#custom-value}

Durch Ändern der benutzerdefinierten Wert-Eigenschaft kann gesteuert werden, ob ein Benutzer den Wert im Eingabefeld der `ComboBox`-Komponente ändern kann oder nicht. Wenn `true`, was der Standardwert ist, kann ein Benutzer den Wert ändern. Wenn auf `false` gesetzt, kann der Benutzer den Wert nicht ändern. Dies kann mit der <JavadocLink type="foundation" location="com/webforj/component/list/ComboBox" code='true' suffix='#setAllowCustomValue(boolean)'>setAllowCustomValue()</JavadocLink>-Methode festgelegt werden.

<ComponentDemo 
path='/webforj/comboboxcustomvalue?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/lists/combobox/ComboBoxCustomValueView.java'
height = '200px'
/>

## Platzhalter {#placeholder}

Ein Platzhalter kann für eine `ComboBox` festgelegt werden, der im Texteingabefeld der Komponente angezeigt wird, wenn es leer ist, um die Nutzer zur Eingabe des gewünschten Wertes aufzufordern. Dies kann mit der <JavadocLink type="foundation" location="com/webforj/component/list/ComboBox" code='true' suffix='#setPlaceholder(java.lang.String)'>setPlaceholder()</JavadocLink>-Methode durchgeführt werden.

<ComponentDemo 
path='/webforj/comboboxplaceholder?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/lists/combobox/ComboBoxPlaceholderView.java'
height = '200px'
/>

## Dropdown-Typ {#dropdown-type}

Mit der <JavadocLink type="foundation" location="com/webforj/component/list/DwcSelectDropdown" code='true' suffix='#setDropdownType(java.lang.String)'>setDropdownType()</JavadocLink>-Methode kann ein Wert für das `type`-Attribut einer `ComboBox` zugewiesen werden, sowie ein entsprechender Wert für das `data-dropdown-for`-Attribut im Dropdown der `ComboBox`. Dies ist hilfreich für das Styling, da das Dropdown bei Öffnung aus seiner aktuellen Position im DOM entfernt und an das Ende des Seitenkörpers verschoben wird.

Diese Abtrennung schafft eine Situation, in der es herausfordernd wird, das Dropdown direkt über CSS oder Shadow-Parts-Selektoren aus der übergeordneten Komponente anzusprechen, es sei denn, Sie verwenden das Dropdown-Typ-Attribut.

Im folgenden Demo wird der Dropdown-Typ eingestellt und im CSS-Format verwendet, um das Dropdown auszuwählen und die Hintergrundfarbe zu ändern.

<ComponentDemo 
path='/webforj/comboboxdropdowntype?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/lists/combobox/ComboBoxDropdownTypeView.java'
cssURL='/css/lists/combobox/comboBoxDropDownType.css'
height='250px'
/>

## Maximale Zeilenanzahl {#max-row-count}

Standardmäßig wird die Anzahl der in der Dropdown-Liste einer `ComboBox` angezeigten Zeilen erhöht, um den Inhalt anzupassen. Mit der <JavadocLink type="foundation" location="com/webforj/component/list/DwcSelectDropdown" code='true' suffix='#setMaxRowCount(int)'>setMaxRowCount()</JavadocLink>-Methode kann jedoch gesteuert werden, wie viele Elemente angezeigt werden.

:::caution
Die Verwendung einer Zahl, die kleiner oder gleich 0 ist, führt zum Entfernen dieser Eigenschaft.
:::

<ComponentDemo 
path='/webforj/comboboxmaxrow?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/lists/combobox/ComboBoxMaxRowView.java'
height='450px'
/>

## Öffnen und Schließen {#opening-and-closing}

Die Sichtbarkeit der Optionen für eine `ComboBox` kann programmgesteuert mit den `open()`- und `close()`-Methoden gesteuert werden. Diese Methoden ermöglichen es, die Liste der Auswahloptionen anzuzeigen oder nach Bedarf auszublenden, was eine größere Flexibilität bei der Verwaltung des Verhaltens einer `ComboBox` bietet.

Zusätzlich hat webforJ Ereignislistener für das Schließen und Öffnen der `ComboBox`, die Ihnen mehr Kontrolle geben, um spezifische Aktionen auszulösen.

```Java
//Fokussieren oder öffnen Sie die nächste Komponente in einem Formular
ComboBox university = new ComboBox("Universität");
ComboBox major = new ComboBox("Studienfach");
Button submit = new Button("Absenden");

//... Listen von Universitäten und Studienfächern hinzufügen

university.onClose( e ->{
  major.open();
});

major.onClose( e ->{
  submit.focus();
});
```

## Öffnungsdimensionen {#opening-dimensions}

Die `ComboBox`-Komponente verfügt über Methoden, die die Dropdown-Dimensionen manipulieren können. Die **maximale Höhe** und **minimale Breite** des Dropdowns können mit der <JavadocLink type="foundation" location="com/webforj/component/list/DwcSelectDropdown" code='true' suffix='#setOpenHeight(int)'>setOpenHeight()</JavadocLink>- und <JavadocLink type="foundation" location="com/webforj/component/list/DwcSelectDropdown" code='true' suffix='#setOpenWidth(int)'>setOpenWidth()</JavadocLink>-Methode festgelegt werden.

:::tip
Wenn Sie einen `String`-Wert an eine dieser Methoden übergeben, können Sie [jede gültige CSS-Einheit](https://developer.mozilla.org/en-US/docs/Learn/CSS/Building_blocks/Values_and_units) anwenden, wie z. B. Pixel, Ansichtsdimensionen oder andere gültige Regeln. Das Übergeben einer `int` setzt den übergebenen Wert in Pixeln.
:::

## Hervorhebung {#highlighting}

Bei der Arbeit mit einer `ComboBox` können Sie anpassen, wann der Text hervorgehoben wird, basierend darauf, wie die Komponente den Fokus erhält. Diese Funktion kann Eingabefehler verringern, wenn Nutzer Formulare ausfüllen, und die gesamte Navigationserfahrung verbessern. Ändern Sie das Hervorhebungsverhalten mit der `setHighlightOnFocus()`-Methode mit einer der integrierten `HasHighlightOnFocus.Behavior`-Enums:

- `ALL`
Inhalte der Komponente werden immer automatisch hervorgehoben, wenn die Komponente den Fokus erhält.
- `FOCUS`
Inhalte der Komponente werden automatisch hervorgehoben, wenn die Komponente programmgesteuert den Fokus erhält.
- `FOCUS_OR_KEY`
Inhalte der Komponente werden automatisch hervorgehoben, wenn die Komponente programmgesteuert den Fokus erhält oder durch Tabben dorthin gelangt.
- `FOCUS_OR_MOUSE`
Inhalte der Komponente werden automatisch hervorgehoben, wenn die Komponente programmgesteuert den Fokus erhält oder durch Klicken mit der Maus darauf gelangt.
- `KEY`
Inhalte der Komponente werden automatisch hervorgehoben, wenn die Komponente durch Tabben dorthin gelangt.
- `KEY_MOUSE`
Inhalte der Komponente werden automatisch hervorgehoben, wenn die Komponente durch Tabben dorthin gelangt oder durch Klicken mit der Maus darauf gelangt.
- `MOUSE`
Inhalte der Komponente werden automatisch hervorgehoben, wenn die Komponente durch Klicken mit der Maus darauf gelangt.
- `NONE`
Inhalte der Komponente werden niemals automatisch hervorgehoben, wenn die Komponente den Fokus erhält.

:::note
Wenn der Inhalt beim Verlust des Fokus hervorgehoben war, wird er beim Wiedererlangen des Fokus unabhängig vom festgelegten Verhalten erneut hervorgehoben.
:::

## Präfix und Suffix {#prefix-and-suffix}

Slots bieten flexible Optionen zur Verbesserung der Funktionalität einer `ComboBox`. Sie können Icons, Labels, Lade-Symbole, die Möglichkeit zum Löschen/Zurücksetzen, Avatare/Profilbilder und andere nützliche Komponenten innerhalb einer `ComboBox` einfügen, um die beabsichtigte Bedeutung für die Nutzer weiter zu klären. Die `ComboBox` hat zwei Slots: die `prefix`- und `suffix`-Slots. Verwenden Sie die `setPrefixComponent()`- und `setSuffixComponent()`-Methoden, um verschiedene Komponenten vor und nach den Optionen innerhalb einer `ComboBox` einzufügen.

```java
ComboBox comboBox = new ComboBox());
  comboBox.setPrefixComponent(TablerIcon.create("box"));
  comboBox.setSuffixComponent(TablerIcon.create("box"));
```

## Styling {#styling}

<TableBuilder name="ComboBox" />

## Best Practices {#best-practices}

Um ein optimales Benutzererlebnis bei der Verwendung der `ComboBox`-Komponente sicherzustellen, sollten Sie die folgenden Best Practices berücksichtigen:

1. **Vorab gängige Werte:** Wenn es häufig verwendete oder gängige Elemente gibt, laden Sie diese in die `ComboBox`-Liste vor. Dies beschleunigt die Auswahl für häufig gewählte Elemente und fördert Konsistenz.

2. **Benutzerfreundliche Labels:** Stellen Sie sicher, dass die angezeigten Labels für jede Option benutzerfreundlich und selbsterklärend sind. Achten Sie darauf, dass die Nutzer den Zweck jeder Auswahl leicht verstehen können.

3. **Validierung:** Implementieren Sie eine Eingabevalidierung zur Handhabung benutzerdefinierter Einträge. Überprüfen Sie die Daten auf Genauigkeit und Konsistenz. Sie möchten möglicherweise Korrekturen oder Bestätigungen für mehrdeutige Eingaben vorschlagen.

4. **Standardauswahl:** Legen Sie eine Standardauswahl fest, insbesondere wenn es eine gängige oder empfohlene Wahl gibt. Dies verbessert die Benutzererfahrung, indem es die Notwendigkeit für zusätzliche Klicks verringert.

5. **ComboBox vs. andere Listenkomponenten:** Eine `ComboBox` ist die beste Wahl, wenn Sie eine einzelne Eingabe vom Benutzer benötigen und ihnen vordefinierte Auswahlmöglichkeiten sowie die Möglichkeit zur Anpassung ihrer Eingaben bieten möchten. Eine andere Listenkomponente könnte besser geeignet sein, wenn Sie folgende Verhaltensweisen benötigen:
    - Mehrfachauswahl und Anzeige aller Elemente auf einmal: [ListBox](./list-box.md)
    - Verhindern benutzerdefinierter Eingaben: [ChoiceBox](./choice-box.md)
