---
sidebar_position: 1
title: Table
hide_giscus_comments: true
description: >-
  Affichez des données tabulaires avec le composant Table, en liant des classes
  d’entité à un Repository pour remplir les colonnes et afficher les lignes.
_i18n_title_hash: 51c45b795d5d18a3e4e0c37e8b20a141
_i18n_description_hash: cad5d5953a2d2c3f85ad59b558632b0b
_i18n_hash: 680ee8ce00bf4efc4ae933a913fe1c1a
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-table" />
<DocChip chip='since' label='24.00' />
<JavadocLink type="table" location="com/webforj/component/table/Table" top='true'/>

La classe `Table` est un composant polyvalent conçu pour présenter des informations tabulaires de manière structurée et facilement compréhensible. Optimisé pour gérer de grands ensembles de données avec une performance élevée, ce composant offre une visualisation avancée et une suite complète d'événements pour un engagement dynamique des utilisateurs.

<!-- INTRO_END -->

## Création d'une `Table` {#creating-a-table}

<!-- vale off -->
<ComponentDemo
path='/webforj/datatable'
files={['src/main/java/com/webforj/samples/views/table/DataTableView.java']}
height='600px'
/>
<!-- vale on -->

Pour créer et peupler une `Table` dans une application, les étapes suivantes peuvent être suivies :

### 1. Créer une classe d'entité {#1-create-an-entity-class}

Définissez une classe pour représenter les entités (données) que vous souhaitez afficher dans la table. Dans l'exemple, cette classe est MusicRecord.

```java
public class MusicRecord {
  // Champs et méthodes pour représenter les attributs de chaque enregistrement
}
```

### 2. Créer un dépôt {#2-create-a-repository}

Une fois qu'une classe d'entité a été créée, utilisez-la pour remplir une collection de ces entités avec les données souhaitées.

À partir de ces données, un `Repository` doit être créé pour être utilisé au sein de la `Table`. La classe `CollectionRepository` est fournie pour transformer toute collection Java valide en un `Repository` utilisable, évitant ainsi de devoir implémenter votre propre classe `Repository`.

```java
List<MusicRecord> data = new Gson().fromJson(
  Assets.contentOf(
    Assets.resolveContextUrl("context://data/CDStore.json")
  ), new TypeToken<List<MusicRecord>>() {}
);

CollectionRepository<MusicRecord> dataRepository = new CollectionRepository<>(data);
```

:::tip Plus d'informations
Pour plus d'informations sur le modèle `Repository` dans webforJ, consultez les [articles sur les dépôts](/docs/advanced/repository/overview).
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
Alternativement, la méthode `setItems()` peut se voir passer toute collection Java valide, qui créera en arrière-plan un `CollectionRepository` pour vous.
:::

Voici un exemple des étapes ci-dessus mises en œuvre pour créer un composant `Table` basique :

<ComponentDemo
path='/webforj/tablebasic'
files={[
  'src/main/java/com/webforj/samples/views/table/TableBasicView.java',
  'src/main/java/com/webforj/samples/views/table/MusicRecord.java',
  'src/main/java/com/webforj/samples/views/table/Service.java',
]}
height='600px'
/>

## Style {#styling}

<TableBuilder name="Table" />
