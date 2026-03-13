---
title: Login
sidebar_position: 70
_i18n_hash: d5724547e5173f77895c401018612328
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-login" />
<DocChip chip='since' label='24.01' />
<JavadocLink type="login" location="com/webforj/component/login/Login" top='true'/>

Die `Login`-Komponente vereinfacht die Benutzerauthentifizierung, indem sie einen einsatzbereiten Anmeldedialog mit Benutzername- und Passwortfeldern bereitstellt. Sie umfasst Funktionen wie Eingabevalidierung, anpassbare Beschriftungen und Nachrichten, Steuerelemente zur Passwortsichtbarkeit und Unterstützung für zusätzliche benutzerdefinierte Felder.

<!-- INTRO_END -->

## Erstellen eines `Login`-Dialogs {#creating-a-login-dialog}

Erstellen Sie einen `Login`-Dialog, indem Sie die Komponente instanziieren und `open()` aufrufen, um ihn anzuzeigen. Der Dialog enthält standardmäßig Benutzername- und Passwortfelder, Eingabevalidierung und eine Anmelden-Schaltfläche.

<ComponentDemo 
path='/webforj/loginbasic?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/login/LoginBasicView.java'
height = '450px'
/>

## Anmelden {#login-submission}

Wenn Benutzer ihren Benutzernamen und ihr Passwort eingeben, validiert die `Login`-Komponente diese Eingaben als erforderliche Felder. Sobald die Validierung bestanden wird, wird ein Formularübermittlungsereignis ausgelöst, das die eingegebenen Anmeldeinformationen übermittelt. Um mehrere Übermittlungen zu verhindern, wird die [Anmelden] Schaltfläche sofort deaktiviert.

Das folgende Beispiel zeigt eine grundlegende `Login`-Komponente. Wenn der Benutzername und das Passwort beide auf "admin" gesetzt sind, wird der Anmeldedialog geschlossen und eine [Abmelden] Schaltfläche angezeigt. Wenn die Anmeldeinformationen nicht übereinstimmen, wird die standardmäßige Fehlermeldung angezeigt.

<ComponentDemo 
path='/webforj/loginsubmission?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/login/LoginSubmissionView.java'
height = '450px'
/>

:::info Deaktivierung der [Anmelden]-Schaltfläche
Standardmäßig deaktiviert `Login` sofort die [Anmelden]-Schaltfläche, sobald die Komponente die Anmeldeeingaben als korrekt validiert, um mehrere Übermittlungen zu verhindern. Sie können die [Anmelden]-Schaltfläche mit der Methode `setEnabled(true)` wieder aktivieren.
:::

:::tip Ermöglichen leerer Passwörter
Sie können Benutzern erlauben, sich nur mit einem Benutzernamen anzumelden, indem Sie die Methode `setEmptyPassword(true)` verwenden.
:::

## Formularaktion <DocChip chip='since' label='25.10' />{#form-action}

Die `Login`-Komponente kann Formulardaten direkt an eine angegebene URL übermitteln, anstatt die Übermittlung über das Ereignis zur Formularübermittlung zu behandeln. Wenn eine Aktions-URL festgelegt ist, führt das Formular eine standardmäßige POST-Anforderung mit dem Benutzernamen und Passwort als Formularparameter aus.

```java
Login login = new Login();
login.setAction("/api/auth");
```

Bei Verwendung von `setAction()` umgeht die Formularübermittlung das `LoginSubmitEvent` und führt stattdessen eine traditionelle HTTP-POST-Anforderung an den angegebenen Endpunkt aus. Der Benutzername und das Passwort werden als Formularparameter mit den Namen "username" und "password" übermittelt. Benutzerdefinierte Felder mit einem Namen-Attribut werden ebenfalls in die POST-Anforderung einbezogen.

:::tip 
Wenn keine Aktions-URL festgelegt ist, wird die Formularübermittlung über das `LoginSubmitEvent` bearbeitet, sodass Sie die Anmeldeinformationen programmgesteuert auf der Serverseite verarbeiten können.
:::

## Internationalisierung (i18n) {#internationalization-i18n}

Die Titel, Beschreibungen, Beschriftungen und Nachrichten innerhalb der `Login`-Komponente sind vollständig anpassbar mit der Klasse `LoginI18n`. Diese Flexibilität ermöglicht es Ihnen, die Anmeldeschnittstelle an spezifische Lokalisierungsanforderungen oder Personalisierungspräferenzen anzupassen.

<ComponentDemo 
path='/webforj/logininternationalization?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/login/LoginInternationalizationView.java'
height = '500px'
/>

## Benutzerdefinierte Felder {#custom-fields}

Die `Login`-Komponente enthält mehrere Slots, die es Ihnen ermöglichen, bei Bedarf zusätzliche Felder hinzuzufügen. Benutzerdefinierte Felder werden automatisch gesammelt, wenn das Formular übermittelt wird, und können über die Datenkarte des Übermittlungsereignisses abgerufen werden.

Der folgende Login hat ein benutzerdefiniertes Feld für eine Kunden-ID hinzugefügt. Dies kann Ihnen helfen, Unternehmen oder Abteilungen zu verwalten, die Inhalte gemeinsam über mehrere Benutzer hinweg verwenden.

<ComponentDemo 
path='/webforj/logincustomfields?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/login/LoginCustomFieldsView.java'
cssURL='/css/login/loginCustomFields.css'
height = '700px'
/>

:::info Name erforderlich
Benutzerdefinierte Felder müssen einen Namen haben, der mit `setName()` festgelegt wird, um in der Formularübermittlung einbezogen zu werden. Der Name wird als Schlüssel verwendet, um den Wert des Felds aus `event.getData()` abzurufen.
:::

## Abbrechen-Schaltfläche {#cancel-button}

`Login` enthält eine [Abbrechen]-Schaltfläche, die standardmäßig ausgeblendet ist. Dies ist besonders nützlich, wenn ein Benutzer versucht, auf einen eingeschränkten Bereich der App zuzugreifen und die Möglichkeit benötigt, zu seinem vorherigen Standort zurückzukehren, ohne die Anmeldung abzuschließen.

Um die Abbrechen-Schaltfläche sichtbar zu machen, geben Sie eine Beschriftung dafür an. Sie können auch auf Abbrechen-Ereignisse hören, um die Stornierung entsprechend zu behandeln.

<ComponentDemo 
path='/webforj/logincancelbutton?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/login/LoginCancelButtonView.java'
height = '450px'
/>

:::tip Elemente ausblenden
Um ein Element auszublenden, setzen Sie seine Beschriftung auf einen leeren String. Dies ermöglicht es Ihnen, die Sichtbarkeit umzuschalten, ohne die Komponente aus Ihrem Code zu entfernen.
:::

## Passwortmanager {#password-managers}

Diese Komponente funktioniert mit browserbasierten Passwortmanagern, um den Anmeldeprozess zu vereinfachen. In Chromium-basierten Browsern integriert sie sich in die [`PasswordCredential`](https://developer.mozilla.org/en-US/docs/Web/API/PasswordCredential) API, die Folgendes bietet:

- **Auto-Ausfüllen**: Der Browser kann die Benutzername- und Passwortfelder automatisch ausfüllen, wenn der Benutzer Anmeldeinformationen für die Website gespeichert hat.
- **Anmeldeinformationen verwalten**: Nach dem Anmelden kann der Browser den Benutzer auffordern, neue Anmeldeinformationen zu speichern, wodurch zukünftige Anmeldungen schneller und einfacher werden.
- **Anmeldebedingungen auswählen**: Wenn mehrere Anmeldeinformationen gespeichert sind, kann der Browser dem Benutzer die Möglichkeit bieten, aus einem der gespeicherten Sätze auszuwählen.

## Styling {#styling}

<TableBuilder name="Login" />
