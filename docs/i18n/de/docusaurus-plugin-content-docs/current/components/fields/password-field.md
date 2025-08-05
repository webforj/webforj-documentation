---
sidebar_position: 30
title: PasswordField
slug: passwordfield
description: A single-line input component for securely entering and masking password data.
sidebar_class_name: updated-content
_i18n_hash: 180bd1578c78bf1ee9e746d23f76ec96
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-field" />
<DocChip chip='since' label='23.02' />
<JavadocLink type="foundation" location="com/webforj/component/field/PasswordField" top='true'/>

<ParentLink parent="Field" />

Die `PasswordField`-Komponente ermöglicht es Benutzern, ein Passwort sicher einzugeben. Sie wird als einzeiliger Texteditor angezeigt, in dem der eingegebene Text verdeckt wird, typischerweise durch Symbole wie Sternchen (”*”) oder Punkte (”•”). Das genaue Symbol kann je nach Browser und Betriebssystem variieren. 

<ComponentDemo 
path='/webforj/passwordfield?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/passwordfield/PasswordFieldView.java'
/>

## Feldwert {#field-value}

Die `PasswordField`-Komponente speichert und ruft ihren Wert als einfachen `String` ab, ähnlich wie ein `TextField`, jedoch mit einer verdeckten visuellen Darstellung, um die Zeichen aus dem Blickfeld zu halten.

Sie können den aktuellen Wert wie folgt abrufen:

```java
passwordField.getValue();
```

:::warning sensible Daten
Obwohl das Feld den Inhalt visuell maskiert, ist der zurückgegebene Wert von `getValue()` immer noch ein einfacher String. Seien Sie sich dessen bewusst, wenn Sie mit sensiblen Daten umgehen, und verschlüsseln oder transformieren Sie diese vor der Speicherung.
:::

Um den Wert programmgesteuert festzulegen oder zurückzusetzen:

```java
passwordField.setValue("MySecret123!");
```

Wenn vom Benutzer kein Wert eingegeben wurde und kein Standardwert festgelegt ist, gibt das Feld einen leeren String (`""`) zurück.

Dieses Verhalten ähnelt dem des nativen HTML `<input type="password">`, bei dem die `value`-Eigenschaft den aktuellen Eingabewert hält.

## Anwendungen {#usages}

Das `PasswordField` eignet sich am besten in Szenarien, in denen das Erfassen oder Verarbeiten sensibler Informationen, wie Passwörter oder andere vertrauliche Daten, für Ihre App von wesentlicher Bedeutung ist. Hier sind einige Beispiele dafür, wann das `PasswordField` verwendet werden sollte:

1. **Benutzerauthentifizierung und -registrierung**: Passwortfelder sind entscheidend in Apps, die Benutzerauthentifizierung oder Registrierungsprozesse erfordern, bei denen eine sichere Passwort-Eingabe erforderlich ist.

2. **Sichere Formulareingaben**: Bei der Gestaltung von Formularen, die die Eingabe sensibler Informationen, wie Kreditkartendaten oder persönliche Identifikationsnummern (PINs), erfordern, sichert die Verwendung eines `PasswordField` die Eingabe solcher Daten.

3. **Kontoverwaltung und Profileinstellungen**: Passwortfelder sind wertvoll in Apps, die die Kontoverwaltung oder Profileinstellungen umfassen, und ermöglichen es Benutzern, ihre Passwörter sicher zu ändern oder zu aktualisieren.

## Passwortsichtbarkeit {#password-visibility}

Benutzer können den Wert des `PasswordField` anzeigen, indem sie auf das Anzeigen-Symbol klicken. Dies ermöglicht es Benutzern, zu überprüfen, was sie eingegeben haben, oder die Informationen in ihre Zwischenablage zu kopieren. Für hochsichere Umgebungen können Sie jedoch `setPasswordReveal()` verwenden, um das Anzeigen-Symbol zu entfernen und zu verhindern, dass Benutzer den Wert sehen. Sie können mit der Methode `isPasswordReveal()` überprüfen, ob ein Benutzer das Anzeigen-Symbol verwenden kann, um den Wert anzuzeigen.

## Musterabgleich {#pattern-matching}

Es wird dringend empfohlen, ein reguläres Ausdrucksmuster im `PasswordField` über die Methode `setPattern()` anzuwenden. Dies ermöglicht es Ihnen, Zeichenregeln und Strukturanforderungen durchzusetzen und die Benutzer zu zwingen, sichere und regelkonforme Anmeldeinformationen zu erstellen. Die Musterabgleich ist besonders nützlich, wenn starke Passwortregeln durchgesetzt werden sollen, wie beispielsweise die Anforderung einer Kombination aus Groß- und Kleinbuchstaben, Zahlen und Symbolen.

Das Muster muss der Syntax eines [JavaScript-Regulären Ausdrucks](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Guide/Regular_expressions) entsprechen, wie es vom Browser interpretiert wird. Das `u` (Unicode) Flag wird intern verwendet, um die Validierung über alle Unicode-Codepunkte hinweg zu gewährleisten. Fügen Sie **keine** führenden Schrägstriche (`/`) um das Muster hinzu.

Im folgenden Snippet erfordert das Muster mindestens einen Kleinbuchstaben, einen Großbuchstaben, eine Zahl und eine Mindestlänge von 8 Zeichen.

```java
passwordField.setPattern("(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,}");
```

Wenn das Muster fehlt oder ungültig ist, wird keine Validierung angewendet.

:::tip
Verwenden Sie `setLabel()`, um ein klares Label bereitzustellen, das den Zweck des Passwortfeldes beschreibt. Um den Benutzern zu helfen, die Passwortanforderungen zu verstehen, verwenden Sie `setHelperText()`, um Hinweise oder Regeln direkt unter dem Feld anzuzeigen.
:::

## Mindest- und Höchstlänge {#minimum-and-maximum-length}

Sie können die erlaubte Länge der Passwort-Eingabe steuern, indem Sie `setMinLength()` und `setMaxLength()` auf dem `PasswordField` verwenden.

Die Methode `setMinLength()` definiert die Mindestanzahl von Zeichen, die ein Benutzer in das Feld eingeben muss, um die Validierung zu bestehen. Dieser Wert muss eine nicht-negative Ganzzahl sein und darf die maximale Länge, sofern festgelegt, nicht überschreiten.

```java
passwordField.setMinLength(8); // Mindestanzahl 8 Zeichen
```

Wenn der Benutzer weniger Zeichen als die Mindestanzahl eingibt, schlägt die Eingabe bei der Validierung fehl. Diese Validierung wird nur angewendet, wenn der Feldwert vom Benutzer geändert wird.

Die Methode `setMaxLength()` legt die maximale Anzahl von Zeichen fest, die im Feld erlaubt sind. Der Wert muss 0 oder größer sein. Wenn er nicht definiert oder auf einen ungültigen Wert gesetzt wird, hat das Feld keine obere Zeichenbegrenzung.

```java
passwordField.setMaxLength(20); // Höchstlänge 20 Zeichen
```

Wenn die Eingabe die maximale Zeichenbegrenzung überschreitet, schlägt das Feld bei der Validierung fehl. Wie bei der Mindestgrenze gilt diese Regel nur, wenn der Benutzer den Wert des Feldes aktualisiert.

:::tip
Verwenden Sie sowohl `setMinLength()` als auch `setMaxLength()`, um effektive Eingabebereiche zu erstellen. Siehe die [HTML-Längenbeschränkungen-Dokumentation](https://developer.mozilla.org/en-US/docs/Web/HTML/Element/input#minlength) für weitere Informationen.
:::

## Beste Praktiken {#best-practices}

Da die `PasswordField`-Komponente häufig mit sensiblen Informationen verbunden ist, sollten Sie bei der Verwendung des `PasswordField` die folgenden besten Praktiken berücksichtigen:

- **Bereitstellung von Passwortstärke-Rückmeldungen**: Integrieren Sie Indikatoren oder Rückmeldungen zur Passwortstärke, um Benutzern zu helfen, starke und sichere Passwörter zu erstellen. Bewerten Sie Faktoren wie Länge, Komplexität und eine Mischung aus Groß- und Kleinbuchstaben, Zahlen und Sonderzeichen.

- **Durchsetzung der Passwortspeicherung**: Speichern Sie Passwörter niemals im Klartext. Implementieren Sie stattdessen angemessene Sicherheitsmaßnahmen, um Passwörter sicher in Ihrer App zu behandeln und zu speichern. Verwenden Sie branchenspezifische Verschlüsselungsalgorithmen für Passwörter und andere sensible Daten.

- **Passwortbestätigung**: Fügen Sie ein zusätzliches Bestätigungsfeld hinzu, wenn ein Benutzer ein Passwort ändert oder erstellt. Diese Maßnahme hilft, die Wahrscheinlichkeit von Tippfehlern zu minimieren und sicherzustellen, dass Benutzer ihr gewünschtes Passwort genau eingeben.

- **Passwortzurücksetzung zulassen**: Wenn Ihre App Benutzerkonten umfasst, bieten Sie eine Option für Benutzer, ihr Passwort zurückzusetzen. Dies könnte in Form einer „Passwort vergessen“-Funktion geschehen, die einen Passwortwiederherstellungsprozess einleitet.

- **Zugänglichkeit**: Richten Sie das `PasswordField` mit Blick auf die Zugänglichkeit ein, damit es den Zugänglichkeitsstandards entspricht, wie beispielsweise die Bereitstellung geeigneter Labels und die Kompatibilität mit unterstützenden Technologien.
