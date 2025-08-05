---
title: Login
sidebar_position: 70
_i18n_hash: f2f1f96cfde1dbbede5bfdaafd3f0a92
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-login" />
<DocChip chip='since' label='24.01' />
<JavadocLink type="login" location="com/webforj/component/login/Login" top='true'/>

Die Login-Komponente ist so konzipiert, dass sie eine benutzerfreundliche Oberfläche zur Authentifizierung bereitstellt, die es den Benutzern ermöglicht, sich mit einem Benutzernamen und Passwort anzumelden. Sie unterstützt verschiedene Anpassungen zur Verbesserung der Benutzererfahrung auf unterschiedlichen Geräten und in verschiedenen Regionen.

<ComponentDemo 
path='/webforj/loginbasic?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/login/LoginBasicView.java'
height = '450px'
/>

## Usages {#usages}

Die Login-Komponente bietet ein benutzerfreundliches Login-Formular innerhalb eines Dialogs zur Eingabe von Authentifizierungsdaten. Sie verbessert die Benutzererfahrung durch folgendes:
   >- Klare Eingabefelder für Benutzername und Passwort.
   >- Sichtbarkeitstaster für das Passwort zur Überprüfung der Eingabe.
   >- Eingabebestätigungsfeedback, um das richtige Format vor der Übermittlung zu fördern.

## Login submission {#login-submission}

Wenn Benutzer ihren Benutzernamen und ihr Passwort eingeben, validiert die Login-Komponente diese Eingaben als erforderliche Felder. Sobald die Validierung erfolgreich ist, wird ein Formularüberereignis ausgelöst, das die eingegebenen Anmeldedaten übermittelt. Um mehrere Übermittlungen zu verhindern, wird die `Signin`-Schaltfläche sofort deaktiviert.

Die folgende Demo veranschaulicht einen grundlegenden Formularübermittlungsprozess. Wenn sowohl der Benutzername als auch das Passwort auf `"admin"` gesetzt sind, wird der Login-Dialog geschlossen und eine Abmelde-Schaltfläche erscheint. Wenn die Anmeldedaten nicht übereinstimmen, wird die standardmäßige Fehlermeldung des Login-Formulars angezeigt.

<ComponentDemo 
path='/webforj/loginsubmission?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/login/LoginSubmissionView.java'
height = '450px'
/>

:::info Deaktivierung der Signin-Schaltfläche
Standardmäßig deaktiviert das Login-Formular sofort die `Signin`-Schaltfläche, sobald die Komponente die Login-Eingaben als korrekt validiert, um mehrere Übermittlungen zu verhindern. Sie können die `Signin`-Schaltfläche mit der Methode `setEnabled(true)` wieder aktivieren.
:::

:::tip Zulassen von leeren Passwörtern
In bestimmten Szenarien können leere Passwörter zulässig sein, sodass Benutzer sich nur mit einem Benutzernamen anmelden können. Der Login-Dialog kann so konfiguriert werden, dass er leere Passwörter akzeptiert, indem `setEmptyPassword(true)` gesetzt wird.
:::

## Internationalisierung (i18n) {#internationalization-i18n}

Die Titel, Beschreibungen, Labels und Nachrichten innerhalb der Login-Komponente sind vollständig anpassbar und verwenden die `LoginI18n`-Klasse. Diese Flexibilität ermöglicht es Ihnen, die Login-Oberfläche an spezifische Lokalisierungsanforderungen oder persönliche Vorlieben anzupassen.

Die folgende Demo veranschaulicht, wie eine deutsche Übersetzung für den Login-Dialog bereitgestellt werden kann, sodass alle Interface-Elemente an die deutsche Sprache angepasst werden, um die Benutzererfahrung für deutschsprachige Benutzer zu verbessern.

<ComponentDemo 
path='/webforj/logininternationalization?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/login/LoginInternationalizationView.java'
height = '500px'
/>

## Benutzerdefinierte Felder {#custom-fields}

Die Login-Komponente enthält mehrere Slots, die es Ihnen ermöglichen, bei Bedarf zusätzliche Felder hinzuzufügen. Dieses Feature bietet mehr Kontrolle über die Informationen, die für eine erfolgreiche Authentifizierung erforderlich sind.

Im folgenden Beispiel wird ein Kunden-ID-Feld zum Login-Formular hinzugefügt. Benutzer müssen eine gültige ID angeben, um die Authentifizierung abzuschließen, was die Sicherheit erhöht und sicherstellt, dass der Zugriff nur nach Überprüfung aller erforderlichen Anmeldedaten gewährt wird.

<ComponentDemo 
path='/webforj/logincustomfields?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/login/LoginCustomFieldsView.java'
cssURL='/css/login/loginCustomFields.css'
height = '700px'
/>

:::info Übertragungsinhalt
Bitte beachten Sie, dass die Login-Komponente zusätzliche Felder, die zum Formular hinzugefügt werden, nicht automatisch erkennt oder in ihrem Übertragungsinhalt enthält. Dies bedeutet, dass Entwickler den Wert zusätzlicher Felder explizit von der Client-Seite abrufen und entsprechend den Anforderungen der App verarbeiten müssen, um den Authentifizierungsprozess abzuschließen.
:::

## Abbrechen-Schaltfläche {#cancel-button}

In bestimmten Szenarien kann es wünschenswert sein, neben der `Signin`-Schaltfläche eine Abbrechen-Schaltfläche hinzuzufügen. Dieses Feature ist besonders nützlich, wenn ein Benutzer versucht, auf einen eingeschränkten Bereich der App zuzugreifen und eine Option zum Abbrechen der Aktion und Rückkehr zu seinem vorherigen Standort benötigt. Das Login-Formular enthält standardmäßig eine Abbrechen-Schaltfläche, die jedoch aus der Sicht verborgen ist.

Um die Abbrechen-Schaltfläche sichtbar zu machen, müssen Sie ihr eine Bezeichnung geben - nach der Beschriftung wird sie auf dem Bildschirm erscheinen. Sie können auch auf Abbrechen-Ereignisse hören, um angemessen auf Benutzeraktionen zu reagieren und eine reibungslose und benutzerfreundliche Erfahrung bei der Navigation in der App sicherzustellen.

<ComponentDemo 
path='/webforj/logincancelbutton?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/login/LoginCancelButtonView.java'
height = '450px'
/>

:::tip Verstecken von Elementen
Um ein Element vom Login-Bildschirm zu verbergen, setzen Sie dessen Bezeichnung einfach auf einen leeren String. Dieser Ansatz ist besonders nützlich, um Interface-Komponenten vorübergehend zu entfernen, ohne den Code dauerhaft zu ändern.
:::

## Passwortmanager {#password-managers}

Die Login-Komponente ist so konzipiert, dass sie mit browserbasierten Passwortmanagern kompatibel ist, was die Benutzererfahrung verbessert, indem der Anmeldeprozess vereinfacht wird. Für Benutzer von Chromium-basierten Browsern integriert sich die Komponente nahtlos mit der [`PasswordCredential`](https://developer.mozilla.org/en-US/docs/Web/API/PasswordCredential) API. Diese Integration ermöglicht mehrere praktische Funktionen:

- **Auto-fill**: Der Browser kann automatisch die Benutzername- und Passwortfelder ausfüllen, wenn der Benutzer Anmeldedaten für die Seite gespeichert hat.
- **Zugangsdatenverwaltung**: Nach der Anmeldung kann der Browser den Benutzer auffordern, neue Anmeldedaten zu speichern, was zukünftige Anmeldungen schneller und einfacher macht.
- **Zugangsdaten-Auswahl**: Wenn mehrere Anmeldedaten gespeichert sind, kann der Browser dem Benutzer die Wahl anbieten, aus einem der gespeicherten Datensätze auszuwählen.

## Styling {#styling}

<TableBuilder name="Login" />
