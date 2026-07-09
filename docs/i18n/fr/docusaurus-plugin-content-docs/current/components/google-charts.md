---
title: Google Charts
sidebar_position: 50
description: >-
  Render bar, line, pie, geo, and other Google Charts in webforJ using the
  GoogleChart component with a typed Java options map and data API.
_i18n_hash: a733a52b4d9ffb87eae039e9729b9cb9
---
<DocChip chip='shadow' />
<DocChip chip='name' label="google-chart" exclude= 'true' />
<DocChip chip='since' label='23.06' />
<JavadocLink type="googlecharts" location="com/webforj/component/googlecharts/GoogleChart" top='true'/>

Le composant `GoogleChart` intègre la bibliothèque [Google Charts](https://developers.google.com/chart) dans webforJ, vous donnant accès à des types de graphiques comme barres, lignes, secteurs, géo, et plus encore. Les graphiques sont configurés avec Java en utilisant un type, un ensemble de données et une carte d'options qui contrôle l'apparence et le comportement.

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

Cet exemple crée un graphique géo qui cartographie les données de revenus à travers différents pays, avec des couleurs personnalisées, un positionnement de légende et une taille de zone de graphique :

<ComponentDemo
path='/webforj/chart'
files={[
  'src/main/java/com/webforj/samples/views/googlecharts/ChartView.java',
  'src/main/frontend/css/googlecharts/chart.css',
]}
height='300px'
/>

## Types de graphiques {#chart-types}

L'addon `GoogleChart` offre un large éventail de types de graphiques pour répondre à divers besoins de visualisation de données. Sélectionner le type de graphique approprié est essentiel pour communiquer efficacement l'histoire des données. Voir la galerie ci-dessous pour des exemples de graphiques courants qui peuvent être utilisés dans une application webforJ.

<ComponentDemo
path='/webforj/chartgallery'
files={[
  'src/main/java/com/webforj/samples/views/googlecharts/ChartGalleryView.java',
  'src/main/frontend/css/googlecharts/chartGallery.css',
]}
height='600px'
/>

## Options {#options}

L'addon `GoogleChart` permet une personnalisation étendue à travers une variété d'options. Ces options vous permettent d'adapter l'apparence et la fonctionnalité de vos graphiques aux besoins de votre application. Les options sont passées en tant que `Map<String, Object>` à la méthode `setOptions()` du graphique.

Voici un exemple pour définir les options d'un graphique :

```java
Map<String, Object> options = new HashMap<>();
options.put("title", "Revenu Mensuel");
options.put("backgroundColor", "#EFEFEF");

// Appliquer les options au graphique
chart.setOptions(options);
```

Pour plus d'informations sur les options disponibles pour des graphiques spécifiques, consultez la [référence de l'API Google Visualization (Galerie de Graphiques)](https://developers.google.com/chart/interactive/docs/gallery).

## Définition des données {#setting-data}

Visualiser des données avec `GoogleChart` nécessite de structurer et de définir correctement les données. Ce guide vous aidera à préparer vos données et à les appliquer à vos graphiques.

### Configuration des données de base {#basic-data-setup}

La façon la plus simple de définir les données est d'utiliser `List<Object>`, où chaque ligne est une liste de valeurs.

```java
List<Object> data = new ArrayList<>();
data.add(Arrays.asList("Tâche", "Heures par Jour"));
data.add(Arrays.asList("Travail", 11));
data.add(Arrays.asList("Manger", 2));
data.add(Arrays.asList("Trajet", 2));
data.add(Arrays.asList("Regarder la TV", 2));
data.add(Arrays.asList("Dormir", 7));
chart.setData(data);
```

### Utilisation de cartes pour des structures plus complexes {#using-maps-for-more-complex-structures}

Pour des structures de données plus complexes, vous pouvez utiliser des cartes pour représenter les lignes et ensuite les convertir dans le format requis.

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

Une fois les données préparées, elles peuvent être appliquées au GoogleChart à l'aide de la méthode setData.

<ComponentDemo
path='/webforj/chartsettingdata'
files={['src/main/java/com/webforj/samples/views/googlecharts/ChartSettingDataView.java']}
height='300px'
/>

### Chargement de données et d'options depuis JSON {#loading-data-and-options-from-json}

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

## Mise à jour de l'apparence des graphiques {#updating-chart-visuals}

Rafraîchir ou mettre à jour l'apparence de vos graphiques en réponse aux modifications de données, aux interactions utilisateur ou aux ajustements d'options visuelles est simple avec la méthode `redraw()`. Cette méthode garantit que vos graphiques restent exacts et visuellement alignés avec les données sous-jacentes ou toute modification de leurs paramètres.

Appelez `redraw()` dans des scénarios tels que :

- **Après des modifications de données** : Assure que le graphique reflète toute mise à jour de sa source de données.
- **Lors du changement d'options** : Applique de nouveaux styles ou changements de configuration au graphique.
- **Pour des ajustements responsives** : Ajuste la disposition ou la taille du graphique lorsque les dimensions du conteneur changent, garantissant un affichage optimal sur tous les appareils.

<ComponentDemo
path='/webforj/chartredraw'
files={[
  'src/main/java/com/webforj/samples/views/googlecharts/ChartRedrawView.java',
  'src/main/frontend/css/googlecharts/chartRedraw.css',
]}
height='650px'
/>

## Exportation de graphiques sous forme d'images {#exporting-charts-as-images}

La méthode `getImageUri()` fournit un moyen d'exporter vos Google Charts sous forme d'images PNG encodées en base64. Cette méthode est particulièrement utile pour partager des graphiques en dehors de l'environnement web, les incorporer dans des e-mails ou des documents, ou simplement pour des fins d'archivage.

Appelez `getImageUri()` sur votre instance de graphique après que le graphique a été entièrement rendu. En général, cette méthode est utilisée dans un écouteur d'événements "prêt" pour garantir que le graphique est prêt à être exporté :

```java
chart.addReadyListener(e -> {
  String imageUri = chart.getImageUri();
  // Vous pouvez maintenant utiliser l'imageUri, par exemple comme l'attribut src d'une balise img
});
```

## `GoogleChartSelectedEvent` {#googlechartselectedevent}

L'`GoogleChartSelectedEvent` est déclenché chaque fois qu'un utilisateur sélectionne un point de données ou un segment dans un composant Google Chart. Cet événement permet d'interagir avec les données du graphique sélectionné, fournissant des détails sur ce qui a été sélectionné. L'événement peut être écouté en utilisant la méthode `addSelectedListener()` sur l'instance `GoogleChart`.

L'`GoogleChartSelectedEvent` est utile dans des applications où l'interaction de l'utilisateur avec le graphique est nécessaire.

```java
GoogleChart chart = new GoogleChart(GoogleChart.Type.BAR);

// Ajouter l'auditeur de sélection au graphique
chart.addSelectedListener(event -> {
  // Obtenir la sélection
  List<Object> selection = chart.getSelection();

  // Traiter l'événement sélectionné
  if (!selection.isEmpty()) {
    System.out.println("Ligne Sélectionnée : " + selection.get(0));
    // Traitement supplémentaire en fonction de la ligne/colonne de la sélection
  }
});
```

### Charge utile {#payload}
L'`GoogleChartSelectedEvent` fournit un accès aux données de sélection, qui peuvent être récupérées en utilisant la méthode `getSelection()` sur l'objet graphique. Cette méthode retourne une liste d'objets, où chaque objet contient les propriétés suivantes :

- **row** : L'index de la ligne dans le tableau de données du graphique qui a été sélectionnée.
- **column** : L'index de la colonne dans le tableau de données, qui est optionnel et s'applique aux graphiques qui permettent la sélection de cellules individuelles, comme un graphique en tableau.

Pour des graphiques tels que les graphiques en secteurs ou en barres, seule la `row` est généralement fournie, indiquant le point de données sélectionné.

Voici un exemple de charge utile :
```java
[
  {
    "row": 3,  // L'index de ligne sélectionné dans les données
    "column": 2  // (Optionnel) L'index de colonne sélectionné
  }
]
```

:::info Sélection de plusieurs points de données
Si l'utilisateur sélectionne plusieurs points de données, la méthode `getSelection()` renverra un tableau d'objets, chacun représentant un élément sélectionné. La charge utile peut varier en fonction du type de graphique et de l'interaction que l'utilisateur effectue.
:::
