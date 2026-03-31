---
sidebar_position: 5
title: ChoiceBox
slug: choicebox
_i18n_hash: 1da4824585c11423d72c2b75b451a6db
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-choicebox" />
<DocChip chip='since' label='23.05' />
<JavadocLink type="foundation" location="com/webforj/component/list/ChoiceBox" top='true'/>

Die `ChoiceBox`-Komponente präsentiert eine Dropdown-Liste, aus der Benutzer eine einzige Option auswählen können. Wenn eine Auswahl getroffen wird, wird der gewählte Wert im Button angezeigt. Sie ist ideal, wenn Benutzer aus einer festen Menge vordefinierter Auswahlmöglichkeiten wählen müssen, und die Pfeiltasten können verwendet werden, um durch die Liste zu navigieren.

<!-- INTRO_END -->

## Usos {#usages}

<ParentLink parent="List" />

`ChoiceBox`-Komponenten werden für verschiedene Zwecke verwendet, z. B. um Elemente aus einem Menü auszuwählen, aus einer Liste von Kategorien zu wählen oder Optionen aus vordefinierten Sätzen auszuwählen. Sie bieten eine organisierte und ansprechend gestaltete Möglichkeit für Benutzer, Auswahl zu treffen, insbesondere wenn mehrere Optionen verfügbar sind. Zu den häufigen Anwendungen gehören:

1. **Benutzerauswahl von Optionen**: Der Hauptzweck einer `ChoiceBox` besteht darin, Benutzern zu ermöglichen, eine einzelne Option aus einer Liste auszuwählen. Dies ist wertvoll in Anwendungen, die von den Benutzern verlangen, Entscheidungen zu treffen, wie z. B.:
    - Auswählen aus einer Liste von Kategorien
    - Optionen aus vordefinierten Sätzen auswählen

2. **Formulareingaben**: Bei der Gestaltung von Formularen, die von Benutzern spezifische Optionen erfordern, vereinfacht die `ChoiceBox` den Auswahlprozess. Egal, ob es sich um die Auswahl eines Landes, eines Bundesstaates oder einer anderen Option aus einer vordefinierten Liste handelt, die `ChoiceBox` optimiert den Eingabeprozess.

3. **Filterung und Sortierung**: `ChoiceBox` kann für Filter- und Sortieraufgaben in Anwendungen verwendet werden. Benutzer können Auswahlkriterien oder Sortierungspräferenzen aus einer Liste auswählen, was die Organisation und Navigation von Daten erleichtert.

4. **Konfiguration und Einstellungen**: Wenn Ihre Anwendung Einstellungen oder Konfigurationsoptionen umfasst, bietet die `ChoiceBox` eine intuitive Möglichkeit für Benutzer, Präferenzen anzupassen. Benutzer können Einstellungen aus einer Liste auswählen, was es einfach macht, die Anwendung an ihre Bedürfnisse anzupassen.

:::tip
Die `ChoiceBox` ist dafür vorgesehen, wenn eine vordefinierte Anzahl von Optionen verfügbar ist, und benutzerdefinierte Optionen nicht erlaubt oder einbezogen werden sollten. Wenn das Eingeben benutzerdefinierter Werte gewünscht wird, verwenden Sie stattdessen eine [`ComboBox`](./combo-box.md).
:::

## Dropdown-Typ {#dropdown-type}

Die Verwendung der <JavadocLink type="foundation" location="com/webforj/component/list/DwcSelectDropdown" code='true' suffix='#setDropdownType(java.lang.String)'>setDropdownType()</JavadocLink>-Methode weist einen Wert dem `type`-Attribut einer `ChoiceBox` zu und einen entsprechenden Wert für das `data-dropdown-for`-Attribut im Dropdown der `ChoiceBox`. Dies ist hilfreich für das Styling, da das Dropdown aus seiner aktuellen Position im DOM entfernt und am Ende des Seiteninhalts verlagert wird, wenn es geöffnet wird.

<!-- ![example type](/img/components/_images/choicebox/type.png)
![example type](/img/components/_images/choicebox/type_zoomed.png) -->

Diese Trennung macht es schwierig, das Dropdown mit CSS oder Shadow-Part-Selektoren aus der übergeordneten Komponente direkt anzusprechen, es sei denn, Sie verwenden das Dropdown-Typ-Attribut.

Im Demo unten wird der Dropdown-Typ gesetzt und in der CSS-Datei verwendet, um das Dropdown auszuwählen und die Hintergrundfarbe zu ändern.

<ComponentDemo 
path='/webforj/choiceboxdropdowntype?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/lists/choicebox/ChoiceboxDropdownTypeView.java'
cssURL='/css/lists/combobox/comboBoxDropDownType.css'
height='250px'
/>

## Maximale Zeilenanzahl {#max-row-count}

Standardmäßig wird die Anzahl der in der Dropdown-Liste einer `ChoiceBox` angezeigten Zeilen erhöht, um den Inhalt anzupassen. Durch die Verwendung der <JavadocLink type="foundation" location="com/webforj/component/list/DwcSelectDropdown" code='true' suffix='#setMaxRowCount(int)'>setMaxRowCount()</JavadocLink>-Methode kann jedoch kontrolliert werden, wie viele Elemente angezeigt werden.

:::tip
Die Verwendung einer Zahl, die kleiner oder gleich 0 ist, führt dazu, dass diese Eigenschaft deaktiviert wird.
:::

<ComponentDemo 
path='/webforj/choiceboxmaxrow?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/lists/choicebox/ChoiceboxMaxRowView.java'
height='450px'
/>

## Öffnen und Schließen {#opening-and-closing}

Die Sichtbarkeit der Optionen für eine `ChoiceBox` kann programmgesteuert mit den Methoden `open()` und `close()` gesteuert werden. Diese Methoden ermöglichen es Ihnen, die Liste der Optionen zur Auswahl anzuzeigen oder bei Bedarf auszublenden, was eine höhere Flexibilität bei der Verwaltung des Verhaltens einer `ChoiceBox` bietet.

Zusätzlich hat webforJ Ereignislistener für den Fall, dass die `ChoiceBox` geschlossen wird und wenn sie geöffnet wird, wodurch Sie mehr Kontrolle haben, um bestimmte Aktionen auszulösen.

```Java
//Fokus oder öffne die nächste Komponente in einem Formular
ChoiceBox university = new ChoiceBox("Universität");
ChoiceBox major = new ChoiceBox("Studienrichtung");
Button submit = new Button("Absenden");

//... Liste der Universitäten und Studienrichtungen hinzufügen

university.onClose( e ->{
  major.focus();
});

major.onClose( e ->{
  submit.focus();
});
```

## Öffnungsdimensionen {#opening-dimensions}

Die `ChoiceBox`-Komponente hat Methoden, die eine Manipulation der Dropdown-Dimensionen ermöglichen. Die **maximale Höhe** und **minimale Breite** des Dropdowns können mit den Methoden <JavadocLink type="foundation" location="com/webforj/component/list/DwcSelectDropdown" code='true' suffix='#setOpenHeight(int)'>setOpenHeight()</JavadocLink> und <JavadocLink type="foundation" location="com/webforj/component/list/DwcSelectDropdown" code='true' suffix='#setOpenWidth(int)'>setOpenWidth()</JavadocLink> festgelegt werden.

:::tip
Das Übergeben eines `String`-Wertes an eine dieser Methoden ermöglicht die Anwendung von [beliebigen gültigen CSS-Einheiten](https://developer.mozilla.org/en-US/docs/Learn/CSS/Building_blocks/Values_and_units), wie z. B. Pixel, Ansichtsdimensionen oder andere gültige Regeln. Das Übergeben einer `int` setzt den Wert in Pixel.
:::

## Präfix und Suffix {#prefix-and-suffix}

Slots bieten flexible Optionen zur Verbesserung der Fähigkeiten einer `ChoiceBox`. Sie können Symbole, Beschriftungen, Ladeanimationen, Löschen/Zurücksetzen, Avatar-Profilbilder und andere nützliche Komponenten innerhalb einer `ChoiceBox` einfügen, um den Benutzern die beabsichtigte Bedeutung weiter zu verdeutlichen. Die `ChoiceBox` hat zwei Slots: den `prefix`- und den `suffix`-Slot. Verwenden Sie die Methoden `setPrefixComponent()` und `setSuffixComponent()`, um verschiedene Komponenten vor und nach der angezeigten Option innerhalb einer `ChoiceBox` einzufügen.

```java
ComboBox choiceBox = new ChoiceBox());
  choiceBox.setPrefixComponent(TablerIcon.create("box"));
  choiceBox.setSuffixComponent(TablerIcon.create("box"));
```

## Styling {#styling}

<TableBuilder name="ChoiceBox" />

## Beste Praktiken {#best-practices}

Um eine optimale Benutzererfahrung beim Einsatz der `ChoiceBox`-Komponente sicherzustellen, sollten folgende bewährte Praktiken berücksichtigt werden:

1. **Klare und begrenzte Optionen**: Halten Sie die Liste der Auswahlmöglichkeiten klar und relevant für die Aufgabe des Benutzers. Eine `ChoiceBox` eignet sich ideal, um eine klare Liste von Optionen anzubieten.

2. **Benutzerfreundliche Beschriftungen**: Stellen Sie sicher, dass die angezeigten Beschriftungen für jede Option benutzerfreundlich und selbsterklärend sind. Achten Sie darauf, dass die Benutzer den Zweck jeder Wahl leicht verstehen können.

3. **Standardauswahl**: Setzen Sie eine Standardauswahl, wenn die `ChoiceBox` zum ersten Mal angezeigt wird. Dies gewährleistet eine vorausgewählte Option und reduziert die Anzahl der erforderlichen Interaktionen, um eine Wahl zu treffen.

4. **ChoiceBox vs. Andere Listenkomponenten**: Eine `ChoiceBox` ist die beste Wahl, wenn Sie die Benutzereingabe auf eine einzige Auswahl aus einer Liste vordefinierter Optionen beschränken möchten. Eine andere Listenkomponente kann besser geeignet sein, wenn Sie die folgenden Verhaltensweisen benötigen:
    - Mehrfachauswahl und Anzeige aller Elemente auf einmal: [`ListBox`](./list-box.md)
    - Benutzerdefinierte Eingaben zulassen: [`ComboBox`](./combo-box.md)
