---
title: JBang
sidebar_position: 15
sidebar_class_name: new-content
_i18n_hash: a8ffb21c2834adc74528dc39cb6d0497
---
# JBang <DocChip chip='since' label='25.11' />

[JBang](https://www.jbang.dev/) ist ein Werkzeug, mit dem Sie Java-Code als Skripte ausführen können, ohne Build-Dateien, Projekt-Setup oder manuelle Kompilierung. Die webforJ JBang-Integration ermöglicht es Ihnen, webforJ-Apps schnell zu erstellen, die sich am besten für schnelles Prototyping, Lernen und schnelle Demos eignen, ohne die traditionellen Abhängigkeiten und die Infrastruktur eines vollwertigen Java-Programms.

## Warum JBang mit webforJ verwenden {#why-use-jbang}

Traditionelle webforJ-Projekte verwenden Maven oder Gradle mit mehreren Konfigurationsdateien und einer Standardprojektstruktur. Dieses Setup ist für Produktions-Apps Standard, kann sich jedoch für einfache Experimente oder Demos schwer anfühlen.

Mit JBang können Sie:

- **Sofort starten**: Schreiben Sie eine einzige `.java`-Datei und führen Sie sie sofort aus
- **Projekt-Setup überspringen**: Keine `pom.xml`, kein `build.gradle`, keine Verzeichnisstruktur
- **Easily teilen**: Schicken Sie jemandem eine einzelne Datei, die er mit einem Befehl ausführen kann
- **Schneller lernen**: Konzentrieren Sie sich auf webforJ-Konzepte ohne die Komplexität von Build-Tools

Die Integration umfasst das automatische Herunterfahren des Servers, wenn Sie den Browser-Tab schließen, um Ihren Entwicklungs-Workflow sauber zu halten.

## Voraussetzungen {#prerequisites}

### JBang installieren {#install-jbang}

Wählen Sie Ihre bevorzugte Installationsmethode:

```bash
# Universal (Linux/macOS/Windows mit bash)
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
Wenn Sie JBang zum ersten Mal ohne installiertes JDK ausführen, lädt JBang automatisch eines herunter. Sie können die JDK-Version und den Anbieter vor dem Ausführen von JBang festlegen:

```bash
export JBANG_DEFAULT_JAVA_VERSION=21
export JBANG_JDK_VENDOR=temurin
```
:::

:::tip[Erfahren Sie mehr über JBang]
Für umfassende JBang-Dokumentation siehe:
- [JBang Erste Schritte](https://www.jbang.dev/documentation/jbang/latest/index.html) - Installation und Grundlagen
- [Skript-Direktiven-Referenz](https://www.jbang.dev/documentation/jbang/latest/script-directives.html) - Alle verfügbaren Direktiven
- [Abhängigkeiten](https://www.jbang.dev/documentation/jbang/latest/dependencies.html) - Erweitertes Abhängigkeitsmanagement
:::

## Erstellen eines webforJ-Skripts {#creating-a-script}

Erstellen Sie eine Datei mit dem Namen `HelloWorld.java` mit folgendem Inhalt:

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

  private FlexLayout self = getBoundComponent();
  private TextField hello = new TextField("Wie heißt du?");
  private Button btn = new Button("Sag Hallo");

  public MainView() {
    self.setDirection(FlexDirection.COLUMN);
    self.setMaxWidth(300);
    self.setStyle("margin", "1em auto");

    btn.setPrefixComponent(FeatherIcon.BELL.create())
        .setTheme(ButtonTheme.PRIMARY)
        .addClickListener(e -> Toast.show("Willkommen bei webforJ JBang Starter " + hello.getValue() + "!", Theme.GRAY));

    self.add(hello, btn);
  }
}

```

### Verständnis der Skriptstruktur {#script-structure}

| Zeile | Zweck |
|-------|-------|
| `///usr/bin/env jbang "$0" "$@" ; exit $?` | Shebang-Zeile, die es ermöglicht, das Skript direkt auf Unix-Systemen auszuführen |
| `//JAVA 21` | Gibt die erforderliche Mindest-Java-Version an; JBang lädt sie automatisch bei Bedarf herunter |
| `//DEPS com.webforj:webforj-jbang-starter:25.11` | Deklariert den webforJ JBang-Starter als Abhängigkeit mit Maven-Koordinaten |
| `@SpringBootApplication` | Aktiviert die automatische Konfiguration von Spring Boot |
| `extends App` | Macht diese Klasse zu einer webforJ-App |

Die Abhängigkeit `webforj-jbang-starter` enthält alles, was nötig ist, um eine webforJ-App auszuführen: den Spring Boot-Starter, Entwicklungstools und das automatische Öffnen des Browsers.

:::note[Version]
Ersetzen Sie `25.11` durch die neueste webforJ-Version. Überprüfen Sie [Maven Central](https://central.sonatype.com/artifact/com.webforj/webforj-jbang-starter) auf die aktuellste Version.
:::
### Hinzufügen von Abhängigkeiten {#adding-dependencies}

Sie können zusätzliche Maven-Abhängigkeiten mit mehreren `//DEPS`-Zeilen hinzufügen:

```java
///usr/bin/env jbang "$0" "$@" ; exit $?
//JAVA 21
//DEPS com.webforj:webforj-jbang-starter:25.11
//DEPS com.google.code.gson:gson:2.11.0
//DEPS org.apache.commons:commons-lang3:3.14.0
```

Abhängigkeiten verwenden Standard-Maven-Koordinaten (`groupId:artifactId:version`). JBang lädt sie beim ersten Ausführen automatisch von Maven Central herunter.

## Ausführen Ihres Skripts {#running-your-script}

Führen Sie das Skript mit JBang aus:

```bash
jbang HelloWorld.java
```

JBang wird:

1. Abhängigkeiten herunterladen (nur beim ersten Ausführen)
2. Das Skript kompilieren
3. Den eingebetteten Server auf einem zufällig verfügbaren Port starten
4. Ihren Standardbrowser zur App öffnen

### Das Skript ausführbar machen {#executable-script}

Auf Unix-Systemen können Sie das Skript direkt ausführbar machen:

```bash
chmod +x HelloWorld.java
./HelloWorld.java
```

Dies funktioniert aufgrund der Shebang-Zeile am Anfang der Datei.

## IDE-Unterstützung {#ide-support}

JBang integriert sich in beliebte Java-IDEs, einschließlich VS Code, IntelliJ IDEA, Eclipse und anderen. Diese Integrationen bieten Funktionen wie Direktive-Autocomplete, automatische Abhängigkeitsauflösung und die Möglichkeit, Skripte direkt aus der IDE auszuführen und zu debuggen.

Siehe die [JBang IDE-Integrationsdokumentation](https://www.jbang.dev/documentation/jbang/latest/editing.html) für Anweisungen zur Einrichtung und unterstützte Editor.

## Konfiguration {#configuration}

Der webforJ JBang-Starter enthält sinnvolle Standardeinstellungen, die für Skripting optimiert sind. Sie können das Verhalten mithilfe von Systemeigenschaften anpassen.

### Automatisches Herunterfahren {#auto-shutdown}

Standardmäßig wird der Server automatisch heruntergefahren, wenn alle Browser-Tabs, die mit der App verbunden sind, geschlossen werden. Dies hält Ihren Entwicklungs-Workflow sauber, indem keine verwaisten Server ausgeführt werden.

| Eigenschaft | Standard | Beschreibung |
|-------------|----------|--------------|
| `webforj.jbang.auto-shutdown` | `true` | Automatisches Herunterfahren ein- oder ausschalten |
| `webforj.jbang.idle-timeout` | `5` | Sekunden, die nach der letzten Browser-Trennung gewartet wird, bevor ein Herunterfahren erfolgt |

Um das automatische Herunterfahren auszuschalten:

```bash
jbang -Dwebforj.jbang.auto-shutdown=false HelloWorld.java
```

Um das Leerlauf-Timeout zu ändern:

```bash
jbang -Dwebforj.jbang.idle-timeout=30 HelloWorld.java
```

### Standardeinstellungen {#default-settings}

Der JBang-Starter konfiguriert folgende Standardeinstellungen:

| Einstellung | Wert | Beschreibung |
|-------------|------|--------------|
| `server.port` | `0` | Zufällige Portzuweisung, um Konflikte beim Ausführen mehrerer Skripte zu vermeiden |
| `server.shutdown` | `immediate` | Schnelles Herunterfahren für eine schnelle Skriptbeendigung |
| `spring.main.banner-mode` | `off` | Blendet das Spring Boot-Banner für eine sauberere Ausgabe aus |
| `logging.level.root` | `ERROR` | Minimales Protokollieren, um die Konsolenausgabe sauber zu halten |
| `logging.level.com.webforj` | `WARN` | Zeigt nur Warnungen und Fehler von webforJ an |
| `webforj.devtools.browser.open` | `true` | Öffnet automatisch den Browser, wenn die App gestartet wird |

### Neufestlegung und Live-Reload {#development-workflow}

JBang-Skripte unterstützen kein Live-Reload. Um Änderungen zu sehen:

1. Stoppen Sie das laufende Skript (schließen Sie den Browser-Tab oder drücken Sie `Ctrl+C`)
2. Bearbeiten Sie Ihren Code
3. Führen Sie `jbang HelloWorld.java` erneut aus

Für automatisches Neufestlegen während der Entwicklung ziehen Sie in Betracht, ein [vollständiges Maven-Projekt mit Spring DevTools](/docs/integrations/spring/spring-boot) zu verwenden. Weitere Informationen finden Sie in der [Live-Reload-Dokumentation](/docs/configuration/deploy-reload/overview).

## Übergang zu einem vollständigen Projekt {#transitioning}

Wenn Ihr Prototyp über eine einzelne Datei hinauswächst, erstellen Sie ein richtiges Projekt mit [startforJ](https://docs.webforj.com/startforj) oder dem [Maven-Archetyp](./spring/spring-boot#option-2-using-the-command-line). Sie können Ihre Skriptlogik direkt in die generierte Projektstruktur kopieren.
