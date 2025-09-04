---
sidebar_position: 30
title: PasswordField
slug: passwordfield
description: A single-line input component for securely entering and masking password data.
sidebar_class_name: updated-content
_i18n_hash: ca055ca343a756152533eb1ab3ec5c8c
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-field" />
<DocChip chip='since' label='23.02' />
<JavadocLink type="foundation" location="com/webforj/component/field/PasswordField" top='true'/>

<ParentLink parent="Field" />

Die `PasswordField`-Komponente ermöglicht es Benutzern, ein Passwort sicher einzugeben. Sie wird als einzeilige Texteingabe dargestellt, bei der der eingegebene Text obscuriert wird, typischerweise ersetzt durch Symbole wie Sterne (”*”) oder Punkte (”•”). Das genaue Symbol kann je nach Browser und Betriebssystem variieren.

<ComponentDemo 
path='/webforj/passwordfield?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/passwordfield/PasswordFieldView.java'
/>

## Feldwert {#field-value}

Die `PasswordField`-Komponente speichert und ruft ihren Wert als einfachen `String` ab, ähnlich wie ein `TextField`, jedoch mit obscurierter visueller Darstellung, um die Zeichen aus dem Blickfeld zu verbergen.

Sie können den aktuellen Wert mit folgendem Befehl abrufen:

```java
passwordField.getValue();
```

:::warning sensible Daten
Obwohl das Feld den Inhalt visuell maskiert, ist der zurückgegebene Wert von `getValue()` weiterhin ein einfacher String. Seien Sie sich dessen bewusst, wenn Sie mit sensiblen Daten umgehen, und verschlüsseln oder transformieren Sie diese vor der Speicherung.
:::

Um den Wert programmgesteuert festzulegen oder zurückzusetzen:

```java
passwordField.setValue("MySecret123!");
```

Wenn vom Benutzer kein Wert eingegeben wurde und kein Standardwert festgelegt ist, gibt das Feld einen leeren String (`""`) zurück.

Dieses Verhalten ähnelt dem des nativen HTML `<input type="password">`, bei dem die `value`-Eigenschaft die aktuelle Eingabe enthält.

## Verwendungsmöglichkeiten {#usages}

Die `PasswordField` wird am besten in Szenarien eingesetzt, in denen das Erfassen oder Verarbeiten sensibler Informationen, wie Passwörter oder andere vertrauliche Daten, für Ihre App wichtig ist. Hier sind einige Beispiele, wann Sie die `PasswordField` verwenden sollten:

1. **Benutzerauthentifizierung und Registrierung**: Passwortfelder sind entscheidend in Apps, die Benutzeranmelde- oder Registrierungsprozesse beinhalten, bei denen eine sichere Eingabe des Passworts erforderlich ist.

2. **Sichere Formulareingaben**: Bei der Gestaltung von Formularen, die die Eingabe sensibler Informationen wie Kreditkartendaten oder persönliche Identifikationsnummern (PINs) erfordern, sorgt die Verwendung eines `PasswordField` für die Sicherheit der Dateneingabe.

3. **Kontoverwaltung und Profileinstellungen**: Passwortfelder sind wertvoll in Apps, die die Kontoverwaltung oder Profileinstellungen betreffen, und ermöglichen es Benutzern, ihre Passwörter sicher zu ändern oder zu aktualisieren.

## Passwortsichtbarkeit {#password-visibility}

Benutzer können den Wert des `PasswordField` anzeigen, indem sie auf das Anzeige-Symbol klicken. Dies ermöglicht es Benutzern, zu überprüfen, was sie eingegeben haben, oder die Informationen in ihre Zwischenablage zu kopieren. In hochsicheren Umgebungen können Sie jedoch `setPasswordReveal()` verwenden, um das Anzeige-Symbol zu entfernen und zu verhindern, dass Benutzer den Wert sehen. Sie können mit der Methode `isPasswordReveal()` überprüfen, ob ein Benutzer das Anzeige-Symbol verwenden kann, um den Wert anzuzeigen.

## Mustererkennung {#pattern-matching}

Es wird dringend empfohlen, ein reguläres Ausdrucksmuster mithilfe der Methode `setPattern()` auf das `PasswordField` anzuwenden. Dies ermöglicht Ihnen, Zeichenregeln und strukturelle Anforderungen durchzusetzen, sodass Benutzer sichere und konforme Anmeldedaten erstellen. Die Mustererkennung ist besonders nützlich, um starke Passwortregeln durchzusetzen, wie etwa die Anforderung einer Mischung aus Groß- und Kleinbuchstaben, Zahlen und Symbolen.

Das Muster muss der Syntax eines [JavaScript-regulären Ausdrucks](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Guide/Regular_expressions) entsprechen, wie sie vom Browser interpretiert wird. Das `u` (Unicode)-Flag wird intern verwendet, um die Validierung über alle Unicode-Codepunkte hinweg zu gewährleisten. Fügen Sie **keine** Schrägstriche (`/`) um das Muster hinzu.

Im folgenden Snippet erfordert das Muster mindestens einen Kleinbuchstaben, einen Großbuchstaben, eine Zahl und eine Mindestlänge von 8 Zeichen.

```java
passwordField.setPattern("(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,}");
```

Wenn das Muster fehlt oder ungültig ist, wird keine Validierung angewendet.

:::tip
Verwenden Sie `setLabel()`, um ein klares Label bereitzustellen, das den Zweck des Passwortfelds beschreibt. Um den Benutzern zu helfen, die Passwortanforderungen zu verstehen, verwenden Sie `setHelperText()`, um Anleitungen oder Regeln direkt unter dem Feld anzuzeigen.
:::

## Minimale und maximale Länge {#minimum-and-maximum-length}

Sie können die erlaubte Länge der Passworteingabe steuern, indem Sie `setMinLength()` und `setMaxLength()` auf dem `PasswordField` verwenden.

Die Methode `setMinLength()` definiert die Mindestanzahl an Zeichen, die ein Benutzer im Feld eingeben muss, um die Validierung zu bestehen. Dieser Wert muss eine nicht-negative Ganzzahl sein und darf die maximale Länge, falls sie festgelegt ist, nicht überschreiten.

```java
passwordField.setMinLength(8); // Mindestlänge 8 Zeichen
```

Wenn der Benutzer weniger Zeichen als die Mindestanzahl eingibt, schlägt die Eingabe in der Validierung fehl. Diese Validierung wird nur angewendet, wenn der Wert des Feldes vom Benutzer geändert wird.

Die Methode `setMaxLength()` legt die maximale Anzahl an Zeichen fest, die im Feld erlaubt sind. Der Wert muss 0 oder größer sein. Wenn er nicht definiert ist oder auf einen ungültigen Wert gesetzt wird, hat das Feld kein oberes Zeichenlimit.

```java
passwordField.setMaxLength(20); // Maximale Länge 20 Zeichen
```

Wenn die Eingabe das maximale Zeichenlimit überschreitet, schlägt das Feld in der Validierung fehl. Wie bei der minimalen Länge gilt diese Regel nur, wenn der Benutzer den Wert des Feldes aktualisiert.

:::tip
Verwenden Sie sowohl `setMinLength()` als auch `setMaxLength()` zusammen, um effektive Eingabebereiche zu schaffen. Siehe die [HTML-Längenbeschränkungsdokumentation](https://developer.mozilla.org/en-US/docs/Web/HTML/Element/input#minlength) für weitere Hinweise.
:::

## Best Practices {#best-practices}

Da die `PasswordField`-Komponente häufig mit sensiblen Informationen verbunden ist, sollten Sie die folgenden Best Practices berücksichtigen, wenn Sie das `PasswordField` verwenden:

- **Bereitstellung von Passwortstärke-Rückmeldungen**: Integrieren Sie Passwortstärke-Indikatoren oder Rückmeldemechanismen, um Benutzern zu helfen, starke und sichere Passwörter zu erstellen. Bewerten Sie Faktoren wie Länge, Komplexität und eine Mischung aus Groß- und Kleinbuchstaben, Zahlen sowie Sonderzeichen.

- **Durchsetzung der Passwortspeicherung**: Speichern Sie niemals Passwörter im Klartext. Implementieren Sie stattdessen geeignete Sicherheitsmaßnahmen, um Passwörter sicher in Ihrer App zu behandeln und zu speichern. Verwenden Sie branchenübliche Verschlüsselungsalgorithmen für Passwörter und andere sensible Daten.

- **Passwortbestätigung**: Fügen Sie ein zusätzliches Bestätigungsfeld hinzu, wenn ein Benutzer ein Passwort ändert oder erstellt. Diese Maßnahme hilft, die Wahrscheinlichkeit von Tippfehlern zu minimieren und sicherzustellen, dass Benutzer ihr gewünschtes Passwort genau eingeben.

- **Erlauben Sie das Zurücksetzen des Passworts**: Wenn Ihre App Benutzerkonten umfasst, bieten Sie eine Option für Benutzer, ihr Passwort zurückzusetzen. Dies könnte in Form einer "Passwort vergessen"-Funktion geschehen, die einen Passwortwiederherstellungsprozess initiiert.

- **Zugänglichkeit**: Richten Sie das `PasswordField` unter Berücksichtigung der Zugänglichkeit ein, sodass es den Standards für Barrierefreiheit entspricht, wie etwa durch Bereitstellung geeigneter Labels und Kompatibilität mit unterstützenden Technologien.
