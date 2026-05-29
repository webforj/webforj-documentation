---
sidebar_position: 5
title: ColorField
slug: colorfield
description: >-
  A component that provides a default browser-based color picker, allowing users
  to select a color from an input field.
_i18n_hash: 50390b19b24346c878300024badc1380
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-color-chooser" />
<DocChip chip='since' label='23.02' />
<JavadocLink type="foundation" location="com/webforj/component/field/ColorField" top='true'/>

Die `ColorField`-Komponente ermöglicht es Benutzern, eine Farbe über den nativen Farbwähler des Browsers auszuwählen. Da sie auf der integrierten Implementierung des Browsers basiert, variiert ihr Erscheinungsbild je nach Browser und Plattform. Sie kann als einfaches Texteingabefeld, als farbstandardmäßiger Farbwähler oder als benutzerdefiniertes Wählerinterface angezeigt werden. Diese Variation kommt den Benutzern zugute, da die Steuerung dem entspricht, was sie bereits kennen.

<!-- INTRO_END -->

## Verwendung von `ColorField` {#using-colorfield}

<ParentLink parent="Field" />

`ColorField` erweitert die gemeinsame `Field`-Klasse, die gängige Funktionen für alle Formularfeldkomponenten bereitstellt. Das folgende Beispiel ermöglicht es dem Benutzer, eine Farbe auszuwählen und deren tetradische Komplementärfarben anzuzeigen.

<ComponentDemo
path='/webforj/colorfield'
files={[
  'src/main/java/com/webforj/samples/views/fields/colorfield/ColorFieldView.java',
  'src/main/resources/static/css/fields/colorfield/colorFieldDemo.css',
]}
height='300px'
/>

Das `ColorField` eignet sich am besten für Szenarien, in denen die Farbauswahl ein wesentlicher Bestandteil der Benutzeroberfläche oder der Anwendungsoberfläche ist. Hier sind einige Szenarien, in denen Sie ein `ColorField` effektiv nutzen können:

1. **Grafikdesign- und Bildbearbeitungswerkzeuge**: Farbfelder sind in Apps, die Anpassungen über die Farbauswahl ermöglichen, unerlässlich.

2. **Themenanpassung**: Wenn Ihre App es Benutzern erlaubt, Themen anzupassen, ermöglicht die Verwendung eines Farbfelds, dass sie Farben für verschiedene UI-Elemente wie Hintergründe, Texte, Schaltflächen usw. auswählen.

3. **Datenvisualisierung**: Bieten Sie den Benutzern ein Farbfeld an, um Farben für Diagramme, Graphen, Heatmaps und andere visuelle Darstellungen auszuwählen.

## Wert {#value}

Das `ColorField` verwendet die [`java.awt.Color`](https://docs.oracle.com/en/java/javase/17/docs/api/java.desktop/java/awt/Color.html) Klasse, um Farben über die Methoden `setValue()` und `getValue()` festzulegen und abzurufen. Während die clientseitige Komponente ausschließlich vollständig opake RGB-Farben in hexadezimaler Notation behandelt, vereinfacht webforJ den Prozess, indem es `Color`-Werte automatisch in das richtige Format umwandelt.

:::tip Hexadezimale Analyse
Beim Verwenden der `setText()`-Methode zum Zuordnen eines Wertes wird das `ColorField` versuchen, die Eingabe als hexadezimale Farbe zu analysieren. Wenn das Parsen fehlschlägt, wird eine `IllegalArgumentException` ausgelöst.
:::

## Statische Hilfsmittel {#static-utilities}

Die `ColorField`-Klasse bietet auch die folgenden statischen Hilfsmethoden:

- `fromHex(String hex)`: Konvertiert eine Farbzeichenfolge im Hexformat in ein `Color`-Objekt, das dann mit dieser Klasse oder an anderer Stelle verwendet werden kann.

- `toHex(Color color)`: Konvertiert den angegebenen Wert in die entsprechende hexadezimale Darstellung.

- `isValidHexColor(String hex)`: Überprüft, ob der angegebene Wert eine gültige 7-stellige hexadezimale Farbe ist.

## Best Practices {#best-practices}

Um eine optimale Benutzererfahrung bei der Verwendung der `ColorField`-Komponente zu gewährleisten, sollten Sie folgende Best Practices berücksichtigen:

- **Kontextuelle Unterstützung**: Bieten Sie kontextuelle Unterstützung, wie Tooltips oder ein Label, um zu verdeutlichen, dass Benutzer eine Farbe auswählen können und deren Zweck zu verstehen.

- **Bereitstellung einer Standardfarbe**: Verwenden Sie eine Standardfarbe, die im Kontext Ihrer App sinnvoll ist.

- **Angebot von vordefinierten Farben**: Fügen Sie eine Palette häufig verwendeter oder markenkonformer Farben neben dem Farbfeld zur schnellen Auswahl hinzu.
