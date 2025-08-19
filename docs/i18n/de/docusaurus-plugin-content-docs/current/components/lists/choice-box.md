---
sidebar_position: 5
title: ChoiceBox
slug: choicebox
_i18n_hash: e90d77e503b1c8f7fc20109633b1e7be
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-choicebox" />
<DocChip chip='since' label='23.05' />
<JavadocLink type="foundation" location="com/webforj/component/list/ChoiceBox" top='true'/>

<ParentLink parent="List" />

Das `ChoiceBox`-Komponente ist ein Benutzeroberflächenelement, das entwickelt wurde, um den Benutzern eine Liste von Optionen oder Auswahlmöglichkeiten zu präsentieren. Benutzer können eine einzelne Option aus dieser Liste auswählen, typischerweise durch Klicken auf das `ChoiceBox`, was das Anzeigen einer Dropdown-Liste mit verfügbaren Wahlmöglichkeiten auslöst. Die Benutzer können auch mit den Pfeiltasten auf das `ChoiceBox` zugreifen. Wenn ein Benutzer eine Auswahl trifft, wird die gewählte Option im `ChoiceBox`-Button angezeigt.

## Usages {#usages}
`ChoiceBox`-Komponenten werden für verschiedene Zwecke verwendet, wie das Auswählen von Elementen aus einem Menü, das Wählen aus einer Liste von Kategorien oder das Auswählen von Optionen aus vordefinierten Mengen. Sie bieten eine organisierte und visuell ansprechende Möglichkeit für Benutzer, Auswahlen zu treffen, insbesondere wenn mehrere Optionen verfügbar sind. Häufige Anwendungen umfassen:

1. **Benutzerauswahl von Optionen**: Der Hauptzweck eines `ChoiceBox` besteht darin, Benutzern die Auswahl einer einzelnen Option aus einer Liste zu ermöglichen. Dies ist wertvoll in Anwendungen, die von den Benutzern Entscheidungen erfordern, wie zum Beispiel:
    - Wählen aus einer Liste von Kategorien
    - Auswählen von Optionen aus vordefinierten Mengen

2. **Formulareingaben**: Bei der Gestaltung von Formularen, die von den Benutzern spezifische Optionen erfordern, vereinfacht das `ChoiceBox` den Auswahlprozess. Ob es um die Auswahl eines Landes, Bundesstaates oder einer anderen Option aus einer vordefinierten Liste geht, das `ChoiceBox` optimiert den Eingabeprozess.

3. **Filtern und Sortieren**: `ChoiceBox` kann für Filter- und Sortieraufgaben in Anwendungen verwendet werden. Benutzer können Filterkriterien oder Sortierungsvorlieben aus einer Liste auswählen, was die Organisation und Navigation der Daten erleichtert.

4. **Konfiguration und Einstellungen**: Wenn Ihre Anwendung Einstellungen oder Konfigurationsoptionen enthält, bietet das `ChoiceBox` eine intuitive Möglichkeit für Benutzer, Präferenzen anzupassen. Benutzer können Einstellungen aus einer Liste auswählen, was es einfach macht, die Anwendung an ihre Bedürfnisse anzupassen.

:::tip
Das `ChoiceBox` ist für die Verwendung vorgesehen, wenn eine vordefinierte Anzahl von Optionen verfügbar ist, und benutzerdefinierte Optionen nicht erlaubt oder einbezogen werden sollten. Wenn es gewünscht ist, den Benutzern das Eingeben benutzerdefinierter Werte zu ermöglichen, verwenden Sie stattdessen ein [`ComboBox`](./combo-box.md).
:::

## Dropdown type {#dropdown-type}

Die Verwendung der <JavadocLink type="foundation" location="com/webforj/component/list/DwcSelectDropdown" code='true' suffix='#setDropdownType(java.lang.String)'>setDropdownType()</JavadocLink>-Methode weist dem `type`-Attribut eines `ChoiceBox` einen Wert zu, sowie einen entsprechenden Wert für das `data-dropdown-for`-Attribut in der Dropdown-Liste des `ChoiceBox`. Dies ist hilfreich für das Styling, da das Dropdown aus seiner aktuellen Position im DOM entfernt und zum Ende des Seitenkörpers verschoben wird, wenn es geöffnet ist.

Dieses Abtrennen schafft eine Situation, in der es schwierig wird, das Dropdown direkt mit CSS oder Shadow-Part-Selektoren vom übergeordneten Element aus anzusprechen, es sei denn, Sie verwenden das Dropdown-Typ-Attribut.

Im folgenden Demo wird der Dropdown-Typ festgelegt und in der CSS-Datei verwendet, um das Dropdown auszuwählen und die Hintergrundfarbe zu ändern.

<ComponentDemo 
path='/webforj/choiceboxdropdowntype?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/lists/choicebox/ChoiceboxDropdownTypeView.java'
cssURL='/css/lists/combobox/comboBoxDropDownType.css'
height='250px'
/>

## Max row count {#max-row-count}

Standardmäßig wird die Anzahl der in der Dropdown-Liste eines `ChoiceBox` angezeigten Zeilen erhöht, um dem Inhalt zu entsprechen. Mit der <JavadocLink type="foundation" location="com/webforj/component/list/DwcSelectDropdown" code='true' suffix='#setMaxRowCount(int)'>setMaxRowCount()</JavadocLink>-Methode kann jedoch kontrolliert werden, wie viele Elemente angezeigt werden.

:::tip
Die Verwendung einer Zahl, die kleiner oder gleich 0 ist, führt dazu, dass diese Eigenschaft zurückgesetzt wird.
:::

<ComponentDemo 
path='/webforj/choiceboxmaxrow?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/lists/choicebox/ChoiceboxMaxRowView.java'
height='450px'
/>

## Opening and closing {#opening-and-closing}

Die Sichtbarkeit der Optionen für ein `ChoiceBox` kann programmatisch mit den Methoden `open()` und `close()` gesteuert werden. Diese Methoden ermöglichen es Ihnen, die Liste der Auswahlmöglichkeiten anzuzeigen oder sie bei Bedarf auszublenden, was eine größere Flexibilität bei der Verwaltung des Verhaltens eines `ChoiceBox` bietet.

Darüber hinaus hat webforJ Ereignislistener dafür, wann das `ChoiceBox` geschlossen und wann es geöffnet wird, sodass Sie mehr Kontrolle haben, um spezifische Aktionen auszulösen.

```Java
//Fokus oder öffne die nächste Komponente in einem Formular
ChoiceBox university = new ChoiceBox("Universität");
ChoiceBox major = new ChoiceBox("Studienfach");
Button submit = new Button("Einreichen");

//... Fügen Sie Listen von Universitäten und Studienfächern hinzu

university.onClose( e ->{
  major.focus();
});

major.onClose( e ->{
  submit.focus();
});
```

## Opening dimensions {#opening-dimensions}

Die `ChoiceBox`-Komponente hat Methoden, die die Manipulation der Dropdown-Dimensionen ermöglichen. Die **maximale Höhe** und **minimale Breite** des Dropdowns können mit den Methoden <JavadocLink type="foundation" location="com/webforj/component/list/DwcSelectDropdown" code='true' suffix='#setOpenHeight(int)'>setOpenHeight()</JavadocLink> und <JavadocLink type="foundation" location="com/webforj/component/list/DwcSelectDropdown" code='true' suffix='#setOpenWidth(int)'>setOpenWidth()</JavadocLink> festgelegt werden.

:::tip
Das Übergeben eines `String`-Wertes an eine dieser Methoden ermöglicht die Anwendung von [beliebigen gültigen CSS-Einheiten](https://developer.mozilla.org/en-US/docs/Learn/CSS/Building_blocks/Values_and_units), wie Pixel, Ansichtsgrößen oder andere gültige Regeln. Das Übergeben eines `int` setzt den übergebenen Wert in Pixeln.
:::

## Prefix and suffix {#prefix-and-suffix}

Slots bieten flexible Optionen zur Verbesserung der Funktionalität eines `ChoiceBox`. Sie können Icons, Labels, Ladeanimationen, eine Lösch-/Rücksetzfunktion, Avatar-/Profilbilder und andere nützliche Komponenten innerhalb eines `ChoiceBox` einfügen, um den Benutzern die beabsichtigte Bedeutung weiter zu verdeutlichen. Das `ChoiceBox` hat zwei Slots: den `prefix`- und den `suffix`-Slot. Verwenden Sie die Methoden `setPrefixComponent()` und `setSuffixComponent()`, um verschiedene Komponenten vor und nach der angezeigten Option innerhalb eines `ChoiceBox` einzufügen.

```java
ComboBox choiceBox = new ChoiceBox());
  choiceBox.setPrefixComponent(TablerIcon.create("box"));
  choiceBox.setSuffixComponent(TablerIcon.create("box"));
```

## Styling {#styling}

<TableBuilder name="ChoiceBox" />

## Best practices {#best-practices}

Um eine optimale Benutzererfahrung bei der Verwendung der `ChoiceBox`-Komponente zu gewährleisten, beachten Sie die folgenden Best Practices:

1. **Klare und begrenzte Optionen**: Halten Sie die Liste von Auswahlmöglichkeiten so knapp wie möglich und relevant für die Aufgabe des Benutzers. Ein `ChoiceBox` eignet sich hervorragend, um eine klare Liste von Optionen zu präsentieren.

2. **Benutzerfreundliche Labels**: Stellen Sie sicher, dass die angezeigten Labels für jede Option benutzerfreundlich und selbsterklärend sind. Achten Sie darauf, dass die Benutzer leicht den Zweck jeder Auswahl verstehen können.

3. **Voreingestellte Auswahl**: Legen Sie eine voreingestellte Auswahl fest, wenn das ChoiceBox erstmals angezeigt wird. Dies sorgt für eine vorab ausgewählte Option, wodurch die Anzahl der erforderlichen Interaktionen zur Auswahl reduziert wird.

4. **ChoiceBox vs. andere Listenkomponenten**: Ein `ChoiceBox` ist die beste Wahl, wenn Sie die Benutzereingabe auf eine einzige Auswahl aus einer Liste vordefinierter Optionen beschränken müssen. Eine andere Listenkomponente kann besser geeignet sein, wenn Sie folgende Verhaltensweisen benötigen:
    - Mehrfachauswahl und Anzeige aller Elemente auf einmal: [`ListBox`](./list-box.md)
    - Ermöglichen benutzerdefinierter Eingaben: [`ComboBox`](./combo-box.md)
