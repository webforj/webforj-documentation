---
title: Login
sidebar_position: 70
_i18n_hash: b95b5a072de318071d9d7ecae890a883
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-login" />
<DocChip chip='since' label='24.01' />
<JavadocLink type="login" location="com/webforj/component/login/Login" top='true'/>

Die Login-Komponente ist so konzipiert, dass sie eine benutzerfreundliche Schnittstelle für die Authentifizierung bereitstellt, mit der Benutzer sich mit einem Benutzernamen und einem Passwort anmelden können. Sie unterstützt verschiedene Anpassungen, um das Benutzererlebnis über verschiedene Geräte und Regionen hinweg zu verbessern.

<ComponentDemo 
path='/webforj/loginbasic?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/login/LoginBasicView.java'
height = '450px'
/>

## Usages {#usages}

Die Login-Komponente bietet eine benutzerfreundliche Anmeldemaske innerhalb eines Dialogfelds zur Eingabe von Authentifizierungsdaten. Sie verbessert das Benutzererlebnis, indem sie Folgendes bietet:
   >- Klare Eingabefelder für Benutzername und Passwort.
   >- Sichtbarkeitsschalter für das Passwort zur Überprüfung der Eingabe.
   >- Eingabebenachrichtigungen zur Rückmeldung, um das korrekte Format vor dem Absenden sicherzustellen.

## Login submission {#login-submission}

Wenn Benutzer ihren Benutzernamen und ihr Passwort eingeben, validiert die Login-Komponente diese Eingaben als erforderliche Felder. Sobald die Validierung bestanden ist, wird ein Formularüberereignis ausgelöst, das die eingegebenen Anmeldeinformationen übermittelt. Um mehrere Übermittlungen zu verhindern, wird die Schaltfläche `Signin` sofort deaktiviert.

Die folgende Demo veranschaulicht einen grundlegenden Formularübermittlungsprozess. Wenn der Benutzername und das Passwort beide auf `"admin"` gesetzt sind, wird das Anmeldedialogfeld geschlossen und eine Schaltfläche zum Abmelden erscheint. Wenn die Anmeldeinformationen nicht übereinstimmen, wird die Standardfehlermeldung des Anmeldeformulars angezeigt.

<ComponentDemo 
path='/webforj/loginsubmission?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/login/LoginSubmissionView.java'
height = '450px'
/>

:::info Deaktivierung der Signin-Schaltfläche
Standardmäßig deaktiviert das Anmeldeformular sofort die Schaltfläche `Signin`, sobald die Komponente die Anmeldeeingaben als korrekt validiert, um mehrere Übermittlungen zu verhindern. Sie können die Schaltfläche `Signin` mit der Methode `setEnabled(true)` wieder aktivieren.
:::

:::tip Erlauben leerer Passwörter
In bestimmten Szenarien können leere Passwörter zulässig sein, sodass Benutzer sich nur mit einem Benutzernamen anmelden können. Das Anmeldedialogfeld kann so konfiguriert werden, dass es leere Passwörter akzeptiert, indem `setEmptyPassword(true)` gesetzt wird.
:::

## Internationalisierung (i18n) {#internationalization-i18n}

Die Titel, Beschreibungen, Labels und Nachrichten innerhalb der Login-Komponente sind vollständig anpassbar mit der Klasse `LoginI18n`. Diese Flexibilität ermöglicht es Ihnen, die Anmeldeschnittstelle so anzupassen, dass sie spezifische Lokalisierungsanforderungen oder Personalisierungspräferenzen erfüllt.

Die folgende Demo veranschaulicht, wie man eine deutsche Übersetzung für das Anmeldedialogfeld bereitstellt, um sicherzustellen, dass alle Schnittstellenelemente an die deutsche Sprache angepasst werden, um das Benutzererlebnis für deutschsprachige Benutzer zu verbessern.

<ComponentDemo 
path='/webforj/logininternationalization?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/login/LoginInternationalizationView.java'
height = '500px'
/>

## Custom fields {#custom-fields}

Die Login-Komponente umfasst mehrere Slots, die es Ihnen ermöglichen, bei Bedarf zusätzliche Felder hinzuzufügen. Diese Funktion ermöglicht mehr Kontrolle über die Informationen, die für eine erfolgreiche Authentifizierung erforderlich sind.

Im untenstehenden Beispiel wird ein Kunden-ID-Feld zum Anmeldeformular hinzugefügt. Benutzer müssen eine gültige ID bereitstellen, um die Authentifizierung abzuschließen, was die Sicherheit erhöht und sicherstellt, dass der Zugriff nur nach Überprüfung aller erforderlichen Anmeldeinformationen gewährt wird.

<ComponentDemo 
path='/webforj/logincustomfields?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/login/LoginCustomFieldsView.java'
cssURL='/css/login/loginCustomFields.css'
height = '700px'
/>

:::info Übermittlungs-Payload
Bitte beachten Sie, dass die Login-Komponente zusätzliche Felder, die dem Formular hinzugefügt wurden, nicht automatisch erkennt oder in ihrem Übermittlungs-Payload einbezieht. Das bedeutet, dass Entwickler explizit den Wert zusätzlicher Felder von der Clientseite abrufen und diesen gemäß den Anforderungen der Anwendung behandeln müssen, um den Authentifizierungsprozess abzuschließen.
:::

## Cancel button {#cancel-button}

In bestimmten Szenarien kann es wünschenswert sein, neben der Schaltfläche `Signin` eine Abbrechen-Schaltfläche hinzuzufügen. Diese Funktion ist besonders nützlich, wenn ein Benutzer versucht, auf einen eingeschränkten Bereich der Anwendung zuzugreifen und eine Option benötigt, um die Aktion abzubrechen und zu seinem vorherigen Standort zurückzukehren. Das Anmeldeformular enthält standardmäßig eine Abbrechen-Schaltfläche, die jedoch nicht angezeigt wird.

Um die Abbrechen-Schaltfläche sichtbar zu machen, müssen Sie ihr ein Label geben - sobald sie beschriftet ist, wird sie auf dem Bildschirm angezeigt. Sie können auch auf Abbrechen-Ereignisse hören, um entsprechend auf die Benutzeraktionen zu reagieren und ein reibungsloses und benutzerfreundliches Erlebnis bei der Navigation in der Anwendung sicherzustellen.

<ComponentDemo 
path='/webforj/logincancelbutton?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/login/LoginCancelButtonView.java'
height = '450px'
/>

:::tip Ausblenden von Elementen
Um ein Element vom Anmeldebildschirm auszublenden, setzen Sie einfach sein Label auf einen leeren String. Dieser Ansatz ist besonders nützlich, um Schnittstellenkomponenten vorübergehend zu entfernen, ohne den Code dauerhaft zu ändern.
:::

## Password managers {#password-managers}

Die Login-Komponente ist so konzipiert, dass sie mit passwortbasierten Browser-Managern kompatibel ist, um das Benutzererlebnis zu verbessern und den Anmeldeprozess zu vereinfachen. Für Benutzer von Chromium-basierten Browsern integriert sich die Komponente nahtlos mit dem [`PasswordCredential`](https://developer.mozilla.org/en-US/docs/Web/API/PasswordCredential) API. Diese Integration ermöglicht mehrere komfortable Funktionen:

- **Automatisches Ausfüllen**: Der Browser kann die Felder für Benutzername und Passwort automatisch ausfüllen, wenn der Benutzer Anmeldeinformationen für die Website gespeichert hat.
- **Credential Management**: Nach der Anmeldung kann der Browser den Benutzer auffordern, neue Anmeldeinformationen zu speichern, was zukünftige Anmeldungen schneller und einfacher macht.
- **Credential Selection**: Wenn mehrere Anmeldeinformationen gespeichert sind, kann der Browser dem Benutzer die Wahl lassen, aus einem der gespeicherten Sätze auszuwählen.

## Styling {#styling}

<TableBuilder name="Login" />
