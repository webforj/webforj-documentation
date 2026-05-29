---
title: TextArea
sidebar_position: 130
_i18n_hash: 5e61ae2b47786f23e6f1f6eba317ed54
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-textarea" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/component/field/TextArea" top='true'/>

Das `TextArea`-Komponente bietet ein mehrzeiliges Texteingabefeld, in dem Benutzer längere Textblöcke eingeben und bearbeiten können. Es unterstützt maximale Zeichengrenzen, Absatzstruktur, Zeilenumbruch und Validierungsregeln, um zu steuern, wie Eingaben verarbeitet werden.

<!-- INTRO_END -->

## Erstellung eines `TextArea` {#creating-a-textarea}

Erstellen Sie ein `TextArea`, indem Sie einen Label an seinen Konstruktor übergeben. Eigenschaften wie Platzhaltertext, Zeichengrenzen und Umbruchverhalten können über Setter-Methoden konfiguriert werden.

<ComponentDemo
path='/webforj/textarea'
files={['src/main/java/com/webforj/samples/views/textarea/TextAreaView.java']}
height='300px'
/>

## Verwaltung von Absätzen {#managing-paragraphs}

Die `TextArea`-Komponente bietet Funktionen zur Handhabung von Textabsätzen, was sie ideal für Anwendungen macht, die Dokumentbearbeitung oder strukturierte Texteingaben erfordern.

Hier ist ein schnelles Beispiel, wie man Inhalte von Absätzen aufbauen und manipulieren kann:

```java
TextArea textArea = new TextArea();

// Fügen Sie einen Absatz am Anfang ein
textArea.addParagraph(0, "Dies ist der erste Absatz.");

// Fügen Sie einen weiteren Absatz ans Ende an
textArea.addParagraph("Hier ist ein zweiter Absatz.");

// Fügen Sie zusätzlichen Inhalt zum ersten Absatz hinzu
textArea.appendToParagraph(0, " Dieser Satz setzt den ersten fort.");

// Entfernen Sie den zweiten Absatz
textArea.removeParagraph(1);

// Alle aktuellen Absätze abrufen und ausgeben
List<String> paragraphs = textArea.getParagraphs();
for (int i = 0; i < paragraphs.size(); i++) {
  System.out.println("Absatz " + i + ": " + paragraphs.get(i));
}
```

## Validierung {#validation}

Die `TextArea`-Komponente unterstützt zwei komplementäre Arten von Validierung: strukturelle Beschränkungen und Inhaltsbeschränkungen.

**Strukturelle Beschränkungen** konzentrieren sich darauf, wie der Text organisiert und visuell angeordnet ist. Zum Beispiel:
- `setLineCountLimit(int maxLines)` beschränkt die Anzahl der in dem Textbereich erlaubten Zeilen.
- `setParagraphLengthLimit(int maxCharsPerLine)` limitiert die Anzahl der Zeichen pro Absatz (oder Zeile), um Lesbarkeit oder Formatierungsstandards sicherzustellen.

**Inhaltsbeschränkungen** hingegen befassen sich mit der Gesamtmenge an eingegebenem Text, unabhängig davon, wie sie verteilt ist:
- `setMaxLength(int maxChars)` begrenzt die Gesamtzahl der über alle Absätze erlaubten Zeichen.
- `setMinLength(int minChars)` setzt eine Mindestlänge fest, um sicherzustellen, dass genügend Inhalt bereitgestellt wird.

Die folgende Demo ermöglicht es Benutzern, Validierungsgrenzen—wie maximale Zeichenzahl, Absatzlänge und Zeilenanzahl—in Echtzeit anzupassen und zu sehen, wie die `TextArea` reagiert.
	
<ComponentDemo
path='/webforj/textareavalidation'
files={['src/main/java/com/webforj/samples/views/textarea/TextAreaValidationView.java']}
height='550px'
/>

## Wortumbruch und Zeilenumbruch {#word-wrap-and-line-wrapping}

Sie können steuern, ob Text umbricht oder horizontal scrollt, indem Sie `setLineWrap()` verwenden. Wenn der Umbruch deaktiviert ist, verlaufen die Zeilen horizontal über den sichtbaren Bereich hinaus, was Scrollen erfordert. Wenn er aktiviert ist, bricht der Text automatisch in die nächste Zeile um, wenn er den Rand der Komponente erreicht.

Um weiter zu verfeinern, wie der Umbruch funktioniert, ermöglicht `setWrapStyle()` die Auswahl zwischen zwei Stilen:
- `WORD_BOUNDARIES` bricht den Text an ganzen Wörtern um, um den natürlichen Lesefluss zu erhalten.
- `CHARACTER_BOUNDARIES` bricht an einzelnen Zeichen um, wodurch eine genauere Kontrolle über das Layout möglich ist, insbesondere in engen oder festbreiten Containern.

Diese Umbruchoptionen arbeiten Hand in Hand mit strukturellen Beschränkungen wie Zeilenanzahl- und Absatzlängenlimits. Während der Umbruch bestimmt, *wie* Text innerhalb des verfügbaren Platzes fließt, definieren die strukturellen Limits, *wie viel* Platz der Text einnehmen darf. Gemeinsam helfen sie, sowohl die visuelle Struktur als auch die Eingabebereiche des Benutzers aufrechtzuerhalten.

<ComponentDemo
path='/webforj/textareawrap'
files={['src/main/java/com/webforj/samples/views/textarea/TextAreaWrapView.java']}
height='400px'
/>

## Vorhergesagter Text {#predicted-text}

Die `TextArea`-Komponente unterstützt intelligente Textvorschläge, um Benutzern zu helfen, schneller und mit weniger Fehlern zu tippen. Während Benutzer Text eingeben, erscheinen vorausschauende Vorschläge basierend auf der aktuellen Eingabe, sodass sie gängige oder erwartete Phrasen vervollständigen können.

Vorhersagen können angenommen werden, indem die `Tab`- oder `Pfeil-Rechts`-Taste gedrückt wird, wodurch der vorgeschlagene Text nahtlos in die Eingabe eingefügt wird. Wenn zu einem bestimmten Zeitpunkt keine geeignete Vorhersage verfügbar ist, bleibt die Eingabe unverändert, und der Benutzer kann ohne Unterbrechung weiter tippen—was sicherstellt, dass die Funktion nie im Weg steht.

Dieses vorhersagende Verhalten verbessert sowohl die Geschwindigkeit als auch die Genauigkeit, insbesondere in Situationen mit wiederholten Eingaben oder Anwendungen, bei denen Konsistenz der Formulierungen wichtig ist.

<ComponentDemo
path='/webforj/textareapredictedtext'
files={[
  'src/main/java/com/webforj/samples/views/textarea/TextAreaPredictedTextView.java',
  'src/main/resources/static/css/textarea/text-area-predicted-text-view.css',
]}
height='400px'
/>

:::info
Diese Demo verwendet die [Datamuse API](https://datamuse.com/), um auf der Grundlage der Eingabe des Benutzers Wortvorschläge bereitzustellen. Die Qualität und Relevanz der Vorhersagen hängen vollständig von dem Datensatz und dem Scoring-Mechanismus der API ab. Es werden keine KI-Modelle oder großen Sprachmodelle (LLMs) verwendet; die Vorschläge werden von einer leichten, regelbasierten Engine generiert, die sich auf lexikalische Ähnlichkeit konzentriert.
:::

## Nur-Lesen- und Deaktivierter Zustand {#read-only-and-disabled-state}

Die `TextArea`-Komponente kann entweder auf Nur-Lesen oder Deaktiviert gesetzt werden, um die Benutzerinteraktion zu steuern.

Ein **Nur-Lesen**-Textbereich ermöglicht es Benutzern, den Inhalt zu sehen und auszuwählen, aber nicht zu bearbeiten. Dies ist nützlich, um dynamische oder vorausgefüllte Informationen anzuzeigen, die unverändert bleiben sollten.

Ein **deaktivierter** Textbereich hingegen blockiert alle Interaktionen—einschließlich Fokus und Textauswahl—und wird typischerweise als inaktiv oder ausgegraut dargestellt.

Verwenden Sie den Nur-Lesen-Modus, wenn der Inhalt relevant, aber unveränderlich ist, und den deaktivierten Modus, wenn die Eingabe derzeit nicht anwendbar ist oder vorübergehend inaktiv sein sollte.

<ComponentDemo
path='/webforj/textareastates'
files={['src/main/java/com/webforj/samples/views/textarea/TextAreaStatesView.java']}
height='300px'
/>

## Stil {#styling}

<TableBuilder name="TextArea" />
