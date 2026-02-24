---
title: RadioButton
slug: radiobutton
sidebar_position: 95
_i18n_hash: 0445bb7e995db7e0d725964c66690d19
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-radio" />
<DocChip chip='since' label='23.01' />
<JavadocLink type="foundation" location="com/webforj/component/optioninput/RadioButton" top='true'/>

Le composant `RadioButton` est un objet qui peut être sélectionné ou désélectionné et qui affiche son état à l'utilisateur. Les boutons radio sont couramment utilisés lorsque des options mutuellement exclusives sont disponibles, permettant à l'utilisateur de choisir une seule option parmi un ensemble de choix.

<ComponentDemo 
path='/webforj/radiobuttongroup?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/radiobutton/RadioButtonGroupView.java'
height="200px"
/>

:::tip Regroupement des composants `RadioButton`
Utilisez un [`RadioButtonGroup`](/docs/components/radiobuttongroup) pour gérer un ensemble de boutons radio lorsque vous souhaitez que les utilisateurs choisissent une seule option.
:::

## Usages {#usages}

Le `RadioButton` est mieux utilisé dans des scénarios où les utilisateurs doivent effectuer une seule sélection parmi un ensemble d'options prédéfini. Voici quelques exemples d'utilisation du `RadioButton` :

1. **Enquêtes ou questionnaires** : Les boutons radio sont couramment utilisés dans les enquêtes ou les questionnaires où les utilisateurs doivent sélectionner une seule réponse parmi une liste d'options.

2. **Paramètres de préférence** : Les applications impliquant des panneaux de préférences ou de paramètres utilisent souvent des boutons radio pour permettre aux utilisateurs de choisir une seule option parmi un ensemble de choix mutuellement exclusifs.

3. **Filtrage ou tri** : Un `RadioButton` peut être utilisé dans des applications qui nécessitent que les utilisateurs sélectionnent un filtre ou une option de tri unique, comme trier une liste d'éléments par différents critères.

## Texte et positionnement {#text-and-positioning}

Les boutons radio peuvent utiliser la méthode ```setText(String text)```, qui sera positionnée près du bouton radio selon la `Position` intégrée. Les boutons radio disposent d'une fonctionnalité intégrée pour définir le texte à afficher soit à droite, soit à gauche du composant. Par défaut, le texte sera affiché à droite du composant. Le positionnement du texte horizontal est pris en charge par l'utilisation de l'énumération `HorizontalAlignment`. Voici les deux paramètres : <br/>

<ComponentDemo 
path='/webforj/radiobuttontext?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/radiobutton/RadioButtonTextView.java'
height="120px"
/>


## Activation {#activation}

Les boutons radio peuvent être contrôlés en utilisant deux types d'activation : activation manuelle et activation automatique. Cela détermine quand un `RadioButton` changera d'état.

<ComponentDemo 
path='/webforj/radiobuttonactivation?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/radiobutton/RadioButtonActivationView.java'
height="175px"
/>

### Activation manuelle {#manual-activation}

Lorsqu'un bouton radio est réglé sur une activation manuelle, cela signifie qu'il ne sera pas automatiquement coché lorsqu'il obtient le focus. 
L'activation manuelle permet à l'utilisateur de naviguer parmi les options de bouton radio à l'aide du clavier ou d'autres méthodes d'entrée sans changer immédiatement l'option sélectionnée.

Si le bouton radio fait partie d'un groupe, sélectionner un bouton radio différent au sein du groupe désélectionnera automatiquement le bouton radio précédemment sélectionné. 
L'activation manuelle permet un meilleur contrôle sur le processus de sélection, nécessitant une action explicite de l'utilisateur pour changer l'option sélectionnée.


### Activation automatique {#auto-activation}

L'activation automatique est l'état par défaut d'un `RadioButton`, et signifie que le bouton sera coché lorsqu'il obtiendra le focus pour une raison quelconque. Cela signifie que non seulement un clic, mais aussi un auto-focus ou une navigation par onglets cocheront le bouton.

:::tip Remarque
La valeur d'activation par défaut est **`MANUAL`** activation.
:::


## Interrupteurs {#switches}

Un `RadioButton` peut également être configuré pour s'afficher comme un interrupteur qui offre une représentation visuelle alternative pour sélectionner des options. Normalement, les boutons radio sont de forme circulaire ou arrondie et indiquent un choix unique parmi un groupe d'options. 

<ComponentDemo 
path='/webforj/radiobuttonswitch?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/radiobutton/RadioButtonSwitchView.java'
height="120px"
/>

Un `RadioButton` peut être transformé en un interrupteur qui ressemble à un interrupteur à bascule ou à un slider en utilisant l'une des deux méthodes :

1. **La méthode de fabrique** : Le RadioButton peut être créé en utilisant les méthodes de fabrique suivantes :

```java
Switch(String text, boolean checked);
Switch(String text);
Switch();
```

Ces méthodes imitent un constructeur `RadioButton`, et créeront le composant avec la propriété switch déjà activée.

2. **Setter** : Il est également possible de changer un `RadioButton` existant en interrupteur en utilisant le setter approprié :

```java
myRadioButton.setSwitch(true);
```


Lorsqu'un `RadioButton` est affiché comme un interrupteur, il apparaîtra généralement sous une forme oblongue avec un indicateur qui peut être activé ou désactivé. Cette représentation visuelle offre aux utilisateurs une interface plus intuitive et familière, similaire aux interrupteurs physiques que l'on trouve couramment dans les appareils électroniques. 

Configurer un `RadioButton` pour s'afficher comme un interrupteur peut améliorer l'expérience utilisateur en fournissant un moyen clair et simple de sélectionner des options. Cela peut améliorer l'attrait visuel et l'utilisabilité des formulaires, des panneaux de réglages ou tout autre élément d'interface nécessitant plusieurs choix.

:::info
Le comportement du `RadioButton` reste le même lorsqu'il est rendu en tant qu'interrupteur, ce qui signifie qu'une seule option peut être sélectionnée à la fois dans un groupe. L'apparence similaire à un interrupteur est une transformation visuelle qui conserve la fonctionnalité d'un `RadioButton`.
:::

<br/>

## Styling {#styling}

### Expanses {#expanses}
Il existe cinq expanses de cases à cocher qui sont prises en charge, ce qui permet un style rapide sans utiliser de CSS.
Les expanses sont prises en charge par l'utilisation de l'énumération `Expanse`. Voici les expanses prises en charge pour le composant checkbox : <br/>

<TableBuilder name="RadioButton" />

## Meilleures pratiques {#best-practices}

Pour garantir une expérience utilisateur optimale lors de l'utilisation du composant RadioButton, considérez les meilleures pratiques suivantes :

1. **Étiquette clairement les options** : Fournissez un texte clair et concis pour chaque option `RadioButton` afin de décrire précisément le choix. Le texte doit être facile à comprendre et à distinguer les uns des autres.

2. **Grouper les boutons radio** : Regroupez les boutons radio connexes pour indiquer leur association. Cela aide les utilisateurs à comprendre qu'une seule option peut être sélectionnée dans un groupe spécifique. Cela peut être réalisé efficacement en utilisant le composant [`RadioButtonGroup`](/docs/components/radiobuttongroup).

3. **Fournir une sélection par défaut** : Si applicable, envisagez de fournir une sélection par défaut pour les boutons radio afin de guider les utilisateurs lorsqu'ils rencontrent pour la première fois les options. La sélection par défaut devrait correspondre à l'option la plus courante ou préférée.
