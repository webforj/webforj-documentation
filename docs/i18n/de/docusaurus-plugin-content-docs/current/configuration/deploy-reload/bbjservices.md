---
title: BBjServices
sidebar_position: 45
sidebar_class_name: new-content
description: >-
  Set up live reload and hotswap for a webforJ app that runs in the BBjServices
  of your own machine through BBjShip.
_i18n_hash: 46a42789a4b05b258a057e9acae1ef12
---
In BBjServices startet der Build keinen eigenen Server. `mvn compile webforj:watch` führt die App in den BBjServices deiner eigenen Maschine über [BBjShip](/docs/configuration/bbjship/overview) direkt aus dem Projekt aus. Ein Archetyp-Projekt mit dem BBj-Geschmack setzt dies als sein Standard-Maven-Ziel, sodass `mvn` ohne Argumente die App kompiliert, den [Frontend-Watch](/docs/configuration/deploy-reload/frontend-watch) startet und die App ausführt.

## Anforderungen {#requirements}

Ein Maven-Projekt, das für BBjShip eingerichtet ist. Siehe [Erste Schritte](/docs/configuration/bbjship/getting-started) oder [Vorhandene Projekte](/docs/configuration/bbjship/existing-projects), mit installiertem BBj auf derselben Maschine wie das Projekt. BBjServices lädt die Klassen aus den Ordnern, in die der Build kompiliert, sodass ein BBj-Server auf einer anderen Maschine nur [Deployments](/docs/configuration/bbjship/deploying) erhält, jedoch ohne Live-Reload oder Hotswap.

## Live-Reload aktivieren {#turning-live-reload-on}

```ini title="webforj.conf"
webforj.devtools.livereload.enabled = true
```

Die verbleibenden Schlüssel sind in den [Einstellungen](/docs/configuration/deploy-reload/overview#settings) aufgeführt.

## Klassenänderungen {#class-changes}

In BBjServices erreicht eine Klassenänderung die laufende App nur über ein [Hotswap-Tool](/docs/configuration/deploy-reload/hotswap). Ohne eines kommt eine kompilierte Änderung nicht zur Anwendung, und die Seite wird nicht neu geladen, bis der Watch erneut startet. Ein Archetyp-Projekt konfiguriert HotswapAgent:

```xml title="pom.xml"
<hotswap>
  <hotswapAgent/>
</hotswap>
```

Das Tool wird an BBjServices angehängt, nicht an den Build. Der erste Lauf fügt es zu den Java-Argumenten von BBjServices hinzu und startet BBjServices einmal neu. Spätere Läufe starten ohne einen Neustart. Unter macOS und Linux sowie unter Windows, wenn BBjServices nicht als Windows-Dienst registriert ist, läuft das neu gestartete BBjServices außerhalb des Dienstverwaltungsprogramms des Betriebssystems, bis die Maschine neu gestartet wird. [Fehlerbehebung](/docs/configuration/bbjship/troubleshooting#development-runs) listet die Meldungen eines Entwicklungslaufs und was man zu jedem tun kann.

:::warning Neustarts von BBjServices
Der erste Lauf ändert die Java-Einstellungen von BBjServices in `<BBj home>/cfg/BBj.properties` und startet BBjServices neu. Der Neustart beendet jede laufende BBj-Sitzung auf dieser Maschine, nicht nur die von webforJ, daher führe ihn aus, wenn sonst niemand diesen BBj verwendet. Vor der ersten Änderung wird eine Kopie der Datei als `BBj.properties.before-webforj` im Ordner `<BBj home>/plugins/.webforj-bbjship-deployments` aufbewahrt. Sichere die Datei zuerst selbst, wenn du sie von Hand geändert hast. `mvn webforj:bbj-setup` nimmt dieselbe Änderung vor.
:::

Eine Abhängigkeitsänderung benötigt einen neuen Start des Watch.

### Java von BBjServices {#java-of-bbjservices}

Mit HotswapAgent hängen [Änderungen in der Klassenstruktur](/docs/configuration/deploy-reload/hotswap#class-structure-changes) von dem Java ab, auf dem BBjServices läuft, nicht von dem Java des Builds. Der Watch ändert es niemals. Um BBjServices auf einer [JetBrains Runtime](https://github.com/JetBrains/JetBrainsRuntime/releases) auszuführen:

1. Gehe im Enterprise Manager zu **BBjServices**, **Java-Einstellungen**.
2. Wähle **BBjServices** unter **Anwendung** und setze das Java Home.
3. Starte BBjServices neu. **Java-Details** im Enterprise Manager zeigt dann das JDK und die Version an, die es verwendet.

Siehe [Voraussetzungen](/docs/introduction/prerequisites#java-development-kit-jdk-21) für die Installation einer JetBrains Runtime.

## Produktionsbuilds {#production-builds}

`mvn install` stellt die verpackte App ohne Entwicklungstools bereit. Siehe [Deploying](/docs/configuration/bbjship/deploying). Die Eigenschaft `webforj.devtools.livereload.enabled` hat in einer verpackten App keine Auswirkung.
