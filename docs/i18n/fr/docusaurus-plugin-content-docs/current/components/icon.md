---
title: Icon
sidebar_position: 55
_i18n_hash: 5c32d2def53818005b15e22290fb3d52
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-icon" />
<DocChip chip='since' label='24.11' />
<JavadocLink type="icons" location="com/webforj/component/icons/Icon" top='true'/>

Le composant `Icon` affiche des icônes qui s'adaptent à toute taille sans perdre en qualité. Vous pouvez choisir parmi trois ensembles d'icônes intégrés ou créer des ensembles personnalisés. Les icônes servent de repères visuels pour la navigation et les actions, réduisant le besoin d'étiquettes textuelles dans votre interface.

<!-- INTRO_END -->

## Bases {#basics}

Chaque `Icon` est conçu comme une image au format Scalable Vector Graphics (SVG), ce qui signifie qu'il peut facilement s'adapter à n'importe quelle taille sans perdre en clarté ni en qualité. De plus, les composants `Icon` sont chargés à la demande à partir d'un réseau de distribution de contenu (CDN), ce qui aide à réduire la latence et à améliorer les performances globales.

Lorsque vous créez une `Icon`, vous devez identifier un ensemble spécifique et le nom de l'icône elle-même. Certaines icônes offrent également le choix entre une version contour ou remplie via [variations](#variations).

<ComponentDemo 
path='/webforj/iconbasics?'  
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/icon/IconBasicsView.java'
height='100px'
/>

:::tip Saviez-vous que ?
Certains composants, comme `PasswordField` et `TimeField`, ont des icônes intégrées pour aider à transmettre un sens aux utilisateurs finaux.
:::

### Ensembles {#pools}

Un ensemble d'icônes est une collection d'icônes couramment utilisées qui permet un accès et une réutilisation faciles. En utilisant des icônes d'un ensemble d'icônes, vous pouvez vous assurer que les icônes dans votre application sont reconnaissables et partagent un style cohérent. L'utilisation de webforJ vous permet de choisir parmi trois ensembles ou de mettre en œuvre un ensemble personnalisé. Chaque ensemble dispose d'une vaste collection d'icônes open source gratuites à utiliser. L'utilisation de webforJ vous donne la flexibilité de choisir parmi trois ensembles et de les utiliser comme classes uniques, sans avoir à télécharger directement les icônes.

| Ensemble d'icônes                                         | Classe webforJ |
| --------                                          | ------- |
| [Tabler](https://tabler-icons.io/)                | `TablerIcon` et `DwcIcon`.<br/>`DwcIcon` est un sous-ensemble des icônes Tabler.|    
| [Feather](https://feathericons.com/)              | `FeatherIcon`    |
| [Font Awesome](https://fontawesome.com/search)    | `FontAwesomeIcon`   |

:::tip

Si vous souhaitez créer votre propre ensemble d'icônes, consultez [Créer des ensembles personnalisés](#creating-custom-pools).

:::

Une fois que vous avez sélectionné l'ensemble ou les ensembles à inclure dans votre application, l'étape suivante consiste à spécifier le nom de l'icône que vous souhaitez utiliser.

### Noms {#names}

Pour inclure une icône dans votre application, tout ce dont vous avez besoin est l'ensemble d'icônes et le nom de l'icône. Parcourez le site web de l'ensemble d'icônes pour l'icône que vous souhaitez utiliser et utilisez le nom de l'icône comme paramètre de la méthode `create()`. De plus, vous pouvez créer les icônes par le biais d'énumérations pour les classes `FeatherIcon` et `DwcIcon`, leur permettant d'apparaître dans la complétion du code.

```java
// Créer une icône à partir d'un nom de chaîne
Icon image = TablerIcon.create("image");
// Créer une icône à partir d'une énumération
Icon image = FeatherIcon.IMAGE.create();
```

### Variations {#variations}

Vous pouvez encore personnaliser les icônes en utilisant des variations. Certaines icônes vous permettent de choisir entre une version contour ou remplie, vous permettant d'appuyer sur une icône spécifique en fonction de vos préférences. Les icônes `FontAwesomeIcon` et `Tabler` offrent des variations.

#### Variations de `FontAwesomeIcon` {#fontawesomeicon-variations}

1. `REGULAR`: La variation contour des icônes. C'est la valeur par défaut.
2. `SOLID`: La variation remplie des icônes.
3. `BRAND`: La variation à utiliser lorsque vous utilisez des icônes de marques.

#### Variations de `TablerIcon` {#tablericon-variations}

1. `OUTLINE`: La variation contour des icônes. C'est la valeur par défaut.
2. `FILLED`: La variation remplie des icônes.

```java
// Une variation remplie d'une icône de Font Awesome
Icon music = FontAwesomeIcon.create("user", FontAwesomeIcon.Variate.SOLID);
```

La démo suivante illustre comment utiliser des icônes de différents ensembles, appliquer des variations et les intégrer sans effort dans des composants.

<ComponentDemo 
path='/webforj/iconvariations?'  
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/icon/IconVariationsView.java'
height='100px'
/>

## Ajouter des icônes aux composants {#adding-icons-to-components}

Intégrez des icônes dans vos composants en utilisant des slots. Les slots offrent des options flexibles pour rendre les composants plus utiles. Il est bénéfique d'ajouter une `Icon` à un composant pour clarifier davantage le sens prévu pour les utilisateurs. Les composants implémentant l'interface `HasPrefixAndSuffix` peuvent inclure une `Icon` ou d'autres composants valides. Les composants ajoutés peuvent être placés dans les slots `prefix` et `suffix` et peuvent améliorer à la fois la conception globale et l'expérience utilisateur.

En utilisant les slots `prefix` et `suffix`, vous pouvez déterminer si vous souhaitez que l'icône soit avant ou après le texte en utilisant les méthodes `setPrefixComponent()` et `setSuffixComponent()`.

Décider de placer une icône avant ou après le texte sur un composant dépend largement du but et du contexte de conception.

### Placement de l'icône : avant VS après {#icon-placement-before-vs-after}

Les icônes positionnées avant le texte du composant aident les utilisateurs à comprendre rapidement l'action principale ou l'objectif du composant, en particulier pour des icônes universellement reconnues comme l'icône de sauvegarde. Les icônes avant le texte d'un composant offrent un ordre logique de traitement, guidant les utilisateurs de manière naturelle à travers l'action prévue, ce qui est bénéfique pour des boutons dont la fonction principale est une action immédiate.

D'un autre côté, placer des icônes après le texte du composant est efficace pour les actions qui fournissent un contexte ou des options supplémentaires, améliorant la clarté et les repères pour la navigation. Les icônes après le texte d'un composant sont idéales pour les composants qui offrent des informations supplémentaires ou guident les utilisateurs dans un flux directionnel.

Finalement, la cohérence est essentielle. Une fois que vous choisissez un style, maintenez-le sur votre site pour une conception cohérente et conviviale.

<ComponentDemo 
path='/webforj/iconprefixsuffix?'  
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/icon/IconPrefixSuffixView.java'
height='100px'
/>️

## Créer des ensembles personnalisés {#creating-custom-pools}

Au-delà de l'utilisation des collections d'icônes existantes, vous avez la possibilité de créer un ensemble personnalisé qui peut être utilisé pour des logos ou avatars personnalisés. Un ensemble personnalisé d'icônes peut être stocké dans un répertoire centralisé ou dans le dossier des ressources (contexte), simplifiant ainsi le processus de gestion des icônes. Avoir un ensemble personnalisé rend la création d'applications plus cohérente et réduit la maintenance à travers différents composants et modules.

Des ensembles personnalisés peuvent être créés à partir d'un dossier contenant des images SVG et en utilisant la classe `IconPoolBuilder`. À partir de là, vous pouvez choisir le nom de votre ensemble personnalisé et l'utiliser avec les noms de fichiers SVG pour créer des composants d'icônes personnalisés.

```java
// Création d'un ensemble personnalisé appelé "app-pool" qui a des images pour un logo et un avatar.
IconPoolBuilder.fromDirectory("app-pool", "context://icons");
Icon customLogo = new Icon("logo", "app-pool");
Icon customAvatar = new Icon("avatar-default", "app-pool");
```

:::tip
Assurez-vous de concevoir des icônes avec une largeur et une hauteur égales, car les composants `Icon` sont conçus pour occuper un espace carré.
:::

### Usine d'ensembles personnalisés {#custom-pool-factory}

Vous pouvez également créer une classe d'usine pour un ensemble personnalisé dans webforJ, tout comme `FeatherIcon`. Cela vous permet de créer et de gérer des ressources d'icônes au sein d'un ensemble spécifié et de permettre la complétion du code. Chaque icône peut être instanciée via la méthode `create()`, qui retourne une `Icon`. La classe d'usine doit fournir des métadonnées spécifiques à l'ensemble, telles que le nom de l'ensemble et l'identifiant de l'icône, formaté selon le nom de fichier de l'image. Cette conception permet un accès facile et standardisé aux actifs d'icônes depuis l'ensemble personnalisé en utilisant des constantes d'énumération, soutenant l'évolutivité et la maintenabilité dans la gestion des icônes.

```java
/// Création d'une usine d'ensembles personnalisés pour app-pool
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

Le code suivant montre les deux manières différentes d'utiliser un ensemble personnalisé.

```java
IconPoolBuilder.fromDirectory("app-pool", "context://icons");

// Créer une icône en utilisant les noms de l'ensemble personnalisé et du fichier image
Icon customLogo = new Icon("logo", "app-pool");

// Créer une icône en utilisant l'usine d'ensemble personnalisé du snippet précédent
Icon customLogo = AppPoolIcon.LOGO.create();
```

## Boutons d'icônes {#icon-buttons}
Un composant `Icon` n'est pas sélectionnable, mais pour des actions qui sont mieux représentées par une simple icône, comme des notifications ou des alertes, vous pouvez utiliser le `IconButton`.

 ```java
IconButton bell = new IconButton(FeatherIcon.BELL.create());
bell.onClick(e -> {
  showMessageDialog("Vous avez un nouveau message !", "Ding Dong !");
});
```

## Meilleures pratiques

- **Accessibilité :** Utilisez une infobulle ou une étiquette sur les icônes pour rendre votre application accessible aux utilisateurs malvoyants s'appuyant sur des lecteurs d'écran.
- **Évitez l'ambiguïté :** Évitez d'utiliser des icônes si leur signification n'est pas claire ou largement comprise. Si les utilisateurs doivent deviner ce que représente l'icône, cela va à l'encontre de l'objectif.
- **Utilisez des icônes avec parcimonie :** Trop d'icônes peuvent submerger les utilisateurs, donc n'utilisez des icônes que lorsqu'elles ajoutent de la clarté ou réduisent la complexité.

## Style
Une icône hérite du thème de son composant parent direct, mais vous pouvez le remplacer en appliquant un thème à une `Icon` directement.

### Thèmes
Les composants d'icônes sont livrés avec sept thèmes distincts intégrés pour un style rapide sans avoir besoin de CSS. Ces thèmes sont des styles prédéfinis qui peuvent être appliqués aux icônes pour modifier leur apparence et leur présentation visuelle. Ils offrent une manière rapide et cohérente de personnaliser l'apparence des icônes dans toute l'application.

Bien qu'il existe de nombreux cas d'utilisation pour chacun des différents thèmes, voici quelques exemples :

- `DANGER` : Meilleur pour les actions aux conséquences graves, comme effacer des informations remplies ou supprimer définitivement un compte/des données.
- `DEFAULT` : Approprié pour les actions dans le cadre d'une application qui ne nécessitent pas d'attention particulière et sont génériques, comme changer un paramètre.
- `PRIMARY` : Approprié comme principal "appel à l'action" sur une page, comme s'inscrire, sauvegarder des modifications ou continuer vers une autre page.
- `SUCCESS` : Excellent pour visualiser l'achèvement réussi d'un élément dans une application, comme la soumission d'un formulaire ou l'achèvement d'un processus d'inscription. Le thème de succès peut être appliqué de manière programmatique une fois qu'une action réussie a été complétée.
- `WARNING` : Utile pour indiquer qu'un utilisateur est sur le point d'effectuer une action potentiellement risquée, comme naviguer loin d'une page avec des modifications non enregistrées. Ces actions sont souvent moins impactantes que celles qui utiliseraient le thème Danger.
- `GRAY` : Bon pour des actions subtiles, comme des paramètres mineurs ou des actions qui sont plus complémentaires à une page, et qui ne font pas partie de la fonctionnalité principale.
- `INFO` : Bon pour fournir des informations supplémentaires clarificatrices à un utilisateur.

<TableBuilder name={['Icon', 'IconButton']} />
