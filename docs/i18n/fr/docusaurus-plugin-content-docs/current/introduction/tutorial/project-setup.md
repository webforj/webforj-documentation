---
title: Project Setup
sidebar_position: 1
_i18n_hash: f8ad0e22acf56c824b05db580be2203b
---
Dans ce tutoriel, l'application sera structurée en **quatre étapes**, chacune introduisant de nouvelles fonctionnalités à mesure que le projet progresse. En suivant, vous obtiendrez une compréhension claire de la façon dont l'application évolue et de la manière dont chaque fonctionnalité est mise en œuvre.

Pour commencer, vous pouvez télécharger l'ensemble du projet ou le cloner depuis GitHub :
<!-- vale off -->
- Télécharger le ZIP : [webforj-demo-application.zip](https://github.com/webforj/webforj-demo-application/archive/refs/heads/main.zip)
- Dépôt GitHub : Clonez le projet [directement depuis GitHub](https://github.com/webforj/webforj-demo-application)
<!-- vale on -->
```bash
git clone https://github.com/webforj/webforj-demo-application.git
```

Le fichier ZIP et le dépôt GitHub contiennent la structure complète du projet avec les quatre étapes, vous pouvez donc commencer à n'importe quel moment ou suivre étape par étape.

<div class="videos-container">
  <video controls>
    <source src="https://cdn.webforj.com/webforj-documentation/video/tutorials/project-setup.mp4" type="video/mp4"/>
  </video>
</div>

## Structure du projet {#project-structure}

Le projet est divisé en quatre répertoires distincts, chacun représentant une étape spécifique du développement de l'application. Ces étapes vous permettent de voir comment l'application évolue d'une configuration de base à un système complet de gestion des clients.

Dans le dossier du projet, vous trouverez quatre sous-répertoires, chacun correspondant à une étape du tutoriel :

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

### Exécution de l'application {#running-the-app}

Pour voir l'application en action à n'importe quelle étape :

1) Accédez au répertoire de l'étape souhaitée. Cela devrait être le répertoire de niveau supérieur pour cette étape, contenant le `pom.xml`

2) Utilisez le plugin Maven Jetty pour déployer l'application localement en exécutant :

```bash
mvn jetty:run
```

3) Ouvrez votre navigateur et accédez à http://localhost:8080 pour voir l'application.

Répétez ce processus pour chaque étape en suivant le tutoriel, ce qui vous permettra d'explorer les fonctionnalités de l'application à mesure qu'elles sont ajoutées.
