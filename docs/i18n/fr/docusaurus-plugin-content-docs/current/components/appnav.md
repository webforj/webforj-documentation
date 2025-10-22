---
title: AppNav
sidebar_position: 6
_i18n_hash: faa14d827865b1697b369a9787315dcd
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-app-nav" />
<DocChip chip="name" label="dwc-app-nav-item" />
<DocChip chip='since' label='24.12' />
<JavadocLink type="appnav" location="com/webforj/component/appnav/AppNav" top='true'/> 

Le composant `AppNav` dans webforJ fournit un menu de navigation latérale flexible et organisé, avec un support pour les structures à la fois plates et hiérarchiques. Chaque entrée est un `AppNavItem`, qui peut représenter un lien simple ou un groupe contenant des sous-éléments. Les éléments peuvent être liés à des vues internes ou des ressources externes, enrichis avec des icônes, des badges ou d'autres composants.

## Ajouter et imbriquer des éléments {#adding-and-nesting-items}

Les instances de `AppNavItem` sont utilisées pour peupler la structure `AppNav`. Ces éléments peuvent être des liens simples ou des en-têtes de groupe imbriqués contenant des éléments enfants. Les en-têtes de groupe sans liens agissent comme des conteneurs extensibles.

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

:::tip Lien des éléments de groupe
Les éléments de niveau supérieur dans un arbre de navigation sont généralement destinés à être extensibles—pas des liens cliquables. Définir un `path` sur de tels éléments peut déranger les utilisateurs qui s'attendent à ce qu'ils révèlent des sous-éléments au lieu de naviguer ailleurs.

Si vous souhaitez que l'en-tête de groupe déclenche une action personnalisée (comme ouvrir des docs externes), laissez le chemin du groupe vide et ajoutez plutôt un contrôle interactif comme un [`IconButton`](./icon#icon-buttons) à suffixe de l'élément. Cela maintient une UX cohérente et propre.
:::

<!--vale off-->
<AppLayoutViewer 
path='/webforj/appnav/Social?'  
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/appnav/AppNavView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/appnav/AppNavPageView.java']}
/>
<!--vale on-->

## Lien des éléments {#linking-items}

Chaque `AppNavItem` peut naviguer vers une vue interne ou un lien externe. Vous pouvez définir cela à l'aide de chemins statiques ou de classes de vue enregistrées.

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

Si votre route annotée prend en charge des [paramètres de route](../routing/route-patterns#named-parameters), vous pouvez également passer un `ParametersBag` :

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

Contrôlez comment les liens s'ouvrent en utilisant `setTarget()`. Cela est particulièrement utile pour les liens externes ou les vues pop-out.

- **`SELF`** (par défaut) : S'ouvre dans la vue actuelle.
- **`BLANK`** : S'ouvre dans un nouvel onglet ou une nouvelle fenêtre.
- **`PARENT`** : S'ouvre dans le contexte de navigation parent.
- **`TOP`** : S'ouvre dans le contexte de navigation de niveau supérieur.

```java
AppNavItem help = new AppNavItem("Help", "https://support.example.com");
help.setTarget(AppNavItem.NavigationTarget.BLANK);
```

## Préfixe et suffixe {#prefix-and-suffix}

`AppNavItem` supporte les composants de préfixe et de suffixe. Utilisez-les pour fournir une clarté visuelle avec des icônes, des badges ou des boutons.

- **Préfixe** : apparaît avant l'étiquette, utile pour les icônes.
- **Suffixe** : apparaît après l'étiquette, idéal pour les badges ou les actions.

```java
AppNavItem notifications = new AppNavItem("Alerts");
notifications.setPrefixComponent(TablerIcon.create("alert"));
notifications.setSuffixComponent(TablerIcon.create("link"));
```

## Groupes auto-ouverts {#auto-opening-groups}

Utilisez `setAutoOpen(true)` sur le composant `AppNav` pour développer automatiquement les groupes imbriqués lorsque l'application est rafraîchie.

```java
nav.setAutoOpen(true);
```

## Styliser `AppNavItem` {#styling-appnavitem}

<TableBuilder name="AppNavItem" />
