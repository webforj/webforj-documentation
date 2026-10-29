---
title: Dialog
sidebar_position: 30
description: >-
  Open modal popups with the Dialog component, including header, content, and
  footer sections, backdrop blur, and configurable close behavior.
_i18n_hash: 8f78bdc280006c73955232e7f52ed365
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-dialog" />
<DocChip chip='since' label='23.06' />
<JavadocLink type="dialog" location="com/webforj/component/dialog/Dialog" top='true'/>

Die `Dialog`-Komponente zeigt ein Popup-Fenster an, das die aktuelle Ansicht überlagert und die Aufmerksamkeit auf fokussierten Inhalt wie Formulare, Bestätigungen oder Informationsnachrichten lenkt.

<!-- INTRO_END -->

## `Dialog`-Struktur {#dialog-structure}

Der `Dialog` ist in drei Abschnitte unterteilt: einen Header, einen Inhaltsbereich und einen Footer. Komponenten können zu jedem Abschnitt mit `addToHeader()`, `addToContent()` und `addToFooter()` hinzugefügt werden.

<ComponentDemo
path='/webforj/dialogsections'
files={['src/main/java/com/webforj/samples/views/dialog/DialogSectionsView.java']}
height='375px'
/>

## Verwendung {#usages}

1. **Benutzerfeedback und Bestätigung**: `Dialog`-Komponenten werden häufig verwendet, um Feedback zu geben oder eine Bestätigung des Benutzers anzufordern. Sie können verschiedene wichtige Rückmeldungen an den Benutzer anzeigen, z. B.:

  >- Erfolgsnachrichten
  >- Fehlermeldungen
  >- Bestätigungsanfragen

2. **Formulareingabe und Bearbeitung**: Sie können Dialoge verwenden, um Benutzereingaben zu sammeln oder ihnen zu ermöglichen, Informationen in einer kontrollierten und fokussierten Weise zu bearbeiten. Zum Beispiel kann ein Dialog erscheinen, um die Profilinformationen eines Benutzers zu bearbeiten oder ein mehrstufiges Formular abzuschließen.

3. **Kontextbezogene Informationen**: Das Anzeigen zusätzlicher kontextbezogener Informationen oder Tooltips in einem Dialog kann den Benutzern helfen, komplexe Funktionen oder Daten zu verstehen. Dialoge können ausführliche Erklärungen, Diagramme oder Hilfedokumentationen bieten.

4. **Bild- und Medienvorschauen**: Wenn Benutzer Medieninhalte anzeigen müssen, kann ein `Dialog` verwendet werden, um größere Vorschauen oder Galerien zu zeigen, wie z. B. beim Interagieren mit:
  >- Bildern
  >- Videos
  >- Anderen Medien

## Hintergrund und Unschärfe {#backdrop-and-blur}

Ein geöffneter `Dialog` hat einen gedämpften Hintergrund, der subtil die Aufmerksamkeit auf seinen Inhalt lenkt. Mit `setBackdrop()` und `setBlurred()` können Sie ändern, wie webforJ den Inhalt hinter dem `Dialog` anzeigt (oder verdeckt). Das Ändern dieser Eigenschaften kann den Benutzern helfen, indem es Tiefe und visuelle Hierarchie bereitstellt.

<ComponentDemo
path='/webforj/dialogbackdropblur'
files={['src/main/java/com/webforj/samples/views/dialog/DialogBackdropBlurView.java']}
height='600px'
/>

## Öffnen und Schließen des `Dialogs` {#opening-and-closing-the-dialog}

Nachdem Sie ein neues `Dialog`-Objekt erstellt haben, verwenden Sie die Methode `open()`, um den Dialog anzuzeigen. Um ihn programmgesteuert zu schließen, verwenden Sie die Methode `close()`. Sie können auch steuern, welche Benutzerinteraktionen den Dialog schließen:

- **Klicken Sie außerhalb** schließt den `Dialog` standardmäßig, da `cancelOnOutsideClick` standardmäßig auf `true` gesetzt ist. Verwenden Sie `setCancelOnOutsideClick(false)`, um dieses Verhalten zu deaktivieren.
- **Drücken der <kbd>ESC</kbd>** schließt den `Dialog` standardmäßig nicht, da `cancelOnEscKey` standardmäßig auf `false` gesetzt ist. Verwenden Sie `setCancelOnEscKey(true)`, um dieses Verhalten zu aktivieren.

Verwenden Sie `setCloseable(true)`, um beide Interaktionen zu aktivieren, oder `setCloseable(false)`, um beide zu deaktivieren. Das folgende Demo aktiviert beide Interaktionen.

<ComponentDemo
path='/webforj/dialogclose'
files={['src/main/java/com/webforj/samples/views/dialog/DialogCloseView.java']}
height='375px'
/>

## Autofokus {#auto-focus}

Wenn aktiviert, gibt der Autofokus automatisch den Fokus auf das erste fokussierbare Element innerhalb des Dialogs. Dies ist nützlich, um die Aufmerksamkeit der Benutzer zu lenken, und ist über die Methode `setAutoFocus()` anpassbar.

<ComponentDemo
path='/webforj/dialogautofocus'
files={['src/main/java/com/webforj/samples/views/dialog/DialogAutoFocusView.java']}
height='400px'
/>

## Ziehbar {#draggable}

Der `Dialog` verfügt über eine eingebaute Funktionalität zum Ziehen, die es dem Benutzer ermöglicht, das `Dialog`-Fenster durch Klicken und Ziehen zu verschieben. Die Position des `Dialogs` kann von jedem der Felder innerhalb davon manipuliert werden: dem Header, dem Inhalt oder dem Footer.

### Am Rand einrasten {#snap-to-edge}
Es ist auch möglich, dieses Verhalten so zu kalibrieren, dass es am Rand des Bildschirms einrastet, was bedeutet, dass sich der `Dialog` automatisch am Rand der Anzeige anpasst, wenn er aus seinem Zieh- und Ablagedatum freigegeben wird. Das Einrasten kann über die Methode `setSnapToEdge()` geändert werden. Die `setSnapThreshold()`-Methode nimmt eine Anzahl von Pixeln an, die festlegt, wie weit der `Dialog` von den Rändern des Bildschirms entfernt sein soll, bevor er automatisch an die Ränder einrastet.

<ComponentDemo
path='/webforj/dialogdraggable'
files={['src/main/java/com/webforj/samples/views/dialog/DialogDraggableView.java']}
height='325px'
/>

## Positionierung {#positioning}

Die Position des Dialogs kann mit den integrierten Methoden `setPosx()` und `setPosy()` manipuliert werden. Diese Methoden nehmen ein String-Argument an, das jede anwendbare CSS-Länge darstellen kann, z. B. Pixel oder Ansichtshöhe/-breite. Eine Liste dieser Maße [finden Sie unter diesem Link](https://developer.mozilla.org/en-US/docs/Learn/CSS/Building_blocks/Values_and_units#numbers_lengths_and_percentages).

<ComponentDemo
path='/webforj/dialogpositioning'
files={['src/main/java/com/webforj/samples/views/dialog/DialogPositioningView.java']}
height='400px'
/>

### Vertikale Ausrichtung {#vertical-alignment}

Zusätzlich zur manuellen Zuweisung der X- und Y-Position eines Dialogs ist es möglich, die integrierte Enum-Klasse des Dialogs zu verwenden, um den `Dialog` auszurichten. Es gibt drei mögliche Werte: `TOP`, `CENTER` und `BOTTOM`, die jeweils mit der Methode `setAlignment()` verwendet werden können.

<ComponentDemo
path='/webforj/dialogalignments'
files={['src/main/java/com/webforj/samples/views/dialog/DialogAlignmentsView.java']}
height='450px'
/>

### Vollbildmodus und Breakpoints {#full-screen-and-breakpoints}

Der `Dialog` kann so eingestellt werden, dass er in den Vollbildmodus wechselt. Wenn der Vollbildmodus aktiviert ist, kann der `Dialog` nicht verschoben oder positioniert werden. Dieser Modus kann mit dem Breakpoint-Attribut des `Dialogs` manipuliert werden. Der Breakpoint ist eine Medienabfrage, die angibt, wann der `Dialog` automatisch in den Vollbildmodus wechselt. Wenn die Abfrage übereinstimmt, wechselt der `Dialog` in den Vollbildmodus - andernfalls wird er positioniert.

### Automatische Breite <DocChip chip='since' label='26.00' /> {#auto-width}

Standardmäßig dehnt sich der `Dialog` aus, um den verfügbaren horizontalen Raum auszufüllen. Wenn die automatische Breite über `setAutoWidth(true)` aktiviert ist, passt sich der `Dialog` stattdessen an die Breite seines Inhalts an.

<ComponentDemo
path='/webforj/dialogautowidth'
files={['src/main/java/com/webforj/samples/views/dialog/DialogAutoWidthView.java']}
height='350px'
/>

## Styling {#styling}

### Themes {#themes}

`Dialog`-Komponenten verfügen über <JavadocLink type="foundation" location="com/webforj/component/dialog/Dialog.Theme.html">7 diskrete Themes</JavadocLink>, die eine schnelle Stilisierung ohne CSS ermöglichen. Diese Themes sind vordefinierte Stile, die auf Schaltflächen angewendet werden können, um deren Erscheinungsbild und visuelle Präsentation zu ändern. Sie bieten eine schnelle und konsistente Möglichkeit, das Aussehen von Schaltflächen in einer Anwendung anzupassen.

Während es viele Anwendungsfälle für jedes der verschiedenen Themes gibt, sind einige Beispiele:

  - **Gefahr**: Aktionen mit schwerwiegenden Konsequenzen, wie das Löschen ausgefüllter Informationen oder das permanente Löschen eines Kontos/Daten, stellt einen guten Anwendungsfall für Dialoge mit dem Gefahr-Thema dar.
  - **Standard**: Das Standard-Theme ist für Aktionen in einer Anwendung geeignet, die keine besondere Aufmerksamkeit erfordern und allgemein sind, wie z. B. das Umschalten eines Einstellungs.
  - **Primär**: Dieses Thema eignet sich als Haupt-"Handlungsaufforderung" auf einer Seite, z. B. für das Anmelden, das Speichern von Änderungen oder den Übergang zu einer anderen Seite.
  - **Erfolg**: Erfolgs-themen Dialoge sind ausgezeichnet, um den erfolgreichen Abschluss eines Elements in einer Anwendung zu visualisieren, wie z. B. die Einreichung eines Formulars oder den Abschluss eines Anmeldevorgangs. Das Erfolgsthema kann programmatisch angewendet werden, sobald eine erfolgreiche Aktion abgeschlossen ist.
  - **Warnung**: Warnungsdialoge sind nützlich, um den Benutzern anzuzeigen, dass sie im Begriff sind, eine potenziell riskante Aktion auszuführen, z. B. beim Navigieren von einer Seite mit ungespeicherten Änderungen. Diese Aktionen sind oft weniger weitreichend als solche, die das Gefahr-Thema verwenden würden.
  - **Grau**: Gut für subtile Aktionen, wie kleinere Einstellungen oder Aktionen, die mehr ergänzend zu einer Seite sind und nicht Teil der Hauptfunktionalität.
  - **Info**: Das Info-Thema ist eine gute Wahl, um einem Benutzer klärende, zusätzliche Informationen bereitzustellen, wenn er gedrängt wird.

<ComponentDemo
path='/webforj/dialogthemes'
files={['src/main/java/com/webforj/samples/views/dialog/DialogThemesView.java']}
height='375px'
/>

<TableBuilder name="Dialog" />
