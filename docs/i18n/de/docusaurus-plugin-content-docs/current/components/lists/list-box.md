---
sidebar_position: 15
title: ListBox
slug: listbox
description: >-
  Show a scrollable, always-visible list with the ListBox component, supporting
  single or multiple selection and keyboard navigation.
_i18n_hash: ea83ed0b82b2f6f91d7fbe9dedebeef2
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-listbox" />
<DocChip chip='since' label='23.05' />
<JavadocLink type="foundation" location="com/webforj/component/list/ListBox" top='true'/>

Die `ListBox`-Komponente zeigt eine scrollbar Liste von Elementen an, die sichtbar bleibt, ohne ein Dropdown öffnen zu müssen. Sie unterstützt sowohl die Einzel- als auch die Mehrfachauswahl und funktioniert gut, wenn die Benutzer alle verfügbaren Optionen gleichzeitig sehen müssen.

<!-- INTRO_END -->

## Anwendungen {#usages}

<ParentLink parent="List" />

1. **Benutzerrollenzuweisung**: In Anwendungen mit Benutzerzugriffskontrolle können Administratoren eine `ListBox` verwenden, um Rollen und Berechtigungen Benutzern zuzuweisen. Benutzer werden aus einer Liste ausgewählt, und die Rollen oder Berechtigungen werden basierend auf ihrer Auswahl zugewiesen. Dies gewährleistet einen präzisen und kontrollierten Zugriff auf verschiedene Funktionen und Daten innerhalb der Anwendung.

2. **Projektaufgabenverteilung**: In Projektmanagement-Software sind `ListBox`-Komponenten nützlich, um Aufgaben an Teammitglieder zuzuweisen. Benutzer können Aufgaben aus einer Liste auswählen und sie verschiedenen Teammitgliedern zuweisen. Dies vereinfacht die Aufgabenverteilung und stellt sicher, dass die Verantwortlichkeiten innerhalb des Teams klar definiert sind.

3. **Filterung nach mehreren Kategorien**: In einer Suchanwendung müssen Benutzer häufig Suchergebnisse basierend auf mehreren Kriterien filtern. Eine `ListBox` kann verschiedene Filteroptionen anzeigen, wie zum Beispiel
>- Produktmerkmale
>- Preisbereiche
>- Marken.

  Benutzer können Elemente aus jeder Filterkategorie auswählen, sodass sie die Suchergebnisse verfeinern und genau das finden können, wonach sie suchen.

4. **Inhaltskategorisierung**: In Content-Management-Systemen helfen `ListBox`-Komponenten dabei, Artikel, Bilder oder Dateien zu kategorisieren. Benutzer können eine oder mehrere Kategorien auswählen, um ihre Inhalte zuzuordnen, was die Organisation und Suche nach Inhalten im System erleichtert.

## Auswahloptionen {#selection-options}

Standardmäßig ist die ListBox so konfiguriert, dass die Auswahl eines einzelnen Elements zur gleichen Zeit ermöglicht wird. Die `ListBox` implementiert jedoch das <JavadocLink type="foundation" location="com/webforj/component/list/MultipleSelectableList" code='true'>MultipleSelectableList</JavadocLink>-Interface, das mit einer integrierten Methode konfiguriert werden kann, die es den Benutzern ermöglicht, mehrere Elemente ***mit der `Shift`-Taste*** für die Auswahl benachbarter Einträge und ***`Control` (Windows) oder `Command` (Mac)*** für die separate Auswahl mehrerer Elemente auszuwählen.

Verwenden Sie die <JavadocLink type="foundation" location="com/webforj/component/list/ListBox" code='true' suffix='#setSelectionMode(org.dwcj.component.list.MultipleSelectableList.SelectionMode)'>setSelectionMode()</JavadocLink>-Funktion, um diese Eigenschaft zu ändern. Diese Methode akzeptiert entweder `SelectionMode.SINGLE` oder `SelectionMode.MULTIPLE`.

:::info Verhalten auf Touch-Geräten
Auf Touch-Geräten können Benutzer, wenn die Mehrfachauswahl aktiviert ist, mehrere Elemente auswählen, ohne die Shift-Taste gedrückt zu halten.
:::

Zusätzlich können die Pfeiltasten verwendet werden, um durch die `ListBox` zu navigieren, und das Tippen einer Buchstabentaste, während die `ListBox` den Fokus hat, wählt die Option aus, die mit diesem Buchstaben beginnt, oder schaltet durch die Optionen, die mit diesem Buchstaben beginnen, falls mehrere Optionen vorhanden sind.

<ComponentDemo
path='/webforj/listboxmultipleselection'
files={['src/main/java/com/webforj/samples/views/lists/listbox/ListboxMultipleSelectionView.java']}
height='250px'
/>

## Styling {#styling}

<TableBuilder name="ListBox" />

## Best Practices {#best-practices}

Um ein optimales Benutzererlebnis bei der Verwendung der `ChoiceBox`-Komponente zu gewährleisten, sollten die folgenden Best Practices berücksichtigt werden:

1. **Priorisieren Sie die Informationshierarchie**: Stellen Sie bei der Verwendung einer `ListBox` sicher, dass die Elemente in einer logischen und hierarchischen Reihenfolge organisiert sind. Platzieren Sie die wichtigsten und am häufigsten verwendeten Optionen an den Anfang der Liste. Dies erleichtert es den Benutzern, was sie benötigen, ohne übermäßiges Scrollen zu müssen.

2. **Liste nicht überlang machen**: Vermeiden Sie es, Benutzer mit einer übermäßig langen `ListBox` zu überfordern. Wenn eine große Anzahl von Elementen angezeigt werden muss, ziehen Sie in Betracht, Paginierungs-, Such- oder Filteroptionen zu implementieren, um den Benutzern zu helfen, Elemente schnell zu finden. Alternativ können Sie die Elemente in Kategorien gruppieren, um die Länge der Liste zu reduzieren.

3. **Klare und beschreibende Beschriftungen**: Geben Sie für jedes Element in der `ListBox` klare und beschreibende Beschriftungen an. Die Benutzer sollten den Zweck jeder Option ohne Mehrdeutigkeit verstehen können. Verwenden Sie prägnante und aussagekräftige Elementbeschriftungen.

4. **Feedback zur Mehrfachauswahl**: Wenn Ihre `ListBox` mehrere Auswahlen zulässt, geben Sie visuelles oder textuelles Feedback, das darauf hinweist, dass mehrere Elemente aus der Liste ausgewählt werden können.

5. **Standardauswahl**: Ziehen Sie in Betracht, eine Standardauswahl für die `ListBox` festzulegen, insbesondere wenn eine Option häufiger verwendet wird als andere. Dies kann das Benutzererlebnis optimieren, indem die wahrscheinlichste Auswahl vorausgewählt wird.
