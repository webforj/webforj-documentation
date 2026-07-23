---
title: RadioButtonGroup
slug: radiobuttongroup
sidebar_position: 100
description: >-
  Coordinate mutually exclusive RadioButton selections with RadioButtonGroup,
  including nested containers and dynamic membership.
_i18n_hash: a616c60faaf0d58f9d9a1e778318880a
---
<DocChip chip='since' label='23.01' />
<JavadocLink type="foundation" location="com/webforj/component/optioninput/RadioButtonGroup" top='true'/>

Le `RadioButtonGroup` gère une collection de [`RadioButton`](/docs/components/radiobutton) composants. Un seul `RadioButton` peut être sélectionné dans un `RadioButtonGroup`. Lorsque l'utilisateur coche un nouveau bouton radio, celui précédemment sélectionné dans le groupe est automatiquement décoché.

<!-- INTRO_END -->

## Création d'un `RadioButtonGroup` {#creating-a-radiobuttongroup}

:::important Rendu du `RadioButtonGroup`
Le composant `RadioButtonGroup` ne rend pas un élément HTML. Il fournit simplement la logique pour faire en sorte que les composants `RadioButton` se comportent comme un groupe plutôt qu'individuellement.
:::

Créez des composants `RadioButton` individuels et passez-les au constructeur du `RadioButtonGroup`. Un seul bouton dans le groupe peut être sélectionné à la fois.

<ComponentDemo
path='/webforj/radiobuttongroup'
files={['src/main/java/com/webforj/samples/views/radiobutton/RadioButtonGroupView.java']}
height='200px'
/>


## Ajouter et supprimer des composants `RadioButton` {#adding-and-removing-radiobuttons}

Vous pouvez inclure des composants `RadioButton` dans le constructeur du `RadioButtonGroup` pour créer un groupe à partir des composants fournis. Pour ajouter ou supprimer un `RadioButton` d'un `RadioButtonGroup` existant, utilisez les méthodes `add()` ou `remove()`.

:::tip Obtenir le groupe d'un `RadioButton`
Le composant `RadioButton` dispose de la méthode `getButtonGroup()`, qui renvoie le `RadioButtonGroup` auquel il appartient, ou `null` s'il n'a pas de groupe.
:::

## Imbrication <DocChip chip='since' label='25.11' /> {#nesting}

Comme d'autres composants, vous pouvez imbriquer un `RadioButtonGroup` dans un conteneur, afin de ne pas avoir à ajouter directement chaque `RadioButton` individuel.

```java
RadioButton agree = new RadioButton("Accepter");
RadioButton neutral = new RadioButton("Neutre");
RadioButton disagree = new RadioButton("Ne pas accepter");

RadioButtonGroup group = new RadioButtonGroup("choix", agree, neutral, disagree);

Fieldset fieldset = new Fieldset("Options");
fieldset.add(group);
```

## Utilisation de `RadioButtonGroupChangeEvent` {#using-radiobuttongroupchangeevent}

Chaque `RadioButton` peut avoir son propre écouteur d'événements pour détecter lorsqu'un utilisateur le bascule. Cependant, un avantage d'utiliser un `RadioButtonGroup` est que vous pouvez utiliser un seul écouteur d'événements qui répond à tous les boutons radio dans le groupe avec le [`RadioButtonGroupChangeEvent`](https://javadoc.io/doc/com.webforj/webforj-foundation/latest/com/webforj/component/optioninput/event/RadioButtonGroupChangeEvent.html).

**Ajout d'écouteurs d'événements à chaque `RadioButton`**

```java
agree.onValueChange(e -> changeEvent());
neutral.onValueChange(e -> changeEvent());
disagree.onValueChange(e -> changeEvent());
```

**Ajout d'un seul écouteur d'événements au `RadioButtonGroup`**

```java
RadioButtonGroup group = new RadioButtonGroup("choix", agree, neutral, disagree);
group.onChange(e -> changeEvent());
```

L'exemple suivant de [Placement de Drawer](/docs/components/drawer#placement) utilise le `RadioButtonGroupChangeEvent` pour changer automatiquement le placement du composant `Drawer` :

<ComponentDemo
path='/webforj/drawerplacement'
files={['src/main/java/com/webforj/samples/views/drawer/DrawerPlacementView.java']}
height='600px'
/>

## Nommer {#naming}

L'attribut `name` dans un `RadioButtonGroup` regroupe des RadioButtons liés, permettant aux utilisateurs de faire un seul choix parmi les options fournies et en renforçant l'exclusivité parmi les RadioButtons. Le nom d'un groupe n'est pas reflété dans le DOM, cependant, et est un utilitaire pratique pour le développeur Java.
