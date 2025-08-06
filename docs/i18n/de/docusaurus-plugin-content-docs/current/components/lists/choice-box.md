---
sidebar_position: 5
title: ChoiceBox
slug: choicebox
_i18n_hash: d2e1c4ceeb6346a98d03075f19f5ee1c
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-choicebox" />
<DocChip chip='since' label='23.05' />
<JavadocLink type="foundation" location="com/webforj/component/list/ChoiceBox" top='true'/>

<ParentLink parent="List" />

Das `ChoiceBox`-Komponente ist ein Benutzerschnittstellelement, das dazu entwickelt wurde, den Benutzern eine Liste von Optionen oder Auswahlmöglichkeiten zu präsentieren. Benutzer können eine einzelne Option aus dieser Liste auswählen, indem sie typischerweise auf das `ChoiceBox` klicken, was die Anzeige einer Dropdown-Liste mit verfügbaren Auswahlmöglichkeiten auslöst. Benutzer können auch mit dem `ChoiceBox` über die Pfeiltasten interagieren. Wenn ein Benutzer eine Auswahl trifft, wird die gewählte Option im `ChoiceBox`-Button angezeigt.

## Usages {#usages}
`ChoiceBox`-Komponenten werden für verschiedene Zwecke verwendet, wie zum Beispiel das Auswählen von Elementen aus einem Menü, das Wählen aus einer Liste von Kategorien oder das Auswählen von Optionen aus vordefinierten Sätzen. Sie bieten eine organisierte und visuell ansprechende Weise für Benutzer, Auswahlentscheidungen zu treffen, insbesondere wenn mehrere Optionen verfügbar sind. Zu den häufigsten Anwendungen gehören:

1. **Benutzerauswahl von Optionen**: Der Hauptzweck eines `ChoiceBox` besteht darin, Benutzern zu ermöglichen, eine einzige Option aus einer Liste auszuwählen. Dies ist wertvoll in Anwendungen, die von Benutzern erfordern, Entscheidungen zu treffen, wie zum Beispiel:
    - Wählen aus einer Liste von Kategorien
    - Optionen aus vordefinierten Sätzen auswählen

2. **Formulareingaben**: Bei der Gestaltung von Formularen, die von Benutzern spezifische Optionen erfordern, vereinfacht das `ChoiceBox` den Auswahlprozess. Ob es darum geht, ein Land, einen Bundesstaat oder eine andere Option aus einer vordefinierten Liste auszuwählen, das `ChoiceBox` optimiert den Eingabeprozess.

3. **Filtern und Sortieren**: `ChoiceBox` kann für Filter- und Sortieraufgaben in Anwendungen verwendet werden. Benutzer können Filterkriterien oder Sortierpräferenzen aus einer Liste wählen, was die Organisation und Navigation von Daten erleichtert.

4. **Konfiguration und Einstellungen**: Wenn Ihre Anwendung Einstellungen oder Konfigurationsoptionen enthält, bietet das `ChoiceBox` eine intuitive Möglichkeit für Benutzer, Präferenzen anzupassen. Benutzer können Einstellungen aus einer Liste auswählen, was es einfach macht, die Anwendung auf ihre Bedürfnisse zuzuschneiden.

:::tip
Das `ChoiceBox` ist für die Verwendung gedacht, wenn eine festgelegte Anzahl von Optionen verfügbar ist und benutzerdefinierte Optionen nicht erlaubt oder einbezogen werden sollten. Wenn es gewünscht ist, Benutzern die Eingabe benutzerdefinierter Werte zu ermöglichen, verwenden Sie stattdessen ein [`ComboBox`](./combo-box.md).
:::

## Dropdowntype {#dropdown-type}

Die Verwendung der <JavadocLink type="foundation" location="com/webforj/component/list/DwcSelectDropdown" code='true' suffix='#setDropdownType(java.lang.String)'>setDropdownType()</JavadocLink>-Methode weist dem `type`-Attribut eines `ChoiceBox`-Elements einen Wert zu und einen entsprechenden Wert für das `data-dropdown-for`-Attribut im Dropdown des `ChoiceBox`. Dies ist hilfreich für das Styling, da das Dropdown aus seiner aktuellen Position im DOM herausgenommen und an das Ende des Seiteninhalts verschoben wird, wenn es geöffnet wird.

Dieser Abzug schafft eine Situation, in der das direkte Ansprechen des Dropdowns mit CSS oder Shadow-Part-Selektoren aus der übergeordneten Komponente heraus herausfordernd wird, es sei denn, Sie nutzen das Dropdowntyp-Attribut.

Im Folgenden ist das Dropdown-Type festgelegt und wird in der CSS-Datei verwendet, um das Dropdown auszuwählen und die Hintergrundfarbe zu ändern.

<ComponentDemo 
path='/webforj/choiceboxdropdowntype?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/lists/choicebox/ChoiceboxDropdownTypeView.java'
cssURL='/css/lists/combobox/comboBoxDropDownType.css'
height='250px'
/>

## Maximale Zeilenanzahl {#max-row-count}

Standardmäßig wird die Anzahl der im Dropdown eines `ChoiceBox`-Elements angezeigten Zeilen erhöht, um den Inhalt anzupassen. Mit der <JavadocLink type="foundation" location="com/webforj/component/list/DwcSelectDropdown" code='true' suffix='#setMaxRowCount(int)'>setMaxRowCount()</JavadocLink>-Methode lässt sich jedoch steuern, wie viele Elemente angezeigt werden.

:::tip
Die Verwendung einer Zahl, die kleiner oder gleich 0 ist, führt dazu, dass diese Eigenschaft zurückgesetzt wird.
:::

<ComponentDemo 
path='/webforj/choiceboxmaxrow?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/lists/choicebox/ChoiceboxMaxRowView.java'
height='450px'
/>

## Öffnen und Schließen {#opening-and-closing}

Die Sichtbarkeit der Optionen für ein `ChoiceBox` kann programmatisch mit den Methoden `open()` und `close()` gesteuert werden. Diese Methoden ermöglichen es Ihnen, die Liste der Auswahlmöglichkeiten zur Auswahl anzuzeigen oder bei Bedarf auszublenden, was größere Flexibilität in der Verwaltung des Verhaltens eines `ChoiceBox`-Elements bietet.

Zusätzlich verfügt webforJ über Ereignislistener für das Schließen und Öffnen des `ChoiceBox`, was Ihnen mehr Kontrolle gibt, um spezifische Aktionen auszulösen.

```Java
//Fokussiere oder öffne die nächste Komponente in einem Formular
ChoiceBox university = new ChoiceBox("Universität");
ChoiceBox major = new ChoiceBox("Hauptfach");
Button submit = new Button("Einreichen");

//... Füge Listen von Universitäten und Hauptfächern hinzu

university.onClose(e -> {
  major.focus();
});

major.onClose(e -> {
  submit.focus();
});
```

## Öffnungsdimensionen {#opening-dimensions}

Die `ChoiceBox`-Komponente verfügt über Methoden, die die Manipulation der Dropdown-Dimensionen ermöglichen. Die **maximale Höhe** und **minimale Breite** des Dropdowns können mithilfe der <JavadocLink type="foundation" location="com/webforj/component/list/DwcSelectDropdown" code='true' suffix='#setOpenHeight(int)'>setOpenHeight()</JavadocLink>- und <JavadocLink type="foundation" location="com/webforj/component/list/DwcSelectDropdown" code='true' suffix='#setOpenWidth(int)'>setOpenWidth()</JavadocLink>-Methoden entsprechend festgelegt werden.

:::tip
Das Übergeben eines `String`-Wertes an eine dieser Methoden ermöglicht die Anwendung von [beliebigen gültigen CSS-Einheiten](https://developer.mozilla.org/en-US/docs/Learn/CSS/Building_blocks/Values_and_units), wie Pixel, Ansichtsdimensionen oder andere gültige Regeln. Das Übergeben eines `int` legt den übergebenen Wert in Pixeln fest.
:::

## Präfix und Suffix {#prefix-and-suffix}

Slots bieten flexible Optionen zur Verbesserung der Funktionalität eines `ChoiceBox`. Sie können Symbole, Beschriftungen, Lade-Spinnner, die Möglichkeit zum Löschen/Zurücksetzen, Avatare/Profilbilder und andere nützliche Komponenten innerhalb eines `ChoiceBox` einfügen, um den Benutzern beabsichtigte Bedeutungen weiter zu verdeutlichen. Das `ChoiceBox` hat zwei Slots: die `prefix`- und `suffix`-Slots. Verwenden Sie die Methoden `setPrefixComponent()` und `setSuffixComponent()`, um verschiedene Komponenten vor und nach der angezeigten Option innerhalb eines `ChoiceBox` einzufügen.

```java
ComboBox choiceBox = new ChoiceBox());
  choiceBox.setPrefixComponent(TablerIcon.create("box"));
  choiceBox.setSuffixComponent(TablerIcon.create("box"));
```

## Styling {#styling}

<TableBuilder name="ChoiceBox" />

## Beste Praktiken {#best-practices}

Um eine optimale Benutzererfahrung bei der Verwendung der `ChoiceBox`-Komponente sicherzustellen, sollten Sie die folgenden besten Praktiken berücksichtigen:

1. **Klare und begrenzte Optionen**: Halten Sie die Liste der Auswahlmöglichkeiten so kurz wie möglich und auf die Aufgabe des Benutzers relevant. Ein `ChoiceBox` eignet sich ideal zur Präsentation einer klaren Liste von Optionen.

2. **Benutzerfreundliche Beschriftungen**: Stellen Sie sicher, dass die angezeigten Beschriftungen für jede Option benutzerfreundlich und selbsterklärend sind. Sorgen Sie dafür, dass die Benutzer leicht verstehen können, welchen Zweck jede Wahl hat.

3. **Standardauswahl**: Stellen Sie eine Standardauswahl ein, wenn das ChoiceBox erstmals angezeigt wird. Dies gewährleistet eine vorgewählte Option und verringert die Anzahl der Interaktionen, die erforderlich sind, um eine Wahl zu treffen.

4. **ChoiceBox vs. andere Listenkomponenten**: Ein `ChoiceBox` ist die beste Wahl, wenn Sie die Benutzereingabe auf eine einzelne Wahl aus einer Liste vordefinierter Optionen beschränken müssen. Eine andere Listenkomponente könnte besser sein, wenn Sie die folgenden Verhaltensweisen benötigen:
    - Mehrfachauswahl und Anzeige aller Elemente auf einmal: [`ListBox`](./list-box.md)
    - Erlauben Sie benutzerdefinierte Eingaben: [`ComboBox`](./combo-box.md)
