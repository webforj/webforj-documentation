---
title: AppNav
sidebar_position: 6
sidebar_class_name: new-content
description: >-
  Build hierarchical side navigation menus with AppNav and AppNavItem, linking
  to routes, registered views, or external URLs.
_i18n_hash: c4ad786639b189058a38c808bd99ec8b
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-app-nav" />
<DocChip chip='since' label='24.12' />
<JavadocLink type="appnav" location="com/webforj/component/layout/appnav/AppNav" top='true'/>

Le composant `AppNav` crée un menu de navigation latéral à partir des entrées `AppNavItem`. Les éléments peuvent se lier à des vues internes ou des ressources externes, s'emboîter sous des éléments parents pour former des menus hiérarchiques, et porter des icônes, des badges ou d'autres composants pour donner aux utilisateurs plus de contexte d'un coup d'œil.

<!-- INTRO_END -->

## Ajout et emboîtement d'éléments <DocChip chip="name" label="dwc-app-nav-item" /> {#adding-and-nesting-items}

Les instances `AppNavItem` sont utilisées pour peupler la structure `AppNav`. Ces éléments peuvent être des liens simples ou des en-têtes de groupe imbriqués contenant des éléments enfants. Les en-têtes de groupe sans liens agissent comme des conteneurs extensibles.

Utilisez `addItem()` pour inclure des éléments dans la navigation :

```java
AppNavItem dashboard = new AppNavItem("Dashboard", "/dashboard");
AppNavItem admin = new AppNavItem("Admin");
admin.addItem(new AppNavItem("Users", "/admin/users"));
admin.addItem(new AppNavItem("Settings", "/admin/settings"));

AppNav nav = new AppNav();
nav.addItem(dashboard);
nav.addItem(admin);
```

:::tip Lien vers des éléments de groupe
Les éléments de niveau supérieur dans un arbre de navigation sont généralement destinés à être extensibles - pas des liens cliquables. Définir un `path` sur de tels éléments peut confondre les utilisateurs qui s'attendent à ce qu'ils révèlent des sous-éléments au lieu de naviguer ailleurs.

Si vous souhaitez que l'en-tête de groupe déclenche une action personnalisée (comme l'ouverture de documents externes), laissez le chemin du groupe vide et ajoutez plutôt un contrôle interactif comme un [`IconButton`](./icon#icon-buttons) dans le suffixe de l'élément. Cela maintient l'UX cohérente et propre.
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

## Lien vers des éléments {#linking-items}

Chaque `AppNavItem` peut naviguer vers une vue interne ou un lien externe. Vous pouvez définir cela en utilisant des chemins statiques ou des classes de vue enregistrées.

### Chemins statiques {#static-paths}

Utilisez des chemins de chaînes pour définir des liens directement :

```java
AppNavItem docs = new AppNavItem("Docs", "/docs");
AppNavItem help = new AppNavItem("Help", "https://support.example.com");
```

### Vues enregistrées {#registered-views}

Si vos vues sont enregistrées auprès du [routeur](../routing/overview), vous pouvez passer la classe au lieu d'une URL codée en dur :

```java
AppNavItem settings = new AppNavItem("Settings", SettingsView.class);
```

Si votre route annotée prend en charge les [paramètres de route](../routing/route-patterns#named-parameters), vous pouvez également passer un `ParametersBag` :

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

## Comportement cible {#target-behavior}

Contrôlez la façon dont les liens s'ouvrent en utilisant `setTarget()`. Cela est particulièrement utile pour les liens externes ou les vues qui s'ouvrent dans une nouvelle fenêtre.

- **`SELF`** (par défaut) : Ouvre dans la vue actuelle.
- **`BLANK`** : Ouvre dans un nouvel onglet ou une nouvelle fenêtre.
- **`PARENT`** : Ouvre dans le contexte de navigation parent.
- **`TOP`** : Ouvre dans le contexte de navigation de niveau supérieur.

```java
AppNavItem help = new AppNavItem("Help", "https://support.example.com");
help.setTarget(AppNavItem.NavigationTarget.BLANK);
```

## Préfixe et suffixe {#prefix-and-suffix}

`AppNavItem` prend en charge les composants de préfixe et de suffixe. Utilisez-les pour fournir une clarté visuelle avec des icônes, des badges ou des boutons.

- **Préfixe** : apparaît avant l'étiquette, utile pour des icônes.
- **Suffixe** : apparaît après l'étiquette, idéal pour des badges ou des actions.

```java
AppNavItem notifications = new AppNavItem("Alerts");
notifications.setPrefixComponent(TablerIcon.create("alert"));
notifications.setSuffixComponent(TablerIcon.create("link"));
```

## Groupes auto-ouverts {#auto-opening-groups}

Utilisez `setAutoOpen(true)` sur le composant `AppNav` pour automatiquement étendre les groupes imbriqués lorsque l'application est actualisée.

```java
nav.setAutoOpen(true);
```

## Étiquettes de section <DocChip chip='since' label='26.02' /> <DocChip chip="name" label="dwc-app-nav-label" /> {#section-labels}

`AppNavLabel` est un en-tête non interactif qui titre une série d'éléments. Une étiquette s'applique à chaque élément qui la suit, jusqu'à la prochaine étiquette ou la fin du menu, ce qui permet à une longue liste d'éléments de niveau supérieur d'être lue comme quelques groupes nommés sans les imbriquer.

Les étiquettes sont ajoutées avec `add()` plutôt qu'avec `addItem()`, et l'ordre des appels définit les sections :

```java
AppNav nav = new AppNav();
nav.addItem(new AppNavItem("Dashboard", DashboardView.class, TablerIcon.create("layout-dashboard")));

nav.add(new AppNavLabel("Analytics"));
nav.addItem(new AppNavItem("Overview", OverviewView.class));
nav.addItem(new AppNavItem("Reports", ReportsView.class));

nav.add(new AppNavLabel("Other"));
nav.addItem(new AppNavItem("Settings", SettingsView.class));
```

Le nav cache automatiquement une étiquette lorsque sa section n'a pas d'éléments visibles, donc une étiquette disparaît quand une [recherche](#search) filtre ses éléments ou lorsque tous sont [épinglés](#pinning) en haut du menu.

### Préfixe et suffixe d'étiquette {#label-prefix-and-suffix}

Comme `AppNavItem`, une étiquette prend en charge des composants de préfixe et de suffixe. Passez un préfixe au constructeur, ou définissez l'un ou l'autre après :

```java
AppNavLabel analytics = new AppNavLabel("Analytics", TablerIcon.create("chart-pie"));
analytics.setSuffixComponent(new Badge().setText("2").setTheme(BadgeTheme.WARNING));

nav.add(analytics);
```

L'exemple ci-dessous regroupe un menu sous trois étiquettes, dont la première porte un préfixe [`Icon`](./icon) et un suffixe [`Badge`](./badge). Dashboard se trouve au-dessus de la première étiquette, donc il n'appartient à aucune section.

<ComponentDemo
path='/webforj/appnavlabel/Dashboard'
frame='desktop'
files={[
  'src/main/java/com/webforj/samples/views/appnav/AppNavLabelView.java',
  'src/main/java/com/webforj/samples/views/appnav/AppNavLabelPageView.java',
]}
/>

## Épinglage <DocChip chip='since' label='26.01' /> {#pinning}

L'épinglage permet à un utilisateur de soulever les éléments qu'il atteint le plus dans un groupe en haut de la navigation, de sorte qu'un menu profond maintienne une courte liste de favoris en un clic. Il est désactivé par défaut. Activez-le via la configuration d'épinglage :

```java
AppNav nav = new AppNav();
nav.getPinning().setEnabled(true);
```

Une fois activé, chaque élément de feuille navigable affiche un bascule de pin. Le bascule est révélé au survol et avec le focus du clavier, de sorte qu'il reste accessible sans souris. L'activer déplace l'élément dans le groupe épinglé en haut du nav.

Quelques règles régissent ce qui peut être épinglé et comment le groupe se comporte :

- Seuls les éléments de feuille navigables peuvent être épinglés. Les en-têtes de groupe (éléments avec enfants) ne sont jamais épinglables.
- Le groupe épinglé apparaît uniquement lorsque quelque chose est épinglé, et disparaît à nouveau lorsque le dernier élément est désépinglé.
- Désépingler renvoie un élément à sa position d'origine exacte, y compris les éléments imbriqués à plusieurs niveaux à l'intérieur des groupes.
- L'élément est déplacé, et non copié, donc tout contenu de préfixe ou suffixe et tous les écouteurs qui y sont attachés continuent de fonctionner pendant qu'il se trouve dans le groupe épinglé.

La démo ci-dessous a l'épinglage activé avec un titre de groupe personnalisé et Dashboard épinglé au chargement. Survolez ou concentrez-vous sur un élément de feuille pour révéler son bascule de pin.

<ComponentDemo
path='/webforj/appnavpinning/Dashboard'
frame='desktop'
files={[
  'src/main/java/com/webforj/samples/views/appnav/AppNavPinningView.java',
  'src/main/java/com/webforj/samples/views/appnav/AppNavPinningPageView.java',
]}
/>

### Commencer un élément épinglé {#starting-an-item-pinned}

Démarrez un élément dans le groupe épinglé en définissant son état épinglé. Utilisez `isPinned()` pour lire l'état actuel.

```java
AppNavItem reports = new AppNavItem("Reports", "/reports");
reports.setPinned(true);
```

:::info L'épinglage doit être activé
`setPinned(true)` n'a d'effet que lorsque l'épinglage est activé sur l'`AppNav` via `getPinning().setEnabled(true)`. Sans cela, l'appel n'a aucun effet.
:::

### Titre du groupe épinglé {#pinned-group-title}

Le groupe épinglé est étiqueté `Pinned` par défaut. Changez-le pour l'adapter à votre application :

```java
nav.getPinning().setTitle("Favorites");
```

### Touches de pin {#pin-keys}

Chaque élément épinglé porte une clé qui l'identifie pour la persistance et pour l'[événement de pin](#reacting-to-pin-changes). Lorsque vous ne définissez pas une clé, la clé revient au chemin de l'élément, donc `getPinKey()` renvoie toujours une valeur utilisable.

```java
AppNavItem reports = new AppNavItem("Reports", "/reports");
reports.setPinKey("reports");
```

Définissez une clé explicite lorsque le chemin peut changer à l'exécution. Une clé stable garde une épingle associée au bon élément à travers les rechargements, même si son URL bouge.

### Sauvegarde automatique dans le stockage local {#autosave}

Les épingles ne vivent que pour la vue de la page actuelle à moins que vous ne les persistiez. L'enregistrement automatique est l'option la plus simple : il stocke l'ensemble des éléments épinglés dans le stockage local du navigateur et les restaure lors du rechargement. C'est désactivé par défaut. Cela nécessite un `id` (ou un nom) stable sur le composant pour la clé de stockage, et le constructeur `AppNav(String id)` est la façon pratique de définir un :

```java
AppNav nav = new AppNav("main-nav"); // donne à l'enregistrement automatique une clé de stockage stable
nav.getPinning().setAutosave(true);
```

:::info L'enregistrement automatique a besoin d'un id
Sans `id` (ou nom) sur le composant, l'enregistrement automatique ne fait rien en silence, puisqu'il n'a pas de clé stable sous laquelle stocker. La persistance se fait par navigateur, donc les épingles ne suivent pas un utilisateur sur un autre appareil ou navigateur.
:::

### Persistance personnalisée {#custom-persistence}

Pour une persistance que vous contrôlez, par exemple par utilisateur sur le serveur, désactivez l'enregistrement automatique et gérez-le vous-même via l'[événement de pin](#reacting-to-pin-changes) et `setPinned` :

```java
nav.getPinning().setAutosave(false);

// persist the current set of pinned keys whenever it changes
nav.onPin(event -> savePins(event.getKeys()));

// on load, restore each saved key
restoredKeys.forEach(key -> findItem(key).setPinned(true));
```

### Réaction aux modifications de pin {#reacting-to-pin-changes}

L'événement de pin se déclenche chaque fois qu'un élément est épinglé ou désépinglé. Il transporte l'élément qui a changé, sa clé, le nouvel état épinglé et l'ensemble complet des clés épinglées dans l'ordre :

```java
nav.onPin(event -> {
  AppNavItem item = event.getItem(); // l'élément qui a changé, ou null s'il n'est plus dans la navigation
  boolean pinned = event.isPinned();
  String key = event.getKey();
  List<String> all = event.getKeys(); // chaque clé épinglée, dans l'ordre épinglé
});
```

`getItem()` résout l'élément en faisant correspondre sa clé d'épingle, et renvoie `null` lorsque l'élément ne fait plus partie de la navigation.

### Icônes d'épingle {#pin-icons}

Le bascule utilise l'icône intégrée `dwc:pin` lorsque l'élément est désépinglé et `dwc:pinned-off` lorsque c'est épinglé. Échangez avec votre propre icône via `setUnpinnedIcon` et `setPinnedIcon`, qui acceptent toute `IconDefinition` :

```java
nav.getPinning()
   .setUnpinnedIcon(TablerIcon.create("pin"))
   .setPinnedIcon(TablerIcon.create("pinned-off"));
```

### Bascule de pin sur les écrans tactiles {#pin-toggle-on-touchscreens}

Les écrans tactiles n'ont pas de survol pour révéler le pin, donc le bascule est caché par défaut. Gardez-le visible et cliquable sur les écrans tactiles avec `setTouchVisible(true)` :

```java
nav.getPinning().setTouchVisible(true);
```

## Recherche <DocChip chip='since' label='26.01' /> {#search}

Le champ de recherche filtre le menu par étiquette d'élément au fur et à mesure que l'utilisateur tape. Il est désactivé par défaut. Vous pouvez l'afficher et lui donner un espace réservé via la configuration de recherche :

```java
nav.getSearch().setFieldVisible(true);
nav.getSearch().setPlaceholder("Search");
```

À mesure que l'utilisateur tape, le nav filtre les éléments par étiquette, ouvre tout groupe qui contient une correspondance et affiche un message vide lorsque rien ne correspond. Les raccourcis épinglés restent visibles pendant la recherche, donc les favoris d'un utilisateur restent accessibles en un clic même pendant le filtrage.

<ComponentDemo
path='/webforj/appnavsearch/Dashboard'
frame='desktop'
files={[
  'src/main/java/com/webforj/samples/views/appnav/AppNavSearchView.java',
  'src/main/java/com/webforj/samples/views/appnav/AppNavSearchPageView.java',
]}
/>

### Message vide {#search-empty-message}

Définissez le message affiché lorsqu'une recherche ne retourne aucun résultat. Le texte brut est rendu en tant que texte :

```java
nav.getSearch().setEmptyMessage("No items found");
```

### Piloter la recherche à partir de votre propre champ {#custom-search-box}

Cachez le champ intégré et alimentez le filtre à partir d'une entrée de votre choix. Poussez le terme actuel via `setTerm` :

```java
nav.getSearch().setFieldVisible(false);

myField.onModify(event -> nav.getSearch().setTerm(event.getText()));
```

Pour réagir à ce que l'utilisateur tape dans le champ intégré, écoutez l'événement de recherche :

```java
nav.onSearch(event -> log(event.getTerm()));
```

## Styliser `AppNavItem` {#styling-appnavitem}

<TableBuilder name="AppNavItem" />
