---
title: Maven Jetty plugin
description: >-
  Tune the Maven Jetty plugin scan interval and webforJ reload properties to
  enable hot redeployment during webforJ development.
_i18n_hash: 7b8efc852651f2bea9db4ca110fe11ed
---
Das Maven Jetty-Plugin ist ein beliebtes Tool, das Entwicklern ermöglicht, Java-Webanwendungen direkt aus ihren Maven-Projekten innerhalb eines eingebetteten Jetty-Servers auszuführen.

Das Jetty-Plugin startet einen eingebetteten Jetty-Server, der die Dateien Ihrer App, einschließlich Java-Klassen und Ressourcen, auf Änderungen überwacht. Wenn es Aktualisierungen erkennt, wird die App automatisch neu bereitgestellt, was die Entwicklung beschleunigt, indem manuelle Build- und Bereitstellungsschritte entfällt.

:::tip Frontend-Änderungen
Änderungen unter `src/main/frontend` werden durch die [frontend watch](/docs/configuration/deploy-reload/frontend-watch) behandelt, die sie neu erstellt und den Browser zusammen mit dem Server aktualisiert.
:::

## Jetty-Konfigurationen {#jetty-configurations}

Hier sind einige wichtige Konfigurationen zur Feinabstimmung der Hot-Deployment- und Server-Interaktions-Einstellungen des Plugins:

| Eigenschaft                          | Beschreibung                                                                                                                                                                           | Standard        |
|-------------------------------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|----------------|
| **`scan`**         | Konfiguriert, wie oft der Jetty-Server nach Dateiänderungen in der **`pom.xml`** sucht. Das Skelettprojekt setzt dies auf `2` Sekunden. Eine Erhöhung dieses Intervalls kann die CPU-Auslastung reduzieren, verzögert jedoch das Anzeigen von Änderungen in der App. | `1`            |

## webforJ-Konfigurationen {#webforj-configurations}

| Eigenschaft                          | Beschreibung                                                                                                                                                                           | Standard        |
|-------------------------------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|----------------|
| **`webforj.reloadOnServerError`** | Bei Verwendung des Hot-Redeploy wird die gesamte WAR-Datei ausgetauscht. Wenn der Client eine Anfrage sendet, während der Server neu gestartet wird, tritt ein Fehler auf. Diese Einstellung ermöglicht es dem Client, einen Seitenreload zu versuchen, in der Annahme, dass der Server bald wieder online sein wird. Gilt nur für Entwicklungsumgebungen und behandelt nur Fehler, die speziell mit dem Hot-Redeployment zusammenhängen. | `on`           |
| **`webforj.clientHeartbeatRate`** | Legt das Intervall für Client-Pings fest, um die Verfügbarkeit des Servers zu überprüfen. Dadurch bleibt die Kommunikation zwischen Client und Server offen. Für die Entwicklung verwenden Sie kürzere Intervalle, um eine schnellere Fehlererkennung zu ermöglichen. In der Produktion setzen Sie dies auf mindestens 50 Sekunden, um übermäßige Anfragen zu vermeiden. | `50s`          |

## Nutzungshinweise {#usage-considerations}

Während das Jetty-Plugin für die Entwicklung äußerst effektiv ist, gibt es einige potenzielle Einschränkungen:

- **Speicher- und CPU-Nutzung**: Häufiges Scannen von Dateien, das durch niedrige `scan`-Werte in der `pom.xml` festgelegt wird, kann den Ressourcenverbrauch erhöhen, insbesondere bei großen Projekten. Eine Erhöhung des Intervalls kann die Belastung reduzieren, verlangsamt jedoch auch das Redeployment.

- **Eingeschränkte Produktionseignung**: Das Jetty-Plugin ist für die Entwicklung und nicht für Produktionsumgebungen konzipiert. Es fehlen die Leistungsoptimierung und die Sicherheitskonfigurationen, die für die Produktion erforderlich sind, was es besser für lokale Tests geeignet macht.

- **Sitzungsverwaltung**: Während des Hot-Redeployments können Benutzersitzungen verloren gehen, insbesondere wenn große strukturelle Änderungen im Code auftreten. Dies kann Tests beeinträchtigen, die Benutzersitzungsdaten betreffen, was eine manuelle Sitzungsverwaltung oder alternative Konfigurationen für die Entwicklung erfordert.
