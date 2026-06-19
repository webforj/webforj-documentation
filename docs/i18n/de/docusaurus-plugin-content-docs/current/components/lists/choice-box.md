---
sidebar_position: 5
title: ChoiceBox
slug: choicebox
description: >-
  Pick a single value from a fixed set with the ChoiceBox dropdown, including
  dropdown type styling, max row count, and keyboard navigation.
_i18n_hash: cf4d092418fcf1f593b8b8d00a47344b
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-choicebox" />
<DocChip chip='since' label='23.05' />
<JavadocLink type="foundation" location="com/webforj/component/list/ChoiceBox" top='true'/>

Die `ChoiceBox`-Komponente präsentiert eine Dropdown-Liste, aus der Benutzer eine einzelne Option auswählen können. Wenn eine Auswahl getroffen wird, wird der gewählte Wert im Button angezeigt. Sie eignet sich gut, wenn Benutzer aus einer festen Menge vordefinierter Auswahlmöglichkeiten wählen müssen, und die Pfeiltasten können verwendet werden, um durch die Liste zu navigieren.

<!-- INTRO_END -->

## Usos {#usages}

<ParentLink parent="List" />

`ChoiceBox`-Komponenten werden für verschiedene Zwecke verwendet, wie z.B. das Auswählen von Elementen aus einem Menü, das Wählen aus einer Liste von Kategorien oder das Picken von Optionen aus vordefinierten Mengen. Sie bieten eine organisierte und visuell ansprechende Möglichkeit für Benutzer, Auswahlen zu treffen, insbesondere wenn mehrere Optionen verfügbar sind. Zu den häufigen Anwendungen gehören:

1. **Benutzerauswahl von Optionen**: Der Hauptzweck einer `ChoiceBox` besteht darin, Benutzern die Auswahl einer einzelnen Option aus einer Liste zu ermöglichen. Dies ist wertvoll in Anwendungen, die von Benutzern erfordern, Entscheidungen zu treffen, wie z.B.:
    - Auswählen aus einer Liste von Kategorien
    - Wählen von Optionen aus vordefinierten Sets

2. **Formulareingaben**: Bei der Gestaltung von Formularen, die von Benutzern spezifische Optionen verlangen, vereinfacht die `ChoiceBox` den Auswahlprozess. Ob es sich um die Auswahl eines Landes, Bundesstaates oder einer anderen Option aus einer vordefinierten Liste handelt, die `ChoiceBox` vereinfacht den Eingabeprozess.

3. **Filtern und Sortieren**: `ChoiceBox` kann für Filter- und Sortieraufgaben in Anwendungen verwendet werden. Benutzer können Filterkriterien oder Sortierpräferenzen aus einer Liste auswählen, wodurch die Organisation und Navigation von Daten erleichtert wird.

4. **Konfiguration und Einstellungen**: Wenn Ihre Anwendung Einstellungen oder Konfigurationsoptionen enthält, bietet die `ChoiceBox` eine intuitive Möglichkeit für Benutzer, Präferenzen anzupassen. Benutzer können Einstellungen aus einer Liste auswählen, was es einfach macht, die Anwendung an ihre Bedürfnisse anzupassen.

:::tip
Die `ChoiceBox` ist dafür gedacht, wenn eine vordefinierte Anzahl von Optionen verfügbar ist, und benutzerdefinierte Optionen sollten nicht erlaubt sein oder hinzugefügt werden. Wenn das Eingeben von benutzerdefinierten Werten gewünscht ist, verwenden Sie stattdessen eine [`ComboBox`](./combo-box.md).
:::

## Dropdown-Typ {#dropdown-type}

Durch die Verwendung der <JavadocLink type="foundation" location="com/webforj/component/list/DwcSelectDropdown" code='true' suffix='#setDropdownType(java.lang.String)'>setDropdownType()</JavadocLink>-Methode wird ein Wert für das `type`-Attribut einer `ChoiceBox` zugewiesen und ein entsprechender Wert für das `data-dropdown-for`-Attribut im Dropdown der `ChoiceBox`. Dies ist hilfreich für das Styling, da das Dropdown beim Öffnen aus seiner aktuellen Position im DOM entfernt und ans Ende des Seitenkörpers verlagert wird.

<!-- ![example type](/img/components/_images/choicebox/type.png)
![example type](/img/components/_images/choicebox/type_zoomed.png) -->

Diese Trennung schafft eine Situation, in der das direkte Anvisieren des Dropdowns mit CSS oder Shadow-Part-Selektoren aus der übergeordneten Komponente heraus herausfordernd wird, es sei denn, Sie verwenden das Dropdown-Typ-Attribut.

Im folgenden Demo wird der Dropdown-Typ gesetzt und in der CSS-Datei verwendet, um eine Option zu vergrößern, wenn Sie mit der Maus darüber fahren.

<ComponentDemo
path='/webforj/choiceboxdropdowntype'
files={[
  'src/main/java/com/webforj/samples/views/lists/choicebox/ChoiceboxDropdownTypeView.java',
  'src/main/resources/static/css/lists/combobox/comboBoxDropDownType.css',
]}
height='250px'
/>

## Maximale Zeilenanzahl {#max-row-count}

Standardmäßig wird die Anzahl der in der Dropdown-Liste einer `ChoiceBox` angezeigten Zeilen erhöht, um den Inhalt anzupassen. Die Verwendung der <JavadocLink type="foundation" location="com/webforj/component/list/DwcSelectDropdown" code='true' suffix='#setMaxRowCount(int)'>setMaxRowCount()</JavadocLink>-Methode ermöglicht eine Kontrolle darüber, wie viele Elemente angezeigt werden.

:::tip
Die Verwendung einer Zahl, die kleiner oder gleich 0 ist, führt dazu, dass diese Eigenschaft zurückgesetzt wird.
:::

<ComponentDemo
path='/webforj/choiceboxmaxrow'
files={['src/main/java/com/webforj/samples/views/lists/choicebox/ChoiceboxMaxRowView.java']}
height='450px'
/>

## Öffnen und Schließen {#opening-and-closing}

Die Sichtbarkeit der Optionen für eine `ChoiceBox` kann programmgesteuert mit den `open()`- und `close()`-Methoden gesteuert werden. Diese Methoden ermöglichen es Ihnen, die Liste der Auswahlmöglichkeiten anzuzeigen oder nach Bedarf auszublenden, was eine größere Flexibilität bei der Verwaltung des Verhaltens einer `ChoiceBox` bietet.

Zusätzlich hat webforJ Ereignis-Listener für das Schließen und Öffnen der `ChoiceBox`, die Ihnen mehr Kontrolle geben, um spezifische Aktionen auszulösen.

```Java
//Fokussieren oder öffnen Sie die nächste Komponente in einem Formular
ChoiceBox universitet = new ChoiceBox("Universität");
ChoiceBox fachrichtung = new ChoiceBox("Fachrichtung");
Button einreichen = new Button("Einreichen");

//... Fügen Sie Listen von Universitäten und Fachrichtungen hinzu

universitet.onClose( e ->{
  fachrichtung.focus();
});

fachrichtung.onClose( e ->{
  einreichen.focus();
});
```

## Öffnungsdimensionen {#opening-dimensions}

Die `ChoiceBox`-Komponente verfügt über Methoden, die die Manipulation der Dropdown-Dimensionen ermöglichen. Die **maximale Höhe** und **minimale Breite** des Dropdowns können mit den <JavadocLink type="foundation" location="com/webforj/component/list/DwcSelectDropdown" code='true' suffix='#setOpenHeight(int)'>setOpenHeight()</JavadocLink>- und <JavadocLink type="foundation" location="com/webforj/component/list/DwcSelectDropdown" code='true' suffix='#setOpenWidth(int)'>setOpenWidth()</JavadocLink>-Methoden festgelegt werden.

:::tip
Das Übergeben eines `String`-Werts an eine dieser Methoden ermöglicht die Anwendung von [irgendeiner gültigen CSS-Einheit](https://developer.mozilla.org/en-US/docs/Learn/CSS/Building_blocks/Values_and_units), wie Pixel, Sichtfelddimensionen oder andere gültige Regeln. Das Übergeben eines `int` setzt den übergebenen Wert in Pixeln.
:::

## Präfix und Suffix {#prefix-and-suffix}

Slots bieten flexible Optionen zur Verbesserung der Fähigkeit einer `ChoiceBox`. Sie können Symbole, Labels, Ladeanimationen, die Möglichkeit zum Löschen/Zurücksetzen, Avatar-/Profilbilder und andere nützliche Komponenten innerhalb einer `ChoiceBox` verschachteln, um den Benutzern die beabsichtigte Bedeutung weiter zu verdeutlichen. Die `ChoiceBox` hat zwei Slots: den `prefix`- und den `suffix`-Slot. Verwenden Sie die Methoden `setPrefixComponent()` und `setSuffixComponent()`, um verschiedene Komponenten vor und nach der angezeigten Option innerhalb einer `ChoiceBox` einzufügen.

```java
ComboBox choiceBox = new ChoiceBox());
  choiceBox.setPrefixComponent(TablerIcon.create("box"));
  choiceBox.setSuffixComponent(TablerIcon.create("box"));
```

## Styling {#styling}

<TableBuilder name="ChoiceBox" />

## Best Practices {#best-practices}

Um ein optimales Benutzererlebnis bei der Verwendung der `ChoiceBox`-Komponente zu gewährleisten, beachten Sie die folgenden Best Practices:

1. **Klarheit und begrenzte Optionen**: Halten Sie die Liste der Auswahlmöglichkeiten so prägnant wie möglich und relevant für die Aufgabe des Benutzers. Eine `ChoiceBox` eignet sich hervorragend für die Präsentation einer klaren Liste von Optionen.

2. **Benutzerfreundliche Labels**: Stellen Sie sicher, dass die angezeigten Labels für jede Option benutzerfreundlich und selbsterklärend sind. Sorgen Sie dafür, dass die Benutzer den Zweck jeder Wahl leicht verstehen können.

3. **Standardauswahl**: Setzen Sie eine Standardauswahl, wenn die `ChoiceBox` zunächst angezeigt wird. Dies gewährleistet eine vorab ausgewählte Option und reduziert die erforderlichen Interaktionen zur Auswahl.

4. **ChoiceBox vs. andere Listenkomponenten**: Eine `ChoiceBox` ist die beste Wahl, wenn Sie die Benutzereingabe auf eine einzige Auswahl aus einer Liste vordefinierter Optionen beschränken müssen. Eine andere Listenkomponente kann besser geeignet sein, wenn Sie folgende Verhaltensweisen benötigen:
    - Mehrfachauswahl und Anzeige aller Elemente auf einmal: [`ListBox`](./list-box.md)
    - Erlauben Sie benutzerdefinierte Eingaben: [`ComboBox`](./combo-box.md)
