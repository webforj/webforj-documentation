---
sidebar_position: 20
title: Fields
hide_giscus_comments: true
sidebar_class_name: has-new-content
_i18n_hash: 288d408cb058dbaa417fea651698123a
---
<JavadocLink type="foundation" location="com/webforj/component/field/AbstractField"/>

Das webforJ-Framework unterstützt sieben verschiedene Typen von Komponenten für Eingabefelder, die jeweils unterschiedliche Verhaltensweisen und Implementierungen aufweisen, die verschiedenen Anforderungen für Eingaben entsprechen.
Obwohl jede dieser Komponenten Variationen in ihren Implementierungen hat, beschreibt dieser Artikel die gemeinsamen Eigenschaften aller Feldklassen.

:::info
Dieser Abschnitt beschreibt gemeinsame Merkmale verschiedener Feldkomponenten in webforJ und ist selbst keine Klasse, die instanziiert und verwendet werden kann.
:::

## Gemeinsame Feld Eigenschaften {#shared-field-properties}

### Label {#label}

Ein Feldlabel ist ein beschreibender Text oder Titel, der mit dem Feld verknüpft ist und im Konstruktor oder durch die Verwendung der Methode `setLabel()` definiert werden kann. Labels bieten eine kurze Erklärung oder Aufforderung, die den Benutzern hilft, den Zweck oder die erwartete Eingabe für dieses bestimmte Feld zu verstehen. Feldlabels sind wichtig für die Benutzerfreundlichkeit und spielen eine entscheidende Rolle für die Barrierefreiheit, da sie es Bildschirmlesegeräten und unterstützenden Technologien ermöglichen, genaue Informationen bereitzustellen und die Navigation über die Tastatur zu erleichtern.

### Hilfetext {#helper-text}

Jedes Feld kann Hilfetext unter der Eingabe anzeigen, indem die Methode `setHelperText()` verwendet wird. Dieser Hilfetext bietet zusätzliche Kontextinformationen oder Erklärungen zu den verfügbaren Eingaben, um sicherzustellen, dass die Benutzer die notwendigen Informationen haben, um informierte Entscheidungen zu treffen.

### Erforderlich {#required}

Sie können die Methode `setRequired(true)` aufrufen, um zu verlangen, dass die Benutzer einen Wert angeben, bevor sie ein Formular absenden. Dieses Attribut funktioniert zusammen mit dem Feldlabel und bietet eine visuelle Anzeige dafür, dass ein Feld erforderlich ist. Dieses visuelle Signal hilft den Personen, Formulare korrekt auszufüllen.

:::info
Feldkomponenten enthalten eingebaute visuelle Validierung, um die Benutzer zu benachrichtigen, wenn ein erforderliches Feld leer ist oder wenn ein Benutzer einen Wert entfernt hat.
:::

### Rechtschreibprüfung {#spellcheck}

Durch die Verwendung von `setSpellCheck(true)` können Sie dem Browser oder Benutzeragenten erlauben, die Rechtschreibung des vom Benutzer eingegebenen Textes zu überprüfen und Fehler zu identifizieren.

### Präfix und Suffix {#prefix-and-suffix}

Slots bieten flexible Optionen zur Verbesserung der Fähigkeit von Feldkomponenten. Sie können Symbole, Labels, Lade-Symbole, die Möglichkeit zum Löschen/Zurücksetzen, Avatare/Profilbilder und andere nützliche Komponenten innerhalb eines Feldes einfügen, um den Benutzern die beabsichtigte Bedeutung besser zu verdeutlichen.
Felder haben zwei Slots: die `prefix`- und `suffix`-Slots. Verwenden Sie die Methoden `setPrefixComponent()` und `setSuffixComponent()`, um verschiedene Komponenten vor und nach der angezeigten Option innerhalb eines Feldes einzufügen. Hier ist ein Beispiel mit dem `TextField`-Feld:

```java
TextField textField = new TextField();
textField.setPrefixComponent(TablerIcon.create("box"));
textField.setSuffixComponent(TablerIcon.create("box"));
```

## Styling {#styling}

:::info
Da alle Feldkomponenten aus einer einzigen Webkomponente erstellt werden, teilen sie sich alle die folgenden Schattenkomponenten und CSS-Property-Werte.
:::

<TableBuilder name="Field" />

## Themen {#topics}

<DocCardList className="topics-section" />
