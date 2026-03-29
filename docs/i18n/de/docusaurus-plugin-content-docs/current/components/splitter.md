---
title: Splitter
sidebar_position: 115
_i18n_hash: 2f66a9093a3c1f6e339df8fb42048a55
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-splitter" />
<DocChip chip='since' label='24.00' />
<JavadocLink type="splitter" location="com/webforj/component/layout/splitter/Splitter" top='true'/>

Die `Splitter`-Komponente, die entwickelt wurde, um Inhalte innerhalb Ihrer App zu teilen und zu resize, kapselt zwei anpassbare Komponenten: die Master- und die Detailkomponenten. Ein Trennstrich trennt diese Komponenten und ermöglicht es den Benutzern, die Größe jeder Komponente dynamisch nach ihren Wünschen anzupassen.

<!-- INTRO_END -->

## Erstellen eines Splitters {#creating-a-splitter}

Erstellen Sie einen `Splitter`, indem Sie zwei Komponenten an seinen Konstruktor übergeben. Die erste wird das Master-Panel und die zweite das Detail-Panel.

<ComponentDemo 
path='/webforj/splitterbasic?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/splitter/SplitterBasicView.java'
height='300px'
/>

## Mindest- und Höchstgröße {#min-and-max-size}

Die `Splitter`-Komponente bietet Methoden zum Festlegen von Mindest- und Höchstgrößen für ihre Panels, sodass Sie das Resize-Verhalten der Komponenten innerhalb des `Splitters` steuern können. Wenn Benutzer versuchen, Panels über die angegebenen Mindest- oder Höchstgrößen hinaus zu resized, erzwingt die Splitter-Komponente diese Einschränkungen und stellt sicher, dass die Panels innerhalb der definierten Grenzen bleiben.

### Größen festlegen {#setting-sizes}

Die Methode `setMasterMinSize(String masterMinSize)` gibt die Mindestgröße für das Master-Panel des Splitters an. Ebenso gibt die Methode `setMasterMaxSize(String masterMaxSize)` die Höchstgröße für das Master-Panel an.

Sie können Größen mit beliebigen gültigen CSS-Einheiten angeben, wie unten gezeigt:

<ComponentDemo 
path='/webforj/splitterminmax?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/splitter/SplitterMinMaxView.java'
height='300px'
/>

## Ausrichtung {#orientation}

Sie können die Ausrichtung in der `Splitter`-Komponente konfigurieren, sodass Sie Layouts erstellen, die auf spezifische Designanforderungen abgestimmt sind. Durch Festlegen der Ausrichtung ordnet die Komponente Panels horizontal oder vertikal an und bietet Flexibilität im Layoutdesign.

Um die Ausrichtung zu konfigurieren, verwenden Sie das unterstützte Ausrichtungen-Enum, um anzugeben, ob der `Splitter` horizontal oder vertikal gerendert werden soll:

<ComponentDemo 
path='/webforj/splitterorientation?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/splitter/SplitterOrientationView.java'
height='300px'
/>

## Relative Position {#relative-position}

Um die Anfangsposition des Trennstrichs in der `Splitter`-Komponente festzulegen, verwenden Sie `setPositionRelative`. Diese Methode nimmt einen numerischen Wert von `0` bis `100` an, der den Prozentsatz des gegebenen Raums im `Splitter` darstellt, und zeigt den Trennstrich am angegebenen Prozentsatz der Gesamtbreite an:

<ComponentDemo 
path='/webforj/splitterposition?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/splitter/SplitterPositionView.java'
height='300px'
/>

## Verschachtelung {#nesting}

Die Verschachtelung von Splittern ermöglicht es Ihnen, komplexe Layouts mit mehreren anpassbaren Panels zu erstellen. Dies ermöglicht die Erstellung von anspruchsvollen Benutzeroberflächen mit granularer Kontrolle über die Anordnung und das Resize von Inhalten.

Um Splitter-Komponenten zu verschachteln, instanziieren Sie neue `Splitter`-Instanzen und fügen Sie sie als Kinder bestehenden `Splitter`-Komponenten hinzu. Diese hierarchische Struktur ermöglicht die Erstellung von mehrstufigen Layouts mit flexiblen Resize-Möglichkeiten. Das folgende Programm demonstriert dies:

<ComponentDemo 
path='/webforj/splitternested?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/splitter/SplitterNestedView.java'
height='300px'
/>

## Automatisches Speichern {#auto-save}

Die `Splitter`-Komponente umfasst eine AutoSave-Option, die den Zustand der Panelgrößen im lokalen Speicher speichert, um die Abmessungen zwischen Neuladen konsistent zu halten.

Wenn Sie die Auto-Save-Konfiguration einstellen, speichert die `Splitter`-Komponente automatisch den Zustand der Panelgrößen im lokalen Speicher des Webbrowsers. Dadurch bleibt sichergestellt, dass die Größen, die Benutzer für Panels auswählen, bei Seitenneuladen oder Browsersitzungen beibehalten werden, was die Notwendigkeit manueller Anpassungen verringert.

### Zustand zurücksetzen {#cleaning-the-state}

Um die `Splitter`-Komponente programmatisch auf die Standardwerte und -abmessungen zurückzusetzen, rufen Sie die Methode `cleanState()` auf, um alle gespeicherten Zustandsdaten im Zusammenhang mit der `Splitter`-Komponente aus dem lokalen Speicher des Webbrowsers zu entfernen.

<ComponentDemo 
path='/webforj/splitterautosave?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/splitter/SplitterAutoSaveView.java'
height='400px'
/>

Im vorhergehenden Demo aktiviert jede Splitter-Instanz die AutoSave-Funktion, indem sie die Methode `setAutosave` aufruft. Dadurch bleiben die Panelgrößen automatisch im lokalen Speicher gespeichert. Somit bleiben die Größen dieser Splitter beim Neuladen des Browsers unverändert.

Ein Klick auf die Schaltfläche "Zustand zurücksetzen" ruft die Methode `cleanState()` auf und aktualisiert das Browserfenster, um die ursprünglichen Abmessungen anzuzeigen.

## Styling {#styling}

<TableBuilder name="Splitter" />

## Beste Praktiken {#best-practices}

Um ein optimales Benutzererlebnis bei der Verwendung der `Splitter`-Komponente zu gewährleisten, berücksichtigen Sie die folgenden besten Praktiken:

- **Anpassung an den Inhalt**: Berücksichtigen Sie bei der Entscheidung über die Ausrichtung und die anfänglichen Größen der Panels die Priorität des Inhalts. Beispielsweise sollte in einem Layout mit einer Navigationsleiste und einem Hauptinhaltsbereich die Navigationsleiste in der Regel schmaler bleiben mit einer festgelegten Mindestgröße für eine klare Navigation.

- **Strategische Verschachtelung**: Die Verschachtelung von Splittern kann vielseitige Layouts erstellen, kann jedoch die Benutzeroberfläche kompliziert machen und die Leistung beeinträchtigen. Planen Sie Ihre verschachtelten Layouts so, dass sie intuitiv sind und das Benutzererlebnis verbessern.

- **Benutzereinstellungen berücksichtigen**: Nutzen Sie die AutoSave-Funktion, um Benutzereinstellungen über Sitzungen hinweg zu speichern und das Benutzererlebnis zu verbessern. Stellen Sie eine Option zur Verfügung, die es Benutzern ermöglicht, zu den Standardeinstellungen zurückzukehren.
