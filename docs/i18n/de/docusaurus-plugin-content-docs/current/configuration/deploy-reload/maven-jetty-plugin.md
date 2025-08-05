---
title: Maven Jetty plugin
_i18n_hash: 13b8de676f30b5a21eb7e9c2b49945b6
---
Das Maven Jetty-Plugin ist ein beliebtes Tool, das Entwicklern ermöglicht, Java-Webanwendungen direkt aus ihren Maven-Projekten in einem eingebetteten Jetty-Server auszuführen.

Das Jetty-Plugin startet einen eingebetteten Jetty-Server, der die Dateien Ihrer Anwendung, einschließlich Java-Klassen und Ressourcen, auf Änderungen überwacht. Wenn Änderungen erkannt werden, wird die Anwendung automatisch neu bereitgestellt, was die Entwicklung beschleunigt, da manuelle Build- und Bereitstellungsschritte entfallen.

## Jetty-Konfigurationen {#jetty-configurations}

Hier sind einige grundlegende Konfigurationen zur Feinabstimmung der Hot-Deployment- und Server-Interaktionsparameter des Plugins:

| Eigenschaft                          | Beschreibung                                                                                                                                                                           | Standard       |
|--------------------------------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|----------------|
| **`scan`**       | Konfiguriert, wie oft der Jetty-Server nach Dateiänderungen in der **`pom.xml`** sucht. Das Skelettprojekt setzt dies auf `2` Sekunden. Eine Erhöhung dieses Intervalls kann die CPU-Belastung reduzieren, verzögert jedoch möglicherweise das Eintreffen von Änderungen in der Anwendung. | `1`            |

## webforJ-Konfigurationen {#webforj-configurations}

| Eigenschaft                          | Beschreibung                                                                                                                                                                           | Standard       |
|--------------------------------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|----------------|
| **`webforj.reloadOnServerError`**   | Bei der Verwendung von Hot-Redeploy wird die gesamte WAR-Datei ausgetauscht. Wenn der Client eine Anfrage sendet, während der Server neu gestartet wird, tritt ein Fehler auf. Diese Einstellung erlaubt es dem Client, einen Seitenneuladen zu versuchen, in der Annahme, dass der Server bald wieder online sein wird. Gilt nur für Entwicklungsumgebungen und behandelt nur Fehler, die spezifisch für das Hot-Redeployment sind. | `on`           |
| **`webforj.clientHeartbeatRate`**   | Legt das Intervall für Client-Pings fest, um die Serververfügbarkeit abzufragen. Dies hält die Kommunikation zwischen Client und Server offen. Für die Entwicklung sollten kürzere Intervalle verwendet werden, um schnellere Fehlererkennung zu ermöglichen. In der Produktion sollte dies auf mindestens 50 Sekunden gesetzt werden, um übermäßige Anfragen zu vermeiden. | `50s`          |

## Nutzungshinweise {#usage-considerations}

Während das Jetty-Plugin äußerst effektiv für die Entwicklung ist, gibt es einige mögliche Einschränkungen:

- **Speicher- und CPU-Nutzung**: Häufiges Scannen von Dateien, das durch niedrige `scan`-Werte in der `pom.xml` festgelegt wird, kann den Ressourcenverbrauch erhöhen, insbesondere bei großen Projekten. Eine Erhöhung des Intervalls kann die Belastung reduzieren, verlangsamt jedoch auch das Redeployment.

- **Eingeschränkte Produktionseignung**: Das Jetty-Plugin ist für die Entwicklung und nicht für Produktionsumgebungen konzipiert. Es fehlen die Leistungsoptimierungen und Sicherheitskonfigurationen, die für die Produktion erforderlich sind, was es besser für lokale Tests geeignet macht.

- **Sitzungsmanagement**: Während des Hot-Redeployments können Benutzersitzungen möglicherweise nicht erhalten bleiben, insbesondere wenn große strukturelle Änderungen im Code vorgenommen werden. Dies kann Tests beeinträchtigen, die Benutzersitzungsdaten betreffen, und erfordert ein manuelles Sitzungsmanagement oder Umgehungskonfigurationen für die Entwicklung.
