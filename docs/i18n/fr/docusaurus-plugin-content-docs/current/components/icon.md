---
title: Icon
sidebar_position: 55
_i18n_hash: 2da7d4e8288df67fc46f2a3ba84e12ee
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-icon" />
<DocChip chip='since' label='24.11' />
<JavadocLink type="icons" location="com/webforj/component/icons/Icon" top='true'/>

Le composant `Icon` de webforJ vous permet d’inclure des icônes sans effort dans votre interface utilisateur.
Les icônes sont une partie fondamentale de l'amélioration du design de l'interface utilisateur, permettant aux utilisateurs de scanner plus rapidement l'écran à la recherche d'éléments exploitables.
L’utilisation d’icônes dans votre application crée des repères visuels pour la navigation et les actions, ce qui peut réduire la quantité de texte nécessaire et simplifier l'interface utilisateur. Vous pouvez choisir parmi trois pools d'icônes existants, et webforJ vous donne également la possibilité de créer de nouveaux pools à partir de zéro.

:::tip Le saviez-vous ?

Certains composants, comme `PasswordField` et `TimeField`, ont des icônes intégrées pour aider à transmettre du sens aux utilisateurs finaux.

:::

## Basics {#basics}

Chaque `Icon` est conçu comme une image en graphiques vectoriels évolutifs (SVG), ce qui signifie qu'il peut facilement être redimensionné à n'importe quelle taille sans perdre de clarté ni de qualité.
De plus, les composants `Icon` sont chargés à la demande à partir d'un réseau de diffusion de contenu (CDN), ce qui aide à réduire la latence et à améliorer les performances globales.

Lorsque vous créez un `Icon`, vous devez identifier un pool spécifique et le nom de l'icône elle-même.
Certaines icônes offrent également le choix entre une version contour ou une version remplie via [variations](#variations).

<ComponentDemo 
path='/webforj/iconbasics?'  
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/icon/IconBasicsView.java'
height='100px'
/>

### Pools {#pools}

Un pool d'icônes est une collection d'icônes couramment utilisées qui permet un accès facile et une réutilisation. En utilisant des icônes d'un pool d'icônes, vous pouvez vous assurer que les icônes de votre application sont reconnaissables et partagent un style cohérent.
L'utilisation de webforJ vous permet de choisir parmi trois pools ou d'implémenter un pool personnalisé.
Chaque pool a une vaste collection d'icônes open source qui sont gratuites à utiliser.
Utiliser webforJ vous donne la flexibilité de choisir parmi trois pools et de les utiliser comme classes uniques, sans avoir à télécharger directement aucune des icônes.

| Icon Pool                                         | webforJ Class |
| --------                                          | ------- |
| [Tabler](https://tabler-icons.io/)                | `TablerIcon` et `DwcIcon`.<br/>`DwcIcon` est un sous-ensemble des icônes Tabler.|    
| [Feather](https://feathericons.com/)              | `FeatherIcon`    |
| [Font Awesome](https://fontawesome.com/search)    | `FontAwesomeIcon`   |

:::tip

Si vous êtes intéressé par la création de votre propre pool d'icônes, consultez [Créer des pools personnalisés](#creating-custom-pools).

:::

Une fois que vous avez sélectionné le ou les pools à inclure dans votre application, l'étape suivante consiste à spécifier le nom de l'icône que vous souhaitez utiliser.

### Names {#names}

Pour inclure une icône dans votre application, tout ce dont vous avez besoin est le pool d'icônes et le nom de l'icône. Parcourez le site Web du pool d'icônes pour trouver l'icône que vous souhaitez utiliser, et utilisez le nom de l'icône comme paramètre de la méthode `create()`.
De plus, vous pouvez créer des icônes via des énumérations pour les classes `FeatherIcon` et `DwcIcon`, leur permettant d'apparaître dans l'achèvement du code.

```java
// Créer une icône à partir d'un nom de chaîne
Icon image = TablerIcon.create("image");
// Créer une icône à partir d'une énumération
Icon image = FeatherIcon.IMAGE.create();
```

### Variations {#variations}

Vous pouvez personnaliser encore plus les icônes en utilisant des variations.
Certaines icônes vous permettent de choisir entre une version contour ou une version remplie, ce qui permet de mettre en avant une icône spécifique en fonction de votre préférence. Les icônes `FontAwesomeIcon` et `Tabler` offrent des variations.

#### Variations de `FontAwesomeIcon` {#fontawesomeicon-variations}

1. `REGULAR`: La variation contour des icônes. C'est la valeur par défaut.
2. `SOLID`: La variation remplie des icônes.
3. `BRAND`: La variation lorsque vous utilisez les icônes de marques.

#### Variations de `TablerIcon` {#tablericon-variations}

1. `OUTLINE`: La variation contour des icônes. C'est la valeur par défaut.
2. `FILLED`: La variation remplie des icônes.

```java
// Une variation remplie d'une icône à partir de Font Awesome
Icon music = FontAwesomeIcon.create("user", FontAwesomeIcon.Variate.SOLID);
```

La démo suivante illustre comment utiliser des icônes de différents pools, appliquer des variations et les intégrer sans effort dans des composants.

<ComponentDemo 
path='/webforj/iconvariations?'  
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/icon/IconVariationsView.java'
height='100px'
/>

## Intégration des icônes dans les composants {#adding-icons-to-components}

Intégrez des icônes dans vos composants à l'aide de slots. Les slots fournissent des options flexibles pour rendre les composants plus utiles. Il est avantageux d'ajouter un `Icon` à un composant pour clarifier davantage le sens prévu aux utilisateurs.
Les composants qui implémentent l'interface `HasPrefixAndSuffix` peuvent inclure un `Icon` ou d'autres composants valides. Les composants ajoutés peuvent être placés dans les slots `prefix` et `suffix` et peuvent améliorer à la fois le design global et l'expérience utilisateur.

En utilisant les slots `prefix` et `suffix`, vous pouvez déterminer si vous souhaitez que l'icône soit avant ou après le texte en utilisant les méthodes `setPrefixComponent()` et `setSuffixComponent()`.

La décision de placer une icône avant ou après le texte sur un composant dépend largement du but et du contexte de conception.

### Positionnement des icônes : avant VS après {#icon-placement-before-vs-after}

Les icônes placées avant le texte du composant aident les utilisateurs à comprendre rapidement l'action principale ou le but du composant, en particulier pour des icônes universellement reconnues comme l'icône de sauvegarde.
Les icônes avant le texte d'un composant offrent un ordre de traitement logique, guidant les utilisateurs naturellement à travers l'action prévue, ce qui est bénéfique pour les boutons dont la fonction principale est une action immédiate.

D'un autre côté, placer des icônes après le texte du composant est efficace pour des actions qui fournissent un contexte ou des options supplémentaires, améliorant la clarté et les repères pour la navigation.
Les icônes après le texte d'un composant sont idéales pour des composants qui offrent des informations supplémentaires ou guident les utilisateurs dans un flux directionnel.

En fin de compte, la cohérence est essentielle. Une fois que vous choisissez un style, maintenez-le sur votre site pour un design cohérent et convivial.

<ComponentDemo 
path='/webforj/iconprefixsuffix?'  
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/icon/IconPrefixSuffixView.java'
height='100px'
/>️

## Création de pools personnalisés {#creating-custom-pools}

Au-delà de l'utilisation de collections d'icônes existantes, vous avez la possibilité de créer un pool personnalisé qui peut être utilisé pour des logos ou des avatars personnalisés.
Un pool personnalisé d'icônes peut être stocké dans un répertoire centralisé ou dans le dossier des ressources (contexte), simplifiant ainsi le processus de gestion des icônes.
Avoir un pool personnalisé rend la création d'applications plus cohérente et réduit la maintenance à travers différents composants et modules.

Des pools personnalisés peuvent être créés à partir d'un dossier contenant des images SVG en utilisant la classe `IconPoolBuilder`. À partir de là, vous pouvez choisir le nom de votre pool personnalisé et l'utiliser avec les noms de fichiers SVG pour créer des composants d'icônes personnalisés.

```java
// Création d'un pool personnalisé appelé "app-pool" qui a des images pour un logo et un avatar.
IconPoolBuilder.fromDirectory("app-pool", "context://icons");
Icon customLogo = new Icon("logo", "app-pool");
Icon customAvatar = new Icon("avatar-default", "app-pool");
```

:::tip
Assurez-vous de concevoir les icônes avec une largeur et une hauteur égales, car les composants `Icon` sont conçus pour occuper un espace carré.
:::

### Usine de pools personnalisés {#custom-pool-factory}

Vous pouvez également créer une classe d’usine pour un pool personnalisé dans webforJ, tout comme `FeatherIcon`. Cela vous permet de créer et de gérer des ressources d'icônes au sein d'un pool spécifié et de permettre l'achèvement du code.
Chaque icône peut être instanciée par la méthode `create()`, qui renvoie un `Icon`. La classe d’usine doit fournir des métadonnées spécifiques au pool, telles que le nom du pool et l'identifiant de l'icône, formaté selon le nom de fichier de l'image.
Ce design permet un accès facile et standardisé aux actifs d'icônes du pool personnalisé en utilisant des constantes d'énumération, soutenant la scalabilité et la maintenabilité dans la gestion des icônes.

```java
// Création d'une usine de pool personnalisé pour app-pool
public enum AppPoolIcon implements IconFactory {
  LOGO, AVATAR_DEFAULT;

  public Icon create() {
    return new Icon(String.valueOf(this), this.getPool());
  }

  /**
   * @return le nom du pool pour les icônes
   */
  @Override
  public String getPool() {
    return "app-pool";
  }

  /**
   * @return le nom de l'icône
   */
  @Override
  public String toString() {
    return this.name().toLowerCase(Locale.ENGLISH).replace('_', '-');
  }
}
```

Le code suivant montre les deux façons différentes d'utiliser un pool personnalisé.

```java
IconPoolBuilder.fromDirectory("app-pool", "context://icons");

// Créer une icône en utilisant les noms du pool personnalisé et le fichier image
Icon customLogo = new Icon("logo", "app-pool");

// Créer une icône en utilisant l'usine de pool personnalisé de l'extrait précédent
Icon customLogo = AppPoolIcon.LOGO.create();
```

## Boutons d'icônes {#icon-buttons}
Un composant `Icon` est non sélectionnable, mais pour des actions qui sont mieux représentées par juste une icône, comme des notifications ou des alertes, vous pouvez utiliser le `IconButton`.

 ```java
IconButton bell = new IconButton(FeatherIcon.BELL.create());
bell.onClick(e -> {
  showMessageDialog("Vous avez un nouveau message !", "Ding Dong !");
});
```

## Meilleures pratiques

- **Accessibilité :** Utilisez une info-bulle ou une étiquette sur les icônes pour rendre votre application accessible aux utilisateurs malvoyants qui dépendent des lecteurs d'écran.
- **Évitez l'ambiguïté :** Évitez d'utiliser des icônes si le sens n'est pas clair ou largement compris. Si les utilisateurs doivent deviner ce que représente l'icône, cela va à l'encontre de l'objectif.
- **Utilisez les icônes avec parcimonie :** Trop d'icônes peuvent submerger les utilisateurs, alors n'utilisez des icônes que lorsque cela ajoute de la clarté ou réduit la complexité.

## Stylisation
Une icône hérite du thème de son composant parent direct, mais vous pouvez le remplacer en appliquant un thème directement sur une `Icon`.

### Thèmes
Les composants d'icônes sont livrés avec sept thèmes discrets intégrés pour un style rapide sans avoir à utiliser de CSS. Ces thèmes sont des styles prédéfinis qui peuvent être appliqués aux icônes pour changer leur apparence et leur présentation visuelle. Ils offrent un moyen rapide et cohérent de personnaliser l'apparence des icônes dans toute une application.

Bien qu'il existe de nombreux cas d'utilisation pour chacun des différents thèmes, quelques exemples d'utilisation sont :

- `DANGER` : Meilleur pour les actions ayant des conséquences graves, comme vider des informations remplies ou supprimer définitivement un compte/des données.
- `DEFAULT` : Approprié pour des actions à travers une application qui ne nécessitent pas d'attention particulière et sont génériques, comme basculer un paramètre.
- `PRIMARY` : Approprié en tant qu’« appel à l'action » principal sur une page, comme s'inscrire, sauvegarder des modifications ou continuer vers une autre page.
- `SUCCESS` : Excellent pour visualiser l'accomplissement réussi d'un élément dans une application, comme la soumission d'un formulaire ou la finalisation d'un processus d'inscription. Le thème de succès peut être appliqué par programme une fois qu'une action réussie a été complétée.
- `WARNING` : Utile pour indiquer qu'un utilisateur est sur le point d'effectuer une action potentiellement risquée, comme quitter une page avec des modifications non sauvegardées. Ces actions sont souvent moins impactantes que celles qui utiliseraient le thème Danger.
- `GRAY` : Bon pour des actions subtiles, comme des paramètres mineurs ou des actions qui sont plus complémentaires à une page et ne font pas partie de la fonctionnalité principale.
- `INFO` : Bon pour fournir des informations supplémentaires clarifiantes à un utilisateur.

<TableBuilder name="Icon" />
