---
title: Login
sidebar_position: 70
_i18n_hash: 59a9ab8cb7ba550b955ab83de0c6d878
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-login" />
<DocChip chip='since' label='24.01' />
<JavadocLink type="login" location="com/webforj/component/login/Login" top='true'/>

Die `Login`-Komponente vereinfacht die Benutzeranmeldung, indem sie einen sofort einsatzbereiten Anmelde-Dialog mit Benutzernamen- und Passwortfeldern bereitstellt. Sie umfasst Funktionen wie Eingabevalidierung, anpassbare Beschriftungen und Nachrichten, Steuerungen für die Passwortsichtbarkeit und Unterstützung für zusätzliche benutzerdefinierte Felder.

<!-- INTRO_END -->

## Erstellen eines `Login`-Dialogs {#creating-a-login-dialog}

Erstellen Sie einen `Login`-Dialog, indem Sie die Komponente instanziieren und `open()` aufrufen, um ihn anzuzeigen. Der Dialog umfasst standardmäßig Benutzername- und Passwortfelder, Eingabevalidierung und eine Anmelde-Schaltfläche.

<ComponentDemo 
path='/webforj/loginbasic?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/login/LoginBasicView.java'
height = '450px'
/>

## Anmelden {#login-submission}

Wenn Benutzer ihren Benutzernamen und ihr Passwort eingeben, validiert die `Login`-Komponente diese Eingaben als erforderliche Felder. Sobald die Validierung erfolgreich ist, wird ein Ereignis zur Formularübermittlung ausgelöst, das die eingegebenen Anmeldeinformationen übermittelt. Um mehrere Übermittlungen zu verhindern, wird die [Anmelden]-Schaltfläche sofort deaktiviert.

Die folgende Darstellung zeigt eine grundlegende `Login`-Komponente. Wenn der Benutzername und das Passwort beide auf `"admin"` eingestellt sind, wird der Anmelde-Dialog geschlossen und eine [Abmelden]-Schaltfläche angezeigt. Wenn die Anmeldeinformationen nicht übereinstimmen, wird die standardmäßige Fehlermeldung angezeigt.

<ComponentDemo 
path='/webforj/loginsubmission?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/login/LoginSubmissionView.java'
height = '450px'
/>

:::info Deaktivierung der [Anmelden]-Schaltfläche
Standardmäßig deaktiviert `Login` sofort die [Anmelden]-Schaltfläche, sobald die Komponente die Anmeldeeingaben als korrekt validiert, um mehrere Übermittlungen zu verhindern. Sie können die [Anmelden]-Schaltfläche mit der Methode `setEnabled(true)` wieder aktivieren.
:::

:::tip Erlauben leerer Passwörter
Sie können es Benutzern ermöglichen, sich nur mit einem Benutzernamen anzumelden, indem Sie die Methode `setEmptyPassword(true)` verwenden.
:::

## Formularaktion <DocChip chip='since' label='25.10' />{#form-action}

Die `Login`-Komponente kann Formulardaten direkt an eine angegebene URL übermitteln, anstatt die Übermittlung über das Ereignis zur Formularübermittlung zu verarbeiten. Wenn eine Aktions-URL festgelegt ist, führt das Formular eine standardmäßige POST-Anfrage mit dem Benutzernamen und Passwort als Formularparameter durch.

```java
Login login = new Login();
login.setAction("/api/auth");
```

Bei Verwendung von `setAction()` umgeht die Formularübermittlung das `LoginSubmitEvent` und führt stattdessen eine traditionelle HTTP-POST-Anfrage an den angegebenen Endpunkt durch. Der Benutzername und das Passwort werden als Formularparameter mit den Namen `"username"` und `"password"` gesendet. Benutzerdefinierte Felder mit einem Namensattribut werden ebenfalls in der POST-Anfrage berücksichtigt.

:::tip 
Wenn keine Aktions-URL festgelegt ist, wird die Formularübermittlung über das `LoginSubmitEvent` verarbeitet, sodass Sie die Anmeldeinformationen programmgesteuert auf der Serverseite bearbeiten können.
:::

## Internationalisierung (i18n) {#internationalization-i18n}

Die Titel, Beschreibungen, Beschriftungen und Nachrichten innerhalb der `Login`-Komponente sind vollständig anpassbar mit der Klasse `LoginI18n`. Diese Flexibilität ermöglicht es Ihnen, die Anmeldeoberfläche an spezifische Lokalisierungsanforderungen oder Personalisierungspräferenzen anzupassen.

<ComponentDemo 
path='/webforj/logininternationalization?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/login/LoginInternationalizationView.java'
height = '600px'
/>

## Benutzerdefinierte Felder {#custom-fields}

Die `Login`-Komponente umfasst mehrere Slots, die es Ihnen ermöglichen, nach Bedarf zusätzliche Felder hinzuzufügen. Benutzerdefinierte Felder werden automatisch gesammelt, wenn das Formular übermittelt wird, und können über die Datenkarte des Ereignisses zur Übermittlung abgerufen werden.

Die folgende Anmeldung hat ein benutzerdefiniertes Feld für eine Kunden-ID hinzugefügt. Dies kann Ihnen helfen, Unternehmen oder Abteilungen zu verwalten, die Inhalte über mehrere Benutzer teilen.

<ComponentDemo 
path='/webforj/logincustomfields?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/login/LoginCustomFieldsView.java'
cssURL='/css/login/loginCustomFields.css'
height = '700px'
/>

:::info Name erforderlich
Benutzerdefinierte Felder müssen einen Namen haben, der mit `setName()` festgelegt wird, um in die Formularübermittlung aufgenommen zu werden. Der Name wird als Schlüssel verwendet, um den Wert des Felds von `event.getData()` abzurufen.
:::

## Abbrechen-Schaltfläche {#cancel-button}

`Login` umfasst eine [Abbrechen]-Schaltfläche, die standardmäßig verborgen ist. Dies ist besonders nützlich, wenn ein Benutzer versucht, auf einen eingeschränkten Bereich der Anwendung zuzugreifen und eine Option benötigt, um ohne Abschluss der Anmeldung zu einem vorherigen Standort zurückzukehren.

Um die Abbrechen-Schaltfläche sichtbar zu machen, geben Sie eine Beschriftung dafür an. Sie können auch Abbrechen-Ereignisse abhören, um die Abbrechung entsprechend zu behandeln.

<ComponentDemo 
path='/webforj/logincancelbutton?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/login/LoginCancelButtonView.java'
height = '450px'
/>

:::tip Elemente ausblenden
Um ein Element auszublenden, setzen Sie seine Beschriftung auf eine leere Zeichenfolge. Dies ermöglicht es Ihnen, die Sichtbarkeit umzuschalten, ohne die Komponente aus Ihrem Code zu entfernen.
:::

## Passwortmanager {#password-managers}

Diese Komponente funktioniert mit browserbasierten Passwortmanagern, um den Anmeldeprozess zu vereinfachen. In Chromium-basierten Browsern integriert sie sich mit der [`PasswordCredential`](https://developer.mozilla.org/en-US/docs/Web/API/PasswordCredential) API, die Folgendes bereitstellt:

- **Auto-fill**: Der Browser kann die Benutzername- und Passwortfelder automatisch ausfüllen, wenn der Benutzer Anmeldeinformationen für die Seite gespeichert hat.
- **Anmeldeinformationsverwaltung**: Nach der Anmeldung kann der Browser den Benutzer auffordern, neue Anmeldeinformationen zu speichern, was zukünftige Anmeldungen schneller und einfacher macht.
- **Auswahl der Anmeldeinformationen**: Wenn mehrere Anmeldeinformationen gespeichert sind, kann der Browser dem Benutzer eine Auswahl anbieten, um aus einem der gespeicherten Sätze auszuwählen.

## Styling {#styling}

<TableBuilder name="Login" />
