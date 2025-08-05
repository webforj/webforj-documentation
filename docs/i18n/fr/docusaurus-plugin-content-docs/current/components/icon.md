---
title: Icon
sidebar_position: 55
_i18n_hash: ab67367c75477c4366e5e86862dac630
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-icon" />
<DocChip chip='since' label='24.11' />
<JavadocLink type="icons" location="com/webforj/component/icons/Icon" top='true'/>

Le composant webforJ `Icon` vous permet d'inclure des icônes sans effort dans votre interface utilisateur. Les icônes sont une partie fondamentale pour améliorer le design de l'interface utilisateur, permettant aux utilisateurs de parcourir l'écran plus rapidement à la recherche d'éléments actionnables. L'utilisation d'icônes dans votre application crée des indices visuels pour la navigation et les actions, ce qui peut réduire la quantité de texte nécessaire et simplifier l'interface utilisateur. Vous pouvez choisir parmi trois ensembles d'icônes existants et webforJ vous donne également la possibilité de créer de nouveaux ensembles de zéro.

:::tip Le saviez-vous ?

Certains composants, comme `PasswordField` et `TimeField`, ont des icônes intégrées pour aider à transmettre un sens aux utilisateurs finaux.

:::

## Bases {#basics}

Chaque `Icon` est conçu comme une image de graphiques vectoriels évolutifs (SVG), ce qui signifie qu'il peut être agrandi à n'importe quelle taille sans perdre de clarté ou de qualité. De plus, les composants `Icon` sont chargés à la demande à partir d'un réseau de distribution de contenu (CDN), ce qui contribue à réduire la latence et à améliorer les performances globales.

Lorsque vous créez une `Icon`, vous devez identifier un ensemble spécifique et le nom de l'icône elle-même. Certaines icônes offrent également le choix entre une version contourée ou remplie via [variations](#variations).

<ComponentDemo 
path='/webforj/iconbasics?'  
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/icon/IconBasicsView.java'
height='100px'
/>

### Ensembles {#pools}

Un ensemble d'icônes est une collection d'icônes couramment utilisées qui permet un accès et une réutilisation faciles. En utilisant des icônes d'un ensemble d'icônes, vous pouvez vous assurer que les icônes de votre application sont reconnaissables et partagent un style cohérent. En utilisant webforJ, vous pouvez choisir parmi trois ensembles, ou mettre en œuvre un ensemble personnalisé. Chaque ensemble dispose d'une vaste collection d'icônes open source gratuites. Utiliser webforJ vous offre la flexibilité de choisir parmi trois ensembles et de les utiliser comme classes uniques, sans avoir à télécharger directement les icônes.

| Ensemble d'icônes                                  | Classe webforJ |
| -------------------------------------------------- | -------------- |
| [Tabler](https://tabler-icons.io/)                 | `TablerIcon` et `DwcIcon`.<br/>`DwcIcon` est un sous-ensemble des icônes Tabler.|    
| [Feather](https://feathericons.com/)               | `FeatherIcon`    |
| [Font Awesome](https://fontawesome.com/search)     | `FontAwesomeIcon`   |

:::tip

Si vous êtes intéressé par la création de votre propre ensemble d'icônes, consultez [Création d'ensembles personnalisés](#creating-custom-pools).

:::

Une fois que vous avez sélectionné l'ensemble ou les ensembles à inclure dans votre application, l'étape suivante consiste à spécifier le nom de l'icône que vous souhaitez utiliser.

### Noms {#names}

Pour inclure une icône dans votre application, il vous suffit de l'ensemble d'icônes et du nom de l'icône. Parcourez le site Web de l'ensemble d'icônes pour l'icône que vous souhaitez utiliser et utilisez le nom de l'icône comme paramètre de la méthode `create()`. De plus, vous pouvez créer les icônes via des énumérations pour les classes `FeatherIcon` et `DwcIcon`, leur permettant d'apparaître dans la complétion de code.

```java
// Créer une icône à partir d'un nom de chaîne
Icon image = TablerIcon.create("image");
// Créer une icône à partir d'une énumération
Icon image = FeatherIcon.IMAGE.create();
```

### Variations {#variations}

Vous pouvez personnaliser encore plus les icônes en utilisant des variations. Certaines icônes vous permettent de choisir entre une version contourée ou remplie, vous permettant de mettre en avant une icône spécifique selon votre préférence. Les icônes `FontAwesomeIcon` et `Tabler` offrent des variations.

#### Variations `FontAwesomeIcon` {#fontawesomeicon-variations}

1. `REGULAR`: La variation contourée des icônes. C'est la valeur par défaut.
2. `SOLID`: La variation remplie des icônes.
3. `BRAND`: La variation à utiliser lorsque vous utilisez les icônes des marques.

#### Variations `TablerIcon` {#tablericon-variations}

1. `OUTLINE`: La variation contourée des icônes. C'est la valeur par défaut.
2. `FILLED`: La variation remplie des icônes.

```java
// Une variation remplie d'une icône de Font Awesome
Icon music = FontAwesomeIcon.create("user", FontAwesomeIcon.Variate.SOLID);
```

La démonstration suivante illustre comment utiliser des icônes de différents ensembles, appliquer des variations et les intégrer harmonieusement dans des composants.

<ComponentDemo 
path='/webforj/iconvariations?'  
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/icon/IconVariationsView.java'
height='100px'
/>

## Ajout d'icônes aux composants {#adding-icons-to-components}

Intégrez des icônes dans vos composants à l'aide de slots. Les slots offrent des options flexibles pour rendre les composants plus utiles. Il est bénéfique d'ajouter une `Icon` à un composant pour clarifier l'intention pour les utilisateurs. Les composants implémentant l'interface `HasPrefixAndSuffix` peuvent inclure une `Icon` ou d'autres composants valides. Les composants ajoutés peuvent être placés dans les slots `prefix` et `suffix` et peuvent améliorer à la fois le design global et l'expérience utilisateur.

En utilisant les slots `prefix` et `suffix`, vous pouvez déterminer si vous souhaitez que l'icône soit avant ou après le texte en utilisant les méthodes `setPrefixComponent()` et `setSuffixComponent()`.

Décider de placer une icône avant ou après le texte sur un composant dépend largement de l'objectif et du contexte de design.

### Placement des icônes : avant VS après {#icon-placement-before-vs-after}

Les icônes positionnées avant le texte du composant aident les utilisateurs à comprendre rapidement l'action principale ou l'objectif du composant, surtout pour des icônes universellement reconnues comme l'icône de sauvegarde. Les icônes avant le texte d'un composant offrent un ordre de traitement logique, guidant les utilisateurs naturellement à travers l'action intended, ce qui est bénéfique pour les boutons dont la fonction principale est une action immédiate.

En revanche, placer des icônes après le texte du composant est efficace pour les actions qui fournissent un contexte ou des options supplémentaires, améliorant la clarté et les indices pour la navigation. Les icônes après le texte d'un composant sont idéales pour les composants qui offrent des informations supplémentaires ou guident les utilisateurs dans un flux directionnel.

En fin de compte, la cohérence est la clé. Une fois que vous choisissez un style, maintenez-le sur votre site pour un design cohérent et convivial.

<ComponentDemo 
path='/webforj/iconprefixsuffix?'  
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/icon/IconPrefixSuffixView.java'
height='100px'
/>️

## Création d'ensembles personnalisés {#creating-custom-pools}

Au-delà de l'utilisation des collections d'icônes existantes, vous avez la possibilité de créer un ensemble personnalisé qui peut être utilisé pour des logos ou avatars personnalisés. Un ensemble personnalisé d'icônes peut être stocké dans un répertoire centralisé ou dans le dossier des ressources (context), simplifiant le processus de gestion des icônes. Disposer d'un ensemble personnalisé rend la création d'applications plus cohérente et réduit la maintenance à travers différents composants et modules.

Des ensembles personnalisés peuvent être créés à partir d'un dossier contenant des images SVG en utilisant la classe `IconPoolBuilder`. À partir de là, vous pouvez choisir le nom de votre ensemble personnalisé et l'utiliser avec les noms de fichiers SVG pour créer des composants d'icônes personnalisés.

```java
// Création d'un ensemble personnalisé appelé "app-pool" qui contient des images pour un logo et un avatar.
IconPoolBuilder.fromDirectory("app-pool", "context://icons");
Icon customLogo = new Icon("logo", "app-pool");
Icon customAvatar = new Icon("avatar-default", "app-pool");
```

:::tip
Assurez-vous de concevoir des icônes avec une largeur et une hauteur égales, car les composants `Icon` sont conçus pour occuper un espace carré.
:::

### Usine d'ensembles personnalisés {#custom-pool-factory}

Vous pouvez également créer une classe d'usine pour un ensemble personnalisé dans webforJ, tout comme `FeatherIcon`. Cela vous permet de créer et de gérer des ressources d'icônes au sein d'un ensemble spécifique et de permettre la complétion de code. Chaque icône peut être instanciée via la méthode `create()`, qui retourne une `Icon`. La classe d'usine doit fournir des métadonnées spécifiques à l'ensemble, telles que le nom de l'ensemble et l'identifiant de l'icône, formaté selon le nom de fichier de l'image. Ce design permet un accès facile et standardisé aux ressources d'icônes de l'ensemble personnalisé en utilisant des constantes d'énumération, soutenant l'évolutivité et la maintenabilité dans la gestion des icônes.

```java
/// Création d'une usine d'ensemble personnalisée pour l'app-pool
public enum AppPoolIcon implements IconFactory {
  LOGO, AVATAR_DEFAULT;

  public Icon create() {
    return new Icon(String.valueOf(this), this.getPool());
  }

  /**
   * @return le nom de l'ensemble pour les icônes
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

Le snippet suivant montre les deux manières différentes d'utiliser un ensemble personnalisé.

```java
IconPoolBuilder.fromDirectory("app-pool", "context://icons");

// Créer une Icon en utilisant les noms de l'ensemble personnalisé et le fichier image
Icon customLogo = new Icon("logo", "app-pool");

// Créer une Icon en utilisant l'usine de l'ensemble personnalisé du snippet précédent
Icon customLogo = AppPoolIcon.LOGO.create();
```

## Boutons d'icônes {#icon-buttons}
Un composant `Icon` est non sélectionnable, mais pour les actions qui sont mieux représentées par une simple icône, comme les notifications ou les alertes, vous pouvez utiliser l'`IconButton`.

```java
IconButton bell = new IconButton(FeatherIcon.BELL.create());
bell.onClick(e -> {
  showMessageDialog("Vous avez un nouveau message !", "Ding Dong !");
});
```

## Meilleures pratiques

- **Accessibilité :** Utilisez un info-bulle ou un label sur les icônes pour rendre votre application accessible aux utilisateurs malvoyants qui dépendent des lecteurs d'écran.
- **Évitez l'ambiguïté :** Évitez d'utiliser des icônes si la signification n'est pas claire ou largement comprise. Si les utilisateurs doivent deviner ce que représente l'icône, cela va à l'encontre de l'objectif.
- **Utilisez les icônes avec parcimonie :** Trop d'icônes peuvent submerger les utilisateurs, donc n'utilisez les icônes que lorsqu'elles ajoutent de la clarté ou réduisent la complexité.

## Styling
Une icône hérite du thème de son composant parent direct, mais vous pouvez remplacer cela en appliquant un thème à une `Icon` directement.

### Thèmes
Les composants d'icônes viennent avec sept thèmes discrets intégrés pour un style rapide sans l'utilisation de CSS. Ces thèmes sont des styles prédéfinis qui peuvent être appliqués aux icônes pour changer leur apparence et leur présentation visuelle. Ils offrent un moyen rapide et cohérent de personnaliser l'apparence des icônes dans l'ensemble d'une application.

Bien qu'il existe de nombreux cas d'utilisation pour chacun des différents thèmes, voici quelques exemples d'utilisation :

- `DANGER`: Meilleur pour les actions ayant des conséquences graves, comme effacer des informations remplies ou supprimer définitivement un compte/données.
- `DEFAULT`: Approprié pour les actions à travers une application qui ne nécessitent pas d'attention particulière et sont génériques, comme basculer un réglage.
- `PRIMARY`: Approprié en tant qu'"appel à l'action" principal sur une page, comme s'inscrire, enregistrer des modifications, ou continuer vers une autre page.
- `SUCCESS`: Excellent pour visualiser la réussite de l'achèvement d'un élément dans une application, comme la soumission d'un formulaire ou l'achèvement d'un processus d'inscription. Le thème de réussite peut être appliqué de manière programmatique une fois qu'une action réussie a été complétée.
- `WARNING`: Utile pour indiquer qu'un utilisateur est sur le point d'effectuer une action potentiellement risquée, comme naviguer en dehors d'une page avec des modifications non enregistrées. Ces actions sont souvent moins impactantes que celles qui utiliseraient le thème Danger.
- `GRAY`: Bon pour des actions subtiles, comme des réglages mineurs ou des actions qui sont plus accessoires à une page, et non pas partie de la fonctionnalité principale.
- `INFO`: Bon pour fournir des informations supplémentaires clarifiantes à un utilisateur.

<TableBuilder name="Icon" />
