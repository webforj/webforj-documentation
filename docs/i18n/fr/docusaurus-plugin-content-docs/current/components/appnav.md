---
title: AppNav
sidebar_position: 6
sidebar_class_name: new-content
description: >-
  Build hierarchical side navigation menus with AppNav and AppNavItem, linking
  to routes, registered views, or external URLs.
_i18n_hash: afb61d8d44c3f5dcb03f533954baafc1
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-app-nav" />
<DocChip chip="name" label="dwc-app-nav-item" />
<DocChip chip="name" label="dwc-app-nav-label" />
<DocChip chip='since' label='24.12' />
<JavadocLink type="appnav" location="com/webforj/component/appnav/AppNav" top='true'/>

Le composant `AppNav` crée un menu de navigation latéral à partir d'entrées `AppNavItem`. Les éléments peuvent lier à des vues internes ou à des ressources externes, s'imbriquer sous des éléments parents pour former des menus hiérarchiques, et porter des icônes, des badges ou d'autres composants pour donner aux utilisateurs plus de contexte d'un coup d'œil.

<!-- INTRO_END -->

## Ajouter et imbriquer des éléments {#adding-and-nesting-items}

Les instances `AppNavItem` sont utilisées pour peupler la structure `AppNav`. Ces éléments peuvent être des liens simples ou des en-têtes de groupe imbriqués contenant des éléments enfants. Les en-têtes de groupe sans liens agissent comme des conteneurs extensibles.

Utilisez `addItem()` pour inclure des éléments dans la navigation :

```java
AppNavItem dashboard = new AppNavItem("Tableau de bord", "/dashboard");
AppNavItem admin = new AppNavItem("Admin");
admin.addItem(new AppNavItem("Utilisateurs", "/admin/users"));
admin.addItem(new AppNavItem("Paramètres", "/admin/settings"));

AppNav nav = new AppNav();
nav.addItem(dashboard);
nav.addItem(admin);
```

:::tip Lier des éléments de groupe
Les éléments de premier niveau dans un arbre de navigation sont généralement destinés à être extensibles - pas des liens cliquables. Définir un `path` sur ces éléments peut troubler les utilisateurs qui s'attendent à ce qu'ils révèlent des sous-éléments au lieu de naviguer ailleurs.

Si vous souhaitez que l'en-tête de groupe déclenche une action personnalisée (comme ouvrir des docs externes), laissez le chemin du groupe vide et ajoutez plutôt un contrôle interactif comme un [`IconButton`](./icon#icon-buttons) à la suffixe de l'élément. Cela maintient l'expérience utilisateur cohérente et propre.
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

## Lier des éléments {#linking-items}

Chaque `AppNavItem` peut naviguer vers une vue interne ou un lien externe. Vous pouvez définir cela en utilisant des chemins statiques ou des classes de vues enregistrées.

### Chemins statiques {#static-paths}

Utilisez des chemins de chaînes pour définir des liens directement :

```java
AppNavItem docs = new AppNavItem("Docs", "/docs");
AppNavItem help = new AppNavItem("Aide", "https://support.example.com");
```

### Vues enregistrées {#registered-views}

Si vos vues sont enregistrées avec le [routeur](../routing/overview), vous pouvez passer la classe au lieu d'une URL codée en dur :

```java
AppNavItem settings = new AppNavItem("Paramètres", SettingsView.class);
```

Si votre route annotée prend en charge [les paramètres de route](../routing/route-patterns#named-parameters), vous pouvez également passer un `ParametersBag` :

```java
ParametersBag params = ParametersBag.of("id=123");
AppNavItem advanced = new AppNavItem("Utilisateur", UserView.class, params);
```

### Avec des paramètres de requête {#with-query-parameters}

Passez un `ParametersBag` pour inclure des chaînes de requête :

```java
ParametersBag params = ParametersBag.of("param1=value1&param2=value2");
AppNavItem advanced = new AppNavItem("Avancé", SettingsView.class, params);
advanced.setQueryParameters(params);
```

## Comportement cible {#target-behavior}

Contrôlez comment les liens s'ouvrent en utilisant `setTarget()`. C'est particulièrement utile pour les liens externes ou les vues pop-out.

- **`SELF`** (par défaut) : S'ouvre dans la vue actuelle.
- **`BLANK`** : S'ouvre dans un nouvel onglet ou une nouvelle fenêtre.
- **`PARENT`** : S'ouvre dans le contexte de navigation parent.
- **`TOP`** : S'ouvre dans le contexte de navigation de niveau supérieur.

```java
AppNavItem help = new AppNavItem("Aide", "https://support.example.com");
help.setTarget(AppNavItem.NavigationTarget.BLANK);
```

## Préfixe et suffixe {#prefix-and-suffix}

`AppNavItem` prend en charge des composants de préfixe et de suffixe. Utilisez-les pour fournir une clarté visuelle avec des icônes, des badges ou des boutons.

- **Préfixe** : apparaît avant le label, utile pour les icônes.
- **Suffixe** : apparaît après le label, idéal pour des badges ou des actions.

```java
AppNavItem notifications = new AppNavItem("Alertes");
notifications.setPrefixComponent(TablerIcon.create("alert"));
notifications.setSuffixComponent(TablerIcon.create("link"));
```

## Groupes auto-ouverts {#auto-opening-groups}

Utilisez `setAutoOpen(true)` sur le composant `AppNav` pour étendre automatiquement les groupes imbriqués lorsque l'application est actualisée.

```java
nav.setAutoOpen(true);
```

## Étiquettes de section <DocChip chip='since' label='26.02' /> {#section-labels}

`AppNavLabel` est un en-tête non interactif qui titre une série d'éléments. Une étiquette s'applique à chaque élément qui la suit, jusqu'à la prochaine étiquette ou la fin du menu, ce qui permet à une longue liste d'éléments de premier niveau de se lire comme quelques groupes nommés sans les imbriquer.

Les étiquettes sont ajoutées avec `add()` plutôt qu'avec `addItem()`, et l'ordre des appels définit les sections :

```java
AppNav nav = new AppNav();
nav.addItem(new AppNavItem("Tableau de bord", DashboardView.class, TablerIcon.create("layout-dashboard")));

nav.add(new AppNavLabel("Analytique"));
nav.addItem(new AppNavItem("Vue d'ensemble", OverviewView.class));
nav.addItem(new AppNavItem("Rapports", ReportsView.class));

nav.add(new AppNavLabel("Autre"));
nav.addItem(new AppNavItem("Paramètres", SettingsView.class));
```

La navigation cache une étiquette automatiquement lorsque sa section n'a pas d'éléments visibles, donc une étiquette disparaît lorsqu'une [recherche](#search) filtre ses éléments ou lorsque tous sont [épinglés](#pinning) en haut du menu.

### Préfixe et suffixe de l'étiquette {#label-prefix-and-suffix}

Comme `AppNavItem`, une étiquette prend en charge des composants de préfixe et de suffixe. Passez un préfixe au constructeur, ou définissez-en un après :

```java
AppNavLabel analytics = new AppNavLabel("Analytique", TablerIcon.create("chart-pie"));
analytics.setSuffixComponent(new Badge().setText("2").setTheme(BadgeTheme.WARNING));

nav.add(analytics);
```

L'exemple ci-dessous groupe un menu sous trois étiquettes, dont la première porte un [`Icon`](./icon) comme préfixe et un [`Badge`](./badge) comme suffixe. Le tableau de bord se trouve au-dessus de la première étiquette, donc il n'appartient à aucune section.

<ComponentDemo
path='/webforj/appnavlabel/Dashboard'
frame='desktop'
files={[
  'src/main/java/com/webforj/samples/views/appnav/AppNavLabelView.java',
  'src/main/java/com/webforj/samples/views/appnav/AppNavLabelPageView.java',
]}
/>

## Épingler <DocChip chip='since' label='26.01' /> {#pinning}

L'épinglage permet à un utilisateur de soulever les éléments qu'il atteint le plus vers un groupe en haut de la navigation, de sorte qu'un menu profond garde une courte liste de favoris à un clic. Par défaut, c'est désactivé. Activez-le via la configuration d'épinglage :

```java
AppNav nav = new AppNav();
nav.getPinning().setEnabled(true);
```

Une fois activé, chaque élément feuille navigable montre un bascule de pin. Le bascule est révélé au survol et lors de la mise au point au clavier, afin qu'il reste accessible sans souris. L'activer déplace l'élément dans le groupe épinglé en haut de la navigation.

Quelques règles régissent ce qui peut être épinglé et comment le groupe se comporte :

- Seuls les éléments feuille navigables peuvent être épinglés. Les en-têtes de groupe (éléments avec enfants) ne sont jamais épinglables.
- Le groupe épinglé n'apparaît qu'une fois quelque chose épinglé, et disparaît à nouveau lorsque le dernier élément est désépinglé.
- Désépingler renvoie un élément à sa position d'origine exacte, y compris les éléments imbriqués à plusieurs niveaux à l'intérieur des groupes.
- L'élément est déplacé, pas copié, donc tout contenu de préfixe ou de suffixe et tous les écouteurs qui y sont attachés continuent de fonctionner tant qu'il est dans le groupe épinglé.

La démo ci-dessous a l'épinglage activé avec un titre de groupe personnalisé et le tableau de bord épinglé au chargement. Survolez ou focalisez un élément feuille pour révéler son bascule de pin.

<ComponentDemo
path='/webforj/appnavpinning/Dashboard'
frame='desktop'
files={[
  'src/main/java/com/webforj/samples/views/appnav/AppNavPinningView.java',
  'src/main/java/com/webforj/samples/views/appnav/AppNavPinningPageView.java',
]}
/>

### Démarrer un élément épinglé {#starting-an-item-pinned}

Démarrez un élément dans le groupe épinglé en définissant son état épinglé. Utilisez `isPinned()` pour lire l'état actuel.

```java
AppNavItem reports = new AppNavItem("Rapports", "/reports");
reports.setPinned(true);
```

:::info L'épinglage doit être activé
`setPinned(true)` n'a d'effet que lorsque l'épinglage est activé sur le `AppNav` via `getPinning().setEnabled(true)`. Sans cela, l'appel n'a aucun effet.
:::

### Titre du groupe épinglé {#pinned-group-title}

Le groupe épinglé est étiqueté `Épinglé` par défaut. Changez-le pour correspondre à votre application :

```java
nav.getPinning().setTitle("Favoris");
```

### Clés de pin {#pin-keys}

Chaque élément épinglable porte une clé qui l'identifie pour la persistance et pour l'[événement de pin](#reacting-to-pin-changes). Lorsque vous n'en définissez pas, la clé se rapporte au chemin de l'élément, donc `getPinKey()` retourne toujours une valeur utilisable.

```java
AppNavItem reports = new AppNavItem("Rapports", "/reports");
reports.setPinKey("reports");
```

Définissez une clé explicite lorsque le chemin peut changer à l'exécution. Une clé stable maintient un pin associé au bon élément lors des rechargements même si son URL change.

### Sauvegarde automatique dans le stockage local {#autosave}

Les épingles vivent uniquement pour la vue de la page actuelle, sauf si vous les persistez. La sauvegarde automatique est l'option la plus simple : elle stocke l'ensemble des éléments épinglés dans le stockage local du navigateur et les restaure au rechargement. Elle est désactivée par défaut. Elle nécessite un `id` stable (ou nom) sur le composant pour la clé de stockage, et le constructeur `AppNav(String id)` est le moyen pratique de définir un :

```java
AppNav nav = new AppNav("main-nav"); // donne à la sauvegarde automatique une clé de stockage stable
nav.getPinning().setAutosave(true);
```

:::info La sauvegarde automatique nécessite un id
Sans `id` (ou nom) sur le composant, la sauvegarde automatique ne fait rien silencieusement, car elle n'a pas de clé stable sous laquelle stocker. La persistance est par navigateur, donc les épingles ne suivent pas un utilisateur sur un autre appareil ou navigateur.
:::

### Persistance personnalisée {#custom-persistence}

Pour la persistance que vous contrôlez, par exemple par utilisateur sur le serveur, désactivez la sauvegarde automatique et gérez-la vous-même via l'[événement de pin](#reacting-to-pin-changes) et `setPinned` :

```java
nav.getPinning().setAutosave(false);

// persister l'ensemble actuel des clés épinglées chaque fois qu'il change
nav.onPin(event -> savePins(event.getKeys()));

// au chargement, restaurez chaque clé sauvegardée
restoredKeys.forEach(key -> findItem(key).setPinned(true));
```

### Réagir aux changements de pin {#reacting-to-pin-changes}

L'événement de pin se déclenche chaque fois qu'un élément est épinglé ou désépinglé. Il porte l'élément qui a changé, sa clé, le nouvel état épinglé et l'ensemble complet ordonné des clés épinglées :

```java
nav.onPin(event -> {
  AppNavItem item = event.getItem(); // l'élément qui a changé, ou null s'il n'est plus dans la navigation
  boolean pinned = event.isPinned();
  String key = event.getKey();
  List<String> all = event.getKeys(); // chaque clé épinglée, dans l'ordre épinglé
});
```

`getItem()` résout l'élément en faisant correspondre sa clé de pin, et retourne `null` lorsque l'élément ne fait plus partie de la navigation.

### Icônes de pin {#pin-icons}

Le bascule utilise l'icône intégrée `dwc:pin` lorsqu'un élément n'est pas épinglé et `dwc:pinned-off` lorsqu'il est épinglé. Remplacez le vôtre à travers `setUnpinnedIcon` et `setPinnedIcon`, qui acceptent n'importe quelle `IconDefinition` :

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

Le champ de recherche filtre le menu par label d'élément à mesure que l'utilisateur tape. Il est désactivé par défaut. Vous pouvez l'afficher et lui donner un texte de remplacement à travers la configuration de recherche :

```java
nav.getSearch().setFieldVisible(true);
nav.getSearch().setPlaceholder("Recherche");
```

À mesure que l'utilisateur tape, la navigation filtre les éléments par label, ouvre tout groupe contenant une correspondance, et montre un message vide lorsque rien ne correspond. Les raccourcis épinglés restent visibles pendant la recherche, donc les favoris d'un utilisateur demeurent à un clic même en milieu de filtrage.

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
nav.getSearch().setEmptyMessage("Aucun élément trouvé");
```

### Piloter la recherche à partir de votre propre champ {#custom-search-box}

Cachez le champ intégré et alimentez le filtre à partir d'une entrée de votre choix. Poussez le terme actuel dans `setTerm` :

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
