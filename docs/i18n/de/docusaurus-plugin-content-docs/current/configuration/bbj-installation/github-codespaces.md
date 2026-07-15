---
sidebar_position: 2
title: Github Codespaces
description: >-
  Run the webforj-hello-world starter in a GitHub Codespace to develop and
  preview webforJ apps directly from the browser.
_i18n_hash: ffbe6dd8d2c6c81e95e7e97dbb8ff32e
---
[`webforj-hello-world`](https://github.com/webforj/webforj-hello-world) wurde konfiguriert, um in Github Codespaces zu laufen. Codespaces ist eine cloudbasierte Entwicklungsumgebung und ermöglicht es Ihnen, webforJ-Anwendungen direkt in Ihrem Browser zu entwickeln und auszuführen. Um mit diesem Tool zu beginnen, folgen Sie bitte den untenstehenden Schritten:

## 1. Navigieren Sie zum HelloWorldJava Repository {#1-navigate-to-the-helloworldjava-repository}

Zuerst müssen Sie zum HelloWorldJava Repository gehen, das [unter diesem Link](https://github.com/webforj/webforj-hello-world) zu finden ist. Klicken Sie dort auf die grüne Schaltfläche **"Use this template"** und wählen Sie dann die Option **"Open in a codespace"**.

![Codespace buttons](/img/bbj-installation/github/1.png#rounded-border)

## 2. Ausführen Ihres Programms {#2-running-your-program}

Nachdem Sie gewartet haben, bis der Codespace geladen ist, sollten Sie eine Browser-Version von VS Studio Code sehen, in der das "HelloWorldJava"-Beispielfrogramm geladen ist. Von hier aus können Sie das Beispielfrogramm ausführen oder mit der Entwicklung beginnen.

Um ein Programm zu kompilieren, öffnen Sie das Terminal in VS Code und führen Sie den Befehl `mvn install` aus.

![Maven Install](/img/bbj-installation/github/2.png#rounded-border)

Wenn alles erfolgreich abgeschlossen wurde, sollten Sie die Nachricht `BUILD SUCCESS` sehen.

:::warning WARNUNG
Stellen Sie sicher, dass Sie den Befehl `mvn install` anstelle der integrierten Maven-Schnittstelle von VS Code zum Installieren Ihres Programms verwenden.
:::

Sobald dies erledigt ist, müssen Sie eine bestimmte Webadresse aufrufen, um Ihr Programm zu sehen. Klicken Sie dazu zuerst auf die Registerkarte **"Ports"** am unteren Rand von VS Code. Hier sehen Sie zwei Ports, 8888 und einen weiteren, aufgelistet.

![Forwarded Ports](/img/bbj-installation/github/3.png#rounded-border)

Klicken Sie auf die kleine Schaltfläche **"Open in Browser"**, die einem Globus ähnelt, im Abschnitt **"Local Address"** der **Ports**-Registerkarte, um einen neuen Tab in Ihrem Browser zu öffnen.

![Browser Button](/img/bbj-installation/github/4.png#rounded-border)

Wenn der Browser-Tab geöffnet ist, möchten Sie am Ende der URL etwas hinzufügen, um sicherzustellen, dass Ihre App ausgeführt wird. Fügen Sie zuerst ein `/webapp` am Ende der Webadresse hinzu, die mit `github.dev` endet. Fügen Sie danach den korrekten App- und Klassennamen (sofern zutreffend) hinzu, um die gewünschte App anzuzeigen. Um zu sehen, wie Sie die URL korrekt konfigurieren, [folgen Sie diesem Leitfaden](./configuration).

:::success Tipp
Wenn Sie das Standardprogramm "Hello World" ausführen möchten, fügen Sie einfach `/hworld` nach dem `/webapp` in der URL hinzu:
<br />

![Modified URL](/img/bbj-installation/github/5.png#rounded-border)
:::


Sobald dies erledigt ist, sollten Sie Ihre App im Browser sehen können und können sie in der VS Code-Instanz, die innerhalb von Codespaces ausgeführt wird, ändern.
