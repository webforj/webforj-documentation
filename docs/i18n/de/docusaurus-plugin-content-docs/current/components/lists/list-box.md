---
sidebar_position: 15
title: ListBox
slug: listbox
_i18n_hash: 48b12429da76fbbe3a7961a0eac4efa9
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-listbox" />
<DocChip chip='since' label='23.05' />
<JavadocLink type="foundation" location="com/webforj/component/list/ListBox" top='true'/>

Die `ListBox`-Komponente zeigt eine scrollbare Liste von Elementen an, die sichtbar bleibt, ohne ein Dropdown zu öffnen. Sie unterstützt sowohl die Einzel- als auch die Mehrfachauswahl und eignet sich gut, wenn Benutzer alle verfügbaren Optionen gleichzeitig sehen müssen.

<!-- INTRO_END -->

## Anwendungen {#usages}

<ParentLink parent="List" />

1. **Benutzerrolle Zuweisung**: In Anwendungen mit Benutzerzugriffssteuerung können Administratoren eine `ListBox` verwenden, um Rollen und Berechtigungen an Benutzer zuzuweisen. Benutzer werden aus einer Liste ausgewählt, und die Rollen oder Berechtigungen werden basierend auf ihrer Auswahl zugewiesen. Dies gewährleistet präzisen und kontrollierten Zugriff auf verschiedene Funktionen und Daten innerhalb der Anwendung.

2. **Projektaufgaben Zuweisung**: In Projektmanagement-Software sind `ListBox`-Komponenten nützlich, um Aufgaben an Teammitglieder zuzuweisen. Benutzer können Aufgaben aus einer Liste auswählen und sie verschiedenen Teammitgliedern zuweisen. Dies vereinfacht die Aufgabenübertragung und stellt sicher, dass Verantwortlichkeiten innerhalb des Teams klar definiert sind.

3. **Mehrkategorie-Filterung**: In einer Suchanwendung müssen Benutzer häufig Suchergebnisse basierend auf mehreren Kriterien filtern. Eine `ListBox` kann verschiedene Filteroptionen anzeigen, wie 
>- Produktmerkmale
>- Preisspannen
>- Marken. 

  Benutzer können Elemente aus jeder Filterkategorie auswählen, wodurch sie die Suchergebnisse verfeinern und genau das finden können, wonach sie suchen.

4. **Inhaltskategorisierung**: In Content-Management-Systemen unterstützen `ListBox`-Komponenten die Kategorisierung von Artikeln, Bildern oder Dateien. Benutzer können eine oder mehrere Kategorien auswählen, um ihre Inhalte zuzuordnen, was es einfacher macht, Inhalte im System zu organisieren und zu suchen.

## Auswahloptionen {#selection-options}

Standardmäßig ist die Liste so konfiguriert, dass die Auswahl eines einzelnen Elements zu einem Zeitpunkt erlaubt ist. Die `ListBox` implementiert jedoch das <JavadocLink type="foundation" location="com/webforj/component/list/MultipleSelectableList" code='true'>MultipleSelectableList</JavadocLink>-Interface, welches mit einer integrierten Methode konfiguriert werden kann, die es Benutzern ermöglicht, mehrere Elemente ***unter Verwendung der `Shift`-Taste*** für zusammenhängende Auswahl und ***`Control` (Windows) oder `Command` (Mac)*** für separate, mehrfache Auswahl zu wählen. 

Verwenden Sie die <JavadocLink type="foundation" location="com/webforj/component/list/ListBox" code='true' suffix='#setSelectionMode(org.dwcj.component.list.MultipleSelectableList.SelectionMode)'>setSelectionMode()</JavadocLink>-Funktion, um diese Eigenschaft zu ändern. Diese Methode akzeptiert entweder `SelectionMode.SINGLE` oder `SelectionMode.MULTIPLE`.

:::info Verhalten auf Touch-Geräten
Auf Touch-Geräten können Benutzer beim Aktivieren der Mehrfachauswahl mehrere Elemente auswählen, ohne die Umschalttaste gedrückt zu halten.
:::

Darüber hinaus können die Pfeiltasten verwendet werden, um die `ListBox` zu navigieren, und das Tippen auf eine Buchstabentaste, während die `ListBox` den Fokus hat, wählt die Option aus, die mit diesem Buchstaben beginnt, oder durchläuft die Optionen, die mit diesem Buchstaben beginnen, falls mehrere Optionen vorhanden sind.

<ComponentDemo
path='/webforj/listboxmultipleselection'
files={['src/main/java/com/webforj/samples/views/lists/listbox/ListboxMultipleSelectionView.java']}
height='250px'
/>

## Styling {#styling}

<TableBuilder name="ListBox" />

## Best Practices {#best-practices}

Um ein optimales Benutzererlebnis bei der Verwendung der `ChoiceBox`-Komponente zu gewährleisten, sollten die folgenden Best Practices beachtet werden:

1. **Priorisieren Sie die Informationshierarchie**: Stellen Sie sicher, dass die Elemente in einer logischen und hierarchischen Reihenfolge organisiert sind, wenn Sie eine `ListBox` verwenden. Platzieren Sie die wichtigsten und am häufigsten verwendeten Optionen an oberster Stelle in der Liste. Dies erleichtert es den Benutzern, das zu finden, was sie benötigen, ohne übermäßig scrollen zu müssen.

2. **Länge der Liste begrenzen**: Vermeiden Sie es, Benutzer mit einer übermäßig langen `ListBox` zu überwältigen. Wenn viele Elemente angezeigt werden sollen, sollten Sie die Implementierung von Seiten, Such- oder Filteroptionen in Betracht ziehen, um den Benutzern zu helfen, schnell Elemente zu finden. Alternativ können Sie Elemente in Kategorien gruppieren, um die Länge der Liste zu reduzieren.

3. **Klare und beschreibende Beschriftungen**: Stellen Sie sicher, dass jede Option in der `ListBox` klar und beschreibend beschriftet ist. Die Benutzer sollten den Zweck jeder Option ohne Mehrdeutigkeit verstehen können. Verwenden Sie prägnante und aussagekräftige Elementbeschriftungen.

4. **Rückmeldung zur Mehrfachauswahl**: Wenn Ihre `ListBox` Mehrfachauswahlen zulässt, bieten Sie visuelles oder textuelles Feedback an, das angibt, dass mehrere Elemente aus der Liste ausgewählt werden können.

5. **Standardauswahl**: Ziehen Sie in Betracht, eine Standardauswahl für die `ListBox` festzulegen, insbesondere wenn eine Option häufiger verwendet wird als andere. Dies kann das Benutzererlebnis durch die Vorauswahl der wahrscheinlichsten Wahl verbessern.
