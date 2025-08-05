---
sidebar_position: 20
title: Fields
hide_giscus_comments: true
sidebar_class_name: has-new-content
_i18n_hash: b04acdedbd800790417edfe940160bf2
---
<JavadocLink type="foundation" location="com/webforj/component/field/AbstractField"/>

Das webforJ-Framework unterstützt sieben verschiedene Typen von Feldkomponenten, die jeweils unterschiedliche Verhaltensweisen und Implementierungen aufweisen, die verschiedenen Bedürfnissen für Eingaben gerecht werden. Während jede dieser Komponenten Variationen in ihren Implementierungen aufweist, beschreibt dieser Artikel die gemeinsamen Eigenschaften aller Feldklassen.

:::info
Dieser Abschnitt beschreibt die gemeinsamen Merkmale verschiedener Feldkomponenten in webforJ und ist selbst keine Klasse, die instanziiert und verwendet werden kann.
:::

## Gemeinsame Feld Eigenschaften {#shared-field-properties}

### Label {#label}

Ein Feldlabel ist ein beschreibender Text oder Titel, der mit dem Feld verknüpft ist und im Konstruktor oder durch die Verwendung der Methode `setLabel()` definiert werden kann. Labels bieten eine kurze Erklärung oder Aufforderung, um den Benutzern zu helfen, den Zweck oder die erwartete Eingabe für dieses spezielle Feld zu verstehen. Feldlabels sind wichtig für die Benutzerfreundlichkeit und spielen eine entscheidende Rolle für die Barrierefreiheit, da sie es Screenreadern und unterstützenden Technologien ermöglichen, genaue Informationen bereitzustellen und die Tastaturnavigation zu erleichtern.

### Hilfetext {#helper-text}

Jedes Feld kann mittels der Methode `setHelperText()` Hilfetext unter der Eingabe anzeigen. Dieser Hilfetext bietet zusätzlichen Kontext oder Erklärungen zu den verfügbaren Eingaben und stellt sicher, dass die Benutzer über die notwendigen Informationen verfügen, um informierte Entscheidungen zu treffen.

### Erforderlich {#required}

Sie können die Methode `setRequired(true)` aufrufen, um von den Benutzern zu verlangen, dass sie einen Wert angeben, bevor sie ein Formular absenden. Diese Eigenschaft funktioniert zusammen mit dem Feldlabel und bietet eine visuelle Anzeige, die anzeigt, dass ein Feld erforderlich ist. Dieses visuelle Signal hilft den Nutzern, Formulare korrekt auszufüllen.

:::info
Feldkomponenten enthalten eine integrierte visuelle Validierung, um Benutzer zu benachrichtigen, wenn ein erforderliches Feld leer ist oder ein Benutzer einen Wert entfernt hat.
:::

### Rechtschreibprüfung {#spellcheck}

Durch die Verwendung von `setSpellCheck(true)` können Sie dem Browser oder Nutzeragenten erlauben, die Rechtschreibung des vom Benutzer eingegebenen Textes zu überprüfen und Fehler zu identifizieren.

### Präfix und Suffix {#prefix-and-suffix}

Slots bieten flexible Optionen zur Verbesserung der Fähigkeit von Feldkomponenten. Sie können Icons, Labels, Ladeanimationen, Funktionen zum Löschen/Zurücksetzen, Avatar/Profilbilder und andere nützliche Komponenten innerhalb eines Feldes einfügen, um die beabsichtigte Bedeutung für die Benutzer weiter zu verdeutlichen. Felder haben zwei Slots: die `prefix` und `suffix` Slots. Verwenden Sie die Methoden `setPrefixComponent()` und `setSuffixComponent()`, um verschiedene Komponenten vor und nach der angezeigten Option innerhalb eines Feldes einzufügen. Hier ist ein Beispiel, das das Feld `TextField` verwendet:

```java
TextField textField = new TextField();
textField.setPrefixComponent(TablerIcon.create("box"));
textField.setSuffixComponent(TablerIcon.create("box"));
```

## Styling {#styling}

:::info
Da alle Feldkomponenten aus einer einzigen Webkomponente erstellt werden, teilen sie sich alle die folgenden Shadow Parts und CSS-Property-Werte
:::

<TableBuilder name="Field" />

## Themen {#topics}

<DocCardList className="topics-section" />
