---
title: Upgrade to 26.00
description: Upgrade from 25.00 to 26.00
slug: /upgrading/webforj-26.00
pagination_next: null
sidebar_class_name: new-content
sidebar_position: 1
_i18n_hash: e62ee79be86c51d62fe19d10af89cc1b
---
Cette documentation sert de guide pour mettre à niveau les applications webforJ de 25.00 à 26.00. Voici les changements nécessaires pour que les applications existantes continuent à fonctionner correctement. Comme toujours, consultez le [aperçu des versions GitHub](https://github.com/webforj/webforj/releases) pour une liste plus complète des changements entre les versions.

<!-- INTRO_END -->

<AutomatedUpgradeTip />

## Changements dans le fichier POM {#pom-file-changes}

### Java 21 et 25 {#java-21-and-25}

webforJ 25.12 est la dernière version compatible avec Java 17. À partir de webforJ 26.00, vous devez utiliser une version de Java qui est soit Java 21, soit Java 25, en fonction de votre configuration.

Installez la version Java requise comme indiqué dans les [prérequis](/docs/introduction/prerequisites), puis mettez à jour votre fichier pom.xml :

```xml {3-4}
<properties>
  ....
  <maven.compiler.target>21</maven.compiler.target>
  <maven.compiler.source>21</maven.compiler.source>
</properties>
```

### URL du dépôt Maven {#maven-repository-url}

L'emplacement où les artefacts de snapshot sont hébergés a changé. Dans le fichier pom.xml de votre projet, avez-vous téléchargé vos dépendances depuis le [Central Portal](https://central.sonatype.com/) ?

**Avant :**
```xml
<repositories>
  <repository>
    <id>snapshots-repo</id>
    <url>https://s01.oss.sonatype.org/content/repositories/snapshots</url>
    ....
  </repository>
</repositories>
```

**Après :**
```xml {3-5}
<repositories>
  <repository>
    <name>Central Portal Snapshots</name>
    <id>central-portal-snapshots</id>
    <url>https://central.sonatype.com/repository/maven-snapshots/</url>
    ....
  </repository>
</repositories>
```

### Mise à niveau de Spring Boot {#spring-boot-upgrade}

webforJ 25.12 est la dernière version qui utilise Spring Boot 3.x. À partir de webforJ 26.00, votre projet doit utiliser Spring Boot 4.x.

```xml {4}
<parent>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-parent</artifactId>
  <version>4.0.5</version>
</parent>
```

:::tip Suppression des substitutions pour la version de Tomcat
Avec Spring Boot 4.x, Tomcat 11.x est maintenant inclus en tant que dépendance, vous pouvez donc supprimer toute substitution spécifique au projet pour la version de Tomcat.
:::

## Changements dans l'API Table {#table-api-changes}

### Constructeurs basés sur des chaînes `IconRenderer` {#iconrenderer- string-based-constructors}

Les constructeurs suivants basés sur des chaînes sont supprimés dans 26.00 ; utilisez plutôt des constructeurs basés sur `IconDefinition` :

| v25 | v26 |
|---|---|
| `IconRenderer(String name, String pool, EventListener)` | `IconRenderer(IconDefinition, EventListener)` |
| `IconRenderer(String name, String pool)` | `IconRenderer(IconDefinition)` |
| `IconRenderer(String name, EventListener)` | `IconRenderer(IconDefinition,  EventListener)` |
| `IconRenderer(String name)` | `IconRenderer(IconDefinition)` |

### Méthodes de sélection obsolètes {#deprecated-selection-methods}

À partir de webforJ 26.00, au lieu de sélectionner des éléments dans une `Table` en fonction des indices, sélectionnez des éléments dans une Table en utilisant la clé de l'élément. Vous pouvez utiliser la méthode `setKeyProvider()` pour fournir des clés personnalisées pour les éléments de la table.

| v25 | v26 |
|---|---|
| `selectIndex(int...)` | `select(items)` ou `selectKey(keys)` |
| `deselectIndex(int...)` | `deselect(items)`, `deselectKey(keys)` ou `deselectAll()` |
| `getSelectedIndex()` | `getSelected()` ou `getSelectedItem()` |
| `getSelectedIndices()` | `getSelectedItems()` |

### Événements de sélection {#selection-events}

Pour renforcer davantage le changement dans la manière de sélectionner des éléments dans une `Table`, `TableItemSelectionChange` n'implémente plus `SelectEvent`.

| v25 | v26 |
|---|---|
| `event.getSelectedIndex()` | `event.getSelectedItem()` |
| `event.getSelectedIndices()` | `event.getSelectedItems()` |

## Options de bootstrap Webswing non prises en charge {#unsupported-webswing-bootstrap-options}

Les méthodes suivantes de `WebswingOptions` sont obsolètes et supprimées dans 26.00 car elles ne sont plus prises en charge par l'API Webswing.

- `getAutoReconnect()` / `setAutoReconnect(Integer)`
- `isDisableLogout()` / `setDisableLogout(boolean)`
- `isDisableLogin()` / `setDisableLogin(boolean)`
- `isSyncClipboard()` / `setSyncClipboard(boolean)`
- `getJavaCallTimeout()` / `setJavaCallTimeout(int)`
- `getPingParams()` / `setPingParams(PingParams)`

La classe `PingParams` est également obsolète. Ceux qui utilisaient ces méthodes ou la classe `PingParams` devraient plutôt utiliser la console d'administration Webswing pour configurer directement les options.

## Filtres pour `Repository` {#filters-for-repository}

Les interfaces `RetrievalCriteria` et `RetrievalBuilder` sont supprimées dans webforJ 26.00. Au lieu d'utiliser l'interface générique `Repository`, utilisez soit `RepositoryCriteria<T, F>`, soit `CollectionRepository` pour des filtres simples, ou [`QueryableRepository`](/docs/advanced/repository/querying-data) pour des types de filtrage, de tri et de pagination plus avancés.

**Avant :**
```java
private String searchTerm = "";

Repository<CustomerRecord> repository = new Repository<>();

repository.setFilter((CustomerRecord r) -> {
  String title = r.getTitle();
  return title.toLowerCase().contains(this.searchTerm);
  });
```

**Après :**
```java {3,5}
private String searchTerm = "";

CollectionRepository<CustomerRecord> repository = new CollectionRepository<>();

repository.setBaseFilter((CustomerRecord r) -> {
  String title = r.getTitle();
  return title.toLowerCase().contains(this.searchTerm);
  });
```

### Méthodes de dépôt obsolètes {#deprecated-repository-methods}

Utilisez le tableau suivant pour voir les méthodes de dépôt obsolètes et quelles méthodes utiliser à l'avenir.

| v25 | v26 |
|---|---|
| `setFilter(...)` | `setBaseFilter(...)` |
| `getFilter()` | `getBaseFilter()` |
| `getOrderBy()` | `getOrderCriteriaList()` |
| `setOrderBy(Comparator<T>)` | `getOrderCriteriaList().add(new OrderCriteria<>(keyExtractor, direction))` |
| `findOneBy(...)` | `findBy(...)` puis `.findFirst()` |
| `findBy(RetrievalCriteria<T>)` | `findBy(RepositoryCriteria<T, F>)` |
| `size(RetrievalCriteria<T>)` | `size(RepositoryCriteria<T, F>)` |
| `getIndex(T)` | `find(key)` ou `findBy(criteria)` |
| `findByIndex(int)` | `find(key)` ou `findBy(criteria)` |

## Suppression de `WebforjBBjBridge` {#removal-of-webforjbbjbridge}

À partir de webforJ 25.11, WebforjBBjBridge et toutes ses API ont été supprimées. Au lieu d'accéder au pont, webforJ utilise maintenant l'API Java directe pour communiquer avec et accéder à toute API BBj requise.

## Changements dans le système de design (DWC 26) {#design-system-changes-dwc-26}

webforJ 26.00 est livré avec la version 26 du système de design DWC. La mise à jour est incrémentale plutôt qu'un réécriture complète : la plupart des variables CSS v25 restent disponibles, l'API de jetons publique est préservée et les personnalisations existantes continuent de fonctionner sans modifications.

Cette section répertorie les changements majeurs sur lesquels vous devrez peut-être agir. Pour un aperçu conceptuel, y compris à quoi ressemble le nouveau moteur de couleurs, comment `--dwc-dark-mode` se propage, pourquoi les ripples ont été abandonnées et la mécanique par zone, consultez [le système de design DWC 26](/docs/upgrading/webforj-26.00/design-system).

### Verdict rapide {#design-system-quick-verdict}

| Scénario | À quoi s'attendre |
|---|---|
| Utilise le style par défaut | Rafraîchissement visuel. Les teintes de la palette par défaut ont été ajustées (le primaire est passé de `h: 211 / s: 100%` à `h: 223 / s: 91%`), les ombres semblent plus stratifiées et les composants se sentent plus arrondis. Aucun changement de code nécessaire. |
| Remplace `--dwc-color-{name}-h` et `-s` | Fonctionne toujours. Le chemin de graine HSL est préservé. |
| Remplace les étapes de palette individuelles (par exemple, `--dwc-color-primary-40`) | Les numéros d'étape peuvent correspondre à des couleurs différentes. Voir [Mécanique de palette de couleurs](/docs/upgrading/webforj-26.00/design-system#the-color-system). |
| Dépend de `--dwc-color-{name}-c` | Supprimer. Le basculement texte clair/sombre est maintenant calculé automatiquement par nuance. |
| Fait référence à des jetons de taille de police nommés (`--dwc-font-size-m`, `-l`, etc.) | L'échelle a été abaissée d'un niveau. `m` est maintenant `14px` au lieu de `16px`. Voir [Typographie](#design-system-typography). |
| Utilise `--dwc-font-weight-semibold` pour obtenir un poids de `500` | `semibold` est maintenant `600`. Passez au nouveau `--dwc-font-weight-medium` pour `500`. |
| Réserve un rembourrage autour des éléments focusables avec `--dwc-focus-ring-width` | L'anneau a maintenant un espace. Ajoutez `--dwc-focus-ring-gap`. Voir [Anneau de focus](#design-system-focus-ring). |
| Personnalise les effets de survol/ripple des boutons | Les ripples sont disparues. Le retour de pression est maintenant une petite réduction d'échelle. |

### `--dwc-color-{name}-c` est supprimé {#design-system-c-removed}

Si vous avez des remplacements `--dwc-color-{name}-c`, vous pouvez les supprimer, ils n'ont aucun effet. Le basculement texte clair/sombre est maintenant calculé automatiquement par nuance.

### La sémantique de `--dwc-color-{name}-alt` a changé {#design-system-alt-changed}

| Jeton | v25 | v26 |
|---|---|---|
| `--dwc-color-{name}-alt` | Étape de palette `95` (fond presque blanc) | Graisse à 12 % d'opacité (tinte translucide) |

Si vous avez utilisé `-alt` comme fond presque blanc solide, cela apparaîtra maintenant comme un superposé teinté translucide. Choisissez une étape spécifique (`--dwc-color-{name}-95`) ou concevez autour de la sémantique translucide.

### La sémantique de `--dwc-border-color-{name}` a changé {#design-system-border-color-changed}

| Jeton | v25 | v26 |
|---|---|---|
| `--dwc-border-color-{name}` | Défini par variation comme `var(--dwc-color-{name})` (la teinte saturée) | Calculé dans le générateur : ton éclairci du graine conscient du mode |

Si votre CSS lit `--dwc-border-color-primary` en attendant la couleur primaire saturée, le rendu visuel est maintenant un ton de séparation subtil. Si vous voulez spécifiquement le rendu saturé, passez directement à `--dwc-color-primary`.

### Le format de `--dwc-shadow-color` a changé {#design-system-shadow-color-changed}

|  | v25 | v26 |
|---|---|---|
| `--dwc-shadow-color` | Triplet HSL (`h, s%, l%`) | Couleur OKLCH complète |

Si votre CSS utilise le format triplet hérité comme `hsla(var(--dwc-shadow-color), 0.07)`, passez à un jeton d'ombre complet (`var(--dwc-shadow-m)`) ou réécrivez avec `oklch(from var(--dwc-shadow-color) l c h / 0.07)`.

### Typographie {#design-system-typography}

L'échelle de police a été retouchée, donc les noms de seaux ont été décalés d'un niveau :

| Jeton | v25 | v26 |
|---|---|---|
| `--dwc-font-size-3xs` | `10px` | `10px` |
| `--dwc-font-size-2xs` | `12px` | `11px` |
| `--dwc-font-size-xs`  | `13px` | `12px` |
| `--dwc-font-size-s`   | `14px` | `13px` |
| `--dwc-font-size-m`   | `16px` | `14px` |
| `--dwc-font-size-l`   | `18px` | `16px` |
| `--dwc-font-size-xl`  | `22px` | `20px` |
| `--dwc-font-size-2xl` | `28px` | `26px` |
| `--dwc-font-size-3xl` | `36px` | `34px` |

Le `--dwc-font-size` par défaut reste résolu à **14px**, il y parvient simplement via `--dwc-font-size-m` (v26) au lieu de `--dwc-font-size-s` (v25). Si votre CSS fait référence aux jetons de taille de police par nom (par exemple, `font-size: var(--dwc-font-size-l)`), le résultat visible sera plus petit dans v26. Augmentez d'un seau pour préserver la taille v25.

Les poids de police ont gagné trois jetons (`thin`, `medium`, `black`) et un jeton existant a été décalé :

| Jeton | v25 | v26 |
|---|---|---|
| `--dwc-font-weight-semibold` | `500` | `600` |
| `--dwc-font-weight-medium`   | (n'existait pas) | `500` |

Si vous avez utilisé `--dwc-font-weight-semibold` pour obtenir du texte de poids 500, passez à `--dwc-font-weight-medium`.

### Rayon de bordure {#design-system-border-radius}

|  | v25 | v26 |
|---|---|---|
| Unité | `em` (scale avec la taille de police parent) | `rem` (scale avec la taille de police racine) |
| `--dwc-border-radius` par défaut | `--dwc-border-radius-s` (`4px`) | `--dwc-border-radius-seed` (`8px`) |
| Étapes disponibles | jusqu'à `2xl` | ajoute `3xl`, `4xl` |

Les composants semblent plus arrondis par défaut. Si un composant imbriqué à l'intérieur d'un texte plus grand héritait d'un plus grand rayon via `em`, ce scaling n'est plus le cas, les rayons sont maintenant ancrés à la racine. Si vous voulez la taille par défaut de v25, divisez la graine par deux :

```css
:root {
  --dwc-border-radius-seed: 0.25rem; /* 4px, divise toute l'échelle par deux */
}
```

### Anneau de focus {#design-system-focus-ring}

L'anneau de focus utilise maintenant un motif à double anneau : un petit espace de couleur de surface, puis l'anneau coloré.

| Variable | v25 | v26 |
|---|---|---|
| `--dwc-focus-ring-width` | `3px` | `2px` |
| `--dwc-focus-ring-a`     | `0.4` | `0.75` |
| `--dwc-focus-ring-gap`   | (aucun) | `2px` |
| `--dwc-focus-ring-l`     | `45%` | (supprimé, la luminosité est calculée par mode) |

Si vous réservez de l'espace autour des éléments focusables avec `padding: var(--dwc-focus-ring-width)`, ajoutez l'écart à ce padding afin que le nouvel anneau ait de la place pour se rendre :

```css
/* v25 */
dwc-button { padding: var(--dwc-focus-ring-width); }

/* v26 */
dwc-button {
  padding: calc(var(--dwc-focus-ring-width) + var(--dwc-focus-ring-gap));
}
```

### Ripples supprimées {#design-system-ripples-removed}

Les effets de ripples de style matériel ne sont plus utilisés par aucun composant DWC. Le nouveau feedback pour tout élément cliquable est une petite réduction d'échelle :

```css
--dwc-scale-press: 0.97;      /* Réduction standard de 3 % */
--dwc-scale-press-deep: 0.93; /* Réduction plus profonde de 7 % pour les boutons */
```

Le mixin SCSS `ripple` et la variable CSS `--dwc-ripple-color` existent toujours dans la build, mais rien ne les importe par défaut. Si vos propres composants optaient pour le mixin, passez aux jetons de réduction d'échelle pour correspondre à la nouvelle sensation.

### Durées de transition rééquilibrées {#design-system-transitions}

| Variable | v25 | v26 |
|---|---|---|
| `--dwc-transition-slow`   | `500ms` | `300ms` |
| `--dwc-transition-medium` | `250ms` | `250ms` |
| `--dwc-transition-fast`   | `150ms` | `150ms` |
| `--dwc-transition-x-fast` | `50ms`  | `100ms` |

Si vous dépendez d'une durée spécifique, supprimez-la dans `:root`.

### Liste de contrôle de mise à niveau pragmatique {#design-system-checklist}

1. Recherchez `--dwc-color-*-c` et supprimez ces déclarations.
2. Recherchez `hsla(var(--dwc-shadow-color)` et remplacez-le par un jeton d'ombre (`var(--dwc-shadow-m)`) ou réécrivez en `oklch(from ...)`.
3. Recherchez des références d'étape directe de palette (`--dwc-color-{name}-{number}`). Si l'une d'elles fournit du style spécifique au mode sombre, passez aux jetons de variation (`--dwc-color-{name}`, `-dark`, `-light`).
4. Recherchez des références de taille de police nommées (`--dwc-font-size-m`, `-l`, etc.). Si vous voulez la taille v25, augmentez d'un seau.
5. Recherchez `--dwc-font-weight-semibold`. Si vous vouliez `500`, passez à `--dwc-font-weight-medium`.
6. Si vous réservez de l'espace autour des éléments focusables avec `--dwc-focus-ring-width`, ajoutez `--dwc-focus-ring-gap` au padding.
7. Ouvrez l'application, cliquez autour. La plupart des applications n'ont besoin de rien d'autre.
