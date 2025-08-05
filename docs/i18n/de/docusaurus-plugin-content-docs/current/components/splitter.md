---
title: Splitter
sidebar_position: 115
_i18n_hash: 0f8ea00bed7b930d5b7a8efe6bcd5446
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-splitter" />
<DocChip chip='since' label='24.00' />
<JavadocLink type="splitter" location="com/webforj/component/layout/splitter/Splitter" top='true'/>

Die `Splitter`-Komponente, die zum Teilen und Ändern der Größe von Inhalten innerhalb Ihrer App konzipiert ist, umfasst zwei anpassbare Komponenten: die Master- und die Detailkomponenten. Ein Trennstück trennt diese Komponenten und ermöglicht es Benutzern, die Größe jeder Komponente entsprechend ihren Wünschen dynamisch anzupassen.

<ComponentDemo 
path='/webforj/splitterbasic?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/splitter/SplitterBasicView.java'
height='300px'
/>

## Minimale und maximale Größe {#min-and-max-size}

Die `Splitter`-Komponente bietet Methoden zum Festlegen von minimalen und maximalen Größen für ihre Paneele, wodurch Sie das Verhalten beim Ändern der Größe der Komponenten innerhalb des `Splitter` steuern können. Wenn Benutzer versuchen, die Paneele über die angegebenen minimalen oder maximalen Größen hinaus zu vergrößern, erzwingt die Splitter-Komponente diese Einschränkungen und stellt sicher, dass die Paneele innerhalb der definierten Grenzen bleiben.

### Größen festlegen {#setting-sizes}

Die Methode `setMasterMinSize(String masterMinSize)` gibt die minimale Größe für das Master-Paneel des Splitters an. Ebenso gibt die Methode `setMasterMaxSize(String masterMaxSize)` die maximale Größe für das Master-Paneel an.

Sie können Größen mit beliebigen gültigen CSS-Einheiten angeben, wie unten gezeigt:

<ComponentDemo 
path='/webforj/splitterminmax?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/splitter/SplitterMinMaxView.java'
height='300px'
/>

## Ausrichtung {#orientation}

Sie können die Ausrichtung in der `Splitter`-Komponente konfigurieren, wodurch Sie Layouts erstellen können, die auf spezifische Designanforderungen zugeschnitten sind. Durch die Angabe der Ausrichtung ordnet die Komponente die Paneele horizontal oder vertikal an und bietet so Vielseitigkeit im Layout-Design.

Um die Ausrichtung zu konfigurieren, verwenden Sie das unterstützte Ausrichtungen-Enum, um anzugeben, ob der `Splitter` horizontal oder vertikal gerendert werden soll:

<ComponentDemo 
path='/webforj/splitterorientation?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/splitter/SplitterOrientationView.java'
height='300px'
/>

## Relative Position {#relative-position}

Um die anfängliche Position des Trennbalkens in der `Splitter`-Komponente festzulegen, verwenden Sie `setPositionRelative`. Diese Methode nimmt einen numerischen Wert von `0` bis `100` an, der den Prozentsatz des verfügbaren Raums im `Splitter` darstellt, und zeigt den Trennbalken an dem angegebenen Prozentsatz der Gesamtbreite an:

<ComponentDemo 
path='/webforj/splitterposition?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/splitter/SplitterPositionView.java'
height='300px'
/>

## Verschachtelung {#nesting}

Die Verschachtelung von Splittern ermöglicht das Erstellen komplexer Layouts mit verschiedenen anpassbaren Paneelen. Sie ermöglicht die Schaffung anspruchsvoller Benutzeroberflächen mit granularer Kontrolle über die Anordnung und Größenänderung des Inhalts.

Um Splitter-Komponenten zu verschachteln, instanziieren Sie neue `Splitter`-Instanzen und fügen Sie sie als Kinder zu bestehenden `Splitter`-Komponenten hinzu. Diese hierarchische Struktur ermöglicht die Erstellung von mehrstufigen Layouts mit flexiblen Größenänderungsmöglichkeiten. Das folgende Programm zeigt dies:

<ComponentDemo 
path='/webforj/splitternested?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/splitter/SplitterNestedView.java'
height='300px'
/>

## Auto-Speichern {#auto-save}

Die `Splitter`-Komponente umfasst eine AutoSave-Option, die den Zustand der Paneelgrößen im lokalen Speicher speichert, um die Abmessungen zwischen den Neuladungen konsistent zu halten.

Wenn Sie die Auto-Save-Konfiguration festlegen, speichert die `Splitter`-Komponente automatisch den Zustand der Paneelgrößen im lokalen Speicher des Webbrowsers. Dies stellt sicher, dass die Größen, die Benutzer für Paneele wählen, über seitliche Neuladungen oder Browsersitzungen hinweg bestehen bleiben, was die Notwendigkeit manueller Anpassungen verringert.

### Zustand reinigen {#cleaning-the-state}

Um die `Splitter`-Komponente programmgesteuert auf die Standardeinstellungen und -größen zurückzusetzen, rufen Sie die Methode `cleanState()` auf, um alle gespeicherten Zustandsdaten, die mit der `Splitter`-Komponente im lokalen Speicher des Webbrowsers verbunden sind, zu entfernen.

<ComponentDemo 
path='/webforj/splitterautosave?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/splitter/SplitterAutoSaveView.java'
height='400px'
/>

In der vorangegangenen Demo aktiviert jede Splitter-Instanz die AutoSave-Funktion, indem sie die Methode `setAutosave` aufruft. Dies stellt sicher, dass die Paneelgrößen automatisch im lokalen Speicher gespeichert werden. Wenn der Browser also neu geladen wird, bleiben die Größen dieser Splitter gleich.

Durch Klicken auf die Schaltfläche "Zustand löschen" wird die Methode `cleanState()` aufgerufen und das Browserfenster wird aktualisiert, um die ursprünglichen Abmessungen anzuzeigen.

## Styling {#styling}

<TableBuilder name="Splitter" />

## Beste Praktiken {#best-practices}

Um ein optimales Benutzererlebnis bei der Verwendung der `Splitter`-Komponente zu gewährleisten, sollten Sie die folgenden bewährten Praktiken berücksichtigen:

- **Anpassen basierend auf Inhalt**: Bei der Entscheidung über die Ausrichtung und die anfänglichen Größen der Paneele sollten Sie die Priorität des Inhalts berücksichtigen. Beispielsweise sollte in einem Layout mit einer Navigationsleiste und einem Hauptinhaltsbereich die Seitenleiste in der Regel schmaler bleiben, mit einer festgelegten minimalen Größe für eine klare Navigation.

- **Strategische Verschachtelung**: Das Verschachteln von Splittern kann vielseitige Layouts schaffen, kann jedoch die Benutzeroberfläche komplizierter gestalten und die Leistung beeinträchtigen. Planen Sie Ihre verschachtelten Layouts, um sicherzustellen, dass sie intuitiv sind und das Benutzererlebnis verbessern.

- **Benutzereinstellungen merken**: Verwenden Sie die AutoSave-Funktion, um Benutzereinstellungen über Sitzungen hinweg zu speichern, und verbessern Sie das Benutzererlebnis. Bieten Sie eine Option an, um Benutzern zu ermöglichen, auf die Standardeinstellungen zurückzusetzen.
