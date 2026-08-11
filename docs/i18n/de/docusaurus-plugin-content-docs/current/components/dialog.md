---
title: Dialog
sidebar_position: 30
description: >-
  Open modal popups with the Dialog component, including header, content, and
  footer sections, backdrop blur, and configurable close behavior.
_i18n_hash: 901c54134f4c21092deb23457747a29b
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-dialog" />
<DocChip chip='since' label='23.06' />
<JavadocLink type="dialog" location="com/webforj/component/dialog/Dialog" top='true'/>

Die `Dialog`-Komponente zeigt ein Popup-Fenster an, das die aktuelle Ansicht überlagert und die Aufmerksamkeit auf fokussierte Inhalte wie Formulare, Bestätigungen oder Informationsnachrichten lenkt.

<!-- INTRO_END -->

## `Dialog`-Struktur {#dialog-structure}

Der `Dialog` ist in drei Abschnitte unterteilt: einen Header, einen Inhaltsbereich und eine Fußzeile. Komponenten können in jeden Abschnitt mit `addToHeader()`, `addToContent()` und `addToFooter()` hinzugefügt werden.

<ComponentDemo
path='/webforj/dialogsections'
files={['src/main/java/com/webforj/samples/views/dialog/DialogSectionsView.java']}
height='375px'
/>

## Verwendungen {#usages}

1. **Benutzerrückmeldungen und Bestätigungen**: `Dialog`-Komponenten werden häufig verwendet, um Rückmeldungen zu geben oder eine Bestätigung vom Benutzer zu verlangen. Sie können verschiedene wichtige Rückmeldungen an einen Benutzer anzeigen, wie zum Beispiel:

  >- Erfolgsmeldungen
  >- Fehlermeldungen
  >- Bestätigungen von Einsendungen

2. **Formulareingabe und -bearbeitung**: Sie können Dialoge verwenden, um Benutzereingaben zu sammeln oder ihnen zu ermöglichen, Informationen in kontrollierter und fokussierter Weise zu bearbeiten. Beispielsweise kann ein Dialog erscheinen, um die Benutzerdaten zu bearbeiten oder ein mehrstufiges Formular abzuschließen.

3. **Kontextuelle Informationen**: Das Anzeigen zusätzlicher kontextueller Informationen oder Tooltips in einem Dialog kann Benutzern helfen, komplexe Funktionen oder Daten zu verstehen. Dialoge können tiefere Erklärungen, Diagramme oder Hilfedokumentationen bieten.

4. **Bild- und Medienvorschauen**: Wenn Benutzer Medieninhalte anzeigen müssen, kann ein `Dialog` verwendet werden, um größere Vorschauen oder Galerien anzuzeigen, z. B. beim Interagieren mit:
  >- Bildern
  >- Videos
  >- Anderen Medien

## Hintergrund und Unschärfe {#backdrop-and-blur}

Ein geöffneter `Dialog`-Komponente hat einen gedämpften Hintergrund, der subtil die Aufmerksamkeit auf seinen Inhalt lenkt. Mit `setBackdrop()` und `setBlurred()` können Sie ändern, wie webforJ den Inhalt hinter dem `Dialog` anzeigt (oder verdeckt). Die Anpassung dieser Attribute kann den Benutzern helfen, indem sie Tiefe und visuelle Hierarchie bieten.

<ComponentDemo
path='/webforj/dialogbackdropblur'
files={['src/main/java/com/webforj/samples/views/dialog/DialogBackdropBlurView.java']}
height='600px'
/>

## Öffnen und Schließen des `Dialogs` {#opening-and-closing-the-dialog}

Nach dem Erstellen eines neuen `Dialog`-Objekts verwenden Sie die Methode `open()`, um den Dialog anzuzeigen. Danach kann die `Dialog`-Komponente durch eine dieser Aktionen geschlossen werden:
- Nutzung der Methode `close()`
- Drücken der <kbd>ESC</kbd>-Taste
- Klicken außerhalb des `Dialogs`

Entwickler können wählen, welche Interaktionen den `Dialog` schließen, mit `setCancelOnEscKey()` und `setCancelOnOutsideClick()`. Darüber hinaus kann die Methode `setClosable()` das Schließen der Komponente entweder durch Drücken der <kbd>ESC</kbd>-Taste oder durch Klicken außerhalb des `Dialogs` verhindern oder erlauben.

<ComponentDemo
path='/webforj/dialogclose'
files={['src/main/java/com/webforj/samples/views/dialog/DialogCloseView.java']}
height='375px'
/>

## Autofokus {#auto-focus}

Wenn aktiviert, gibt der Autofokus automatisch den Fokus auf das erste Element innerhalb des Dialogs, das fokussiert werden kann. Dies ist nützlich, um die Aufmerksamkeit der Benutzer zu lenken, und ist über die Methode `setAutoFocus()` anpassbar.

<ComponentDemo
path='/webforj/dialogautofocus'
files={['src/main/java/com/webforj/samples/views/dialog/DialogAutoFocusView.java']}
height='400px'
/>

## Änderbar {#draggable}

Der `Dialog` verfügt über eine integrierte Funktionalität, um verschoben zu werden, sodass der Benutzer das `Dialog`-Fenster durch Klicken und Ziehen an einen anderen Ort verschieben kann. Die Position des `Dialogs` kann von einem der Felder innerhalb davon manipuliert werden: dem Header, dem Inhalt oder der Fußzeile.

### Am Rand einrasten {#snap-to-edge}
Es ist auch möglich, dieses Verhalten so anzupassen, dass es am Rand des Bildschirms einrastet, was bedeutet, dass sich der `Dialog` automatisch am Rand des Displays ausrichtet, wenn er aus seinem Ziehen und Ablegen abgegeben wird. Das Einklinken kann über die Methode `setSnapToEdge()` geändert werden. Die `setSnapThreshold()`-Methode akzeptiert eine Anzahl von Pixeln, die festlegt, wie weit der `Dialog` von den Seiten des Bildschirms entfernt sein sollte, bevor er automatisch an den Rändern einrastet.

<ComponentDemo
path='/webforj/dialogdraggable'
files={['src/main/java/com/webforj/samples/views/dialog/DialogDraggableView.java']}
height='325px'
/>

## Positionierung {#positioning}

Die Position des Dialogs kann mit den integrierten Methoden `setPosx()` und `setPosy()` manipuliert werden. Diese Methoden akzeptieren ein String-Argument, das jede anwendbare CSS-Längeneinheit repräsentieren kann, z. B. Pixel oder Ansichts-Höhe/-Breite. Eine Liste dieser Maße [finden Sie unter diesem Link](https://developer.mozilla.org/en-US/docs/Learn/CSS/Building_blocks/Values_and_units#numbers_lengths_and_percentages).

<ComponentDemo
path='/webforj/dialogpositioning'
files={['src/main/java/com/webforj/samples/views/dialog/DialogPositioningView.java']}
height='400px'
/>

### Vertikale Ausrichtung {#vertical-alignment}

Zusätzlich zur manuellen Zuweisung der X- und Y-Position eines Dialogs ist es möglich, die integrierte Enum-Klasse des Dialogs zu verwenden, um den `Dialog` auszurichten. Es stehen drei mögliche Werte zur Verfügung: `TOP`, `CENTER` und `BOTTOM`, die jeweils mit der Methode `setAlignment()` verwendet werden können.

<ComponentDemo
path='/webforj/dialogalignments'
files={['src/main/java/com/webforj/samples/views/dialog/DialogAlignmentsView.java']}
height='450px'
/>

### Vollbild und Breakpoints {#full-screen-and-breakpoints}

Der `Dialog` kann so eingestellt werden, dass er in den Vollbildmodus wechselt. Wenn der Vollbildmodus aktiviert ist, kann der `Dialog` nicht bewegt oder positioniert werden. Dieser Modus kann über das Breakpoint-Attribut des `Dialogs` manipuliert werden. Der Breakpoint ist eine Medienabfrage, die anzeigt, wann der `Dialog` automatisch in den Vollbildmodus wechselt. Wenn die Abfrage übereinstimmt, wird der `Dialog` auf Vollbild gewechselt - andernfalls wird er positioniert.

### Automatische Breite <DocChip chip='since' label='26.00' /> {#auto-width}

Standardmäßig streckt sich der `Dialog`, um den verfügbaren horizontalen Platz auszufüllen. Wenn die automatische Breite über `setAutoWidth(true)` aktiviert ist, passt sich der `Dialog` stattdessen an die Breite seines Inhalts an.

<ComponentDemo
path='/webforj/dialogautowidth'
files={['src/main/java/com/webforj/samples/views/dialog/DialogAutoWidthView.java']}
height='350px'
/>

## Styling {#styling}

### Themen {#themes}

`Dialog`-Komponenten sind mit <JavadocLink type="foundation" location="com/webforj/component/dialog/Dialog.Theme.html">7 diskreten Themen</JavadocLink> ausgestattet, die eine schnelle Gestaltung ohne Verwendung von CSS ermöglichen. Diese Themen sind vordefinierte Stile, die auf Schaltflächen angewendet werden können, um deren Erscheinungsbild und visuelle Präsentation zu ändern. Sie bieten eine schnelle und konsistente Möglichkeit, das Aussehen von Schaltflächen in einer Anwendung anzupassen.

Während es viele Anwendungsfälle für jedes der verschiedenen Themen gibt, sind hier einige Beispiele:

  - **Gefahr**: Aktionen mit schwerwiegenden Folgen, wie das Löschen ausgefüllter Informationen oder das permanente Löschen eines Kontos/Daten, stellen einen guten Anwendungsfall für Dialoge mit dem Gefahr-Thema dar.
  - **Standard**: Das Standard-Thema ist für Aktionen in einer Anwendung geeignet, die keine besondere Aufmerksamkeit erfordern und allgemein sind, z. B. das Umschalten einer Einstellung.
  - **Primär**: Dieses Thema eignet sich als Haupt-"Call-to-Action" auf einer Seite, z. B. sich anzumelden, Änderungen zu speichern oder zu einer anderen Seite zu gelangen.
  - **Erfolg**: Erfolgsthemedialoge sind hervorragend geeignet, um die erfolgreiche Durchführung eines Elements in einer Anwendung zu visualisieren, wie z. B. das Einreichen eines Formulars oder den Abschluss eines Anmeldevorgangs. Das Erfolgsthema kann programmgesteuert angewendet werden, sobald eine erfolgreiche Aktion abgeschlossen ist.
  - **Warnung**: Warnung-Dialoge sind nützlich, um Benutzer darauf hinzuweisen, dass sie eine potenziell riskante Aktion ausführen, beispielsweise beim Navigieren von einer Seite mit nicht gespeicherten Änderungen. Diese Aktionen sind oft weniger stark als solche, die das Gefahr-Thema verwenden würden.
  - **Grau**: Gut für subtile Aktionen, wie geringfügige Einstellungen oder Aktionen, die eher ergänzend zu einer Seite sind und nicht Teil der Hauptfunktionalität.
  - **Info**: Das Info-Thema ist eine gute Wahl, um einem Benutzer zusätzliche klarstellende Informationen bereitzustellen, wenn dies erforderlich ist.

<ComponentDemo
path='/webforj/dialogthemes'
files={['src/main/java/com/webforj/samples/views/dialog/DialogThemesView.java']}
height='375px'
/>

<TableBuilder name="Dialog" />
