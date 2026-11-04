---
title: Hotswap
sidebar_position: 10
sidebar_class_name: new-content
description: >-
  Apply compiled class changes to a running webforJ app without a restart,
  through HotswapAgent or JRebel configured in the webforJ build plugin.
_i18n_hash: 97e2fbe0bd74ada89fb19fae84fc4102
---
# Hotswap <DocChip chip='since' label='26.02' />

Ein Hot-Swap-Werkzeug übernimmt Änderungen an kompilierten Klassen in der laufenden App, ohne sie neu zu starten. Die App behält ihren Zustand zwischen den Aktualisierungen bei. Das Werkzeug wird in der Konfiguration des [webforJ-Build-Plugins](/docs/configuration/build-plugin) angegeben und angehängt, wenn der Build die App startet. Der Ausführungsbefehl bleibt unverändert, und das Projekt muss dafür keine Abhängigkeit deklarieren.

Es werden zwei Werkzeuge unterstützt:

- **HotswapAgent** ist Open Source. Das Build-Plugin lädt den Agent beim ersten Ausführen herunter und speichert ihn im Cache.
- **JRebel** ist ein kommerzielles Produkt. Es erfordert eine eigene Installation und Lizenz.

Konfigurieren Sie genau eines der beiden Werkzeuge. Ein Build, in dem beide angegeben sind, schlägt mit einer Fehlermeldung fehl, die beide nennt.

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

Zwei Optionen ermöglichen eine genauere Konfiguration des Anhängens:

| Option | Beschreibung |
|--------|-------------|
| `version` | Eine bestimmte Agent-Version anstelle der vom Plugin ausgewählten Version. |
| `path` | Eine Agent-JAR-Datei auf dem Datenträger, die direkt und ohne Download verwendet wird. Für Rechner ohne Netzwerkzugriff oder für einen benutzerdefinierten Agent-Build. |

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

### Änderungen an der Klassenstruktur {#class-structure-changes}

Änderungen am Methodenrumpf werden auf jeder Java Virtual Machine übernommen. Änderungen an der Struktur einer Klasse, etwa ein neues Feld oder eine neue Methode, erfordern eine Virtual Machine, die die Option `-XX:+AllowEnhancedClassRedefinition` akzeptiert. Diese wird von der [JetBrains Runtime](https://github.com/JetBrains/JetBrainsRuntime/releases) bereitgestellt. Der Build erkennt diese Fähigkeit und aktiviert sie. Informationen zur Installation einer JetBrains Runtime finden Sie unter [Voraussetzungen](/docs/introduction/prerequisites#java-development-kit-jdk-21).

Ohne diese Fähigkeit werden Änderungen am Methodenrumpf weiterhin übernommen, Änderungen an der Klassenstruktur erreichen die laufende App jedoch erst nach einem Neustart. Das Build-Protokoll gibt eine Warnung mit der erforderlichen Einstellung aus, und im Browser wird einmalig ein Hinweis angezeigt.

<!-- vale off -->
## JRebel {#jrebel}
<!-- vale on -->

[JRebel](https://www.jrebel.com/) ist ein kommerzielles Produkt, das vom Anbieter lizenziert wird. webforJ liefert es nicht mit, lädt es nicht herunter und ist nicht an der Lizenzierung beteiligt. Der Build liest den konfigurierten Pfad, prüft, ob die Datei vorhanden ist, und hängt sie unverändert an.

Verweisen Sie in der Konfiguration auf den Agent aus Ihrer JRebel-Installation, eine native Bibliothek oder eine JAR-Datei:

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

Der Pfad ist erforderlich. Ein Build, der JRebel auswählt, ohne diesen Pfad anzugeben, schlägt mit einer Fehlermeldung zur fehlenden Einstellung fehl.

Mit JRebel werden alle Klassenänderungen, einschließlich Änderungen an der Klassenstruktur, auf jeder Java-Laufzeitumgebung übernommen.

## Auswahl über die Befehlszeile {#command-line-selection}

Die Eigenschaft `webforj.hotswap` überschreibt für einen einzelnen Lauf die Build-Datei. Zulässige Werte sind `hotswapAgent`, `jrebel` und `off`. Jeder andere Wert führt dazu, dass der Build mit einer Fehlermeldung fehlschlägt, die die zulässigen Werte auflistet. Bei Auswahl von `jrebel` muss der Agent-Pfad weiterhin in der Konfiguration angegeben sein.

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

## Änderungen übernehmen {#applying-a-change}

Kompilieren Sie eine Änderung, damit sie die laufende App erreicht. Speichern Sie die Änderung in einer IDE, die beim Speichern kompiliert, oder führen Sie die Kompilierung in einem zweiten Terminal aus.

Wenn alle geänderten Klassen zu dem gehören, was die aktuelle Seite rendert, wird der betroffene Teil an Ort und Stelle neu aufgebaut und der App-Zustand bleibt erhalten. Andernfalls wird die Seite vollständig neu geladen: bei einer App ohne Routing, bei einer Klasse außerhalb der gerenderten Routen oder wenn der Neuaufbau nicht durchgeführt werden kann. Eine kompilierte Änderung führt zu genau einer Aktualisierung im Browser.

## Neuladevorgänge in IntelliJ IDEA beschleunigen {#speeding-up-reloads-in-intellij-idea}

IntelliJ IDEA kompiliert beim Speichern nur, wenn automatische Builds aktiviert sind, und wartet vor jedem Schritt. Verkürzen Sie die Wartezeiten, damit Änderungen die laufende App schneller erreichen.

### Automatische Builds aktivieren {#enable-automatic-builds}

Öffnen Sie die Einstellungen und aktivieren Sie die folgenden Optionen:

1. Aktivieren Sie unter **Build, Execution, Deployment** > **Compiler** die Option **Build project automatically**.
<!-- vale off -->
2. Aktivieren Sie unter **Advanced Settings** > **Compiler** die Option **Allow auto-make to start even if developed application is currently running**.
<!-- vale on -->
3. Aktivieren Sie unter **Appearance & Behavior** > **System Settings** im Abschnitt **Autosave** die Option **Save files if the IDE is idle for** und setzen Sie den Wert auf `1` Sekunde.

<Tabs>
<TabItem value="windows-linux" label="Windows and Linux">

Drücken Sie <kbd>Ctrl+ALt+S</kbd> oder wählen Sie **File** > **Settings**.

</TabItem>
<TabItem value="macos" label="macOS">

Drücken Sie <kbd>Cmd+,</kbd> oder wählen Sie **IntelliJ IDEA** > **Settings**.

</TabItem>
</Tabs>

### Build-Verzögerungen verkürzen {#shorten-the-build-delays}

Setzen Sie die folgenden Registry-Schlüssel auf `0`:

| Schlüssel | Beschreibung |
|-----|-------------|
| `compiler.automake.trigger.delay` | Die Verzögerung zwischen einer Dateiänderung und dem Start eines Builds. |
| `compiler.automake.build.while.idle.timeout` | Die Leerlaufzeit, bevor ein Build startet. |
| `compiler.automake.postpone.when.idle.less.than` | Die Leerlaufzeit, unterhalb derer IntelliJ IDEA einen Build verschiebt. |

<Tabs>
<TabItem value="windows-linux" label="Windows and Linux">

1. Drücken Sie `Ctrl+Shift+A`, um **Find Action** zu öffnen.
2. Geben Sie `Registry...` ein und wählen Sie den Eintrag aus.

</TabItem>
<TabItem value="macos" label="macOS">

1. Drücken Sie `Cmd+Shift+A`, um **Find Action** zu öffnen.
2. Geben Sie `Registry...` ein und wählen Sie den Eintrag aus.

</TabItem>
</Tabs>

Sie können auch zweimal `Shift` drücken, um **Search Everywhere** zu öffnen, zum Tab **Actions** wechseln und `Registry` eingeben.

Geben Sie im Dialogfeld **Registry** einen Schlüsselnamen ein, um die Liste zu filtern, und setzen Sie dann den zugehörigen Wert auf `0`.

:::warning
Registry-Einträge sind erweiterte, nicht unterstützte Einstellungen von IntelliJ IDEA. Wählen Sie im Dialogfeld **Registry** die Option **Restore Defaults**, um sie zurückzusetzen.
:::
