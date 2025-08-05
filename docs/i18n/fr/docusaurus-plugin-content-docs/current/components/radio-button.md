---
title: RadioButton
slug: radiobutton
sidebar_position: 95
_i18n_hash: bf7e30274560f1e29fc307b5894c533a
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-radio" />
<DocChip chip='since' label='23.01' />
<JavadocLink type="foundation" location="com/webforj/component/optioninput/RadioButton" top='true'/>

La classe `RadioButton` crée un objet pouvant être sélectionné ou désélectionné, et qui affiche son état à l'utilisateur. Par convention, un seul bouton radio dans un groupe peut être sélectionné à la fois. Les boutons radio sont couramment utilisés lorsque des options mutuellement exclusives sont disponibles, permettant à l'utilisateur de choisir une seule option parmi un ensemble de choix.

<ComponentDemo 
path='/webforj/radiobuttongroup?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/radiobutton/RadioButtonGroupView.java'
height="200px"
/>

## Usages {#usages}

Le `RadioButton` est le mieux utilisé dans des scénarios où les utilisateurs doivent faire une sélection unique parmi un ensemble prédéfini d'options. Voici quelques exemples de quand utiliser le `RadioButton` :

1. **Enquêtes ou questionnaires** : Les boutons radio sont couramment utilisés dans les enquêtes ou questionnaires où les utilisateurs doivent sélectionner une seule réponse parmi une liste d'options.

2. **Paramètres de préférence** : Les applications qui impliquent des panneaux de préférences ou de paramètres utilisent souvent des boutons radio pour permettre aux utilisateurs de choisir une seule option parmi un ensemble de choix mutuellement exclusifs.

3. **Filtrage ou tri** : Un `RadioButton` peut être utilisé dans des applications qui nécessitent que les utilisateurs sélectionnent un seul filtre ou une option de tri, comme trier une liste d'articles selon différents critères.

## Texte et positionnement {#text-and-positioning}

Les boutons radio peuvent utiliser la méthode ```setText(String text)```, qui sera positionnée près du bouton radio selon la `Position` intégrée.
Les boutons radio disposent d'une fonctionnalité intégrée pour définir le texte à afficher soit à droite, soit à gauche du composant. Par défaut, le texte sera affiché à droite du composant. Le positionnement du texte horizontal est pris en charge par l'utilisation de la classe énumérée `HorizontalAlignment`. Voici les deux paramètres : <br/>

<ComponentDemo 
path='/webforj/radiobuttontext?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/radiobutton/RadioButtonTextView.java'
height="120px"
/>

## Activation {#activation}

Les boutons radio peuvent être contrôlés à l'aide de deux types d'activation : l'activation manuelle et l'activation automatique. Ceux-ci dictent quand un `RadioButton` changera d'état.

<ComponentDemo 
path='/webforj/radiobuttonactivation?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/radiobutton/RadioButtonActivationView.java'
height="175px"
/>

### Activation manuelle {#manual-activation}

Lorsqu'un bouton radio est réglé sur l'activation manuelle, cela signifie qu'il ne sera pas automatiquement coché lorsqu'il reçoit le focus.
L'activation manuelle permet à l'utilisateur de naviguer à travers les options de boutons radio à l'aide du clavier ou d'autres méthodes d'entrée sans changer immédiatement l'option sélectionnée.

Si le bouton radio fait partie d'un groupe, sélectionner un bouton radio différent dans le groupe désélectionnera automatiquement le bouton radio précédemment sélectionné.
L'activation manuelle offre un meilleur contrôle sur le processus de sélection, nécessitant une action explicite de l'utilisateur pour changer l'option sélectionnée.

### Activation automatique {#auto-activation}

L'activation automatique est l'état par défaut d'un `RadioButton`, et signifie que le bouton sera coché chaque fois qu'il reçoit le focus pour une raison quelconque. Cela signifie que non seulement le clic, mais également le focus automatique ou la navigation par onglets cocheront le bouton.

:::tip Remarque
La valeur d'activation par défaut est **`MANUELLE`**.
:::

## Interrupteurs {#switches}

Un `RadioButton` peut également être configuré pour s'afficher comme un interrupteur, offrant ainsi une représentation visuelle alternative pour sélectionner des options. Normalement, les boutons radio sont de forme circulaire ou arrondie et indiquent un seul choix parmi un groupe d'options.

<ComponentDemo 
path='/webforj/radiobuttonswitch?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/radiobutton/RadioButtonSwitchView.java'
height="120px"
/>

Un `RadioButton` peut être transformé en interrupteur qui ressemble à un interrupteur bascule ou à un curseur en utilisant l'une des deux méthodes :

1. **La méthode de fabrication** : Le `RadioButton` peut être créé en utilisant les méthodes de fabrication suivantes :

```java
Switch(String text, boolean checked);
Switch(String text);
Switch();
```

Ces méthodes imitent un constructeur de `RadioButton`, et créeront le composant avec la propriété d'interrupteur déjà activée.

2. **Setter** : Il est également possible de changer un `RadioButton` existant en interrupteur en utilisant le setter approprié :

```java
myRadioButton.setSwitch(true);
```

Lorsqu'un `RadioButton` est affiché comme un interrupteur, il apparaît généralement sous la forme d'une forme oblongue avec un indicateur pouvant être activé ou désactivé. Cette représentation visuelle offre aux utilisateurs une interface plus intuitive et familière, similaire aux interrupteurs physiques couramment trouvés dans les appareils électroniques.

Configurer un `RadioButton` pour s'afficher comme un interrupteur peut améliorer l'expérience utilisateur en fournissant un moyen clair et direct de sélectionner des options. Cela peut améliorer l'attrait visuel et la convivialité des formulaires, des panneaux de paramètres ou de tout autre élément d'interface nécessitant plusieurs choix.

:::info
Le comportement du `RadioButton` reste le même lorsqu'il est rendu comme un interrupteur, ce qui signifie qu'une seule option peut être sélectionnée à la fois dans un groupe. L'apparence semblable à un interrupteur est une transformation visuelle qui conserve la fonctionnalité d'un `RadioButton`.
:::

<br/>

## Style {#styling}

### Expansions {#expanses}
Il existe cinq expansions de cases à cocher qui sont prises en charge et qui permettent une mise en forme rapide sans utiliser de CSS.
Les expansions sont prises en charge par l'utilisation de la classe énumérée `Expanse`. Voici les expansions prises en charge pour le composant de case à cocher : <br/>

<TableBuilder name="RadioButton" />

## Meilleures pratiques {#best-practices}

Pour garantir une expérience utilisateur optimale lors de l'utilisation du composant RadioButton, considérez les meilleures pratiques suivantes :

1. **Étiquetez clairement les options** : Fournissez un texte clair et concis pour chaque option de `RadioButton` afin de décrire précisément le choix. Le texte doit être facile à comprendre et à distinguer les uns des autres.

2. **Regroupez les boutons radio** : Regroupez les boutons radio associés pour indiquer leur association. Cela aide les utilisateurs à comprendre qu'une seule option peut être sélectionnée dans un groupe spécifique. Cela peut être réalisé efficacement en utilisant le composant [`RadioButtonGroup`](/docs/components/radiobuttongroup).

3. **Fournissez une sélection par défaut** : Si applicable, envisagez de fournir une sélection par défaut pour les boutons radio afin de guider les utilisateurs lorsqu'ils rencontrent pour la première fois les options. La sélection par défaut doit correspondre au choix le plus courant ou préféré.
