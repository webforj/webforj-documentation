---
title: Dialog
sidebar_position: 30
_i18n_hash: e0d440fddf7ad6be7a78958ae1ddde1a
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-dialog" />
<DocChip chip='since' label='23.06' />
<JavadocLink type="dialog" location="com/webforj/component/dialog/Dialog" top='true'/>

Die webforJ Dialog-Komponente wurde entwickelt, um Entwicklern zu ermöglichen, schnell und einfach einen Dialog in ihrer Anwendung anzuzeigen, zum Beispiel als Anmeldefenster oder Informationskasten.

Die Komponente besteht aus drei Abschnitten, von denen jeder ein `Panel`-Komponente ist: der **Kopfzeile**, dem **Inhalt** und der **Fußzeile**.

<ComponentDemo 
path='/webforj/dialogsections?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/dialog/DialogSectionsView.java'
height = '225px'
/>

## Verwendungen {#usages}

1. **Benutzerfeedback und Bestätigung**: `Dialog`-Komponenten werden häufig verwendet, um Feedback zu geben oder eine Bestätigung von Benutzern anzufordern. Sie können verschiedene wichtige Stücke von Feedback einem Benutzer anzeigen, wie zum Beispiel:

  >- Erfolgsmeldungen 
  >- Fehlermeldungen
  >- Bestätigungsübertragungen

2. **Formulareingabe und Bearbeitung**: Sie können Dialoge verwenden, um Benutzereingaben zu sammeln oder ihnen zu ermöglichen, Informationen in einer kontrollierten und fokussierten Weise zu bearbeiten. Zum Beispiel kann ein Dialog erscheinen, um Details des Benutzerprofils zu bearbeiten oder ein mehrstufiges Formular abzuschließen.

3. **Kontextuelle Informationen**: Zusätzliche kontextuelle Informationen oder Tooltips in einem Dialog anzuzeigen, kann Benutzern helfen, komplexe Funktionen oder Daten zu verstehen. Dialoge können tiefere Erklärungen, Diagramme oder Hilfsdokumentationen bereitstellen.

4. **Bild- und Medienvorschauen**: Wenn Benutzer Stücke von Medien ansehen müssen, kann ein `Dialog` verwendet werden, um größere Vorschauen oder Galerien anzuzeigen, beispielsweise beim Interagieren mit:
  >- Bildern
  >- Videos
  >- Anderen Medien

## Hintergrund und Unschärfe {#backdrop-and-blur}

Durch Aktivierung des Hintergrundattributs der webforJ `Dialog`-Komponente wird ein Hintergrund hinter dem `Dialog` angezeigt. Zusätzlich wird, wenn aktiviert, das unscharfe Attribut des Dialogs den Hintergrund des `Dialogs` unscharf machen. Die Modifizierung dieser Einstellungen kann den Benutzern helfen, indem sie Tiefen, visuelle Hierarchie und Kontext bereitstellt, was zu klareren Anweisungen für einen Benutzer führt.

<ComponentDemo 
path='/webforj/dialogbackdropblur?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/dialog/DialogBackdropBlurView.java'
height = '300px'
/>

## Öffnen und Schließen des `Dialogs` {#opening-and-closing-the-dialog}

Nachdem ein neuer `Dialog`-Objekt erstellt wurde, verwenden Sie die `open()`-Methode, um den Dialog anzuzeigen. Dann kann die `Dialog`-Komponente durch eine dieser Aktionen geschlossen werden:
- Nutzung der `close()`-Methode
- Drücken der <kbd>ESC</kbd>-Taste
- Klick außerhalb des `Dialogs`

Entwickler können wählen, welche Interaktionen den `Dialog` schließen, mit `setCancelOnEscKey()` und `setCancelOnOutsideClick()`. Zusätzlich kann die Methode `setClosable()` verhindern oder erlauben, dass sowohl das Drücken der <kbd>ESC</kbd>-Taste als auch das Klicken außerhalb des `Dialogs` die Komponente schließen.

<ComponentDemo 
path='/webforj/dialogclose?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/dialog/DialogCloseView.java'
height = '350px'
/>

## Autofokus {#auto-focus}

Wenn aktiviert, wird der Autofokus automatisch den Fokus auf das erste Element innerhalb des Dialogs legen, das fokussierbar ist. Dies ist nützlich, um die Aufmerksamkeit der Benutzer zu lenken und kann über die Methode `setAutoFocus()` angepasst werden.

<ComponentDemo 
path='/webforj/dialogautofocus?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/dialog/DialogAutoFocusView.java'
height = '350px'
/>

## Verschiebbar {#draggable}

Der `Dialog` hat eine eingebaute Funktionalität, um verschiebbar zu sein, sodass der Benutzer das `Dialog`-Fenster durch Klicken und Ziehen verlegen kann. Die Position des `Dialogs` kann von jedem der Felder innerhalb davon manipuliert werden: der Kopfzeile, dem Inhalt oder der Fußzeile.

### Zum Rand rasten {#snap-to-edge}
Es ist auch möglich, dieses Verhalten so zu kalibrieren, dass es am Rand des Bildschirms rastet, was bedeutet, dass der `Dialog` sich automatisch mit dem Rand des Displays ausrichtet, wenn er von seinem Ziehen-und-Ablegen-Datum losgelassen wird. Das Rasten kann über die Methode `setSnapToEdge()` geändert werden. Die Methode `setSnapThreshold()` nimmt eine Anzahl von Pixeln, die festlegt, wie weit der `Dialog` von den Seiten des Bildschirms entfernt sein sollte, bevor er automatisch an den Rändern rasten kann.

<ComponentDemo 
path='/webforj/dialogdraggable?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/dialog/DialogDraggableView.java'
height = '350px'
/>

## Positionierung {#positioning}

Die Position des Dialogs kann mit den eingebauten Methoden `setPosx()` und `setPosy()` manipuliert werden. Diese Methoden nehmen ein String-Argument, das eine beliebige anwendbare CSS-Längeneinheit darstellen kann, wie Pixel oder Sichthöhe/-breite. Eine Liste dieser Maße [kann unter diesem Link gefunden werden](https://developer.mozilla.org/en-US/docs/Learn/CSS/Building_blocks/Values_and_units#numbers_lengths_and_percentages).

<ComponentDemo 
path='/webforj/dialogpositioning?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/dialog/DialogPositioningView.java'
height = '350px'
/>

### Vertikale Ausrichtung {#vertical-alignment}

Neben der manuellen Zuweisung der X- und Y-Position eines Dialogs ist es möglich, die eingebaute Enum-Klasse des Dialogs zu verwenden, um den `Dialog` auszurichten. Es gibt drei mögliche Werte: `TOP`, `CENTER` und `BOTTOM`, von denen jeder mit der Methode `setAlignment()` verwendet werden kann.

<ComponentDemo 
path='/webforj/dialogalignments?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/dialog/DialogAlignmentsView.java'
height = '550px'
/>

### Vollbild und Breakpoints {#full-screen-and-breakpoints}

Der `Dialog` kann auf den Vollbildmodus gesetzt werden. Wenn der Vollbildmodus aktiviert ist, kann der `Dialog` nicht bewegt oder positioniert werden. Dieser Modus kann mit dem Breakpoint-Attribut des `Dialogs` manipuliert werden. Der Breakpoint ist eine Medienabfrage, die Komponenten erstellt, wenn der `Dialog` automatisch in den Vollbildmodus wechselt. Wenn die Abfrage übereinstimmt, wechselt der `Dialog` in den Vollbildmodus - andernfalls wird er positioniert.

## Styling {#styling}

### Themen {#themes}

`Dialog`-Komponenten kommen mit <JavadocLink type="foundation" location="com/webforj/component/dialog/Dialog.Theme.html">7 verschiedenen Themen</JavadocLink>, die ein schnelles Styling ohne Verwendung von CSS ermöglichen. Diese Themen sind vordefinierte Stile, die auf Schaltflächen angewendet werden können, um ihr Erscheinungsbild und ihre visuelle Präsentation zu ändern. Sie bieten eine schnelle und konsistente Möglichkeit, das Aussehen von Schaltflächen in einer Anwendung anzupassen. 

Während es viele Anwendungsfälle für jedes der verschiedenen Themen gibt, sind einige Beispielverwendungen:

  - **Gefahr**: Aktionen mit schwerwiegenden Konsequenzen, wie das Löschen ausgefüllter Informationen oder das dauerhafte Löschen eines Kontos/Daten, stellen einen guten Anwendungsfall für Dialoge mit dem Gefahr-Thema dar.
  - **Standard**: Das Standard-Thema ist geeignet für Aktionen in einer Anwendung, die keine besondere Aufmerksamkeit erfordern und allgemein sind, zum Beispiel das Umschalten einer Einstellung.
  - **Primär**: Dieses Thema eignet sich als Haupt-"Call-to-Action" auf einer Seite, wie zum Beispiel Anmelden, Änderungen speichern oder zu einer anderen Seite fortfahren.
  - **Erfolg**: Dialoge mit dem Erfolgsthema sind hervorragend geeignet, um den erfolgreichen Abschluss eines Elements in einer Anwendung zu visualisieren, wie zum Beispiel die Übermittlung eines Formulars oder den Abschluss eines Anmeldevorgangs. Das Erfolgsthema kann programmgesteuert angewendet werden, sobald eine erfolgreiche Aktion abgeschlossen ist.
  - **Warnung**: Warnung-Dialoge sind nützlich, um Benutzer darauf hinzuweisen, dass sie kurz davor sind, eine potenziell riskante Aktion auszuführen, beispielsweise beim Navigieren von einer Seite mit ungesicherten Änderungen. Diese Aktionen haben oft geringere Auswirkungen als solche, die das Gefahr-Thema verwenden würden.
  - **Grau**: Gut für subtile Aktionen, wie geringfügige Einstellungen oder Aktionen, die eher ergänzend zu einer Seite sind und nicht Teil der Hauptfunktionalität.
  - **Info**: Das Info-Thema ist eine gute Wahl, um einem Benutzer klärende, zusätzliche Informationen bereitzustellen, wenn es erforderlich ist.

<ComponentDemo 
path='/webforj/dialogthemes?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/dialog/DialogThemesView.java'
height = '500px'
/>

<TableBuilder name="Dialog" />
