---
title: Google Charts
sidebar_position: 50
_i18n_hash: 7421699c19919de6aab7db8a36123524
---
<DocChip chip='shadow' />
<DocChip chip='name' label="google-chart" exclude= 'true' />
<DocChip chip='since' label='23.06' />
<JavadocLink type="googlecharts" location="com/webforj/component/googlecharts/GoogleChart" top='true'/>

Le composant `GoogleChart` intègre la bibliothèque [Google Charts](https://developers.google.com/chart) dans webforJ, vous donnant accès à des types de graphiques tels que barres, lignes, secteurs, géographiques, et plus encore. Les graphiques sont configurés avec Java à l'aide d'un type, d'un ensemble de données et d'une carte d'options qui contrôle l'apparence et le comportement.

<!-- INTRO_END -->

## Création d'un graphique {#creating-a-chart}

:::info Importation des graphiques Google
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

Cet exemple crée un graphique géographique qui cartographie les données de revenus à travers différents pays, avec des couleurs personnalisées, un positionnement de légende et un dimensionnement de la zone graphique :

<ComponentDemo 
path='/webforj/chart?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/googlecharts/ChartView.java'
cssURL='/css/googlecharts/chart.css'
height='300px'
/>

## Types de graphiques {#chart-types}

L'addon `GoogleChart` propose un large éventail de types de graphiques adaptés à divers besoins de visualisation de données. Choisir le type de graphique approprié est essentiel pour communiquer efficacement l'histoire des données. Consultez la galerie ci-dessous pour des exemples de graphiques courants pouvant être utilisés dans une application webforJ.

<ComponentDemo 
path='/webforj/chartgallery?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/googlecharts/ChartGalleryView.java'
cssURL='/css/googlecharts/chartGallery.css'
height='600px'
/>

## Options {#options}

L'addon `GoogleChart` permet une personnalisation étendue via une variété d'options. Ces options vous permettent d'adapter l'apparence et la fonctionnalité de vos graphiques pour répondre aux besoins de votre application. Les options sont passées en tant que `Map<String, Object>` au méthode `setOptions()` du graphique.

Voici un exemple pour définir les options d'un graphique :

```java
Map<String, Object> options = new HashMap<>();
options.put("title", "Revenus mensuels");
options.put("backgroundColor", "#EFEFEF");

// Appliquer les options au graphique
chart.setOptions(options);
```

Pour plus d'informations sur les options disponibles pour des graphiques spécifiques, consultez la [référence de l'API de visualisation Google (Galerie de graphiques)](https://developers.google.com/chart/interactive/docs/gallery).

## Définition des données {#setting-data}

Visualiser des données avec `GoogleChart` nécessite de structurer correctement et de définir les données. Ce guide vous expliquera comment préparer vos données et les appliquer à vos graphiques.

### Configuration des données de base {#basic-data-setup}

La manière la plus simple de définir les données est d'utiliser `List<Object>`, où chaque ligne est une liste de valeurs.

```java
List<Object> data = new ArrayList<>();
data.add(Arrays.asList("Tâche", "Heures par jour"));
data.add(Arrays.asList("Travail", 11));
data.add(Arrays.asList("Manger", 2));
data.add(Arrays.asList("Trajet", 2));
data.add(Arrays.asList("Regarder la télévision", 2));
data.add(Arrays.asList("Dormir", 7));
chart.setData(data);
```

### Utilisation des cartes pour des structures plus complexes {#using-maps-for-more-complex-structures}

Pour des structures de données plus complexes, vous pouvez utiliser des cartes pour représenter les lignes, puis les convertir au format requis.

```java
List<Object> data = new ArrayList<>();

// Ligne d'en-tête
data.add(Arrays.asList("Pays", "Revenus"));

// Lignes de données
Map<String, Object> row1 = Map.of("Pays", "Allemagne", "Revenus", 1000);
Map<String, Object> row2 = Map.of("Pays", "États-Unis", "Revenus", 1170);
Map<String, Object> row3 = Map.of("Pays", "Brésil", "Revenus", 660);

data.add(new ArrayList<>(row1.values()));
data.add(new ArrayList<>(row2.values()));
data.add(new ArrayList<>(row3.values()));

chart.setData(data);
```

Une fois les données préparées, elles peuvent être appliquées au GoogleChart à l'aide de la méthode setData.

<ComponentDemo 
path='/webforj/chartsettingdata?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/googlecharts/ChartSettingDataView.java'
height='300px'
/>

<!-- tabs={['ChartDemoSettingData.java']} -->

### Chargement des données et options depuis JSON {#loading-data-and-options-from-json}

Vous pouvez également charger des données et des options depuis des fichiers JSON en utilisant Gson pour une gestion plus facile. Cette approche permet de garder vos données et options organisées et faciles à mettre à jour.

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

Rafraîchir ou mettre à jour l'apparence de vos graphiques en réponse à des modifications de données, des interactions utilisateur ou des ajustements d'options visuelles est simple avec la méthode `redraw()`. Cette méthode garantit que vos graphiques restent précis et visuellement alignés avec les données sous-jacentes ou toute modification de leurs paramètres.

Appelez `redraw()` dans des scénarios tels que :

- **Après des modifications de données** : Assure que le graphique reflète toute mise à jour de sa source de données.
- **Lors du changement d'options** : Applique de nouveaux styles ou changements de configuration au graphique.
- **Pour des ajustements réactifs** : Ajuste la mise en page ou la taille du graphique lorsque les dimensions du conteneur changent, assurant un affichage optimal sur différents appareils.

<ComponentDemo 
path='/webforj/chartredraw?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/googlecharts/ChartRedrawView.java'
cssURL='/css/googlecharts/chartRedraw.css'
height='650px'
/>

<!-- tabs={['ChartDemoRedraw.java', 'redrawchart_styles.css']} -->

## Exportation des graphiques en tant qu'images {#exporting-charts-as-images}

La méthode `getImageUri()` fournit un moyen d'exporter vos graphiques Google sous forme d'images PNG encodées en base64. Cette méthode est particulièrement utile pour partager des graphiques en dehors de l'environnement web, les intégrer dans des emails ou des documents, ou simplement pour des fins d'archivage.

Appelez `getImageUri()` sur votre instance de graphique après que le graphique a été complètement rendu. En général, cette méthode est utilisée dans un écouteur d'événements "prêt" pour s'assurer que le graphique est prêt pour l'exportation :

```java
chart.addReadyListener(e -> {
  String imageUri = chart.getImageUri();
  // Vous pouvez maintenant utiliser l'imageUri, par exemple, comme attribut src d'une balise img
});
```

## `GoogleChartSelectedEvent` {#googlechartselectedevent}

L'événement `GoogleChartSelectedEvent` est déclenché chaque fois qu'un utilisateur sélectionne un point de données ou un segment dans un composant Google Chart. Cet événement permet d'interagir avec les données de graphique sélectionnées, fournissant des détails sur ce qui a été sélectionné. L'événement peut être écouté en utilisant la méthode `addSelectedListener()` sur l'instance `GoogleChart`.

L'événement `GoogleChartSelectedEvent` est utile dans les applications où l'interaction utilisateur avec le graphique est nécessaire. 

```java
GoogleChart chart = new GoogleChart(GoogleChart.Type.BAR);

// Ajouter l'écouteur de sélection au graphique
chart.addSelectedListener(event -> {
  // Obtenir la sélection
  List<Object> selection = chart.getSelection();
  
  // Gérer l'événement sélectionné
  if (!selection.isEmpty()) {
    System.out.println("Ligne sélectionnée : " + selection.get(0));
    // Traitement ultérieur basé sur la ligne/colonne de la sélection
  }
});
```

### Payload {#payload}
L'événement `GoogleChartSelectedEvent` fournit l'accès aux données de sélection, qui peuvent être récupérées en utilisant la méthode `getSelection()` sur l'objet graphique. Cette méthode renvoie une liste d'objets, où chaque objet contient les propriétés suivantes :

- **row** : L'index de la ligne dans la table de données du graphique qui a été sélectionnée.
- **column** : L'index de la colonne dans la table de données, qui est optionnel et s'applique aux graphiques qui permettent la sélection de cellules individuelles, comme un graphique en table.
  
Pour des graphiques comme les graphiques à secteurs ou à barres, seul `row` est généralement fourni, indiquant le point de données sélectionné.

Voici un exemple de payload :
```java
[
  {
    "row": 3,  // L'index de la ligne sélectionnée dans les données
    "column": 2  // (Optionnel) L'index de la colonne sélectionnée
  }
]
```

:::info Sélection de plusieurs points de données
Si l'utilisateur sélectionne plusieurs points de données, la méthode `getSelection()` renverra un tableau d'objets, chacun représentant un élément sélectionné. Le payload peut varier en fonction du type de graphique et de l'interaction réalisée par l'utilisateur.
:::
