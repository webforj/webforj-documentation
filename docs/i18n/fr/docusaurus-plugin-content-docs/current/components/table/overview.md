---
sidebar_position: 1
title: Table
hide_giscus_comments: true
sidebar_class_name: has-new-content
_i18n_hash: 53496a465aa0a0971cb4fdc55afa55de
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-table" />
<DocChip chip='since' label='24.00' />
<JavadocLink type="table" location="com/webforj/component/table/Table" top='true'/>

La classe `Table` est un composant polyvalent conçu pour présenter des informations tabulaires de manière structurée et facilement compréhensible. Optimisée pour le traitement de grands ensembles de données avec une haute performance, ce composant offre une visualisation avancée et une suite complète d'événements pour un engagement dynamique des utilisateurs.

<!-- INTRO_END -->

## Création d'une `Table` {#creating-a-table}

<!-- vale off -->
<ComponentDemo 
path='/webforj/datatable?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/DataTableView.java'
height='600px'
/>
<!-- vale on -->

Afin de créer et de remplir une `Table` dans une application, les étapes suivantes peuvent être suivies :

### 1. Créer une classe d'entité {#1-create-an-entity-class}

Définissez une classe pour représenter les entités (données) que vous souhaitez afficher dans la table. Dans cet exemple, cette classe est MusicRecord.

```java
public class MusicRecord {
  // Champs et méthodes pour représenter les attributs de chaque enregistrement
}
```

### 2. Créer un référentiel {#2-create-a-repository}

Une fois qu'une classe d'entité a été créée, utilisez-la pour remplir une collection de ces entités avec les données souhaitées.

À partir de ces données, un `Repository` doit être créé pour être utilisé dans la `Table`. La classe `CollectionRepository` est fournie pour convertir n'importe quelle collection Java valide en un `Repository` utilisable, évitant ainsi la nécessité d'implémenter votre propre classe `Repository`.

```java
List<MusicRecord> data = new Gson().fromJson(
  Assets.contentOf(
    Assets.resolveContextUrl("context://data/CDStore.json")
  ), new TypeToken<List<MusicRecord>>() {}
);

CollectionRepository<MusicRecord> dataRepository = new CollectionRepository<>(data);
```

:::tip Plus d'informations
Pour plus d'informations sur le modèle `Repository` dans webforJ, consultez les [articles sur les Repository](/docs/advanced/repository/overview).
:::

### 3. Instancier la `Table` et ajouter des colonnes {#3-instantiate-table-and-add-columns}

Instanciez un nouvel objet `Table`, et utilisez l'une des méthodes de fabrique fournies pour ajouter les colonnes souhaitées à la `Table` nouvellement créée :

```java
Table<MusicRecord> table = new Table<>();
table.addColumn("Numéro", MusicRecord::getNumber);
table.addColumn("Titre", MusicRecord::getTitle);
table.addColumn("Artiste", MusicRecord::getArtist);
table.addColumn("Genre", MusicRecord::getMusicType);
table.addColumn("Coût", MusicRecord::getCost);
```

### 4. Définir les données de la `Table` {#4-set-the-table-data}

Enfin, définissez le `Repository` pour la `Table` créée à l'étape précédente :

```java
table.setRepository(Service.getMusicRecords());
```

:::info
Alternativement, la méthode `setItems()` peut recevoir n'importe quelle collection Java valide, ce qui créera en arrière-plan un `CollectionRepository` pour vous. 
:::

Voici un exemple des étapes ci-dessus mises en œuvre pour créer un composant `Table` basique :

<ComponentDemo 
path='/webforj/tablebasic?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableBasicView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/Service.java']}
height='600px'
/>

## Styliser

<TableBuilder name="Table" />
