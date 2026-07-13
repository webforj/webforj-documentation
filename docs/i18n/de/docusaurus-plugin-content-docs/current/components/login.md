---
title: Login
sidebar_position: 70
description: >-
  Display an authentication dialog with the Login component, handling
  submission, validation, custom fields, and form action URLs.
_i18n_hash: 5016fc4d15ba24b16c61eed8e6e272ee
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-login" />
<DocChip chip='since' label='24.01' />
<JavadocLink type="login" location="com/webforj/component/login/Login" top='true'/>

Die `Login`-Komponente vereinfacht die Benutzer-Authentifizierung, indem sie einen sofort einsatzbereiten Anmelde-Dialog mit Benutzernamen- und Passwortfeldern bereitstellt. Sie umfasst Funktionen wie Eingabevalidierung, anpassbare Beschriftungen und Nachrichten, Steuerungen für die Sichtbarkeit des Passworts sowie Unterstützung für zusätzliche benutzerdefinierte Felder.

<!-- INTRO_END -->

## Erstellen eines `Login`-Dialogs {#creating-a-login-dialog}

Erstellen Sie einen `Login`-Dialog, indem Sie die Komponente instanziieren und `open()` aufrufen, um ihn anzuzeigen. Der Dialog umfasst standardmäßig Benutzername- und Passwortfelder, Eingabevalidierung und eine Anmelde-Schaltfläche.

<ComponentDemo
path='/webforj/loginbasic'
files={['src/main/java/com/webforj/samples/views/login/LoginBasicView.java']}
height='450px'
/>

## Anmeldung {#login-submission}

Wenn Benutzer ihren Benutzernamen und ihr Passwort eingeben, validiert die `Login`-Komponente diese Eingaben als erforderliche Felder. Sobald die Validierung besteht, wird ein Formularüberereignis ausgelöst, das die eingegebenen Anmeldeinformationen übermittelt. Um mehrfachen Übermittlungen vorzubeugen, wird die [Anmelden]-Schaltfläche sofort deaktiviert.

Das folgende Beispiel zeigt eine grundlegende `Login`-Komponente. Wenn der Benutzername und das Passwort jeweils auf `"admin"` gesetzt sind, wird der Anmelde-Dialog geschlossen und eine [Abmelden]-Schaltfläche erscheint. Wenn die Anmeldeinformationen nicht übereinstimmen, wird die Standardfehlermeldung angezeigt.

<ComponentDemo
path='/webforj/loginsubmission'
files={['src/main/java/com/webforj/samples/views/login/LoginSubmissionView.java']}
height='450px'
/>

:::info Deaktivierung der [Anmelden] Schaltfläche
Standardmäßig deaktiviert `Login` sofort die [Anmelden]-Schaltfläche, sobald die Komponente die Anmeldeeingaben als korrekt validiert, um mehrfache Übermittlungen zu verhindern. Sie können die [Anmelden]-Schaltfläche mit der Methode `setEnabled(true)` wieder aktivieren.
:::

:::tip Zulassung von leeren Passwörtern
Sie können es Benutzern ermöglichen, sich nur mit einem Benutzernamen anzumelden, indem Sie die Methode `setEmptyPassword(true)` verwenden.
:::

## Formularaktion <DocChip chip='since' label='25.10' />{#form-action}

Die `Login`-Komponente kann Formulardaten direkt an eine angegebene URL übermitteln, anstatt die Übermittlung über das Übermittlungsereignis zu handhaben. Wenn eine Aktions-URL festgelegt ist, führt das Formular eine standardmäßige POST-Anfrage mit den Anmeldeinformationen als Formularparameter durch.

```java
Login login = new Login();
login.setAction("/api/auth");
```

Bei Verwendung von `setAction()` umgeht die FormularÜbermittlung das `LoginSubmitEvent` und führt stattdessen eine traditionelle HTTP-POST-Anfrage an den angegebenen Endpunkt durch. Der Benutzername und das Passwort werden als Formularparameter mit den Namen `"username"` und `"password"` übermittelt. Benutzerdefinierte Felder mit einem Namensattribut werden ebenfalls in der POST-Anfrage enthalten.

:::tip
Wenn keine Aktions-URL festgelegt ist, wird die Formularübermittlung über das `LoginSubmitEvent` gehandhabt, sodass Sie die Anmeldeinformationen programmgesteuert auf der Serverseite verarbeiten können.
:::

## Internationalisierung (i18n) {#internationalization-i18n}

Die Titel, Beschreibungen, Beschriftungen und Nachrichten innerhalb der `Login`-Komponente sind vollständig anpassbar, indem die Klasse `LoginI18n` verwendet wird. Diese Flexibilität ermöglicht es Ihnen, die Anmeldeoberfläche an spezifische Lokalisierungsanforderungen oder Personalisierungsvorlieben anzupassen.

<ComponentDemo
path='/webforj/logininternationalization'
files={['src/main/java/com/webforj/samples/views/login/LoginInternationalizationView.java']}
height='600px'
/>

## Benutzerdefinierte Felder {#custom-fields}

Die `Login`-Komponente enthält mehrere Slots, die es Ihnen ermöglichen, bei Bedarf zusätzliche Felder hinzuzufügen. Benutzerdefinierte Felder werden automatisch gesammelt, wenn das Formular übermittelt wird, und können über die Datenkarte des Übermittlungsereignisses abgerufen werden.

Das folgende Login enthält ein benutzerdefiniertes Feld für eine Kunden-ID. Dies kann Ihnen helfen, Unternehmen oder Abteilungen zu verwalten, die gemeinsam genutzte Inhalte über mehrere Benutzer hinweg haben.

<ComponentDemo
path='/webforj/logincustomfields'
files={[
  'src/main/java/com/webforj/samples/views/login/LoginCustomFieldsView.java',
  'src/main/frontend/css/login/loginCustomFields.css',
]}
height='700px'
/>

:::info Name erforderlich
Benutzerdefinierte Felder müssen einen Namen haben, der mit `setName()` festgelegt wird, um in der Formularübermittlung enthalten zu sein. Der Name wird als Schlüssel verwendet, um den Wert des Feldes von `event.getData()` abzurufen.
:::

## Abbrechen-Schaltfläche {#cancel-button}

`Login` enthält eine [Abbrechen]-Schaltfläche, die standardmäßig ausgeblendet ist. Dies ist besonders nützlich, wenn ein Benutzer versucht, auf einen eingeschränkten Bereich der App zuzugreifen und eine Möglichkeit benötigt, ohne Abschluss des Logins zu seinem vorherigen Standort zurückzukehren.

Um die Abbrechen-Schaltfläche sichtbar zu machen, geben Sie eine Beschriftung dafür an. Sie können auch auf Abbrechen-Ereignisse hören, um die Abmeldung angemessen zu behandeln.

<ComponentDemo
path='/webforj/logincancelbutton'
files={['src/main/java/com/webforj/samples/views/login/LoginCancelButtonView.java']}
height='450px'
/>

:::tip Elemente verstecken
Um ein Element zu verstecken, setzen Sie seine Beschriftung auf einen leeren String. Dies ermöglicht es Ihnen, die Sichtbarkeit umzuschalten, ohne die Komponente aus Ihrem Code zu entfernen.
:::

## Passwortmanager {#password-managers}

Diese Komponente funktioniert mit browserbasierten Passwortmanagern, um den Anmeldeprozess zu vereinfachen. In Chromium-basierten Browsern integriert sie sich mit der [`PasswordCredential`](https://developer.mozilla.org/en-US/docs/Web/API/PasswordCredential) API, die Folgendes bietet:

- **Auto-fill**: Der Browser kann automatisch die Felder für Benutzername und Passwort ausfüllen, wenn der Benutzer gespeicherte Anmeldeinformationen für die Website hat.
- **Anmeldeinformationsverwaltung**: Nach dem Einloggen kann der Browser den Benutzer auffordern, neue Anmeldeinformationen zu speichern, was zukünftige Anmeldungen schneller und einfacher macht.
- **Auswahl von Anmeldeinformationen**: Wenn mehrere Anmeldeinformationen gespeichert sind, kann der Browser dem Benutzer die Auswahl eines der gespeicherten Sets anbieten.

## Styling {#styling}

<TableBuilder name="Login" />
