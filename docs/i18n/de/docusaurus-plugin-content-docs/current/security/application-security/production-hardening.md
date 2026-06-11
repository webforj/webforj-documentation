---
sidebar_position: 3
title: Production Hardening
description: >-
  Practical steps for running a webforJ app safely in production, from transport
  encryption and dependency upkeep to server-side checks and disclosure.
_i18n_hash: b127e22d65b9a0ee8fc5b58b542aee36
---
webforJ's [server-driven model](/docs/architecture/client-server) und integrierte Schutzmaßnahmen gegen [häufige Bedrohungen](/docs/security/application-security/common-threats) decken viel ab, aber eine sichere Bereitstellung hängt immer noch davon ab, wie Sie die App betreiben. Die folgenden Schritte runden das Bild ab.

## Jede Verbindung verschlüsseln {#encrypt-every-connection}

Führen Sie den Produktionsverkehr nur über HTTPS aus. Beenden Sie TLS am Container, Proxy oder Lastenausgleich vor der App und leiten Sie jede Anfrage über ungesichertes HTTP an ihre sichere Entsprechung weiter, damit Anmeldeinformationen und Sitzungsidentifikatoren niemals unverschlüsselt übertragen werden.

## Nichts vom Browser vertrauen {#trust-nothing-from-the-browser}

Ein manipuliertes Client kann alles senden. Überprüfen Sie jeden Wert, den Ihr Code erhält, erneut, selbst Werte, die Ihre Schnittstelle bereits eingeschränkt hat, bevor Sie sie speichern oder damit arbeiten. Der Artikel [Client/Server-Interaktion](/docs/architecture/client-server) erklärt, warum der Server der einzige Ort ist, an dem eine Regel wirklich gelten kann.

webforJ's [Datenbindung und Validierung](/docs/data-binding/validation/overview) hilft hier: Da die Bindung in Java auf dem Server ausgeführt wird, werden die Einschränkungen, die Sie an ein Modell anhängen, einschließlich [Jakarta-Validierung](/docs/data-binding/validation/jakarta-validation), serverseitig durchgesetzt und nicht nur im Browser. Betrachten Sie das als Ihre Integritätsschicht, nicht als Schutz gegen Injektions- oder Markup-Angriffe, die noch die im Artikel [Häufige Bedrohungen](/docs/security/application-security/common-threats) beschriebenen Handlungen erfordern.

## Deaktiviert und verborgen sind keine Sicherheit {#disabled-and-hidden-arent-security}

`setEnabled(false)` und `setVisible(false)` sind Schnittstellenhinweise, keine Zugriffskontrollen. webforJ spiegelt den deaktivierten Zustand einer Steuerung an den Client, verhindert jedoch nicht, dass ein manipuliertes Client diese Steuerung wieder aktiviert und ihre Aktion auslöst. Verlassen Sie sich niemals auf eine deaktivierte oder verborgene Steuerung, um etwas zu verhindern.

Setzen Sie stattdessen die echte Regel im serverseitigen Handler ein: Bestätigen Sie, dass der Benutzer berechtigt ist und die Vorbedingungen erfüllt sind, bevor Sie die Aktion ausführen, genau so, wie Sie es tun würden, wenn die Steuerung die ganze Zeit über aktiviert gewesen wäre. Der deaktivierte Zustand leitet ehrliche Benutzer; die serverseitige Regel stoppt unehrliche.

## Schützen Sie Ihre Ansichten {#lock-down-your-views}

Sichern Sie Ansichten mit [Routen-Sicherheit](/docs/security/overview), sodass jede die richtige Authentifizierung und Rollen erfordert. Geben Sie den Personen den engsten Zugriff, der es ihnen ermöglicht zu arbeiten, und bevorzugen Sie eine sicherheitsorientierte Grundeinstellung, bei der eine unmarkierte Route dennoch eine Anmeldung erfordert.

## Geheimnisse extern halten {#keep-secrets-external}

Anmeldeinformationen, Schlüssel und Tokens gehören nicht in den Code oder Ihr Repository. Ziehen Sie sie stattdessen aus der Umgebung oder einer externen Quelle, wie im Abschnitt [Verwalten von Geheimnissen](/docs/security/application-security/managing-secrets) gezeigt.

## Aktuell bei Abhängigkeiten bleiben {#stay-current-on-dependencies}

Die Bibliotheken, die Sie verwenden, sind eine größere Quelle des Risikos als Ihr eigener Code. Verfolgen Sie Hinweise, aktualisieren Sie regelmäßig webforJ und Ihre anderen Abhängigkeiten, und wenn eine gepatchte Version einer transitiven Bibliothek vor der Bibliothek erscheint, die sie einzieht, fixieren Sie die korrigierte Version in Ihrer `pom.xml`.

## Leise fehlgeschlagen {#fail-quietly}

Lassen Sie keine Stack-Traces, Dateipfade oder interne Identifikatoren die Endbenutzer erreichen. Protokollieren Sie die Details in Ihren Serverprotokollen und präsentieren Sie eine einfache, allgemeine Nachricht in der Schnittstelle. Registrieren Sie einen benutzerdefinierten Handler über die [Fehlerbehandlung](/docs/advanced/error-handling) von webforJ, damit nicht erfasste Ausnahmen eine kontrollierte Seite anstelle von Rohdiagnosen anzeigen.

## Verantwortungsbewusst offenlegen {#disclose-responsibly}

Haben Sie einen möglichen Fehler in webforJ selbst gefunden? Melden Sie ihn privat über GitHubs [private Vulnerability Reporting](https://github.com/webforj/webforj/security/advisories), anstatt ein öffentliches Problem oder eine Pull-Anfrage zu eröffnen, damit eine Lösung bereitgestellt werden kann, bevor die Details bekannt werden.
