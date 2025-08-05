---
title: Project Setup
sidebar_position: 1
_i18n_hash: b1ac0a58b11558f40824c8caedeb95b3
---
In diesem Tutorial wird die App in **vier Schritte** strukturiert, die jeweils neue Funktionen einführen, während das Projekt voranschreitet. Durch das Mitverfolgen erhalten Sie ein klares Verständnis dafür, wie sich die App entwickelt und wie jede Funktion implementiert wird.

Um zu beginnen, können Sie das gesamte Projekt herunterladen oder von GitHub klonen:
<!-- vale off -->
- ZIP herunterladen: [webforj-demo-application.zip](https://github.com/webforj/webforj-demo-application/archive/refs/heads/main.zip)
- GitHub-Repository: Klonen Sie das Projekt [direkt von GitHub](https://github.com/webforj/webforj-demo-application)
<!-- vale on -->
```bash
git clone https://github.com/webforj/webforj-demo-application.git
```

Sowohl die ZIP-Datei als auch das GitHub-Repository enthalten die vollständige Projektstruktur mit allen vier Schritten, sodass Sie an jedem Punkt starten oder Schritt für Schritt folgen können.

<div class="videos-container">
  <video controls>
    <source src="https://cdn.webforj.com/webforj-documentation/video/tutorials/project-setup.mp4" type="video/mp4"/>
  </video>
</div>

## Projektstruktur {#project-structure}

Das Projekt ist in vier separate Verzeichnisse unterteilt, die jeweils eine spezifische Phase der Entwicklung der App darstellen. Diese Schritte ermöglichen es Ihnen, zu sehen, wie sich die App von einer grundlegenden Einrichtung zu einem voll funktionsfähigen Kundenverwaltungssystem entwickelt.

Im Projektordner finden Sie vier Unterverzeichnisse, die jeweils einem Schritt im Tutorial entsprechen:

```
webforj-demo-application
│   .gitignore
│   LICENSE
│   README.md
│   tree.txt
│
├───1-creating-a-basic-app  
├───2-working-with-data
├───3-scaling-with-routing-and-composites
└───4-validating-and-binding-data
```

### Die App ausführen {#running-the-app}

Um die App zu jedem Zeitpunkt in Aktion zu sehen:

1) Navigieren Sie zum Verzeichnis des gewünschten Schrittes. Dies sollte das oberste Verzeichnis für diesen Schritt sein, das die `pom.xml` enthält.

2) Verwenden Sie das Maven Jetty-Plugin, um die App lokal bereitzustellen, indem Sie Folgendes ausführen:

```bash
mvn jetty:run
```

3) Öffnen Sie Ihren Browser und navigieren Sie zu http://localhost:8080, um die App anzuzeigen.

Wiederholen Sie diesen Prozess für jeden Schritt, während Sie dem Tutorial folgen, damit Sie die Funktionen der App erkunden können, während sie hinzugefügt werden.
