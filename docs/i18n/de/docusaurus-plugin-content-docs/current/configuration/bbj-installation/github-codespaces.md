---
sidebar_position: 2
title: Github Codespaces
_i18n_hash: ece2c4669673edd4de7ed74c967c9e4f
---
import UnderConstruction from '@site/src/components/PageTools/UnderConstruction';

[`webforj-hello-world`](https://github.com/webforj/webforj-hello-world) wurde so konfiguriert, dass es in Github Codespaces läuft. Codespaces ist eine cloudbasierte Entwicklungsumgebung, die es Ihnen ermöglicht, webforJ-Anwendungen direkt in Ihrem Browser zu entwickeln und auszuführen. Um mit diesem Tool zu beginnen, befolgen Sie die folgenden Schritte:

## 1. Navigieren Sie zum HelloWorldJava-Repository {#1-navigate-to-the-helloworldjava-repository}

Zunächst müssen Sie zum HelloWorldJava-Repository gehen, das Sie [unter diesem Link](https://github.com/webforj/webforj-hello-world) finden können. Klicken Sie dann auf die grüne **"Use this template"**-Schaltfläche und wählen Sie die Option **"Open in a codespace"**.

![Codespace buttons](/img/bbj-installation/github/1.png#rounded-border)

## 2. Führen Sie Ihr Programm aus {#2-running-your-program}

Nachdem Sie gewartet haben, bis der Codespace geladen ist, sollten Sie eine Browser-Version von VS Studio Code mit dem geladenen "HelloWorldJava"-Beispielprogramm sehen. Von hier aus können Sie das Beispielprogramm ausführen oder mit der Entwicklung beginnen.

Um ein Programm zu kompilieren, öffnen Sie das Terminal in VS Code und führen Sie den Befehl `mvn install` aus.

![Maven Install](/img/bbj-installation/github/2.png#rounded-border)

Wenn alles erfolgreich abgeschlossen wurde, sollten Sie die Nachricht `BUILD SUCCESS` sehen.

:::warning WARNUNG
Stellen Sie sicher, dass Sie den Befehl `mvn install` anstelle der integrierten Maven-Oberfläche von VS Code für die Installation Ihres Programms verwenden.
:::

Nachdem dies erledigt ist, müssen Sie eine bestimmte Webadresse aufrufen, um Ihr Programm zu sehen. Klicken Sie dazu zuerst auf die **"Ports"**-Registerkarte am unteren Rand von VS Code. Hier sehen Sie zwei Ports, 8888 und einen weiteren, aufgeführt.

![Forwarded Ports](/img/bbj-installation/github/3.png#rounded-border)

Klicken Sie auf die kleine **"Open in Browser"**-Schaltfläche, die einem Globus ähnelt, im Abschnitt **"Local Address"** der **Ports**-Registerkarte, um eine neue Registerkarte in Ihrem Browser zu öffnen.

![Browser Button](/img/bbj-installation/github/4.png#rounded-border)

Wenn die Browsertab geöffnet ist, möchten Sie am Ende der URL hinzufügen, um sicherzustellen, dass Ihre App ausgeführt wird. Fügen Sie zuerst ein `/webapp` am Ende der Webadresse hinzu, die mit `github.dev` endet. Danach fügen Sie den richtigen App- und Klassennamen (falls zutreffend) hinzu, um die gewünschte App anzuzeigen. Um zu sehen, wie die URL richtig konfiguriert wird, [befolgen Sie diesen Leitfaden](./configuration).

:::success Tipp
Wenn Sie das Standardprogramm "Hello World" ausführen möchten, fügen Sie einfach `/hworld` nach dem `/webapp` in der URL hinzu:
<br />

![Modified URL](/img/bbj-installation/github/5.png#rounded-border)
:::

Nachdem dies erledigt ist, sollten Sie Ihre App im Browser sehen und können sie in der VS Code-Instanz, die innerhalb von Codespaces ausgeführt wird, modifizieren.
