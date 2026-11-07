---
sidebar_position: 15
title: ListBox
slug: listbox
description: >-
  Show a scrollable, always-visible list with the ListBox component, supporting
  single or multiple selection and keyboard navigation.
_i18n_hash: 0a9d307607ef33fdebb557f190abd7cf
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-listbox" />
<DocChip chip='since' label='23.05' />
<JavadocLink type="foundation" location="com/webforj/component/list/ListBox" top='true'/>

Die `ListBox`-Komponente zeigt eine scrollbare Liste von Elementen an, die sichtbar bleibt, ohne dass ein Dropdown geöffnet werden muss. Sie unterstützt sowohl die Einzel- als auch die Mehrfachauswahl und eignet sich besonders, wenn Benutzer alle verfügbaren Optionen gleichzeitig sehen müssen.

<!-- INTRO_END -->

## Verwendung {#usages}

<ParentLink parent="List" />

1. **Zuweisung von Benutzerrollen**: In Anwendungen mit Zugriffskontrolle können Administratoren eine `ListBox` verwenden, um Benutzern Rollen und Berechtigungen zuzuweisen. Benutzer werden aus einer Liste ausgewählt, und die Rollen oder Berechtigungen werden entsprechend ihrer Auswahl vergeben. So wird ein präziser und kontrollierter Zugriff auf verschiedene Funktionen und Daten innerhalb der Anwendung gewährleistet.

2. **Zuweisung von Projektaufgaben**: In Projektmanagement-Software eignen sich `ListBox`-Komponenten, um Teammitgliedern Aufgaben zuzuweisen. Benutzer können Aufgaben aus einer Liste auswählen und sie verschiedenen Teammitgliedern zuweisen. Das vereinfacht die Aufgabenverteilung und stellt sicher, dass die Verantwortlichkeiten innerhalb des Teams klar definiert sind.

3. **Filterung nach mehreren Kategorien**: In einer Suchanwendung müssen Benutzer Suchergebnisse häufig anhand mehrerer Kriterien filtern. Eine `ListBox` kann verschiedene Filteroptionen anzeigen, beispielsweise
>- Produktmerkmale
>- Preisspannen
>- Marken.

  Benutzer können Elemente aus jeder Filterkategorie auswählen, um die Suchergebnisse einzugrenzen und genau das zu finden, wonach sie suchen.

4. **Kategorisierung von Inhalten**: In Content-Management-Systemen helfen `ListBox`-Komponenten dabei, Artikel, Bilder oder Dateien zu kategorisieren. Benutzer können eine oder mehrere Kategorien auswählen, die ihren Inhalten zugeordnet werden. So lassen sich Inhalte im System leichter organisieren und durchsuchen.

## Auswahloptionen {#selection-options}

Standardmäßig ist das Listenfeld so konfiguriert, dass jeweils ein Element ausgewählt werden kann. Die `ListBox` implementiert jedoch die Schnittstelle <JavadocLink type="foundation" location="com/webforj/component/list/MultipleSelectableList" code='true'>MultipleSelectableList</JavadocLink>, die mit einer integrierten Methode konfiguriert werden kann. Damit können Benutzer mehrere Elemente auswählen: ***mit der Taste <kbd>Shift</kbd>*** für zusammenhängende Einträge und ***mit <kbd>Ctrl</kbd> (Windows) oder <kbd>Cmd</kbd> (Mac)*** für die Auswahl mehrerer, nicht zusammenhängender Einträge.

Verwenden Sie die Funktion <JavadocLink type="foundation" location="com/webforj/component/list/ListBox" code='true' suffix='#setSelectionMode(org.dwcj.component.list.MultipleSelectableList.SelectionMode)'>setSelectionMode()</JavadocLink>, um diese Eigenschaft zu ändern. Diese Methode akzeptiert entweder `SelectionMode.SINGLE` oder `SelectionMode.MULTIPLE`.

:::info Verhalten auf Touchgeräten
Auf Touchgeräten können Benutzer bei aktivierter Mehrfachauswahl mehrere Elemente auswählen, ohne die Umschalttaste gedrückt zu halten.
:::

Außerdem können die Pfeiltasten zur Navigation in der `ListBox` verwendet werden. Wenn die `ListBox` den Fokus hat, wird durch Eingabe eines Buchstabens die Option ausgewählt, die mit diesem Buchstaben beginnt. Gibt es mehrere passende Optionen, kann durch sie navigiert werden.

<ComponentDemo
path='/webforj/listboxmultipleselection'
files={['src/main/java/com/webforj/samples/views/lists/listbox/ListboxMultipleSelectionView.java']}
height='250px'
/>

## Gestaltung {#styling}

<TableBuilder name="ListBox" />

## Bewährte Vorgehensweisen {#best-practices}

Beachten Sie die folgenden bewährten Vorgehensweisen, um bei Verwendung der `ChoiceBox`-Komponente eine optimale Benutzererfahrung zu gewährleisten:

1. **Informationshierarchie priorisieren**: Achten Sie bei Verwendung einer `ListBox` darauf, die Elemente logisch und hierarchisch anzuordnen. Platzieren Sie die wichtigsten und am häufigsten verwendeten Optionen am Anfang der Liste. So finden Benutzer leichter, was sie benötigen, ohne übermäßig scrollen zu müssen.

2. **Listenlänge begrenzen**: Vermeiden Sie eine übermäßig lange `ListBox`, die Benutzer überfordert. Wenn viele Elemente angezeigt werden müssen, sollten Sie Seitenaufteilung, Such- oder Filteroptionen einführen, damit Benutzer Elemente schnell finden können. Alternativ können Sie die Elemente in Kategorien gruppieren, um die Liste zu verkürzen.

3. **Klare und aussagekräftige Beschriftungen**: Verwenden Sie für jedes Element in der `ListBox` eine klare und aussagekräftige Beschriftung. Benutzer sollten den Zweck jeder Option eindeutig verstehen können. Achten Sie auf prägnante und verständliche Beschriftungen.

4. **Feedback zur Mehrfachauswahl**: Wenn Ihre `ListBox` Mehrfachauswahl zulässt, geben Sie visuelles oder textuelles Feedback, das darauf hinweist, dass mehrere Elemente aus der Liste ausgewählt werden können.

5. **Standardauswahl**: Erwägen Sie, eine Standardauswahl für die `ListBox` festzulegen, insbesondere wenn eine Option häufiger verwendet wird als andere. So lässt sich die Benutzererfahrung verbessern, indem die wahrscheinlichste Auswahl bereits vorausgewählt ist.
