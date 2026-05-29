---
sidebar_position: 5
title: ChoiceBox
slug: choicebox
_i18n_hash: 6e04ceea1fadc5f159b8d4dd9645e014
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-choicebox" />
<DocChip chip='since' label='23.05' />
<JavadocLink type="foundation" location="com/webforj/component/list/ChoiceBox" top='true'/>

Die `ChoiceBox`-Komponente präsentiert eine Dropdown-Liste, aus der Benutzer eine einzelne Option auswählen können. Wenn eine Auswahl getroffen wird, wird der gewählte Wert im Button angezeigt. Sie eignet sich gut, wenn Benutzer aus einer festen Menge vordefinierter Optionen auswählen müssen, und die Pfeiltasten können verwendet werden, um in der Liste zu navigieren.

<!-- INTRO_END -->

## Usos {#usages}

<ParentLink parent="List" />

`ChoiceBox`-Komponenten werden für verschiedene Zwecke verwendet, wie das Auswählen von Elementen aus einem Menü, das Wählen aus einer Liste von Kategorien oder das Auswählen von Optionen aus vordefinierten Sätzen. Sie bieten eine organisierte und ansprechend gestaltete Möglichkeit für Benutzer, Auswahlen zu treffen, insbesondere wenn mehrere Optionen verfügbar sind. Zu den häufigsten Verwendungen gehören:

1. **Benutzerauswahl von Optionen**: Der Hauptzweck einer `ChoiceBox` besteht darin, Benutzern zu ermöglichen, eine einzelne Option aus einer Liste auszuwählen. Dies ist wertvoll in Anwendungen, die von Benutzern Entscheidungen erfordern, wie:
    - Auswahl aus einer Liste von Kategorien
    - Auswahl von Optionen aus vordefinierten Sätzen

2. **Formulareingaben**: Bei der Gestaltung von Formularen, die von Benutzern spezifische Optionen erfordern, vereinfacht die `ChoiceBox` den Auswahlprozess. Ob es sich um die Auswahl eines Landes, Bundesstaates oder einer anderen Option aus einer vordefinierten Liste handelt, die `ChoiceBox` optimiert den Eingabeprozess.

3. **Filterung und Sortierung**: `ChoiceBox` kann für Filter- und Sortieraufgaben in Anwendungen eingesetzt werden. Benutzer können Filterkriterien oder Sortierpräferenzen aus einer Liste auswählen, was die Organisation und Navigation von Daten erleichtert.

4. **Konfiguration und Einstellungen**: Wenn Ihre Anwendung Einstellungen oder Konfigurationsoptionen umfasst, bietet die `ChoiceBox` eine intuitive Möglichkeit für Benutzer, Präferenzen anzupassen. Benutzer können Einstellungen aus einer Liste auswählen, was es einfach macht, die Anwendung an ihre Bedürfnisse anzupassen.

:::tip
Die `ChoiceBox` ist dafür gedacht, verwendet zu werden, wenn eine vordefinierte Anzahl von Optionen verfügbar ist, und benutzerdefinierte Optionen nicht erlaubt oder enthalten sein sollten. Wenn es gewünscht ist, dass Benutzer benutzerdefinierte Werte eingeben können, verwenden Sie stattdessen eine [`ComboBox`](./combo-box.md).
:::

## Dropdown-Typ {#dropdown-type}

Die Verwendung der <JavadocLink type="foundation" location="com/webforj/component/list/DwcSelectDropdown" code='true' suffix='#setDropdownType(java.lang.String)'>setDropdownType()</JavadocLink>-Methode weist dem `type`-Attribut einer `ChoiceBox` einen Wert zu und einen entsprechenden Wert für das `data-dropdown-for`-Attribut im Dropdown der `ChoiceBox`. Dies ist hilfreich für das Styling, da das Dropdown aus seiner aktuellen Position im DOM entfernt und beim Öffnen an das Ende des Seitenkörpers verschoben wird.

<!-- ![example type](/img/components/_images/choicebox/type.png)
![example type](/img/components/_images/choicebox/type_zoomed.png) -->

Diese Ablösung schafft eine Situation, in der es herausfordernd wird, das Dropdown direkt mit CSS oder Schattenpartikelselektoren aus der übergeordneten Komponente anzusprechen, es sei denn, Sie nutzen das Dropdown-Typ-Attribut.

Im folgenden Demo wird der Dropdown-Typ festgelegt und in der CSS-Datei verwendet, um das Dropdown auszuwählen und die Hintergrundfarbe zu ändern.

<ComponentDemo
path='/webforj/choiceboxdropdowntype'
files={[
  'src/main/java/com/webforj/samples/views/lists/choicebox/ChoiceboxDropdownTypeView.java',
  'src/main/resources/static/css/lists/combobox/comboBoxDropDownType.css',
]}
height='250px'
/>

## Maximale Zeilenanzahl {#max-row-count}

Standardmäßig wird die Anzahl der Anzeigen in der Dropdown-Liste einer `ChoiceBox` erhöht, um den Inhalt aufzunehmen. Die Verwendung der <JavadocLink type="foundation" location="com/webforj/component/list/DwcSelectDropdown" code='true' suffix='#setMaxRowCount(int)'>setMaxRowCount()</JavadocLink>-Methode ermöglicht die Kontrolle darüber, wie viele Elemente angezeigt werden.

:::tip
Die Verwendung einer Zahl, die kleiner oder gleich 0 ist, führt zum Zurücksetzen dieser Eigenschaft.
:::

<ComponentDemo
path='/webforj/choiceboxmaxrow'
files={['src/main/java/com/webforj/samples/views/lists/choicebox/ChoiceboxMaxRowView.java']}
height='450px'
/>

## Öffnen und Schließen {#opening-and-closing}

Die Sichtbarkeit der Optionen für eine `ChoiceBox` kann programmgesteuert mit den Methoden `open()` und `close()` gesteuert werden. Diese Methoden ermöglichen es, die Liste der Auswahlmöglichkeiten anzuzeigen oder nach Bedarf auszublenden, was eine größere Flexibilität bei der Verwaltung des Verhaltens einer `ChoiceBox` bietet.

Darüber hinaus hat webforJ Ereignislistener für das Schließen und Öffnen der `ChoiceBox`, wodurch Sie mehr Kontrolle haben, um spezifische Aktionen auszulösen.

```Java
// Fokussiere oder öffne die nächste Komponente in einem Formular
ChoiceBox university = new ChoiceBox("Universität");
ChoiceBox major = new ChoiceBox("Hauptfach");
Button submit = new Button("Abschicken");

// ... Fügen Sie Listen von Universitäten und Hauptfächern hinzu

university.onClose( e ->{
  major.focus();
});

major.onClose( e ->{
  submit.focus();
});
```

## Öffnungsdimenisonen {#opening-dimensions}

Die `ChoiceBox`-Komponente hat Methoden, die eine Manipulation der Dropdown-Dimensionen ermöglichen. Die **maximale Höhe** und **minimale Breite** des Dropdowns können mittels der <JavadocLink type="foundation" location="com/webforj/component/list/DwcSelectDropdown" code='true' suffix='#setOpenHeight(int)'>setOpenHeight()</JavadocLink>- und <JavadocLink type="foundation" location="com/webforj/component/list/DwcSelectDropdown" code='true' suffix='#setOpenWidth(int)'>setOpenWidth()</JavadocLink>-Methoden festgelegt werden, respectively. 

:::tip
Das Übergeben eines `String`-Wertes an eine dieser Methoden ermöglicht es, [jede gültige CSS-Einheit](https://developer.mozilla.org/en-US/docs/Learn/CSS/Building_blocks/Values_and_units) anzuwenden, wie Pixel, Ansichtsdimensionen oder andere gültige Regeln. Das Übergeben eines `int` setzt den übergebenen Wert in Pixeln.
:::

## Präfix und Suffix {#prefix-and-suffix}

Slots bieten flexible Optionen zur Verbesserung der Funktionalität einer `ChoiceBox`. Sie können Symbole, Labels, Ladespinner, Möglichkeiten zum Löschen/Zurücksetzen, Avatare/Profilbilder und andere nützliche Komponenten innerhalb einer `ChoiceBox` einfügen, um die beabsichtigte Bedeutung für die Benutzer weiter zu verdeutlichen. Die `ChoiceBox` verfügt über zwei Slots: die `prefix`- und `suffix`-Slots. Verwenden Sie die Methoden `setPrefixComponent()` und `setSuffixComponent()`, um verschiedene Komponenten vor und nach der angezeigten Auswahl innerhalb einer `ChoiceBox` einzufügen.

```java
ComboBox choiceBox = new ChoiceBox());
  choiceBox.setPrefixComponent(TablerIcon.create("box"));
  choiceBox.setSuffixComponent(TablerIcon.create("box"));
```

## Styling {#styling}

<TableBuilder name="ChoiceBox" />

## Beste Praktiken {#best-practices}

Um ein optimales Benutzererlebnis bei der Verwendung der `ChoiceBox`-Komponente zu gewährleisten, sollten Sie die folgenden besten Praktiken berücksichtigen:

1. **Klare und begrenzte Optionen**: Halten Sie die Liste der Auswahlmöglichkeiten nach Möglichkeit prägnant und relevant für die Aufgabe des Benutzers. Eine `ChoiceBox` eignet sich hervorragend, um eine klare Liste von Optionen darzustellen.

2. **Benutzerfreundliche Labels**: Stellen Sie sicher, dass die angezeigten Labels für jede Option benutzerfreundlich und selbsterklärend sind. Stellen Sie sicher, dass die Benutzer den Zweck jeder Auswahl leicht verstehen können.

3. **Standardauswahl**: Legen Sie eine Standardauswahl fest, wenn die `ChoiceBox` zunächst angezeigt wird. Dies gewährleistet eine vorausgewählte Option, wodurch die Anzahl der erforderlichen Interaktionen zur Auswahl reduziert wird.

4. **ChoiceBox vs. andere Listenkomponenten**: Eine `ChoiceBox` ist die beste Wahl, wenn Sie die Benutzereingabe auf eine einzige Auswahl aus einer Liste vordefinierter Optionen beschränken müssen. Eine andere Listenkomponente kann besser sein, wenn Sie folgende Verhaltensweisen benötigen:
    - Mehrfachauswahl und Anzeige aller Elemente auf einmal: [`ListBox`](./list-box.md)
    - Erlauben von benutzerdefinierten Eingaben: [`ComboBox`](./combo-box.md)
