---
title: Maven Jetty plugin
_i18n_hash: 7311fe4d0b6c5382244d898f099b9435
---
Der Maven Jetty-Plugin ist ein beliebtes Werkzeug, das es Entwicklern ermöglicht, Java-Webanwendungen innerhalb eines eingebetteten Jetty-Servers direkt aus ihren Maven-Projekten auszuführen.

Das Jetty-Plugin startet einen eingebetteten Jetty-Server, der die Dateien Ihrer Anwendung, einschließlich Java-Klassen und Ressourcen, auf Änderungen überwacht. Wenn es Aktualisierungen erkennt, wird die Anwendung automatisch neu bereitgestellt, was die Entwicklung beschleunigt, da manuelle Build- und Bereitstellungsschritte entfallen.

## Jetty-Konfigurationen {#jetty-configurations}

Hier sind einige wesentliche Konfigurationen zur Feinabstimmung der Hot-Deployment- und Serverinteraktionseinstellungen des Plugins:

| Eigenschaft                          | Beschreibung                                                                                                                                                                           | Standard        |
|-----------------------------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|----------------|
| **`scan`**         | Konfiguriert, wie oft der Jetty-Server nach Dateiänderungen in der **`pom.xml`** scannt. Das Skeleton-Projekt setzt dies auf `2` Sekunden. Eine Erhöhung dieses Intervalls kann die CPU-Auslastung reduzieren, kann jedoch dazu führen, dass Änderungen später in der Anwendung sichtbar werden. | `1`            |

## webforJ-Konfigurationen {#webforj-configurations}

| Eigenschaft                          | Beschreibung                                                                                                                                                                           | Standard        |
|-----------------------------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|----------------|
| **`webforj.reloadOnServerError`** | Bei Verwendung von Hot-Redeploy wird die gesamte WAR-Datei ausgewechselt. Wenn der Client eine Anfrage sendet, während der Server neu gestartet wird, tritt ein Fehler auf. Diese Einstellung ermöglicht es dem Client, einen Seitenneuladevorgang zu versuchen, in der Annahme, dass der Server bald wieder online sein wird. Gilt nur für Entwicklungsumgebungen und behandelt nur Fehler, die spezifisch für das Hot-Redeployment sind. | `on`           |
| **`webforj.clientHeartbeatRate`** | Legt das Intervall für Client-Pings fest, um die Verfügbarkeit des Servers abzufragen. Dies hält die Kommunikation zwischen Client und Server offen. Für die Entwicklung sollten kürzere Intervalle verwendet werden, um schnellere Fehlererkennung zu ermöglichen. In der Produktion sollte dies auf mindestens 50 Sekunden eingestellt werden, um übermäßige Anfragen zu vermeiden. | `50s`          |

## Nutzungsüberlegungen {#usage-considerations}

Während das Jetty-Plugin für die Entwicklung äußerst effektiv ist, gibt es einige potenzielle Einschränkungen:

- **Speicher- und CPU-Nutzung**: Häufiges Scannen von Dateien, das durch niedrige `scan`-Werte in der `pom.xml` festgelegt wird, kann den Ressourcenverbrauch erhöhen, insbesondere bei großen Projekten. Eine Erhöhung des Intervalls kann die Belastung reduzieren, aber auch die neu Bereitstellung verlangsamen.

- **Begrenzte Verwendung in der Produktion**: Das Jetty-Plugin ist für die Entwicklung konzipiert, nicht für Produktionsumgebungen. Es fehlen die Leistungsoptimierung und die Sicherheitskonfigurationen, die für die Produktion erforderlich sind, was es besser für lokale Tests geeignet macht.

- **Sitzungsverwaltung**: Während des Hot-Redeployments können Benutzersitzungen möglicherweise nicht beibehalten werden, insbesondere wenn große strukturelle Änderungen im Code auftreten. Dies kann Tests, die Benutzersitzungsdaten betreffen, stören und erfordert manuelle Sitzungsverwaltung oder Umgehungskonfigurationen für die Entwicklung.
