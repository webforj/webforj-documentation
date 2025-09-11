---
sidebar_position: 1
title: Table
hide_giscus_comments: true
sidebar_class_name: has-new-content
_i18n_hash: 59525ff188c1c03526b8c601a82c7a76
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-table" />
<DocChip chip='since' label='24.00' />
<JavadocLink type="table" location="com/webforj/component/table/Table" top='true'/>

La classe `Table` est un composant polyvalent conçu pour présenter des informations tabulaires de manière structurée et facilement compréhensible. Optimisé pour gérer de grands ensembles de données avec une haute performance, ce composant offre une visualisation avancée et un ensemble complet d'événements pour une interaction dynamique avec l'utilisateur.

<!-- vale off -->
<ComponentDemo 
path='/webforj/datatable?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/DataTableView.java'
height='600px'
/>
<!-- vale on -->


## Création d'un `Table` {#creating-a-table}

Pour créer et peupler un `Table` dans une application, les étapes suivantes peuvent être suivies :

### 1. Créer une classe d'entité {#1-create-an-entity-class}

Définissez une classe pour représenter les entités (données) que vous souhaitez afficher dans le tableau. Dans cet exemple, cette classe est MusicRecord.

```java
public class MusicRecord {
    // Champs et méthodes pour représenter les attributs de chaque enregistrement
}
```

### 2. Créer un référentiel {#2-create-a-repository}

Une fois qu'une classe d'entité a été créée, utilisez-la pour remplir une collection de ces entités avec les données souhaitées.

À partir de ces données, un `Repository` doit être créé pour être utilisé dans le `Table`. La classe `CollectionRepository` est fournie pour transformer toute collection Java valide en un `Repository` utilisable, évitant ainsi la nécessité d'implémenter votre propre classe `Repository`.

```java
List<MusicRecord> data = new Gson().fromJson(
    Assets.contentOf(
        Assets.resolveContextUrl("context://data/CDStore.json")
    ), new TypeToken<List<MusicRecord>>() {}
);

CollectionRepository<MusicRecord> dataRepository = new CollectionRepository<>(data);
```

:::tip Plus d'informations
Pour plus d'informations sur le modèle `Repository` dans webforJ, voir les [articles sur les dépôts](/docs/advanced/repository/overview).
:::

### 3. Instancier `Table` et ajouter des colonnes {#3-instantiate-table-and-add-columns}

Instanciez un nouvel objet `Table`, et utilisez l'une des méthodes de fabrique fournies pour ajouter les colonnes souhaitées à un nouveau `Table` créé :

```java
Table<MusicRecord> table = new Table<>();
table.addColumn("Numéro", MusicRecord::getNumber);
table.addColumn("Titre", MusicRecord::getTitle);
table.addColumn("Artiste", MusicRecord::getArtist);
table.addColumn("Genre", MusicRecord::getMusicType);
table.addColumn("Coût", MusicRecord::getCost);
```

### 4. Définir les données du `Table` {#4-set-the-table-data}

Enfin, définissez le `Repository` pour le `Table` créé à l'étape précédente :

```java
table.setRepository(Service.getMusicRecords());
```

:::info
Alternativement, la méthode `setItems()` peut recevoir toute collection Java valide, ce qui créera un `CollectionRepository` en arrière-plan pour vous. 
:::

Voici un exemple des étapes ci-dessus mises en œuvre pour créer un composant `Table` de base :

<ComponentDemo 
path='/webforj/tablebasic?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableBasicView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/Service.java']}
height='600px'
/>

## Stylisation

<TableBuilder name="Table" />
