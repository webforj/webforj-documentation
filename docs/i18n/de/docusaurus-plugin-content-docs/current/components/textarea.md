---
title: TextArea
sidebar_position: 130
_i18n_hash: 0ca8e9c1163e55bb86adf44931de139a
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-textarea" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/component/field/TextArea" top='true'/>

Die `TextArea`-Komponente in webforJ bietet eine Lösung für die Eingabe mehrzeiliger Texte. Endbenutzer können frei Text eingeben und bearbeiten, während Entwickler vernünftige Grenzen mit Funktionen wie maximalen Zeichenzahlen, Absatzstrukturen und Validierungsregeln festlegen können.

Hier ist ein Beispiel für eine `TextArea` zur Eingabe mehrzeiliger Texte:

<ComponentDemo 
path='/webforj/textarea?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/textarea/TextAreaView.java'
height = '300px'
/>

## Verwaltung von Absätzen {#managing-paragraphs}

Die `TextArea`-Komponente bietet Funktionen zur Handhabung von Textabsätzen und ist damit ideal für Anwendungen, die Dokumentenbearbeitung oder strukturierte Texteingaben erfordern.

Hier ist ein schnelles Beispiel, wie man Absatzinhalte aufbauen und bearbeiten kann:

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

// Holen Sie sich alle aktuellen Absätze und drucken Sie sie aus
List<String> paragraphs = textArea.getParagraphs();
for (int i = 0; i < paragraphs.size(); i++) {
    System.out.println("Absatz " + i + ": " + paragraphs.get(i));
}
```

## Validierung {#validation}

Die `TextArea`-Komponente unterstützt zwei komplementäre Arten von Validierungen: strukturelle Einschränkungen und Inhaltseinschränkungen.

**Strukturelle Einschränkungen** konzentrieren sich darauf, wie der Text organisiert und visuell angeordnet ist. Zum Beispiel:
- `setLineCountLimit(int maxLines)` beschränkt die Anzahl der Zeilen, die im Textbereich erlaubt sind.
- `setParagraphLengthLimit(int maxCharsPerLine)` begrenzt die Anzahl der Zeichen pro Absatz (oder Zeile) und hilft, Lesbarkeit oder Formatierungsstandards durchzusetzen.

**Inhaltseinschränkungen** hingegen betreffen die Gesamtmenge des eingegebenen Textes, unabhängig davon, wie er verteilt ist:
- `setMaxLength(int maxChars)` begrenzt die Gesamtzahl der Zeichen, die über alle Absätze erlaubt sind.
- `setMinLength(int minChars)` setzt eine Mindestlänge und stellt sicher, dass genügend Inhalt bereitgestellt wird.

Die folgende Demo ermöglicht den Benutzern, die Validierungsgrenzen—wie maximale Zeichenzahl, Absatzlänge und Zeilenzahl— in Echtzeit anzupassen und zu sehen, wie die `TextArea` reagiert.

<ComponentDemo 
path='/webforj/textareavalidation?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/textarea/TextAreaValidationView.java'
height = '550px'
/>

## Wortumbruch und Zeilenumbruch {#word-wrap-and-line-wrapping}

Sie können steuern, ob Text umbricht oder horizontal scrollt, indem Sie `setLineWrap()` verwenden. Wenn das Umbruchverhalten deaktiviert ist, verlaufen die Zeilen horizontal über den sichtbaren Bereich hinaus, was Scrollen erforderlich macht. Wenn es aktiviert ist, wird der Text automatisch in die nächste Zeile umbrochen, wenn er den Rand der Komponente erreicht.

Um weiter zu verfeinern, wie das Umbruchverhalten funktioniert, ermöglicht `setWrapStyle()` die Auswahl zwischen zwei Stilen:
- `WORD_BOUNDARIES` bricht den Text an ganzen Wörtern um, wodurch der natürliche Lesefluss erhalten bleibt.
- `CHARACTER_BOUNDARIES` bricht an einzelnen Zeichen um, was eine engere Kontrolle über das Layout ermöglicht, insbesondere in schmalen oder festgelegten Containern.

Diese Umbruchoptionen arbeiten Hand in Hand mit strukturellen Einschränkungen wie Zeilenanzahl und Absatzlängenbegrenzungen. Während der Umbruch bestimmt, *wie* sich der Text im verfügbaren Raum bewegt, definieren die strukturellen Grenzen, *wie viel* Raum der Text einnehmen darf. Zusammen helfen sie, sowohl die visuelle Struktur als auch die Grenzen der Benutzereingabe aufrechtzuerhalten.

<ComponentDemo 
path='/webforj/textareawrap?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/textarea/TextAreaWrapView.java'
height = '400px'
/>

## Vorhergesagter Text {#predicted-text}

Die `TextArea`-Komponente unterstützt intelligente Textvorschläge, um Benutzern das schneller Tippen und das Minimieren von Fehlern zu erleichtern. Wenn Benutzer Text eingeben, erscheinen voraussichtliche Vorschläge basierend auf der aktuellen Eingabe, sodass sie häufige oder erwartete Phrasen vervollständigen können.

Vorhersagen können durch Drücken der `Tab`- oder `Pfeilrechts`-Taste akzeptiert werden, wobei der vorgeschlagene Text nahtlos in die Eingabe eingefügt wird. Wenn zu einem bestimmten Zeitpunkt keine geeignete Vorhersage verfügbar ist, bleibt die Eingabe unverändert und der Benutzer kann ohne Unterbrechung weiter tippen—was sicherstellt, dass die Funktion niemals im Weg steht.

Dieses Vorhersageverhalten verbessert sowohl die Geschwindigkeit als auch die Genauigkeit, insbesondere in wiederholenden Eingabeszenarien oder Anwendungen, in denen Konsistenz der Formulierungen wichtig ist.

<ComponentDemo 
path='/webforj/textareapredictedtext?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/textarea/TextAreaPredictedTextView.java'
height = '400px'
/>

:::info
Diese Demo verwendet die [Datamuse API](https://datamuse.com/), um Wortvorschläge basierend auf der Eingabe des Benutzers bereitzustellen. Die Qualität und Relevanz der Vorhersagen hängen vollständig von den Datensätzen und dem Bewertungssystem der API ab. Es werden keine KI-Modelle oder großen Sprachmodelle (LLMs) verwendet; die Vorschläge werden von einer leichtgewichtigen, regelbasierten Engine generiert, die sich auf lexikalische Ähnlichkeit konzentriert.
:::

## Nur-Lesen- und Deaktiviert-Zustand {#read-only-and-disabled-state}

Die `TextArea`-Komponente kann entweder auf Nur-Lesen oder deaktiviert gesetzt werden, um die Benutzerinteraktion zu steuern.

Ein **Nur-Lesen**-Textbereich ermöglicht es Benutzern, den Inhalt anzuzeigen und auszuwählen, aber nicht zu bearbeiten. Dies ist nützlich, um dynamische oder vorausgefüllte Informationen anzuzeigen, die unverändert bleiben sollen.

Ein **deaktivierter** Textbereich hingegen blockiert alle Interaktionen—einschließlich Fokus und Textauswahl—und wird typischerweise als inaktiv oder ausgegraut gestaltet.

Verwenden Sie den Nur-Lesen-Modus, wenn der Inhalt relevant, aber unveränderlich ist, und den Deaktiviert-Modus, wenn die Eingabe derzeit nicht anwendbar ist oder vorübergehend inaktiv sein sollte.

<ComponentDemo 
path='/webforj/textareastates?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/textarea/TextAreaStatesView.java'
height = '300px'
/>

## Styling {#styling}

<TableBuilder name="TextArea" />
