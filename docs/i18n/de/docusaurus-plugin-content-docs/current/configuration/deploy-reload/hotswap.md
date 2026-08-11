---
title: Hotswap
sidebar_position: 10
sidebar_class_name: new-content
description: >-
  Apply compiled class changes to a running webforJ app without a restart,
  through HotswapAgent or JRebel configured in the webforJ build plugin.
_i18n_hash: 0943bf726abb55f753a0149ca3744ad7
---
# Hotswap <DocChip chip='since' label='26.02' />

Ein Hotswap-Tool wendet compilierte Klassenänderungen auf die laufende Anwendung an, ohne sie neu zu starten. Die Anwendung behält ihren Zustand zwischen den Aktualisierungen. Das Tool wird in der Konfiguration des [webforJ-Build-Plugins](/docs/configuration/build-plugin) benannt und wird aktiv, wenn der Build die Anwendung startet. Der Ausführungsbefehl bleibt gleich, und das Projekt gibt keine Abhängigkeit dafür an.

Es werden zwei Tools unterstützt:

- **HotswapAgent** ist Open Source. Das Build-Plugin lädt den Agenten beim ersten Ausführen herunter und cached ihn.
- **JRebel** ist ein kommerzielles Produkt. Es erfordert Ihre eigene Installation und Lizenz.

Konfigurieren Sie genau eines. Ein Build, das beide benennt, schlägt mit einem Fehler fehl.

<!-- vale off -->
## HotswapAgent {#hotswapagent}
<!-- vale on -->

Ein leeres Element ist eine vollständige Konfiguration:

<Tabs>
<TabItem value="maven" label="Maven">

```xml title="pom.xml"
<plugin>
  <groupId>com.webforj</groupId>
  <artifactId>webforj-maven-plugin</artifactId>
  <extensions>true</extensions>
  <configuration>
    <hotswap>
      <hotswapAgent/>
    </hotswap>
  </configuration>
</plugin>
```

</TabItem>
<TabItem value="gradle" label="Gradle">

```groovy title="build.gradle"
webforj {
  hotswap {
    hotswapAgent {}
  }
}
```

</TabItem>
</Tabs>

Zwei Optionen verfeinern die Anbindung:

| Option | Beschreibung |
|--------|-------------|
| `version` | Eine spezifische Agentversion anstelle der vom Plugin ausgewählten. |
| `path` | Eine Agent-JAR auf der Festplatte, die direkt ohne Download verwendet wird. Für Maschinen ohne Netzwerkzugang oder für einen benutzerdefinierten Agentenbuild. |

<Tabs>
<TabItem value="maven" label="Maven">

```xml title="pom.xml"
<hotswap>
  <hotswapAgent>
    <path>/path/to/hotswap-agent.jar</path>
  </hotswapAgent>
</hotswap>
```

</TabItem>
<TabItem value="gradle" label="Gradle">

```groovy title="build.gradle"
webforj {
  hotswap {
    hotswapAgent {
      path = file('/path/to/hotswap-agent.jar')
    }
  }
}
```

</TabItem>
</Tabs>

### Änderungen der Klassenstruktur {#class-structure-changes}

Änderungen des Methodenkörpers gelten auf jeder Java Virtual Machine. Änderungen an der Struktur einer Klasse, wie ein neues Feld oder eine neue Methode, erfordern eine virtuelle Maschine, die die Option `-XX:+AllowEnhancedClassRedefinition` akzeptiert, die von der [JetBrains Runtime](https://github.com/JetBrains/JetBrainsRuntime/releases) bereitgestellt wird. Der Build erkennt die Fähigkeit und aktiviert sie. Siehe [Voraussetzungen](/docs/introduction/prerequisites#java-development-kit-jdk-21) für die Installation einer JetBrains Runtime.

Ohne diese Fähigkeit gelten die Änderungen des Methodenkörpers weiterhin, und eine Änderung der Klassenstruktur erreicht die laufende Anwendung nicht, bis eine Neuladung erfolgt. Das Build-Log gibt eine Warnung aus, die die Anforderung benennt, und der Browser zeigt einmal eine Benachrichtigung an.

<!-- vale off -->
## JRebel {#jrebel}
<!-- vale on -->

[JRebel](https://www.jrebel.com/) ist ein kommerzielles Produkt, das von seinem Anbieter lizenziert ist. webforJ schickt es nicht mit, lädt es nicht herunter und nimmt keine an seinem Lizenzwesen teil. Der Build liest den konfigurierten Pfad, überprüft, dass die Datei vorhanden ist, und schließt sie unverändert an.

Richten Sie die Konfiguration auf den Agenten Ihrer JRebel-Installation, einer nativen Bibliothek oder einer JAR:

<Tabs>
<TabItem value="maven" label="Maven">

```xml title="pom.xml"
<hotswap>
  <jrebel>
    <path>/path/to/libjrebel64.dylib</path>
  </jrebel>
</hotswap>
```

</TabItem>
<TabItem value="gradle" label="Gradle">

```groovy title="build.gradle"
webforj {
  hotswap {
    jrebel {
      path = file('/path/to/libjrebel64.dylib')
    }
  }
}
```

</TabItem>
</Tabs>

Der Pfad ist erforderlich. Ein Build, der JRebel ohne diesen auswählt, schlägt mit einem Fehler fehl, der die fehlende Einstellung benennt.

Mit JRebel gelten alle Klassenänderungen, einschließlich Strukturänderungen, auf jeder Java-Laufzeit.

## Command Line-Auswahl {#command-line-selection}

Die `webforj.hotswap`-Eigenschaft überschreibt die Build-Datei für einen einzelnen Lauf. Akzeptierte Werte sind `hotswapAgent`, `jrebel` und `off`. Jeder andere Wert führt zu einem Build-Fehler mit einer Fehlerausgabe zur Auflistung der gültigen Werte. Die Auswahl von `jrebel` erfordert weiterhin den Agentenpfad in der Konfiguration.

<Tabs>
<TabItem value="maven" label="Maven">

```bash
mvn -Dwebforj.hotswap=off
mvn -Dwebforj.hotswap=hotswapAgent
```

</TabItem>
<TabItem value="gradle" label="Gradle">

```bash
./gradlew bootRun -Pwebforj.hotswap=off
./gradlew bootRun -Pwebforj.hotswap=hotswapAgent
```

</TabItem>
</Tabs>

## Anwenden einer Änderung {#applying-a-change}

Kompilieren Sie eine Änderung, und sie erreicht die laufende Anwendung. Speichern Sie in einer IDE, die beim Speichern kompiliert, oder führen Sie eine Kompilierung in einem zweiten Terminal aus.

Wenn jede geänderte Klasse zu dem gehört, was die aktuelle Seite rendert, wird der betroffene Teil vor Ort neu aufgebaut und der Anwendungszustand bleibt bestehen. Andernfalls wird die Seite vollständig neu geladen: für eine Anwendung ohne Routing, für eine Klasse außerhalb der gerenderten Routen oder wenn der Neubau nicht durchgeführt werden kann. Eine kompilierte Änderung ergibt ein Update im Browser.
