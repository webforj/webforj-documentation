---
title: AppNav
sidebar_position: 6
_i18n_hash: 47432ed72280efdc4d1b48e72d95b87d
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-app-nav" />
<DocChip chip="name" label="dwc-app-nav-item" />
<DocChip chip='since' label='24.12' />
<JavadocLink type="appnav" location="com/webforj/component/appnav/AppNav" top='true'/> 

Le composant `AppNav` de webforJ fournit un menu de navigation latéral flexible et organisé avec prise en charge des structures à la fois plates et hiérarchiques. Chaque entrée est un `AppNavItem`, qui peut représenter un lien simple ou un groupe contenant des sous-éléments. Les éléments peuvent être liés à des vues internes ou à des ressources externes, améliorés par des icônes, des badges ou d'autres composants.

## Ajout et imbrication des éléments {#adding-and-nesting-items}

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

:::tip Lien des Éléments de Groupe
Les éléments de niveau supérieur dans un arbre de navigation sont généralement destinés à être extensibles - pas des liens cliquables. Définir un `path` sur de tels éléments peut brouiller les utilisateurs qui s'attendent à ce qu'ils révèlent des sous-éléments plutôt que de naviguer ailleurs.

Si vous souhaitez que l'en-tête de groupe déclenche une action personnalisée (comme ouvrir des documents externes), gardez le chemin du groupe vide et ajoutez plutôt un contrôle interactif comme un [`IconButton`](./icon#icon-buttons) à la suffixe de l'élément. Cela maintient l'UX cohérente et claire.
:::

<AppLayoutViewer 
path='/webforj/appnav/Social?'  
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/appnav/AppNavView.java'
/>

## Lien des Éléments {#linking-items}

Chaque `AppNavItem` peut naviguer vers une vue interne ou un lien externe. Vous pouvez définir cela en utilisant des chemins statiques ou des classes de vue enregistrées.

### Chemins statiques {#static-paths}

Utilisez des chemins de chaîne pour définir des liens directement :

```java
AppNavItem docs = new AppNavItem("Docs", "/docs");
AppNavItem help = new AppNavItem("Help", "https://support.example.com");
```

### Vues enregistrées {#registered-views}

Si vos vues sont enregistrées auprès du [routeur](../routing/overview), vous pouvez passer la classe au lieu d'une URL codée en dur :

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

## Comportement cible {#target-behavior}

Contrôlez comment les liens s'ouvrent en utilisant `setTarget()`. Cela est particulièrement utile pour les liens externes ou les vues pop-up.

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

- **Préfixe** : apparaît avant l'étiquette, utile pour les icônes.
- **Suffixe** : apparaît après l'étiquette, idéal pour les badges ou les actions.

```java
AppNavItem notifications = new AppNavItem("Alerts");
notifications.setPrefixComponent(TablerIcon.create("alert");
notifications.setSuffixComponent(TablerIcon.create("link"));
```

## Groupes à ouverture automatique {#auto-opening-groups}

Utilisez `setAutoOpen(true)` sur le composant `AppNav` pour développer automatiquement les groupes imbriqués lorsque l'application est rafraîchie.

```java
nav.setAutoOpen(true);
```

## Stylisation de `AppNavItem` {#styling-appnavitem}

<TableBuilder name="AppNavItem" />
