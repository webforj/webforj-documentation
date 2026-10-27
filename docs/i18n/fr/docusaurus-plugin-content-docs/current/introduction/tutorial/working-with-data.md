---
title: Travailler avec les données
sidebar_position: 3
description: Step 2 - Use Spring to work with data.
_i18n_hash: d2e2ee90a1ad908c6884df92fb575c5b
---
Dans cette étape, vous apprendrez comment créer un modèle de données en utilisant Spring et afficher ces données visuellement. À la fin de cette étape, l'application créée à l'étape précédente, [Créer une application de base](/docs/introduction/tutorial/creating-a-basic-app), aura un tableau qui affiche des données sur les clients. Suivre ce tutoriel vous apprendra :

- Les annotations Spring
- La gestion des données
- Le composant `Table` webforJ

La réalisation de cette étape crée une version de [2-working-with-data](https://github.com/webforj/webforj-tutorial/tree/main/2-working-with-data).

## Exécution de l'application {#running-the-app}

Alors que vous développez votre application, vous pouvez utiliser [2-working-with-data](https://github.com/webforj/webforj-tutorial/tree/main/2-working-with-data) comme comparaison. Pour voir l'application en action :

1. Rendez-vous dans le répertoire de niveau supérieur contenant le fichier `pom.xml`, c'est `2-working-with-data` si vous suivez la version sur GitHub.

2. Utilisez la commande Maven suivante pour exécuter l'application Spring Boot localement :
    ```bash
    mvn
    ```

L'exécution de l'application ouvre automatiquement un nouveau navigateur à `http://localhost:8080`.

## Dépendances et configurations {#dependencies-and-configurations}

Ce tutoriel utilise [H2 database](https://www.h2database.com/html/main.html) et, dans une étape future, l'API Jakarta Persistence (JPA) via [Spring Data JPA](https://docs.spring.io/spring-data/jpa/reference/index.html). Cela vous oblige à ajouter des dépendances au fichier `pom.xml` et à mettre à jour `application.properties`. Ce sera la dernière fois que vous devrez modifier ces deux fichiers pour le reste du tutoriel.

Dans votre POM, ajoutez les dépendances suivantes :

```xml
<dependency>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-data-jpa</artifactId>
</dependency>
<dependency>
  <groupId>com.h2database</groupId>
  <artifactId>h2</artifactId>
</dependency>
```

Dans `application.properties`, situé dans `src/main/resources`, ajoutez ce qui suit :

```
# Configuration de la base de données H2
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=

# Configuration JPA
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
spring.jpa.hibernate.ddl-auto=update
```

:::info Accéder aux données
Ce tutoriel utilise une base de données en mémoire et les identifiants par défaut pour accéder aux données. Consultez la documentation de Spring sur [Data Access](https://docs.spring.io/spring-boot/how-to/data-access.html) pour en savoir plus sur les options de configuration spécifiques à Spring Boot.
:::

## Beans Spring {#spring-beans}

Une partie clé de l'utilisation du framework Spring est de comprendre ce que sont les beans. Les beans sont des objets avec des annotations Spring définies qui facilitent la configuration par Spring en connaissant l'objet prévu de la classe. Consultez la documentation de Spring sur [Aperçu des beans](https://docs.spring.io/spring-framework/reference/core/beans/definition.html) pour en savoir plus.

## Création d'un modèle de données {#creating-a-data-model}

Avant d'afficher visuellement ou de créer les données, ce tutoriel a besoin d'un moyen de représenter les données de chaque client, y compris son nom, son pays et son entreprise. Avec Spring, cela se fait à l'aide d'une classe qui possède une annotation `@Entity`.

Créez une classe dans `src/main/java/com/webforj/tutorial/entity` nommée `Customer.java`. Elle doit avoir l'annotation `@Entity` et inclure des méthodes getter et setter pour les valeurs du client, sauf pour l'`id`. Au lieu d'utiliser une méthode de création pour les valeurs d'`id`, utilisez les annotations `@Id` et `@GeneratedValue` pour garantir que chaque client obtienne un `id` unique.

Avec le modèle de données `Customer` en place, vous pouvez maintenant commencer à ajouter la logique métier à votre application.

## Gestion des données {#managing-data}

Après avoir créé un modèle de données, vous allez créer un référentiel et un service pour gérer les données des clients. Créer ces types de classes dans votre application vous permet d’inclure des opérations telles que l'ajout, la suppression et la mise à jour des enregistrements des clients.

### Création d'un référentiel {#creating-a-repository}

La création d'un référentiel rend les données des entités accessibles, afin que votre application puisse contenir plusieurs clients. L'objectif de ce tutoriel est de rendre les données editables, triables et valables. Vous déterminez les capacités d'un référentiel par le référentiel Spring Data que vous utilisez.

Dans une étape future, [Validation et liaison des données](/docs/introduction/tutorial/validating-and-binding-data), vous aurez besoin d'accéder à Spring Data JPA pour valider les propriétés des clients. Par conséquent, le référentiel approprié à utiliser est le `JpaRepository`.

Dans `src/main/java/com/webforj/tutorial/repository`, créez une interface de référentiel qui a l'annotation Spring `@Repository` et étend `JpaRepository`. Vous devrez spécifier quel type d'entités se trouve dans ce référentiel et quel type d'objet est l'`id`. Pour être sûr, étendez également `JpaSpecificationExecutor`. Cette addition vous permettra de mettre en œuvre des options de filtrage avancées, si nécessaire.

```java title="CustomerRepository.java"
@Repository
public interface CustomerRepository
        extends JpaRepository<Customer, Long>,
        JpaSpecificationExecutor<Customer> {
}
```

Le `CustomerRepository` que vous venez de créer n'aura pas de méthodes déclarées. Les méthodes pour gérer les données (la logique métier de l'application) résideront dans une classe de service.

### Création d'un service {#creating-a-service}

Dans `src/main/java/com/webforj/tutorial/service`, créez une classe `CustomerService`. Ce service contiendra des méthodes pour créer, mettre à jour, supprimer et interroger les clients en utilisant `CustomerRepository`.

De plus, ce service a besoin d'un mécanisme pour connecter les référentiels Spring Data aux composants UI de webforJ. Utiliser la classe `SpringDataRepository` de webforJ vous permet de créer ce pont. Cela simplifie la liaison des données et les opérations CRUD en permettant à vos tableaux et formulaires webforJ de fonctionner librement avec votre couche de données gérée par Spring. Pour plus d'informations sur l'intégration Spring de webforJ, consultez l'article [Spring Data JPA](/docs/integrations/spring/spring-data-jpa).

Pour cette classe de service, vous utiliserez deux annotations Spring :

- **`@Service`** - Cela marque une classe comme étant un composant de service dans Spring, la rendant automatiquement détectée et gérée comme un bean pour la logique métier ou les opérations réutilisables.

- **`@Transactional`** - Cette annotation indique à Spring d'exécuter la méthode ou la classe dans le cadre d'une transaction de base de données, de sorte que toutes les opérations à l'intérieur soient validées ou annulées ensemble. Plus de détails sont disponibles dans la documentation de Spring, [Utilisation de @Transactional](https://docs.spring.io/spring-framework/reference/data-access/transaction/declarative/annotations.html#page-title).

## Chargement des données initiales {#loading-initial-data}

Pour ce tutoriel, l'ensemble de données clients initial provient d'un fichier JSON. L'application Java charge le fichier, et non le navigateur, alors créez-le à l'intérieur de `src/main/resources/data` en utilisant les données suivantes :

Ensuite, l'application a besoin d'un moyen pour récupérer ces données lors de son démarrage. Dans `src/main/java/com/webforj/tutorial/config`, créez une classe `DataInitializer`. Ainsi, lorsque l'application s'exécute, si aucun client n'est détecté, elle chargera les clients à partir du fichier JSON et les mettra dans la base de données H2 :

## Affichage des données visuellement {#displaying-data-visually}

La dernière partie de cette étape est d'utiliser le composant [`Table`](/docs/components/table/overview) et de le connecter aux données Spring.

Une instance d'un `Table` webforJ doit avoir un type de données pour fonctionner, c'est la classe d'entité créée plus tôt dans cette étape :

Une fois que vous avez un `Table`, chaque propriété du client obtient sa propre colonne. Pour chaque colonne que vous ajoutez, utilisez le nom de la propriété, sa méthode getter dans l'entité `Customer`, et la méthode `setLabel()` pour afficher les informations dans l'ordre que vous souhaitez :

Après avoir ajouté les colonnes, vous devez spécifier quel référentiel le `Table` doit utiliser pour peupler ses données. Cette application obtient le référentiel à partir de la méthode `getRepositoryAdapter()` dans le `CustomerService` créé :

### Taille du tableau {#table-sizing}

Pour le tableau, vous pouvez utiliser `setSize()` pour définir sa taille en pixels ou en autres [unités CSS](https://developer.mozilla.org/en-US/docs/Learn_web_development/Core/Styling_basics/Values_and_units). En définissant une largeur maximale par rapport à la largeur de l'écran, vous aidez votre application à être plus adaptable aux petits écrans.

Pour les colonnes, vous pouvez définir les largeurs individuellement ou utiliser l'une des méthodes `Table` comme `setColumnsToAutoFit()` pour laisser webforJ gérer les largeurs pour vous.

### Interactions utilisateur {#user-interactions}

Le composant `Table` a également des méthodes pour contrôler la façon dont les utilisateurs interagissent avec les colonnes :

Les parties en surbrillance de la classe `Application` ajoutent le composant `Table`, définissent ses colonnes et utilisent `CustomerService` pour récupérer le référentiel :

## Prochaine étape {#next-step}

Avec ces changements, l'application charge les données des clients dans la base de données, puis les affiche dans un composant `Table`. La prochaine étape, [Routage et composites](/docs/introduction/tutorial/routing-and-composites), introduit le routage et les vues multiples pour ajouter de nouveaux clients.
