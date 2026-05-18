---
title: Dialog
sidebar_position: 30
sidebar_class_name: new-content
_i18n_hash: 750f3d1f7c1c905274eac22a90b270de
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-dialog" />
<DocChip chip='since' label='23.06' />
<JavadocLink type="dialog" location="com/webforj/component/dialog/Dialog" top='true'/>

Die `Dialog`-Komponente zeigt ein Popup-Fenster an, das die aktuelle Ansicht überlagert und die Aufmerksamkeit auf fokussierte Inhalte wie Formulare, Bestätigungen oder Informationsnachrichten lenkt. 

<!-- INTRO_END -->

## `Dialog`-Struktur {#dialog-structure}

Der `Dialog` ist in drei Abschnitte organisiert: einen Kopfbereich, einen Inhaltsbereich und einen Fußbereich. Komponenten können zu jedem Abschnitt mit `addToHeader()`, `addToContent()` und `addToFooter()` hinzugefügt werden.

<ComponentDemo
path='/webforj/dialogsections'
files={['src/main/java/com/webforj/samples/views/dialog/DialogSectionsView.java']}
height='225px'
/>

## Anwendungen {#usages}

1. **Benutzerfeedback und Bestätigung**: `Dialog`-Komponenten werden oft verwendet, um Feedback zu geben oder eine Benutzerbestätigung anzufordern. Sie können verschiedene wichtige Informationen an einen Benutzer anzeigen, wie zum Beispiel:

  >- Erfolgsmeldungen 
  >- Fehlermeldungen
  >- Bestätigungen von Einsendungen

2. **Formulareingabe und Bearbeitung**: Sie können Dialoge verwenden, um Benutzereingaben zu sammeln oder es ihnen zu ermöglichen, Informationen auf kontrollierte und fokussierte Weise zu bearbeiten. Zum Beispiel kann ein Dialog erscheinen, um die Profilinformationen eines Benutzers zu bearbeiten oder ein mehrstufiges Formular auszufüllen.

3. **Kontextuale Informationen**: Das Anzeigen zusätzlicher kontextueller Informationen oder Tooltips in einem Dialog kann Benutzern helfen, komplexe Funktionen oder Daten zu verstehen. Dialoge können umfassende Erklärungen, Diagramme oder Hilfedokumentationen bereitstellen.

4. **Bild- und Medienvorschau**: Wenn Benutzer Medieninhalte anzeigen müssen, kann ein `Dialog` verwendet werden, um größere Vorschauen oder Galerien anzuzeigen, zum Beispiel, wenn mit folgenden Inhalten interagiert wird:
  >- Bilder
  >- Videos
  >- Weitere Medien

## Hintergrund und Unschärfe {#backdrop-and-blur}

Durch Aktivieren des Hintergrundattributs der webforJ `Dialog`-Komponente wird ein Hintergrund hinter dem `Dialog` angezeigt. Darüber hinaus wird, wenn es aktiviert ist, das Unschärfeartribut des Dialogs den Hintergrund des `Dialogs` unscharf machen. Das Ändern dieser Einstellungen kann den Benutzern helfen, indem es Tiefen, visuelle Hierarchien und Kontexte bietet, was zu einer klareren Anleitung für den Benutzer führt.

<ComponentDemo
path='/webforj/dialogbackdropblur'
files={['src/main/java/com/webforj/samples/views/dialog/DialogBackdropBlurView.java']}
height='300px'
/>

## Öffnen und Schließen des `Dialogs` {#opening-and-closing-the-dialog}

Nach der Erstellung eines neuen `Dialog`-Objekts verwenden Sie die Methode `open()`, um den Dialog anzuzeigen. Dann kann die `Dialog`-Komponente durch eine dieser Aktionen geschlossen werden:
- Verwendung der Methode `close()`
- Drücken der <kbd>ESC</kbd>-Taste
- Klicken außerhalb des `Dialogs`

Entwickler können wählen, welche Interaktionen den `Dialog` mit `setCancelOnEscKey()` und `setCancelOnOutsideClick()` schließen. Darüber hinaus kann die Methode `setClosable()` verhindern oder erlauben, dass sowohl das Drücken der <kbd>ESC</kbd>-Taste als auch das Klicken außerhalb des `Dialogs` die Komponente schließen.

<ComponentDemo
path='/webforj/dialogclose'
files={['src/main/java/com/webforj/samples/views/dialog/DialogCloseView.java']}
height='350px'
/>

## Automatische Fokussierung {#auto-focus}

Wenn aktiviert, wird automatisch der erste fokussierbare Bereich innerhalb des Dialogs fokussiert. Dies ist nützlich, um die Aufmerksamkeit der Benutzer zu lenken und kann über die Methode `setAutoFocus()` angepasst werden.

<ComponentDemo
path='/webforj/dialogautofocus'
files={['src/main/java/com/webforj/samples/views/dialog/DialogAutoFocusView.java']}
height='350px'
/>

## Ziehbar {#draggable}

Der `Dialog` verfügt über eine integrierte Funktion, um ziehbar zu sein, sodass der Benutzer das `Dialog`-Fenster durch Klicken und Ziehen verschieben kann. Die Position des `Dialogs` kann von einem der Felder innerhalb davon manipuliert werden: dem Kopfbereich, dem Inhalt oder dem Fußbereich.

### An die Kante schnappen {#snap-to-edge}
Es ist auch möglich, dieses Verhalten so zu kalibrieren, dass es an die Kante des Bildschirms schnappen kann, was bedeutet, dass sich der `Dialog` beim Loslassen von seiner Zieh- und Ablegedatei automatisch an der Kante des Displays ausrichtet. Das Schnappen kann über die Methode `setSnapToEdge()` geändert werden. Die `setSnapThreshold()`-Methode nimmt eine Anzahl von Pixeln entgegen, die angibt, wie weit der `Dialog` von den Seiten des Bildschirms entfernt sein sollte, bevor er automatisch an den Kanten schnappen kann.  

<ComponentDemo
path='/webforj/dialogdraggable'
files={['src/main/java/com/webforj/samples/views/dialog/DialogDraggableView.java']}
height='350px'
/>

## Positionierung {#positioning}

Die Position des Dialogs kann mit den integrierten Methoden `setPosx()` und `setPosy()` manipuliert werden. Diese Methoden nehmen ein string-Argument entgegen, das jede anwendbare CSS-Längeneinheit darstellen kann, wie z. B. Pixel oder View-Höhe/-Breite. Eine Liste dieser Maße [kann unter diesem Link gefunden werden](https://developer.mozilla.org/en-US/docs/Learn/CSS/Building_blocks/Values_and_units#numbers_lengths_and_percentages).

<ComponentDemo
path='/webforj/dialogpositioning'
files={['src/main/java/com/webforj/samples/views/dialog/DialogPositioningView.java']}
height='350px'
/>

### Vertikale Ausrichtung {#vertical-alignment}

Neben der manuellen Zuweisung der X- und Y-Position eines Dialogs ist es möglich, die integrierte Enum-Klasse des Dialogs zu verwenden, um den `Dialog` auszurichten. Es gibt drei mögliche Werte: `TOP`, `CENTER` und `BOTTOM`, die jeweils mit der Methode `setAlignment()` verwendet werden können. 

<ComponentDemo
path='/webforj/dialogalignments'
files={['src/main/java/com/webforj/samples/views/dialog/DialogAlignmentsView.java']}
height='550px'
/>

### Vollbild und Breakpoints {#full-screen-and-breakpoints}

Der `Dialog` kann so eingestellt werden, dass er in den Vollbildmodus wechselt. Wenn der Vollbildmodus aktiviert ist, kann der `Dialog` nicht verschoben oder positioniert werden. Dieser Modus kann mit dem Breakpoint-Attribut des `Dialogs` manipuliert werden. Der Breakpoint ist eine Medienabfrage, die definiert, wann der `Dialog` automatisch in den Vollbildmodus wechselt. Wenn die Abfrage übereinstimmt, ändert sich der `Dialog` in den Vollbildmodus - andernfalls wird er positioniert.

### Automatische Breite <DocChip chip='since' label='26.00' /> {#auto-width}

Standardmäßig dehnt sich der `Dialog` aus, um den verfügbaren horizontalen Raum auszufüllen. Wenn die automatische Breite über `setAutoWidth(true)` aktiviert ist, passt sich der `Dialog` stattdessen an die Breite seines Inhalts an.

<ComponentDemo
path='/webforj/dialogautowidth'
files={['src/main/java/com/webforj/samples/views/dialog/DialogAutoWidthView.java']}
height='350px'
/>

## Stil {#styling}

### Themen {#themes}

`Dialog`-Komponenten bieten <JavadocLink type="foundation" location="com/webforj/component/dialog/Dialog.Theme.html">7 verschiedene Themen</JavadocLink>, die integriert sind und eine schnelle Stilgebung ohne die Verwendung von CSS ermöglichen. Diese Themen sind vordefinierte Stile, die auf Schaltflächen angewendet werden können, um ihr Aussehen und ihre visuelle Präsentation zu ändern. Sie bieten eine schnelle und konsistente Möglichkeit, das Erscheinungsbild von Schaltflächen in einer Anwendung anzupassen. 

Während es viele Anwendungsfälle für jedes der verschiedenen Themen gibt, sind einige Beispiele:

  - **Gefahr**: Aktionen mit schwerwiegenden Konsequenzen, wie das Löschen ausgefüllter Informationen oder das dauerhafte Löschen eines Kontos/Daten, stellen einen guten Anwendungsfall für Dialoge mit dem Gefahr-Thema dar.
  - **Standard**: Das Standardthema ist für Aktionen in einer Anwendung geeignet, die keine besondere Aufmerksamkeit erfordern und allgemein sind, wie das Umschalten einer Einstellung.
  - **Primär**: Dieses Thema eignet sich gut als Haupt-"Handlungsaufforderung" auf einer Seite, wie zum Beispiel sich anzumelden, Änderungen zu speichern oder zu einer anderen Seite fortzufahren.
  - **Erfolg**: Dialoge im Erfolgsthema sind hervorragend geeignet, um den erfolgreichen Abschluss eines Elements in einer Anwendung zu visualisieren, z. B. die Einsendung eines Formulars oder den Abschluss eines Anmeldeprozesses. Das Erfolgsthema kann programmatisch angewendet werden, sobald eine erfolgreiche Aktion abgeschlossen ist.
  - **Warnung**: Warnung-Dialoge sind nützlich, um die Benutzer darauf hinzuweisen, dass sie eine potenziell riskante Aktion durchführen, wie zum Beispiel das Verlassen einer Seite mit nicht gespeicherten Änderungen. Diese Aktionen sind oft weniger Auswirkungen als die, die das Gefahr-Thema verwenden würden.
  - **Grau**: Gut für subtile Aktionen, wie geringfügige Einstellungen oder Aktionen, die mehr ergänzend zu einer Seite sind und nicht Teil der Hauptfunktionalität.
  - **Info**: Das Info-Thema ist eine gute Wahl, um klärende, zusätzliche Informationen an einen Benutzer bereitzustellen.

<ComponentDemo
path='/webforj/dialogthemes'
files={['src/main/java/com/webforj/samples/views/dialog/DialogThemesView.java']}
height='500px'
/>

<TableBuilder name="Dialog" />
