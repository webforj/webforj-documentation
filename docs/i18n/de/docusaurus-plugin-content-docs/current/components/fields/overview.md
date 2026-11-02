---
sidebar_position: 20
title: Fields
hide_giscus_comments: true
description: >-
  Share common field properties across webforJ input components, including
  labels, helper text, required state, spellcheck, and prefix or suffix slots.
_i18n_hash: 56462cf4d659ce15f72a624acae60f0f
---
<JavadocLink type="foundation" location="com/webforj/component/field/DwcField"/>

Das webforJ-Framework unterstützt elf Arten von Feldkomponenten, darunter vier maskierte Felder, die jeweils unterschiedliche Verhaltensweisen und Implementierungen aufweisen, die verschiedenen Eingabebedürfnissen gerecht werden. Während jede dieser Komponenten Variationen in ihren Implementierungen hat, beschreibt dieser Artikel die gemeinsamen Eigenschaften aller Feldklassen.

:::info
Dieser Abschnitt beschreibt gemeinsame Merkmale verschiedener Feldkomponenten in webforJ und ist selbst keine Klasse, die instanziiert und verwendet werden kann.
:::

## Gemeinsame Feldmerkmale {#shared-field-properties}

### Bezeichnung {#label}

Ein Feldbezeichner ist ein beschreibender Text oder Titel, der mit dem Feld verbunden ist und im Konstruktor oder durch Verwendung der Methode `setLabel()` definiert werden kann. Bezeichnungen bieten eine kurze Erklärung oder Aufforderung, um den Benutzern zu helfen, den Zweck oder die erwartete Eingabe für dieses spezielle Feld zu verstehen. Feldbezeichnungen sind wichtig für die Benutzerfreundlichkeit und spielen eine entscheidende Rolle für die Barrierefreiheit, da sie es Bildschirmlese- und Hilfstechnologien ermöglichen, genaue Informationen bereitzustellen und die Navigation mit der Tastatur zu erleichtern.

### Hilfetext {#helper-text}

Jedes Feld kann Hilfetexte unter dem Eingabefeld mithilfe der Methode `setHelperText()` anzeigen. Dieser Hilfetext bietet zusätzliche Kontexte oder Erklärungen zu den verfügbaren Eingaben und stellt sicher, dass die Benutzer die notwendigen Informationen haben, um informierte Auswahlentscheidungen zu treffen.

### Erforderlich {#required}

Sie können die Methode `setRequired(true)` aufrufen, um von den Benutzern zu verlangen, dass sie einen Wert angeben, bevor sie ein Formular absenden. Diese Eigenschaft funktioniert in Verbindung mit dem Feldbezeichner und bietet eine visuelle Anzeige dafür, dass ein Feld notwendig ist. Dieser visuelle Hinweis hilft den Benutzern, Formulare genau auszufüllen.

:::info
Feldkomponenten enthalten integrierte visuelle Validierung, um Benutzer zu benachrichtigen, wenn ein erforderliches Feld leer ist oder wenn ein Benutzer einen Wert entfernt hat.
:::

### Rechtschreibprüfung {#spellcheck}

Durch Verwendung von `setSpellCheck(true)` können Sie dem Browser oder Benutzeragenten erlauben, die Rechtschreibung des vom Benutzer eingegebenen Textes zu überprüfen und eventuelle Fehler zu identifizieren.

### Präfix und Suffix {#prefix-and-suffix}

Slots bieten flexible Optionen zur Verbesserung der Funktionalität von Feldkomponenten. Sie können Icons, Bezeichnungen, Ladeindikatoren, Lösch-/Zurücksetzen-Funktionalitäten, Avatar-/Profilbilder und andere nützliche Komponenten innerhalb eines Feldes einfügen, um den Benutzern die beabsichtigte Bedeutung weiter zu verdeutlichen. Felder haben zwei Slots: den `prefix`- und den `suffix`-Slot. Verwenden Sie die Methoden `setPrefixComponent()` und `setSuffixComponent()`, um verschiedene Komponenten vor und nach der angezeigten Option innerhalb eines Feldes einzufügen. Hier ist ein Beispiel mit dem `TextField`-Feld:

```java
TextField textField = new TextField();
textField.setPrefixComponent(TablerIcon.create("box"));
textField.setSuffixComponent(TablerIcon.create("box"));
```

## Styling {#styling}

:::info
Da alle Feldkomponenten aus einer einzigen Webkomponente erstellt werden, teilen sie sich alle die folgenden Shadow Parts und CSS-Eigenschaftswerte.
:::

<TableBuilder name="Feld" />

## Themen {#topics}

<DocCardList className="topics-section" />
