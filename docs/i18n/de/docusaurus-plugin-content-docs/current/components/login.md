---
title: Login
sidebar_position: 70
_i18n_hash: 929bacbc38791adc906102078bdd6bfa
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-login" />
<DocChip chip='since' label='24.01' />
<JavadocLink type="login" location="com/webforj/component/login/Login" top='true'/>

Die `Login`-Komponente vereinfacht die Benutzerauthentifizierung, indem sie einen sofort einsatzbereiten Anmelde-Dialog mit Benutzername- und Passwortfeldern bereitstellt. Sie umfasst Funktionen wie Eingabevalidierung, anpassbare Labels und Nachrichten, Steuerungen zur Sichtbarkeit des Passworts und die Unterstützung zusätzlicher benutzerdefinierter Felder.

<!-- INTRO_END -->

## Erstellung eines `Login`-Dialogs {#creating-a-login-dialog}

Erstellen Sie einen `Login`-Dialog, indem Sie die Komponente instanziieren und `open()` aufrufen, um ihn anzuzeigen. Standardmäßig enthält der Dialog Benutzername- und Passwortfelder, Eingabevalidierung und eine Anmelden-Schaltfläche.

<ComponentDemo
path='/webforj/loginbasic'
files={['src/main/java/com/webforj/samples/views/login/LoginBasicView.java']}
height='450px'
/>

## Anmeldung {#login-submission}

Wenn Benutzer ihren Benutzername und ihr Passwort eingeben, validiert die `Login`-Komponente diese Eingaben als Pflichtfelder. Sobald die Validierung erfolgreich ist, wird ein Formularübermittlungsereignis ausgelöst, das die eingegebenen Anmeldeinformationen übermittelt. Um mehrere Übermittlungen zu verhindern, wird die [Anmelden]-Schaltfläche sofort deaktiviert.

Folgendes zeigt eine grundlegende `Login`-Komponente. Wenn der Benutzername und das Passwort beide auf `"admin"` gesetzt sind, wird der Anmelde-Dialog geschlossen und eine [Abmelden]-Schaltfläche wird angezeigt. Wenn die Anmeldeinformationen nicht übereinstimmen, wird die standardmäßige Fehlermeldung angezeigt.

<ComponentDemo
path='/webforj/loginsubmission'
files={['src/main/java/com/webforj/samples/views/login/LoginSubmissionView.java']}
height='450px'
/>

:::info Deaktivierung der [Anmelden]-Schaltfläche
Standardmäßig deaktiviert `Login` sofort die [Anmelden]-Schaltfläche, sobald die Komponente die Anmeldeeingaben als korrekt validiert, um mehrere Übermittlungen zu verhindern. Sie können die [Anmelden]-Schaltfläche mit der Methode `setEnabled(true)` wieder aktivieren.
:::

:::tip Zulassen von leeren Passwörtern
Sie können Benutzern erlauben, sich nur mit einem Benutzernamen anzumelden, indem Sie die Methode `setEmptyPassword(true)` verwenden.
:::

## Formularaktion <DocChip chip='since' label='25.10' />{#form-action}

Die `Login`-Komponente kann Formulardaten direkt an eine bestimmte URL übermitteln, anstatt die Übermittlung über das Übermittlungsereignis zu verarbeiten. Wenn eine Aktions-URL gesetzt ist, führt das Formular eine standardmäßige POST-Anfrage mit Benutzername und Passwort als Formularparameter aus.

```java
Login login = new Login();
login.setAction("/api/auth");
```

Bei Verwendung von `setAction()` umgeht die Formularübermittlung das `LoginSubmitEvent` und führt stattdessen eine traditionelle HTTP-POST-Anfrage an den angegebenen Endpunkt durch. Der Benutzername und das Passwort werden als Formularparameter mit den Namen `"username"` und `"password"` gesendet. Benutzerdefinierte Felder mit einem Namensattribut werden ebenfalls in der POST-Anfrage enthalten.

:::tip 
Wenn keine Aktions-URL festgelegt ist, wird die Formularübermittlung über das `LoginSubmitEvent` verarbeitet, sodass Sie die Anmeldeinformationen programmgesteuert auf der Serverseite verarbeiten können.
:::

## Internationalisierung (i18n) {#internationalization-i18n}

Die Titel, Beschreibungen, Labels und Nachrichten innerhalb der `Login`-Komponente sind vollständig anpassbar, indem die Klasse `LoginI18n` verwendet wird. Diese Flexibilität ermöglicht es Ihnen, die Anmeldeoberfläche an spezifische Lokalisierungsanforderungen oder Personalisierungsvorlieben anzupassen.

<ComponentDemo
path='/webforj/logininternationalization'
files={['src/main/java/com/webforj/samples/views/login/LoginInternationalizationView.java']}
height='600px'
/>

## Benutzerdefinierte Felder {#custom-fields}

Die `Login`-Komponente umfasst mehrere Slots, die es Ihnen ermöglichen, bei Bedarf zusätzliche Felder hinzuzufügen. Benutzerdefinierte Felder werden automatisch gesammelt, wenn das Formular übermittelt wird, und können über die Datenkarte des Übermittlungsereignisses abgerufen werden.

Das folgende Login hat ein benutzerdefiniertes Feld für eine Kunden-ID hinzugefügt. Dies kann Ihnen helfen, Unternehmen oder Abteilungen zu verwalten, die gemeinsame Inhalte über mehrere Benutzer hinweg nutzen.

<ComponentDemo
path='/webforj/logincustomfields'
files={[
  'src/main/java/com/webforj/samples/views/login/LoginCustomFieldsView.java',
  'src/main/resources/static/css/login/loginCustomFields.css',
]}
height='700px'
/>

:::info Name erforderlich
Benutzerdefinierte Felder müssen mit `setName()` einen Namen festgelegt haben, um in die Formularübermittlung aufgenommen zu werden. Der Name wird als Schlüssel verwendet, um den Wert des Feldes aus `event.getData()` abzurufen.
:::

## Abbrechen-Schaltfläche {#cancel-button}

`Login` enthält eine [Abbrechen]-Schaltfläche, die standardmäßig verborgen ist. Dies ist besonders nützlich, wenn ein Benutzer versucht, auf einen eingeschränkten Bereich der App zuzugreifen und eine Option benötigt, um zu ihrem vorherigen Standort zurückzukehren, ohne sich anzumelden.

Um die Abbrechen-Schaltfläche sichtbar zu machen, geben Sie ihr ein Label. Sie können auch auf Abbrechen-Ereignisse hören, um die Stornierung entsprechend zu behandeln.

<ComponentDemo
path='/webforj/logincancelbutton'
files={['src/main/java/com/webforj/samples/views/login/LoginCancelButtonView.java']}
height='450px'
/>

:::tip Elemente ausblenden
Um ein Element auszublenden, setzen Sie sein Label auf einen leeren String. Dies ermöglicht es Ihnen, die Sichtbarkeit zu steuern, ohne die Komponente aus Ihrem Code zu entfernen.
:::

## Passwortmanager {#password-managers}

Diese Komponente funktioniert mit browserbasierten Passwortmanagern, um den Anmeldeprozess zu vereinfachen. In Chromium-basierten Browsern integriert sie sich in die [`PasswordCredential`](https://developer.mozilla.org/en-US/docs/Web/API/PasswordCredential) API, die Folgendes bietet:

- **Automatisches Ausfüllen**: Der Browser kann automatisch die Benutzername- und Passwortfelder ausfüllen, wenn der Benutzer Anmeldeinformationen für die Website gespeichert hat.
- **Verwaltung von Anmeldeinformationen**: Nach der Anmeldung kann der Browser den Benutzer auffordern, neue Anmeldeinformationen zu speichern, sodass zukünftige Anmeldungen schneller und einfacher sind.
- **Auswahl von Anmeldeinformationen**: Wenn mehrere Anmeldeinformationen gespeichert sind, kann der Browser dem Benutzer die Auswahl aus einem der gespeicherten Sätze anbieten.

## Styling {#styling}

<TableBuilder name="Login" />
