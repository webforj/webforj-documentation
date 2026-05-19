---
title: AppNav
sidebar_position: 6
_i18n_hash: 859da44bd50a1b3e985139da624ed4d4
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-app-nav" />
<DocChip chip="name" label="dwc-app-nav-item" />
<DocChip chip='since' label='24.12' />
<JavadocLink type="appnav" location="com/webforj/component/appnav/AppNav" top='true'/> 

Le composant `AppNav` crée un menu de navigation latéral à partir des entrées `AppNavItem`. Les éléments peuvent se lier à des vues internes ou à des ressources externes, s'imbriquer sous des éléments parents pour former des menus hiérarchiques, et porter des icônes, des badges ou d'autres composants pour donner aux utilisateurs plus de contexte d'un coup d'œil.

<!-- INTRO_END -->

## Ajout et imbrication des éléments {#adding-and-nesting-items}

Les instances `AppNavItem` sont utilisées pour peupler la structure `AppNav`. Ces éléments peuvent être des liens simples ou des en-têtes de groupe imbriqués qui contiennent des éléments enfants. Les en-têtes de groupe sans liens agissent comme des conteneurs extensibles.

Utilisez `addItem()` pour inclure des éléments dans la navigation :

```java
AppNavItem tableauDeBord = new AppNavItem("Tableau de bord", "/dashboard");
AppNavItem admin = new AppNavItem("Admin");
admin.addItem(new AppNavItem("Utilisateurs", "/admin/users"));
admin.addItem(new AppNavItem("Paramètres", "/admin/settings"));

AppNav nav = new AppNav();
nav.addItem(tableauDeBord);
nav.addItem(admin);
```

:::tip Liens des éléments de groupe
Les éléments de niveau supérieur dans un arbre de navigation sont généralement destinés à être extensibles — pas des liens cliquables. Définir un `path` sur de tels éléments peut confondre les utilisateurs qui s'attendent à ce qu'ils révèlent des sous-éléments plutôt que de naviguer ailleurs.

Si vous souhaitez que l'en-tête de groupe déclenche une action personnalisée (comme ouvrir des documents externes), gardez le chemin du groupe vide et ajoutez plutôt un contrôle interactif comme un [`IconButton`](./icon#icon-buttons) à la suffixe de l'élément. Cela garde l'UX cohérent et propre.
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

## Lien des éléments {#linking-items}

Chaque `AppNavItem` peut naviguer vers une vue interne ou un lien externe. Vous pouvez définir cela en utilisant des chemins statiques ou des classes de vue enregistrées.

### Chemins statiques {#static-paths}

Utilisez des chemins de chaîne pour définir des liens directement :

```java
AppNavItem docs = new AppNavItem("Docs", "/docs");
AppNavItem aide = new AppNavItem("Aide", "https://support.example.com");
```

### Vues enregistrées {#registered-views}

Si vos vues sont enregistrées avec le [router](../routing/overview), vous pouvez passer la classe au lieu d'une URL codée en dur :

```java
AppNavItem paramètres = new AppNavItem("Paramètres", SettingsView.class);
```

Si votre route annotée prend en charge des [paramètres de route](../routing/route-patterns#named-parameters), vous pouvez également passer un `ParametersBag` :

```java
ParametersBag params = ParametersBag.of("id=123");
AppNavItem avancé = new AppNavItem("Utilisateur", UserView.class, params);
```

### Avec des paramètres de requête {#with-query-parameters}

Passez un `ParametersBag` pour inclure des chaînes de requête :

```java
ParametersBag params = ParametersBag.of("param1=value1&param2=value2");
AppNavItem avancé = new AppNavItem("Avancé", SettingsView.class, params);
advanced.setQueryParameters(params);
```

## Comportement cible {#target-behavior}

Contrôlez comment les liens s'ouvrent à l'aide de `setTarget()`. Ceci est particulièrement utile pour les liens externes ou les vues pop-out.

- **`SELF`** (par défaut) : S'ouvre dans la vue actuelle.
- **`BLANK`** : S'ouvre dans un nouvel onglet ou une nouvelle fenêtre.
- **`PARENT`** : S'ouvre dans le contexte de navigation parent.
- **`TOP`** : S'ouvre dans le contexte de navigation de niveau supérieur.

```java
AppNavItem aide = new AppNavItem("Aide", "https://support.example.com");
help.setTarget(AppNavItem.NavigationTarget.BLANK);
```

## Préfixe et suffixe {#prefix-and-suffix}

`AppNavItem` prend en charge les composants de préfixe et de suffixe. Utilisez-les pour fournir de la clarté visuelle avec des icônes, des badges ou des boutons.

- **Préfixe** : apparaît avant le label, utile pour les icônes.
- **Suffixe** : apparaît après le label, idéal pour les badges ou les actions.

```java
AppNavItem notifications = new AppNavItem("Alertes");
notifications.setPrefixComponent(TablerIcon.create("alert"));
notifications.setSuffixComponent(TablerIcon.create("link"));
```

## Groupes à ouverture automatique {#auto-opening-groups}

Utilisez `setAutoOpen(true)` sur le composant `AppNav` pour étendre automatiquement les groupes imbriqués lorsque l'application est rafraîchie.

```java
nav.setAutoOpen(true);
```

## Style de `AppNavItem` {#styling-appnavitem}

<TableBuilder name="AppNavItem" />
