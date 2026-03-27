---
title: Icon
sidebar_position: 55
_i18n_hash: 8350df59fb9ce335776bc0556861cda5
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-icon" />
<DocChip chip='since' label='24.11' />
<JavadocLink type="icons" location="com/webforj/component/icons/Icon" top='true'/>

Le composant `Icon` affiche des icônes qui s'adaptent à toutes les tailles sans perdre en qualité. Vous pouvez choisir parmi trois collections d'icônes intégrées ou créer des icônes personnalisées. Les icônes servent de repères visuels pour la navigation et les actions, réduisant ainsi le besoin d'étiquettes textuelles dans votre interface.

<!-- INTRO_END -->

## Bases {#basics}

Chaque `Icon` est conçu comme une image vectorielle évolutive (SVG), ce qui signifie qu'il peut facilement s'adapter à n'importe quelle taille sans perdre de clarté ou de qualité. De plus, les composants `Icon` sont chargés à la demande depuis un réseau de diffusion de contenu (CDN), ce qui aide à réduire la latence et à améliorer la performance globale.

Lorsque vous créez un `Icon`, vous devez identifier une collection spécifique et le nom de l'icône elle-même. Certaines icônes offrent également le choix entre une version contour ou remplie via [variations](#variations).

<ComponentDemo 
path='/webforj/iconbasics?'  
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/icon/IconBasicsView.java'
height='100px'
/>

:::tip Saviez-vous ?
Certains composants, comme `PasswordField` et `TimeField`, ont des icônes intégrées pour aider à transmettre leur signification aux utilisateurs finaux.
:::

### Collections {#pools}

Une collection d'icônes est un ensemble d'icônes couramment utilisées qui facilite l'accès et la réutilisation. En utilisant des icônes d'une collection, vous pouvez vous assurer que les icônes de votre application sont reconnaissables et partagent un style cohérent. Utiliser webforJ vous permet de choisir parmi trois collections ou d'implémenter une collection personnalisée. Chaque collection possède une vaste collection d'icônes open source gratuites à utiliser. Utiliser webforJ vous donne la flexibilité de choisir parmi trois collections et de les utiliser en tant que classes uniques, sans avoir à télécharger directement l'une des icônes.

| Collection d'icônes                               | Classe webforJ |
| ------------------------------------------------ | -------------- |
| [Tabler](https://tabler-icons.io/)               | `TablerIcon` et `DwcIcon`.<br/>`DwcIcon` est un sous-ensemble des icônes Tabler.|    
| [Feather](https://feathericons.com/)             | `FeatherIcon`    |
| [Font Awesome](https://fontawesome.com/search)   | `FontAwesomeIcon`   |

:::tip

Si vous êtes intéressé à créer votre propre collection d'icônes, voir [Création de collections personnalisées](#creating-custom-pools).

:::

Une fois que vous avez sélectionné la ou les collections à inclure dans votre application, l'étape suivante consiste à spécifier le nom de l'icône que vous souhaitez utiliser.

### Noms {#names}

Pour inclure une icône dans votre application, tout ce dont vous avez besoin est la collection d'icônes et le nom de l'icône. Parcourez le site de la collection d'icônes pour trouver l'icône que vous souhaitez utiliser, et utilisez le nom de l'icône comme paramètre de la méthode `create()`. De plus, vous pouvez créer les icônes via des énumérations pour les classes `FeatherIcon` et `DwcIcon`, ce qui permet qu'elles apparaissent dans la complétion de code.

```java
// Créer une icône à partir d'un nom de chaîne
Icon image = TablerIcon.create("image");
// Créer une icône à partir d'une énumération
Icon image = FeatherIcon.IMAGE.create();
```

### Variations {#variations}

Vous pouvez personnaliser encore plus les icônes en utilisant des variations. Certaines icônes vous permettent de choisir entre une version contour ou remplie, ce qui vous permet de mettre en valeur une icône spécifique selon vos préférences. Les icônes `FontAwesomeIcon` et `Tabler` offrent des variations.

#### Variations des `FontAwesomeIcon` {#fontawesomeicon-variations}

1. `REGULAR`: La variation contour des icônes. C'est la valeur par défaut.
2. `SOLID`: La variation remplie des icônes.
3. `BRAND`: La variation lorsque vous utilisez les icônes de marques.

#### Variations des `TablerIcon` {#tablericon-variations}

1. `OUTLINE`: La variation contour des icônes. C'est la valeur par défaut.
2. `FILLED`: La variation remplie des icônes.

```java
// Une variation remplie d'une icône de Font Awesome
Icon music = FontAwesomeIcon.create("user", FontAwesomeIcon.Variate.SOLID);
```

La démonstration suivante illustre comment utiliser des icônes de différentes collections, appliquer des variations et les intégrer de manière transparente dans les composants.

<ComponentDemo 
path='/webforj/iconvariations?'  
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/icon/IconVariationsView.java'
height='100px'
/>

## Ajout d'icônes aux composants {#adding-icons-to-components}

Intégrez des icônes dans vos composants en utilisant des slots. Les slots offrent des options flexibles pour rendre les composants plus utiles. Il est bénéfique d'ajouter une `Icon` à un composant pour clarifier davantage sa signification aux utilisateurs. Les composants implémentant l'interface `HasPrefixAndSuffix` peuvent inclure une `Icon` ou d'autres composants valides. Les composants ajoutés peuvent être placés dans les slots `prefix` et `suffix` et peuvent améliorer à la fois le design global et l'expérience utilisateur.

En utilisant les slots `prefix` et `suffix`, vous pouvez déterminer si vous souhaitez que l'icône soit placée avant ou après le texte en utilisant les méthodes `setPrefixComponent()` et `setSuffixComponent()`.

Décider de placer une icône avant ou après le texte d'un composant dépend largement du but et du contexte de design.

### Placement des icônes : avant VS après {#icon-placement-before-vs-after}

Les icônes positionnées avant le texte du composant aident les utilisateurs à comprendre rapidement l'action principale ou le but du composant, en particulier pour des icônes universellement reconnues comme l'icône de sauvegarde. Les icônes avant le texte d'un composant offrent un ordre de traitement logique, guidant les utilisateurs de manière naturelle à travers l'action prévue, ce qui est bénéfique pour les boutons dont la fonction principale est une action immédiate.

En revanche, placer les icônes après le texte du composant est efficace pour les actions qui fournissent un contexte ou des options supplémentaires, améliorant la clarté et les indications pour la navigation. Les icônes après le texte d'un composant sont idéales pour les composants qui offrent des informations supplémentaires ou guident les utilisateurs dans un flux directionnel.

En fin de compte, la cohérence est essentielle. Une fois que vous choisissez un style, maintenez-le sur votre site pour un design cohésif et agréable pour l'utilisateur.
   
<ComponentDemo 
path='/webforj/iconprefixsuffix?'  
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/icon/IconPrefixSuffixView.java'
height='100px'
/>️

## Création de collections personnalisées {#creating-custom-pools}

Au-delà de l'utilisation des collections d'icônes existantes, vous avez la possibilité de créer une collection personnalisée qui peut être utilisée pour des logos ou avatars personnalisés. Une collection d'icônes personnalisée peut être stockée dans un répertoire centralisé ou dans le dossier des ressources (contexte), simplifiant ainsi le processus de gestion des icônes. Avoir une collection personnalisée rend la création d'applications plus cohérente et réduit la maintenance à travers différents composants et modules.

Des collections personnalisées peuvent être créées à partir d'un dossier contenant des images SVG et en utilisant la classe `IconPoolBuilder`. À partir de là, vous pouvez choisir le nom de votre collection personnalisée et l'utiliser avec les noms de fichiers SVG pour créer des composants d'icônes personnalisés.

```java
// Création d'une collection personnalisée appelée "app-pool" qui contient des images pour un logo et un avatar.
IconPoolBuilder.fromDirectory("app-pool", "context://icons");
Icon customLogo = new Icon("logo", "app-pool");
Icon customAvatar = new Icon("avatar-default", "app-pool");
```

:::tip
Assurez-vous de concevoir les icônes avec une largeur et une hauteur égales, car les composants `Icon` sont conçus pour occuper un espace carré.
:::

### Fabrique de collections personnalisées {#custom-pool-factory}

Vous pouvez également créer une classe de fabrique pour une collection personnalisée dans webforJ, tout comme `FeatherIcon`. Cela vous permet de créer et de gérer des ressources d'icônes au sein d'une collection spécifiée et de permettre la complétion de code. Chaque icône peut être instanciée via la méthode `create()`, qui retourne un `Icon`. La classe de fabrique doit fournir des métadonnées spécifiques à la collection, telles que le nom de la collection et l'identifiant de l'icône, formatées selon le nom de fichier de l'image. Ce design permet un accès facile et standardisé aux actifs d'icônes de la collection personnalisée en utilisant des constantes d'énumération, soutenant la scalabilité et la maintenabilité dans la gestion des icônes.

```java
/// Création d'une fabrique de collection pour app-pool
public enum AppPoolIcon implements IconFactory {
  LOGO, AVATAR_DEFAULT;

  public Icon create() {
    return new Icon(String.valueOf(this), this.getPool());
  }

  /**
   * @return le nom de la collection pour les icônes
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

Le snippet suivant montre les deux manières différentes d'utiliser une collection personnalisée.

```java
IconPoolBuilder.fromDirectory("app-pool", "context://icons");

// Créer un Icon en utilisant les noms de la collection personnalisée et le fichier image
Icon customLogo = new Icon("logo", "app-pool");

// Créer un Icon en utilisant la fabrique de collection personnalisée du snippet précédent
Icon customLogo = AppPoolIcon.LOGO.create();
```

## Boutons d'icônes {#icon-buttons}
Un composant `Icon` n'est pas sélectionnable, mais pour les actions qui sont mieux représentées par une simple icône, telles que les notifications ou les alertes, vous pouvez utiliser le `IconButton`.

 ```java
IconButton bell = new IconButton(FeatherIcon.BELL.create());
bell.onClick(e -> {
  showMessageDialog("Vous avez un nouveau message !", "Ding Dong !");
});
```

## Meilleures pratiques

- **Accessibilité :** Utilisez un tooltip ou une étiquette sur les icônes pour rendre votre application accessible aux utilisateurs malvoyants qui dépendent des lecteurs d'écran.
- **Évitez l'ambiguïté :** Évitez d'utiliser des icônes si leur signification n'est pas claire ou largement comprise. Si les utilisateurs doivent deviner ce que représente l'icône, cela va à l'encontre de l'objectif.
- **Utilisez les icônes avec parcimonie :** Trop d'icônes peuvent submerger les utilisateurs, utilisez donc des icônes uniquement lorsqu'elles ajoutent de la clarté ou réduisent la complexité.

## Stylisation
Une icône hérite du thème de son composant parent direct, mais vous pouvez le remplacer en appliquant un thème à une `Icon` directement.

### Thèmes
Les composants d'icônes sont livrés avec sept thèmes discrets prédéfinis pour un stylage rapide sans utiliser de CSS. Ces thèmes sont des styles prédéfinis qui peuvent être appliqués aux icônes pour changer leur apparence et leur présentation visuelle. Ils offrent un moyen rapide et cohérent de personnaliser l'apparence des icônes dans une application.

Bien qu'il existe de nombreux cas d'utilisation pour chacun des différents thèmes, quelques exemples sont :

- `DANGER`: Meilleur pour les actions ayant des conséquences graves, telles que la suppression d'informations remplies ou la suppression permanente d'un compte/données.
- `DEFAULT`: Approprié pour les actions dans l'application qui ne nécessitent pas d'attention particulière et sont génériques, telles que le basculement d'un paramètre.
- `PRIMARY`: Approprié comme appel à l'action principal sur une page, tel que s'inscrire, enregistrer des modifications ou continuer vers une autre page.
- `SUCCESS`: Excellent pour visualiser l'achèvement réussi d'un élément dans une application, tel que la soumission d'un formulaire ou l'achèvement d'un processus d'inscription. Le thème de succès peut être appliqué par programmation une fois qu'une action réussie a été effectuée.
- `WARNING`: Utile pour indiquer qu'un utilisateur est sur le point de réaliser une action potentiellement risquée, telle que naviguer loin d'une page avec des modifications non enregistrées. Ces actions sont souvent moins impactantes que celles utilisant le thème Danger.
- `GRAY`: Bon pour des actions discrètes, telles que de petits réglages ou des actions qui sont plus complémentaires à une page et ne font pas partie de la fonctionnalité principale.
- `INFO`: Bon pour fournir des informations supplémentaires clarifiantes à un utilisateur.

<TableBuilder name="Icon" />
