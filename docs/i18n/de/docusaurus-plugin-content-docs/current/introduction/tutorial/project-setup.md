---
title: Projektsetup
sidebar_position: 1
_i18n_hash: f8ad0e22acf56c824b05db580be2203b
---
In diesem Tutorial wird die App in **vier Schritte** unterteilt, die jeweils neue Funktionen einführen, während das Projekt fortschreitet. Durch das Mitverfolgen erhalten Sie ein klares Verständnis dafür, wie sich die App entwickelt und wie jede Funktion implementiert wird.

Um zu beginnen, können Sie das gesamte Projekt herunterladen oder es von GitHub klonen:
<!-- vale off -->
- ZIP herunterladen: [webforj-demo-application.zip](https://github.com/webforj/webforj-demo-application/archive/refs/heads/main.zip)
- GitHub-Repository: Klonen Sie das Projekt [direkt von GitHub](https://github.com/webforj/webforj-demo-application)
<!-- vale on -->
```bash
git clone https://github.com/webforj/webforj-demo-application.git
```

Sowohl die ZIP-Datei als auch das GitHub-Repository enthalten die vollständige Projektstruktur mit allen vier Schritten, sodass Sie an jedem Punkt beginnen oder Schritt für Schritt mitverfolgen können.

<div class="videos-container">
  <video controls>
    <source src="https://cdn.webforj.com/webforj-documentation/video/tutorials/project-setup.mp4" type="video/mp4"/>
  </video>
</div>

## Projektstruktur {#project-structure}

Das Projekt ist in vier separate Verzeichnisse unterteilt, die jeweils eine spezifische Phase der Entwicklung der App darstellen. Diese Schritte ermöglichen es Ihnen zu sehen, wie sich die App von einem grundlegenden Setup zu einem voll funktionsfähigen Kundenverwaltungssystem entwickelt.

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

### App ausführen {#running-the-app}

Um die App in Aktion zu sehen, gehen Sie wie folgt vor:

1) Navigieren Sie zum Verzeichnis für den gewünschten Schritt. Dies sollte das Hauptverzeichnis für diesen Schritt sein, das die `pom.xml` enthält.

2) Verwenden Sie das Maven Jetty-Plugin, um die App lokal zu deployen, indem Sie Folgendes ausführen:

```bash
mvn jetty:run
```

3) Öffnen Sie Ihren Browser und navigieren Sie zu http://localhost:8080, um die App zu sehen.

Wiederholen Sie diesen Prozess für jeden Schritt, während Sie dem Tutorial folgen, sodass Sie die Funktionen der App erkunden können, während sie hinzugefügt werden.
