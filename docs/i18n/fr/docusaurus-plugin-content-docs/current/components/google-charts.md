---
title: Google Charts
sidebar_position: 50
description: >-
  Render bar, line, pie, geo, and other Google Charts in webforJ using the
  GoogleChart component with a typed Java options map and data API.
_i18n_hash: 8e1916e526ef45c55ca75c6cd32a4e7f
---
<DocChip chip='shadow' />
<DocChip chip='name' label="google-chart" exclude= 'true' />
<DocChip chip='since' label='23.06' />
<JavadocLink type="googlecharts" location="com/webforj/component/googlecharts/GoogleChart" top='true'/>

Le composant `GoogleChart` intègre la bibliothèque [Google Charts](https://developers.google.com/chart) dans webforJ, vous donnant accès à des types de graphiques tels que barres, lignes, secteurs, géo, et plus encore. Les graphiques sont configurés avec Java à l'aide d'un type, d'un ensemble de données et d'une carte d'options qui contrôle l'apparence et le comportement.

<!-- INTRO_END -->

## Création d'un graphique {#creating-a-chart}

:::info Importation de Google Charts
Pour utiliser la classe `GoogleChart` dans votre application, utilisez le XML suivant dans votre fichier POM :

```xml
<dependency>
  <groupId>com.webforj</groupId>
  <artifactId>webforj-googlecharts</artifactId>
  <version>${webforj.version}</version>
</dependency>
```
:::

Pour créer un graphique, spécifiez un type de graphique, configurez ses options visuelles et fournissez les données à afficher.

Cet exemple crée un graphique géo qui cartographie les données de revenus à travers différents pays, avec des couleurs personnalisées, un positionnement de légende et un dimensionnement de la zone de graphique :

<ComponentDemo
path='/webforj/chart'
files={[
  'src/main/java/com/webforj/samples/views/googlecharts/ChartView.java',
  'src/main/frontend/css/googlecharts/chart.css',
]}
height='300px'
/>

## Types de graphiques {#chart-types}

Le module complémentaire `GoogleChart` offre une vaste gamme de types de graphiques pour répondre à divers besoins de visualisation des données. Sélectionner le type de graphique approprié est essentiel pour communiquer efficacement l'histoire des données. Consultez la galerie ci-dessous pour des exemples de graphiques courants pouvant être utilisés dans une application webforJ.

<ComponentDemo
path='/webforj/chartgallery'
files={[
  'src/main/java/com/webforj/samples/views/googlecharts/ChartGalleryView.java',
  'src/main/frontend/css/googlecharts/chartGallery.css',
]}
height='600px'
/>

## Options {#options}

Le module complémentaire `GoogleChart` permet une personnalisation étendue grâce à une variété d'options. Ces options vous permettent d’adapter l’apparence et la fonctionnalité de vos graphiques en fonction des besoins de votre application. Les options sont passées sous forme de `Map<String, Object>` au méthode `setOptions()` du graphique.

Voici un exemple pour définir les options d'un graphique :

```java
Map<String, Object> options = new HashMap<>();
options.put("title", "Revenus Mensuels");
options.put("backgroundColor", "#EFEFEF");

// Appliquer les options au graphique
chart.setOptions(options);
```

Pour plus d'informations sur les options disponibles pour des graphiques spécifiques, consultez la [référence de l'API de visualisation Google (Galerie de graphiques)](https://developers.google.com/chart/interactive/docs/gallery).

## Définition des données {#setting-data}

La visualisation des données avec `GoogleChart` nécessite de structurer et de définir correctement les données. Ce guide vous accompagnera dans la préparation de vos données et leur application à vos graphiques.

### Configuration de base des données {#basic-data-setup}

La manière la plus simple de définir les données est d'utiliser `List<Object>`, où chaque ligne est une liste de valeurs.

```java
List<Object> data = new ArrayList<>();
data.add(Arrays.asList("Tâche", "Heures par Jour"));
data.add(Arrays.asList("Travail", 11));
data.add(Arrays.asList("Manger", 2));
data.add(Arrays.asList("Trajet", 2));
data.add(Arrays.asList("Regarder la télé", 2));
data.add(Arrays.asList("Dormir", 7));
chart.setData(data);
```

### Utilisation de cartes pour des structures plus complexes {#using-maps-for-more-complex-structures}

Pour des structures de données plus complexes, vous pouvez utiliser des cartes pour représenter des lignes, puis les convertir dans le format requis.

```java
List<Object> data = new ArrayList<>();

// Ligne d'en-tête
data.add(Arrays.asList("Pays", "Revenu"));

// Lignes de données
Map<String, Object> row1 = Map.of("Pays", "Allemagne", "Revenu", 1000);
Map<String, Object> row2 = Map.of("Pays", "États-Unis", "Revenu", 1170);
Map<String, Object> row3 = Map.of("Pays", "Brésil", "Revenu", 660);

data.add(new ArrayList<>(row1.values()));
data.add(new ArrayList<>(row2.values()));
data.add(new ArrayList<>(row3.values()));

chart.setData(data);
```

Une fois les données préparées, elles peuvent être appliquées au GoogleChart en utilisant la méthode setData.

<ComponentDemo
path='/webforj/chartsettingdata'
files={['src/main/java/com/webforj/samples/views/googlecharts/ChartSettingDataView.java']}
height='300px'
/>

### Chargement de données et d'options à partir de JSON {#loading-data-and-options-from-json}

Vous pouvez également charger des données et des options à partir de fichiers JSON en utilisant Gson pour une gestion plus facile. Cette approche aide à garder vos données et options organisées et faciles à mettre à jour.

```java
List<Object> data = new ArrayList<>();
data.add(Arrays.asList("Année", "Ventes", "Dépenses"));
data.add(Arrays.asList("2013", 1000, 400));
data.add(Arrays.asList("2014", 1170, 460));
data.add(Arrays.asList("2015", 660, null));
data.add(Arrays.asList("2016", 1030, 540));
chart.setData(data);

Map<String, Object> options = new Gson().fromJson(
  Assets.contentOf("options.json"),
  new TypeToken<Map<String, Object>>() {}.getType()
);
chart.setOptions(options);
```

## Mise à jour des visuels du graphique {#updating-chart-visuals}

Le `GoogleChart` se met à jour automatiquement lorsque ses données, options, type ou sélection changent. Vous n'avez pas besoin d'appeler `redraw()` après avoir utilisé `setData()`, `setOptions()`, `setType()`, ou `setSelection()`.

Le composant écoute également les événements de redimensionnement de la fenêtre du navigateur et se redessine automatiquement pour s'adapter aux nouvelles dimensions.

Appelez `redraw()` pour des changements de taille du conteneur qui ne déclenchent pas d'événement de redimensionnement de fenêtre. Des exemples incluent l'extension d'un panneau et le redimensionnement d'une barre latérale. Appelez-le après que les nouvelles dimensions du conteneur aient été appliquées.

La démo suivante met à jour les données du graphique avec `setData()`. Le graphique se redessine automatiquement lorsque les nouvelles données sont appliquées.

<ComponentDemo
path='/webforj/chartredraw'
files={[
  'src/main/java/com/webforj/samples/views/googlecharts/ChartRedrawView.java',
  'src/main/frontend/css/googlecharts/chartRedraw.css',
]}
height='650px'
/>

## Exportation des graphiques sous forme d'images {#exporting-charts-as-images}

La méthode `getImageUri()` fournit un moyen d'exporter vos Google Charts sous forme d'images PNG encodées en base64. Cette méthode est particulièrement utile pour partager des graphiques en dehors de l'environnement web, les intégrer dans des emails ou des documents, ou simplement pour des raisons d'archivage.

Appelez `getImageUri()` sur votre instance de graphique après que le graphique a été entièrement rendu. En général, cette méthode est utilisée dans un auditeur d'événements "prêt" pour garantir que le graphique est prêt à être exporté :

```java
chart.addReadyListener(e -> {
  String imageUri = chart.getImageUri();
  // Vous pouvez maintenant utiliser imageUri, par exemple, en tant qu'attribut src d'une balise img
});
```

## `GoogleChartSelectedEvent` {#googlechartselectedevent}

L'`GoogleChartSelectedEvent` est déclenché chaque fois qu'un utilisateur sélectionne un point de données ou un segment dans un composant Google Chart. Cet événement permet d'interagir avec les données de graphique sélectionnées, fournissant des détails sur ce qui a été sélectionné. L'événement peut être écouté en utilisant la méthode `addSelectedListener()` sur l'instance de `GoogleChart`.

L'`GoogleChartSelectedEvent` est utile dans les applications où l'interaction de l'utilisateur avec le graphique est nécessaire.

```java
GoogleChart chart = new GoogleChart(GoogleChart.Type.BAR);

// Ajouter l'auditeur de sélection au graphique
chart.addSelectedListener(event -> {
  // Obtenir la sélection
  List<Object> selection = chart.getSelection();

  // Traiter l'événement de sélection
  if (!selection.isEmpty()) {
    System.out.println("Ligne Sélectionnée : " + selection.get(0));
    // Traitement supplémentaire basé sur la ligne/colonne de sélection
  }
});
```

### Payload {#payload}
L'`GoogleChartSelectedEvent` permet d'accéder aux données de sélection, qui peuvent être récupérées en utilisant la méthode `getSelection()` sur l'objet graphique. Cette méthode renvoie une liste d'objets, où chaque objet contient les propriétés suivantes :

- **row** : L'indice de la ligne dans le tableau de données du graphique qui a été sélectionnée.
- **column** : L'indice de la colonne dans le tableau de données, qui est facultatif et s'applique aux graphiques qui permettent la sélection de cellules individuelles, comme un graphique en tableau.

Pour des graphiques comme les graphiques à secteurs ou à barres, seule la `row` est généralement fournie, indiquant le point de données sélectionné.

Voici un exemple de payload :
```java
[
  {
    "row": 3,  // L'indice de la ligne sélectionnée dans les données
    "column": 2  // (Optionnel) L'indice de la colonne sélectionnée
  }
]
```

:::info Sélection de plusieurs points de données
Si l'utilisateur sélectionne plusieurs points de données, la méthode `getSelection()` renverra un tableau d'objets, chacun représentant un élément sélectionné. Le payload peut varier en fonction du type de graphique et de l'interaction que l'utilisateur effectue.
:::
