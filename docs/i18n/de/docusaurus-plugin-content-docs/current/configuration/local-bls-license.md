---
title: Local BLS License
sidebar_class_name: new-content
sidebar_position: 25
description: >-
  Configure a webforJ project to use a locally installed BLS certificate and
  client configuration.
_i18n_hash: 981d7333984cb25b65a90d27775eab9f
---
Ein lokaler BASIS License Service (BLS) ermöglicht es einer webforJ-App, eine Lizenz von einem Dienst anzufordern, der auf Ihrem Entwicklungsrechner oder internen Netzwerk läuft. Dieses Setup ist nützlich, wenn Sie eine Seriennummer und eine Autorisierungsnummer haben und möchten, dass das Projekt die generierten lokalen Lizenzdateien anstelle der standardmäßigen Lizenzkonfiguration verwendet.

Ein webforJ-Projekt, das mit [startforJ](https://docs.webforj.com/startforj) erstellt wurde, enthält zwei Lizenzdateien im Verzeichnis `src/main/resources`:

```text
src/main/resources/blsclient.conf
src/main/resources/certificate.bls
```

Hier erfahren Sie, wie Sie die standardmäßigen Lizenzdateien durch die mit einer lokalen BLS-Installation generierten Dateien ersetzen:

## Voraussetzungen {#prerequisites}

Bevor Sie beginnen, stellen Sie sicher, dass Sie:

- Java 21 oder Java 25 zur Ausführung des BLS 26-Installers verfügbar haben.
- Eine Seriennummer und eine Autorisierungsnummer haben.
- Ein webforJ-Projekt mit einem Verzeichnis `src/main/resources` haben.
- Zugriff auf den Rechner haben, auf dem BLS ausgeführt wird.

## 1. Laden Sie den BLS-Installer herunter {#1-download-bls}

Um den BLS-Installer zu erhalten, gehen Sie zur Seite [BASIS Product Suite Downloads](https://basis.cloud/bbj-downloads/).
Nachdem Sie eine gewünschte Sprache für das Formular ausgewählt haben, gehen Sie zum Abschnitt **Produkt auswählen**. Wählen Sie im Dropdown-Menü **Produkt** `BLS` und im Dropdown-Menü **Revision** die neueste Version aus. Die erforderlichen Java-Versionen zum Ausführen des BLS finden Sie im Dropdown-Menü **Revision**.

Füllen Sie dann das Formular im Bereich **Kontaktinformationen** aus und aktivieren Sie die Kontrollkästchen im Bereich **Download-Bedingungen**.
Nachdem Sie das Formular ausgefüllt haben, klicken Sie auf die Schaltfläche `Download`, um die BLS-Installer-JAR herunterzuladen.

![Download-Formular mit ausgewähltem BLS als Produkt](/img/configuration/local-bls-license/download-bls.png#rounded-border)

*Download-Formular mit `BLS` ausgewählt als Produkt.*

## 2. Installieren und Konfigurieren des BLS {#2-install-andc-onfig-bls}

Die heruntergeladene ausführbare JAR hat folgendes Namensschema: `BLS<revision><date>_<time>.jar`. Suchen Sie die JAR und doppelklicken Sie darauf, um den Installer zu starten, oder führen Sie ihn von einer Befehlskonsole aus:

```bash
java -jar <downloaded-bls-installer>.jar
```

Befolgen Sie die Eingabeaufforderungen des Installers und füllen Sie die erforderlichen Informationen aus.

Standardmäßig wird der BLS in bestimmten Verzeichnissen, je nach Betriebssystem, installiert, kann jedoch im Fenster **Verzeichnis auswählen** geändert werden. In der Folge bezieht sich `<blshome>` auf den Installationsort des BLS.

- **Windows**: `C:\bls`
- **macOS**: `/Applications/bls`
- **Andere Betriebssysteme**: `/usr/local/bls`

Nachdem Sie den BLS installiert haben, öffnet sich der **Lizenzregistrierungs-Assistent**.

### Lizenzregistrierung {#license-registration}

1. Wählen Sie im Lizenzregistrierungs-Assistenten die Option `Lizenz abrufen`:

![Lizenzregistrierungs-Assistent mit ausgewähltem Lizenz abrufen](/img/configuration/local-bls-license/retrieve-license.png#rounded-border)

*Lizenzregistrierungs-Assistent mit ausgewählter `Lizenz abrufen`.*

2. Geben Sie in den nächsten Fenstern Ihre Kontaktinformationen, Seriennummer und Autorisierungsnummer ein.

3. Wenn Sie zum Fenster **Lizenzregistrierungs- und Liefermethoden** gelangen, wählen Sie `Lizenz automatisch registrieren und installieren`.

Nachdem Sie Ihre Lizenz registriert haben, beenden Sie die Konfiguration des lokalen BLS nach Bedarf. Wenn Sie später Ihre BLS-Einstellungen ändern oder eine weitere Lizenz abrufen müssen, verwenden Sie die BLS-Admin unter `<blshome>/bin/BLSAdmin`.

## 3. Kopieren Sie die generierten Lizenzdateien {#3-copy-the-generated-license-files}

Gehen Sie nun zum `<blshome>/cfg`-Verzeichnis und suchen Sie die generierten Lizenzdateien `blsclient.conf` und `certificate.bls`:

![BLS cfg-Ordner mit der generierten Clientkonfiguration und dem Zertifikat](/img/configuration/local-bls-license/bls-cfg-folder.png#rounded-border)

*BLS-Installations-`cfg`-Ordner mit der generierten Clientkonfiguration und dem Zertifikat.*

Kopieren Sie `blsclient.conf` und `certificate.bls` in Ihr webforJ-Projekt und ersetzen Sie vorhandene Dateien mit denselben Namen im Ressourcenverzeichnis. Jetzt, wenn Ihr lokaler BLS läuft, fordert Ihre webforJ-App die Lizenz von diesem Dienst an.

```
src
├───main
│   ├───java/
│   └───resources
│       ├───icons/
│       ├───static/
│       ├───application.properties
│       ├───banner.txt
// highlight-next-line
│       ├───blsclient.conf
// highlight-next-line
│       └───certificate.bls
```

:::tip
Wenn Ihre Lizenzdateien außerhalb des standardmäßigen webforJ-Konfigurationsverzeichnisses liegen, können Sie das Lizenzverzeichnis mit [`webforj.license.cfg`](./properties#configuration-options) konfigurieren.
:::
