---
title: TextArea
sidebar_position: 130
_i18n_hash: c25007720c315e5b0b26197e1fdfff61
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-textarea" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/component/field/TextArea" top='true'/>

Die `TextArea`-Komponente bietet ein mehrzeiliges Texteingabefeld, in das Benutzer längere Textblöcke eingeben und bearbeiten können. Sie unterstützt maximale Zeichenbeschränkungen, Absatzstrukturen, Zeilenumbrüche und Validierungsregeln, um zu steuern, wie Eingaben behandelt werden.

<!-- INTRO_END -->

## Erstellung einer `TextArea` {#creating-a-textarea}

Erstellen Sie eine `TextArea`, indem Sie ein Label an den Konstruktor übergeben. Eigenschaften wie Platzhaltertext, Zeichenbeschränkungen und Umbruchverhalten können über Setter-Methoden konfiguriert werden.

<ComponentDemo 
path='/webforj/textarea?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/textarea/TextAreaView.java'
height = '300px'
/>

## Verwaltung von Absätzen {#managing-paragraphs}

Die `TextArea`-Komponente bietet Funktionen zum Umgang mit Textabsätzen, was sie ideal für Anwendungen macht, die Dokumentbearbeitung oder strukturierte Texteingaben erfordern.

Hier ist ein kurzes Beispiel, wie Sie Absatzinhalte aufbauen und bearbeiten können:

```java
TextArea textArea = new TextArea();

// Fügen Sie einen Absatz am Anfang ein
textArea.addParagraph(0, "Dies ist der erste Absatz.");

// Fügen Sie einen weiteren Absatz am Ende hinzu
textArea.addParagraph("Hier ist ein zweiter Absatz.");

// Fügen Sie zusätzlichen Inhalt zum ersten Absatz hinzu
textArea.appendToParagraph(0, " Dieser Satz ergänzt den ersten.");

// Entfernen Sie den zweiten Absatz
textArea.removeParagraph(1);

// Abrufen und Drucken aller aktuellen Absätze
List<String> paragraphs = textArea.getParagraphs();
for (int i = 0; i < paragraphs.size(); i++) {
  System.out.println("Absatz " + i + ": " + paragraphs.get(i));
}
```

## Validierung {#validation}

Die `TextArea`-Komponente unterstützt zwei ergänzende Arten der Validierung: strukturelle Einschränkungen und Inhaltsbeschränkungen.

**Strukturelle Einschränkungen** konzentrieren sich darauf, wie der Text organisiert und visuell angeordnet ist. Zum Beispiel:
- `setLineCountLimit(int maxLines)` beschränkt die Anzahl der im Textbereich erlaubten Zeilen.
- `setParagraphLengthLimit(int maxCharsPerLine)` begrenzt die Anzahl der Zeichen pro Absatz (oder Zeile) und hilft, Lesbarkeit oder Formatierungsstandards durchzusetzen.

**Inhaltsbeschränkungen** hingegen befassen sich mit der insgesamt eingegebenen Textmenge, unabhängig davon, wie sie verteilt ist:
- `setMaxLength(int maxChars)` begrenzt die Gesamtanzahl der über alle Absätze erlaubten Zeichen.
- `setMinLength(int minChars)` erfordert eine Mindestlänge, um sicherzustellen, dass genügend Inhalt bereitgestellt wird.

Die folgende Demo ermöglicht es Benutzern, Validierungsgrenzen - wie die maximale Zeichenzahl, Absatzlänge und Zeilenanzahl - in Echtzeit anzupassen und zu sehen, wie die `TextArea` reagiert.
	
<ComponentDemo 
path='/webforj/textareavalidation?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/textarea/TextAreaValidationView.java'
height = '550px'
/>

## Wortumbruch und Zeilenumbruch {#word-wrap-and-line-wrapping}

Sie können steuern, ob Text umbricht oder horizontal scrollt, indem Sie `setLineWrap()` verwenden. Wenn das Umbruchverhalten deaktiviert ist, verlaufen die Zeilen horizontal über den sichtbaren Bereich hinaus, was Scrollen erfordert. Wenn es aktiviert ist, umbricht der Text automatisch zur nächsten Zeile, wenn er den Rand der Komponente erreicht.

Um weiter zu verfeinern, wie der Umbruch funktioniert, lässt `setWrapStyle()` Sie zwischen zwei Stilen wählen:
- `WORD_BOUNDARIES` bricht Text an ganzen Wörtern um und bewahrt den natürlichen Lesefluss.
- `CHARACTER_BOUNDARIES` bricht an einzelnen Zeichen um und ermöglicht eine genauere Steuerung über das Layout, insbesondere in schmalen oder festbreiten Containern.

Diese Umbruchoptionen arbeiten Hand in Hand mit strukturellen Einschränkungen wie Zeilenanzahl und Absatzlängenlimits. Während der Umbruch bestimmt, *wie* Text im verfügbaren Raum fließt, definieren die strukturellen Limits, *wie viel* Raum der Text einnehmen darf. Zusammen helfen sie, sowohl die visuelle Struktur als auch die Eingabebeschränkungen für Benutzer zu wahren.

<ComponentDemo 
path='/webforj/textareawrap?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/textarea/TextAreaWrapView.java'
height = '400px'
/>

## Vorhergesagter Text {#predicted-text}

Die `TextArea`-Komponente unterstützt intelligente Textvorschläge, um Benutzern zu helfen, schneller und fehlerfreier zu tippen. Wenn Benutzer Text eingeben, erscheinen vorausschauende Vorschläge basierend auf der aktuellen Eingabe, die es ihnen ermöglichen, gebräuchliche oder erwartete Phrasen zu vervollständigen.

Vorhersagen können durch Drücken der `Tab`- oder `Pfeil-Rechts`-Taste akzeptiert werden, wodurch der vorgeschlagene Text nahtlos in die Eingabe eingefügt wird. Wenn zu einem bestimmten Zeitpunkt keine geeignete Vorhersage verfügbar ist, bleibt die Eingabe unverändert und der Benutzer kann ohne Unterbrechung weiter tippen - wodurch sichergestellt wird, dass die Funktion niemals im Weg steht.

Dieses vorausschauende Verhalten steigert sowohl die Geschwindigkeit als auch die Genauigkeit, insbesondere in repetitiven Eingabeszenarien oder Anwendungen, bei denen Konsistenz der Formulierungen wichtig ist.

<ComponentDemo 
path='/webforj/textareapredictedtext?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/textarea/TextAreaPredictedTextView.java'
height = '400px'
/>

:::info
Diese Demo verwendet die [Datamuse API](https://datamuse.com/), um Wortvorschläge basierend auf der Eingabe des Benutzers bereitzustellen. Die Qualität und Relevanz der Vorhersagen hängt vollständig von den Datensätzen und dem Bewertungssystem der API ab. Es werden keine KI-Modelle oder große Sprachmodelle (LLMs) verwendet; die Vorschläge werden von einer leichtgewichtigen, regelbasierten Engine generiert, die sich auf lexikalische Ähnlichkeit konzentriert.
:::

## Nur-Lesen- und Deaktivierungszustand {#read-only-and-disabled-state}

Die `TextArea`-Komponente kann auf entweder nur-lesend oder deaktiviert gesetzt werden, um die Benutzerinteraktion zu steuern.

Ein **nur-lesender** Textbereich erlaubt es Benutzern, den Inhalt anzuzeigen und auszuwählen, jedoch nicht zu bearbeiten. Dies ist nützlich, um dynamische oder vorausgefüllte Informationen anzuzeigen, die unverändert bleiben sollen.

Ein **deaktivierter** Textbereich hingegen blockiert jegliche Interaktion - einschließlich Fokus und Textauswahl - und wird typischerweise als inaktiv oder ausgegraut dargestellt.

Verwenden Sie den Nur-Lesen-Modus, wenn der Inhalt relevant, aber unveränderlich ist, und den Deaktivierten-Modus, wenn die Eingabe derzeit nicht anwendbar ist oder vorübergehend inaktiv sein sollte.

<ComponentDemo 
path='/webforj/textareastates?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/textarea/TextAreaStatesView.java'
height = '300px'
/>

## Stil {#styling}

<TableBuilder name="TextArea" />
