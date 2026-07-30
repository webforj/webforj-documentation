---
title: Dialog
sidebar_position: 30
description: >-
  Open modal popups with the Dialog component, including header, content, and
  footer sections, backdrop blur, and configurable close behavior.
_i18n_hash: 385730b12eeec91287bcbbf77b4e9c77
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-dialog" />
<DocChip chip='since' label='23.06' />
<JavadocLink type="dialog" location="com/webforj/component/dialog/Dialog" top='true'/>

Die `Dialog`-Komponente zeigt ein Popup-Fenster an, das die aktuelle Ansicht überlagert und die Aufmerksamkeit auf fokussierte Inhalte wie Formulare, Bestätigungen oder informative Nachrichten lenkt.

<!-- INTRO_END -->

## `Dialog`-Struktur {#dialog-structure}

Der `Dialog` ist in drei Sektionen organisiert: einen Header, einen Inhaltsbereich und einen Footer. Komponenten können zu jeder Sektion mit `addToHeader()`, `addToContent()` und `addToFooter()` hinzugefügt werden.

<ComponentDemo
path='/webforj/dialogsections'
files={['src/main/java/com/webforj/samples/views/dialog/DialogSectionsView.java']}
height='225px'
/>

## Verwendungen {#usages}

1. **Benutzerfeedback und Bestätigung**: `Dialog`-Komponenten werden häufig verwendet, um Feedback zu geben oder um eine Bestätigung des Benutzers zu bitten. Sie können verschiedene wichtige Rückmeldungen an einen Benutzer anzeigen, wie zum Beispiel:

  >- Erfolgsmeldungen
  >- Fehlermeldungen
  >- Bestätigungsübermittlungen

2. **Formulareingabe und Bearbeitung**: Sie können Dialoge verwenden, um Benutzereingaben zu sammeln oder ihnen zu erlauben, Informationen auf kontrollierte und fokussierte Weise zu bearbeiten. Beispielsweise kann ein Dialog erscheinen, um die Profilinformationen eines Benutzers zu bearbeiten oder ein mehrstufiges Formular auszufüllen.

3. **Kontextuelle Informationen**: Das Anzeigen zusätzlicher kontextueller Informationen oder Tooltips in einem Dialog kann Benutzern helfen, komplexe Funktionen oder Daten zu verstehen. Dialoge können umfassende Erklärungen, Diagramme oder Hilfsdokumentationen bereitstellen.

4. **Bild- und Medienvorschauen**: Wenn Benutzer Teile von Medien ansehen müssen, kann ein `Dialog` verwendet werden, um größere Vorschauen oder Galerien anzuzeigen, z. B. beim Interagieren mit:
  >- Bildern
  >- Videos
  >- Anderen Medien

## Hintergrund und Unschärfe {#backdrop-and-blur}

Ein geöffneter `Dialog` hat einen gedimmten Hintergrund, der subtil die Aufmerksamkeit auf seine Inhalte lenkt. Mit `setBackdrop()` und `setBlurred()` können Sie ändern, wie webforJ den Inhalt hinter dem `Dialog` anzeigt (oder verdeckt). Die Modifizierung dieser Attribute kann den Benutzern helfen, indem Tiefe und visuelle Hierarchie bereitgestellt werden.

<ComponentDemo
path='/webforj/dialogbackdropblur'
files={['src/main/java/com/webforj/samples/views/dialog/DialogBackdropBlurView.java']}
height='600px'
/>

## Öffnen und Schließen des `Dialogs` {#opening-and-closing-the-dialog}

Nach der Erstellung eines neuen `Dialog`-Objekts verwenden Sie die `open()`-Methode, um den Dialog anzuzeigen. Dann kann die `Dialog`-Komponente durch eine der folgenden Aktionen geschlossen werden:
- Mit der Methode `close()`
- Durch Drücken der <kbd>ESC</kbd>-Taste
- Durch Klicken außerhalb des `Dialogs`

Entwickler können entscheiden, welche Interaktionen den `Dialog` mit `setCancelOnEscKey()` und `setCancelOnOutsideClick()` schließen. Darüber hinaus kann die Methode `setClosable()` verhindern oder erlauben, dass sowohl das Drücken der <kbd>ESC</kbd>-Taste als auch das Klicken außerhalb des `Dialogs` die Komponente schließen.

<ComponentDemo
path='/webforj/dialogclose'
files={['src/main/java/com/webforj/samples/views/dialog/DialogCloseView.java']}
height='350px'
/>

## Automatischer Fokus {#auto-focus}

Wenn aktiviert, gibt der automatische Fokus automatisch das Augenmerk auf das erste Element innerhalb des Dialogs, das fokussiert werden kann. Dies ist nützlich, um die Aufmerksamkeit der Benutzer zu lenken, und ist über die Methode `setAutoFocus()` anpassbar.

<ComponentDemo
path='/webforj/dialogautofocus'
files={['src/main/java/com/webforj/samples/views/dialog/DialogAutoFocusView.java']}
height='350px'
/>

## Verschiebbar {#draggable}

Der `Dialog` hat eine integrierte Funktionalität zur Verschiebbarkeit, die es dem Benutzer ermöglicht, das `Dialog`-Fenster durch Klicken und Ziehen zu verschieben. Die Position des `Dialogs` kann von jedem der Felder innerhalb davon manipuliert werden: dem Header, dem Inhalt oder dem Footer.

### An den Rand snappen {#snap-to-edge}
Es ist auch möglich, dieses Verhalten so zu kalibrieren, dass es an den Rand des Bildschirms schnallt, was bedeutet, dass sich der `Dialog` automatisch mit dem Rand des Displays ausrichtet, wenn er aus dem Ziehen und Ablegen losgelassen wird. Das Snapping kann über die Methode `setSnapToEdge()` geändert werden. Die `setSnapThreshold()`-Methode nimmt eine Anzahl von Pixeln, die festlegt, wie weit der `Dialog` von den Seiten des Bildschirms entfernt sein sollte, bevor er automatisch an den Rändern anheftet.

<ComponentDemo
path='/webforj/dialogdraggable'
files={['src/main/java/com/webforj/samples/views/dialog/DialogDraggableView.java']}
height='350px'
/>

## Positionierung {#positioning}

Die Position des Dialogs kann mittels der integrierten Methoden `setPosx()` und `setPosy()` manipuliert werden. Diese Methoden nehmen ein String-Argument an, das jede anwendbare CSS-Längeneinheit darstellen kann, wie z. B. Pixel oder Ansichtshöhe/-breite. Eine Liste dieser Messungen [kann unter diesem Link gefunden werden](https://developer.mozilla.org/en-US/docs/Learn/CSS/Building_blocks/Values_and_units#numbers_lengths_and_percentages).

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

Der `Dialog` kann so eingestellt werden, dass er in den Vollbildmodus wechselt. Wenn der Vollbildmodus aktiviert ist, kann der `Dialog` nicht verschoben oder positioniert werden. Dieser Modus kann über das Breakpoint-Attribut des `Dialogs` manipuliert werden. Der Breakpoint ist eine Medienabfrage, die Komponenten angibt, wann der `Dialog` automatisch in den Vollbildmodus wechselt. Wenn die Abfrage übereinstimmt, wechselt der `Dialog` in den Vollbildmodus - andernfalls wird er positioniert.

### Automatische Breite <DocChip chip='since' label='26.00' /> {#auto-width}

Standardmäßig dehnt sich der `Dialog` aus, um den verfügbaren horizontalen Platz zu füllen. Wenn die automatische Breite über `setAutoWidth(true)` aktiviert ist, passt sich der `Dialog` an die Breite seines Inhalts an.

<ComponentDemo
path='/webforj/dialogautowidth'
files={['src/main/java/com/webforj/samples/views/dialog/DialogAutoWidthView.java']}
height='350px'
/>

## Stilgebung {#styling}

### Themen {#themes}

`Dialog`-Komponenten verfügen über <JavadocLink type="foundation" location="com/webforj/component/dialog/Dialog.Theme.html">7 verschiedene integrierte Themen</JavadocLink> für eine schnelle Stilisierung ohne die Verwendung von CSS. Diese Themen sind vordefinierte Stile, die auf Schaltflächen angewendet werden können, um deren Aussehen und visuelle Darstellung zu ändern. Sie bieten eine schnelle und konsistente Möglichkeit, das Aussehen von Schaltflächen in einer Anwendung anzupassen.

Während es viele Anwendungsfälle für jedes der verschiedenen Themen gibt, sind einige Beispiele:

  - **Gefahr**: Aktionen mit schwerwiegenden Konsequenzen, wie das Löschen ausgefüllter Informationen oder das dauerhafte Löschen eines Kontos/Daten, stellen einen guten Anwendungsfall für Dialoge mit dem Gefahr-Thema dar.
  - **Standard**: Das Standardthema ist für Aktionen in einer Anwendung geeignet, die keine besondere Aufmerksamkeit erfordern und die allgemein sind, wie das Umstellen eines Settings.
  - **Primär**: Dieses Thema eignet sich als Haupt-"Handlungsaufforderung" auf einer Seite, wie z. B. Registrierung, Änderungen speichern oder zu einer anderen Seite fortfahren.
  - **Erfolg**: Dialoge mit dem Erfolgsthema eignen sich hervorragend zur Visualisierung des erfolgreichen Abschlusses eines Elements in einer Anwendung, wie z. B. der Übermittlung eines Formulars oder dem Abschluss eines Anmeldeprozesses. Das Erfolgsthema kann programmatisch angewendet werden, sobald eine erfolgreiche Aktion abgeschlossen wurde.
  - **Warnung**: Warnungsdialoge sind nützlich, um Benutzer darauf hinzuweisen, dass sie dabei sind, eine potenziell riskante Aktion durchzuführen, z. B. beim Navigieren von einer Seite mit nicht gespeicherten Änderungen. Diese Aktionen sind oft weniger beeinträchtigend als diejenigen, die das Gefahr-Thema verwenden würden.
  - **Grau**: Gut für subtile Aktionen, wie kleinere Einstellungen oder Aktionen, die eher ergänzend zu einer Seite sind und nicht Teil der Hauptfunktionalität.
  - **Info**: Das Informationsthema ist eine gute Wahl, um einem Benutzer klärende, zusätzliche Informationen bereitzustellen, wenn dies erforderlich ist.

<ComponentDemo
path='/webforj/dialogthemes'
files={['src/main/java/com/webforj/samples/views/dialog/DialogThemesView.java']}
height='500px'
/>

<TableBuilder name="Dialog" />
