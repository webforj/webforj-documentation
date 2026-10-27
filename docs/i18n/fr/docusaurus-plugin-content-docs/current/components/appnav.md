---
title: AppNav
sidebar_position: 6
sidebar_class_name: new-content
description: >-
  Build hierarchical side navigation menus with AppNav and AppNavItem, linking
  to routes, registered views, or external URLs.
_i18n_hash: 4f5eaa3e9738a7f959de22544589d240
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-app-nav" />
<DocChip chip="name" label="dwc-app-nav-item" />
<DocChip chip="name" label="dwc-app-nav-label" />
<DocChip chip='since' label='24.12' />
<JavadocLink type="appnav" location="com/webforj/component/layout/appnav/AppNav" top='true'/>

Le composant `AppNav` crée un menu de navigation latéral à partir des entrées `AppNavItem`. Les éléments peuvent lier à des vues internes ou à des ressources externes, s'imbriquer sous des éléments parents pour former des menus hiérarchiques, et porter des icônes, des badges ou d'autres composants pour donner aux utilisateurs plus de contexte d'un coup d'œil.

<!-- INTRO_END -->

## Ajout et imbriquement d'éléments {#adding-and-nesting-items}

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

:::tip Lien vers les éléments de groupe
Les éléments de niveau supérieur dans un arbre de navigation sont généralement destinés à être extensibles - pas des liens cliquables. Définir un `path` sur de tels éléments peut confondre les utilisateurs qui s'attendent à ce qu'ils révèlent des sous-éléments au lieu de naviguer ailleurs.

Si vous souhaitez que l'en-tête de groupe déclenche une action personnalisée (comme ouvrir des documents externes), laissez le chemin du groupe vide et ajoutez plutôt un contrôle interactif comme un [`IconButton`](./icon#icon-buttons) à la suffixe de l'élément. Cela maintient l’UX cohérente et propre.
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

Utilisez des chemins en chaîne pour définir des liens directement :

```java
AppNavItem docs = new AppNavItem("Docs", "/docs");
AppNavItem help = new AppNavItem("Help", "https://support.example.com");
```

### Vues enregistrées {#registered-views}

Si vos vues sont enregistrées avec le [routeur](../routing/overview), vous pouvez passer la classe au lieu d'une URL codée en dur :

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

## Comportement de cible {#target-behavior}

Contrôlez comment les liens s'ouvrent en utilisant `setTarget()`. Ceci est particulièrement utile pour les liens externes ou les vues pop-out.

- **`SELF`** (par défaut) : S'ouvre dans la vue actuelle.
- **`BLANK`** : S'ouvre dans un nouvel onglet ou une nouvelle fenêtre.
- **`PARENT`** : S'ouvre dans le contexte de navigation parent.
- **`TOP`** : S'ouvre dans le contexte de navigation de premier niveau.

```java
AppNavItem help = new AppNavItem("Help", "https://support.example.com");
help.setTarget(AppNavItem.NavigationTarget.BLANK);
```

## Préfixe et suffixe {#prefix-and-suffix}

`AppNavItem` prend en charge des composants de préfixe et de suffixe. Utilisez ceux-ci pour fournir une clarté visuelle avec des icônes, des badges ou des boutons.

- **Préfixe** : apparaît avant l'étiquette, utile pour les icônes.
- **Suffixe** : apparaît après l'étiquette, idéal pour les badges ou les actions.

```java
AppNavItem notifications = new AppNavItem("Alerts");
notifications.setPrefixComponent(TablerIcon.create("alert"));
notifications.setSuffixComponent(TablerIcon.create("link"));
```

## Groupes à ouverture automatique {#auto-opening-groups}

Utilisez `setAutoOpen(true)` sur le composant `AppNav` pour étendre automatiquement les groupes imbriqués lorsque l'application est rafraîchie.

```java
nav.setAutoOpen(true);
```

## Étiquettes de section <DocChip chip='since' label='26.02' /> {#section-labels}

`AppNavLabel` est un en-tête non interactif qui titre une série d'éléments. Une étiquette s'applique à chaque élément qui la suit, jusqu'à la prochaine étiquette ou à la fin du menu, ce qui permet à une longue liste d'éléments de niveau supérieur d'être lue comme quelques groupes nommés sans les imbriquer.

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

Le nav cache automatiquement une étiquette lorsque sa section n'a pas d'éléments visibles, donc une étiquette disparaît lorsqu'une [recherche](#search) filtre ses éléments ou lorsque tous sont [pinnés](#pinning) en haut du menu.

### Préfixe et suffixe d'étiquette {#label-prefix-and-suffix}

Comme `AppNavItem`, une étiquette prend en charge des composants de préfixe et de suffixe. Passez un préfixe au constructeur, ou définissez l'un ou l'autre par la suite :

```java
AppNavLabel analytics = new AppNavLabel("Analytics", TablerIcon.create("chart-pie"));
analytics.setSuffixComponent(new Badge().setText("2").setTheme(BadgeTheme.WARNING));

nav.add(analytics);
```

L'exemple ci-dessous groupe un menu sous trois étiquettes, dont la première porte un préfixe [`Icon`](./icon) et un suffixe [`Badge`](./badge). Dashboard se trouve au-dessus de la première étiquette, donc il n'appartient à aucune section.

<ComponentDemo
path='/webforj/appnavlabel/Dashboard'
frame='desktop'
files={[
  'src/main/java/com/webforj/samples/views/appnav/AppNavLabelView.java',
  'src/main/java/com/webforj/samples/views/appnav/AppNavLabelPageView.java',
]}
/>

## Épinglage <DocChip chip='since' label='26.01' /> {#pinning}

L'épinglage permet à un utilisateur de soulever les éléments qu'il recherche le plus dans un groupe en haut de la navigation, de sorte qu'un menu profond garde toujours une courte liste de favoris à portée d'un clic. C'est désactivé par défaut. Activez-le via la configuration d'épinglage :

```java
AppNav nav = new AppNav();
nav.getPinning().setEnabled(true);
```

Une fois activé, chaque élément de feuille navigable affiche un basculement d'épinglage. Le basculement est révélé au survol et au focus clavier, il reste donc accessible sans souris. L'activer déplace l'élément dans le groupe épinglé en haut du nav.

Quelques règles régissent ce qui peut être épinglé et comment le groupe se comporte :

- Seuls les éléments de feuille navigables sont épinglables. Les en-têtes de groupe (éléments avec enfants) ne sont jamais épinglables.
- Le groupe épinglé n'apparaît que lorsqu'un élément est épinglé, et disparaît à nouveau lorsque le dernier élément est désépinglé.
- Désepingler renvoie un élément à sa position d'origine exacte, y compris les éléments imbriqués à plusieurs niveaux à l'intérieur des groupes.
- L'élément est déplacé, pas copié, donc tout contenu de préfixe ou de suffixe et tous les auditeurs attachés continuent de fonctionner tant qu'il est dans le groupe épinglé.

La démo ci-dessous a l'épinglage activé avec un titre de groupe personnalisé et Dashboard épinglé au chargement. Survolez ou concentrez une feuille d'éléments pour révéler son basculement d'épinglage.

<ComponentDemo
path='/webforj/appnavpinning/Dashboard'
frame='desktop'
files={[
  'src/main/java/com/webforj/samples/views/appnav/AppNavPinningView.java',
  'src/main/java/com/webforj/samples/views/appnav/AppNavPinningPageView.java',
]}
/>

### Démarrage d'un élément épinglé {#starting-an-item-pinned}

Commencez un élément dans le groupe épinglé en définissant son état épinglé. Utilisez `isPinned()` pour lire l'état actuel.

```java
AppNavItem reports = new AppNavItem("Reports", "/reports");
reports.setPinned(true);
```

:::info L'épinglage doit être activé
`setPinned(true)` n'aura d'effet que lorsque l'épinglage est activé sur le `AppNav` via `getPinning().setEnabled(true)`. Sans cela, l'appel n'a aucun effet.
:::

### Titre du groupe épinglé {#pinned-group-title}

Le groupe épinglé est étiqueté `Pinned` par défaut. Changez-le pour convenir à votre application :

```java
nav.getPinning().setTitle("Favoris");
```

### Clés d'épinglage {#pin-keys}

Chaque élément épinglable possède une clé qui l'identifie pour la persistance et pour l'[événement d'épinglage](#reacting-to-pin-changes). Lorsque vous n'en définissez pas, la clé revient au chemin de l'élément, donc `getPinKey()` renvoie toujours une valeur utilisable.

```java
AppNavItem reports = new AppNavItem("Reports", "/reports");
reports.setPinKey("reports");
```

Définissez une clé explicite lorsque le chemin peut changer à l'exécution. Une clé stable conserve une épingle assortie au bon élément à travers les rechargements, même si son URL change.

### Sauvegarde automatique dans le stockage local {#autosave}

Les épingles ne vivent que pour la vue de page actuelle à moins que vous ne les persistiez. La sauvegarde automatique est l'option la plus simple : elle stocke l'ensemble des éléments épinglés dans le stockage local du navigateur et les restaure lors du rechargement. C'est désactivé par défaut. Elle nécessite un `id` (ou un nom) stable sur le composant pour la clé de stockage, et le constructeur `AppNav(String id)` est le moyen pratique de définir un :

```java
AppNav nav = new AppNav("main-nav"); // donne à la sauvegarde automatique une clé de stockage stable
nav.getPinning().setAutosave(true);
```

:::info La sauvegarde automatique nécessite un id
Sans `id` (ou nom) sur le composant, la sauvegarde automatique ne fait rien silencieusement, car elle n'a pas de clé stable à stocker. La persistance est par navigateur, donc les épingles ne suivent pas un utilisateur vers un autre appareil ou navigateur.
:::

### Persistance personnalisée {#custom-persistence}

Pour une persistance que vous contrôlez, par exemple par utilisateur sur le serveur, désactivez la sauvegarde automatique et gérez-la vous-même à travers l'[événement d'épinglage](#reacting-to-pin-changes) et `setPinned` :

```java
nav.getPinning().setAutosave(false);

// persist the current set of pinned keys whenever it changes
nav.onPin(event -> savePins(event.getKeys()));

// on load, restore each saved key
restoredKeys.forEach(key -> findItem(key).setPinned(true));
```

### Réaction aux changements d'épinglage {#reacting-to-pin-changes}

L'événement d'épinglage se déclenche chaque fois qu'un élément est épinglé ou désépinglé. Il contient l'élément qui a changé, sa clé, le nouvel état épinglé, et l'ensemble complet ordonné des clés épinglées :

```java
nav.onPin(event -> {
  AppNavItem item = event.getItem(); // l'élément qui a changé, ou null s'il n'est plus dans la navigation
  boolean pinned = event.isPinned();
  String key = event.getKey();
  List<String> all = event.getKeys(); // chaque clé épinglée, dans l'ordre d'épinglage
});
```

`getItem()` résout l'élément en faisant correspondre sa clé d'épinglage, et renvoie `null` lorsque l'élément ne fait plus partie de la navigation.

### Icônes d'épinglage {#pin-icons}

Le basculement utilise l'icône intégrée `dwc:pin` pendant qu'un élément est désépinglé et `dwc:pinned-off` pendant qu'il est épinglé. Remplacez-le par le vôtre via `setUnpinnedIcon` et `setPinnedIcon`, qui acceptent toute `IconDefinition` :

```java
nav.getPinning()
   .setUnpinnedIcon(TablerIcon.create("pin"))
   .setPinnedIcon(TablerIcon.create("pinned-off"));
```

### Basculement d'épinglage sur les écrans tactiles {#pin-toggle-on-touchscreens}

Les écrans tactiles n'ont pas de survol pour révéler l'épinglage, donc le basculement est caché par défaut. Gardez-le visible et tappable sur les écrans tactiles avec `setTouchVisible(true)` :

```java
nav.getPinning().setTouchVisible(true);
```

## Recherche <DocChip chip='since' label='26.01' /> {#search}

Le champ de recherche filtre le menu par étiquette d'élément au fur et à mesure que l'utilisateur tape. C'est désactivé par défaut. Vous pouvez l'afficher et lui donner un espace réservé via la configuration de recherche :

```java
nav.getSearch().setFieldVisible(true);
nav.getSearch().setPlaceholder("Recherche");
```

Au fur et à mesure que l'utilisateur tape, le nav filtre les éléments par étiquette, ouvre tout groupe contenant une correspondance et montre un message vide lorsque rien ne correspond. Les raccourcis épinglés restent visibles pendant la recherche, donc les favoris d'un utilisateur restent à un clic même en plein filtre.

<ComponentDemo
path='/webforj/appnavsearch/Dashboard'
frame='desktop'
files={[
  'src/main/java/com/webforj/samples/views/appnav/AppNavSearchView.java',
  'src/main/java/com/webforj/samples/views/appnav/AppNavSearchPageView.java',
]}
/>

### Message vide {#search-empty-message}

Définissez le message affiché lorsqu'une recherche ne retourne aucun résultat. Le texte brut est rendu comme texte :

```java
nav.getSearch().setEmptyMessage("Aucun élément trouvé");
```

### Conduire la recherche depuis votre propre champ {#custom-search-box}

Cachez le champ intégré et alimentez le filtre depuis une entrée de votre choix. Poussez le terme actuel via `setTerm` :

```java
nav.getSearch().setFieldVisible(false);

myField.onModify(event -> nav.getSearch().setTerm(event.getText()));
```

Pour réagir à ce que l'utilisateur tape dans le champ intégré, écoutez l'événement de recherche :

```java
nav.onSearch(event -> log(event.getTerm()));
```

## Style de `AppNavItem` {#styling-appnavitem}

<TableBuilder name="AppNavItem" />
