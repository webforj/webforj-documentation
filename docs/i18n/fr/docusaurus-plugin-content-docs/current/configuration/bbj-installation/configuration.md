---
title: Install Plugin
sidebar_position: 3
displayed_sidebar: documentationSidebar
_i18n_hash: 3f3e4285abb3b23f9427cdd7b9baa282
---
Vous pouvez configurer webforJ en utilisant le fichier POM d'un projet, qui est conçu pour faciliter le déploiement d'une application. Les sections suivantes décrivent les différentes options que vous pouvez modifier pour obtenir le résultat souhaité.

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

Les balises à l'intérieur de la balise `<configuration>` peuvent être modifiées pour configurer votre application. En modifiant les lignes suivantes dans le fichier POM par défaut qui accompagne le [`HelloWorldJava`](https://github.com/webforj/HelloWorldJava) dépôt de départ, vous obtiendrez ces changements :

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

- **`<deployurl>`** Cette balise est l'URL où le point de terminaison webforJ pour l'installation du projet peut être atteint. Pour les utilisateurs exécutant leur application localement, un port par défaut de 8888 est utilisé. Pour les utilisateurs exécutant Docker, le port doit être changé pour celui qui a été saisi lors de [la configuration du conteneur Docker](./docker#2-configuration).

- **`<classname>`** Cette balise doit contenir le nom du paquet et de la classe de l'application que vous souhaitez exécuter. Ce sera la seule classe de votre projet qui étend la classe `App` et s'exécute à partir de l'URL de base.

- **`<publishname>`** Cette balise spécifie le nom de l'application dans l'URL publiée. En général, pour exécuter votre programme, vous naviguerez vers une URL similaire à `http://localhost:8888/webapp/<publishname>`, en remplaçant `<publishname>` par la valeur de la balise `<publishname>`. Ensuite, le programme spécifié par la balise `<classname>` est exécuté.

- **`<debug>`** La balise de débogage peut être définie sur vrai ou faux, et déterminera si la console du navigateur affiche ou non les messages d'erreur générés par votre programme.

## Exécution d'un programme spécifique {#running-a-specific-program}

Il existe deux façons d'exécuter un programme spécifique dans votre application :

1. Placez le programme dans la méthode `run()` de la classe qui étend `App`.
2. Utilisez [le routage](../../routing/overview) dans votre application webforJ pour donner au programme une URL dédiée.

## Comment webforJ sélectionne un point d'entrée {#how-webforj-selects-an-entry-point}

Le point d'entrée d'une application est déterminé par la balise `<classname>` spécifiée dans le fichier POM. Si aucun point d'entrée n'est spécifié dans le fichier POM, le système commencera une recherche de point d'entrée.

### Recherche de point d'entrée {#entry-point-search}

1. S'il existe une seule classe qui étend la classe `App`, celle-ci deviendra le point d'entrée.
2. Si plusieurs classes étendent `App`, le système vérifie si l'une d'elles a l'annotation `com.webforj.annotation.AppEntry`. La classe unique annotée avec `@AppEntry` deviendra le point d'entrée.
    :::warning
    Si plusieurs classes sont annotées avec `@AppEntry`, une exception est levée, répertoriant toutes les classes découvertes.
    :::

S'il y a plusieurs classes qui étendent `App` et aucune d'elles n'est annotée avec `@AppEntry`, une exception est levée, détaillant chaque sous-classe.

## Mode débogage {#debug-mode}

Il est également possible d'exécuter votre application en mode débogage, ce qui permet à la console d'imprimer des messages d'erreur complets.

La première option est de modifier le fichier `config.bbx`, trouvé dans le répertoire `cfg/` de votre installation BBj. Ajoutez la ligne `SET DEBUG=1` au fichier et enregistrez vos modifications.

De plus, dans l'Enterprise Manager, vous pouvez ajouter ce qui suit comme argument de programme : `DEBUG`

Compléter l'une de ces options permet à la console du navigateur d'imprimer les messages d'erreur.
