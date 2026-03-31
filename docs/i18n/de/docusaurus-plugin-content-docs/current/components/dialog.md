---
title: Dialog
sidebar_position: 30
_i18n_hash: 4896ea2a90b7c5155fe9ef291e69b2ad
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-dialog" />
<DocChip chip='since' label='23.06' />
<JavadocLink type="dialog" location="com/webforj/component/dialog/Dialog" top='true'/>

Die `Dialog`-Komponente zeigt ein Popup-Fenster an, das sich über die aktuelle Ansicht legt und die Aufmerksamkeit auf fokussierte Inhalte wie Formulare, Bestätigungen oder Informationsnachrichten lenkt.

<!-- INTRO_END -->

## `Dialog`-Struktur {#dialog-structure}

Der `Dialog` ist in drei Sektionen organisiert: einen Header, einen Inhaltsbereich und eine Fußzeile. Komponenten können zu jeder Sektion mit `addToHeader()`, `addToContent()` und `addToFooter()` hinzugefügt werden.

<ComponentDemo 
path='/webforj/dialogsections?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/dialog/DialogSectionsView.java'
height = '225px'
/>

## Anwendungen {#usages}

1. **Benutzerrückmeldungen und Bestätigungen**: `Dialog`-Komponenten werden oft verwendet, um Rückmeldungen zu geben oder eine Bestätigung vom Benutzer anzufordern. Sie können verschiedene wichtige Rückmeldungen an einen Benutzer anzeigen, wie zum Beispiel:

  >- Erfolgreiche Nachrichten 
  >- Fehlerwarnungen
  >- Bestätigungen von Einreichungen

2. **Formulareingabe und -bearbeitung**: Dialoge können verwendet werden, um Benutzereingaben zu sammeln oder es Benutzern zu ermöglichen, Informationen auf kontrollierte und fokussierte Weise zu bearbeiten. Zum Beispiel kann ein Dialog angezeigt werden, um die Profildetails eines Benutzers zu bearbeiten oder ein mehrstufiges Formular auszufüllen.

3. **Kontextuelle Informationen**: Die Anzeige zusätzlicher kontextueller Informationen oder Tooltips in einem Dialog kann Benutzern helfen, komplexe Funktionen oder Daten besser zu verstehen. Dialoge können ausführliche Erklärungen, Diagramme oder Hilfsdokumentation bereitstellen.

4. **Bild- und Medienvorschauen**: Wenn Benutzer Medieninhalte ansehen müssen, kann ein `Dialog` verwendet werden, um größere Vorschauen oder Galerien anzuzeigen, beispielsweise beim Interagieren mit:
  >- Bildern
  >- Videos
  >- anderen Medien

## Hintergrund und Unschärfe {#backdrop-and-blur}

Durch Aktivieren des Hintergrundattributs der webforJ `Dialog`-Komponente wird ein Hintergrund hinter dem `Dialog` angezeigt. Darüber hinaus wird der Hintergrund des `Dialogs` bei aktiviertem unscharf-Attribut verschwommen. Die Anpassung dieser Einstellungen kann den Benutzern helfen, indem sie Tiefe, visuelle Hierarchie und Kontext bieten, was zu klareren Anleitungen für den Benutzer führt.

<ComponentDemo 
path='/webforj/dialogbackdropblur?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/dialog/DialogBackdropBlurView.java'
height = '300px'
/>

## Öffnen und Schließen des `Dialogs` {#opening-and-closing-the-dialog}

Nach der Erstellung eines neuen `Dialog`-Objekts verwenden Sie die Methode `open()`, um den Dialog anzuzeigen. Anschließend kann die `Dialog`-Komponente durch eine dieser Aktionen geschlossen werden:
- Verwendung der Methode `close()`
- Drücken der <kbd>ESC</kbd>-Taste
- Klicken außerhalb des `Dialogs`

Entwickler können auswählen, welche Interaktionen den `Dialog` mit `setCancelOnEscKey()` und `setCancelOnOutsideClick()` schließen. Darüber hinaus kann die Methode `setClosable()` verhindern oder erlauben, dass sowohl das Drücken der <kbd>ESC</kbd>-Taste als auch das Klicken außerhalb des `Dialogs` die Komponente schließen.

<ComponentDemo 
path='/webforj/dialogclose?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/dialog/DialogCloseView.java'
height = '350px'
/>

## Autofokus {#auto-focus}

Wenn aktiviert, gibt der Autofokus automatisch den Fokus auf das erste Element innerhalb des Dialogs, das fokussiert werden kann. Dies ist nützlich, um die Aufmerksamkeit der Benutzer zu lenken, und ist über die Methode `setAutoFocus()` anpassbar.

<ComponentDemo 
path='/webforj/dialogautofocus?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/dialog/DialogAutoFocusView.java'
height = '350px'
/>

## Ziehbar {#draggable}

Der `Dialog` verfügt über eine eingebaute Funktionalität, um ziehbar zu sein, sodass der Benutzer das `Dialog`-Fenster durch Klicken und Ziehen verschieben kann. Die Position des `Dialogs` kann von jedem der Felder innerhalb davon manipuliert werden: dem Header, dem Inhalt oder der Fußzeile.

### Kanten-Schnappen {#snap-to-edge}
Es ist auch möglich, dieses Verhalten so zu kalibrieren, dass es an den Rand des Bildschirms schnippt, was bedeutet, dass sich der `Dialog` automatisch an den Rand des Displays ausrichtet, wenn er von seinem Zieh- und Ablagedatum losgelassen wird. Das Schnappen kann über die Methode `setSnapToEdge()` geändert werden. Die `setSnapThreshold()`-Methode nimmt eine Anzahl von Pixeln entgegen, die festlegt, wie weit der `Dialog` von den Seiten des Bildschirms entfernt sein sollte, bevor er automatisch an die Ränder schnippt.

<ComponentDemo 
path='/webforj/dialogdraggable?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/dialog/DialogDraggableView.java'
height = '350px'
/>

## Positionierung {#positioning}

Die Position des Dialogs kann mit den integrierten Methoden `setPosx()` und `setPosy()` manipuliert werden. Diese Methoden akzeptieren ein String-Argument, das jede anwendbare CSS-Länge darstellen kann, wie Pixel oder Ansichtshöhe/-breite. Eine Liste dieser Maße [kann unter diesem Link gefunden werden](https://developer.mozilla.org/en-US/docs/Learn/CSS/Building_blocks/Values_and_units#numbers_lengths_and_percentages).

<ComponentDemo 
path='/webforj/dialogpositioning?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/dialog/DialogPositioningView.java'
height = '350px'
/>

### Vertikale Ausrichtung {#vertical-alignment}

Zusätzlich zur manuellen Zuordnung der X- und Y-Position eines Dialogs ist es möglich, die integrierte Enum-Klasse des Dialogs zu verwenden, um den `Dialog` auszurichten. Es gibt drei mögliche Werte: `TOP`, `CENTER` und `BOTTOM`, die jeweils mit der Methode `setAlignment()` verwendet werden können.

<ComponentDemo 
path='/webforj/dialogalignments?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/dialog/DialogAlignmentsView.java'
height = '550px'
/>

### Vollbild und Breakpoints {#full-screen-and-breakpoints}

Der `Dialog` kann auf den Vollbildmodus eingestellt werden. Wenn der Vollbildmodus aktiviert ist, kann der `Dialog` nicht verschoben oder positioniert werden. Dieser Modus kann mit dem breakpoint-Attribut des `Dialogs` manipuliert werden. Der Breakpoint ist eine Medienabfrage, die Komponenten angibt, wann der `Dialog` automatisch in den Vollbildmodus wechselt. Wenn die Abfrage übereinstimmt, wechselt der `Dialog` in den Vollbildmodus - andernfalls wird er positioniert.

## Stil {#styling}

### Themen {#themes}

`Dialog`-Komponenten verfügen über <JavadocLink type="foundation" location="com/webforj/component/dialog/Dialog.Theme.html">7 verschiedene integrierte Themen</JavadocLink>, die eine schnelle Stilgestaltung ohne die Verwendung von CSS ermöglichen. Diese Themen sind vordefinierte Stile, die auf Schaltflächen angewendet werden können, um ihr Aussehen und ihre visuelle Präsentation zu ändern. Sie bieten eine schnelle und konsistente Möglichkeit, das Erscheinungsbild von Schaltflächen in einer Anwendung anzupassen. 

Es gibt viele Anwendungsfälle für jedes der verschiedenen Themen. Einige Beispiele sind:

  - **Gefahr**: Aktionen mit schwerwiegenden Folgen, wie das Löschen ausgefüllter Informationen oder das permanente Löschen eines Kontos/Daten, sind gute Anwendungsfälle für Dialoge mit dem Gefahr-Thema.
  - **Standard**: Das Standardthema ist geeignet für Aktionen in einer Anwendung, die keine besondere Aufmerksamkeit erfordern und allgemein sind, wie das Umschalten einer Einstellung.
  - **Primär**: Dieses Thema eignet sich als Haupt-"Handlungsaufforderung" auf einer Seite, wie sich anzumelden, Änderungen zu speichern oder zu einer anderen Seite fortzufahren.
  - **Erfolg**: Dialoge mit Erfolgsthema sind hervorragend geeignet, um den erfolgreichen Abschluss eines Elements in einer Anwendung zu visualisieren, wie das Einreichen eines Formulars oder den Abschluss eines Anmeldevorgangs. Das Erfolgsthema kann programmgesteuert angewendet werden, sobald eine erfolgreiche Aktion abgeschlossen wurde.
  - **Warnung**: Warnungsdialoge sind nützlich, um Benutzer darauf hinzuweisen, dass sie dabei sind, eine potenziell riskante Aktion auszuführen, z.B. beim Navigieren von einer Seite mit nicht gespeicherten Änderungen. Diese Aktionen sind oft weniger bedeutend als solche, die das Gefahr-Thema verwenden.
  - **Grau**: Gut für subtile Aktionen, wie kleine Einstellungen oder Aktionen, die mehr ergänzend zu einer Seite sind und nicht Teil der Hauptfunktionalität.
  - **Info**: Das Info-Thema ist eine gute Wahl, um einem Benutzer klärende, zusätzliche Informationen zur Verfügung zu stellen, wenn erforderlich.

<ComponentDemo 
path='/webforj/dialogthemes?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/dialog/DialogThemesView.java'
height = '500px'
/>

<TableBuilder name="Dialog" />
