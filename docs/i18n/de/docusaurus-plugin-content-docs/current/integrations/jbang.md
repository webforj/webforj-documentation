---
title: JBang
sidebar_position: 15
description: >-
  Run webforJ apps as single-file JBang scripts with no Maven, Gradle, or
  project structure for rapid prototyping and demos.
_i18n_hash: a7af803fa3e58cb86e0c18289f8fbb40
---
# JBang <DocChip chip='since' label='25.11' />

[JBang](https://www.jbang.dev/) ist ein Tool, mit dem Sie Java-Code als Skripte ausführen können – ohne Build-Dateien, Projekteinrichtung oder manuelle Kompilierung. Die JBang-Integration von webforJ ermöglicht es Ihnen, webforJ-Apps schnell zu erstellen. Sie eignet sich besonders für schnelles Prototyping, zum Lernen und für kurze Demos, ohne dass die herkömmlichen Abhängigkeiten und die Infrastruktur eines vollwertigen Java-Programms erforderlich sind.

## Warum JBang mit webforJ verwenden? {#why-use-jbang}

Herkömmliche webforJ-Projekte verwenden Maven oder Gradle mit mehreren Konfigurationsdateien und einer standardisierten Projektstruktur. Diese Einrichtung ist für Produktions-Apps üblich, kann sich für einfache Experimente oder Demos jedoch als aufwendig erweisen.

Mit JBang können Sie:

- **Sofort loslegen**: Eine einzelne `.java`-Datei schreiben und direkt ausführen
- **Die Projekteinrichtung überspringen**: Kein `pom.xml`, kein `build.gradle`, keine Verzeichnisstruktur
- **Einfach teilen**: Jemandem eine einzelne Datei schicken, die mit einem Befehl ausgeführt werden kann
- **Schneller lernen**: Sich ohne die Komplexität von Build-Tools auf webforJ-Konzepte konzentrieren

Die Integration beendet den Server automatisch, wenn Sie den Browser-Tab schließen, und sorgt so für einen aufgeräumten Entwicklungsworkflow.

## Voraussetzungen {#prerequisites}

### JBang installieren {#install-jbang}

Wählen Sie Ihre bevorzugte Installationsmethode:

```bash
# Universal (Linux/macOS/Windows with bash)
curl -Ls https://sh.jbang.dev | bash -s - app setup

# SDKMan
sdk install jbang

# Homebrew (macOS)
brew install jbangdev/tap/jbang

# Chocolatey (Windows)
choco install jbang

# Scoop (Windows)
scoop install jbang
```

Überprüfen Sie die Installation:

```bash
jbang --version
```

:::info[Standard-Java-Version]
Wenn Sie JBang zum ersten Mal ausführen und kein JDK installiert ist, lädt JBang automatisch eines herunter. Sie können die JDK-Version und den Anbieter festlegen, bevor Sie JBang ausführen:

```bash
export JBANG_DEFAULT_JAVA_VERSION=21
export JBANG_JDK_VENDOR=temurin
```
:::

:::tip[Mehr über JBang erfahren]
Eine umfassende Dokumentation zu JBang finden Sie hier:
- [Erste Schritte mit JBang](https://www.jbang.dev/documentation/jbang/latest/index.html) – Installation und Grundlagen
- [Referenz zu Skript-Direktiven](https://www.jbang.dev/documentation/jbang/latest/script-directives.html) – Alle verfügbaren Direktiven
- [Abhängigkeiten](https://www.jbang.dev/documentation/jbang/latest/dependencies.html) – Erweiterte Verwaltung von Abhängigkeiten
:::

## Ein webforJ-Skript erstellen {#creating-a-script}

Erstellen Sie eine Datei namens `HelloWorld.java` mit folgendem Inhalt:

```java title="HelloWorld.java"
///usr/bin/env jbang "$0" "$@" ; exit $?
//DEPS com.webforj:webforj-jbang-starter:25.11
//JAVA 21

package bang;

import com.webforj.App;
import com.webforj.annotation.Routify;
import com.webforj.component.Composite;
import com.webforj.component.Theme;
import com.webforj.component.button.Button;
import com.webforj.component.button.ButtonTheme;
import com.webforj.component.field.TextField;
import com.webforj.component.icons.FeatherIcon;
import com.webforj.component.layout.flexlayout.FlexDirection;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.component.toast.Toast;
import com.webforj.router.annotation.Route;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Routify
public class HelloWorld extends App {
  public static void main(String[] args) {
    SpringApplication.run(HelloWorld.class, args);
  }
}

@Route("/")
class MainView extends Composite<FlexLayout> {

  private final FlexLayout self = getBoundComponent();
  private TextField hello = new TextField("What is your name?");
  private Button btn = new Button("Say Hello");

  public MainView() {
    self.setDirection(FlexDirection.COLUMN);
    self.setMaxWidth(300);
    self.setStyle("margin", "1em auto");

    btn.setPrefixComponent(FeatherIcon.BELL.create())
        .setTheme(ButtonTheme.PRIMARY)
        .addClickListener(e -> Toast.show("Welcome to webforJ JBang Starter " + hello.getValue() + "!", Theme.GRAY));

    self.add(hello, btn);
  }
}

```

### Aufbau des Skripts verstehen {#script-structure}

| Zeile | Zweck |
|------|---------|
| `///usr/bin/env jbang "$0" "$@" ; exit $?` | Shebang-Zeile, mit der das Skript direkt auf Unix-Systemen ausgeführt werden kann |
| `//JAVA 21` | Gibt die erforderliche Mindestversion von Java an; JBang lädt sie bei Bedarf automatisch herunter |
| `//DEPS com.webforj:webforj-jbang-starter:25.11` | Deklariert den JBang-Starter von webforJ mithilfe von Maven-Koordinaten als Abhängigkeit |
| `@SpringBootApplication` | Aktiviert die Spring-Boot-Autokonfiguration |
| `extends App` | Macht diese Klasse zu einer webforJ-App |

Die Abhängigkeit `webforj-jbang-starter` enthält alles, was zum Ausführen einer webforJ-App benötigt wird: den Spring-Boot-Starter, Entwicklungstools und das automatische Öffnen des Browsers.

:::note[Version]
Ersetzen Sie `25.11` durch die neueste webforJ-Version. Die aktuellste Version finden Sie auf [Maven Central](https://central.sonatype.com/artifact/com.webforj/webforj-jbang-starter).
:::
### Abhängigkeiten hinzufügen {#adding-dependencies}

Sie können zusätzliche Maven-Abhängigkeiten mit mehreren `//DEPS`-Zeilen hinzufügen:

```java
///usr/bin/env jbang "$0" "$@" ; exit $?
//JAVA 21
//DEPS com.webforj:webforj-jbang-starter:25.11
//DEPS com.google.code.gson:gson:2.11.0
//DEPS org.apache.commons:commons-lang3:3.14.0
```

Abhängigkeiten verwenden standardmäßige Maven-Koordinaten (`groupId:artifactId:version`). JBang lädt sie beim ersten Ausführen automatisch von Maven Central herunter.

## Skript ausführen {#running-your-script}

Führen Sie das Skript mit JBang aus:

```bash
jbang HelloWorld.java
```

JBang wird:

1. Abhängigkeiten herunterladen (nur beim ersten Ausführen)
2. Das Skript kompilieren
3. Den eingebetteten Server auf einem zufällig ausgewählten verfügbaren Port starten
4. Ihren Standardbrowser mit der App öffnen

### Skript ausführbar machen {#executable-script}

Auf Unix-Systemen können Sie das Skript direkt ausführbar machen:

```bash
chmod +x HelloWorld.java
./HelloWorld.java
```

Dies funktioniert aufgrund der Shebang-Zeile am Anfang der Datei.

## IDE-Unterstützung {#ide-support}

JBang lässt sich in beliebte Java-IDEs integrieren, darunter VS Code, IntelliJ IDEA, Eclipse und weitere. Diese Integrationen bieten Funktionen wie die automatische Vervollständigung von Direktiven, die automatische Auflösung von Abhängigkeiten sowie die Möglichkeit, Skripte direkt in der IDE auszuführen und zu debuggen.

Anweisungen zur Einrichtung und eine Liste der unterstützten Editoren finden Sie in der [Dokumentation zur JBang-IDE-Integration](https://www.jbang.dev/documentation/jbang/latest/editing.html).

## Konfiguration {#configuration}

Der JBang-Starter von webforJ enthält sinnvolle, für Skripte optimierte Standardeinstellungen. Sie können das Verhalten mithilfe von Systemeigenschaften anpassen.

### Automatisches Beenden {#auto-shutdown}

Standardmäßig wird der Server automatisch beendet, wenn alle mit der App verbundenen Browser-Tabs geschlossen werden. So bleibt Ihr Entwicklungsworkflow aufgeräumt und es laufen keine verwaisten Server weiter.

| Eigenschaft | Standardwert | Beschreibung |
|----------|---------|-------------|
| `webforj.jbang.auto-shutdown` | `true` | Aktiviert oder deaktiviert das automatische Beenden |
| `webforj.jbang.idle-timeout` | `5` | Sekunden, die nach dem Trennen des letzten Browsers bis zum Beenden gewartet wird |

So deaktivieren Sie das automatische Beenden:

```bash
jbang -Dwebforj.jbang.auto-shutdown=false HelloWorld.java
```

So ändern Sie die Leerlaufzeitüberschreitung:

```bash
jbang -Dwebforj.jbang.idle-timeout=30 HelloWorld.java
```

### Standardeinstellungen {#default-settings}

Der JBang-Starter konfiguriert die folgenden Standardeinstellungen:

| Einstellung | Wert | Beschreibung |
|---------|-------|-------------|
| `server.port` | `0` | Zufällige Portzuweisung, um Konflikte beim Ausführen mehrerer Skripte zu vermeiden |
| `server.shutdown` | `immediate` | Schnelles Beenden, damit Skripte zügig beendet werden |
| `spring.main.banner-mode` | `off` | Blendet das Spring-Boot-Banner aus, um die Ausgabe übersichtlicher zu halten |
| `logging.level.root` | `ERROR` | Minimale Protokollierung, damit die Konsolenausgabe übersichtlich bleibt |
| `logging.level.com.webforj` | `WARN` | Zeigt nur Warnungen und Fehler von webforJ an |
| `webforj.devtools.browser.open` | `true` | Öffnet beim Start der App automatisch den Browser |

### Neubereitstellung und Live-Reload {#development-workflow}

JBang-Skripte unterstützen kein Live-Reload. So übernehmen Sie Änderungen:

1. Beenden Sie das laufende Skript (schließen Sie den Browser-Tab oder drücken Sie <kbd>Ctrl</kbd>+<kbd>C</kbd>)
2. Bearbeiten Sie Ihren Code
3. Führen Sie `jbang HelloWorld.java` erneut aus

Für eine automatische Neubereitstellung während der Entwicklung können Sie ein [vollständiges Maven-Projekt mit Spring DevTools](/docs/integrations/spring/spring-boot) verwenden. Weitere Informationen finden Sie in der [Dokumentation zu Live-Reload](/docs/configuration/deploy-reload/overview).

## Zu einem vollständigen Projekt wechseln {#transitioning}

Wenn Ihr Prototyp über eine einzelne Datei hinauswächst, erstellen Sie ein richtiges Projekt mit [startforJ](https://docs.webforj.com/startforj) oder dem [Maven-Archetyp](./spring/spring-boot#option-2-using-the-command-line). Sie können die Logik Ihres Skripts direkt in die generierte Projektstruktur übernehmen.
