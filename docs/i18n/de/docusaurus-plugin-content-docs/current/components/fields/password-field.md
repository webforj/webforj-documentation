---
sidebar_position: 30
title: PasswordField
slug: passwordfield
description: A single-line input component for securely entering and masking password data.
_i18n_hash: 7bdcafe322080112206dd70e6a2146c7
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-field" />
<DocChip chip='since' label='23.02' />
<JavadocLink type="foundation" location="com/webforj/component/field/PasswordField" top='true'/>

Die `PasswordField`-Komponente ermöglicht es Benutzern, ein Passwort sicher einzugeben. Sie wird als einzeiliger Texteditor angezeigt, in dem der eingegebene Text verdeckt wird, typischerweise ersetzt durch Symbole wie Sternchen ("*") oder Punkte ("•"). Das genaue Symbol kann je nach Browser und Betriebssystem variieren.

<!-- INTRO_END -->

## Verwendung der `PasswordField` {#using-passwordfield}

<ParentLink parent="Field" />

`PasswordField` erweitert die gemeinsame `Field`-Klasse, die gemeinsame Funktionen über alle Feldkomponenten hinweg bereitstellt. Das folgende Beispiel erstellt ein `PasswordField` mit einem Label und Platzhaltertext.

<ComponentDemo 
path='/webforj/passwordfield?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/passwordfield/PasswordFieldView.java'
/>

## Feldwert {#field-value}

Die `PasswordField`-Komponente speichert und ruft ihren Wert als einfachen `String` ab, ähnlich wie ein `TextField`, jedoch mit verdeckter visueller Darstellung, um die Zeichen aus der Sicht zu verstecken.

Sie können den aktuellen Wert mit folgendem Befehl abrufen:

```java
passwordField.getValue();
```

:::warning sensible Daten
Obwohl das Feld den Inhalt visuell maskiert, ist der zurückgegebene Wert von `getValue()` immer noch ein einfacher String. Seien Sie sich dessen bewusst, wenn Sie mit sensiblen Daten umgehen, und verschlüsseln oder transformieren Sie diese vor der Speicherung.
:::

Um den Wert programmgesteuert zu setzen oder zurückzusetzen:

```java
passwordField.setValue("MeinGeheimnis123!");
```

Wenn kein Wert vom Benutzer eingegeben wurde und kein Standardwert festgelegt ist, gibt das Feld einen leeren String (`""`) zurück.

Dieses Verhalten ahmt das des nativen HTML `<input type="password">` nach, bei dem die `value`-Eigenschaft den aktuellen Eingang speichert.

## Verwendungen {#usages}

Das `PasswordField` eignet sich am besten für Szenarien, in denen die Erfassung oder Verarbeitung sensibler Informationen, wie Passwörter oder andere vertrauliche Daten, für Ihre App wesentlich ist. Hier sind einige Beispiele, wann das `PasswordField` verwendet werden sollte:

1. **Benutzerauthentifizierung und -registrierung**: Passwortfelder sind entscheidend in Apps, die Benutzerauthentifizierungs- oder Registrierungsprozesse beinhalten, bei denen eine sichere Passworteingabe erforderlich ist.

2. **Sichere Formulareingaben**: Bei der Gestaltung von Formularen, die die Eingabe sensibler Informationen wie Kreditkartendaten oder persönliche Identifikationsnummern (PINs) erfordern, gewährleistet die Verwendung eines `PasswordField` die Sicherheit der Daten.

3. **Kontoverwaltung und Profileinstellungen**: Passwortfelder sind wertvoll in Apps, die die Kontoverwaltung oder Profileinstellungen beinhalten, da Benutzer ihre Passwörter sicher ändern oder aktualisieren können.

## Passwortsichtbarkeit {#password-visibility}

Benutzer können den Wert der `PasswordField`-Komponente anzeigen, indem sie auf das Anzeige-Symbol klicken. Dies ermöglicht es Benutzern, zu überprüfen, was sie eingegeben haben, oder die Informationen in ihre Zwischenablage zu kopieren. Für hochsichere Umgebungen können Sie jedoch `setPasswordReveal()` verwenden, um das Anzeige-Symbol zu entfernen und zu verhindern, dass Benutzer den Wert sehen. Sie können mit der Methode `isPasswordReveal()` überprüfen, ob ein Benutzer das Anzeige-Symbol verwenden kann, um den Wert anzuzeigen.

## Musterabgleich {#pattern-matching}

Es wird dringend empfohlen, ein reguläres Ausdrucksmuster auf das `PasswordField` mit der Methode `setPattern()` anzuwenden. Dies ermöglicht es Ihnen, Zeichenregeln und strukturelle Anforderungen durchzusetzen und die Benutzer zu zwingen, sichere und konforme Anmeldedaten zu erstellen. Musterabgleich ist besonders nützlich, wenn starke Passwortregeln durchgesetzt werden, z. B. die Anforderung einer Kombination aus Groß- und Kleinbuchstaben, Zahlen und Symbolen.

Das Muster muss der Syntax eines [JavaScript-regulären Ausdrucks](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Guide/Regular_expressions) entsprechen, wie sie vom Browser interpretiert wird. Das interne `u` (Unicode) Flag wird verwendet, um die Validierung über alle Unicode-Codepunkte hinweg zu gewährleisten. Fügen Sie **keine** Schrägstriche (`/`) um das Muster ein.

Im folgenden Snippet erfordert das Muster mindestens einen Kleinbuchstaben, einen Großbuchstaben, eine Zahl und eine Mindestlänge von 8 Zeichen.

```java
passwordField.setPattern("(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,}");
```

Wenn das Muster fehlt oder ungültig ist, wird keine Validierung angewendet.

:::tip
Verwenden Sie `setLabel()`, um ein klares Label bereitzustellen, das den Zweck des Passwortfelds beschreibt. Um den Benutzern zu helfen, die Anforderungen an Passwörter zu verstehen, verwenden Sie `setHelperText()`, um Hinweise oder Regeln direkt unter dem Feld anzuzeigen.
:::


## Minimale und maximale Länge {#minimum-and-maximum-length}

Sie können die zulässige Länge der Passwort-Eingabe steuern, indem Sie `setMinLength()` und `setMaxLength()` auf dem `PasswordField` verwenden.

Die Methode `setMinLength()` definiert die Mindestanzahl von Zeichen, die ein Benutzer in das Feld eingeben muss, um die Validierung zu bestehen. Dieser Wert muss eine nicht negative Ganzzahl sein und darf die maximale Länge nicht überschreiten, wenn diese festgelegt ist.

```java
passwordField.setMinLength(8); // Mindestanzahl 8 Zeichen
```

Wenn der Benutzer weniger Zeichen als die Mindestanzahl eingibt, schlägt die Eingabe die validierungsbeschränkung fehl. Diese Validierung wird nur angewendet, wenn der Wert des Feldes vom Benutzer verändert wird.

Die Methode `setMaxLength()` legt die maximale Anzahl von Zeichen fest, die im Feld zulässig sind. Der Wert muss 0 oder mehr betragen. Wenn er nicht definiert ist oder auf einen ungültigen Wert gesetzt ist, hat das Feld kein oberes Zeichenlimit.

```java
passwordField.setMaxLength(20); // Maximal 20 Zeichen
```

Wenn die Eingabe die maximale Zeichenbeschränkung überschreitet, schlägt das Feld die Validierungsbeschränkung fehl. Wie bei der Mindestanzahl gilt diese Regel nur, wenn der Benutzer den Wert des Feldes aktualisiert.

:::tip
Verwenden Sie sowohl `setMinLength()` als auch `setMaxLength()`, um effektive Eingabebeschränkungen zu schaffen. Weitere Informationen finden Sie in der [HTML-Dokumentation zu Längenbeschränkungen](https://developer.mozilla.org/en-US/docs/Web/HTML/Element/input#minlength).
:::


## Beste Praktiken {#best-practices}

Da die `PasswordField`-Komponente häufig mit sensiblen Informationen assoziiert wird, sollten Sie die folgenden bewährten Verfahren bei der Verwendung des `PasswordField` berücksichtigen:

- **Bereitstellung von Rückmeldungen zur Passwortstärke**: Integrieren Sie Passwortstärkeindikatoren oder Rückmeldemechanismen, um Benutzern zu helfen, starke und sichere Passwörter zu erstellen. Bewerten Sie Faktoren wie Länge, Komplexität sowie eine Mischung aus Groß- und Kleinbuchstaben, Zahlen und Sonderzeichen.

- **Durchsetzung der Passwortspeicherung**: Speichern Sie niemals Passwörter im Klartext. Implementieren Sie stattdessen angemessene Sicherheitsmaßnahmen, um Passwörter in Ihrer App sicher zu verarbeiten und zu speichern. Verwenden Sie branchenübliche Verschlüsselungsalgorithmen für Passwörter und andere sensible Daten.

- **Passwortbestätigung**: Fügen Sie ein zusätzliches Bestätigungsfeld hinzu, wenn ein Benutzer ein Passwort ändert oder erstellt. Diese Maßnahme hilft, die Wahrscheinlichkeit von Tippfehlern zu minimieren und sicherzustellen, dass Benutzer ihr gewünschtes Passwort korrekt eingeben.

- **Passwortzurücksetzung zulassen**: Wenn Ihre App Benutzerkonten umfasst, bieten Sie eine Option für Benutzer an, ihr Passwort zurückzusetzen. Dies könnte in Form einer Funktion "Passwort vergessen" geschehen, die einen Passwortwiederherstellungsprozess einleitet.

- **Barrierefreiheit**: Richten Sie das `PasswordField` mit Blick auf die Barrierefreiheit ein, damit es die Barrierefreiheitsstandards erfüllt, z. B. durch Bereitstellung angemessener Labels und Kompatibilität mit unterstützenden Technologien.
