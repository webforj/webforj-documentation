---
sidebar_position: 10
title: ComboBox
slug: combobox
description: >-
  Combine a dropdown list with a text input in the ComboBox to let users select
  preset items or type custom values with placeholder support.
_i18n_hash: 9e5c0f54f07f604ee91a84210189ca30
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-combobox" />
<DocChip chip='since' label='23.05' />
<JavadocLink type="foundation" location="com/webforj/component/list/ComboBox" top='true'/>

Die `ComboBox`-Komponente kombiniert eine Dropdown-Liste mit einem Texteingabefeld, sodass Benutzer entweder aus vordefinierten Optionen auswählen oder einen benutzerdefinierten Wert eingeben können. Wenn benutzerdefinierte Eingaben zusammen mit einer Reihe von vorgeschlagenen Optionen erlaubt werden müssen, füllt sie die Lücke, die `ChoiceBox` nicht abdeckt.

<!-- INTRO_END -->

## Usages {#usages}

<ParentLink parent="List" />

Die ComboBox-Komponente ist ein vielseitiges Eingabeelement, das die Funktionen einer Dropdown-Liste und eines Texteingabefelds kombiniert. Sie ermöglicht es den Benutzern, Elemente aus einer vordefinierten Liste auszuwählen oder nach Bedarf benutzerdefinierte Werte einzugeben. Dieser Abschnitt untersucht gängige Anwendungen der ComboBox-Komponente in verschiedenen Szenarien:

1. **Produkt Suche und Eingabe**: Verwenden Sie eine ComboBox, um eine Produkt-Such- und Eingabefunktion zu implementieren. Benutzer können entweder ein Produkt aus einer vordefinierten Liste auswählen oder einen benutzerdefinierten Produktnamen eingeben. Dies ist hilfreich für Anwendungen wie E-Commerce-Seiten, auf denen Produkte zahlreich und vielfältig sind.

2. **Tag-Auswahl und Eingabe**: In Anwendungen, die das Tagging von Inhalten betreffen, kann eine ComboBox eine ausgezeichnete Wahl sein. Benutzer können aus einer Liste bestehender Tags auswählen oder benutzerdefinierte Tags hinzufügen, indem sie diese eingeben. Dies ist nützlich, um Inhalte zu organisieren und zu kategorisieren. Beispiele für solche Tags sind:
    >- Projektlabel: In einem Projektmanagement-Tool können Benutzer Labels oder Tags (z.B. "Dringend", "In Bearbeitung", "Abgeschlossen") auswählen, um Aufgaben oder Projekte zu kategorisieren, und sie können nach Bedarf benutzerdefinierte Labels erstellen.
    >- Rezept-Zutaten: In einer Koch- oder Rezept-App können Benutzer Zutaten aus einer Liste auswählen (z.B. "Tomaten", "Zwiebeln", "Hühnchen") oder ihre eigenen Zutaten für benutzerdefinierte Rezepte hinzufügen.
    >- Standort-Tags: In einer Mapping- oder Geotagging-Anwendung können Benutzer vordefinierte Standort-Tags (z.B. "Strand", "Stadt", "Park") auswählen oder benutzerdefinierte Tags erstellen, um bestimmte Orte auf einer Karte zu markieren.

3. **Daten Eingabe mit vorgeschlagenen Werten**: In Dateneingabeformularen kann eine ComboBox verwendet werden, um die Eingabe zu beschleunigen, indem eine Liste von vorgeschlagenen Werten basierend auf der Benutzereingabe bereitgestellt wird. Dies hilft den Benutzern, Daten genau und effizient einzugeben.

    :::tip
    Die `ComboBox` sollte verwendet werden, wenn Benutzer benutzerdefinierte Werte eingeben können. Wenn nur voreingestellte Werte gewünscht sind, verwenden Sie stattdessen eine [`ChoiceBox`](./choice-box.md).
    :::

## Custom value {#custom-value}

Die Änderung der benutzerdefinierten Wert-Eigenschaft ermöglicht es, zu steuern, ob ein Benutzer den Wert im Eingabefeld der `ComboBox`-Komponente ändern kann oder nicht. Wenn `true`, was der Standard ist, kann der Benutzer den Wert ändern. Wenn auf `false` gesetzt, kann der Benutzer den Wert nicht ändern. Dies kann mit der <JavadocLink type="foundation" location="com/webforj/component/list/ComboBox" code='true' suffix='#setAllowCustomValue(boolean)'>setAllowCustomValue()</JavadocLink>-Methode eingestellt werden.

<ComponentDemo
path='/webforj/comboboxcustomvalue'
files={['src/main/java/com/webforj/samples/views/lists/combobox/ComboBoxCustomValueView.java']}
height='200px'
/>

## Placeholder {#placeholder}

Ein Platzhalter kann für eine `ComboBox` festgelegt werden, der im Texteingabefeld der Komponente angezeigt wird, wenn es leer ist, um Benutzer zur gewünschten Eingabe im Feld aufzufordern. Dies kann mit der <JavadocLink type="foundation" location="com/webforj/component/list/ComboBox" code='true' suffix='#setPlaceholder(java.lang.String)'>setPlaceholder()</JavadocLink>-Methode erreicht werden.

<ComponentDemo
path='/webforj/comboboxplaceholder'
files={['src/main/java/com/webforj/samples/views/lists/combobox/ComboBoxPlaceholderView.java']}
height='200px'
/>

## Dropdown type {#dropdown-type}

Die Verwendung der <JavadocLink type="foundation" location="com/webforj/component/list/DwcSelectDropdown" code='true' suffix='#setDropdownType(java.lang.String)'>setDropdownType()</JavadocLink>-Methode weist den `type`-Attributwert einer `ComboBox` zu und einem entsprechenden Wert für das `data-dropdown-for`-Attribut im Dropdown der `ComboBox`. Dies ist hilfreich für das Styling, da das Dropdown bei Öffnung aus seiner aktuellen Position im DOM entfernt und ans Ende des Seitenkörpers verschoben wird.

Diese Abtrennung schafft eine Situation, in der das direkte Anvisieren des Dropdowns mit CSS oder Shadow-Part-Selektoren aus der übergeordneten Komponente heraus herausfordernd wird, es sei denn, Sie nutzen das Dropdown-Type-Attribut.

Im unteren Demo wird der Dropdown-Typ festgelegt und in der CSS-Datei verwendet, um eine Option zu vergrößern, wenn Sie darüber fahren.

<ComponentDemo
path='/webforj/comboboxdropdowntype'
files={[
  'src/main/java/com/webforj/samples/views/lists/combobox/ComboBoxDropdownTypeView.java',
  'src/main/frontend/css/lists/combobox/comboBoxDropDownType.css',
]}
height='250px'
/>

## Max row count {#max-row-count}

Standardmäßig wird die Anzahl der angezeigten Zeilen im Dropdown einer `ComboBox` erhöht, um den Inhalt passend zu machen. Die Verwendung der <JavadocLink type="foundation" location="com/webforj/component/list/DwcSelectDropdown" code='true' suffix='#setMaxRowCount(int)'>setMaxRowCount()</JavadocLink>-Methode ermöglicht die Kontrolle darüber, wie viele Elemente angezeigt werden.

:::caution
Die Verwendung einer Zahl, die kleiner oder gleich 0 ist, führt dazu, dass diese Eigenschaft deaktiviert wird.
:::

<ComponentDemo
path='/webforj/comboboxmaxrow'
files={['src/main/java/com/webforj/samples/views/lists/combobox/ComboBoxMaxRowView.java']}
height='450px'
/>

## Opening and closing {#opening-and-closing}

Die Sichtbarkeit der Optionen für eine `ComboBox` kann programmgesteuert mit den Methoden `open()` und `close()` gesteuert werden. Diese Methoden ermöglichen es Ihnen, die Liste der Optionen zur Auswahl anzuzeigen oder nach Bedarf auszublenden, um mehr Flexibilität beim Verwalten des Verhaltens einer `ComboBox` zu bieten.

Darüber hinaus verfügt webforJ über Ereignislistener, die aktiviert werden, wenn die `ComboBox` geschlossen und geöffnet wird, sodass Sie spezifische Aktionen auslösen können.

```Java
// Fokussieren oder öffnen Sie die nächste Komponente in einem Formular
ComboBox university = new ComboBox("Universität");
ComboBox major = new ComboBox("Fach");
Button submit = new Button("Absenden");

// ... Listen von Universitäten und Fächern hinzufügen

university.onClose( e ->{
  major.open();
});

major.onClose( e ->{
  submit.focus();
});
```

## Opening dimensions {#opening-dimensions}

Die `ComboBox`-Komponente verfügt über Methoden, die eine Manipulation der Dropdown-Abmessungen ermöglichen. Die **maximale Höhe** und **minimale Breite** des Dropdowns können mit den Methoden <JavadocLink type="foundation" location="com/webforj/component/list/DwcSelectDropdown" code='true' suffix='#setOpenHeight(int)'>setOpenHeight()</JavadocLink> und <JavadocLink type="foundation" location="com/webforj/component/list/DwcSelectDropdown" code='true' suffix='#setOpenWidth(int)'>setOpenWidth()</JavadocLink> festgelegt werden.

:::tip
Das Übergeben eines `String`-Wertes an eine dieser Methoden ermöglicht die Anwendung [irgendwelcher gültigen CSS-Einheiten](https://developer.mozilla.org/en-US/docs/Learn/CSS/Building_blocks/Values_and_units), wie Pixel, Ansichtsgrößen oder andere gültige Regeln. Das Übergeben einer `int`-Zahl setzt den übergebenen Wert in Pixeln.
:::

## Highlighting {#highlighting}

Bei der Arbeit mit einer `ComboBox` können Sie anpassen, wann der Text basierend auf der Art und Weise, wie die Komponente den Fokus erhält, hervorgehoben wird. Diese Funktion kann Eingabefehler verringern, wenn Benutzer Formulare ausfüllen, und die allgemeine Navigationserfahrung verbessern. Ändern Sie das Hervorhebungsverhalten mit der `setHighlightOnFocus()`-Methode mit einem der integrierten `HasHighlightOnFocus.Behavior`-Enums:

- `ALL`
Der Inhalt der Komponente wird immer automatisch hervorgehoben, wenn die Komponente den Fokus erhält.
- `FOCUS`
Der Inhalt der Komponente wird automatisch hervorgehoben, wenn die Komponente unter Programmsteuerung den Fokus erhält.
- `FOCUS_OR_KEY`
Der Inhalt der Komponente wird automatisch hervorgehoben, wenn die Komponente unter Programmsteuerung den Fokus erhält oder mit der Tabulatortaste hineingegangen wird.
- `FOCUS_OR_MOUSE`
Der Inhalt der Komponente wird automatisch hervorgehoben, wenn die Komponente unter Programmsteuerung den Fokus erhält oder durch Klicken mit der Maus hineingegangen wird.
- `KEY`
Der Inhalt der Komponente wird automatisch hervorgehoben, wenn die Komponente den Fokus erhält, indem in sie hinein tabuliert wird.
- `KEY_MOUSE`
Der Inhalt der Komponente wird automatisch hervorgehoben, wenn die Komponente den Fokus erhält, indem in sie hinein tabuliert wird oder durch Klicken mit der Maus hinein.
- `MOUSE`
Der Inhalt der Komponente wird automatisch hervorgehoben, wenn die Komponente durch Klicken mit der Maus hinein den Fokus erhält.
- `NONE`
Der Inhalt der Komponente wird niemals automatisch hervorgehoben, wenn die Komponente den Fokus erhält.

:::note
Wenn der Inhalt beim Verlust des Fokus hervorgehoben wurde, wird er nach dem Wiedererlangen des Fokus erneut hervorgehoben, unabhängig vom festgelegten Verhalten.
:::

## Prefix and suffix {#prefix-and-suffix}

Slots bieten flexible Optionen zur Verbesserung der Funktionalität einer `ComboBox`. Sie können Symbole, Labels, Lade-Spinnner, die Möglichkeit zum Löschen/Zurücksetzen, Avatar-/Profilbilder und andere nützliche Komponenten innerhalb einer `ComboBox` haben, um den beabsichtigten Sinn für die Benutzer weiter zu verdeutlichen. Die `ComboBox` verfügt über zwei Slots: die `prefix`- und `suffix`-Slots. Verwenden Sie die Methoden `setPrefixComponent()` und `setSuffixComponent()`, um verschiedene Komponenten vor und nach den Optionen innerhalb einer `ComboBox` einzufügen.

```java
ComboBox comboBox = new ComboBox());
  comboBox.setPrefixComponent(TablerIcon.create("box"));
  comboBox.setSuffixComponent(TablerIcon.create("box"));
```

## Styling {#styling}

<TableBuilder name="ComboBox" />

## Best practices {#best-practices}

Um eine optimale Benutzererfahrung bei der Verwendung der `ComboBox`-Komponente zu gewährleisten, sollten die folgenden Best Practices berücksichtigt werden:

1. **Häufige Werte vorladen**: Wenn es gängige oder häufig verwendete Elemente gibt, laden Sie diese in die Liste der `ComboBox` vor. Dies beschleunigt die Auswahl für häufig gewählte Elemente und fördert die Konsistenz.

2. **Benutzerfreundliche Labels**: Stellen Sie sicher, dass die angezeigten Labels für jede Option benutzerfreundlich und selbsterklärend sind. Stellen Sie sicher, dass die Benutzer den Zweck jeder Auswahl leicht verstehen können.

3. **Validierung**: Implementieren Sie eine Eingabevalidierung, um benutzerdefinierte Eingaben zu behandeln. Überprüfen Sie die Daten auf Genauigkeit und Konsistenz. Möglicherweise möchten Sie Korrekturen oder Bestätigungen für mehrdeutige Eingaben vorschlagen.

4. **Standardauswahl**: Legen Sie eine Standardauswahl fest, insbesondere wenn es eine gängige oder empfohlene Auswahl gibt. Dies verbessert die Benutzererfahrung, indem es die Notwendigkeit zusätzlicher Klicks verringert.

5. **ComboBox vs. Andere Listenkomponenten**: Eine `ComboBox` ist die beste Wahl, wenn Sie eine einzelne Eingabe vom Benutzer benötigen und Ihnen vorgegebene Auswahlmöglichkeiten sowie die Möglichkeit zur Anpassung ihrer Eingabe bieten möchten. Eine andere Listenkomponente könnte besser geeignet sein, wenn Sie folgende Verhaltensweisen benötigen:
    - Mehrfachauswahl und Anzeige aller Elemente auf einmal: [ListBox](./list-box.md)
    - Verhindern von benutzerdefinierten Eingaben: [ChoiceBox](./choice-box.md)
