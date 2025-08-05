---
title: Project Setup
sidebar_position: 1
_i18n_hash: b1ac0a58b11558f40824c8caedeb95b3
---
In deze tutorial zal de app worden gestructureerd in **vier stappen**, waarbij elke stap nieuwe functies introduceert naarmate het project voortschrijdt. Door mee te volgen, krijg je een duidelijk begrip van hoe de app evolueert en hoe elke functie wordt geïmplementeerd.

Om te beginnen, kun je het volledige project downloaden of het klonen van GitHub:
<!-- vale off -->
- Download ZIP: [webforj-demo-application.zip](https://github.com/webforj/webforj-demo-application/archive/refs/heads/main.zip)
- GitHub Repository: Clone het project [direct vanaf GitHub](https://github.com/webforj/webforj-demo-application)
<!-- vale on -->
```bash
git clone https://github.com/webforj/webforj-demo-application.git
```

Zowel het ZIP-bestand als de GitHub-repository bevatten de complete projectstructuur met alle vier stappen, zodat je op elk gewenst punt kunt beginnen of stap voor stap mee kunt volgen.

<div class="videos-container">
  <video controls>
    <source src="https://cdn.webforj.com/webforj-documentation/video/tutorials/project-setup.mp4" type="video/mp4"/>
  </video>
</div>

## Projectstructuur {#project-structure}

Het project is opgedeeld in vier afzonderlijke mappen, die elk een specifieke fase van de ontwikkeling van de app vertegenwoordigen. Deze stappen stellen je in staat om te zien hoe de app evolueert van een basisopzet naar een volledig functioneel klantbeheersysteem.

In de projectmap vind je vier submappen, die elk overeenkomen met een stap in de tutorial:

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

### De app draaien {#running-the-app}

Om de app in actie te zien in elke fase:

1) Navigeer naar de map voor de gewenste stap. Dit moet de bovenste niveaumap voor die stap zijn, met de `pom.xml`

2) Gebruik de Maven Jetty-plugin om de app lokaal te implementeren door het volgende uit te voeren:

```bash
mvn jetty:run
```

3) Open je browser en navigeer naar http://localhost:8080 om de app te bekijken.

Herhaal dit proces voor elke stap terwijl je de tutorial volgt, zodat je de functies van de app kunt verkennen naarmate ze worden toegevoegd.
