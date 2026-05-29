---
title: Splitter
sidebar_position: 115
_i18n_hash: 340bcd9862027e6bfb967c0e6a9b5ec1
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-splitter" />
<DocChip chip='since' label='24.00' />
<JavadocLink type="splitter" location="com/webforj/component/layout/splitter/Splitter" top='true'/>

Die `Splitter`-Komponente, die zum Teilen und Ändern der Größe von Inhalten innerhalb Ihrer App entwickelt wurde, kapselt zwei größenveränderbare Komponenten: die Master- und die Detailkomponenten. Ein Trennschalter trennt diese Komponenten, sodass Benutzer die Größe jeder Komponente nach ihren Wünschen dynamisch anpassen können.

<!-- INTRO_END -->

## Erstellung eines Splitters {#creating-a-splitter}

Erstellen Sie einen `Splitter`, indem Sie zwei Komponenten an seinen Konstruktor übergeben. Die erste wird das Master-Panel und die zweite das Detail-Panel.

<ComponentDemo
path='/webforj/splitterbasic'
files={[
  'src/main/java/com/webforj/samples/views/splitter/SplitterBasicView.java',
  'src/main/java/com/webforj/samples/components/SplitterBox.java',
  'src/main/resources/static/css/splitter-box.css',
]}
height='300px'
/>

## Minimale und maximale Größe {#min-and-max-size}

Die `Splitter`-Komponente bietet Methoden zum Festlegen der minimalen und maximalen Größen für ihre Panels, sodass Sie das Größenänderungsverhalten der Komponenten innerhalb des `Splitters` steuern können. Wenn Benutzer versuchen, Panels über die angegebenen Minimal- oder Maximalgrößen hinaus zu vergrößern, erzwingt die Splitter-Komponente diese Einschränkungen, um sicherzustellen, dass die Panels innerhalb der definierten Grenzen bleiben.

### Größen festlegen {#setting-sizes}

Die Methode `setMasterMinSize(String masterMinSize)` gibt die Mindestgröße für das Master-Panel des Splitters an. Ebenso gibt die Methode `setMasterMaxSize(String masterMaxSize)` die Maximalgröße für das Master-Panel an.

Sie können Größen mit beliebigen gültigen CSS-Einheiten angeben, wie unten gezeigt:

<ComponentDemo
path='/webforj/splitterminmax'
files={[
  'src/main/java/com/webforj/samples/views/splitter/SplitterMinMaxView.java',
  'src/main/java/com/webforj/samples/components/SplitterBox.java',
  'src/main/resources/static/css/splitter-box.css',
]}
height='300px'
/>

## Ausrichtung {#orientation}

Sie können die Ausrichtung in der `Splitter`-Komponente konfigurieren, sodass Sie Layouts erstellen können, die auf bestimmte Designanforderungen zugeschnitten sind. Durch die Angabe der Ausrichtung arrangiert die Komponente Panels horizontal oder vertikal und bietet Vielseitigkeit im Layout-Design.

Um die Ausrichtung zu konfigurieren, verwenden Sie die unterstützten Orientierungs-Enums, um anzugeben, ob der `Splitter` horizontal oder vertikal gerendert werden soll:

<ComponentDemo
path='/webforj/splitterorientation'
files={[
  'src/main/java/com/webforj/samples/views/splitter/SplitterOrientationView.java',
  'src/main/java/com/webforj/samples/components/SplitterBox.java',
  'src/main/resources/static/css/splitter-box.css',
]}
height='300px'
/>

## Relative Position {#relative-position}

Um die Anfangsposition der Trennleiste in der `Splitter`-Komponente festzulegen, verwenden Sie `setPositionRelative`. Diese Methode nimmt einen numerischen Wert von `0` bis `100`, der den Prozentsatz des gegebenen Raums im `Splitter` darstellt, und zeigt den Trenner bei dem angegebenen Prozentsatz der Gesamtbreite an:

<ComponentDemo
path='/webforj/splitterposition'
files={[
  'src/main/java/com/webforj/samples/views/splitter/SplitterPositionView.java',
  'src/main/java/com/webforj/samples/components/SplitterBox.java',
  'src/main/resources/static/css/splitter-box.css',
]}
height='300px'
/>

## Verschachtelung {#nesting}

Die Verschachtelung von Splittern ermöglicht es Ihnen, komplexe Layouts mit verschiedenen größenveränderbaren Panels zu erstellen. Sie ermöglicht die Erstellung von anspruchsvollen Benutzeroberflächen mit granularer Kontrolle über die Anordnung und Größenänderung von Inhalten.

Um Splitter-Komponenten zu verschachteln, instanziieren Sie neue `Splitter`-Instanzen und fügen Sie sie als Kinder zu bestehenden `Splitter`-Komponenten hinzu. Diese hierarchische Struktur ermöglicht die Erstellung von mehrstufigen Layouts mit flexiblen Größenänderungsmöglichkeiten. Das folgende Programm demonstriert dies:

<ComponentDemo
path='/webforj/splitternested'
files={[
  'src/main/java/com/webforj/samples/views/splitter/SplitterNestedView.java',
  'src/main/java/com/webforj/samples/components/SplitterBox.java',
  'src/main/resources/static/css/splitter-box.css',
]}
height='300px'
/>

## Automatisches Speichern {#auto-save}

Die `Splitter`-Komponente umfasst eine AutoSave-Option, die den Zustand der Panelgrößen im lokalen Speicher speichert, um die Abmessungen zwischen Neuladungen konsistent zu halten.

Wenn Sie die Auto-Save-Konfiguration festlegen, speichert die `Splitter`-Komponente automatisch den Zustand der Panelgrößen im lokalen Speicher des Webbrowsers. Dadurch wird sichergestellt, dass die Größen, die Benutzer für Panels wählen, über Seiten-Neuladungen oder Browsersitzungen hinweg bestehen bleiben, wodurch der Bedarf an manuellen Anpassungen verringert wird.

### Zustand zurücksetzen {#cleaning-the-state}

Um den `Splitter` programmgesteuert auf die Standardeinstellungen und -abmessungen zurückzusetzen, rufen Sie die Methode `cleanState()` auf, um alle gespeicherten Zustandsdaten, die mit der `Splitter`-Komponente im lokalen Speicher des Webbrowsers verbunden sind, zu entfernen.

<ComponentDemo
path='/webforj/splitterautosave'
files={[
  'src/main/java/com/webforj/samples/views/splitter/SplitterAutoSaveView.java',
  'src/main/java/com/webforj/samples/components/SplitterBox.java',
  'src/main/resources/static/css/splitter-box.css',
]}
height='400px'
/>

Im vorherigen Demo wird jede Splitter-Instanz durch Aufruf der Methode `setAutosave` die AutoSave-Funktion aktivieren. Dadurch wird sichergestellt, dass die Panelgrößen automatisch im lokalen Speicher gespeichert werden. Wenn Sie also den Browser neu laden, bleiben die Größen dieser Splitter gleich.

Durch Klicken auf die Schaltfläche "Zustand zurücksetzen" wird die Methode `cleanState()` aufgerufen und das Browserfenster aktualisiert, um die ursprünglichen Abmessungen anzuzeigen.

## Styling {#styling}

<TableBuilder name="Splitter" />

## Beste Praktiken {#best-practices}

Um ein optimales Benutzererlebnis bei der Verwendung der `Splitter`-Komponente zu gewährleisten, sollten Sie die folgenden bewährten Praktiken berücksichtigen:

- **Anpassung basierend auf Inhalten**: Berücksichtigen Sie bei der Entscheidung über die Ausrichtung und die Anfangsgrößen der Panels die Priorität des Inhalts. Beispielsweise sollte in einem Layout mit einer Navigationsleiste und einem Hauptinhaltbereich die Leiste in der Regel schmaler bleiben mit einer festgelegten Mindestgröße für eine klare Navigation.

- **Strategische Verschachtelung**: Das Verschachteln von Splittern kann vielseitige Layouts schaffen, kann jedoch die Benutzeroberfläche komplizieren und die Leistung beeinträchtigen. Planen Sie Ihre verschachtelten Layouts sorgfältig, um sicherzustellen, dass sie intuitiv sind und das Benutzererlebnis verbessern.

- **Benutzereinstellungen merken**: Verwenden Sie die AutoSave-Funktion, um Benutzereinstellungen über Sitzungen hinweg zu speichern und das Benutzererlebnis zu verbessern. Bieten Sie eine Option, damit Benutzer auf die Standardeinstellungen zurückgesetzt werden können.
