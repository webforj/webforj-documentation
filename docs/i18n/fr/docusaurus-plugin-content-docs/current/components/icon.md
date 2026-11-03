---
title: Icon
sidebar_position: 55
description: >-
  Render scalable SVG icons with the Icon component from Tabler, Feather, Font
  Awesome, or custom pools loaded on demand from a CDN.
_i18n_hash: f1572fcb6d1396a20fac32bfa282c091
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-icon" />
<DocChip chip='since' label='24.11' />
<JavadocLink type="icons" location="com/webforj/component/icons/Icon" top='true'/>

Le composant `Icon` affiche des icônes qui se mettent à l'échelle à n'importe quelle taille sans perte de qualité. Vous pouvez choisir parmi trois groupes d'icônes intégrés ou créer des icônes personnalisées. Les icônes servent de repères visuels pour la navigation et les actions, réduisant le besoin d'étiquettes textuelles dans votre interface.

Chaque `Icon` est rendu comme une image Scalable Vector Graphics (SVG), chargée à la demande à partir d'un réseau de distribution de contenu (CDN) pour maintenir une faible latence. Pour en créer une, choisissez un groupe d'icônes et le nom d'une icône. Certaines icônes offrent également le choix entre une version contour ou remplie via [variations](#variations).

<!-- INTRO_END -->

<ComponentDemo
path='/webforj/iconbasics'
files={['src/main/java/com/webforj/samples/views/icon/IconBasicsView.java']}
height='100px'
/>

:::tip Le saviez-vous ?
Certains composants, comme `PasswordField` et `TimeField`, ont des icônes intégrées pour aider à transmettre un sens aux utilisateurs finaux.
:::

## Groupes {#pools}

Un groupe d'icônes est une collection d'icônes couramment utilisées qui permet un accès et une réutilisation faciles. En utilisant des icônes d'un groupe, vous pouvez vous assurer que les icônes de votre application sont reconnaissables et partagent un style cohérent. 
L'utilisation de webforJ vous permet de choisir parmi trois groupes ou d'implémenter un groupe personnalisé. 
Chaque groupe a une vaste collection d'icônes open source qui sont gratuites à utiliser. 
L'utilisation de webforJ vous donne la flexibilité de choisir parmi trois groupes et de les utiliser comme classes uniques, sans le tracas de télécharger directement les icônes.

| Groupe d'icônes                                     | Classe webforJ |
| -------------------------------------------------- | -------------- |
| [Tabler](https://tabler-icons.io/)                 | `TablerIcon` et `DwcIcon`.<br/>`DwcIcon` est un sous-ensemble des icônes Tabler. |
| [Feather](https://feathericons.com/)               | `FeatherIcon`    |
| [Font Awesome](https://fontawesome.com/search)     | `FontAwesomeIcon`   |

:::tip

Si vous êtes intéressé par la création de votre propre groupe d'icônes, consultez [Création de groupes personnalisés](#creating-custom-pools).

:::

Une fois que vous avez sélectionné le groupe ou les groupes à inclure dans votre application, la prochaine étape consiste à spécifier le nom de l'icône que vous souhaitez utiliser.

## Noms {#names}

Pour inclure une icône dans votre application, il vous suffit du groupe d'icônes et du nom de l'icône. Parcourez le site Web du groupe d'icônes pour trouver l'icône que vous souhaitez utiliser et utilisez le nom de l'icône comme paramètre de la méthode `create()`. 
De plus, vous pouvez créer des icônes via des énumérations pour les classes `FeatherIcon` et `DwcIcon`, permettant leur apparition dans la complétion de code.

```java
// Créer une icône à partir d'un nom de chaîne
Icon image = TablerIcon.create("image");
// Créer une icône à partir d'une énumération
Icon image = FeatherIcon.IMAGE.create();
```

## Variations {#variations}

Vous pouvez personnaliser encore plus les icônes en utilisant des variations. 
Certaines icônes vous permettent de choisir entre une version contour ou remplie, permettant de mettre en évidence une icône spécifique en fonction de votre préférence. Les icônes `FontAwesomeIcon` et `Tabler` offrent des variations.

### Variations de `FontAwesomeIcon` {#fontawesomeicon-variations}

1. `REGULAR` : La variation contour des icônes. C'est la valeur par défaut.
2. `SOLID` : La variation remplie des icônes.
3. `BRAND` : La variation pour les icônes de marques.

### Variations de `TablerIcon` {#tablericon-variations}

1. `OUTLINE` : La variation contour des icônes. C'est la valeur par défaut.
2. `FILLED` : La variation remplie des icônes.

```java
// Une variation remplie d'une icône de Font Awesome
Icon music = FontAwesomeIcon.create("user", FontAwesomeIcon.Variate.SOLID);
```

La démo suivante illustre comment utiliser des icônes de différents groupes, appliquer des variations, et les intégrer de manière transparente dans des composants.

<ComponentDemo
path='/webforj/iconvariations'
files={['src/main/java/com/webforj/samples/views/icon/IconVariationsView.java']}
height='100px'
/>

## Ajouter des icônes aux composants {#adding-icons-to-components}

Intégrez des icônes dans vos composants à l'aide des slots. Les slots offrent des options flexibles pour rendre les composants plus utiles. Il est avantageux d'ajouter un `Icon` à un composant pour clarifier davantage la signification auprès des utilisateurs. 
Les composants implémentant l'interface `HasPrefixAndSuffix` peuvent inclure un `Icon` ou d'autres composants valides. Les composants ajoutés peuvent être placés dans les slots `prefix` et `suffix` et peuvent améliorer à la fois le design général et l'expérience utilisateur.

En utilisant les slots `prefix` et `suffix`, vous pouvez déterminer si vous souhaitez que l'icône soit avant ou après le texte en utilisant les méthodes `setPrefixComponent()` et `setSuffixComponent()`.

Décider de placer une icône avant ou après le texte d'un composant dépend largement de l'objectif et du contexte du design.

### Placement d'icône : avant contre après {#icon-placement-before-vs-after}

Les icônes placées avant le texte du composant aident les utilisateurs à comprendre rapidement l'action principale ou l'objectif du composant, en particulier pour les icônes universellement reconnues comme l'icône de sauvegarde. 
Les icônes avant le texte d'un composant offrent un ordre de traitement logique, guidant les utilisateurs naturellement à travers l'action prévue, ce qui est bénéfique pour les boutons dont la fonction principale est une action immédiate.

En revanche, placer les icônes après le texte du composant est efficace pour des actions qui fournissent un contexte ou des options supplémentaires, améliorant la clarté et les repères pour la navigation. 
Les icônes après le texte d'un composant sont idéales pour les composants qui offrent des informations supplémentaires ou guident les utilisateurs dans un flux directionnel.

En fin de compte, la cohérence est essentielle. Une fois que vous choisissez un style, maintenez-le sur votre site pour un design cohésif et convivial.

<ComponentDemo
path='/webforj/iconprefixsuffix'
files={['src/main/java/com/webforj/samples/views/icon/IconPrefixSuffixView.java']}
height='100px'
/>️

## Création de groupes personnalisés {#creating-custom-pools}

Au-delà de l'utilisation des collections d'icônes existantes, vous avez la possibilité de créer un groupe personnalisé qui peut être utilisé pour des logos ou des avatars personnalisés. 
Un groupe personnalisé d'icônes peut être stocké dans un répertoire centralisé ou dans le dossier des ressources (contexte), simplifiant le processus de gestion des icônes. 
Avoir un groupe personnalisé rend la création d'applications plus cohérente et réduit la maintenance à travers différents composants et modules.

Des groupes personnalisés peuvent être créés à partir d'un dossier contenant des images SVG et en utilisant la classe `IconPoolBuilder`. De là, vous pouvez choisir le nom de votre groupe personnalisé et l'utiliser avec les noms de fichiers SVG pour créer des composants d'icônes personnalisés.

```java
// Création d'un groupe personnalisé appelé "app-pool" qui contient des images pour un logo et un avatar.
IconPoolBuilder.fromDirectory("app-pool", "context://icons");
Icon customLogo = new Icon("logo", "app-pool");
Icon customAvatar = new Icon("avatar-default", "app-pool");
```

:::tip
Assurez-vous de concevoir des icônes avec une largeur et une hauteur égales, car les composants `Icon` sont conçus pour occuper un espace carré.
:::

### Fabrique de groupes personnalisés {#custom-pool-factory}

Vous pouvez également créer une classe de fabrique pour un groupe personnalisé dans webforJ, tout comme `FeatherIcon`. Cela vous permet de créer et de gérer des ressources d'icônes dans un groupe spécifié et de permettre une complétion de code. 
Chaque icône peut être instanciée via la méthode `create()`, qui retourne un `Icon`. La classe de fabrique doit fournir des métadonnées spécifiques au groupe, telles que le nom du groupe et l'identifiant de l'icône, formatées selon le nom de fichier de l'image. 
Ce design permet un accès facile et standardisé aux actifs d'icônes depuis le groupe personnalisé en utilisant des constantes d'énumération, soutenant l'évolutivité et la maintenabilité dans la gestion des icônes.

```java
/// Création d'une fabrique de groupes personnalisés pour app-pool
public enum AppPoolIcon implements IconFactory {
  LOGO, AVATAR_DEFAULT;

  public Icon create() {
    return new Icon(String.valueOf(this), this.getPool());
  }

  /**
   * @return le nom du groupe pour les icônes
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

Le snippet suivant montre les deux façons différentes d'utiliser un groupe personnalisé.

```java
IconPoolBuilder.fromDirectory("app-pool", "context://icons");

// Créer une icône en utilisant les noms du groupe personnalisé et du fichier image
Icon customLogo = new Icon("logo", "app-pool");

// Créer une icône en utilisant la fabrique de groupes personnalisés du snippet précédent
Icon customLogo = AppPoolIcon.LOGO.create();
```

## Boutons d'icônes <DocChip chip="name" label="dwc-icon-button" /> {#icon-buttons}
Un composant `Icon` n'est pas sélectionnable, mais pour des actions qui sont mieux représentées par une seule icône, comme des notifications ou des alertes, vous pouvez utiliser le `IconButton`.

 ```java
IconButton bell = new IconButton(FeatherIcon.BELL.create());
bell.onClick(e -> {
  showMessageDialog("Vous avez un nouveau message !", "Ding Dong !");
});
```

## Meilleures pratiques

- **Accessibilité :** Utilisez une info-bulle ou une étiquette sur les icônes pour rendre votre application accessible aux utilisateurs malvoyants qui comptent sur les lecteurs d'écran.
- **Éviter l'ambiguïté :** Évitez d'utiliser des icônes si le sens n'est pas clair ou largement compris. Si les utilisateurs doivent deviner ce que représente l'icône, cela va à l'encontre de l'objectif.
- **Utilisez les icônes avec parcimonie :** Trop d'icônes peuvent submerger les utilisateurs, utilisez donc les icônes seulement lorsque cela ajoute de la clarté ou réduit la complexité.

## Style
Une icône hérite du thème de son composant parent direct, mais vous pouvez remplacer cela en appliquant un thème à une `Icon` directement.

### Thèmes
Les composants d'icônes sont livrés avec sept thèmes discrets intégrés pour un style rapide sans utiliser CSS. Ces thèmes sont des styles pré-définis qui peuvent être appliqués aux icônes pour modifier leur apparence et leur présentation visuelle. Ils offrent une manière rapide et cohérente de personnaliser l'apparence des icônes à travers une application.

Bien qu'il y ait de nombreux cas d'utilisation pour chacun des différents thèmes, quelques exemples d'utilisation sont :

- `DANGER` : Meilleur pour les actions ayant de graves conséquences, telles que l'effacement d'informations remplies ou la suppression permanente d'un compte/de données.
- `DEFAULT` : Approprié pour les actions dans une application qui ne nécessitent pas d'attention particulière et sont génériques, comme basculer un paramètre.
- `PRIMARY` : Approprié comme un principal "appel à l'action" sur une page, comme s'inscrire, enregistrer des modifications ou continuer vers une autre page.
- `SUCCESS` : Excellent pour visualiser l'achèvement réussi d'un élément dans une application, comme la soumission d'un formulaire ou la finalisation d'un processus d'inscription. Le thème de succès peut être appliqué par programme une fois qu'une action réussie a été effectuée.
- `WARNING` : Utile pour indiquer qu'un utilisateur est sur le point d'effectuer une action potentiellement risquée, comme naviguer loin d'une page avec des modifications non enregistrées. Ces actions sont souvent moins impactantes que celles qui utiliseraient le thème Danger.
- `GRAY` : Bon pour des actions subtiles, telles que des paramètres mineurs ou des actions qui sont plus complémentaires à une page, et non partie de la fonctionnalité principale.
- `INFO` : Bon pour fournir des informations supplémentaires clarifiant un utilisateur.

<TableBuilder name={['Icon', 'IconButton']} />
