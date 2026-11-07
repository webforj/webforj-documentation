---
title: Hotswap
sidebar_position: 10
sidebar_class_name: new-content
description: >-
  Apply compiled class changes to a running webforJ app without a restart,
  through HotswapAgent or JRebel configured in the webforJ build plugin.
_i18n_hash: 5c17c1ef76de9c15b7177aee4b2b8419
---
# Hotswap <DocChip chip='since' label='26.02' />

Ein Hotswap-Tool wendet Änderungen an kompilierten Klassen auf die laufende App an, ohne sie neu zu starten. Der Zustand der App bleibt zwischen den Aktualisierungen erhalten. Das Tool wird in der Konfiguration des [webforJ-Build-Plugins](/docs/configuration/build-plugin) angegeben und angehängt, wenn der Build die App startet. Der Ausführungsbefehl bleibt unverändert, und im Projekt muss keine Abhängigkeit dafür deklariert werden.

Es werden zwei Tools unterstützt:

- **HotswapAgent** ist Open Source. Das Build-Plugin lädt den Agent beim ersten Ausführen herunter und speichert ihn im Cache.
- **JRebel** ist ein kommerzielles Produkt. Es erfordert eine eigene Installation und Lizenz.

Konfigurieren Sie genau eines davon. Ein Build, in dem beide angegeben sind, schlägt mit einem Fehler fehl, der beide nennt.

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

Zwei Optionen ermöglichen eine genauere Konfiguration des Anbindens:

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

Änderungen am Methodenrumpf werden auf jeder Java Virtual Machine angewendet. Änderungen an der Struktur einer Klasse, etwa ein neues Feld oder eine neue Methode, erfordern eine Virtual Machine, die die Option `-XX:+AllowEnhancedClassRedefinition` akzeptiert. Diese wird von der [JetBrains Runtime](https://github.com/JetBrains/JetBrainsRuntime/releases) bereitgestellt. Der Build erkennt diese Fähigkeit und aktiviert sie. Informationen zur Installation einer JetBrains Runtime finden Sie unter [Voraussetzungen](/docs/introduction/prerequisites#java-development-kit-jdk-21).

Ohne diese Fähigkeit werden Änderungen am Methodenrumpf weiterhin angewendet; eine Änderung der Klassenstruktur wird jedoch erst nach einem Neustart in der laufenden App wirksam. Das Build-Protokoll gibt eine Warnung mit der erforderlichen Voraussetzung aus, und im Browser wird einmalig ein Hinweis angezeigt.

<!-- vale off -->
## JRebel {#jrebel}
<!-- vale on -->

[JRebel](https://www.jrebel.com/) ist ein kommerzielles Produkt, das vom Anbieter lizenziert wird. webforJ stellt es nicht bereit, lädt es nicht herunter und ist nicht an der Lizenzierung beteiligt. Der Build liest den konfigurierten Pfad, prüft, ob die Datei vorhanden ist, und bindet sie unverändert ein.

Verweisen Sie in der Konfiguration auf den Agent aus Ihrer JRebel-Installation – entweder eine native Bibliothek oder eine JAR-Datei:

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

Der Pfad ist erforderlich. Ein Build, der JRebel auswählt, ohne diesen Pfad anzugeben, schlägt mit einem Fehler fehl, der auf die fehlende Einstellung hinweist.

Mit JRebel werden alle Klassenänderungen, einschließlich Änderungen der Klassenstruktur, auf jeder Java-Laufzeitumgebung angewendet.

## Auswahl über die Befehlszeile {#command-line-selection}

Die Eigenschaft `webforj.hotswap` überschreibt die Build-Datei für einen einzelnen Lauf. Zulässige Werte sind `hotswapAgent`, `jrebel` und `off`. Jeder andere Wert führt dazu, dass der Build mit einem Fehler fehlschlägt, der die gültigen Werte auflistet. Bei Auswahl von `jrebel` muss weiterhin der Agent-Pfad in der Konfiguration angegeben sein.

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

## Änderungen anwenden {#applying-a-change}

Kompilieren Sie eine Änderung, damit sie in der laufenden App wirksam wird. Speichern Sie die Änderung in einer IDE, die beim Speichern kompiliert, oder starten Sie die Kompilierung in einem zweiten Terminal.

Wenn alle geänderten Klassen zu dem gehören, was auf der aktuellen Seite gerendert wird, wird der betroffene Teil an Ort und Stelle neu aufgebaut, und der App-Zustand bleibt erhalten. Andernfalls wird die gesamte Seite neu geladen: bei einer App ohne Routing, bei einer Klasse außerhalb der gerenderten Routen oder wenn der Neuaufbau nicht ausgeführt werden kann. Eine kompilierte Änderung führt zu genau einer Aktualisierung im Browser.

## Neuladevorgänge in IntelliJ IDEA beschleunigen {#speeding-up-reloads-in-intellij-idea}

IntelliJ IDEA kompiliert nur dann beim Speichern, wenn automatische Builds aktiviert sind, und wartet vor jedem Schritt. Verkürzen Sie die Wartezeiten, damit eine Änderung schneller in der laufenden App wirksam wird.

### Automatische Builds aktivieren {#enable-automatic-builds}

Öffnen Sie die Einstellungen und aktivieren Sie die folgenden Optionen:

1. Aktivieren Sie unter **Build, Execution, Deployment** > **Compiler** die Option **Build project automatically**.
<!-- vale off -->
2. Aktivieren Sie unter **Advanced Settings** > **Compiler** die Option **Allow auto-make to start even if developed application is currently running**.
<!-- vale on -->
3. Aktivieren Sie unter **Appearance & Behavior** > **System Settings** im Abschnitt **Autosave** die Option **Save files if the IDE is idle for** und stellen Sie den Wert auf `1` Sekunde ein.

<Tabs>
<TabItem value="windows-linux" label="Windows and Linux">

Drücken Sie <kbd>Ctrl</kbd>+<kbd>ALt</kbd>+<kbd>S</kbd> oder wählen Sie **File** > **Settings**.

</TabItem>
<TabItem value="macos" label="macOS">

Drücken Sie <kbd>Cmd</kbd>+<kbd>+</kbd> oder wählen Sie **IntelliJ IDEA** > **Settings**.

</TabItem>
</Tabs>

### Build-Verzögerungen verkürzen {#shorten-the-build-delays}

Legen Sie für die folgenden Registry-Schlüssel den Wert `0` fest:

| Schlüssel | Beschreibung |
|-----|-------------|
| `compiler.automake.trigger.delay` | Die Verzögerung zwischen einer Dateiänderung und dem Start eines Builds. |
| `compiler.automake.build.while.idle.timeout` | Die Leerlaufzeit, bevor ein Build gestartet wird. |
| `compiler.automake.postpone.when.idle.less.than` | Die Leerlaufzeit, unterhalb derer IntelliJ IDEA einen Build aufschiebt. |

<Tabs>
<TabItem value="windows-linux" label="Windows and Linux">

1. Drücken Sie <kbd>Ctrl</kbd>+<kbd>Shift</kbd>+<kbd>A</kbd>, um **Find Action** zu öffnen.
2. Geben Sie `Registry...` ein und wählen Sie den entsprechenden Eintrag aus.

</TabItem>
<TabItem value="macos" label="macOS">

1. Drücken Sie <kbd>Cmd</kbd>+<kbd>Shift</kbd>+<kbd>A</kbd>, um **Find Action** zu öffnen.
2. Geben Sie `Registry...` ein und wählen Sie den entsprechenden Eintrag aus.

</TabItem>
</Tabs>

Sie können auch zweimal <kbd>Shift</kbd> drücken, um **Search Everywhere** zu öffnen, zum Tab **Actions** wechseln und `Registry` eingeben.

Geben Sie im Dialogfeld **Registry** einen Schlüsselnamen ein, um die Liste zu filtern, und setzen Sie anschließend dessen Wert auf `0`.

:::warning
Registry-Einträge sind erweiterte, nicht unterstützte Einstellungen von IntelliJ IDEA. Wählen Sie im Dialogfeld **Registry** die Option **Restore Defaults**, um die Standardwerte wiederherzustellen.
:::
