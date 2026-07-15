---
sidebar_position: 5
title: ChoiceBox
slug: choicebox
description: >-
  Pick a single value from a fixed set with the ChoiceBox dropdown, including
  dropdown type styling, max row count, and keyboard navigation.
_i18n_hash: f897ac9d3f5c252ac323762080e1edcf
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-choicebox" />
<DocChip chip='since' label='23.05' />
<JavadocLink type="foundation" location="com/webforj/component/list/ChoiceBox" top='true'/>

Die `ChoiceBox`-Komponente bietet eine Dropdown-Liste, aus der Benutzer eine einzige Option auswählen können. Wenn eine Auswahl getroffen wird, wird der gewählte Wert im Button angezeigt. Sie eignet sich gut, wenn Benutzer aus einer festgelegten Menge vordefinierter Optionen auswählen müssen, und die Pfeiltasten können verwendet werden, um die Liste zu navigieren.

<!-- INTRO_END -->

## Usos {#usages}

<ParentLink parent="List" />

`ChoiceBox`-Komponenten werden für verschiedene Zwecke verwendet, wie z.B. die Auswahl von Elementen aus einem Menü, die Auswahl aus einer Liste von Kategorien oder das Auswählen von Optionen aus vordefinierten Gruppen. Sie bieten eine organisierte und optisch ansprechende Möglichkeit für Benutzer, Auswahl zu treffen, insbesondere wenn es mehrere verfügbare Optionen gibt. Häufige Verwendungsmöglichkeiten sind:

1. **Benutzerauswahl von Optionen**: Der Hauptzweck einer `ChoiceBox` besteht darin, Benutzern zu ermöglichen, eine einzelne Option aus einer Liste auszuwählen. Dies ist wertvoll in Anwendungen, die von Benutzern Entscheidungen verlangen, wie z.B.:
    - Auswahl aus einer Liste von Kategorien
    - Auswählen von Optionen aus vordefinierten Gruppen

2. **Formulareingaben**: Bei der Gestaltung von Formularen, die von Benutzern spezifische Optionen erfordern, vereinfacht die `ChoiceBox` den Auswahlprozess. Ob es darum geht, ein Land, einen Bundesstaat oder eine andere Option aus einer vordefinierten Liste auszuwählen, die `ChoiceBox` optimiert den Eingabeprozess.

3. **Filtern und Sortieren**: `ChoiceBox` kann für Filter- und Sortieraufgaben in Anwendungen eingesetzt werden. Benutzer können Filterkriterien oder Sortierpräferenzen aus einer Liste auswählen, was die Organisation und Navigation von Daten erleichtert.

4. **Konfiguration und Einstellungen**: Wenn Ihre Anwendung Einstellungen oder Konfigurationsoptionen enthält, bietet die `ChoiceBox` eine intuitive Möglichkeit für Benutzer, Präferenzen anzupassen. Benutzer können Einstellungen aus einer Liste auswählen, wodurch es einfach wird, die Anwendung an ihre Bedürfnisse anzupassen.

:::tip
Die `ChoiceBox` ist für die Verwendung vorgesehen, wenn eine festgelegte Anzahl von Optionen verfügbar ist, und benutzerdefinierte Optionen nicht erlaubt oder eingeschlossen werden sollten. Wenn es gewünscht wird, dass Benutzer benutzerdefinierte Werte eingeben können, verwenden Sie stattdessen eine [`ComboBox`](./combo-box.md).
:::

## Dropdown-Typ {#dropdown-type}

Mit der <JavadocLink type="foundation" location="com/webforj/component/list/DwcSelectDropdown" code='true' suffix='#setDropdownType(java.lang.String)'>setDropdownType()</JavadocLink>-Methode wird ein Wert für das `type`-Attribut einer `ChoiceBox` zugewiesen und ein entsprechender Wert für das `data-dropdown-for`-Attribut im Dropdown der `ChoiceBox` festgelegt. Dies ist hilfreich für das Styling, da das Dropdown bei Öffnung aus seiner aktuellen Position im DOM entfernt und ans Ende des Seiteninhalts verlagert wird.

<!-- ![example type](/img/components/_images/choicebox/type.png)
![example type](/img/components/_images/choicebox/type_zoomed.png) -->

Diese Abspaltung schafft eine Situation, in der das gezielte Ansprechen des Dropdowns mit CSS oder Shadow-Part-Selektoren aus der übergeordneten Komponente heraus schwierig wird, es sei denn, Sie nutzen das Dropdown-Typ-Attribut.

Im folgenden Demo wird der Dropdown-Typ eingestellt und in der CSS-Datei verwendet, um eine Option beim Darüberfahren zu vergrößern.

<ComponentDemo
path='/webforj/choiceboxdropdowntype'
files={[
  'src/main/java/com/webforj/samples/views/lists/choicebox/ChoiceboxDropdownTypeView.java',
  'src/main/frontend/css/lists/combobox/comboBoxDropDownType.css',
]}
height='250px'
/>

## Maximale Zeilenanzahl {#max-row-count}

Standardmäßig wird die Anzahl der im Dropdown einer `ChoiceBox` angezeigten Zeilen erhöht, um den Inhalt anzupassen. Mit der <JavadocLink type="foundation" location="com/webforj/component/list/DwcSelectDropdown" code='true' suffix='#setMaxRowCount(int)'>setMaxRowCount()</JavadocLink>-Methode kann jedoch die Anzahl der angezeigten Elemente gesteuert werden.

:::tip
Die Verwendung einer Zahl, die kleiner oder gleich 0 ist, führt dazu, dass diese Eigenschaft zurückgesetzt wird.
:::

<ComponentDemo
path='/webforj/choiceboxmaxrow'
files={['src/main/java/com/webforj/samples/views/lists/choicebox/ChoiceboxMaxRowView.java']}
height='450px'
/>

## Öffnen und Schließen {#opening-and-closing}

Die Sichtbarkeit der Optionen für eine `ChoiceBox` kann programmgesteuert mit den Methoden `open()` und `close()` gesteuert werden. Diese Methoden ermöglichen es, die Liste der Optionen zur Auswahl anzuzeigen oder bei Bedarf auszublenden, wodurch eine größere Flexibilität in der Handhabung des Verhaltens einer `ChoiceBox` gegeben ist.

Zusätzlich hat webforJ Ereignislistener für das Schließen und Öffnen der `ChoiceBox`, die Ihnen mehr Kontrolle ermöglichen, um spezifische Aktionen auszulösen.

```Java
//Fokussieren oder öffnen Sie die nächste Komponente in einem Formular
ChoiceBox universität = new ChoiceBox("Universität");
ChoiceBox hauptfach = new ChoiceBox("Hauptfach");
Button absenden = new Button("Absenden");

//... Listen von Universitäten und Hauptfächern hinzufügen

universität.onClose( e ->{
  hauptfach.focus();
});

hauptfach.onClose( e ->{
  absenden.focus();
});
```

## Öffnungsdimensionen {#opening-dimensions}

Die `ChoiceBox`-Komponente verfügt über Methoden, die eine Manipulation der Dropdown-Dimensionen ermöglichen. Die **maximale Höhe** und **minimale Breite** des Dropdowns können mit den <JavadocLink type="foundation" location="com/webforj/component/list/DwcSelectDropdown" code='true' suffix='#setOpenHeight(int)'>setOpenHeight()</JavadocLink>- und <JavadocLink type="foundation" location="com/webforj/component/list/DwcSelectDropdown" code='true' suffix='#setOpenWidth(int)'>setOpenWidth()</JavadocLink>-Methoden festgelegt werden.

:::tip
Wenn Sie einen `String`-Wert an eine dieser Methoden übergeben, wird [jede gültige CSS-Einheit](https://developer.mozilla.org/en-US/docs/Learn/CSS/Building_blocks/Values_and_units) angewendet, wie Pixel, Ansichtsdimensionen oder andere gültige Regeln. Die Übergabe eines `int` setzt den übergebenen Wert in Pixeln.
:::

## Präfix und Suffix {#prefix-and-suffix}

Slots bieten flexible Optionen zur Verbesserung der Funktionalität einer `ChoiceBox`. Sie können Icons, Labels, Ladekreise, die Möglichkeit zum Löschen/Zurücksetzen, Avatar-/Profilbilder und andere nützliche Komponenten innerhalb einer `ChoiceBox` verschachteln, um die beabsichtigte Bedeutung für die Benutzer weiter zu verdeutlichen. 
Die `ChoiceBox` hat zwei Slots: den `prefix`- und den `suffix`-Slot. Verwenden Sie die Methoden `setPrefixComponent()` und `setSuffixComponent()`, um verschiedene Komponenten vor und nach der angezeigten Option innerhalb einer `ChoiceBox` einzufügen.

```java
ComboBox choiceBox = new ChoiceBox());
  choiceBox.setPrefixComponent(TablerIcon.create("box"));
  choiceBox.setSuffixComponent(TablerIcon.create("box"));
```

## Styling {#styling}

<TableBuilder name="ChoiceBox" />

## Best Practices {#best-practices}

Um ein optimales Benutzererlebnis bei der Verwendung der `ChoiceBox`-Komponente sicherzustellen, sollten Sie die folgenden Best Practices berücksichtigen:

1. **Klare und begrenzte Optionen**: Halten Sie die Liste der Auswahlmöglichkeiten so prägnant wie möglich und relevant für die Aufgabe des Benutzers. Eine `ChoiceBox` eignet sich ideal für die Präsentation einer klaren Liste von Optionen.

2. **Benutzerfreundliche Labels**: Stellen Sie sicher, dass die angezeigten Labels für jede Option benutzerfreundlich und selbsterklärend sind. Achten Sie darauf, dass die Benutzer den Zweck jeder Auswahl leicht verstehen können.

3. **Standardauswahl**: Setzen Sie eine Standardauswahl, wenn die `ChoiceBox` anfänglich angezeigt wird. Dadurch wird eine vorab ausgewählte Option sichergestellt, die die Anzahl der benötigten Interaktionen zur Auswahl reduziert.

4. **ChoiceBox vs. Andere Listenkomponenten**: Eine `ChoiceBox` ist die beste Wahl, wenn Sie die Benutzereingabe auf eine einzige Auswahl aus einer Liste vordefinierter Optionen beschränken müssen. Eine andere Listenkomponente kann besser geeignet sein, wenn Sie folgendes Verhalten benötigen:
    - Mehrfachauswahl und Anzeige aller Elemente gleichzeitig: [`ListBox`](./list-box.md)
    - Zulassen von benutzerdefinierten Eingaben: [`ComboBox`](./combo-box.md)
