---
title: Icon
sidebar_position: 55
description: >-
  Render scalable SVG icons with the Icon component from Tabler, Feather, Font
  Awesome, or custom pools loaded on demand from a CDN.
_i18n_hash: c526ee2878756d5dd13fa2972dfef56e
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-icon" />
<DocChip chip='since' label='24.11' />
<JavadocLink type="icons" location="com/webforj/component/icons/Icon" top='true'/>

Le composant `Icon` affiche des icônes qui s'adaptent à n'importe quelle taille sans perdre en qualité. Vous pouvez choisir parmi trois bibliothèques d'icônes intégrées ou créer des icônes personnalisées. Les icônes servent d'indicateurs visuels pour la navigation et les actions, réduisant ainsi le besoin d'étiquettes textuelles dans votre interface.

Chaque `Icon` se rend en tant qu'image Scalable Vector Graphics (SVG), chargée à la demande à partir d'un réseau de distribution de contenu (CDN) afin de maintenir une faible latence. Pour en créer une, choisissez une bibliothèque d'icônes et le nom d'une icône. Certaines icônes offrent également le choix entre une version contournée ou remplie via [variations](#variations).

<!-- INTRO_END -->

<ComponentDemo
path='/webforj/iconbasics'
files={['src/main/java/com/webforj/samples/views/icon/IconBasicsView.java']}
height='100px'
/>

:::tip Saviez-vous ?
Certains composants, comme `PasswordField` et `TimeField`, ont des icônes intégrées pour aider à transmettre une signification aux utilisateurs finaux.
:::

## Bibliothèques {#pools}

Une bibliothèque d'icônes est une collection d'icônes couramment utilisées qui permet un accès facile et une réutilisation. En utilisant des icônes d'une bibliothèque, vous pouvez garantir que les icônes de votre application sont reconnaissables et partagent un style cohérent. 
L'utilisation de webforJ vous permet de choisir parmi trois bibliothèques, ou d'implémenter une bibliothèque personnalisée.
Chaque bibliothèque contient une vaste collection d'icônes open source gratuites à utiliser.
Utiliser webforJ vous donne la flexibilité de choisir parmi trois bibliothèques et de les utiliser comme classes uniques, sans avoir à télécharger directement aucune des icônes.

| Bibliothèque d'icônes                               | Classe webforJ |
| --------------------------------------------------- | --------------- |
| [Tabler](https://tabler-icons.io/)                  | `TablerIcon` et `DwcIcon`.<br/>`DwcIcon` est un sous-ensemble des icônes Tabler.|
| [Feather](https://feathericons.com/)                | `FeatherIcon`    |
| [Font Awesome](https://fontawesome.com/search)      | `FontAwesomeIcon`   |

:::tip

Si vous êtes intéressé par la création de votre propre bibliothèque d'icônes, consultez [Créer des bibliothèques personnalisées](#creating-custom-pools).

:::

Une fois que vous avez sélectionné la ou les bibliothèques à inclure dans votre application, l'étape suivante consiste à spécifier le nom de l'icône que vous souhaitez utiliser.

## Noms {#names}

Pour inclure une icône dans votre application, tout ce dont vous avez besoin est la bibliothèque d'icônes et le nom de l'icône. Parcourez le site web de la bibliothèque d'icônes pour l'icône que vous souhaitez utiliser, et utilisez le nom de l'icône comme paramètre de la méthode `create()`.
De plus, vous pouvez créer les icônes via des énumérations pour les classes `FeatherIcon` et `DwcIcon`, ce qui leur permet d'apparaître dans la complétion de code.

```java
// Créer une icône à partir d'un nom de chaîne
Icon image = TablerIcon.create("image");
// Créer une icône à partir d'une énumération
Icon image = FeatherIcon.IMAGE.create();
```

## Variations {#variations}

Vous pouvez personnaliser encore plus les icônes en utilisant des variations.
Certaines icônes vous permettent de choisir entre une version contournée ou remplie, vous permettant de mettre en évidence une icône spécifique en fonction de votre préférence. Les icônes `FontAwesomeIcon` et `Tabler` offrent des variations.

### Variations `FontAwesomeIcon` {#fontawesomeicon-variations}

1. `REGULAR` : La variation contournée des icônes. C'est la par défaut.
2. `SOLID` : La variation remplie des icônes.
3. `BRAND` : La variation pour les icônes des marques.

### Variations `TablerIcon` {#tablericon-variations}

1. `OUTLINE` : La variation contournée des icônes. C'est la par défaut.
2. `FILLED` : La variation remplie des icônes.

```java
// Une variation remplie d'une icône de Font Awesome
Icon music = FontAwesomeIcon.create("user", FontAwesomeIcon.Variate.SOLID);
```

La démonstration suivante illustre comment utiliser des icônes provenant de différentes bibliothèques, appliquer des variations et les intégrer sans effort dans des composants.

<ComponentDemo
path='/webforj/iconvariations'
files={['src/main/java/com/webforj/samples/views/icon/IconVariationsView.java']}
height='100px'
/>

## Ajout d'icônes aux composants {#adding-icons-to-components}

Intégrez des icônes dans vos composants en utilisant des emplacements (slots). Les emplacements offrent des options flexibles pour rendre les composants plus utiles. Il est bénéfique d'ajouter une `Icon` à un composant pour clarifier davantage le sens prévu aux utilisateurs. 
Les composants implémentant l'interface `HasPrefixAndSuffix` peuvent inclure une `Icon` ou d'autres composants valides. Les composants ajoutés peuvent être placés dans les emplacements `prefix` et `suffix` et peuvent améliorer à la fois le design global et l'expérience utilisateur.

En utilisant les emplacements `prefix` et `suffix`, vous pouvez déterminer si vous souhaitez que l'icône soit avant ou après le texte en utilisant les méthodes `setPrefixComponent()` et `setSuffixComponent()`.

Décider de placer une icône avant ou après le texte d'un composant dépend largement de l'objectif et du contexte de design.

### Placement de l'icône : avant VS après {#icon-placement-before-vs-after}

Les icônes positionnées avant le texte du composant aident les utilisateurs à comprendre rapidement l'action principale ou l'objectif du composant, surtout pour des icônes universellement reconnues comme l'icône de sauvegarde. 
Les icônes avant le texte d'un composant offrent un ordre de traitement logique, guidant les utilisateurs de manière naturelle à travers l'action prévue, ce qui est bénéfique pour les boutons dont la fonction principale est une action immédiate.

D'un autre côté, placer des icônes après le texte d'un composant est efficace pour les actions qui fournissent un contexte ou des options supplémentaires, améliorant la clarté et les indications de navigation. 
Les icônes après le texte d'un composant sont idéales pour les composants qui offrent des informations supplémentaires ou guident les utilisateurs dans un flux directionnel.

En fin de compte, la cohérence est essentielle. Une fois que vous avez choisi un style, maintenez-le à travers votre site pour un design cohérent et convivial.

<ComponentDemo
path='/webforj/iconprefixsuffix'
files={['src/main/java/com/webforj/samples/views/icon/IconPrefixSuffixView.java']}
height='100px'
/>️

## Création de bibliothèques personnalisées {#creating-custom-pools}

Au-delà de l'utilisation de collections d'icônes existantes, vous avez la possibilité de créer une bibliothèque personnalisée qui peut être utilisée pour des logos ou avatars personnalisés. 
Une bibliothèque personnalisée d'icônes peut être stockée dans un répertoire centralisé ou dans le dossier des ressources (contexte), simplifiant le processus de gestion des icônes. 
Avoir une bibliothèque personnalisée rend la création d'applications plus cohérente et réduit la maintenance entre différents composants et modules.

Des bibliothèques personnalisées peuvent être créées à partir d'un dossier contenant des images SVG et en utilisant la classe `IconPoolBuilder`. À partir de là, vous pouvez choisir le nom de votre bibliothèque personnalisée et l'utiliser avec les noms de fichiers SVG pour créer des composants d'icône personnalisés.

```java
// Création d'une bibliothèque personnalisée appelée "app-pool" qui contient des images pour un logo et un avatar.
IconPoolBuilder.fromDirectory("app-pool", "context://icons");
Icon customLogo = new Icon("logo", "app-pool");
Icon customAvatar = new Icon("avatar-default", "app-pool");
```

:::tip
Assurez-vous de concevoir des icônes avec une largeur et une hauteur égales, car les composants `Icon` sont conçus pour occuper un espace carré.
:::

### Fabrique de bibliothèque personnalisée {#custom-pool-factory}

Vous pouvez également créer une classe de fabrique pour une bibliothèque personnalisée dans webforJ, tout comme `FeatherIcon`. Cela vous permet de créer et de gérer des ressources d'icônes au sein d'une bibliothèque spécifiée et de permettre la complétion de code. 
Chaque icône peut être instanciée via la méthode `create()`, qui renvoie une `Icon`. La classe de fabrique doit fournir des métadonnées spécifiques à la bibliothèque, telles que le nom de la bibliothèque et l'identifiant de l'icône, formaté selon le nom de fichier de l'image. 
Ce design permet un accès standardisé facile aux ressources d'icônes de la bibliothèque personnalisée utilisant des constantes d'énumération, soutenant l'évolutivité et la maintenabilité dans la gestion des icônes.

```java
/// Création d'une fabrique de bibliothèque personnalisée pour app-pool
public enum AppPoolIcon implements IconFactory {
  LOGO, AVATAR_DEFAULT;

  public Icon create() {
    return new Icon(String.valueOf(this), this.getPool());
  }

  /**
   * @return le nom de la bibliothèque pour les icônes
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

Le snippet suivant montre les deux manières différentes d'utiliser une bibliothèque personnalisée.

```java
IconPoolBuilder.fromDirectory("app-pool", "context://icons");

// Créer une icône en utilisant les noms de la bibliothèque personnalisée et le fichier image
Icon customLogo = new Icon("logo", "app-pool");

// Créer une icône en utilisant la fabrique de bibliothèque personnalisée de l'extrait précédent
Icon customLogo = AppPoolIcon.LOGO.create();
```

## Boutons d'icône {#icon-buttons}
Un composant `Icon` n'est pas sélectionnable, mais pour des actions qui sont mieux représentées par une simple icône, comme les notifications ou les alertes, vous pouvez utiliser le `IconButton`.

```java
IconButton bell = new IconButton(FeatherIcon.BELL.create());
bell.onClick(e -> {
  showMessageDialog("Vous avez un nouveau message !", "Ding Dong !");
});
```

## Meilleures pratiques

- **Accessibilité :** Utilisez un outil de conseil ou une étiquette sur les icônes pour rendre votre application accessible aux utilisateurs malvoyants qui dépendent des lecteurs d'écran.
- **Évitez l'ambiguïté :** Évitez d'utiliser des icônes si la signification n'est pas claire ou largement comprise. Si les utilisateurs doivent deviner ce que représente l'icône, cela annule le but.
- **Utilisez les icônes avec parcimonie :** Trop d'icônes peuvent submerger les utilisateurs, donc utilisez uniquement des icônes lorsqu'elles ajoutent de la clarté ou réduisent la complexité.

## Style
Une icône hérite du thème de son composant parent direct, mais vous pouvez le remplacer en appliquant un thème directement à une `Icon`.

### Thèmes
Les composants d'icônes viennent avec sept thèmes distincts intégrés pour un stylage rapide sans utilisation de CSS. Ces thèmes sont des styles prédéfinis qui peuvent être appliqués aux icônes pour changer leur apparence et leur présentation visuelle. Ils offrent un moyen rapide et cohérent de personnaliser l'aspect des icônes à travers une application.

Bien qu'il existe de nombreux cas d'utilisation pour chacun des différents thèmes, quelques exemples sont :

- `DANGER` : Meilleur pour les actions aux conséquences graves, telles que vider des informations remplies ou supprimer définitivement un compte ou des données.
- `DEFAULT` : Approprié pour les actions à travers une application qui n'exigent pas d'attention particulière et sont génériques, comme basculer un paramètre.
- `PRIMARY` : Approprié comme principal "appel à l'action" sur une page, comme s'inscrire, sauvegarder des modifications ou continuer vers une autre page.
- `SUCCESS` : Excellent pour visualiser la réussite d'un élément dans une application, comme la soumission d'un formulaire ou la finalisation d'un processus d'inscription. Le thème de réussite peut être appliqué par programmation une fois qu'une action réussie a été complétée.
- `WARNING` : Utile pour indiquer qu'un utilisateur est sur le point d'effectuer une action potentiellement risquée, comme naviguer hors d'une page avec des modifications non sauvegardées. Ces actions sont souvent moins impactantes que celles qui utilisent le thème Danger.
- `GRAY` : Bon pour des actions subtiles, comme des paramètres mineurs ou des actions qui sont plus complémentaires à une page, et non pas partie de la fonctionnalité principale.
- `INFO` : Bon pour fournir des informations supplémentaires clarifiantes à un utilisateur.

<TableBuilder name={['Icon', 'IconButton']} />
