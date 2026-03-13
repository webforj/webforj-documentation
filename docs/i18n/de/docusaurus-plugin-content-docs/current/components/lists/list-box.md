---
sidebar_position: 15
title: ListBox
slug: listbox
_i18n_hash: 9bf0e23b101252295342c62ce6a0dee9
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-listbox" />
<DocChip chip='since' label='23.05' />
<JavadocLink type="foundation" location="com/webforj/component/list/ListBox" top='true'/>

Die `ListBox`-Komponente zeigt eine scrollbare Liste von Elementen an, die sichtbar bleibt, ohne dass ein Dropdown geöffnet werden muss. Sie unterstützt sowohl die Einzel- als auch die Mehrfachauswahl und funktioniert gut, wenn Benutzer alle verfügbaren Optionen gleichzeitig sehen müssen.

<!-- INTRO_END -->

## Anwendungen {#usages}

<ParentLink parent="List" />

1. **Benutzerrollen-Zuweisung**: In Anwendungen mit Benutzerzugriffskontrolle können Administratoren eine `ListBox` verwenden, um Rollen und Berechtigungen an Benutzer zuzuweisen. Benutzer werden aus einer Liste ausgewählt, und die Rollen oder Berechtigungen werden basierend auf ihrer Auswahl zugewiesen. Dies gewährleistet einen präzisen und kontrollierten Zugriff auf verschiedene Funktionen und Daten innerhalb der Anwendung.

2. **Projektaufgaben-Zuweisung**: In Projektmanagement-Software sind `ListBox`-Komponenten nützlich, um Aufgaben an Teammitglieder zuzuweisen. Benutzer können Aufgaben aus einer Liste auswählen und diese verschiedenen Teammitgliedern zuweisen. Dies vereinfacht die Aufgabenverteilung und stellt sicher, dass die Verantwortlichkeiten innerhalb des Teams klar definiert sind.

3. **Filterung nach mehreren Kategorien**: In einer Suchanwendung müssen Benutzer häufig Suchergebnisse basierend auf mehreren Kriterien filtern. Eine `ListBox` kann verschiedene Filteroptionen anzeigen, wie 
>- Produktmerkmale
>- Preisbereiche
>- Marken.

   Benutzer können Elemente aus jeder Filterkategorie auswählen, sodass sie die Suchergebnisse verfeinern und genau das finden können, wonach sie suchen.

4. **Inhaltskategorisierung**: In Content-Management-Systemen unterstützen `ListBox`-Komponenten die Kategorisierung von Artikeln, Bildern oder Dateien. Benutzer können eine oder mehrere Kategorien auswählen, um ihren Inhalten zuzuordnen, was die Organisation und die Suche nach Inhalten im System erleichtert.

## Auswahloptionen {#selection-options}

Standardmäßig ist die Listenbox so konfiguriert, dass die Auswahl eines einzelnen Elements zur gleichen Zeit erlaubt ist. Die `ListBox` implementiert jedoch das <JavadocLink type="foundation" location="com/webforj/component/list/MultipleSelectableList" code='true'>MultipleSelectableList</JavadocLink>-Interface, das mit einer integrierten Methode konfiguriert werden kann, die es Benutzern ermöglicht, mehrere Elemente auszuwählen, ***indem sie die `Shift`-Taste*** für die zusammenhängende Auswahl und die ***`Control` (Windows) oder `Command` (Mac)-Taste*** für die separate Auswahl mehrerer Elemente verwenden.

Verwenden Sie die <JavadocLink type="foundation" location="com/webforj/component/list/ListBox" code='true' suffix='#setSelectionMode(org.dwcj.component.list.MultipleSelectableList.SelectionMode)'>setSelectionMode()</JavadocLink>-Funktion, um diese Eigenschaft zu ändern. Diese Methode akzeptiert entweder `SelectionMode.SINGLE` oder `SelectionMode.MULTIPLE`.

:::info Verhalten auf Touch-Geräten
Auf Touch-Geräten können Benutzer, wenn die Mehrfachauswahl aktiv ist, mehrere Elemente auswählen, ohne die Umschalttaste gedrückt zu halten.
:::

Zusätzlich können die Pfeiltasten verwendet werden, um die `ListBox` zu navigieren, und das Tippen einer Buchstabentaste, während die `ListBox` den Fokus hat, wählt die Option aus, die mit diesem Buchstaben beginnt, oder durchläuft die Optionen, die mit diesem Buchstaben beginnen, sofern mehrere Optionen vorhanden sind.

<ComponentDemo 
path='/webforj/listboxmultipleselection?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/lists/listbox/ListboxMultipleSelectionView.java'
height = '250px'
/>

## Gestaltung {#styling}

<TableBuilder name="ListBox" />

## Beste Praktiken {#best-practices}

Um eine optimale Benutzererfahrung bei der Verwendung der `ChoiceBox`-Komponente sicherzustellen, berücksichtigen Sie die folgenden besten Praktiken:

1. **Priorisieren Sie die Informationshierarchie**: Stellen Sie bei der Verwendung einer `ListBox` sicher, dass die Elemente in einer logischen und hierarchischen Reihenfolge organisiert sind. Platzieren Sie die wichtigsten und am häufigsten verwendeten Optionen an oberster Stelle in der Liste. Dadurch wird es den Benutzern erleichtert, das zu finden, was sie benötigen, ohne übermäßig scrollen zu müssen.

2. **Die Länge der Liste begrenzen**: Vermeiden Sie es, Benutzer mit einer übermäßig langen `ListBox` zu überfordern. Wenn eine große Anzahl von Elementen angezeigt werden muss, sollten Sie in Erwägung ziehen, Paginierung, Suche oder Filteroptionen zu implementieren, um den Benutzern zu helfen, schnell Elemente zu finden. Alternativ können Sie die Elemente in Kategorien gruppieren, um die Listenlänge zu reduzieren.

3. **Klare und aussagekräftige Bezeichnungen**: Stellen Sie klare und aussagekräftige Bezeichnungen für jedes Element in der `ListBox` bereit. Die Benutzer sollten den Zweck jeder Option ohne Mehrdeutigkeit verstehen können. Verwenden Sie prägnante und bedeutungsvolle Elementbezeichnungen.

4. **Rückmeldung zur Mehrfachauswahl**: Wenn Ihre `ListBox` mehrfache Auswahlen zulässt, geben Sie visuelle oder textliche Rückmeldung, die darauf hinweist, dass mehrere Elemente aus der Liste ausgewählt werden können.

5. **Standardauswahl**: Erwägen Sie, eine Standardauswahl für die `ListBox` festzulegen, insbesondere wenn eine Option häufiger verwendet wird als andere. Dies kann das Benutzererlebnis durch die vorausgewählte wahrscheinlichste Wahl optimieren.
