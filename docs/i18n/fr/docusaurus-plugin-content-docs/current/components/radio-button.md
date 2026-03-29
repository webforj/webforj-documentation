---
title: RadioButton
slug: radiobutton
sidebar_position: 95
_i18n_hash: 19e51a9c57a6524781ac008abcebc790
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-radio" />
<DocChip chip='since' label='23.01' />
<JavadocLink type="foundation" location="com/webforj/component/optioninput/RadioButton" top='true'/>

Le composant `RadioButton` représente une seule option qui peut être sélectionnée ou désélectionnée. Les boutons radio sont généralement regroupés de manière à ce que la sélection de l'un désélectionne automatiquement les autres, permettant aux utilisateurs de faire un choix unique parmi un ensemble d'options mutuellement exclusives.

<!-- INTRO_END -->

## Usages {#usages}

Le `RadioButton` est préférable dans les scénarios où les utilisateurs doivent effectuer une seule sélection parmi un ensemble d'options prédéfinies. Voici quelques exemples de quand utiliser le `RadioButton` :

1. **Sondages ou questionnaires** : Les boutons radio sont couramment utilisés dans les sondages ou les questionnaires où les utilisateurs doivent sélectionner une seule réponse parmi une liste d'options.

2. **Paramètres de préférence** : Les applications impliquant des panneaux de préférences ou de paramètres utilisent souvent des boutons radio pour permettre aux utilisateurs de choisir une seule option parmi un ensemble de choix mutuellement exclusifs.

3. **Filtrage ou tri** : Un `RadioButton` peut être utilisé dans les applications qui exigent que les utilisateurs sélectionnent une seule option de filtrage ou de tri, par exemple, trier une liste d'éléments selon différents critères.

:::tip Regroupement des composants `RadioButton`
Utilisez un [`RadioButtonGroup`](/docs/components/radiobuttongroup) pour gérer un ensemble de boutons radio lorsque vous souhaitez que les utilisateurs choisissent une seule option.
:::

## Texte et positionnement {#text-and-positioning}

Les boutons radio peuvent utiliser la méthode ```setText(String text)```, qui sera positionnée près du bouton radio selon la `Position` intégrée.
Les boutons radio ont une fonctionnalité intégrée pour définir le texte à afficher soit à droite, soit à gauche du composant. Par défaut, le texte sera affiché à droite du composant. Le positionnement du texte horizontal est pris en charge par l'utilisation de l'énumération `HorizontalAlignment`. Ci-dessous, les deux paramètres : <br/>

<ComponentDemo 
path='/webforj/radiobuttontext?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/radiobutton/RadioButtonTextView.java'
height="120px"
/>


## Activation {#activation}

Les boutons radio peuvent être contrôlés à l'aide de deux types d'activation : activation manuelle et activation automatique. Ceux-ci dictent quand un `RadioButton` changera son état.

<ComponentDemo 
path='/webforj/radiobuttonactivation?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/radiobutton/RadioButtonActivationView.java'
height="175px"
/>

### Activation manuelle {#manual-activation}

Lorsqu'un bouton radio est défini sur activation manuelle, cela signifie qu'il ne sera pas automatiquement sélectionné lorsqu'il obtient le focus.
L'activation manuelle permet à l'utilisateur de naviguer à travers les options de boutons radio à l'aide du clavier ou d'autres méthodes d'entrée sans changer immédiatement l'option sélectionnée.

Si le bouton radio fait partie d'un groupe, la sélection d'un autre bouton radio au sein du groupe désélectionnera automatiquement le bouton radio précédemment sélectionné.
L'activation manuelle offre un contrôle plus fin sur le processus de sélection, nécessitant une action explicite de l'utilisateur pour changer l'option sélectionnée.

### Activation automatique {#auto-activation}

L'activation automatique est l'état par défaut pour un `RadioButton`, et signifie que le bouton sera sélectionné dès qu'il obtient le focus pour n'importe quelle raison. Cela signifie que non seulement le clic, mais aussi le focus automatique ou la navigation par onglet sélectionneront également le bouton.

:::tip Remarque
La valeur d'activation par défaut est **`MANUELLE`** activation.
:::


## Interrupteurs {#switches}

Un `RadioButton` peut également être configuré pour s'afficher comme un interrupteur, offrant une représentation visuelle alternative pour sélectionner des options. Normalement, les boutons radio sont circulaires ou arrondis et indiquent un choix unique parmi un groupe d'options. 

<ComponentDemo 
path='/webforj/radiobuttonswitch?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/radiobutton/RadioButtonSwitchView.java'
height="120px"
/>

Un `RadioButton` peut être transformé en un interrupteur ressemblant à un interrupteur à bascule ou à un curseur en utilisant l'une des deux méthodes :

1. **La méthode de fabrique** : Le RadioButton peut être créé en utilisant les méthodes de fabrique suivantes :

```java
Switch(String text, boolean checked);
Switch(String text);
Switch();
```

Ces méthodes reflètent un constructeur `RadioButton` et créeront le composant avec la propriété d'interrupteur déjà activée.

2. **Setter** : Il est également possible de changer un `RadioButton` déjà existant en un interrupteur en utilisant le setter approprié :

```java
myRadioButton.setSwitch(true);
```


Lorsqu'un `RadioButton` est affiché comme un interrupteur, il apparaît généralement sous la forme d'une forme oblongue avec un indicateur qui peut être activé ou désactivé. Cette représentation visuelle offre aux utilisateurs une interface plus intuitive et familière, similaire aux interrupteurs physiques couramment trouvés dans les appareils électroniques. 

Définir un `RadioButton` pour s'afficher comme un interrupteur peut améliorer l'expérience utilisateur en fournissant un moyen clair et direct de sélectionner des options. Cela peut améliorer l'attrait visuel et l'utilisabilité des formulaires, des panneaux de paramètres ou de tout autre élément d'interface nécessitant plusieurs choix.

:::info
Le comportement du `RadioButton` reste le même lorsqu'il est rendu comme un interrupteur, signifiant qu'une seule option peut être sélectionnée à la fois au sein d'un groupe. L'apparence de type interrupteur est une transformation visuelle qui conserve la fonctionnalité d'un `RadioButton`.
:::

<br/>

## Stylisation {#styling}

### Expanses {#expanses}
Il existe cinq expanse de case à cocher qui sont prises en charge, permettant un stylage rapide sans utiliser de CSS.
Les expanses sont prises en charge par l'utilisation de l'énumération `Expanse`. Voici les expanses prises en charge pour le composant case à cocher : <br/>

<TableBuilder name="RadioButton" />

## Meilleures pratiques {#best-practices}

Pour garantir une expérience utilisateur optimale lors de l'utilisation du composant RadioButton, considérez les meilleures pratiques suivantes :

1. **Étiquetez clairement les options** : Fournissez un texte clair et concis pour chaque option `RadioButton` afin de décrire précisément le choix. Le texte doit être facile à comprendre et à distinguer les uns des autres.

2. **Regroupez les boutons radio** : Regroupez les boutons radio associés pour indiquer leur association. Cela aide les utilisateurs à comprendre qu'une seule option peut être sélectionnée au sein d'un groupe spécifique. Cela peut être fait efficacement en utilisant le composant [`RadioButtonGroup`](/docs/components/radiobuttongroup).

3. **Fournissez une sélection par défaut** : Si applicable, envisagez de fournir une sélection par défaut pour les boutons radio afin de guider les utilisateurs lorsqu'ils rencontrent pour la première fois les options. La sélection par défaut doit correspondre au choix le plus courant ou préféré.
