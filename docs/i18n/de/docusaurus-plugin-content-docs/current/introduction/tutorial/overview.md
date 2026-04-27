---
title: Overview
hide_giscus_comments: true
sidebar_class_name: has-new-content
_i18n_hash: 781bf0258ed2366e2125e99587cda439
---
Dieser schrittweise Leitfaden führt Sie durch den Prozess des Aufbaus einer Kundenverwaltungs-App mit webforJ und Spring Boot. Er zeigt Ihnen, wie Sie eine moderne, benutzerfreundliche Oberfläche zum Anzeigen, Hinzufügen und Bearbeiten von Kundendaten erstellen.

Jeder Schritt führt neue Konzepte ein und ergibt eine ausführbare Spring Boot-App (JAR). Sie können Ihre App lokal mit Maven starten und über einen Webbrowser darauf zugreifen. Mit diesem Setup erhalten Sie einen schnellen Entwicklungszyklus und ein produktionsbereites Bereitstellungsmodell, das den eingebetteten Server von Spring Boot nutzt.

Es sind keine vorherigen Erfahrungen mit Spring Boot oder webforJ erforderlich, aber Sie sollten ein grundlegendes Verständnis von Java und Maven haben, um das Beste aus diesem Tutorial herauszuholen. Dieses Tutorial behandelt Spring-Konzepte, wenn sie auftreten, aber diejenigen, die ein tiefgehendes Verständnis von Spring wünschen, können die [Hauptdokumentation von Spring](https://spring.io/learn) und die Dokumentation von Spring zu [Spring Boot](https://docs.spring.io/spring-boot/index.html) besuchen.

## Tutorial-Konzepte {#tutorial-concepts}

Der erste Teil des Tutorials ist der [Projektsetup](/docs/introduction/tutorial/project-setup) gewidmet, um Ihre Spring Boot + webforJ-Umgebung vorzubereiten. Die folgenden Schritte führen neue Funktionen ein und entwickeln Ihr Projekt weiter. Indem Sie mitmachen, erhalten Sie ein klares Verständnis dafür, wie sich eine App entwickelt, während Sie Funktionen implementieren.

Jeder Schritt hat eine entsprechende ausführbare App, die auf GitHub verfügbar ist:

| Schritt | Dokumentation | GitHub |
| ----- | ----- | ----- |
| 1 | [Erstellen einer Basis-App](/docs/introduction/tutorial/creating-a-basic-app)                               | [Schritt 1 App](https://github.com/webforj/webforj-tutorial/tree/main/1-creating-a-basic-app)
| 2 | [Arbeiten mit Daten](/docs/introduction/tutorial/working-with-data)                                     | [Schritt 2 App](https://github.com/webforj/webforj-tutorial/tree/main/2-working-with-data)
| 3 | [Routing und Kompositionen](/docs/introduction/tutorial/routing-and-composites)                           | [Schritt 3 App](https://github.com/webforj/webforj-tutorial/tree/main/3-routing-and-composites)
| 4 | [Beobachter und Routenparameter](/docs/introduction/tutorial/observers-and-route-parameters)           | [Schritt 4 App](https://github.com/webforj/webforj-tutorial/tree/main/4-observers-and-route-parameters)
| 5 | [Validierung und Binding von Daten](/docs/introduction/tutorial/validating-and-binding-data)                 | [Schritt 5 App](https://github.com/webforj/webforj-tutorial/tree/main/5-validating-and-binding-data)
| 6 | [Integration eines App-Layouts](/docs/introduction/tutorial/integrating-an-app-layout)                     | [Schritt 6 App](https://github.com/webforj/webforj-tutorial/tree/main/6-integrating-an-app-layout)

## Voraussetzungen {#prerequisites}

Sie sollten die folgenden Werkzeuge/Ressourcen auf Ihrem Entwicklungsrechner haben:

- Java 21 oder 25
- Maven
- Eine Java-IDE
- Git (empfohlen, aber nicht erforderlich)

:::info webforJ-Voraussetzungen
Überprüfen Sie den [Artikel zu den Voraussetzungen](/docs/introduction/prerequisites) für einen detaillierteren Überblick über die benötigten Tools für Ihre Entwicklungsumgebung.
:::

<DocCardList className="topics-section" />
