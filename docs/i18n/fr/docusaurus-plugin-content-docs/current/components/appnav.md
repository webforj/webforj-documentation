---
title: AppNav
sidebar_position: 6
_i18n_hash: d4756db6bed23bc005fbcd2be222b4ea
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-app-nav" />
<DocChip chip="name" label="dwc-app-nav-item" />
<DocChip chip='since' label='24.12' />
<JavadocLink type="appnav" location="com/webforj/component/appnav/AppNav" top='true'/> 

Le composant `AppNav` crée un menu de navigation latéral à partir des éléments `AppNavItem`. Les éléments peuvent lier à des vues internes ou des ressources externes, s'emboîter sous des éléments parents pour former des menus hiérarchiques, et porter des icônes, des badges ou d'autres composants pour donner plus de contexte aux utilisateurs d'un coup d'œil.

<!-- INTRO_END -->

## Ajout et imbriquement d'éléments {#adding-and-nesting-items}

Les instances `AppNavItem` sont utilisées pour peupler la structure de `AppNav`. Ces éléments peuvent être des liens simples ou des en-têtes de groupe imbriqués contenant des éléments enfants. Les en-têtes de groupe sans liens agissent comme des conteneurs extensibles.

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

:::tip Lien des Éléments de Groupe
Les éléments de premier niveau dans un arbre de navigation doivent généralement être extensibles - et non des liens cliquables. Définir un `path` sur de tels éléments peut déconcerter les utilisateurs qui s'attendent à ce qu'ils révèlent des sous-éléments plutôt qu'à naviguer ailleurs.

Si vous voulez que l'en-tête de groupe déclenche une action personnalisée (comme ouvrir des docs externes), gardez le chemin du groupe vide et ajoutez plutôt un contrôle interactif comme un [`IconButton`](./icon#icon-buttons) au suffixe de l'élément. Cela maintient une expérience utilisateur cohérente et propre.
:::

<!--vale off-->
<AppLayoutViewer 
path='/webforj/appnav/Social?'  
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/appnav/AppNavView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/appnav/AppNavPageView.java']}
/>
<!--vale on-->

## Lien des Éléments {#linking-items}

Chaque `AppNavItem` peut naviguer vers une vue interne ou un lien externe. Vous pouvez définir cela en utilisant des chemins statiques ou des classes de vue enregistrées.

### Chemins statiques {#static-paths}

Utilisez des chemins sous forme de chaîne pour définir des liens directement :

```java
AppNavItem docs = new AppNavItem("Docs", "/docs");
AppNavItem help = new AppNavItem("Aide", "https://support.example.com");
```

### Vues enregistrées {#registered-views}

Si vos vues sont enregistrées auprès du [routeur](../routing/overview), vous pouvez passer la classe au lieu d'une URL codée en dur :

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

## Comportement de la cible {#target-behavior}

Contrôlez comment les liens s'ouvrent en utilisant `setTarget()`. C'est particulièrement utile pour les liens externes ou les vues pop-out.

- **`SELF`** (par défaut) : Ouvre dans la vue actuelle.
- **`BLANK`** : Ouvre dans un nouvel onglet ou une nouvelle fenêtre.
- **`PARENT`** : Ouvre dans le contexte de navigation parent.
- **`TOP`** : Ouvre dans le contexte de navigation au niveau supérieur.

```java
AppNavItem help = new AppNavItem("Aide", "https://support.example.com");
help.setTarget(AppNavItem.NavigationTarget.BLANK);
```

## Préfixe et suffixe {#prefix-and-suffix}

`AppNavItem` prend en charge les composants de préfixe et de suffixe. Utilisez-les pour offrir une clarté visuelle avec des icônes, des badges ou des boutons.

- **Préfixe** : apparaît avant l'étiquette, utile pour les icônes.
- **Suffixe** : apparaît après l'étiquette, excellent pour les badges ou les actions.

```java
AppNavItem notifications = new AppNavItem("Alertes");
notifications.setPrefixComponent(TablerIcon.create("alert");
notifications.setSuffixComponent(TablerIcon.create("link"));
```

## Groupes s'ouvrant automatiquement {#auto-opening-groups}

Utilisez `setAutoOpen(true)` sur le composant `AppNav` pour étendre automatiquement les groupes imbriqués lors du rafraîchissement de l'application.

```java
nav.setAutoOpen(true);
```

## Styler `AppNavItem` {#styling-appnavitem}

<TableBuilder name="AppNavItem" />
