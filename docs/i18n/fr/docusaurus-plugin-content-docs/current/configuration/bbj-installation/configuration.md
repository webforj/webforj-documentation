---
title: Install Plugin
sidebar_position: 3
displayed_sidebar: documentationSidebar
_i18n_hash: 1a3e48999554631e4f15a67c80385111
---
Vous pouvez configurer webforJ à l'aide du fichier POM d'un projet, qui est conçu pour faciliter le déploiement d'une application. Les sections suivantes décrivent les différentes options que vous pouvez modifier pour atteindre le résultat souhaité.

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

## Tags du fichier POM {#pom-file-tags}

Les tags dans le tag `<configuration>` peuvent être modifiés pour configurer votre application. Modifier les lignes suivantes dans le fichier POM par défaut qui vient avec le dépôt de démarrage [`HelloWorldJava`](https://github.com/webforj/HelloWorldJava) se traduira par ces changements :

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

- **`<deployurl>`** Ce tag est l'URL où le point de terminaison webforJ pour l'installation du projet peut être atteint. Pour les utilisateurs exécutant leur application localement, un port par défaut de 8888 est utilisé. Pour les utilisateurs exécutant Docker, le port doit être modifié pour le port qui a été saisi lors de [la configuration du conteneur Docker](./docker#2-configuration).

- **`<classname>`** Ce tag doit contenir le package et le nom de la classe de l'application que vous souhaitez exécuter. Ce sera la seule classe de votre projet qui étend la classe `App` et s'exécute à partir de l'URL de base.

- **`<publishname>`** Ce tag spécifie le nom de l'application dans l'URL publiée. En général, pour exécuter votre programme, vous naviguerez vers une URL similaire à `http://localhost:8888/webapp/<publishname>`, remplaçant `<publishname>` par la valeur dans le tag `<publishname>`. Ensuite, le programme spécifié par le tag `<classname>` est exécuté.

- **`<debug>`** Le tag de débogage peut être défini sur true ou false, et déterminera si la console du navigateur affiche ou non les messages d'erreur générés par votre programme. 

## Exécution d'un programme spécifique {#running-a-specific-program}

Il existe deux manières d'exécuter un programme spécifique dans votre application :

1. Placez le programme dans la méthode `run()` de la classe qui étend `App`.
2. Utilisez [le routage](../../routing/overview) dans votre application webforJ pour donner au programme une URL dédiée.

## Comment webforJ sélectionne un point d'entrée {#how-webforj-selects-an-entry-point}

Le point d'entrée d'une application est déterminé par le `<classname>` spécifié dans le fichier POM. Si aucun point d'entrée n'est spécifié dans le fichier POM, le système commencera une recherche de point d'entrée.

### Recherche de point d'entrée {#entry-point-search}

1. S'il existe une seule classe qui étend la classe `App`, elle deviendra le point d'entrée.
2. Si plusieurs classes étendent `App`, le système vérifie si l'une d'elles a l'annotation `com.webforj.annotation.AppEntry`. La seule classe annotée avec `@AppEntry` deviendra le point d'entrée.
    :::warning
    Si plusieurs classes sont annotées avec `@AppEntry`, une exception est levée, énumérant toutes les classes découvertes.
    :::

S'il existe plusieurs classes qui étendent `App` et aucune d'entre elles n'est annotée avec `@AppEntry`, une exception est levée, détaillant chaque sous-classe.

## Mode débogage {#debug-mode}

Il est également possible d'exécuter votre application en mode débogage, ce qui permet à la console d'imprimer des messages d'erreur complets. 

La première option consiste à modifier le fichier `config.bbx`, que vous trouverez dans le répertoire `cfg/` de votre installation BBj. Ajoutez la ligne `SET DEBUG=1` au fichier et enregistrez vos modifications.

De plus, dans l'Enterprise Manager, vous pouvez ajouter ce qui suit en tant qu'argument de programme : `DEBUG`

Compléter l'une de ces options permet à la console du navigateur d'imprimer les messages d'erreur.
