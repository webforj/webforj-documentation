---
title: TextArea
sidebar_position: 130
_i18n_hash: f109f006fcd252bf81b6cccb83d38a50
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-textarea" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/component/field/TextArea" top='true'/>

Die `TextArea`-Komponente in webforJ bietet eine Lösung für die Eingabe mehrzeiliger Texte. Endbenutzer können frei Text eingeben und bearbeiten, während Entwickler angemessene Grenzen mit Funktionen wie maximalen Zeichenlimits, Absatzstrukturen und Validierungsregeln festlegen können.

Hier ist ein Beispiel für eine `TextArea` zur Eingabe mehrzeiliger Texte:

<ComponentDemo 
path='/webforj/textarea?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/textarea/TextAreaView.java'
height = '300px'
/>

## Verwaltung von Absätzen {#managing-paragraphs}

Die `TextArea`-Komponente bietet Funktionen zum Umgang mit Textabsätzen, was sie ideal für Anwendungen macht, die Dokumentbearbeitung oder strukturierte Eingaben erfordern.

Hier ist ein kurzes Beispiel, wie man Absatzinhalte aufbauen und manipulieren kann:

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

Die `TextArea`-Komponente unterstützt zwei komplementäre Arten von Validierung: strukturelle Einschränkungen und Inhaltsbeschränkungen.

**Strukturelle Einschränkungen** konzentrieren sich darauf, wie der Text organisiert und visuell angeordnet ist. Zum Beispiel:
- `setLineCountLimit(int maxLines)` beschränkt die Anzahl der in der Textarea erlaubten Zeilen.
- `setParagraphLengthLimit(int maxCharsPerLine)` begrenzt die Anzahl der Zeichen pro Absatz (oder Zeile) und hilft, Lesbarkeit oder Formatierungsstandards durchzusetzen.

**Inhaltsbeschränkungen** hingegen befassen sich mit der gesamten Menge an eingegebenem Text, unabhängig von der Verteilung:
- `setMaxLength(int maxChars)` begrenzt die Gesamtanzahl der Zeichen, die über alle Absätze hinweg erlaubt sind.
- `setMinLength(int minChars)` setzt eine Mindestlänge fest, um sicherzustellen, dass genügend Inhalt bereitgestellt wird.

Die folgende Demo ermöglicht es den Benutzern, die Validierungsgrenzen—wie maximale Zeichenanzahl, Absatzlänge und Zeilenanzahl—in Echtzeit anzupassen und zu sehen, wie die `TextArea` reagiert.

<ComponentDemo 
path='/webforj/textareavalidation?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/textarea/TextAreaValidationView.java'
height = '550px'
/>

## Zeilenumbruch und Zeilenumbruchverhalten {#word-wrap-and-line-wrapping}

Sie können steuern, ob der Text umbricht oder horizontal scrollt, indem Sie `setLineWrap()` verwenden. Wenn das Umbrüche deaktiviert ist, setzen sich die Zeilen horizontal über den sichtbaren Bereich hinaus fort, was Scrollen erforderlich macht. Wenn es aktiviert ist, wird der Text automatisch umgebrochen, wenn er den Rand der Komponente erreicht.

Um das Umbruchverhalten weiter zu verfeinern, lässt `setWrapStyle()` Sie zwischen zwei Stilen wählen:
- `WORD_BOUNDARIES` bricht den Text an ganzen Wörtern um und bewahrt den natürlichen Lesefluss.
- `CHARACTER_BOUNDARIES` bricht an einzelnen Zeichen um, was eine strengere Kontrolle über das Layout ermöglicht, insbesondere in schmalen oder festen Behältern.

Diese Umbruchoptionen arbeiten Hand in Hand mit strukturellen Einschränkungen wie Zeilenanzahl und Absatzlängenlimits. Während das Umbruchverhalten bestimmt, *wie* Text im verfügbaren Raum fließt, definieren die strukturellen Limits, *wie viel* Raum Text einnehmen darf. Zusammen helfen sie, sowohl die visuelle Struktur als auch die Benutzergrenzen für Eingaben aufrechtzuerhalten.

<ComponentDemo 
path='/webforj/textareawrap?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/textarea/TextAreaWrapView.java'
height = '400px'
/>

## Vorhergesagter Text {#predicted-text}

Die `TextArea`-Komponente unterstützt intelligente Texteingabevorschläge, um den Benutzern zu helfen, schneller zu tippen und weniger Fehler zu machen. Während Benutzer Text eingeben, erscheinen vorhersagende Vorschläge basierend auf der aktuellen Eingabe, die es ihnen ermöglichen, gängige oder erwartete Phrasen zu vervollständigen.

Vorhersagen können angenommen werden, indem die `Tab`- oder die `Pfeil-rechts`-Taste gedrückt wird, wodurch der vorgeschlagene Text nahtlos in die Eingabe eingefügt wird. Wenn zu einem bestimmten Zeitpunkt keine geeignete Vorhersage verfügbar ist, bleibt die Eingabe unverändert, und der Benutzer kann ohne Unterbrechung weiter tippen—was sicherstellt, dass die Funktion nie im Weg steht.

Dieses vorhersagende Verhalten verbessert sowohl die Geschwindigkeit als auch die Genauigkeit, insbesondere in wiederholenden Eingabeszenarien oder Anwendungen, in denen eine Konsistenz der Formulierungen wichtig ist.

<ComponentDemo 
path='/webforj/textareapredictedtext?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/textarea/TextAreaPredictedTextView.java'
height = '400px'
/>

:::info
Diese Demo nutzt die [Datamuse API](https://datamuse.com/), um Wortvorschläge basierend auf der Eingabe des Benutzers bereitzustellen. Die Qualität und Relevanz der Vorhersagen hängen vollständig vom Datensatz und dem Bewertungssystem der API ab. Es werden keine KI-Modelle oder großen Sprachmodelle (LLMs) verwendet; die Vorschläge werden von einer leichten, regelbasierten Engine generiert, die sich auf lexikalische Ähnlichkeit konzentriert.
:::

## Nur-Lesen- und Deaktiviert-Zustand {#read-only-and-disabled-state}

Die `TextArea`-Komponente kann entweder auf Nur-Lese- oder Deaktiviert-Zustand gesetzt werden, um die Benutzerinteraktion zu steuern.

Eine **Nur-Lese**-TextArea ermöglicht es Benutzern, den Inhalt anzuzeigen und auszuwählen, aber nicht zu bearbeiten. Dies ist nützlich, um dynamische oder vorausgefüllte Informationen anzuzeigen, die unverändert bleiben sollten.

Eine **deaktivierte** TextArea blockiert hingegen alle Interaktionen - einschließlich Fokus und Textauswahl - und wird typischerweise als inaktiv oder ausgegraut dargestellt.

Verwenden Sie den Nur-Lese-Modus, wenn der Inhalt relevant, aber unveränderlich ist, und den Deaktiviert-Modus, wenn die Eingabe derzeit nicht anwendbar ist oder vorübergehend inaktiv sein sollte.

<ComponentDemo 
path='/webforj/textareastates?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/textarea/TextAreaStatesView.java'
height = '300px'
/>

## Styling {#styling}

<TableBuilder name="TextArea" />
