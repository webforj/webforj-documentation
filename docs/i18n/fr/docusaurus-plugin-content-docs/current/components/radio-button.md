---
title: RadioButton
slug: radiobutton
sidebar_position: 95
description: >-
  Add a single-choice RadioButton with text positioning, activation modes, and
  grouping for mutually exclusive selections.
_i18n_hash: b261deb2944ba67d2e9ae8cbbb8b26eb
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-radio" />
<DocChip chip='since' label='23.01' />
<JavadocLink type="foundation" location="com/webforj/component/optioninput/RadioButton" top='true'/>

Le composant `RadioButton` représente une seule option pouvant être sélectionnée ou désélectionnée. Les boutons radio sont généralement regroupés de sorte que la sélection de l'un désélectionne automatiquement les autres, permettant aux utilisateurs de faire un choix unique parmi un ensemble d'options mutuellement exclusives.

<!-- INTRO_END -->

## Usages {#usages}

Le `RadioButton` est idéal pour des scénarios où les utilisateurs doivent faire une seule sélection parmi un ensemble d'options prédéfinies. Voici quelques exemples de quand utiliser le `RadioButton` :

1. **Enquêtes ou questionnaires** : Les boutons radio sont couramment utilisés dans les enquêtes ou questionnaires où les utilisateurs doivent sélectionner une seule réponse à partir d'une liste d'options.

2. **Paramètres de préférences** : Les applications impliquant des panneaux de préférence ou des paramètres utilisent souvent des boutons radio pour permettre aux utilisateurs de choisir une seule option parmi un ensemble de choix mutuellement exclusifs.

3. **Filtrage ou tri** : Un `RadioButton` peut être utilisé dans des applications nécessitant que les utilisateurs sélectionnent une seule option de filtre ou de tri, comme le tri d'une liste d'articles selon différents critères.

:::tip Regroupement des composants `RadioButton`
Utilisez un [`RadioButtonGroup`](/docs/components/radiobuttongroup) pour gérer un ensemble de boutons radio lorsque vous souhaitez que les utilisateurs choisissent une option unique.
:::

## Texte et positionnement {#text-and-positioning}

Utilisez `setText(String text)` pour définir l'étiquette d'un bouton radio. Par défaut, le texte apparaît à droite du composant.
Pour changer sa position, utilisez `setTextPosition(Position.LEFT)` ou `setTextPosition(Position.RIGHT)` avec l'énumération `HasTextPosition.Position`.

<ComponentDemo
path='/webforj/radiobuttontext'
files={['src/main/java/com/webforj/samples/views/radiobutton/RadioButtonTextView.java']}
height='120px'
/>


## Activation {#activation}

Les boutons radio peuvent être contrôlés en utilisant deux types d'activation : l'activation manuelle et l'activation automatique. Cela détermine quand un `RadioButton` changera d'état. Par défaut, c'est `Activation.MANUAL`, de l'énumération `RadioButton.Activation`.

<ComponentDemo
path='/webforj/radiobuttonactivation'
files={['src/main/java/com/webforj/samples/views/radiobutton/RadioButtonActivationView.java']}
height='175px'
/>

### Activation manuelle {#manual-activation}

Lorsqu'un bouton radio est défini sur l'activation manuelle, cela signifie qu'il ne sera pas automatiquement coché lorsqu'il reçoit le focus.
L'activation manuelle permet à l'utilisateur de naviguer à travers les options de boutons radio en utilisant le clavier ou d'autres méthodes d'entrée sans changer immédiatement l'option sélectionnée.

Si le bouton radio fait partie d'un groupe, sélectionner un autre bouton radio dans le groupe désélectionnera automatiquement le bouton radio précédemment sélectionné.
L'activation manuelle offre un contrôle plus précis sur le processus de sélection, nécessitant une action explicite de l'utilisateur pour changer l'option sélectionnée.


### Activation automatique {#auto-activation}

Activez l'activation automatique avec `setActivation(Activation.AUTO)`. Dans ce mode, le bouton radio est coché chaque fois qu'il reçoit le focus, que ce soit par un clic, un auto-focus ou une navigation par tabulation.


## Interrupteurs {#switches}

Un `RadioButton` peut également être configuré pour s'afficher sous forme d'interrupteur, fournissant une représentation visuelle alternative pour sélectionner des options. Normalement, les boutons radio sont ronds ou arrondis et indiquent un choix unique parmi un groupe d'options.

<ComponentDemo
path='/webforj/radiobuttonswitch'
files={['src/main/java/com/webforj/samples/views/radiobutton/RadioButtonSwitchView.java']}
height='120px'
/>

Un `RadioButton` peut être transformé en interrupteur ressemblant à un interrupteur à bascule ou un curseur en utilisant l'une des deux méthodes suivantes :

1. **La méthode de la fabrique** : Le `RadioButton` peut être créé en utilisant les méthodes de fabrique suivantes :

```java
Switch(String text, boolean checked);
Switch(String text);
Switch();
```

Ces méthodes imitent un constructeur de `RadioButton`, et créeront le composant avec la propriété switch déjà activée.

2. **Setter** : Il est également possible de changer un `RadioButton` déjà existant en un interrupteur en utilisant le setter approprié :

```java
myRadioButton.setSwitch(true);
```


Lorsqu'un `RadioButton` est affiché comme un interrupteur, il apparaît généralement sous forme de forme allongée avec un indicateur pouvant être activé ou désactivé. Cette représentation visuelle donne aux utilisateurs une interface plus intuitive et familière, similaire aux interrupteurs physiques communs dans les appareils électroniques.

Définir un `RadioButton` pour s'afficher comme un interrupteur peut améliorer l'expérience utilisateur en fournissant un moyen clair et simple de sélectionner des options. Cela peut améliorer l'attrait visuel et l'utilisabilité des formulaires, des panneaux de paramètres ou de tout autre élément d'interface nécessitant plusieurs choix.

:::info
Le comportement du `RadioButton` reste le même lorsqu'il est rendu en tant qu'interrupteur, ce qui signifie qu'une seule option peut être sélectionnée à la fois dans un groupe. L'apparence de type interrupteur est une transformation visuelle qui conserve la fonctionnalité d'un `RadioButton`.
:::

<br/>

## Style {#styling}

### Expansions {#expanses}

Utilisez `setExpanse()` avec l'énumération `Expanse` pour ajuster la taille d'un `RadioButton` sans écrire de CSS. Les valeurs disponibles sont `XSMALL`, `SMALL`, `MEDIUM`, `LARGE`, `XLARGE`, et `NONE`.

<TableBuilder name="RadioButton" />

## Meilleures pratiques {#best-practices}

Pour garantir une expérience utilisateur optimale lors de l'utilisation du composant RadioButton, considérez les meilleures pratiques suivantes :

1. **Étiquetez clairement les options** : Fournissez un texte clair et concis pour chaque option de `RadioButton` afin de décrire avec précision le choix. Le texte doit être facile à comprendre et à distinguer les uns des autres.

2. **Regroupez les boutons radio** : Regroupez les boutons radio liés pour indiquer leur association. Cela aide les utilisateurs à comprendre qu'une seule option peut être sélectionnée dans un groupe spécifique. Cela peut être fait efficacement en utilisant le composant [`RadioButtonGroup`](/docs/components/radiobuttongroup).

3. **Fournir une sélection par défaut** : si applicable, envisagez de fournir une sélection par défaut pour les boutons radio afin de guider les utilisateurs lorsqu'ils rencontrent les options pour la première fois. La sélection par défaut doit correspondre au choix le plus courant ou préféré.
