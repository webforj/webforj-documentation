---
title: Google Charts
sidebar_position: 50
_i18n_hash: b477c90cfb24a59329f3047d7ae7d24c
---
<DocChip chip='shadow' />
<DocChip chip='name' label="google-chart" exclude= 'true' />
<DocChip chip='since' label='23.06' />
<JavadocLink type="googlecharts" location="com/webforj/component/googlecharts/GoogleChart" top='true'/>

<!-- Brève présentation du composant et de ce qu'il est/fait -->

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

La classe `GoogleChart` est une solution complète pour intégrer des graphiques riches et interactifs au sein des applications web. Cette classe agit comme un pont vers la bibliothèque [Google Charts](https://developers.google.com/chart), offrant une large variété de types de graphiques adaptés à toute tâche de visualisation de données.

<ComponentDemo 
path='/webforj/chart?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/googlecharts/ChartView.java'
cssURL='/css/googlecharts/chart.css'
height='300px'
/>


## Types de graphiques {#chart-types}

L'addon `GoogleChart` propose une gamme complète de types de graphiques pour répondre à divers besoins en matière de visualisation de données. Choisir le type de graphique approprié est essentiel pour communiquer efficacement l'histoire des données. Consultez la galerie ci-dessous pour des exemples de graphiques courants pouvant être utilisés dans une application webforJ.

<ComponentDemo 
path='/webforj/chartgallery?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/googlecharts/ChartGalleryView.java'
cssURL='/css/googlecharts/chartGallery.css'
height='600px'
/>

## Options {#options}

L'addon `GoogleChart` permet une personnalisation extensive grâce à une variété d'options. Ces options vous permettent d'adapter l'apparence et la fonctionnalité de vos graphiques pour répondre aux besoins de votre application. Les options sont passées en tant que `Map<String, Object>` au méthode `setOptions()` du graphique.

Voici un exemple pour définir les options d'un graphique :

```java
Map<String, Object> options = new HashMap<>();
options.put("title", "Revenu Mensuel");
options.put("backgroundColor", "#EFEFEF");

// Appliquer les options au graphique
chart.setOptions(options);
```

Pour plus d'informations sur les options disponibles pour des graphiques spécifiques, consultez la [référence de l'API Google Visualization (Galerie de Graphiques)](https://developers.google.com/chart/interactive/docs/gallery).

## Définir les données {#setting-data}

Visualiser des données avec `GoogleChart` nécessite de structurer correctement et de définir les données. Ce guide vous accompagnera dans la préparation de vos données et leur application à vos graphiques.

### Configuration de base des données {#basic-data-setup}

La manière la plus simple de définir les données est d'utiliser `List<Object>`, où chaque ligne est une liste de valeurs.

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

### Utiliser des cartes pour des structures plus complexes {#using-maps-for-more-complex-structures}

Pour des structures de données plus complexes, vous pouvez utiliser des cartes pour représenter les lignes puis les convertir dans le format requis.

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

Une fois les données préparées, elles peuvent être appliquées à GoogleChart en utilisant la méthode setData.

<ComponentDemo 
path='/webforj/chartsettingdata?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/googlecharts/ChartSettingDataView.java'
height='300px'
/>

<!-- tabs={['ChartDemoSettingData.java']} -->

### Charger des données et options depuis JSON {#loading-data-and-options-from-json}

Vous pouvez également charger des données et options depuis des fichiers JSON en utilisant Gson pour une gestion plus facile. Cette approche aide à garder vos données et options organisées et faciles à mettre à jour.


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

## Mettre à jour l'apparence des graphiques {#updating-chart-visuals}

Rafraîchir ou mettre à jour l'apparence de vos graphiques en réponse aux modifications de données, aux interactions des utilisateurs ou aux ajustements d'options visuelles est simple avec la méthode `redraw()`. Cette méthode garantit que vos graphiques restent précis et visuellement alignés avec les données sous-jacentes ou toute modification de leurs paramètres.

Appelez `redraw()` dans des scénarios tels que :

- **Après des modifications de données** : Assure que le graphique reflète toutes les mises à jour de sa source de données.
- **Lors de changements d'options** : Applique de nouveaux styles ou modifications de configuration au graphique.
- **Pour des ajustements réactifs** : Ajuste la mise en page ou la taille du graphique lorsque les dimensions du conteneur changent, garantissant un affichage optimal sur tous les appareils.

<ComponentDemo 
path='/webforj/chartredraw?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/googlecharts/ChartRedrawView.java'
cssURL='/css/googlecharts/chartRedraw.css'
height='650px'
/>

<!-- tabs={['ChartDemoRedraw.java', 'redrawchart_styles.css']} -->

## Exporter des graphiques en tant qu'images {#exporting-charts-as-images}

La méthode `getImageUri()` fournit un moyen d'exporter vos Graphiques Google en tant qu'images PNG encodées en base64. Cette méthode est particulièrement utile pour partager des graphiques en dehors de l'environnement web, les intégrer dans des e-mails ou des documents, ou simplement à des fins d'archivage.

Appelez `getImageUri()` sur votre instance de graphique après que le graphique a été entièrement rendu. En général, cette méthode est utilisée dans un écouteur d'événements "prêt" pour s'assurer que le graphique est prêt à être exporté :

```java
chart.addReadyListener(e -> {
    String imageUri = chart.getImageUri();
    // Vous pouvez maintenant utiliser l'imageUri, par exemple, comme l'attribut src d'une balise img
});
```

## `GoogleChartSelectedEvent` {#googlechartselectedevent}

L'événement `GoogleChartSelectedEvent` est déclenché chaque fois qu'un utilisateur sélectionne un point de données ou un segment dans un composant Google Chart. Cet événement permet d'interagir avec les données sélectionnées du graphique, fournissant des détails sur ce qui a été sélectionné. L'événement peut être écouté en utilisant la méthode `addSelectedListener()` sur l'instance `GoogleChart`.

L'événement `GoogleChartSelectedEvent` est utile dans les applications où l'interaction de l'utilisateur avec le graphique est nécessaire.

```java
GoogleChart chart = new GoogleChart(GoogleChart.Type.BAR);

// Ajouter l'écouteur de sélection au graphique
chart.addSelectedListener(event -> {
    // Obtenir la sélection
    List<Object> selection = chart.getSelection();
    
    // Traiter l'événement sélectionné
    if (!selection.isEmpty()) {
        System.out.println("Ligne Sélectionnée : " + selection.get(0));
        // Traitement supplémentaire basé sur la sélection de ligne/colonne
    }
});
```

### Payload {#payload}
L'événement `GoogleChartSelectedEvent` fournit l'accès aux données de sélection, qui peuvent être récupérées en utilisant la méthode `getSelection()` sur l'objet graphique. Cette méthode renvoie une liste d'objets, où chaque objet contient les propriétés suivantes :

- **row** : L'index de la ligne dans la table de données du graphique qui a été sélectionnée.
- **column** : L'index de la colonne dans la table de données, qui est optionnel et s'applique aux graphiques qui permettent la sélection de cellules individuelles, comme un graphique de tableau.
  
Pour des graphiques comme les graphiques en secteurs ou les graphiques à barres, seule la `row` est généralement fournie, indiquant le point de données sélectionné.

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
