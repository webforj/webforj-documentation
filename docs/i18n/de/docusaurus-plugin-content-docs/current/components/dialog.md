---
title: Dialog
sidebar_position: 30
sidebar_class_name: new-content
_i18n_hash: 621dc045e979c7513b41ef04c6cd242a
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-dialog" />
<DocChip chip='since' label='23.06' />
<JavadocLink type="dialog" location="com/webforj/component/dialog/Dialog" top='true'/>

Die `Dialog`-Komponente zeigt ein Popup-Fenster an, das sich über die aktuelle Ansicht legt und die Aufmerksamkeit auf fokussierte Inhalte wie Formulare, Bestätigungen oder Informationsnachrichten lenkt. 

<!-- INTRO_END -->

## `Dialog`-Struktur {#dialog-structure}

Der `Dialog` ist in drei Abschnitte organisiert: Kopfzeile, Inhaltsbereich und Fußzeile. Komponenten können jedem Abschnitt mit `addToHeader()`, `addToContent()` und `addToFooter()` hinzugefügt werden.

<ComponentDemo 
path='/webforj/dialogsections?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/dialog/DialogSectionsView.java'
height = '225px'
/>

## Verwendungsmöglichkeiten {#usages}

1. **Benutzerrückmeldungen und Bestätigungen**: `Dialog`-Komponenten werden häufig verwendet, um Rückmeldungen zu geben oder um eine Bestätigung vom Benutzer zu bitten. Sie können dem Benutzer verschiedene wichtige Rückmeldungen anzeigen, wie zum Beispiel:

  >- Erfolgsmeldungen 
  >- Fehlermeldungen
  >- Bestätigungsübermittlungen

2. **Formulareingabe und -bearbeitung**: Sie können Dialoge verwenden, um Benutzereingaben zu sammeln oder um ihnen zu ermöglichen, Informationen kontrolliert und fokussiert zu bearbeiten. Zum Beispiel kann ein Dialog aufpoppen, um Details des Benutzerprofils zu bearbeiten oder ein mehrstufiges Formular abzuschließen.

3. **Kontextbezogene Informationen**: Das Anzeigen zusätzlicher kontextbezogener Informationen oder Tooltipps in einem Dialog kann den Benutzern helfen, komplexe Funktionen oder Daten zu verstehen. Dialoge können eingehende Erklärungen, Diagramme oder Hilfedokumentationen bieten.

4. **Vorschauen von Bildern und Medien**: Wenn Benutzer Teile von Medien ansehen müssen, kann ein `Dialog` verwendet werden, um größere Vorschauen oder Galerien anzuzeigen, wie z.B. beim Umgang mit:
  >- Bildern
  >- Videos
  >- Anderen Medien

## Hintergrund und Unschärfe {#backdrop-and-blur}

Indem die Hintergrund-Eigenschaft der webforJ `Dialog`-Komponente aktiviert wird, wird ein Hintergrund hinter dem `Dialog` angezeigt. Wenn aktiviert, wird die Unschärfe-Eigenschaft des Dialogs den Hintergrund des `Dialogs` unscharf machen. Das Ändern dieser Einstellungen kann den Benutzern helfen, durch Bereitstellung von Tiefen, visueller Hierarchie und Kontext mehr Klarheit und Anleitung zu geben.

<ComponentDemo 
path='/webforj/dialogbackdropblur?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/dialog/DialogBackdropBlurView.java'
height = '300px'
/>

## Öffnen und Schließen des `Dialogs` {#opening-and-closing-the-dialog}

Nach der Erstellung eines neuen `Dialog`-Objekts verwenden Sie die Methode `open()`, um den Dialog anzuzeigen. Dann kann die `Dialog`-Komponente durch eine dieser Aktionen geschlossen werden:
- Verwendung der Methode `close()`
- Drücken der <kbd>ESC</kbd>-Taste
- Klick außerhalb des `Dialogs`

Entwickler können wählen, welche Interaktionen den `Dialog` schließen, mit `setCancelOnEscKey()` und `setCancelOnOutsideClick()`. Zusätzlich kann die Methode `setClosable()` verhindern oder erlauben, dass sowohl das Drücken der <kbd>ESC</kbd>-Taste als auch das Klicken außerhalb des `Dialogs` die Komponente schließen.

<ComponentDemo 
path='/webforj/dialogclose?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/dialog/DialogCloseView.java'
height = '350px'
/>

## Autofokus {#auto-focus}

Wenn aktiviert, wird der Autofokus automatisch den ersten fokussierbaren Element im Dialog fokussieren. Dies ist nützlich, um die Aufmerksamkeit der Benutzer zu lenken, und kann über die Methode `setAutoFocus()` angepasst werden.

<ComponentDemo 
path='/webforj/dialogautofocus?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/dialog/DialogAutoFocusView.java'
height = '350px'
/>

## Verschiebbar {#draggable}

Der `Dialog` verfügt über eine integrierte Funktionalität, um verschiebbar zu sein, wodurch der Benutzer das `Dialog`-Fenster durchs Klicken und Ziehen neu anordnen kann. Die Position des `Dialogs` kann von jedem der Felder innerhalb davon manipuliert werden: Kopfzeile, Inhalt oder Fußzeile.

### An den Rand schnappen {#snap-to-edge}
Es ist auch möglich, dieses Verhalten so zu kalibrieren, dass es an den Rand des Bildschirms schnappen kann, was bedeutet, dass der `Dialog` automatisch mit dem Rand des Displays ausgerichtet wird, wenn er vom Ziehen und Ablegen losgelassen wird. Das Snapping kann über die Methode `setSnapToEdge()` geändert werden. Die Methode `setSnapThreshold()` nimmt eine Anzahl von Pixeln, die festlegt, wie weit der `Dialog` von den Seiten des Bildschirms entfernt sein sollte, bevor er automatisch an den Rändern schnappen kann.

<ComponentDemo 
path='/webforj/dialogdraggable?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/dialog/DialogDraggableView.java'
height = '350px'
/>

## Positionierung {#positioning}

Die Position des Dialogs kann mithilfe der integrierten Methoden `setPosx()` und `setPosy()` manipuliert werden. Diese Methoden akzeptieren ein String-Argument, das jede anwendbare CSS-Längeneinheit wie Pixel oder Sichthöhe/-breite darstellen kann. Eine Liste dieser Maße [kann unter diesem Link gefunden werden](https://developer.mozilla.org/en-US/docs/Learn/CSS/Building_blocks/Values_and_units#numbers_lengths_and_percentages).

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

Der `Dialog` kann so eingestellt werden, dass er in den Vollbildmodus wechselt. Wenn der Vollbildmodus aktiviert ist, kann der `Dialog` nicht bewegt oder positioniert werden. Dieser Modus kann mit der Breakpoint-Eigenschaft des `Dialogs` manipuliert werden. Der Breakpoint ist eine Medienabfrage, die die Komponenten angibt, wenn der `Dialog` automatisch in den Vollbildmodus wechselt. Wenn die Abfrage übereinstimmt, wechselt der `Dialog` in den Vollbildmodus – andernfalls wird er positioniert.

### Automatische Breite <DocChip chip='since' label='26.00' /> {#auto-width}

Standardmäßig dehnt sich der `Dialog` aus, um den verfügbaren horizontalen Raum zu füllen. Wenn die automatische Breite über `setAutoWidth(true)` aktiviert wird, passt sich der `Dialog` stattdessen an die Breite seines Inhalts an.

<ComponentDemo 
path='/webforj/dialogautowidth?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/dialog/DialogAutoWidthView.java'
height = '350px'
/>

## Gestaltung {#styling}

### Themen {#themes}

`Dialog`-Komponenten kommen mit <JavadocLink type="foundation" location="com/webforj/component/dialog/Dialog.Theme.html">7 unterschiedlichen Themen</JavadocLink>, die eine schnelle Gestaltung ohne die Verwendung von CSS ermöglichen. Diese Themen sind vordefinierte Stile, die auf Schaltflächen angewendet werden können, um deren Aussehen und visuelle Präsentation zu ändern. Sie bieten eine schnelle und konsistente Möglichkeit, das Erscheinungsbild von Schaltflächen in einer Anwendung anzupassen. 

Obwohl es viele Anwendungsfälle für jedes der verschiedenen Themen gibt, sind einige Beispielverwendungen:

  - **Gefahr**: Aktionen mit schweren Folgen, wie das Löschen von ausgefüllten Informationen oder das permanente Löschen eines Kontos/Daten, stellen einen guten Anwendungsfall für Dialoge mit dem Gefahr-Thema dar.
  - **Standard**: Das Standardthema ist für Aktionen in einer Anwendung geeignet, die keine besondere Aufmerksamkeit erfordern und die allgemein sind, wie das Umschalten einer Einstellung.
  - **Primär**: Dieses Thema ist geeignet als Haupthandlungsaufforderung auf einer Seite, z.B. für Anmeldungen, das Speichern von Änderungen oder das Fortfahren zu einer anderen Seite.
  - **Erfolg**: Dialoge mit Erfolgsthema sind hervorragend geeignet, um den erfolgreichen Abschluss eines Elements in einer Anwendung zu visualisieren, wie die Übermittlung eines Formulars oder den Abschluss eines Registrierungsprozesses. Das Erfolgsthema kann programmatisch angewendet werden, sobald eine erfolgreiche Aktion abgeschlossen wurde.
  - **Warnung**: Warnungsdialoge sind nützlich, um Benutzer darauf hinzuweisen, dass sie dabei sind, eine potenziell riskante Aktion auszuführen, wie z.B. wenn sie von einer Seite mit nicht gespeicherten Änderungen weg navigieren. Diese Aktionen sind oft weniger bedeutsam als solche, die das Gefahr-Thema verwenden würden.
  - **Grau**: Gut für subtile Aktionen, wie geringfügige Einstellungen oder Aktionen, die eher ergänzend zu einer Seite sind und nicht Teil der Hauptfunktionalität.
  - **Info**: Das Info-Thema ist eine gute Wahl, um einem Benutzer zusätzliche, klärende Informationen bereitzustellen.

<ComponentDemo 
path='/webforj/dialogthemes?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/dialog/DialogThemesView.java'
height = '500px'
/>

<TableBuilder name="Dialog" />
