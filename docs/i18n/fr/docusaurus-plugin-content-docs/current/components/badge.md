---
title: Badge
sidebar_position: 8
sidebar_class_name: new-content
_i18n_hash: 112f61dea5c6c0d434267a25ccc61b9e
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-badge" />
<DocChip chip='since' label='25.12' />
<JavadocLink type="badge" location="com/webforj/component/badge/Badge" top='true'/>

Un `Badge` est une étiquette compacte et visuellement distincte utilisée pour transmettre un statut, des comptes ou de courtes pièces d'informations contextuelles. Que vous ayez besoin de signaler un compte de notification, de marquer un élément comme "Nouveau" ou d'attirer l'attention sur un avertissement, les badges vous offrent un moyen léger de faire remonter cette information directement dans l'UI.

<!-- INTRO_END -->

:::tip Utilisation d’un `Badge`
Les badges fonctionnent bien pour les comptes de notification, les étiquettes de statut et les métadonnées courtes comme les balises de version ou les états de publication. Gardez le texte du badge à un ou deux mots afin que l'étiquette soit lisible d'un coup d'œil.
:::

## Création d'un badge {#creating-a-badge}

Le badge le plus simple prend une chaîne de texte. Vous pouvez également passer un `BadgeTheme` directement dans le constructeur pour définir immédiatement le style visuel. Le constructeur sans argument est disponible lorsque vous devez créer un badge dynamiquement et le configurer après sa création.

```java
Badge badge = new Badge("Nouveau");

// Avec un thème
Badge primary = new Badge("En vedette", BadgeTheme.SUCCESS);

// Créé dynamiquement
Badge status = new Badge();
status.setLabel("En attente");
status.setTheme(BadgeTheme.WARNING);
```

## Étiquette {#label}

Vous pouvez définir ou mettre à jour le contenu textuel d'un badge à tout moment avec `setLabel()`. La méthode `setText()` est un alias pour la même opération ; utilisez celle qui semble la plus naturelle dans le contexte. Les deux ont des accesseurs correspondants, `getLabel()` et `getText()`, si vous devez lire la valeur actuelle.

```java
Badge badge = new Badge();
badge.setLabel("Mise à jour");

// Équivalent
badge.setText("Mise à jour");

// Lire la valeur actuelle
String current = badge.getLabel();
```

## Icônes {#icons}

Parfois, une approche plus visuelle est utile pour transmettre des informations avec un `Badge`. Les badges prennent en charge le contenu d'icône. Passez une icône avec le texte utilisant le constructeur `Badge(String, Component...)`, ou passez une icône seule pour créer un badge uniquement iconique. Lorsqu'il est combiné avec du texte, l'icône s'affiche à gauche de l'étiquette.

Les badges uniquement iconiques fonctionnent particulièrement bien pour des indicateurs de statut compacts dans des mises en page denses où un mot court semblerait encombré. Associer une icône avec du texte est un bon compromis lorsque l'icône seule pourrait être ambiguë. Un symbole de statut est largement compris tout seul, mais ajouter une étiquette de texte courte élimine les suppositions pour les nouveaux utilisateurs. Vous pouvez passer plusieurs composants au constructeur si vous avez besoin de composer un préfixe plus riche, bien qu'en pratique, une seule icône soit le motif le plus courant.

<!-- vale off -->
<ComponentDemo
path='/webforj/badgeicons?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/badge/BadgeIconsView.java'
height='320px'
/>
<!-- vale on -->

```java
// Icône avec texte
Badge done = new Badge("Fait", FeatherIcon.CHECK_CIRCLE.create());
done.setTheme(BadgeTheme.SUCCESS);

// Icône seule
Badge check = new Badge(FeatherIcon.CHECK.create());
check.setTheme(BadgeTheme.SUCCESS);
```

## Utilisation dans d'autres composants {#usage-in-other-components}

### Boutons {#buttons}

Attachez un `Badge` à un `Button` en utilisant `setBadge()`. Le badge apparaît dans le coin supérieur droit du bouton, chevauchant le bord du bouton. C'est un motif courant pour les comptes de notification sur les actions de la barre d'outils ou les boutons d'icône. Comme le badge est un composant autonome, il est entièrement indépendant du thème et de la taille du bouton. Vous pouvez associer un bouton principal avec un badge danger, ou un bouton fantôme avec un badge succès, et chaque côté de la combinaison s'auto-styled sans conflit. Mettre à jour le compte plus tard est aussi simple que d'appeler `badge.setLabel()` avec une nouvelle valeur ; le bouton n'a pas besoin d'être touché.

<!-- vale off -->
<ComponentDemo
path='/webforj/badgebuttons?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/badge/BadgeButtonsView.java'
height='250px'
/>
<!-- vale on -->

### Volet à onglets {#tabbed-pane}

Ajoutez un `Badge` comme suffixe sur un `Tab` en utilisant `setSuffixComponent()`. C'est un ajustement naturel pour les comptes de style boîte de réception ou les indicateurs de statut sur chaque onglet. C'est le genre de motif que vous voyez dans les clients de messagerie ou les gestionnaires de tâches où il est important de signaler l'activité dans chaque section d'un coup d'œil. Le badge se trouve à l'extrémité arrière de l'étiquette d'onglet, après tout contenu préfixe, et reste visible quelle que soit l'onglet actuellement actif. Cette persistance est intentionnelle : cacher le badge sur des onglets inactifs rendrait plus difficile de savoir quelles sections nécessitent une attention sans passer à chacune d'elles.

<!-- vale off -->
<ComponentDemo
path='/webforj/badgetabbedpane?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/badge/BadgeTabbedPaneView.java'
height='325px'
/>
<!-- vale on -->

## Style {#styling}

Les badges prennent en charge plusieurs dimensions de style : les couleurs de thème pour transmettre un sens, une échelle d'expansion pour contrôler la taille, et les propriétés CSS pour une personnalisation précise.

### Thèmes {#themes}

Comme pour de nombreux composants dans webforJ, le `Badge` existe en quatorze thèmes : sept variants remplis et sept variants en contour.

Les thèmes remplis utilisent un fond solide et calculent automatiquement une couleur de texte qui respecte les exigences de contraste. Les variants en contour utilisent plutôt un fond teinté avec une bordure colorée, ce qui en fait une option plus subtile lorsque vous voulez que le badge complète le contenu environnant plutôt que de le dominer.

Appliquez un thème en utilisant `setTheme()` ou via le constructeur.

<!-- vale off -->
<ComponentDemo
path='/webforj/badgethemes?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/badge/BadgeThemesView.java'
height='260px'
/>
<!-- vale on -->

### Couleur personnalisée {#custom-color}

Si les thèmes intégrés ne correspondent pas à votre palette, définissez une couleur de graine personnalisée en utilisant la propriété CSS `--dwc-badge-seed`. À partir de cette seule valeur, le badge dérive automatiquement les couleurs de fond, de texte et de bordure, de sorte que chaque combinaison reste lisible sans que vous deviez spécifier chacune individuellement. Cela signifie que vous pouvez marquer un badge à n'importe quelle couleur de votre système de design en toute confiance. Les valeurs de Teinte, Saturation et Luminosité (HSL) sont particulièrement pratiques ici ; échanger la teinte seule suffit à produire une famille de couleurs complètement différente tout en gardant le contraste intact.

```java
Badge badge = new Badge("Personnalisé");
badge.setStyle("--dwc-badge-seed", "hsl(262, 52%, 47%)");
```

### Dimensionnement {#sizing}

Utilisez `setExpanse()` pour contrôler la taille du badge. Neuf tailles sont disponibles, allant de `XXXSMALL` à `XXXLARGE`, et la taille par défaut est `SMALL`.

<!-- vale off -->
<ComponentDemo
path='/webforj/badgesizes?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/badge/BadgeSizesView.java'
height='260px'
/>
<!-- vale on -->

### Parts et variables CSS {#parts-and-css-variables}

<TableBuilder name="Badge" />
