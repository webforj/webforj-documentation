---
title: Login
sidebar_position: 70
sidebar_class_name: updated-content
_i18n_hash: cdcad4b5ef5d3ba0bd84e4d9deac49b5
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-login" />
<DocChip chip='since' label='24.01' />
<JavadocLink type="login" location="com/webforj/component/login/Login" top='true'/>

Die `Login`-Komponente vereinfacht die Benutzerauthentifizierung, indem sie einen sofort einsatzbereiten Anmelde-Dialog mit Feldern für Benutzername und Passwort bereitstellt. Sie enthält Funktionen wie Eingabevalidierung, anpassbare Beschriftungen und Nachrichten, Steuerungen für die Passwortsichtbarkeit und Unterstützung für zusätzliche benutzerdefinierte Felder.

<ComponentDemo 
path='/webforj/loginbasic?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/login/LoginBasicView.java'
height = '450px'
/>

## Anmeldung {#login-submission}

Wenn Benutzer ihren Benutzernamen und ihr Passwort eingeben, validiert die `Login`-Komponente diese Eingaben als erforderliche Felder. Sobald die Validierung abgeschlossen ist, wird ein Formularüberereignis ausgelöst, das die eingegebenen Anmeldedaten übermittelt. Um mehrere Übermittlungen zu verhindern, wird der [Anmelden]-Button sofort deaktiviert.

Das folgende Beispiel zeigt eine grundlegende `Login`-Komponente. Wenn der Benutzername und das Passwort beide auf `"admin"` gesetzt sind, schließt sich der Anmelde-Dialog und ein [Abmelden]-Button erscheint. Wenn die Anmeldedaten nicht übereinstimmen, wird die Standardfehlermeldung angezeigt.

<ComponentDemo 
path='/webforj/loginsubmission?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/login/LoginSubmissionView.java'
height = '450px'
/>

:::info Deaktivierung des [Anmelden]-Buttons
Standardmäßig deaktiviert `Login` sofort den [Anmelden]-Button, nachdem die Eingaben zur Anmeldung als korrekt validiert wurden, um mehrere Übermittlungen zu verhindern. Sie können den [Anmelden]-Button mit der Methode `setEnabled(true)` wieder aktivieren.
:::

:::tip Erlauben von leeren Passwörtern
Sie können es Benutzern erlauben, sich nur mit einem Benutzernamen anzumelden, indem Sie die Methode `setEmptyPassword(true)` verwenden.
:::

## Formularaktion <DocChip chip='since' label='25.10' />{#form-action}

Die `Login`-Komponente kann Formulardaten direkt an eine bestimmte URL übermitteln, anstatt die Übermittlung über das Ereignis zur Einreichung zu handhaben. Wenn eine Aktions-URL festgelegt ist, führt das Formular eine standardmäßige POST-Anfrage mit Benutzername und Passwort als Formularparameter durch.

```java
Login login = new Login();
login.setAction("/api/auth");
```

Bei der Verwendung von `setAction()` umgeht die Formularübermittlung das `LoginSubmitEvent` und führt stattdessen eine traditionelle HTTP-POST-Anfrage an den angegebenen Endpunkt durch. Der Benutzername und das Passwort werden als Formularparameter mit den Namen "username" und "password" gesendet. Benutzerdefinierte Felder mit einem Namensattribut werden ebenfalls in die POST-Anfrage einbezogen.

:::tip 
Wenn keine Aktions-URL festgelegt ist, wird die Formularübermittlung über das `LoginSubmitEvent` gehandhabt, sodass Sie die Anmeldedaten programmgesteuert auf der Serverseite verarbeiten können.
:::

## Internationalisierung (i18n) {#internationalization-i18n}

Die Titel, Beschreibungen, Beschriftungen und Nachrichten innerhalb der `Login`-Komponente sind vollständig anpassbar über die Klasse `LoginI18n`. Diese Flexibilität ermöglicht es Ihnen, die Anmeldeschnittstelle an spezifische Lokalisierungsanforderungen oder Personalisierungspräferenzen anzupassen.

<ComponentDemo 
path='/webforj/logininternationalization?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/login/LoginInternationalizationView.java'
height = '500px'
/>

## Benutzerdefinierte Felder {#custom-fields}

Die `Login`-Komponente enthält mehrere Slots, die es Ihnen ermöglichen, zusätzliche Felder nach Bedarf hinzuzufügen. Benutzerdefinierte Felder werden automatisch gesammelt, wenn das Formular übermittelt wird, und können über die Datenkarte des Übermittlungsereignisses abgerufen werden.

Der folgende Login hat ein benutzerdefiniertes Feld für eine Kunden-ID hinzugefügt. Dies kann Ihnen helfen, Unternehmen oder Abteilungen zu verwalten, die Inhalte gemeinsam über mehrere Benutzer hinweg teilen.

<ComponentDemo 
path='/webforj/logincustomfields?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/login/LoginCustomFieldsView.java'
cssURL='/css/login/loginCustomFields.css'
height = '700px'
/>

:::info Name erforderlich
Benutzerdefinierte Felder müssen einen Namen haben, der über `setName()` festgelegt wird, um in die Formularübermittlung aufgenommen zu werden. Der Name wird als Schlüssel verwendet, um den Wert des Feldes aus `event.getData()` abzurufen.
:::

## Abbrechen-Button {#cancel-button}

`Login` enthält einen [Abbrechen]-Button, der standardmäßig verborgen ist. Dies ist besonders nützlich, wenn ein Benutzer versucht, auf einen eingeschränkten Bereich der App zuzugreifen und eine Option benötigt, um zu seinem vorherigen Standort zurückzukehren, ohne die Anmeldung abzuschließen.

Um den Abbrechen-Button sichtbar zu machen, geben Sie eine Beschriftung dafür an. Sie können auch auf Abbrechen-Ereignisse hören, um die Stornierung entsprechend zu verarbeiten.

<ComponentDemo 
path='/webforj/logincancelbutton?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/login/LoginCancelButtonView.java'
height = '450px'
/>

:::tip Ausblenden von Elementen
Um ein Element auszublenden, setzen Sie seine Beschriftung auf eine leere Zeichenfolge. Auf diese Weise können Sie die Sichtbarkeit umschalten, ohne die Komponente aus Ihrem Code zu entfernen.
:::

## Passwortmanager {#password-managers}

Diese Komponente funktioniert mit browserbasierten Passwortmanagern, um den Anmeldeprozess zu vereinfachen. In Chromium-basierten Browsern integriert sie sich in die [`PasswordCredential`](https://developer.mozilla.org/en-US/docs/Web/API/PasswordCredential) API, die Folgendes bietet:

- **Automatische Ausfüllung**: Der Browser kann automatisch die Felder für Benutzernamen und Passwort ausfüllen, wenn der Benutzer für die Seite gespeicherte Anmeldeinformationen hat.
- **Verwaltung von Anmeldeinformationen**: Nach der Anmeldung kann der Browser den Benutzer auffordern, neue Anmeldeinformationen zu speichern, was zukünftige Anmeldungen schneller und einfacher macht.
- **Auswahl von Anmeldeinformationen**: Wenn mehrere Anmeldeinformationen gespeichert sind, kann der Browser dem Benutzer die Möglichkeit bieten, aus einem der gespeicherten Sätze auszuwählen.

## Styling {#styling}

<TableBuilder name="Login" />
