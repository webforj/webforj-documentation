---
sidebar_position: 15
title: ListBox
slug: listbox
_i18n_hash: 33476ec9bd7a601aaec3f1e37e7c099f
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-listbox" />
<DocChip chip='since' label='23.05' />
<JavadocLink type="foundation" location="com/webforj/component/list/ListBox" top='true'/>

<ParentLink parent="List" />

Das `ListBox`-Komponente ist ein Benutzeroberflächenelement, das entwickelt wurde, um eine scrollbar Liste von Objekten anzuzeigen und es den Nutzern zu ermöglichen, einzelne oder mehrere Elemente aus der Liste auszuwählen. Nutzer können auch mit dem `ListBox` über die Pfeiltasten interagieren.

## Usages {#usages}

1. **Zuweisung von Benutzerrollen**: In Anwendungen mit Zugriffskontrolle können Administratoren ein `ListBox` verwenden, um Rollen und Berechtigungen an Benutzer zuzuweisen. Benutzer werden aus einer Liste ausgewählt, und die Rollen oder Berechtigungen werden basierend auf ihrer Auswahl zugewiesen. Dies gewährleistet einen präzisen und kontrollierten Zugriff auf verschiedene Funktionen und Daten innerhalb der Anwendung.

2. **Zuweisung von Projektaufgaben**: In Projektmanagementsoftware sind `ListBox`-Komponenten nützlich für die Zuweisung von Aufgaben an Teammitglieder. Benutzer können Aufgaben aus einer Liste auswählen und sie verschiedenen Teammitgliedern zuweisen. Dies vereinfacht die Aufgabenverteilung und stellt sicher, dass Verantwortlichkeiten innerhalb des Teams klar definiert sind.

3. **Mehrkategorien-Filterung**: In einer Suchanwendung müssen Benutzer oft Suchergebnisse basierend auf mehreren Kriterien filtern. Ein `ListBox` kann verschiedene Filteroptionen anzeigen, wie 
>- Produktmerkmale
>- Preisspannen
>- Marken.

  Benutzer können Elemente aus jeder Filterkategorie auswählen, sodass sie die Suchergebnisse verfeinern und genau das finden können, wonach sie suchen.

4. **Inhaltskategorisierung**: In Content-Management-Systemen unterstützen `ListBox`-Komponenten bei der Kategorisierung von Artikeln, Bildern oder Dateien. Benutzer können eine oder mehrere Kategorien auswählen, um sie mit ihrem Inhalt zu verknüpfen, was das Organisieren und Suchen von Inhaltelementen im System erleichtert.

## Auswahloptionen {#selection-options}

Standardmäßig ist das Listenfeld so konfiguriert, dass die Auswahl eines einzelnen Elements zur gleichen Zeit erlaubt ist. Der `ListBox` implementiert jedoch das <JavadocLink type="foundation" location="com/webforj/component/list/MultipleSelectableList" code='true'>MultipleSelectableList</JavadocLink>-Interface, welches mit einer integrierten Methode konfiguriert werden kann, die es Nutzern ermöglicht, mehrere Elemente ***unter Verwendung der `Shift`-Taste*** für zusammenhängende Auswahl und ***`Control` (Windows) oder `Command` (Mac) Taste*** für separate, mehrere Auswahl zu wählen.

Verwenden Sie die <JavadocLink type="foundation" location="com/webforj/component/list/ListBox" code='true' suffix='#setSelectionMode(org.dwcj.component.list.MultipleSelectableList.SelectionMode)'>setSelectionMode()</JavadocLink>-Funktion, um diese Eigenschaft zu ändern. Diese Methode akzeptiert entweder `SelectionMode.SINGLE` oder `SelectionMode.MULTIPLE`.

:::info Verhalten bei Touch-Geräten
Auf Touch-Geräten können Benutzer bei aktivierter Mehrfachauswahl mehrere Elemente auswählen, ohne die Shift-Taste gedrückt zu halten.
:::

Darüber hinaus können die Pfeiltasten verwendet werden, um durch den `ListBox` zu navigieren, und das Tippen einer Buchstabentaste, während der `ListBox` den Fokus hat, wählt die Option aus, die mit diesem Buchstaben beginnt, oder durchläuft die Optionen, die mit diesem Buchstaben beginnen, falls mehrere Optionen vorhanden sind.

<ComponentDemo 
path='/webforj/listboxmultipleselection?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/lists/listbox/ListboxMultipleSelectionView.java'
height = '250px'
/>

## Styling {#styling}

<TableBuilder name="ListBox" />

## Best Practices {#best-practices}

Um ein optimales Benutzererlebnis bei der Verwendung der `ChoiceBox`-Komponente sicherzustellen, sollten die folgenden Best Practices berücksichtigt werden:

1. **Priorisieren Sie die Informationshierarchie**: Stellen Sie beim Einsatz eines `ListBox` sicher, dass die Elemente in einer logischen und hierarchischen Reihenfolge organisiert sind. Platzieren Sie die wichtigsten und am häufigsten verwendeten Optionen an oberster Stelle in der Liste. Dadurch können Benutzer das finden, was sie benötigen, ohne übermäßig scrollen zu müssen.

2. **Begrenzen Sie die Listenlänge**: Vermeiden Sie es, Benutzer mit einer übermäßig langen `ListBox` zu überfordern. Wenn eine große Anzahl von Elementen angezeigt werden muss, ziehen Sie in Betracht, Paginierung, Such- oder Filteroptionen zu implementieren, um den Benutzern zu helfen, schnell Elemente zu finden. Alternativ können Sie Elemente in Kategorien gruppieren, um die Listenlänge zu reduzieren.

3. **Klare und beschreibende Labels**: Stellen Sie für jedes Element im `ListBox` klare und beschreibende Labels zur Verfügung. Benutzer sollten den Zweck jeder Option ohne Mehrdeutigkeit verstehen können. Verwenden Sie prägnante und bedeutungsvolle Elementbezeichnungen.

4. **Mehrauswahl-Feedback**: Wenn Ihr `ListBox` mehrere Auswahlen zulässt, bieten Sie visuelles oder textuelles Feedback, das anzeigt, dass mehrere Elemente aus der Liste ausgewählt werden können.

5. **Standardauswahl**: Ziehen Sie in Betracht, eine Standardauswahl für den `ListBox` festzulegen, insbesondere wenn eine Option häufiger als andere verwendet wird. Dies kann das Benutzererlebnis durch die Vorauswahl der wahrscheinlichsten Wahl optimieren.
