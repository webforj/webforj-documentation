---
title: RadioButton
slug: radiobutton
sidebar_position: 95
_i18n_hash: efd1171b68ca07b593064abe0366ded7
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-radio" />
<DocChip chip='since' label='23.01' />
<JavadocLink type="foundation" location="com/webforj/component/optioninput/RadioButton" top='true'/>

La classe `RadioButton` crée un objet qui peut être sélectionné ou désélectionné, et qui affiche son état à l'utilisateur. Par convention, un seul bouton radio dans un groupe peut être sélectionné à la fois. Les boutons radio sont couramment utilisés lorsque des options mutuellement exclusives sont disponibles, permettant à l'utilisateur de choisir une seule option parmi un ensemble de choix.

<ComponentDemo 
path='/webforj/radiobuttongroup?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/radiobutton/RadioButtonGroupView.java'
height="200px"
/>

## Usages {#usages}

Le `RadioButton` est mieux utilisé dans des scénarios où les utilisateurs doivent faire une seule sélection parmi un ensemble d'options prédéfinies. Voici quelques exemples de quand utiliser le `RadioButton` :

1. **Enquêtes ou questionnaires** : Les boutons radio sont couramment utilisés dans les enquêtes ou questionnaires où les utilisateurs doivent sélectionner une seule réponse parmi une liste d'options.

2. **Paramètres de préférence** : Les applications qui impliquent des panneaux de préférence ou de paramètres utilisent souvent les boutons radio pour permettre aux utilisateurs de choisir une seule option parmi un ensemble de choix mutuellement exclusifs.

3. **Filtrage ou tri** : Un `RadioButton` peut être utilisé dans des applications qui nécessitent que les utilisateurs sélectionnent une option de filtrage ou de tri unique, comme trier une liste d'éléments selon différents critères.

## Texte et positionnement {#text-and-positioning}

Les boutons radio peuvent utiliser la méthode ```setText(String text)```, qui sera positionnée près du bouton radio selon la position intégrée. Les boutons radio disposent de fonctionnalités intégrées pour afficher le texte soit à droite, soit à gauche du composant. Par défaut, le texte sera affiché à droite du composant. Le positionnement du texte horizontal est supporté par l'utilisation de la classe énumérée `HorizontalAlignment`. Voici ci-dessous les deux réglages : <br/>

<ComponentDemo 
path='/webforj/radiobuttontext?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/radiobutton/RadioButtonTextView.java'
height="120px"
/>

## Activation {#activation}

Les boutons radio peuvent être contrôlés en utilisant deux types d'activation : activation manuelle et activation automatique. Celles-ci déterminent quand un `RadioButton` changera d'état.

<ComponentDemo 
path='/webforj/radiobuttonactivation?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/radiobutton/RadioButtonActivationView.java'
height="175px"
/>

### Activation manuelle {#manual-activation}

Lorsqu'un bouton radio est réglé sur l'activation manuelle, cela signifie qu'il ne sera pas automatiquement coché lorsqu'il obtient le focus. L'activation manuelle permet à l'utilisateur de naviguer entre les options de bouton radio à l'aide du clavier ou d'autres méthodes d'entrée sans changer immédiatement l'option sélectionnée.

Si le bouton radio fait partie d'un groupe, sélectionner un autre bouton radio au sein du groupe désélectionnera automatiquement le bouton radio précédemment sélectionné. L'activation manuelle offre un meilleur contrôle sur le processus de sélection, nécessitant une action explicite de l'utilisateur pour changer l'option sélectionnée.

### Activation automatique {#auto-activation}

L'activation automatique est l'état par défaut d'un `RadioButton`, ce qui signifie que le bouton sera coché chaque fois qu'il obtient le focus pour une raison quelconque. Cela signifie que non seulement le clic, mais aussi le focus automatique ou la navigation par onglet cocheront également le bouton.

:::tip Note
La valeur d'activation par défaut est **`MANUAL`** activation.
:::

## Interrupteurs {#switches}

Un `RadioButton` peut également être réglé pour s'afficher comme un interrupteur, offrant une représentation visuelle alternative pour sélectionner des options. Normalement, les boutons radio sont de forme circulaire ou arrondie et indiquent un choix unique parmi un groupe d'options.

<ComponentDemo 
path='/webforj/radiobuttonswitch?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/radiobutton/RadioButtonSwitchView.java'
height="120px"
/>

Un `RadioButton` peut être transformé en un interrupteur qui ressemble à un interrupteur à bascule ou à un curseur en utilisant l'une des deux méthodes :

1. **La méthode de fabrique** : Le RadioButton peut être créé à l'aide des méthodes de fabrique suivantes :

```java
Switch(String text, boolean checked);
Switch(String text);
Switch();
```

Ces méthodes reflètent un constructeur de `RadioButton` et créeront le composant avec la propriété d'interrupteur déjà activée.

2. **Setter** : Il est également possible de changer un `RadioButton` existant en un interrupteur en utilisant le setter approprié :

```java
myRadioButton.setSwitch(true);
```

Lorsqu'un `RadioButton` est affiché comme un interrupteur, il apparaît généralement sous la forme d'une forme oblongue avec un indicateur qui peut être activé ou désactivé. Cette représentation visuelle offre aux utilisateurs une interface plus intuitive et familière, similaire aux interrupteurs physiques couramment trouvés dans les appareils électroniques.

Régler un `RadioButton` pour s'afficher comme un interrupteur peut améliorer l'expérience utilisateur en offrant un moyen clair et simple de sélectionner des options. Cela peut améliorer l'attrait visuel et l'utilisabilité des formulaires, des panneaux de paramètres, ou de tout autre élément d'interface nécessitant des choix multiples.

:::info
Le comportement du `RadioButton` reste le même lorsqu'il est rendu en tant qu'interrupteur, ce qui signifie qu'une seule option peut être sélectionnée à la fois dans un groupe. L'apparence similaire à celle d'un interrupteur est une transformation visuelle qui conserve la fonctionnalité d'un `RadioButton`.
:::

<br/>

## Stylisation {#styling}

### Espaces {#expanses}
Il existe cinq espaces de case à cocher qui sont pris en charge, permettant un stylage rapide sans utiliser de CSS. Les espaces sont pris en charge par l'utilisation de la classe énumérée `Expanse`. Voici ci-dessous les espaces pris en charge pour le composant case à cocher : <br/>

<TableBuilder name="RadioButton" />

## Meilleures pratiques {#best-practices}

Pour garantir une expérience utilisateur optimale lors de l'utilisation du composant RadioButton, considérez les meilleures pratiques suivantes :

1. **Étiqueter clairement les options** : Fournissez un texte clair et concis pour chaque option `RadioButton` afin de décrire avec précision le choix. Le texte doit être facile à comprendre et à distinguer les uns des autres.

2. **Grouper les boutons radio** : Regroupez les boutons radio connexes pour indiquer leur association. Cela aide les utilisateurs à comprendre qu'une seule option peut être sélectionnée au sein d'un groupe spécifique. Cela peut être fait efficacement en utilisant le composant [`RadioButtonGroup`](/docs/components/radiobuttongroup).

3. **Fournir une sélection par défaut** : Si cela est applicable, envisagez de fournir une sélection par défaut pour les boutons radio afin de guider les utilisateurs lorsqu'ils rencontrent les options pour la première fois. La sélection par défaut doit correspondre au choix le plus courant ou préféré.
