---
title: RadioButton
slug: radiobutton
sidebar_position: 95
description: >-
  Add a single-choice RadioButton with text positioning, activation modes, and
  grouping for mutually exclusive selections.
_i18n_hash: 32d2e2f74e7f255b901de15622e8e2cc
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-radio" />
<DocChip chip='since' label='23.01' />
<JavadocLink type="foundation" location="com/webforj/component/optioninput/RadioButton" top='true'/>

Le composant `RadioButton` représente une seule option qui peut être sélectionnée ou désélectionnée. Les boutons radio sont généralement regroupés de sorte que sélectionner l'un d'eux désélectionne automatiquement les autres, permettant aux utilisateurs de faire un choix unique parmi un ensemble d'options mutuellement exclusives.

<!-- INTRO_END -->

## Usages {#usages}

Le `RadioButton` est mieux utilisé dans des scénarios où les utilisateurs doivent faire une seule sélection parmi un ensemble prédéfini d'options. Voici quelques exemples de quand utiliser le `RadioButton` :

1. **Enquêtes ou questionnaires** : Les boutons radio sont couramment utilisés dans les enquêtes ou les questionnaires où les utilisateurs doivent sélectionner une seule réponse à partir d'une liste d'options.

2. **Paramètres de préférence** : Les applications qui impliquent des panneaux de préférence ou de paramètres utilisent souvent des boutons radio pour permettre aux utilisateurs de choisir une seule option parmi un ensemble de choix mutuellement exclusifs.

3. **Filtrage ou tri** : Un `RadioButton` peut être utilisé dans des applications qui nécessitent que les utilisateurs sélectionnent une seule option de filtrage ou de tri, comme le tri d'une liste d'éléments selon différents critères.

:::tip Regroupement des composants `RadioButton`
Utilisez un [`RadioButtonGroup`](/docs/components/radiobuttongroup) pour gérer un ensemble de boutons radio lorsque vous souhaitez que les utilisateurs choisissent une seule option.
:::

## Texte et positionnement {#text-and-positioning}

Les boutons radio peuvent utiliser la méthode ```setText(String text)```, qui sera positionnée près du bouton radio selon la `Position` intégrée. Les boutons radio ont des fonctionnalités intégrées pour définir le texte à afficher soit à droite, soit à gauche du composant. Par défaut, le texte sera affiché à droite du composant. Le positionnement du texte horizontal est pris en charge par l'utilisation de la classe énumérée `HorizontalAlignment`. Voici les deux paramètres : <br/>

<ComponentDemo
path='/webforj/radiobuttontext'
files={['src/main/java/com/webforj/samples/views/radiobutton/RadioButtonTextView.java']}
height='120px'
/>

## Activation {#activation}

Les boutons radio peuvent être contrôlés à l'aide de deux types d'activation : activation manuelle et activation automatique. Cela détermine quand un `RadioButton` changera son état.

<ComponentDemo
path='/webforj/radiobuttonactivation'
files={['src/main/java/com/webforj/samples/views/radiobutton/RadioButtonActivationView.java']}
height='175px'
/>

### Activation manuelle {#manual-activation}

Lorsque le bouton radio est réglé sur une activation manuelle, cela signifie qu'il ne sera pas automatiquement coché lorsqu'il obtient le focus. L'activation manuelle permet à l'utilisateur de naviguer à travers les options de bouton radio à l'aide du clavier ou d'autres méthodes d'entrée sans changer immédiatement l'option sélectionnée.

Si le bouton radio fait partie d'un groupe, sélectionner un autre bouton radio au sein du groupe désélectionnera automatiquement le bouton radio précédemment sélectionné. L'activation manuelle offre un contrôle plus fin sur le processus de sélection, nécessitant une action explicite de l'utilisateur pour changer l'option sélectionnée.

### Activation automatique {#auto-activation}

L'activation automatique est l'état par défaut d'un `RadioButton`, et signifie que le bouton sera coché lorsqu'il obtient le focus pour une raison quelconque. Cela signifie que non seulement un clic, mais également le focus automatique ou la navigation par onglets cocheront le bouton.

:::tip Remarque
La valeur d'activation par défaut est **`MANUAL`** activation.
:::

## Interrupteurs {#switches}

Un `RadioButton` peut également être affiché comme un interrupteur qui fournit une représentation visuelle alternative pour sélectionner des options. Normalement, les boutons radio sont de forme circulaire ou arrondie et indiquent un seul choix parmi un groupe d'options.

<ComponentDemo
path='/webforj/radiobuttonswitch'
files={['src/main/java/com/webforj/samples/views/radiobutton/RadioButtonSwitchView.java']}
height='120px'
/>

Un `RadioButton` peut être transformé en un interrupteur qui ressemble à un interrupteur à bascule ou à un glissement en utilisant l'une des deux méthodes :

1. **La méthode de la fabrique** : Le `RadioButton` peut être créé à l'aide des méthodes de fabrique suivantes :

```java
Switch(String text, boolean checked);
Switch(String text);
Switch();
```

Ces méthodes imitent un constructeur `RadioButton`, et créeront le composant avec la propriété de commutateur déjà basée.

2. **Setter** : Il est également possible de changer un `RadioButton` existant en un interrupteur en utilisant le setter approprié :

```java
myRadioButton.setSwitch(true);
```

Lorsqu'un `RadioButton` est affiché comme un interrupteur, il apparaît généralement sous une forme oblongue avec un indicateur qui peut être activé ou désactivé. Cette représentation visuelle offre aux utilisateurs une interface plus intuitive et familière, semblable aux interrupteurs physiques couramment trouvés dans les appareils électroniques.

Rendre un `RadioButton` afin qu'il s'affiche comme un interrupteur peut améliorer l'expérience utilisateur en fournissant un moyen clair et simple de sélectionner des options. Cela peut améliorer l'attrait visuel et l'utilisabilité des formulaires, des panneaux de paramètres ou de tout autre élément d'interface nécessitant plusieurs choix.

:::info
Le comportement du `RadioButton` reste le même lorsqu'il est rendu comme un interrupteur, ce qui signifie qu'une seule option peut être sélectionnée à la fois dans un groupe. L'apparence de type interrupteur est une transformation visuelle qui conserve la fonctionnalité d'un `RadioButton`.
:::

<br/>

## Stylisation {#styling}

### Expanses {#expanses}
Il existe cinq expansions de case à cocher prises en charge qui permettent un stylage rapide sans utiliser de CSS. Les expansions sont prises en charge par l'utilisation de la classe énumérée `Expanse`. Voici les expansions prises en charge pour le composant checkbox : <br/>

<TableBuilder name="RadioButton" />

## Meilleures pratiques {#best-practices}

Pour garantir une expérience utilisateur optimale lors de l'utilisation du composant RadioButton, considérez les meilleures pratiques suivantes :

1. **Étiquetez clairement les options** : Fournissez un texte clair et concis pour chaque option de `RadioButton` afin de décrire avec précision le choix. Le texte doit être facile à comprendre et à distinguer les uns des autres.

2. **Groupez les boutons radio** : Regroupez les boutons radio connexes pour indiquer leur association. Cela aide les utilisateurs à comprendre qu'une seule option peut être sélectionnée dans un groupe spécifique. Cela peut être fait efficacement en utilisant le composant [`RadioButtonGroup`](/docs/components/radiobuttongroup).

3. **Fournissez une sélection par défaut** : Si applicable, envisagez de fournir une sélection par défaut pour les boutons radio afin de guider les utilisateurs lorsqu'ils rencontrent pour la première fois les options. La sélection par défaut doit correspondre au choix le plus courant ou préféré.
