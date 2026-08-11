---
title: TextArea
sidebar_position: 130
description: >-
  Capture multi-line input with the TextArea component, including paragraph
  management, character limits, wrapping, and validation.
_i18n_hash: f9863352a124e1af3575a849204b97ed
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-textarea" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/component/field/TextArea" top='true'/>

Die `TextArea`-Komponente bietet ein mehrzeiliges Texteingabefeld, in das Benutzer längere Textblöcke eingeben und bearbeiten können. Sie unterstützt maximale Zeichenbeschränkungen, Absatzstrukturen, Zeilenumbruch und Validierungsregeln, um zu steuern, wie Eingaben behandelt werden.

<!-- INTRO_END -->

## Erstellen eines `TextArea` {#creating-a-textarea}

Erstellen Sie ein `TextArea`, indem Sie ein Label an seinen Konstruktor übergeben. Eigenschaften wie Platzhaltertext, Zeichenbeschränkungen und Umbruchverhalten können über Settermethoden konfiguriert werden.

<ComponentDemo
path='/webforj/textarea'
files={['src/main/java/com/webforj/samples/views/textarea/TextAreaView.java']}
height='300px'
/>

## Verwaltung von Absätzen {#managing-paragraphs}

Die `TextArea`-Komponente bietet Funktionen zur Handhabung von Textabsätzen, was sie ideal für Anwendungen macht, die Dokumentenbearbeitung oder strukturierte Texteingabe erfordern.

Hier ist ein kurzes Beispiel, wie man Absatzinhalte erstellen und manipulieren kann:

```java
TextArea textArea = new TextArea();

// Fügen Sie einen Absatz am Anfang hinzu
textArea.addParagraph(0, "Dies ist der erste Absatz.");

// Fügen Sie einen weiteren Absatz am Ende hinzu
textArea.addParagraph("Hier ist ein zweiter Absatz.");

// Fügen Sie zusätzlichen Inhalt zum ersten Absatz hinzu
textArea.appendToParagraph(0, " Dieser Satz setzt den ersten fort.");

// Entfernen Sie den zweiten Absatz
textArea.removeParagraph(1);

// Alle aktuellen Absätze abrufen und ausdrucken
List<String> paragraphs = textArea.getParagraphs();
for (int i = 0; i < paragraphs.size(); i++) {
  System.out.println("Absatz " + i + ": " + paragraphs.get(i));
}
```

## Validierung {#validation}

Die `TextArea`-Komponente unterstützt zwei ergänzende Arten von Validierung: strukturelle Einschränkungen und Inhaltsbeschränkungen.

**Strukturelle Einschränkungen** konzentrieren sich darauf, wie der Text organisiert und visuell angeordnet ist. Zum Beispiel:
- `setLineCountLimit(int maxLines)` beschränkt die Anzahl der zulässigen Zeilen im Textbereich.
- `setParagraphLengthLimit(int maxCharsPerLine)` begrenzt die Anzahl der Zeichen pro Absatz (oder Zeile) und hilft, Lesbarkeit oder Formatierungsstandards durchzusetzen.

**Inhaltsbeschränkungen** hingegen befassen sich mit der insgesamt eingegebenen Textmenge, unabhängig davon, wie sie verteilt ist:
- `setMaxLength(int maxChars)` begrenzt die Gesamtanzahl der in allen Absätzen zulässigen Zeichen.
- `setMinLength(int minChars)` setzt eine Mindestlänge fest, um sicherzustellen, dass genügend Inhalt bereitgestellt wird.

Die folgende Demo ermöglicht es Benutzern, Validierungslimits—wie maximale Zeichenanzahl, Absatzlänge und Zeilenanzahl—in Echtzeit anzupassen und zu sehen, wie das `TextArea` darauf reagiert.

<ComponentDemo
path='/webforj/textareavalidation'
files={['src/main/java/com/webforj/samples/views/textarea/TextAreaValidationView.java']}
height='550px'
/>

## Wortumbruch und Zeilenumbruch {#word-wrap-and-line-wrapping}

Sie können steuern, ob Text umbrochen oder horizontal gescrollt wird, indem Sie `setLineWrap()` verwenden. Wenn der Umbruch deaktiviert ist, verlaufen die Zeilen horizontal über den sichtbaren Bereich hinaus, was Scrollen erforderlich macht. Wenn aktiviert, wird der Text automatisch umgebrochen, wenn er den Rand der Komponente erreicht.

Um weiter zu verfeinern, wie der Umbruch gehandhabt wird, lässt `setWrapStyle()` die Wahl zwischen zwei Stilen:
- `WORD_BOUNDARIES` bricht den Text an ganzen Wörtern um und erhält einen natürlichen Lesefluss.
- `CHARACTER_BOUNDARIES` bricht an einzelnen Zeichen um und ermöglicht eine engere Kontrolle über das Layout, insbesondere in schmalen oder festbreiten Containern.

Diese Umbruchoptionen funktionieren Hand in Hand mit strukturellen Einschränkungen wie Zeilenanzahl und Absatzlängenbegrenzungen. Während der Umbruch bestimmt, *wie* Text innerhalb des verfügbaren Raums fließt, definieren die strukturellen Limits, *wie viel* Raum der Text einnehmen darf. Gemeinsam helfen sie, sowohl die visuelle Struktur als auch die Grenzen der Benutzereingabe aufrechtzuerhalten.

<ComponentDemo
path='/webforj/textareawrap'
files={['src/main/java/com/webforj/samples/views/textarea/TextAreaWrapView.java']}
height='400px'
/>

## Vorhergesagter Text {#predicted-text}

Die `TextArea`-Komponente unterstützt intelligente Textvorschläge, um Benutzern zu helfen, schneller und mit weniger Fehlern zu tippen. Wenn Benutzer Text eingeben, erscheinen vorhersagende Vorschläge basierend auf der aktuellen Eingabe, die es ihnen ermöglichen, gängige oder erwartete Phrasen zu vervollständigen.

Vorhersagen können durch Drücken der `Tab`- oder `ArrowRight`-Taste akzeptiert werden, wodurch der vorgeschlagene Text nahtlos in die Eingabe eingefügt wird. Wenn zu einem bestimmten Zeitpunkt keine geeignete Vorhersage verfügbar ist, bleibt die Eingabe unverändert, und der Benutzer kann ungestört weiter tippen—was sicherstellt, dass die Funktion nicht im Weg steht.

Dieses prädiktive Verhalten verbessert sowohl die Geschwindigkeit als auch die Genauigkeit, insbesondere in Situationen mit wiederholten Eingaben oder Anwendungen, bei denen Konsistenz in der Formulierung wichtig ist.

<ComponentDemo
path='/webforj/textareapredictedtext'
files={[
  'src/main/java/com/webforj/samples/views/textarea/TextAreaPredictedTextView.java',
  'src/main/frontend/css/textarea/text-area-predicted-text-view.css',
]}
height='400px'
/>

:::info
Diese Demo verwendet die [Datamuse API](https://datamuse.com/), um Wortvorschläge basierend auf der Eingabe des Benutzers bereitzustellen. Die Qualität und Relevanz der Vorhersagen hängen vollständig von den Datensätzen und dem Bewertungssystem der API ab. Sie verwendet keine KI-Modelle oder großen Sprachmodelle (LLMs); die Vorschläge werden von einer leichten, regelbasierten Engine erzeugt, die sich auf lexikalische Ähnlichkeit konzentriert.
:::

## Nur-Lesen- und Deaktiviert-Zustand {#read-only-and-disabled-state}

Die `TextArea`-Komponente kann entweder auf schreibgeschützt oder deaktiviert gesetzt werden, um die Benutzerinteraktion zu steuern.

Ein **schreibgeschützter** Textbereich erlaubt es Benutzern, den Inhalt anzusehen und zu wählen, aber nicht zu bearbeiten. Dies ist nützlich, um dynamische oder vorausgefüllte Informationen anzuzeigen, die unverändert bleiben sollten.

Ein **deaktivierter** Textbereich hingegen blockiert alle Interaktionen—einschließlich Fokus und Texteingabe—und wird typischerweise als inaktiv oder ausgeblendet dargestellt.

Verwenden Sie den schreibgeschützten Modus, wenn der Inhalt relevant, aber unveränderlich ist, und den deaktivierten Modus, wenn die Eingabe derzeit nicht anwendbar ist oder vorübergehend inaktiv sein sollte.

<ComponentDemo
path='/webforj/textareastates'
files={['src/main/java/com/webforj/samples/views/textarea/TextAreaStatesView.java']}
height='300px'
/>

## Gestaltung {#styling}

<TableBuilder name="TextArea" />
