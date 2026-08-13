---
title: Splitter
sidebar_position: 115
description: >-
  Divide a layout into resizable master and detail panels with the Splitter
  component, with min and max sizes and orientation control.
_i18n_hash: 0683e5c7589bbf3fa42b8ea137c4f809
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-splitter" />
<DocChip chip='since' label='24.00' />
<JavadocLink type="splitter" location="com/webforj/component/layout/splitter/Splitter" top='true'/>

Die `Splitter`-Komponente, die dazu konzipiert ist, Inhalte innerhalb Ihrer App zu teilen und zu skalieren, umfasst zwei skalierbare Komponenten: die Master- und die Detailkomponenten. Ein Trennstrich trennt diese Komponenten und ermöglicht es den Benutzern, die Größe jeder Komponente dynamisch nach ihren Wünschen anzupassen.

<!-- INTRO_END -->

## Erstellung eines Splitters {#creating-a-splitter}

Erstellen Sie einen `Splitter`, indem Sie zwei Komponenten an seinen Konstruktor übergeben. Die erste wird das Master-Panel und die zweite das Detail-Panel.

<ComponentDemo
path='/webforj/splitterbasic'
files={[
  'src/main/java/com/webforj/samples/views/splitter/SplitterBasicView.java',
  'src/main/java/com/webforj/samples/components/SplitterBox.java',
  'src/main/frontend/css/splitter-box.css',
]}
height='300px'
/>

## Minimale und maximale Größe {#min-and-max-size}

Die `Splitter`-Komponente bietet Methoden zum Festlegen von Mindest- und Höchstgrößen für ihre Paneele, wodurch Sie das Skalierungsverhalten der Komponenten innerhalb des `Splitters` steuern können. Wenn Benutzer versuchen, Paneele über die festgelegten Mindest- oder Höchstgrößen hinaus zu skalieren, erzwingt die Splitter-Komponente diese Einschränkungen, sodass die Paneele innerhalb der definierten Grenzen bleiben.

### Größen festlegen {#setting-sizes}

Die Methode `setMasterMinSize(String masterMinSize)` gibt die Mindestgröße für das Master-Panel des Splitters an. Ebenso gibt die Methode `setMasterMaxSize(String masterMaxSize)` die Höchstgröße für das Master-Panel an.

Sie können Größen mit beliebigen gültigen CSS-Einheiten angeben, wie unten gezeigt:

<ComponentDemo
path='/webforj/splitterminmax'
files={[
  'src/main/java/com/webforj/samples/views/splitter/SplitterMinMaxView.java',
  'src/main/java/com/webforj/samples/components/SplitterBox.java',
  'src/main/frontend/css/splitter-box.css',
]}
height='300px'
/>

## Ausrichtung {#orientation}

Sie können die Ausrichtung in der `Splitter`-Komponente konfigurieren, um Layouts zu erstellen, die auf spezifische Designanforderungen zugeschnitten sind. Durch Festlegen der Ausrichtung arranges die Komponente die Paneele horizontal oder vertikal, was Flexibilität im Layoutdesign bietet.

Um die Ausrichtung zu konfigurieren, verwenden Sie das unterstützte Ausrichtungs-Enum, um anzugeben, ob der `Splitter` horizontal oder vertikal gerendert werden soll:

<ComponentDemo
path='/webforj/splitterorientation'
files={[
  'src/main/java/com/webforj/samples/views/splitter/SplitterOrientationView.java',
  'src/main/java/com/webforj/samples/components/SplitterBox.java',
  'src/main/frontend/css/splitter-box.css',
]}
height='300px'
/>

## Relative Position {#relative-position}

Um die anfängliche Position des Trennstrichs in der `Splitter`-Komponente festzulegen, verwenden Sie `setPositionRelative`. Diese Methode nimmt einen numerischen Wert von `0` bis `100`, der den Prozentsatz des gegebenen Raums im `Splitter` darstellt, und zeigt den Trennstrich auf dem gegebenen Prozentsatz der Gesamtbreite an:

<ComponentDemo
path='/webforj/splitterposition'
files={[
  'src/main/java/com/webforj/samples/views/splitter/SplitterPositionView.java',
  'src/main/java/com/webforj/samples/components/SplitterBox.java',
  'src/main/frontend/css/splitter-box.css',
]}
height='300px'
/>

## Verschachtelung {#nesting}

Die Verschachtelung von Splittern ermöglicht es Ihnen, komplexe Layouts mit mehreren skalierbaren Paneelen zu erstellen. Sie ermöglicht die Erstellung anspruchsvoller Benutzeroberflächen mit granularer Steuerung über das Arrangement und die Skalierung von Inhalten.

Um Splitter-Komponenten zu verschachteln, instanziieren Sie neue `Splitter`-Instanzen und fügen Sie sie als Kinder zu bestehenden `Splitter`-Komponenten hinzu. Diese hierarchische Struktur ermöglicht die Erstellung von mehrstufigen Layouts mit flexiblen Skalierungsmöglichkeiten. Das folgende Programm veranschaulicht dies:

<ComponentDemo
path='/webforj/splitternested'
files={[
  'src/main/java/com/webforj/samples/views/splitter/SplitterNestedView.java',
  'src/main/java/com/webforj/samples/components/SplitterBox.java',
  'src/main/frontend/css/splitter-box.css',
]}
height='300px'
/>

## Automatisches Speichern {#auto-save}

Die `Splitter`-Komponente umfasst eine AutoSave-Option, die den Zustand der Paneelgrößen im lokalen Speicher speichert, um die Dimensionen zwischen Neuladungen konsistent zu halten.

Wenn Sie die Auto-Save-Konfiguration festlegen, speichert die `Splitter`-Komponente automatisch den Zustand der Paneelgrößen im lokalen Speicher des Webbrowsers. Dies stellt sicher, dass die Größen, die Benutzer für Paneele auswählen, über Seitenneuladungen oder Browsersitzungen hinweg bestehen bleiben, wodurch der Bedarf an manuellen Anpassungen verringert wird.

### Zustand bereinigen {#cleaning-the-state}

Um den `Splitter` programmgesteuert auf die Standardeinstellungen und -dimensionen zurückzusetzen, rufen Sie die Methode `cleanState()` auf, um alle gespeicherten Zustandsdaten, die sich auf die `Splitter`-Komponente beziehen, aus dem lokalen Speicher des Webbrowsers zu entfernen.

<ComponentDemo
path='/webforj/splitterautosave'
files={[
  'src/main/java/com/webforj/samples/views/splitter/SplitterAutoSaveView.java',
  'src/main/java/com/webforj/samples/components/SplitterBox.java',
  'src/main/frontend/css/splitter-box.css',
]}
height='400px'
/>

Im vorhergehenden Demo aktiviert jede Splitter-Instanz die AutoSave-Funktion, indem sie die Methode `setAutosave` aufruft. Dies stellt sicher, dass die Paneelgrößen automatisch im lokalen Speicher gespeichert werden. Wenn der Browser neu geladen wird, bleiben die Größen dieser Splitter gleich.

Durch Klicken auf die Schaltfläche "Zustand zurücksetzen" wird die Methode `cleanState()` aufgerufen und das Browserfenster aktualisiert, um die ursprünglichen Dimensionen anzuzeigen.

## Stil {#styling}

<TableBuilder name="Splitter" />

## Best Practices {#best-practices}

Um ein optimales Benutzererlebnis beim Verwenden der `Splitter`-Komponente sicherzustellen, berücksichtigen Sie die folgenden Best Practices:

- **Anpassung basierend auf Inhalt**: Berücksichtigen Sie bei der Entscheidung über die Ausrichtung und die anfänglichen Größen der Paneele die Priorität des Inhalts. In einem Layout mit einer Navigationsleiste und einem Hauptinhaltbereich sollte die Navigationsleiste typischerweise schmaler bleiben und eine feste Mindestgröße aufweisen, um eine klare Navigation zu gewährleisten.

- **Strategische Verschachtelung**: Die Verschachtelung von Splittern kann vielseitige Layouts schaffen, aber die Benutzeroberfläche komplizieren und die Leistung beeinträchtigen. Planen Sie Ihre verschachtelten Layouts so, dass sie intuitiv sind und die Benutzererfahrung verbessern.

- **Benutzerpräferenzen im Gedächtnis behalten**: Verwenden Sie die AutoSave-Funktion, um Benutzereinstellungen über Sitzungen hinweg zu speichern, was die Benutzererfahrung verbessert. Bieten Sie eine Option an, die es den Benutzern ermöglicht, auf die Standardeinstellungen zurückzusetzen.
