---
title: Maven Jetty plugin
description: >-
  Tune the Maven Jetty plugin scan interval and webforJ reload properties to
  enable hot redeployment during webforJ development.
_i18n_hash: 6ce3da7be312bb71f2ded56a583d7687
---
Das Maven Jetty-Plugin ist ein beliebtes Tool, das Entwicklern ermöglicht, Java-Webanwendungen innerhalb eines eingebetteten Jetty-Servers direkt aus ihren Maven-Projekten auszuführen.

Das Jetty-Plugin startet einen eingebetteten Jetty-Server, der die Dateien Ihrer Anwendung, einschließlich Java-Klassen und Ressourcen, auf Änderungen überwacht. Wenn es Updates erkennt, wird die Anwendung automatisch neu bereitgestellt, was die Entwicklung beschleunigt, da manuelle Build- und Bereitstellungsschritte entfallen.

:::tip Frontend-Änderungen
Änderungen unter `src/main/frontend` werden durch das [frontend watch](/docs/configuration/deploy-reload/frontend-watch) behandelt, das sie neu aufbaut und den Browser zusammen mit dem Server aktualisiert.
:::

## Jetty-Konfigurationen {#jetty-configurations}

Hier sind einige wesentliche Konfigurationen zur Feinabstimmung der Hot-Deployment- und Server-Interaktionseinstellungen des Plugins:

| Eigenschaft                          | Beschreibung                                                                                                                                                                           | Standard        |
|--------------------------------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|-----------------|
| **`scan`**         | Konfiguriert, wie oft der Jetty-Server nach Dateiänderungen in der **`pom.xml`** sucht. Das Basisprojekt setzt dies auf `2` Sekunden. Eine Erhöhung dieses Intervalls kann die CPU-Auslastung reduzieren, jedoch auch die Reflexion von Änderungen in der Anwendung verzögern. | `1`             |

## webforJ-Konfigurationen {#webforj-configurations}

| Eigenschaft                          | Beschreibung                                                                                                                                                                           | Standard        |
|--------------------------------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|-----------------|
| **`webforj.reloadOnServerError`** | Bei der Verwendung von Hot-Redeploy wird die gesamte WAR-Datei getauscht. Wenn der Client eine Anfrage sendet, während der Server neu gestartet wird, tritt ein Fehler auf. Diese Einstellung ermöglicht es dem Client, einen Seitenneuladen zu versuchen, in der Annahme, dass der Server bald wieder online ist. Gilt nur für Entwicklungsumgebungen und behandelt nur Fehler, die spezifisch für das Hot-Redeployment sind. | `on`            |
| **`webforj.clientHeartbeatRate`** | Setzt das Intervall für Client-Pings zur Abfrage der Serververfügbarkeit. Dies hält die Kommunikation zwischen Client und Server offen. Für die Entwicklung sollten kürzere Intervalle verwendet werden, um Fehler schneller zu erkennen. In der Produktion sollte dies auf mindestens 50 Sekunden gesetzt werden, um übermäßige Anfragen zu vermeiden. | `50s`           |

## Nutzungsüberlegungen {#usage-considerations}

Obwohl das Jetty-Plugin sehr effektiv für die Entwicklung ist, gibt es einige potenzielle Einschränkungen:

- **Speicher- und CPU-Nutzung**: Häufige Dateiüberprüfungen, die durch niedrige `scan`-Werte in der `pom.xml` festgelegt werden, können den Ressourcenverbrauch erhöhen, insbesondere in großen Projekten. Eine Erhöhung des Intervalls kann die Belastung reduzieren, aber auch das Redeployment verlangsamen.

- **Eingeschränkte Produktionsnutzung**: Das Jetty-Plugin ist für die Entwicklung konzipiert, nicht für Produktionsumgebungen. Es fehlt an Leistungsoptimierung und Sicherheitskonfigurationen, die für die Produktion erforderlich sind, wodurch es sich besser für lokale Tests eignet.

- **Sitzungsmanagement**: Während des Hot-Redeployments können Benutzersitzungen verloren gehen, insbesondere wenn es zu größeren strukturellen Änderungen im Code kommt. Dies kann Tests, die Benutzersitzungsdaten umfassen, stören und erfordert ein manuelles Sitzungsmanagement oder alternative Konfigurationen für die Entwicklung.
