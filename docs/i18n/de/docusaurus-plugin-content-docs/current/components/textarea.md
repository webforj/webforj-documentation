---
title: TextArea
sidebar_position: 130
_i18n_hash: 423b70520e8f64a463d2c7b1d0e35ddc
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-textarea" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/component/field/TextArea" top='true'/>

Der `TextArea`-Komponente bietet ein mehrzeiliges Texteingabefeld, in dem Benutzer längere Textblöcke eingeben und bearbeiten können. Es unterstützt maximale Zeichenlimits, Absatzstruktur, Zeilenumbruch und Validierungsregeln, um zu steuern, wie Eingaben verarbeitet werden.

<!-- INTRO_END -->

## Erstellung eines `TextArea` {#creating-a-textarea}

Erstellen Sie ein `TextArea`, indem Sie ein Label an seinen Konstruktor übergeben. Eigenschaften wie Platzhaltertext, Zeichenbeschränkungen und Umbruchverhalten können über Setter-Methoden konfiguriert werden.

<ComponentDemo 
path='/webforj/textarea?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/textarea/TextAreaView.java'
height = '300px'
/>

## Verwaltung von Absätzen {#managing-paragraphs}

Die `TextArea`-Komponente bietet Funktionen zur Handhabung von Textabsätzen, wodurch sie ideal für Anwendungen ist, die Dokumentbearbeitung oder strukturierte Texteingabe erfordern.

Hier ist ein kurzes Beispiel, wie Inhalte für Absätze erstellt und bearbeitet werden können:

```java
TextArea textArea = new TextArea();

// Ein Absatz am Anfang einfügen
textArea.addParagraph(0, "Dies ist der erste Absatz.");

// Einen weiteren Absatz am Ende anhängen
textArea.addParagraph("Hier ist ein zweiter Absatz.");

// Zusätzlichen Inhalt zum ersten Absatz hinzufügen
textArea.appendToParagraph(0, " Dieser Satz setzt den ersten fort.");

// Den zweiten Absatz entfernen
textArea.removeParagraph(1);

// Alle aktuellen Absätze abrufen und ausgeben
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

**Inhaltsbeschränkungen** hingegen betreffen die Gesamtmenge des eingegebenen Textes, unabhängig davon, wie er verteilt ist:
- `setMaxLength(int maxChars)` begrenzt die Gesamtanzahl der Zeichen, die über alle Absätze hinweg erlaubt sind.
- `setMinLength(int minChars)` legt eine Mindestlänge fest und stellt sicher, dass genügend Inhalt bereitgestellt wird.

Die folgende Demo ermöglicht es Benutzern, die Validierungsgrenzen—wie die maximale Zeichenanzahl, Absatzzahl und Zeilenanzahl—in Echtzeit anzupassen und zu sehen, wie die `TextArea` darauf reagiert.

<ComponentDemo 
path='/webforj/textareavalidation?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/textarea/TextAreaValidationView.java'
height = '550px'
/>

## Wort- und Zeilenumbruch {#word-wrap-and-line-wrapping}

Sie können steuern, ob der Text umbricht oder horizontal scrollt, indem Sie `setLineWrap()` verwenden. Wenn das Umbrichen deaktiviert ist, setzen sich die Zeilen horizontal über den sichtbaren Bereich hinaus fort, was Scrollen erfordert. Wenn es aktiviert ist, bricht der Text automatisch zur nächsten Zeile um, wenn er den Rand der Komponente erreicht.

Um weiter zu verfeinern, wie der Umbruch funktioniert, ermöglicht `setWrapStyle()` die Wahl zwischen zwei Stilen:
- `WORD_BOUNDARIES` bricht den Text an ganzen Wörtern um und bewahrt den natürlichen Lesefluss.
- `CHARACTER_BOUNDARIES` bricht an einzelnen Zeichen um und ermöglicht eine genauere Kontrolle über das Layout, insbesondere in schmalen oder festen Breite-Containern.

Diese Umbruchoptionen arbeiten Hand in Hand mit strukturellen Einschränkungen wie Zeilenanzahl und Absatzlängenbeschränkungen. Während der Umbruch bestimmt, *wie* der Text im verfügbaren Raum fließt, definieren die strukturellen Limits, *wie viel* Raum der Text beanspruchen darf. Gemeinsam helfen sie, sowohl die visuelle Struktur als auch die Eingabebeschränkungen der Benutzer aufrechtzuerhalten.

<ComponentDemo 
path='/webforj/textareawrap?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/textarea/TextAreaWrapView.java'
height = '400px'
/>

## Vorhergesagter Text {#predicted-text}

Die `TextArea`-Komponente unterstützt intelligente Textvorschläge, um Benutzern zu helfen, schneller und mit weniger Fehlern zu tippen. Während die Benutzer Text eingeben, erscheinen prädiktive Vorschläge basierend auf der aktuellen Eingabe, die es ihnen ermöglichen, gängige oder erwartete Phrasen zu vervollständigen.

Prognosen können durch Drücken der Tasten `Tab` oder `Pfeil Rechts` akzeptiert werden, wodurch der vorgeschlagene Text nahtlos in die Eingabe eingefügt wird. Wenn zu einem bestimmten Zeitpunkt keine geeignete Vorhersage verfügbar ist, bleibt die Eingabe unverändert und der Benutzer kann ohne Unterbrechung weiter tippen – was sicherstellt, dass die Funktion niemals im Weg steht.

Dieses prädiktive Verhalten verbessert sowohl die Geschwindigkeit als auch die Genauigkeit, insbesondere in wiederholenden Eingabeszenarien oder Anwendungen, in denen Konsistenz der Formulierungen wichtig ist.

<ComponentDemo 
path='/webforj/textareapredictedtext?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/textarea/TextAreaPredictedTextView.java'
height = '400px'
/>

:::info
Diese Demo verwendet die [Datamuse API](https://datamuse.com/), um Wortvorschläge basierend auf der Eingabe des Benutzers bereitzustellen. Die Qualität und Relevanz der Vorhersagen hängen vollständig vom Datensatz und dem Bewertungssystem der API ab. Es werden keine KI-Modelle oder großen Sprachmodelle (LLMs) verwendet; die Vorschläge werden von einer leichten, regelbasierten Engine generiert, die sich auf lexikalische Ähnlichkeit konzentriert.
:::

## Nur-Lesen- und Deaktivierter Zustand {#read-only-and-disabled-state}

Die `TextArea`-Komponente kann entweder auf Nur-Lesen oder Deaktiviert gesetzt werden, um die Benutzerinteraktion zu steuern.

Ein **Nur-Lesen**-Textbereich ermöglicht es Benutzern, den Inhalt anzuzeigen und auszuwählen, jedoch nicht zu bearbeiten. Dies ist nützlich, um dynamische oder vorab ausgefüllte Informationen anzuzeigen, die unverändert bleiben sollten.

Ein **deaktivierter** Textbereich hingegen blockiert jegliche Interaktion – einschließlich Fokus und Textauswahl – und wird normalerweise als inaktiv oder ausgegraut dargestellt.

Verwenden Sie den Nur-Lesen-Modus, wenn der Inhalt relevant, aber unveränderlich ist, und den deaktivierten Modus, wenn die Eingabe derzeit nicht anwendbar ist oder vorübergehend inaktiv sein sollte.

<ComponentDemo 
path='/webforj/textareastates?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/textarea/TextAreaStatesView.java'
height = '300px'
/>

## Styling {#styling}

<TableBuilder name="TextArea" />
