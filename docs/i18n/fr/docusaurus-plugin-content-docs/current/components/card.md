---
title: Card
sidebar_position: 17
sidebar_class_name: new-content
description: >-
  Group related content and actions with the Card component, including slotted
  regions. orientation, elevation, dividers, and click handling.
_i18n_hash: 08b0239bc5bbeb0b14f3b03dda7b8b17
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-card" />
<DocChip chip='since' label='26.02' />
<JavadocLink type="card" location="com/webforj/component/card/Card" top='true'/>

Le composant `Card` fournit une surface pour regrouper du contenu et des actions associés en un seul élément. Il prend en charge des régions en slots pour une figure, un en-tête, un corps et un pied de page, ainsi que des paramètres d'orientation, d'élévation, de séparateur et de densité qui contrôlent la présentation de la carte.

<!-- INTRO_END -->

## Création d'une `Card` {#creating-a-card}

Créez une `Card` en passant du contenu à son constructeur, qui place ce contenu dans le corps de la carte. Le corps peut également être rempli après la création avec `add()` ou `addToBody()`, qui font la même chose.

```java
Card card = new Card(new Paragraph("Les ventes ont augmenté dans toutes les régions."));

//Équivalent
Card card = new Card();
card.addToBody(new Paragraph("Les ventes ont augmenté dans toutes les régions."));
```

Une `Card` vide rend son cadre et rien d'autre.

## Régions de la carte {#card-regions}

Chaque région, à part le corps, est remplie par son propre slot, et une région dont le slot ne contient aucun contenu n'est pas rendue. Une `Card` sans pied de page se termine après le corps, et une `Card` avec un corps seul est un bloc de contenu encadré.

- `addToFigure()` contient l'illustration de la carte, comme une image, une vidéo ou un graphique. Sa position dépend de l'orientation de la carte.
- `addToIcon()` définit le visuel principal dans la ligne d'en-tête et accepte tout composant, y compris un `Icon` ou un `Avatar`.
- `addToTitle()` définit le titre dans la ligne d'en-tête.
- `addToCaption()` ajoute une ligne secondaire sous le titre, utile pour une date, un auteur ou un statut.
- `addToHeaderActions()` remplit la fin de la ligne d'en-tête, généralement avec un `Button` ou un menu.
- `addToFooter()` ferme la `Card`, généralement avec des actions ou des métadonnées.

```java
Card card = new Card(new Paragraph("Les ventes ont augmenté dans toutes les régions."));
card.addToFigure(new Img("cover.png", "Couverture du rapport"))
    .addToIcon(TablerIcon.create("chart-bar"))
    .addToTitle(new H3("Rapport mensuel"))
    .addToCaption(new Paragraph("Juillet 2026"))
    .addToHeaderActions(new Button("Partager"))
    .addToFooter(new Button("En savoir plus"));
````

:::info Titre et nom accessible
Une `Card` s'annonce comme une région, et le titre devient son nom accessible. Utilisez un élément d'en-tête tel que `H3` afin que les utilisateurs de lecteurs d'écran puissent trouver la `Card` à travers la structure d'en-tête de la page.
:::

<ComponentDemo
path='/webforj/cardregions'
files={[
  'src/main/java/com/webforj/samples/views/card/CardRegionsView.java',
  'src/main/frontend/css/card/cardRegions.css',
]}
height='700px'
/>

## Orientation {#orientation}

L'orientation contrôle où se trouve la figure par rapport aux autres régions et est définie avec `setOrientation()`.

Les cartes sont verticales par défaut, ce qui les empile avec la figure au-dessus de l'en-tête, du corps et du pied de page. Cela convient aux cartes disposées dans une grille, où chacune occupe une colonne étroite. Passer `Card.Orientation.HORIZONTAL` à `setOrientation()` rend la carte horizontale à la place, plaçant la figure à côté de ces régions.

```java
card.setOrientation(Card.Orientation.HORIZONTAL);
```

<ComponentDemo
path='/webforj/cardorientation'
files={['src/main/java/com/webforj/samples/views/card/CardOrientationView.java']}
height='500px'
/>

Puisque le paramètre déplace la figure et rien d'autre, une `Card` sans figure semble identique dans les deux orientations.

## Élévation et bordure {#elevation-and-border}

Deux paramètres déterminent à quelle distance la `Card` se sépare de la page derrière elle. `setShadow()` applique une valeur de l'échelle de l'ombre, qui va de `NONE` à `XSMALL`, `SMALL`, `MEDIUM`, `LARGE`, et `XLARGE` à `XXLARGE`. `setBorderless()` contrôle si la `Card` dessine sa bordure. Les valeurs par défaut sont `Shadow.XSMALL` avec la bordure dessinée.

Les paramètres sont indépendants, donc n'importe quelle ombre peut être associée ou non à la bordure.

<ComponentDemo
path='/webforj/cardappearance'
files={[
  'src/main/java/com/webforj/samples/views/card/CardAppearanceView.java']}
height='300px'
/>

## Séparateurs et espace {#dividers-and-expanse}

Alors que les paramètres d'élévation et de bordure contrôlent comment la `Card` se situe par rapport à la page, les séparateurs et l'espace contrôlent les régions lisibles à l'intérieur de la carte elle-même.

`setDivided(true)` dessine un séparateur après l'en-tête et avant le pied de page, ce qui aide lorsque les régions contiennent un contenu dense. Les séparateurs sont désactivés par défaut. Un séparateur pour une région qui ne contient aucun contenu n'est pas dessiné, donc une carte divisée sans pied de page montre un séparateur, sous l'en-tête. Les séparateurs ont plus de poids sur les cartes plates, où aucun cadre n'est présent pour faire ce travail.

`setExpanse()` contrôle la densité, régulant le remplissage, les espaces entre les régions, et la taille du titre et de la légende. `Card` utilise l'énumération partagée `Expanse`, qui offre `NONE`, `XSMALL`, `SMALL`, `MEDIUM`, `LARGE`, et `XLARGE`, avec `MEDIUM` comme valeur par défaut. Des espaces plus petits conviennent aux tuiles de tableau de bord et aux barres latérales, où plusieurs cartes partagent l'écran.

L'exemple suivant montre deux composants `Card` avec des séparateurs. Une `Card` utilise `Expanse.LARGE`, tandis que l'autre utilise `Expanse.SMALL` :

<ComponentDemo
path='/webforj/carddensity'
files={[
  'src/main/java/com/webforj/samples/views/card/CardDensityView.java',
  'src/main/frontend/css/card/cardDensity.css',
]}
height='400px'
/>

## Événements de clic {#click-events}

Le composant `Card` implémente `HasElementClickListener`, donc un listener enregistré avec `onClick()` ou `addClickListener()` reçoit un `ElementClickEvent`. Cela fait toute la surface une seule cible.

```java
card.onClick(event -> Router.getCurrent().navigate(new Location("/reports/july")));
```

:::warning Clics à l'intérieur de la `Card`
Les clics sur les composants à l'intérieur de la `Card` atteignent également la `Card`, donc une `Card` avec son propre listener le déclenche lorsque l'utilisateur appuie sur un `Button` dans les actions de l'en-tête ou le pied de page. Ajoutez un listener à la `Card` lorsque celle-ci a une action claire, et réservez les boutons à l'intérieur pour des actions que la `Card` elle-même ne réalise pas.
:::

## Style {#styling}

<TableBuilder name="Card" />
