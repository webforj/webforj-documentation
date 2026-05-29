---
title: RadioButton
slug: radiobutton
sidebar_position: 95
_i18n_hash: 491fdadd826e3b34acc02b8833704faf
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-radio" />
<DocChip chip='since' label='23.01' />
<JavadocLink type="foundation" location="com/webforj/component/optioninput/RadioButton" top='true'/>

Le composant `RadioButton` représente une seule option qui peut être sélectionnée ou désélectionnée. Les boutons radio sont généralement regroupés de manière à ce que la sélection de l'un désélectionne automatiquement les autres, permettant ainsi aux utilisateurs de faire un choix unique parmi un ensemble d'options mutuellement exclusives.

<!-- INTRO_END -->

## Usages {#usages}

Le `RadioButton` est particulièrement utile dans les scénarios où les utilisateurs doivent faire une seule sélection à partir d'un ensemble prédéfini d'options. Voici quelques exemples de quand utiliser le `RadioButton` :

1. **Enquêtes ou Questionnaires** : Les boutons radio sont couramment utilisés dans les enquêtes ou les questionnaires où les utilisateurs doivent sélectionner une seule réponse d'une liste d'options.

2. **Paramètres de Préférence** : Les applications impliquant des panneaux de préférences ou de paramètres utilisent souvent les boutons radio pour permettre aux utilisateurs de choisir une seule option parmi un ensemble de choix mutuellement exclusifs.

3. **Filtrage ou Tri** : Un `RadioButton` peut être utilisé dans les applications qui nécessitent que les utilisateurs sélectionnent une seule option de filtrage ou de tri, comme trier une liste d'éléments selon différents critères.

:::tip Regroupement des composants `RadioButton`
Utilisez un [`RadioButtonGroup`](/docs/components/radiobuttongroup) pour gérer un ensemble de boutons radio lorsque vous souhaitez que les utilisateurs choisissent une seule option.
:::

## Texte et positionnement {#text-and-positioning}

Les boutons radio peuvent utiliser la méthode ```setText(String text)```, qui sera positionnée près du bouton radio selon la `Position` intégrée.
Les boutons radio ont une fonctionnalité intégrée pour afficher du texte soit à droite, soit à gauche du composant. Par défaut, le texte sera affiché à droite du composant. Le positionnement du texte horizontal est pris en charge par l'utilisation de la classe énumérée `HorizontalAlignment`. Voici les deux paramètres : <br/>

<ComponentDemo
path='/webforj/radiobuttontext'
files={['src/main/java/com/webforj/samples/views/radiobutton/RadioButtonTextView.java']}
height='120px'
/>

## Activation {#activation}

Les boutons radio peuvent être contrôlés en utilisant deux types d'activation : l'activation manuelle et l'activation automatique. Cela détermine quand un `RadioButton` changera son état.

<ComponentDemo
path='/webforj/radiobuttonactivation'
files={['src/main/java/com/webforj/samples/views/radiobutton/RadioButtonActivationView.java']}
height='175px'
/>

### Activation manuelle {#manual-activation}

Lorsque un bouton radio est réglé sur activation manuelle, cela signifie qu'il ne sera pas automatiquement coché lorsqu'il reçoit le focus.
L'activation manuelle permet à l'utilisateur de naviguer à travers les options de boutons radio en utilisant le clavier ou d'autres méthodes d'entrée sans changer immédiatement l'option sélectionnée.

Si le bouton radio fait partie d'un groupe, sélectionner un autre bouton radio au sein du groupe désélectionnera automatiquement le bouton radio précédemment sélectionné.
L'activation manuelle offre un contrôle plus précis sur le processus de sélection, nécessitant une action explicite de l'utilisateur pour changer l'option sélectionnée.

### Activation automatique {#auto-activation}

L'activation automatique est l'état par défaut pour un `RadioButton`, ce qui signifie que le bouton sera coché chaque fois qu'il reçoit le focus pour une raison quelconque. Cela signifie que non seulement le clic, mais aussi l'auto-focus ou la navigation par tabulation cocheront le bouton.

:::tip Remarque
La valeur d'activation par défaut est **`MANUELLE`**.
:::


## Interrupteurs {#switches}

Un `RadioButton` peut également être configuré pour s'afficher sous forme d'interrupteur, ce qui fournit une représentation visuelle alternative pour sélectionner des options. Normalement, les boutons radio sont de forme circulaire ou arrondie et indiquent un choix unique parmi un groupe d'options.

<ComponentDemo
path='/webforj/radiobuttonswitch'
files={['src/main/java/com/webforj/samples/views/radiobutton/RadioButtonSwitchView.java']}
height='120px'
/>

Un `RadioButton` peut être transformé en interrupteur qui ressemble à un interrupteur à bascule ou à un curseur en utilisant l'une des deux méthodes :

1. **La méthode de Fabrique** : Le bouton radio peut être créé en utilisant les méthodes de fabrique suivantes :

```java
Switch(String text, boolean checked);
Switch(String text);
Switch();
```

Ces méthodes reflètent un constructeur de `RadioButton`, et créeront le composant avec la propriété d'interrupteur déjà activée.

2. **Setter** : Il est également possible de changer un `RadioButton` existant en interrupteur en utilisant le setter approprié :

```java
myRadioButton.setSwitch(true);
```

Lorsqu'un `RadioButton` est affiché sous forme d'interrupteur, il apparaît généralement sous la forme d'une forme oblongue avec un indicateur pouvant être basculé activé ou désactivé. Cette représentation visuelle donne aux utilisateurs une interface plus intuitive et familière, similaire aux interrupteurs physiques couramment trouvés dans les appareils électroniques.

Définir un `RadioButton` pour s'afficher sous forme d'interrupteur peut améliorer l'expérience utilisateur en fournissant une manière claire et directe de sélectionner des options. Cela peut améliorer l'attrait visuel et la facilité d'utilisation des formulaires, des panneaux de paramètres ou de tout autre élément d'interface nécessitant plusieurs choix.

:::info
Le comportement du `RadioButton` reste le même lorsqu'il est rendu sous forme d'interrupteur, ce qui signifie qu'une seule option peut être sélectionnée à la fois dans un groupe. L'apparence semblable à un interrupteur est une transformation visuelle qui conserve la fonctionnalité d'un `RadioButton`.
:::

<br/>

## Style {#styling}

### Expanses {#expanses}
Cinq étendues de cases à cocher sont prises en charge, permettant un stylisme rapide sans utiliser de CSS.
Les étendues sont prises en charge par l'utilisation de la classe énumérée `Expanse`. Voici les étendues prises en charge pour le composant checkbox : <br/>

<TableBuilder name="RadioButton" />

## Meilleures pratiques {#best-practices}

Pour garantir une expérience utilisateur optimale lors de l'utilisation du composant RadioButton, tenez compte des meilleures pratiques suivantes :

1. **Étiquettes Claires pour les Options** : Fournir un texte clair et concis pour chaque option de `RadioButton` afin de décrire avec précision le choix. Le texte doit être facilement compréhensible et distinct l'un de l'autre.

2. **Regrouper les Boutons Radio** : Regrouper les boutons radio associés pour indiquer leur association. Cela aide les utilisateurs à comprendre qu'une seule option peut être sélectionnée dans un groupe spécifique. Cela peut être fait efficacement en utilisant le composant [`RadioButtonGroup`](/docs/components/radiobuttongroup).

3. **Fournir une Sélection Par Défaut** : Si applicable, envisagez de fournir une sélection par défaut pour les boutons radio afin de guider les utilisateurs lorsqu'ils rencontrent pour la première fois les options. La sélection par défaut doit s'aligner sur le choix le plus courant ou préféré.
