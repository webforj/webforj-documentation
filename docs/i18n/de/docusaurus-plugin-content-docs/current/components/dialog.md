---
title: Dialog
sidebar_position: 30
_i18n_hash: d0087974ac244db9b082133be7966a3e
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-dialog" />
<DocChip chip='since' label='23.06' />
<JavadocLink type="dialog" location="com/webforj/component/dialog/Dialog" top='true'/>

Die webforJ Dialog-Komponente wurde entwickelt, um einem Entwickler zu ermöglichen, schnell und einfach einen Dialog in seiner Anwendung anzuzeigen, zum Beispiel für ein Anmeldefenster oder ein Informationsfeld.

Die Komponente besteht aus drei Abschnitten, die jeweils `Panel`-Komponenten sind: der **Kopfzeile**, dem **Inhalt** und der **Fußzeile**.

<ComponentDemo 
path='/webforj/dialogsections?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/dialog/DialogSectionsView.java'
height = '225px'
/>

## Verwendungen {#usages}

1. **Benutzerfeedback und Bestätigung**: `Dialog`-Komponenten werden oft verwendet, um Feedback zu geben oder eine Bestätigung vom Benutzer anzufordern. Sie können verschiedene wichtige Informationen an einen Benutzer anzeigen, wie zum Beispiel:

  >- Erfolgsnachrichten 
  >- Fehlermeldungen
  >- Bestätigungen von Einsendungen

2. **Formulareingabe und Bearbeitung**: Sie können Dialoge verwenden, um Benutzereingaben zu sammeln oder ihnen zu ermöglichen, Informationen in einer kontrollierten und fokussierten Weise zu bearbeiten. Zum Beispiel kann ein Dialog erscheinen, um Benutzerdaten zu bearbeiten oder ein mehrstufiges Formular auszufüllen.

3. **Kontextuelle Informationen**: Das Anzeigen zusätzlicher kontextueller Informationen oder Tooltips in einem Dialog kann Benutzern helfen, komplexe Funktionen oder Daten zu verstehen. Dialoge können ausführliche Erklärungen, Diagramme oder Hilfsdokumentationen bieten.

4. **Bild- und Medienvorschauen**: Wenn Benutzer Medienstücke anzeigen müssen, kann ein `Dialog` verwendet werden, um größere Vorschauen oder Galerien anzuzeigen, zum Beispiel bei der Interaktion mit:
  >- Bildern
  >- Videos
  >- Anderen Medien

## Hintergrund und Unschärfe {#backdrop-and-blur}

Durch Aktivieren des Hintergrundattributs der webforJ `Dialog`-Komponente wird ein Hintergrund hinter dem `Dialog` angezeigt. Darüber hinaus wird beim Aktivieren das unscharfe Attribut des Dialogs den Hintergrund des `Dialogs` unscharf machen. Das Modifizieren dieser Einstellungen kann Benutzern helfen, indem es Tiefe, visuelle Hierarchie und Kontext bietet, was zu einer klareren Anleitung für den Benutzer führt.

<ComponentDemo 
path='/webforj/dialogbackdropblur?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/dialog/DialogBackdropBlurView.java'
height = '300px'
/>

## Öffnen und Schließen des `Dialogs` {#opening-and-closing-the-dialog}

Nachdem ein neuer `Dialog`-Objekt erstellt wurde, verwenden Sie die Methode `open()`, um den Dialog anzuzeigen. Der `Dialog`-Komponente kann dann durch eine dieser Aktionen geschlossen werden:
- Verwenden der Methode `close()`
- Drücken der <kbd>ESC</kbd>-Taste
- Klicken außerhalb des `Dialogs`

Entwickler können wählen, welche Interaktionen den `Dialog` mit `setCancelOnEscKey()` und `setCancelOnOutsideClick()` schließen. Darüber hinaus kann die Methode `setClosable()` verhindern oder zulassen, dass sowohl das Drücken der <kbd>ESC</kbd>-Taste als auch das Klicken außerhalb des `Dialogs` die Komponente schließen.

<ComponentDemo 
path='/webforj/dialogclose?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/dialog/DialogCloseView.java'
height = '350px'
/>

## Auto-Fokus {#auto-focus}

Wenn aktiviert, wird der Auto-Fokus automatisch den Fokus auf das erste fokussierbare Element innerhalb des Dialogs setzen. Dies ist nützlich, um die Aufmerksamkeit der Benutzer zu lenken und über die Methode `setAutoFocus()` anpassbar.

<ComponentDemo 
path='/webforj/dialogautofocus?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/dialog/DialogAutoFocusView.java'
height = '350px'
/>

## Ziehbar {#draggable}

Der `Dialog` verfügt über eine integrierte Funktionalität, um gezogen zu werden, sodass der Benutzer das `Dialog`-Fenster durch Klicken und Ziehen versetzen kann. Die Position des `Dialogs` kann von jedem der Felder innerhalb davon manipuliert werden: der Kopfzeile, dem Inhalt oder der Fußzeile.

### An den Rand einrasten {#snap-to-edge}
Es ist auch möglich, dieses Verhalten so einzustellen, dass es am Rand des Bildschirms einrastet, was bedeutet, dass der `Dialog` sich automatisch am Rand des Displays ausrichtet, wenn er von seinem Zieh- und Ablegedatum freigegeben wird. Das Einrasten kann über die Methode `setSnapToEdge()` geändert werden. Die `setSnapThreshold()`-Methode nimmt eine Anzahl von Pixeln, die festlegt, wie weit der `Dialog` von den Seiten des Bildschirms entfernt sein sollte, bevor er automatisch an den Rändern einrastet.

<ComponentDemo 
path='/webforj/dialogdraggable?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/dialog/DialogDraggableView.java'
height = '350px'
/>

## Positionierung {#positioning}

Die Position des Dialogs kann mit den integrierten Methoden `setPosx()` und `setPosy()` manipuliert werden. Diese Methoden nehmen ein String-Argument an, das jede anwendbare CSS-Längeneinheit wie Pixel oder Ansichtshöhe/-breite darstellen kann. Eine Liste dieser Maße [findet man unter diesem Link](https://developer.mozilla.org/en-US/docs/Learn/CSS/Building_blocks/Values_and_units#numbers_lengths_and_percentages).

<ComponentDemo 
path='/webforj/dialogpositioning?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/dialog/DialogPositioningView.java'
height = '350px'
/>

### Vertikale Ausrichtung {#vertical-alignment}

Neben der manuellen Zuweisung der X- und Y-Position eines Dialogs ist es möglich, die integrierte Enum-Klasse des Dialogs zu verwenden, um den `Dialog` auszurichten. Es gibt drei mögliche Werte: `TOP`, `CENTER` und `BOTTOM`, die jeweils mit der Methode `setAlignment()` verwendet werden können. 

<ComponentDemo 
path='/webforj/dialogalignments?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/dialog/DialogAlignmentsView.java'
height = '550px'
/>

### Vollbild und Breakpoints {#full-screen-and-breakpoints}

Der `Dialog` kann so eingestellt werden, dass er in den Vollbildmodus wechselt. Wenn der Vollbildmodus aktiviert ist, kann der `Dialog` nicht bewegt oder positioniert werden. Dieser Modus kann mit dem Breakpointattribut des `Dialogs` manipuliert werden. Der Breakpoint ist eine Medienabfrage, die angibt, wann der `Dialog` automatisch in den Vollbildmodus wechselt. Wenn die Abfrage übereinstimmt, wechselt der `Dialog` in den Vollbildmodus - andernfalls ist er positioniert.

## Stil {#styling}

### Themen {#themes}

`Dialog`-Komponenten kommen mit <JavadocLink type="foundation" location="com/webforj/component/dialog/Dialog.Theme.html">7 unterschiedlichen Themen</JavadocLink>, die für eine schnelle Stilisierung ohne die Verwendung von CSS eingerichtet sind. Diese Themen sind vordefinierte Stile, die auf Schaltflächen angewendet werden können, um ihr Erscheinungsbild und ihre visuelle Präsentation zu ändern. Sie bieten eine schnelle und konsistente Möglichkeit, das Aussehen von Schaltflächen in einer Anwendung zu personalisieren.

Während es viele Anwendungsfälle für jedes der verschiedenen Themen gibt, sind einige Beispiele für Verwendungen:

  - **Gefahr**: Aktionen mit schweren Konsequenzen, wie das Löschen von ausgefüllten Informationen oder das dauerhafte Löschen eines Kontos/Daten, stellen einen guten Anwendungsfall für Dialoge mit dem Gefahr-Thema dar.
  - **Standard**: Das Standardthema ist geeignet für Aktionen in einer Anwendung, die keine besondere Aufmerksamkeit erfordern und generisch sind, wie das Umschalten einer Einstellung.
  - **Primär**: Dieses Thema eignet sich als Haupt-"Handlungsaufruf" auf einer Seite, wie das Anmelden, Speichern von Änderungen oder das Fortfahren zu einer anderen Seite.
  - **Erfolg**: Dialoge im Erfolgsthema eignen sich hervorragend zur Visualisierung der erfolgreichen Durchführung eines Elements in einer Anwendung, wie das Einreichen eines Formulars oder den Abschluss eines Anmeldeprozesses. Das Erfolgsthema kann programmatisch angewendet werden, nachdem eine erfolgreiche Aktion abgeschlossen wurde.
  - **Warnung**: Warnungsdialoge sind nützlich, um Benutzer darauf hinzuweisen, dass sie eine potenziell riskante Aktion durchführen, wie das Navigieren von einer Seite mit nicht gespeicherten Änderungen weg. Diese Aktionen sind oft weniger wirkungsvoll als diejenigen, die das Gefahr-Thema verwenden würden.
  - **Grau**: Gut für subtile Aktionen, wie geringfügige Einstellungen oder Aktionen, die eher ergänzend zu einer Seite und nicht Teil der Hauptfunktionalität sind.
  - **Info**: Das Info-Thema ist eine gute Wahl, um einem Benutzer erklärende, zusätzliche Informationen bereitzustellen, wenn nötig.

<ComponentDemo 
path='/webforj/dialogthemes?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/dialog/DialogThemesView.java'
height = '500px'
/>

<TableBuilder name="Dialog" />
