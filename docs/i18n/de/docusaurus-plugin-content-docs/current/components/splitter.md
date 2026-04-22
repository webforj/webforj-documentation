---
title: Splitter
sidebar_position: 115
_i18n_hash: 9eb7b2aa3890f16f8fe8a2d4c303b227
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-splitter" />
<DocChip chip='since' label='24.00' />
<JavadocLink type="splitter" location="com/webforj/component/layout/splitter/Splitter" top='true'/>

Die `Splitter`-Komponente, die entwickelt wurde, um Inhalte innerhalb Ihrer App zu teilen und zu verändern, kapselt zwei veränderbare Komponenten: die Master- und die Detailkomponenten. Ein Trennstück trennt diese Komponenten und ermöglicht es den Benutzern, die Größe jeder Komponente dynamisch an ihre Vorlieben anzupassen.

<!-- INTRO_END -->

## Erstellung eines Splitters {#creating-a-splitter}

Erstellen Sie einen `Splitter`, indem Sie zwei Komponenten an seinen Konstruktor übergeben. Die erste wird das Master-Panel und die zweite das Detail-Panel.

<ComponentDemo 
path='/webforj/splitterbasic?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/splitter/SplitterBasicView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/components/SplitterBox.java',]}
cssURL='/css/splitter-box.css'
height='300px'
/>

## Mindest- und Höchstgröße {#min-and-max-size}

Die `Splitter`-Komponente bietet Methoden, um Mindest- und Höchstgrößen für ihre Paneele festzulegen, sodass Sie das Größenverhalten der Komponenten innerhalb des `Splitter` steuern können. Wenn Benutzer versuchen, die Paneele über die angegebenen Mindest- oder Höchstgrößen hinaus zu vergrößern, setzt die Splitter-Komponente diese Einschränkungen durch und stellt sicher, dass die Paneele innerhalb der festgelegten Grenzen bleiben.

### Größen festlegen {#setting-sizes}

Die Methode `setMasterMinSize(String masterMinSize)` legt die Mindestgröße für das Master-Panel des Splitters fest. Ebenso legt die Methode `setMasterMaxSize(String masterMaxSize)` die Höchstgröße für das Master-Panel fest.

Sie können Größen mit beliebigen gültigen CSS-Einheiten angeben, wie unten gezeigt:

<ComponentDemo 
path='/webforj/splitterminmax?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/splitter/SplitterMinMaxView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/components/SplitterBox.java',]}
cssURL='/css/splitter-box.css'
height='300px'
/>

## Orientierung {#orientation}

Sie können die Orientierung in der `Splitter`-Komponente konfigurieren, wodurch Sie Layouts erstellen können, die auf spezifische Designanforderungen zugeschnitten sind. Durch die Angabe der Orientierung ordnet die Komponente die Paneele horizontal oder vertikal an und bietet Flexibilität im Layout-Design.

Um die Orientierung zu konfigurieren, verwenden Sie das unterstützte Enum für Orientierungen, um anzugeben, ob der `Splitter` horizontal oder vertikal gerendert werden soll:

<ComponentDemo 
path='/webforj/splitterorientation?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/splitter/SplitterOrientationView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/components/SplitterBox.java',]}
cssURL='/css/splitter-box.css'
height='300px'
/>

## Relative Position {#relative-position}

Um die anfängliche Position des Trennbalkens in der `Splitter`-Komponente festzulegen, verwenden Sie `setPositionRelative`. Diese Methode nimmt einen numerischen Wert von `0` bis `100` an, der den Prozentsatz des gegebenen Raums im `Splitter` darstellt, und zeigt den Trennbalken bei dem angegebenen Prozentsatz der Gesamtbreite an:

<ComponentDemo 
path='/webforj/splitterposition?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/splitter/SplitterPositionView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/components/SplitterBox.java',]}
cssURL='/css/splitter-box.css'
height='300px'
/>

## Verschachtelung {#nesting}

Die Verschachtelung von Splittern ermöglicht es Ihnen, komplexe Layouts mit verschiedenen Ebenen von veränderbaren Paneelen zu erstellen. Sie ermöglicht die Erstellung anspruchsvoller Benutzeroberflächen mit granularer Kontrolle über die Anordnung und Größenänderung der Inhalte.

Um Splitter-Komponenten zu verschachteln, instanziieren Sie neue `Splitter`-Instanzen und fügen Sie sie als Kinder in bestehende `Splitter`-Komponenten ein. Diese hierarchische Struktur ermöglicht die Erstellung von mehrstufigen Layouts mit flexiblen Größenänderungsmöglichkeiten. Das folgende Programm demonstriert dies:

<ComponentDemo 
path='/webforj/splitternested?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/splitter/SplitterNestedView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/components/SplitterBox.java',]}
cssURL='/css/splitter-box.css'
height='300px'
/>

## Automatisches Speichern {#auto-save}

Die `Splitter`-Komponente umfasst eine AutoSave-Option, die den Status der Paneelgrößen im lokalen Speicher speichert, um die Dimensionen zwischen Neuladen zu konsistent zu halten.

Wenn Sie die Auto-Save-Konfiguration festlegen, speichert die `Splitter`-Komponente automatisch den Status der Paneelgrößen im lokalen Speicher des Webbrowsers. Dies stellt sicher, dass die Größen, die Benutzer für Paneele wählen, über Seitenneuladen oder Browser-Sitzungen hinweg bestehen bleiben, was die Notwendigkeit manueller Anpassungen verringert.

### Zustand bereinigen {#cleaning-the-state}

Um das `Splitter`-Programmatisch auf die Standardeinstellungen und -dimensionen zurückzusetzen, rufen Sie die Methode `cleanState()` auf, um alle gespeicherten Zustandsdaten, die sich auf die `Splitter`-Komponente beziehen, aus dem lokalen Speicher des Webbrowsers zu entfernen.

<ComponentDemo 
path='/webforj/splitterautosave?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/splitter/SplitterAutoSaveView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/components/SplitterBox.java',]}
cssURL='/css/splitter-box.css'
height='400px'
/>

Im vorherigen Demonstrationsbeispiel aktiviert jede Splitter-Instanz die AutoSave-Funktion, indem sie die Methode `setAutosave` aufruft. Dies stellt sicher, dass die Paneelgrößen automatisch im lokalen Speicher gespeichert werden. Somit bleiben die Größen dieser Splitter beim Neuladen des Browsers gleich.

Durch Klicken auf die Schaltfläche "Zustand zurücksetzen" wird die Methode `cleanState()` aufgerufen und das Browserfenster aktualisiert, um die ursprünglichen Dimensionen anzuzeigen.

## Gestaltung {#styling}

<TableBuilder name="Splitter" />

## Beste Vorgehensweisen {#best-practices}

Um ein optimales Benutzererlebnis bei der Verwendung der `Splitter`-Komponente zu gewährleisten, sollten Sie folgende beste Vorgehensweisen in Betracht ziehen:

- **Anpassung basierend auf Inhalt**: Berücksichtigen Sie bei der Entscheidung über die Orientierung und die anfänglichen Größen der Paneele die Priorität des Inhalts. Beispielsweise sollte in einem Layout mit einer Navigationsleiste und einem Hauptinhaltbereich die Sidebar in der Regel schmaler bleiben mit einer festgelegten Mindestgröße für die klare Navigation.

- **Strategische Verschachtelung**: Verschachteltes Splitter kann vielseitige Layouts schaffen, kann jedoch die Benutzeroberfläche komplizieren und die Leistung beeinträchtigen. Planen Sie Ihre verschachtelten Layouts so, dass sie intuitiv sind und die Benutzererfahrung verbessern.

- **Benutzervorlieben beachten**: Verwenden Sie die AutoSave-Funktion, um die Anpassungen der Benutzer über Sitzungen hinweg zu speichern und die Benutzererfahrung zu verbessern. Bieten Sie eine Option an, mit der Benutzer auf die Standardeinstellungen zurücksetzen können.
