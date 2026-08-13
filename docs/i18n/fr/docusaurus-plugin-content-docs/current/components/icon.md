---
title: Icon
sidebar_position: 55
description: >-
  Render scalable SVG icons with the Icon component from Tabler, Feather, Font
  Awesome, or custom pools loaded on demand from a CDN.
_i18n_hash: 0e51ecab262c62fb63cd767ba8167084
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-icon" />
<DocChip chip='since' label='24.11' />
<JavadocLink type="icons" location="com/webforj/component/icons/Icon" top='true'/>

Le composant `Icon` affiche des icônes qui se scalent à n'importe quelle taille sans perte de qualité. Vous pouvez choisir parmi trois pools d'icônes intégrés ou créer des icônes personnalisées. Les icônes servent de repères visuels pour la navigation et les actions, réduisant ainsi le besoin d'étiquettes textuelles dans votre interface.

<!-- INTRO_END -->

## Bases {#basics}

Chaque `Icon` est conçu comme une image SVG (Scalable Vector Graphics), ce qui signifie qu'il peut facilement se scalper à n'importe quelle taille sans perdre de clarté ou de qualité.
De plus, les composants `Icon` sont chargés à la demande à partir d'un réseau de diffusion de contenu (CDN), ce qui contribue à réduire la latence et à améliorer les performances globales.

Lorsque vous créez une `Icon`, vous devrez identifier un pool spécifique et le nom de l'icône elle-même.
Certaines icônes offrent également le choix entre une version contour ou remplie via [variations](#variations).

<ComponentDemo
path='/webforj/iconbasics'
files={['src/main/java/com/webforj/samples/views/icon/IconBasicsView.java']}
height='100px'
/>

:::tip Le saviez-vous ?
Certains composants, comme `PasswordField` et `TimeField`, ont des icônes intégrées pour aider à transmettre un sens aux utilisateurs finaux.
:::

### Pools {#pools}

Un pool d'icônes est une collection d'icônes couramment utilisées qui permet un accès et une réutilisation faciles. En utilisant des icônes d'un pool d'icônes, vous pouvez vous assurer que les icônes de votre application sont reconnaissables et partagent un style cohérent.
Utiliser webforJ vous permet de choisir parmi trois pools, ou d'implémenter un pool personnalisé.
Chaque pool a une vaste collection d'icônes open source qui sont libres d'utilisation.
Utiliser webforJ vous donne la flexibilité de choisir parmi trois pools et de les utiliser comme des classes uniques, sans le tracas de télécharger directement aucune des icônes.

| Pool d'icônes                                     | Classe webforJ |
| --------                                          | ------- |
| [Tabler](https://tabler-icons.io/)                | `TablerIcon` et `DwcIcon`.<br/>`DwcIcon` est un sous-ensemble des icônes Tabler.|
| [Feather](https://feathericons.com/)              | `FeatherIcon`    |
| [Font Awesome](https://fontawesome.com/search)    | `FontAwesomeIcon`   |

:::tip

Si vous êtes intéressé par la création de votre propre pool d'icônes, consultez [Création de pools personnalisés](#creating-custom-pools).

:::

Une fois que vous avez sélectionné le ou les pools à inclure dans votre application, l'étape suivante consiste à spécifier le nom de l'icône que vous souhaitez utiliser.

### Noms {#names}

Pour inclure une icône dans votre application, il vous suffit de connaître le pool d'icônes et le nom de l'icône. Parcourez le site Web du pool d'icônes pour l'icône que vous souhaitez utiliser, et utilisez le nom de l'icône comme paramètre de la méthode `create()`.
De plus, vous pouvez créer les icônes à travers des énumérations pour les classes `FeatherIcon` et `DwcIcon`, leur permettant d'apparaître dans la complétion de code.

```java
// Créer une icône à partir d'un nom de chaîne
Icon image = TablerIcon.create("image");
// Créer une icône à partir d'une énumération
Icon image = FeatherIcon.IMAGE.create();
```

### Variations {#variations}

Vous pouvez personnaliser encore plus les icônes en utilisant des variations.
Certaines icônes vous permettent de choisir entre une version contour ou remplie, vous permettant d'accentuer une icône spécifique en fonction de votre préférence. Les icônes `FontAwesomeIcon` et `Tabler` offrent des variations.

#### Variations des `FontAwesomeIcon` {#fontawesomeicon-variations}

1. `REGULAR`: La variation contour des icônes. C'est la valeur par défaut.
2. `SOLID`: La variation remplie des icônes.
3. `BRAND`: La variation à utiliser lorsque vous utilisez les icônes de marques.

#### Variations des `TablerIcon` {#tablericon-variations}

1. `OUTLINE`: La variation contour des icônes. C'est la valeur par défaut.
2. `FILLED`: La variation remplie des icônes.

```java
// Une variation remplie d'une icône de Font Awesome
Icon music = FontAwesomeIcon.create("user", FontAwesomeIcon.Variate.SOLID);
```

La démo suivante illustre comment utiliser des icônes de différents pools, appliquer des variations et les intégrer harmonieusement dans des composants.

<ComponentDemo
path='/webforj/iconvariations'
files={['src/main/java/com/webforj/samples/views/icon/IconVariationsView.java']}
height='100px'
/>

## Ajout d'icônes aux composants {#adding-icons-to-components}

Intégrez des icônes dans vos composants en utilisant des slots. Les slots offrent des options flexibles pour rendre les composants plus utiles. Il est avantageux d'ajouter une `Icon` à un composant pour clarifier davantage le sens voulu pour les utilisateurs.
Les composants implémentant l'interface `HasPrefixAndSuffix` peuvent inclure une `Icon` ou d'autres composants valides. Les composants ajoutés peuvent être placés dans les slots `prefix` et `suffix` et peuvent améliorer à la fois le design global et l'expérience utilisateur.

En utilisant les slots `prefix` et `suffix`, vous pouvez déterminer si vous souhaitez que l'icône soit avant ou après le texte en utilisant les méthodes `setPrefixComponent()` et `setSuffixComponent()`.

Décider de placer une icône avant ou après le texte d'un composant dépend largement du but et du contexte de conception.

### Placement des icônes : avant VS après {#icon-placement-before-vs-after}

Les icônes positionnées avant le texte du composant aident les utilisateurs à comprendre rapidement l'action principale ou le but du composant, en particulier pour des icônes universellement reconnues comme l'icône de sauvegarde.
Les icônes avant le texte d'un composant offrent un ordre de traitement logique, guidant les utilisateurs naturellement à travers l'action prévue, ce qui est bénéfique pour les boutons dont la fonction principale est une action immédiate.

D'un autre côté, placer des icônes après le texte du composant est efficace pour des actions qui fournissent un contexte ou des options supplémentaires, améliorant la clarté et les repères pour la navigation.
Les icônes après le texte d'un composant sont idéales pour les composants qui offrent des informations supplémentaires ou guident les utilisateurs dans un flux directionnel.

En fin de compte, la cohérence est essentielle. Une fois que vous avez choisi un style, maintenez-le sur l'ensemble de votre site pour un design cohérent et convivial.

<ComponentDemo
path='/webforj/iconprefixsuffix'
files={['src/main/java/com/webforj/samples/views/icon/IconPrefixSuffixView.java']}
height='100px'
/>️

## Création de pools personnalisés {#creating-custom-pools}

Au-delà de l'utilisation des collections d'icônes existantes, vous avez la possibilité de créer un pool personnalisé qui peut être utilisé pour des logos ou avatars personnalisés.
Un pool d'icônes personnalisé peut être stocké dans un répertoire centralisé ou dans le dossier des ressources, simplifiant le processus de gestion des icônes.
Disposer d'un pool personnalisé rend la création d'applications plus cohérente et réduit la maintenance à travers différents composants et modules.

Des pools personnalisés peuvent être créés à partir d'un dossier contenant des images SVG et en utilisant la classe `IconPoolBuilder`. À partir de là, vous pouvez choisir le nom de votre pool personnalisé et l'utiliser avec les noms de fichiers SVG pour créer des composants d'icônes personnalisés.

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

Vous pouvez également créer une classe d'usine pour un pool personnalisé dans webforJ, tout comme `FeatherIcon`. Cela vous permet de créer et de gérer les ressources icônes au sein d'un pool spécifié et permet la complétion de code.
Chaque icône peut être instanciée via la méthode `create()`, qui retourne une `Icon`. La classe d'usine doit fournir des métadonnées spécifiques au pool, telles que le nom du pool et l'identifiant de l'icône, formaté selon le nom de fichier de l'image.
Ce design permet d'accéder facilement et de manière standardisée aux actifs icônes depuis le pool personnalisé en utilisant des constantes d'énumération, soutenant la scalabilité et la maintenabilité dans la gestion des icônes.

```java
/// Création d'une usine de pool personnalisé pour app-pool
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

Le snippet suivant montre les deux différentes manières d'utiliser un pool personnalisé.

```java
IconPoolBuilder.fromDirectory("app-pool", "context://icons");

// Créer une icône en utilisant les noms du pool personnalisé et du fichier image
Icon customLogo = new Icon("logo", "app-pool");

// Créer une icône en utilisant l'usine du pool personnalisé du snippet précédent
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

- **Accessibilité :** Utilisez un tooltip ou une étiquette sur les icônes pour rendre votre application accessible aux utilisateurs malvoyants s'appuyant sur des lecteurs d'écran.
- **Évitez l'ambiguïté :** Évitez d'utiliser des icônes si la signification n'est pas claire ou largement comprise. Si les utilisateurs doivent deviner ce que représente l'icône, cela va à l'encontre de l'objectif.
- **Utilisez les icônes avec parcimonie :** Trop d'icônes peuvent submerger les utilisateurs, donc utilisez-les uniquement lorsque cela apporte de la clarté ou réduit la complexité.

## Stylisme
Une icône hérite du thème de son composant parent direct, mais vous pouvez le remplacer en appliquant un thème à une `Icon` directement.

### Thèmes
Les composants d'icônes comportent sept thèmes distincts intégrés pour un stylisme rapide sans utiliser de CSS. Ces thèmes sont des styles prédéfinis qui peuvent être appliqués aux icônes pour changer leur apparence et leur présentation visuelle. Ils offrent un moyen rapide et cohérent de personnaliser l'apparence des icônes dans toute une application.

Bien qu'il existe de nombreux cas d'utilisation pour chacun des différents thèmes, voici quelques exemples d'utilisation :

- `DANGER`: Idéal pour des actions aux conséquences graves, telles que la suppression d'informations saisies ou la suppression permanente d'un compte/données.
- `DEFAULT`: Approprié pour des actions au sein d'une application qui ne nécessitent pas d'attention particulière et sont génériques, comme basculer un paramètre.
- `PRIMARY`: Approprié en tant que principal "appel à l'action" sur une page, comme s'inscrire, sauvegarder des modifications ou continuer vers une autre page.
- `SUCCESS`: Excellent pour visualiser l'achèvement réussi d'un élément dans une application, comme la soumission d'un formulaire ou l'achèvement d'un processus d'inscription. Le thème de succès peut être appliqué de manière programmatique une fois qu'une action réussie a été accomplie.
- `WARNING`: Utile pour indiquer qu'un utilisateur est sur le point de réaliser une action potentiellement risquée, comme naviguer hors d'une page avec des modifications non enregistrées. Ces actions sont souvent moins impactantes que celles qui utiliseraient le thème Danger.
- `GRAY`: Bon pour des actions subtiles, telles que des paramètres mineurs ou des actions qui sont plus complémentaires à une page, et qui ne font pas partie de la fonctionnalité principale.
- `INFO`: Bon pour fournir des informations supplémentaires clarificatrices à un utilisateur.

<TableBuilder name={['Icon', 'IconButton']} />
