---
title: Dialog
sidebar_position: 30
description: >-
  Open modal popups with the Dialog component, including header, content, and
  footer sections, backdrop blur, and configurable close behavior.
_i18n_hash: 3dcdd5a9a66f2b00229064500da2bb79
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-dialog" />
<DocChip chip='since' label='23.06' />
<JavadocLink type="dialog" location="com/webforj/component/dialog/Dialog" top='true'/>

Die `Dialog`-Komponente zeigt ein Popup-Fenster an, das sich über die aktuelle Ansicht legt und die Aufmerksamkeit auf fokussierte Inhalte wie Formulare, Bestätigungen oder Informationsnachrichten lenkt.

<!-- INTRO_END -->

## `Dialog`-Struktur {#dialog-structure}

Der `Dialog` ist in drei Bereiche organisiert: einen Kopfbereich, einen Inhaltsbereich und einen Fußbereich. Komponenten können zu jedem Abschnitt hinzugefügt werden, indem `addToHeader()`, `addToContent()` und `addToFooter()` verwendet werden.

<ComponentDemo
path='/webforj/dialogsections'
files={['src/main/java/com/webforj/samples/views/dialog/DialogSectionsView.java']}
height='225px'
/>

## Verwendungen {#usages}

1. **Benutzerfeedback und Bestätigung**: `Dialog`-Komponenten werden häufig verwendet, um Feedback zu geben oder die Bestätigung des Benutzers zu verlangen. Sie können verschiedene wichtige Rückmeldungen an den Benutzer anzeigen, wie zum Beispiel:

  >- Erfolgsmeldungen
  >- Fehlermeldungen
  >- Bestätigungszusammenfassungen

2. **Formulareingabe und Bearbeitung**: Sie können Dialoge verwenden, um Benutzereingaben zu sammeln oder ihnen zu ermöglichen, Informationen auf kontrollierte und fokussierte Weise zu bearbeiten. Beispielsweise kann ein Dialog aufpoppen, um die Benutzerdaten zu bearbeiten oder ein mehrstufiges Formular auszufüllen.

3. **Kontextuelle Informationen**: Das Anzeigen zusätzlicher kontextueller Informationen oder Tooltips in einem Dialog kann den Benutzern helfen, komplexe Funktionen oder Daten zu verstehen. Dialoge können umfassende Erklärungen, Diagramme oder Hilfedokumentationen bereitstellen.

4. **Vorschau von Medien und Bildern**: Wenn Benutzer Medienstücke anzeigen müssen, kann ein `Dialog` verwendet werden, um größere Vorschauen oder Galerien anzuzeigen, beispielsweise beim Interagieren mit:
  >- Bildern
  >- Videos
  >- Anderen Medien

## Hintergrund und Unschärfe {#backdrop-and-blur}

Durch Aktivieren des Hintergrundattributs der webforJ `Dialog`-Komponente wird ein Hintergrund hinter dem `Dialog` angezeigt. Darüber hinaus wird, wenn aktiviert, das unscharfe Attribut des Dialogs den Hintergrund des `Dialogs` unscharf machen. Das Ändern dieser Einstellungen kann den Benutzern helfen, indem es Tiefen, visuelle Hierarchie und Kontext bietet, was zu klareren Anleitungen für den Benutzer führt.

<ComponentDemo
path='/webforj/dialogbackdropblur'
files={['src/main/java/com/webforj/samples/views/dialog/DialogBackdropBlurView.java']}
height='300px'
/>

## Öffnen und Schließen des `Dialogs` {#opening-and-closing-the-dialog}

Nachdem ein neues `Dialog`-Objekt erstellt wurde, verwenden Sie die Methode `open()`, um den Dialog anzuzeigen. Der `Dialog`-Komponente kann dann durch eine dieser Aktionen geschlossen werden:
- Verwenden der Methode `close()`
- Drücken der <kbd>ESC</kbd>-Taste
- Klicken außerhalb des `Dialogs`

Entwickler können wählen, welche Interaktionen den `Dialog` mit `setCancelOnEscKey()` und `setCancelOnOutsideClick()` schließen. Darüber hinaus kann die Methode `setClosable()` das Schließen der Komponente durch Betätigen der <kbd>ESC</kbd>-Taste und Klicken außerhalb des `Dialogs` verhindern oder erlauben.

<ComponentDemo
path='/webforj/dialogclose'
files={['src/main/java/com/webforj/samples/views/dialog/DialogCloseView.java']}
height='350px'
/>

## Auto-Fokus {#auto-focus}

Wenn aktiviert, wird der Auto-Fokus automatisch das erste Element im Dialog anvisieren, das fokussiert werden kann. Dies ist nützlich, um die Aufmerksamkeit der Benutzer zu lenken und kann über die Methode `setAutoFocus()` angepasst werden.

<ComponentDemo
path='/webforj/dialogautofocus'
files={['src/main/java/com/webforj/samples/views/dialog/DialogAutoFocusView.java']}
height='350px'
/>

## Ziehbar {#draggable}

Der `Dialog` verfügt über eine integrierte Funktionalität, um ziehbar zu sein, die es dem Benutzer ermöglicht, das `Dialog`-Fenster durch Klicken und Ziehen zu verschieben. Die Position des `Dialogs` kann von einem der Felder innerhalb davon, also dem Kopf-, Inhalts- oder Fußbereich, manipuliert werden.

### An den Rand schnappen {#snap-to-edge}
Es ist auch möglich, dieses Verhalten so zu kalibrieren, dass es am Bildschirmrand anliegt, was bedeutet, dass sich der `Dialog` automatisch am Rand des Displays ausrichtet, wenn er von seinem Ziehen und Ablegen positioniert wird. Das Schnappen kann über die Methode `setSnapToEdge()` geändert werden. Die Methode `setSnapThreshold()` nimmt eine Anzahl von Pixeln entgegen, die festlegt, wie weit der `Dialog` von den Seiten des Bildschirms entfernt sein sollte, bevor er automatisch an die Ränder schnallt.

<ComponentDemo
path='/webforj/dialogdraggable'
files={['src/main/java/com/webforj/samples/views/dialog/DialogDraggableView.java']}
height='350px'
/>

## Positionierung {#positioning}

Die Position des Dialogs kann mit den integrierten Methoden `setPosx()` und `setPosy()` manipuliert werden. Diese Methoden nehmen ein String-Argument an, das jede anwendbare CSS-Längeneinheit darstellen kann, wie Pixel oder Höhe/Breite der Ansicht. Eine Liste dieser Maße [finden Sie unter diesem Link](https://developer.mozilla.org/en-US/docs/Learn/CSS/Building_blocks/Values_and_units#numbers_lengths_and_percentages).

<ComponentDemo
path='/webforj/dialogpositioning'
files={['src/main/java/com/webforj/samples/views/dialog/DialogPositioningView.java']}
height='350px'
/>

### Vertikale Ausrichtung {#vertical-alignment}

Zusätzlich zur manuellen Zuweisung der X- und Y-Position eines Dialogs ist es möglich, die integrierte Enum-Klasse des Dialogs zu verwenden, um den `Dialog` auszurichten. Es gibt drei mögliche Werte: `TOP`, `CENTER` und `BOTTOM`, die jeweils mit der Methode `setAlignment()` verwendet werden können.

<ComponentDemo
path='/webforj/dialogalignments'
files={['src/main/java/com/webforj/samples/views/dialog/DialogAlignmentsView.java']}
height='550px'
/>

### Vollbild und Breakpoints {#full-screen-and-breakpoints}

Der `Dialog` kann so eingestellt werden, dass er in den Vollbildmodus wechselt. Wenn der Vollbildmodus aktiviert ist, kann der `Dialog` nicht verschoben oder positioniert werden. Dieser Modus kann mit dem Breakpointattribut des `Dialogs` manipuliert werden. Der Breakpoint ist eine Medienabfrage, die Komponenten definiert, wann der `Dialog` automatisch in den Vollbildmodus wechselt. Wenn die Abfrage übereinstimmt, wechselt der `Dialog` in den Vollbildmodus - andernfalls wird er positioniert.

### Automatische Breite <DocChip chip='since' label='26.00' /> {#auto-width}

Standardmäßig dehnt sich der `Dialog` aus, um den verfügbaren Horizontalraum auszufüllen. Wenn die automatische Breite über `setAutoWidth(true)` aktiviert wird, passt sich der `Dialog` stattdessen an die Breite seines Inhalts an.

<ComponentDemo
path='/webforj/dialogautowidth'
files={['src/main/java/com/webforj/samples/views/dialog/DialogAutoWidthView.java']}
height='350px'
/>

## Gestaltung {#styling}

### Themen {#themes}

`Dialog`-Komponenten verfügen über <JavadocLink type="foundation" location="com/webforj/component/dialog/Dialog.Theme.html">7 verschiedene Themen </JavadocLink>, die für eine schnelle Gestaltung ohne CSS integriert sind. Diese Themen sind vordefinierte Stile, die auf Schaltflächen angewendet werden können, um deren Erscheinungsbild und visuelle Darstellung zu verändern. Sie bieten eine schnelle und konsistente Möglichkeit, das Aussehen von Schaltflächen in einer Anwendung anzupassen.

Obwohl es viele Anwendungsfälle für jedes der verschiedenen Themen gibt, sind einige Beispiele:

  - **Gefahr**: Aktionen mit schwerwiegenden Folgen, wie das Löschen ausgefüllter Informationen oder das dauerhafte Löschen eines Kontos/Daten, stellen einen guten Anwendungsfall für Dialoge mit dem Gefahren-Thema dar.
  - **Standard**: Das Standardthema ist geeignet für Aktionen in einer Anwendung, die keine besondere Aufmerksamkeit erfordern und die allgemein sind, wie das Umschalten einer Einstellung.
  - **Primär**: Dieses Thema eignet sich als Haupt-„Handlungsaufforderung“ auf einer Seite, wie beispielsweise das Anmelden, Speichern von Änderungen oder das Fortfahren zu einer anderen Seite.
  - **Erfolg**: Erfolgsorientierte Dialoge sind hervorragend geeignet, um die erfolgreiche Abschluss eines Elements in einer Anwendung zu visualisieren, z. B. das Einreichen eines Formulars oder den Abschluss eines Anmeldevorgangs. Das Erfolgsthema kann programmgesteuert angewendet werden, sobald eine erfolgreiche Aktion abgeschlossen wurde.
  - **Warnung**: Warnungsdialoge sind nützlich, um Benutzer darauf hinzuweisen, dass sie dabei sind, eine potenziell riskante Aktion durchzuführen, wie z.B. wenn sie eine Seite mit nicht gespeicherten Änderungen verlassen. Diese Aktionen sind oft weniger einschneidend als diejenigen, die das Gefahren-Thema verwenden würden.
  - **Grau**: Gut für subtile Aktionen, wie kleine Einstellungen oder Aktionen, die eher ergänzend zu einer Seite sind und nicht Teil der Hauptfunktionalität.
  - **Info**: Das Info-Thema ist eine gute Wahl, um einem Benutzer klärende, zusätzliche Informationen bereitzustellen, wenn es gepusht wird.

<ComponentDemo
path='/webforj/dialogthemes'
files={['src/main/java/com/webforj/samples/views/dialog/DialogThemesView.java']}
height='500px'
/>

<TableBuilder name="Dialog" />
