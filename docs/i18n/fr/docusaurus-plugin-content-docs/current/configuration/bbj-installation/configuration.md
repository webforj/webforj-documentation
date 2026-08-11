---
title: Install Plugin
sidebar_position: 3
displayed_sidebar: documentationSidebar
description: >-
  Configure the webforJ install Maven plugin with deploy URL, class name,
  publish name, and debug flags for BBjServices deployments.
_i18n_hash: b01357f571ce256abb8b390cebdbf5cc
---
Vous pouvez configurer webforJ à l'aide du fichier POM d'un projet, qui est conçu pour faciliter le déploiement d'une application. Les sections suivantes décrivent les différentes options que vous pouvez modifier pour obtenir le résultat désiré.

## Exclusion de l'engin {#engine-exclusion}

Lors de l'exécution avec `BBjServices`, la dépendance `webforj-engine` doit être exclue, car les fonctionnalités fournies par l'engin sont déjà disponibles.

```xml
<dependencies>
  <dependency>
    <groupId>com.webforj</groupId>
    <artifactId>webforj</artifactId>
    <version>${webforj.version}</version>
    <exclusions>
      <exclusion>
        <groupId>com.webforj</groupId>
        <artifactId>webforj-engine</artifactId>
      </exclusion>
    </exclusions>
  </dependency>
</dependencies>
```

## Éléments du fichier POM {#pom-file-tags}

Les éléments dans la balise `<configuration>` peuvent être modifiés pour configurer votre application. La modification des lignes suivantes dans le fichier POM par défaut qui accompagne le [`HelloWorldJava`](https://github.com/webforj/HelloWorldJava) dépôt de démarrage entraînera ces changements :

```xml {13-16} showLineNumbers
<plugin>
  <groupId>com.webforj</groupId>
  <artifactId>webforj-install-maven-plugin</artifactId>
  <version>${webforj.version}</version>
  <executions>
    <execution>
      <goals>
        <goal>install</goal>
      </goals>
  </execution>
  </executions>
  <configuration>
    <deployurl>http://localhost:8888/webforj-install</deployurl>
    <classname>samples.HelloWorldApp</classname>
    <publishname>hello-world</publishname>
    <debug>true</debug>
  </configuration>
</plugin>
```

- **`<deployurl>`** Cette balise est l'URL à laquelle le point de terminaison webforJ pour l'installation du projet peut être atteint. Pour les utilisateurs exécutant leur application localement, un port par défaut de 8888 est utilisé. Pour les utilisateurs exécutant Docker, le port doit être changé pour le port qui a été saisi lors de [la configuration du conteneur Docker](./docker#2-configuration).

- **`<classname>`** Cette balise doit contenir le package et le nom de la classe de l'application que vous souhaitez exécuter. Ce sera la seule classe de votre projet qui étend la classe `App` et s'exécute à partir de l'URL de base.

- **`<publishname>`** Cette balise spécifie le nom de l'application dans l'URL publiée. En général, pour exécuter votre programme, vous naviguerez vers une URL similaire à `http://localhost:8888/webapp/<publishname>`, remplaçant `<publishname>` par la valeur dans la balise `<publishname>`. Ensuite, le programme spécifié par la balise `<classname>` est exécuté.

- **`<debug>`** La balise de débogage peut être définie sur true ou false, et déterminera si oui ou non la console du navigateur affiche les messages d'erreur lancés par votre programme.

## Exécution d'un programme spécifique {#running-a-specific-program}

Il existe deux façons d'exécuter un programme spécifique dans votre application :

1. Placez le programme dans la méthode `run()` de la classe qui étend `App`.
2. Utilisez [le routage](../../routing/overview) dans votre application webforJ pour donner au programme une URL dédiée.

## Comment webforJ sélectionne un point d'entrée {#how-webforj-selects-an-entry-point}

Le point d'entrée d'une application est déterminé par la balise `<classname>` spécifiée dans le fichier POM. Si aucun point d'entrée n'est spécifié dans le fichier POM, le système commencera une recherche de point d'entrée.

### Recherche de point d'entrée {#entry-point-search}

1. S'il existe une seule classe qui étend la classe `App`, cela deviendra le point d'entrée.
2. Si plusieurs classes étendent `App`, le système vérifie si l'une d'elles a l'annotation `com.webforj.annotation.AppEntry`. La seule classe annotée avec `@AppEntry` deviendra le point d'entrée.
    :::warning
    Si plusieurs classes sont annotées avec `@AppEntry`, une exception est lancée, listant toutes les classes découvertes.
    :::

S'il y a plusieurs classes qui étendent `App` et aucune d'entre elles n'est annotée avec `@AppEntry`, une exception est lancée, détaillant chaque sous-classe.

## Mode débogage {#debug-mode}

Il est également possible d'exécuter votre application en mode débogage, ce qui permet à la console d'imprimer des messages d'erreur complets.

La première option consiste à modifier le fichier `config.bbx`, trouvé dans le répertoire `cfg/` de votre installation BBj. Ajoutez la ligne `SET DEBUG=1` au fichier et enregistrez vos modifications.

De plus, dans l'Enterprise Manager, vous pouvez ajouter ce qui suit comme argument de programme : `DEBUG`

Compléter l'une ou l'autre de ces options permet à la console du navigateur d'imprimer des messages d'erreur.
