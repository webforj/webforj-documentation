---
sidebar_position: 2
title: Github Codespaces
_i18n_hash: e9d0c9402dcba748eea3671a39562b83
---
[`webforj-hello-world`](https://github.com/webforj/webforj-hello-world) wurde konfiguriert, um in Github Codespaces ausgeführt zu werden. Codespaces ist eine cloudbasierte Entwicklungsumgebung und ermöglicht es Ihnen, webforJ-Anwendungen direkt in Ihrem Browser zu entwickeln und auszuführen. Um mit diesem Tool zu beginnen, befolgen Sie die folgenden Schritte:

## 1. Navigieren Sie zum HelloWorldJava-Repository {#1-navigate-to-the-helloworldjava-repository}

Um zu beginnen, müssen Sie zum HelloWorldJava-Repository gehen, das [unter diesem Link](https://github.com/webforj/webforj-hello-world) zu finden ist. Klicken Sie dort auf die grüne Schaltfläche **"Use this template"** und dann auf die Option **"Open in a codespace"**.

![Codespace buttons](/img/bbj-installation/github/1.png#rounded-border)

## 2. Führen Sie Ihr Programm aus {#2-running-your-program}

Nachdem Sie gewartet haben, bis der Codespace geladen ist, sollten Sie eine Browser-Version von VS Studio Code sehen, die mit dem "HelloWorldJava"-Beispielprogramm geladen ist. Von hier aus können Sie das Beispielprogramm ausführen oder mit der Entwicklung beginnen.

Um ein Programm zu kompilieren, öffnen Sie das Terminal in VS Code und führen Sie den Befehl `mvn install` aus.

![Maven Install](/img/bbj-installation/github/2.png#rounded-border)

Wenn alles erfolgreich abgeschlossen ist, sollten Sie die Nachricht `BUILD SUCCESS` sehen.

:::warning WARNUNG 
Stellen Sie sicher, dass Sie den Befehl `mvn install` anstelle der integrierten Maven-Oberfläche von VS Code zum Installieren Ihres Programms verwenden.
:::

Sobald dies erledigt ist, müssen Sie zu einer bestimmten Webadresse navigieren, um Ihr Programm zu sehen. Klicken Sie dazu zunächst auf die Registerkarte **"Ports"** unten in VS Code. Hier sehen Sie zwei Ports, 8888 und einen weiteren, aufgelistet.

![Forwarded Ports](/img/bbj-installation/github/3.png#rounded-border)

Klicken Sie auf die kleine Schaltfläche **"Open in Browser"**, die einem Globus ähnelt, im Abschnitt **"Local Address"** der Registerkarte **Ports**, die einen neuen Tab in Ihrem Browser öffnet.

![Browser Button](/img/bbj-installation/github/4.png#rounded-border)

Wenn der Browser-Tab geöffnet ist, möchten Sie am Ende der URL etwas hinzufügen, um sicherzustellen, dass Ihre App ausgeführt wird. Fügen Sie zunächst ein `/webapp` am Ende der Webadresse hinzu, die mit `github.dev` endet. Fügen Sie danach den korrekten App- und Klassennamen (falls zutreffend) hinzu, um die gewünschte App anzuzeigen. Um zu sehen, wie Sie die URL richtig konfigurieren, [folgen Sie diesem Leitfaden](./configuration).

:::success Tipp
Wenn Sie das Standardprogramm "Hello World" ausführen möchten, fügen Sie einfach `/hworld` nach dem `/webapp` in der URL hinzu:
<br />

![Modified URL](/img/bbj-installation/github/5.png#rounded-border)
:::

Sobald dies erledigt ist, sollten Sie Ihre App im Browser sehen und können sie in der VS Code-Instanz, die innerhalb von Codespaces läuft, bearbeiten.
