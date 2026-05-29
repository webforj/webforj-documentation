---
sidebar_position: 30
title: PasswordField
slug: passwordfield
description: A single-line input component for securely entering and masking password data.
_i18n_hash: b0641475acf187af7c45d6786506010d
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-field" />
<DocChip chip='since' label='23.02' />
<JavadocLink type="foundation" location="com/webforj/component/field/PasswordField" top='true'/>

Die `PasswordField`-Komponente ermöglicht es Benutzern, ein Passwort sicher einzugeben. Es wird als einzeiliges Texteingabefeld dargestellt, in dem der eingegebene Text obscuriert wird, typischerweise ersetzt durch Symbole wie Sternchen ("*") oder Punkte ("•"). Das genaue Symbol kann je nach Browser und Betriebssystem variieren.

<!-- INTRO_END -->

## Verwendung der `PasswordField` {#using-passwordfield}

<ParentLink parent="Field" />

`PasswordField` erweitert die gemeinsame `Field`-Klasse, die gemeinsame Funktionen für alle Feldkomponenten bereitstellt. Das folgende Beispiel erstellt ein `PasswordField` mit einem Label und Platzhaltertext.

<ComponentDemo
path='/webforj/passwordfield'
files={['src/main/java/com/webforj/samples/views/fields/passwordfield/PasswordFieldView.java']}
/>

## Feldwert {#field-value}

Die `PasswordField`-Komponente speichert und ruft ihren Wert als einfachen `String` ab, ähnlich wie ein `TextField`, jedoch mit einer obscurierten visuellen Darstellung, um die Zeichen vor dem Blick zu verbergen.

Sie können den aktuellen Wert mit folgender Methode abrufen:

```java
passwordField.getValue();
```

:::warning sensible Daten
Obwohl das Feld den Inhalt visuell maskiert, ist der zurückgegebene Wert von `getValue()` weiterhin ein einfacher String. Seien Sie sich dessen bewusst, wenn Sie mit sensiblen Daten umgehen und verschlüsseln oder transformieren Sie diese vor der Speicherung.
:::

Um den Wert programmgesteuert festzulegen oder zurückzusetzen:

```java
passwordField.setValue("MySecret123!");
```

Wenn der Benutzer keinen Wert eingegeben hat und kein Standardwert festgelegt ist, gibt das Feld einen leeren String (`""`) zurück.

Dieses Verhalten ähnelt dem des nativen HTML `<input type="password">`, bei dem die `value`-Eigenschaft die derzeitige Eingabe enthält.

## Verwendungen {#usages}

Das `PasswordField` ist am besten geeignet für Szenarien, in denen die Erfassung oder Verarbeitung sensibler Informationen, wie Passwörter oder andere vertrauliche Daten, für Ihre App von entscheidender Bedeutung ist. Hier sind einige Beispiele, wann das `PasswordField` zu verwenden ist:

1. **Benutzerauthentifizierung und Registrierung**: Passwortfelder sind entscheidend für Apps, die Benutzerauthentifizierungs- oder Registrierungsprozesse beinhalten, bei denen eine sichere Passworteingabe erforderlich ist.

2. **Sichere Formulareingaben**: Bei der Gestaltung von Formularen, die die Eingabe sensibler Informationen erfordern, wie Kreditkartendaten oder persönliche Identifikationsnummern (PINs), sorgt die Verwendung eines `PasswordField` für die Sicherheit der Eingabe solcher Daten.

3. **Kontoverwaltung und Profileinstellungen**: Passwortfelder sind wertvoll in Apps, die Kontoverwaltung oder Profileinstellungen beinhalten und es Benutzern ermöglichen, ihre Passwörter sicher zu ändern oder zu aktualisieren.

## Passwortsichtbarkeit {#password-visibility}

Benutzer können den Wert des `PasswordField` anzeigen, indem sie auf das Anzeige-Symbol klicken. Dies ermöglicht es den Benutzern, zu überprüfen, was sie eingegeben haben, oder die Informationen in ihre Zwischenablage zu kopieren. Für Umgebungen mit hoher Sicherheit können Sie jedoch `setPasswordReveal()` verwenden, um das Anzeige-Symbol zu entfernen und zu verhindern, dass Benutzer den Wert sehen. Sie können überprüfen, ob ein Benutzer das Anzeige-Symbol verwenden kann, um den Wert mit der Methode `isPasswordReveal()` anzuzeigen.

## Musterabgleich {#pattern-matching}

Es wird dringend empfohlen, ein reguläres Ausdrucksmuster auf das `PasswordField` mithilfe der Methode `setPattern()` anzuwenden. Dies ermöglicht es Ihnen, Regeln für Zeichen und strukturelle Anforderungen durchzusetzen und Benutzer zu zwingen, sichere und konforme Anmeldeinformationen zu erstellen. Der Musterabgleich ist besonders nützlich, wenn es darum geht, strenge Passwortregeln durchzusetzen, wie das Erfordern von einer Mischung aus Groß- und Kleinbuchstaben, Zahlen und Symbolen.

Das Muster muss der Syntax eines [JavaScript-Regulären Ausdrucks](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Guide/Regular_expressions) entsprechen, wie vom Browser interpretiert. Das `u` (Unicode)-Flag wird intern verwendet, um eine Validierung über alle Unicode-Codepunkte hinweg zu garantieren. Fügen Sie **keine** Schrägstriche (`/`) um das Muster ein.

Im folgenden Snippet erfordert das Muster mindestens einen Kleinbuchstaben, einen Großbuchstaben, eine Zahl und eine Mindestlänge von 8 Zeichen.

```java
passwordField.setPattern("(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,}");
```

Fehlt das Muster oder ist es ungültig, wird keine Validierung angewendet.

:::tip
Verwenden Sie `setLabel()`, um ein klares Label bereitzustellen, das den Zweck des Passwortfelds beschreibt. Um den Benutzern zu helfen, die Passwortanforderungen zu verstehen, verwenden Sie `setHelperText()`, um Ratschläge oder Regeln direkt unter dem Feld anzuzeigen.
:::

## Minimale und maximale Länge {#minimum-and-maximum-length}

Sie können die zulässige Länge der Passworteingabe steuern, indem Sie `setMinLength()` und `setMaxLength()` am `PasswordField` verwenden.

Die Methode `setMinLength()` definiert die Mindestanzahl von Zeichen, die ein Benutzer in das Feld eingeben muss, um die Validierung zu bestehen. Dieser Wert muss eine nicht negative Ganzzahl sein und darf die maximale Länge nicht überschreiten, wenn eine solche festgelegt ist.

```java
passwordField.setMinLength(8); // Mindestlänge 8 Zeichen
```

Gibt der Benutzer weniger Zeichen als die Mindestanzahl ein, schlägt die Eingabe bei der Antragstellung der Validierungsbedingungen fehl. Diese Validierung wird nur angewendet, wenn der Wert des Feldes vom Benutzer geändert wird.

Die Methode `setMaxLength()` legt die maximale Anzahl von Zeichen fest, die im Feld erlaubt sind. Der Wert muss 0 oder größer sein. Wenn er nicht definiert oder auf einen ungültigen Wert gesetzt ist, hat das Feld keine obere Zeichenbegrenzung.

```java
passwordField.setMaxLength(20); // Maximale Länge 20 Zeichen
```

Überschreitet die Eingabe die maximale Zeichenanzahl, schlägt das Feld bei der Antragstellung der Validierungsbedingungen fehl. Wie bei der Mindestanzahl gilt diese Regel nur, wenn der Benutzer den Wert des Feldes aktualisiert.

:::tip
Verwenden Sie sowohl `setMinLength()` als auch `setMaxLength()` zusammen, um effektive Eingabengrenzen zu schaffen. Siehe die [HTML-Längenbeschränkungen Dokumentation](https://developer.mozilla.org/en-US/docs/Web/HTML/Element/input#minlength) für weitere Referenz.
:::

## Beste Praktiken {#best-practices}

Da die `PasswordField`-Komponente häufig mit sensiblen Informationen in Verbindung gebracht wird, berücksichtigen Sie die folgenden bewährten Methoden, wenn Sie das `PasswordField` verwenden:

- **Geben Sie Passwortstärke-Feedback**: Integrieren Sie Passwortstärke-Indikatoren oder Feedback-Mechanismen, um Benutzern zu helfen, starke und sichere Passwörter zu erstellen. Bewerten Sie Faktoren wie Länge, Komplexität und eine Mischung aus Groß- und Kleinbuchstaben, Zahlen und Sonderzeichen.

- **Durchsetzung der Passwortspeicherung**: Speichern Sie Passwörter niemals im Klartext. Implementieren Sie stattdessen geeignete Sicherheitsmaßnahmen, um Passwörter in Ihrer App sicher zu handhaben und zu speichern. Verwenden Sie branchenübliche Verschlüsselungsalgorithmen für Passwörter und andere sensible Daten.

- **Passwortbestätigung**: Fügen Sie ein zusätzliches Bestätigungsfeld hinzu, wenn ein Benutzer ein Passwort ändert oder erstellt. Diese Maßnahme hilft, die Wahrscheinlichkeit von Tippfehlern zu minimieren und stellt sicher, dass die Benutzer ihr gewünschtes Passwort genau eingeben.

- **Erlauben Sie die Passwortzurücksetzung**: Wenn Ihre App Benutzerkonten umfasst, bieten Sie eine Option für Benutzer, ihr Passwort zurückzusetzen. Dies könnte in Form einer "Passwort vergessen"-Funktion erfolgen, die einen Passwortwiederherstellungsprozess initiiert.

- **Zugänglichkeit**: Richten Sie das `PasswordField` mit der Zugänglichkeit im Hinterkopf ein, sodass es die Zugänglichkeitsstandards erfüllt, wie die Bereitstellung geeigneter Label und die Kompatibilität mit unterstützenden Technologien.
