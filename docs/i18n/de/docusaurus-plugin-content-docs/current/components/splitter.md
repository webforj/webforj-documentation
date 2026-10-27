---
title: Splitter
sidebar_position: 115
description: >-
  Divide a layout into resizable master and detail panels with the Splitter
  component, with min and max sizes and orientation control.
_i18n_hash: c700d01058105b5b752ecfa560224fb5
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-splitter" />
<DocChip chip='since' label='24.00' />
<JavadocLink type="splitter" location="com/webforj/component/layout/splitter/Splitter" top='true'/>

Die `Splitter`-Komponente, die entwickelt wurde, um Inhalte in Ihrer App zu teilen und zu skalieren, umfasst zwei anpassbare Komponenten: die Haupt- und die Detailkomponenten. Ein Divider trennt diese Komponenten und ermöglicht es den Nutzern, die Größe jeder Komponente entsprechend ihren Vorlieben dynamisch anzupassen.

<!-- INTRO_END -->

## Erstellung eines Splitters {#creating-a-splitter}

Erstellen Sie einen `Splitter`, indem Sie zwei Komponenten an seinen Konstruktor übergeben. Die erste wird zum Hauptpanel und die zweite zum Detailpanel.

<ComponentDemo
path='/webforj/splitterbasic'
files={[
  'src/main/java/com/webforj/samples/views/splitter/SplitterBasicView.java',
  'src/main/java/com/webforj/samples/components/SplitterBox.java',
  'src/main/frontend/css/splitter/splitter-box.css',
]}
height='300px'
/>

## Minimale und maximale Größe {#min-and-max-size}

Die `Splitter`-Komponente bietet Methoden, um minimale und maximale Größen für ihre Panels festzulegen, sodass Sie das Größenänderungsverhalten der Komponenten innerhalb des `Splitters` steuern können. Wenn Benutzer versuchen, Panels über die angegebenen minimalen oder maximalen Größen hinaus zu ändern, erzwingt die Splitter-Komponente diese Einschränkungen und stellt sicher, dass die Panels innerhalb der definierten Grenzen bleiben.

### Größen festlegen {#setting-sizes}

Die Methode `setMasterMinSize(String masterMinSize)` gibt die Mindestgröße für das Hauptpanel des Splitters an. Ebenso gibt die Methode `setMasterMaxSize(String masterMaxSize)` die maximale Größe für das Hauptpanel an.

Sie können Größen mit beliebigen gültigen CSS-Einheiten angeben, wie unten gezeigt:

<ComponentDemo
path='/webforj/splitterminmax'
files={[
  'src/main/java/com/webforj/samples/views/splitter/SplitterMinMaxView.java',
  'src/main/java/com/webforj/samples/components/SplitterBox.java',
  'src/main/frontend/css/splitter/splitter-box.css',
]}
height='300px'
/>

## Ausrichtung {#orientation}

Sie können die Ausrichtung in der `Splitter`-Komponente konfigurieren, um Layouts zu erstellen, die auf spezifische Designanforderungen zugeschnitten sind. Durch die Angabe der Ausrichtung arrangiert die Komponente die Panels horizontal oder vertikal und bietet Vielseitigkeit im Layout-Design.

Um die Ausrichtung zu konfigurieren, verwenden Sie die unterstützten Ausrichtungen Enum, um anzugeben, ob der `Splitter` horizontal oder vertikal gerendert werden soll:

<ComponentDemo
path='/webforj/splitterorientation'
files={[
  'src/main/java/com/webforj/samples/views/splitter/SplitterOrientationView.java',
  'src/main/java/com/webforj/samples/components/SplitterBox.java',
  'src/main/frontend/css/splitter/splitter-box.css',
]}
height='300px'
/>

## Relative Position {#relative-position}

Um die Anfangsposition der Trennleiste in der `Splitter`-Komponente festzulegen, verwenden Sie `setPositionRelative`. Diese Methode akzeptiert einen numerischen Wert von `0` bis `100`, der den Prozentsatz des gegebenen Raums im `Splitter` darstellt, und zeigt den Divider am angegebenen Prozentsatz der Gesamtlänge an:

<ComponentDemo
path='/webforj/splitterposition'
files={[
  'src/main/java/com/webforj/samples/views/splitter/SplitterPositionView.java',
  'src/main/java/com/webforj/samples/components/SplitterBox.java',
  'src/main/frontend/css/splitter/splitter-box.css',
]}
height='300px'
/>

## Verschachtelung {#nesting}

Die Verschachtelung von Splittern ermöglicht es Ihnen, komplexe Layouts mit Ebenen von anpassbaren Panels zu erstellen. Sie ermöglicht die Erstellung anspruchsvoller Benutzeroberflächen mit granularer Kontrolle über die Anordnung und Größenänderung von Inhalten.

Um Splitter-Komponenten zu verschachteln, instanziieren Sie neue `Splitter`-Instanzen und fügen Sie sie als Kinder zu bestehenden `Splitter`-Komponenten hinzu. Diese hierarchische Struktur ermöglicht die Erstellung von mehrstufigen Layouts mit flexiblen Möglichkeiten zur Größenänderung. Das folgende Programm demonstriert dies:

<ComponentDemo
path='/webforj/splitternested'
files={[
  'src/main/java/com/webforj/samples/views/splitter/SplitterNestedView.java',
  'src/main/java/com/webforj/samples/components/SplitterBox.java',
  'src/main/frontend/css/splitter/splitter-box.css',
]}
height='300px'
/>

## Automatisches Speichern {#auto-save}

Die `Splitter`-Komponente enthält eine AutoSave-Option, die den Zustand der Panelgrößen im lokalen Speicher sichert, um die Abmessungen zwischen Neuladungen konsistent zu halten.

Wenn Sie die Auto-Save-Konfiguration festlegen, speichert die `Splitter`-Komponente automatisch den Zustand der Panelgrößen im lokalen Speicher des Webbrowsers. Dadurch wird sichergestellt, dass die von den Benutzern gewählten Größen für Panels über Seitenneuladungen oder Browsersitzungen hinweg bestehen bleiben, was den Bedarf an manuellen Anpassungen reduziert.

### Zustand bereinigen {#cleaning-the-state}

Um den `Splitter` programmgesteuert auf die Standardeinstellungen und -abmessungen zurückzusetzen, rufen Sie die Methode `cleanState()` auf, um alle gespeicherten Zustandsdaten, die sich auf die `Splitter`-Komponente beziehen, aus dem lokalen Speicher des Webbrowsers zu entfernen.

<ComponentDemo
path='/webforj/splitterautosave'
files={[
  'src/main/java/com/webforj/samples/views/splitter/SplitterAutoSaveView.java',
  'src/main/java/com/webforj/samples/components/SplitterBox.java',
  'src/main/frontend/css/splitter/splitter-box.css',
]}
height='400px'
/>

Im vorhergehenden Demo aktiviert jede Splitter-Instanz die AutoSave-Funktion, indem sie die Methode `setAutosave` aufruft. Dadurch werden die Panelgrößen automatisch im lokalen Speicher gespeichert. Somit bleiben die Größen dieser Splitter beim Neuladen des Browsers gleich.

Ein Klick auf die Schaltfläche "Zustand löschen" ruft die Methode `cleanState()` auf und aktualisiert das Browserfenster, um die ursprünglichen Abmessungen anzuzeigen.

## Stil {#styling}

<TableBuilder name="Splitter" />

## Beste Praktiken {#best-practices}

Um ein optimales Benutzererlebnis bei der Verwendung der `Splitter`-Komponente sicherzustellen, beachten Sie die folgenden besten Praktiken:

- **Anpassung an den Inhalt**: Berücksichtigen Sie bei der Entscheidung über die Ausrichtung und die Anfangsgrößen der Panels die Priorität des Inhalts. Beispielsweise sollte in einem Layout mit einer Navigationsleiste und einem Hauptinhaltsbereich die Leiste in der Regel kleiner mit einer festgelegten minimalen Größe bleiben, um eine klare Navigation zu gewährleisten.

- **Strategische Verschachtelung**: Die Verschachtelung von Splittern kann vielseitige Layouts erstellen, kann jedoch die Benutzeroberfläche komplizieren und die Leistung beeinträchtigen. Planen Sie Ihre verschachtelten Layouts so, dass sie intuitiv sind und das Benutzererlebnis verbessern.

- **Benutzervorlieben beachten**: Verwenden Sie die AutoSave-Funktion, um Benutzereinstellungen über Sitzungen hinweg zu speichern und das Benutzererlebnis zu verbessern. Bieten Sie eine Option an, die es den Benutzern ermöglicht, zu den Standardeinstellungen zurückzukehren.
