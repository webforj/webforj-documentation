---
sidebar_position: 15
title: ListBox
slug: listbox
_i18n_hash: 7bd48c55ca5607255c1d6503c500a25d
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-listbox" />
<DocChip chip='since' label='23.05' />
<JavadocLink type="foundation" location="com/webforj/component/list/ListBox" top='true'/>

<ParentLink parent="List" />

Der `ListBox`-Komponente ist ein Benutzerschnittstellenelement, das dazu dient, eine scrollbare Liste von Objekten anzuzeigen und es Benutzern zu ermöglichen, einzelne oder mehrere Elemente aus der Liste auszuwählen. Benutzer können auch mit dem `ListBox` über die Pfeiltasten interagieren.

## Usages {#usages}

1. **Zuweisung von Benutzerrollen**: In Anwendungen mit Benutzerzugriffskontrolle können Administratoren ein `ListBox` verwenden, um Rollen und Berechtigungen an Benutzer zuzuweisen. Benutzer werden aus einer Liste ausgewählt, und die Rollen oder Berechtigungen werden basierend auf ihrer Auswahl zugewiesen. Dies gewährleistet präzisen und kontrollierten Zugriff auf verschiedene Funktionen und Daten innerhalb der Anwendung.

2. **Zuweisung von Projektaufgaben**: In Projektmanagement-Software sind `ListBox`-Komponenten nützlich für die Zuweisung von Aufgaben an Teammitglieder. Benutzer können Aufgaben aus einer Liste auswählen und sie verschiedenen Teammitgliedern zuweisen. Dies vereinfacht die Aufgabenverteilung und stellt sicher, dass Verantwortlichkeiten innerhalb des Teams klar definiert sind.

3. **Mehrkategorie-Filterung**: In einer Suchanwendung müssen Benutzer häufig Suchergebnisse anhand mehrerer Kriterien filtern. Eine `ListBox` kann verschiedene Filteroptionen anzeigen, wie 
>- Produktmerkmale
>- Preiskategorien
>- Marken.

  Benutzer können Elemente aus jeder Filterkategorie auswählen, sodass sie die Suchergebnisse verfeinern und genau das finden können, wonach sie suchen.

4. **Inhaltskategorisierung**: In Content-Management-Systemen helfen `ListBox`-Komponenten bei der Kategorisierung von Artikeln, Bildern oder Dateien. Benutzer können eine oder mehrere Kategorien auswählen, um sie mit ihrem Inhalt zu verknüpfen, wodurch die Organisation und Suche nach Inhaltelementen im System erleichtert wird.

## Selection Options {#selection-options}

Standardmäßig ist das Listenelement so konfiguriert, dass nur ein Element gleichzeitig ausgewählt werden kann. Der `ListBox` implementiert jedoch das <JavadocLink type="foundation" location="com/webforj/component/list/MultipleSelectableList" code='true'>MultipleSelectableList</JavadocLink>-Interface, das mit einer integrierten Methode konfiguriert werden kann, die es Benutzern ermöglicht, mehrere Elemente ***unter Verwendung der `Shift`-Taste*** für zusammenhängende Eingabewahlen und ***`Control` (Windows) oder `Command` (Mac)*** für separate, mehrfache Auswahl von Elementen auszuwählen.

Verwenden Sie die <JavadocLink type="foundation" location="com/webforj/component/list/ListBox" code='true' suffix='#setSelectionMode(org.dwcj.component.list.MultipleSelectableList.SelectionMode)'>setSelectionMode()</JavadocLink>-Funktion, um diese Einstellung zu ändern. Diese Methode akzeptiert entweder `SelectionMode.SINGLE` oder `SelectionMode.MULTIPLE`.

:::info Verhalten auf Touch-Geräten
Auf Touch-Geräten können Benutzer bei aktivierter Mehrfachauswahl mehrere Elemente auswählen, ohne die Umschalttaste gedrückt zu halten.
:::

Zusätzlich können die Pfeiltasten verwendet werden, um durch die `ListBox` zu navigieren, und das Tippen einer Buchstabentaste während der `ListBox` den Fokus hat, wählt die Option aus, die mit diesem Buchstaben beginnt, oder durchläuft die Optionen, die mit diesem Buchstaben beginnen, falls mehrere Optionen vorhanden sind.

<ComponentDemo 
path='/webforj/listboxmultipleselection?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/lists/listbox/ListboxMultipleSelectionView.java'
height = '250px'
/>

## Styling {#styling}

<TableBuilder name="ListBox" />

## Best Practices {#best-practices}

Um ein optimales Benutzererlebnis beim Verwenden der `ChoiceBox`-Komponente zu gewährleisten, sollten die folgenden Best Practices berücksichtigt werden:

1. **Priorität der Informationshierarchie**: Achten Sie beim Verwenden eines `ListBox` darauf, dass die Elemente in einer logischen und hierarchischen Reihenfolge organisiert sind. Platzieren Sie die wichtigsten und am häufigsten verwendeten Optionen oben in der Liste. Dies erleichtert den Benutzern das Auffinden der benötigten Elemente, ohne zu viel scrollen zu müssen.

2. **Liste begrenzen**: Vermeiden Sie es, Benutzer mit einer übermäßig langen `ListBox` zu überfordern. Wenn eine große Anzahl von Elementen angezeigt werden muss, sollten Sie die Implementierung von Seitenzahlen, Such- oder Filteroptionen in Betracht ziehen, um den Benutzern zu helfen, schnell Artikel zu finden. Alternativ können Sie die Elemente in Kategorien gruppieren, um die Länge der Liste zu reduzieren.

3. **Klare und beschreibende Beschriftungen**: Stellen Sie für jedes Element in der `ListBox` klare und beschreibende Beschriftungen bereit. Die Benutzer sollten den Zweck jeder Option ohne Mehrdeutigkeit verstehen können. Verwenden Sie prägnante und aussagekräftige Elementbeschriftungen.

4. **Mehrfachauswahl-Feedback**: Wenn Ihre `ListBox` Mehrfachauswahlen zulässt, geben Sie visuelles oder textliches Feedback, das darauf hinweist, dass mehrere Elemente aus der Liste ausgewählt werden können.

5. **Standardauswahl**: Ziehen Sie in Betracht, eine Standardauswahl für die `ListBox` festzulegen, insbesondere wenn eine Option häufiger verwendet wird als andere. Dies kann das Benutzererlebnis durch die Vorauswahl der wahrscheinlichsten Wahl optimieren.
