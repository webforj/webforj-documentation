---
title: Badge
sidebar_position: 8
sidebar_class_name: new-content
_i18n_hash: 83dfb4c5ec1d554fc78e7e860128fb46
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-badge" />
<DocChip chip='since' label='25.12' />
<JavadocLink type="badge" location="com/webforj/component/badge/Badge" top='true'/>

Un `Badge` est une étiquette compacte et visuellement distincte utilisée pour transmettre un statut, des comptes ou de courtes informations contextuelles. Que vous ayez besoin de signaler un compte de notification, de marquer un élément comme "Nouveau" ou d'attirer l'attention sur un avertissement, les badges vous offrent un moyen léger de faire remonter cette information directement dans l'interface utilisateur.

<!-- INTRO_END -->

:::tip Utilisation d'un `Badge`
Les badges fonctionnent bien pour les comptes de notifications, les étiquettes de statut et les métadonnées courtes comme les étiquettes de version ou les états de publication. Gardez le texte du badge à un ou deux mots pour que l'étiquette soit lisible d'un coup d'œil.
:::

## Création d'un badge {#creating-a-badge}

Le badge `Badge` le plus simple prend une chaîne de texte. Vous pouvez également passer un `BadgeTheme` directement dans le constructeur pour définir immédiatement le style visuel. Le constructeur sans argument est disponible lorsque vous devez créer un badge dynamiquement et le configurer après sa création.

```java
Badge badge = new Badge("Nouveau");

// Avec un thème
Badge primary = new Badge("Mise en avant", BadgeTheme.SUCCESS);

// Construit dynamiquement
Badge status = new Badge();
status.setLabel("En attente");
status.setTheme(BadgeTheme.WARNING);
```

## Étiquette {#label}

Vous pouvez définir ou mettre à jour le contenu textuel d'un badge à tout moment avec `setLabel()`. La méthode `setText()` est un alias pour la même opération ; utilisez celle qui semble plus naturelle dans le contexte. Les deux ont des getters correspondants, `getLabel()` et `getText()`, si vous avez besoin de lire la valeur actuelle.

```java
Badge badge = new Badge();
badge.setLabel("Mis à jour");

// Équivalent
badge.setText("Mis à jour");

// Lire la valeur
String current = badge.getLabel();
```

## Icônes {#icons}

Parfois, une approche plus visuelle est utile pour transmettre des informations avec un `Badge`. Les badges supportent un contenu d'icône inséré. Passez une icône avec le texte en utilisant le constructeur `Badge(String, Component...)`, ou passez une icône seule pour créer un badge uniquement avec une icône. Lorsqu'ils sont combinés avec du texte, l'icône est rendue à gauche de l'étiquette.

Les badges uniquement avec icône fonctionnent particulièrement bien pour les indicateurs de statut compacts dans des mises en page denses où un mot court semblerait encombré. Associer une icône avec du texte est un bon compromis lorsque l'icône seule pourrait être ambiguë. Un symbole de statut est largement compris par lui-même, mais ajouter une courte étiquette textuelle élimine les incertitudes pour les utilisateurs novices. Vous pouvez passer plusieurs composants au constructeur si vous avez besoin de composer un préfixe plus riche, bien qu'en pratique une seule icône soit le motif le plus courant.

<!-- vale off -->
<ComponentDemo
path='/webforj/badgeicons?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/badge/BadgeIconsView.java'
height='345px'
/>
<!-- vale on -->

```java
// Icône avec texte
Badge done = new Badge("Fait", FeatherIcon.CHECK_CIRCLE.create());
done.setTheme(BadgeTheme.SUCCESS);

// Icône seulement
Badge check = new Badge(FeatherIcon.CHECK.create());
check.setTheme(BadgeTheme.SUCCESS);
```

## Utilisation dans d'autres composants {#usage-in-other-components}

### Boutons {#buttons}

Attachez un `Badge` à un `Button` en utilisant `setBadge()`. Le badge apparaît dans le coin supérieur droit du bouton, chevauchant le bord du bouton. C'est un motif commun pour les comptes de notifications sur les actions de la barre d'outils ou les boutons d'icône. Puisque le badge est un composant autonome, il est complètement indépendant du thème et de la taille du bouton. Vous pouvez associer un bouton principal avec un badge de danger, ou un bouton fantôme avec un badge de succès, et chaque côté de la combinaison se style indépendamment sans conflit. Mettre à jour le compte plus tard est aussi simple que d'appeler `badge.setLabel()` avec une nouvelle valeur ; le bouton n'a pas besoin d'être touché.

<!-- vale off -->
<ComponentDemo
path='/webforj/badgebuttons?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/badge/BadgeButtonsView.java'
height='290px'
/>
<!-- vale on -->

### Onglet tabulé {#tabbed-pane}

Ajoutez un `Badge` comme suffixe sur un `Tab` en utilisant `setSuffixComponent()`. C'est un ajustement naturel pour les comptes de style boîte de réception ou les indicateurs de statut sur chaque onglet. C'est le genre de motif que vous voyez sur les clients de messagerie ou les gestionnaires de tâches où il est important de signaler l'activité sur chaque section d'un coup d'œil. Le badge se trouve à l'extrémité trailing de l'étiquette de l'onglet, après tout contenu préfixe, et reste visible, quel que soit l'onglet actuellement actif. Cette persistance est intentionnelle : cacher le badge sur les onglets inactifs rendrait plus difficile de savoir quelles sections nécessitent une attention sans passer à chaque section.

<!-- vale off -->
<ComponentDemo
path='/webforj/badgetabbedpane?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/badge/BadgeTabbedPaneView.java'
height='360px'
/>
<!-- vale on -->

## Style {#styling}

Les badges supportent plusieurs dimensions de style : des couleurs de thème pour transmettre un sens, une échelle d'ampleur pour contrôler la taille, et des propriétés CSS pour une personnalisation fine.

### Thèmes {#themes}

Comme pour de nombreux composants dans webforJ, le `Badge` existe en quatorze thèmes : sept variantes remplies et sept variantes dessinées.

Les thèmes remplis utilisent un arrière-plan solide et calculent automatiquement une couleur de texte qui respecte les exigences de contraste. Les variantes dessinées utilisent plutôt un arrière-plan teinté avec une bordure colorée, ce qui en fait une option plus subtile lorsque vous souhaitez que le badge complète le contenu environnant plutôt que de le dominer.

Appliquez un thème en utilisant `setTheme()` ou via le constructeur.

<!-- vale off -->
<ComponentDemo
path='/webforj/badgethemes?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/badge/BadgeThemesView.java'
height='260px'
/>
<!-- vale on -->

### Couleur personnalisée {#custom-color}

Si les thèmes intégrés ne correspondent pas à votre palette, définissez une couleur de base personnalisée en utilisant la propriété CSS `--dwc-badge-seed`. À partir de cette seule valeur, le badge dérive automatiquement les couleurs d'arrière-plan, de texte et de bordure, de sorte que chaque combinaison reste lisible sans que vous ayez à spécifier chaque couleur individuellement. Cela signifie que vous pouvez marquer un badge de n'importe quelle couleur dans votre système de design en toute confiance. Les valeurs de teinte, saturation et luminosité (HSL) sont particulièrement pratiques ici ; il suffit d'échanger la teinte pour produire une famille de couleurs complètement différente tout en maintenant le contraste intact.

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
height='300px'
/>
<!-- vale on -->

### Parties et variables CSS {#parts-and-css-variables}

<TableBuilder name="Badge" />
