---
title: Installer le Plugin
sidebar_position: 3
displayed_sidebar: documentationSidebar
description: >-
  Configure the webforJ install Maven plugin with deploy URL, class name,
  publish name, and debug flags for BBjServices deployments.
_i18n_hash: 1019285125db3439621951fe22047837
---
:::danger Obsolète et sera supprimé
Le plugin d'installation webforJ est obsolète et sera supprimé dans une future version. Ne commencez pas de nouveaux projets avec. Déplacez les projets existants vers [BBjShip](/docs/configuration/bbjship/overview), qui installe, déploie et exécute des applications webforJ dans BBjServices. [Projets existants](/docs/configuration/bbjship/existing-projects) répertorie les modifications nécessaires à un projet.
:::

Vous pouvez configurer webforJ à l'aide du fichier POM d'un projet, conçu pour faciliter le déploiement d'une application. Les sections suivantes décrivent les différentes options que vous pouvez modifier pour obtenir le résultat souhaité.

## Exclusion du moteur {#engine-exclusion}

Lors de l'exécution avec `BBjServices`, la dépendance `webforj-engine` doit être exclue, car les fonctionnalités fournies par le moteur sont déjà disponibles.

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

## Balises du fichier POM {#pom-file-tags}

Les balises à l'intérieur de la balise `<configuration>` peuvent être modifiées pour configurer votre application. Modifier les lignes suivantes dans le fichier POM par défaut qui accompagne le dépôt de démarrage [`HelloWorldJava`](https://github.com/webforj/HelloWorldJava) entraînera ces modifications :

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

- **`<deployurl>`** Cette balise est l'URL où le point de terminaison webforJ pour l'installation du projet peut être atteint. Pour les utilisateurs exécutant leur application localement, un port par défaut de 8888 est utilisé. Pour les utilisateurs exécutant Docker, le port doit être modifié en celui saisi lors de [la configuration du conteneur Docker](./docker#2-configuration).

- **`<classname>`** Cette balise doit contenir le nom du paquet et le nom de la classe de l'application que vous souhaitez exécuter. Cela sera la seule classe de votre projet qui étend la classe `App` et s'exécute à partir de l'URL de base.

- **`<publishname>`** Cette balise spécifie le nom de l'application dans l'URL publiée. En général, pour exécuter votre programme, vous vous rendrez à une URL similaire à `http://localhost:8888/webapp/<publishname>`, en remplaçant `<publishname>` par la valeur dans la balise `<publishname>`. Ensuite, le programme spécifié par la balise `<classname>` est exécuté.

- **`<debug>`** La balise debug peut être définie sur vrai ou faux, et déterminera si la console du navigateur affiche les messages d'erreur générés par votre programme.

## Exécution d'un programme spécifique {#running-a-specific-program}

Il y a deux façons d'exécuter un programme spécifique dans votre application :

1. Placez le programme dans la méthode `run()` de la classe qui étend `App`.
2. Utilisez [le routage](../../routing/overview) dans votre application webforJ pour donner au programme une URL dédiée.

## Comment webforJ sélectionne un point d'entrée {#how-webforj-selects-an-entry-point}

Le point d'entrée d'une application est déterminé par la `<classname>` spécifiée dans le fichier POM. Si aucun point d'entrée n'est spécifié dans le fichier POM, le système commencera une recherche de point d'entrée.

### Recherche de point d'entrée {#entry-point-search}

1. S'il y a une seule classe qui étend la classe `App`, cela deviendra le point d'entrée.
2. Si plusieurs classes étendent `App`, le système vérifie si l'une d'elles a l'annotation `com.webforj.annotation.AppEntry`. La seule classe annotée avec `@AppEntry` deviendra le point d'entrée.
    :::warning
    Si plusieurs classes sont annotées avec `@AppEntry`, une exception est lancée, énumérant toutes les classes découvertes.
    :::

S'il y a plusieurs classes qui étendent `App` et qu'aucune d'elles n'est annotée avec `@AppEntry`, une exception est lancée, détaillant chaque sous-classe.

## Mode débogage {#debug-mode}

Il est également possible d'exécuter votre application en mode débogage, ce qui permet à la console d'imprimer des messages d'erreur détaillés.

La première option consiste à modifier le fichier `config.bbx`, trouvé dans le répertoire `cfg/` de votre installation BBj. Ajoutez la ligne `SET DEBUG=1` au fichier et enregistrez vos modifications.

De plus, dans le Gestionnaire d'entreprise, vous pouvez ajouter ce qui suit en tant qu'argument de programme : `DEBUG`

Compléter l'un de ces deux processus permet à la console du navigateur d'imprimer des messages d'erreur.
