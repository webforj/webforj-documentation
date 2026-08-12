---
title: Security
sidebar_position: 9
description: >-
  What craftforJ can reach in your project, how it restricts access, and how to
  confirm it's disabled in production.
_i18n_hash: 5ffbc5b5c6e6cfcf64143712a21944d5
---
craftforJ liest und schreibt die Quelle des Projekts, an das es angehängt ist. Diese Seite beschreibt die Grenzen darum und wie man bestätigt, dass craftforJ in den bereitgestellten Builds deaktiviert ist.

## Zwei erforderliche Einstellungen {#two-required-settings}

craftforJ erfordert, dass beide der folgenden Einstellungen aktiviert sind:

- `webforj.debug`
- `webforj.devtools.craftforj.enabled`

Keine von beiden hat allein einen Effekt. Eine App, die in die Produktion gelangt, während der Debug-Modus eingeschaltet ist, macht craftforJ nicht sichtbar, und eine App, die die craftforJ-Eigenschaft in einer gemeinsamen Konfigurationsdatei hat, macht es außerhalb des Debug-Modus nicht sichtbar.

Projekte, die mit [startforJ](https://docs.webforj.com/startforj) oder von einem webforJ [archetype](/docs/building-ui/archetypes/overview) erstellt wurden, haben beide aktiviert, sodass craftforJ bereits beim ersten Lauf funktioniert. Bevor Sie bereitstellen, arbeiten Sie die [Produktionscheckliste](#in-production) unten durch.

## Lokaler Zugang standardmäßig {#local-access-by-default}

Nur ein Browser auf dem Gerät, das die App ausführt, kann auf craftforJ zugreifen. Alles andere wird abgelehnt, und dies gilt ohne jegliche Konfiguration Ihrerseits. Um von einem anderen Gerät auf craftforJ zuzugreifen, benennen Sie dieses Gerät in [`hosts-allowed`](/docs/craftforj/configuration#access). Adressen werden wörtlich verglichen, sodass ein Client nicht durch die Behauptung, etwas anderes zu sein, zugreifen kann.

:::warning Das Platzhalterzeichen entfernt die Beschränkung vollständig
Die Einstellung `hosts-allowed = "*"` bedeutet, dass jeder, der den Port Ihrer App erreichen kann, die Quellcodes Ihres Projekts lesen und schreiben kann. Diese Einstellung ist für geschlossene Umgebungen gedacht, wie z. B. einem Container, der nur von Ihnen erreicht werden kann. Verwenden Sie es an keinem anderen Ort.
:::

## Keine zusätzlichen HTTP-Oberflächen {#no-added-http-surface}

craftforJ fügt Ihrer App keinen HTTP-Endpunkt, Servlet oder Filter hinzu. Es funktioniert über die Verbindung, die Ihre App bereits hat, sodass Ihre App exakt dasselbe Set von Anfragen beantwortet, ob craftforJ aktiv ist oder nicht.

## Anfragen kommen von Ihrer Seite {#requests-come-from-your-page}

craftforJ reagiert nur auf Anfragen, die von der Seite stammen, die Ihr Server tatsächlich bereitgestellt hat. Ein Skript, das irgendwoher in die Seite gelangt, z. B. durch eine kompromittierte Abhängigkeit oder etwas, das in eine Konsole eingefügt wurde, kann craftforJ nicht steuern.

## API-Schlüssel {#api-keys}

Ihr Schlüssel wird auf dem Gerät gespeichert, das Ihre App ausführt. Der [AI-Assistent](/docs/craftforj/ai) läuft im Browser, daher muss craftforJ ihm den Schlüssel zur Verfügung stellen, und dieser wird im Arbeitsspeicher gehalten, solange die Seite geöffnet ist. Es wird nichts im lokalen Speicher des Browsers gespeichert, und das Schließen der Seite hinterlässt nichts.

Der Assistent kommuniziert dann von Ihrem Browser aus mit Ihrem Anbieter, nicht über Ihren Server. Es gibt keinen Relay, keinen Proxy, keine Telemetrie und keinen Dritten dazwischen.

Was Ihren Anbieter erreicht, ist das eigentliche Gespräch, das die Teile Ihrer App umfasst, die der Assistent angesehen hat, sowie alle Screenshots, die er aufgenommen hat. Berücksichtigen Sie dies, bevor Sie ein gehostetes Modell auf eine App richten, die mit echten Daten arbeitet. Ein lokal laufendes Modell lässt alles auf Ihrem Gerät.

## Was craftforJ ändern kann {#what-craftforj-can-change}

Mit jeder aktivierten Funktion kann craftforJ:

- Jede Quelldatei unter dem Stammverzeichnis Ihres Projekts lesen
- Java-Quelldateien schreiben, einschließlich Routen-Zugriffsanmerkungen
- Das Stylesheet Ihrer App schreiben
- Komponenten in der laufenden App ändern und entfernen
- In der laufenden App navigieren

Jede dieser Funktionen kann unabhängig [deaktiviert](/docs/craftforj/configuration#feature-flags) werden, und jeder Schreibvorgang auf die Festplatte erfolgt über einen Diff, den Sie genehmigen.

## In Produktion {#in-production}

Deaktivieren Sie craftforJ. Es ist deaktiviert, es sei denn, Sie haben es eingeschaltet, sodass in den meisten Fällen nichts zu tun ist. Um dies zu bestätigen:

1. `webforj.devtools.craftforj.enabled` ist in der Konfiguration, die Sie tatsächlich bereitstellen, nicht gesetzt oder `false`.
2. `webforj.debug` ist in derselben Konfiguration nicht gesetzt oder `false`.
3. Keine der Eigenschaften wird durch eine Umgebungsvariable oder durch ein Profil gesetzt, das nur in der Produktion gilt.
4. Laden Sie die bereitgestellte App und bestätigen Sie, dass kein craftforJ-Auslöser auf der Seite vorhanden ist.

Für das größere Bild siehe [Produktionshärtung](/docs/security/application-security/production-hardening).

## Meldung eines Sicherheitsproblems {#reporting-a-security-issue}

Wenn Sie ein Sicherheitsproblem in craftforJ finden, melden Sie es über die [Sicherheitsrichtlinie von webforJ](https://github.com/webforj/webforj/security), anstatt es in einem öffentlichen Problem zu tun.
