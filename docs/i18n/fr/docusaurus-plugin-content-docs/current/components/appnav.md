---
title: AppNav
sidebar_position: 6
sidebar_class_name: new-content
description: >-
  Build hierarchical side navigation menus with AppNav and AppNavItem, linking
  to routes, registered views, or external URLs.
_i18n_hash: 7283cd36346dd18b131a5393db8e8fd3
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-app-nav" />
<DocChip chip="name" label="dwc-app-nav-item" />
<DocChip chip='since' label='24.12' />
<JavadocLink type="appnav" location="com/webforj/component/appnav/AppNav" top='true'/>

Le composant `AppNav` crée un menu de navigation latéral à partir des entrées `AppNavItem`. Les items peuvent lier à des vues internes ou des ressources externes, s'emboîter sous des items parents pour former des menus hiérarchiques, et porter des icônes, des badges, ou d'autres composants pour donner aux utilisateurs plus de contexte d'un coup d'œil.

<!-- INTRO_END -->

## Ajouter et imbriquer des items {#adding-and-nesting-items}

Les instances `AppNavItem` sont utilisées pour peupler la structure de `AppNav`. Ces items peuvent être des liens simples ou des en-têtes de groupe imbriqués qui contiennent des items enfants. Les en-têtes de groupe sans liens agissent comme des conteneurs extensibles.

Utilisez `addItem()` pour inclure des items dans la navigation :

```java
AppNavItem dashboard = new AppNavItem("Dashboard", "/dashboard");
AppNavItem admin = new AppNavItem("Admin");
admin.addItem(new AppNavItem("Users", "/admin/users"));
admin.addItem(new AppNavItem("Settings", "/admin/settings"));

AppNav nav = new AppNav();
nav.addItem(dashboard);
nav.addItem(admin);
```

:::tip Lier les items de groupe
Les items de premier niveau dans un arbre de navigation sont généralement destinés à être extensibles — pas des liens cliquables. Définir un `path` sur de tels items peut créer de la confusion pour les utilisateurs qui s'attendent à ce qu'ils révèlent des sous-items au lieu de naviguer ailleurs.

Si vous souhaitez que l'en-tête du groupe déclenche une action personnalisée (comme l'ouverture de documents externes), gardez le chemin du groupe vide et ajoutez plutôt un contrôle interactif comme un [`IconButton`](./icon#icon-buttons) à la suffixe de l'item. Cela maintient une expérience utilisateur cohérente et claire.
:::

<!--vale off-->
<ComponentDemo
path='/webforj/appnav/Social'
frame='desktop'
files={[
  'src/main/java/com/webforj/samples/views/appnav/AppNavView.java',
  'src/main/java/com/webforj/samples/views/appnav/AppNavPageView.java',
]}
/>
<!--vale on-->

## Lier des items {#linking-items}

Chaque `AppNavItem` peut naviguer vers une vue interne ou un lien externe. Vous pouvez définir cela en utilisant des chemins statiques ou des classes de vues enregistrées.

### Chemins statiques {#static-paths}

Utilisez des chemins de chaîne pour définir des liens directement :

```java
AppNavItem docs = new AppNavItem("Docs", "/docs");
AppNavItem help = new AppNavItem("Help", "https://support.example.com");
```

### Vues enregistrées {#registered-views}

Si vos vues sont enregistrées avec le [routeur](../routing/overview), vous pouvez passer la classe au lieu d'une URL codée en dur :

```java
AppNavItem settings = new AppNavItem("Settings", SettingsView.class);
```

Si votre route annotée prend en charge [les paramètres de route](../routing/route-patterns#named-parameters), vous pouvez également passer un `ParametersBag` :

```java
ParametersBag params = ParametersBag.of("id=123");
AppNavItem advanced = new AppNavItem("User", UserView.class, params);
```

### Avec des paramètres de requête {#with-query-parameters}

Passez un `ParametersBag` pour inclure des chaînes de requête :

```java
ParametersBag params = ParametersBag.of("param1=value1&param2=value2");
AppNavItem advanced = new AppNavItem("Advanced", SettingsView.class, params);
advanced.setQueryParameters(params);
```

## Comportement de la cible {#target-behavior}

Contrôlez comment les liens s'ouvrent en utilisant `setTarget()`. Cela est particulièrement utile pour les liens externes ou les vues pop-out.

- **`SELF`** (par défaut) : Ouvre dans la vue actuelle.
- **`BLANK`** : Ouvre dans un nouvel onglet ou une nouvelle fenêtre.
- **`PARENT`** : Ouvre dans le contexte parent de navigation.
- **`TOP`** : Ouvre dans le contexte de navigation de niveau supérieur.

```java
AppNavItem help = new AppNavItem("Help", "https://support.example.com");
help.setTarget(AppNavItem.NavigationTarget.BLANK);
```

## Préfixe et suffixe {#prefix-and-suffix}

`AppNavItem` prend en charge les composants de préfixe et de suffixe. Utilisez-les pour fournir une clarté visuelle avec des icônes, des badges ou des boutons.

- **Préfixe** : apparaît avant le label, utile pour les icônes.
- **Suffixe** : apparaît après le label, idéal pour les badges ou les actions.

```java
AppNavItem notifications = new AppNavItem("Alerts");
notifications.setPrefixComponent(TablerIcon.create("alert"));
notifications.setSuffixComponent(TablerIcon.create("link"));
```

## Groupes à ouverture automatique {#auto-opening-groups}

Utilisez `setAutoOpen(true)` sur le composant `AppNav` pour développer automatiquement les groupes imbriqués lorsque l'application est actualisée.

```java
nav.setAutoOpen(true);
```

## Épingler <DocChip chip='since' label='26.01' /> {#pinning}

L’épinglage permet à un utilisateur de faire remonter les items qu'il recherche le plus dans un groupe en haut de la navigation, de sorte qu'un menu profond garde une liste courte de favoris à portée de clic. C'est désactivé par défaut. Activez-le via la configuration d'épinglage :

```java
AppNav nav = new AppNav();
nav.getPinning().setEnabled(true);
```

Une fois activé, chaque item feuillleté navigable affiche un bouton d'épinglage. Le bouton est révélé au survol et au focus clavier, ce qui le rend accessible sans souris. L'activer déplace l'item dans le groupe épinglé en haut de la navigation.

Quelques règles gouvernent ce qui peut être épinglé et comment le groupe se comporte :

- Seuls les items feuillés navigables peuvent être épinglés. Les en-têtes de groupe (items avec enfants) ne peuvent jamais être épinglés.
- Le groupe épinglé apparaît uniquement lorsque quelque chose est épinglé, et disparaît à nouveau lorsque le dernier item est détaché.
- Détacher un item le retourne à sa position exacte d'origine, y compris les items imbriqués à plusieurs niveaux à l'intérieur de groupes.
- L'item est déplacé, pas copié, donc tout contenu de préfixe ou de suffixe et tous les écouteurs attachés continuent de fonctionner pendant qu'il se trouve dans le groupe épinglé.

La démonstration ci-dessous a l'épinglage activé avec un titre de groupe personnalisé et le tableau de bord épinglé au chargement. Survolez ou concentrez-vous sur un item feuillé pour révéler son bouton d'épinglage.

<ComponentDemo
path='/webforj/appnavpinning/Dashboard'
frame='desktop'
files={[
  'src/main/java/com/webforj/samples/views/appnav/AppNavPinningView.java',
  'src/main/java/com/webforj/samples/views/appnav/AppNavPinningPageView.java',
]}
/>

### Démarrer un item épinglé {#starting-an-item-pinned}

Démarrez un item dans le groupe épinglé en définissant son état épinglé. Utilisez `isPinned()` pour lire l'état actuel.

```java
AppNavItem reports = new AppNavItem("Reports", "/reports");
reports.setPinned(true);
```

:::info L'épinglage doit être activé
`setPinned(true)` n'a d'effet que lorsque l'épinglage est activé sur l'`AppNav` via `getPinning().setEnabled(true)`. Sans cela, l'appel n'a aucun effet.
:::

### Titre du groupe épinglé {#pinned-group-title}

Le groupe épinglé est étiqueté par défaut `Pinned`. Changez-le pour l'adapter à votre application :

```java
nav.getPinning().setTitle("Favorites");
```

### Clés d'épinglage {#pin-keys}

Chaque item épinglé porte une clé qui l'identifie pour la persistance et pour l'[événement d'épinglage](#reacting-to-pin-changes). Lorsque vous n'en définissez pas une, la clé tombe en arrière sur le chemin de l'item, donc `getPinKey()` renvoie toujours une valeur utilisable.

```java
AppNavItem reports = new AppNavItem("Reports", "/reports");
reports.setPinKey("reports");
```

Définissez une clé explicite lorsque le chemin peut changer à l'exécution. Une clé stable maintient un épinglage correspondant au bon item à travers les rechargements même si son URL se déplace.

### Sauvegarde automatique dans le stockage local {#autosave}

Les épingles ne vivent que pour la vue de page actuelle à moins que vous ne les persistiez. La sauvegarde automatique est l'option la plus simple : elle stocke l'ensemble des items épinglés dans le stockage local du navigateur et les restaure lors du rechargement. C'est désactivé par défaut. Cela nécessite un `id` (ou un nom) stable sur le composant pour la clé de stockage, et le constructeur `AppNav(String id)` est le moyen pratique de le définir :

```java
AppNav nav = new AppNav("main-nav"); // donne à la sauvegarde automatique une clé de stockage stable
nav.getPinning().setAutosave(true);
```

:::info La sauvegarde automatique nécessite un id
Sans `id` (ou nom) sur le composant, la sauvegarde automatique ne fait rien en silence, car elle n'a pas de clé stable sous laquelle stocker. La persistance est par navigateur, donc les épingles ne suivent pas un utilisateur vers un autre appareil ou navigateur.
:::

### Persistance personnalisée {#custom-persistence}

Pour une persistance que vous contrôlez, par exemple par utilisateur sur le serveur, désactivez la sauvegarde automatique et gérez-la vous-même via l'[événement d'épinglage](#reacting-to-pin-changes) et `setPinned` :

```java
nav.getPinning().setAutosave(false);

// persister l'ensemble actuel des clés épinglées chaque fois qu'il change
nav.onPin(event -> savePins(event.getKeys()));

// au chargement, restaurer chaque clé sauvegardée
restoredKeys.forEach(key -> findItem(key).setPinned(true));
```

### Réagir aux changements d'épinglage {#reacting-to-pin-changes}

L'événement d'épinglage se déclenche chaque fois qu'un item est épinglé ou détaché. Il transporte l'item qui a changé, sa clé, le nouvel état épinglé, et l'ensemble complet ordonné des clés épinglées :

```java
nav.onPin(event -> {
  AppNavItem item = event.getItem(); // l'item qui a changé, ou null s'il n'est plus dans la nav
  boolean pinned = event.isPinned();
  String key = event.getKey();
  List<String> all = event.getKeys(); // chaque clé épinglée, dans l'ordre épinglé
});
```

`getItem()` résout l'item en faisant correspondre sa clé d'épinglage et retourne `null` lorsque l'item ne fait plus partie de la navigation.

### Icônes d'épinglage {#pin-icons}

Le bouton utilise l'icône intégrée `dwc:pin` tant qu'un item est non épinglé et `dwc:pinned-off` lorsqu'il est épinglé. Échangez la vôtre via `setUnpinnedIcon` et `setPinnedIcon`, qui acceptent n'importe quelle `IconDefinition` :

```java
nav.getPinning()
   .setUnpinnedIcon(TablerIcon.create("pin"))
   .setPinnedIcon(TablerIcon.create("pinned-off"));
```

### Bouton d'épinglage sur les écrans tactiles {#pin-toggle-on-touchscreens}

Les écrans tactiles n'ont pas de survol pour révéler l'épingle, donc le bouton est caché par défaut. Gardez-le visible et cliquable sur les écrans tactiles avec `setTouchVisible(true)` :

```java
nav.getPinning().setTouchVisible(true);
```

## Recherche <DocChip chip='since' label='26.01' /> {#search}

Le champ de recherche filtre le menu par label d'item au fur et à mesure que l'utilisateur tape. C'est désactivé par défaut. Vous pouvez l'afficher et lui donner un espace réservé via la configuration de recherche :

```java
nav.getSearch().setFieldVisible(true);
nav.getSearch().setPlaceholder("Recherche");
```

Au fur et à mesure que l'utilisateur tape, la navigation filtre les items par label, ouvre tout groupe contenant une correspondance et affiche un message vide lorsque rien ne correspond. Les raccourcis épinglés restent visibles lors de la recherche, de sorte que les favoris de l'utilisateur demeurent à un clic, même au milieu du filtrage.

<ComponentDemo
path='/webforj/appnavsearch/Dashboard'
frame='desktop'
files={[
  'src/main/java/com/webforj/samples/views/appnav/AppNavSearchView.java',
  'src/main/java/com/webforj/samples/views/appnav/AppNavSearchPageView.java',
]}
/>

### Message vide {#search-empty-message}

Définissez le message affiché lorsqu'une recherche ne retourne aucun résultat. Le texte brut est rendu comme du texte :

```java
nav.getSearch().setEmptyMessage("Aucun item trouvé");
```

### Piloter la recherche depuis votre propre champ {#custom-search-box}

Masquez le champ intégré et alimenter le filtre depuis une entrée de votre propre. Poussez le terme actuel via `setTerm` :

```java
nav.getSearch().setFieldVisible(false);

myField.onModify(event -> nav.getSearch().setTerm(event.getText()));
```

Pour réagir à ce que l'utilisateur tape dans le champ intégré, écoutez l'événement de recherche :

```java
nav.onSearch(event -> log(event.getTerm()));
```

## Stylisation `AppNavItem` {#styling-appnavitem}

<TableBuilder name="AppNavItem" />
