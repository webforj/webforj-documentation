---
sidebar_position: 10
title: ComboBox
slug: combobox
_i18n_hash: 199696bfbf6489520cec364f16226489
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-combobox" />
<DocChip chip='since' label='23.05' />
<JavadocLink type="foundation" location="com/webforj/component/list/ComboBox" top='true'/>

Die `ComboBox`-Komponente kombiniert eine Dropdown-Liste mit einem Texteingabefeld, sodass Benutzer entweder aus vordefinierten Optionen auswählen oder einen benutzerdefinierten Wert eingeben können. Wenn benutzerdefinierte Einträge zusammen mit einer Reihe von vorgeschlagenen Optionen zulässig sein müssen, füllt sie die Lücke, die `ChoiceBox` nicht abdeckt.

<!-- INTRO_END -->

## Verwendungen {#usages}

<ParentLink parent="Liste" />

Die ComboBox-Komponente ist ein vielseitiges Eingabeelement, das die Funktionen sowohl einer Dropdown-Liste als auch eines Texteingabefelds kombiniert. Sie ermöglicht es Benutzern, Elemente aus einer vordefinierten Liste auszuwählen oder nach Bedarf benutzerdefinierte Werte einzugeben. Dieser Abschnitt untersucht gängige Verwendungen der ComboBox-Komponente in verschiedenen Szenarien:

1. **Produkt-Suche und Eingabe**: Verwenden Sie eine ComboBox, um eine Produkt-Such- und Eingabefunktion zu implementieren. Benutzer können entweder ein Produkt aus einer vordefinierten Liste auswählen oder einen benutzerdefinierten Produktnamen eingeben. Dies ist hilfreich für Anwendungen wie E-Commerce-Websites, auf denen Produkte umfangreich und vielfältig sind.

2. **Tag-Auswahl und Eingabe**: In Anwendungen, die Inhalte taggen, kann eine ComboBox eine hervorragende Wahl sein. Benutzer können aus einer Liste vorhandener Tags auswählen oder benutzerdefinierte Tags eingeben, indem sie diese eintippen. Dies ist nützlich für die Organisation und Kategorisierung von Inhalten. Beispiele für solche Tags sind:
    >- Projekt-Tags: In einem Projektmanagement-Tool können Benutzer Labels oder Tags (z. B. "Dringend", "In Bearbeitung", "Abgeschlossen") auswählen, um Aufgaben oder Projekte zu kategorisieren, und sie können bei Bedarf benutzerdefinierte Labels erstellen.
    >- Rezept-Zutaten: In einer Koch- oder Rezept-App können Benutzer Zutaten aus einer Liste auswählen (z. B. "Tomaten", "Zwiebeln", "Hühnchen") oder ihre eigenen Zutaten für benutzerdefinierte Rezepte hinzufügen.
    >- Standort-Tags: In einer Mapping- oder Geotagging-Anwendung können Benutzer vordefinierte Standort-Tags auswählen (z. B. "Strand", "Stadt", "Park") oder benutzerdefinierte Tags erstellen, um bestimmte Orte auf einer Karte zu kennzeichnen.

3. **Daten-Eingabe mit vorgeschlagenen Werten**: In Daten-Eingabeformularen kann eine ComboBox verwendet werden, um die Eingabe zu beschleunigen, indem eine Liste vorgeschlagener Werte basierend auf der Benutzereingabe bereitgestellt wird. Dies hilft den Benutzern, Daten genau und effizient einzugeben.

    :::tip
    Die `ComboBox` sollte verwendet werden, wenn Benutzern das Eingeben benutzerdefinierter Werte erlaubt ist. Wenn nur vordefinierte Werte gewünscht sind, verwenden Sie stattdessen eine [`ChoiceBox`](./choice-box.md).
    :::

## Benutzerdefinierter Wert {#custom-value}

Die Änderung der Eigenschaft for die benutzerdefinierte Werte ermöglicht die Kontrolle darüber, ob ein Benutzer den Wert im Eingabefeld der `ComboBox`-Komponente ändern darf oder nicht. Wenn `true`, was der Standardwert ist, kann ein Benutzer den Wert ändern. Wenn auf `false` gesetzt, kann der Benutzer den Wert nicht ändern. Dies kann mit der <JavadocLink type="foundation" location="com/webforj/component/list/ComboBox" code='true' suffix='#setAllowCustomValue(boolean)'>setAllowCustomValue()</JavadocLink>-Methode eingestellt werden.

<ComponentDemo
path='/webforj/comboboxcustomvalue'
files={['src/main/java/com/webforj/samples/views/lists/combobox/ComboBoxCustomValueView.java']}
height='200px'
/>

## Platzhalter {#placeholder}

Ein Platzhalter kann für eine `ComboBox` gesetzt werden, der im Textfeld der Komponente angezeigt wird, wenn es leer ist, um Benutzer zur gewünschten Eingabe im Feld zu animieren. Dies kann mit der <JavadocLink type="foundation" location="com/webforj/component/list/ComboBox" code='true' suffix='#setPlaceholder(java.lang.String)'>setPlaceholder()</JavadocLink>-Methode geschehen.

<ComponentDemo
path='/webforj/comboboxplaceholder'
files={['src/main/java/com/webforj/samples/views/lists/combobox/ComboBoxPlaceholderView.java']}
height='200px'
/>

## Dropdown-Typ {#dropdown-type}

Durch die Verwendung der <JavadocLink type="foundation" location="com/webforj/component/list/DwcSelectDropdown" code='true' suffix='#setDropdownType(java.lang.String)'>setDropdownType()</JavadocLink>-Methode wird ein Wert für das `type`-Attribut einer `ComboBox` zugewiesen und ein entsprechender Wert für das `data-dropdown-for`-Attribut im Dropdown der `ComboBox`. Dies ist beim Styling hilfreich, da das Dropdown beim Öffnen aus seiner aktuellen Position im DOM herausgenommen und an das Ende des Seitenkörpers verschoben wird.

Diese Abtrennung schafft eine Situation, in der es schwierig wird, das Dropdown direkt mit CSS- oder Shadow-Part-Selektoren von der übergeordneten Komponente anzusprechen, es sei denn, Sie verwenden das Dropdown-Typ-Attribut.

Im folgenden Demo wird der Dropdown-Typ gesetzt und im CSS-Dokument verwendet, um das Dropdown auszuwählen und die Hintergrundfarbe zu ändern.

<ComponentDemo
path='/webforj/comboboxdropdowntype'
files={[
  'src/main/java/com/webforj/samples/views/lists/combobox/ComboBoxDropdownTypeView.java',
  'src/main/resources/static/css/lists/combobox/comboBoxDropDownType.css',
]}
height='250px'
/>

## Maximale Zeilenanzahl {#max-row-count}

Standardmäßig wird die Anzahl der in der Dropdown-Liste einer `ComboBox` angezeigten Zeilen erhöht, um den Inhalt anzupassen. Die Verwendung der <JavadocLink type="foundation" location="com/webforj/component/list/DwcSelectDropdown" code='true' suffix='#setMaxRowCount(int)'>setMaxRowCount()</JavadocLink>-Methode ermöglicht die Kontrolle über die Anzahl der angezeigten Elemente.

:::caution
Die Verwendung einer Zahl, die kleiner oder gleich 0 ist, führt dazu, dass diese Eigenschaft zurückgesetzt wird.
:::

<ComponentDemo
path='/webforj/comboboxmaxrow'
files={['src/main/java/com/webforj/samples/views/lists/combobox/ComboBoxMaxRowView.java']}
height='450px'
/>

## Öffnen und Schließen {#opening-and-closing}

Die Sichtbarkeit der Optionen für eine `ComboBox` kann programmgesteuert mit den Methoden `open()` und `close()` gesteuert werden. Diese Methoden ermöglichen es Ihnen, die Liste der auswählbaren Optionen anzuzeigen oder bei Bedarf auszublenden, was eine größere Flexibilität bei der Verwaltung des Verhaltens einer `ComboBox` bietet.

Darüber hinaus verfügt webforJ über Ereignislistener für das Schließen der `ComboBox` und das Öffnen, sodass Sie spezifische Aktionen auslösen können.

```Java
//Fokussieren oder öffnen Sie die nächste Komponente in einem Formular
ComboBox university = new ComboBox("Universität");
ComboBox major = new ComboBox("Fach");
Button submit = new Button("Absenden");

//... Listen von Universitäten und Studiengängen hinzufügen

university.onClose( e ->{
  major.open();
});

major.onClose( e ->{
  submit.focus();
});
```

## Öffnungsdimensionen {#opening-dimensions}

Die `ComboBox`-Komponente verfügt über Methoden, mit denen die Dimensionen des Dropdowns manipuliert werden können. Die **maximale Höhe** und **minimale Breite** des Dropdowns können mit den <JavadocLink type="foundation" location="com/webforj/component/list/DwcSelectDropdown" code='true' suffix='#setOpenHeight(int)'>setOpenHeight()</JavadocLink>- und <JavadocLink type="foundation" location="com/webforj/component/list/DwcSelectDropdown" code='true' suffix='#setOpenWidth(int)'>setOpenWidth()</JavadocLink>-Methoden festgelegt werden.

:::tip
Das Übergeben eines `String`-Werts an eine dieser Methoden ermöglicht es, [jedes gültige CSS-Einheit](https://developer.mozilla.org/en-US/docs/Learn/CSS/Building_blocks/Values_and_units) anzuwenden, wie Pixel, Sichtfenstermassen oder andere gültige Regeln. Das Übergeben einer `int`-Zahl legt den übergebenen Wert in Pixeln fest.
:::

## Hervorhebung {#highlighting}

Beim Arbeiten mit einer `ComboBox` können Sie anpassen, wann der Text hervorgehoben wird, je nachdem, wie die Komponente den Fokus erhält. Diese Funktion kann Eingabefehler reduzieren, wenn Benutzer Formulare ausfüllen, und kann das gesamte Navigationserlebnis verbessern. Ändern Sie das Hervorhebungsverhalten mit der `setHighlightOnFocus()`-Methode mit einer der integrierten `HasHighlightOnFocus.Behavior`-Enums:

- `ALL`
Inhalte der Komponente werden automatisch hervorgehoben, wenn die Komponente den Fokus erhält.
- `FOCUS`
Inhalte der Komponente werden automatisch hervorgehoben, wenn die Komponente programmgesteuert den Fokus erhält.
- `FOCUS_OR_KEY`
Inhalte der Komponente werden automatisch hervorgehoben, wenn die Komponente programmgesteuert den Fokus erhält oder durch das Tabben hinein.
- `FOCUS_OR_MOUSE`
Inhalte der Komponente werden automatisch hervorgehoben, wenn die Komponente programmgesteuert den Fokus erhält oder durch Klicken mit der Maus hinein.
- `KEY`
Inhalte der Komponente werden automatisch hervorgehoben, wenn die Komponente den Fokus durch Tabben erhält.
- `KEY_MOUSE`
Inhalte der Komponente werden automatisch hervorgehoben, wenn die Komponente den Fokus durch Tabben oder Klicken mit der Maus erhält.
- `MOUSE`
Inhalte der Komponente werden automatisch hervorgehoben, wenn die Komponente den Fokus durch Klicken mit der Maus erhält.
- `NONE`
Inhalte der Komponente werden niemals automatisch hervorgehoben, wenn die Komponente den Fokus erhält.

:::note
Wenn Inhalte beim Verlust des Fokus hervorgehoben wurden, werden sie beim Wiedererlangen des Fokus unabhängig vom eingestellten Verhalten erneut hervorgehoben.
:::

## Präfix und Suffix {#prefix-and-suffix}

Slots bieten flexible Optionen zur Verbesserung der Funktionalität einer `ComboBox`. Sie können Icons, Labels, Ladeanzeigen, Optionen zum Löschen/Zurücksetzen, Avatar/profilbilder und andere nützliche Komponenten innerhalb einer `ComboBox` verschachteln, um den Benutzern die beabsichtigte Bedeutung weiter zu verdeutlichen. Die `ComboBox` hat zwei Slots: die `prefix`- und `suffix`-Slots. Verwenden Sie die `setPrefixComponent()`- und `setSuffixComponent()`-Methoden, um verschiedene Komponenten vor und nach den Optionen innerhalb einer `ComboBox` einzufügen.

```java
ComboBox comboBox = new ComboBox();
  comboBox.setPrefixComponent(TablerIcon.create("box"));
  comboBox.setSuffixComponent(TablerIcon.create("box"));
```

## Styling {#styling}

<TableBuilder name="ComboBox" />

## Beste Praktiken {#best-practices}

Um eine optimale Benutzererfahrung bei der Verwendung der `ComboBox`-Komponente zu gewährleisten, sollten Sie die folgenden bewährten Praktiken berücksichtigen:

1. **Vorab häufig verwendete Werte**: Wenn es häufig verwendete Elemente gibt, laden Sie diese vorab in die `ComboBox`-Liste. Dies beschleunigt die Auswahl häufig gewählter Elemente und fördert die Konsistenz.

2. **Benutzerfreundliche Labels**: Stellen Sie sicher, dass die angezeigten Bezeichnungen für jede Option benutzerfreundlich und selbsterklärend sind. Achten Sie darauf, dass die Benutzer den Zweck jeder Wahl leicht verstehen können.

3. **Validierung**: Implementieren Sie eine Eingabevalidierung zur Handhabung benutzerdefinierter Einträge. Überprüfen Sie die Daten auf Genauigkeit und Konsistenz. Sie möchten möglicherweise Korrekturen oder Bestätigungen für mehrdeutige Eingaben vorschlagen.

4. **Standardauswahl**: Setzen Sie eine Standardauswahl, insbesondere wenn es eine häufige oder empfohlene Wahl gibt. Dies verbessert die Benutzererfahrung, indem die Notwendigkeit zusätzlicher Klicks reduziert wird.

5. **ComboBox vs. Andere Listenkomponenten**: Eine `ComboBox` ist die beste Wahl, wenn Sie eine einzelne Eingabe vom Benutzer benötigen und ihm vordefinierte Auswahlmöglichkeiten sowie die Möglichkeit zur Anpassung seiner Eingabe bieten möchten. Eine andere Listenkomponente könnte besser sein, wenn Sie folgende Verhaltensweisen benötigen:
    - Mehrfachauswahl und Anzeige aller Elemente auf einmal: [ListBox](./list-box.md)
    - Benutzerdefinierte Eingabe verhindern: [ChoiceBox](./choice-box.md)
