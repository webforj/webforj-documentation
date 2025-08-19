---
title: Splitter
sidebar_position: 115
_i18n_hash: 7a830c81311c3830e4d1c36bd08903c5
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-splitter" />
<DocChip chip='since' label='24.00' />
<JavadocLink type="splitter" location="com/webforj/component/layout/splitter/Splitter" top='true'/>

Die `Splitter`-Komponente, die zum Teilen und Ändern der Größe von Inhalten in Ihrer App konzipiert ist, fasst zwei Größenänderbare Komponenten zusammen: die Master- und die Detail-Komponenten. Ein Trennzeichen trennt diese Komponenten und ermöglicht es den Benutzern, die Größe jeder Komponente dynamisch nach ihren Vorlieben anzupassen.

<ComponentDemo 
path='/webforj/splitterbasic?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/splitter/SplitterBasicView.java'
height='300px'
/>

## Minimale und maximale Größe {#min-and-max-size}

Die `Splitter`-Komponente bietet Methoden zur Festlegung der minimalen und maximalen Größen für ihre Panels, sodass Sie das Größenänderungsverhalten der Komponenten innerhalb des `Splitter` steuern können. Wenn Benutzer versuchen, Panels über die angegebenen minimalen oder maximalen Größen hinaus zu ändern, erzwingt die Splitter-Komponente diese Einschränkungen, damit die Panels innerhalb der definierten Grenzen bleiben.

### Größen festlegen {#setting-sizes}

Die Methode `setMasterMinSize(String masterMinSize)` gibt die minimale Größe für das Master-Panel des Splitters an. Ebenso gibt die Methode `setMasterMaxSize(String masterMaxSize)` die maximale Größe für das Master-Panel an.

Sie können Größen mit beliebigen gültigen CSS-Einheiten angeben, wie unten gezeigt:

<ComponentDemo 
path='/webforj/splitterminmax?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/splitter/SplitterMinMaxView.java'
height='300px'
/>

## Orientierung {#orientation}

Sie können die Orientierung in der `Splitter`-Komponente konfigurieren, sodass Sie Layouts erstellen können, die auf bestimmte Designanforderungen zugeschnitten sind. Durch die Angabe der Orientierung arrangiert die Komponente die Panels horizontal oder vertikal und bietet Flexibilität im Layout-Design.

Um die Orientierung zu konfigurieren, verwenden Sie das unterstützte Orientierungs-Enum, um anzugeben, ob der `Splitter` horizontal oder vertikal angezeigt werden soll:

<ComponentDemo 
path='/webforj/splitterorientation?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/splitter/SplitterOrientationView.java'
height='300px'
/>

## Relative Position {#relative-position}

Um die Anfangsposition der Trennleiste in der `Splitter`-Komponente festzulegen, verwenden Sie `setPositionRelative`. Diese Methode nimmt einen numerischen Wert von `0` bis `100`, der den Prozentsatz des angegebenen Raums im `Splitter` darstellt, und zeigt die Trennleiste beim angegebenen Prozentsatz der Gesamtbreite an:

<ComponentDemo 
path='/webforj/splitterposition?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/splitter/SplitterPositionView.java'
height='300px'
/>

## Verschachtelung {#nesting}

Die Verschachtelung von Splittern ermöglicht es Ihnen, komplexe Layouts mit Ebenen von größenveränderbaren Panels zu erstellen. Sie ermöglicht die Erstellung von anspruchsvollen Benutzeroberflächen mit granularer Kontrolle über die Anordnung und Größenänderung von Inhalten.

Um Splitter-Komponenten zu verschachteln, instanziieren Sie neue `Splitter`-Instanzen und fügen Sie sie als Kinder zu bestehenden `Splitter`-Komponenten hinzu. Diese hierarchische Struktur ermöglicht die Erstellung von mehrstufigen Layouts mit flexiblen Größenänderungsmöglichkeiten. Das folgende Programm demonstriert dies:

<ComponentDemo 
path='/webforj/splitternested?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/splitter/SplitterNestedView.java'
height='300px'
/>

## Automatisches Speichern {#auto-save}

Die `Splitter`-Komponente enthält eine AutoSave-Option, mit der der Zustand der Panelgrößen im lokalen Speicher gespeichert wird, um die Abmessungen zwischen Neuladen konsistent zu halten.

Wenn Sie die Auto-Save-Konfiguration festlegen, speichert die `Splitter`-Komponente automatisch den Zustand der Panelgrößen im lokalen Speicher des Webbrowsers. Dies stellt sicher, dass die Größen, die Benutzer für Panels wählen, über Seitenaktualisierungen oder Browsersitzungen hinweg bestehen bleiben, wodurch die Notwendigkeit manueller Anpassungen verringert wird.

### Zustand bereinigen {#cleaning-the-state}

Um den `Splitter` programmgesteuert auf die Standardeinstellungen und Abmessungen zurückzusetzen, rufen Sie die Methode `cleanState()` auf, um alle gespeicherten Zustanddaten, die mit der `Splitter`-Komponente im lokalen Speicher des Webbrowsers verbunden sind, zu entfernen.

<ComponentDemo 
path='/webforj/splitterautosave?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/splitter/SplitterAutoSaveView.java'
height='400px'
/>

In der vorhergehenden Demo aktiviert jede Splitter-Instanz die AutoSave-Funktion, indem sie die Methode `setAutosave` aufruft. Dadurch wird sichergestellt, dass die Panelgrößen automatisch im lokalen Speicher gespeichert werden. Wenn der Browser neu geladen wird, bleiben die Größen dieser Splitter gleich.

Ein Klick auf die Schaltfläche "Zustand löschen" ruft die Methode `cleanState()` auf und aktualisiert das Browserfenster, um die ursprünglichen Abmessungen anzuzeigen.

## Styling {#styling}

<TableBuilder name="Splitter" />

## Beste Praktiken {#best-practices}

Um eine optimale Benutzererfahrung bei der Verwendung der `Splitter`-Komponente sicherzustellen, beachten Sie die folgenden besten Praktiken: 

- **Anpassen basierend auf Inhalten**: Berücksichtigen Sie beim Festlegen der Orientierung und der Anfangsgrößen der Panels die Priorität des Inhalts. Beispielsweise sollte in einem Layout mit einer Navigationsleiste und einem Hauptinhaltbereich die Leiste typischerweise schmaler bleiben, mit einer festgelegten minimalen Größe für eine klare Navigation.

- **Strategische Verschachtelung**: Das Verschachteln von Splittern kann vielseitige Layouts schaffen, kann jedoch die Benutzeroberfläche komplizieren und die Leistung beeinflussen. Planen Sie Ihre verschachtelten Layouts, um sicherzustellen, dass sie intuitiv sind und die Benutzererfahrung verbessern.

- **Benutzereinstellungen beachten**: Verwenden Sie die AutoSave-Funktion, um die Anpassungen der Benutzer über Sitzungen hinweg zu speichern und die Benutzererfahrung zu verbessern. Bieten Sie eine Option an, damit Benutzer zu den Standardeinstellungen zurückkehren können.
