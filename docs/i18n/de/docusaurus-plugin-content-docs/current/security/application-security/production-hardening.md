---
sidebar_position: 3
title: Produktions-Härtung
description: >-
  Practical steps for running a webforJ app safely in production, from transport
  encryption and dependency upkeep to server-side checks and disclosure.
_i18n_hash: 62e3e574855705f8b97295f4ebe5169b
---
webforJ's [server-driven model](/docs/architecture/client-server) und integrierte Sicherheitsmaßnahmen gegen [häufige Bedrohungen](/docs/security/application-security/common-threats) decken viel ab, aber eine sichere Bereitstellung hängt immer noch davon ab, wie Sie die Anwendung betreiben. Die folgenden Schritte runden das Bild ab.

## Verschlüsseln Sie jede Verbindung {#encrypt-every-connection}

Führen Sie Produktionsverkehr nur über HTTPS aus. Terminate TLS am Container, Proxy oder Lastenausgleich vor der Anwendung, und leiten Sie jede Plain-HTTP-Anfrage auf die sichere Entsprechung um, sodass Anmeldeinformationen und Sitzungsbezeichner niemals unverschlüsselt übertragen werden.

## Vertrauen Sie nichts vom Browser {#trust-nothing-from-the-browser}

Ein manipuliertes Client kann alles senden. Überprüfen Sie jeden Wert, den Ihr Code empfängt, sogar Werte, die Ihre Schnittstelle bereits eingeschränkt hat, bevor Sie sie speichern oder darauf reagieren. Der Artikel [Client/Server-Interaktion](/docs/architecture/client-server) erklärt, warum der Server der einzige Ort ist, an dem eine Regel wirklich gelten kann.

webforJ's [Datenbindung und Validierung](/docs/data-binding/validation/overview) hilft hier: Da die Bindung auf dem Server in Java läuft, werden die Einschränkungen, die Sie an ein Modell anhängen, einschließlich [Jakarta-Validierung](/docs/data-binding/validation/jakarta-validation), serverseitig durchgesetzt, nicht nur im Browser. Behandeln Sie dies als Ihre Integritätsschicht, nicht als Verteidigung gegen Injektions- oder Markup-Angriffe, die immer noch die Behandlung erfordern, die im Artikel [Häufige Bedrohungen](/docs/security/application-security/common-threats) beschrieben wird.

## Deaktiviert und verborgen sind keine Sicherheitsmaßnahmen {#disabled-and-hidden-arent-security}

`setEnabled(false)` und `setVisible(false)` sind Interface-Hinweise, keine Zugriffskontrollen. webforJ spiegelt den deaktivierten Zustand einer Steuerung an den Client, aber es hindert ein manipuliertes Client nicht daran, diese Steuerung wieder zu aktivieren und ihre Aktion auszulösen. Lehnen Sie sich niemals auf eine deaktivierte oder verborgene Steuerung, um etwas daran zu hindern, dass es passiert.

Setzen Sie die echte Regel im serverseitigen Handler stattdessen um: Bestätigen Sie, dass der Benutzer berechtigt ist und die Vorbedingungen erfüllt sind, bevor Sie die Aktion ausführen, genau so, wie Sie es tun würden, wenn die Steuerung die ganze Zeit über aktiviert gewesen wäre. Der deaktivierte Zustand leitet ehrliche Benutzer; die serverseitige Regel stoppt unehrliche.

## Schützen Sie Ihre Ansichten {#lock-down-your-views}

Schützen Sie Ansichten mit [Routen-Sicherheit](/docs/security/overview), sodass jede die richtige Authentifizierung und Rollen verlangt. Geben Sie den Personen den engsten Zugang, der ihnen erlaubt zu arbeiten, und bevorzugen Sie eine sichere Standardhaltung, bei der eine nicht markierte Route dennoch die Anmeldung erfordert.

## Halten Sie Geheimnisse extern {#keep-secrets-external}

Anmeldeinformationen, Schlüssel und Tokens gehören nicht in Code oder in Ihr Repository. Ziehen Sie sie stattdessen aus der Umgebung oder einer externen Quelle, wie im Artikel [Verwalten von Geheimnissen](/docs/security/application-security/managing-secrets) dargestellt.

## Lassen Sie Entwicklungstools deaktiviert {#leave-development-tooling-off}

[craftforJ](/docs/craftforj) ist die Entwicklungsumgebung, die eine laufende Anwendung inspiziert und Änderungen in ihren Java-Quellcode zurückschreibt. Es erfordert sowohl `webforj.debug` als auch `webforj.devtools.craftforj.enabled` und antwortet standardmäßig nur der Maschine, die die Anwendung ausführt. Projekte, die mit [startforJ](https://docs.webforj.com/startforj) oder aus einem webforJ [Archetyp](/docs/building-ui/archetypes/overview) erstellt wurden, haben beide Einstellungen für die Entwicklung aktiviert, also bestätigen Sie sie, anstatt davon auszugehen.

Überprüfen Sie, dass beide Eigenschaften in der Konfiguration, die Sie tatsächlich bereitstellen, nicht gesetzt oder `false` sind, einschließlich aller Umgebungsvariablen oder Profile, die nur in der Produktion gelten. Laden Sie dann die bereitgestellte Anwendung und bestätigen Sie, dass kein craftforJ-Trigger auf der Seite erscheint. Weitere Informationen finden Sie unter [craftforJ-Sicherheit](/docs/craftforj/security).

## Halten Sie Abhängigkeiten aktuell {#stay-current-on-dependencies}

Die Bibliotheken, die Sie einbinden, sind eine größere Risikoquelle als Ihr eigener Code. Verfolgen Sie Hinweise, aktualisieren Sie webforJ und Ihre anderen Abhängigkeiten regelmäßig, und wenn eine gepatchte Version einer transitive Bibliothek vor der Bibliothek veröffentlicht wird, die sie einbindet, fixieren Sie die festgelegte Version in Ihrer `pom.xml`.

## Stillen Sie leise {#fail-quietly}

Lassen Sie keine Stack-Traces, Dateipfade oder interne Bezeichner die Endbenutzer erreichen. Protokollieren Sie die Details in Ihren Serverprotokollen und präsentieren Sie eine einfache, generische Nachricht in der Schnittstelle. Registrieren Sie einen benutzerdefinierten Handler über die [Fehlerbehandlung](/docs/advanced/error-handling) von webforJ, sodass nicht behandelte Ausnahmen eine kontrollierte Seite anstelle von Rohdiagnosen anzeigen.

## Verantwortungsbewusst offenlegen {#disclose-responsibly}

Haben Sie einen möglichen Fehler in webforJ selbst gefunden? Melden Sie es privat über GitHub's [private Schwachstellenberichterstattung](https://github.com/webforj/webforj/security/advisories), anstatt ein öffentliches Problem oder einen Pull-Request zu eröffnen, damit eine Lösung bereitstehen kann, bevor die Details bekannt werden.
