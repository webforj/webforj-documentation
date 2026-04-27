---
title: TextArea
sidebar_position: 130
_i18n_hash: e8956f1a5bf39eab9a42244ff8d5ff21
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-textarea" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/component/field/TextArea" top='true'/>

Die `TextArea`-Komponente bietet ein mehrzeiliges Texteingabefeld, in das Benutzer längere Textblöcke eingeben und bearbeiten können. Sie unterstützt maximale Zeichengrenzen, Absatzstruktur, Zeilenumbruch und Validierungsregeln, um zu steuern, wie Eingaben verarbeitet werden.

<!-- INTRO_END -->

## Erstellung eines `TextArea` {#creating-a-textarea}

Erstellen Sie ein `TextArea`, indem Sie ein Label an seinen Konstruktor übergeben. Eigenschaften wie Platzhaltertext, Zeichengrenzen und Umbruchverhalten können über Setter-Methoden konfiguriert werden.

<ComponentDemo 
path='/webforj/textarea?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/textarea/TextAreaView.java'
height = '300px'
/>

## Verwaltung von Absätzen {#managing-paragraphs}

Die `TextArea`-Komponente bietet Funktionen zur Handhabung von Textabsätzen, was sie ideal für Anwendungen macht, die Dokumentbearbeitung oder strukturierte Texteingaben erfordern.

Hier ist ein kurzes Beispiel, wie man Absatzinhalte aufbauen und manipulieren kann:

```java
TextArea textArea = new TextArea();

// Fügen Sie einen Absatz am Anfang ein
textArea.addParagraph(0, "Dies ist der erste Absatz.");

// Fügen Sie einen weiteren Absatz am Ende hinzu
textArea.addParagraph("Hier ist ein zweiter Absatz.");

// Fügen Sie zusätzlichen Inhalt zum ersten Absatz hinzu
textArea.appendToParagraph(0, " Dieser Satz setzt den ersten fort.");

// Entfernen Sie den zweiten Absatz
textArea.removeParagraph(1);

// Abrufen und Ausdrucken aller aktuellen Absätze
List<String> paragraphs = textArea.getParagraphs();
for (int i = 0; i < paragraphs.size(); i++) {
  System.out.println("Absatz " + i + ": " + paragraphs.get(i));
}
```

## Validierung {#validation}

Die `TextArea`-Komponente unterstützt zwei komplementäre Arten der Validierung: strukturelle Einschränkungen und Inhaltsbeschränkungen.

**Strukturelle Einschränkungen** konzentrieren sich darauf, wie der Text organisiert und visuell angeordnet ist. Zum Beispiel:
- `setLineCountLimit(int maxLines)` beschränkt die Anzahl der in das Textfeld erlaubten Zeilen.
- `setParagraphLengthLimit(int maxCharsPerLine)` beschränkt die Anzahl der Zeichen pro Absatz (oder Zeile) und hilft, Lesbarkeit oder Formatierungsstandards einzuhalten.

**Inhaltsbeschränkungen** hingegen behandeln die Gesamtmenge an eingegebenem Text, unabhängig davon, wie sie verteilt ist:
- `setMaxLength(int maxChars)` begrenzt die Gesamtzahl der über alle Absätze erlaubten Zeichen.
- `setMinLength(int minChars)` setzt eine Mindestlänge fest und stellt sicher, dass ausreichend Inhalt bereitgestellt wird.

Die folgende Demo ermöglicht es den Benutzern, Validierungslimits wie maximale Zeichenzahl, Absatzlänge und Zeilenanzahl in Echtzeit anzupassen und zu sehen, wie das `TextArea` darauf reagiert.

<ComponentDemo 
path='/webforj/textareavalidation?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/textarea/TextAreaValidationView.java'
height = '550px'
/>

## Wort- und Zeilenumbruch {#word-wrap-and-line-wrapping}

Sie können steuern, ob Text umbricht oder horizontal scrollt, indem Sie `setLineWrap()` verwenden. Wenn der Umbruch deaktiviert ist, setzen sich die Zeilen horizontal über den sichtbaren Bereich hinaus fort, und es ist Scrollen erforderlich. Wenn er aktiviert ist, wird der Text automatisch in die nächste Zeile umgebrochen, wenn er den Rand der Komponente erreicht.

Um weiter zu verfeinern, wie der Umbruch funktioniert, lässt `setWrapStyle()` die Wahl zwischen zwei Stilen:
- `WORD_BOUNDARIES` bricht den Text an ganzen Wörtern um und bewahrt den natürlichen Lesefluss.
- `CHARACTER_BOUNDARIES` bricht an einzelnen Zeichen um und ermöglicht eine genauere Steuerung des Layouts, insbesondere in schmalen oder festgelegten Breitenbehältern.

Diese Umbruchoptionen arbeiten Hand in Hand mit strukturellen Einschränkungen wie Zeilenanzahl und Absatzlängenlimits. Während der Umbruch bestimmt, *wie* der Text innerhalb des verfügbaren Raums fließt, definieren die strukturellen Limits, *wie viel* Platz der Text einnehmen darf. Zusammen helfen sie, sowohl die visuelle Struktur als auch die Grenzen der Benutzereingabe aufrechtzuerhalten.

<ComponentDemo 
path='/webforj/textareawrap?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/textarea/TextAreaWrapView.java'
height = '400px'
/>

## Vorhergesagter Text {#predicted-text}

Die `TextArea`-Komponente unterstützt intelligente Textvorschläge, um Benutzern zu helfen, schneller und mit weniger Fehlern zu tippen. Während Benutzer Text eingeben, erscheinen prädiktive Vorschläge basierend auf der aktuellen Eingabe, was es ihnen ermöglicht, gängige oder erwartete Phrasen zu vervollständigen.

Vorhersagen können durch Drücken der `Tab`- oder `ArrowRight`-Taste angenommen werden, wodurch der vorgeschlagene Text nahtlos in die Eingabe eingefügt wird. Wenn zu einem bestimmten Zeitpunkt keine geeignete Vorhersage vorhanden ist, bleibt die Eingabe unverändert, und der Benutzer kann ohne Unterbrechung weiter tippen – was sicherstellt, dass die Funktion niemals im Weg steht.

Dieses prädiktive Verhalten verbessert sowohl die Geschwindigkeit als auch die Genauigkeit, insbesondere in Szenarien mit wiederholter Eingabe oder Anwendungen, in denen Konsistenz in der Formulierung wichtig ist.

<ComponentDemo 
path='/webforj/textareapredictedtext?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/textarea/TextAreaPredictedTextView.java'
cssURL='/css/textarea/text-area-predicted-text-view.css'
height = '400px'
/>

:::info
Diese Demo verwendet die [Datamuse API](https://datamuse.com/), um Wortvorschläge basierend auf der Eingabe des Benutzers bereitzustellen. Die Qualität und Relevanz der Vorhersagen hängen vollständig von den Daten und dem Bewertungssystem der API ab. Sie verwendet keine KI-Modelle oder großen Sprachmodelle (LLMs); die Vorschläge werden von einer leichten, regelbasierten Engine generiert, die sich auf lexikalische Ähnlichkeit konzentriert.
:::

## Nur-Lese- und Deaktivierungsstatus {#read-only-and-disabled-state}

Die `TextArea`-Komponente kann entweder auf schreibgeschützt oder deaktiviert gesetzt werden, um die Benutzerinteraktion zu steuern.

Ein **schreibgeschützter** Textbereich ermöglicht es Benutzern, den Inhalt anzuzeigen und auszuwählen, aber nicht zu bearbeiten. Dies ist nützlich, um dynamische oder vorausgefüllte Informationen anzuzeigen, die unverändert bleiben sollen.

Ein **deaktivierter** Textbereich hingegen blockiert alle Interaktionen – einschließlich Fokussierung und Textauswahl – und wird typischerweise inaktiv oder ausgegraut dargestellt.

Verwenden Sie den schreibgeschützten Modus, wenn der Inhalt relevant, aber unveränderlich ist, und den deaktivierten Modus, wenn die Eingabe derzeit nicht anwendbar ist oder vorübergehend inaktiv sein soll.

<ComponentDemo 
path='/webforj/textareastates?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/textarea/TextAreaStatesView.java'
height = '300px'
/>

## Stilgestaltung {#styling}

<TableBuilder name="TextArea" />
