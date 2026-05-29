---
title: Icon
sidebar_position: 55
_i18n_hash: ae46080226d89087113b901c748f0942
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-icon" />
<DocChip chip='since' label='24.11' />
<JavadocLink type="icons" location="com/webforj/component/icons/Icon" top='true'/>

Le composant `Icon` affiche des icônes qui s'adaptent à n'importe quelle taille sans perte de qualité. Vous pouvez choisir parmi trois pools d'icônes intégrés ou créer les vôtres. Les icônes servent de repères visuels pour la navigation et les actions, réduisant ainsi le besoin d'étiquettes textuelles dans votre interface.

<!-- INTRO_END -->

## Bases {#basics}

Chaque `Icon` est conçu comme une image en graphiques vectoriels évolutifs (SVG), ce qui signifie qu'il peut facilement s'adapter à n'importe quelle taille sans perdre de clarté ou de qualité. De plus, les composants `Icon` sont chargés à la demande depuis un réseau de distribution de contenu (CDN), ce qui aide à réduire la latence et à améliorer les performances globales.

Lors de la création d'un `Icon`, vous devez identifier un pool spécifique et le nom de l'icône elle-même. Certaines icônes offrent également le choix entre une version à contour ou remplie via [variations](#variations).

<ComponentDemo
path='/webforj/iconbasics'
files={['src/main/java/com/webforj/samples/views/icon/IconBasicsView.java']}
height='100px'
/>

:::tip Saviez-vous ?
Certains composants, comme `PasswordField` et `TimeField`, ont des icônes intégrées pour aider à transmettre un sens aux utilisateurs finaux.
:::

### Pools {#pools}

Un pool d'icônes est une collection d'icônes couramment utilisées qui permet un accès et une réutilisation faciles. En utilisant des icônes d'un pool d'icônes, vous pouvez vous assurer que les icônes dans votre application sont reconnaissables et partagent un style cohérent. L'utilisation de webforJ vous permet de choisir parmi trois pools, ou de mettre en œuvre un pool personnalisé. Chaque pool possède une vaste collection d'icônes open source qui sont gratuites à utiliser. L'utilisation de webforJ vous donne la flexibilité de choisir parmi trois pools et de les utiliser comme classes uniques, sans avoir à télécharger directement aucune des icônes.

| Pool d'icônes                                       | Classe webforJ |
| --------                                            | ------- |
| [Tabler](https://tabler-icons.io/)                 | `TablerIcon` et `DwcIcon`.<br/>`DwcIcon` est un sous-ensemble des icônes Tabler.|    
| [Feather](https://feathericons.com/)               | `FeatherIcon`    |
| [Font Awesome](https://fontawesome.com/search)     | `FontAwesomeIcon`   |

:::tip

Si vous êtes intéressé par la création de votre propre pool d'icônes, consultez [Créer des pools personnalisés](#creating-custom-pools).

:::

Une fois que vous avez sélectionné le ou les pools à inclure dans votre application, l'étape suivante consiste à spécifier le nom de l'icône que vous souhaitez utiliser.

### Noms {#names}

Pour inclure une icône dans votre application, tout ce dont vous avez besoin est le pool d'icônes et le nom de l'icône. Parcourez le site Web du pool d'icônes pour trouver l'icône que vous souhaitez utiliser, et utilisez le nom de l'icône comme paramètre de la méthode `create()`. De plus, vous pouvez créer les icônes via des énumérations pour les classes `FeatherIcon` et `DwcIcon`, ce qui leur permet d'apparaître dans la complétion de code.

```java
// Créer une icône à partir d'un nom de chaîne
Icon image = TablerIcon.create("image");
// Créer une icône à partir d'une énumération
Icon image = FeatherIcon.IMAGE.create();
```

### Variations {#variations}

Vous pouvez personnaliser encore plus les icônes en utilisant des variations. Certaines icônes vous permettent de choisir entre une version à contour ou remplie, ce qui vous permet de mettre en avant une icône spécifique en fonction de vos préférences. Les icônes `FontAwesomeIcon` et `Tabler` offrent des variations.

#### Variations `FontAwesomeIcon` {#fontawesomeicon-variations}

1. `REGULAR`: La variation à contour des icônes. C'est la valeur par défaut.
2. `SOLID`: La variation remplie des icônes.
3. `BRAND`: La variation pour l'utilisation des icônes de marques.

#### Variations `TablerIcon` {#tablericon-variations}

1. `OUTLINE`: La variation à contour des icônes. C'est la valeur par défaut.
2. `FILLED`: La variation remplie des icônes.

```java
// Une variation remplie d'une icône de Font Awesome
Icon music = FontAwesomeIcon.create("user", FontAwesomeIcon.Variate.SOLID);
```

La démo suivante illustre comment utiliser des icônes de différents pools, appliquer des variations et les intégrer de manière transparente dans des composants.

<ComponentDemo
path='/webforj/iconvariations'
files={['src/main/java/com/webforj/samples/views/icon/IconVariationsView.java']}
height='100px'
/>

## Ajouter des icônes aux composants {#adding-icons-to-components}

Intégrez des icônes dans vos composants en utilisant des slots. Les slots offrent des options flexibles pour rendre les composants plus utiles. Il est bénéfique d'ajouter une `Icon` à un composant pour clarifier encore plus le sens prévu aux utilisateurs. Les composants implémentant l'interface `HasPrefixAndSuffix` peuvent inclure une `Icon` ou d'autres composants valides. Les composants ajoutés peuvent être placés dans les slots `prefix` et `suffix` et peuvent améliorer à la fois le design global et l'expérience utilisateur.

En utilisant les slots `prefix` et `suffix`, vous pouvez déterminer si vous souhaitez que l'icône soit placée avant ou après le texte à l'aide des méthodes `setPrefixComponent()` et `setSuffixComponent()`.

Décider de placer une icône avant ou après le texte sur un composant dépend largement de l'objectif et du contexte de conception.

### Placement des icônes : avant VS après {#icon-placement-before-vs-after}

Les icônes positionnées avant le texte du composant aident les utilisateurs à comprendre rapidement l'action principale ou le but du composant, en particulier pour des icônes universellement reconnues comme l'icône de sauvegarde. Les icônes placées avant le texte d'un composant offrent un ordre de traitement logique, guidant les utilisateurs naturellement à travers l'action prévue, ce qui est bénéfique pour les boutons dont la fonction principale est une action immédiate.

D'un autre côté, placer des icônes après le texte du composant est efficace pour des actions qui fournissent un contexte ou des options supplémentaires, améliorant ainsi la clarté et les repères pour la navigation. Les icônes après le texte d'un composant sont idéales pour les composants qui offrent des informations supplémentaires ou guident les utilisateurs dans un flux directionnel.

En fin de compte, la cohérence est essentielle. Une fois que vous choisissez un style, maintenez-le sur l'ensemble de votre site pour un design cohérent et convivial.

<ComponentDemo
path='/webforj/iconprefixsuffix'
files={['src/main/java/com/webforj/samples/views/icon/IconPrefixSuffixView.java']}
height='100px'
/>️

## Création de pools personnalisés {#creating-custom-pools}

Au-delà de l'utilisation des collections d'icônes existantes, vous avez la possibilité de créer un pool personnalisé pouvant être utilisé pour des logos ou des avatars personnalisés. Un pool d'icônes personnalisé peut être stocké dans un répertoire centralisé ou dans le dossier des ressources (contexte), simplifiant le processus de gestion des icônes. Avoir un pool personnalisé rend la création d'applications plus cohérente et réduit l'entretien à travers différents composants et modules.

Des pools personnalisés peuvent être créés à partir d'un dossier contenant des images SVG et en utilisant la classe `IconPoolBuilder`. De là, vous pouvez choisir le nom de votre pool personnalisé et l'utiliser avec les noms de fichiers SVG pour créer des composants d'icônes personnalisés.

```java
// Création d'un pool personnalisé appelé "app-pool" qui a des images pour un logo et un avatar.
IconPoolBuilder.fromDirectory("app-pool", "context://icons");
Icon customLogo = new Icon("logo", "app-pool");
Icon customAvatar = new Icon("avatar-default", "app-pool");
```

:::tip
Assurez-vous de concevoir des icônes avec une largeur et une hauteur égales, car les composants `Icon` sont conçus pour occuper un espace carré.
:::

### Usine de pool personnalisé {#custom-pool-factory}

Vous pouvez également créer une classe d'usine pour un pool personnalisé dans webforJ, tout comme `FeatherIcon`. Cela vous permet de créer et de gérer des ressources d'icônes au sein d'un pool spécifié et d'assurer la complétion de code. Chaque icône peut être instanciée par la méthode `create()`, qui renvoie une `Icon`. La classe d'usine doit fournir des métadonnées spécifiques au pool, telles que le nom du pool et l'identifiant de l'icône, formaté selon le nom de fichier de l'image. Cette conception permet un accès facile et standardisé aux actifs d'icônes du pool personnalisé en utilisant des constantes d'énumération, soutenant la scalabilité et la maintenabilité dans la gestion des icônes.

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

Le snippet suivant montre les deux manières d'utiliser un pool personnalisé.

```java
IconPoolBuilder.fromDirectory("app-pool", "context://icons");

// Créer une icône en utilisant les noms du pool personnalisé et le nom du fichier image
Icon customLogo = new Icon("logo", "app-pool");

// Créer une icône en utilisant l'usine de pools personnalisée du snippet précédent
Icon customLogo = AppPoolIcon.LOGO.create();
```

## Boutons d'icônes {#icon-buttons}
Un composant `Icon` n'est pas sélectionnable, mais pour des actions qui sont mieux représentées par une simple icône, telles que des notifications ou des alertes, vous pouvez utiliser le `IconButton`.

 ```java
IconButton bell = new IconButton(FeatherIcon.BELL.create());
bell.onClick(e -> {
  showMessageDialog("Vous avez un nouveau message !", "Ding Dong !");
});
```

## Meilleures pratiques

- **Accessibilité :** Utilisez un tooltip ou une étiquette sur les icônes pour rendre votre application accessible aux utilisateurs malvoyants qui s'appuient sur des lecteurs d'écran.
- **Évitez l'ambiguïté :** Évitez d'utiliser des icônes si leur signification n'est pas claire ou largement comprise. Si les utilisateurs doivent deviner ce que l'icône représente, cela va à l'encontre de son objectif.
- **Utilisez des icônes avec parcimonie :** Trop d'icônes peuvent submerger les utilisateurs, alors utilisez des icônes uniquement lorsqu'elles apportent de la clarté ou réduisent la complexité.

## Style
Une icône hérite du thème de son composant parent direct, mais vous pouvez le remplacer en appliquant un thème à une `Icon` directement.

### Thèmes
Les composants d'icônes sont livrés avec sept thèmes distincts intégrés pour un style rapide sans utiliser de CSS. Ces thèmes sont des styles prédéfinis qui peuvent être appliqués aux icônes pour changer leur apparence et leur présentation visuelle. Ils offrent un moyen rapide et cohérent de personnaliser l'apparence des icônes tout au long d'une application.

Bien qu'il existe de nombreux cas d'utilisation pour chacun des différents thèmes, voici quelques exemples :

- `DANGER`: Meilleur pour les actions ayant des conséquences graves, telles que la suppression d'informations remplies ou la suppression permanente d'un compte/données.
- `DEFAULT`: Approprié pour les actions dans une application qui ne nécessitent pas d'attention particulière et sont génériques, comme activer ou désactiver un paramètre.
- `PRIMARY`: Approprié en tant qu'"appel à l'action" principal sur une page, comme s'inscrire, enregistrer des modifications ou continuer vers une autre page.
- `SUCCESS`: Excellent pour visualiser l'achèvement réussi d'un élément dans une application, comme la soumission d'un formulaire ou l'achèvement d'un processus d'inscription. Le thème de succès peut être programmé une fois qu'une action réussie a été réalisée.
- `WARNING`: Utile pour indiquer qu'un utilisateur va réaliser une action potentiellement risquée, comme naviguer loin d'une page avec des modifications non enregistrées. Ces actions ont souvent moins d'impact que celles qui utiliseraient le thème Danger.
- `GRAY`: Bon pour des actions subtiles, telles que des réglages mineurs ou des actions qui sont davantage complémentaires à une page et ne font pas partie des fonctionnalités principales.
- `INFO`: Bon pour fournir des informations supplémentaires et clarifiantes à un utilisateur.

<TableBuilder name={['Icon', 'IconButton']} />
